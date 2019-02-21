<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/content/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>我的圈子</title>
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
		<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>
		<script type="text/javascript">
			function getInCircle(circleId,createUserId){
					$("#circleId").val(circleId);
					$("#circleMenu").show();
					$("#createUserId").val(createUserId);
			}
			function chooseMenu(type){
				var circleId = $("#circleId").val();
				if(type == 1){
					if(userId == createUserId){
						window.location = "${base}/mine/circle!edit.action?circle.id="+circleId;
					}else{
						window.location = "${base}/mine/circle!detail.action?fromPath=myCircle&circle.id="+circleId;
					}
					
				}else if(type == 2){
					window.location = "${base}/mine/circle!diaryList.action?circle.id="+circleId;
				}else if(type == 3){
					window.location = "${base}/mine/circle!userList.action?init&circle.id="+circleId;
				}else {
					$("#circleMenu").hide();
				}
			}
			wx.config({
				 debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
	             appId: '${appId}', // 必填，公众号的唯一标识
	             timestamp: '${timestamp}', // 必填，生成签名的时间戳
	             nonceStr: '${noncestr}', // 必填，生成签名的随机串
	             signature:'${signature}',// 必填，签名，见附录1
				      jsApiList: [
				                  'checkJsApi',
				                  'closeWindow'
				                 ] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
				      });
			 function ifLogin(){
				  var isLogin = $("#isLogin").val();
				   if(isLogin != ""&&isLogin=="no"){
					  alert("请先登录哦");
					  wx.closeWindow();
				  } 
			  }
			XBack.listen(function(){
		 		wx.closeWindow();
			});
		</script>
	</head>
	<body style="background-color:#F0EFEE;" onload="ifLogin()">
			<input type="hidden" id="isLogin" value="${isLogin }"/>
			<input type="hidden" value="${loginUser.id }" id="userId"/>
			<input type="hidden" value="" id="createUserId"/>
			<div style="width:100%;text-align:center;margin-top:50px;">
				<img src="${base}/images/title-bg.png" alt="" />
			</div>
			<div style="width:100%;text-align:center;">
				<c:forEach items="${myCircles }" var="c">
				<div style="width:20%;float:left;margin-left:3%;margin-top:10px;" 
					onclick="getInCircle(${c.id},${c.create_user })">
					<img src="${base }/circle_cover/${c.cover}" width="100%" onerror="this.src='${base }/images/circle-cover.jpg'" alt="" />
					<p style="width:100%;font-size:12px;height:18px;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;">${c.name }</p>
				</div>
				</c:forEach>
				<div style="width:20%;float:left;margin-left:3%;margin-top:10px;" onclick="window.location='${base}/mine/circle!buildCircle.action'">
					<img src="${base }/images/add_circle.png" width="100%" alt="" />
				<span style="display:block;">创建圈子</span>
				</div>
				<div style="width:20%;float:left;margin-left:3%;margin-top:10px;" onclick="window.location='${base}/mine/circle!otherCircles.action?type=0'">
					<img src="${base }/images/join_circle.png" width="100%" alt="" />
				<span style="display:block;">加入圈子</span>
				</div>
				<div class="clear"></div>
			</div>
			<div class="write-comment" id="circleMenu" style="display:none">
				<input type="hidden" name="circle.id" id="circleId"/>
				<div style="width:100%;margin-top:250px;">
					<div class="circle-menu" onclick="chooseMenu(1)">圈子详情</div>
					<div class="circle-menu" onclick="chooseMenu(2)">进入日志列表</div>
					<div class="circle-menu" onclick="chooseMenu(3)">查看成员列表</div>
					<div class="circle-menu" onclick="chooseMenu(4)">返回</div>
				</div>
			</div>
	</body>
</html>
