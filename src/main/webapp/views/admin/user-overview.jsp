<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Quản Lý Người Dùng - PhimHub Admin</title>
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
                    <div class="card-header bg-white">
                        <h5 class="mb-0 text-primary fw-bold"><i class="fas fa-users me-2"></i>DANH SÁCH NGƯỜI DÙNG</h5>
                    </div>
                    
                    <div class="card-body">
                        <c:if test="${not empty error}">
                            <div class="alert alert-danger">${error}</div>
                        </c:if>
                        <c:if test="${param.error == 'cant_change_self'}">
                            <div class="alert alert-warning">Bạn không thể thay đổi quyền của chính mình!</div>
                        </c:if>
                        <c:if test="${not empty message}">
                            <div class="alert alert-success">${message}</div>
                        </c:if>

                        <div class="table-responsive">
                            <table class="table table-hover table-bordered align-middle">
                                <thead class="table-dark text-center">
                                    <tr>
                                        <th>User ID</th>
                                        <th>Họ và Tên</th>
                                        <th>Email</th>
                                        <th>Quyền Admin</th> <th>Hành động</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${users}" var="item">
                                        <tr>
                                            <td class="text-center fw-bold">${item.userId}</td>
                                            <td>${item.fullName}</td>
                                            <td>${item.email}</td>
                                            
                                            <td class="text-center">
                                                <form action="${pageContext.request.contextPath}/admin/user/toggle" method="post">
                                                    <input type="hidden" name="userId" value="${item.userId}">
                                                    
                                                    <div class="form-check form-switch d-flex justify-content-center">
                                                        <input class="form-check-input" type="checkbox" 
                                                               onchange="this.form.submit()"
                                                               style="cursor: pointer; transform: scale(1.3);"
                                                               ${item.admin ? 'checked' : ''}
                                                               ${item.userId == sessionScope.user.userId ? 'disabled' : ''} 
                                                               title="${item.userId == sessionScope.user.userId ? 'Không thể tự hủy quyền của mình' : 'Bật/Tắt quyền Admin'}">
                                                    </div>
                                                </form>
                                            </td>

                                            <td class="text-center">
                                                <c:if test="${item.userId != sessionScope.user.userId}">
                                                    <a href="${pageContext.request.contextPath}/admin/user/delete?id=${item.userId}" 
                                                       class="btn btn-danger btn-sm"
                                                       onclick="return confirm('Bạn có chắc chắn muốn xóa người dùng này?')">
                                                        <i class="fas fa-trash"></i> Xóa
                                                    </a>
                                                </c:if>
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