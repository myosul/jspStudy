package controller.survey1;

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

import common.Util;
import model.survey.dao.SurveyAnswerDAO;
import model.survey.dao.SurveyDAO;
import model.survey.dto.SurveyAnswerDTO;
import model.survey.dto.SurveyDTO;

@WebServlet("/survey1_servlet/*")
public class Survey1Controller extends HttpServlet {
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
        
        // 검색데이터-------
        String list_gubun = util.list_gubunCheck(request.getParameter("list_gubun"));
        
        String search_option = request.getParameter("search_option");
        String search_data = request.getParameter("search_data");
        String search_date_s = request.getParameter("search_date_s");
        String search_date_e = request.getParameter("search_date_e");
        String search_date_check = request.getParameter("search_date_check");
        String[] searchArray = util.searchCheck(search_option, search_data, search_date_s, search_date_e, search_date_check);
        search_option = searchArray[0];
        search_data = searchArray[1];
        search_date_s = searchArray[2];
        search_date_e = searchArray[3];
        search_date_check = searchArray[4];
        // -----------------
        
        // dateMap----------
        int[] date = util.getDateTime();
        Map<String, Integer> dateMap = new HashMap<>();
        dateMap.put("now_y", date[0]);
        dateMap.put("now_m", date[1]);
        dateMap.put("now_d", date[2]);
        
        request.setAttribute("dateMap", dateMap);
        // -----------------
        
