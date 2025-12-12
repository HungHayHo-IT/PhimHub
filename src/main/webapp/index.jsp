<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="header.jsp"></jsp:include>

<style>
    /* CSS riêng cho phần Banner của trang chủ */
    .hero-section {
        position: relative;
        height: 80vh; /* Chiều cao bằng 80% màn hình */
        background: url('https://assets.nflxext.com/ffe/siteui/vlv3/f841d4c7-10e1-40af-bcae-07a3f8dc141a/f6d7434e-d6de-4185-a6d4-c77a2d08737b/US-en-20220502-popsignuptwoweeks-perspective_alpha_website_large.jpg') center/cover;
        display: flex;
        align-items: center;
        justify-content: center;
        text-align: center;
    }
    .overlay {
        position: absolute; top: 0; left: 0; width: 100%; height: 100%;
        background: rgba(0, 0, 0, 0.6); /* Lớp phủ đen mờ */
    }
    .hero-content { position: relative; z-index: 2; color: white; padding: 20px; }
    .btn-cta {
        background-color: #e50914; color: white; padding: 12px 30px; font-size: 1.2rem;
        border: none; border-radius: 4px; font-weight: bold; text-decoration: none;
    }
    .btn-cta:hover { background-color: #f40612; color: white; }
</style>

<div class="hero-section">
    <div class="overlay"></div>
    <div class="hero-content">
        <h1 class="display-3 fw-bold mb-3">Chương trình truyền hình, phim không giới hạn</h1>
        <p class="fs-4 mb-4">Xem ở mọi nơi. Hủy bất kỳ lúc nào.</p>
        
        <a href="HomeServlet" class="btn-cta">
            Khám Phá Ngay <i class="fas fa-chevron-right ms-2"></i>
        </a>
    </div>
</div>

<jsp:include page="footer.jsp"></jsp:include>