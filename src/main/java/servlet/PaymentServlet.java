package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.OrderDao;
import dao.VideoDao; // Cần thêm cái này để nếu lỗi thì load lại info phim
import model.Video; // Cần thêm
import utils.SessionUtils;

@WebServlet("/PaymentServlet")
public class PaymentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("HomeServlet");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        
        String userId = (String) SessionUtils.get(request, "userId");
        
        // Nếu chưa đăng nhập thì đuổi về
        if (userId == null) {
            response.sendRedirect("LoginServlet");
            return;
        }

        String videoId = request.getParameter("videoId");
        String priceStr = request.getParameter("price");
        
        // Lấy thông tin thẻ ngân hàng từ form
        String cardHolder = request.getParameter("cardHolder");
        String cardNumber = request.getParameter("cardNumber").replace(" ", ""); // Xóa dấu cách
        String cvv = request.getParameter("cvv");

        // --- VALIDATION (KIỂM TRA GIẢ LẬP) ---
        String error = null;

        // 1. Kiểm tra số thẻ (Phải đủ 16 số)
        if (cardNumber.length() != 16) {
            error = "Số thẻ không hợp lệ! Vui lòng kiểm tra lại (đủ 16 số).";
        }
        // 2. Kiểm tra CVV (Phải là số)
        else if (!cvv.matches("\\d{3}")) {
            error = "Mã bảo mật (CVV) phải là 3 chữ số!";
        }
        // 3. Giả sử thẻ hết tiền (Nếu nhập số thẻ toàn số 0)
        else if (cardNumber.equals("0000000000000000")) {
            error = "Thẻ của bạn không đủ số dư!";
        }

        // --- NẾU CÓ LỖI ---
        if (error != null) {
            // Phải load lại thông tin Video để hiển thị lại trang checkout (vì checkout.jsp cần object video)
            VideoDao vDao = new VideoDao();
            Video video = vDao.findById(videoId);
            request.setAttribute("video", video);
            
            request.setAttribute("error", error); // Gửi lỗi sang JSP
            request.getRequestDispatcher("/views/user/checkout.jsp").forward(request, response);
            return;
        }

        // --- NẾU HỢP LỆ -> XỬ LÝ THANH TOÁN ---
        try {
            // Giả lập độ trễ ngân hàng (Chờ 2 giây cho nó "ngầu")
            Thread.sleep(2000); 
            
            double price = Double.parseDouble(priceStr);
            
            // Lưu vào Database
            OrderDao dao = new OrderDao();
            dao.createOrder(userId, videoId, price);

            // Chuyển hướng về trang xem phim (Thêm tham số success để báo thành công nếu muốn)
            response.sendRedirect("VideoServlet?action=watch&id=" + videoId);
            
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("HomeServlet"); // Lỗi hệ thống thì về trang chủ
        }
    }
}