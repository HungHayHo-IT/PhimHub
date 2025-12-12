<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Quản Trị Hệ Thống - PhimHub</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        body { background-color: #f4f6f9; }
        .sidebar { min-height: 100vh; background: #343a40; color: white; }
        .sidebar a { color: #c2c7d0; text-decoration: none; padding: 15px; display: block; border-bottom: 1px solid #4b545c; }
        .sidebar a:hover, .sidebar a.active { background: #007bff; color: white; }
        .card-box { border-radius: 10px; color: white; padding: 20px; position: relative; overflow: hidden; }
        .bg-primary-gradient { background: linear-gradient(45deg, #007bff, #0056b3); }
        .bg-success-gradient { background: linear-gradient(45deg, #28a745, #1e7e34); }
        .bg-warning-gradient { background: linear-gradient(45deg, #ffc107, #d39e00); }
        .bg-danger-gradient { background: linear-gradient(45deg, #dc3545, #bd2130); }
    </style>
</head>
<body>
    <div class="container-fluid">
        <div class="row">
            <div class="col-md-2 sidebar p-0">
                <div class="p-3 text-center fw-bold border-bottom border-secondary">
                    <h4 class="text-danger">PHIMHUB ADMIN</h4>
                </div>
                <div class="p-3 text-center">
                    <img src="https://ui-avatars.com/api/?name=${sessionScope.user.fullName}" class="rounded-circle mb-2" width="50">
                    <br> Xin chào, ${sessionScope.user.fullName}
                </div>
                <a href="admin/home" class="active"><i class="fas fa-tachometer-alt me-2"></i> Dashboard</a>
                <a href="admin/videos"><i class="fas fa-film me-2"></i> Quản Lý Phim</a>
                <a href="admin/users"><i class="fas fa-users me-2"></i> Quản Lý Người Dùng</a>
                <a href="HomeServlet"><i class="fas fa-home me-2"></i> Về Trang Chủ</a>
                <a href="LogoffServlet"><i class="fas fa-sign-out-alt me-2"></i> Đăng Xuất</a>
            </div>

            <div class="col-md-10 p-4">
                <h3 class="mb-4">Tổng Quan Hệ Thống</h3>
                
                <div class="row g-4">
                    <div class="col-md-3">
                        <div class="card-box bg-primary-gradient">
                            <h3>${videoCount}</h3>
                            <p>Tổng số phim</p>
                            <i class="fas fa-film fa-4x position-absolute" style="opacity: 0.3; right: 10px; top: 10px;"></i>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="card-box bg-success-gradient">
                            <h3>${userCount}</h3>
                            <p>Thành viên</p>
                            <i class="fas fa-users fa-4x position-absolute" style="opacity: 0.3; right: 10px; top: 10px;"></i>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="card-box bg-warning-gradient">
                            <h3>${viewCount}</h3>
                            <p>Lượt xem</p>
                            <i class="fas fa-eye fa-4x position-absolute" style="opacity: 0.3; right: 10px; top: 10px;"></i>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="card-box bg-danger-gradient">
                            <h3>${likeCount}</h3>
                            <p>Lượt yêu thích</p>
                            <i class="fas fa-heart fa-4x position-absolute" style="opacity: 0.3; right: 10px; top: 10px;"></i>
                        </div>
                    </div>
                </div>

                <div class="card mt-4 shadow-sm">
                    <div class="card-header bg-white fw-bold">
                        <i class="fas fa-chart-bar me-2"></i> Hoạt động gần đây
                    </div>
                    <div class="card-body">
                        <p class="text-muted text-center py-5">Chưa có dữ liệu thống kê chi tiết...</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>