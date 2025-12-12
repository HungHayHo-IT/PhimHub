<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>${not empty video.videoId ? 'Cập Nhật Phim' : 'Thêm Phim Mới'}</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

    <div class="container mt-5" style="max-width: 800px;">
        <div class="card shadow">
            <div class="card-header bg-dark text-white">
                <h4 class="mb-0">
                    ${not empty video.title ? 'CẬP NHẬT PHIM: ' : 'THÊM PHIM MỚI'} 
                    <span class="text-warning">${video.title}</span>
                </h4>
            </div>
            <div class="card-body">
                
                <c:if test="${not empty error}">
                    <div class="alert alert-danger">${error}</div>
                </c:if>
                <c:if test="${not empty message}">
                    <div class="alert alert-success">${message}</div>
                </c:if>

                <c:set var="actionUrl" value="${not empty video.title ? 'update' : 'create'}" />
                
                <form action="${pageContext.request.contextPath}/admin/video/${actionUrl}" method="post">
                    
                    <div class="row mb-3">
                        <div class="col-md-6">
                            <label class="form-label fw-bold">Mã Video (ID)</label>
                            <input type="text" name="videoId" class="form-control" value="${video.videoId}" 
                                   ${not empty video.title ? 'readonly' : ''} required placeholder="Ví dụ: v01">
                        </div>
                        <div class="col-md-6">
                            <label class="form-label fw-bold">Tên Phim</label>
                            <input type="text" name="title" class="form-control" value="${video.title}" required>
                        </div>
                    </div>

                    <div class="mb-3">
                        <label class="form-label fw-bold">Link Hình Ảnh (Poster)</label>
                        <input type="text" name="poster" class="form-control" value="${video.poster}" required placeholder="https://...">
                    </div>

                    <div class="mb-3">
                        <label class="form-label fw-bold">Link Video (Youtube Embed/ID)</label>
                        <input type="text" name="href" class="form-control" value="${video.href}" required placeholder="https://www.youtube.com/embed/...">
                    </div>

                    <div class="mb-3">
                        <label class="form-label fw-bold">Mô Tả Phim</label>
                        <textarea name="description" class="form-control" rows="4">${video.description}</textarea>
                    </div>
					<div class="mb-3">
    					<label class="form-label fw-bold">Giá Tiền (VNĐ)</label>
    					<input type="number" name="price" class="form-control" value="${video.price}" min="0" step="1000" placeholder="Nhập 0 nếu là phim miễn phí">
					</div>
                    <div class="form-check mb-4">
                        <input class="form-check-input" type="checkbox" name="active" id="activeCheck" 
                               ${video.active ? 'checked' : ''} ${empty video.title ? 'checked' : ''}>
                        <label class="form-check-label" for="activeCheck">
                            Kích hoạt (Hiển thị phim này)
                        </label>
                    </div>
					
                    <div class="d-flex justify-content-end gap-2">
                        <a href="${pageContext.request.contextPath}/admin/videos" class="btn btn-secondary">Hủy bỏ</a>
                        <button type="submit" class="btn btn-primary">
                            ${not empty video.title ? 'Lưu Cập Nhật' : 'Thêm Mới'}
                        </button>
                    </div>
                </form>

            </div>
        </div>
    </div>

</body>
</html>