package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UserDao;

import model.User;
import utils.encryption;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/views/register.jsp").forward(request, response);
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    // 1. Cấu hình tiếng Việt cho request
	    request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");

	    // 2. Lấy dữ liệu từ form
	    String email = request.getParameter("email");
	    String fullname = request.getParameter("fullname");
	    String password = request.getParameter("password");
	    String re_password = request.getParameter("re_password");

	    // Giữ lại giá trị user đã nhập để nếu lỗi thì không phải nhập lại
	    request.setAttribute("email", email);
	    request.setAttribute("fullname", fullname);

	    String message = "";
	    String url = "/views/register.jsp";
	    UserDao dao = new UserDao();

	    // --- BẮT ĐẦU VALIDATE (KIỂM TRA) ---
	    
	    // Check 1: Định dạng Email phải là @gmail.com
	    // Regex: Bắt đầu bằng chữ/số, kết thúc chính xác bằng @gmail.com
	    if (email == null || !email.matches("^[\\w-\\.]+@gmail\\.com$")) {
	        message = "Email phải thuộc hệ thống Gmail (@gmail.com)!";
	    }
	    // Check 2: Độ dài mật khẩu >= 8 ký tự
	    else if (password == null || password.length() < 8) {
	        message = "Mật khẩu phải có ít nhất 8 ký tự!";
	    }
	    // Check 3: Mật khẩu nhập lại phải khớp
	    else if (!password.equals(re_password)) {
	        message = "Mật khẩu nhập lại không khớp!";
	    } 
	    else {
	        // --- XỬ LÝ LOGIC NGHIỆP VỤ ---
	        
	        // Tạo userId từ phần trước của email (ví dụ: abcd@gmail.com -> abcd)
	        String userId = email.substring(0, email.indexOf("@"));
	        
	        // Kiểm tra xem UserID hoặc Email đã tồn tại chưa
	        User existingUser = dao.findByUsernameOrEmail(email); 
	        // Lưu ý: hàm findByUsernameOrEmail của bạn check cả UserId VÀ Email
	        
	        if (existingUser != null) {
	            message = "Tài khoản hoặc Email này đã tồn tại!";
	        } else {
	            try {
	                // Mã hóa mật khẩu
	                String passEncryption = encryption.toSHA1(password);
	                
	                // Tạo đối tượng User
	                User user = new User();
	                user.setUserId(userId);       // Set UserId tự động
	                user.setEmail(email);
	                user.setFullName(fullname);
	                user.setPassword(passEncryption);
	                user.setAdmin(false);         // Mặc định là user thường
	                
	                // Gọi hàm insert bên DAO
	                dao.insert(user);
	                
	                // Đăng ký thành công
	                message = "Đăng ký thành công! Vui lòng đăng nhập.";
	                url = "/views/login.jsp"; // Chuyển hướng sang trang Login
	                
	            } catch (Exception e) {
	                e.printStackTrace();
	                message = "Lỗi hệ thống: " + e.getMessage();
	            }
	        }
	    }

	    // Gửi thông báo ra trang JSP (tên biến là 'message' để khớp với <c:if test="${not empty message}">)
	    request.setAttribute("message", message);
	    
	    // Chuyển hướng (Forward)
	    RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(url);
	    requestDispatcher.forward(request, response);
	}

}
