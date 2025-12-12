package utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionUtils {
    // Lưu thông tin vào Session
    public static void add(HttpServletRequest req, String name, Object value) {
        HttpSession session = req.getSession();
        session.setAttribute(name, value);
    }

    // Lấy thông tin từ Session
    public static Object get(HttpServletRequest req, String name) {
        HttpSession session = req.getSession();
        return session.getAttribute(name);
    }

    // Xóa session (Đăng xuất)
    public static void invalidate(HttpServletRequest req) {
        HttpSession session = req.getSession();
        session.removeAttribute("username");
        session.invalidate();
    }

    // Kiểm tra đã đăng nhập chưa
    public static boolean isLogin(HttpServletRequest req) {
        return get(req, "username") != null;
    }

    // Lấy tên User đang đăng nhập
    public static String getLoginUsername(HttpServletRequest req) {
        Object username = get(req, "username");
        return username == null ? null : username.toString();
    }
}