<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<head>
    <title>Add Employee</title>
</head>
<body>

<h2>Add Employee</h2>

<form action="${pageContext.request.contextPath}/employee/insert" method="post">
    Name: <input type="text" name="name" required><br><br>
    Email: <input type="email" name="email" required><br><br>
    Department: <input type="text" name="department"><br><br>
    Salary: <input type="number" name="salary" step="0.01"><br><br>

    <button type="submit">Add Employee</button>
</form>

<br>
<a href="${pageContext.request.contextPath}/employee/">Back to List</a>

</body>
</html>
