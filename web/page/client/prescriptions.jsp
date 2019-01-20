<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF8" pageEncoding="UTF-8"%>
<%request.setCharacterEncoding("UTF-8");%>
<html>
<head>
    <meta charset="UTF-8">
    <fmt:setBundle basename="language.locale"></fmt:setBundle>
    <link rel="stylesheet" href="/styles.css">
    <title><fmt:message key="Prescriptions"></fmt:message></title>
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
                        <button name="command" value="client-medicine-list" type="submit">
                            <fmt:message key="AvailableMedicines"></fmt:message></button>
                    </form>
                </li>
                <li>
                    <form method="get" action="/pharmacy">
                        <button name="command" value="client-prescription-list" type="submit">
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
                <fmt:message key="WrittenPrescriptions"></fmt:message>
            </div>
            <c:choose>
                <c:when test="${!clientPrescriptionList.isEmpty()}">
                    <table>
                        <tr>
                            <th><fmt:message key="medicine.Name"></fmt:message></th>
                            <th><fmt:message key="medicine.Dosage"></fmt:message></th>
                            <th><fmt:message key="medicine.PackageType"></fmt:message></th>
                            <th><fmt:message key="medicine.Amount"></fmt:message></th>
                            <th><fmt:message key="medicine.AvailableQuantity"></fmt:message></th>
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
                                <td><c:out value="${prescription.getKey().doctorMail}"></c:out></td>
                                <td><c:choose>
                                    <c:when test="${prescription.getKey().requestedForExtension}">
                                        <fmt:message key="prescription.Requested"></fmt:message></c:when>
                                    <c:otherwise>
                                        <form method="post" action="/pharmacy">
                                            <input type="hidden" name="prescriptionId" value="${prescription.getKey().prescriptionId}">
                                            <button name="command" value="request-prescription-for-extension" type="submit">
                                                <fmt:message key="prescription.Request"></fmt:message>
                                            </button>
                                        </form>
                                    </c:otherwise>
                                </c:choose>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                </c:when>
                <c:otherwise><fmt:message key="noPrescriptions"></fmt:message></c:otherwise>
            </c:choose>
        </div>

    </section>
</div>

<footer>
    <p>verabond © 2019</p>
</footer>

</body>
</html>