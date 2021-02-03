<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="common.Util"%>

<%@ page import="java.io.PrintWriter"%>
<%@ page import="org.json.simple.JSONObject"%>

<%
String name = request.getParameter("name");
int kor = Util.toNumber(request.getParameter("kor"));
int eng = Util.toNumber(request.getParameter("eng"));
int mat = Util.toNumber(request.getParameter("mat"));
int sci = Util.toNumber(request.getParameter("sci"));
int his = Util.toNumber(request.getParameter("his"));

int tot = kor + eng + mat + sci + his;
double avg = tot / 5.0;
String grade = "가";
if (avg >= 90) {
    grade = "수";
} else if (avg >= 80) {
    grade = "우";
} else if (avg >= 70) {
    grade = "미";
} else if (avg >= 60) {
    grade = "양";
}

JSONObject jsonobj = new JSONObject();
jsonobj.put("name", name);
jsonobj.put("kor", kor);
jsonobj.put("eng", eng);
jsonobj.put("mat", mat);
jsonobj.put("sci", sci);
jsonobj.put("his", his);
jsonobj.put("tot", tot);
jsonobj.put("avg", avg);
jsonobj.put("grade", grade);

String json_sj = jsonobj.toJSONString();

// System.out.println(json_sj);
// out.println(json_sj);
PrintWriter pw = response.getWriter();
pw.println(json_sj);
%>