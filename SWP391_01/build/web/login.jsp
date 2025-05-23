<%-- 
    Document   : login
    Created on : May 22, 2025, 8:09:23 AM
    Author     : AN
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đăng nhập - shop</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <link rel="stylesheet" href="css/styles.css">
</head>
<body class="bg-gray-100">
    <div class="flex items-center justify-center min-h-screen">
        <% 
            String mode = request.getParameter("mode");
            String step = request.getParameter("step");
            String storeName = (String) session.getAttribute("storeName");
            
            if ("signup".equals(mode)) {
        %>
        <!-- Form Đăng ký -->
        <div class="bg-white p-6 rounded-lg shadow-lg max-w-md w-full">
            <div class="flex justify-center mb-4">
                <h2 class="text-2xl font-bold ml-2 text-blue-600">SWP391-GRUOP1</h2>
            </div>
            <form action="/signup" method="POST" class="space-y-4">
                <input type="text" name="fullname" placeholder="Họ và tên" class="w-full p-3 border rounded-lg" required>
                <input type="tel" name="phone" placeholder="Số điện thoại" class="w-full p-3 border rounded-lg" required>
                <input type="text" name="storeName" placeholder="Tên cửa hàng" class="w-full p-3 border rounded-lg" required>
                <input type="text" name="username" placeholder="Tài khoản" class="w-full p-3 border rounded-lg" required>
                <input type="password" name="password" placeholder="Mật khẩu" class="w-full p-3 border rounded-lg" required>
                <button type="submit" class="bg-red-600 text-white px-6 py-3 rounded-lg font-semibold hover:bg-red-700 w-full">Đăng ký</button>
                <p class="text-center text-sm mt-2">Đã có tài khoản? <a href="index.html" class="text-blue-600 hover:underline">Đăng nhập</a></p>
            </form>
        </div>
        <% } else if ("2".equals(step) && storeName != null) { %>
        <!-- Form Đăng nhập: Tài khoản và Mật khẩu -->
        <div class="bg-white p-6 rounded-lg shadow-lg max-w-md w-full">
            <div class="flex justify-center mb-4">       
                <h2 class="text-2xl font-bold ml-2 text-blue-600">SWP391-GRUOP1</h2>
            </div>
            <form action="/login" method="POST" class="space-y-4">
                <input type="hidden" name="storeName" value="<%= storeName %>">
                <input type="text" name="username" placeholder="Tài khoản" class="w-full p-3 border rounded-lg" required>
                <input type="password" name="password" placeholder="Mật khẩu" class="w-full p-3 border rounded-lg" required>
                <div class="flex justify-between text-sm">
                    <span class="text-green-600">✔ Đã thử đăng nhập</span>
                    <a href="#" class="text-blue-600 hover:underline">Quên mật khẩu?</a>
                </div>
                <button type="submit" class="bg-blue-600 text-white w-full py-3 rounded-lg font-semibold hover:bg-blue-700">Đăng nhập</button>
            </form>
        </div>
        <% } else { %>
        <!-- Nếu không có Tên cửa hàng, chuyển về index.html -->
        <script>
            window.location.href = "index.html";
        </script>
        <% } %>
    </div>
</body>
</html>
