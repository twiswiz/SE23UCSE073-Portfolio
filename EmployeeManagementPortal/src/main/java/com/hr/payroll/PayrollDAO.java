package com.hr.payroll;

import java.sql.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import javax.servlet.ServletContext;

/**
 * Payroll DAO: computes hours worked by pairing IN/OUT events and calculates payable salary.
 */
public class PayrollDAO {

    // standard month work hours (adjustable)
    private static final double STANDARD_MONTH_HOURS = 160.0;

    // timestamp format used in attendance table
    private static final DateTimeFormatter TS = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private Connection getConnection(ServletContext ctx) throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new SQLException("SQLite JDBC driver not found", e);
        }
        String dbPath = ctx.getRealPath("/WEB-INF/db/employee_portal.db");
        String url = "jdbc:sqlite:" + dbPath;
        return DriverManager.getConnection(url);
    }

    /**
     * Compute total hours worked for a user for a specific year/month.
     * Pairs IN -> OUT chronologically. If IN without OUT at end of period it's ignored.
     */
    public double computeHoursForMonth(String username, int year, int month, ServletContext ctx) throws SQLException {
        // Build month start/end strings for simple filtering (YYYY-MM)
        String monthPrefix = String.format("%04d-%02d", year, month); // e.g. "2025-11"
        String sql = "SELECT event_type, event_time FROM attendance " +
                     "WHERE username = ? AND event_time LIKE ? " +
                     "ORDER BY event_time ASC";

        double totalMinutes = 0.0;

        try (Connection conn = getConnection(ctx);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, monthPrefix + "%"); // event_time starts with 'YYYY-MM'

            try (ResultSet rs = ps.executeQuery()) {
                LocalDateTime lastIn = null;
                while (rs.next()) {
                    String type = rs.getString("event_type");
                    String time = rs.getString("event_time");
                    LocalDateTime dt;
                    try {
                        dt = LocalDateTime.parse(time, TS);
                    } catch (Exception ex) {
                        // skip malformed timestamps
                        continue;
                    }

                    if ("IN".equalsIgnoreCase(type)) {
                        // if there's already an unmatched IN, replace it with newer IN (assume user missed OUT)
                        lastIn = dt;
                    } else if ("OUT".equalsIgnoreCase(type)) {
                        if (lastIn != null) {
                            Duration d = Duration.between(lastIn, dt);
                            long minutes = d.toMinutes();
                            if (minutes > 0) { // only positive durations
                                totalMinutes += minutes;
                            }
                            lastIn = null;
                        } else {
                            // OUT without IN -> ignore
                        }
                    }
                }
            }
        }
        return totalMinutes / 60.0; // hours
    }

    /**
     * Tries to fetch monthly salary for a user. The employees table may store either "username" or "name".
     * Returns 0 if not found or on error.
     */
    public double fetchMonthlySalary(String username, ServletContext ctx) {
        String sql = "SELECT salary FROM employees WHERE username = ? OR name = ? LIMIT 1";
        try (Connection conn = getConnection(ctx);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, username);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // salary column assumed numeric
                    return rs.getDouble("salary");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0.0;
    }

    /**
     * Build a PayrollSummary for a user for the selected month.
     */
    public PayrollSummary getMonthlyPayroll(String username, int year, int month, ServletContext ctx) {
        try {
            double hours = computeHoursForMonth(username, year, month, ctx);
            double monthlySalary = fetchMonthlySalary(username, ctx);
            double payable = 0.0;
            if (monthlySalary > 0.0) {
                payable = (monthlySalary / STANDARD_MONTH_HOURS) * hours;
            }
            PayrollSummary s = new PayrollSummary(username, hours, monthlySalary, payable);
            return s;
        } catch (Exception e) {
            e.printStackTrace();
            return new PayrollSummary(username, 0.0, 0.0, 0.0);
        }
    }

    /**
     * For manager: get payroll summary for all users who have attendance this month.
     * We will fetch distinct usernames from attendance table for month.
     */
    public List<PayrollSummary> getMonthlyPayrollForAll(int year, int month, ServletContext ctx) {
        List<PayrollSummary> res = new ArrayList<>();
        String monthPrefix = String.format("%04d-%02d", year, month);
        String sql = "SELECT DISTINCT username FROM attendance WHERE event_time LIKE ?";

        try (Connection conn = getConnection(ctx);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, monthPrefix + "%");

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String username = rs.getString("username");
                    PayrollSummary s = getMonthlyPayroll(username, year, month, ctx);
                    res.add(s);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
}
