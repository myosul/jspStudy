<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/inc_header.jsp" %>

<table border="1" align="center">
    <tr>
        <td colspan="2"><h2>게시글삭제</h2></td>
    </tr>
    <tr>
        <td style="align: center;">작성자</td>
        <td><input type="text" name="board_writer" id="board_writer" value="${ dto.board_writer }" readonly></td>
    </tr>
    <tr>
        <td style="align: center;">이메일</td>
        <td><input type="text" name="board_email" id="board_email" value="${ dto.board_email }" readonly></td>
    </tr>
    <tr>
        <td style="align: center;">비밀번호</td>
        <td><input type="password" name="board_passwd" id="board_passwd"></td>
    </tr>
    <tr>
        <td style="align: center;">제목</td>
        <td><input type="text" name="board_subject" id="board_subject" value="${ dto.board_subject }" readonly></td>
    </tr>
    <tr>
        <td style="align: center;">내용</td>
        <td><textarea name="board_content" id="board_content" style="width: 640px; height: 100px;" readonly>${ dto.board_content }</textarea></td>
    </tr>
    <tr>
        <td style="align: center;">공지글</td>
        <td>
            <input type="text" name="board_notice" id="board_notice" value="${ dto.board_notice_no }" readonly>
            <c:if test="${ dto.board_notice_no > 0 }">
            <input type="checkbox" name="board_notice_checkBox" id="board_notice_checkBox" value="T" onclick="clickChk('notice_check')" checked>
            공지글 체크
            </c:if>
            <c:if test="${ dto.board_notice_no <= 0 }">
            <input type="checkbox" name="board_notice_checkBox" id="board_notice_checkBox" value="T" onclick="clickChk('notice_check')">
            공지글 체크
            </c:if>
        </td>
    </tr>
    <tr>
        <td style="align: center;">비밀글</td>
        <td>
            <input type="text" name="board_secret" id="board_secret" value="${ dto.board_secret }" readonly>
            <c:if test="${ dto.board_secret == 'T' }">
            <input type="checkbox" name="board_secret_checkBox" id="board_secret_checkBox" value="T" onclick="clickChk('secret_check')" checked>
            비밀글 체크
            </c:if>
            <c:if test="${ dto.board_secret != 'T' }">
            <input type="checkbox" name="board_secret_checkBox" id="board_secret_checkBox" value="T" onclick="clickChk('secret_check')">
            비밀글 체크
            </c:if>
        </td>
    </tr>
    <tr>
        <td colspan="2" height="50px;">
            <button type="button" id="btnList">목록으로</button>
            <button type="button" id="btnDelete">삭제하기</button>
        </td>
    </tr>
</table>

<script>
$(document).ready(function() {
    $("#board_passwd").focus();
    
    $("#btnDelete").click(function() {
        if (confirm('삭하시겠습니까?')) {
            GoBoardPage('deleteProc', '');
        }
    });
    
    $("#btnList").click(function() {
        select_page('1');
    });
});
</script>