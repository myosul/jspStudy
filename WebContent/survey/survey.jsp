<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/inc_header.jsp" %>
<table border="0">
    <tr>
        <td><h2>설문 조사</h2></td>
    </tr>
    <tr>
        <td style="padding: 10px 0px;">
            span_list_size : <span id="span_list_size" style="display: ;">${ list.size() }</span><br>
            span_survey_answer_set : <span id="span_survey_answer_set" style="display: ;"></span><br>
            전체 ${ totalRecord } 건입니다.
        </td>
    </tr>
    <c:set var="k" value="0"></c:set>
    <c:forEach var="dto" items="${ list }">
    <tr>
        <td>
            <a named="a_${ k }"></a>
            <span id="span_survey_question_${ k }" style="display: ;">${ dto.survey_no }</span><br>
            <span id="span_survey_answer_${ k }" style="display: ;"></span><br>
            <table border="1" style="width: 640px;">
                <tr>
                    <td>Q) ${ dto.survey_question }</td>
                </tr>
                <tr>
                    <td>
                        <a href="#a_${ k }" onclick="check_survey_answer_set('${ k }', '1');"><font style="font-family: 'MS Gothic';"><span id="${ k }_survey_answer1">①</span></font></a>
                        <a href="#a_${ k }" onclick="check_survey_answer_set('${ k }', '1');">${ dto.survey_answer1 }</a>
                    </td>
                </tr>
                <tr>
                    <td>
                        <a href="#a_${ k }" onclick="check_survey_answer_set('${ k }', '2');"><font style="font-family: 'MS Gothic';"><span id="${ k }_survey_answer2">②</span></font></a>
                        <a href="#a_${ k }" onclick="check_survey_answer_set('${ k }', '2');">${ dto.survey_answer2 }</a>
                    </td>
                </tr>
                <tr>
                    <td>
                        <a href="#a_${ k }" onclick="check_survey_answer_set('${ k }', '3');"><font style="font-family: 'MS Gothic';"><span id="${ k }_survey_answer3">③</span></font></a>
                        <a href="#a_${ k }" onclick="check_survey_answer_set('${ k }', '3');">${ dto.survey_answer3 }</a>
                    </td>
                </tr>
                <tr>
                    <td>
                        <a href="#a_${ k }" onclick="check_survey_answer_set('${ k }', '4');"><font style="font-family: 'MS Gothic';"><span id="${ k }_survey_answer4">④</span></font></a>
                        <a href="#a_${ k }" onclick="check_survey_answer_set('${ k }', '4');">${ dto.survey_answer4 }</a>
                    </td>
                </tr>
                <tr>
                    <td>
                        <a href="#a_${ k }" onclick="check_survey_answer_set('${ k }', '5');"><font style="font-family: 'MS Gothic';"><span id="${ k }_survey_answer5">⑤</span></font></a>
                        <a href="#a_${ k }" onclick="check_survey_answer_set('${ k }', '5');">${ dto.survey_answer5 }</a>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td>&nbsp;</td>
    </tr>
    <c:set var="k" value="${ k = k + 1 }"></c:set>
    </c:forEach>
    <c:if test="${ totalRecord > 0 }">
    <tr>
        <td colspan="5" align="center" style="padding: 10px 0px;">
            <table>
                <tr>
                    <td>
                        <c:if test="${ startPage > blockSize }">
                            <a href="#" onclick="select_survey_page('1')">[처음]</a>
                        </c:if>
                        <c:if test="${ startPage <= blockSize }">
                            <p style="color: grey;">[처음]</p>
                        </c:if>
                    </td>
                    <td>
                        <c:if test="${ startPage > blockSize }">
                            <a href="#" onclick="select_survey_page(${ startPage = blockSize })">[이전${ blockSize }개]</a>
                        </c:if>
                        <c:if test="${ startPage <= blockSize }">
                            <p style="color: grey;">[이전${ blockSize }개]</p>
                        </c:if>
                    </td>
                    <c:forEach var="i" begin="${ startPage }" end="${ lastPage }" step="1">
                    <c:if test="${ i == pageNumber }">
                    <td>
                        [${ i }]
                    </td>
                    </c:if>
                    <c:if test="${ i != pageNumber }">
                    <td>
                        <a href="#" onclick="select_survey_page(${ i })">${ i }</a>
                    </td>
                    </c:if>
                    </c:forEach>
                    <td>
                        <c:if test="${ lastPage < totalPage }">
                            <a href="#" onclick="select_survey_page(${ startPage + blockSize })">[다음${ blockSize }개]</a>
                        </c:if>
                        <c:if test="${ lastPage >= totalPage }">
                            <p style="color: grey;">[다음${ blockSize }개]</p>
                        </c:if>
                    </td>
                    <td>
                        <c:if test="${ lastPage < totalPage }">
                            <a href="#" onclick="select_survey_page(${ totalPage })">[마지막]</a>
                        </c:if>
                        <c:if test="${ lastPage >= totalPage }">
                            <p style="color: grey;">[마지막]</p>
                        </c:if>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
    </c:if>
    <tr>
        <td align="right" style="padding: 10px 20px;">
            <button type="button" onclick="select_list('all')">설문 목록</button>&nbsp;
            <button type="button" onclick="select_survey('all')">전체 설문</button>&nbsp;
            <button type="button" onclick="select_survey('end')">종료된 설문</button>&nbsp;
            <button type="button" onclick="select_survey('ing')">진행중인 설문</button>&nbsp;
            <button type="button" id="btnAnswerSet">답변 저장</button>
        </td>
    </tr>
