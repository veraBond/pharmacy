<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Write prescription</title>
</head>
<body>
    <style>
        table{
            margin: 50px 0;
            text-align: left;
            border-collapse: separate;
            border: 1px solid #ddd;
            border-spacing: 1px;
            border-radius: 3px;
            background: #fdfdfd;
            font-size: 12px;
            width: auto;
        }
        td,th{
            border: 1px solid #ddd;
            padding: 5px;
            border-radius: 3px;
        }
        th{
            background: #E4E4E4;
        }
        caption{
            font-style: italic;
            text-align: right;
            color: #547901;
        }
    </style>
    <h2 align="right">${userName}, ${position}</h2>
    <h2>${errorMessage}</h2>
    <form method="get" action="/pharmacy">
        <input type="hidden" name="command" value="doctor-medicine-list">
        <div>
            <button type="submit">Write prescription</button>
        </div>
    </form>
    <form method="get" action="/pharmacy">
        <input type="hidden" name="command" value="prescription-list">
        <div>
            <button type="submit">Prescriptions</button>
        </div>
    </form>
    <h3>${prescriptionStatus}</h3>
    <h3>${inputErrors}</h3>
    <form method="post" action="/pharmacy">
        <input type="hidden" name="command" value="write-prescription">
        Available medicines
        <input type="hidden" name="medicines" value="${medicineList}">
        <c:forEach var="medicines" items="${medicineList}">
            <c:out value="${medicines.name}"></c:out>
            <c:out value="${medicines.dosage}"></c:out>

            <c:out value="${medicines.packageType}"></c:out>
            <c:out value="${medicines.packageAmount}"></c:out><br>
            <input type="hidden" name="medicineNumber" value="${medicines.medicineNumber}">
            <div>
                <button type="submit">Write</button>
            </div>
        </c:forEach>
    </form>
    <form method="post" action="/pharmacy">
        <input type="hidden" name="command" value="log-out">
        <div>
            <button type="submit">Log out</button>
        </div>
    </form>
</body>
</html>