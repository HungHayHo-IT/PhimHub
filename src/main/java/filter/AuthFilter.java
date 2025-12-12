package filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.User;
import utils.SessionUtils;

// Filter này sẽ chặn tất cả các đường dẫn bắt đầu bằng /admin/*
@WebFilter(urlPatterns = "/*")
public class AuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        
        String uri = req.getRequestURI();
        User user = (User) SessionUtils.get(req, "user"); 
        
        if (uri.contains("/admin/")) {
            
            if (user == null) {
                resp.sendRedirect(req.getContextPath() + "/LoginServlet?message=Vui lòng đăng nhập để truy cập quản trị");
                return;
            }
            
            else if (!user.isAdmin()) {
                resp.sendRedirect(req.getContextPath() + "/HomeServlet?message=Bạn không có quyền truy cập");
                return;
            }
        }

        
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}