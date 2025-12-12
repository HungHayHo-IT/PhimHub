package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.VideoDao;
import model.Video;
import utils.SessionUtils;

@WebServlet("/CheckoutServlet")
public class CheckoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. Kiểm tra đăng nhập (Bắt buộc đăng nhập mới được vào trang thanh toán)
        String userId = (String) SessionUtils.get(request, "userId");
        if (userId == null) {
            response.sendRedirect("LoginServlet?message=Vui lòng đăng nhập để thanh toán!");
            return;
        }

        // 2. Lấy ID phim từ đường dẫn (URL)
        String id = request.getParameter("id");
        
        // 3. Lấy thông tin phim từ Database
        VideoDao dao = new VideoDao();
        Video video = dao.findById(id);

        if (video != null) {
            // 4. Gửi thông tin phim sang trang checkout.jsp để hiển thị (Tên phim, Giá tiền...)
            request.setAttribute("video", video);
            request.getRequestDispatcher("/views/user/checkout.jsp").forward(request, response);
        } else {
            // Nếu không tìm thấy phim thì về trang chủ
            response.sendRedirect("HomeServlet");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}