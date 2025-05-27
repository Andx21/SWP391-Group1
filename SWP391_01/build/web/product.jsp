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
        <div class="top-bar">
            <form action="addProduct">
                <button type="submit">ADD</button>
            </form>

            <form action="searchProduct" method="post" class="search-form">
                <input type="text" name="search" value="${requestScope.info}"/>
                <input type="submit" value="search"/>
            </form>
        </div>

        <div class="content">
            <center>
                <c:choose>
                    <c:when test="${requestScope.searchedProductList.size() > 0}">
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
                                        <tr>
                                            <td><strong>Quantity:</strong></td>
                                            <td>${product.quantity}</td>
                                        </tr>
                                    </table>
                                    <form action="checkAvailableCar" method="post">
                                        <button type="submit" class="buy-button">Buy</button>
                                    </form>
                                    <form action="editProduct" method="get">
                                        <input type="hidden" name="id" value="${product.productId}"/>
                                        <input type="hidden" name="name" value="${product.productName}"/>
                                        <input type="hidden" name="price" value="${product.price}"/>
                                        <input type="hidden" name="image" value="${product.image}"/>
                                        <input type="hidden" name="unit" value="${product.unit}"/>
                                        <input type="hidden" name="quantity" value="${product.quantity}"/>
                                        <button type="submit" class="buy-button">Edit</button>
                                    </form>
                                </div>
                            </div>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <c:if test="${requestScope.status == 'empty'}">
                            <h3>Not found!</h3>
                            <hr class="divider"/>
                            <h2>Another products</h2>

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
                                        <tr>
                                            <td><strong>Quantity:</strong></td>
                                            <td>${product.quantity}</td>
                                        </tr>
                                    </table>
                                    <form action="" method="post">
                                        <button type="submit" class="buy-button">Buy</button>
                                    </form>
                                    <form action="editProduct" method="get">
                                        <input type="hidden" name="id" value="${product.productId}"/>
                                        <input type="hidden" name="name" value="${product.productName}"/>
                                        <input type="hidden" name="price" value="${product.price}"/>
                                        <input type="hidden" name="image" value="${product.image}"/>
                                        <input type="hidden" name="unit" value="${product.unit}"/>
                                        <input type="hidden" name="quantity" value="${product.quantity}"/>
                                        <button type="submit" class="buy-button">Edit</button>
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
