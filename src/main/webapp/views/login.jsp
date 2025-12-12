<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Đăng Nhập - PhimHub</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    
    <style>
        body {
            /* Hình nền Netflix style */
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
            padding: 60px;
            border-radius: 10px;
            width: 100%;
            max-width: 450px;
            color: white;
            box-shadow: 0 0 20px rgba(0,0,0,0.5);
        }

        .form-control {
            background: #333;
            border: none;
            height: 50px;
            color: white;
            margin-bottom: 20px;
        }

        .form-control:focus {
            background: #444;
            color: white;
            box-shadow: none;
            border: 1px solid #555;
        }
        
        /* Label nổi (floating label) màu xám nhạt */
        .form-floating > label {
            color: #8c8c8c;
        }
        .form-floating > .form-control:focus ~ label,
        .form-floating > .form-control:not(:placeholder-shown) ~ label {
            color: #8c8c8c;
            opacity: 0.8;
        }

        .btn-login {
            background-color: #e50914;
            color: white;
            font-weight: bold;
            height: 50px;
            width: 100%;
            border: none;
            border-radius: 5px;
            margin-top: 20px;
            font-size: 1.1rem;
            transition: background 0.2s;
        }
        
        .btn-login:hover { 
            background-color: #f40612; 
        }

        .form-check-input {
            background-color: #737373;
            border-color: #737373;
        }
        .form-check-input:checked {
            background-color: #e50914;
            border-color: #e50914;
        }
        .form-check-input:focus {
            box-shadow: none;
        }

        .brand-logo {
            color: #e50914;
            font-size: 3rem;
            font-weight: 900;
            text-decoration: none;
            display: block;
            text-align: center;
            margin-bottom: 30px;
            letter-spacing: 2px;
            text-shadow: 2px 2px 4px rgba(0,0,0,0.5);
        }

        .hover-white:hover {
            color: white !important;
            text-decoration: underline !important;
        }

        /* --- XỬ LÝ AUTOFILL MÀU TRẮNG CỦA CHROME --- */
        input:-webkit-autofill,
        input:-webkit-autofill:hover, 
        input:-webkit-autofill:focus, 
        input:-webkit-autofill:active {
            -webkit-box-shadow: 0 0 0 30px #333 inset !important;
            -webkit-text-fill-color: white !important;
            transition: background-color 5000s ease-in-out 0s;
            caret-color: white; /* Màu con trỏ chuột */
        }
    </style>
</head>
<body>

    <div class="login-card">
        <a href="Home" class="brand-logo">PHIMHUB</a>
        
        <h2 class="fw-bold mb-4">Đăng Nhập</h2>

        <c:if test="${not empty message}">
            <div class="alert alert-danger d-flex align-items-center" role="alert">
                <i class="fas fa-exclamation-circle me-2"></i>
                <div>${message}</div>
            </div>
        </c:if>

        <form action="LoginServlet" method="post">
            
            <div class="form-floating mb-3">
                <input type="text" class="form-control" id="username" name="username" 
                       placeholder="Email hoặc Tên đăng nhập" value="${username}" required>
                <label for="username">Email hoặc Tên đăng nhập</label>
            </div>
            
            <div class="form-floating mb-3">
                <input type="password" class="form-control" id="password" name="password" 
                       placeholder="Mật khẩu" value="${password}" required>
                <label for="password">Mật khẩu</label>
            </div>

            <button type="submit" class="btn btn-login">Đăng Nhập</button>

            <div class="d-flex justify-content-between align-items-center mt-3 small text-secondary">
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" name="remember" id="rememberMe">
                    <label class="form-check-label text-white-50" for="rememberMe">Ghi nhớ tôi</label>
                </div>
                <a href="ForgotPasswordServlet" class="text-decoration-none text-white-50 hover-white">Quên mật khẩu?</a>
            </div>
        </form>

        <div class="mt-5 text-secondary">
            Bạn mới tham gia PhimHub? 
            <a href="RegisterServlet" class="text-white text-decoration-none fw-bold hover-white">Đăng ký ngay</a>.
        </div>
        
        <div class="mt-3 small text-muted">
            Trang này được bảo vệ bởi Google reCAPTCHA để đảm bảo bạn không phải là robot.
        </div>
    </div>

</body>
</html>