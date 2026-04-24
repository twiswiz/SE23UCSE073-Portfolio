package com.hr.attendance;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

public class AttendanceDAO {

    // Get DB connection
    private Connection getConnection(ServletContext context) throws SQLException {
        String relativePath = "/WEB-INF/db/employee_portal.db";
        String dbPath = context.getRealPath(relativePath);
        String url = "jdbc:sqlite:" + dbPath;
        return DriverManager.getConnection(url);
    }

    // Insert new attendance record
    public void insertAttendance(Attendance a, ServletContext context) {
        String sql = "INSERT INTO attendance(username, event_type, event_time, note) VALUES (?, ?, ?, ?)";

        try (Connection conn = getConnection(context);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, a.getUsername());
            ps.setString(2, a.getEventType());
            ps.setString(3, a.getEventTime());
            ps.setString(4, a.getNote());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Fetch history for a user
    public List<Attendance> getUserEvents(String username, ServletContext context) {
        List<Attendance> list = new ArrayList<>();
        String sql = "SELECT id, username, event_type, event_time, note FROM attendance WHERE username = ? ORDER BY id DESC";

        try (Connection conn = getConnection(context);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Attendance a = new Attendance();
                a.setId(rs.getInt("id"));
                a.setUsername(rs.getString("username"));
                a.setEventType(rs.getString("event_type"));
                a.setEventTime(rs.getString("event_time"));
                a.setNote(rs.getString("note"));
                list.add(a);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // Get latest (most recent) event for a user (or null)
    public Attendance getLatestEvent(String username, ServletContext context) {
        String sql = "SELECT id, username, event_type, event_time, note FROM attendance WHERE username = ? ORDER BY id DESC LIMIT 1";
        try (Connection conn = getConnection(context);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Attendance a = new Attendance();
                a.setId(rs.getInt("id"));
                a.setUsername(rs.getString("username"));
                a.setEventType(rs.getString("event_type"));
                a.setEventTime(rs.getString("event_time"));
                a.setNote(rs.getString("note"));
                return a;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
