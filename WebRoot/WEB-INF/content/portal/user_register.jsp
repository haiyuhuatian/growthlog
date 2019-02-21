<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/content/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
		<title>用户注册</title>
		<meta name="keywords" value="成长日志" />
		<meta name="description" value="首个记录成长日志的网站" />
		<meta name="author" value="成长日志" />
		<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
		<link href="${base }/css/bootstrap.min.css" rel="stylesheet">
  		<link href="${base }/css/cropper.min.css" rel="stylesheet">
  		<link href="${base }/css/setCover.css" rel="stylesheet">
  		<link href="${base }/phone-css/phone.css" rel="stylesheet">
		<script language="javascript" type="text/javascript" src="${base}/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="${base }/js/jquery.js"></script>
		<script type="text/javascript"  src="${base }/phone-js/xback.js"></script>
    	<script type="text/javascript">
    	$(function(){
     			$.post("${base}/portal/user!getCityName.action",{"cityMap.pid":0},function(data){
					if(data != "") {
						$.each(data,function(i,item){
			    				$("#city1").append("<option value='"+item.id+"' >"+item.name+"</option>");
			    		});
					}
				},"json");
     	});
    	function getCityName(level,item) {
			$("#errorms").html("");
			var pid = $("#"+item).val();
			$("#city"+level).empty();
    		$("#city"+level).append("<option value=''>--请选择--</option>");
    		if(level == "2") {
    			$("#city"+3).empty();
	    		$("#city"+3).append("<option value=''>--请选择--</option>");
    		}
			$.post("${base}/home/user!getCityName.action",{"cityMap.pid":pid},function(data){
				if(data != "") {
					$.each(data,function(i,item){
		    				$("#city"+level).append("<option value='"+item.id+"' >"+item.name+"</option>");
		    		});
				}
			},"json");
		}
    	function checkLoginName() {
			var loginName = $("#loginName").val();
			if(loginName != "") {
				$.post("${base}/portal/user!checkLoginName.action",{"user.login_name":loginName},function(data) {
					if(data < 0) {
						$("#span1").text("已占用")
					}
				});
			}
		}
    	function checkNickname() {
    		var nickname = $("#nickname").val();
    		if(nickname != "") {
    			$.post("${base}/portal/user!checkNickName.action",{"user.nickname":nickname},function(data){
	    				if(data<0){
	    					$("#span2").text("已占用")
	    				}
	    			});
    		}
    	}
		function checkForm() {
			var error = 0;
			var loginName = $("#loginName").val();
			if(loginName == "") {
				$("#span1").text("请输入登录名");
				error = error+1;
			}else {
				if(loginName.indexOf(" ")!=-1){
					$("#span1").text("登录名勿含空格");
					error = error+1;
				}else{
					var reg = /^[0-9a-zA-Z]+$/
					if(!reg.test(loginName)){
						$("#span1").text("只能输入字母或数字");
					}
				}
			}
			if($("#nickname").val() == "") {
				$("#span2").text("请输入昵称");
				error = error+1;
			}
			if($("#password").val() == "") {
				$("#span3").text("请输入密码");
				error = error+1;
			}
			if($("#sPassword").val() == "") {
				$("#span4").text("请确认密码");
				error = error+1;
			}
			if($("#password").val() != "" && $("#sPassword").val() != "") {
				if($("#password").val() != $("#sPassword").val()) {
					$("#span4").text("请和密码一致");
					error = error+1;
				}
			}
			if($("#openid").val()!=""){
				$.post("/portal/user!checkOpenId.action",{"user.login_name":$("#login_name").val()},function(data){
					if(data != ""){
						if(confirm("该微信号已注册过账号【"+data+"】您可以直接登录"));
					}else {
						if(error == 0) {
							$("#registerForm").submit();
						}
					}
				});
			}else{
				if(error == 0) {
					$("#registerForm").submit();
				}
			}
		}
		function cleanSpan(item) {
			item.siblings("span").text("*");
		}
		 XBack.listen(function(){
			  window.location = "${base}/mine/member!detail.action";
			});
    	</script>
</head>
<body style="width:100%;height:800px;background:url(${base }/images/register-bg-phone.jpg) no-repeat;background-size:100% 100%;">
	<form action="${base}/portal/user!register.action" id="registerForm" method="post">
		<input type="hidden" value="${openid }" id="openid"/>
		<div class="register">
			<ul class="register-ul">
				<li>
					登&nbsp; 录&nbsp; 名：
					<input type="text" name="user.login_name" id="loginName" onblur="checkLoginName()" onfocus="cleanSpan($(this))"/>
					<span id="span1">*</span>
				</li>
				<li>
					昵&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;称：
					<input type="text" name="user.nickname" id="nickname" onfocus="cleanSpan($(this))" onblur="checkNickname()"/>
					<span id="span2">*</span>
				</li>
				<li>
					密&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;码：
					<input type="password" name="user.password" id="password" onfocus="cleanSpan($(this))"/>
					<span id="span3">*</span>
				</li>
				<li>
					确认密码：
					<input type="password" name="users.password" id="sPassword" onfocus="cleanSpan($(this))"/>
					<span id="span4">*</span>
				</li>
				<!-- <li>
					<div style="float:left;">
						模&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;式：
					</div>
					<div style="float:left;">
						<input style="width:14px;height:14px;" type="radio" name="user.model" value="1" id="baby" />
						<label for="baby" style="font-weight:normal;">宝贝日志</label>
					</div>
					<div style="float:left;">
						<input  style="width:14px;height:14px;" type="radio" value="2" name="user.model" id="self" checked="checked"/>
						<label for="self" style="font-weight:normal;">个人日志</label>
					</div>
					<div class="clear"></div>
				</li>  -->
				
				<div class="clear"></div>
			</ul>
			<input type="button" class="registerBtn" value="注册" onclick="checkForm()" />
			<div class="to_public_code">识别二维码进入公众号</div>
			<div class="public_code">
				<img src="${base }/images/public_code.jpg" style="margin-top:42px;" width="149px" alt="" />
			</div>
			<div style="width:100%;text-align:center;">（长按二维码可进行识别）</div>
		</div>
	</form>
</body>
</html>
