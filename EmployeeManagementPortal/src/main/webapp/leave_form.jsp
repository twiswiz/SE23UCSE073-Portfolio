<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="javax.servlet.http.*,java.time.*" %>
<%
    HttpSession s = request.getSession(false);
    if (s == null || s.getAttribute("username") == null) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }
%>
<html>
<head><title>Request Leave</title></head>
<body>
<h2>Request Leave</h2>
<form action="${pageContext.request.contextPath}/leave/create" method="post">
    Start date (YYYY-MM-DD): <input type="date" name="start_date" required><br><br>
    End date (YYYY-MM-DD): <input type="date" name="end_date" required><br><br>
    Type:
    <select name="type">
        <option>Paid</option>
        <option>Casual</option>
        <option>Sick</option>
    </select><br><br>
    Reason:<br>
    <textarea name="reason" rows="4" cols="50"></textarea><br><br>

    <button type="submit">Submit Request</button>
</form>

<br>
<a href="${pageContext.request.contextPath}/leave/list">Back to requests</a>
</body>
</html>
