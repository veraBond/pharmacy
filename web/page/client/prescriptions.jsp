<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF8" pageEncoding="UTF-8"%>
<%request.setCharacterEncoding("UTF-8");%>
<html>
<head>
    <meta charset="UTF-8">
    <fmt:setBundle basename="language.locale"></fmt:setBundle>
    <link rel="stylesheet" href="/styles.css">
    <title><fmt:message key="client.Prescriptions"></fmt:message></title>
</head>
<body>
<h3 align="right">${userName}, ${position}</h3><br>
<form method="get" action="/pharmacy">
    <div>
        <button name="command" value="client-medicine-list" type="submit">
            <fmt:message key="client.AvailableMedicines"></fmt:message></button>
        <button name="command" value="client-prescription-list" type="submit">
            <fmt:message key="client.WrittenPrescriptions"></fmt:message></button>
    </div>
</form>
<c:if test="${clientPrescriptionList != null}">
<table>
    <caption><fmt:message key="client.WrittenPrescriptions"></fmt:message></caption>
    <tr>
        <th><fmt:message key="medicine.Name"></fmt:message></th>
        <th><fmt:message key="medicine.Dosage"></fmt:message></th>
        <th><fmt:message key="medicine.PackageType"></fmt:message></th>
        <th><fmt:message key="medicine.Amount"></fmt:message></th>
        <th><fmt:message key="medicine.AvailableQuantity"></fmt:message></th>
        <th><fmt:message key="prescription.Status"></fmt:message></th>
        <th><fmt:message key="prescription.DoctorEmail"></fmt:message></th>
        <th><fmt:message key="prescription.ExtensionRequest"></fmt:message></th>
    </tr>
    <c:forEach var="prescription" items="${clientPrescriptionList}">
        <tr>
            <td><c:out value="${prescription.getValue().name}"></c:out></td>
            <td><c:out value="${prescription.getValue().dosage}"></c:out></td>
            <td><c:out value="${prescription.getValue().packageType}"></c:out></td>
            <td><c:out value="${prescription.getValue().packageAmount}"></c:out></td>
            <td><c:out value="${prescription.getKey().availableMedicineAmount}"></c:out></td>
            <td><c:out value="${prescription.getKey().status}"></c:out></td>
            <td><c:out value="${prescription.getKey().doctorMail}"></c:out></td>
            <td><c:when test="${prescription.getKey().isRequestedForExtension}">
                <fmt:message key="prescription.Requested"></fmt:message></c:when>
                <c:otherwise>
                    <form method="post" action="/pharmacy">
                        <input type="hidden" name="prescriptionId" value="prescription.getKey().prescriptionId">
                        <button name="command" value="request-prescription-for-extension" type="submit">
                            <fmt:message key="prescription.Request"></fmt:message>
                        </button>
                    </form>
                </c:otherwise>
            </td>
        </tr>
    </c:forEach>
</table>
</c:if>
<h2>${message}</h2>
<form method="post" action="/pharmacy">
    <div>
        <button name="command" value="log-out" type="submit">
            <fmt:message key="logOut"></fmt:message></button>
    </div>
</form>
</body>
</html>