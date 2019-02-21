<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/content/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>积分详情</title>
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
		<script language="javascript" type="text/javascript" src="${base}/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript">
			XBack.listen(function(){
		 		window.location="${base}/mine/member!detail.action";
			});
			function selectDate(){
				$("#searchDiv").show();
			}
			function closeSearch(){
				$("#searchDiv").hide();
			}
			function changeDate(item){
				var searchDate = item.val();
				if(searchDate == ""){
					alert("请选择年月");
				}else{
					window.location = "${base}/mine/integral!detail.action?searchDate="+searchDate;
				}
			}
		</script>
	</head>
	<body style="background-color:#F0EFEE;">
		<div class="integral-detail">
			<div class="total-bar">当前总积分：${totalIntegral }分</div>
			<div class="search-bar">
				<div class="search-date">
					${searchDate }
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					共&nbsp;&nbsp;&nbsp;<span style="color:red;">${monthTotal }</span>&nbsp;&nbsp;&nbsp;积分
				</div>
				<div class="search-img" onclick="selectDate()">
					<img src="${base }/images/calendar.png" width="25px;"/>
				</div>
				<div class="clear"></div>
			</div>
			<div class="integral-detail-list" >
				<table>
					<tr>
						<th>积分类型</th>
						<th>分值</th>
						<th>日期</th>
					</tr>
				<c:forEach items="${integralList }" var="i" varStatus="status">
					<tr style="${(status.index+1)%2!=0?'background-color:#c9c6c6;':''}">
						<td>${i.type==1?"注册":"写日志" }</td>
						<td>${i.value }</td>
						<td><fmt:formatDate value="${i.create_time }" pattern="yyyy-MM-dd"/></td>
					</tr>
				</c:forEach>
				</table>
			</div>
		</div>
		<div class="write-comment" id="searchDiv" style="display:none;">
			<div onclick="closeSearch()" style="margin-top:17px;text-align:right;width:97%;color:white;">
				<img src="${base }/images/close2h.png" alt="" width="15px" />关闭
			</div>
			<div class="choose-date">
				<span>请选择年月：</span><input type="text"  readonly="true" onclick="WdatePicker({dateFmt:'yyyy-MM'});"
						id="diaryDate" name="searchDate"  value="${searchDate }" onchange="changeDate($(this))"
						style="width:150px;height:25px;border:1px solid gray;font-size:18px;"/>
			</div>
		</div>
	</body>
</html>
