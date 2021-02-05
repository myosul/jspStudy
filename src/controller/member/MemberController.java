package controller.member;

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
import model.member.dto.MemberDTO;
import model.member.dao.MemberDAO;

@WebServlet("/member_servlet/*") // url-mapping
public class MemberController extends HttpServlet {
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
        
        if (url.indexOf("join.do") != -1) {
            
            // menu_gubun 변수에 member_join 을 할당
            request.setAttribute("menu_gubun", "member_join");
            
            // page 로 request 값을 포함하여 포워딩(response) 시킨다.
            RequestDispatcher rd = request.getRequestDispatcher(page);
            rd.forward(request, response);
            
        } else if (url.indexOf("joinProc.do") != -1) {
            
            String member_id = request.getParameter("member_id");
            if (util.numberAndAlphabetCheck(member_id)) {
                member_id = util.toNumberAndAlphabet(member_id);
                member_id = util.toEntityCode(member_id);
            } else {
                response.setContentType("text/html; charset=utf-8");
                PrintWriter out = response.getWriter();
                out.println("<script>");
                out.println("alert('적절하지 않은 아이디입니다.');");
                out.println("history.back();");
                out.println("</script>");
                return;
            }
            
            String member_password = request.getParameter("member_password");
            if (util.passwordCheck(member_password)) {
                member_password = util.toPassword(member_password);
                member_password = util.toEntityCode(member_password);
            } else {
                response.setContentType("text/html; charset=utf-8");
                PrintWriter out = response.getWriter();
                out.println("<script>");
                out.println("alert('적절하지 않은 비밀번호입니다.');");
                out.println("history.back();");
                out.println("</script>");
                return;
            }
            
            String member_password_check = request.getParameter("member_password_check");
            if (util.passwordCheck(member_password_check)) {
                member_password_check = util.toPassword(member_password_check);
                member_password_check = util.toEntityCode(member_password_check);
            } else {
                response.setContentType("text/html; charset=utf-8");
                PrintWriter out = response.getWriter();
                out.println("<script>");
                out.println("alert('적절하지 않은 비밀번호입니다.');");
                out.println("history.back();");
                out.println("</script>");
                return;
            }
            
            String member_name = util.toEntityCode(request.getParameter("member_name"));
            
            String member_gender = util.toEntityCode(request.getParameter("member_gender"));
            
            int member_born_year = util.toNumber(request.getParameter("member_born_year"));
            
            int member_postcode = util.toNumber(request.getParameter("member_postcode"));
            
            String member_address = util.toEntityCode(request.getParameter("member_address"));
            
            String member_detail_address = util.toEntityCode(request.getParameter("member_detail_address"));
            
            String member_extra_address = util.toEntityCode(request.getParameter("member_extra_address"));
            
            MemberDTO dto = new MemberDTO();
            dto.setMember_id(member_id);
            dto.setMember_password(member_password);
            dto.setMember_password_check(member_password_check);
            dto.setMember_name(member_name);
            dto.setMember_gender(member_gender);
            dto.setMember_born_year(member_born_year);
            dto.setMember_postcode(member_postcode);
            dto.setMember_address(member_address);
            dto.setMember_detail_address(member_detail_address);
            dto.setMember_extra_address(member_extra_address);
            
            MemberDAO dao = new MemberDAO();
            int result = dao.setInsert(dto);
            
            if (result > 0) { // 회원가입 성공
                /*
                String temp = "";
                temp = path + "/member_servlet/login.do";
                response.sendRedirect(temp);
                */
                response.setContentType("text/html; charset=utf-8");
                PrintWriter out = response.getWriter();
                out.println("<script>");
                out.println("alert('정상적으로 가입되었습니다.');");
                out.println("location.href='" + path + "/member_servlet/login.do';");
                out.println("</script>");
                return;
            } else { // 회원가입 실패
                response.setContentType("text/html; charset=utf-8");
                PrintWriter out = response.getWriter();
                out.println("<script>");
                out.println("alert('정상적으로 처리되지 않았습니다.');");
                out.println("history.back();");
                out.println("</script>");
                return;
            }
            
        } else if (url.indexOf("login.do") != -1) {
            
            // 세션 제거--------------------
            HttpSession session = request.getSession();
            session.invalidate();
            // ------------------------------
            
            request.setAttribute("menu_gubun", "member_login");
            
            RequestDispatcher rd = request.getRequestDispatcher(page);
            rd.forward(request, response);
            
        } else if (url.indexOf("loginProc.do") != -1) {
            
            String member_id = util.toNumberAndAlphabet(request.getParameter("member_id"));
            member_id = util.toEntityCode(member_id);
            
            String member_password_check = util.toPassword(request.getParameter("member_password_check"));
            member_password_check = util.toEntityCode(member_password_check);
            
            MemberDTO dto = new MemberDTO();
            dto.setMember_id(member_id);
            dto.setMember_password_check(member_password_check);
            
            MemberDAO dao = new MemberDAO();
            MemberDTO resultDto = dao.getLogin(dto);
            
            String temp = "";
            if (resultDto.getMember_no() > 0) { // 로그인 성공
                HttpSession session = request.getSession();
                session.setMaxInactiveInterval(20*60); // 세션 시간 설정
                session.setAttribute("cookNo", resultDto.getMember_no());
                session.setAttribute("cookId", resultDto.getMember_id());
                session.setAttribute("cookName", resultDto.getMember_name());
                
                temp = path;
                response.sendRedirect(temp);
                return;
            } else { // 로그인 실패
                response.setContentType("text/html; charset=utf-8");
                PrintWriter out = response.getWriter();
                out.println("<script>");
                out.println("alert('로그인하지 못 했습니다.');");
                out.println("history.back();");
                out.println("</script>");
                return;
            }
            
        } else if (url.indexOf("logout.do") != -1) {
            
            // 세션 제거--------------------
            HttpSession session = request.getSession();
            session.invalidate();
            // ------------------------------
            
            String temp = path;
            response.sendRedirect(temp);
            
        } else if (url.indexOf("list.do") != -1) {
            
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
            
            MemberDAO dao = new MemberDAO(); // DAO 선언
            
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
            
            // 멤버 리스트 ------------------
            ArrayList<MemberDTO> list = dao.getSelectAll(startRecord, lastRecord);
            
            request.setAttribute("list", list);
            request.setAttribute("menu_gubun", "member_list");
            // ------------------------------
            
            request.setAttribute("pageNumber", pageNumber);
            
            RequestDispatcher rd = request.getRequestDispatcher(page);
            rd.forward(request, response);
            
        } else if (url.indexOf("view.do") != -1) {
            
            int member_no = util.toNumber(request.getParameter("member_no"));
            
            MemberDAO dao = new MemberDAO();
            MemberDTO dto = dao.getSelect(member_no);
            
            request.setAttribute("dto", dto);
            request.setAttribute("menu_gubun", "member_view");
            
            RequestDispatcher rd = request.getRequestDispatcher(page);
            rd.forward(request, response);
            
        } else if (url.indexOf("modify.do") != -1) {
            
            int member_no = util.toNumber(request.getParameter("member_no"));
            
            // 세션 체크--------------------
            HttpSession session = request.getSession();
            int cookNo = 0;
            if (session.getAttribute("cookNo") != null) {
                cookNo = (Integer)session.getAttribute("cookNo");
            }
            
            if (cookNo != member_no) { // 일치하지 않는다면
                response.setContentType("text/html; charset=utf-8");
                PrintWriter out = response.getWriter();
                out.println("<script>");
                out.println("alert('접근 권한이 없습니다.');");
                out.println("history.back();");
                out.println("</script>");
                return;
            }
            // -----------------------------
            
            MemberDAO dao = new MemberDAO();
            MemberDTO dto = dao.getSelect(member_no);
            
            request.setAttribute("dto", dto);
            request.setAttribute("menu_gubun", "member_modify");
            
            RequestDispatcher rd = request.getRequestDispatcher(page);
            rd.forward(request, response);
            
        } else if (url.indexOf("delete.do") != -1) {
            
            int member_no = util.toNumber(request.getParameter("member_no"));
            
            // 세션 체크--------------------
            HttpSession session = request.getSession();
            int cookNo = 0;
            if (session.getAttribute("cookNo") != null) {
                cookNo = (Integer)session.getAttribute("cookNo");
            }
            
            if (cookNo != member_no) { // 일치하지 않는다면
                response.setContentType("text/html; charset=utf-8");
                PrintWriter out = response.getWriter();
                out.println("<script>");
                out.println("alert('접근 권한이 없습니다.');");
                out.println("history.back();");
                out.println("</script>");
                return;
            }
            // -----------------------------
            
            MemberDAO dao = new MemberDAO();
            MemberDTO dto = dao.getSelect(member_no);
            
            request.setAttribute("dto", dto);
            request.setAttribute("menu_gubun", "member_delete");
            
            RequestDispatcher rd = request.getRequestDispatcher(page);
            rd.forward(request, response);
            
        } else if (url.indexOf("modifyProc.do") != -1) {
            
            int member_no = util.toNumber(request.getParameter("member_no"));
            
            // 세션 체크--------------------
            HttpSession session = request.getSession();
            int cookNo = 0;
            if (session.getAttribute("cookNo") != null) {
                cookNo = (Integer)session.getAttribute("cookNo");
            }
            
            if (cookNo != member_no) { // 일치하지 않는다면
                response.setContentType("text/html; charset=utf-8");
                PrintWriter out = response.getWriter();
                out.println("<script>");
                out.println("alert('접근 권한이 없습니다.');");
                out.println("history.back();");
                out.println("</script>");
                return;
            }
            // -----------------------------
            
            String member_id = util.toNumberAndAlphabet(request.getParameter("member_id"));
            member_id = util.toEntityCode(member_id);
            
            String member_password_check = util.toPassword(request.getParameter("member_password_check"));
            member_password_check = util.toEntityCode(member_password_check);
            
            String member_name = util.toEntityCode(request.getParameter("member_name"));
            
            String member_gender = util.toEntityCode(request.getParameter("member_gender"));
            
            int member_born_year = util.toNumber(request.getParameter("member_born_year"));
            
            int member_postcode = util.toNumber(request.getParameter("member_postcode"));
            
            String member_address = util.toEntityCode(request.getParameter("member_address"));
            
            String member_detail_address = util.toEntityCode(request.getParameter("member_detail_address"));
            
            String member_extra_address = util.toEntityCode(request.getParameter("member_extra_address"));
            
            MemberDTO dto = new MemberDTO();
            dto.setMember_no(member_no);
            dto.setMember_id(member_id);
            dto.setMember_password_check(member_password_check);
            dto.setMember_name(member_name);
            dto.setMember_gender(member_gender);
            dto.setMember_born_year(member_born_year);
            dto.setMember_postcode(member_postcode);
            dto.setMember_address(member_address);
            dto.setMember_detail_address(member_detail_address);
            dto.setMember_extra_address(member_extra_address);
            
            MemberDAO dao = new MemberDAO();
            int result = dao.setUpdate(dto);
            
            if (result > 0) { // 업데이트 성공
                /*
                String temp = ""
                temp = path + "/member_servlet/view.do?member_no=" + member_no;
                response.sendRedirect(temp);
                */
                response.setContentType("text/html; charset=utf-8");
                PrintWriter out = response.getWriter();
                out.println("<script>");
                out.println("alert('정상적으로 수정되었습니다.');");
                out.println("location.href='" + path + "/member_servlet/view.do?member_no=" + member_no + "';");
                out.println("</script>");
                return;
            } else { // 업데이트 실패
                response.setContentType("text/html; charset=utf-8");
                PrintWriter out = response.getWriter();
                out.println("<script>");
                out.println("alert('수정하지 못 했습니다.');");
                out.println("history.back();");
                out.println("</script>");
            }
            
        } else if (url.indexOf("deleteProc.do") != -1) {
            
            int member_no = util.toNumber(request.getParameter("member_no"));
            
            // 세션 체크--------------------
            HttpSession session = request.getSession();
            int cookNo = 0;
            if (session.getAttribute("cookNo") != null) {
                cookNo = (Integer)session.getAttribute("cookNo");
            }
            
            if (cookNo != member_no) { // 일치하지 않는다면
                response.setContentType("text/html; charset=utf-8");
                PrintWriter out = response.getWriter();
                out.println("<script>");
                out.println("alert('접근 권한이 없습니다.');");
                out.println("history.back();");
                out.println("</script>");
                return;
            }
            // -----------------------------
            
            String member_password_check = util.toNumberAndAlphabet(request.getParameter("member_password_check"));
            member_password_check = util.toEntityCode(member_password_check);
            
            MemberDTO dto = new MemberDTO();
            dto.setMember_no(member_no);
            dto.setMember_password_check(member_password_check);
            
            MemberDAO dao = new MemberDAO();
            int result = dao.setDelete(dto);
            
            String temp = "";
            if (result > 0) { // 삭제 성공
                session.invalidate();
                /*
                String temp = "";
                temp = path;
                response.sendRedirect(temp);
                */
                response.setContentType("text/html; charset=utf-8");
                PrintWriter out = response.getWriter();
                out.println("<script>");
                out.println("alert('정상적으로 탈퇴되었습니다.');");
                out.println("location.href='" + path + "';");
                out.println("</script>");
                return;
            } else { // 삭제 실패
                response.setContentType("text/html; charset=utf-8");
                PrintWriter out = response.getWriter();
                out.println("<script>");
                out.println("alert('탈퇴하지 못 했습니다.');");
                out.println("history.back();");
                out.println("</script>");
            }
            
        } else if (url.indexOf("id_check.do") != -1) {
            
            MemberDAO dao = new MemberDAO();
            
            String member_id = request.getParameter("member_id");
            
            int result = 1;
            
            if (util.numberAndAlphabetCheck(member_id)) {
                member_id = util.toNumberAndAlphabet(member_id);
                member_id = util.toEntityCode(member_id);
                result = dao.getIdCheck(member_id);
            }
            
            PrintWriter out = response.getWriter();
            out.println(result);
            
        } else if (url.indexOf("id_check_win.do") != -1) {
            
            String temp = path + "/member/id_check_win.jsp";
            response.sendRedirect(temp);
            
        } else if (url.indexOf("id_check_win_proc.do") != -1) {
            
            MemberDAO dao = new MemberDAO();
            
            String member_id = request.getParameter("member_id");
            
            int result = 1;
            
            if (util.numberAndAlphabetCheck(member_id)) {
                member_id = util.toNumberAndAlphabet(member_id);
                member_id = util.toEntityCode(member_id);
                result = dao.getIdCheck(member_id);
            }
            
            if (result <= 0) { // 아이디 중복이 없으면 member_id 값을 전달한다.
                request.setAttribute("member_id", member_id);
            }
            request.setAttribute("result", result);
            String temp = "/member/id_check_win.jsp";
            RequestDispatcher rd = request.getRequestDispatcher(temp);
            rd.forward(request, response);
            
        }
    }

}
