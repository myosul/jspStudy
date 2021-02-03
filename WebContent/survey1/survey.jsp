<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/inc_header.jsp" %>
<table border="0">
    <tr>
        <td><h2>설문 조사</h2></td>
    </tr>
    <tr>
        <td style="padding: 10px 0px;">
            전체 ${ totalRecord } 건입니다.
        </td>
    </tr>
    <tr>
        <td>
            <form name="DirForm" id="DirForm">
            <c:set var="k" value="0"></c:set>
            <c:forEach var="dto" items="${ list }">
            <a named="a_${ k }"></a>
            <table border="1" style="width: 640px;">
                <tr>
                    <td>Q) ${ dto.survey_question }<input type="hidden" name="${ k }_survey_no" id="${ k }_survey_no" style="width: 100%;" value="${ dto.survey_no }" readonly></td>
                </tr>
                <tr>
                    <td>
                        &nbsp;&nbsp;<input type="radio" name="${ k }_survey_answer" value="1">${ dto.survey_answer1 }
                    </td>
                </tr>
                <tr>
                    <td>
                        &nbsp;&nbsp;<input type="radio" name="${ k }_survey_answer" value="2">${ dto.survey_answer2 }
                    </td>
                </tr>
                <tr>
                    <td>
                        &nbsp;&nbsp;<input type="radio" name="${ k }_survey_answer" value="3">${ dto.survey_answer3 }
                    </td>
                </tr>
                <tr>
                    <td>
                        &nbsp;&nbsp;<input type="radio" name="${ k }_survey_answer" value="4">${ dto.survey_answer4 }
                    </td>
                </tr>
                <tr>
                    <td>
                        &nbsp;&nbsp;<input type="radio" name="${ k }_survey_answer" value="5">${ dto.survey_answer5 }
                    </td>
                </tr>
            </table>
            <br>
            <c:set var="k" value="${ k = k + 1 }"></c:set>
            </c:forEach>
            <input type="hidden" name="k" id="k" value="${ k }" readonly>
            </form>
        </td>
    </tr>
    <tr>
        <td>&nbsp;</td>
    </tr>
    <c:if test="${ totalRecord > 0 }">
    <tr>
        <td colspan="5" align="center" style="padding: 10px 0px;">
            <table>
                <tr>
                    <td>
                        <c:if test="${ startPage > blockSize }">
                            <a href="#" onclick="GoPage('survey1_survey','1','${ list_gubun }')">[처음]</a>
                        </c:if>
                        <c:if test="${ startPage <= blockSize }">
                            <p style="color: grey;">[처음]</p>
                        </c:if>
                    </td>
                    <td>
                        <c:if test="${ startPage > blockSize }">
                            <a href="#" onclick="GoPage('survey1_survey','${ startPage = blockSize }','${ list_gubun }')">[이전${ blockSize }개]</a>
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
                        <a href="#" onclick="GoPage('survey1_survey','${ i }','${ list_gubun }')">${ i }</a>
                    </td>
                    </c:if>
                    </c:forEach>
                    <td>
                        <c:if test="${ lastPage < totalPage }">
                            <a href="#" onclick="GoPage('survey1_survey','${ startPage + blockSize }','${ list_gubun }')">[다음${ blockSize }개]</a>
                        </c:if>
                        <c:if test="${ lastPage >= totalPage }">
                            <p style="color: grey;">[다음${ blockSize }개]</p>
                        </c:if>
                    </td>
                    <td>
                        <c:if test="${ lastPage < totalPage }">
                            <a href="#" onclick="GoPage('survey1_survey','${ totalPage }','${ list_gubun }')">[마지막]</a>
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
            <button type="button" onclick="GoPage('survey1_list','','all')">설문 목록</button>&nbsp;
            <button type="button" onclick="GoPage('survey1_survey','','all')">전체 설문</button>&nbsp;
            <button type="button" onclick="GoPage('survey1_survey','','end')">종료된 설문</button>&nbsp;
            <button type="button" onclick="GoPage('survey1_survey','','ing')">진행중인 설문</button>&nbsp;
            <button type="button" onclick="answerSetProc()">답변 저장</button>
        </td>
    </tr>
</table>
<script>
function answerSetProc() {
    if (confirm('답변하시겠습니까?')) {
        DirForm.method = 'post';
        DirForm.action = '${ path }/survey1_servlet/answerSetProc.do';
        DirForm.submit();
    }
}
</script>