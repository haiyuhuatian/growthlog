function overLike(item) {
			item.addClass("like");
			item.find("img").attr("src","../images/like.png");
		}
		function outLike(item) {
			if(item.attr("id").indexOf("un")>=0) {
				item.removeClass("like");
				item.find("img").attr("src","../images/before_like.png");
				like = false;
			}
		}
		function clickLike(item,commentId) {
			if(item.attr("id").indexOf("un")>=0) {
			item.find("img").attr("src","../images/like.png");
			item.attr("class","comment-operate_like");
			item.attr("id",item.attr("id").substring(2));
			if(item.find("i").text() != "") {
				item.find("i").text(parseInt(item.find("i").text())+1);
			}else {
				item.find("i").text(1);
			}
			
			$.post("diary!like.action",{"diaryComment.id":commentId},function(data) {
			});
			}
		}
		function overReply(item) {
			item.addClass("like");
			item.find("img").attr("src","../images/stop_reply.png");
		}
		function outReply(item) {
			if(item.attr("id").indexOf("be")>=0) {
				item.removeClass("like");
				item.find("img").attr("src","../images/add_reply.png");
			}
		}
		function clickReply(item,pid,dadId,myName,dadName){
			item.addClass("like");
			item.text("收起");
			item.find("img").attr("src","../images/stop_reply.png");
			var myId = item.attr("id").substring(2);
			item.attr("id",myId);
			var clearId = item.attr("iod");
			var now=new Date();    
			var number = now.getTime(); 
			var replyContentId = "replyContent"+number;
			var fId = "father"+number+Math.ceil(Math.random()*100);
			$("#"+clearId).after("<div class='reply-box' id='"+fId+"'><div class='repy-box-block'><textarea id='"
								+replyContentId
								+"' class='reply-box-textarea' spellcheck='false'></textarea></div>"
								+"<div class='reply-box-btn' rid='"+myId+"' fid='"+fId+"' cid='"+clearId+"' iod='"
								+replyContentId
								+"' onclick='subCommentReply($(this),"
								+pid
								+",\""+dadId+"\",\""+myName+"\",\""+dadName+"\")'>回复</div></div>");
			item.attr("onclick","closeReply($(this),'"+fId+"',"+pid+",\""+dadId+"\",\""+myName+"\",\""+dadName+"\")");
			
		}
		function closeReply(item,boxId,pid,dadId,myName,dadName) {
			$("#"+boxId).remove();
			item.removeClass("like");
			item.attr("id","be"+item.attr("id"));
			item.html("<img src='../images/add_reply.png'  style='vertical-align:middle;'  /> 回复");
			item.attr("onclick","clickReply($(this),"+pid+",\""+dadId+"\",\""+myName+"\",\""+dadName+"\")")
		}
		function submitComment(){
			var myComment = $("#myCommentVal").val();
			if(myComment == "") {
				alert("您还没有填写评论内容哟");
				return ;
			}
			
			$("#submitBtn").attr("disabled","disabled");
			var diaryId = $("#diaryId").val();
			var userId = $("#userId").val();
			var headImg = $("#headImg").val();
			var userName = $("#userName").val();
			var now=new Date();    
			var number = now.getTime(); 
			var likeId = "unLikeId"+number;
			var breplyId = "be"+number;
			var clearId = "clear"+number;
			$.post("diary!saveComment.action",{"diary.id":diaryId,"comment":myComment},
					function(data){
					if(data > 0) {
						$("#submitBtn").attr("disabled",false);
						$("#myCommentVal").val("");
						$("#writeComment").after("<div  class='comment'"
								+"><div class='comment-img'><img src='../uploadImages/growthLog"
								+userId
								+"/"+headImg+"' width='100%' height='100%'  /> </div>"
								+"<div id='grand"+data+"'>  <p class='comment-user'><span class='comment-username'>"+userName
								+"</span></p><div class='comment-content'>"
								+myComment
								+"</div><div class='comment-operate'><div  class='comment-operate-before_like'"
								+" id='"+likeId
								+"' onmouseover='overLike($(this))' onmouseout='outLike($(this))' onclick='clickLike($(this),"
								+data
								+")'>"
								+"<img src='../images/before_like.png'  style='vertical-align:middle;'  />点赞 "
								+"<i style='color:#848484'></i></div><div class='comment-reply' onmouseover='overReply($(this))' onmouseout='outReply($(this))' id='"
								+breplyId
								+"' onclick='clickReply($(this),"+data+",\"grand"+data+"\",\""+userName+"\",\""+userName+"\")' iod='"+clearId+"'><img src='../images/add_reply.png'  style='vertical-align:middle;'  />"
								+" 回复</div></div><div class='clear' id='"+clearId+"'></div>"
							+"</div></div>");
					}
					if(data == 0) {
						alert("参数错误，请重试！");
						$("#submitBtn").attr("disabled",true);
					}
				});
		}
		function subCommentReply(item,pid,dadId,myName,dadName) {
			var contentId = item.attr("iod");
			var content = $("#"+contentId).text();
			$.post("diary!saveComentSon.action",{"diaryComment.id":pid,"comment":content},function(data){
				if(data > 0) {
					var fatherId = item.attr("fid");
					var clearId = item.attr("cid");
					var toReplyId = item.attr("rid");
					var now=new Date();    
					var number = now.getTime(); 
					var rlikeId = "un"+number;
					var v = "elear"+number;
					var self = "self"+number;
					$("#"+fatherId).remove();
					$("#"+clearId).after("<div class='reply' id='son"+data+"'><p class='reply-content'><span class='reply-user'>" 
							+myName
							+"<i style='font-size:500;color:#a3a3a3;'> 回复</i> "
							+dadName+"：</span>"
							+content
							+"</p><div class='reply-operate-like' id='"
							+rlikeId
							+"' onmouseover='replyLikeover($(this))' onmouseout='replyLikeout($(this))' onclick='replyLikeClick($(this),"
							+data
							+")'>"
							+"<img  src='../images/fire1.png' style='vertical-align:bottom;'/> "
							+"赞 <i style='color:#848484'></i></div><div class='reply-reply' id='"
							+v
							+"' onmouseover='replyMouseover($(this))' onmouseout='replyMouseout($(this))' iod='"
							+self
							+"' onclick='openReply($(this),"
							+data
							+",\"son"+data+"\",\""+myName+"\",\""+myName+"\")'>"
							+"<img  src='../images/reply1.png' style='vertical-align:bottom;'/> 回复</div>"
							+"<div class='clear' id='"
							+self
							+"'></div></div>")
					$("#"+toReplyId).removeClass("like");
					$("#"+toReplyId).html("<img src='../images/add_reply.png'  style='vertical-align:middle;'/>  回复");
				}
			});
			
		}
		function replyLikeover(item) {
			item.addClass("reply-like");
			item.find("img").attr("src","../images/fire2.png");
		}
		function replyLikeout(item) {
			if(item.attr("id").indexOf("un")>=0) {
				item.removeClass("reply-like");
				item.find("img").attr("src","../images/fire1.png");
			}
		}
		function replyLikeClick(item,replyId) {
			if(item.attr("id").indexOf("un")>=0) {
			item.find("img").attr("src","../images/fire2.png");
			item.addClass("reply-like");
			item.attr("id","af"+item.attr("id").substring(2));
			if(item.find("i").text() != "") {
				item.find("i").text(parseInt(item.find("i").text())+1);
			}else {
				item.find("i").text(1);
			}
			$.post("diary!like.action",{"diaryComment.id":replyId},function(data) {
			});
			}
		}
		function replyMouseover(item) {
			item.addClass("reply-like");
			item.find("img").attr("src","../images/reply2.png");
		}
		function replyMouseout(item) {
			if(item.attr("id").indexOf("el")>=0) {
				item.removeClass("reply-like");
				item.find("img").attr("src","../images/reply1.png");
			}
		}
		function openReply(item,pid,dadId,myName,dadName) {
			var mId = item.attr("id").substring(2);
			item.attr("id",mId);
			item.addClass("reply-like");
			var divId = item.attr("iod");
			var now=new Date();    
			var number = now.getTime(); 
			var replyContentId = "replyContent"+number;
			var faId = "father"+number;
			$("#"+divId).after(
					"<div class='reply-box' id='"+faId+"'><div class='repy-box-block'><textarea id='"
					+replyContentId
					+"' class='reply-box-textarea' spellcheck='false'></textarea></div>"
					+"<div class='reply-box-btn' rid='"+mId+"' fid='"+faId+"' cid='"+divId+"' iod='"
					+replyContentId
					+"' onclick='subReply($(this),"
					+pid+",\""+dadId+"\",\""+myName+"\",\""+dadName
					+"\")'>回复</div></div>"
					);
			item.html("收起");
			item.attr("onclick","closeReplyBox($(this),'"+faId+"',"+pid+",\""+dadId+"\",\""+myName+"\",\""+dadName+"\")");
			
		}
		function closeReplyBox(item,boxId,pid,dadId,myName,dadName) {
			$("#"+boxId).remove();
			item.removeClass("reply-like");
			item.attr("id","el"+item.attr("id"));
			item.html("<img  src='../images/reply1.png' style='vertical-align:bottom;'/> 回复");
			item.attr("onclick","openReply($(this),"+pid+",\""+dadId+"\",\""+myName+"\",\""+dadName+"\")");
		}
		function subReply(item,pid,dadId,myName,dadName) {
			var contentId = item.attr("iod");
			var content = $("#"+contentId).text();
			var now=new Date();    
			var number = now.getTime(); 
			var sId = ""+number;
			$.post("diary!saveComentSon.action",{"diaryComment.id":pid,"comment":content},function(data){
				if(data > 0) {
					$("#"+dadId).after("<div class='reply'  id='son"+data+"'>"
									+"<p class='reply-content'><span class='reply-user'>"
									+myName+"<i style='font-size:500;color:#a3a3a3;'> 回复 </i>"
									+dadName+"：</span>"+content+"</p>"
									+"<div class='reply-operate-like' id='un"+data
									+"' onmouseover='replyLikeover($(this))' onmouseout='replyLikeout($(this))' "
									+"onclick='replyLikeClick($(this),"+data+")'>"
									+" <img  src='../images/fire1.png' style='vertical-align:bottom;'/> 赞"
									+"</div><div class='reply-reply' id='elear"+data
									+"' onmouseover='replyMouseover($(this))' onmouseout='replyMouseout($(this))' iod='self"+data
									+"' onclick='openReply($(this),"+data+",\"son"+data+"\",\""+myName+"\",\""+myName+"\")'>"
									+"<img  src='../images/reply1.png' style='vertical-align:bottom;'/> 回复"
									+"</div><div class='clear' id='self"+data+"'></div>"
							);
					$("#"+item.attr("fid")).remove();
					var openId = item.attr("rid");
					$("#"+openId).attr("id","el"+openId)
					.html("<img  src='../images/reply1.png' style='vertical-align:bottom;'/> 回复")
					.attr("onclick","openReply($(this),"+data+",\""+dadId+"\",\""+myName+"\",\""+dadName+"\")")
					.removeClass("reply-like");
				}
			});
		}
		function showFamily(item,id,familyNum) {
			$("#family"+id).removeAttr("style");
			item.html("收起全部"+familyNum+"条回复");
			item.attr("onclick","closeFamily($(this),"+id+","+familyNum+")");
		}
		function closeFamily(item,id,familyNum) {
			$("#family"+id).css("display","none");
			item.html("查看全部"+familyNum+"条回复");
			item.attr("onclick","showFamily($(this),"+id+","+familyNum+")");
		}
		function diaryLikeOver(item) {
			item.addClass("likeDiary");
			item.find("img").attr("src","../images/like-it.png");
		}
		function diaryLikeOut(item) {
			if(item.attr("id").indexOf("before")>=0) {
				item.removeClass("likeDiary");
				item.find("img").attr("src","../images/like-it-before.png");
			}
		}
		function diaryLike(item,diaryId) {
			if(item.attr("id").indexOf("before")>=0) {
				item.addClass("likeDiary");
				item.find("img").attr("src","../images/like-it.png");
				item.attr("id","afterLike");
				if(item.find("i").text() == "") {
					item.find("i").text(1);
				}else {
					item.find("i").text(parseInt(item.find("i").text())+1);
				}
				$.post("diary!likeDiary.action",{"diary.id":diaryId},function(data){});
			}
		}