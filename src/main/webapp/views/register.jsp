<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Đăng Ký - PhimHub</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    
    <style>
        /* CSS đồng bộ với trang Login */
        body {
            background-image: linear-gradient(rgba(0,0,0,0.7), rgba(0,0,0,0.7)), url('https://assets.nflxext.com/ffe/siteui/vlv3/f841d4c7-10e1-40af-bcae-07a3f8dc141a/f6d7434e-d6de-4185-a6d4-c77a2d08737b/US-en-20220502-popsignuptwoweeks-perspective_alpha_website_large.jpg');
            background-size: cover;
            background-position: center;
            height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            font-family: 'Roboto', sans-serif;
            margin: 0;
        }
        .login-card {
            background-color: rgba(0, 0, 0, 0.75);
            padding: 40px 60px;
            border-radius: 10px;
            width: 100%;
            max-width: 450px;
            color: white;
            box-shadow: 0 0 20px rgba(0,0,0,0.5);
        }
        .form-control { background: #333; border: none; height: 50px; color: white; margin-bottom: 15px; }
        .form-control:focus { background: #444; color: white; box-shadow: none; border: 1px solid #555; }
        .form-floating > label { color: #8c8c8c; }
        .form-floating > .form-control:focus ~ label, .form-floating > .form-control:not(:placeholder-shown) ~ label { color: #8c8c8c; opacity: 0.8; }
        .btn-login { background-color: #e50914; color: white; font-weight: bold; height: 50px; width: 100%; border: none; border-radius: 5px; margin-top: 10px; font-size: 1.1rem; transition: background 0.2s; }
        .btn-login:hover { background-color: #f40612; }
        .brand-logo { color: #e50914; font-size: 3rem; font-weight: 900; text-decoration: none; display: block; text-align: center; margin-bottom: 20px; letter-spacing: 2px; }
        
        input:-webkit-autofill, input:-webkit-autofill:hover, input:-webkit-autofill:focus, input:-webkit-autofill:active {
            -webkit-box-shadow: 0 0 0 30px #333 inset !important;
            -webkit-text-fill-color: white !important;
            transition: background-color 5000s ease-in-out 0s;
            caret-color: white;
        }
    </style>
</head>
<body>

    <div class="login-card">
        <a href="HomeServlet" class="brand-logo">PHIMHUB</a>
        <h3 class="fw-bold mb-4">Đăng Ký Thành Viên</h3>

        <c:if test="${not empty message}">
            <div class="alert alert-warning small d-flex align-items-center" role="alert">
                <i class="fas fa-exclamation-triangle me-2"></i> <div>${message}</div>
            </div>
        </c:if>

        <form action="RegisterServlet" method="post">
            
            <div class="form-floating mb-2">
                <input type="email" class="form-control" id="email" name="email" 
                       placeholder="Email" value="${email}" required>
                <label for="email">Địa chỉ Email</label>
            </div>
            
            <div class="form-floating mb-2">
                <input type="text" class="form-control" id="fullName" name="fullname" 
                       placeholder="Họ và tên" value="${fullname}" required>
                <label for="fullName">Họ và tên</label>
            </div>
            
            <div class="form-floating mb-3">
                <input type="password" class="form-control" id="password" name="password" 
                       placeholder="Mật khẩu" required>
                <label for="password">Mật khẩu</label>
            </div>
            
            <div class="form-floating mb-3">
                <input type="password" class="form-control" id="re_password" name="re_password" 
                       placeholder="Nhập lại mật khẩu" required>
                <label for="re_password">Nhập lại mật khẩu</label>
            </div>

            <button type="submit" class="btn btn-login">Đăng Ký</button>
        </form>

        <div class="mt-4 text-secondary small">
            Đã có tài khoản? 
            <a href="LoginServlet" class="text-white text-decoration-none fw-bold">Đăng nhập ngay</a>.
        </div>
    </div>

</body>
</html>