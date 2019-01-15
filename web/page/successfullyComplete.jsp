<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF8" pageEncoding="UTF-8"%>
<%request.setCharacterEncoding("UTF-8");%>
<html>
<head>
    <meta charset="UTF-8">
    <fmt:setBundle basename="language.locale"></fmt:setBundle>
    <link rel="stylesheet" href="/styles.css">
    <title><fmt:message key="successfullyCompleted"></fmt:message></title>
</head>
<body>
<h3 align="right">${userName}, ${position}</h3>
<h2>${message}</h2>
<form method="get" action="/pharmacy">
    <input type="hidden" name="command" value="start-page">
    <input type="hidden" name="startPage" value="${startPage}">
    <button type="submit"><fmt:message key="back"></fmt:message></button>
</form>
</body>
</html>