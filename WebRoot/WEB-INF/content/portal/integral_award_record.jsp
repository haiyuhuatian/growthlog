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
		</script>
	</head>
	<body style="background-color:#F0EFEE;">
			<input type="hidden" id="awardId" name="award.id" value="${awardRecord.award_id }"/>
			<div class="info-box">
				<ul>
					<li>姓名：${awardRecord.realname }</li>
					<li>手机号：${awardRecord.mobile }</li>
				</ul>
			</div>
			<div class="info-box">
				<ul>
					<li>
						省：${province }
					</li>
					<li>
						市：${city }
					</li>
					<li>
						区/县：${area }
					</li>
					<li>
						详细地址：${awardRecord.detailed_address }
					</li>
				</ul>
			</div>
			<div class="info-box">	
				<ul>
					<li>提交时间：<fmt:formatDate value="${awardRecord.create_time }" pattern="yyyy-MM-dd HH:mm:ss" /></li>
					<li>审核状态：${awardRecord.verify == 0?'待审核':awardRecord.verify == 1?'通过':'不通过' }</li>
					<li>审核时间：<fmt:formatDate value="${awardRecord.verify_time }" pattern="yyyy-MM-dd HH:mm:ss" /></li>
					<li>备注：${awardRecord.remark }</li>
				</ul>
			</div>
			<c:if test="${awardRecord.verify==1 }">
				<div class="info-box">	
					<ul>
						<li>发货状态：${empty(awardRecord.status)?'':awardRecord.status==0?'待发货':awardRecord.status==1?'已发货':awardRecord.status == 2?'待评价':'已完成' }</li>
					</ul>
				</div>
			</c:if>
			<div class="sub-box" onclick="window.location = '${base}/portal/integral!awardDetail.action?award.id=${awardRecord.award_id }'">
				返&nbsp;&nbsp;&nbsp;&nbsp;回
			</div>
	</body>
</html>
