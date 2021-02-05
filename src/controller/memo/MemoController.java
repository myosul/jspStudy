package controller.memo;

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
import model.memo.dao.MemoDAO;
import model.memo.dto.MemoDTO;

@WebServlet("/memo_servlet/*") // url-mapping
public class MemoController extends HttpServlet {
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
        
        Util util = new Util();
        
        String path = request.getContextPath();
        String url = request.getRequestURI().toString(); // 사용자가 요청한 주소
        
        String page = "/main/main.jsp"; // 포워딩할 주소
        
        // pageNumber-------
        int pageNumber = util.toNumber(request.getParameter("pageNumber"));
        if (pageNumber <= 0) {
            pageNumber = 1;
        }
        // -----------------
        
        if (url.indexOf("write.do") != -1) {
            
            // 세션 체크--------------------
            HttpSession session = request.getSession();
            int cookNo = 0;
            if (session.getAttribute("cookNo") != null) {
                cookNo = (Integer)session.getAttribute("cookNo");
            }
            
            if (cookNo <= 0) { // 로그인을 하지 않았다면
                response.setContentType("text/html; charset=utf-8");
                PrintWriter out = response.getWriter();
                out.println("<script>");
                out.println("alert('로그인 후 이용하세요.');");
                out.println("location.href='" + path + "/member_servlet/login.do';");
                out.println("</script>");
                return;
            }
            // ------------------------------
            
            request.setAttribute("pageNumber", pageNumber);
            request.setAttribute("menu_gubun", "memo_write");
            
            RequestDispatcher rd = request.getRequestDispatcher(page);
            rd.forward(request, response);
            
        } else if (url.indexOf("writeProc.do") != -1) {
            
            // 세션 체크--------------------
            HttpSession session = request.getSession();
            int cookNo = 0;
            if (session.getAttribute("cookNo") != null) {
                cookNo = (Integer)session.getAttribute("cookNo");
            }
            
            if (cookNo <= 0) { // 로그인을 하지 않았다면
                response.setContentType("text/html; charset=utf-8");
                PrintWriter out = response.getWriter();
                out.println("<script>");
                out.println("alert('로그인 후 이용하세요.');");
                out.println("location.href='" + path + "/member_servlet/login.do';");
                out.println("</script>");
                return;
            }
            // ------------------------------
            
            int memo_writer_no = cookNo;
            String memo_subject = util.toEntityCode(request.getParameter("memo_subject"));
            String memo_content = util.toEntityCode(request.getParameter("memo_content"));
            
            MemoDTO dto = new MemoDTO();
            dto.setMemo_writer_no(memo_writer_no);
            dto.setMemo_subject(memo_subject);
            dto.setMemo_content(memo_content);
            
            MemoDAO dao = new MemoDAO();
            int result = dao.setInsert(dto);
            
            if (result > 0) {
                System.out.println("등록되었습니다.");
            } else {
                System.out.println("결과코드 : " + result);
            }
            
        } else if (url.indexOf("list.do") != -1) {
            
            HttpSession session = request.getSession();
            int cookNo = 0;
            if (session.getAttribute("cookNo") != null) {
                cookNo = (Integer)session.getAttribute("cookNo");
            }
            
            MemoDAO dao = new MemoDAO();
            
            // 페이징 -----------------------
            int pageSize = 3; // 한페이지 당 보여질 행 개수
            int blockSize = 5; // 한 블록 당 보여질 페이지 개수
            int totalRecord = dao.getTotalRecord(cookNo); // 총 행 개수
            int recordNum = totalRecord - pageSize * (pageNumber - 1); // 화면상에 보여질 일련번호 (행 개수 기준)
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
            
            // 메모리스트 -------------------
            ArrayList<MemoDTO> list = dao.getSelectAll(cookNo, startRecord, lastRecord);
            request.setAttribute("list", list);
            // ------------------------------
            
            request.setAttribute("pageNumber", pageNumber);
            
            String temp = "/memo/list.jsp";
            RequestDispatcher rd = request.getRequestDispatcher(temp);
            rd.forward(request, response);
            
        }
    }

}
