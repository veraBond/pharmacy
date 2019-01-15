<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF8" pageEncoding="UTF-8"%>
<%request.setCharacterEncoding("UTF-8");%>
<html>
<head>
    <meta charset="UTF-8">
    <fmt:setBundle basename="language.locale"></fmt:setBundle>
    <link rel="stylesheet" href="/styles.css">
    <title><fmt:message key="index.LogIn"></fmt:message></title>
</head>
<body>
<h1><fmt:message key="index.LogIn"></fmt:message></h1>
<form method="post" action="/pharmacy">
    <input type="hidden" name="command" value="log-in">
    <label><fmt:message key="logIn.EnterMail"></fmt:message>
        <input type="email" name="mail" pattern="[\w]{2,}[@][\p{Alpha}]{2,}[.][\p{Alpha}]{2,}">
    </label>
    <label><fmt:message key="logIn.EnterPassword"></fmt:message>
        <input type="password" name="password">
    </label>
    <div>
        <button type="submit"><fmt:message key="index.LogIn"></fmt:message></button>
    </div>
</form>
<c:out value="${inputErrors}"></c:out>
<div>
    <button onclick="location.href='/'"><fmt:message key="back"></fmt:message></button>
</div>
</body>
</html>
