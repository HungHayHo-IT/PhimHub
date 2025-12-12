<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<jsp:include page="../../header.jsp"></jsp:include>

<style>
    /* CSS riêng cho trang chi tiết */
    body {
        background-color: #0f0f0f;
        color: #e0e0e0;
    }
    .video-container {
        box-shadow: 0 4px 30px rgba(229, 9, 20, 0.3);
        border-radius: 8px;
        overflow: hidden;
        position: relative; /* Để overlay hoạt động */
        background: #000;
    }
    .movie-title {
        font-weight: 900;
        letter-spacing: 1px;
        text-shadow: 2px 2px 4px rgba(0,0,0,0.8);
    }
    .btn-action {
        transition: all 0.3s;
        border-radius: 20px;
        padding: 8px 20px;
        font-weight: 600;
    }
    .btn-action:hover {
        transform: translateY(-2px);
    }
    .recommend-card img {
        transition: transform 0.3s;
    }
    .recommend-card:hover img {
        transform: scale(1.05);
    }
    .text-justify {
        text-align: justify;
    }
    
    /* CSS cho lớp phủ khi chưa mua phim */
    .locked-content {
        position: absolute;
        top: 0; left: 0; 
        width: 100%; height: 100%;
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;
        z-index: 10;
        background: rgba(0, 0, 0, 0.7); /* Nền đen mờ */
    }
    .poster-bg {
        width: 100%; height: 100%; 
        object-fit: cover; 
        opacity: 0.3; /* Làm mờ ảnh nền */
    }
</style>

