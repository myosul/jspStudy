<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/inc_header.jsp" %>
<div style="display: none;">
start_y : ${ startDateMap['start_y'] }<br>
start_m : ${ startDateMap['start_m'] }<br>
start_d : ${ startDateMap['start_d'] }<br>
end_y : ${ endDateMap['end_y'] }<br>
end_m : ${ endDateMap['end_m'] }<br>
end_d : ${ endDateMap['end_d'] }<br>
regi_y : ${ regiDateMap['regi_y'] }<br>
regi_m : ${ regiDateMap['regi_m'] }<br>
regi_d : ${ regiDateMap['regi_d'] }<br>
</div>
<form name="DirForm" id="DirForm">
<table border="1" style="width: 480px;">
    <tr>
        <td colspan="2"><h2>설문 수정</h2></td>
    </tr>
    <tr>
        <td style="align: center;">question</td>
        <td>
            <input type="text" name="survey_question" id="survey_question" style="width: 100%;" value="${ dto.survey_question }">
            <input type="hidden" name="survey_no" id="survey_no" style="width: 100%;" value="${ dto.survey_no }" readonly>
        </td>
    </tr>
    <tr>
        <td style="align: center;">answer1</td>
        <td><input type="text" name="survey_answer1" id="survey_answer1" style="width: 100%;" value="${ dto.survey_answer1 }"></td>
    </tr>
    <tr>
        <td style="align: center;">answer2</td>
        <td><input type="text" name="survey_answer2" id="survey_answer2" style="width: 100%;" value="${ dto.survey_answer2 }"></td>
    </tr>
    <tr>
        <td style="align: center;">answer3</td>
        <td><input type="text" name="survey_answer3" id="survey_answer3" style="width: 100%;" value="${ dto.survey_answer3 }"></td>
    </tr>
    <tr>
        <td style="align: center;">answer4</td>
        <td><input type="text" name="survey_answer4" id="survey_answer4" style="width: 100%;" value="${ dto.survey_answer4 }"></td>
    </tr>
    <tr>
        <td style="align: center;">answer5</td>
        <td><input type="text" name="survey_answer5" id="survey_answer5" style="width: 100%;" value="${ dto.survey_answer5 }"></td>
    </tr>
    <tr>
        <td style="align: center;">status</td>
        <td>
            <c:if test="${ dto.survey_status == 1 }">
            <input type="radio" name="survey_status" id="survey_status" value="1" checked>진행중&nbsp;
            <input type="radio" name="survey_status" id="survey_status" value="0">종료&nbsp;
            </c:if>
            <c:if test="${ dto.survey_status == 0 }">
            <input type="radio" name="survey_status" id="survey_status" value="1">진행중&nbsp;
            <input type="radio" name="survey_status" id="survey_status" value="0" checked>종료&nbsp;
            </c:if>
        </td>
    </tr>
    <tr>
        <td>start_date</td>
        <td>
            <select name="survey_start_date_yyyy" id="survey_start_date_yyyy">
                <c:forEach var="i" begin="${ regiDateMap['regi_y'] - 2 }" end="${ regiDateMap['regi_y'] + 2 }" step="1">
                <c:if test="${ i == startDateMap['start_y'] }">
                <option value="${ i }" selected>${ i }</option>
                </c:if>
                <c:if test="${ i != startDateMap['start_y'] }">
                <option value="${ i }">${ i }</option>
                </c:if>
                </c:forEach>
            </select>
            년
            <select name="survey_start_date_mm" id="survey_start_date_mm">
                <c:forEach var="i" begin="1" end="12" step="1">
                <c:if test="${ i < 10 }">
                    <c:if test="${ i == startDateMap['start_m'] }">
                    <option value="0${ i }" selected>0${ i }</option>
                    </c:if>
                    <c:if test="${ i != startDateMap['start_m'] }">
                    <option value="0${ i }">0${ i }</option>
                    </c:if>
                </c:if>
                <c:if test="${ i >= 10 }">
                    <c:if test="${ i == startDateMap['start_m'] }">
                    <option value="${ i }" selected>${ i }</option>
                    </c:if>
                    <c:if test="${ i != startDateMap['start_m'] }">
                    <option value="${ i }">${ i }</option>
                    </c:if>
                </c:if>
                </c:forEach>
            </select>
            월
            <select name="survey_start_date_dd" id="survey_start_date_dd">
                <c:forEach var="i" begin="1" end="31" step="1">
                <c:if test="${ i < 10 }">
                    <c:if test="${ i == startDateMap['start_d'] }">
                    <option value="0${ i }" selected>0${ i }</option>
                    </c:if>
                    <c:if test="${ i != startDateMap['start_d'] }">
                    <option value="0${ i }">0${ i }</option>
                    </c:if>
                </c:if>
                <c:if test="${ i >= 10 }">
                    <c:if test="${ i == startDateMap['start_d'] }">
                    <option value="${ i }" selected>${ i }</option>
                    </c:if>
                    <c:if test="${ i != startDateMap['start_d'] }">
                    <option value="${ i }">${ i }</option>
                    </c:if>
                </c:if>
                </c:forEach>
            </select>
            일
        </td>
    </tr>
    <tr>
        <td>end_date</td>
        <td>
            <select name="survey_end_date_yyyy" id="survey_end_date_yyyy">
                <c:forEach var="i" begin="${ regiDateMap['regi_y'] - 2 }" end="${ regiDateMap['regi_y'] + 2 }" step="1">
                <c:if test="${ i == endDateMap['end_y'] }">
                <option value="${ i }" selected>${ i }</option>
                </c:if>
                <c:if test="${ i != endDateMap['end_y'] }">
                <option value="${ i }">${ i }</option>
                </c:if>
                </c:forEach>
            </select>
            년
            <select name="survey_end_date_mm" id="survey_end_date_mm">
                <c:forEach var="i" begin="1" end="12" step="1">
                <c:if test="${ i < 10 }">
                    <c:if test="${ i == endDateMap['end_m'] }">
                    <option value="0${ i }" selected>0${ i }</option>
                    </c:if>
                    <c:if test="${ i != endDateMap['end_m'] }">
                    <option value="0${ i }">0${ i }</option>
                    </c:if>
                </c:if>
                <c:if test="${ i >= 10 }">
                    <c:if test="${ i == endDateMap['end_m'] }">
                    <option value="${ i }" selected>${ i }</option>
                    </c:if>
                    <c:if test="${ i != endDateMap['end_m'] }">
                    <option value="${ i }">${ i }</option>
                    </c:if>
                </c:if>
                </c:forEach>
            </select>
            월
            <select name="survey_end_date_dd" id="survey_end_date_dd">
                <c:forEach var="i" begin="1" end="31" step="1">
                <c:if test="${ i < 10 }">
                    <c:if test="${ i == endDateMap['end_d'] }">
                    <option value="0${ i }" selected>0${ i }</option>
                    </c:if>
                    <c:if test="${ i != endDateMap['end_d'] }">
                    <option value="0${ i }">0${ i }</option>
                    </c:if>
                </c:if>
                <c:if test="${ i >= 10 }">
                    <c:if test="${ i == endDateMap['end_d'] }">
                    <option value="${ i }" selected>${ i }</option>
                    </c:if>
                    <c:if test="${ i != endDateMap['end_d'] }">
                    <option value="${ i }">${ i }</option>
                    </c:if>
                </c:if>
                </c:forEach>
            </select>
            일
        </td>
    </tr>
    <tr>
        <td colspan="2" align="center" style="padding: 10px 20px;">
            <button type="button" id="btnList">목록으로</button>&nbsp;
            <button type="button" id="btnModifyProc">수정하기</button>
        </td>
    </tr>
