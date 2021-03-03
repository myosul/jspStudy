<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../include/inc_header.jsp" %>

proc : <span id="span_proc"></span><br>
pageNumber : <span id="span_pageNumber">${ pageNumber }</span><br>
product_no : <span id="span_product_no">${ product_no }</span><br>
search_option : <span id="span_search_option">${ search_option }</span><br>
search_data : <span id="span_search_data">${ search_data }</span><br>

<input type="text" name="a" style="display: ;"><br><!-- ajax 테스트용 -->

<div id="result" style="border: 0px solid red; position: relative;"></div>

<script>
$(document).ready(function() {
    <c:if test="${ menu_gubun == 'product_index'}">
    select_proc('list', '1', '');
    // select_proc('add', '1', '');
    </c:if>
});

function select_proc(value1, value2, value3) {
    if (value1 == "add" || value1 == "list") {
        $("#span_product_no").text("");
    } else if (value1 == "addProc" || value1 == "deleteProc") {
        $("#span_product_no").text("");
    }
    
    if (value1 != '') {
        $("#span_proc").text(value1);
    }
    if (value2 != '') {
        $("#span_pageNumber").text(value2);
    }
    if (value3 != '') {
        $("#span_product_no").text(value3);
    }
    
    GoProductPage(value1);
}

function GoProductPage(value1) {
    var param;
    var process_data;
    var content_type;
    var url = "${ path }/product_servlet/" + value1 + ".do";
    
    if (value1 == "add") {
        param = {}
    } else if (value1 == "addProc" || value1 == "modifyProc") {
        process_data = false;
        content_type = false;
        
        param = new FormData();
        
        param.append("product_no", $("#product_no").val());
        param.append("product_name", $("#product_name").val());
        param.append("product_price", $("#product_price").val());
        param.append("product_description", $("#product_description").val());
        /* 
        console.log($('input[name="file"]')[0].files[0]);
        console.log($('input[name="file"]')[1].files[0]);
        console.log($('input[name="file"]')[2].files[0]);
        return;
         */
        var file_counter = parseInt($('input[name="file"]').length);
        for (i=0; i<file_counter; i++) {
            param.append(i, $('input[name="file"]')[i].files[0]);
        }
    } else if (value1 == "list") {
        param = {
                "pageNumber" : $("#span_pageNumber").text(),
                "search_option" : $("#span_search_option").text(),
                "search_data" : $("#span_search_data").text()
        }
    } else if (value1 == "view" || value1 == "modify" || value1 == "deleteProc") {
        param = {
                "product_no" : $("#span_product_no").text(),
                "pageNumber" : $("#span_pageNumber").text(),
                "search_option" : $("#span_search_option").text(),
                "search_data" : $("#span_search_data").text()
        }
    }
    $.ajax({
        type: "post",
        data: param,
        processData: process_data,
        contentType: content_type,
        url: url,
        success: function(data) { // 콜백함수(서버에서 처리가 완료된 후 실행되는 코드)
            $("#result").html(data);
        }
    });
}
</script>