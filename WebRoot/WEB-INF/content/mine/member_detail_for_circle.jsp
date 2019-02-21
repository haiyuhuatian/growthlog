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
		<style type="text/css">
			.detailUl li{
				border-bottom:1px solid #847c7c;
				font-size:14px;
			}
			.leftSpan{
				line-height:50px;text-align:center;margin-left:5%;
			}
			.rightSpan{
				float:right;;
				text-align:center;
				line-height:50px;
				margin-right:20px;
			}
		</style>
		<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>
		<script type="text/javascript" src="${base }/js/jquery.js"></script>
		<script type="text/javascript"  src="${base }/phone-js/xback.js"></script>
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
	  		 function showHeadPhoto(){
	  			 var headPho = $("#headPhoto").attr("src");
	  			 var serverPath = $("#serverPath").val();
	  			 var headPath = serverPath+headPho;
	  			wx.previewImage({
	  				current: headPath, // 当前显示图片的http链接
	  				urls: [headPath] // 需要预览的图片http链接列表
	  				}); 
	  		 }
	  		XBack.listen(function(){
		 		window.location = "${base}/mine/circle!userList.action?circle.id="+$("#circleId").val()+"&lastIdSign="+$("#lastIdSign").val();
			});
	  	</script>
	</head>
	<body style="background-color:#F0EFEE;">
		<div style="width:100%;margin-top:50px;">
			<input type="hidden" id="serverPath" value="${serverPath }"/>
			<input type="hidden" id="circleId" value="${circle.id}"/>
			<input type="hidden" id="lastIdSign" value="${lastIdSign }"/>
			<div style="background-color:#fff;border:1px solid #d0cfca;width:95%;margin:0 auto;font-size:10px;">
				<ul class="detailUl">
					<li onclick="showHeadPhoto()" style="padding-top:5px;padding-bottom:5px;">
						<span class="leftSpan">
							<img id="headPhoto" src="${base }/uploadImages/growthLog${user.id}/${user.head_img}"
								onerror="this.src='${base}/images/default_head.png'"
								 style="vertical-align:top" width="60px" alt="" />
							${user.nickname }
						</span>
						<div class="clear"></div>
					</li>
					<c:if test="${user.is_wchat_open == 1 }">
					<li >
						<span class="leftSpan">微信号</span>
						<span class="rightSpan" >
							${user.wchat_no }
						</span>
						<div class="clear"></div>
					</li>
					</c:if>
					<li >
						<span class="leftSpan">性别</span>
						<span class="rightSpan" >
							${user.sex == 0?"女":user.sex == 1?"男":"" }
						</span>
						<div class="clear"></div>
					</li>
					<li >
						<span class="leftSpan">所在地</span>
						<span class="rightSpan" >
							${area }
						</span>
						<div class="clear"></div>
					</li>
					<li onclick="window.location='${base}/mine/circle!personDiaryList.action?circle.id=${circle.id }&member=${user.id }'">
						<span class="leftSpan">日志列表</span>
						<span class="rightSpan" style="margin-right:0;">
							<img src="${base }/images/to_edit.png" style="vertical-align: middle;" alt="" />
						</span>
						<div class="clear"></div>
					</li>
					<li >
						<p style="height:50px;line-height:50px;width:100%;text-align:center;">简介</p>
						<p style="line-height:30px;width:90%;margin:0 auto;">${user.introduction }</p>
					</li>
				</ul>
			</div>
		</div>
	</body>
</html>
