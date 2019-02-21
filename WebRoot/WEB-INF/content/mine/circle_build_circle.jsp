<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/content/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>创建圈子</title>
		<meta name="keywords" value="成长日志" />
		<meta name="description" value="首个记录成长日志的网站" />
		<meta name="author" value="成长日志" />
		<meta name="viewport" content="width=device-width,height=device-height,inital-scale=1.0,maximum-scale=1.0,user-scalable=no;">
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="apple-mobile-web-app-status-bar-style" content="black">
		<meta name="format-detection" content="telephone=no">
		<link href="${base }/phone-css/phone.css" rel='stylesheet' type='text/css' />
		<script type="text/javascript" src="${base }/js/jquery.js"></script>
		<script type="text/javascript"  src="${base }/phone-js/xback.js"></script>
		<script type="text/javascript">
			function submitForm(){
				if($("#title").val()==""){
					alert("请填写圈子名称");
				}else {
					$("#myForm").submit();
				}
			}
			XBack.listen(function(){
				  window.location = "${base}/mine/circle!myCircle.action";
				});
		</script>
	</head>
	<body style="background-color:#F0EFEE;margin-bottom:90px;">
		<c:if test="${method eq 'update' }">
			<div class="circle-cover-box" onclick="window.location='${base}/mine/circle!setCover.action?circle.id=${circle.id }&method=update'">
				<c:if test="${!empty(cover) }">
					<img src="${base }/circle_cover/${circle.cover}" alt="" />
				</c:if>
				<c:if test="${empty(cover) }">
					<img src="${base }/images/circle-cover.jpg" alt="" />
				</c:if>
				<p style="font-size:10px;">点击图片更换封面</p>
			</div>
		</c:if>
		<form action="${base }/mine/circle!${method }.action" id="myForm" method="post" >
			<input  type="hidden" id="circleId" name="circle.id" value="${circle.id }"/>
			<c:if test="${method eq 'update' }">
				<div class="circle-update-box">
			</c:if>
			<c:if test="${method ne 'update' }">
				<div class="circle-build-box">
			</c:if>
				<input type="text" placeholder="圈子名称"  name="circle.name" id="title" value="${circle.name }" class="circle-name"/>
			</div>
			<div class="circle-type-box">
				<input type="radio" name="circle.type" value="1" id="city" ${circle.type==1?"checked":"" }/>
				<label for="city">同城</label>
				<input type="radio" name="circle.type" value="2" id="classmate" ${circle.type==2?"checked":"" }/>
				<label for="classmate">同学</label>
				<input type="radio" name="circle.type" value="3" checked="checked" id="hobby" ${circle.type==3?"checked":"" }/>
				<label for="hobby">兴趣爱好</label>
				<input type="radio" name="circle.type" value="4" id="p" ${circle.type==4?"checked":"" }/>
				<label for="p">家长会</label>
				<input type="radio" name="circle.type" value="5" id="friend"  ${circle.type==5?"checked":"" }/>
				<label for="friend">好友</label>
				<input type="radio" name="circle.type" value="6" id="family" ${circle.type==6?"checked":"" }/>
				<label for="family">家族</label>
			</div>
			<div class="circle-introduction-box">
				<div >圈子介绍</div>
				<textarea  s name="circle.describetion">${circle.describetion }</textarea>
			</div>
		</form>
			<div class="circle-build-sub" onclick="submitForm()">保存</div>
	</body>
</html>
