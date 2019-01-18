<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF8" pageEncoding="UTF-8"%>
<%request.setCharacterEncoding("UTF-8");%>
<html>
<head>
    <meta charset="UTF-8">
    <fmt:setBundle basename="language.locale"></fmt:setBundle>
    <link rel="stylesheet" href="/styles.css">
    <title><fmt:message key="registration"></fmt:message></title>
</head>

<body>

<div class="body-content">
    <header>
        <div class="header-logo">
            <span class="header-logo-helper"></span>
            <img src="/./logo.png" alt="Pharmacy" height="52" width="52">
        </div>
    </header>

    <section>
        <div class="main">

            <h1><fmt:message key="registration"></fmt:message></h1>

            <form method="post" action="/pharmacy">
                <input type="hidden" name="command" value="registration">
                <div class="form-item">
                    <label><fmt:message key="registration.ChoosePosition"></fmt:message>
                        <select name="position">
                            <option value="client"><fmt:message key="position.Client"></fmt:message></option>
                            <option value="doctor"><fmt:message key="position.Doctor"></fmt:message></option>
                            <option value="pharmacist"><fmt:message key="position.Pharmacist"></fmt:message></option>
                        </select>
                    </label>
                </div>
                <div class="form-item">
                    <label><fmt:message key="registration.EnterName"></fmt:message>
                        <input type="text" name="userName" value="${userName}" pattern="[\p{Alpha}]{2,20}">
                    </label>
                </div>
                <div class="form-item">
                    <label><fmt:message key="logIn.EnterMail"></fmt:message>
                        <input type="email" name="mail" value="${mail}">
                    </label>
                </div>
                <div class="form-item">
                    <label><fmt:message key="logIn.EnterPassword"></fmt:message>
                        <input type="password" name="password" pattern="[\p{Graph}]{5,20}">
                    </label>
                </div>
                <div>
                    <button type="submit"><fmt:message key="registration.Register"></fmt:message></button>
                </div>
            </form>

            <c:forEach var="error" items="${inputErrors}">
                <c:out value="${error}"></c:out>
            </c:forEach>

            <div>
                <button onclick="location.href='/'"><fmt:message key="back"></fmt:message></button>
            </div>
        </div>
    </section>
</div>

<footer>
    <p>verabond © 2019</p>
</footer>

</body>
</html>
