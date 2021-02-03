<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!-- JSTL(Jsp Standard Tag Library) : Java EE 기반 웹 애플리케이션 개발 플랫폼을 위한 컴포넌트 모음 -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="path" value="${ pageContext.request.contextPath }" />
<c:set var="url" value="${ pageContext.request.requestURL }" />
<c:set var="uri" value="${ pageContext.request.requestURI }" />

<!-- jquery 라이브러리 로딩 -->
<script src="http://code.jquery.com/jquery-3.3.1.min.js"></script>