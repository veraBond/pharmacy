<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF8" pageEncoding="UTF-8"%>
<%request.setCharacterEncoding("UTF-8");%>
<html>
<head>
    <title>Registration</title>
</head>
<body>
    <h1>Registration</h1>

    <form method="post" action="/pharmacy">
        <input type="hidden" name="command" value="registration">
        <label>Choose position:
            <select name="position">
                <option value="client">client</option>
                <option value="doctor">doctor</option>
                <option value="pharmacist">pharmacist</option>
            </select>
        </label>
        <label>Enter your name:
            <input type="text" name="userName" value="${userName}" pattern="[\p{Alpha}]{2,}">
        </label>
        <label>Enter your e-mail:
            <input type="text" name="mail" value="${mail}" pattern="[\w]{2,}[@][\p{Alpha}]{2,}[.][\p{Alpha}]{2,}">
        </label>
        <label>Enter your password:
            <input type="text" name="password" pattern="[\w]{5,}">
        </label>
        <div>
            <button type="submit">register</button>
        </div>
    </form>
    <c:forEach var="error" items="${inputErrors}">
        <c:out value="${error}"></c:out>
    </c:forEach>
    <div>
        <button onclick="location.href='/'">Back to start page</button>
    </div>
</body>
</html>
