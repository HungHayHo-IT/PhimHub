<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>PhimHub - Xem phim chất lượng cao</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    
    <style>
        body { background-color: #141414; color: white; font-family: 'Roboto', sans-serif; }
        .navbar { background-color: #000 !important; }
        .brand-title { color: #e50914 !important; font-weight: 900; font-size: 1.5rem; letter-spacing: 2px; }
        .nav-link { color: #ccc !important; }
        .nav-link:hover { color: #fff !important; }
        
        /* CSS cho Dropdown menu user */
        .dropdown-menu-dark { background-color: #1f1f1f; border: 1px solid #333; }
        .dropdown-item:hover { background-color: #333; color: #fff; }

        /* [MỚI] CSS cho ô kết quả tìm kiếm */
        #searchResults {
            background-color: #1f1f1f;
            border: 1px solid #333;
            max-height: 400px;
            overflow-y: auto; /* Cho phép cuộn nếu danh sách dài */
        }
        /* CSS cho từng dòng kết quả trong SearchServlet trả về */
        .list-group-item {
            background-color: #1f1f1f !important;
            border-color: #333 !important;
            color: #fff !important;
            transition: background-color 0.2s;
        }
        .list-group-item:hover {
            background-color: #333 !important;
            color: #e50914 !important;
            cursor: pointer;
        }
    </style>
</head>
<body>

<nav class="navbar navbar-expand-lg navbar-dark fixed-top">
  <div class="container">
    <a class="navbar-brand brand-title" href="index.jsp">PHIMHUB</a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
      <span class="navbar-toggler-icon"></span>
    </button>
    
    <div class="collapse navbar-collapse" id="navbarNav">
      <ul class="navbar-nav me-auto mb-2 mb-lg-0">
        <li class="nav-item"><a class="nav-link" href="HomeServlet">Trang Chủ</a></li>
        
        <li class="nav-item"><a class="nav-link" href="FavoriteServlet">Phim Yêu Thích</a></li>
      </ul>

      <div class="mx-auto position-relative d-none d-lg-block me-4" style="width: 350px;">
        <div class="input-group">
            <span class="input-group-text bg-dark border-secondary text-secondary">
                <i class="fas fa-search"></i>
            </span>
            <input type="text" id="searchInput" class="form-control bg-dark text-white border-secondary" 
                   placeholder="Tìm tên phim..." autocomplete="off" onkeyup="searchMovie()">
        </div>
        
        <div id="searchResults" class="position-absolute w-100 shadow rounded mt-1 overflow-hidden" 
             style="z-index: 1050; display: none;">
             </div>
      </div>
      <div class="d-flex align-items-center">
        <c:choose>
            <c:when test="${not empty sessionScope.user}">
                
                <c:if test="${sessionScope.user.admin}">
                    <a href="AdminHomeServlet" class="btn btn-outline-warning btn-sm me-3">
                        <i class="fas fa-tachometer-alt me-1"></i> Quản Trị
                    </a>
                </c:if>

                <div class="dropdown">
                    <button class="btn btn-secondary dropdown-toggle bg-transparent border-0" type="button" data-bs-toggle="dropdown" aria-expanded="false">
                        <img src="https://ui-avatars.com/api/?name=${sessionScope.user.fullName}&background=random" class="rounded-circle me-2" width="30" height="30">
                        Xin chào, <strong>${sessionScope.user.fullName}</strong>
                    </button>
                    <ul class="dropdown-menu dropdown-menu-dark dropdown-menu-end">
                        <li>
                            <a class="dropdown-item" href="ProfileServlet">
                                <i class="fas fa-user-circle me-2"></i>Hồ sơ cá nhân
                            </a>
                        </li>
                        <li>
                            <a class="dropdown-item" href="FavoriteServlet">
                                <i class="fas fa-heart me-2"></i>Phim yêu thích
                            </a>
                        </li>
                        <li><hr class="dropdown-divider bg-secondary"></li>
                        <li>
                            <a class="dropdown-item text-danger" href="LogoffServlet">
                                <i class="fas fa-sign-out-alt me-2"></i>Đăng xuất
                            </a>
                        </li>
                    </ul>
                </div>

            </c:when>
            
            <c:otherwise>
                <a href="LoginServlet" class="btn btn-danger btn-sm">Đăng nhập</a>
            </c:otherwise>
        </c:choose>
      </div>
    </div>
  </div>
</nav>
<div style="margin-top: 70px;"></div>

<script>
    function searchMovie() {
        var keyword = document.getElementById("searchInput").value;
        var resultsBox = document.getElementById("searchResults");

        if (keyword.length > 1) { // Chỉ tìm khi gõ hơn 1 ký tự
            // Gọi sang SearchServlet
            fetch('SearchServlet?query=' + encodeURIComponent(keyword))
                .then(response => response.text())
                .then(data => {
                    resultsBox.innerHTML = data;
                    resultsBox.style.display = "block";
                })
                .catch(err => console.error("Lỗi tìm kiếm:", err));
        } else {
            resultsBox.style.display = "none";
        }
    }

    // Ẩn kết quả khi click ra ngoài vùng tìm kiếm
    document.addEventListener('click', function(e) {
        var searchBox = document.getElementById('searchResults');
        var input = document.getElementById('searchInput');
        if (!input.contains(e.target) && !searchBox.contains(e.target)) {
            searchBox.style.display = 'none';
        }
    });
</script>