
function toLikeDiary(item,diaryId) {
			var userId = $("#userId").val();
			var likeId = $("#likeId").val();
			if(userId == ""){
				alert("登录后才能点赞哦！请点击菜单栏的【我的】进入【我的账号】进行登录或者注册");
			}else {
				if(likeId == 0) {
					if(item.attr("id").indexOf("before")>=0) {
						item.addClass("likeDiary");
						item.find("img").attr("src","../images/like.png");
						item.attr("id","afterLike");
						if(item.find("i").text() == "") {
							item.find("i").text(1);
						}else {
							item.find("i").text(parseInt(item.find("i").text())+1);
						}
						$.post("diary!likeDiary.action",{"diary.id":diaryId},function(data){
							if(data>0){
								$("#likeId").val(data);
							}
						});
					}
				}else {
					$.post("diary!cancleLikeDiary.action",{"likeId":likeId},function(data){
						if(data > 0){
							item.removeClass("likeDiary");
							item.find("img").attr("src","../images/before_like.png");
							item.attr("id","beforeLike");
							item.find("i").text(parseInt(item.find("i").text())-1);
							$("#likeId").val(0);
						}
					});
				}
			}
			
}
function cancleFollow(followId){
	if(confirm("您确定要取消关注吗？")){
		if(followId != ""){
			$.post("${base}/mine/follow!cancleFollow.action",{"follow.id":followId},function(data){
				if(data == "1"){
					$("#follow").html("关&nbsp;&nbsp;&nbsp;注");
					var diaryId = $("#diaryId").val();
					$("#follow").attr("onclick","follow("+diaryId+")");
				}else {
					alert("出错了，请稍后重试");
				}
			});
		}
	}
}
function follow(diaryId){
	var userId = $("#userId").val();
	if(userId == ""){
		alert("登录后才能关注哦！请点击菜单栏的【我的】进入【我的账号】进行登录或者注册");
	}else {
		if(diaryId != ""){
			$.post("/mine/follow!follow.action",{"diary.id":diaryId},function(data){
				if(data == "self"){
					alert("您不需要关注自己哦！");
				}else if(data == "error") {
					alert("出错了，请稍后重试");
				}else {
					$("#follow").html("取消关注");
					$("#follow").attr("onclick","cancleFollow("+data+")");
				}
			});
		}
	}
	
}
function diaryLike(item,diaryId,likeId) {
	var userId = $("#userId").val();
	if(userId == ""){
		alert("登录后才能点赞哦！请点击菜单栏的【我的】进入【我的账号】进行登录或者注册");
	}else {
		if(likeId == 0) {
			if(item.attr("id").indexOf("before")>=0) {
				item.addClass("likeDiary");
				item.find("img").attr("src","../images/like.png");
				item.attr("id","afterLike");
				if(item.find("i").text() == "") {
					item.find("i").text(1);
				}else {
					item.find("i").text(parseInt(item.find("i").text())+1);
				}
				$.post("diary!likeDiary.action",{"diary.id":diaryId},function(data){});
			}
		}
	}
	
}

