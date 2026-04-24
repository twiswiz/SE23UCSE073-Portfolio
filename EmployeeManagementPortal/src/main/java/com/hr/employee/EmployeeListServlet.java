package com.hr.employee;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import java.io.IOException;
import java.util.List;

@WebServlet("/employee/list")
public class EmployeeListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private EmployeeDAO dao = new EmployeeDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        try {
            ServletContext ctx = getServletContext();
            List<Employee> employees = dao.getAllEmployees(ctx);
            req.setAttribute("employees", employees);
        } catch (Exception e) {
            // show a friendly message on page
            req.setAttribute("err", "Unable to load employees: " + e.getMessage());
            e.printStackTrace();
        }

        RequestDispatcher rd = req.getRequestDispatcher("/employees.jsp");
        rd.forward(req, resp);
    }
}
