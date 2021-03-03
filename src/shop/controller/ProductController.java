package shop.controller;

import java.io.File;
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
        
        String temp = ""; // 공용 임시변수
        
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
            
        } else if (url.indexOf("addProc.do") != -1 || url.indexOf("modifyProc.do") != -1) {
            
            String img_path01 = request.getSession().getServletContext().getRealPath("/attach/product_img/");
            java.io.File isDir = new java.io.File(img_path01);
            if (!isDir.isDirectory()) {
                System.out.println("디렉토리가 존재하지 않습니다. 디렉토리를 생성합니다.");
                isDir.mkdir();
            }
            String img_path02 = img_path01.replace("\\", "/");
            String img_path03 = img_path01.replace("\\", "\\\\");
            int max_size = 10 * 1024 * 1024; // 10M, 업로드 최대용량
            
            // System.out.println("img_path01 : " + img_path01);
            // System.out.println("img_path02 : " + img_path02);
            // System.out.println("img_path03 : " + img_path03);
            
            MultipartRequest multi = new MultipartRequest(request, img_path03, max_size, "utf-8", new DefaultFileRenamePolicy()); // 서버에 저장된다.
            
            int arrayCounter = 3;
            String[] array = new String[arrayCounter];
            for (int i=0; i<array.length; i++) {
                array[i] = "-";
            }
            
            Enumeration files = multi.getFileNames();
            while (files.hasMoreElements()) { // 다음 요소가 있으면
                String formName = (String)files.nextElement(); // 폼 이름
                String filename = multi.getFilesystemName(formName); // 파일 이름
                filename = Normalizer.normalize(filename, Normalizer.Form.NFC); // 완성형으로 바꾸어준다. (mac 크롬에서 업로드시 한글 자소분리(조합형으로 업로드 되는 현상) 해결)
                String fileType = multi.getContentType(formName); // 파일 타입
                
                // String fileOrgName = multi.getOriginalFileName(formName);
                // String fileType = multi.getContentType(formName);
                
                // System.out.println("formName : " + formName); // file0 file1 file2
                // System.out.println("filename : " + filename); // 파일이름
                // System.out.println(formName + " : " + filename + " : " + fileOrgName + " : " + fileType);
                
                if (filename == null || filename.trim().equals("")) {
                    filename = "-";
                }
                
                int k = Integer.parseInt(formName);
                array[k] = filename;
            }
            
            for (int i=0; i<array.length; i++) {
                temp = array[i];
                if (temp.equals("-")) {
                    continue;
                }
                String old_path = img_path03 + temp; // 원본이 업로드된 절대경로와 파일명을 구한다
                java.io.File f1 = new java.io.File(old_path);
                if (!f1.exists()) {
                    array[i] = "-"; // 실제 파일이 없으면 배열내 값을 비운다.
                    continue;
                }
                
                String ext = "";
                int point_index = temp.lastIndexOf(".");
                if (point_index == -1) { // . 이 없으면..
                    f1.delete();
                    array[i] = "-";
                    continue;
                }
                ext = temp.substring(point_index + 1).toLowerCase();
                if (!(ext.equals("jpg") || ext.equals("jpeg") || ext.equals("gif") || ext.equals("png"))) {
                    f1.delete();
                    array[i] = "-";
                    continue;
                }
                
                String uuid = util.create_uuid();
                String newFilename = util.getDateTimeType() + "-" + uuid + "." + ext;
                java.io.File newFile = new java.io.File(img_path03 + newFilename);
                f1.renameTo(newFile); // 파일 이동
                array[i] = array[i] + "|" + newFilename;
            }
            
            String str = "";
            for (int i=0; i<array.length; i++) {
                str += "," + array[i];
            }
            str = str.substring(1);
            // System.out.println(str);
            
            product_no = util.toNumber(multi.getParameter("product_no"));
            String product_name = util.toEntityCode(multi.getParameter("product_name"));
            int product_price = util.toNumber(multi.getParameter("product_price"));
            String product_description = util.toEntityCode(multi.getParameter("product_description"));
            
            // System.out.println("product_name : " + product_name);
            // System.out.println("product_price : " + product_price);
            // System.out.println("product_description : " + product_description);
            
            dto.setProduct_no(product_no);
            dto.setProduct_name(product_name);
            dto.setProduct_price(product_price);
            dto.setProduct_description(product_description);
            
            // System.out.println(dto.toString());
            
            int result = 0;
            if (url.indexOf("addProc.do") != -1) {
                request.setAttribute("menu_gubun", "product_addProc");
                dto.setProduct_img(str);
                
                // System.out.println(dto.toString());
                result = dao.setInsert(dto);
            } else if (url.indexOf("modifyProc.do") != -1) {
                
                request.setAttribute("menu_gubun", "product_modifyProc");
                
                ProductDTO dto2 = dao.getSelect(product_no);
                // System.out.println(dto2.toString());
                String db_product_img = dto2.getProduct_img(); // DB의 product_img 가져오기
                // System.out.println(db_product_img);
                String deleteFileName = "";
                if (str.trim().equals("-,-,-")) {
                    dto.setProduct_img(db_product_img);
                } else { // 첨부파일이 있을 경우, 순서 고민, 반복문
                    temp = "";
                    String[] dbArray = db_product_img.split(",");
                    for (int i=0; i<array.length; i++) {
                        if (array[i].equals("-")) {
                            temp += "," + dbArray[i];
                        } else {
                            temp += "," + array[i];
                            deleteFileName += "," + dbArray[i].substring(dbArray[i].lastIndexOf("|") + 1);
                        }
                    }
                }
                // System.out.println(deleteFileName);
                if (deleteFileName != null && !deleteFileName.equals("")) {
                    deleteFileName = deleteFileName.substring(1);
                }
                // System.out.println(deleteFileName);
                temp = temp.substring(1);
                // System.out.println(temp);
                dto.setProduct_img(temp);
                
                // System.out.println(dto.toString());
                result = dao.setUpdate(dto);
                
                String[] arrayDelete = deleteFileName.split(",");
                for (int i=0; i<arrayDelete.length; i++) {
                    if (!arrayDelete[i].trim().equals("-")) {
                        java.io.File f1 = new java.io.File(img_path03 + arrayDelete[i]);
                        f1.delete();
                    }
                }
            }
            
            if (result > 0) { // 성공
                System.out.println("-- 성공 --");
                out.println("<script>");
                out.println("select_proc('list', '1', '');");
                out.println("</script>");
            } else { // 실패
                System.out.println("-- 실패 --");
                out.println("<script>");
                out.println("alert('처리하지 못했습니다.');");
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
            
        } else if (url.indexOf("view.do") != -1) {
            
            dto = dao.getSelect(product_no);
            
            // String product_description = dto.getProduct_description().replace("\n", "<br>");
            // dto.setProduct_description(product_description);
            
            request.setAttribute("menu_gubun", "product_view");
            request.setAttribute("dto", dto);
            
            page = "/shop/product/view.jsp";
            RequestDispatcher rd = request.getRequestDispatcher(page);
            rd.forward(request, response);
            
        } else if (url.indexOf("modify.do") != -1) {
            
            dto = dao.getSelect(product_no);
            
            // String product_description = dto.getProduct_description().replace("\n", "<br>");
            // dto.setProduct_description(product_description);
            
            request.setAttribute("menu_gubun", "product_modify");
            request.setAttribute("dto", dto);
            
            page = "/shop/product/modify.jsp";
            RequestDispatcher rd = request.getRequestDispatcher(page);
            rd.forward(request, response);
            
        } else if (url.indexOf("deleteProc.do") != -1) {
            
            dto.setProduct_no(product_no);
            
            // System.out.println(dto.toString());
            
            request.setAttribute("menu_gubun", "product_modifyProc");
            
            String img_path01 = request.getSession().getServletContext().getRealPath("/attach/product_img/");
            java.io.File isDir = new java.io.File(img_path01);
            if (!isDir.isDirectory()) {
                System.out.println("디렉토리가 존재하지 않습니다. 디렉토리를 생성합니다.");
                isDir.mkdir();
            }
            String img_path02 = img_path01.replace("\\", "/");
            String img_path03 = img_path01.replace("\\", "\\\\");
            
            // System.out.println("img_path01 : " + img_path01);
            // System.out.println("img_path02 : " + img_path02);
            // System.out.println("img_path03 : " + img_path03);
            
            ProductDTO dto2 = dao.getSelect(product_no);
            String db_product_img = dto2.getProduct_img(); // DB의 product_img 가져오기
            String deleteFileName = "";
            
            String[] dbArray = db_product_img.split(",");
            for (int i=0; i<dbArray.length; i++) {
                if (dbArray[i].equals("-")) {
                    
                } else {
                    deleteFileName += "," + dbArray[i].substring(dbArray[i].lastIndexOf("|") + 1);
                }
            }
            
            // System.out.println(deleteFileName);
            if (deleteFileName != null && !deleteFileName.equals("")) {
                deleteFileName = deleteFileName.substring(1);
            }
            System.out.println(deleteFileName);
            
            int result = dao.setDelete(dto);
            
            String[] arrayDelete = deleteFileName.split(",");
            for (int i=0; i<arrayDelete.length; i++) {
                if (!arrayDelete[i].trim().equals("-")) {
                    java.io.File f1 = new java.io.File(img_path03 + arrayDelete[i]);
                    f1.delete();
                }
            }
            
            
            if (result > 0) { // 성공
                System.out.println("-- 삭제 성공 --");
                out.println("<script>");
                out.println("select_proc('list', '1', '');");
                out.println("</script>");
            } else { // 실패
                System.out.println("-- 삭제 실패 --");
                out.println("<script>");
                out.println("alert('처리하지 못했습니다.');");
                out.println("select_proc('view', '', '" + product_no + "');");
                out.println("</script>");
            }
            
        }
        
    }

}
