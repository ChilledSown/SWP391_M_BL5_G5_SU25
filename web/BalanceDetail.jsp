<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
    <head>
        <title>Transaction Detail</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css"/>
    </head>
    <body class="container mt-5">
        <h3>Transaction Detail</h3>
        <c:if test="${not empty errorMessage}">
            <div class="error-message">${errorMessage}</div>
        </c:if>
        <c:choose>
            <c:when test="${not empty transaction and not empty buyer}">
                <table class="table table-bordered">
                    <tr>
                        <th>Order ID</th>
                        <td>${transaction.orderId}</td>
                    </tr>
                    <tr>
                        <th>Date</th>
                        <td><fmt:formatDate value="${transaction.orderDate}" pattern="yyyy-MM-dd" /></td>
                    </tr>
                    <tr>
                        <th>Description</th>
                        <td>${transaction.description}</td>
                    </tr>
                    <tr>
                        <th>Amount</th>
                        <td><fmt:formatNumber value="${transaction.amount}" type="currency" currencySymbol="$" maxFractionDigits="2" /></td>
                    </tr>
                    <tr>
                        <th>Method</th>
                        <td>${transaction.paymentMethod}</td>
                    </tr>
                    <tr>
                        <th>Buyer Name</th>
                        <td>
                            <c:out value="${buyer.firstName}" />
                            <c:if test="${not empty buyer.middleName}"> ${buyer.middleName}</c:if>
                            <c:if test="${not empty buyer.lastName}"> ${buyer.lastName}</c:if>
                            </td>
                        </tr>
                        <tr>
                            <th>Buyer Email</th>
                            <td>${buyer.email}</td>
                    </tr>
                    <tr>
                        <th>Buyer Address</th>
                        <td>${buyer.address}</td>
                    </tr>
                    <tr>
                        <th>Buyer Phone</th>
                        <td>${buyer.phone}</td>
                    </tr>
                </table>
            </c:when>
            <c:otherwise>
                <p>No transaction or buyer details available.</p>
            </c:otherwise>
        </c:choose>
        <a href="balance" class="btn btn-secondary">Back</a>
    </body>
</html>