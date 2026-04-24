<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*, com.hr.payroll.PayrollSummary" %>
<%
    javax.servlet.http.HttpSession sess = request.getSession(false);
    if (sess == null || sess.getAttribute("username") == null) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }
    String role = (String) sess.getAttribute("role");
    int year = (Integer) request.getAttribute("year");
    int month = (Integer) request.getAttribute("month");
%>

<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>Payroll - <%= month %>/<%= year %></title>
    <style>
        table{border-collapse:collapse;width:100%}
        th,td{border:1px solid #ccc;padding:6px;text-align:left}
        th{background:#f5f5f5}
        .small{font-size:0.9em;color:#444}
    </style>
</head>
<body>
<h2>Payroll Summary — <%= month %> / <%= year %></h2>

<form method="get" action="<%= request.getContextPath() %>/payroll">
    Month:
    <input type="number" name="month" value="<%= month %>" min="1" max="12" />
    Year:
    <input type="number" name="year" value="<%= year %>" min="2000" max="2100" />
    <button type="submit">Show</button>
</form>

<br/>

<% if ("manager".equalsIgnoreCase(role)) { %>

    <h3>All employees (with attendance this month)</h3>
    <table>
        <tr><th>User</th><th>Hours Worked</th><th>Monthly Salary</th><th>Payable Amount</th></tr>
        <%
            List<PayrollSummary> list = (List<PayrollSummary>) request.getAttribute("payrollList");
            if (list != null && !list.isEmpty()) {
                for (PayrollSummary p : list) {
        %>
        <tr>
            <td><%= p.getUsername() %></td>
            <td><%= String.format("%.2f", p.getHoursWorked()) %></td>
            <td><%= p.getMonthlySalary() %></td>
            <td><%= String.format("%.2f", p.getPayableAmount()) %></td>
        </tr>
        <%      }
            } else { %>
        <tr><td colspan="4">No payroll data for this month.</td></tr>
        <% } %>
    </table>

<% } else { %>

    <h3>Your payroll</h3>
    <%
        PayrollSummary p = (PayrollSummary) request.getAttribute("payroll");
        if (p != null) {
    %>
    <p class="small">Hours worked: <strong><%= String.format("%.2f", p.getHoursWorked()) %></strong></p>
    <p class="small">Monthly salary (recorded): <strong><%= p.getMonthlySalary() %></strong></p>
    <p class="small">Payable (prorated): <strong><%= String.format("%.2f", p.getPayableAmount()) %></strong></p>

    <table>
        <tr><th>User</th><th>Hours</th><th>Monthly Salary</th><th>Payable</th></tr>
        <tr>
            <td><%= p.getUsername() %></td>
            <td><%= String.format("%.2f", p.getHoursWorked()) %></td>
            <td><%= p.getMonthlySalary() %></td>
            <td><%= String.format("%.2f", p.getPayableAmount()) %></td>
        </tr>
    </table>

    <% } else { %>
        <p>No payroll data available.</p>
    <% } %>

<% } %>

</body>
</html>
