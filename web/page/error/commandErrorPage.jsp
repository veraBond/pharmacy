<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Error</title>
</head>
<body>
<h3 align="right">${userName}, ${position}</h3>
<h1>Error.</h1>
<h2>${errorMessage}</h2>
<form method="get" action="/pharmacy">
    <input type="hidden" name="command" value="start-page">
    <input type="hidden" name="startPage" value="${startPage}">
    <button type="submit">Back to start page</button>
</form>
</body>
</html>