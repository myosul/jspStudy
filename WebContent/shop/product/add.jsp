<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../include/inc_header.jsp" %>

<table border="1" align="center">
    <tr>
        <td colspan="2"><h2>상품등록</h2></td>
    </tr>
    <tr>
        <td style="align: center;">상품명</td>
        <td><input type="text" name="product_name" id="product_name"></td>
    </tr>
    <tr>
        <td style="align: center;">가격</td>
        <td><input type="number" name="product_price" id="product_price"></td>
    </tr>
    <tr>
        <td style="align: center;">상품설명</td>
        <td><textarea name="product_description" id="product_description" style="width: 640px; height: 100px;" wrap="hard">${ dto.product_description }</textarea></td>
    </tr>
    <tr>
        <td style="align: center;">상품이미지</td>
        <td>
            <input type="file" name="file">
            <input type="file" name="file">
            <input type="file" name="file">
        </td>
    </tr>
    <tr>
        <td colspan="2" height="50px;">
            <button type="button" id="btnList">목록으로</button>
            <button type="button" id="btnAdd">등록하기</button>
        </td>
    </tr>
</table>

<script>
$(document).ready(function() {
    $("#product_name").focus();
    
    $("#btnAdd").click(function() {
        if (confirm('등록하시겠습니까?')) {
            select_proc('addProc', '1', '');
        }
    });
    
    $("#btnList").click(function() {
        select_proc('list', '1', '');
    });
});
</script>