<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF8" pageEncoding="UTF-8" %>
<%request.setCharacterEncoding("UTF-8");%>
<html>
<head>
    <meta charset="UTF-8">
    <fmt:setBundle basename="language.locale"></fmt:setBundle>
    <link rel="stylesheet" href="/styles.css">
    <title><fmt:message key="writePrescription"></fmt:message></title>
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
                        <input type="hidden" name="command" value="start-page">
                        <button type="submit"><fmt:message key="cancel"></fmt:message></button>
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
                <fmt:message key="writePrescription"></fmt:message>
            </div>

            <div>

                <form method="post" action="/pharmacy">
                    <input type="hidden" name="command" value="complete-write-prescription">
                    <input type="hidden" name="medicineId" value="${medicine.medicineId}">
                    <input type="hidden" name="availableMedicineQuantity" value="${availableMedicineQuantity}">
                    <fmt:message key="medicine.Name"></fmt:message> ${medicine.name} <br>
                    <fmt:message key="medicine.Dosage"></fmt:message> ${medicine.dosage}<br>
                    <fmt:message key="medicine.Group"></fmt:message>
                    <c:forEach var="group" items="${medicine.medicineGroup}">
                        <c:out value="${group}"></c:out>
                    </c:forEach><br>
                    <fmt:message key="medicine.PackageType"></fmt:message> ${medicine.packageType}<br>
                    <fmt:message key="medicine.Amount"></fmt:message> ${medicine.packageAmount}<br>
                    <fmt:message key="medicine.Price"></fmt:message> ${medicine.price}<br>
                    <fmt:message key="EnterQuantity"></fmt:message> ${availableMedicineQuantity}
                    <input type="number" name="prescriptionMedicineQuantity"
                           value="${prescriptionMedicineQuantity == null ? 1 : prescriptionMedicineQuantity}" min="1"
                           max="${availableMedicineQuantity}"><br>
                    <fmt:message key="prescription.clientEmail"></fmt:message>
                    <input type="email" name="clientMail" value="${clientMail}">
                    <div class="form-errors">
                        <span><c:if test="${incorrectQuantity}"><fmt:message
                                key="incorrectQuantity"></fmt:message></c:if></span>
                        <span><c:if test="${incorrectMail}"><fmt:message key="incorrectMail"></fmt:message></c:if></span>
                    </div>
                    <div class="submit-button">
                        <button type="submit"><fmt:message key="complete"></fmt:message></button>
                    </div>
                </form>
            </div>

        </div>
    </section>
</div>

<footer>
    <p>verabond © 2019</p>
</footer>

</body>
</html>