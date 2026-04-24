<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<h3>Pending Leave Requests</h3>

<c:if test="${empty leaves}">
    <p>No leave requests found.</p>
</c:if>

<c:forEach var="l" items="${leaves}">
    <div style="border:1px solid #ccc; padding:10px; margin:10px 0;">
        <strong>User:</strong> ${l.username} <br>
        <strong>Dates:</strong> ${l.startDate} → ${l.endDate} <br>
        <strong>Type:</strong> ${l.type} <br>
        <strong>Status:</strong> ${l.status} <br>

        <c:if test="${l.status == 'PENDING'}">
            <form action="${pageContext.request.contextPath}/leave/decide" method="post" style="display:inline;">
                <input type="hidden" name="id" value="${l.id}" />
                <button name="decision" value="APPROVED">Approve</button>
                <button name="decision" value="REJECTED">Reject</button>
            </form>
        </c:if>
    </div>
</c:forEach>
