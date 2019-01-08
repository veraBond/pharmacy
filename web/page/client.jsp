<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Client page</title>
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

    <form method="get" action="/pharmacy">
        <input type="hidden" name="command" value="client-medicine-list">
        <div>
            <button type="submit">Available medicines</button>
        </div>
    </form>
    <form method="get" action="/pharmacy">
        <input type="hidden" name="command" value="client-recipes">
        <div>
            <button type="submit">Written prescriptions</button>
        </div>
    </form>

    <table>
        <caption>Available medicines</caption>
        <tr>
            <th>Name</th>
            <th>Dosage</th>
            <th>Group</th>
            <th>Package type</th>
            <th>Amount</th>
            <th>Price</th>
        </tr>

        <c:forEach var="medicines" items="${medicineList}">
            <tr>
                <td><c:out value="${medicines.name}"></c:out></td>
                <td><c:out value="${medicines.dosage}"></c:out></td>
                <td>
                    <c:forEach var="group" items="${medicines.groupType}">
                        <c:out value="${group}"></c:out>
                    </c:forEach>
                </td>
                <td><c:out value="${medicines.packageType}"></c:out></td>
                <td><c:out value="${medicines.packageAmount}"></c:out></td>
                <td><c:out value="${medicines.price}"></c:out></td>
                <td><input type="hidden" name="addedMedicine" value="${medicines.medicineNumber}">
                    <input type="number" min="0" max="${medicines.storageAmount > 5 ? 5 : medicines.storageAmount}"
                           step="1" name="addedMedicineAmount" value="0">
                </td>
            </tr>
        </c:forEach>
    </table>
<button type="submit">Order</button>

    <div>
        <button onclick="location.href='/'">Log out</button>
    </div>
</body>
</html>