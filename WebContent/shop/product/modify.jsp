<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../include/inc_header.jsp" %>

<table border="1" align="center">
    <tr>
        <td colspan="2"><h2>상품정보수정</h2></td>
    </tr>
    <tr>
        <td width="150px">상품코드</td>
        <td>${ dto.product_no }<input type="hidden" name="product_no" id="product_no" value="${ dto.product_no }" readonly></td>
    </tr>
    <tr>
        <td>상품사진</td>
        <td>
            <c:choose>
                <c:when test="${ row.product_img == '-,-,-' }">
                    <a href="#" onclick="select_proc('view', '','${ dto.product_no }')">이미지X</a>
                </c:when>
                <c:otherwise>
                    <c:set var="temp1" value="${ fn:split(dto.product_img, ',')[0] }"></c:set>
                    <c:set var="temp2" value="${ fn:split(temp1, '|')[0] }"></c:set>
                    <c:set var="temp3" value="${ fn:split(temp1, '|')[1] }"></c:set>
                    <img src="${ path }/attach/product_img/${ temp3 }" alt="${ dto.product_name }" title="${ dto.product_name }" style="width: 50px; height: 50px;">
                </c:otherwise>
            </c:choose>
        </td>
    </tr>
    <tr>
        <td style="align: center;">상품명</td>
        <td>
            <input type="text" name="product_name" id="product_name" value="${ dto.product_name }">
        </td>
    </tr>
    <tr>
        <td style="align: center;">가격</td>
        <td><input type="number" name="product_price" id="product_price" value="${ dto.product_price }"></td>
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
            <button type="button" id="btnModify">수정하기</button>
        </td>
    </tr>
</table>

<script>
$(document).ready(function() {
    // $("#product_name").focus();
    
    $("#btnModify").click(function() {
        if (confirm('수정하시겠습니까?')) {
            select_proc('modifyProc', '1', '');
        }
    });
    
    $("#btnList").click(function() {
        select_proc('list', '1', '');
    });
});
</script>