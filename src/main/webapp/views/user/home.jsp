<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="../../header.jsp" />

<div class="container my-5">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h3 class="text-white border-start border-4 border-danger ps-3">Phim Mới Cập Nhật</h3>
    </div>

    <div class="row row-cols-1 row-cols-md-3 row-cols-lg-4 g-4">
        
        <c:forEach items="${videos}" var="video">
            <div class="col">
                <div class="card h-100 bg-dark text-white border-secondary shadow-sm card-hover">
                    <div class="position-relative overflow-hidden">
                        <img src="${video.poster}" class="card-img-top" alt="${video.title}" 
                             style="height: 300px; object-fit: cover;">
                        
                        <c:if test="${video.price > 0}">
                            <div class="position-absolute top-0 end-0 m-2" style="z-index: 5;">
                                <span class="badge bg-warning text-dark shadow fw-bold">
                                    <fmt:formatNumber value="${video.price}" type="number"/> đ
                                </span>
                            </div>
                        </c:if>
                        <div class="card-overlay d-flex align-items-center justify-content-center">
                            <a href="VideoServlet?action=watch&id=${video.videoId}" class="btn btn-danger rounded-circle p-3">
                                <i class="fas fa-play fa-lg"></i>
                            </a>
                        </div>
                    </div>

                    <div class="card-body">
                        <h5 class="card-title text-truncate" title="${video.title}">${video.title}</h5>
                        <p class="card-text small text-muted text-truncate">${video.description}</p>
                    </div>

                    <div class="card-footer bg-transparent border-top border-secondary d-flex justify-content-between align-items-center">
                        <small class="text-muted">
                            <i class="fas fa-eye me-1"></i> 
                            <fmt:formatNumber value="${video.views}" type="number"/> lượt xem
                        </small>
                        <button class="btn btn-sm btn-outline-danger">
                            <i class="fas fa-heart"></i>
                        </button>
                    </div>
                </div>
            </div>
        </c:forEach>
        
    </div>
</div>

<style>
    /* Hiệu ứng zoom nhẹ khi di chuột vào card */
    .card-hover { transition: transform 0.3s ease, box-shadow 0.3s ease; }
    .card-hover:hover { transform: translateY(-5px); box-shadow: 0 10px 20px rgba(229, 9, 20, 0.4); border-color: #e50914 !important; }
    
    /* Hiệu ứng lớp phủ mờ đen khi hover ảnh */
    .card-overlay {
        position: absolute; top: 0; left: 0; width: 100%; height: 100%;
        background: rgba(0, 0, 0, 0.5);
        opacity: 0; transition: opacity 0.3s;
    }
    .card-hover:hover .card-overlay { opacity: 1; }
</style>

<jsp:include page="../../footer.jsp"></jsp:include>