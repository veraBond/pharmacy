<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/InformationTag" %>
<%@ page contentType="text/html; charset=UTF8" pageEncoding="UTF-8" %>
<%request.setCharacterEncoding("UTF-8");%>
<html>
<head>
    <meta charset="UTF-8">
    <fmt:setBundle basename="language.locale"></fmt:setBundle>
    <link rel="stylesheet" href="/css/styles.css">
    <title><fmt:message key="logIn"></fmt:message></title>
</head>

<body>
<div class="body-content">

    <header>
        <div class="header-logo">
            <span class="header-logo-helper"></span>
            <img src="/img/logo.png" alt="Pharmacy" height="52" width="52">
        </div>
    </header>

    <section>
        <div class="main">

            <h1><fmt:message key="logIn"></fmt:message></h1>

            <form method="post" action="/pharmacy">
                <input type="hidden" name="command" value="log-in">
                <div class="form-item">
                    <label><fmt:message key="logIn.EnterMail"></fmt:message>
                        <input type="email" name="mail" pattern="[\w]{2,}[@][\p{Alpha}]{2,}[.][\p{Alpha}]{2,}">
                    </label>
                </div>
                <div class="form-item">
                    <label><fmt:message key="logIn.EnterPassword"></fmt:message>
                        <input type="password" name="password">
                    </label>
                </div>
                <div>
                    <button type="submit"><fmt:message key="logIn"></fmt:message></button>
                </div>
            </form>

            <div class="form-errors">
                <span>
                    <c:if test="${logInError}">
                        <fmt:message key="logInError"></fmt:message>
                    </c:if>
                </span>
            </div>

            <div>
                <button onclick="location.href='/'"><fmt:message key="back"></fmt:message></button>
            </div>
        </div>
    </section>

</div>

<footer>
    <p>
        <ctg:projectInformation/>
    </p>
</footer>

</body>
</html>
