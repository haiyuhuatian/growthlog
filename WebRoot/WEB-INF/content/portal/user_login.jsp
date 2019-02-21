<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/content/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
		<title>用户登录</title>
		<meta name="keywords" value="成长日志" />
		<meta name="description" value="首个记录成长日志的网站" />
		<meta name="author" value="成长日志" />
		<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
  		<link href="${base }/phone-css/phone.css" rel="stylesheet">
		<%-- <script type="text/javascript" src="${base }/js/jquery.1.4.2.js"></script> --%>
			 <script type="text/javascript" src="${base }/js/jquery.js"></script>
    	<script type="text/javascript">
	    	function refreshImg()
			{
				document.getElementById("codeImg").src='${base}/portal/user!imgcode.action?a='+new Date().getTime();
			}
	    	function login(){
	    		$("#loginForm").submit();
	    	}
	    	function forgetPass(){
	    		alert("请发送密码两个汉字到公众号重新设置密码！");
	    	}
    	</script>
    	<style type="text/css">
    		input{
    			height:25px;
    		}
    	</style>
</head>
<body style="width:100%;height:800px;background:url(${base }/images/phone-login-bg.png) no-repeat;background-size:100% 100%;">
	<div class="login-box" >
		<form action="${base }/portal/user!login.action" method="post" id="loginForm">
			<input type="hidden" name="from" value="${from }"/>
			<table>
				<tr>
					<td >登录名：</td>
					<td><input type="text" name="user.login_name" /></td>
				</tr>
				<tr>
					<td>密&nbsp;&nbsp;&nbsp;码：</td>
					<td>
						<input type="password" style="width:60%;margin-right:10px;" name="user.password" />
						<span style="color:blue;" onclick="forgetPass()">忘记密码</span>
					</td>
				</tr>
				<!-- <tr>
					<td>验证码：</td>
					<td>
						<input type="text" name="imgCode" style="width:60px;;"/>
						<img  style="cursor: pointer; height:30px;width:70px;float:right;"
						 id="codeImg"
						title="点击刷新"
						onclick="refreshImg();" />
						<script>refreshImg();</script>
						<div class="clear"></div>
					</td>
				</tr> -->
			</table>
			<div class="login-btn" onclick="login()">登&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;录</div>
			<div class="login-btn"  onclick="window.location='/portal/user!toRegister.action'">注&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;册</div>
		</form>
	</div>
</body>
</html>
