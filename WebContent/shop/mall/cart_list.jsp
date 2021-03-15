<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../include/inc_header.jsp" %>
<table border="0">
    <tr>
        <td><h2>장바구니 목록</h2></td>
    </tr>
    <tr>
        <td style="padding: 10px 0px 5px;">
            전체 ${ totalRecord } 건입니다.
        </td>
    </tr>
    <tr>
        <td>
            <table border="1" width="800px">
                <tr>
                    <th>
                        <input type="checkbox" name="checkAll" id="checkAll">
                    </th>
                    <th>상품사진</th>
                    <th>상품명</th>
                    <th>가격</th>
                    <th>구매수량</th>
                    <th>금액</th>
                    <th>등록일</th>
                </tr>
                <c:if test="${ list.size() <= 0 }">
                <tr>
                    <td colspan="10" align="center" height="100">장바구니가 비어있습니다.</td>
                </tr>
                </c:if>
                <c:set var="totalPrice" value="0"></c:set>
                <c:set var="i" value="1"></c:set>
                <c:forEach var="dto" items="${ list }">
                <span id="b_${ dto.cart_no }" style="display: ;">${ dto.member_no }, ${ dto.product_no }, ${ dto.product_amount }</span>
                <tr>
                    <td align="center">
                        <input type="checkbox" name="chk" id="chk" value="${ dto.cart_no }">${ dto.cart_no }
                    </td>
                    <td height="50px">
                        <c:choose>
                            <c:when test="${ fn:split(dto.product_img, ',')[0] == '-' }">
                                이미지X
                            </c:when>
                            <c:otherwise>
                                <c:set var="temp1" value="${ fn:split(dto.product_img, ',')[0] }"></c:set>
                                <c:set var="temp2" value="${ fn:split(temp1, '|')[0] }"></c:set>
                                <c:set var="temp3" value="${ fn:split(temp1, '|')[1] }"></c:set>
                                <img src="${ path }/attach/product_img/${ temp3 }" alt="${ dto.product_name }" title="${ dto.product_name }" style="width: 50px; height: 50px;">
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td>${ dto.product_name}</td>
                    <td><fmt:formatNumber type="number" maxFractionDigits="3" value="${ dto.product_price }"/></td>
                    <td><fmt:formatNumber type="number" maxFractionDigits="3" value="${ dto.product_amount}"/></td>
                    <td><fmt:formatNumber type="number" maxFractionDigits="3" value="${ dto.cart_buy_money }"/></td>
                    <td><fmt:formatDate value="${ dto.cart_regi_date }" type="both" dateStyle="medium"/></td>
                </tr>
                <c:set var="i" value="${ i = i + 1 }"></c:set>
                <c:set var="totalPrice" value="${ totalPrice = totalPrice + dto.cart_buy_money }"></c:set>
                </c:forEach>
                <tr>
                    <td colspan="7" align="right">
                        합계금액 : <fmt:formatNumber type="number" maxFractionDigits="3" value="${ totalPrice }"/>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
    <c:if test="${ totalRecord > 0 }">
    <c:set var="tempGubun" value="cart_list"></c:set>
    <tr>
        <td align="center" style="padding: 10px 0px;">
            <table>
                <tr>
                    <td>
                        <c:if test="${ startPage > blockSize }">
                            <a href="#" onclick="select_proc('${ tempGubun }', '1', '')">[처음]</a>
                        </c:if>
                        <c:if test="${ startPage <= blockSize }">
                            <p style="color: grey;">[처음]</p>
                        </c:if>
                    </td>
                    <td>
                        <c:if test="${ startPage > blockSize }">
                            <a href="#" onclick="select_proc('${ tempGubun }', ${ startPage = blockSize }, '')">[이전${ blockSize }개]</a>
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
                        <a href="#" onclick="select_proc('${ tempGubun }', ${ i }, '')">${ i }</a>
                    </td>
                    </c:if>
                    </c:forEach>
                    <td>
                        <c:if test="${ lastPage < totalPage }">
                            <a href="#" onclick="select_proc('${ tempGubun }', ${ startPage + blockSize }, '')">[다음${ blockSize }개]</a>
                        </c:if>
                        <c:if test="${ lastPage >= totalPage }">
                            <p style="color: grey;">[다음${ blockSize }개]</p>
                        </c:if>
                    </td>
                    <td>
                        <c:if test="${ lastPage < totalPage }">
                            <a href="#" onclick="select_proc('${ tempGubun }', ${ totalPage }, '')">[마지막]</a>
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
    <tr>
        <td align="right" style="padding: 20px 0px 0px;">
            <button type="button" onclick="select_proc('cart_clear', '1', '')">장바구니비우기</button>
            <button type="button" onclick="select_proc('mall_list', '1', '')">쇼핑하기</button>
            <button type="button" onclick="select_proc('buy', '', '')">주문하기</button>
        </td>
    </tr>
</table>

<script>
$(document).ready(function() {
    $("#checkAll").click(function() {
        if($("#checkAll").prop("checked")) {
            $("input[name=chk]").prop("checked", true);
        } else {
            $("input[name=chk]").prop("checked", false);
        }
    });
});
</script>