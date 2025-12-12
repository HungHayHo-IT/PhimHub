package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utils.GeminiUtils;

/**
 * Servlet implementation class ChatServlet
 */
@WebServlet("/ChatServlet")
public class ChatServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChatServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        String userMessage = request.getParameter("message");
        PrintWriter out = response.getWriter();

        if (userMessage == null || userMessage.trim().isEmpty()) {
            out.print("Bạn cần hỏi gì nào?");
            return;
        }

        // --- KỸ THUẬT PROMPT ---
        // Ta cộng thêm một đoạn ngữ cảnh để dạy AI biết nó là ai
        String context = "Bạn là nhân viên tư vấn của website xem phim tên là PhimHub. "
                       + "Hãy trả lời ngắn gọn, thân thiện bằng tiếng Việt. "
                       + "Nếu người dùng hỏi về giá, hãy nói là phim thường miễn phí, phim bom tấn có phí rẻ. "
                       + "Câu hỏi của khách hàng là: ";
        
        String finalQuestion = context + userMessage;

        // Gọi AI xử lý
        String botResponse = GeminiUtils.getResponse(finalQuestion);
        
        // Chuyển ký tự xuống dòng của AI (\n) thành thẻ <br> của HTML để hiển thị đẹp
        botResponse = botResponse.replace("\n", "<br>");

        out.print(botResponse);
    }
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
