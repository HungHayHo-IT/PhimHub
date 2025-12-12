package servlet;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ShareDao;
import model.Share;
import model.User;
import model.Video;
import utils.EmailUtils;
import utils.SessionUtils;

/**
 * Servlet implementation class ShareServlet
 */
@WebServlet("/ShareServlet")
public class ShareServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShareServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1. Kiểm tra đăng nhập (Bắt buộc đăng nhập mới được share để lưu vào DB)
        String userId = (String) SessionUtils.get(request, "userId");
        if (userId == null) {
            response.sendRedirect("LoginServlet");
            return;
        }

        // 2. Lấy thông tin từ form
        String emailTo = request.getParameter("email");
        String videoId = request.getParameter("videoId");
        String videoTitle = request.getParameter("videoTitle"); // Truyền title để gửi mail cho đẹp
        
        // Đường dẫn video (cần cấu hình lại cho đúng domain thực tế, ví dụ localhost)
        String videoLink = request.getRequestURL().toString().replace("ShareServlet", "VideoServlet?action=watch&id=" + videoId);

        if (emailTo != null && !emailTo.isEmpty()) {
            // 3. Gửi Email
            String subject = "PhimHub: Có người muốn chia sẻ phim hay với bạn!";
            String body = "Xin chào,<br>Bạn của bạn vừa xem bộ phim <b>" + videoTitle + "</b> tại PhimHub và muốn chia sẻ với bạn.<br>"
                        + "Click vào đây để xem ngay: <a href='" + videoLink + "'>Xem Phim</a>";
            
            EmailUtils.send(emailTo, subject, body);

            // 4. Lưu vào Database
            ShareDao dao = new ShareDao();
            Share share = new Share();
            share.setEmails(emailTo);
            share.setShareDate(new Date());
            
            User user = new User(); user.setUserId(userId);
            share.setUser(user);
            
            Video video = new Video(); video.setVideoId(videoId);
            share.setVideo(video);
            
            dao.insert(share);
        }
        
        // 5. Quay lại trang chi tiết
        response.sendRedirect("VideoServlet?action=watch&id=" + videoId);
    }
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
