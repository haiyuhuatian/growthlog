<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/content/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>${circle }</title>
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
		<script type="text/javascript">
			function submitForm(){
				$('#searchForm').submit();
			}
			function toFollow(userId,item){
				$.post("follow!followUser.action",{"user.id":userId},function(data){
					if(data == "0"){
						alert("参数错误，请刷新后重试");
					}else{
						item.html("<p style='background-color:gray'>已关注</p>");
					}
				});
			}
			XBack.listen(function(){
		 		window.location = "${base}/mine/circle!myCircle.action";
			});
		</script>
	</head>
	<body style="background-color:#F0EFEE;">
	 <form action="${base }/mine/circle!userList.action" method="post" id="searchForm">
			<div class="search-nickname-div">
				<input type="hidden" name="circle.id" value="${circle.id }"/>
					<input type="text" name="user.nickname" id="nickname" value="${circleUserList['user.nickname'] }" 
						class="nickname-input"/>
					<input type="button" onclick="submitForm()" class="nickname-sub"/>
			</div>
		</form>  
		<input type="hidden" value="${circle.id }" id="circleId"/>
		<input type="hidden" id="lastIdSign" value="${lastIdSign }"/>
		<input type="hidden" id="selfId" value="${loginUser.id }"/>
		<div id="wrapper" class="circle-user-box">
			<ul id="ulId" >
				<c:forEach items="${users }" var="u">
					<li >
						<div onclick="window.location='${base}/mine/member!detailForCircle.action?user.id=${u.id }&circle.id=${circle.id }'">
							<div class="head-img-div">
								<img src="${base }/uploadImages/growthLog${u.id}/${u.head_img}" 
								onerror="this.src='${base}/images/default_head.png'"  width="100%" alt="" />
							</div>
							<div class="user-info-div">
								<p>${u.nickname }</p>
								<p class="introduction-p">${u.introduction }</p>
							</div>
						</div>
						<c:if test="${u.id ne loginUser.id }">
						<div class="follow-div" onclick="toFollow(${u.id},$(this))">
							<p style="${!empty(u.follow_id)?'background-color:gray;':''}">
								<c:if test="${empty(u.follow_id) }">关&nbsp;&nbsp;注</c:if>
								<c:if test="${!empty(u.follow_id) }">已关注</c:if>
							</p>
						</div>
						</c:if>
						<div class="clear"></div>
					</li>
				</c:forEach>
			</ul>
		</div>
		<script type="text/javascript">
				var loadDatas = "";
				var circleId = $("#circleId").val();
				var selfId = $("#selfId").val();
				function loadData(){
					var	lastIdSign = $("#lastIdSign").val();
					 $.ajax({ 
						 type: "post", 
						 url: "circle!loadUserData.action", 
						 async: false,
						 dataType: "json", 
						 data:{"lastIdSign":lastIdSign,"circle.id":circleId,"nickname":$("#nickname").val()},
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
					window.location = '${base}/mine/circle!userList.action?circle.id='+circleId;
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
								innerH = '<div onclick="window.location=\'${base}/mine/member!detailForCircle.action?user.id='+item.id+'&circle.id='+circleId+'\'">'
											 +'<div class="head-img-div">'
												 	+'<img src="/uploadImages/growthLog'+item.id+'/'+item.head_img+'" '
													+'onerror="this.src=\'/images/default_head.png\'"  width="100%" />'
										     +'</div>'
										     +'<div class="user-info-div">'
													+'<p>'+item.nickname+'</p>'
													+'<p class="introduction-p">'+item.introduction+'</p>'
											 +'</div>'
										  +'</div>';
								if(selfId != item.id){
									innerH = innerH
										   +'<div class="follow-div" onclick="toFollow('+item.id+',$(this))">';	
									 if(item.follow_id == ''||item.follow_id == null){
										 innerH = innerH
									 	    +'<p >关&nbsp;&nbsp;注</p>';
									 }else{
										 innerH = innerH
									 	    +'<p style="background-color:gray;">已关注</p>';
									 }
									 innerH = innerH + '</div>';
								}
								innerH = innerH+'<div class="clear"></div>'; 
								li.innerHTML = innerH;
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
						loadDatas = '';
					}, 1000);
				}
				
		</script>
	</body>
</html>
