<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- 자주 쓰이는 자바스크립트 --%>
<script>
function GoPage(value1, value2, value3) {
    if (value1 == 'member_join') {
        location.href = '${ path }/member_servlet/join.do';
    } else if (value1 == 'member_login') {
        location.href = '${ path }/member_servlet/login.do';
    } else if (value1 == 'member_list') {
        location.href = '${ path }/member_servlet/list.do?pageNumber=' + value2;
    } else if (value1 == 'member_view') {
        location.href = '${ path }/member_servlet/view.do?pageNumber=' + value2 + '&member_no=' + value3;
    } else if (value1 == 'member_modify') {
        location.href = '${ path }/member_servlet/modify.do?member_no=' + value3;
    } else if (value1 == 'member_delete') {
        location.href = '${ path }/member_servlet/delete.do?member_no=' + value3;
    } else if (value1 == 'memo_write') {
        location.href = '${ path }/memo_servlet/write.do?pageNumber=' + value2;
    } else if (value1 == 'guestbook_list') {
        location.href = '${ path }/guestbook_servlet/list.do?pageNumber=' + value2;
    } else if (value1 == 'guestbook_write') {
        location.href = '${ path }/guestbook_servlet/write.do';
    } else if (value1 == 'survey1_list') {
        location.href = '${ path }/survey1_servlet/list.do?pageNumber=' + value2 + '&list_gubun=' + value3;
    } else if (value1 == 'survey1_search_list') {
        searchForm.method = 'post';
        searchForm.action = '${ path }/survey1_servlet/list.do?pageNumber=' + value2 + '&list_gubun=' + value3;
        searchForm.submit();
    } else if (value1 == 'survey1_view') {
        location.href = '${ path }/survey1_servlet/view.do?pageNumber=' + value2 + '&survey_no=' + value3;
    } else if (value1 == 'survey1_modify') {
        location.href = '${ path }/survey1_servlet/modify.do?survey_no=' + value3;
    } else if (value1 == 'survey1_add') {
        location.href = '${ path }/survey1_servlet/add.do';
    } else if (value1 == 'survey1_survey') {
        location.href = '${ path }/survey1_servlet/survey.do?pageNumber=' + value2 + '&list_gubun=' + value3;
    }
}

function noSpaceForm(obj) { // 공백을 사용할 수 없는 폼         
    var str_space = /\s/;   // 공백 체크
    if(str_space.exec(obj.value)) {   // 공백 체크
        alert("해당 항목에는 공백을 사용할 수 없습니다.\n\n공백이 제거됩니다.");
        obj.focus();
        obj.value = obj.value.replace(' ',''); // 공백제거
        return false;
    }
}
function noStartSpaceForm(obj) {    // 첫글자 공백 제거 폼
    if(obj.value == " ") {  // 공백 체크               
        alert("첫글자로 공백을 사용할 수 없습니다.\n\n공백이 제거됩니다.");
        obj.focus();
        obj.value = obj.value.replace(' ','');  // 공백 제거
        return false;
    }
}
</script>