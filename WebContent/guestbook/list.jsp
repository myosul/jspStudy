<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/inc_header.jsp" %>
<table border="0">
    <tr>
        <td colspan="6"><h2>방명록</h2></td>
    </tr>
    <tr>
        <td colspan="6">
            <button type="button" onclick="GoPage('guestbook_write','','')">방명록 작성</button>
        </td>
    </tr>
    <c:if test="${ list.size() > 0 }">
    <tr>
        <td colspan="6" align="center" height="80">
            <table>
                <tr>
                    <td>
                        <c:if test="${ startPage > blockSize }">
                            <a href="#" onclick="GoPage('guestbook_list', '1', '')">[처음]</a>
                        </c:if>
                        <c:if test="${ startPage <= blockSize }">
                            <p style="color: grey;">[처음]</p>
                        </c:if>
                    </td>
                    <td>
                        <c:if test="${ startPage > blockSize }">
                            <a href="#" onclick="GoPage('guestbook_list', '${ startPage = blockSize }', '')">[이전${ blockSize }개]</a>
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
                        <a href="#" onclick="GoPage('guestbook_list', '${ i }', '')">${ i }</a>
                    </td>
                    </c:if>
                    </c:forEach>
                    <td>
                        <c:if test="${ lastPage < totalPage }">
                            <a href="#" onclick="GoPage('guestbook_list', '${ startPage + blockSize }', '')">[다음${ blockSize }개]</a>
                        </c:if>
                        <c:if test="${ lastPage >= totalPage }">
                            <p style="color: grey;">[다음${ blockSize }개]</p>
                        </c:if>
                    </td>
                    <td>
                        <c:if test="${ lastPage < totalPage }">
                            <a href="#" onclick="GoPage('guestbook_list', '${ totalPage }', '')">[마지막]</a>
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
        <td>
            <table border="1" width="480px">
                <c:if test="${ list.size() <= 0 }">
                <tr>
                    <td colspan="6" align="center" height="100">등록된 글이 없습니다.</td>
                </tr>
                </c:if>
                <c:forEach var="dto" items="${ list }">
                <tr>
                    <td style="width: 60px;">작성자</td>
                    <td>${ dto.guestbook_writer_name }</td>
                    <td style="width: 60px;">작성일</td>
                    <td style="width: 180px;">${ dto.guestbook_regi_date }</td>
                </tr>
                <tr>
                    <td>이메일</td>
                    <td colspan="3">${ dto.guestbook_writer_email }</td>
                </tr>
                <tr>
                    <td colspan="4">
                        <textarea class="autosize" name="guestbook_content" onclick="resize(this)" style="width: 100%; padding: 5px 5px; resize: none;" readonly>${ dto.guestbook_content }</textarea>
                    </td>
                </tr>
                </c:forEach>
            </table>
        </td>
    </tr>
</table>