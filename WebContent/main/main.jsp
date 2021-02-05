<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/inc_header.jsp" %>
<%@ include file="../include/inc_script.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
    <table border="0" align="center" style="width: 100%;">
        <tr>
            <td align="center" style="padding: 20px 20px;">
            <!-- 상단 -->
                <jsp:include page="../include/inc_menu.jsp"></jsp:include>
            <!-- 상단 -->
            </td>
        </tr>
        <tr>
            <td align="center"><hr></td>
        </tr>
        <tr>
            <td align="center" style="padding: 50px 20px;">
            <!-- 본문 -->
                <c:choose>
                    <c:when test="${ menu_gubun == 'index' }">
                        <jsp:include page="../main/main_sub.jsp"></jsp:include>
                    </c:when>
                    <c:when test="${ menu_gubun == 'member_list' }">
                        <jsp:include page="../member/list.jsp"></jsp:include>
                    </c:when>
                    <c:when test="${ menu_gubun == 'member_join' }">
                        <jsp:include page="../member/join.jsp"></jsp:include>
                    </c:when>
                    <c:when test="${ menu_gubun == 'member_login' }">
                        <jsp:include page="../member/login.jsp"></jsp:include>
                    </c:when>
                    <c:when test="${ menu_gubun == 'member_view' }">
                        <jsp:include page="../member/view.jsp"></jsp:include>
                    </c:when>
                    <c:when test="${ menu_gubun == 'member_modify' }">
                        <jsp:include page="../member/modify.jsp"></jsp:include>
                    </c:when>
                    <c:when test="${ menu_gubun == 'member_delete' }">
                        <jsp:include page="../member/delete.jsp"></jsp:include>
                    </c:when>
                    <c:when test="${ menu_gubun == 'memo_write' }">
                        <jsp:include page="../memo/write.jsp"></jsp:include>
                    </c:when>
                    <c:when test="${ menu_gubun == 'guestbook_list' }">
                        <jsp:include page="../guestbook/list.jsp"></jsp:include>
                    </c:when>
                    <c:when test="${ menu_gubun == 'guestbook_write' }">
                        <jsp:include page="../guestbook/write.jsp"></jsp:include>
                    </c:when>
                    <c:when test="${ menu_gubun == 'survey_index' }">
                        <jsp:include page="../survey/index.jsp"></jsp:include>
                    </c:when>
                    <c:when test="${ menu_gubun == 'survey1_list' }">
                        <jsp:include page="../survey1/list.jsp"></jsp:include>
                    </c:when>
                    <c:when test="${ menu_gubun == 'survey1_add' }">
                        <jsp:include page="../survey1/add.jsp"></jsp:include>
                    </c:when>
                    <c:when test="${ menu_gubun == 'survey1_view' }">
                        <jsp:include page="../survey1/view.jsp"></jsp:include>
                    </c:when>
                    <c:when test="${ menu_gubun == 'survey1_modify' }">
                        <jsp:include page="../survey1/modify.jsp"></jsp:include>
                    </c:when>
                    <c:when test="${ menu_gubun == 'survey1_survey' }">
                        <jsp:include page="../survey1/survey.jsp"></jsp:include>
                    </c:when>
                    <c:when test="${ menu_gubun == 'board_index' }">
                        <jsp:include page="../board/index.jsp"></jsp:include>
                    </c:when>
                    <c:otherwise>
                        <jsp:include page="../main/main_sub.jsp"></jsp:include>
                    </c:otherwise>
                </c:choose>
            <!-- 본문 -->
            </td>
        </tr>
        <tr>
            <td align="center"><hr></td>
        </tr>
        <tr>
            <td align="center" style="padding: 20px 20px;">
            <!-- 하단 -->
                <jsp:include page="../include/inc_bottom.jsp"></jsp:include>
            <!-- 하단 -->
            </td>
        </tr>
    </table>
</body>
</html>