<div class="container-fluid py-4" style="max-width: 1400px;">
    <div class="row g-4">

        <div class="col-lg-9">

            <div class="ratio ratio-16x9 video-container mb-4">
                <c:choose>
                    <%-- TRƯỜNG HỢP 1: ĐƯỢC PHÉP XEM (Miễn phí hoặc Đã mua) --%>
                    <c:when test="${isGranted}">
                        <iframe src="${video.href}?autoplay=1&rel=0"
                                title="${video.title}"
                                allowfullscreen
                                allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture">
                        </iframe>
                    </c:when>
                    
                    <%-- TRƯỜNG HỢP 2: CHƯA MUA PHIM TÍNH PHÍ --%>
                    <c:otherwise>
                        <img src="${video.poster}" class="poster-bg" alt="Locked">
                        
                        <div class="locked-content px-3 text-center">
                            <h2 class="text-danger fw-bold text-uppercase mb-3">
                                <i class="fas fa-lock me-2"></i>Nội Dung Tính Phí
                            </h2>
                            <p class="text-white mb-4 fs-5">
                                Bạn cần thanh toán để mở khóa bộ phim bom tấn này.
                            </p>
                            
                            <h1 class="text-warning mb-4 fw-bold" style="font-size: 3rem; text-shadow: 0 0 10px rgba(255, 193, 7, 0.5);">
                                <fmt:formatNumber value="${video.price}" type="currency" currencySymbol="₫"/>
                            </h1>

                            <a href="CheckoutServlet?id=${video.videoId}" class="btn btn-danger btn-lg px-5 py-3 rounded-pill fw-bold shadow">
                                <i class="fas fa-shopping-cart me-2"></i> MUA NGAY
                            </a>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>

            <div class="bg-dark p-4 rounded shadow-sm border border-secondary border-opacity-25">
                <div class="d-flex justify-content-between align-items-start flex-wrap">
                    <h1 class="movie-title text-danger text-uppercase mb-2">${video.title}</h1>
                    
                    <c:if test="${video.price > 0}">
                        <span class="badge bg-warning text-dark fs-5 mb-2">
                            <c:choose>
                                <c:when test="${isGranted}">
                                    <i class="fas fa-check-circle me-1"></i> Đã sở hữu
                                </c:when>
                                <c:otherwise>
                                    Giá: <fmt:formatNumber value="${video.price}" type="number"/> đ
                                </c:otherwise>
                            </c:choose>
                        </span>
                    </c:if>
                </div>

                <div class="d-flex flex-wrap align-items-center justify-content-between mb-3 text-muted small">
                    <div>
                        <span class="me-3">
                            <i class="fas fa-eye text-warning me-1"></i>
                            <fmt:formatNumber value="${video.views}" type="number"/> lượt xem
                        </span>
                        <span class="me-3">
                            <i class="fas fa-calendar-alt text-primary me-1"></i> 2024
                        </span>
                        <span class="badge bg-secondary">HD</span>
                    </div>

                    <div class="mt-2 mt-md-0">
                        <a href="VideoServlet?action=like&id=${video.videoId}"
                           class="btn btn-action me-2 ${flagLike ? 'btn-danger' : 'btn-outline-danger'}">
                            <i class="${flagLike ? 'fas' : 'far'} fa-heart me-2"></i>
                            ${flagLike ? 'Đã Thích' : 'Yêu Thích'}
                        </a>

                        <button type="button" class="btn btn-outline-light btn-action"
                                data-bs-toggle="modal" data-bs-target="#shareModal">
                            <i class="fas fa-share me-2"></i> Chia Sẻ
                        </button>

                        <div class="modal fade" id="shareModal" tabindex="-1" aria-hidden="true">
                            <div class="modal-dialog modal-dialog-centered">
                                <div class="modal-content bg-dark text-white border-secondary">
                                    <div class="modal-header border-secondary">
                                        <h5 class="modal-title">Chia Sẻ Phim Qua Email</h5>
                                        <button type="button" class="btn-close btn-close-white"
                                                data-bs-dismiss="modal" aria-label="Close"></button>
                                    </div>

                                    <form action="ShareServlet" method="post">
                                        <div class="modal-body">
                                            <input type="hidden" name="videoId" value="${video.videoId}">
                                            <input type="hidden" name="videoTitle" value="${video.title}">

                                            <div class="mb-3">
                                                <label for="emailShare" class="form-label">Email người nhận:</label>
                                                <input type="email"
                                                       class="form-control bg-secondary text-white border-0"
                                                       id="emailShare"
                                                       name="email"
                                                       required
                                                       placeholder="nhap_email_ban_be@gmail.com">
                                            </div>
                                        </div>

                                        <div class="modal-footer border-secondary">
                                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                                            <button type="submit" class="btn btn-danger">Gửi Ngay</button>
                                        </div>
                                    </form>

                                </div>
                            </div>
                        </div>

                    </div>
                </div>

                <hr class="border-secondary border-opacity-50">

                <h5 class="text-white fw-bold mb-3">Nội Dung Phim</h5>
                <p class="text-justify text-light opacity-75 lh-lg">
                    ${video.description}
                </p>

                <div class="mt-4">
                    <span class="text-muted small">Tags: </span>
                    <a href="#" class="badge bg-secondary text-decoration-none">Hành động</a>
                    <a href="#" class="badge bg-secondary text-decoration-none">Viễn tưởng</a>
                    <a href="#" class="badge bg-secondary text-decoration-none">Bom tấn</a>
                </div>
            </div>

            <div class="mt-5">
                <h4 class="text-white border-start border-4 border-danger ps-3 mb-4">Bình Luận</h4>

                <div class="d-flex align-items-start mb-3">
                    <img src="https://ui-avatars.com/api/?name=User&background=random"
                         class="rounded-circle me-3" width="40">
                    <div class="form-floating w-100">
                        <textarea class="form-control bg-dark text-white border-secondary"
                                  placeholder="Viết bình luận..." style="height: 100px"></textarea>
                        <label class="text-muted">Viết bình luận của bạn...</label>
                    </div>
                </div>

                <div class="d-flex justify-content-end">
                    <button class="btn btn-danger">Gửi Bình Luận</button>
                </div>
            </div>

        </div>

        <div class="col-lg-3">
            <h5 class="text-white border-bottom border-danger pb-2 mb-3">Phim Có Thể Bạn Thích</h5>

            <div class="d-flex flex-column gap-3">
                <a href="#" class="text-decoration-none text-white recommend-card">
                    <div class="row g-0 align-items-center bg-dark rounded overflow-hidden border border-secondary border-opacity-25 p-2">
                        <div class="col-4">
                            <img src="https://upload.wikimedia.org/wikipedia/en/5/52/Dune_Part_Two_poster.jpg"
                                 class="img-fluid rounded" alt="Poster">
                        </div>
                        <div class="col-8 ps-3">
                            <h6 class="mb-1 text-truncate">Dune: Part Two</h6>
                            <small class="text-muted d-block"><i class="fas fa-star text-warning"></i> 9.8</small>
                            <small class="text-secondary" style="font-size: 0.8rem;">2024</small>
                        </div>
                    </div>
                </a>
                
                <a href="#" class="text-decoration-none text-white recommend-card">
                    <div class="row g-0 align-items-center bg-dark rounded overflow-hidden border border-secondary border-opacity-25 p-2">
                        <div class="col-4">
                            <img src="https://upload.wikimedia.org/wikipedia/en/b/be/Godzilla_x_kong_the_new_empire_poster.jpg"
                                 class="img-fluid rounded" alt="Poster">
                        </div>
                        <div class="col-8 ps-3">
                            <h6 class="mb-1 text-truncate">Godzilla x Kong</h6>
                            <small class="text-muted d-block"><i class="fas fa-star text-warning"></i> 8.5</small>
                            <small class="text-secondary" style="font-size: 0.8rem;">2024</small>
                        </div>
                    </div>
                </a>

                <a href="#" class="text-decoration-none text-white recommend-card">
                    <div class="row g-0 align-items-center bg-dark rounded overflow-hidden border border-secondary border-opacity-25 p-2">
                        <div class="col-4">
                            <img src="https://upload.wikimedia.org/wikipedia/en/7/7f/Kung_Fu_Panda_4_poster.jpg"
                                 class="img-fluid rounded" alt="Poster">
                        </div>
                        <div class="col-8 ps-3">
                            <h6 class="mb-1 text-truncate">Kung Fu Panda 4</h6>
                            <small class="text-muted d-block"><i class="fas fa-star text-warning"></i> 9.0</small>
                            <small class="text-secondary" style="font-size: 0.8rem;">2024</small>
                        </div>
                    </div>
                </a>

            </div>
        </div>

    </div>
</div>

<jsp:include page="../../footer.jsp"></jsp:include>