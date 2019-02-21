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
			function submitForm(){
				$('#searchForm').submit();
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
			function cancleFollow(followId,nickname,item){
				if(confirm("您确定不再关注"+nickname+"吗")){
					$.post("${base}/mine/follow!cancleFollow.action",{"follow.id":followId},function(data){
						if(data == 1){
							item.parent("li").remove();
						}
					});
				}
			}
			XBack.listen(function(){
		 		wx.closeWindow();
			});
			
		</script>
	</head>
	<body style="background-color:#F0EFEE;" onload="ifLogin()">
		<input type="hidden" id="isLogin" value="${isLogin }"/>
		<input type="hidden" id="lastIdSign" value="${lastIdSign }"/>
		<form action="${base }/mine/follow!list.action" method="post" id="searchForm">
			<div class="search-nickname-div">
					<input type="text" name="user.nickname" id="nickname" value="${nickname }" 
						class="nickname-input"/>
					<input type="button" onclick="submitForm()" class="nickname-sub"/>
			</div>
		</form>
			<div id="wrapper" class="circle-user-box" >
			<ul id="ulId" >
				<c:forEach items="${follows }" var="u">
					<li >
						<div onclick="window.location='${base}/mine/member!detailForFollow.action?user.id=${u.uid }&followId=${u.fid }'">
							<div class="head-img-div">
								<img src="${base }/uploadImages/growthLog${u.uid}/${u.head_img}" 
								onerror="this.src='${base}/images/default_head.png'"  width="100%" alt="" />
							</div>
							<div class="user-info-div">
								<p>${u.nickname }</p>
								<p class="introduction-p">${u.introduction }</p>
							</div>
						</div>
						<div class="follow-div" onclick="cancleFollow(${u.fid},'${u.nickname }',$(this))">
							<p>取消关注</p>
						</div>
						<div class="clear"></div>
					</li>
				</c:forEach>
			</ul>
		</div>
		<script type="text/javascript">
				var loadDatas = "";
				function loadData(){
					var	lastIdSign = $("#lastIdSign").val();
					var nickname = $("#nickname").val();
					 $.ajax({ 
						 type: "post", 
						 url: "follow!loadData.action", 
						 async: false,
						 dataType: "json", 
						 data:{"lastIdSign":lastIdSign,"user.nickname":nickname},
						 success: function (data) {
							 if(data == "-1"){
								 alert("参数错误，请刷新页面重试");
							 }else if(data == "0"){
								 loadDatas = "";
							 }else{
								 loadDatas = data;
							 }
						 } 
						});
				}
				refresher.init({
					id: "wrapper",//<------------------------------------------------------------------------------------┐
					pullDownAction: Refresh,
					pullUpAction: Load
				});
				var generatedCount = 0;
				function Refresh() {
					var nickname = $("#nickname").val();
					window.location = '${base}/mine/follow!list.action?user.nickname='+nickname;
				}
				
				function Load() {
					loadData();
					setTimeout(function () {// <-- Simulate network congestion, remove setTimeout from production!
						var el, li, i;
						el = document.querySelector("#wrapper ul");
						if(loadDatas != ''){
							$.each(loadDatas,function(i,item){
								li = document.createElement('li');
								 var innerH = ""
								innerH = '<div onclick="window.location=\'${base}/mine/member!detailForCircle.action?user.id='+item.uid+'\'">'
											 +'<div class="head-img-div">'
												 	+'<img src="/uploadImages/growthLog'+item.uid+'/'+item.head_img+'" '
													+'onerror="this.src=\'/images/default_head.png\'"  width="100%" />'
										     +'</div>'
										     +'<div class="user-info-div">'
													+'<p>'+item.nickname+'</p>'
													+'<p class="introduction-p">'+item.introduction+'</p>'
											 +'</div>'
										  +'</div>'
										  +'<div class="follow-div" onclick="cancleFollow('+item.fid+',\''+item.nickname+'\',$(this))">'
									 	   		+'<p >取消关注</p>'
									  	  + '</div>'
									  	  +'<div class="clear"></div>'; 
								li.innerHTML = innerH;
								if(i == loadDatas.length-1){
									var lastIdSignNode = document.getElementById('lastIdSign');
									lastIdSignNode.value = item.fid;
								}
								el.appendChild(li, el.childNodes[0]);
				    		});
						}
						wrapper.refresh();
						for (var i = 0; i < document.querySelectorAll("#wrapper ul li").length; i++) {
							document.querySelectorAll("#wrapper ul li")[i];
						}
						loadDatas = '';
					}, 1000);
				}
				
		</script>
	</body>
</html>
