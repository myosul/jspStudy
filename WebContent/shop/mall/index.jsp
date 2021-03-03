<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../include/inc_header.jsp" %>

proc : <span id="span_proc"></span><br>
pageNumber : <span id="span_pageNumber">${ pageNumber }</span><br>
cart_no : <span id="span_cart_no">${ cart_no }</span><br>
search_option : <span id="span_search_option">${ search_option }</span><br>
search_data : <span id="span_search_data">${ search_data }</span><br>
jumun_su : <span id="span_jumun_su">${ jumun_su }</span><br>

<input type="text" name="a" style="display: ;"><br><!-- ajax 테스트용 -->

<div id="result" style="border: 1px solid red; position: relative;"></div>

<script>
$(document).ready(function() {
    <c:if test="${ menu_gubun == 'mall_index'}">
    select_proc('list', '1', '');
    // select_proc('add', '1', '');
    </c:if>
});
</script>