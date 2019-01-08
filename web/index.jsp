<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Pharmacy</title>
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

  <h1>Welcome to On-line Pharmacy!</h1>
    <div>
      <button onclick="location.href='/page/login.jsp'">Log in</button>
      <button onclick="location.href='/page/registration.jsp'">Register</button>
    </div>
    <form method="get" action="/pharmacy">
      <input type="hidden" name="command" value="all-client-medicine-list">
  <table>
    <caption>Available medicines</caption>
    <tr>
      <th>Name</th>
      <th>Dosage</th>
      <th>Group</th>
      <th>Package type</th>
      <th>Amount</th>
      <th>Price</th>
    </tr>

    <c:forEach var="medicines" items="${medicineList}">
      <tr>
        <td><c:out value="${medicines.name}"></c:out></td>
        <td><c:out value="${medicines.dosage}"></c:out></td>
        <td>
          <c:forEach var="group" items="${medicines.groupType}">
            <c:out value="${group}"></c:out>
          </c:forEach>
        </td>
        <td><c:out value="${medicines.packageType}"></c:out></td>
        <td><c:out value="${medicines.packageAmount}"></c:out></td>
        <td><c:out value="${medicines.price}"></c:out></td>
      </tr>
    </c:forEach>
  </table>
  </form>
  </body>
</html>
