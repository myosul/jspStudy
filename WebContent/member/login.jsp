<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/inc_header.jsp" %>
<form name="DirForm">
<table border="1">
    <tr>
        <td colspan="2"><h2>로그인</h2></td>
    </tr>
    <tr>
        <td style="width: 150px;">아이디</td>
        <td><input type="text" name="member_id" value="" maxlength="10"></td>
    </tr>
    <tr>
        <td>비밀번호</td>
        <td><input type="password" name="member_password_check" value="" maxlength="20"></td>
    </tr>
    <tr>
        <td colspan="2" align="center" style="padding: 10px 20px;">
            <input type="button" value="회원가입" onclick="GoPage('member_join', '', '')">&nbsp;
            <input type="button" value="로그인" onclick="loginProc()">
        </td>
    </tr>
</table>
</form>
<script>
function loginProc() {
    if (DirForm.member_id.value == '') {
        alert('아이디를 입력하세요.');
        DirForm.member_id.focus();
        return false;
    }
    if (DirForm.member_password_check.value == '') {
        alert('비밀번호를 입력하세요.');
        DirForm.member_password_check.focus();
        return false;
    }
    DirForm.method = 'post';
    DirForm.action = '${ path }/member_servlet/loginProc.do';
    DirForm.submit();
}
</script>