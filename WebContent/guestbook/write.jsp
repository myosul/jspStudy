<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/inc_header.jsp" %>
<form name="DirForm">
<table border="1" width="480px">
    <tr>
        <td colspan="2"><h2>방명록 작성</h2></td>
    </tr>
    <tr>
        <td style="width: 100px;">작성자</td>
        <td><input type="text" name="guestbook_writer_name" value="" maxlength="10" style="width: 100%"></td>
    </tr>
    <tr>
        <td>이메일</td>
        <td><input type="text" name="guestbook_writer_email" value="" maxlength="30" style="width: 100%"></td>
    </tr>
    <tr>
        <td>비밀번호</td>
        <td><input type="password" name="guestbook_passwd" value="" maxlength="20" style="width: 100%"></td>
    </tr>
    <tr>
        <td colspan="2">
            <textarea class="autosize" name="guestbook_content" onkeydown="resize(this)" onkeyup="resize(this)" style="width: 100%; padding: 5px 5px; resize: none;" placeholder="내용을 입력하세요."></textarea>
        </td>
    </tr>
    <tr>
        <td colspan="2" align="center" style="padding: 10px 20px;">
            <input type="button" value="목록으로" onclick="GoPage('guestbook_list', '', '')">&nbsp;
            <input type="button" value="저장하기" onclick="writeProc()">
        </td>
    </tr>
</table>
</form>
<script>
function writeProc() {
    if (DirForm.guestbook_writer_name.value == '') {
        alert('작성자를 입력하세요.');
        DirForm.guestbook_writer_name.focus();
        return false;
    }
    if (DirForm.guestbook_writer_email.value == '') {
        alert('이메일을 입력하세요.');
        DirForm.guestbook_writer_email.focus();
        return false;
    }
    if (DirForm.guestbook_passwd.value == '') {
        alert('비밀번호를 입력하세요.');
        DirForm.guestbook_passwd.focus();
        return false;
    }
    if (DirForm.guestbook_content.value == '') {
        alert('내용을 입력하세요.');
        DirForm.guestbook_content.focus();
        return false;
    }
    DirForm.method = 'post';
    DirForm.action = '${ path }/guestbook_servlet/writeProc.do';
    DirForm.submit();
}
function resize(obj) {
    obj.style.height = '1px';
    obj.style.height = (obj.scrollHeight + 16) + 'px';
}
</script>