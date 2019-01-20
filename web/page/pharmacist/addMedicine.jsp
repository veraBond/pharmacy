<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF8" pageEncoding="UTF-8"%>
<%request.setCharacterEncoding("UTF-8");%>
<html>
<head>
    <meta charset="UTF-8">
    <fmt:setBundle basename="language.locale"></fmt:setBundle>
    <link rel="stylesheet" href="/styles.css">
    <title><fmt:message key="addMedicine"></fmt:message></title>
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
                <fmt:message key="addMedicine"></fmt:message>
            </div>

            <div>
                <form method="post" action="/pharmacy">
                    <input type="hidden" name="command" value="complete-add-medicine">
                    <div class="form-item">
                        <label><fmt:message key="medicine.Name"></fmt:message>
                            <input type="text" name="medicineName" value="${medicineName}" pattern="[\p{Alpha}]{2,20}">
                        </label>
                    </div>
                    <div class="form-item">
                        <label><fmt:message key="medicine.Dosage"></fmt:message>
                            <input type="number" name="medicineDosage" value="${medicineDosage == null ? 1 : medicineDosage}"
                                   min="1" max="1000">
                        </label>
                    </div>
                    <div class="form-item">
                        <label><fmt:message key="medicine.Group"></fmt:message>
                            <select name="position">
                                <c:forEach var="group" items="${medicineGroupList}">
                                    <option value="${group}"></option>
                                </c:forEach>
                            </select>
                        </label>
                    </div>
                    <div class="form-item">
                        <label><fmt:message key="medicine.PackageType"></fmt:message>
                            <select name="position">
                                <c:forEach var="type" items="${packageTypeList}">
                                    <option value="${type}"></option>
                                </c:forEach>
                            </select>
                        </label>
                    </div>
                    <div class="form-item">
                        <label><fmt:message key="medicine.Amount"></fmt:message>
                            <input type="number" name="packageAmount" value="${packageAmount == null ? 1 : packageAmount}"
                                   min="1" max="1000">
                        </label>
                    </div>
                    <div class="form-item">
                        <label><fmt:message key="medicine.Price"></fmt:message>
                            <input type="number" name="medicinePrice" value="${medicinePrice == null ? 0 : medicinePrice}"
                                   min="0" max="1000">
                        </label>
                    </div>
                    <div class="form-item">
                        <label><fmt:message key="medicine.PrescriptionNeed"></fmt:message>
                            <select name="prescriptionNeed">
                                <option value="yes"></option><fmt:message key="need"></fmt:message>
                                <option value="no"></option><fmt:message key="no"></fmt:message>
                            </select>
                        </label>
                    </div>
                    <div class="form-item">
                        <label><fmt:message key="medicine.StorageAmount"></fmt:message>
                            <input type="number" name="storageAmount" value="${storageAmount == null ? 0 : storageAmount}"
                                   min="0" max="1000">
                        </label>
                    </div>
                    <div>
                        <button type="submit"><fmt:message key="registration.Register"></fmt:message></button>
                    </div>
                </form>

                <c:forEach var="error" items="${inputErrors}">
                    <c:out value="${error}"></c:out>
                </c:forEach>
            </div>

        </div>
    </section>
</div>

<footer>
    <p>verabond © 2019</p>
</footer>

</body>
</html>