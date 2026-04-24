package com.hr.auth;

import java.io.IOException;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;  // optional warning fix

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String u = req.getParameter("username");
        String p = req.getParameter("password");

        // Path to SQLite DB inside WEB-INF/db
        String dbPath = getServletContext().getRealPath("/WEB-INF/db/employee_portal.db");
        String url = "jdbc:sqlite:" + dbPath;

        boolean authenticated = false;
        String role = null;

        try {
            // VERY IMPORTANT: Load SQLite driver BEFORE getConnection()
            Class.forName("org.sqlite.JDBC");

            try (Connection conn = DriverManager.getConnection(url)) {
                String sql = "SELECT role FROM users WHERE username = ? AND password = ?";
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setString(1, u);
                    ps.setString(2, p);

                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            authenticated = true;
                            role = rs.getString("role");
                        }
                    }
                }
            }

        } catch (ClassNotFoundException e) {
            throw new ServletException("SQLite driver not found. Ensure sqlite-jdbc jar is in WEB-INF/lib.", e);

        } catch (SQLException e) {
            throw new ServletException("Database error while authenticating", e);
        }

        if (authenticated) {
            HttpSession s = req.getSession(true);
            s.setAttribute("username", u);
            s.setAttribute("role", role);
            resp.sendRedirect(req.getContextPath() + "/welcome.jsp");

        } else {
            req.setAttribute("error", "Invalid credentials");
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect(req.getContextPath() + "/login.jsp");
    }
}
