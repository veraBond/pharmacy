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

<div class="body-content">

    <header>
        <div class="header-logo">
            <span class="header-logo-helper"></span>
            <img src="/./logo.png" alt="Pharmacy" height="52" width="52">
        </div>
        <div class="header-info">
            <h3>${userName}, ${position}</h3>
        </div>
    </header>

    <section>

        <nav>
            <ul>
                <li>
                    <form method="get" action="/pharmacy">
                        <button name="command" value="doctor-medicine-list" type="submit">
                            <fmt:message key="AvailableMedicines"></fmt:message></button>
                    </form>
                </li>
                <li>
                    <form method="get" action="/pharmacy">
                        <button name="command" value="doctor-prescription-list" type="submit">
                            <fmt:message key="WrittenPrescriptions"></fmt:message></button>
                    </form>
                </li>
                <li>
                    <form method="post" action="/pharmacy">
                        <button name="command" value="log-out" type="submit">
                            <fmt:message key="logOut"></fmt:message></button>
                    </form>
                </li>
            </ul>
        </nav>

        <div class="content">
            <div class="content-title">
                <fmt:message key="AvailableMedicines"></fmt:message>
            </div>

            <c:choose>
                <c:when test="${!doctorMedicineList.isEmpty()}">
                    <table>
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
                <c:otherwise><div class="content-message"><fmt:message key="noAvailableMedicines"></fmt:message></div></c:otherwise>
            </c:choose>
        </div>
    </section>
</div>

<footer>
    <p>verabond © 2019</p>
</footer>

</body>
</html>

