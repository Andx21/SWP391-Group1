/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.ProductDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.ArrayList;
import model.Supplier;
import dal.SupplierDAO;
import dal.PurchaseOrderDAO;
import model.Product;

/**
 *
 * @author Hung
 */
public class PurchaseOrder extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet PurchaseOrder</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet PurchaseOrder at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);
        HttpSession session = request.getSession();
        String DBname = (String) session.getAttribute("storeName");
        String productId = (String) request.getParameter("id");
        String name = (String) request.getParameter("name");
        String price = (String) request.getParameter("price");
        String unit = (String) request.getParameter("unit");
        String image = (String) request.getParameter("image");
        SupplierDAO sDAO = new SupplierDAO();
        ArrayList<Supplier> listSupplier = sDAO.getAllSupplier("SalesManagement");

        request.setAttribute("check", "not empty");
        request.setAttribute("listSupplier", listSupplier);
        request.setAttribute("id", productId);
        request.setAttribute("name", name);
        request.setAttribute("price", price);
        request.setAttribute("unit", unit);
        request.setAttribute("image", image);
        request.getRequestDispatcher("purchaseOrder.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);
        HttpSession session = request.getSession();
        int warehouseId = (int) session.getAttribute("warehouseId");
        String[] productIdArray = request.getParameterValues("productId");
        String[] quantityArray = request.getParameterValues("quantity");
        String[] purchasePriceArray = request.getParameterValues("purchasePrice");
        String[] sellingPriceArray = request.getParameterValues("sellingPrice");
        String[] supplierIdArray = request.getParameterValues("supplierId");
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (int i = 0; i < purchasePriceArray.length; i++) {
            BigDecimal purchasePrice = new BigDecimal(purchasePriceArray[i]);
            int quantity = Integer.parseInt(quantityArray[i]);
            BigDecimal lineTotal = purchasePrice.multiply(BigDecimal.valueOf(quantity));
            totalAmount = totalAmount.add(lineTotal);
        }
        PurchaseOrderDAO poDAO = new PurchaseOrderDAO();
        int id = poDAO.addPurchaseOrder("SalesManagement", warehouseId, totalAmount);
        for (int i = 0; i < productIdArray.length; i++) {
            int productId = Integer.parseInt(productIdArray[i]);
            int quantity = Integer.parseInt(quantityArray[i]);
            BigDecimal purchasePrice = new BigDecimal(purchasePriceArray[i]);
            BigDecimal sellingPrice = new BigDecimal(sellingPriceArray[i]);
            int supplierId = Integer.parseInt(supplierIdArray[i]);
            poDAO.addPurchaseOrderDetail(id, productId, quantity, purchasePrice, sellingPrice, "SalesManagement", supplierId);
            ProductDAO pDAO = new ProductDAO();
            Product p = pDAO.getProductById("SalesManagement", productId, warehouseId);
            int newQuantity = quantity + p.getQuantity();
            pDAO.updateQuantity(newQuantity, "SalesManagement", warehouseId, productId);
            pDAO.updatePrice(productId, sellingPrice, "SalesManagement");
        }
        session.removeAttribute("listPurchaseOrder");
        response.sendRedirect("purchaseOrder.jsp");
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
