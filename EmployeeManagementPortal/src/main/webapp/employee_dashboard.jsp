<%@ page session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="/WEB-INF/includes/header.jsp" />

<h2>Employee Dashboard</h2>

<p>Welcome, <c:out value="${sessionScope.username}" default="Guest"/>!</p>

<h3>Attendance</h3>

<!-- Flash message (from AttendanceServlet) -->
<c:if test="${not empty attMsg}">
    <div style="color: green; font-weight: bold;">
        ${attMsg}
    </div>
</c:if>

<!-- Clock IN / OUT quick actions -->
<form action="${pageContext.request.contextPath}/attendance/clock" method="post" style="display:inline;">
    <input type="hidden" name="action" value="IN">
    <button type="submit">Clock IN</button>
</form>

<form action="${pageContext.request.contextPath}/attendance/clock" method="post" style="display:inline;">
    <input type="hidden" name="action" value="OUT">
    <button type="submit">Clock OUT</button>
</form>

<br/><br/>
<a href="${pageContext.request.contextPath}/attendance/history">View Attendance History</a>

<hr/>

<h3>Your Leave Requests</h3>

<c:if test="${not empty leaveError}">
    <div style="color:red">${leaveError}</div>
</c:if>

<c:if test="${empty leaveRequests}">
    <div>No leave requests found. <a href="${pageContext.request.contextPath}/leave/form">Apply for leave</a></div>
</c:if>

<c:if test="${not empty leaveRequests}">
    <table style="border-collapse:collapse;width:100%;">
        <tr style="background:#f4f4f4;">
            <th style="border:1px solid #ddd;padding:6px">#</th>
            <th style="border:1px solid #ddd;padding:6px">Dates</th>
            <th style="border:1px solid #ddd;padding:6px">Type</th>
            <th style="border:1px solid #ddd;padding:6px">Status</th>
            <th style="border:1px solid #ddd;padding:6px">Requested At</th>
        </tr>
        <c:forEach var="l" items="${leaveRequests}" varStatus="st">
            <tr>
                <td style="border:1px solid #ddd;padding:6px">${st.index + 1}</td>
                <td style="border:1px solid #ddd;padding:6px">${l.startDate} - ${l.endDate}</td>
                <td style="border:1px solid #ddd;padding:6px">${l.type}</td>
                <td style="border:1px solid #ddd;padding:6px">${l.status}</td>
                <td style="border:1px solid #ddd;padding:6px">${l.requestedAt}</td>
            </tr>
        </c:forEach>
    </table>
</c:if>

<hr/>
<p>
  Quick links:
  <a href="${pageContext.request.contextPath}/employees">All Employees</a> |
  <a href="${pageContext.request.contextPath}/payroll">Payroll</a>
</p>
