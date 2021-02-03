<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/inc_header.jsp" %>
<form name="DirForm">
<table border="1">
    <tr>
        <td colspan="2"><h2>회원탈퇴</h2></td>
    </tr>
    <tr>
        <td style="width: 150px;">아이디<input type="hidden" name="member_no" value="${ dto.member_no }" readonly></td>
        <td>${ dto.member_id }</td>
    </tr>
    <tr>
        <td>비밀번호</td>
        <td><input type="password" name="member_password_check" value="" maxlength="20"></td>
    </tr>
    <tr>
        <td>이름</td>
        <td>${ dto.member_name }</td>
    </tr>
    <tr>
        <td>성별</td>
        <td>${ dto.member_gender }</td>
    </tr>
    <tr>
        <td>출생년도</td>
        <td>${ dto.member_born_year }</td>
    </tr>
    <tr>
        <td>우편번호</td>
        <td>${ dto.member_postcode }</td>
    </tr>
    <tr>
        <td rowspan="2">주소</td>
        <td>${ dto.member_address }</td>
    </tr>
    <tr>
        <td>${ dto.member_detail_address } ${ dto.member_extra_address }</td>
    </tr>
    <tr>
        <td>가입일</td>
        <td>${ dto.member_regi_date }</td>
    </tr>
    <tr>
        <td colspan="2" align="center" style="padding: 10px 20px;"><input type="button" value="탈퇴하기" onclick="deleteProc()"></td>
    </tr>
</table>
</form>
<script>
function deleteProc() {
    if (DirForm.member_password_check.value == '') {
        alert('비밀번호를 입력하세요.');
        DirForm.member_password_check.focus();
        return false;
    }
    DirForm.method = 'post';
    DirForm.action = '${ path }/member_servlet/deleteProc.do';
    DirForm.submit();
}
</script>