package com.hr.leave;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@WebServlet("/leave/*")
public class LeaveServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private LeaveDAO dao = new LeaveDAO();
    private static final DateTimeFormatter TS = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo();
        HttpSession s = req.getSession(false);
        if (s == null || s.getAttribute("username") == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }
        String username = (String) s.getAttribute("username");
        String role = (String) s.getAttribute("role"); // may be null

        if (path == null || "/".equals(path) || "/list".equals(path)) {
            try {
                if ("manager".equalsIgnoreCase(role)) {
                    List<LeaveRequest> all = dao.findAll(getServletContext());
                    req.setAttribute("requests", all);
                } else {
                    List<LeaveRequest> mine = dao.findByUser(username, getServletContext());
                    req.setAttribute("requests", mine);
                }
                req.getRequestDispatcher("/leave_list.jsp").forward(req, resp);
            } catch (Exception e) {
                throw new ServletException(e);
            }
            return;
        }

        if ("/new".equals(path)) {
            req.getRequestDispatcher("/leave_form.jsp").forward(req, resp);
            return;
        }

        resp.sendError(404);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo();
        HttpSession s = req.getSession(false);
        if (s == null || s.getAttribute("username") == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }
        String username = (String) s.getAttribute("username");
        String role = (String) s.getAttribute("role");

        try {
            if ("/create".equals(path)) {
                // create request
                String start = req.getParameter("start_date");
                String end = req.getParameter("end_date");
                String type = req.getParameter("type");
                String reason = req.getParameter("reason");
                String ts = OffsetDateTime.now().format(TS);

                LeaveRequest lr = new LeaveRequest(username, start, end, type, reason, ts);
                dao.save(lr, getServletContext());
                req.getSession().setAttribute("leaveMsg", "Leave request submitted.");
                resp.sendRedirect(req.getContextPath() + "/leave/list");
                return;
            }

            if ("/decide".equals(path)) {
                // manager approve/deny
                if (!"manager".equalsIgnoreCase(role)) {
                    resp.sendError(HttpServletResponse.SC_FORBIDDEN);
                    return;
                }
                int id = Integer.parseInt(req.getParameter("id"));
                String decision = req.getParameter("decision"); // APPROVED or DENIED
                String ts = OffsetDateTime.now().format(TS);
                dao.updateStatus(id, decision, username, ts, getServletContext());
                req.getSession().setAttribute("leaveMsg", "Request " + decision.toLowerCase() + ".");
                resp.sendRedirect(req.getContextPath() + "/leave/list");
                return;
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }

        resp.sendError(404);
    }
}
