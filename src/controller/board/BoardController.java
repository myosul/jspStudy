package controller.board;

import java.io.IOException;
import java.io.PrintWriter;
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
import model.board.dto.BoardCommentDTO;

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
        PrintWriter out = response.getWriter();
        
        String temp; // 공용 임시변수
        
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
        
        request.setAttribute("search_data", search_data);
        // -----------------
        
        // 세션 ------------
        String[] sessionArray = util.sessionCheck(request);
        int cookNo = Integer.parseInt(sessionArray[0]);
        String cookId = sessionArray[1];
        String cookName = sessionArray[2];
        
        request.setAttribute("cookId", cookId);
        request.setAttribute("cookName", cookName);
        // ------------------
        
        BoardDAO dao = new BoardDAO();
        BoardDTO dto = new BoardDTO();
        BoardCommentDTO cDto = new BoardCommentDTO();
        
        // board_tbl--------
        String board_tbl = util.tblCheck(request.getParameter("board_tbl"), "freeboard");
        String board_tbl_name = dao.selectBoardTypeName(board_tbl);
        if (board_tbl_name.equals("")) {
            out.println("<script>");
            out.println("alert('없거나 사용이 중지된 게시판 이름입니다.');");
            out.println("history.back();");
            out.println("</script>");
            return;
        }
        request.setAttribute("board_tbl", board_tbl);
        request.setAttribute("board_tbl_name", board_tbl_name);
        //------------------
        
        String page = "/main/main.jsp"; // 포워딩할 주소
        
        if (url.indexOf("index.do") != -1) {
            
            // menu_gubun 변수에 board_index 을 할당
            request.setAttribute("menu_gubun", "board_index");
            
            // page 로 request 값을 포함하여 포워딩(response) 시킨다.
            RequestDispatcher rd = request.getRequestDispatcher(page);
            rd.forward(request, response);
            
        } else if (url.indexOf("add.do") != -1 || url.indexOf("reply.do") != -1) {
            
            request.setAttribute("menu_gubun", "board_add");
            
            if (board_no > 0) { // 답변이면
                dto = dao.getSelect(board_no);
                
                temp = "[" + dto.getBoard_writer() + "]님이 작성한 글입니다.\n";
                temp += dto.getBoard_content();
                temp = temp.replace("\n", "\n> ");
                temp += "\n------------------------------------------------\n";
                
                dto.setBoard_content(temp);
                request.setAttribute("dto", dto);
            }
            
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
            int board_ref_no = dao.getMaxRefNo() + 1;
            int board_step_no = 1;
            int board_level_no = 1;
            int board_parent_no = 0;
            
            if (board_no > 0) {
                BoardDTO dto2 = dao.getSelect(board_no);
                dao.setUpdateReLevel(dto2); // 답변글.. // 부모 글코다 큰 re_level의 값을 전부 1씩 증가시켜준다.
                board_ref_no = dto2.getBoard_ref_no();
                board_step_no = dto2.getBoard_step_no() + 1;
                board_level_no = dto2.getBoard_level_no() + 1;
                board_parent_no = dto2.getBoard_no();
            }
            
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
            } else { // 실패
                System.out.println("-- 글 등록 실패 --");
                out.println("<script>");
                out.println("alert('등록하지 못했습니다.');");
                out.println("select_page('1');");
                out.println("</script>");
            }
            
        } else if (url.contains("list.do")) {
            
            // 페이징 -----------------------
            int pageSize = 10; // 한페이지 당 보여질 행 개수
            int blockSize = 10; // 한 블록 당 보여질 페이지 개수
            int totalRecord = dao.getTotalRecord(board_tbl, search_option, search_data); // 총 행 개수
            
            int[] pagerArray = util.pager(pageSize, blockSize, totalRecord, pageNumber);
            int recordNum = pagerArray[0];
            int startRecord = pagerArray[1];
            int lastRecord = pagerArray[2];
            int totalPage = pagerArray[3];
            int startPage = pagerArray[4];
            int lastPage = pagerArray[5];
            
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
            ArrayList<BoardDTO> list = dao.getSelectAll(startRecord, lastRecord, board_tbl, search_option, search_data);
            
            request.setAttribute("list", list);
            // ------------------------------
            
            // pageNumber
            request.setAttribute("pageNumber", pageNumber);
            
            // 검색데이터--------------------
            request.setAttribute("search_option", search_option);
            request.setAttribute("search_data", search_data);
            // ------------------------------
            
            request.setAttribute("menu_gubun", "board_list");
            
            page = "/board/list.jsp";
            RequestDispatcher rd = request.getRequestDispatcher(page);
            rd.forward(request, response);
            
        } else if (url.indexOf("view.do") != -1) {
            
            dao.setUpdateHit(board_no);
            dto = dao.getSelect(board_no);
            
            // String board_content = dto.getBoard_content().replace("\n", "<br>");
            // dto.setBoard_content(board_content);
            
            String imsiPage = "viewPage";
            if (dto.getBoard_secret().equals("T")) { // 비밀글이면
                String view_passwd = util.nullCheck(request.getParameter("view_passwd"));
                if (dto.getBoard_passwd().equals(view_passwd) && !dto.getBoard_passwd().equals("")) {
                    
                } else {
                    imsiPage = "viewPasswdPage";
                }
            }
            
            request.setAttribute("menu_gubun", "board_view");
            request.setAttribute("dto", dto);
            request.setAttribute("imsiPage", imsiPage);
            
            page = "/board/view.jsp";
            RequestDispatcher rd = request.getRequestDispatcher(page);
            rd.forward(request, response);
        } else if (url.indexOf("modify.do") != -1) {
            
            request.setAttribute("menu_gubun", "board_modify");
            
            dto = dao.getSelect(board_no);
            
            request.setAttribute("dto", dto);
            
            page = "/board/modify.jsp";
            RequestDispatcher rd = request.getRequestDispatcher(page);
            rd.forward(request, response);
            
        } else if (url.indexOf("modifyProc.do") != -1) {
            
            String check_passwd = dao.getSelect(board_no).getBoard_passwd();
            String board_passwd = request.getParameter("board_passwd");
            
            if (!check_passwd.equals(board_passwd)) {
                /*
                out.println("<script>");
                out.println("alert('비밀번호가 다릅니다.');");
                out.println("GoBoardPage('view', "+ board_no +");");
                out.println("</script>");
                */
                out.println("<script>");
                out.println("$('#span_board_passwd').text('F');");
                out.println("</script>");
                return;
            }
            
            String board_writer = request.getParameter("board_writer");
            String board_email = request.getParameter("board_email");
            String board_subject = request.getParameter("board_subject");
            String board_content = request.getParameter("board_content");
            String board_notice = request.getParameter("board_notice");
            
            int board_notice_no;
            if (board_notice == null || board_notice.trim().equals("") || !board_notice.equals("T") || board_notice.equals("0")) {
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
            
            dto.setBoard_no(board_no);
            dto.setBoard_writer(board_writer);
            dto.setBoard_subject(board_subject);
            dto.setBoard_content(board_content);
            dto.setBoard_email(board_email);
            dto.setBoard_passwd(board_passwd);
            dto.setBoard_ip(ip);
            dto.setMember_no(cookNo);
            dto.setBoard_notice_no(board_notice_no);
            dto.setBoard_secret(board_secret);
            
            int result = dao.setUpdate(dto);
            
            if (result > 0) { // 성공
                System.out.println("-- 글 수정 성공 --");
                /*
                out.println("<script>");
                // out.println("alert('정상적으로 수정되었습니다.');");
                out.println("GoBoardPage('view', "+ board_no +");");
                out.println("</script>");
                */
            } else { // 실패
                System.out.println("-- 글 수정 실패 --");
                out.println("<script>");
                out.println("alert('수정하지 못했습니다.');");
                out.println("GoBoardPage('view', "+ board_no +");");
                out.println("</script>");
            }
            
        } else if (url.indexOf("delete.do") != -1) {
            
            request.setAttribute("menu_gubun", "board_delete");
            
            dto = dao.getSelect(board_no);
            
            request.setAttribute("dto", dto);
            
            page = "/board/delete.jsp";
            RequestDispatcher rd = request.getRequestDispatcher(page);
            rd.forward(request, response);
            
        } else if (url.indexOf("deleteProc.do") != -1) {
            
            String check_passwd = dao.getSelect(board_no).getBoard_passwd();
            String board_passwd = request.getParameter("board_passwd");
            
            if (!check_passwd.equals(board_passwd)) {
                out.println("<script>");
                out.println("$('#span_board_passwd').text('F');");
                out.println("</script>");
                return;
            }
            
            dto.setBoard_no(board_no);
            dto.setBoard_passwd(board_passwd);
            
            int result = dao.setDelete(dto);
            
            if (result > 0) { // 성공
                System.out.println("-- 글 삭제 성공 --");
            } else { // 실패
                System.out.println("-- 글 삭제 실패 --");
                out.println("<script>");
                out.println("alert('삭제하지 못했습니다.');");
                out.println("GoBoardPage('view', "+ board_no +");");
                out.println("</script>");
            }
            
        } else if (url.contains("commentList.do")) {
            
            // comment_pageNumber-------
            int comment_pageNumber = util.toNumber(request.getParameter("comment_pageNumber"));
            if (comment_pageNumber <= 0) {
                comment_pageNumber = 1;
            }
            
            request.setAttribute("comment_pageNumber", comment_pageNumber);
            // -----------------
            
            // 페이징 -----------------------
            int pageSize = 5; // 한페이지 당 보여질 행 개수
            int blockSize = 10; // 한 블록 당 보여질 페이지 개수
            int totalRecord = dao.getTotalCommentRecord(board_no); // 총 행 개수
            
            int[] pagerArray = util.pager(pageSize, blockSize, totalRecord, comment_pageNumber);
            int recordNum = pagerArray[0];
            int startRecord = pagerArray[1];
            int lastRecord = pagerArray[2];
            int totalPage = pagerArray[3];
            int startPage = pagerArray[4];
            int lastPage = pagerArray[5];
            
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
            
            // 게시글 코멘트 리스트 ----------------
            ArrayList<BoardCommentDTO> commentList = dao.getSelectComment(board_no, startRecord, lastRecord);
            
            request.setAttribute("commentList", commentList);
            // ------------------------------
            
            // pageNumber
            request.setAttribute("comment_pageNumber", comment_pageNumber);
            
            request.setAttribute("menu_gubun", "board_comment_list");
            
            page = "/board/comment_list.jsp";
            RequestDispatcher rd = request.getRequestDispatcher(page);
            rd.forward(request, response);
            
        } else if (url.indexOf("commentProc.do") != -1) {
            
            String board_comment_writer = request.getParameter("board_comment_writer");
            String board_comment_passwd = request.getParameter("board_comment_passwd");
            String board_comment_content = request.getParameter("board_comment_content");
            
            BoardCommentDTO commentDto = new BoardCommentDTO();
            
            commentDto.setBoard_no(board_no);
            commentDto.setBoard_comment_writer(board_comment_writer);
            commentDto.setBoard_comment_content(board_comment_content);
            commentDto.setBoard_comment_passwd(board_comment_passwd);
            commentDto.setBoard_comment_ip(ip);
            commentDto.setMember_no(cookNo);
            
            int result = dao.setInsertComment(commentDto);
            
            if (result > 0) { // 성공
                System.out.println("-- 댓글 등록 성공 --");
            } else { // 실패
                System.out.println("-- 댓글 등록 실패 --");
                out.println("<script>");
                out.println("alert('등록하지 못했습니다.');");
                out.println("GoCommentPage('1');");
                out.println("</script>");
            }
            
        }
	    
    }

}
