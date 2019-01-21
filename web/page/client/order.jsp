<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF8" pageEncoding="UTF-8"%>
<%request.setCharacterEncoding("UTF-8");%>
<html>
<head>
    <meta charset="UTF-8">
    <fmt:setBundle basename="language.locale"></fmt:setBundle>
    <link rel="stylesheet" href="/styles.css">
    <title><fmt:message key="order"></fmt:message></title>
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
                        <button name="command" value="start-page" type="submit">
                            <fmt:message key="cancel"></fmt:message></button>
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
                <fmt:message key="orderMedicine"></fmt:message>
            </div>

            <div>
                <form method="post" action="/pharmacy">
                    <input type="hidden" name="command" value="complete-order">
                    <input type="hidden" name="medicineId" value="${medicine.medicineId}">
                    <input type="hidden" name="availableMedicineQuantity" value="${availableMedicineQuantity}">
                    <c:out value="${incorrectQuantity}"></c:out>
                    <fmt:message key="medicine.Name"></fmt:message> ${medicine.name} <br>
                    <fmt:message key="medicine.Dosage"></fmt:message> ${medicine.dosage}<br>
                    <fmt:message key="medicine.Group"></fmt:message> <c:forEach var="group" items="${medicine.medicineGroup}">
                    <c:out value="${group}"></c:out>
                </c:forEach><br>
                    <fmt:message key="medicine.PackageType"></fmt:message> ${medicine.packageType}<br>
                    <fmt:message key="medicine.Amount"></fmt:message> ${medicine.packageAmount}<br>
                    <fmt:message key="medicine.Price"></fmt:message> ${medicine.price}<br>
                    <label><fmt:message key="EnterQuantity"></fmt:message> ${availableMedicineQuantity}
                        <input type="number" name="bookMedicineQuantity" value="1" min="1" max="${availableMedicineQuantity}">
                    </label>
                    <div class="form-errors"></div>
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