package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UserDao;
import model.User;
import utils.EmailUtils;
import utils.encryption;

/**
 * Servlet implementation class ForgotPasswordServlet
 */
@WebServlet("/ForgotPasswordServlet")
public class ForgotPasswordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ForgotPasswordServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.getRequestDispatcher("/views/user/forgotpassword.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
        UserDao dao = new UserDao();
        
        User user = dao.findByUsernameOrEmail(email);
        
        if (user == null) {
            request.setAttribute("message", "Email này chưa được đăng ký!");
        } else {
            // 2. Tạo mật khẩu mới (Ví dụ: 6 số ngẫu nhiên)
            // Bạn có thể dùng thư viện RandomString hoặc đơn giản như sau:
            String newPassword = String.valueOf((int) (Math.random() * 900000) + 100000); 

            // 3. Cập nhật mật khẩu mới vào Database
            // Cần set lại các thông tin cũ vì hàm update update toàn bộ
            user.setPassword(encryption.toSHA1(newPassword)); 
            dao.update(user);

            // 4. Gửi Email
            String subject = "PhimHub - Khôi phục mật khẩu";
            String body = "Xin chào " + user.getFullName() + ",<br>"
                        + "Mật khẩu mới của bạn là: <b style='color:red; font-size:18px'>" + newPassword + "</b><br>"
                        + "Vui lòng đăng nhập và đổi lại mật khẩu ngay.";
            
            EmailUtils.send(email, subject, body);
            
            request.setAttribute("message", "Mật khẩu mới đã được gửi về Email của bạn!");
        }
        
        request.getRequestDispatcher("/views/user/forgotpassword.jsp").forward(request, response);
	}

}
