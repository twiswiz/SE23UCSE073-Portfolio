<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>
<div style="padding:10px; border-bottom:1px solid #ddd; margin-bottom:16px;">
  <span style="font-weight:bold">EmployeePortal</span>
  &nbsp;|&nbsp;
  <span>Hi, <c:out value="${sessionScope.username}" default="Guest"/></span>
  &nbsp;(<c:out value="${sessionScope.role}" default="-" />)

  <span style="float:right">
    <c:choose>
      <c:when test="${sessionScope.role == 'hr'}">
        <a href="${pageContext.request.contextPath}/hr/dashboard">HR Dashboard</a> |
      </c:when>
      <c:when test="${sessionScope.role == 'manager'}">
        <a href="${pageContext.request.contextPath}/manager/dashboard">Manager Dashboard</a> |
      </c:when>
      <c:when test="${sessionScope.role == 'employee'}">
        <a href="${pageContext.request.contextPath}/employee/dashboard">My Dashboard</a> |
      </c:when>
      <c:otherwise>
        <a href="${pageContext.request.contextPath}/employee/dashboard">Dashboard</a> |
      </c:otherwise>
    </c:choose>

    <a href="${pageContext.request.contextPath}/logout">Logout</a>
  </span>
</div>
