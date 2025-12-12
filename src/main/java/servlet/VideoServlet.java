package servlet;

import java.io.IOException;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.FavoriteDao;
import dao.OrderDao; // <-- Nhớ import OrderDao
import dao.VideoDao;
import model.Favorite;
import model.User;
import model.Video;
import utils.SessionUtils;

/**
 * Servlet implementation class VideoServlet
 */
@WebServlet("/VideoServlet")
public class VideoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public VideoServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userId = (String) SessionUtils.get(request, "userId");
        String action = request.getParameter("action");
        String id = request.getParameter("id");
        
        if (action == null) action = "watch";
        
        if (action.equals("watch")) {
        	if (userId == null) {
                response.sendRedirect("LoginServlet");
                return;
            }
        	
            VideoDao dao = new VideoDao();
            Video video = dao.findById(id);
            
            if (video != null) {
                boolean isGranted = false; // Cờ kiểm tra quyền xem

                // 1. Kiểm tra phim có phí không
                if (video.getPrice() > 0) {
                    // Nếu chưa đăng nhập -> coi như chưa mua
                    if (userId == null) {
                        isGranted = false;
                    } else {
                        // Đã đăng nhập -> Check OrderDao
                        OrderDao orderDao = new OrderDao();
                        isGranted = orderDao.checkUserHasBought(userId, video.getVideoId());
                        
                        // Nếu là Admin thì luôn được xem (tùy chọn, thêm vào cho tiện test)
                        // User currentUser = (User) SessionUtils.get(request, "user");
                        // if (currentUser != null && currentUser.isAdmin()) isGranted = true;
                    }
                } else {
                    // Phim miễn phí -> Luôn được xem
                    isGranted = true;
                }

                // 2. Xử lý tăng view (Chỉ tăng nếu được phép xem)
                if (isGranted) {
                    video.setViews(video.getViews() + 1);
                    dao.update(video);
                }

                // 3. Gửi dữ liệu sang JSP
                request.setAttribute("video", video);
                request.setAttribute("isGranted", isGranted); // Gửi cờ này sang detail.jsp

                // Xử lý nút Like (giữ nguyên code cũ)
                boolean flagLike = false;
                if (userId != null) {
                    FavoriteDao favDao = new FavoriteDao();
                    Favorite favorite = favDao.findByUserIdAndVideoId(userId, video.getVideoId());
                    flagLike = (favorite != null);
                }
                request.setAttribute("flagLike", flagLike);
                
                // 4. LUÔN CHUYỂN VỀ TRANG DETAIL (Không chuyển checkout ở đây nữa)
                RequestDispatcher rd = request.getRequestDispatcher("/views/user/detail.jsp");
                rd.forward(request, response);
            }
        }
        else if(action.equals("like")) {
        	// Logic like giữ nguyên
            if (userId == null) {
                response.sendRedirect("LoginServlet");
                return;
            }
            
            FavoriteDao favDao = new FavoriteDao();
            Favorite favorite = favDao.findByUserIdAndVideoId(userId, id);

            if (favorite == null) {
                Video video = new Video();
                video.setVideoId(id);
                
                User user = new User();
                user.setUserId(userId);
                
                Favorite newFav = new Favorite();
                newFav.setUser(user);
                newFav.setVideo(video);
                newFav.setLikeDate(new Date());
                
                favDao.insert(newFav);
            } else {
                favDao.delete(favorite.getFavoriteId());
            }
            
            response.sendRedirect("VideoServlet?action=watch&id=" + id);
        }
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}