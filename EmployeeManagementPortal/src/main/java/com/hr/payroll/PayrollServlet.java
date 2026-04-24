package com.hr.payroll;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.LocalDate;

import java.util.List;

@WebServlet("/payroll")
public class PayrollServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private PayrollDAO dao = new PayrollDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession s = req.getSession(false);
        if (s == null || s.getAttribute("username") == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }
        String username = (String) s.getAttribute("username");
        String role = (String) s.getAttribute("role");

        // default = current month/year
        LocalDate now = LocalDate.now();
        int year = now.getYear();
        int month = now.getMonthValue();

        String yearParam = req.getParameter("year");
        String monthParam = req.getParameter("month");
        try {
            if (yearParam != null) year = Integer.parseInt(yearParam);
            if (monthParam != null) month = Integer.parseInt(monthParam);
        } catch (NumberFormatException nfe) {
            // ignore and fall back to default
        }

        if ("manager".equalsIgnoreCase(role)) {
            // manager sees payroll for all users with attendance this month
            List<PayrollSummary> list = dao.getMonthlyPayrollForAll(year, month, getServletContext());
            req.setAttribute("payrollList", list);
        } else {
            PayrollSummary ssum = dao.getMonthlyPayroll(username, year, month, getServletContext());
            req.setAttribute("payroll", ssum);
        }

        // pass month/year to JSP for display
        req.setAttribute("year", year);
        req.setAttribute("month", month);

        req.getRequestDispatcher("/payroll.jsp").forward(req, resp);
    }
}
