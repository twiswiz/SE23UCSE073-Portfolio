<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.hr.employee.Employee" %>

<%
    Employee emp = (Employee) request.getAttribute("emp");
%>

<html>
<head>
    <title>Edit Employee</title>
</head>
<body>

<h2>Edit Employee</h2>

<form action="update" method="post">
    <input type="hidden" name="id" value="<%= emp.getId() %>">

    Name: <input type="text" name="name" value="<%= emp.getName() %>" required><br><br>
    Email: <input type="email" name="email" value="<%= emp.getEmail() %>" required><br><br>
    Department: <input type="text" name="department" value="<%= emp.getDepartment() %>"><br><br>
    Salary: <input type="number" name="salary" step="0.01" value="<%= emp.getSalary() %>"><br><br>

    <button type="submit">Update</button>
</form>

<br>
<a href="../employee/">Back to List</a>

</body>
</html>