</table>
<script>
$(document).ready(function() {
    $("#btnAnswerSet").click(function() {
        GoAnswerSetProc();
    });
});

function check_survey_answer_set(value1, value2) {
    $("#span_survey_answer_" + value1).text(value2);
    
    if (value2 == '1') {
        $("#" + value1 + "_survey_answer1").text('❶');
        $("#" + value1 + "_survey_answer2").text('②');
        $("#" + value1 + "_survey_answer3").text('③');
        $("#" + value1 + "_survey_answer4").text('④');
        $("#" + value1 + "_survey_answer5").text('⑤');
    } else if (value2 == '2') {
        $("#" + value1 + "_survey_answer1").text('①');
        $("#" + value1 + "_survey_answer2").text('❷');
        $("#" + value1 + "_survey_answer3").text('③');
        $("#" + value1 + "_survey_answer4").text('④');
        $("#" + value1 + "_survey_answer5").text('⑤');
    } else if (value2 == '3') {
        $("#" + value1 + "_survey_answer1").text('①');
        $("#" + value1 + "_survey_answer2").text('②');
        $("#" + value1 + "_survey_answer3").text('❸');
        $("#" + value1 + "_survey_answer4").text('④');
        $("#" + value1 + "_survey_answer5").text('⑤');
    } else if (value2 == '4') {
        $("#" + value1 + "_survey_answer1").text('①');
        $("#" + value1 + "_survey_answer2").text('②');
        $("#" + value1 + "_survey_answer3").text('③');
        $("#" + value1 + "_survey_answer4").text('❹');
        $("#" + value1 + "_survey_answer5").text('⑤');
    } else if (value2 == '5') {
        $("#" + value1 + "_survey_answer1").text('①');
        $("#" + value1 + "_survey_answer2").text('②');
        $("#" + value1 + "_survey_answer3").text('③');
        $("#" + value1 + "_survey_answer4").text('④');
        $("#" + value1 + "_survey_answer5").text('❺');
    }
    
    var counter = parseInt($("#span_list_size").text());
    var msg = "";
    for (i=0; i<counter; i++) {
        var survey_question = $("#span_survey_question_" + i).text();
        var survey_answer = $("#span_survey_answer_" + i).text();
        if (survey_answer.length > 0) {
            if (msg == '') {
                msg = survey_question + ":" + survey_answer;
            } else {
                msg = msg + "|" + survey_question + ":" + survey_answer;
            }
        }
    }
    $("#span_survey_answer_set").text(msg);
}

function GoAnswerSetProc() {
    if (confirm('답변하시겠습니까?')) {
        var param = {
            "survey_answer_set" : $("#span_survey_answer_set").text()
        }
        $.ajax({
            type: "post",
            data: param,
            url: "${ path }/survey_servlet/answerSetProc.do",
            success: function() {
                select_page('1');
            }
        });
    }
}
</script>