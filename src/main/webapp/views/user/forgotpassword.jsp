<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Quên Mật Khẩu - PhimHub</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        /* Dùng lại CSS của trang login cho đồng bộ */
        body { background: #000; color: #fff; display: flex; align-items: center; justify-content: center; height: 100vh; }
        .card-custom { background: #1f1f1f; padding: 30px; border-radius: 10px; width: 100%; max-width: 400px; }
    </style>
</head>
<body>
    <div class="card-custom">
        <h3 class="text-center mb-4 text-danger">Quên Mật Khẩu?</h3>
        
        <c:if test="${not empty message}">
            <div class="alert alert-info">${message}</div>
        </c:if>

        <form action="ForgotPasswordServlet" method="post">
            <div class="mb-3">
                <label class="form-label">Nhập Email đăng ký của bạn:</label>
                <input type="email" name="email" class="form-control" required placeholder="email@example.com">
            </div>
            <button type="submit" class="btn btn-danger w-100">Gửi Mật Khẩu Mới</button>
        </form>
        
        <div class="text-center mt-3">
            <a href="LoginServlet" class="text-decoration-none text-light">Quay lại Đăng nhập</a>
        </div>
    </div>
</body>
</html>