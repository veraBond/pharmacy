<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/InformationTag" %>
<%@ page contentType="text/html; charset=UTF8" pageEncoding="UTF-8"%>
<%request.setCharacterEncoding("UTF-8");%>
<html>
<head>
    <meta charset="UTF-8">
    <fmt:setBundle basename="language.locale"></fmt:setBundle>
    <link rel="stylesheet" href="/css/styles.css">
    <title><fmt:message key="order"></fmt:message></title>
</head>

<body>

<div class="body-content">
    <header>
        <div class="header-logo">
            <span class="header-logo-helper"></span>
            <img src="/img/logo.png" alt="Pharmacy" height="52" width="52">
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
                    <fmt:message key="medicine.Name"></fmt:message><c:out value=" ${medicine.name}"></c:out><br>
                    <fmt:message key="medicine.Dosage"></fmt:message><c:out value=" ${medicine.dosage}"></c:out><br>
                    <fmt:message key="medicine.Group"></fmt:message><c:out value=" ${medicine.group}"></c:out><br>
                    <fmt:message key="medicine.PackageType"></fmt:message><c:out value=" ${medicine.packageType}"></c:out><br>
                    <fmt:message key="medicine.Amount"></fmt:message><c:out value=" ${medicine.packageAmount}"></c:out><br>
                    <fmt:message key="medicine.Price"></fmt:message><c:out value=" ${medicine.price}"></c:out><br>
                    <label><fmt:message key="EnterQuantity"></fmt:message><c:out value=" ${availableMedicineQuantity} "></c:out>
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
    <p>
        <ctg:projectInformation/>
    </p>
</footer>

</body>
</html>