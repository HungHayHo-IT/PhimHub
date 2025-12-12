<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<jsp:include page="../../header.jsp"></jsp:include>

<div class="container my-5" style="max-width: 900px;">
    <div class="row g-0 bg-dark text-white shadow-lg rounded overflow-hidden border border-secondary">
        
        <div class="col-md-5 p-4 border-end border-secondary bg-black">
            <h5 class="text-white-50 mb-4">TÓM TẮT ĐƠN HÀNG</h5>
            <div class="text-center mb-3">
                <img src="${video.poster}" class="rounded shadow" style="width: 150px; border: 2px solid #333;">
            </div>
            <h4 class="fw-bold text-center mb-2">${video.title}</h4>
            <div class="d-flex justify-content-between align-items-center mt-4 border-top border-secondary pt-3">
                <span class="text-muted">Phí dịch vụ</span>
                <span>0 đ</span>
            </div>
            <div class="d-flex justify-content-between align-items-center mt-2">
                <span class="text-muted">Tổng cộng</span>
                <h3 class="text-warning fw-bold m-0">
                    <fmt:formatNumber value="${video.price}" type="currency" currencySymbol="₫"/>
                </h3>
            </div>
        </div>

        <div class="col-md-7 p-5">
            <h4 class="mb-4 fw-bold"><i class="far fa-credit-card me-2"></i>Thông Tin Thanh Toán</h4>
            
            <c:if test="${not empty error}">
                <div class="alert alert-danger">
                    <i class="fas fa-exclamation-circle me-2"></i> ${error}
                </div>
            </c:if>

            <form action="PaymentServlet" method="post" id="paymentForm">
                <input type="hidden" name="videoId" value="${video.videoId}">
                <input type="hidden" name="price" value="${video.price}">

                <div class="mb-3">
                    <label class="form-label text-muted small">CHỦ THẺ (In trên thẻ)</label>
                    <input type="text" name="cardHolder" class="form-control bg-secondary text-white border-0 py-2" 
                           placeholder="NGUYEN VAN A" required style="text-transform: uppercase;">
                </div>

                <div class="mb-3">
                    <label class="form-label text-muted small">SỐ THẺ</label>
                    <div class="input-group">
                        <input type="text" name="cardNumber" class="form-control bg-secondary text-white border-0 py-2" 
                               placeholder="0000 0000 0000 0000" maxlength="19" required id="cardNumber">
                        <span class="input-group-text bg-secondary border-0 text-white">
                            <i class="fab fa-cc-visa fa-lg me-2"></i> <i class="fab fa-cc-mastercard fa-lg"></i>
                        </span>
                    </div>
                </div>

                <div class="row">
                    <div class="col-6">
                        <div class="mb-3">
                            <label class="form-label text-muted small">HẾT HẠN (MM/YY)</label>
                            <input type="text" name="expiry" class="form-control bg-secondary text-white border-0 py-2" 
                                   placeholder="MM/YY" maxlength="5" required>
                        </div>
                    </div>
                    <div class="col-6">
                        <div class="mb-3">
                            <label class="form-label text-muted small">MÃ CVV</label>
                            <input type="password" name="cvv" class="form-control bg-secondary text-white border-0 py-2" 
                                   placeholder="123" maxlength="3" required>
                        </div>
                    </div>
                </div>

                <div class="mt-4">
                    <button type="submit" class="btn btn-danger w-100 py-3 fw-bold shadow" id="btnPay">
                        THANH TOÁN NGAY
                    </button>
                    <div class="text-center mt-3">
                        <a href="HomeServlet" class="text-decoration-none text-muted small">Hủy bỏ giao dịch</a>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>

<script>
    // JS nhỏ để tự động thêm dấu cách khi nhập số thẻ
    document.getElementById('cardNumber').addEventListener('input', function (e) {
        e.target.value = e.target.value.replace(/[^\d]/g, '').replace(/(.{4})/g, '$1 ').trim();
    });

    // Hiệu ứng Loading khi bấm nút
    document.getElementById('paymentForm').addEventListener('submit', function() {
        var btn = document.getElementById('btnPay');
        btn.innerHTML = '<i class="fas fa-spinner fa-spin me-2"></i> Đang xử lý...';
        btn.disabled = true;
    });
</script>

<jsp:include page="../../footer.jsp"></jsp:include>