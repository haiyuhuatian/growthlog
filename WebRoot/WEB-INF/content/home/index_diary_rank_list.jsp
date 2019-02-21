<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/content/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>成长日志</title>
		<meta name="keywords" value="成长日志" />
		<meta name="description" value="首个记录成长日志的网站" />
		<meta name="author" value="成长日志" />
		<meta name="viewport" content="width=device-width,height=device-height,inital-scale=1.0,maximum-scale=1.0,user-scalable=no;">
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="apple-mobile-web-app-status-bar-style" content="black">
		<meta name="format-detection" content="telephone=no">
		<link href="${base }/phone-css/phone.css" rel='stylesheet' type='text/css' />
		<link rel="stylesheet" href="${base }/phone-css/reset.css" />
		<link rel="stylesheet" href="${base }/phone-css/pullToRefresh.css" />
		<script type="text/javascript" src="${base }/js/jquery.js"></script>
		<script type="text/javascript" src="${base }/phone-js/iscroll.js"></script>
		<script type="text/javascript" src="${base }/phone-js/pullToRefresh.js"></script>
		<script type="text/javascript"  src="${base }/phone-js/xback.js"></script>
		<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>
		<script type="text/javascript">
			XBack.listen(function(){
		 		wx.closeWindow();
			});
		</script>
	</head>
	<body style="background-color:#F0EFEE;" onload="ifLogin()">
			<div class="rank-diary-box" >
			<ul>
				<c:forEach items="${rankList }" var="r">
					<li onclick="window.location='${base}/home/diary!detail.action?fromPath=rank&diary.id=${r.id }'">
						<div class="rank-diary-cover">
							<img src="${base }/uploadImages/growthLog${r.user_id}/diary${r.id}/${r.cover}" 
								onerror="this.src='${base}/images/default_diary_image.jpg'"   height="200px" alt="" />
						</div>
						<div class="diary-info-box">
							<p class="rank-acount">
								<img src="${base }/images/fire2.png" height="100%"/>
								<span>${r.acount }</span>
							</p>
							<p class="rank-diary-title">${r.title }</p>
							<p class="rank-diary-content">${r.content }</p>
						</div>
						<div class="clear"></div>
					</li>
				</c:forEach>
			</ul>
		</div>
	</body>
</html>
