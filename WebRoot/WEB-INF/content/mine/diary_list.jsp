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
		<script type="text/javascript" src="${base }/js/jquery.js"></script> 
		<script type="text/javascript"  src="${base }/phone-js/xback.js"></script>
		<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>
		<script type="text/javascript">
		  function	showDetail(diaryId){
			  window.location="${base}/mine/diary!detail.action?diary.id="
					         +diaryId
					         +"&diaryYear="
					         +$("#diaryYear").val()
					         +"&diaryMonth="
					         +$("#diaryMonth").val();
		  }
		  function showMenu(){
			  $("#menu").show();
		  }
		  function showDiaryList(diaryYear,diaryMonth) {
			  window.location = "${base}/mine/diary!list.action?diaryYear="+diaryYear+"&diaryMonth="+diaryMonth;
		  }
		  function closeMenu(){
			  $("#menu").hide();
		  }
		  function ifLogin(){
			  var isLogin = $("#isLogin").val();
			   if(isLogin != ""&&isLogin=="no"){
				  alert("请先登录哦");
				  wx.closeWindow();
			  } 
		  }
		  XBack.listen(function(){
		 		wx.closeWindow();
			});
		 
		</script>
	</head>
	<body style="background-color:#F0EFEE;" onload="ifLogin()">
		<input type="hidden" id="isLogin" value="${isLogin }"/>
		<input type="hidden" id="diaryYear" value="${diaryYear }"/>
		<input type="hidden" id="diaryMonth" value="${diaryMonth }"/>
		<div class="my-diary-box">
			<c:if test="${empty(diaryList) }">
				<div style="width:80%;margin:0 auto;">您本月还没有写日志哦</div>
			</c:if>
			<c:forEach items="${diaryList }" var="d">
			<div class="my-diary-list" onclick="showDetail(${d.id})">
				<div class="title-box">
					<p class="date-box">${d.diaryMonth }月/${d.diaryDay }日</p>
					<p style="margin-top:10px;">${d.title }</p>
				</div>
				<c:if test="${d.imgNum != 0 }">
				<div class="detail-box">
					<c:if test="${d.imgNum==1 }">
						<c:forEach items="${d.images }" var="i">
							<figure style="width:100%;">
								<img src="${base }/uploadImages/growthLog${loginUser.id }/diary${d.id }/${i}" style="width:100%;" />
							</figure>
						</c:forEach>
					</c:if>
					<c:if test="${d.imgNum==2||d.imgNum==4 }">
						<c:forEach items="${d.images }" var="i">
							<figure style="width:45%;">
								<img src="${base }/uploadImages/growthLog${loginUser.id }/diary${d.id }/${i}" style="width:100%;height:100px;" />
							</figure>
						</c:forEach>
					</c:if>
					<c:if test="${d.imgNum==3 }">
						<c:forEach items="${d.images }" var="i">
							<figure style="width:28%;">
								<img src="${base }/uploadImages/growthLog${loginUser.id }/diary${d.id }/${i}" style="width:100%;height:100px;" />
							</figure>
						</c:forEach>
					</c:if>
				</div>
				</c:if>
				<c:if test="${d.imgNum == 0 }">
					<div class="diary-content-show">
						${d.content }
					</div>
					<div class="content-overflow">……</div>
				</c:if>
				<div class="clear"></div>
			</div>
			</c:forEach>
		</div>	
		<div class="write-comment" id="menu" style="display:none">
			<div onclick="closeMenu()" style="margin-top:50px;text-align:right;width:97%;color:white;">
				<img src="${base }/images/close2h.png" alt="" width="15px" />关闭
			</div>
			<c:forEach items="${menu }" var="m">
			<div style="width:100%;margin-top:20px;">
					<div class="yearDiv" style="${diaryYear eq m.dYear?'background-color:#f6672f':'background-color:#311bee;'}">
						${m.dYear }
					</div>
					<div style="width:100%;">
						<c:forEach items="${m.monthList }" var="o">
							<div class="monthDiv" style="${(diaryMonth eq o.month && diaryYear eq m.dYear)?'background-color:#f99128;color:#fff':'background-color:#fff'}"
								onclick="showDiaryList(${m.dYear},${o.month })">
								${o.month }月
							</div>
						</c:forEach>
						<div class="clear"></div>
					</div>
			</div>
			</c:forEach>
		</div>
		<div class="footer">
			<span style="color:#f85f1b;font-size:18px;">
            	<a onclick="showMenu()"  style="color:#037f88;text-decoration:none;">目录&nbsp;&nbsp;&nbsp;&nbsp;</a>
            	<a href="${base }/mine/diary!write.action"  style="color:#037f88;text-decoration:none;">写日志</a>
          </span>
		</div>
	</body>
</html>
