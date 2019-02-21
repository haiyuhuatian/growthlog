<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/content/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>我的</title>
		<meta name="keywords" value="成长日志" />
		<meta name="description" value="首个记录成长日志的网站" />
		<meta name="author" value="成长日志" />
		<meta name="viewport" content="width=device-width,height=device-height,inital-scale=1.0,maximum-scale=1.0,user-scalable=no;">
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="apple-mobile-web-app-status-bar-style" content="black">
		<meta name="format-detection" content="telephone=no">
		<link href="${base }/phone-css/phone.css" rel='stylesheet' type='text/css' />
		<script type="text/javascript" src="${base }/js/jquery.js"></script>
		<script type="text/javascript" src="${base }/js/jquery-form.js"></script> 
		<%-- <script type="text/javascript" src="${base }/phone-js/member-detail.js"></script> --%>
		<script type="text/javascript"  src="${base }/phone-js/xback.js"></script>
		<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>
		<script type="text/javascript">
		 wx.config({
             debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
             appId: '${appId}', // 必填，公众号的唯一标识
             timestamp: '${timestamp}', // 必填，生成签名的时间戳
             nonceStr: '${noncestr}', // 必填，生成签名的随机串
             signature:'${signature}',// 必填，签名
             jsApiList: ['checkJsApi',
                         'previewImage'
                        ] // 必填，需要使用的JS接口列表
         });
		 wx.ready(function(){ 
			 $("#headPic").click(function(e){
				 wx.previewImage({
	                 current: this.src,
	                 urls: [this.src]
	             });
			 });
		 });
			function myInfo(){
				var userId = $("#userId").val();
				if(userId == null || userId == ""){
					$("#loginArea").show();	
				}
			}
			function refreshImg()
			{
				document.getElementById("codeImg").src='/portal/user!imgcode.action?a='+new Date().getTime();
			}
			function forgetPass(){
	    		alert("请发送密码两个汉字到公众号重新设置密码！");
	    	}
	    	function closeLogin(){
	    		$("#login_name").val("");
	    		$("#pwd").val("");
	    		$("#pwd1").val("");
	    		$("#ic").val("");
	    		$("#loginArea").hide();
	    	}
			function login(){
	    		if($("#login_name").val()==""){
	    			alert("请填写登录名");
	    		}else if($("#pwd").val()==""){
	    			alert("请填写密码");
	    		}else if($("#ic").val()==""){
	    			alert("请填写验证码");
	    		}else{
	    			if($("#openid").val()!=""){
	    				$.post("/portal/user!ifBind.action",{"user.login_name":$("#login_name").val()},function(data){
	    					if(data == ""){
	    						subLogin();
	    					}else if(data == "0"){
	    						alert("请填写登录名");
	    					}else if(data == "1"){
	    						if(confirm("是否绑定该微信号？绑定后可免登录")){
				    				$("#ifBind").val("1");
				    			}
	    					}else{
	    						if(confirm("当前微信号已绑定账号【"+data+"】是否解绑，并绑定当前账号，绑定后可免登录")){
				    				$("#ifBind").val("1");
				    			}
	    					}
	    					subLogin();
	    				});
	    			}else{
	    				subLogin();
	    			}
	    		
	    		}
	    	}
			function subLogin(){
				$("#loginForm").ajaxSubmit({
   				 url: "/portal/user!login.action",
  		    	     type: "Post",
  		    	     success: function (data) {
  		    	    	 if(data == "0"){
  		    	    		 alert("登录名输入错误，请重新输入");
  		    	    	 }else if(data == "1"){
  		    	    		 alert("密码输入错误，请重新输入");
  		    	    	 }else{
  		    	    		 $("#login_name").val("");
  		    	    		 $("#pwd1").val("");
  		    	    		$("#pwd").val("");
  		    	    		$("#ic").val("");
  		    	    		$("#loginArea").hide();
  		    	    		var result = jQuery.parseJSON(data);
  		    	    		$("#userId").val(result.id);
  		    	    		$("#introduction").val(result.introduction);
  		    	    		$("#headImg").html(
	    	    					"<img src='/uploadImages/growthLog"+result.id+"/"+result.head_img+"' width='50px' id='headPic' " 
	    	    					+"  onerror='this.src=\"${base}/images/default_head.png\"' />"
	    	    					); 
  		    	    		$("#loginName").html("<p class='login-name-p'>"+result.login_name+"</p><p >"+result.nickname+"</p>");
  		    	    		$("#loginOutDiv").show();
  		    	    	 }
  		    	     }
   			});
			}
			function loginOut(){
				$.post("/portal/user!loginOut.action",function(data){
					if(data=="1"){
						$("#userId").val("");
						$("#headPic").attr("src","/images/default_head.png");
						$("#loginName").html("<p class='login-name-p'>登录/注册</p>");
						$("#loginOutDiv").hide();
					}
				});
			}
			XBack.listen(function(){
		 		wx.closeWindow();
			});
		</script>
	</head>
	<body style="background-color:#F0EFEE;">
		<input type="hidden" value="${empty(user.id)?'':user.id }" id="userId"/>
		<input type="hidden" value="${openid }" id="openid"/>
		<div class="member-detail-header">
			<div class="head-img" id="headImg" >
				<c:if test="${empty(user) }">
					<img src="${base}/images/default_head.png" width="50px" id="headPic" />
				</c:if>
				<c:if test="${!empty(user) }">
					<img src="${base }/uploadImages/growthLog${user.id}/${user.head_img}"
						 onerror="this.src='${base}/images/default_head.png'"
						 id="headPic"  width="50px" />
				</c:if>
			</div>
			<div class="login-name" id="loginName" onclick="myInfo()">
				<c:if test="${empty(user) }">
					<p class="login-name-p">登录/注册</p>
				</c:if>
				<c:if test="${!empty(user) }">
					<p class="login-name-p">${user.login_name }</p>
					<p >${user.nickname }</p>
				</c:if>
			</div>
			<div class="set-up" onclick="window.location='${base}/mine/member!showInfo.action'">设置</div>
			<div class="clear"></div>
		</div>
		<div class="tools-div">
			<ul >
				<li onclick="window.location='${base}/mine/integral!detail.action'">
					<span class="leftSpan">积分详情</span>
					<span class="rightSpan" id="integral">
						<img src="${base }/images/to_edit.png" style="vertical-align: middle;" alt="" />
					</span>
					<div class="clear"></div>
				</li>
				<li onclick="window.location='${base}/mine/integral!claimRecord.action'">
					<span class="leftSpan">兑换记录</span>
					<span class="rightSpan" id="integral">
						<img src="${base }/images/to_edit.png" style="vertical-align: middle;" alt="" />
					</span>
					<div class="clear"></div>
				</li>
			</ul>
		</div>
		<div class="loginOut"  id="loginOutDiv" onclick="loginOut()" style="${empty(user)?'display:none':''}">
						退出登录
		</div>
		<form action="${base }/portal/user!login.action" method="post" id="loginForm">
			<input type="hidden" id="ifBind" name="ifBind"/>
			<div class="write-comment" id="loginArea" style="display:none;">
				<div class="login-box">
				<div class="close-div" onclick="closeLogin()">
						<img src="${base }/images/close-btn.png" alt="" width="15px" />关闭
					</div>
					 <table>
					<tr>
						<td style="text-align:right;width:25%;">登录名：</td>
						<td style="text-align:left;">
							<div style="width:98%;height:30px;border:1px solid;">
							<input type="text"   style="border: none;width:100%;height: 100%;background: transparent;"  name="user.login_name" id="login_name"/>
							</div>
						</td>
					</tr>
					 <tr>
						<td style="text-align:right;width:20%;">密&nbsp;&nbsp;&nbsp;&nbsp;码：</td>
						<td>
							<div style="width:60%;height:30px;border:1px solid;display: inline-block;">
								<input type="password" name="user.password" id="pwd" style="width:100%;border: none;margin-right:10px;height: 100%;background: transparent;" />
							</div>
							<span style="color:blue;font-size:12px;" onclick="forgetPass()">忘记密码</span>
						</td>
					</tr> 
					<!-- <tr>
						<td style="text-align:right;width:20%;">验证码：</td>
						<td>
							<div style="width:50%;height:30px;border:1px solid;display: inline-block;">
								<input type="text" name="imgCode" style="width:100%;border: none;height: 100%;background: transparent" id="ic"/>
							</div>
							<img  style="cursor: pointer; height:30px;width:46%;float:right;"
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
					<div class="clear"></div>
				</div>
		</form>
	</body>
</html>
