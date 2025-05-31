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
import java.util.ArrayList;
import model.Supplier;

/**
 *
 * @author Hung
 */
public class addProduct extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
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
            out.println("<title>Servlet addProduct</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet addProduct at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        //processRequest(request, response);
        SupplierDAO sDAO = new SupplierDAO();
        HttpSession session = request.getSession();
        String DBname = (String) session.getAttribute("storeName");
        ArrayList<Supplier> listSupplier = sDAO.getAllSupplier("SalesManagement");
        request.setAttribute("listSupplier", listSupplier);
        request.getRequestDispatcher("addProduct.jsp").forward(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
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
        String warehouse = (String) session.getAttribute("warehouseId");
        int warehouseId = Integer.parseInt(warehouse);
        String name = request.getParameter("name");
        BigDecimal price = new BigDecimal(request.getParameter("price"));
        String image = request.getParameter("image");
        String unit = request.getParameter("unit");
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        ProductDAO pDAO = new ProductDAO();
        int id = pDAO.addProduct(name, image, "SalesManagement", price, unit);
        pDAO.addProductToInventory("SalesManagement", id, warehouseId, quantity);
        response.sendRedirect("product");
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
