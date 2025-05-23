/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author AN
 */
@WebServlet(urlPatterns = {"/login-step2", "/login", "/signup"})
public class AuthServlet extends HttpServlet {
   
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
            out.println("<title>Servlet AuthServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AuthServlet at " + request.getContextPath () + "</h1>");
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
        processRequest(request, response);
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
        String path = request.getServletPath();

        if ("/login-step1".equals(path)) {
            String storeName = request.getParameter("storeName");
            // TODO: Kiểm tra Tên cửa hàng có tồn tại trong cơ sở dữ liệu
            if (storeName != null && !storeName.isEmpty()) {            
                HttpSession session = request.getSession();
                session.setAttribute("storeName", storeName);
                response.sendRedirect("login.jsp?step=2");
            } else {
                response.sendRedirect("index.html?error=invalid_store");
            }
        } else if ("/login".equals(path)) {
            HttpSession session = request.getSession();
            String storeName = (String) session.getAttribute("storeName");
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            
            // TODO: Thêm logic đăng nhập (kiểm tra Tài khoản và Mật khẩu với Tên cửa hàng)
            if (storeName != null && username != null && password != null) {
                // Mô phỏng đăng nhập thành công
                session.setAttribute("loggedIn", true);
                response.sendRedirect("dashboard.jsp"); // Chuyển đến trang quản lý giả định
            } else {
                response.sendRedirect("login.jsp?error=invalid_credentials");
            }
        } else if ("/signup".equals(path)) {
            String fullname = request.getParameter("fullname");
            String phone = request.getParameter("phone");
            String storeName = request.getParameter("storeName");
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            
            // TODO: Thêm logic đăng ký (lưu thông tin người dùng và cửa hàng)
            if (fullname != null && phone != null && storeName != null && username != null && password != null) {
                // Mô phỏng đăng ký thành công
                response.sendRedirect("login.jsp?signup=success");
            } else {
                response.sendRedirect("login.jsp?signup=failed");
            }
        }
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
