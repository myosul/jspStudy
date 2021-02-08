<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/inc_header.jsp" %>
<table border="0">
    <tr>
        <td colspan="10"><h2>게시 목록</h2></td>
    </tr>
    <tr>
        <td colspan="10">
            <select name="search_option" id="search_option">
            <c:choose>
                <c:when test="${ search_option == 'writer_subject_content' }">
                    <option value="">- 선택 -</option>
                    <option value="writer_subject_content" selected>작성자+제목+내용</option>
                    <option value="writer">작성자</option>
                    <option value="subject">제목</option>
                    <option value="content">내용</option>
                </c:when>
                <c:when test="${ search_option == 'writer' }">
                    <option value="">- 선택 -</option>
                    <option value="writer_subject_content">작성자+제목+내용</option>
                    <option value="writer" selected>작성자</option>
                    <option value="subject">제목</option>
                    <option value="content">내용</option>
                </c:when>
                <c:when test="${ search_option == 'subject' }">
                    <option value="">- 선택 -</option>
                    <option value="writer_subject_content">작성자+제목+내용</option>
                    <option value="writer">작성자</option>
                    <option value="subject" selected>제목</option>
                    <option value="content">내용</option>
                </c:when>
                <c:when test="${ search_option == 'content' }">
                    <option value="">- 선택 -</option>
                    <option value="writer_subject_content">작성자+제목+내용</option>
                    <option value="writer">작성자</option>
                    <option value="subject">제목</option>
                    <option value="content" selected>내용</option>
                </c:when>
                <c:otherwise>
                    <option value="">- 선택 -</option>
                    <option value="writer_subject_content">작성자+제목+내용</option>
                    <option value="writer">작성자</option>
                    <option value="subject">제목</option>
                    <option value="content">내용</option>
                </c:otherwise>
            </c:choose>
            </select>
            <input type="text" name="search_data" id="search_data" value="${ search_data }" style="width: 150px;">&nbsp;
            <input type="button" value="검색" onclick="search();">
        </td>
    </tr>
    <tr>
        <td colspan="10" style="padding: 10px 0px;">
            전체 ${ totalRecord } 건입니다.
        </td>
    </tr>
    <tr>
        <td>
            <table border="1" width="800px">
                <tr>
                    <th style="width: 32px;">순번</th>
                    <th>제목</th>
                    <th style="width: 60px;">작성자</th>
                    <th style="width: 100px;">등록일</th>
                    <th style="width: 32px;">조회</th>
                    <th style="width: 80px;">ip</th>
                    <th style="width: 60px;">회원번호</th>
                    <th style="width: 32px;">공지</th>
                    <th style="width: 48px;">비밀글</th>
                    <th style="width: 80px;">자식글여부</th>
                </tr>
                <c:if test="${ list.size() <= 0 }">
                <tr>
                    <td colspan="10" align="center" height="100">등록된 게시글이 없습니다.</td>
                </tr>
                </c:if>
                <c:forEach var="dto" items="${ list }">
                <tr>
                    <td>${ recordNum }</td>
                    <td><a href="#" onclick="">${ dto.board_subject }</a></td>
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
        <td colspan="10" align="center" style="padding: 10px 0px;">
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
        <td colspan="10" align="right" style="padding: 20px 0px 0px;">
            <button type="button" onclick="GoBoardPage('list', '')">전체목록</button>&nbsp;
            <button type="button" onclick="GoBoardPage('add', '')">글쓰기</button>
        </td>
    </tr>
</table>
