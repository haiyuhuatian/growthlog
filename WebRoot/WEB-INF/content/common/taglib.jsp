<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="g" uri="/WEB-INF/czrz-taglib.tld" %>
<%@ taglib uri="http://www.opensymphony.com/oscache" prefix="cache" %>
<c:set var="cache_time" value="86400" />
<c:set var="cache_scope" value="application" />
<c:set var="base" value="${pageContext.request.contextPath}" />
<%-- <% response.addHeader("X-UA-Compatible", "IE=7"); %> --%>