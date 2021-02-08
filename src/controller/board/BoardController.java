package controller.board;

import java.io.IOException;
import java.util.ArrayList;
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
        
        // board_tbl
        String board_tbl = util.tblCheck(request.getParameter("tbl"), "freeboard");
        request.setAttribute("board_tbl", board_tbl);
        
        // board_no
        int board_no = util.toNumber(request.getParameter("board_no"));
        request.setAttribute("board_no", board_no);
        
        // pageNumber-------
        int pageNumber = util.toNumber(request.getParameter("pageNumber"));
        if (pageNumber <= 0) {
            pageNumber = 1;
        }
        
        request.setAttribute("pageNumber", pageNumber);
        // -----------------
        
        // 검색데이터-------
        String search_option = request.getParameter("search_option");
        String search_data = request.getParameter("search_data");
        String[] searchArray = util.searchCheck(search_option, search_data);
        search_option = searchArray[0];
        search_data = searchArray[1];
        
        request.setAttribute("search_option", search_option);
        request.setAttribute("search_data", search_data);
        // -----------------
        
        String[] sessionArray = util.sessionCheck(request);
        int cookNo = Integer.parseInt(sessionArray[0]);
        String cookId = sessionArray[1];
        String cookName = sessionArray[2];
        
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
            
            int board_notice_no;
            if (board_notice == null || board_notice.trim().equals("") || !board_notice.equals("T")) {
                board_notice_no = 0;
            } else {
                board_notice_no = dao.getMaxNoticeNo(board_tbl) + 1;
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
            
            dto.setBoard_notice_no(board_notice_no);
            dto.setBoard_secret(board_secret);
            
            int result = dao.setInsert(dto);
            
            if (result > 0) { // 성공
                System.out.println("-- 글 등록 성공 --");
                return;
            } else { // 실패
                System.out.println("-- 글 등록 실패 --");
                return;
            }
            
        } else if (url.indexOf("list.do") != -1) {
            
            // 페이징 -----------------------
            int pageSize = 5; // 한페이지 당 보여질 행 개수
            int blockSize = 5; // 한 블록 당 보여질 페이지 개수
            int totalRecord = dao.getTotalRecord(); // 총 행 개수
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
            
            // 게시글 리스트 ----------------
            ArrayList<BoardDTO> list = dao.getSelectAll(startRecord, lastRecord);
            
            request.setAttribute("list", list);
            // ------------------------------
            
            // pageNumber
            request.setAttribute("pageNumber", pageNumber);
            
            // 검색데이터--------------------
            request.setAttribute("search_option", search_option);
            request.setAttribute("search_data", search_data);
            // ------------------------------
            
            temp = "/board/list.jsp";
            RequestDispatcher rd = request.getRequestDispatcher(temp);
            rd.forward(request, response);
            
        }
	    
    }

}
