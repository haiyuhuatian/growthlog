<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/content/common/taglib.jsp" %>
<script>
<c:if test="${!empty message}">
alert("${message}");
</c:if>
<c:if test="${!empty redirectionUrl}">
location.href = "${redirectionUrl}";
</c:if>
<c:if test="${empty redirectionUrl}">
history.back();
</c:if>
</script>
