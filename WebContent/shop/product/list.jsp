<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../include/inc_header.jsp" %>
<table border="0">
    <tr>
        <td><h2>상품 목록</h2></td>
    </tr>
    <tr>
        <td>
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
        </td>
    </tr>
    <tr>
        <td style="padding: 10px 0px;">
            전체 ${ totalRecord } 건입니다.
        </td>
    </tr>
    <tr>
        <td>
            <table border="1" width="800px">
                <tr>
                    <th>순번</th>
                    <th>상품사진</th>
                    <th>상품명</th>
                    <th>가격</th>
                    <th>파일</th>
                    <th>장바구니수</th>
                    <th>등록일</th>
                </tr>
                <c:if test="${ list.size() <= 0 }">
                <tr>
                    <td colspan="10" align="center" height="100">등록된 상품이 없습니다.</td>
                </tr>
                </c:if>
                <c:forEach var="dto" items="${ list }">
                <tr>
                    <td>${ recordNum }</td>
                    <td>
                        <c:choose>
                            <c:when test="${ row.product_img == '-,-,-' }">
                                <a href="#" onclick="select_proc('view', '','${ dto.product_no }')">이미지X</a>
                            </c:when>
                            <c:otherwise>
                                <c:set var="temp1" value="${ fn:split(dto.product_img, ',')[0] }"></c:set>
                                <c:set var="temp2" value="${ fn:split(temp1, '|')[0] }"></c:set>
                                <c:set var="temp3" value="${ fn:split(temp1, '|')[1] }"></c:set>
                                <a href="#" onclick="select_proc('view', '', '${ dto.product_no }')">
                                    <img src="${ path }/attach/product_img/${ temp3 }" alt="${ dto.product_name }" title="${ dto.product_name }" style="width: 50px; height: 50px;">
                                </a>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td>${ dto.product_name}</td>
                    <td><fmt:formatNumber type="number" maxFractionDigits="3" value="${ dto.product_price}"/></td>
                    <td>${ dto.product_img}</td>
                    <td><fmt:formatNumber type="number" maxFractionDigits="3" value="0"/></td>
                    <td><fmt:formatDate value="${ dto.product_regi_date }" type="both" dateStyle="medium"/></td>
                </tr>
                <c:set var="recordNum" value="${ recordNum = recordNum - 1 }"></c:set>
                </c:forEach>
            </table>
        </td>
    </tr>
    <c:if test="${ totalRecord > 0 }">
    <tr>
        <td align="center" style="padding: 10px 0px;">
            <table>
                <tr>
                    <td>
                        <c:if test="${ startPage > blockSize }">
                            <a href="#" onclick="select_proc('list', '1', '')">[처음]</a>
                        </c:if>
                        <c:if test="${ startPage <= blockSize }">
                            <p style="color: grey;">[처음]</p>
                        </c:if>
                    </td>
                    <td>
                        <c:if test="${ startPage > blockSize }">
                            <a href="#" onclick="select_proc('list', ${ startPage = blockSize }, '')">[이전${ blockSize }개]</a>
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
                        <a href="#" onclick="select_proc('list', ${ i }, '')">${ i }</a>
                    </td>
                    </c:if>
                    </c:forEach>
                    <td>
                        <c:if test="${ lastPage < totalPage }">
                            <a href="#" onclick="select_proc('list', ${ startPage + blockSize }, '')">[다음${ blockSize }개]</a>
                        </c:if>
                        <c:if test="${ lastPage >= totalPage }">
                            <p style="color: grey;">[다음${ blockSize }개]</p>
                        </c:if>
                    </td>
                    <td>
                        <c:if test="${ lastPage < totalPage }">
                            <a href="#" onclick="select_proc('list', ${ totalPage }, '')">[마지막]</a>
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
            <button type="button" onclick="select_proc('list', '1', '')">전체목록</button>&nbsp;
            <button type="button" onclick="select_proc('add', '1', '')">상품등록</button>
        </td>
    </tr>
</table>

<script>
function search() {
    if (confirm('검색 OK?')) {
        $("#span_search_option").text($("#search_option").val());
        $("#span_search_data").text($("#search_data").val());
        select_page('1');
    }
}

function select_all() {
    $("#span_search_option").text("");
    $("#span_search_data").text("");
    select_page('1');
}
</script>