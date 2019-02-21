<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/content/common/taglib.jsp"%>
<script type="text/javascript" src="${base }/js/jquery.js"></script>
		<script type="text/javascript">
			function setHead(){
				$("#setHeadForm").submit();
			}
		</script>
<div >
	<div class="footer">
     <c:if test="${empty(loginUser) }">
          <span style="color:#f85f1b;font-size:18px;">
            	<a href="${base }/home/index!login.action"  style="color:#037f88;text-decoration:none;">登录&nbsp;&nbsp;&nbsp;&nbsp;</a>
            	<a href="${base }/home/index!register.action" onclick="toRegister()" style="color:#037f88;text-decoration:none;">注册</a>
          </span>
     </c:if>
     <form action="/home/user!setHead.action" method="post" id="setHeadForm">
     </form>
     <c:if test="${!empty(loginUser) }">
            	<img src="${base }/uploadImages/growthLog${user.id}/${user.head_img}" alt="" 
            		onclick="setHead()" 
            		width="40px" height="40px"
            		style="border-radius:50%;vertical-align:middle;"/>
            	<span style="cursor:pointer" onclick="window.location='${base}/mobile/member!detail.action'">${user.nickname }</span>
     </c:if> 
	</div>
</div>
