<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF8" pageEncoding="UTF-8"%>
<%request.setCharacterEncoding("UTF-8");%>
<html>
<head>
    <meta charset="UTF-8">
    <fmt:setBundle basename="language.locale"></fmt:setBundle>
    <link rel="stylesheet" href="/styles.css">
    <title><fmt:message key="AvailableMedicines"></fmt:message></title>
</head>
<body>
<h3 align="right">${userName}, ${position}</h3>
<form method="post" action="/pharmacy">
    <div align="right">
        <button name="command" value="log-out" type="submit">
            <fmt:message key="logOut"></fmt:message></button>
    </div>
</form>
<form method="get" action="/pharmacy">
    <div>
        <button name="command" value="doctor-medicine-list" type="submit">
            <fmt:message key="AvailableMedicines"></fmt:message></button>
        <button name="command" value="prescription-list" type="submit">
            <fmt:message key="WrittenPrescriptions"></fmt:message></button>
    </div>
</form>
<c:choose>
    <c:when test="${doctorMedicineList != null}">
        <table>
            <caption><fmt:message key="AvailableMedicines"></fmt:message></caption>
            <tr>
                <th><fmt:message key="medicine.Name"></fmt:message></th>
                <th><fmt:message key="medicine.Dosage"></fmt:message></th>
                <th><fmt:message key="medicine.Group"></fmt:message></th>
                <th><fmt:message key="medicine.PackageType"></fmt:message></th>
                <th><fmt:message key="medicine.Amount"></fmt:message></th>
                <th><fmt:message key="medicine.Price"></fmt:message></th>
                <th><fmt:message key="writePrescription"></fmt:message></th>
            </tr>
            <c:forEach var="medicines" items="${doctorMedicineList}">
                <tr>
                    <td><c:out value="${medicines.name}"></c:out></td>
                    <td><c:out value="${medicines.dosage}"></c:out></td>
                    <td>
                        <c:forEach var="group" items="${medicines.medicineGroup}">
                            <c:out value="${group}"></c:out>
                        </c:forEach>
                    </td>
                    <td><c:out value="${medicines.packageType}"></c:out></td>
                    <td><c:out value="${medicines.packageAmount}"></c:out></td>
                    <td><c:out value="${medicines.price}"></c:out></td>
                    <form method="get" action="/pharmacy">
                        <td><input type="hidden" name="medicineId" value="${medicines.medicineId}">
                            <button name="command" value="write-prescription" type="submit">
                                <fmt:message key="writePrescription"></fmt:message></button>
                        </td>
                    </form>
                </tr>
            </c:forEach>
        </table>
    </c:when>
    <c:otherwise><h2><fmt:message key="noAvailableMedicines"></fmt:message></h2></c:otherwise>
</c:choose>
</body>
</html>