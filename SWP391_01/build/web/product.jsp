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
        <div>
            <form action="searchProduct" method="post">
                <input type="text" name="search" value="${requestScope.info}"/>
                <input type="submit" value="search"/>
            </form> 
        </div>
        <div class="content">
            <center>
                <c:choose>
                    <c:when test="${requestScope.searchedProductList!=null}">
                        <c:forEach var="product" items="${requestScope.searchedProductList}">
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
                                        <form action="editProduct.jsp" method="post">
                                            <button type="submit" class="buy-button">Edit</button>
                                        </form>
                                </div>
                            </div>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <c:if test="${requestScope.productList==null}">
                            <%
                                response.sendRedirect("product");
                            %>
                        </c:if>
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
                    </c:otherwise>
                </c:choose>
            </center>
        </div>
    </body>
</html>
