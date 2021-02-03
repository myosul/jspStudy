<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/inc_header.jsp" %>
<form name="DirForm">
<table border="1">
    <tr>
        <td colspan="2"><h2>회원가입</h2></td>
    </tr>
    <tr>
        <td style="width: 150px;">아이디</td>
        <td>
            <input type="text" name="member_id" id="member_id" onkeyup="noSpaceForm(this);" onchange="noSpaceForm(this);" value="${ member_id }" maxlength="10" placeholder="숫자 또는 알파벳">
            <button type="button" onclick="id_check()">아이디찾기</button>
            <button type="button" onclick="javascript:id_check_win_open();">아이디찾기(새창)</button>
            <br>
            <label id="label_id"></label>
        </td>
    </tr>
    <tr>
        <td>비밀번호</td>
        <td><input type="password" name="member_password" onkeyup="noSpaceForm(this);" onchange="noSpaceForm(this);" size="32" value="" maxlength="20" placeholder="숫자 또는 알파벳 또는 !@#$%^*=+-"></td>
    </tr>
    <tr>
        <td>비밀번호 확인</td>
        <td><input type="password" name="member_password_check" onkeyup="noSpaceForm(this);" onchange="noSpaceForm(this);" size="32" value="" maxlength="20" placeholder="숫자 또는 알파벳 또는 !@#$%^*=+-"></td>
    </tr>
    <tr>
        <td>이름</td>
        <td><input type="text" name="member_name" onkeyup="noStartSpaceForm(this);" onchange="noStartSpaceForm(this);" value="" maxlength="15"></td>
    </tr>
    <tr>
        <td>성별</td>
        <td>
            <input type="radio" name="member_gender" value="M">남자&nbsp;
            <input type="radio" name="member_gender" value="F">여자&nbsp;
        </td>
    </tr>
    <tr>
        <td>출생년도</td>
        <td><input type="number" name="member_born_year" value="" maxlength="4" placeholder="4자리 숫자"></td>
    </tr>
    <tr>
        <td>우편번호</td>
        <td>
            <input type="number" name="member_postcode" id="member_postcode" placeholder="5자리 또는 6자리 숫자">
            <input type="button" onclick="execDaumPostcode()" value="우편번호 찾기">
        </td>
    </tr>
    <tr>
        <td rowspan="2">주소</td>
        <td>
            <input type="text" name="member_address" id="member_address" onkeyup="noStartSpaceForm(this);" onchange="noStartSpaceForm(this);" placeholder="주소">
        </td>
    </tr>
    <tr>
        <td>
            <input type="text" name="member_detail_address"  id="member_detail_address" onkeyup="noStartSpaceForm(this);" onchange="noStartSpaceForm(this);" placeholder="상세주소">
            <input type="text" name="member_extra_address" id="member_extra_address" onkeyup="noStartSpaceForm(this);" onchange="noStartSpaceForm(this);" placeholder="참고항목">
        </td>
    </tr>
    <tr>
        <td colspan="2" align="center" style="padding: 10px 20px;"><input type="button" value="가입하기" onclick="join()"></td>
    </tr>
</table>
</form>
<script>
function id_check(){
	var member_id = $("#member_id").val();
	if (member_id == '') {
		$("#label_id").html('아이디를 입력하세요.');
		$("#label_id").css('color', 'green');
		$("#label_id").css('font-size', 'x-small');
		return;
	}
	
	var param = "member_id=" + member_id;
	$.ajax({
		type: "post",
		data: param, // data:$('form이름').serialize(), // 변수를 만들지 않고 from 값을 그대로 넘기기
		url: "${ path }/member_servlet/id_check.do",
		success: function(result) {
			if (result > 0) {
				$("#member_id").val('');
				$("#label_id").html('사용할 수 없는 아이디입니다.');
				$("#label_id").css('color', 'red');
				$("#label_id").css('font-size', 'x-small')
			} else {
			    $("#label_id").html('사용할 수 있는 아이디입니다.');
                $("#label_id").css('color', 'blue');
                $("#label_id").css('font-size', 'x-small')
			}
		}
	})
}

function id_check_win_open(){
    window.open("${ path }/member_servlet/id_check_win.do", "id_check_win", "width=460, height=320, toolbar=no, menubar=no, scrollbars=no, resizable=yes");
}

function join() {
	if (DirForm.member_id.value == '') {
        alert('아이디를 입력하세요.');
        DirForm.member_id.focus();
        return false;
    }
	if (DirForm.member_password.value == '') {
        alert('비밀번호를 입력하세요.');
        DirForm.member_password.focus();
        return false;
    }
	if (DirForm.member_password_check.value == '') {
        alert('비밀번호를 확인하세요.');
        DirForm.member_password_check.focus();
        return false;
    }
	if (DirForm.member_password.value != DirForm.member_password_check.value) {
        alert('비밀번호가 일치하지 않습니다.');
        DirForm.member_password_check.focus();
        return false;
    }
	if (DirForm.member_name.value == '') {
        alert('이름을 입력하세요.');
        DirForm.member_name.focus();
        return false;
    }
	if (DirForm.member_gender.value == '') {
        alert('성별을 선택하세요.');
        DirForm.member_gender.focus();
        return false;
    }
	if (DirForm.member_born_year.value == '') {
        alert('출생년도를 입력하세요.');
        DirForm.member_born_year.focus();
        return false;
    }
	DirForm.method = 'post';
	DirForm.action = '${ path }/member_servlet/joinProc.do';
	DirForm.submit();
}
</script>

<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script>
    function execDaumPostcode() {
        new daum.Postcode({
            oncomplete: function(data) {
                // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

                // 각 주소의 노출 규칙에 따라 주소를 조합한다.
                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                var addr = ''; // 주소 변수
                var extraAddr = ''; // 참고항목 변수

                //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
                if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                    addr = data.roadAddress;
                } else { // 사용자가 지번 주소를 선택했을 경우(J)
                    addr = data.jibunAddress;
                }

                // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
                if(data.userSelectedType === 'R'){
                    // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                    // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                    if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                        extraAddr += data.bname;
                    }
                    // 건물명이 있고, 공동주택일 경우 추가한다.
                    if(data.buildingName !== '' && data.apartment === 'Y'){
                        extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                    }
                    // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                    if(extraAddr !== ''){
                        extraAddr = ' (' + extraAddr + ')';
                    }
                    // 조합된 참고항목을 해당 필드에 넣는다.
                    document.getElementById("member_extra_address").value = extraAddr;
                
                } else {
                    document.getElementById("member_extra_address").value = '';
                }

                // 우편번호와 주소 정보를 해당 필드에 넣는다.
                document.getElementById('member_postcode').value = data.zonecode;
                document.getElementById("member_address").value = addr;
                // 커서를 상세주소 필드로 이동한다.
                document.getElementById("member_detail_address").focus();
            }
        }).open();
    }
</script>