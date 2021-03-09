package chart.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
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

import org.json.simple.JSONObject;

import chart.service.ChartService;
import chart.common.UtilChart;
import shop.model.dao.CartDAO;
import shop.model.dao.ProductDAO;
import shop.model.dto.CartDTO;
import shop.model.dto.ProductDTO;

@WebServlet("/chart_servlet/*")
public class ChartController extends HttpServlet {
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
        
        UtilChart util = new UtilChart();
        
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
            
            // menu_gubun 변수에 chart_index 을 할당
            request.setAttribute("menu_gubun", "chart_index");
            
            // page 로 request 값을 포함하여 포워딩(response) 시킨다.
            RequestDispatcher rd = request.getRequestDispatcher(page);
            rd.forward(request, response);
            
        } else if (url.indexOf("googleChartJson.do") != -1) {
            
            // menu_gubun 변수에 chart_googleChartJson 을 할당
            request.setAttribute("menu_gubun", "chart_googleChartJson");
            
            page = "/chart/googleChartJson.jsp";
            // page 로 request 값을 포함하여 포워딩(response) 시킨다.
            RequestDispatcher rd = request.getRequestDispatcher(page);
            rd.forward(request, response);
            
        } else if (url.indexOf("createJson.do") != -1) {
            
            request.setAttribute("menu_gubun", "chart_createJson");
            
            ChartService service = new ChartService();
            JSONObject json = service.getChartData();
            request.setAttribute("data", json);
            
            String img_path01 = request.getSession().getServletContext().getRealPath("/attach/json/");
            java.io.File isDir = new java.io.File(img_path01);
            if(!isDir.isDirectory()){
                isDir.mkdir();
            }
            String img_path02 = img_path01.replace("\\", "/");
            String img_path03 = img_path01.replace("\\", "\\\\");
            
            util.fileDelete(request, img_path03);
            
            String newFileName = util.getDateTimeType() + "_" + util.create_uuid() + ".json";
            File file = new File(img_path03 + newFileName);
            file.createNewFile();
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            bufferedWriter.write(json.toString());
            bufferedWriter.close();
            
            request.setAttribute("menu_gubun", "chart_myChart");
            request.setAttribute("chart_subject", "챠트 제목입니다."); 
            request.setAttribute("chart_type", "PieChart");
            request.setAttribute("chart_jsonFileName", newFileName);
            
            page = "/chart/myChart.jsp";
            RequestDispatcher rd = request.getRequestDispatcher(page);
            rd.forward(request, response);
            
        }
        
	}

}
