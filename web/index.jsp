<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
  <fmt:setBundle basename="language.locale"></fmt:setBundle>
  <title><fmt:message key="pharmacy"></fmt:message></title>
  <link rel="stylesheet" href="/styles.css">
</head>

<body>
<div class="body-content">
  <header>
    <div class="header-logo">
      <span class="header-logo-helper"></span>
      <img src="/logo.png" alt="Pharmacy" height="52" width="52">
    </div>
    <div class="header-info">
      <fmt:setLocale value="${locale == null ? 'ru_RU' : locale}" scope="session"></fmt:setLocale>
      <form method="get" action="/pharmacy">
        <input type="hidden" name="command" value="set-locale">
        <button name="language" value="ru_RU" type="submit">
          <fmt:message key="ru"></fmt:message>
        </button>
        <button name="language" value="en_EN" type="submit">
          <fmt:message key="en"></fmt:message>
        </button>
      </form>
    </div>
  </header>


  <section>
    <div class="main">

      <h1><fmt:message key="index.Welcome"></fmt:message></h1>

      <div>
        <button onclick="location.href='/page/login.jsp'">
          <fmt:message key="logIn"></fmt:message>
        </button>
        <button onclick="location.href='/page/registration.jsp'">
          <fmt:message key="registration"></fmt:message></button>
        </button>
      </div>

    </div>
  </section>

</div>

<footer>
  <p>verabond © 2019</p>
</footer>

</body>
</html>
