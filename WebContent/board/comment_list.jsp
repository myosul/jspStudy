<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/inc_header.jsp" %>
<table border="0" align="center" style="width: 95%;">
    <tr>
        <td style="padding: 0 0 20 0;">
            이름 : <input type="text" name="board_comment_writer" id="board_comment_writer" value="${ cookName }">
            비밀번호 : <input type="password" name="board_comment_passwd" id="board_comment_passwd"><br>
            댓글 : <input type="text" name="board_comment_content" id="board_comment_content" style="width: 480px;">
            <input type="button" id="btnCommentSave" value="확인">
        </td>
    </tr>
    <c:if test="${ totalRecord > 0 }">
    <c:forEach var="commentDto" items="${ commentList }">
    <tr>
        <td>
            ${ commentDto.board_comment_writer }&nbsp;
            <fmt:formatDate value="${ commentDto.board_comment_regi_date }" type="both" dateStyle="short"/><br>
            ${ commentDto.board_comment_content }
            <hr>
        </td>
    </tr>
    </c:forEach>
    <tr>
        <td>
            <table align="center">
                <tr>
                    <td>
                        <c:if test="${ startPage > blockSize }">
                            <a href="#comment" onclick="GoCommentPage('1')">[처음]</a>
                        </c:if>
                        <c:if test="${ startPage <= blockSize }">
                            <p style="color: grey;">[처음]</p>
                        </c:if>
                    </td>
                    <td>
                        <c:if test="${ startPage > blockSize }">
                            <a href="#comment" onclick="GoCommentPage('${ startPage = blockSize }')">[이전${ blockSize }개]</a>
                        </c:if>
                        <c:if test="${ startPage <= blockSize }">
                            <p style="color: grey;">[이전${ blockSize }개]</p>
                        </c:if>
                    </td>
                    <c:forEach var="i" begin="${ startPage }" end="${ lastPage }" step="1">
                    <c:if test="${ i == pageNumber }">
                    <td>
                        [${ i }]
                    </td>
                    </c:if>
                    <c:if test="${ i != pageNumber }">
                    <td>
                        <a href="#comment" onclick="GoCommentPage('${ i }')">${ i }</a>
                    </td>
                    </c:if>
                    </c:forEach>
                    <td>
                        <c:if test="${ lastPage < totalPage }">
                            <a href="#comment" onclick="GoCommentPage('${ startPage + blockSize }')">[다음${ blockSize }개]</a>
                        </c:if>
                        <c:if test="${ lastPage >= totalPage }">
                            <p style="color: grey;">[다음${ blockSize }개]</p>
                        </c:if>
                    </td>
                    <td>
                        <c:if test="${ lastPage < totalPage }">
                            <a href="#comment" onclick="GoCommentPage('${ totalPage }')">[마지막]</a>
                        </c:if>
                        <c:if test="${ lastPage >= totalPage }">
                            <p style="color: grey;">[마지막]</p>
                        </c:if>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
    </c:if>
</table>

<script>
$(document).ready(function() {
    $("#btnCommentSave").click(function() {
        comment_add();
    });
});

function comment_add() {
    if (board_comment_writer == '') {
		alert('이름을 입력하세요.');
		$('#board_comment_writer').select();
		$('#board_comment_writer').focus();
		return false;
	}
	if (board_comment_passwd == '') {
		alert('비밀번호를 입력하세요.');
        $('#board_comment_passwd').select();
        $('#board_comment_passwd').focus();
        return false;
	}
	if (board_comment_content == '') {
		alert('내용을 입력하세요.');
        $('#board_comment_content').select();
        $('#board_comment_content').focus();
        return false;
	}
	
	var url = "${ path }/board_servlet/commentProc.do";
	var param = {
	        "board_no" : $("#span_board_no").text(),
	        "comment_pageNumber" : $("#comment_pageNumber").text(),
	        "board_comment_writer" : $("#board_comment_writer").val(),
            "board_comment_passwd" : $("#board_comment_passwd").val(),
            "board_comment_content" : $("#board_comment_content").val()
    }
	
	$.ajax({
		type: "post",
		data: param,
		url: url,
		success: function(data) { // 콜백함수(서버에서 처리가 완료된 후 실행되는 코드)
		    $("#span_comment_pageNumber").text(${ comment_pageNumber });
			comment_list();
		}
	});
}

function GoCommentPage(value1) {
    $("#span_comment_pageNumber").text(value1);
    comment_list();
}
</script>