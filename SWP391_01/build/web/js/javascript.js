/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

function doDeletePurchaseOrderTmp(productId) {
    if (confirm("Are you sure you want to delete this item?")) {
        window.location.href = "purchaseOrderTemp?productId=" + productId;
    }
}
