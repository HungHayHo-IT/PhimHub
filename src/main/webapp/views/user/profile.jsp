<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:include page="../../header.jsp"></jsp:include>

<div class="container my-5">
    <div class="row justify-content-center">
        <div class="col-lg-8">
            <h2 class="text-white mb-4 border-start border-4 border-danger ps-3">Hồ Sơ Của Tôi</h2>

            <c:if test="${not empty message}">
                <div class="alert alert-success mb-4">${message}</div>
            </c:if>
            <c:if test="${not empty error}">
                <div class="alert alert-danger mb-4">${error}</div>
            </c:if>

            <div class="card bg-dark text-white border-secondary">
                <div class="card-header border-secondary">
                    <ul class="nav nav-tabs card-header-tabs" id="profileTab" role="tablist">
                        <li class="nav-item">
                            <button class="nav-link active text-white" id="info-tab" data-bs-toggle="tab" data-bs-target="#info" type="button">
                                <i class="fas fa-user me-2"></i>Thông Tin Chung
                            </button>
                        </li>
                        <li class="nav-item">
                            <button class="nav-link text-white" id="password-tab" data-bs-toggle="tab" data-bs-target="#password" type="button">
                                <i class="fas fa-key me-2"></i>Đổi Mật Khẩu
                            </button>
                        </li>
                    </ul>
                </div>
                
                <div class="card-body p-4">
                    <div class="tab-content" id="profileTabContent">
                        
                        <div class="tab-pane fade show active" id="info" role="tabpanel">
                            <form action="ProfileServlet" method="post">
                                <input type="hidden" name="action" value="update_info">
                                
                                <div class="mb-3">
                                    <label class="form-label text-muted">Email (Không thể thay đổi)</label>
                                    <input type="text" class="form-control bg-secondary text-white border-0" value="${user.email}" disabled>
                                </div>
                                
                                <div class="mb-3">
                                    <label class="form-label">Họ và Tên</label>
                                    <input type="text" name="fullname" class="form-control bg-dark text-white border-secondary" value="${user.fullName}" required>
                                </div>
                                
                                <div class="mt-4">
                                    <button type="submit" class="btn btn-danger">Lưu Thay Đổi</button>
                                </div>
                            </form>
                        </div>

                        <div class="tab-pane fade" id="password" role="tabpanel">
                            <form action="ProfileServlet" method="post">
                                <input type="hidden" name="action" value="change_password">
                                
                                <div class="mb-3">
                                    <label class="form-label">Mật khẩu hiện tại</label>
                                    <input type="password" name="old_pass" class="form-control bg-dark text-white border-secondary" required>
                                </div>
                                
                                <div class="mb-3">
                                    <label class="form-label">Mật khẩu mới</label>
                                    <input type="password" name="new_pass" class="form-control bg-dark text-white border-secondary" required minlength="6">
                                </div>
                                
                                <div class="mb-3">
                                    <label class="form-label">Nhập lại mật khẩu mới</label>
                                    <input type="password" name="confirm_pass" class="form-control bg-dark text-white border-secondary" required minlength="6">
                                </div>
                                
                                <div class="mt-4">
                                    <button type="submit" class="btn btn-danger">Đổi Mật Khẩu</button>
                                </div>
                            </form>
                        </div>

                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<style>
    /* CSS cho Tab active màu đỏ */
    .nav-tabs .nav-link.active {
        background-color: #dc3545 !important; /* Màu đỏ Bootstrap */
        color: white !important;
        border-color: #dc3545 !important;
    }
    .nav-tabs .nav-link:hover {
        border-color: #dc3545;
        color: #dc3545;
    }
</style>

<jsp:include page="../../footer.jsp"></jsp:include>