package com.hr.leave;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContext;

public class LeaveDAO {

    // Ensure the SQLite driver is loaded; if not, throw SQLException
    private Connection getConnection(ServletContext ctx) throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new SQLException("SQLite JDBC driver not found on classpath", e);
        }

        String dbPath = ctx.getRealPath("/WEB-INF/db/employee_portal.db");
        String url = "jdbc:sqlite:" + dbPath;
        return DriverManager.getConnection(url);
    }

    // CREATE
    public void save(LeaveRequest lr, ServletContext ctx) throws SQLException {
        String sql = "INSERT INTO leave_requests (username, start_date, end_date, type, reason, status, requested_at) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection(ctx);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, lr.getUsername());
            ps.setString(2, lr.getStartDate());
            ps.setString(3, lr.getEndDate());
            ps.setString(4, lr.getType());
            ps.setString(5, lr.getReason());
            ps.setString(6, lr.getStatus());
            ps.setString(7, lr.getRequestedAt());

            ps.executeUpdate();
        }
    }

    // FETCH: user requests
    public List<LeaveRequest> findByUser(String username, ServletContext ctx) throws SQLException {
        List<LeaveRequest> list = new ArrayList<>();
        String sql = "SELECT * FROM leave_requests WHERE username = ? ORDER BY requested_at DESC";

        try (Connection conn = getConnection(ctx);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        }
        return list;
    }

    // FETCH: all requests (manager)
    public List<LeaveRequest> findAll(ServletContext ctx) throws SQLException {
        List<LeaveRequest> list = new ArrayList<>();
        String sql = "SELECT * FROM leave_requests ORDER BY requested_at DESC";

        try (Connection conn = getConnection(ctx);
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapRow(rs));
            }
        }
        return list;
    }

    // APPROVE / DENY
    public void updateStatus(int id, String status, String manager, String time, ServletContext ctx)
            throws SQLException {

        String sql = "UPDATE leave_requests SET status = ?, decision_by = ?, decision_at = ? WHERE id = ?";

        try (Connection conn = getConnection(ctx);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setString(2, manager);
            ps.setString(3, time);
            ps.setInt(4, id);

            ps.executeUpdate();
        }
    }

    private LeaveRequest mapRow(ResultSet rs) throws SQLException {
        LeaveRequest lr = new LeaveRequest();

        lr.setId(rs.getInt("id"));
        lr.setUsername(rs.getString("username"));
        lr.setStartDate(rs.getString("start_date"));
        lr.setEndDate(rs.getString("end_date"));
        lr.setType(rs.getString("type"));
        lr.setReason(rs.getString("reason"));
        lr.setStatus(rs.getString("status"));
        lr.setRequestedAt(rs.getString("requested_at"));
        lr.setDecisionBy(rs.getString("decision_by"));
        lr.setDecisionAt(rs.getString("decision_at"));
        
        return lr;
    }
}
