package email.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.Util;
import email.model.dto.EmailDTO;
import email.service.EmailService;

@WebServlet("/email_servlet/*")
public class EmailController extends HttpServlet {
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
        PrintWriter out = response.getWriter();
        
        Util util = new Util();
        
        // dateMap----------
        int[] date = util.getDateTime();
        Map<String, Integer> dateMap = new HashMap<>();
        dateMap.put("now_y", date[0]);
        dateMap.put("now_m", date[1]);
        dateMap.put("now_d", date[2]);
        
        request.setAttribute("dateMap", dateMap);
        // -----------------
        
        // serverInfo-------
        String[] serverInfo = util.getServerInfo(request); // request.getContectPath();
        String refer = serverInfo[0];
        String path = serverInfo[1];
        String url = serverInfo[2];
        String uri = serverInfo[3];
        String ip = serverInfo[4];
        // String ip6 = serverInfo[5];
        
        request.setAttribute("ip", ip);
        // -----------------
        
        // 세션 ------------
        String[] sessionArray = util.sessionCheck(request);
        int cookNo = Integer.parseInt(sessionArray[0]);
        String cookId = sessionArray[1];
        String cookName = sessionArray[2];
        
        request.setAttribute("cookId", cookId);
        request.setAttribute("cookName", cookName);
        // ------------------
        
        String page = "/main/main.jsp"; // 포워딩할 주소
        
        if (url.indexOf("index.do") != -1) {
            
            // menu_gubun 변수에 email_index 을 할당
            request.setAttribute("menu_gubun", "email_index");
            
            // page 로 request 값을 포함하여 포워딩(response) 시킨다.
            RequestDispatcher rd = request.getRequestDispatcher(page);
            rd.forward(request, response);
            
        } else if (url.indexOf("add.do") != -1) {
            
            request.setAttribute("menu_gubun", "email_add");
            page = "/email/add.jsp";
            RequestDispatcher rd = request.getRequestDispatcher(page);
            rd.forward(request, response);
            
        } else if (url.indexOf("addProc.do") != -1) {
            
            String fromName = util.toEntityCode(request.getParameter("fromName"));
            String fromEmail = util.toEntityCode(request.getParameter("fromEmail"));
            String toEmail = util.toEntityCode(request.getParameter("toEmail"));
            String subject = util.toEntityCode(request.getParameter("subject"));
            String content = util.toEntityCode(request.getParameter("content"));
            
            EmailDTO dto = new EmailDTO();
            dto.setFromName(fromName);
            dto.setFromEmail(fromEmail);
            dto.setToEmail(toEmail);
            dto.setSubject(subject);
            dto.setContent(content);
            
            System.out.println(dto.toString());
            
            EmailService service = new EmailService();
            try {
                service.mailSender(dto);
            } catch (Exception e) {
                e.printStackTrace();
            }
            
        }
        
    }

}
