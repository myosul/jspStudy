<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../include/inc_header.jsp" %>

proc : <span id="span_proc"></span><br>
pageNumber : <span id="span_pageNumber">${ pageNumber }</span><br>
product_no : <span id="span_product_no">${ product_no }</span><br>
search_option : <span id="span_search_option">${ search_option }</span><br>
search_data : <span id="span_search_data">${ search_data }</span><br>
jumun_su : <span id="span_jumun_su">${ jumun_su }</span><br>

<input type="text" name="a" style="display: ;"><br><!-- ajax 테스트용 -->

<div id="result" style="border: 1px solid red; position: relative;"></div>

<script>
$(document).ready(function() {
    <c:if test="${ menu_gubun == 'mall_index'}">
    select_proc('mall_list', '1', '');
    </c:if>
});

function select_proc(value1, value2, value3) {
    if (value1 == 'mall_list') {
        $("#span_proc").text(value1);
        $("#span_product_no").text("");
    } else if (value1 == 'mall_view') {
        $("#span_proc").text(value1);
        $("#span_product_no").text(value3);
    } else if (value1 == 'mall_search') {
        value1 = 'mall_list'
        $("#span_proc").text(value1);
        $("#span_product_no").text("");
        $("#span_search_option").text($("#search_option").val());
        $("#span_search_data").text($("#search_data").val());
    } else if (value1 == 'cart_addProc') {
        $("#span_proc").text(value1);
        $("#span_product_no").text(value3);
        $("#span_product_amount").text($("#product_amount").val());
    }  else if (value1 == 'cart_list') {
        $("#span_proc").text(value1);
        $("#span_product_no").text("");
        $("#span_product_amount").text("");
    } else if (value1 == 'cart_view') {
        if (confirm('제품상세보기 페이지로 이동하시겠습니까?')) {
             value1 = 'mall_view';
             $("#span_proc").text(value1);
             $("#span_product_no").text(value3);
        } else {
            return;
        }
    } else if (value1 == 'cart_clear') {
        if (confirm('정말 비우시겠습니까?')) {
            $("#span_proc").text(value1);
            $("#span_product_no").text("");
            $("#span_product_amount").text("");
       } else {
           return;
       }
    } else {
        $("#span_proc").text(value1);
    }
    
    if (value2 != '') {
        $("#span_pageNumber").text(value2);
    }
    
    GoPage(value1);
}

function GoPage(value1) {
    var param;
    var url = "${ path }/mall_servlet/" + value1 + ".do";
    
    if (value1 == "mall_list") {
        param = {};
        param.pageNumber = $("#span_pageNumber").text();
        param.search_option = $("#span_search_option").text();
        param.search_data = $("#span_search_data").text();
    } else if (value1 == "mall_view") {
        param = {};
        param.product_no = $("#span_product_no").text();
        param.search_option = $("#span_search_option").text();
        param.search_data = $("#span_search_data").text();
    } else if (value1 == "cart_addProc") {
        param = {};
        param.product_no = $("#span_product_no").text();
        param.product_amount = $("#product_amount").val();
    } else if (value1 == "cart_list") {
        param = {};
        param.cart_no = $("#span_cart_no").text();
        param.pageNumber = $("#span_pageNumber").text();
    } else if (value1 == 'cart_clear') {
        var chk_no = '';
        $('input[name="chk"]:checked').each(function(index) {
            if (index != 0) {
                chk_no += ',';
            }
            chk_no += $(this).val();
        });
        alert(chk_no);
        param = {};
        param.chk_no = chk_no;
    }
    
    $.ajax ({
        type: "post",
        data: param,
        url: url,
        success: function(data) {
            $("#result").html(data);
        }
    });
}
</script>