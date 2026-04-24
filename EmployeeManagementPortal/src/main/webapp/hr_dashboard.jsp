<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>

<%@ page session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="/WEB-INF/includes/header.jsp" />

<h2>HR Dashboard</h2>

<c:if test="${not empty err}">
    <div style="color:red; font-weight:bold;">${err}</div>
</c:if>

<hr>

<h3>All Employees</h3>

<table border="1" cellpadding="6" style="border-collapse:collapse; width:100%;">
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Email</th>
        <th>Department</th>
        <th>Salary</th>
    </tr>

    <c:choose>
        <c:when test="${not empty employees}">
            <c:forEach var="e" items="${employees}">
                <tr>
                    <td><c:out value="${e.id}"/></td>
                    <td><c:out value="${e.name}"/></td>
                    <td><c:out value="${e.email}"/></td>
                    <td><c:out value="${e.department}"/></td>
                    <td><c:out value="${e.salary}"/></td>
                </tr>
            </c:forEach>
        </c:when>

        <c:otherwise>
            <tr><td colspan="5">No employees found.</td></tr>
        </c:otherwise>
    </c:choose>
</table>

<hr>

<h3>Pending Leave Requests</h3>

<table border="1" cellpadding="6" style="border-collapse:collapse; width:100%;">
    <tr>
        <th>User</th>
        <th>Dates</th>
        <th>Type</th>
        <th>Status</th>
    </tr>

    <c:choose>
        <c:when test="${not empty leaves}">
            <c:forEach var="lr" items="${leaves}">
                <tr>
                    <td><c:out value="${lr.username}"/></td>
                    <td><c:out value="${lr.startDate}"/> → <c:out value="${lr.endDate}"/></td>
                    <td><c:out value="${lr.type}"/></td>
                    <td><c:out value="${lr.status}"/></td>
                </tr>
            </c:forEach>
        </c:when>

        <c:otherwise>
            <tr><td colspan="4">No leave requests found.</td></tr>
        </c:otherwise>
    </c:choose>
</table>
