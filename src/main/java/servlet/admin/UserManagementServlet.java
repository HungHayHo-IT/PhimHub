package servlet.admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UserDao;
import model.User;
import utils.SessionUtils;

@WebServlet({"/admin/users", "/admin/user/delete", "/admin/user/toggle"})
public class UserManagementServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = request.getRequestURI();
        UserDao dao = new UserDao();
        request.setCharacterEncoding("UTF-8");

        if (url.contains("delete")) {
            // --- CHỨC NĂNG XÓA USER ---
            String id = request.getParameter("id");
            String currentUserId = (String) SessionUtils.get(request, "userId");

            // Không cho phép tự xóa chính mình
            if (id != null && id.equals(currentUserId)) {
                request.setAttribute("error", "Bạn không thể xóa chính tài khoản mình đang đăng nhập!");
            } else {
                try {
                    dao.delete(id);
                    request.setAttribute("message", "Xóa người dùng thành công!");
                } catch (Exception e) {
                    e.printStackTrace();
                    request.setAttribute("error", "Không thể xóa! User này đã có dữ liệu tương tác (Like/Share).");
                }
            }
        }
        
        // Mặc định: Luôn load danh sách User
        List<User> list = dao.findAll();
        request.setAttribute("users", list);
        request.getRequestDispatcher("/views/admin/user-overview.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = request.getRequestURI();
        request.setCharacterEncoding("UTF-8");

        if (url.contains("toggle")) {
            // --- CHỨC NĂNG BẬT/TẮT QUYỀN ADMIN ---
            UserDao dao = new UserDao();
            String userId = request.getParameter("userId");
            String currentUserId = (String) SessionUtils.get(request, "userId");

            // Không cho phép tự tước quyền admin của chính mình (để tránh bị khóa)
            if (userId.equals(currentUserId)) {
                // Nếu tự sửa mình thì bỏ qua, không làm gì cả
                response.sendRedirect(request.getContextPath() + "/admin/users?error=cant_change_self");
                return;
            }

            User user = dao.findById(userId);
            if (user != null) {
                // Đảo ngược trạng thái Admin (Nếu đang True -> False, False -> True)
                boolean newStatus = !user.isAdmin();
                user.setAdmin(newStatus);
                
                dao.update(user); // Cập nhật vào DB
            }
            
            // Load lại trang danh sách
            response.sendRedirect(request.getContextPath() + "/admin/users");
        }
    }
}