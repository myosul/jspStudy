<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/inc_header.jsp" %>
<form name="DirForm" id="DirForm">
<table border="1" style="width: 480px;">
    <tr>
        <td><h2>설문 보기</h2></td>
    </tr>
    <tr>
        <td>Q) ${ dto.survey_question }<input type="hidden" name="survey_no" id="survey_no" style="width: 100%;" value="${ dto.survey_no }" readonly></td>
    </tr>
    <tr>
        <td>
            &nbsp;&nbsp;<input type="radio" name="survey_answer" value="1">${ dto.survey_answer1 }
        </td>
    </tr>
    <tr>
        <td>
            &nbsp;&nbsp;<input type="radio" name="survey_answer" value="2">${ dto.survey_answer2 }
        </td>
    </tr>
    <tr>
        <td>
            &nbsp;&nbsp;<input type="radio" name="survey_answer" value="3">${ dto.survey_answer3 }
        </td>
    </tr>
    <tr>
        <td>
            &nbsp;&nbsp;<input type="radio" name="survey_answer" value="4">${ dto.survey_answer4 }
        </td>
    </tr>
    <tr>
        <td>
            &nbsp;&nbsp;<input type="radio" name="survey_answer" value="5">${ dto.survey_answer5 }
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
            <button type="button" onclick="GoPage('survey1_list', '', '')">목록으로</button>&nbsp;
            <button type="button" onclick="GoPage('survey1_modify', '', '${ dto.survey_no }')">설문 수정</button>&nbsp;
            <button type="button" onclick="answerProc()">답변하기</button>
        </td>
    </tr>
</table>
</form>
<script>
function answerProc() {
    if (DirForm.survey_answer.value == '') {
        alert('답변을 선택하세요.');
        DirForm.survey_answer.focus();
        return false;
    }
    if (confirm('답변하시겠습니까?')) {
        DirForm.method = 'post';
        DirForm.action = '${ path }/survey1_servlet/answerProc.do';
        DirForm.submit();
    }
}
</script>