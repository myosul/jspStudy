<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!-- jquery 라이브러리 로딩 -->
<script src="http://code.jquery.com/jquery-3.3.1.min.js"></script>
<title>Insert title here</title>
</head>
<body>
    <table style="width: 320px;">
        <tr>
            <td colspan="2"><h2>성적 입력</h2></td>
        </tr>
        <tr>
            <td style="width: 60px;">이름</td>
            <td><input type="text" name="name" id="name" value=""></td>
        </tr>
        <tr>
            <td>국어</td>
            <td><input type="number" name="kor" id="kor" value=""></td>
        </tr>
        <tr>
            <td>영어</td>
            <td><input type="number" name="eng" id="eng" value=""></td>
        </tr>
        <tr>
            <td>수학</td>
            <td><input type="number" name="mat" id="mat" value=""></td>
        </tr>
        <tr>
            <td>과학</td>
            <td><input type="number" name="sci" id="sci" value=""></td>
        </tr>
        <tr>
            <td>역사</td>
            <td><input type="number" name="his" id="his" value=""></td>
        </tr>
        <tr>
            <td colspan="2" style="padding: 20px 0px;">
                <button type="button" id="btnSave">입력하기</button>
            </td>
        </tr>
    </table>
    <div style="display: ;">
    <hr>
    name : <span id="json_name"></span><br>
    kor : <span id="json_kor"></span><br>
    eng : <span id="json_eng"></span><br>
    mat : <span id="json_mat"></span><br>
    sci : <span id="json_sci"></span><br>
    his : <span id="json_his"></span><br>
    tot : <span id="json_tot"></span><br>
    avg : <span id="json_avg"></span><br>
    grade : <span id="json_grade"></span><br>
    <br>
    <span id="result_json"></span>
    </div>
</body>
<script>
$(document).ready(function() {
    $("#btnSave").click(function() {
        json01Proc();
    });
});

function json01Proc() {
    var param = {
            "name" : $("#name").val(),
            "kor" : $("#kor").val(),
            "eng" : $("#eng").val(),
            "mat" : $("#mat").val(),
            "sci" : $("#sci").val(),
            "his" : $("#his").val()
    }
    
    var url = "json02.jsp";
    $.ajax({
        type : "post",
        data : param,
        datatype : "json",
        url : url,
        success : function(result) {
            $("#result_json").html(result);
            
            var json_sj = JSON.parse(result);
            $("#json_name").text(json_sj.name);
            $("#json_kor").text(json_sj.kor);
            $("#json_eng").text(json_sj.eng);
            $("#json_mat").text(json_sj.mat);
            $("#json_sci").text(json_sj.sci);
            $("#json_his").text(json_sj.his);
            $("#json_tot").text(json_sj.tot);
            $("#json_avg").text(json_sj.avg);
            $("#json_grade").text(json_sj.grade);
        }
    });
}
</script>
</html>