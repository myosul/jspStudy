<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../include/inc_header.jsp" %>
<table border="0" align="center" width="80%">
    <tr>
        <td colspan="7">
            <h2>Shopping Mall</h2>
        </td>
    </tr>
    <tr>
        <td colspan="7">
            <select name="search_option" id="search_option">
            <c:choose>
                <c:when test="${ search_option == 'name_description' }">
                    <option value="">- 선택 -</option>
                    <option value="name_description" selected>상품이름+상품설명</option>
                    <option value="product_name">상품이름</option>
                    <option value="product_description">상품설명</option>
                </c:when>
                <c:when test="${ search_option == 'product_name' }">
                    <option value="">- 선택 -</option>
                    <option value="name_description">상품이름+상품설명</option>
                    <option value="product_name" selected>상품이름</option>
                    <option value="product_description">상품설명</option>
                </c:when>
                <c:when test="${ search_option == 'product_description' }">
                    <option value="">- 선택 -</option>
                    <option value="name_description">상품이름+상품설명</option>
                    <option value="product_name">상품이름</option>
                    <option value="product_description" selected>상품설명</option>
                </c:when>
                <c:otherwise>
                    <option value="">- 선택 -</option>
                    <option value="name_description">상품이름+상품설명</option>
                    <option value="product_name">상품이름</option>
                    <option value="product_description">상품설명</option>
                </c:otherwise>
            </c:choose>
            </select>
            <input type="text" name="search_data" id="search_data" value="${ search_data }" style="width: 150px;">
            <input type="button" value="검색" onclick="search();">
            <script>
            function search() {
                if (confirm('검색 OK?')) {
                    select_proc('mall_search', '1', '');
                }
            }
            </script>
        </td>
    </tr>
    <tr>
        <td style="padding: 10px 0px;">
            전체 ${ totalRecord } 건입니다.
        </td>
    </tr>
    <c:if test="${ totalRecord == 0 }">
    <tr>
        <td colspan="10" align="center" height="100">등록된 상품이 없습니다.</td>
    </tr>
    </c:if>
    <c:if test="${ totalRecord > 0 }">
    <tr>
        <td style="padding: 0 0 20px 0;">
            <c:set var="cell_counter" value="3"></c:set>
            
            <c:set var="k" value="0"></c:set>
            <table border="1" align="center" width="100%">
                <c:forEach var="dto" items="${ list }">
                    <c:set var="k" value="${ k = k + 1 }"></c:set>
                    <c:if test="${ k mod cell_counter == 1 }">
                    <c:set var="imsi_counter" value="0"></c:set>
                    <tr>
                    </c:if>
                        <td>
                        <c:set var="imsi_counter" value="${ imsi_counter = imsi_counter + 1 }"></c:set>
                        <table border="1" align="center" width="50%">
                            <tr>
                                <td align="center" height="50px">
                                <c:choose>
                                    <c:when test="${ fn:split(dto.product_img, ',')[0] == '-' }">
                                        <a href="#" onclick="select_proc('mall_view', '', '${ dto.product_no}');">이미지X</a>
                                    </c:when>
                                    <c:otherwise>
                                        <c:set var="temp1" value="${ fn:split(dto.product_img, ',')[0] }"></c:set>
                                        <c:set var="temp2" value="${ fn:split(temp1, '|')[0] }"></c:set>
                                        <c:set var="temp3" value="${ fn:split(temp1, '|')[1] }"></c:set>
                                        <a href="#" onclick="select_proc('mall_view', '', '${ dto.product_no }');">
                                            <img src="${ path }/attach/product_img/${ temp3 }" alt="${ dto.product_name }" title="${ dto.product_name }" style="width: 50px; height: 50px;">
                                        </a>
                                    </c:otherwise>
                                </c:choose>
                                </td>
                            </tr>
                            <tr>
                                <td align="center">${ dto.product_name }</td>
                            </tr>
                            <tr>
                                <td align="center"><fmt:formatNumber type="number" maxFractionDigits="3" value="${ dto.product_price }" /></td>
                            </tr>
                        </table>
                        <c:if test="${ k mod cell_counter == 0 }">
                        </tr>
                        </c:if>
                        <c:set var="recordNum" value="${ recordNum = recordNum - 1 }"></c:set>
                </c:forEach>
                
                <c:if test="${ imsi_counter < cell_counter }">
                <c:forEach var="i" begin="${ imsi_counter + 1 }" end="${ cell_counter }" step="1">
                    <td>&nbsp;</td>
                </c:forEach>
                </tr>
                </c:if>
                
            </table>
        </td>
    </tr>
    <tr>
        <td align="center" style="padding: 10px 0px;">
            <table>
                <tr>
                    <td>
                        <c:if test="${ startPage > blockSize }">
                            <a href="#" onclick="select_proc('mall_list', '1', '')">[처음]</a>
                        </c:if>
                        <c:if test="${ startPage <= blockSize }">
                            <p style="color: grey;">[처음]</p>
                        </c:if>
                    </td>
                    <td>
                        <c:if test="${ startPage > blockSize }">
                            <a href="#" onclick="select_proc('mall_list', ${ startPage = blockSize }, '')">[이전${ blockSize }개]</a>
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
                        <a href="#" onclick="select_proc('mall_list', ${ i }, '')">${ i }</a>
                    </td>
                    </c:if>
                    </c:forEach>
                    <td>
                        <c:if test="${ lastPage < totalPage }">
                            <a href="#" onclick="select_proc('mall_list', ${ startPage + blockSize }, '')">[다음${ blockSize }개]</a>
                        </c:if>
                        <c:if test="${ lastPage >= totalPage }">
                            <p style="color: grey;">[다음${ blockSize }개]</p>
                        </c:if>
                    </td>
                    <td>
                        <c:if test="${ lastPage < totalPage }">
                            <a href="#" onclick="select_proc('mall_list', ${ totalPage }, '')">[마지막]</a>
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
        <td colspan="7" height="50" align="right">
            <button type="button" onclick="select_proc('mall_list', '1', '');">상품목록</button>
            <button type="button" onclick="select_proc('cart_list', '1', '');">장바구니</button>
        </td>
    </tr>
</table>
