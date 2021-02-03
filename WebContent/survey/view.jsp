<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/inc_header.jsp" %>
<span id="span_survey_answer" style="display: none;"></span>
<table border="1" style="width: 480px;">
    <tr>
        <td><h2>설문 보기</h2></td>
    </tr>
    <tr>
        <td>Q) ${ dto.survey_question }<input type="hidden" name="survey_no" id="survey_no" style="width: 100%;" value="${ dto.survey_no }" readonly></td>
    </tr>
    <tr>
        <td>
            <a href="#" onclick="check_survey_answer('1');"><font style="font-family: 'MS Gothic';"><span id="survey_answer1">①</span></font></a>
            <a href="#" onclick="check_survey_answer('1');">${ dto.survey_answer1 }</a>
        </td>
    </tr>
    <tr>
        <td>
            <a href="#" onclick="check_survey_answer('2');"><font style="font-family: 'MS Gothic';"><span id="survey_answer2">②</span></font></a>
            <a href="#" onclick="check_survey_answer('2');">${ dto.survey_answer2 }</a>
        </td>
    </tr>
    <tr>
        <td>
            <a href="#" onclick="check_survey_answer('3');"><font style="font-family: 'MS Gothic';"><span id="survey_answer3">③</span></font></a>
            <a href="#" onclick="check_survey_answer('3');">${ dto.survey_answer3 }</a>
        </td>
    </tr>
    <tr>
        <td>
            <a href="#" onclick="check_survey_answer('4');"><font style="font-family: 'MS Gothic';"><span id="survey_answer4">④</span></font></a>
            <a href="#" onclick="check_survey_answer('4');">${ dto.survey_answer4 }</a>
        </td>
    </tr>
    <tr>
        <td>
            <a href="#" onclick="check_survey_answer('5');"><font style="font-family: 'MS Gothic';"><span id="survey_answer5">⑤</span></font></a>
            <a href="#" onclick="check_survey_answer('5');">${ dto.survey_answer5 }</a>
        </td>
    </tr>
    <tr>
        <td>상태 : ${ dto.survey_status }</td>
    </tr>
    <tr>
        <td>기간 : ${ dto.survey_start_date } ~ ${ dto.survey_end_date }</td>
    </tr>
    <tr>
        <td align="center" style="padding: 10px 20px;">
            <button type="button" id="btnList">목록으로</button>&nbsp;
            <button type="button" id="btnModify">설문 수정</button>&nbsp;
            <button type="button" id="btnAnswer">답변하기</button>
        </td>
    </tr>
</table>
<script>
$(document).ready(function() {
    $("#btnAnswer").click(function() {
        GoAnswerProc();
    });
    $("#btnList").click(function() {
        GoList();
    });
    $("#btnModify").click(function() {
        GoModify(${ dto.survey_no });
    });
});

function check_survey_answer(value1) {
    $("#span_survey_answer").text(value1);
    if (value1 == '1') {
        $("#survey_answer1").text('❶');
        $("#survey_answer2").text('②');
        $("#survey_answer3").text('③');
        $("#survey_answer4").text('④');
        $("#survey_answer5").text('⑤');
    } else if (value1 == '2') {
        $("#survey_answer1").text('①');
        $("#survey_answer2").text('❷');
        $("#survey_answer3").text('③');
        $("#survey_answer4").text('④');
        $("#survey_answer5").text('⑤');
    } else if (value1 == '3') {
        $("#survey_answer1").text('①');
        $("#survey_answer2").text('②');
        $("#survey_answer3").text('❸');
        $("#survey_answer4").text('④');
        $("#survey_answer5").text('⑤');
    } else if (value1 == '4') {
        $("#survey_answer1").text('①');
        $("#survey_answer2").text('②');
        $("#survey_answer3").text('③');
        $("#survey_answer4").text('❹');
        $("#survey_answer5").text('⑤');
    } else if (value1 == '5') {
        $("#survey_answer1").text('①');
        $("#survey_answer2").text('②');
        $("#survey_answer3").text('③');
        $("#survey_answer4").text('④');
        $("#survey_answer5").text('❺');
    }
}

function GoAnswerProc() {
    if ($("#span_survey_answer").text() == '') {
        alert('답변을 선택하세요.');
        return false;
    }
    if (confirm('답변하시겠습니까?')) {
        var param = {
                "survey_no" : ${ dto.survey_no },
                "survey_answer" : $("#span_survey_answer").text()
            }
        $.ajax({
            type: "post",
            data: param,
            url: "${ path }/survey_servlet/answerProc.do",
            success: function() {
                select_page('1');
            }
        });
    }
}
</script>