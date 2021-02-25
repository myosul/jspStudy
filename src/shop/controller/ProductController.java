package shop.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import shop.common.UtilProduct;
import shop.common.Constants;
import shop.model.dao.ProductDAO;
import shop.model.dto.ProductDTO;

@WebServlet("/product_servlet/*")
public class ProductController extends HttpServlet {
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
        
        UtilProduct util = new UtilProduct();
        
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
        
        String page = "/main/main.jsp"; // 포워딩할 주소
        
        if (url.indexOf("index.do") != -1) {
            
            // menu_gubun 변수에 board_index 을 할당
            request.setAttribute("menu_gubun", "product_index");
            
            // page 로 request 값을 포함하여 포워딩(response) 시킨다.
            RequestDispatcher rd = request.getRequestDispatcher(page);
            rd.forward(request, response);
            
        } else if (url.indexOf("add.do") != -1) {
            
            request.setAttribute("menu_gubun", "product_add");
            
            page = "/shop/product/add.jsp";
            RequestDispatcher rd = request.getRequestDispatcher(page);
            rd.forward(request, response);
            
        } else if (url.indexOf("addProc.do") != -1) {
            
            String img_path01 = request.getSession().getServletContext().getRealPath("/attach/product_img/");
            String img_path02 = img_path01.replace("\\", "/");
            String img_path03 = img_path01.replace("\\", "\\\\");
            int max_size = 10 * 1024 * 1024; // 10M, 업로드 최대용량
            
            // System.out.println("img_path01 : " + img_path01);
            // System.out.println("img_path02 : " + img_path02);
            // System.out.println("img_path03 : " + img_path03);
            
            MultipartRequest multi = new MultipartRequest(request, img_path03, max_size, "utf-8", new DefaultFileRenamePolicy());
            
            
            String product_name = util.toEntityCode(multi.getParameter("product_name"));
            int product_price = util.toNumber(multi.getParameter("product_price"));
            String product_description = util.toEntityCode(multi.getParameter("product_description"));
            
            // System.out.println("product_name : " + product_name);
            // System.out.println("product_price : " + product_price);
            // System.out.println("product_description : " + product_description);
            
            String[] array = new String[3];
            
            Enumeration files = multi.getFileNames();
            while (files.hasMoreElements()) {
                String formName = (String)files.nextElement();
                String filename = multi.getFilesystemName(formName);
                filename = Normalizer.normalize(filename, Normalizer.Form.NFC); // 완성형으로 바꾸어준다. (mac 크롬에서 업로드시 한글 자소분리(조합형으로 업로드 되는 현상) 해결)
                
                // String fileOrgName = multi.getOriginalFileName(formName);
                // String fileType = multi.getContentType(formName);
                
                // System.out.println("formName : " + formName); // file0 file1 file2
                // System.out.println("filename : " + filename); // 파일이름
                // System.out.println(formName + " : " + filename + " : " + fileOrgName + " : " + fileType);
                
                if (formName.equals("0")) {
                    array[0] = filename;
                } else if (formName.equals("1")) {
                    array[1] = filename;
                } else if (formName.equals("2")) {
                    array[2] = filename;
                }
            }
            
            temp = "";
            for (int i=0; i<array.length; i++) {
                String imsi = array[i];
                if (imsi == null) {
                    imsi = "-";
                }
                temp += "," + imsi;
            }
            System.out.println(temp);
            temp = temp.substring(1);
            System.out.println(temp);

            dto.setProduct_name(product_name);
            dto.setProduct_price(product_price);
            dto.setProduct_description(product_description);
            dto.setProduct_img(temp);
            
            int result = dao.setInsert(dto);
            
            if (result > 0) { // 성공
                System.out.println("-- 상품 등록 성공 --");
            } else { // 실패
                System.out.println("-- 상품 등록 실패 --");
                out.println("<script>");
                out.println("alert('등록하지 못했습니다.');");
                out.println("select_proc('list', '1', '');");
                out.println("</script>");
            }
            
        } else if (url.contains("list.do")) {
            
            // 페이징 -----------------------
            int pageSize = 10; // 한페이지 당 보여질 행 개수
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
            
            request.setAttribute("menu_gubun", "board_list");
            
            page = "/shop/product/list.jsp";
            RequestDispatcher rd = request.getRequestDispatcher(page);
            rd.forward(request, response);
            
        } /* else if (url.indexOf("view.do") != -1) {
            
            dto = dao.getSelect(product_no);
            
            // String product_description = dto.getProduct_description().replace("\n", "<br>");
            // dto.setProduct_description(product_description);
            
            request.setAttribute("menu_gubun", "product_view");
            request.setAttribute("dto", dto);
            
            page = "/shop/product/view.jsp";
            RequestDispatcher rd = request.getRequestDispatcher(page);
            rd.forward(request, response);
        } */
	    
    }

}
