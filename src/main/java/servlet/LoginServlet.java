package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UserDao;
import model.User;
import utils.CookiesUtils;
import utils.SessionUtils;
import utils.encryption;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public LoginServlet() {
		super();
	}

	// ------------------------------------------------------------------
	// HÀM doGet: CHỈ DÙNG ĐỂ HIỂN THỊ TRANG LOGIN
	// (Tuyệt đối không check pass ở đây)
	// ------------------------------------------------------------------
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		// 1. Chỉ lấy Cookie để điền sẵn vào ô input cho tiện (nếu có)
		String username = CookiesUtils.get("username", request);
		String password = CookiesUtils.get("password", request);

		// 2. Gửi dữ liệu này sang JSP để hiện lên
		request.setAttribute("username", username);
		request.setAttribute("password", password);
		
		// 3. Chuyển hướng sang trang giao diện. 
		// Lúc này biến "message" chưa có gì cả => Không hiện lỗi đỏ.
		request.getRequestDispatcher("/views/login.jsp").forward(request, response);
	}

	// ------------------------------------------------------------------
	// HÀM doPost: CHỈ DÙNG KHI NGƯỜI DÙNG BẤM NÚT "ĐĂNG NHẬP"
	// (Lúc này mới check pass)
	// ------------------------------------------------------------------
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		// 1. Lấy dữ liệu người dùng nhập từ form
		String username = request.getParameter("username");
		String pass = request.getParameter("password");
		
		String remember = request.getParameter("remember");
		
		String passEncryption= encryption.toSHA1(pass);
		// 2. Bây giờ mới gọi DAO để kiểm tra
		UserDao dao = new UserDao();
		User user = dao.findByUsernameOrEmail(username);

		// 3. Xử lý kết quả
		if (user != null && user.getPassword().equals(passEncryption)) {
			// --- ĐĂNG NHẬP THÀNH CÔNG ---
			SessionUtils.add(request, "username", user.getFullName());
			SessionUtils.add(request, "userId", user.getUserId());
			SessionUtils.add(request, "user", user);

			// Xử lý lưu/xóa Cookie
			if (remember != null) {
				CookiesUtils.add("username", username, 24, response);
				CookiesUtils.add("password", pass, 24, response);
			} else {
				CookiesUtils.add("username", null, 0, response);
				CookiesUtils.add("password", null, 0, response);
			}

			// Về trang chủ
			response.sendRedirect("HomeServlet");
		} else {
			// --- ĐĂNG NHẬP THẤT BẠI ---
			// Lúc này mới set biến "message" => JSP mới hiện lỗi đỏ
			request.setAttribute("message", "Sai tài khoản/email hoặc mật khẩu!");
			
			// Giữ lại tên đăng nhập để user đỡ phải gõ lại
			request.setAttribute("username", username);
			
			// Quay lại trang Login
			request.getRequestDispatcher("/views/login.jsp").forward(request, response);
		}
	}
}