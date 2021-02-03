<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/inc_header.jsp" %>
<table>
    <tr>
        <td>
            <c:if test="${ startPage > blockSize }">
                <a href="#" onclick="GoPage('memo_write', '1', '')">[처음]</a>
            </c:if>
            <c:if test="${ startPage <= blockSize }">
                <p style="color: grey;">[처음]</p>
            </c:if>
        </td>
        <td>
            <c:if test="${ startPage > blockSize }">
                <a href="#" onclick="GoPage('memo_write', '${ startPage = blockSize }', '')">[이전${ blockSize }개]</a>
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
            <a href="#" onclick="GoPage('memo_write', '${ i }', '')">${ i }</a>
        </td>
        </c:if>
        </c:forEach>
        <td>
            <c:if test="${ lastPage < totalPage }">
                <a href="#" onclick="GoPage('memo_write', '${ startPage + blockSize }', '')">[다음${ blockSize }개]</a>
            </c:if>
            <c:if test="${ lastPage >= totalPage }">
                <p style="color: grey;">[다음${ blockSize }개]</p>
            </c:if>
        </td>
        <td>
            <c:if test="${ lastPage < totalPage }">
                <a href="#" onclick="GoPage('memo_write', '${ totalPage }', '')">[마지막]</a>
            </c:if>
            <c:if test="${ lastPage >= totalPage }">
                <p style="color: grey;">[마지막]</p>
            </c:if>
        </td>
    </tr>
</table>
<c:forEach var="dto" items="${ list }">
<table border="0"  style="width: 480px;">
    <tr>
        <td colspan="2" align="center"><hr></td>
    </tr>
    <tr>
        <td rowspan="3" style="width: 50px;"># ${ recordNum } <br><br><br></td>
        <td align="right">
            <input type="text" id="memo_subject" style="width: 100%; font-size: medium;" maxlength="100" value="${ dto.memo_subject }" placeholder="제목을 입력하세요." readonly>
        </td>
    </tr>
    <tr>
        <td align="right">
            <textarea class="autosize" id="memo_content" onclick="resize(this)" style="width: 100%; padding: 5px 5px; resize: none;" readonly>${ dto.memo_content }</textarea>
        </td>
    </tr>
    <tr>
        <td align="right" style="font-size: small;">
            작성일 : ${ dto.memo_regi_date }
        </td>
    </tr>
</table>
<c:set var="recordNum" value="${ recordNum = recordNum - 1 }"></c:set>
</c:forEach>