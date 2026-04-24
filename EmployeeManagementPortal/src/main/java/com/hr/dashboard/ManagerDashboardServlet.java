package com.hr.dashboard;

import com.hr.leave.LeaveDAO;
import com.hr.leave.LeaveRequest;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.RequestDispatcher;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/manager/dashboard")
public class ManagerDashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private LeaveDAO leaveDAO = new LeaveDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        String username = (session != null) ? (String) session.getAttribute("username") : null;
        String role = (session != null) ? (String) session.getAttribute("role") : null;

        // redirect if not logged in
        if (username == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        // allow only manager or hr
        if (!( "manager".equalsIgnoreCase(role) || "hr".equalsIgnoreCase(role) )) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied");
            return;
        }

        try {
            // fetch all leave requests (LeaveDAO handles DB access)
            List<LeaveRequest> allLeaves = leaveDAO.findAll(getServletContext());

            // filter only PENDING for manager review
            List<LeaveRequest> pending = new ArrayList<>();
            if (allLeaves != null) {
                for (LeaveRequest lr : allLeaves) {
                    if ("PENDING".equalsIgnoreCase(lr.getStatus())) {
                        pending.add(lr);
                    }
                }
            }

            req.setAttribute("pendingLeaves", pending);
        } catch (Exception e) {
            req.setAttribute("err", "Unable to load leave requests: " + e.getMessage());
            e.printStackTrace();
        }

        RequestDispatcher rd = req.getRequestDispatcher("/manager_dashboard.jsp");
        rd.forward(req, resp);
    }
}
