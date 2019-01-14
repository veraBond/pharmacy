<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Order</title>
</head>
<body>
<h3 align="right">${userName}, ${position}</h3>
<form method="post" action="/pharmacy">
    <input type="hidden" name="command" value="complete-order">
    <input type="hidden" name="medicineId" value="${medicine.medicineId}">
    <input type="hidden" name="availableMedicineQuantity" value="${availableMedicineQuantity}">
    <h2>Order medicine</h2>
    <c:out value="${inputErrors}"></c:out>
    Name ${medicine.name} <br>
    Dosage ${medicine.dosage}<br>
    Group <c:forEach var="group" items="${medicine.medicineGroup}">
            <c:out value="${group}"></c:out>
        </c:forEach><br>
    Package type ${medicine.packageType}<br>
    Amount ${medicine.packageAmount}<br>
    Price ${medicine.price}<br>
    <label>Enter quantity (available ${availableMedicineQuantity}):
        <input type="number" name="bookMedicineQuantity" value="1" min="1" max="${availableMedicineQuantity}">
    </label>
    <div>
        <button type="submit">Complete</button>
    </div>
</form>
<form method="post" action="/pharmacy">
    <input type="hidden" name="command" value="cancel-order">
    <button type="submit">Cancel</button>
</form>
<form method="post" action="/pharmacy">
    <input type="hidden" name="command" value="log-out">
    <div>
        <button type="submit">Log out</button>
    </div>
</form>
</body>
</html>