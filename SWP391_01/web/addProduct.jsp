<%-- 
    Document   : addProduct
    Created on : 26 thg 5, 2025, 16:07:09
    Author     : Hung
--%>

<%@page import="model.Supplier"%>
<%@page import="java.util.ArrayList"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add Product Page</title>
        <link rel="stylesheet" href="css/addProduct.css">
    </head>
    <body>
        <form action="addProduct" method="post" class="add-form">
            <label>Name of product: <input type="text" name="name" /></label><br/>
            <h4 style="color: red">${requestScope.error}</h4>
            <label>Unit: <input type="text" name="unit" /></label><br/>
            <label>Price: <input type="number" step="0.01" name="price" /></label><br/>
            <h4 style="color: red">${requestScope.error}</h4>
            <label>Image link:
                <input type="text" name="image" />
            </label><br/>

            <input type="submit" value="Add" class="submit-btn" />
        </form>
    </body>

</html>
