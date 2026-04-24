package com.hr.dashboard;

import com.hr.employee.Employee;
import com.hr.employee.EmployeeDAO;
import com.hr.leave.LeaveDAO;
import com.hr.leave.LeaveRequest;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.RequestDispatcher;
import java.io.IOException;
import java.util.List;

@WebServlet("/hr/dashboard")
public class HRDashboardServlet extends HttpServlet {

    private final EmployeeDAO employeeDAO = new EmployeeDAO();
    private final LeaveDAO leaveDAO = new LeaveDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        String role = (session != null) ? (String) session.getAttribute("role") : null;

        // Only HR allowed
        if (role == null || !role.equals("hr")) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        try {
            // Load employee list
            List<Employee> employees = employeeDAO.getAllEmployees(getServletContext());
            req.setAttribute("employees", employees);

            // Load leave requests (optional)
            List<LeaveRequest> leaves = leaveDAO.findAll(getServletContext());
            req.setAttribute("leaves", leaves);

        } catch (Exception e) {
            req.setAttribute("err", "Failed to load HR dashboard data: " + e.getMessage());
        }

        RequestDispatcher rd = req.getRequestDispatcher("/hr_dashboard.jsp");
        rd.forward(req, resp);
    }
}
