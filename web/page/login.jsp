<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF8" pageEncoding="UTF-8"%>
<%request.setCharacterEncoding("UTF-8");%>
<html>
<head>
    <title>Log in</title>
</head>
<body>
<h1>Log in</h1>
<form method="post" action="/pharmacy">
    <input type="hidden" name="command" value="log-in">
    <label>Enter your e-mail:
        <input type="email" name="mail" pattern="[\w]{2,}[@][\p{Alpha}]{2,}[.][\p{Alpha}]{2,}">
    </label>
    <label>Enter your password:
        <input type="password" name="password">
    </label>
    <div>
        <button type="submit">log in</button>
    </div>
</form>
<c:out value="${inputErrors}"></c:out>
<div>
    <button onclick="location.href='/'">Back to start page</button>
</div>
</body>
</html>
