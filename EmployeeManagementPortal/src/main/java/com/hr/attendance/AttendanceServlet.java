package com.hr.attendance;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/attendance/*")
public class AttendanceServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private AttendanceDAO dao = new AttendanceDAO();
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String path = req.getPathInfo();
        HttpSession session = req.getSession(false);

        if (session == null || session.getAttribute("username") == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        String username = (String) session.getAttribute("username");

        if (path == null || path.equals("/")) {
            resp.sendRedirect(req.getContextPath() + "/attendance/history");
            return;
        }

        switch (path) {
            case "/history":
                listHistory(username, req, resp);
                break;
            default:
                resp.sendError(404, "Unknown attendance action");
        }
    }

    // POST to /attendance/clock with parameter "action" = "IN" or "OUT"
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String path = req.getPathInfo();
        HttpSession session = req.getSession(false);

        if (session == null || session.getAttribute("username") == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        String username = (String) session.getAttribute("username");

        if ("/clock".equals(path)) {
            String action = req.getParameter("action");
            String note = req.getParameter("note");
            if (action == null || !(action.equals("IN") || action.equals("OUT"))) {
                resp.sendRedirect(req.getContextPath() + "/attendance/history");
                return;
            }
            try {
                recordEvent(username, action, note, req, resp);
            } catch (Exception e) {
                throw new ServletException(e);
            }
        } else {
            resp.sendError(404, "Unknown attendance action");
        }
    }

    private void recordEvent(String username, String type, String note,
                             HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        // server-side duplicate prevention:
        Attendance last = dao.getLatestEvent(username, getServletContext());
        if (last != null && type.equalsIgnoreCase(last.getEventType())) {
            // already clocked same type; set a session flash message and redirect
            req.getSession().setAttribute("attMsg", "Already recorded " + type + ".");
            resp.sendRedirect(req.getContextPath() + "/attendance/history");
            return;
        }

        String timestamp = LocalDateTime.now().format(FORMATTER);
        Attendance a = new Attendance(username, type, timestamp, note);

        dao.insertAttendance(a, getServletContext());

        // set a small flash message to display once
        req.getSession().setAttribute("attMsg", "Recorded " + type + " at " + timestamp);
        resp.sendRedirect(req.getContextPath() + "/attendance/history");
    }

    private void listHistory(String username,
                             HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        List<Attendance> history = dao.getUserEvents(username, getServletContext());
        Attendance last = dao.getLatestEvent(username, getServletContext());

        req.setAttribute("history", history);
        req.setAttribute("lastEvent", last);

        // pop the flash message from session into request (so it shows once)
        HttpSession session = req.getSession(false);
        if (session != null) {
            Object msg = session.getAttribute("attMsg");
            if (msg != null) {
                req.setAttribute("attMsg", msg.toString());
                session.removeAttribute("attMsg");
            }
        }

        req.getRequestDispatcher("/attendance_history.jsp").forward(req, resp);
    }
}