function clickLike(item,commentId) {
	var likeId = $("#commentLike"+commentId).val();
	var userId = $("#userId").val();
	if(userId == ""){
		alert("登录后才能点赞哦！请点击菜单栏的【我的】进入【我的账号】进行登录或者注册");
	}else {
		if(likeId == "") {
			item.find("img").attr("src","../images/like.png");
			item.attr("class","likeComment");
			item.css("color","red");
			if(item.find("i").text() != "") {
				item.find("i").text(parseInt(item.find("i").text())+1);
			}else {
				item.find("i").text(1);
			}
			$.post("diary!likeComment.action",{"diaryComment.id":commentId},function(data) {
				if(data > 0){
					$("#commentLike"+commentId).val(data);
				}
			});
	}else {
			item.find("img").attr("src","../images/before_like.png");
			item.find("i").text(parseInt(item.find("i").text())-1);
			item.css("color","gray");
			$.post("diary!cancleCommentLike.action",{"likeId":likeId},function(data) {
				if(data > 0){
					$("#commentLike"+commentId).val("");
				}
			});
		}
	}
}
function clickLike2(item,commentId) {
	var likeId = $("#commentLike"+commentId).val();
	var userId = $("#userId").val();
	if(userId == ""){
		alert("登录后才能点赞哦！请点击菜单栏的【我的】进入【我的账号】进行登录或者注册");
	}else {
		if(likeId == "" ) {
			item.find("img").attr("src","../images/like.png");
			item.css("color","red");
			if(item.find("i").text() != "") {
				item.find("i").text(parseInt(item.find("i").text())+1);
			}else {
				item.find("i").text(1);
			}
			$.post("diary!likeComment.action",{"diaryComment.id":commentId},function(data) {
				if(data > 0){
					$("#commentLike"+commentId).val(data);
				}
			});
	}else {
			item.find("img").attr("src","../images/before_like.png");
			item.find("i").text(parseInt(item.find("i").text())-1);
			item.css("color","gray");
			$.post("diary!cancleCommentLike.action",{"likeId":likeId},function(data) {
				if(data > 0){
					$("#commentLike"+commentId).val("");
				}
			});
		}
	}
}
function openComment(commentType,commentId,nickName) {//commentType等于1表示评论的是日志，等于2表示评论的是别人的评论
	var userId = $("#userId").val();
	if(userId == ""){
		alert("登录后才能评论哦！请点击菜单栏的【我的】进入【我的账号】进行登录或者注册");
	}else {
		if(commentType == 2) {
			$("#commentType").val(2);
			$("#commentId").val(commentId);
			$("#faNickName").val(nickName);
		}else {
			$("#commentType").val("");
		}
		$('#writeComment').show();
		$("#writeInput").focus();
	}
}
function closeComment(){
	$('#writeComment').hide();
}
function submitComment(){
	if($("#writeInput").val() == "") {
		alert("请输入评论内容");
	}else {
		var userId = $("#userId").val();
		var headImg = $("#headImg").val();
		var comment = $("#writeInput").val();
		var nickName = $("#nickname").val();
		if($("#commentType").val() == "") {
			$.post("diary!saveComment.action",{"comment":comment,"diary.id":$("#diaryId").val()},function(data){
				if(data>0) {
					$("#commentNum").after(
							"<div class='comment' id='comment"+data+"'><div class='comment-img'><img src='../uploadImages/growthLog"
						   +userId
						   +"/"+headImg+"' width='50px' height='50px;' style='border-radius:50%;' /></div>" 
						   +"<div class='comment-content'>"
						   +"<p class='comment-user'>"
						   +nickName
						   +"</p>"
						   +"<div class='comment-detail'>"+comment+"</div>"
						   +"<div style='width:100%;'>" 
						   +"<div style='float:right;' onclick='clickLike($(this),"+data+")'>"
						   +"<input type='hidden' id='commentLike"+data+"' value='' />"
						   +"<img src='../images/before_like.png' width='20px;'  style='vertical-align:middle;'/>"
						   +"赞<i style='color:#848484'></i>"
						   +"</div>"
						   +"<div style='float:right;margin-right:10px;' onclick='openComment(2,"+data+",\""+nickName+"\")'>"
						   +"<img src='../images/add_reply.png' width='20px;'  style='vertical-align:middle;'/> 回复</div>"
						   +"</div>"
						   +"<div class='clear'></div></div>"
							)
							$("#writeInput").val("");
							closeComment();
							var total = $("#total").val();
								total = parseInt(total);
							    total = total + 1;
							    $("#total").val(total);
							    $("#totalNum").text("共"+total+"条");
				}
			});
		}else {
			var commentId = $("#commentId").val();
			var faNickName = $("#faNickName").val();
			$.post("diary!saveComentSon.action",{"comment":comment,"diaryComment.id":commentId},function(data){
				var h = "<div class='comment'><div class='comment-img'><img src='../uploadImages/growthLog"
					   +userId
					   +"/"+headImg+"' width='50px' height='50px;' style='border-radius:50%;' /></div>" 
					   +"<div class='comment-content'>"
					   +"<p class='comment-user'>"
					   +nickName
					   +"</p>回复"
					   +"<p class='comment-user'>"
					   +faNickName
					   +"</p>"
					   +"<div class='comment-detail'>"+comment+"</div>"
					   +"<div style='width:100%;'>" 
					   +"<div style='float:right;' onclick='clickLike($(this),"+data+")'>"
					   +"<input type='hidden' id='commentLike"+data+"' value='' />"
					   +"<img src='../images/before_like.png' width='20px;'  style='vertical-align:middle;'/>"
					   +"赞<i style='color:#848484'></i>"
					   +"</div>"
					   +"</div>"
					   +"</div><div class='clear'></div></div>";
				$("#comment"+commentId).after(h);
				$("#writeInput").val("");
				closeComment();
				$("#commentId").val("");
				$("#nickName").val("");
				$("#commentType").val("");
			});
		}
	}
}
function subComment(){
	if($("#writeInput").val() == "") {
		alert("请输入评论内容");
	}else {
		var userId = $("#userId").val();
		var headImg = $("#headImg").val();
		var comment = $("#writeInput").val();
		var nickName = $("#nickname").val();
		var commentId = $("#commentId").val();
		var faNickName = $("#faNickName").val();
		$.post("diary!saveComentSon.action",{"comment":comment,"diaryComment.id":commentId},function(data){
			$("#comment"+commentId).after(
					 '<li id="comment'+data+'">'
					 +'<div class="head-div">'
					 + '<img src="../uploadImages/growthLog'+userId+'/'+headImg+'"  width="50px;" />'
				     + '</div>'
					 + '<div class="comment-div"><div style="text-align:left;width:100%;">'
				     + '<p class="comment-user">'+nickName+'</p>回复'
				     + '<p class="comment-user">'+faNickName+'</p></div>'
					 + '<p class="comment-p">'+comment+'</p>'
					 + '<p class="like-p" style="width:100%;text-align:right;" onclick="clickLike($(this),'+data+')">'
					 +'<input type="hidden" id="commentLike'+data+'" value="" />'
					 + '<img src="../images/before_like.png" width="20px;"  />'
					 + '赞<i style="color:#848484">0</i>'
					 + '</p>'
					 + '</div>'
					 + '<div class="clear"></div>'
					 +'</li>'
					);
			$("#writeInput").val("");
			closeComment();
			$("#commentId").val("");
			$("#nickName").val("");
			$("#commentType").val("");
		});
	}
}
