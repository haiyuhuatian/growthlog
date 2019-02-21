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
		<link rel="stylesheet" href="${base }/phone-css/pullToRefresh_comment_list.css" /> 
		<script type="text/javascript" src="${base }/js/jquery.js"></script> 
		<script type="text/javascript" src="${base }/js/mobile-comment.js"></script> 
		<script type="text/javascript"  src="${base }/phone-js/xback.js"></script>
		<script type="text/javascript" src="${base }/phone-js/iscroll.js"></script>
		<script type="text/javascript" src="${base }/phone-js/pullToRefresh.js"></script>
		<script type="text/javascript" >
			 	XBack.listen(function(){
				  window.location = "${base}/home/diary!moreComments.action?lastCommentIdSign="
						  +$("#fatherId").val()
						  +"&fromSon=yes&diary.id="
						  +$("#diaryId").val()
						  +"&model="
						  +$("#model").val();
				});
		</script>
	</head>
	<body style="background-color:#F0EFEE;">
		<input type="hidden" id="diaryCommentId" value="${diaryComment.id }"/>
		<input type="hidden" id="model" value="${model }"/>
		<input type="hidden" id="diaryId" value="${diaryId }"/>
		<input type="hidden" id="userId" value="${userId }"/>
		<input type="hidden" id="lastCommentIdSign" value="${lastCommentIdSign }"/>
		<input type="hidden" id="fatherId" value="${diaryComment.id }"/>
		<div id="wrapper" class="comment-containter">
			<ul >
				<c:forEach items="${family }" var="c">
					<li >
						<div class="head-div">
							<img src="${base }/uploadImages/growthLog${c.comment_user_id }/${c.head_img}"  width="50px;" />
						</div>
						<div class="comment-div">
							<p class='comment-user-nickname'>${c.nickname }</p>
							<p class="comment-p">${c.content }</p>
							<p class="like-p-son" style="${empty(c.like_id)?'':'color:red;'}" onclick="clickLike2($(this),${c.id})">
								<input type="hidden" id="commentLike${c.id }" value="${c.like_id }"/>
								<img src='${empty(c.like_id)?"../images/before_like.png":"../images/like.png" }'
									width="20px;"  />
								赞<i style="color:#848484">${c.like_num}</i>
							</p>
						</div>
						<div class="clear"></div>
					</li>
				</c:forEach>
			</ul>
		</div>
		<script type="text/javascript">
				var loadDatas = "";
				var diaryCommentId = $("#diaryCommentId").val();
				function loadData(){
					var	lastCommentIdSign = $("#lastCommentIdSign").val();
					 $.ajax({ 
						 type: "post", 
						 url: "diary!loadSonComments.action", 
						 async: false,
						 dataType: "json", 
						 data:{"diaryComment.id":diaryCommentId,"lastCommentIdSign":lastCommentIdSign},
						 success: function (data) {
							 if(data != "0"){
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
					var model = document.getElementById("model").value;
					var diaryCommentId = document.getElementById("diaryCommentId").value;
					window.location = '${base}/home/diary!showSonComments.action?&diaryComment.id='+diaryCommentId+'&model='+model;
				}
				
				function Load() {
					loadData();
					setTimeout(function () {
						var el, li, i;
						el = document.querySelector("#wrapper ul");
						if(loadDatas != ''){
							$.each(loadDatas,function(i,item){
								li = document.createElement('li');
								li.setAttribute('id','comment'+item.id);
								if(item.like_id == ""){
									li.innerHTML = '<div class="head-div">'
										 + '<img src="${base }/uploadImages/growthLog'+item.comment_user_id+'/'+item.head_img+'"  width="50px;" />'
									     + '</div>'
										 + '<div class="comment-div">'
									     + '<p class="comment-user-nickname">'+item.nickname+'</p>'
										 + '<p class="comment-p">'+item.content+'</p>'
										 + '<p class="like-p-son" onclick="clickLike2($(this),'+item.id+')">'
										 + '<input type="hidden" id="commentLike'+item.id+'" value="'+item.like_id+'"/>'
										 + '<img src="${base }/images/before_like.png" width="20px;"  />'
										 + '赞<i style="color:#848484">'+item.like_num+'</i>'
										 + '</p>'
										 + '</div>'
										 + '<div class="clear"></div>';
								}else {
									li.innerHTML = '<div class="head-div">'
										 + '<img src="${base }/uploadImages/growthLog'+item.user_id+'/'+item.head_img+'"  width="50px;" />'
									     + '</div>'
										 + '<div class="comment-div">'
									     + '<p class="comment-user-nickname">'+item.nickname+'</p>'
										 + '<p class="comment-p">'+item.content+'</p>'
										 + '<p class="like-p-son" style="color:red;" onclick="clickLike2($(this),'+item.id+')">'
										 + '<input type="hidden" id="commentLike'+item.id+'" value="'+item.like_id+'"/>'
										 + '<img src="${base }/images/like.png" width="20px;"  />'
										 + '赞<i style="color:#848484">'+item.like_num+'</i>'
										 + '</p>'
										 + '</div>'
										 + '<div class="clear"></div>';
								}
								
								if(i == loadDatas.length-1){
									var lastCommentIdSignNode = document.getElementById('lastCommentIdSign');
									lastCommentIdSignNode.value = item.id;
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
