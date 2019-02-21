<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/content/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>${nickname }的日志</title>
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
		<script type="text/javascript" src="${base }/phone-js/iscroll.js"></script>
		<script type="text/javascript" src="${base }/phone-js/pullToRefresh.js"></script>
		<script type="text/javascript" src="${base }/js/jquery.js"></script>
		<script type="text/javascript"  src="${base }/phone-js/xback.js"></script>
		<script type="text/javascript">
			XBack.listen(function(){
				url = "${base}/mine/member!detailForFollow.action?user.id="
						+$("#userId").val()
 						+"&followId="
 						+$("#followId").val();
				window.location = url;
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
		<input type="hidden" id="followId" value="${followId }"/>
		<input type="hidden" id="userId" value="${userId }"/>
		<div id="wrapper">
			<c:if test="${!empty(diaries) }">
				<ul id="ulId">
					<c:forEach items="${diaries }" var="d" varStatus="status">
						<li class="${(status.index+1)%2==0?'rightLi':'leftLi' }" 
							onclick="window.location='${base}/mine/follow!diaryDetail.action?follow.id=${followId }&diary.id=${d.id }'">
							<figure style="display:block;float:left;margin:0 5px 5px 0;width:100%;">
								<img src="${base }/uploadImages/growthLog${d.uid }/diary${d.id }/${d.image}" 
									onerror="src='${base}/images/default_diary_image.jpg'"  style="width:100%;height:200px;"/>
							</figure>
							<div class="index-diary-detail">
								<p class="index-diary-title" >${d.title }</p>
								<p class="index-diary-content">${d.content }</p>
								<span style="float:right;margin-top:5px;">……</span>
								<div class="clear" ></div>
							</div>
						</li>
					</c:forEach>
					<div class="clear" id="clearDiv"></div>
				</ul>
			</c:if>
			<c:if test="${empty(diaries) }">
				<div style="margin-top:200px;text-align:center;color:red;">${nickname }还没有发表日志哦</div>
			</c:if>
		</div>
		 <script type="text/javascript">
				var loadDatas = "";
				var followId = $("#followId").val();
				var userId = $("#userId").val();
				function loadData(){
					var	lastIdSign = $("#lastIdSign").val();
					 $.ajax({ 
						 type: "post", 
						 url: "follow!loadPersonDiaryData.action", 
						 async: false,
						 dataType: "json", 
						 data:{"lastIdSign":lastIdSign,"user.id":userId},
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
					id: "wrapper",
					pullDownAction: Refresh,
					pullUpAction: Load
				});
				var generatedCount = 0;
				function Refresh() {
					window.location = '${base}/mine/follow!personDiaryList.action?follow.id='+followId+"&user.id="+userId;
				}
				
				function Load() {
					loadData();
					setTimeout(function () {
						var el, li, i;
						el = document.querySelector("#wrapper ul");
						if(loadDatas != ''){
							var clearNode = document.getElementById('clearDiv');
							clearNode.parentNode.removeChild(clearNode);
							var liList = document.getElementsByName('diaryLi');
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
								li.setAttribute('name','diaryLi');
								li.setAttribute('id','diaryLi'+item.id);
								 if(floatSign%2 == 0){
									li.className = 'rightLi';
								}else {
									li.className = 'leftLi';
								} 
								floatSign = floatSign + 1;
								li.innerHTML = '<figure style="display:block;float:left;margin:0 5px 5px 0;width:100%;">'
								     + '<img src="${base }/uploadImages/growthLog'+item.uid+'/diary'+item.id+'/'+item.image+'"' 
									 + ' onerror="src=\'/images/default_diary_image.jpg\'"  style="width:100%;height:200px;"/>'
								     + '</figure>'
								     + '<div class="index-diary-detail">'
							         + '<p class="index-diary-title" >'+item.title+'</p>'
									 + '<p class="index-diary-content">'+item.content+'</p>'
									 + '<span style="float:right;margin-top:5px;">……</span>'
									 + '<div class="clear" ></div>'
									 + '</div>';
								li.onclick=function(){
									window.location='${base}/mine/follow!diaryDetail.action?follow.id='+followId+'&diary.id='+item.id+'&model='+model;
								};
								if(i == loadDatas.length-1){
									var lastIdSignNode = document.getElementById('lastIdSign');
									lastIdSignNode.value = item.id;
								}
								el.appendChild(li, el.childNodes[0]);
				    		});
						}
						wrapper.refresh();
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
