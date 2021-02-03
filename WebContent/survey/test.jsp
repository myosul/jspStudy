<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<script src="http://code.jquery.com/jquery-3.3.1.min.js"></script>
<body>

<span id="hahaha"></span>
<hr>

<input type="checkbox" name="aaa" id="aaa" value="T" onclick="test();">

<script>
function test() {
    if ($("input:checkbox[id=aaa]").is(":checked") == true) {
        $("#hahaha").text($("#aaa").val());
    } else {
        $("#hahaha").text("");
    }
}
</script>

</body>
</html>