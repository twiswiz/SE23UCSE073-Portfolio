package com.hr.employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

public class EmployeeDAO {

    // Get DB connection using servlet context
    private Connection getConnection(ServletContext ctx) throws Exception {
        String dbPath = ctx.getRealPath("/WEB-INF/db/employee_portal.db");
        String url = "jdbc:sqlite:" + dbPath;

        Class.forName("org.sqlite.JDBC");
        return DriverManager.getConnection(url);
    }

    // INSERT employee
    public void addEmployee(Employee emp, ServletContext ctx) throws Exception {
        String sql = "INSERT INTO employees (name, email, department, salary) VALUES (?, ?, ?, ?)";

        try (Connection conn = getConnection(ctx);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, emp.getName());
            ps.setString(2, emp.getEmail());
            ps.setString(3, emp.getDepartment());
            ps.setDouble(4, emp.getSalary());
            ps.executeUpdate();
        }
    }

    // GET all employees
    public List<Employee> getAllEmployees(ServletContext ctx) throws Exception {
        List<Employee> list = new ArrayList<>();
        String sql = "SELECT * FROM employees";

        try (Connection conn = getConnection(ctx);
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Employee e = new Employee(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("department"),
                        rs.getDouble("salary")
                );
                list.add(e);
            }
        }
        return list;
    }

    // GET employee by id
    public Employee getEmployeeById(int id, ServletContext ctx) throws Exception {
        String sql = "SELECT * FROM employees WHERE id = ?";
        Employee e = null;

        try (Connection conn = getConnection(ctx);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    e = new Employee(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getString("department"),
                            rs.getDouble("salary")
                    );
                }
            }
        }
        return e;
    }

    // UPDATE employee
    public void updateEmployee(Employee emp, ServletContext ctx) throws Exception {
        String sql = "UPDATE employees SET name=?, email=?, department=?, salary=? WHERE id=?";

        try (Connection conn = getConnection(ctx);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, emp.getName());
            ps.setString(2, emp.getEmail());
            ps.setString(3, emp.getDepartment());
            ps.setDouble(4, emp.getSalary());
            ps.setInt(5, emp.getId());

            ps.executeUpdate();
        }
    }

    // DELETE employee
    public void deleteEmployee(int id, ServletContext ctx) throws Exception {
        String sql = "DELETE FROM employees WHERE id=?";

        try (Connection conn = getConnection(ctx);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
