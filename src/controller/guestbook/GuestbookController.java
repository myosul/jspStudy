package controller.guestbook;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.Util;
import model.guestbook.dao.GuestbookDAO;
import model.guestbook.dto.GuestbookDTO;

@WebServlet("/guestbook_servlet/*") // url-mapping
public class GuestbookController extends HttpServlet {
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
        
        String page = "/main/main.jsp"; // 포워딩할 주소
        
        // pageNumber-------
        int pageNumber = Util.toNumber(request.getParameter("pageNumber"));
        if (pageNumber <= 0) {
            pageNumber = 1;
        }
        // -----------------
        
        if (url.indexOf("write.do") != -1) {
            
            request.setAttribute("menu_gubun", "guestbook_write");
            
            RequestDispatcher rd = request.getRequestDispatcher(page);
            rd.forward(request, response);
            
        } else if (url.indexOf("writeProc.do") != -1) {
            
            String guestbook_writer_name = Util.toEntityCode(request.getParameter("guestbook_writer_name"));
            String guestbook_writer_email = Util.toEntityCode(request.getParameter("guestbook_writer_email"));
            String guestbook_passwd = Util.toNumberAndAlphabet(request.getParameter("guestbook_passwd"));
            String guestbook_content = Util.toEntityCode(request.getParameter("guestbook_content"));
            
            GuestbookDTO dto = new GuestbookDTO();
            dto.setGuestbook_writer_name(guestbook_writer_name);
            dto.setGuestbook_writer_email(guestbook_writer_email);
            dto.setGuestbook_passwd(guestbook_passwd);
            dto.setGuestbook_content(guestbook_content);
            
            GuestbookDAO dao = new GuestbookDAO();
            int result = dao.setInsert(dto);
            
            if (result > 0) { // 작성 성공
                String temp = "";
                temp = path + "/guestbook_servlet/list.do";
                response.sendRedirect(temp);
            } else { // 작성 실패
                response.setContentType("text/html; charset=utf-8");
                PrintWriter out = response.getWriter();
                out.println("<script>");
                out.println("alert('정상적으로 처리되지 않았습니다.');");
                out.println("history.back();");
                out.println("</script>");
                return;
            }
            
        } else if (url.indexOf("list.do") != -1) {
            
            GuestbookDAO dao = new GuestbookDAO(); // DAO 선언
            
            // 페이징 -----------------------
            int pageSize = 3; // 한페이지 당 보여질 행 개수
            int blockSize = 5; // 한 블록 당 보여질 페이지 개수
            int totalRecord = dao.getTotalRecord(); // 총 행 개수
            int recordNum = (totalRecord - pageSize * (pageNumber - 1)) + 1; // 화면상에 보여질 일련번호 (행 개수 기준)
            int startRecord = pageSize * (pageNumber -1) + 1; // 시작 행
            int lastRecord = pageSize * pageNumber; // 마지막 행
            
            int totalPage = 0; // 총 페이지
            int startPage = 1; // 시작 페이지
            int lastPage = 1; // 마지막 페이지
            if (totalRecord > 0) {
                totalPage = totalRecord / pageSize + (totalRecord % pageSize == 0 ? 0 : 1);
                startPage = (pageNumber / blockSize - (pageNumber % blockSize != 0 ? 0 : 1)) * blockSize + 1;
                lastPage = startPage + blockSize - 1;
                if (lastPage > totalPage) {
                    lastPage = totalPage;
                }
            }
            request.setAttribute("pageSize", pageSize);
            request.setAttribute("blockSize", blockSize);
            request.setAttribute("totalRecord", totalRecord);
            request.setAttribute("recordNum", recordNum);
            request.setAttribute("startRecord", startRecord);
            request.setAttribute("lastRecord", lastRecord);
            request.setAttribute("totalPage", totalPage);
            request.setAttribute("startPage", startPage);
            request.setAttribute("lastPage", lastPage);
            // ------------------------------
            
            // 방명록 리스트 ----------------
            ArrayList<GuestbookDTO> list = dao.getSelectAll(startRecord, lastRecord);
            
            request.setAttribute("list", list);
            request.setAttribute("menu_gubun", "guestbook_list");
            // ------------------------------
            
            request.setAttribute("pageNumber", pageNumber);
            
            RequestDispatcher rd = request.getRequestDispatcher(page);
            rd.forward(request, response);
            
        }
    }

}
