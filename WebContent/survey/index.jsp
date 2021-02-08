<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/inc_header.jsp" %>
<!-- hidden 영역 시작 -->
<input type="text" name="a" style="display: ;"><br>
<div style="display: ;">
    list_gubun : <span id="span_list_gubun">${ list_gubun }</span><br>
    pageNumber : <span id="span_pageNumber">${ pageNumber }</span><br>
    survey_no : <span id="survey_no">${ survey_no }</span><br>
    search_option : <span id="span_search_option">${ search_option }</span><br>
    search_data : <span id="span_search_data">${ search_data }</span><br>
    
    search_date_check : <span id="span_search_date_check">${ search_date_check }</span><br>
    search_date_s : <span id="span_search_date_s">${ search_date_s }</span><br>
    search_date_e : <span id="span_search_date_e">${ search_date_e }</span><br>
</div>
<!-- hidden 영역 종료 -->

<div id="result"></div>

<c:if test="${ menu_gubun == 'survey_index' }">
    <script>
    $(document).ready(function() {
        GoList();
    	// GoAdd();
    });
    </script>
</c:if>

<script>
function GoAdd() {
    var param = {}
    $.ajax({
        type: "post",
        data: param,
        url: "${ path }/survey_servlet/add.do",
        success: function(data) {
            $("#result").html(data);
        }
    });
}

function GoView(value1) {
    var param = {
            "survey_no" : value1
    }
    $.ajax({
        type: "post",
        data: param,
        url: "${ path }/survey_servlet/view.do",
        success: function(data) {
            $("#result").html(data);
        }
    });
}

function GoModify(value1) {
    var param = {
            "survey_no" : value1
    }
    $.ajax({
        type: "post",
        data: param,
        url: "${ path }/survey_servlet/modify.do",
        success: function(data) {
            $("#result").html(data);
        }
    });
}

function GoList() {
    var param = {
        "list_gubun" : $("#span_list_gubun").text(),
        "pageNumber" : $("#span_pageNumber").text(),
        "search_option" : $("#span_search_option").text(),
        "search_data" : $("#span_search_data").text(),
        "search_date_check" : $("#span_search_date_check").text(),
        "search_date_s" : $("#span_search_date_s").text(),
        "search_date_e" : $("#span_search_date_e").text()
    }
    $.ajax({
        type: "post",
        data: param,
        url: "${ path }/survey_servlet/list.do",
        success: function(result) {
            $("#result").html(result);
            if ($("#span_search_date_check").text() == "O") {
                $("input[id=search_date_check]:checkbox").prop("checked", true)
            } else {
                $("input[id=search_date_check]:checkbox").prop("checked", false)
            }
        }
    });
}

function select_page(value1) {
    $("#span_pageNumber").text(value1); // 값 담기
    GoList();
}

function select_list(value1) {
    $("#span_list_gubun").text(value1);
    $("#span_pageNumber").text(1);
    GoList();
}

function GoSurvey() {
    var param = {
            "list_gubun" : $("#span_list_gubun").text(),
            "pageNumber" : $("#span_pageNumber").text()
    }
    $.ajax({
        type: "post",
        data: param,
        url: "${ path }/survey_servlet/survey.do",
        success: function(result) {
            $("#result").html(result);
        }
    });
}

function select_survey_page(value1) {
    $("#span_pageNumber").text(value1); // 값 담기
    GoSurvey();
}

function select_survey(value1) {
    $("#span_list_gubun").text(value1);
    $("#span_pageNumber").text(1);
    GoSurvey();
}
</script>