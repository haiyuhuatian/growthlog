<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/content/common/taglib.jsp"%>
<div >
	<div class="bar">
		<ul >
	        <li class="barli"><a  href="${base }/home/index!index.action?menu=1" ${menu == 1 ? "class=' active'" : ""}>网站首页</a><b></b></li>
	        <li class="barli"><a onclick="return filterUrl(this);" href="${base }/mobile/circle!myCircle.action?type=0" target="_top"${menu ==2? "class=' active'" : ""}>我的圈子</a><b></b></li>
	        <li class="barli"><a onclick="return filterUrl(this);" href="${base}/mobile/diary!list.action?init&menu=3"${menu ==3? "class=' active'" : ""}>我的日志</a><b></b></li>
	       <%--  <li class="barli"><a onclick="return filterUrl(this);" href="${base}/student/student!subjectList.action?menu=4"${menu ==4? "class=' active'" : ""}>我的论坛</a><b></b></li> --%>
	        <div class="clear"></div>
	     </ul> 
	</div>
</div>
