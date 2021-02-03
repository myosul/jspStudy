<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/inc_header.jsp" %>
<table border="0">
    <tr>
        <td colspan="5"><h2>설문조사 목록</h2></td>
    </tr>
    <tr>
        <td colspan="5">
            <form name="searchForm" id="searchForm">
            <select name="search_option" id="search_option">
            <c:choose>
                <c:when test="${ search_option == 'survey_question' }">
                    <option value="">- 선택 -</option>
                    <option value="survey_question" selected>질문내용</option>
                </c:when>
                <c:otherwise>
                    <option value="">- 선택 -</option>
                    <option value="survey_question">질문내용</option>
                </c:otherwise>
            </c:choose>
            </select>
            <input type="text" name="search_data" id="search_data" value="${ search_data }" style="width: 150px;">&nbsp;
            <input type="date" name="search_date_s" id="search_date_s" value="${ search_date_s }">&nbsp;~
            <input type="date" name="search_date_e" id="search_date_e" value="${ search_date_e }">
            <br>
            <c:choose>
                <c:when test="${ search_date_check == 'O' }">
                    <input type="checkbox" name="search_date_check" id="search_date_check" value="O" checked>
                </c:when>
                <c:otherwise>
                    <input type="checkbox" name="search_date_check" id="search_date_check" value="O">
                </c:otherwise>
            </c:choose>
            <span style="color: blue; font-size: small;">(날짜 검색시 체크)</span>&nbsp;
            <input type="button" value="검색" onclick="search();">
            </form>
        </td>
    </tr>
    <tr>
        <td colspan="5" style="padding: 10px 0px;">
            전체 ${ totalRecord } 건입니다.
        </td>
    </tr>
    <tr>
        <td>
            <table border="1" width="640px">
                <tr>
                    <td style="width: 48px;">순번</td>
                    <td>질문</td>
                    <td style="width: 180px;">기간</td>
                    <td style="width: 60px;">참여수</td>
                    <td style="width: 48px;">상태</td>
                </tr>
                <c:if test="${ list.size() <= 0 }">
                <tr>
                    <td colspan="5" align="center" height="100">등록된 설문이 없습니다.</td>
                </tr>
                </c:if>
                <c:forEach var="dto" items="${ list }">
                <tr>
                    <td>${ recordNum }</td>
                    <td><a href="#" onclick="GoPage('survey1_view', '${ pageNumber }', '${ dto.survey_no }')">${ dto.survey_question }</a></td>
                    <td>${ dto.survey_start_date }<br>${ dto.survey_end_date }</td>
                    <td>${ dto.survey_counter }</td>
                    <td>${ dto.survey_status }</td>
                </tr>
                <c:set var="recordNum" value="${ recordNum = recordNum - 1 }"></c:set>
                </c:forEach>
            </table>
        </td>
    </tr>
    <c:if test="${ totalRecord > 0 }">
    <tr>
        <td colspan="6" align="center">
            <table>
                <tr>
                    <td>
                        <c:if test="${ startPage > blockSize }">
                            <a href="#" onclick="GoPage('survey1_search_list', '1', '${ list_gubun }')">[처음]</a>
                        </c:if>
                        <c:if test="${ startPage <= blockSize }">
                            <p style="color: grey;">[처음]</p>
                        </c:if>
                    </td>
                    <td>
                        <c:if test="${ startPage > blockSize }">
                            <a href="#" onclick="GoPage('survey1_search_list', '${ startPage = blockSize }', '${ list_gubun }')">[이전${ blockSize }개]</a>
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
                        <a href="#" onclick="GoPage('survey1_search_list', '${ i }', '${ list_gubun }')">${ i }</a>
                    </td>
                    </c:if>
                    </c:forEach>
                    <td>
                        <c:if test="${ lastPage < totalPage }">
                            <a href="#" onclick="GoPage('survey1_search_list', '${ startPage + blockSize }', '${ list_gubun }')">[다음${ blockSize }개]</a>
                        </c:if>
                        <c:if test="${ lastPage >= totalPage }">
                            <p style="color: grey;">[다음${ blockSize }개]</p>
                        </c:if>
                    </td>
                    <td>
                        <c:if test="${ lastPage < totalPage }">
                            <a href="#" onclick="GoPage('survey1_search_list', '${ totalPage }', '${ list_gubun }')">[마지막]</a>
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
        <td colspan="5" align="right" style="padding: 20px 0px 0px;">
            <button type="button" onclick="GoPage('survey1_list','','all')">전체 설문 목록</button>&nbsp;
            <button type="button" onclick="GoPage('survey1_list','','end')">종료된 설문 목록</button>&nbsp;
            <button type="button" onclick="GoPage('survey1_list','','ing')">진행중인 설문 목록</button>&nbsp;
            <button type="button" onclick="GoPage('survey1_add','','')">설문 등록</button>&nbsp;
            <button type="button" onclick="GoPage('survey1_survey','1','')">설문 응답</button>
        </td>
    </tr>
</table>
<script>
function search() {
    searchForm.method = 'post';
    searchForm.action = '${ path }/survey1_servlet/list.do';
    searchForm.submit();
}
</script>