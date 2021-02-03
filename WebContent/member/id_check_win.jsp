<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/inc_header.jsp" %>
<form name="DirForm">
<table border="1" align="center">
    <tr>
        <td colspan="2"><h2>아이디 찾기</h2></td>
    </tr>
    <tr>
        <td style="width: 100px;">아이디</td>
        <td>
            <input type="text" name="member_id" id="member_id" onkeyup="noSpaceForm(this);" onchange="noSpaceForm(this);" value="${ member_id }" maxlength="10">
            <input type="hidden" name="use_id" id="use_id" value="${ member_id }" maxlength="10">
            <br>
            <c:if test="${ result == 0 }">
                <label id="label_id" style="font-size: x-small; color: blue;">사용할 수 있는 아이디입니다.</label>
            </c:if>
            <c:if test="${ result == 1 }">
                <label id="label_id" style="font-size: x-small; color: red;">사용할 수 없는 아이디입니다.</label>
            </c:if>
        </td>
    </tr>
    <tr>
        <td colspan="2" align="center" style="padding: 10px 20px;">
            <input type="button" value="검색" onclick="search()">&nbsp;
            <input type="button" value="적용" onclick="use()">
        </td>
    </tr>
</table>
</form>
<script>
function noSpaceForm(obj) { // 공백을 사용할 수 없는 폼         
    var str_space = /\s/;   // 공백 체크
    if(str_space.exec(obj.value)) {   // 공백 체크
        alert("해당 항목에는 공백을 사용할 수 없습니다.\n\n공백이 제거됩니다.");
        obj.focus();
        obj.value = obj.value.replace(' ',''); // 공백제거
        return false;
    }
}

function use() {
	if (DirForm.member_id.value == '') {
        alert('검색할 아이디를 입력하세요.');
        DirForm.member_id.focus();
        return false;
    }
	var member_id = document.getElementById("use_id").value;
	
	if (member_id != document.getElementById("member_id").value) {
		alert('검색을 수행하세요.');
		return false;
	} else {
		opener.document.getElementById("member_id").value = member_id;
		window.close();
	}
}

function search() {
    if (DirForm.member_id.value == '') {
        alert('검색할 아이디를 입력하세요.');
        DirForm.member_id.focus();
        return false;
    }
    DirForm.method = 'post';
    DirForm.action = '${ path }/member_servlet/id_check_win_proc.do';
    DirForm.submit();
}
</script>