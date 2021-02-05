package common;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.Calendar;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class Util {
    
    public boolean numberCheck(String inputString) { // 숫자면 true, 숫자가 아니면 false
        boolean result = false;
        if (inputString != null && inputString.trim().replace(" ", "").length() > 0) {
            inputString = inputString.trim().replace(" ", "");
            int errorCount = 0;
            char oneChar;
            for (int i=0; i < inputString.length(); i++) {
                oneChar = inputString.charAt(i);
                if (!(oneChar >= '0' && oneChar <= '9')) {
                    errorCount++;
                }
            }
            if (errorCount <= 0) {
                result = true;
            }
        }
        return result;
    }
    
    public int toNumber(String inputString) { // numberCheck = true 면 inputString.trim()을 숫자로 변환하여 반환, 아니면 0 반환
        int resultInt = 0;
        if (numberCheck(inputString)) {
                resultInt = Integer.parseInt(inputString.trim().replace(" ", ""));
        }
        return resultInt;
    }
    
    public int toNumber(String inputString, int defaultInt) { // numberCheck = true 면 inputString.trim()을 숫자로 변환하여 반환, 아니면 0 반환
        int resultInt = defaultInt;
        if (numberCheck(inputString)) {
                resultInt = Integer.parseInt(inputString.trim().replace(" ", ""));
        }
        return resultInt;
    }
    
    public boolean numberAndAlphabetCheck(String inputString) { // 숫자와알파벳 이면 true, 아니면 false
        boolean result = false;
        if (inputString != null && inputString.trim().replace(" ", "").length() > 0) {
            inputString = inputString.trim().replace(" ", "");
            int errorCount = 0;
            char oneChar;
            for (int i=0; i < inputString.length(); i++) {
                oneChar = inputString.charAt(i);
                if (!(oneChar >= '0' && oneChar <= '9') && !(oneChar >= 'a' && oneChar <= 'z') && !(oneChar >= 'A' && oneChar <= 'Z')) {
                    errorCount++;
                }
            }
            if (errorCount <= 0) {
                result = true;
            }
        }
        return result;
    }
    
    public String toNumberAndAlphabet(String inputString) { // numberAndAlphabetCheck = true 면 inputString.trim() 반환, 아니면 "" 반환
        String resultString = "";
        if (numberAndAlphabetCheck(inputString)) {
            resultString = inputString.trim().replace(" ", "");
        }
        return resultString;
    }
    
    public boolean passwordCheck(String inputString) { // 숫자와알파벳,일정특문 이면 true, 아니면 false
        boolean result = false;
        if (inputString != null && inputString.trim().replace(" ", "").length() > 0) {
            inputString = inputString.trim().replace(" ", "");
            int errorCount = 0;
            char oneChar;
            for (int i=0; i < inputString.length(); i++) {
                oneChar = inputString.charAt(i);
                if (!(oneChar >= '0' && oneChar <= '9') && !(oneChar >= 'a' && oneChar <= 'z') && !(oneChar >= 'A' && oneChar <= 'Z') 
                        && oneChar != '!' && oneChar != '@' && oneChar != '#' && oneChar != '$' && oneChar != '%'
                        && oneChar != '^' && oneChar != '*' && oneChar != '+' && oneChar != '=' && oneChar != '-') {
                    errorCount++;
                }
            }
            if (errorCount <= 0) {
                result = true;
            }
        }
        return result;
    }
    
    public String toPassword(String inputString) { // passwordCheck = true 면 inputStrng.trim() 반환, 아니면 "" 반환
        String resultString = "";
        if (passwordCheck(inputString)) {
            resultString = inputString.trim().replace(" ", "");
        }
        return resultString;
    }
    
    public String toEntityCode(String inputString) { // 스크립트용 특수문자를 HTML Entity Code 로 변환
        String resultString = "";
        if (inputString != null && inputString.length() > 0) {
            inputString = inputString.replace("<", "&lt;");
            inputString = inputString.replace(">", "&gt;");
            inputString = inputString.replace("&", "&amp;");
            inputString = inputString.replace("\"", "&quot;");
            inputString = inputString.replace("'", "&apos;");
            resultString = inputString;
        }
        return resultString;
    }
    
    public int[] getDateTime() {
        int[] result = new int[6];
        Calendar cal = Calendar.getInstance();
        result[0] = cal.get(Calendar.YEAR);
        result[1] = cal.get(Calendar.MONTH) + 1;
        result[2] = cal.get(Calendar.DAY_OF_MONTH);
        result[3] = cal.get(Calendar.HOUR_OF_DAY);
        result[4] = cal.get(Calendar.MINUTE);
        result[5] = cal.get(Calendar.SECOND);
        return result;
    }
    
    public String list_gubunCheck(String list_gubun) {
        String result = "all";
        if (list_gubun != null && list_gubun.trim().length() > 0) {
            list_gubun = list_gubun.trim();
            if (list_gubun.equals("all")) {
                result = list_gubun;
            } else if (list_gubun.equals("ing")) {
                result = list_gubun;
            } else if (list_gubun.equals("end")) {
                result = list_gubun;
            }
        }
        return result;
    }
    
    public String[] searchCheck(String search_option, String search_data, String search_date_s, String search_date_e, String search_date_check) {
        String[] result = new String[5];
        result[0] = "";
        result[1] = "";
        result[2] = "";
        result[3] = "";
        result[4] = "";
        if (search_option != null && search_option.trim().length() > 0) {
            search_option = search_option.trim();
            if (search_option.equals("survey_question")) {
                result[0] = search_option;
            }
        }
        if (search_data != null && search_data.trim().length() > 0) {
            result[1] = search_data.trim();
        }
        if (search_date_check != null && search_date_check.trim().length() > 0) {
            search_date_check = search_date_check.trim();
            if (search_date_check.equals("O")) {
                result[4] = search_date_check;
            }
        }
        if ((search_date_s != null && search_date_s.trim().length() > 0) && (search_date_e != null && search_date_e.trim().length() > 0)) {
            result[2] = search_date_s.trim();
            result[3] = search_date_e.trim();
        }
        return result;
    }
    
    public String[] getServerInfo(HttpServletRequest request) throws UnknownHostException {
        String[] result = new String[6];
        String referer = request.getHeader("REFERER");
        if (referer == null || referer.trim().equals("")) {
            referer = "";
        }
        String path = request.getContextPath();
        String url = request.getRequestURL().toString();
        String uri = request.getRequestURI().toString();
        String ip = Inet4Address.getLocalHost().getHostAddress();
        String ip6 = "";
        
        result[0] = referer;
        result[1] = path;
        result[2] = url;
        result[3] = uri;
        result[4] = ip;
        result[5] = ip6;
        
        return result;
    }
    
    public String[] sessionCheck(HttpServletRequest request) {
        String[] result = new String[3];
        
        HttpSession session = request.getSession();
        
        int cookNo = 0;
        if (session.getAttribute("cookNo") != null) {
            cookNo = (Integer)session.getAttribute("cookNo");
        }
        
        String cookId = "";
        if (session.getAttribute("cookId") != null) {
            cookId = (String)session.getAttribute("cookId");
        }
        
        String cookName = "";
        if (session.getAttribute("cookId") != null) {
            cookName = (String)session.getAttribute("cookName");
        }
        
        result[0] = cookNo + "";
        result[1] = cookId;
        result[2] = cookName;
        
        return result;
    }
    
    public int[] pager(int pageSize, int blockSize, int totalRecord, int pageNumber) {
        int[] result = new int[6];
        
        int recordNum = totalRecord - pageSize * (pageNumber - 1); // 화면상에 보여질 일련번호 (행 개수 기준)
        int startRecord = pageSize * (pageNumber -1) + 1; // 시작 행
        int lastRecord = pageSize * pageNumber; // 마지막 행
        if (lastRecord > totalRecord) {
            lastRecord = totalRecord;
        }
        
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
        
        result[0] = recordNum;
        result[1] = startRecord;
        result[2] = lastRecord;
        result[3] = totalPage;
        result[4] = startPage;
        result[5] = lastPage;
        
        return result;
    }

}
