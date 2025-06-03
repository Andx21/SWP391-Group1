/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.ProductDAO;
import dal.WarehouseDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;

/**
 *
 * @author Hung
 */
public class Product extends HttpServlet {

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
            out.println("<title>Servlet Product</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Product at " + request.getContextPath() + "</h1>");
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
        WarehouseDAO wDAO = new WarehouseDAO();
        ArrayList warehouseList = wDAO.getAllWarehouse("SalesManagement");
        session.setAttribute("warehouseList", warehouseList);
        if (session.getAttribute("warehouseId") != null) {
            int warehouseId = (int) session.getAttribute("warehouseId");
            ProductDAO pDAO = new ProductDAO();
            ArrayList productList = pDAO.getAllProductsOfOneWarehouse("SalesManagement", warehouseId);
            request.setAttribute("productList", productList);
            request.getRequestDispatcher("product.jsp").forward(request, response);
        }
        else{
            response.sendRedirect("product.jsp");
        }
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
        String id = request.getParameter("warehouseId");
        int warehouseId = Integer.parseInt(id);
        ProductDAO pDAO = new ProductDAO();
        ArrayList productList = pDAO.getAllProductsOfOneWarehouse("SalesManagement", warehouseId);
        WarehouseDAO wDAO = new WarehouseDAO();
        ArrayList warehouseList = wDAO.getAllWarehouse("SalesManagement");
        if (session.getAttribute("warehouseId") != null) {
            session.removeAttribute("warehouseId");
        }
        session.setAttribute("warehouseList", warehouseList);
        session.setAttribute("warehouseId", warehouseId);
        request.setAttribute("productList", productList);
        request.getRequestDispatcher("product.jsp").forward(request, response);
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
