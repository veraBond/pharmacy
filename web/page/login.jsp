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
    <label>Choose position:
        <select name="position">
            <option value="client">client</option>
            <option value="doctor">doctor</option>
            <option value="pharmacist">pharmacist</option>
        </select>
    </label>
    <label>Enter your e-mail:
        <input type="text" name="mail">
    </label>
    <label>Enter your password:
        <input type="text" name="password">
    </label>
    <div>
        <button type="submit">log in</button>
    </div>
</form>
<h2>${errorMessage}</h2>
<div>
    <button onclick="location.href='/'">Back to start page</button>
</div>
</body>
</html>
