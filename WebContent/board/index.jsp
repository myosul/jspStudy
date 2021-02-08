<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/inc_header.jsp" %>

menu_gubun : ${ menu_gubun }<br>
dateMap : ${ dateMap }<br>
ip : ${ ip }<br>
board_tbl : <span id="span_board_tbl">${ board_tbl }</span><br>
pageNumber : <span id="span_pageNumber">${ pageNumber }</span><br>
board_no : <span id="span_board_no">${ board_no }</span><br>
search_option : <span id="span_search_option">${ search_option }</span><br>
search_date : <span id="span_search_date">${ search_date }</span><br>

<input type="text" name="a" style="display: ;"><br><!-- ajax 테스트용 -->

<div id="result" style="border: 1px solid red; height: 500px;"></div>

<script>
$(document).ready(function() {
    <c:if test="${ menu_gubun == 'board_index'}">
    GoBoardPage('list', '');
    // GoBoardPage('add', '');
    </c:if>
});

function GoBoardPage(value1, value2) {
    var url = "${ path }/board_servlet/" + value1 + ".do";
    if (value1 == "add") {
        $("#span_board_no").text("");
        var param = {}
    } else if (value1 == "addProc") {
        var param = {
                "board_no" : $("#span_board_no").text(),
                "board_tbl" : $("#span_board_tbl").text(),
                "board_writer" : $("#board_writer").val(),
                "board_email" : $("#board_email").val(),
                "board_passwd" : $("#board_passwd").val(),
                "board_subject" : $("#board_subject").val(),
                "board_content" : $("#board_content").val(),
                "board_notice" : $("#board_notice").val(),
                "board_secret" : $("#board_secret").val()
        }
    } else if (value1 == "list") {
        var param = {
                "board_tbl" : $("#span_tbl").text(),
                "pageNumber" : $("#span_pageNumber").text(),
                "search_option" : $("#span_search_option").text(),
                "search_data" : $("#span_search_data").text(),
        }
    }
    $.ajax({
        type: "post",
        data: param,
        url: url,
        success: function(data) { // 콜백함수(서버에서 처리가 오나료된 후 실행되는 코드)
            if (value1 == "addProc") {
                select_page('1');
            } else {
                $("#result").html(data);
            }
        }
    });
}

function select_page(value1) {
    $("#span_pageNumber").text(value1);
    $("#span_board_no").text("");
    GoBoardPage('list', '');
}
</script>