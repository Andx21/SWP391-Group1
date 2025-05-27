<%-- 
    Document   : editProduct
    Created on : 26 thg 5, 2025, 09:57:14
    Author     : Hung
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="css/editProduct.css">
    </head>
    <body>
        <form action="editProduct" method="post" class="edit-form">
            <input type="hidden" name="id" value="${requestScope.id}" />

            <label>Name of product:
                <input type="text" name="name" value="${requestScope.name}" />
            </label><br/>

            <label>Unit:
                <input type="text" name="unit" value="${requestScope.unit}" />
            </label><br/>
            
            <label>Quantity:
                <input type="number" name="quantity" value="${requestScope.quantity}" />
            </label><br/>

            <label>Price:
                <input type="number" step="0.01" name="price" value="${requestScope.price}" />
            </label><br/>

            <label>Image link:
                <input type="text" name="image" value="${requestScope.image}" />
            </label><br/>

            <input type="submit" value="Save" class="submit-btn" />
        </form>
    </body>

</html>
