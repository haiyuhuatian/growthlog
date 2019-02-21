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
		<link href="${base }/phone-css/phone.css" rel='stylesheet' type='text/css' />
		<script type="text/javascript" src="${base }/js/jquery.js"></script> 
		<script type="text/javascript"  src="${base }/phone-js/xback.js"></script>
		<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>
		<script src='${base }/js/hhSwipe.js' type="text/javascript"></script> 
		<style type="text/css">
			*{margin:0;padding:0;list-style-type:none;}
			a,img{border:0;}
			body{font:12px/180% Arial, Helvetica, sans-serif, "新宋体";}
			
			.addWrap{ position:relative; width:100%;background:#fff;margin:0; padding:0;}
			.addWrap .swipe{overflow: hidden;visibility: hidden;position:relative;}
			.addWrap .swipe-wrap{overflow:hidden;position:relative;}
			.addWrap .swipe-wrap > div {float: left;width: 100%;position:relative;}
			#position{ position:absolute; bottom:0; right:0; padding-right:8px; margin:0; background:#000; opacity: 0.4; width:100%; filter: alpha(opacity=50);text-align:right;}
			#position li{width:10px;height:10px;margin:0 2px;display:inline-block;-webkit-border-radius:5px;border-radius:5px;background-color:#AFAFAF;}
			#position li.cur{background-color:#FF0000;}
			.img-responsive { display: block; max-width:100%;height: auto;}
		</style>
		<script type="text/javascript">
		 XBack.listen(function(){
				 var fromPath = $("#fromPath").val();
				 if(fromPath==""||fromPath==null){
					 window.location = "${base}/portal/integral!mall.action?lastIdSign="+$("#awardId").val();
				 }else{
					 window.location = "${base}/mine/integral!claimRecord.action";
				 }
				  
			});
			 wx.config({
	             debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
	             appId: '${appId}', // 必填，公众号的唯一标识
	             timestamp: '${timestamp}', // 必填，生成签名的时间戳
	             nonceStr: '${noncestr}', // 必填，生成签名的随机串
	             signature:'${signature}',// 必填，签名
	             jsApiList: ['checkJsApi',
	                         'previewImage'] // 必填，需要使用的JS接口列表
	         });
			 wx.ready(function(){  //微信读取
			        var srcList = [];
			        $.each($('.award-img a img'),function(i,item){  //$('.info_detail .container img') 容器中的图片
			            if(item.src) {
			                srcList.push(item.src);
			                $(item).click(function(e){
			                    // 通过这个API就能直接调起微信客户端的图片播放组件了
			                    wx.previewImage({
			                        current: this.src,
			                        urls: srcList
			                    });
			                });
			            }
			        });
			    });
			
			 function toClaim(){
				 var userId = $("#userId").val();
				 if(userId == ""||userId==null){
					 alert("请先登录哦");
				 }else{
					 var notEnough = $("#notEnough").val();
					 if(notEnough==""||notEnough==null){
						 window.location="${base}/portal/integral!toClaim.action?award.id="+$("#awardId").val();
					 }else{
						 alert("您的积分不足以兑换该奖品哦，请继续努力吧");
					 }
				 }
			 }
			 function changeOption(type,item){
				 item.addClass("selected-option").siblings().removeClass("selected-option");
				 $("#option"+type).show().siblings(".detail-box").hide();
			 }
			function showEvaluationImg(item){
				 var srcList = [];
				 var serverPath = $("#serverPath").val();
				 var currentImg = serverPath+item.attr("src");
				 srcList.push(currentImg);
				 var imgNodes = item.siblings("img");
				 if(imgNodes.length>0){
					 for(var i=0;i<imgNodes.length;i++){
						 var imgNode = imgNodes[i];
						 srcList.push(imgNode.src);
					 }
				 }
				 wx.previewImage({
	                 current: currentImg,
	                 urls: srcList
	             }); 
			}
		</script>
	</head>
	<body style="background-color:#F0EFEE;">
		<input type="hidden" id="inventory" value="${award.inventory }"/>
		<input type="hidden" id="awardId" value="${award.id }"/>
		<input type="hidden" id="userId" value="${user.id }"/>
		<input type="hidden" id="fromPath" value="${fromPath }"/>
		<input type="hidden" id="serverPath" value="${serverPath }"/>
		<input type="hidden" id="notEnough" value="${notEnough }"/>
		<div class="addWrap">
			<div class="swipe" id="mySwipe">
				<div class="swipe-wrap">
					<c:forEach items="${images }" var="i">
						<div class="award-img">
							<a>
								<img class="img-responsive" src="${base }/awardImages/award${award.id}/${i.name}"/>
							</a>
						</div>
					</c:forEach>
				</div>
			</div>
			<ul id="position">
				<c:forEach items="${images }" var="m" varStatus="status">
					<li class="${status.index==0?'cur':'' }"></li>
				</c:forEach>
			</ul>
		</div>
		<div class="award-box">
			<p style="font-size:14px;">
				<span style="color:red;">${award.integral_need } </span>积分
				<span style="float:right;">数量：${award.inventory } 个</span>
				<div class="clear"></div>
			</p>
			<p class="award-name" >${award.name }</p>
		</div>
		<div class="award-box" style="background-color:#fff;" >
			<c:if test="${empty(awardRecord)||awardRecord.verify == 2||awardRecord.status==3}">
				<div class="claim-button" style="${award.inventory == 0?'background-color:gray;':''}" onclick="${award.inventory > 0?'toClaim()':''}">
					我要兑换
				</div>
			</c:if>
			<c:if test="${!empty(awardRecord) && awardRecord.verify ne 2 && awardRecord.status ne 3}">
					<div class="claim-button" style="background-color:#36be6a;"  onclick="window.location='integral!showAwardRecord.action?awardRecord.id=${awardRecord.id}'">
						兑换申请
					</div>
			</c:if>
			<div class="clear"></div>
		</div>
		<div class="option-box">
			<p class="selected-option" onclick="changeOption(1,$(this))">详情</p>
			<p style="width:25%;" onclick="changeOption(2,$(this))">兑换流程</p>
			<p style="width:25%;" onclick="changeOption(3,$(this))">注意事项</p>
			<p onclick="changeOption(4,$(this))">评价</p>
			<div class="clear"></div>
		</div>
		<div class="detail-box" id="option1">
			<p style="line-height:25px;">${award.description }</p>
		</div>
		<div class="detail-box" style="display:none" id="option2">
			<ul>
				<li>1、通过写日志获取积分，当积分达到奖品所需的数额后可点击“我要兑换”按钮</li>
				<li>2、填写相应的信息后提交申请</li>
				<li>3、提交后进入审核阶段，审核通过后名单会在奖品详情页公布，不通过的将返还积分</li>
				<li>4、审核通过后客服人员核对地址，确定地址无误后将陆续发出</li>
				<li>5、收到奖品后须在奖品详情页填写感言并上传奖品照片</li>
			</ul>
			
		</div>
		<div class="detail-box" style="display:none" id="option3">
			<p style="line-height:25px;">收到奖品后一定要记得填写感言并上传照片哦，否则将会影响今后的兑换以及其他活动哦</p>
		</div>
		<div class="detail-box" style="display:none" id="option4">
			<c:if test="${empty(evaluations) }">
				<p style="text-align:center;color:red;">还没有评价哦</p>
			</c:if>
			<c:if test="${!empty(evaluations) }">
				<c:forEach items="${evaluations }" var="e" varStatus="status">
					<div class="evaluation-list-box" style="${status.index%2!=0?'background-color:#F0EFEE;':''}">
					<div class="evaluation-user-box">
						<div class="head-img-box">
							<img src="${base }/uploadImages/growthLog${e.user_id}/${e.head_img}" 
								onerror="this.src='${base}/images/default_head.png'" width="50px"/>
						</div>
						<div class="user-nickname">
							<p style="font-size:14px;">${e.nickname }</p>
							<p style="color:#a29d9d;">
								<fmt:formatDate value="${e.create_time }" pattern="yyyy-MM-dd HH:mm:ss" />
							</p>
						</div>
						<div class="clear"></div>
					</div>
					<div class="evaluation-content">
						${e.evaluation }
					</div>
					<c:if test="${!empty(e.imgs) }">
						<div class="evaluation-img-box">
							<c:forEach items="${e.imgs }" var="i">
								<img src="${base }/awardEvaluationImgs/awardRecord${e.id}/${i}" onclick="showEvaluationImg($(this))" width="20%" />
							</c:forEach>
						</div>
					</c:if>
					</div>
				</c:forEach>
			</c:if>
		</div>
		<script type="text/javascript">
			var bullets = document.getElementById('position').getElementsByTagName('li');
			var banner = Swipe(document.getElementById('mySwipe'), {
				auto: 4000,
				continuous: true,
				disableScroll:false,
				callback: function(pos) {
					var i = bullets.length;
					while (i--) {
						bullets[i].className = ' ';
					}
					bullets[pos].className = 'cur';
				}
			})
		</script>
	</body>
</html>
