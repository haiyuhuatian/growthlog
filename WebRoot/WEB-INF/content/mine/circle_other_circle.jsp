<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/content/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>圈子</title>
		<meta name="keywords" value="成长日志" />
		<meta name="description" value="首个记录成长日志的网站" />
		<meta name="author" value="成长日志" />
		<meta name="viewport" content="width=device-width,height=device-height,inital-scale=1.0,maximum-scale=1.0,user-scalable=no;">
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="apple-mobile-web-app-status-bar-style" content="black">
		<meta name="format-detection" content="telephone=no">
		<link href="${base }/phone-css/phone.css" rel='stylesheet' type='text/css' />
		<script type="text/javascript" src="${base }/js/jquery.js"></script>
		<script type="text/javascript"  src="${base }/phone-js/xback.js"></script>
		<script type="text/javascript">
			function submitForm(){
				$('#searchForm').submit();
			}
			function searchByType(type){
				$("#circleType").val(type);
				$('#searchForm').submit();
			}
			XBack.listen(function(){
		 			window.location = "${base}/mine/circle!myCircle.action";
			});
		</script>
	</head>
	<body style="background-color:#F0EFEE;">
		<input type="hidden" id="lastIdSign" name="lastIdSign" value="${lastCircleIdSign }"/>
		<form action="${base }/mine/circle!otherCircles.action" method="post" id="searchForm">
			<div class="other-circle-search">
					<input class="search-circle" type="text" id="circleName" name="circle.name" value="${otherCircleList['circle.name'] }" />
					<input class="search-sircle-sub" type="button" onclick="submitForm()" />
					<input type="hidden" id="circleType" name="type" value="${empty(type)?0:type }"/>
			</div>
			<div class="refresh-other-circles" onclick="window.location='${base}/mine/circle!otherCircles.action?type=0'">
				<img src="${base }/images/refresh.png" width="30px" alt="" />
				刷新
			</div>
			<div class="circle-type" >
				<p class="${type==0?'selected-circle-type':'' }" style="width:9%;text-align:center" 
						onclick="searchByType(0)">全部</p>
				<p class="${type==1?'selected-circle-type':'' }" style="width:10%;text-align:center"
				        onclick="searchByType(1)">同城</p>
				<p class="${type==2?'selected-circle-type':'' }" style="width:10%;text-align:center"
						onclick="searchByType(2)">同学</p>
				<p class="${type==3?'selected-circle-type':'' }" style="width:18%;text-align:center"
						onclick="searchByType(3)">兴趣爱好</p>
				<p class="${type==4?'selected-circle-type':'' }" style="width:14%;text-align:center"
						onclick="searchByType(4)">家长会</p>
				<p class="${type==5?'selected-circle-type':'' }" style="width:10%;text-align:center"
						onclick="searchByType(5)">好友</p>
				<p class="${type==6?'selected-circle-type':'' }" style="width:10%;text-align:center"
						onclick="searchByType(6)">家人</p>
				<div class="clear"></div>
			</div>
		</form>
		<div class="other-circle-box">
			<c:forEach items="${otherCircles }" var="c">
				<div class="circle-detail" onclick="window.location='${base}/mine/circle!detail.action?&fromPath=otherCircle&circle.id=${c.id }'">
					<img src="${base }/circle_cover/${c.cover}" width="100%" onerror="this.src='${base }/images/circle-cover.jpg'" alt="" />
					<p >${c.name }</p>
				</div>
			</c:forEach>
			<div class="clear" id="clearDiv"></div>
		</div>
		<div class="load-more-circle" id="loadNore" onclick="loadMore()">
			点击加载更多
		</div>
		<input type="hidden" id="lastCircleIdSign" value="${lastCircleIdSign }"/>
		<script type="text/javascript">
			function loadMore(){
				var lastIdSign = $("#lastIdSign").val();
				var circleName = $("#circleName").val();
				var circleType = $("#circleType").val();
				$.post(
						"circle!loadOtherCircles.action",
						{"lastIdSign":lastIdSign,"circle.name":circleName,"circle.type":circleType},
						function(data){
							if(data == "0"){
								$("#loadNore").html("没有更多了");
							}else{
								$.each(data,function(index,item){
									var node = "<div class='circle-detail' onclick='window.location=\"${base}/mine/circle!detail.action?circle.id="+item.id+"\"'>"
											 		+"<img src='${base }/circle_cover/"+item.cover+"' width='100%' onerror='this.src=\'${base }/images/circle-cover.jpg\''  />"
												    +"<p >"+item.name+"</p>"
											 +"</div>"
									if(index == data.length-1){
										$("#lastIdSign").val(item.id);
									}
									$("#clearDiv").before(node);
								});
							}
						},"json");
			}
		</script>
	</body>
</html>
