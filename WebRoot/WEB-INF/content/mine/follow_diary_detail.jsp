<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/content/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>${diary.title }</title>
		<meta name="keywords" value="成长日志" />
		<meta name="description" value="首个记录成长日志的网站" />
		<meta name="author" value="成长日志" />
		<meta name="viewport" content="width=device-width,height=device-height,inital-scale=1.0,maximum-scale=1.0,user-scalable=no;">
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="apple-mobile-web-app-status-bar-style" content="black">
		<meta name="format-detection" content="telephone=no">
		<link href="${base }/phone-css/phone.css" rel='stylesheet' type='text/css' />
		<script type="text/javascript" src="${base }/js/jquery.js"></script> 
		<script type="text/javascript" src="${base }/js/mobile-comment.js"></script> 
		<script type="text/javascript"  src="${base }/phone-js/xback.js"></script>
		<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>
		<script type="text/javascript">
			 wx.config({
	             debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
	             appId: '${appId}', // 必填，公众号的唯一标识
	             timestamp: '${timestamp}', // 必填，生成签名的时间戳
	             nonceStr: '${noncestr}', // 必填，生成签名的随机串
	             signature:'${signature}',// 必填，签名
	             jsApiList: ['checkJsApi',
	                         'previewImage',
	                         'onMenuShareTimeline'] // 必填，需要使用的JS接口列表
	         });
			 wx.ready(function(){  //微信读取
			        var srcList = [];
			        $.each($('.img-containter figure img'),function(i,item){  //$('.info_detail .container img') 容器中的图片
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
			        wx.onMenuShareTimeline({
					    title: '我的分享', // 分享标题
					    link: 'http://www.growthlog.cn/home/diary!detail.action?diary.id=1&model=0', // 分享链接，该链接域名或路径必须与当前页面对应的公众号JS安全域名一致
					    imgUrl: 'http://www.growthlog.cn/images/picture.jpg', // 分享图标
					    success: function () {
					    // 用户确认分享后执行的回调函数
					    alert('haha');
					},
					cancel: function () {
					    // 用户取消分享后执行的回调函数
					    }
					});
			    });
			 
			 XBack.listen(function(){
				  window.location = "${base}/mine/follow!personDiaryList.action?follow.id="
						  +$("#followId").val()
						  +"&lastIdSign="
						  +$("#diaryId").val()
						  +"&user.id="
						  +$("#followUserId").val();
				});
		</script>
	</head>
	<body style="background-color:#F0EFEE;">
		<input type="hidden" value="${user.nickname }" id="nickname"/>
		<input type="hidden" value="${diary.id }" id="diaryId"/>
		<input type="hidden" value="${diary.user_id }" id="followUserId"/>
		<input type="hidden" value="${followId }" id="followId"/>
		<div class="img-containter">
			<c:if test="${!empty(imgs) }">
				<c:forEach items="${imgs }" var="i">
					<figure >
						<img  src="${base }/uploadImages/growthLog${diary.user_id }/diary${diary.id}/${i}"  />
					</figure>
				</c:forEach>
				<div class="clear"></div>
			</c:if>
		</div>
		<div class="content" style="margin-top:10px;">
			<input type="hidden" value="${diary.id }" id="diaryId"/>
			<input type="hidden" value="${total }" id="total"/>
			<input type="hidden" id="userId" value="${user.id}"/>
			<input type="hidden" value="${user.head_img }" id="headImg"/>
			<div class="titleDiv">${diary.title }</div>
			<div class="diary-info">
				<span><fmt:formatDate value="${diary.create_date }" pattern="yyyy-MM-dd"></fmt:formatDate></span>
							/
				<span>${diary.weekday }</span>
							/
				<span>${diary.weather }</span>
			</div>
			<div class="diary-content">
							${diary.content }
			</div>
			<div id="cut-line" class="cut-line"></div>
			<div class="comment-num-div" id="commentNum">
				<div class="intro"><h4>评论</h4></div>
				<div class="comment-num" id="totalNum">共${total }条</div>
				<div class="clear"></div>
			</div>
			<div class="comments">
			<c:forEach items="${comments }" var="c">
				<div class="comment" id="comment${c.id }">
					<div class="comment-img">
						<img src='../uploadImages/growthLog${c.user_id }/${c.head_img}' onerror="src='${base}/images/default_head.png'" width='40px' height='40px;' style="border-radius:50%;" />
					</div>
					<div class="comment-content">
						<p class='comment-user'>${c.nickname }</p>
						<div class='comment-detail'>${c.content }</div>
						<div style="width:100%;">
							<div style="float:right;" class="${empty(c.like_id)?'':'likeComment' }"
								onclick="clickLike($(this),${c.id})">
								<input type="hidden" id="commentLike${c.id }" value="${c.like_id }"/>
								<img src='${empty(c.like_id)?"../images/before_like.png":"../images/like.png" }' width="20px;"  style='vertical-align:middle;'/>
								赞<i style="color:#848484">${c.like_num>0?c.like_num:"" }</i>
							</div>
							<div style="float:right;margin-right:10px;" onclick="openComment(2,${c.id},'${c.nickname }')">
								<img src='../images/add_reply.png' width="20px;"  style='vertical-align:middle;'alt="" /> 回复
							</div>
							<c:if test="${!empty(c.family) }">
							<div class="replyNum" 
								onclick="window.location='${base}/mine/diary!showFamilyComments.action?diaryComment.id=${c.id}&circle.id=${circle.id }'">
								查看全部${c.familyNum }条回复
							</div>
							</c:if>
						</div>
					</div>
					<div class="clear"></div>
				</div>
			</c:forEach>
			<c:if test="${total>20 }">
				<div class="show-more-comment" 
					onclick="window.location='${base}/mine/diary!moreComments.action?follow.id=${followId }&diary.id=${diary.id }'">
					点击查看更过评论
				</div>
			</c:if>
			</div>
			<div class="write-comment" id="writeComment" style="display:none">
				<input type="hidden" id="commentType" />
				<input type="hidden" id="commentId"/>
				<input type="hidden" id="faNickName"/>
				<div class="write-area" id="writeArea">
					<div onclick="closeComment()" class="close-textarea"><img src="${base }/images/close-btn.png" alt="" width="15px" />关闭</div>
					<textarea rows="" cols="" id="writeInput" style="font-size:14px;"></textarea>
					<div onclick="submitComment()" class="comment-submit">
						提&nbsp;&nbsp;&nbsp;&nbsp;交
					</div>
				</div>
			</div>
			<div class="comment-like-bar">
				<div id="toWriteComment" onclick="openComment(1,0,'')" 
					style="width:33%;text-align:center;float:left;" >
					<img src="${base }/images/add_reply.png" alt="" />
					<span style="font-size:18px;">评论</span>
				</div>
				<div class="${likeId>0?'diary-like likeDiary':'diary-like' }"  id="${likeId>0?'afterLike':'beforeLike' }"
					onclick="toLikeDiary($(this),${diary.id})">
					<input type="hidden" id="likeId" value="${likeId }"/>
					<img src="${likeId>0?'../images/like.png':'../images/before_like.png' }" alt="" />
					<span style="font-size:18px;">赞</span>
					<i>${likeCount }</i>
				</div>
			</div>
		</div>
	</body>
</html>
