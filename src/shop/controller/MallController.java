package shop.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.survey.dao.SurveyDAO;
import model.survey.dto.SurveyDTO;
import shop.common.UtilMall;
import shop.model.dao.ProductDAO;
import shop.model.dto.ProductDTO;
import shop.model.dao.CartDAO;
import shop.model.dto.CartDTO;

@WebServlet("/mall_servlet/*")
public class MallController extends HttpServlet {
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
        
        String temp = ""; // 공용 임시변수
        
        UtilMall util = new UtilMall();
        
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
        
        // product_no
        int product_no = util.toNumber(request.getParameter("product_no"));
        request.setAttribute("product_no", product_no);
        
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
        
        ProductDTO dto = new ProductDTO();
        ProductDAO dao = new ProductDAO();
        
        CartDAO cartDao = new CartDAO();
        CartDTO cartDto = new CartDTO();
        
        String page = "/main/main.jsp"; // 포워딩할 주소
        
        if (url.indexOf("index.do") != -1) {
            
            // menu_gubun 변수에 mall_index 을 할당
            request.setAttribute("menu_gubun", "mall_index");
            
            // page 로 request 값을 포함하여 포워딩(response) 시킨다.
            RequestDispatcher rd = request.getRequestDispatcher(page);
            rd.forward(request, response);
            
        } else if (url.indexOf("mall_list") != -1) {
            
            // 페이징 -----------------------
            int pageSize = 12; // 한페이지 당 보여질 행 개수
            int blockSize = 10; // 한 블록 당 보여질 페이지 개수
            int totalRecord = dao.getTotalRecord(search_option, search_data); // 총 행 개수
            
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
            ArrayList<ProductDTO> list = dao.getSelectAll(startRecord, lastRecord, search_option, search_data);
            
            request.setAttribute("list", list);
            // ------------------------------
            
            // pageNumber
            request.setAttribute("pageNumber", pageNumber);
            
            // 검색데이터--------------------
            request.setAttribute("search_option", search_option);
            request.setAttribute("search_data", search_data);
            // ------------------------------
            
            request.setAttribute("menu_gubun", "mall_list");
            
            page = "/shop/mall/mall_list.jsp";
            RequestDispatcher rd = request.getRequestDispatcher(page);
            rd.forward(request, response);
            
        } else if (url.indexOf("mall_view.do") != -1) {
            
            dto = dao.getSelect(product_no);
            
            // String product_description = dto.getProduct_description().replace("\n", "<br>");
            // dto.setProduct_description(product_description);
            
            request.setAttribute("menu_gubun", "mall_view");
            request.setAttribute("dto", dto);
            
            page = "/shop/mall/mall_view.jsp";
            RequestDispatcher rd = request.getRequestDispatcher(page);
            rd.forward(request, response);
            
        } else if (url.indexOf("cart_addProc.do") != -1) {
            
            // 세션 체크--------------------
            if (cookNo <= 0) { // 로그인을 하지 않았다면
                out.println("<script>");
                out.println("alert('로그인 후 이용하세요.');");
                out.println("location.href='" + path + "/member_servlet/login.do';");
                out.println("</script>");
                return;
            }
            // ------------------------------
            
            int product_amount = util.toNumber(request.getParameter("product_amount"));
            
            cartDto.setMember_no(cookNo);
            cartDto.setProduct_no(product_no);
            cartDto.setProduct_amount(product_amount);
            
            // System.out.println(cartDto.toString());
            
            int result = cartDao.setInsert(cartDto);
            
            if (result > 0) { // 성공
                System.out.println("-- 성공 --");
                out.println("<script>");
                out.println("select_proc('cart_list', '1', '');");
                out.println("</script>");
            } else { // 실패
                System.out.println("-- 실패 --");
                out.println("<script>");
                out.println("alert('처리하지 못했습니다.');");
                out.println("select_proc('cart_list', '1', '');");
                out.println("</script>");
            }
            
        } else if (url.indexOf("cart_list.do") != -1) {
            
            // 세션 체크--------------------
            if (cookNo <= 0) { // 로그인을 하지 않았다면
                out.println("<script>");
                out.println("alert('로그인 후 이용하세요.');");
                out.println("location.href='" + path + "/member_servlet/login.do';");
                out.println("</script>");
                return;
            }
            // ------------------------------
            
            // 페이징 -----------------------
            int pageSize = 10; // 한페이지 당 보여질 행 개수
            int blockSize = 10; // 한 블록 당 보여질 페이지 개수
            int totalRecord = cartDao.getTotalRecord(cookNo); // 총 행 개수
            
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
            
            // 리스트 ----------------
            ArrayList<CartDTO> list = cartDao.getSelectAll(cookNo, startRecord, lastRecord);
            
            request.setAttribute("list", list);
            // ------------------------------
            
            // pageNumber
            request.setAttribute("pageNumber", pageNumber);
            
            request.setAttribute("menu_gubun", "cart_list");
            
            page = "/shop/mall/cart_list.jsp";
            RequestDispatcher rd = request.getRequestDispatcher(page);
            rd.forward(request, response);
            
        } else if (url.indexOf("cart_clear.do") != -1) {
            
            temp = request.getParameter("chk_no");
            String[] array = temp.split(",");
            
            boolean result = cartDao.setDeleteBatch(array);
            
            if (result) { // 성공
                System.out.println("-- 삭제 성공 --");
                out.println("<script>");
                out.println("select_proc('cart_list', '1', '');");
                out.println("</script>");
            } else { // 실패
                System.out.println("-- 삭제 실패 --");
                out.println("<script>");
                out.println("alert('처리하지 못했습니다.');");
                out.println("select_proc('cart_list', '1', '');");
                out.println("</script>");
            }
            
        }
	    
    }

}
