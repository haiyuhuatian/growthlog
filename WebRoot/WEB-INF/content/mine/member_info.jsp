<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/content/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>个人资料</title>
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
		<script type="text/javascript" src="${base }/phone-js/member-detail.js"></script>
		<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>
		<script language="javascript" type="text/javascript" src="${base}/My97DatePicker/WdatePicker.js"></script>
		
	</head>
	<body style="background-color:#F0EFEE;">
		<div style="width:100%;margin-top:10px;">
			<input type="hidden" id="introduction" value="${user.introduction }"/>
			<input type="hidden"  id="userId" value="${user.id }" />
			<div class="tools-div">
				<ul >
					<li onclick="changeHead()">
						<span class="leftSpan">头像</span>
						<span class="rightSpan" id="headImg">
							<c:if test="${!empty(user) }">
								<img src="${base }/uploadImages/growthLog${user.id}/${user.head_img}"
									onerror="this.src='${base}/images/default_head.png'" width="50px" alt="" />
								<img src="${base }/images/to_edit.png" alt="" />
							</c:if>
						</span>
						
						<div class="clear"></div>
					</li>
					<li>
						<span class="leftSpan">登录名</span>
						<span class="rightSpan" style="margin-right:5%;" id="loginName">${user.login_name }</span>
						<div class="clear"></div>
					</li>
					<li onclick="editUser(1)">
						<span class="leftSpan">昵称</span>
						<span class="rightSpan" id="myNickname">
							${user.nickname }
							<img src="${base }/images/to_edit.png" style="vertical-align: middle;" alt="" />
						</span>
						<div class="clear"></div>
					</li>
					<li onclick="editUser(8)">
						<span class="leftSpan">微信号</span>
						<span class="rightSpan" id="myWchatNo">
							${user.wchat_no }
							<img src="${base }/images/to_edit.png" style="vertical-align: middle;" alt="" />
						</span>
						<div class="clear"></div>
					</li>
						<li onclick="editUser(9)">
						<span class="leftSpan">简介</span>
						<span class="rightSpan" id="intro">
							<img src="${base }/images/to_edit.png" style="vertical-align: middle;" alt="" />
						</span>
						<div class="clear"></div>
					</li>
					<li onclick="editUser(2)">
						<span class="leftSpan">密码</span>
						<span class="rightSpan" id="pass" >
							修改
							<img src="${base }/images/to_edit.png" style="vertical-align: middle;" alt="" />
						</span>
						<div class="clear"></div>
					</li>
					<li onclick="editUser(3)">
						<span class="leftSpan">性别</span>
						<span class="rightSpan" id="mySex">
							${user.sex == 0?"女":user.sex == 1?"男":"" }
							<img src="${base }/images/to_edit.png" style="vertical-align: middle;" alt="" />
						</span>
						<div class="clear"></div>
					</li>
					<li onclick="editUser(4)">
						<span class="leftSpan">生日</span>
						<span class="rightSpan" id="myBirthday" >
							<fmt:formatDate value="${user.birthday }" pattern="yyyy-MM-dd" />
							<img src="${base }/images/to_edit.png" style="vertical-align: middle;" alt="" />
						</span>
						<div class="clear"></div>
					</li>
					<li onclick="editUser(5)" style="${empty(user.baby_birthday)?'display:none':'' }" id="babyBirthdayLi">
						<span class="leftSpan">宝贝生日</span>
						<span class="rightSpan" id="myBabyBirthday">
							<fmt:formatDate value="${user.baby_birthday }" pattern="yyyy-MM-dd" />
							<img src="${base }/images/to_edit.png" style="vertical-align: middle;" alt="" />
						</span>
						<div class="clear"></div>
					</li>
					<li onclick="editUser(6)">
						<span class="leftSpan">常用邮箱</span>
						<span class="rightSpan" id="myEmail">
							${user.email }
							<img src="${base }/images/to_edit.png" style="vertical-align: middle;" alt="" />
						</span>
						<div class="clear"></div>
					</li>
					<li onclick="editUser(7)">
						<span class="leftSpan">所在地</span>
						<span class="rightSpan" id="myArea">
							${area }
							<img src="${base }/images/to_edit.png" style="vertical-align: middle;" alt="" />
						</span>
						<div class="clear"></div>
					</li>
				</ul>
			</div>
		</div>
		<form action="member!update.action" method="post" id="myForm">
			<input type="hidden" name="updateType" id="updateType" />
			<div class="write-comment" id="editArea" style="display:none">
				<div style="margin-top:100px;width:100%;text-align:center;background-color:#fff;height:180px;font-size:10px;" id="contentPar">
					<div id="editContent" >
					</div>
					<p style="color:red;margin:10px 0;font-size:12px;" id="remind"></p>
					<div style="width:40%;margin:0 auto;">
						<div class="edit-user-submit" onclick="checkForm()">提交</div>
						<div class="edit-user-close" onclick="closeForm()">关闭</div>
						<div class="clear"></div>
					</div>
				</div>
			</div>
		</form>
		
	</body>
</html>
