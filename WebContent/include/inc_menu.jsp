<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/inc_header.jsp" %>
<c:set var="a" value="${ menu_gubun }"></c:set>
<c:set var="b" value="${ fn:indexOf(a, '_') }"></c:set>
<c:set var="menu" value="${ fn:substring(a, 0, b) }"></c:set>
<table border="0">
    <tr>
        <td colspan="13" align="right" style="padding: 0px 20px 10px;">
            <c:choose>
                <c:when test="${ sessionScope.cookNo == null || sessionScope.cookNo == 0 }">
                    <a href="${ path }/member_servlet/login.do">[로그인]</a>
                </c:when>
                <c:otherwise>
                    ${ sessionScope.cookName }님 환영합니다.
                    <a href="${ path }/member_servlet/modify.do?member_no=${ sessionScope.cookNo }">[회원정보수정]</a>
                    <a href="${ path }/member_servlet/delete.do?member_no=${ sessionScope.cookNo }">[회원탈퇴]</a>
                    <a href="${ path }/member_servlet/logout.do">[로그아웃]</a>
                </c:otherwise>
            </c:choose>
        </td>
    </tr>
    <tr align="center">
        <td id="home" style="padding: 0px 20px;">
            <a href="${ path }">HOME</a>
        </td>
        <td id="member" style="padding: 0px 20px;">
            <a href="${ path }/member_servlet/list.do">회원관리</a>
        </td>
        <td id="memo" style="padding: 0px 20px;">
            <a href="${ path }/memo_servlet/write.do">메모장</a>
        </td>
        <td id="guestbook" style="padding: 0px 20px;">
            <a href="${ path }/guestbook_servlet/list.do">방명록</a>
        </td>
        <td id="survey" style="padding: 0px 20px;">
            <a href="${ path }/survey_servlet/index.do">설문조사(ajax)</a>
        </td>
        <td id="survey" style="padding: 0px 20px;">
            <a href="${ path }/survey1_servlet/list.do">설문조사</a>
        </td>
        <td id="board" style="padding: 0px 20px;">
            <a href="${ path }/board_servlet/index.do">게시판(ajax)</a>
        </td>
        <td id="product" style="padding: 0px 20px;">
            <a href="${ path }/product_servlet/index.do">Mall(상품관리-ajax)</a>
        </td>
        <td id="mall" style="padding: 0px 20px;">
            <a href="${ path }/mall_servlet/index.do">Mall(ajax)</a>
        </td>
        <td style="padding: 0px 20px;">
            <a href="#">관리자</a>
        </td>
    </tr>
    <tr>
        <td colspan="13" align="left" >
            >>> ${ menu }
        </td>
    </tr>
</table>
<c:choose>
    <c:when test="${ menu == 'index' }">
        <script>
        $("#home").css("background-color", "silver");
        </script>
    </c:when>
    <c:when test="${ menu == 'member' }">
        <script>
        $("#member").css("background-color", "silver");
        </script>
    </c:when>
    <c:when test="${ menu == 'memo' }">
        <script>
        $("#memo").css("background-color", "silver");
        </script>
    </c:when>
    <c:when test="${ menu == 'guestbook' }">
        <script>
        $("#guestbook").css("background-color", "silver");
        </script>
    </c:when>
    <c:when test="${ menu == 'survey' }">
        <script>
        $("#survey").css("background-color", "silver");
        </script>
    </c:when>
    <c:when test="${ menu == 'board' }">
        <script>
        $("#board").css("background-color", "silver");
        </script>
    </c:when>
    <c:when test="${ menu == 'product' }">
        <script>
        $("#product").css("background-color", "silver");
        </script>
    </c:when>
    <c:when test="${ menu == 'mall' }">
        <script>
        $("#mall").css("background-color", "silver");
        </script>
    </c:when>
</c:choose>
<script>
function logout() {
	if (confirm('로그아웃 하시겠습니까?')) {
		location.href = '${ path }/member_servlet/logout.do';
	}
}
</script>