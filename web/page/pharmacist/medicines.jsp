<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Medicines</title>
</head>
<body>
    <h2 align="right">${userName}, ${position}</h2>



    <form method="post" action="/pharmacy">
        <input type="hidden" name="command" value="log-out">
        <div>
            <button type="submit">Log out</button>
        </div>
    </form>
</body>
</html>