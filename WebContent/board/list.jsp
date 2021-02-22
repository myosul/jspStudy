<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/inc_header.jsp" %>
<table border="0">
    <tr>
        <td><h2>${ board_tbl_name } 목록</h2></td>
    </tr>
    <tr>
        <td>
            <select name="search_option" id="search_option">
            <c:choose>
                <c:when test="${ search_option == 'writer_subject_content' }">
                    <option value="">- 선택 -</option>
                    <option value="writer_subject_content" selected>작성자+제목+내용</option>
                    <option value="board_writer">작성자</option>
                    <option value="board_subject">제목</option>
                    <option value="board_content">내용</option>
                </c:when>
                <c:when test="${ search_option == 'board_writer' }">
                    <option value="">- 선택 -</option>
                    <option value="writer_subject_content">작성자+제목+내용</option>
                    <option value="board_writer" selected>작성자</option>
                    <option value="board_subject">제목</option>
                    <option value="board_content">내용</option>
                </c:when>
                <c:when test="${ search_option == 'board_subject' }">
                    <option value="">- 선택 -</option>
                    <option value="writer_subject_content">작성자+제목+내용</option>
                    <option value="board_writer">작성자</option>
                    <option value="board_subject" selected>제목</option>
                    <option value="board_content">내용</option>
                </c:when>
                <c:when test="${ search_option == 'board_content' }">
                    <option value="">- 선택 -</option>
                    <option value="writer_subject_content">작성자+제목+내용</option>
                    <option value="board_writer">작성자</option>
                    <option value="board_subject">제목</option>
                    <option value="board_content" selected>내용</option>
                </c:when>
                <c:otherwise>
                    <option value="">- 선택 -</option>
                    <option value="writer_subject_content">작성자+제목+내용</option>
                    <option value="board_writer">작성자</option>
                    <option value="board_subject">제목</option>
                    <option value="board_content">내용</option>
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
                    <th>제목</th>
                    <th>작성자</th>
                    <th>등록일</th>
                    <th>조회</th>
                    <th>ip</th>
                    <th>회원번호</th>
                    <th>공지</th>
                    <th>비밀글</th>
                    <th>자식글여부</th>
                </tr>
                <c:if test="${ list.size() <= 0 }">
                <tr>
                    <td colspan="10" align="center" height="100">등록된 게시글이 없습니다.</td>
                </tr>
                </c:if>
                <c:forEach var="dto" items="${ list }">
                <tr>
                    <td>
                        <c:if test="${ dto.board_notice_no > 0 }">
                            공지
                        </c:if>
                        <c:if test="${ dto.board_notice_no == 0 }">
                            ${ recordNum }
                        </c:if>
                    </td>
                    <td>
                        <c:forEach var="i" begin="2" end="${ dto.board_step_no }" step="1"><!-- varStatus="status" -->
                            &nbsp;
                        </c:forEach>
                        <c:if test="${ dto.board_step_no > 1 }">
                            <c:set var="reVar" value="[Re]:" />
                        </c:if>
                        <c:if test="${ dto.board_step_no <= 1 }">
                            <c:set var="reVar" value="" />
                        </c:if>
                        
                        <a href="#" onclick="GoBoardPage('view', '${ dto.board_no }')">${ reVar } ${ dto.board_subject }</a>
                    </td>
                    <td>${ dto.board_writer}</td>
                    <td><fmt:formatDate value="${ dto.board_regi_date }" type="both" dateStyle="medium"/></td>
                    <td>${ dto.board_hit }</td>
                    <td>${ dto.board_ip }</td>
                    <td>${ dto.member_no }</td>
                    <td>${ dto.board_notice_no }</td>
                    <td>${ dto.board_secret }</td>
                    <td></td>
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
                            <a href="#" onclick="select_page('1')">[처음]</a>
                        </c:if>
                        <c:if test="${ startPage <= blockSize }">
                            <p style="color: grey;">[처음]</p>
                        </c:if>
                    </td>
                    <td>
                        <c:if test="${ startPage > blockSize }">
                            <a href="#" onclick="select_page(${ startPage = blockSize })">[이전${ blockSize }개]</a>
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
                        <a href="#" onclick="select_page(${ i })">${ i }</a>
                    </td>
                    </c:if>
                    </c:forEach>
                    <td>
                        <c:if test="${ lastPage < totalPage }">
                            <a href="#" onclick="select_page(${ startPage + blockSize })">[다음${ blockSize }개]</a>
                        </c:if>
                        <c:if test="${ lastPage >= totalPage }">
                            <p style="color: grey;">[다음${ blockSize }개]</p>
                        </c:if>
                    </td>
                    <td>
                        <c:if test="${ lastPage < totalPage }">
                            <a href="#" onclick="select_page(${ totalPage })">[마지막]</a>
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
            <button type="button" onclick="select_all()">전체목록</button>&nbsp;
            <button type="button" onclick="GoBoardPage('add', '')">글쓰기</button>
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