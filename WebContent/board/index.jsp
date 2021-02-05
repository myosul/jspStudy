<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/inc_header.jsp" %>

<div id="result" style="border: 1px solid red; height: 500px;"></div>

<script>
$(document).ready(function() {
    <c:if test="${ menu_gubun == 'board_index'}">
    // GoBoardPage('list');
    GoBoardPage('add');
    </c:if>
});

function GoBoardPage(value1) {
    if (value1 == "add") {
        var param = {}
        var url = "${ path }/board_servlet/add.do";
    } else if (value1 == "addProc") {
        var param = {
                "board_writer" : $("#board_writer").val(),
                "board_email" : $("#board_email").val(),
                "board_passwd" : $("#board_passwd").val(),
                "board_subject" : $("#board_subject").val(),
                "board_content" : $("#board_content").val(),
                "board_notice" : $("#board_notice").val(),
                "board_secret" : $("#board_secret").val()
        }
        var url = "${ path }/board_servelet/addProc.do";
    }
    $.ajax({
        type: "post",
        data: param,
        url: url,
        success: function(data) {
            if (value1 == "addProc") {
                select_page('1');
            } else {
                $("#result").html(data);
            }
        }
    });
}

function select_page(value1) {
    // $("#span_pageNumber").text(value1);
    // $("#span_no").text("");
    GoBoardPage('list');
}
</script>