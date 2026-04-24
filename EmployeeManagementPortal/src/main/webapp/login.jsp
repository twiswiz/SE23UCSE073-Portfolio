<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>
<head><title>Login</title></head>
<body>
  <h2>Sign in</h2>
  <form action="${pageContext.request.contextPath}/login" method="post">
    <label>Username: <input type="text" name="username" required></label><br/>
    <label>Password: <input type="password" name="password" required></label><br/>
    <button type="submit">Log in</button>
  </form>
</body>
</html>
