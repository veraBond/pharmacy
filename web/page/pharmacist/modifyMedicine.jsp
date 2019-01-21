<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF8" pageEncoding="UTF-8"%>
<%request.setCharacterEncoding("UTF-8");%>
<html>
<head>
    <meta charset="UTF-8">
    <fmt:setBundle basename="language.locale"></fmt:setBundle>
    <link rel="stylesheet" href="/styles.css">
    <title><fmt:message key="modifyMedicine"></fmt:message></title>
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
                <fmt:message key="modifyMedicine"></fmt:message>
            </div>

            <div>
                <form method="post" action="/pharmacy">
                    <input type="hidden" name="command" value="complete-modify-medicine">
                    <input type="hidden" name="medicineId" value="${medicineId}">
                    <input type="hidden" name="medicineGroupList" value="${medicineGroupList}">
                    <input type="hidden" name="packageTypeList" value="${packageTypeList}">
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
                            <select name="medicineGroup">
                                <option selected value="${medicineGroup}">${medicineGroup}</option>
                                <c:forEach var="group" items="${medicineGroupList}">
                                    <option value="${group}">${group}</option>
                                </c:forEach>
                            </select>
                        </label>
                    </div>
                    <div class="form-item">
                        <label><fmt:message key="medicine.PackageType"></fmt:message>
                            <select name="packageType">
                                <option selected value="${packageType}">${packageType}</option>
                                <c:forEach var="type" items="${packageTypeList}">
                                    <option value="${type}">${type}</option>
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
                            <input type="text" name="medicinePrice" value="${medicinePrice == null ? 0 : medicinePrice}"
                                   pattern="[\d]{1,3}[.]?[\d]{0,2}">
                        </label>
                    </div>
                    <div class="form-item">
                        <label><fmt:message key="medicine.PrescriptionNeed"></fmt:message>
                            <select name="prescriptionNeed">
                                <option selected value="${prescriptionNeed}">${prescriptionNeed}</option>
                                <option value="yes"><fmt:message key="need"></fmt:message></option>
                                <option value="no"><fmt:message key="no"></fmt:message></option>
                            </select>
                        </label>
                    </div>
                    <div class="form-item">
                        <label><fmt:message key="medicine.StorageAmount"></fmt:message>
                            <input type="number" name="storageAmount" value="${storageAmount == null ? 0 : storageAmount}"
                                   min="0" max="1000">
                        </label>
                    </div>
                    <div class="form-errors">
                        <span>
                            <c:if test="${incorrectMedicineName}">
                                <fmt:message key="incorrectMedicineName"></fmt:message><br>
                            </c:if>
                            <c:if test="${incorrectMedicineDosage}">
                                <fmt:message key="incorrectMedicineDosage"></fmt:message><br>
                            </c:if>
                            <c:if test="${incorrectMedicineGroup}">
                                <fmt:message key="incorrectMedicineGroup"></fmt:message><br>
                            </c:if>
                            <c:if test="${incorrectPackageType}">
                                <fmt:message key="incorrectPackageType"></fmt:message><br>
                            </c:if>
                            <c:if test="${incorrectPackageAmount}">
                                <fmt:message key="incorrectPackageAmount"></fmt:message><br>
                            </c:if>
                            <c:if test="${incorrectPrice}">
                                <fmt:message key="incorrectPrice"></fmt:message><br>
                            </c:if>
                            <c:if test="${incorrectStorageAmount}">
                                <fmt:message key="incorrectStorageAmount"></fmt:message><br>
                            </c:if>
                        </span>
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