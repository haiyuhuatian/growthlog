<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/content/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>积分兑换申请单</title>
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
		<script src='${base }/js/hhSwipe.js' type="text/javascript"></script> 
		<script type="text/javascript">
			 XBack.listen(function(){
				  window.location = "${base}/portal/integral!awardDetail.action?award.id="+$("#awardId").val();
				});
			 function getCityName(level,item) {
	  				var pid = item.val();
	  				$("#city"+level).empty();
	  	    		$("#city"+level).append("<option value=''>--请选择--</option>");
	  	    		if(level == "2") {
	  	    			$("#city"+3).empty();
	  		    		$("#city"+3).append("<option value=''>--请选择--</option>");
	  	    		}
	  				$.post("/portal/user!getCityName.action",{"cityMap.pid":pid},function(data){
	  					if(data != "") {
	  						$.each(data,function(i,item){
	  			    				$("#city"+level).append("<option value='"+item.id+"' >"+item.name+"</option>");
	  			    		});
	  					}
	  				},"json");
	  			}
			 function subForm(){
				 var error = 0;
				 if($("#realname").val() == ""){
					 alert("请填写姓名");
					 $("#realname").focus();
					 error = error+1;
				 }else if($("#mobile").val() == ""){
					 alert("请填写手机号");
					 $("#mobile").focus();
					 error = error+1;
				 }else if($("#city1").val() == ""){
					 alert("请选择所在的省/直辖市");
					 $("#city1").focus();
					 error = error+1;
				 }else  if($("#city2").val() == ""){
					 alert("请选择所在的市");
					 $("#city2").focus();
					 error = error+1;
				 }else  if($("#city3").val() == ""){
					 alert("请选择所在的区/县");
					 $("#city3").focus();
					 error = error+1;
				 }else  if($("#detailed_address").val() == ""){
					 alert("请填写详细地址");
					 $("#detailed_address").focus();
					 error = error+1;
				 }
				 if(error == 0){
					 $("#applyForm").submit();
				 }
			 }
		</script>
	</head>
	<body style="background-color:#F0EFEE;">
		<form action="integral!subApply.action" method="post" id="applyForm">
			<input type="hidden" id="awardId" name="award.id" value="${award.id }"/>
			<div class="info-box">
				<ul>
					<li>姓名：<input type="text" name="awardRecord.realname" id="realname"/></li>
					<li>手机号：<input type="text" name="awardRecord.mobile" id="mobile"/></li>
				</ul>
			</div>
			<div class="info-box">
				<ul>
					<li>
						省/直辖市：
						<select name="awardRecord.province" id="city1" onchange="getCityName(2,$(this))">
							<option value="">--请选择--</option>
						<c:forEach items="${city1 }" var="c">
							<option value="${c.id }">${c.name }</option>
						</c:forEach>
						</select>
					</li>
					<li>
						市：
						<select name="awardRecord.city" id="city2" onchange="getCityName(3,$(this))">
							<option value="">--请选择--</option>
						</select>
					</li>
					<li>
						区/县：
						<select id="city3" name="awardRecord.area">
							<option value="">--请选择--</option>
						</select>
					</li>
					<li>
						详细地址：
						<textarea name="awardRecord.detailed_address" id="detailed_address"></textarea>
					</li>
				</ul>
			</div>
			<div class="sub-box" onclick="subForm()">
				提&nbsp;&nbsp;&nbsp;&nbsp;交
			</div>
		</form>
	</body>
</html>
