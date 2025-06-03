/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.ProductDAO;
import dal.SupplierDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import model.PurchaseOrders;
import model.Supplier;

/**
 *
 * @author Hung
 */
public class PurchaseOrderTemp extends HttpServlet {

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
            out.println("<title>Servlet PurchaseOrderTemp</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet PurchaseOrderTemp at " + request.getContextPath() + "</h1>");
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
        System.out.println("post");
        HttpSession session = request.getSession();
        ArrayList<PurchaseOrders> list = (ArrayList<PurchaseOrders>) session.getAttribute("listPurchaseOrder");
        Iterator<PurchaseOrders> iterator = list.iterator();
        int productId = Integer.parseInt(request.getParameter("productId"));
//        while (iterator.hasNext()) {
//            PurchaseOrders po = iterator.next();
//            if (po.getProductId() == productId) {
//                iterator.remove();
//                break;
//            }
//        }
        for (int i = 0; i < list.size(); i++) {
            PurchaseOrders po = list.get(i);
            if (po.getProductId() == productId) {
                list.remove(i);
                break;
            }
        }

        session.setAttribute("listPurchaseOrder", list);
        response.sendRedirect("purchaseOrder.jsp");
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
        String DBname = (String) session.getAttribute("storeName");

        int productId = Integer.parseInt(request.getParameter("productId"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        BigDecimal purchasePrice = new BigDecimal(request.getParameter("purchasePrice"));
        BigDecimal sellingPrice = new BigDecimal(request.getParameter("sellingPrice"));
        int warehouseId = (int) session.getAttribute("warehouseId");

        String supplierID = request.getParameter("supplierId");
        int supplierId = 0;
        if (supplierID != null && !supplierID.trim().isEmpty()) {
            supplierId = Integer.parseInt(supplierID.trim());
        } else {
            supplierId = 0;
        }
        ArrayList<PurchaseOrders> list = (ArrayList<PurchaseOrders>) session.getAttribute("listPurchaseOrder");
        PurchaseOrders po = new PurchaseOrders(0, warehouseId, supplierId, productId, quantity, purchasePrice, sellingPrice, LocalDate.now());
        if (list == null) {
            list = new ArrayList<>();
            session.setAttribute("listPurchaseOrder", list);
        }
        list.add(po);
        session.setAttribute("listPurchaseOrder", list);

        ProductDAO pDAO = new ProductDAO();
        SupplierDAO sDAO = new SupplierDAO();
        ArrayList<model.Product> productList = pDAO.getAllProducts("SalesManagement");
        ArrayList<Supplier> supplierList = sDAO.getAllSupplier("SalesManagement");

        Map<Integer, model.Product> productMap = new HashMap<>();
        for (model.Product p : productList) {
            productMap.put(p.getProductId(), p);
        }

        Map<Integer, Supplier> supplierMap = new HashMap<>();
        for (Supplier s : supplierList) {
            supplierMap.put(s.getSupplierId(), s);
        }

        request.setAttribute("productMap", productMap);
        request.setAttribute("supplierMap", supplierMap);
        request.getRequestDispatcher("purchaseOrder.jsp").forward(request, response);
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
