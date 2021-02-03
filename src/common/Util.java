package common;

import java.util.Calendar;

public class Util {
    
    public static boolean numberCheck(String inputString) { // 숫자면 true, 숫자가 아니면 false
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
    
    public static int toNumber(String inputString) { // numberCheck = true 면 inputString.trim()을 숫자로 변환하여 반환, 아니면 0 반환
        int resultInt = 0;
        if (numberCheck(inputString)) {
                resultInt = Integer.parseInt(inputString.trim().replace(" ", ""));
        }
        return resultInt;
    }
    
    public static boolean numberAndAlphabetCheck(String inputString) { // 숫자와알파벳 이면 true, 아니면 false
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
    
    public static String toNumberAndAlphabet(String inputString) { // numberAndAlphabetCheck = true 면 inputString.trim() 반환, 아니면 "" 반환
        String resultString = "";
        if (numberAndAlphabetCheck(inputString)) {
            resultString = inputString.trim().replace(" ", "");
        }
        return resultString;
    }
    
    public static boolean passwordCheck(String inputString) { // 숫자와알파벳,일정특문 이면 true, 아니면 false
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
    
    public static String toPassword(String inputString) { // passwordCheck = true 면 inputStrng.trim() 반환, 아니면 "" 반환
        String resultString = "";
        if (passwordCheck(inputString)) {
            resultString = inputString.trim().replace(" ", "");
        }
        return resultString;
    }
    
    public static String toEntityCode(String inputString) { // 스크립트용 특수문자를 HTML Entity Code 로 변환
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
    
    public static int[] getDateTime() {
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
    
    public static String list_gubunCheck(String list_gubun) {
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
    
    public static String[] searchCheck(String search_option, String search_data, String search_date_s, String search_date_e, String search_date_check) {
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

}
