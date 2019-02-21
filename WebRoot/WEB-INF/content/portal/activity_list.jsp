<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/content/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
		<title>写日志赢拍立得相机</title>
		<meta name="keywords" value="成长日志" />
		<meta name="description" value="首个记录成长日志的网站" />
		<meta name="author" value="成长日志" />
		<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
  		<link href="${base }/phone-css/phone.css" rel="stylesheet">
		<script type="text/javascript" src="${base }/js/jquery.js"></script>
		<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>
    	<script type="text/javascript">
    	 wx.config({
             debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
             appId: '${appId}', // 必填，公众号的唯一标识
             timestamp: '${timestamp}', // 必填，生成签名的时间戳
             nonceStr: '${noncestr}', // 必填，生成签名的随机串
             signature:'${signature}',// 必填，签名
             jsApiList: ['checkJsApi',
                         'previewImage'] // 必填，需要使用的JS接口列表
         });
    	 wx.ready(function(){ 
			 $("#publicCode").click(function(e){
				 wx.previewImage({
	                 current: this.src,
	                 urls: [this.src]
	             });
			 });
		 });
    	</script>
</head>
<body>
	<div class="activity-box">
		<div class="activity-rolue-box">
			<ul>
				<li>1、即日起至2018年10月10日获得点赞数最多的日志作者将获得由本公众号提供的拍立得一台</li>
				<li>2、活动期间大家可以点击菜单栏里的“排行榜”随时关注日志的点赞排名情况</li>
				<li>3、活动结束后会在此处公布获奖者，获奖者需在一周内在“我的账户”里填写具体收货信息</li>
				<li>4、获奖者提交完收货信息后客服人员会确认信息，确认无误后会在一周内寄出</li>
				<li>5、获奖者收到奖品后需上传照片及获奖感言，否则会影响今后的活动的参与</li>
			</ul>
		</div>
	</div>
	<div class="public-intro-box" >
		<img src="${base }/images/public_code.jpg" id="publicCode" width="80px" alt="" />
		关注公众号
	</div>
</body>
</html>
