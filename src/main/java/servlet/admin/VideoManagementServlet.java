package servlet.admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.VideoDao;
import model.Video;

@WebServlet({"/admin/videos", "/admin/video/create", "/admin/video/update", "/admin/video/delete", "/admin/video/reset", "/admin/video/edit"})
public class VideoManagementServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = request.getRequestURI();
        VideoDao dao = new VideoDao();
        
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        if (url.contains("edit")) {
            // Lấy thông tin phim đổ lên form để sửa
            String id = request.getParameter("id");
            Video video = dao.findById(id);
            request.setAttribute("video", video);
            request.getRequestDispatcher("/views/admin/video-edit.jsp").forward(request, response);
        } else if (url.contains("delete")) {
            // Xóa phim
            String id = request.getParameter("id");
            dao.delete(id);
            response.sendRedirect(request.getContextPath() + "/admin/videos");
        } else if (url.contains("reset")) {
            // Xóa trắng form nhập liệu
            request.setAttribute("video", new Video());
            request.getRequestDispatcher("/views/admin/video-edit.jsp").forward(request, response);
        } else {
            // Mặc định: Hiển thị danh sách phim
            List<Video> list = dao.findAll();
            request.setAttribute("videos", list);
            request.getRequestDispatcher("/views/admin/video-overview.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        String url = request.getRequestURI();
        VideoDao dao = new VideoDao();
        
        // Lấy dữ liệu từ form
        String videoId = request.getParameter("videoId");
        String title = request.getParameter("title");
        String poster = request.getParameter("poster");
        String href = request.getParameter("href");
        String description = request.getParameter("description");
        boolean active = request.getParameter("active") != null; // Checkbox được chọn trả về "on", không thì null
        double price = 0;
        try {
            price = Double.parseDouble(request.getParameter("price"));
        } catch (Exception e) {
            price = 0; // Mặc định lỗi thì về 0
        }

        Video video = new Video();
        video.setVideoId(videoId);
        video.setTitle(title);
        video.setPoster(poster);
        video.setHref(href);
        video.setDescription(description);
        video.setActive(active);
        video.setPrice(price);

        if (url.contains("create")) {
            // Kiểm tra trùng ID trước khi thêm
            if (dao.findById(videoId) != null) {
                request.setAttribute("error", "Mã phim này đã tồn tại!");
                request.setAttribute("video", video); // Giữ lại dữ liệu đã nhập
                request.getRequestDispatcher("/views/admin/video-edit.jsp").forward(request, response);
            } else {
                video.setViews(0); // Phim mới thì view = 0
                dao.insert(video);
                request.setAttribute("message", "Thêm phim thành công!");
                // Sau khi thêm, reload lại danh sách
                response.sendRedirect(request.getContextPath() + "/admin/videos"); 
            }
        } else if (url.contains("update")) {
            // Cập nhật
            // Cần lấy lại lượt xem cũ để không bị reset về 0
            Video oldVideo = dao.findById(videoId);
            video.setViews(oldVideo.getViews());
            
            dao.update(video);
            request.setAttribute("message", "Cập nhật thành công!");
            response.sendRedirect(request.getContextPath() + "/admin/videos");
        }
    }
}