<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Prescriptions</title>
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
        <input type="hidden" name="command" value="doctor-prescription-list">
        <div>
            <button type="submit">Prescriptions</button>
        </div>
    </form>
    <table>
        <caption>Prescriptions</caption>
        <tr>
            <th>Medicine Name</th>
            <th>Dosage</th>
            <th>Package type</th>
            <th>Package Amount</th>
            <th>Available Quantity</th>
            <th>Status</th>
            <th>Request for Extension</th>
            <th>Client e-mail</th>
            <th>Modify</th>
            <th>Delete</th>
        </tr>

        <c:forEach var="prescription" items="${prescriptionList}">
            <tr>
                <td><c:out value="${prescription.getValue().name}"></c:out></td>
                <td><c:out value="${prescription.getValue().dosage}"></c:out></td>
                <td><c:out value="${prescription.getValue().packageType}"></c:out></td>
                <td><c:out value="${prescription.getValue().packageAmount}"></c:out></td>
                <td><input type="number" min="1" max="${medicines.storageAmount > 5 ? 5 : medicines.storageAmount}"
                           step="1" name="addedMedicineAmount" value="0" pattern="[01234]">${prescription.getKey().availableMedicineAmount}
                </td>
                <td><c:out value="${prescription.getKey().status}"></c:out></td>
                <td><c:out value="${prescription.getKey().isRequestedForExtension ? 'requested' : 'no'}"></c:out>
                    <input type="radio" name="prolong" value="prolong">prolong<br>
                    <input type="radio" name="reject" value="reject">reject<br>
                </td>
                <td><c:out value="${prescription.getKey().clientMail}"></c:out></td>
                <td><form method="post" action="/pharmacy">
                        <input type="hidden" name="command" value="modify-prescription">
                        <input type="hidden" name="modified-prescription" value="prescription.getKey().prescriptionNumber">
                        <button type="submit">modify</button>
                    </form>
                </td>
                <td><form method="post" action="/pharmacy">
                        <input type="hidden" name="command" value="delete-prescription">
                        <input type="hidden" name="deleted-prescription" value="prescription.getKey().prescriptionNumber">
                        <button type="submit">delete</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
    <form method="post" action="/pharmacy">
        <input type="hidden" name="command" value="log-out">
        <div>
            <button type="submit">Log out</button>
        </div>
    </form>
</body>
</html>