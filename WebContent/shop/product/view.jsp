<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../include/inc_header.jsp" %>

<table border="1" align="center" width="800px">
    <tr>
        <td colspan="2">
            <h1>상품상세보기</h1>
        </td>
    </tr>
    <tr>
        <td width="150px">상품코드 : </td>
        <td>${ dto.product_no }</td>
    </tr>
    <tr>
        <td>상품사진 : </td>
        <td></td>
    </tr>
    <tr>
        <td>상품명 : </td>
        <td>${ dto.product_name }</td>
    </tr>
    <tr>
        <td>가격 : </td>
        <td>${ dto.product_price }</td>
    </tr>
    <tr>
        <td>상품설명 : </td>
        <td id="content">${ dto.product_description }</td>
    </tr>
    <tr>
        <td>파일명 : </td>
        <td>${ dto.product_img }</td>
    </tr>
    <tr>
        <td>등록일 : </td>
        <td>${ dto.product_regi_date }</td>
    </tr>
    <tr>
        <td>장바구니수량 : </td>
        <td></td>
    </tr>
    <tr>
        <td colspan="2">
            <button type="button" onclick="select_page('1');">목록으로</button>
            
            <c:if test="${ dto.board_child_counter == 0 }">
            <button type="button" onclick="GoProductPage('delete', '${ dto.product_no }');">삭제하기</button>
            </c:if>
            
            <button type="button" onclick="GoProductPage('modify', '${ dto.product_no }');">수정하기</button>
            
            <button type="button" onclick="GoProductPage('add', '');">글쓰기</button>
            
        </td>
    </tr>
    <tr>
        <td colspan="2" height="50px" style="align: center;">
            <table border="1" width="100%">
                <tr>
                    <td width="100px">이전상품 : </td>
                    <td>
                        <c:if test="${ dto.product_pre_name == null }">
                            이전상품이 없습니다.
                        </c:if>
                        <c:if test="${ dto.product_pre_name != null }">
                            <a href="#" onclick="GoProductPage('view', '${ dto.product_pre_no }');">${ dto.product_pre_name }</a>
                        </c:if>
                    </td>
                </tr>
                <tr>
                    <td width="100px">다음상품 : </td>
                    <td>
                        <c:if test="${ dto.product_nxt_name == null }">
                            다음상품이 없습니다.
                        </c:if>
                        <c:if test="${ dto.product_nxt_name != null }">
                            <a href="#" onclick="GoProductPage('view', '${ dto.product_nxt_no }');">${ dto.product_nxt_name }</a>
                        </c:if>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>
<script>
$(document).ready(function() {
    var content = $("#content").text().replace(/(?:\r\n|\r|\n)/g, '<br>');
    $("#content").html(content);
});
</script>