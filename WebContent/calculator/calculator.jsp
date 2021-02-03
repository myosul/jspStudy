<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<script src="http://code.jquery.com/jquery-3.3.1.min.js"></script>
<body>
<table>
    <tr>
        <td align="right" style="width: 240px; height: 40px; border: 1px solid;">
            <span id="span_display" style="font-size: x-large;">${ inputNum }</span>
        </td>
    </tr>
    <tr>
        <td>
            <span id="span_resultNum">0</span>
            <span id="span_methType">${ methType }</span>
        </td>
    </tr>
    <tr>
        <td>
            <table border="1">
                <tr style="height: 60px;">
                    <td align="center" style="width: 60px;" onclick="inputNum('7')">7</td>
                    <td align="center" style="width: 60px;" onclick="inputNum('8')">8</td>
                    <td align="center" style="width: 60px;" onclick="inputNum('9')">9</td>
                    <td align="center" style="width: 60px;" onclick="methType('+')">+</td>
                </tr>
                <tr style="height: 60px;">
                    <td align="center" onclick="inputNum('4')">4</td>
                    <td align="center" onclick="inputNum('5')">5</td>
                    <td align="center" onclick="inputNum('6')">6</td>
                    <td align="center" onclick="methType('-')">-</td>
                </tr>
                <tr style="height: 60px;">
                    <td align="center" onclick="inputNum('1')">1</td>
                    <td align="center" onclick="inputNum('2')">2</td>
                    <td align="center" onclick="inputNum('3')">3</td>
                    <td align="center" onclick="methType('*')">*</td>
                </tr>
                <tr style="height: 60px;">
                    <td align="center" onclick="displayClear()">C</td>
                    <td align="center" onclick="inputNum('0')">0</td>
                    <td align="center" onclick="methType('=')">=</td>
                    <td align="center" onclick="methType('/')">/</td>
                </tr>
            </table>
        </td>
    </tr>
</table>

<script>
function inputNum(value1) {
    if ($("#span_methType").text() == "=") {
        displayClear();
    }
    var inputNum = $("#span_display").text() + value1;
    $("#span_display").text(inputNum);
}

function displayClear() {
    $("#span_display").text("");
    $("#span_resultNum").text("0");
    $("#span_methType").text("");
}

function methType(value1) {
    var methType = $("#span_methType").text();
    
    var num1 = $("#span_resultNum").text();
    if (num1 == "") {
        num1 = 0;
    } else {
        num1 = parseFloat(num1);
    }
    
    var num2 = $("#span_display").text();
    if (num2 == "") {
        num2 = num1;
    } else {
        num2 = parseFloat(num2);
    }
    
    var result = num1;
    
    if (methType == "") {
        result = num2;
    } else if (methType == "+") {
        result = num1 + num2;
    } else if (methType == "-") {
        result = num1 - num2;
    } else if (methType == "*") {
        result = num1 * num2;
    } else if (methType == "/") {
        result = num1 / num2;
    } else if (methType == "=") {
        result = num2;
    }
    
    if (value1 == "=") {
        $("#span_display").text(result);
    } else {
        $("#span_display").text("");
    }
    $("#span_resultNum").text(result);
    $("#span_methType").text(value1);
    
}

</script>

</body>
</html>