<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:include page="/WEB-INF/includes/header.jsp" />

<h2>All Employees</h2>

<c:if test="${not empty err}">
  <div style="color: red;">${err}</div>
</c:if>

<table border="1" cellpadding="6" style="border-collapse:collapse; width:100%;">
  <tr style="background:#f5f5f5;">
    <th>ID</th><th>Name</th><th>Email</th><th>Department</th><th>Salary</th><th>Actions</th>
  </tr>

  <c:choose>
    <c:when test="${not empty employees}">
      <c:forEach var="e" items="${employees}">
        <tr>
          <td><c:out value="${e.id}" /></td>
          <td><c:out value="${e.name}" /></td>
          <td><c:out value="${e.email}" /></td>
          <td><c:out value="${e.department}" /></td>
          <td><c:out value="${e.salary}" /></td>
          <td>
            <a href="${pageContext.request.contextPath}/employee/edit?id=${e.id}">Edit</a> |
            <a href="${pageContext.request.contextPath}/employee/delete?id=${e.id}" onclick="return confirm('Delete this employee?')">Delete</a>
          </td>
        </tr>
      </c:forEach>
    </c:when>

    <c:otherwise>
      <tr><td colspan="6">No employees found.</td></tr>
    </c:otherwise>
  </c:choose>
</table>

<br/>
<a href="${pageContext.request.contextPath}/employee/add">Add new employee</a>
