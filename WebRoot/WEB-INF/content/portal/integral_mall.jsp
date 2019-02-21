<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/content/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>积分商城</title>
		<meta name="keywords" value="成长日志" />
		<meta name="description" value="首个记录成长日志的网站" />
		<meta name="author" value="成长日志" />
		<meta name="viewport" content="width=device-width,height=device-height,inital-scale=1.0,maximum-scale=1.0,user-scalable=no;">
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="apple-mobile-web-app-status-bar-style" content="black">
		<meta name="format-detection" content="telephone=no">
		<link rel="stylesheet" href="${base }/phone-css/reset.css" />
		<link rel="stylesheet" href="${base }/phone-css/pullToRefresh.css" />
		<link href="${base }/phone-css/phone.css" rel='stylesheet' type='text/css' />
		<script type="text/javascript" src="${base }/phone-js/iscroll.js"></script>
		<script type="text/javascript" src="${base }/phone-js/pullToRefresh.js"></script>
		<script type="text/javascript" src="${base }/js/jquery.js"></script>
		<script type="text/javascript"  src="${base }/phone-js/xback.js"></script>
		<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>
		<script type="text/javascript" >
		XBack.listen(function(){
	 		wx.closeWindow();
		});
		 wx.config({
			 debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
             appId: '${appId}', // 必填，公众号的唯一标识
             timestamp: '${timestamp}', // 必填，生成签名的时间戳
             nonceStr: '${noncestr}', // 必填，生成签名的随机串
             signature:'${signature}',// 必填，签名，见附录1
			      jsApiList: [
			                  'checkJsApi'
			                 ] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
			      });
		</script>
		<style type='text/css'>
			.leftLi{
				float:left;
				margin-left:2.5%;
			}
			.rightLi {
				float:right;
				margin-right:2.5%;
			}
		</style>
	</head>
	<body style="background-color:#F0EFEE;">
		<input type="hidden" id="lastIdSign" value="${lastIdSign }"/>
		<div id="wrapper">
			<ul id="ulId">
				<c:forEach items="${awardList }" var="a" varStatus="status">
					<li class="${(status.index+1)%2==0?'rightLi':'leftLi' }" 
						onclick="window.location='${base}/portal/integral!awardDetail.action?award.id=${a.id }'">
						<figure style="display:block;float:left;margin:0 5px 5px 0;width:100%;">
							<img src="${base }/awardImages/award${a.id }/${a.cover}" 
								onerror="src='${base}/images/default_diary_image.jpg'"  style="width:100%;height:200px;"/>
						</figure>
						<div class="index-diary-detail">
							<p class="index-diary-title" >
								<span style="color:red;">${a.integral_need }</span>
								&nbsp;积分
								<span style="float:right;margin-right:5px;">数量：${a.inventory }</span>
							</p>
							<p class="index-diary-content">${a.description }</p>
							<span style="float:right;margin-top:5px;">……</span>
							<div class="clear" ></div>
						</div>
					</li>
				</c:forEach>
				<div class="clear" id="clearDiv"></div>
			</ul>
		</div>
		<script type="text/javascript">
				var loadDatas = "";
				var model = $("#model").val();
				function loadData(){
					var	lastIdSign = $("#lastIdSign").val();
					 $.ajax({ 
						 type: "post", 
						 url: "integral!loadData.action", 
						 async: false,
						 dataType: "json", 
						 data:{"lastIdSign":lastIdSign},
						 success: function (data) {
							 if(data != "0"&&data!="-1"){
								 loadDatas = data;
							 }else {
								 loadDatas = "";
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
					window.location = '${base}/portal/integral!mall.action';
				}
				
				function Load() {
					loadData();
					setTimeout(function () {// <-- Simulate network congestion, remove setTimeout from production!
						var el, li, i;
						el = document.querySelector("#wrapper ul");
						if(loadDatas != ''){
							var clearNode = document.getElementById('clearDiv');
							clearNode.parentNode.removeChild(clearNode);
							var liList = document.getElementsByName('awardLi');
							var floatSign = 0;
							if(liList.length>0){
								var lastLi = liList[liList.length-1];
								var floatVal = lastLi.style.float;
								if(floatVal == 'left'){
									floatSign = 4;
								}else {
									floatSign = 3;
								}
							}
							$.each(loadDatas,function(i,item){
								li = document.createElement('li');
								li.className = 'mui-table-view-cell';
								li.setAttribute('name','awardLi');
								li.setAttribute('id','awardLi'+item.id);
								 if(floatSign%2 == 0){
									li.className = 'rightLi';
								}else {
									li.className = 'leftLi';
								} 
								floatSign = floatSign + 1;
								li.innerHTML = '<figure style="display:block;float:left;margin:0 5px 5px 0;width:100%;">'
										     		+ '<img src="${base }/awardImages/award'+item.id+'/'+item.cover+'"' 
											 		+ ' onerror="src=\'/images/default_diary_image.jpg\'"  style="width:100%;height:200px;"/>'
										     + '</figure>'
										     + '<div class="index-diary-detail">'
									         		+ '<p class="index-diary-title" >'
									         				+'<span style="color:red;">'+item.integral_need+'</span>'
									         				+'&nbsp;积分'
															+'<span style="float:right;margin-right:5px;">数量：'+item.inventory+'</span>'
									         		+'</p>'
											 		+ '<p class="index-diary-content">'+item.description+'</p>'
											 		+ '<span style="float:right;margin-top:5px;">……</span>'
											 		+ '<div class="clear" ></div>'
											 + '</div>';
								li.onclick=function(){
									window.location='${base}/portal/integral!awardDetail.action?award.id='+item.id;
								};
								if(i == loadDatas.length-1){
									var lastIdSignNode = document.getElementById('lastIdSign');
									lastIdSignNode.value = item.id;
								}
								el.appendChild(li, el.childNodes[0]);
				    		});
						}
						wrapper.refresh();/****remember to refresh after action completed！！！   ---id.refresh(); --- ****/
						for (var i = 0; i < document.querySelectorAll("#wrapper ul li").length; i++) {
							document.querySelectorAll("#wrapper ul li")[i];
						}
						var ulNode = document.getElementById('ulId');
						var newClearDiv = document.createElement('div');
						newClearDiv.className = 'clear';
						newClearDiv.id = 'clearDiv';
						ulNode.appendChild(newClearDiv);
						loadDatas = '';
					}, 1000);
				}
				
		</script>
	</body>
</html>
