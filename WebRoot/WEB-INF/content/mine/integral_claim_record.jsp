<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/content/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>积分兑换记录</title>
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
			 XBack.listen(function(){
				  window.location = "${base}/mine/member!detail.action";
				});
			 function signOrEvaluate(recordStatus,signStatus,awardRecordId){
				 if(recordStatus == 2){
					 window.location= "${base}/mine/integral!evaluation.action?awardRecord.id="+awardRecordId;
				 }else if(recordStatus==1&&signStatus==0){
					 $.post("integral!confirmGoods.action",{"awardRecord.id":awardRecordId},function(data){
						 if(data=="-1"){
							 alert("参数错误，请退出页面重试");
						 }else{
							 $("#signBtn").html("评价");
							 $("#signBtn").click(function(){
								 window.location= "${base}/mine/integral!evaluation.action?awardRecord.id="+awardRecordId;
							 });
						 }
					 });
				 }
			 }
		</script>
	</head>
	<body style="background-color:#F0EFEE;">
		<c:forEach items="${list }" var="l">
			<div class="claim-record-box" >
					<div class="courier-mess">
						<c:if test="${l.status != 0 }">
							<div style="float:left;">
								<p>快递公司：${l.courier_company}</p>
								<p style="color:#969d98;font-size:10px;">订单编号：${l.courier_number }</p>
							</div>
						</c:if>
						<div class="record-status" style="${l.verify!=1?'color:red':''}">
							<c:if test="${l.verify == 1 }">
								${l.status == 0?'待发货':l.status ==1?'已发货':l.status ==2?'待评价':'已完成'  }
							</c:if>
							<c:if test="${l.verify != 1 }">
								${l.verify == 0?'待审核':'未通过' }
							</c:if>
						</div>
						<div class="clear"></div>
					</div>
				<div class="award-info" onclick="window.location='${base}/portal/integral!awardDetail.action?fromPath=member&award.id=${l.award_id }'">
					<div class="award-cover">
						<img src="${base }/awardImages/award${l.award_id}/${l.cover}" width="100%" />
					</div>
					<div class="award-name">
						${l.name }
					</div>
					<div class="award-need-integral">
						<span>${l.integral_need }</span> 积分
					</div>
					<div class="clear"></div>
				</div>
				<div class="sign-info" style="${l.verify==2?'text-align:right;':''}">
					<c:if test="${l.status != 3 && l.verify==1 }">
						<div class="sign-info-btn" id="signBtn" onclick="signOrEvaluate(${l.status},${l.sign_status },${l.id })">
							${l.status == 1 &&l.sign_status==0?"确认收货":l.status==2?"评价":"" }
						</div>
						<div class="clear"></div>
					</c:if>
					<c:if test="${l.verify==2 }">
						${l.remark }
					</c:if>
				</div>
			</div>
		</c:forEach>
	</body>
</html>
