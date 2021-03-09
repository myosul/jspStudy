package chart.common;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import common.Util;

public class UtilChart extends Util {
    
    public void fileDelete(HttpServletRequest request, String dir) {
        if (dir.trim().equals("")) {
            return;
        }
        
        // Calendar 객체 생성
        Calendar cal = Calendar.getInstance();
        long todayMil = cal.getTimeInMillis(); // 현재시간(밀리세컨드)
        long oneDayMil = 24*60*60*1000; // 일 단위
        
        Calendar fileCal = Calendar.getInstance();
        Date fileDate = null;
        
        File path = new File(dir); // c:\test\
        File[] list = path.listFiles(); // 파일 리스트 가져오기
        
        for (int j=0; j<list.length; j++) {
            // 파일의 마지막 수정시간 가져오기
            fileDate = new Date(list[j].lastModified());
            
            // 현재시간과 파일 수정시간 시간차 계산(단위 : 밀리 세컨드)
            fileCal.setTime(fileDate);
            long diffMil = todayMil - fileCal.getTimeInMillis();
            // System.out.println("diffMil:" + diffMil);
            
            // 날짜로 계산
            int diffDay = (int)(diffMil/oneDayMil);
            
            // 3일이 지난 파일 삭제
            if (diffDay > 3 && list[j].exists()) {
                list[j].delete();
                // System.out.println(list[j].getName() + " 파일을 삭제했습니다.");
            }
            
            if (diffMil > 0 && list[j].exists()) { // 1000000
                list[j].delete();
                // System.out.println(list[j].getName() + " 파일을 삭제했습니다.");
            }
            
        }
    }

}
