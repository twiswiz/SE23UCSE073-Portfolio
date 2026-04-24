package com.hr.dashboard;

import com.hr.leave.LeaveDAO;
import com.hr.leave.LeaveRequest;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/employee/dashboard")
public class EmployeeDashboardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // get logged-in username from session
        HttpSession session = req.getSession(false);
        String username = null;
        if (session != null) {
            username = (String) session.getAttribute("username");
        }

        // attach username for display
        req.setAttribute("username", username);

        // fetch user's leave requests via LeaveDAO and set as request attribute
        if (username != null) {
            ServletContext ctx = getServletContext();
            try {
                LeaveDAO leaveDao = new LeaveDAO();
                List<LeaveRequest> leaves = leaveDao.findByUser(username, ctx);
                req.setAttribute("leaveRequests", leaves);
            } catch (SQLException e) {
                // don't crash the page — show an error message instead
                req.setAttribute("leaveError", "Unable to load leave requests: " + e.getMessage());
                e.printStackTrace();
            }
        }

        // forward to JSP (employee_dashboard.jsp)
        RequestDispatcher rd = req.getRequestDispatcher("/employee_dashboard.jsp");
        rd.forward(req, resp);
    }
}
