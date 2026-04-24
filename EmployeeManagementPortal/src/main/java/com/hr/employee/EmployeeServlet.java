package com.hr.employee;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/employee/*")
public class EmployeeServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private EmployeeDAO dao = new EmployeeDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getPathInfo();

        try {
            if (action == null || "/".equals(action)) {
                listEmployees(req, resp);
                return;
            }

            switch (action) {
                case "/add":
                    showAddForm(req, resp);
                    break;

                case "/edit":
                    showEditForm(req, resp);
                    break;

                case "/delete":
                    deleteEmployee(req, resp);
                    break;

                default:
                    listEmployees(req, resp);
                    break;
            }

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getPathInfo();

        try {
            if (action == null) {
                listEmployees(req, resp);
                return;
            }

            switch (action) {
                case "/insert":
                    insertEmployee(req, resp);
                    break;

                case "/update":
                    updateEmployee(req, resp);
                    break;

                default:
                    listEmployees(req, resp);
                    break;
            }

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    // -------------------------
    //    HANDLER METHODS
    // -------------------------

    private void listEmployees(HttpServletRequest req, HttpServletResponse resp)
            throws Exception {

        List<Employee> list = dao.getAllEmployees(getServletContext());
        // set attribute name to "employees" so JSP and servlet agree
        req.setAttribute("employees", list);

        req.getRequestDispatcher("/employees.jsp").forward(req, resp);
    }

    private void showAddForm(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.getRequestDispatcher("/add_employee.jsp").forward(req, resp);
    }

    private void showEditForm(HttpServletRequest req, HttpServletResponse resp)
            throws Exception {

        int id = Integer.parseInt(req.getParameter("id"));
        Employee emp = dao.getEmployeeById(id, getServletContext());

        req.setAttribute("emp", emp);
        req.getRequestDispatcher("/edit_employee.jsp").forward(req, resp);
    }

    private void insertEmployee(HttpServletRequest req, HttpServletResponse resp)
            throws Exception {

        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String dept = req.getParameter("department");
        double salary = Double.parseDouble(req.getParameter("salary"));

        Employee emp = new Employee(name, email, dept, salary);
        dao.addEmployee(emp, getServletContext());

        resp.sendRedirect(req.getContextPath() + "/employee/");
    }

    private void updateEmployee(HttpServletRequest req, HttpServletResponse resp)
            throws Exception {

        int id = Integer.parseInt(req.getParameter("id"));
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String dept = req.getParameter("department");
        double salary = Double.parseDouble(req.getParameter("salary"));

        Employee emp = new Employee(id, name, email, dept, salary);
        dao.updateEmployee(emp, getServletContext());

        resp.sendRedirect(req.getContextPath() + "/employee/");
    }

    private void deleteEmployee(HttpServletRequest req, HttpServletResponse resp)
            throws Exception {

        int id = Integer.parseInt(req.getParameter("id"));
        dao.deleteEmployee(id, getServletContext());
        resp.sendRedirect(req.getContextPath() + "/employee/");
    }
}
