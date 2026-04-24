<%@ page session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="/WEB-INF/includes/header.jsp" />

<html>
<body>
  <h2>Welcome</h2>
  <p>Hello, <c:out value="${sessionScope.username}" default="Guest"/>!</p>
  <p>Role: <c:out value="${sessionScope.role}" default="-" /></p>
</body>
</html>