</table>
</form>
<script>
$(document).ready(function() {
    $("#btnModifyProc").click(function() {
        GoModifyProc();
    });
    $("#btnList").click(function() {
        GoList();
    });
});

function GoModifyProc() {
    if (DirForm.survey_question.value == '') {
        alert('question 을 입력하세요.');
        DirForm.survey_question.focus();
        return false;
    }
    if (DirForm.survey_answer1.value == '') {
        alert('answer1 을 입력하세요.');
        DirForm.survey_answer1.focus();
        return false;
    }
    if (DirForm.survey_answer2.value == '') {
        alert('answer2 을 입력하세요.');
        DirForm.survey_answer2.focus();
        return false;
    }
    if (DirForm.survey_answer3.value == '') {
        alert('answer3 을 입력하세요.');
        DirForm.survey_answer3.focus();
        return false;
    }
    if (DirForm.survey_answer4.value == '') {
        alert('answer4 을 입력하세요.');
        DirForm.survey_answer4.focus();
        return false;
    }
    if (DirForm.survey_answer5.value == '') {
        alert('answer5 을 입력하세요.');
        DirForm.survey_answer5.focus();
        return false;
    }
    if (confirm('수정하시겠습니까?')) {
        $.ajax({
            type: "post",
            data: $("#DirForm").serialize(),
            url: "${ path }/survey_servlet/modifyProc.do",
            success: function() {
                select_page('1');
            }
        });
    }
}
</script>