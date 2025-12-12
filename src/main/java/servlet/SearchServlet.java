package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.VideoDao;
import model.Video;

@WebServlet("/SearchServlet")
public class SearchServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Cấu hình trả về tiếng Việt
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        String query = request.getParameter("query");
        PrintWriter out = response.getWriter();

        if (query != null && !query.trim().isEmpty()) {
            VideoDao dao = new VideoDao();
            List<Video> videos = dao.findByName(query);

            if (!videos.isEmpty()) {
                // Trả về danh sách kết quả dạng HTML
                out.println("<div class='list-group'>");
                for (Video v : videos) {
                    out.println("<a href='VideoServlet?action=watch&id=" + v.getVideoId() + "' class='list-group-item list-group-item-action bg-dark text-white border-secondary d-flex align-items-center'>");
                    out.println("<img src='" + v.getPoster() + "' width='40' height='60' class='me-3 rounded' style='object-fit:cover;'>");
                    out.println("<div>");
                    out.println("<h6 class='mb-0 fw-bold'>" + v.getTitle() + "</h6>");
                    if (v.getPrice() > 0) {
                        out.println("<small class='text-warning'><i class='fas fa-tag me-1'></i>" + String.format("%,.0f", v.getPrice()) + " đ</small>");
                    } else {
                        out.println("<small class='text-success'>Miễn phí</small>");
                    }
                    out.println("</div>");
                    out.println("</a>");
                }
                out.println("</div>");
            } else {
                out.println("<div class='p-3 text-muted small'>Không tìm thấy phim nào...</div>");
            }
        }
    }
}