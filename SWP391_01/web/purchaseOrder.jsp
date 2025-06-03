<%-- 
    Document   : purchaseOrder
    Created on : 1 thg 6, 2025, 22:50:46
    Author     : Hung
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Purchase Order Page</title>
        <script src="js/javascript.js"></script>
        <link rel="stylesheet" href="css/purchaseOrder.css">
    </head>
    <body>
        <div>
            <form action="addProduct">
                <input type="submit" value="New product"/>
            </form>
            <form action="searchProduct" method="get" class="search-form">
                <input type="text" name="search" value="${requestScope.info}"/>
                <input type="submit" value="search"/>
            </form>
        </div>

        <div>
            <c:if test="${requestScope.searchedProductList.size() > 0}">
                <c:forEach var="product" items="${requestScope.searchedProductList}">
                    <form action="purchaseOrder">
                        <input type="hidden" name="id" value="${product.productId}"/>
                        <input type="hidden" name="name" value="${product.productName}"/>
                        <input type="hidden" name="price" value="${product.price}"/>
                        <input type="hidden" name="image" value="${product.image}"/>
                        <input type="hidden" name="unit" value="${product.unit}"/>

                        <button type="submit">
                            <div class="product-container">
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
                                </div>
                            </div>
                        </button>
                    </form>
                </c:forEach>
            </c:if>

            <c:if test="${requestScope.check == 'not empty'}">
                <form action="purchaseOrderTemp" method="post" class="add-form">
                    <label>Name of product: <input type="text" name="name" value="${requestScope.name}" readonly/></label><br/>
                    <label>Unit: <input type="text" name="unit" value="${requestScope.unit}" readonly/></label><br/>
                    <label>Quantity: <input type="number" name="quantity"/></label><br/>
                    <label>Selling Price: <input type="number" step="0.01" name="sellingPrice" value="${requestScope.price}"/></label><br/>
                    <label>Purchase Price: <input type="number" step="0.01" name="purchasePrice" /></label><br/>
                    <input type="hidden" name="warehouseId" value="${sessionScope.warehouseId}"/>
                    <input type="hidden" name="productId" value="${requestScope.id}"/>
                    <label>Supplier:
                        <select name="supplierId" class="form-select">
                            <option value="">-- Other --</option>
                            <c:if test="${not empty requestScope.listSupplier}">
                                <c:forEach var="s" items="${requestScope.listSupplier}">
                                    <option value="${s.supplierId}">${s.supplierName}</option>
                                </c:forEach>
                            </c:if>
                        </select>
                    </label><br/>

                    <label>Image link:
                        <input type="text" name="image" value="${requestScope.image}"readonly/>
                    </label><br/>

                    <input type="submit" value="Confirm" class="submit-btn" />
                </form>
            </c:if>
        </div>

        <div>
            <c:if test="${sessionScope.listPurchaseOrder.size() > 0}">
                <form action="purchaseOrder" method="get">
                    <table>
                        <tr>
                            <th>Name</th>
                            <th>Quantity</th>
                            <th>Purchase Price</th>
                            <th>Selling Price</th>
                            <th>Supplier</th>
                            <th>Action</th>
                        </tr>
                        <c:forEach var="product" items="${sessionScope.listPurchaseOrder}">
                            <tr>                                                               
                                <td>${productMap[product.productId].productName}</td>
                                <td>${product.quantity}</td>
                                <td>${product.purchasePrice}</td>
                                <td>${product.sellingPrice}</td>
                                <td>${supplierMap[product.supplierId].supplierName}</td>
                                <td><button type="button" onclick="doDeletePurchaseOrderTmp('${product.productId}')">Delete</button></td>                      
                            </tr>
                            <input type="hidden" name="productId"value="${product.productId}"/>
                        </c:forEach>
                    </table>
                    <button type="submit">Save</button>
                </form>
            </c:if>
        </div>

    </body>
</html>
