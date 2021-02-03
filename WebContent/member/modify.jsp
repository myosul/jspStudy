<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/inc_header.jsp" %>
<form name="DirForm">
<table border="1">
    <tr>
        <td colspan="2"><h2>회원정보수정</h2></td>
    </tr>
    <tr>
        <td style="width: 150px;">아이디<input type="hidden" name="member_no" value="${ dto.member_no }" readonly></td>
        <td>${ dto.member_id }<input type="hidden" name="member_id" value="${ dto.member_id }" readonly></td>
    </tr>
    <tr>
        <td>비밀번호</td>
        <td><input type="password" name="member_password_check" value="" maxlength="20"></td>
    </tr>
    <tr>
        <td>이름</td>
        <td><input type="text" name="member_name" value="${ dto.member_name }" maxlength="15"></td>
    </tr>
    <tr>
        <td>성별</td>
        <td>
        <c:if test="${ dto.member_gender == 'M' }">
            <input type="radio" name="member_gender" value="M" checked>남자&nbsp;
            <input type="radio" name="member_gender" value="F">여자&nbsp;
        </c:if>
        <c:if test="${ dto.member_gender == 'F' }">
            <input type="radio" name="member_gender" value="M">남자&nbsp;
            <input type="radio" name="member_gender" value="F" checked>여자&nbsp;
        </c:if>
        </td>
    </tr>
    <tr>
        <td>출생년도</td>
        <td><input type="number" name="member_born_year" value="${ dto.member_born_year }" maxlength="4"></td>
    </tr>
    <tr>
        <td>우편번호</td>
        <td>
            <input type="text" name="member_postcode" id="member_postcode" value="${ dto.member_postcode }" placeholder="우편번호">
            <input type="button" onclick="execDaumPostcode()" value="우편번호 찾기">
        </td>
    </tr>
    <tr>
        <td rowspan="2">주소</td>
        <td>
            <input type="text" name="member_address" id="member_address" value="${ dto.member_address }" placeholder="주소">
        </td>
    </tr>
    <tr>
        <td>
            <input type="text" name="member_detail_address"  id="member_detail_address" value="${ dto.member_detail_address }" placeholder="상세주소">
            <input type="text" name="member_extra_address" id="member_extra_address" value="${ dto.member_extra_address }" placeholder="참고항목">
        </td>
    </tr>
    <tr>
        <td>가입일</td>
        <td>${ dto.member_regi_date }</td>
    </tr>
    <tr>
        <td colspan="2" align="center" style="padding: 10px 20px;"><input type="button" value="수정하기" onclick="modifyProc()"></td>
    </tr>
</table>
</form>
<script>
function modifyProc() {
    if (DirForm.member_password_check.value == '') {
        alert('비밀번호를 입력하세요.');
        DirForm.member_password_check.focus();
        return false;
    }
    if (DirForm.member_name.value == '') {
        alert('이름을 선택하세요.');
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
    DirForm.action = '${ path }/member_servlet/modifyProc.do';
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