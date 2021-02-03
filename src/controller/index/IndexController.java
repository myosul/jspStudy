package controller.index;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/index.do") // url-mapping
public class IndexController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doProc(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doProc(request, response);
    }
    
    protected void doProc(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=utf-8");
        
        String path = request.getContextPath();
        String url = request.getRequestURI().toString(); // 사용자가 요청한 주소
        
        request.setAttribute("menu_gubun", "index"); // 포워딩할 페이지에 값 넘기기
        String page = "/main/main.jsp"; // 포워딩할 주소
        RequestDispatcher rd = request.getRequestDispatcher(page);
        rd.forward(request, response);
        
    }
}
