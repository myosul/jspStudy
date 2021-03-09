<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../include/inc_header.jsp" %>

<div id="result" style="border: 1px solid red; position: relative;"></div>

<script>
$(document).ready(function() {
    <c:if test="${ menu_gubun == 'email_index'}">
    	GoPage('add');
    </c:if>
});

function GoPage(value1) {
    var url = "${ path }/email_servlet/" + value1 + ".do";
    
    if (value1 == "add") {
        var param = {}
    } else if (value1 == "addProc") {
        var param = {
                "fromName": $("#fromName").val(),
                "fromEmail": $("#fromEmail").val(),
                "toEmail": $("#toEmail").val(),
                "subject": $("#subject").val(),
                "content": $("#content").val()
        }
    }
    
    $.ajax({
        type: "post",
        data: param,
        url: url,
        success: function(data) {
            if (value1 == "add") {
                $("#result").html(data);
            } else {
                GoPage('add');
            }
        }
    });
}
</script>