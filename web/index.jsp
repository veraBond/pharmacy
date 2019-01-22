<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/InformationTag" %>
<c:set var="language"
       value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
       scope="session"/>
<fmt:setLocale value="${language}" scope="session"/>
<fmt:bundle basename="language.locale">
  <html lang="${language}">
<head>
  <title><fmt:message key="pharmacy"></fmt:message></title>
  <link rel="stylesheet" href="/css/styles.css">
</head>

<body>
<div class="body-content">
  <header>
    <div class="header-logo">
      <span class="header-logo-helper"></span>
      <img src="/img/logo.png" alt="Pharmacy" height="52" width="52">
    </div>
    <div class="header-info">
      <form method="get" action="/pharmacy" class="header-info-actions">
        <input type="hidden" name="command" value="set-locale">
        <button name="language" value="ru_RU" type="submit">
          <fmt:message key="ru"></fmt:message>
        </button>
        <button name="language" value="en_US" type="submit">
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
          <fmt:message key="registration"></fmt:message>
        </button>
      </div>

    </div>
  </section>

</div>

<footer>
  <p>
    <ctg:projectInformation/>
  </p>
</footer>
</fmt:bundle>
</body>
</html>
