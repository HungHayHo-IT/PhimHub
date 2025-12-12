<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<footer class="bg-black text-light pt-5 mt-5 border-top border-secondary">
    <div class="container">
        <div class="row g-4">
            <div class="col-md-4">
                <h5 class="text-uppercase fw-bold text-danger mb-3">PHIMHUB</h5>
                <p class="text-muted small">
                    Trải nghiệm xem phim chất lượng cao, cập nhật liên tục các bộ phim bom tấn, phim bộ và show truyền hình hấp dẫn nhất.
                </p>
                <div class="mt-3">
                    <a href="#" class="text-light me-3"><i class="fab fa-facebook fa-lg"></i></a>
                    <a href="#" class="text-light me-3"><i class="fab fa-instagram fa-lg"></i></a>
                    <a href="#" class="text-light me-3"><i class="fab fa-youtube fa-lg"></i></a>
                </div>
            </div>

            <div class="col-md-4">
                <h5 class="text-white mb-3">Thông Tin</h5>
                <ul class="list-unstyled text-muted small">
                    <li class="mb-2"><a href="#" class="text-decoration-none text-muted">Điều khoản sử dụng</a></li>
                    <li class="mb-2"><a href="#" class="text-decoration-none text-muted">Chính sách bảo mật</a></li>
                    <li class="mb-2"><a href="#" class="text-decoration-none text-muted">Hỗ trợ khách hàng</a></li>
                </ul>
            </div>

            <div class="col-md-4">
                <h5 class="text-white mb-3">Liên Hệ</h5>
                <ul class="list-unstyled text-muted small">
                    <li class="mb-2"><i class="fas fa-map-marker-alt me-2"></i> TP. Hồ Chí Minh, Việt Nam</li>
                    <li class="mb-2"><i class="fas fa-envelope me-2"></i> contact@phimhub.com</li>
                    <li class="mb-2"><i class="fas fa-phone me-2"></i> 0909 123 456</li>
                </ul>
            </div>
        </div>

        <hr class="border-secondary my-4">

        <div class="row align-items-center pb-3">
            <div class="col-md-6 text-center text-md-start">
                <small class="text-muted">&copy; 2025 PhimHub. All rights reserved.</small>
            </div>
        </div>
    </div>
</footer>

<button class="chat-btn" onclick="toggleChat()">
    <i class="fas fa-comment-dots fa-lg"></i>
</button>

<div class="chat-box" id="chatBox">
    <div class="chat-header">
        <span><i class="fas fa-robot me-2"></i>PhimHub Assistant</span>
        <button type="button" class="btn-close btn-close-white" onclick="toggleChat()"></button>
    </div>
    
    <div class="chat-body" id="chatContent">
        <div class="bot-msg">Xin chào! Tôi là AI tư vấn phim. Bạn muốn tìm phim gì hay cần hỏi giá vé? (Ví dụ: "Phim hành động hay", "Giá vé")</div>
    </div>
    
    <div class="chat-footer">
        <input type="text" id="chatInput" placeholder="Nhập tin nhắn..." onkeypress="handleEnter(event)">
        <button onclick="sendMessage()"><i class="fas fa-paper-plane"></i></button>
    </div>
</div>

<style>
    /* Nút tròn nổi */
    .chat-btn {
        position: fixed; bottom: 30px; right: 30px;
        width: 60px; height: 60px;
        background-color: #e50914; color: white;
        border: none; border-radius: 50%;
        box-shadow: 0 4px 10px rgba(0,0,0,0.5);
        cursor: pointer; z-index: 9999; /* Z-index cao để nổi lên trên cùng */
        transition: transform 0.3s;
        display: flex; align-items: center; justify-content: center;
    }
    .chat-btn:hover { transform: scale(1.1); background-color: #ff0f1f; }

    /* Khung chat */
    .chat-box {
        position: fixed; bottom: 100px; right: 30px;
        width: 350px; height: 450px;
        background-color: #1f1f1f; border: 1px solid #333;
        border-radius: 10px;
        display: none; /* Mặc định ẩn */
        flex-direction: column;
        box-shadow: 0 5px 25px rgba(0,0,0,0.8);
        z-index: 9999;
        overflow: hidden;
    }

    .chat-header {
        background-color: #e50914; color: white;
        padding: 15px;
        display: flex; justify-content: space-between; align-items: center;
        font-weight: bold;
        border-bottom: 1px solid #333;
    }

    .chat-body {
        flex: 1; padding: 15px;
        overflow-y: auto;
        display: flex; flex-direction: column; gap: 10px;
        background-color: #141414;
    }

    .chat-footer {
        padding: 10px; border-top: 1px solid #333;
        display: flex; gap: 5px;
        background-color: #1f1f1f;
    }
    .chat-footer input {
        flex: 1; padding: 8px 15px; border-radius: 20px; border: none; outline: none;
        background-color: #333; color: white;
    }
    .chat-footer button {
        background: none; border: none; color: #e50914; font-size: 1.2rem; cursor: pointer;
    }

    /* Tin nhắn */
    .user-msg {
        align-self: flex-end;
        background-color: #e50914; color: white;
        padding: 8px 12px; border-radius: 15px 15px 0 15px;
        max-width: 80%;
        word-wrap: break-word;
        font-size: 0.9rem;
    }
    .bot-msg {
        align-self: flex-start;
        background-color: #333; color: white;
        padding: 8px 12px; border-radius: 15px 15px 15px 0;
        max-width: 80%;
        word-wrap: break-word;
        font-size: 0.9rem;
    }
    
    /* Scrollbar đẹp hơn */
    .chat-body::-webkit-scrollbar { width: 6px; }
    .chat-body::-webkit-scrollbar-thumb { background-color: #555; border-radius: 3px; }
</style>

<script>
    function toggleChat() {
        var box = document.getElementById("chatBox");
        if (box.style.display === "none" || box.style.display === "") {
            box.style.display = "flex";
        } else {
            box.style.display = "none";
        }
    }

    function handleEnter(e) {
        if (e.key === "Enter") sendMessage();
    }

    function sendMessage() {
        var input = document.getElementById("chatInput");
        var message = input.value.trim();
        if (message === "") return;

        // 1. Hiện tin nhắn người dùng lên giao diện
        var chatContent = document.getElementById("chatContent");
        chatContent.innerHTML += '<div class="user-msg">' + message + '</div>';
        input.value = ""; // Xóa ô nhập
        chatContent.scrollTop = chatContent.scrollHeight; // Cuộn xuống cuối

        // Hiệu ứng "Đang nhập..." giả
        var loadingId = "loading-" + Date.now();
        chatContent.innerHTML += '<div class="bot-msg text-muted fst-italic" id="' + loadingId + '">AI đang trả lời...</div>';
        chatContent.scrollTop = chatContent.scrollHeight;

        // 2. Gửi AJAX về Servlet
        fetch('ChatServlet', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: 'message=' + encodeURIComponent(message)
        })
        .then(response => response.text())
        .then(data => {
            // Xóa dòng đang nhập
            document.getElementById(loadingId).remove();
            
            // 3. Hiện phản hồi của Bot
            chatContent.innerHTML += '<div class="bot-msg">' + data + '</div>';
            chatContent.scrollTop = chatContent.scrollHeight;
        })
        .catch(error => {
            document.getElementById(loadingId).remove();
            console.error('Lỗi:', error);
            chatContent.innerHTML += '<div class="bot-msg text-danger">Lỗi kết nối server!</div>';
        });
    }
</script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>