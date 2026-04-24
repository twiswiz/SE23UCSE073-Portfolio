<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*, com.hr.leave.LeaveRequest" %>
<%
    HttpSession s = request.getSession(false);
    if (s == null || s.getAttribute("username") == null) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }
    String role = (String) s.getAttribute("role");
%>
<html>
<head><title>Leave Requests</title></head>
<body>
<h2>Leave Requests</h2>

<p>
    <a href="${pageContext.request.contextPath}/leave/new">Request new leave</a>
    &nbsp;|&nbsp;
    <a href="${pageContext.request.contextPath}/employee/">Back to employees</a>
</p>

<% String msg = (String) session.getAttribute("leaveMsg");
   if (msg != null) { %>
    <div style="background:#e6ffed;padding:8px;margin-bottom:8px;"><%= msg %></div>
<% session.removeAttribute("leaveMsg"); } %>

<table border="1" cellpadding="6">
<tr>
    <th>Id</th><th>User</th><th>From</th><th>To</th><th>Type</th><th>Reason</th><th>Status</th><th>Requested At</th><th>Decision</th>
</tr>

<%
    List<LeaveRequest> list = (List<LeaveRequest>) request.getAttribute("requests");
    if (list != null && !list.isEmpty()) {
        for (LeaveRequest lr : list) {
%>
<tr>
    <td><%= lr.getId() %></td>
    <td><%= lr.getUsername() %></td>
    <td><%= lr.getStartDate() %></td>
    <td><%= lr.getEndDate() %></td>
    <td><%= lr.getType() %></td>
    <td><%= lr.getReason() %></td>
    <td><%= lr.getStatus() %></td>
    <td><%= lr.getRequestedAt() %></td>
    <td>
      <% if ("manager".equalsIgnoreCase(role) && "PENDING".equalsIgnoreCase(lr.getStatus())) { %>
        <form action="${pageContext.request.contextPath}/leave/decide" method="post" style="display:inline;">
            <input type="hidden" name="id" value="<%= lr.getId() %>"/>
            <button name="decision" value="APPROVED" type="submit">Approve</button>
        </form>
        <form action="${pageContext.request.contextPath}/leave/decide" method="post" style="display:inline;">
            <input type="hidden" name="id" value="<%= lr.getId() %>"/>
            <button name="decision" value="DENIED" type="submit">Deny</button>
        </form>
      <% } else { %>
        <%= lr.getDecisionBy() != null ? lr.getDecisionBy() + " @ " + lr.getDecisionAt() : "-" %>
      <% } %>
    </td>
</tr>
<%
        }
    } else {
%>
<tr><td colspan="9">No leave requests found.</td></tr>
<%
    }
%>
</table>
</body>
</html>
