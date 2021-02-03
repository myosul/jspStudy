<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/inc_header.jsp" %>
<table border="0" style="width: 480px;">
    <tr>
        <td colspan="2"><h2>${ sessionScope.cookName }의 메모장</h2></td>
    </tr>
    <tr>
        <td rowspan="3" style="width: 60px;">새메모 <br><br><br></td>
        <td>
            <input type="text" id="memo_subject" style="width: 100%; font-size: medium;" "maxlength="100" placeholder="제목을 입력하세요.">
        </td>
    </tr>
    <tr>
        <td>
            <textarea class="autosize" id="memo_content" onkeydown="resize(this)" onkeyup="resize(this)" style="width: 100%; padding: 5px 5px; resize: none;" placeholder="내용을 입력하세요."></textarea>
        </td>
    </tr>
    <tr>
        <td style="padding: 10px 0px;">
            <input type="button" id="btnSave" value="저장">
        </td>
    </tr>
</table>
<br>
<!-- 결과가 출력되는 영역 -->
<div id="result"></div>


<script>
$(document).ready(function() {
	list();
	$("#btnSave").click(function() {
		insert();
	});
});

function insert() {
	var memo_subject = $("#memo_subject").val(); // id="memo_subject" 의 값을 가져와 변수 memo_subject 에 할당
	var memo_content = $("#memo_content").val(); // id="memo_content" 의 값을 가져와 변수 memo_content 에 할당
	
	if (memo_subject == '') {
		alert('제목을 입력하세요.');
		$('#memo_subject').select();
		$('#memo_subject').focus();
		return false;
	}
	if (memo_content == '') {
		alert('내용을 입력하세요.');
        $('#memo_content').select();
        $('#memo_content').focus();
        return false;
	}
	
	var param = "memo_subject=" + memo_subject;
	param += "&memo_content=" + memo_content; // 쿼리스트링
	
	$.ajax({
		type: "post",
		data: param,
		url: "${ path }/memo_servlet/writeProc.do",
		success: function(result) { // 콜백함수
			list();
			$("#memo_subject").val("");
			$("#memo_content").val("");
		}
	});
}

function list() {
    var param = "search_gubun=&sdata=";
    param += "&pageNumber=${ pageNumber }";
    $.ajax({
		type: "post",
		data: param,
		url: "${ path }/memo_servlet/list.do",
		success: function(result) { // 콜백함수
			$("#result").html(result);
		}
	});
}

function resize(obj) {
	obj.style.height = '1px';
	obj.style.height = (obj.scrollHeight + 16) + 'px';
}
</script>