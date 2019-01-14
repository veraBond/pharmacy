<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Recipes</title>
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
        <input type="hidden" name="command" value="client-prescription-list">
        <div>
            <button type="submit">Written prescriptions</button>
        </div>
    </form>
    <table>
        <caption>Written prescriptions</caption>
        <tr>
            <th>Medicine Name</th>
            <th>Dosage</th>
            <th>Package type</th>
            <th>Package Amount</th>
            <th>Available Quantity</th>
            <th>Status</th>
            <th>Request for Extension</th>
            <th>Doctor e-mail</th>
        </tr>

        <c:forEach var="prescription" items="${clientPrescriptionList}">
            <tr>
                <td><c:out value="${prescription.getValue().name}"></c:out></td>
                <td><c:out value="${prescription.getValue().dosage}"></c:out></td>
                <td><c:out value="${prescription.getValue().packageType}"></c:out></td>
                <td><c:out value="${prescription.getValue().packageAmount}"></c:out></td>
                <td><c:out value="${prescription.getKey().availableMedicineAmount}"></c:out></td>
                <td><c:out value="${prescription.getKey().status}"></c:out></td>
                <td><c:out value="${prescription.getKey().isRequestedForExtension ? 'requested' : 'no'}"></c:out>
                    <form method="post" action="/pharmacy">
                        <input type="hidden" name="command" value="request-prescription-for-extension">
                        <input type="hidden" name="requested-prescription" value="prescription.getKey().prescriptionNumber">
                        <button type="submit">request</button>
                    </form>
                </td>
                <td><c:out value="${prescription.getKey().doctorMail}"></c:out></td>
            </tr>
        </c:forEach>
    </table>

    <h2>${message}</h2>
    <form method="post" action="/pharmacy">
        <input type="hidden" name="command" value="log-out">
        <div>
            <button type="submit">Log out</button>
        </div>
    </form>
</body>
</html>