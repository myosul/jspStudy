<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/inc_header.jsp" %>

<table border="1" align="center">
    <tr>
        <c:if test="${ board_no <= 0 }">
        <td colspan="2"><h2>게시글쓰기</h2></td>
        </c:if>
        <c:if test="${ board_no > 0 }">
        <td colspan="2"><h2>답변글쓰기</h2></td>
        </c:if>
    </tr>
    <tr>
        <td style="align: center;">작성자</td>
        <td><input type="text" name="board_writer" id="board_writer"></td>
    </tr>
    <tr>
        <td style="align: center;">이메일</td>
        <td><input type="text" name="board_email" id="board_email"></td>
    </tr>
    <tr>
        <td style="align: center;">비밀번호</td>
        <td><input type="password" name="board_passwd" id="board_passwd"></td>
    </tr>
    <tr>
        <td style="align: center;">제목</td>
        <td><input type="text" name="board_subject" id="board_subject" value="${ dto.board_subject }"></td>
    </tr>
    <tr>
        <td style="align: center;">내용</td>
        <td><textarea name="board_content" id="board_content" style="width: 640px; height: 100px;">${ dto.board_content }</textarea></td>
    </tr>
    <tr>
        <td style="align: center;">공지글</td>
        <td>
            <input type="text" name="board_notice" id="board_notice">
            <input type="checkbox" name="board_notice_checkBox" id="board_notice_checkBox" value="T" onclick="clickChk('notice_check')">
            공지글 체크
        </td>
    </tr>
    <tr>
        <td style="align: center;">비밀글</td>
        <td>
            <input type="text" name="board_secret" id="board_secret">
            <input type="checkbox" name="board_secret_checkBox" id="board_secret_checkBox" value="T" onclick="clickChk('secret_check')">
            비밀글 체크
        </td>
    </tr>
    <tr>
        <td colspan="2" height="50px;">
            <button type="button" id="btnList">목록으로</button>
            <button type="button" id="btnAdd">등록하기</button>
        </td>
    </tr>
</table>

<script>
$(document).ready(function() {
    $("#board_writer").focus();
    
    $("#btnAdd").click(function() {
        if (confirm('등록하시겠습니까?')) {
            GoBoardPage('addProc', '');
        }
    });
    
    $("#btnList").click(function() {
        select_page('1');
    });
});
</script>