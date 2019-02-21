<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/content/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>圈子详情</title>
		<meta name="keywords" value="成长日志" />
		<meta name="description" value="首个记录成长日志的网站" />
		<meta name="author" value="成长日志" />
		<meta name="viewport" content="width=device-width,height=device-height,inital-scale=1.0,maximum-scale=1.0,user-scalable=no;">
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="apple-mobile-web-app-status-bar-style" content="black">
		<meta name="format-detection" content="telephone=no">
		<link href="${base }/phone-css/phone.css" rel='stylesheet' type='text/css' />
		<script type="text/javascript" src="${base }/js/jquery.js"></script>
		<script type="text/javascript" src="${base }/phone-js/jquery-form.js"></script>
		<script type="text/javascript"  src="${base }/phone-js/xback.js"></script>
		<script type="text/javascript">
			function submitForm(){
				var joinText = $("#join").html();
				if(joinText == "加入"){
					$("#myForm").ajaxSubmit({
						  url: '${base }/mine/circle!joinCircle.action',
				    	  type: "Post",
				    	  success:function(data){
				    		  if(data != ""){
				    			  alert("加入成功");
				    			  $("#join").html("退出");
				    		  }
				    	  }
					  });
				}else{
					$("#myForm").ajaxSubmit({
						  url: '${base }/mine/circle!exitCircle.action',
				    	  type: "Post",
				    	  success:function(data){
				    		  if(data != ""){
				    			  alert("退出成功");
				    			  $("#join").html("加入");
				    		  }
				    	  }
					  });
				}
				
			}
			XBack.listen(function(){
					var fromPath = $("#fromPath").val();
					if(fromPath == "otherCircle"){
						window.location = "${base}/mine/circle!otherCircles.action?lastIdSign="+$("#lastIdSign").val();
					}else{
						window.location = "${base}/mine/circle!myCircle.action";
					}
		 			
			});
		</script>
		<style type="text/css">
			li{
				height:30px;
				line-height:30px;
			}
		</style>
	</head>
	<body style="background-color:#F0EFEE;">
		<input type="hidden" id="lastIdSign" name="lastIdSign" value="${circle.id }"/>
		<input type="hidden" id="fromPath" value="${fromPath }"/>
		<form  id="myForm" >
			<input type="hidden" name="circle.id" value="${circle.id }"/>
			<div class="circle-detail-box">
				<div class="circle-cover">
					<img src="${base }/circle_cover/${circle.cover}" alt="" onerror="this.src='${base}/images/circle-cover.jpg'"/>
				</div>
				<div class="circle-name">
					${circle.name }
				</div>
				<div class="circle-info">
					<ul >
						<li>创建时间：<fmt:formatDate value="${circle.create_time }" pattern="yyyy年MM月dd日" /></li>
						<li>创&nbsp;建 &nbsp;人：${createUser }</li>
						<li>类&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;型：${circle.type==1?"同城":circle.type==2?"同学":circle.type==3?"兴趣爱好":circle.type==4?"家长会":circle.type==5?"好友":circle.type==6?"家族":"" }</li>
					</ul>
				</div>
				<div class="circle-describetion-box">
					<p style="font-weight:bold;">简介</p>
					<c:if test="${!empty(circle.describetion) }">
		      		<p class="circle-describetion">${circle.describetion }</p>
		      		</c:if>
		      		<c:if test="${empty(circle.describetion) }">
		      			<p style="color:#d21818;">创建人很懒，没有写简介哦</p>
		      		</c:if>
				</div>
			</div>
		</form>
			<div class="to-join-circle" onclick="submitForm()" id="join">${fromPath eq 'myCircle'?'退出':'加入' }</div>
			<!-- <div style="width:100%;height:100px;"></div> -->
	</body>
</html>
