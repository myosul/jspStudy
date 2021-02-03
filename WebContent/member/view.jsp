<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/inc_header.jsp" %>
<table border="1">
    <tr>
        <td colspan="2"><h2>회원정보</h2></td>
    </tr>
    <tr>
        <td style="width: 150px;">아이디</td>
        <td>${ dto.member_id }</td>
    </tr>
    <tr>
        <td>이름</td>
        <td>${ dto.member_name }</td>
    </tr>
    <tr>
        <td>성별</td>
        <td>${ dto.member_gender }</td>
    </tr>
    <tr>
        <td>출생년도</td>
        <td>${ dto.member_born_year }</td>
    </tr>
    <tr>
        <td>우편번호</td>
        <td>${ dto.member_postcode }</td>
    </tr>
    <tr>
        <td rowspan="2">주소</td>
        <td>${ dto.member_address }</td>
    </tr>
    <tr>
        <td>${ dto.member_detail_address } ${ dto.member_extra_address }</td>
    </tr>
    <tr>
        <td>가입일</td>
        <td>${ dto.member_regi_date }</td>
    </tr>
    <tr>
        <td colspan="2" align="center" style="padding: 10px 20px;">
            <input type="button" value="목록으로" onclick="GoPage('member_list', '', '')">&nbsp;
            <input type="button" value="삭제하기" onclick="GoPage('member_delete', '', '${ dto.member_no }')">&nbsp;
            <input type="button" value="수정하기" onclick="GoPage('member_modify', '', '${ dto.member_no }')">
        </td>
    </tr>
</table>