        if (url.indexOf("list.do") != -1 || url.indexOf("survey.do") != -1 ) {
            
            SurveyDAO dao = new SurveyDAO();
            
            // 페이징 -----------------------
            int pageSize = 5; // 한페이지 당 보여질 행 개수
            int blockSize = 5; // 한 블록 당 보여질 페이지 개수
            int totalRecord = dao.getTotalRecord(list_gubun, search_option, search_data, search_date_s, search_date_e, search_date_check); // 총 행 개수
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
            ArrayList<SurveyDTO> list = dao.getSelectAll(startRecord, lastRecord, list_gubun, search_option, search_data, search_date_s, search_date_e, search_date_check);
            
            request.setAttribute("list", list);
            // ------------------------------
            
            // pageNumber
            request.setAttribute("pageNumber", pageNumber);
            
            // 검색데이터--------------------
            request.setAttribute("list_gubun", list_gubun);
            request.setAttribute("search_option", search_option);
            request.setAttribute("search_data", search_data);
            request.setAttribute("search_date_s", search_date_s);
            request.setAttribute("search_date_e", search_date_e);
            request.setAttribute("search_date_check", search_date_check);
            // ------------------------------
            
            // menu_gubun
            if (url.indexOf("list.do") != -1) {
                request.setAttribute("menu_gubun", "survey1_list");
            } else if (url.indexOf("survey.do") != -1) {
                request.setAttribute("menu_gubun", "survey1_survey");
            }
            
            // page 로 request 값을 포함하여 포워딩(response) 시킨다.
            RequestDispatcher rd = request.getRequestDispatcher(page);
            rd.forward(request, response);
            
        } else if (url.indexOf("add.do") != -1) {
            
            // menu_gubun
            request.setAttribute("menu_gubun", "survey1_add");
            
            RequestDispatcher rd = request.getRequestDispatcher(page);
            rd.forward(request, response);
            
        } else if (url.indexOf("addProc.do") != -1) {
            
            String survey_question = util.toEntityCode(request.getParameter("survey_question"));
            String survey_answer1 = util.toEntityCode(request.getParameter("survey_answer1"));
            String survey_answer2 = util.toEntityCode(request.getParameter("survey_answer2"));
            String survey_answer3 = util.toEntityCode(request.getParameter("survey_answer3"));
            String survey_answer4 = util.toEntityCode(request.getParameter("survey_answer4"));
            String survey_answer5 = util.toEntityCode(request.getParameter("survey_answer5"));
            String survey_status = util.toEntityCode(request.getParameter("survey_status"));
            
            String survey_start_date_yyyy = Integer.toString(util.toNumber(request.getParameter("survey_start_date_yyyy")));
            String survey_start_date_mm = Integer.toString(util.toNumber(request.getParameter("survey_start_date_mm")));
            String survey_start_date_dd = Integer.toString(util.toNumber(request.getParameter("survey_start_date_dd")));
            String survey_start_date_ = survey_start_date_yyyy + "-" + survey_start_date_mm + "-" + survey_start_date_dd;
            survey_start_date_ += " 00:00:00.0";
            Timestamp survey_start_date = Timestamp.valueOf(survey_start_date_);
            
            String survey_end_date_yyyy = Integer.toString(util.toNumber(request.getParameter("survey_end_date_yyyy")));
            String survey_end_date_mm = Integer.toString(util.toNumber(request.getParameter("survey_end_date_mm")));
            String survey_end_date_dd = Integer.toString(util.toNumber(request.getParameter("survey_end_date_dd")));
            String survey_end_date_ = survey_end_date_yyyy + "-" + survey_end_date_mm + "-" + survey_end_date_dd;
            survey_end_date_ += " 23:59:59.999999";
            Timestamp survey_end_date = Timestamp.valueOf(survey_end_date_);
            
            SurveyDTO dto = new SurveyDTO();
            dto.setSurvey_question(survey_question);
            dto.setSurvey_answer1(survey_answer1);
            dto.setSurvey_answer2(survey_answer2);
            dto.setSurvey_answer3(survey_answer3);
            dto.setSurvey_answer4(survey_answer4);
            dto.setSurvey_answer5(survey_answer5);
            dto.setSurvey_status(survey_status);
            dto.setSurvey_start_date(survey_start_date);
            dto.setSurvey_end_date(survey_end_date);
            
            SurveyDAO dao = new SurveyDAO();
            int result = dao.setInsert(dto);
            
            if (result > 0) { // 설문등록 성공
                response.setContentType("text/html; charset=utf-8");
                PrintWriter out = response.getWriter();
                out.println("<script>");
                out.println("alert('정상적으로 등록되었습니다.');");
                out.println("location.href='" + path + "/survey1_servlet/list.do';");
                out.println("</script>");
                return;
            } else { // 설문등록 실패
                response.setContentType("text/html; charset=utf-8");
                PrintWriter out = response.getWriter();
                out.println("<script>");
                out.println("alert('정상적으로 처리되지 않았습니다.');");
                out.println("history.back();");
                out.println("</script>");
                return;
            }
            
        } else if (url.indexOf("view.do") != -1) {
            
            int survey_no = util.toNumber(request.getParameter("survey_no"));
            
            SurveyDAO dao = new SurveyDAO();
            SurveyDTO dto = dao.getSelect(survey_no);
            
            request.setAttribute("dto", dto);
            
            // menu_gubun
            request.setAttribute("menu_gubun", "survey1_view");
            
            RequestDispatcher rd = request.getRequestDispatcher(page);
            rd.forward(request, response);
            
        } else if (url.indexOf("answerProc.do") != -1) {
            
            int survey_no = util.toNumber(request.getParameter("survey_no"));
            int survey_answer = util.toNumber(request.getParameter("survey_answer"));
            
            SurveyAnswerDTO dto = new SurveyAnswerDTO();
            dto.setSurvey_no(survey_no);
            dto.setSurvey_answer(survey_answer);
            
            SurveyAnswerDAO dao = new SurveyAnswerDAO();
            int result = dao.setInsert(dto);
            
            if (result > 0) { // 설문답변 성공
                response.setContentType("text/html; charset=utf-8");
                PrintWriter out = response.getWriter();
                out.println("<script>");
                out.println("alert('답변이 등록되었습니다.');");
                out.println("location.href='" + path + "/survey1_servlet/list.do';");
                out.println("</script>");
                return;
            } else { // 설문답변 실패
                response.setContentType("text/html; charset=utf-8");
                PrintWriter out = response.getWriter();
                out.println("<script>");
                out.println("alert('정상적으로 처리되지 않았습니다.');");
                out.println("history.back();");
                out.println("</script>");
                return;
            }
            
        } else if (url.indexOf("modify.do") != -1) {
            
            int survey_no = util.toNumber(request.getParameter("survey_no"));
            
            SurveyDAO dao = new SurveyDAO();
            SurveyDTO dto = dao.getSelect(survey_no);
            
            request.setAttribute("dto", dto);
            
            // survey_start_date 나누어 담기-------
            int start_y = dto.getSurvey_start_date().toLocalDateTime().getYear();
            int start_m = dto.getSurvey_start_date().toLocalDateTime().getMonthValue();
            int start_d = dto.getSurvey_start_date().toLocalDateTime().getDayOfMonth();
            
            Map<String, Integer> startDateMap = new HashMap<>();
            startDateMap.put("start_y", start_y);
            startDateMap.put("start_m", start_m);
            startDateMap.put("start_d", start_d);
            
            request.setAttribute("startDateMap", startDateMap);
            // ------------------------------------
            
            // survey_end_date 나누어 담기-------
            int end_y = dto.getSurvey_end_date().toLocalDateTime().getYear();
            int end_m = dto.getSurvey_end_date().toLocalDateTime().getMonthValue();
            int end_d = dto.getSurvey_end_date().toLocalDateTime().getDayOfMonth();
            
            Map<String, Integer> endDateMap = new HashMap<>();
            endDateMap.put("end_y", end_y);
            endDateMap.put("end_m", end_m);
            endDateMap.put("end_d", end_d);
            
            request.setAttribute("endDateMap", endDateMap);
            // ------------------------------------
            
            // survey_regi_date 나누어 담기-------
            int regi_y = dto.getSurvey_regi_date().toLocalDateTime().getYear();
            int regi_m = dto.getSurvey_regi_date().toLocalDateTime().getMonthValue();
            int regi_d = dto.getSurvey_regi_date().toLocalDateTime().getDayOfMonth();
            
            Map<String, Integer> regiDateMap = new HashMap<>();
            regiDateMap.put("regi_y", regi_y);
            regiDateMap.put("regi_m", regi_m);
            regiDateMap.put("regi_d", regi_d);
            
            request.setAttribute("regiDateMap", regiDateMap);
            // ------------------------------------
            
            // menu_gubun
            request.setAttribute("menu_gubun", "survey1_modify");
            
            RequestDispatcher rd = request.getRequestDispatcher(page);
            rd.forward(request, response);
            
        } else if (url.indexOf("modifyProc.do") != -1) {
            
            int survey_no = util.toNumber(request.getParameter("survey_no"));
            
            String survey_question = util.toEntityCode(request.getParameter("survey_question"));
            String survey_answer1 = util.toEntityCode(request.getParameter("survey_answer1"));
            String survey_answer2 = util.toEntityCode(request.getParameter("survey_answer2"));
            String survey_answer3 = util.toEntityCode(request.getParameter("survey_answer3"));
            String survey_answer4 = util.toEntityCode(request.getParameter("survey_answer4"));
            String survey_answer5 = util.toEntityCode(request.getParameter("survey_answer5"));
            String survey_status = util.toEntityCode(request.getParameter("survey_status"));
            
            String survey_start_date_yyyy = Integer.toString(util.toNumber(request.getParameter("survey_start_date_yyyy")));
            String survey_start_date_mm = Integer.toString(util.toNumber(request.getParameter("survey_start_date_mm")));
            String survey_start_date_dd = Integer.toString(util.toNumber(request.getParameter("survey_start_date_dd")));
            String survey_start_date_ = survey_start_date_yyyy + "-" + survey_start_date_mm + "-" + survey_start_date_dd;
            survey_start_date_ += " 00:00:00.0";
            Timestamp survey_start_date = Timestamp.valueOf(survey_start_date_);
            
            String survey_end_date_yyyy = Integer.toString(util.toNumber(request.getParameter("survey_end_date_yyyy")));
            String survey_end_date_mm = Integer.toString(util.toNumber(request.getParameter("survey_end_date_mm")));
            String survey_end_date_dd = Integer.toString(util.toNumber(request.getParameter("survey_end_date_dd")));
            String survey_end_date_ = survey_end_date_yyyy + "-" + survey_end_date_mm + "-" + survey_end_date_dd;
            survey_end_date_ += " 23:59:59.999999";
            Timestamp survey_end_date = Timestamp.valueOf(survey_end_date_);
            
            SurveyDTO dto = new SurveyDTO();
            dto.setSurvey_no(survey_no);
            dto.setSurvey_question(survey_question);
            dto.setSurvey_answer1(survey_answer1);
            dto.setSurvey_answer2(survey_answer2);
            dto.setSurvey_answer3(survey_answer3);
            dto.setSurvey_answer4(survey_answer4);
            dto.setSurvey_answer5(survey_answer5);
            dto.setSurvey_status(survey_status);
            dto.setSurvey_start_date(survey_start_date);
            dto.setSurvey_end_date(survey_end_date);
            
            SurveyDAO dao = new SurveyDAO();
            int result = dao.setUpdate(dto);
            
            if (result > 0) { // 설문수정 성공
                response.setContentType("text/html; charset=utf-8");
                PrintWriter out = response.getWriter();
                out.println("<script>");
                out.println("alert('정상적으로 수정되었습니다.');");
                out.println("location.href='" + path + "/survey1_servlet/list.do';");
                out.println("</script>");
                return;
            } else { // 설문수정 실패
                response.setContentType("text/html; charset=utf-8");
                PrintWriter out = response.getWriter();
                out.println("<script>");
                out.println("alert('정상적으로 처리되지 않았습니다.');");
                out.println("history.back();");
                out.println("</script>");
                return;
            }
            
        } else if (url.indexOf("answerSetProc") != -1) {
            
            int k = util.toNumber(request.getParameter("k"));
            
            int errorCount = 0;
            for (int i=0; i<k; i++ ) {
                int tempNo = util.toNumber(request.getParameter(i + "_survey_no"));
                int tempAnswer = util.toNumber(request.getParameter(i + "_survey_answer"));
                
                // System.out.println("tempNo : " + tempNo + " | tempAnswer : " + tempAnswer);
                
                if (tempAnswer > 0) {
                    SurveyAnswerDTO answerDto = new SurveyAnswerDTO();
                    answerDto.setSurvey_no(tempNo);
                    answerDto.setSurvey_answer(tempAnswer);
                    
                    SurveyAnswerDAO dao = new SurveyAnswerDAO();
                    int result = dao.setInsert(answerDto);
                    
                    if (result <= 0) { // 답변 실패
                        errorCount += 1;
                    }
                }
            }
            
            if (errorCount <= 0) { // 설문답변 성공
                response.setContentType("text/html; charset=utf-8");
                PrintWriter out = response.getWriter();
                out.println("<script>");
                out.println("alert('정상적으로 답변되었습니다.');");
                out.println("location.href='" + path + "/survey1_servlet/list.do';");
                out.println("</script>");
                return;
            } else { // 설문답변 실패
                response.setContentType("text/html; charset=utf-8");
                PrintWriter out = response.getWriter();
                out.println("<script>");
                out.println("alert('답변 전체 또는 일부가 정상적으로 처리되지 않았습니다.');");
                out.println("history.back();");
                out.println("</script>");
                return;
            }
        }
        
    }

}
