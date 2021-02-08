package controller.board;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.Util;
import common.UtilBoard;
import model.board.dao.BoardDAO;
import model.board.dto.BoardDTO;

@WebServlet("/board_servlet/*")
public class BoardController extends HttpServlet {
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
        
        String temp;
        
        UtilBoard util = new UtilBoard();
        
        // dateMap----------
        int[] date = util.getDateTime();
        Map<String, Integer> dateMap = new HashMap<>();
        dateMap.put("now_y", date[0]);
        dateMap.put("now_m", date[1]);
        dateMap.put("now_d", date[2]);
        
        request.setAttribute("dateMap", dateMap);
        // -----------------
        
        String serverInfo[] = util.getServerInfo(request); // request.getContectPath();
        String refer = serverInfo[0];
        String path = serverInfo[1];
        String url = serverInfo[2];
        String uri = serverInfo[3];
        String ip = serverInfo[4];
        // String ip6 = serverInfo[5];
        
        temp = request.getParameter("tbl");
        String board_tbl = util.tblCheck(temp, "freeboard");
        
        // board_no
        int board_no = util.toNumber(request.getParameter("board_no"));
        
        // pageNumber-------
        int pageNumber = util.toNumber(request.getParameter("pageNumber"));
        if (pageNumber <= 0) {
            pageNumber = 1;
        }
        // -----------------
        
        // 검색데이터-------
        String search_option = request.getParameter("search_option");
        String search_data = request.getParameter("search_data");
        String[] searchArray = util.searchCheck(search_option, search_data);
        search_option = searchArray[0];
        search_data = searchArray[1];
        // -----------------
        
        String[] sessionArray = util.sessionCheck(request);
        int cookNo = Integer.parseInt(sessionArray[0]);
        String cookId = sessionArray[1];
        String cookName = sessionArray[2];
        
        request.setAttribute("dateMap", dateMap);
        request.setAttribute("ip", ip);
        request.setAttribute("board_tbl", board_tbl);
        request.setAttribute("pageNumber", pageNumber);
        request.setAttribute("board_no", board_no);
        request.setAttribute("search_option", search_option);
        request.setAttribute("search_data", search_data);
        
        BoardDAO dao = new BoardDAO();
        BoardDTO dto = new BoardDTO();
        
        String page = "/main/main.jsp"; // 포워딩할 주소
        
        if (url.indexOf("index.do") != -1) {
            
            // menu_gubun 변수에 board_index 을 할당
            request.setAttribute("menu_gubun", "board_index");
            
            // page 로 request 값을 포함하여 포워딩(response) 시킨다.
            RequestDispatcher rd = request.getRequestDispatcher(page);
            rd.forward(request, response);
            
        } else if (url.indexOf("add.do") != -1) {
            
            request.setAttribute("menu_gubun", "board_add");
            
            page = "/board/add.jsp";
            RequestDispatcher rd = request.getRequestDispatcher(page);
            rd.forward(request, response);
            
        } else if (url.indexOf("addProc.do") != -1) {
            
            String board_writer = request.getParameter("board_writer");
            String board_email = request.getParameter("board_email");
            String board_passwd = request.getParameter("board_passwd");
            String board_subject = request.getParameter("board_subject");
            String board_content = request.getParameter("board_content");
            String board_notice = request.getParameter("board_notice");
            
            int notice_no;
            if (board_notice == null || board_notice.trim().equals("") || !board_notice.equals("T")) {
                notice_no = 0;
            } else {
                notice_no = dao.getMaxNoticeNo(board_tbl) + 1;
            }
            
            String board_secret = request.getParameter("board_secret");
            if (board_secret == null || board_secret.trim().equals("") || !board_secret.equals("T")) {
                board_secret = "F";
            } else {
                board_secret = "T";
            }
            
            int board_num = dao.getMaxNum() + 1;
            int board_ref_no = dao.getMaxRefNo() + 1; // 글 그룹을 의미 = 쿼리를 실행시켜서 가장 큰 ref 값을 가져온 후 +1을 해준다.
            int board_step_no = 1;
            int board_level_no = 1;
            int board_parent_no = 0;
            
            int board_hit = 0;
            
            dto.setBoard_no(board_no);
            dto.setBoard_num(board_num);
            dto.setBoard_tbl(board_tbl);
            dto.setBoard_writer(board_writer);
            dto.setBoard_subject(board_subject);
            dto.setBoard_content(board_content);
            dto.setBoard_email(board_email);
            dto.setBoard_passwd(board_passwd);
            
            dto.setBoard_ref_no(board_ref_no);
            dto.setBoard_step_no(board_step_no);
            dto.setBoard_level_no(board_level_no);
            dto.setBoard_parent_no(board_parent_no);
            dto.setBoard_hit(board_hit);
            
            dto.setBoard_ip(ip);
            dto.setMember_no(cookNo);
            
            dto.setBoard_notice_no(notice_no);
            dto.setBoard_secret(board_secret);
            
            int result = dao.setInsert(dto);
            
            if (result > 0) { // 성공
                System.out.println("-- 글 등록 성공 --");
                return;
            } else { // 실패
                System.out.println("-- 글 등록 실패 --");
                return;
            }
            
        }
	    
    }

}
