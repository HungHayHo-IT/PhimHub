<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Quản Lý Phim - PhimHub Admin</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body class="bg-light">

    <div class="container-fluid mt-4">
        <div class="row">
            <div class="col-md-2">
                <a href="${pageContext.request.contextPath}/AdminHomeServlet" class="btn btn-secondary w-100 mb-2">
                    <i class="fas fa-arrow-left"></i> Quay lại Dashboard
                </a>
            </div>

            <div class="col-md-10">
                <div class="card shadow-sm">
                    <div class="card-header bg-white d-flex justify-content-between align-items-center">
                        <h5 class="mb-0 text-danger fw-bold"><i class="fas fa-film me-2"></i>DANH SÁCH PHIM</h5>
                        <a href="${pageContext.request.contextPath}/admin/video/reset" class="btn btn-success">
                            <i class="fas fa-plus me-1"></i> Thêm Phim Mới
                        </a>
                    </div>
                    
                    <div class="card-body">
                        <div class="table-responsive">
                            <table class="table table-hover table-bordered align-middle">
                                <thead class="table-dark text-center">
                                    <tr>
                                        <th>ID</th>
                                        <th>Poster</th>
                                        <th>Tên Phim</th>
                                        <th>Lượt xem</th>
                                        <th>Trạng thái</th>
                                        <th>Giá tiền</th>
                                        <th>Hành động</th>
                                        
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${videos}" var="item">
                                        <tr>
                                            <td class="text-center fw-bold">${item.videoId}</td>
                                            <td class="text-center">
                                                <img src="${item.poster}" alt="Poster" style="width: 50px; height: 75px; object-fit: cover; border-radius: 4px;">
                                            </td>
                                            <td>${item.title}</td>
                                            <td class="text-center">${item.views}</td>
                                            <td class="text-center">
                                                <c:choose>
                                                    <c:when test="${item.active}">
                                                        <span class="badge bg-success">Hoạt động</span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span class="badge bg-secondary">Ẩn</span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td>${item.price}</td>
                                            <td class="text-center">
                                                <a href="${pageContext.request.contextPath}/admin/video/edit?id=${item.videoId}" class="btn btn-primary btn-sm me-1">
                                                    <i class="fas fa-edit"></i> Sửa
                                                </a>
                                                <a href="${pageContext.request.contextPath}/admin/video/delete?id=${item.videoId}" 
                                                   class="btn btn-danger btn-sm"
                                                   onclick="return confirm('Bạn có chắc chắn muốn xóa phim này không?')">
                                                    <i class="fas fa-trash"></i> Xóa
                                                </a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

</body>
</html>