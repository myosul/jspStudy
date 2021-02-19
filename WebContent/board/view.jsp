<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/inc_header.jsp" %>
<c:choose>
    <c:when test="${ imsiPage == 'viewPasswdPage' }">
        <table border="1" align="center" width="800px">
            <tr>
                <td colspan="2">
                    <h2>글상세보기 (비밀글입니다.)</h2>
                </td>
            </tr>
            <tr>
                <td width="150px">비밀번호 : </td>
                <td>
                    <input type="password" name="view_passwd" id="view_passwd">
                    <button type="button" id="btnViewPasswd">확인</button>
                </td>
            </tr>
        </table>
        <script>
        $(document).ready(function() {
            $("#btnViewPasswd").click(function() {
                GoBoardPage('view', $("#span_board_no").text());
            });
        });
        </script>
    </c:when>
    <c:otherwise>
    <table border="1" align="center" width="800px">
        <tr>
            <td colspan="2">
                <h1>글상세보기</h1>
                <input type="text" name="no" value="${ sto.board_no }">
            </td>
        </tr>
        <tr>
            <td width="150px">작성자 : </td>
            <td>${ dto.board_writer }</td>
        </tr>
        <tr>
            <td>제목 : </td>
            <td>${ dto.board_subject }</td>
        </tr>
        <tr>
            <td>내용 : </td>
            <td id="content">${ dto.board_content }</td>
        </tr>
        <tr>
            <td>이메일 : </td>
            <td>${ dto.board_email }</td>
        </tr>
        <tr>
            <td>조회수 : </td>
            <td>${ dto.board_hit }</td>
        </tr>
        <tr>
            <td>현재글의 답변글 : </td>
            <td>${ dto.board_child_counter }</td>
        </tr>
        <tr>
            <td colspan="2">
                <button type="button" onclick="select_page('1');">목록으로</button>
                
                <c:if test="${ dto.board_child_counter == 0 }">
                <button type="button" onclick="GoBoardPage('delete', '${ dto.board_no }');">삭제하기</button>
                </c:if>
                
                <button type="button" onclick="GoBoardPage('modify', '${ dto.board_no }');">수정하기</button>
                
                <c:if test="${ dto.board_notice_no == 0}">
                <button type="button" onclick="GoBoardPage('reply', '${ dto.board_no }');">답변쓰기</button>
                </c:if>
                
                <button type="button" onclick="GoBoardPage('add', '');">글쓰기</button>
                
            </td>
        </tr>
        <tr>
            <td colspan="2" height="50px" style="align: center;">
                <table border="1" width="100%">
                    <tr>
                        <td width="100px">이전글 : </td>
                        <td>
                            <c:if test="${ dto.board_pre_subject == null }">
                                이전글이 없습니다.
                            </c:if>
                            <c:if test="${ dto.board_pre_subject != null }">
                                <a href="#" onclick="GoBoardPage('view', '${ dto.board_pre_no }');">${ dto.board_pre_subject }</a>
                            </c:if>
                        </td>
                    </tr>
                    <tr>
                        <td width="100px">다음글 : </td>
                        <td>
                            <c:if test="${ dto.board_nxt_subject == null }">
                                다음글이 없습니다.
                            </c:if>
                            <c:if test="${ dto.board_nxt_subject != null }">
                                <a href="#" onclick="GoBoardPage('view', '${ dto.board_nxt_no }');">${ dto.board_nxt_subject }</a>
                            </c:if>
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
        <tr>
            <td colspan="2" height="50px" style="padding: 20 0 0 0;">
            
                <a name="comment"></</a>
                comment_pageNumber : <span id="span_comment_pageNumber">${ comment_pageNumber }</span><br>
                <div id="commentResult"></div>
                
            </td>
        </tr>
    </table>
    <script>
    $(document).ready(function() {
        var content = $("#content").text().replace(/(?:\r\n|\r|\n)/g, '<br>');
        $("#content").html(content);
        comment_list();
    });
    
    function comment_list() {
        var param = {
                "board_no" : $("#span_board_no").text(),
                "comment_pageNumber" : $("#span_comment_pageNumber").text()
        }
        var url =  "${ path }/board_servlet/commentList.do";
        
        $.ajax({
            type: "post",
            data: param,
            url: url,
            success: function(data) {
                $("#commentResult").html(data);
            }
        });
    }
    </script>
    </c:otherwise>
</c:choose>