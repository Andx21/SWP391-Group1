<%-- 
    Document   : product
    Created on : 24 thg 5, 2025, 21:47:12
    Author     : Hung
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Product Page</title>
        <link rel="stylesheet" href="css/product.css">
    </head>
    <body>
        <c:if test="${requestScope.productList==null}">
            <%
                response.sendRedirect("product");
            %>
        </c:if>
        <div class="content">
            <center>
                <c:forEach var="product" items="${requestScope.productList}">
                    <div class="product-container">
                        <div class="product-image">
                            <img src="${product.image}" alt="Product Image"/>
                        </div>
                        <div class="product-info">
                            <table>
                                <tr>
                                    <td><strong>Name:</strong></td>
                                    <td>${product.productName}</td>
                                </tr>
                                <tr>
                                    <td><strong>Price:</strong></td>
                                    <td>${product.price}</td>
                                </tr>
                            </table>
                            <form action="checkAvailableCar" method="post">
                                <button type="submit" class="buy-button">Buy</button>
                            </form>
                        </div>
                    </div>
                </c:forEach>
            </center>
        </div>
    </body>
</html>
