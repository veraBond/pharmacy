<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
  <fmt:setBundle basename="language.locale"></fmt:setBundle>
  <title><fmt:message key="index.Title"></fmt:message></title>
  <link rel="stylesheet" href="/styles.css">
</head>
<body>
<fmt:setLocale value="${locale == null ? 'ru_RU' : locale}" scope="session"></fmt:setLocale>
<div align="right">
  <form method="get" action="/pharmacy">
    <input type="hidden" name="command" value="set-locale">
    <button name="language" value="ru_RU" type="submit">
      <fmt:message key="ru"></fmt:message></button>
    <button name="language" value="en_EN" type="submit">
      <fmt:message key="en"></fmt:message></button>
  </form>
</div>
<h1><fmt:message key="index.Welcome"></fmt:message> </h1>
  <div>
    <button onclick="location.href='/page/login.jsp'">
      <fmt:message key="index.LogIn"></fmt:message></button>
    <button onclick="location.href='/page/registration.jsp'">
        <fmt:message key="index.Registration"></fmt:message></button>
    </button>
  </div>
</body>
</html>
