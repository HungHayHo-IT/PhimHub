package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UserDao;
import model.User;
import utils.SessionUtils;
import utils.encryption;

@WebServlet("/ProfileServlet")
public class ProfileServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. Kiểm tra đăng nhập
        String userId = (String) SessionUtils.get(request, "userId");
        if (userId == null) {
            response.sendRedirect("LoginServlet");
            return;
        }

        // 2. Lấy thông tin mới nhất từ DB (để đảm bảo dữ liệu đúng)
        UserDao dao = new UserDao();
        User user = dao.findById(userId);

        request.setAttribute("user", user);
        request.getRequestDispatcher("/views/user/profile.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        
        String action = request.getParameter("action");
        String userId = (String) SessionUtils.get(request, "userId");
        
        if (userId == null) {
            response.sendRedirect("LoginServlet");
            return;
        }

        UserDao dao = new UserDao();
        User user = dao.findById(userId); // Lấy user hiện tại lên để chỉnh sửa

        try {
            if ("update_info".equals(action)) {
                // --- TRƯỜNG HỢP 1: CẬP NHẬT THÔNG TIN ---
                String fullname = request.getParameter("fullname");
                user.setFullName(fullname);
                dao.update(user);
                
                request.setAttribute("message", "Cập nhật thông tin thành công!");
                
            } else if ("change_password".equals(action)) {
                // --- TRƯỜNG HỢP 2: ĐỔI MẬT KHẨU ---
                String oldPass = request.getParameter("old_pass");
                String newPass = request.getParameter("new_pass");
                String confirmPass = request.getParameter("confirm_pass");

                // Mã hóa mật khẩu cũ người dùng nhập để so sánh với DB
                String oldPassHash = encryption.toSHA1(oldPass);

                if (!user.getPassword().equals(oldPassHash)) {
                    request.setAttribute("error", "Mật khẩu cũ không đúng!");
                } else if (!newPass.equals(confirmPass)) {
                    request.setAttribute("error", "Mật khẩu xác nhận không khớp!");
                } else {
                    // Mã hóa mật khẩu mới và lưu
                    user.setPassword(encryption.toSHA1(newPass));
                    dao.update(user);
                    request.setAttribute("message", "Đổi mật khẩu thành công!");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi hệ thống: " + e.getMessage());
        }

        // Load lại thông tin user để hiển thị ra JSP
        request.setAttribute("user", user);
        request.getRequestDispatcher("/views/user/profile.jsp").forward(request, response);
    }
}