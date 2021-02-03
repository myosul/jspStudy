<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/inc_header.jsp" %>
<table border="1">
    <tr>
        <td colspan="6"><h2>회원목록</h2></td>
    </tr>
    <tr>
        <td>번호</td>
        <td>아이디</td>
        <td>이름</td>
        <td>성별</td>
        <td>출생년도</td>
        <td>가입일</td>
    </tr>
    <c:if test="${ list.size() <= 0 }">
    <tr>
        <td colspan="6" align="center" height="100">등록된 회원이 없습니다.</td>
    </tr>
    </c:if>
    <c:if test="${ list.size() > 0 }">
    <c:forEach var="dto" items="${ list }">
    <tr>
        <td>${ recordNum }</td>
        <td><a href="#" onclick="GoPage('member_view', '${ pageNumber }', '${ dto.member_no }')">${ dto.member_id }</a></td>
        <td>${ dto.member_name }</td>
        <td>${ dto.member_gender }</td>
        <td>${ dto.member_born_year }</td>
        <td>${ dto.member_regi_date }</td>
    </tr>
    <c:set var="recordNum" value="${ recordNum = recordNum - 1 }"></c:set>
    </c:forEach>
    <tr>
        <td colspan="6" align="center">
            <table>
                <tr>
                    <td>
                        <c:if test="${ startPage > blockSize }">
                            <a href="#" onclick="GoPage('member_list', '1', '')">[처음]</a>
                        </c:if>
                        <c:if test="${ startPage <= blockSize }">
                            <p style="color: grey;">[처음]</p>
                        </c:if>
                    </td>
                    <td>
                        <c:if test="${ startPage > blockSize }">
                            <a href="#" onclick="GoPage('member_list', '${ startPage = blockSize }', '')">[이전${ blockSize }개]</a>
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
                        <a href="#" onclick="GoPage('member_list', '${ i }', '')">${ i }</a>
                    </td>
                    </c:if>
                    </c:forEach>
                    <td>
                        <c:if test="${ lastPage < totalPage }">
                            <a href="#" onclick="GoPage('member_list', '${ startPage + blockSize }', '')">[다음${ blockSize }개]</a>
                        </c:if>
                        <c:if test="${ lastPage >= totalPage }">
                            <p style="color: grey;">[다음${ blockSize }개]</p>
                        </c:if>
                    </td>
                    <td>
                        <c:if test="${ lastPage < totalPage }">
                            <a href="#" onclick="GoPage('member_list', '${ totalPage }', '')">[마지막]</a>
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
        <td colspan="6" align="center" style="padding: 10px 20px;">
            <button type="button" onclick="GoPage('member_join','','')">회원가입</button>
        </td>
    </tr>
</table>