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
	                         'previewImage'] // 必填，需要使用的JS接口列表
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
			    });
			
			 function deleteDiary(diaryId) {
					if(confirm('您确定要删除这篇日志吗？')){
						window.location='${base}/mine/diary!delete.action?diary.id='+diaryId;
					}
				}
			 XBack.listen(function(){
				  window.location = "${base}/mine/diary!list.action?diaryYear="+$("#diaryYear").val()+"&diaryMonth="+$("#diaryMonth").val();
				});
		</script>
	</head>
	<body style="background-color:#F0EFEE;">
		<input type="hidden" value="${user.id }" id="userId" />
		<input type="hidden" value="${user.nickname }" id="nickname"/>
		<input type="hidden" id="diaryYear" value="${diaryYear }"/>
		<input type="hidden" id="diaryMonth" value="${diaryMonth }"/>
		<div class="img-containter">
			<c:if test="${!empty(imgs) }">
				<c:forEach items="${imgs }" var="i">
					<figure >
						<img  src="${base }/uploadImages/growthLog${diary.user_id }/diary${diary.id}/${i}"   />
					</figure>
				</c:forEach>
				<div class="clear"></div>
			</c:if>
		</div>
		<div class="content" style="margin-top:10px;">
			<input type="hidden" value="${diary.id }" id="diaryId"/>
			<input type="hidden" value="${user.head_img }" id="headImg"/>
			<div class="titleDiv">${diary.title }</div>
			<div class="diary-info">
				<span><fmt:formatDate value="${diary.create_date }" pattern="yyyy-MM-dd"></fmt:formatDate></span>
							/
				<span>${diary.weekday }</span>
							/
				<span>${diary.weather }</span>
			</div>
			<div style="line-height:28px;width:95%;text-align:justify;margin:5px auto;">
							${diary.content }
			</div>
			<div style="width:97%;text-align:right;color:red;" onclick="deleteDiary(${diary.id})">删除</div>
			<div id="cut-line" style="width:100%;height:1px;background-color:#8f8888;margin-top:10px;"></div>
			<div style="width:100%;margin-top:10px;" id="commentNum">
				<div style="font-size:16px;font-weight:bold;margin-left:10%;float:left;"><h4>评论</h4></div>
				<div style="font-size:16px;float:right;color:red;margin-right:10%;">共${total }条</div>
				<div class="clear"></div>
			</div>
			<div class="comments">
			<c:forEach items="${comments }" var="c">
				<div class="comment" id="comment${c.id }">
					<div class="comment-img">
						<img src='../uploadImages/growthLog${c.userId }/head.png' onerror="src='${base}/images/default_head.png'" width='40px' height='40px;' style="border-radius:50%;" />
					</div>
					<div class="comment-content">
						<p class='comment-user'>${c.nickname }</p>
						<div class='comment-detail'>${c.content }</div>
						<div style="width:100%;">
							<div style="float:right;" class="${empty(c.likeId)?'':'likeComment' }"
								onclick="clickLike($(this),${c.id},${empty(c.likeId)?0:1 })">
								<img src='${empty(c.likeId)?"../images/before_like.png":"../images/like.png" }' width="20px;"  style='vertical-align:middle;'/>
								赞<i style="color:#848484">${c.likeNum>0?c.likeNum:"" }</i>
							</div>
							<div style="float:right;margin-right:10px;" onclick="openComment(2,${c.id},'${c.nickname }')">
								<img src='../images/add_reply.png' width="20px;"  style='vertical-align:middle;'alt="" /> 回复
							</div>
							<c:if test="${!empty(c.family) }">
							<div class="replyNum" onclick="window.location='${base}/mine/diary!showFamilyComments.action?diaryComment.id=${c.id}'">
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
					onclick="window.location='${base}/mine/diary!moreComments.action?diary.id=${diary.id }'">
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
					<textarea rows="" cols="" id="writeInput"></textarea>
					<div onclick="submitComment()" style="background-color:#037f88;width:50%;text-align:center;color:#fff;font-size:16px;
							font-weight:bold;margin:10px auto;height:30px;line-height:30px;border-radius:5px;">
						提&nbsp;&nbsp;&nbsp;&nbsp;交
					</div>
				</div>
			</div>
			<%-- <div class="comment-like-bar">
				<div id="toWriteComment" onclick="openComment(1,0,'')" style="width:40%;text-align:center;float:left;margin-left:10%;" >
					<img src="${base }/images/add_reply.png" alt="" />
					<span style="font-size:18px;">评论</span>
				</div>
				<div class="${likeId>0?'diary-like likeDiary':'diary-like' }"  id="${likeId>0?'afterLike':'beforeLike' }"
					onclick="diaryLike($(this),${diary.id},${likeId})">
					<img src="${likeId>0?'../images/like.png':'../images/before_like.png' }" alt="" />
					<span style="font-size:18px;">赞</span>
					<i>${likeCount }</i>
				</div>
			</div> --%>
		</div>
		<script type="text/javascript">
			var initPhotoSwipeFromDOM = function(gallerySelector) {
			
			    // parse slide data (url, title, size ...) from DOM elements 
			    // (children of gallerySelector)
			    var parseThumbnailElements = function(el) {
			        var thumbElements = el.childNodes,
			            numNodes = thumbElements.length,
			            items = [],
			            figureEl,
			            linkEl,
			            size,
			            item;
			
			        for(var i = 0; i < numNodes; i++) {
			
			            figureEl = thumbElements[i]; // <figure> element
			
			            // include only element nodes 
			            if(figureEl.nodeType !== 1) {
			                continue;
			            }
			
			            linkEl = figureEl.children[0]; // <a> element
			
			            size = linkEl.getAttribute('data-size').split('x');
			
			            // create slide object
			            item = {
			                src: linkEl.getAttribute('href'),
			                w: parseInt(size[0], 10),
			                h: parseInt(size[1], 10)
			            };
			
			
			
			            if(figureEl.children.length > 1) {
			                // <figcaption> content
			                item.title = figureEl.children[1].innerHTML; 
			            }
			
			            if(linkEl.children.length > 0) {
			                // <img> thumbnail element, retrieving thumbnail url
			                item.msrc = linkEl.children[0].getAttribute('src');
			            } 
			
			            item.el = figureEl; // save link to element for getThumbBoundsFn
			            items.push(item);
			        }
			
			        return items;
			    };
			
			    // find nearest parent element
			    var closest = function closest(el, fn) {
			        return el && ( fn(el) ? el : closest(el.parentNode, fn) );
			    };
			
			    // triggers when user clicks on thumbnail
			    var onThumbnailsClick = function(e) {
			        e = e || window.event;
			        e.preventDefault ? e.preventDefault() : e.returnValue = false;
			
			        var eTarget = e.target || e.srcElement;
			
			        // find root element of slide
			        var clickedListItem = closest(eTarget, function(el) {
			            return (el.tagName && el.tagName.toUpperCase() === 'FIGURE');
			        });
			
			        if(!clickedListItem) {
			            return;
			        }
			
			        // find index of clicked item by looping through all child nodes
			        // alternatively, you may define index via data- attribute
			        var clickedGallery = clickedListItem.parentNode,
			            childNodes = clickedListItem.parentNode.childNodes,
			            numChildNodes = childNodes.length,
			            nodeIndex = 0,
			            index;
			
			        for (var i = 0; i < numChildNodes; i++) {
			            if(childNodes[i].nodeType !== 1) { 
			                continue; 
			            }
			
			            if(childNodes[i] === clickedListItem) {
			                index = nodeIndex;
			                break;
			            }
			            nodeIndex++;
			        }
			
			
			
			        if(index >= 0) {
			            // open PhotoSwipe if valid index found
			            openPhotoSwipe( index, clickedGallery );
			        }
			        return false;
			    };
			
			    // parse picture index and gallery index from URL (#&pid=1&gid=2)
			    var photoswipeParseHash = function() {
			        var hash = window.location.hash.substring(1),
			        params = {};
			
			        if(hash.length < 5) {
			            return params;
			        }
			
			        var vars = hash.split('&');
			        for (var i = 0; i < vars.length; i++) {
			            if(!vars[i]) {
			                continue;
			            }
			            var pair = vars[i].split('=');  
			            if(pair.length < 2) {
			                continue;
			            }           
			            params[pair[0]] = pair[1];
			        }
			
			        if(params.gid) {
			            params.gid = parseInt(params.gid, 10);
			        }
			
			        return params;
			    };
			
			    var openPhotoSwipe = function(index, galleryElement, disableAnimation, fromURL) {
			        var pswpElement = document.querySelectorAll('.pswp')[0],
			            gallery,
			            options,
			            items;
			
			        items = parseThumbnailElements(galleryElement);
			
			        // define options (if needed)
			        options = {
			
			            // define gallery index (for URL)
			            galleryUID: galleryElement.getAttribute('data-pswp-uid'),
			
			            getThumbBoundsFn: function(index) {
			                // See Options -> getThumbBoundsFn section of documentation for more info
			                var thumbnail = items[index].el.getElementsByTagName('img')[0], // find thumbnail
			                    pageYScroll = window.pageYOffset || document.documentElement.scrollTop,
			                    rect = thumbnail.getBoundingClientRect(); 
			
			                return {x:rect.left, y:rect.top + pageYScroll, w:rect.width};
			            }
			
			        };
			
			        // PhotoSwipe opened from URL
			        if(fromURL) {
			            if(options.galleryPIDs) {
			                // parse real index when custom PIDs are used 
			                // http://photoswipe.com/documentation/faq.html#custom-pid-in-url
			                for(var j = 0; j < items.length; j++) {
			                    if(items[j].pid == index) {
			                        options.index = j;
			                        break;
			                    }
			                }
			            } else {
			                // in URL indexes start from 1
			                options.index = parseInt(index, 10) - 1;
			            }
			        } else {
			            options.index = parseInt(index, 10);
			        }
			
			        // exit if index not found
			        if( isNaN(options.index) ) {
			            return;
			        }
			
			        if(disableAnimation) {
			            options.showAnimationDuration = 0;
			        }
			
			        // Pass data to PhotoSwipe and initialize it
			        gallery = new PhotoSwipe( pswpElement, PhotoSwipeUI_Default, items, options);
			        gallery.init();
			    };
			
			    // loop through all gallery elements and bind events
			    var galleryElements = document.querySelectorAll( gallerySelector );
			
			    for(var i = 0, l = galleryElements.length; i < l; i++) {
			        galleryElements[i].setAttribute('data-pswp-uid', i+1);
			        galleryElements[i].onclick = onThumbnailsClick;
			    }
			
			    // Parse URL and open gallery if it contains #&pid=3&gid=1
			    var hashData = photoswipeParseHash();
			    if(hashData.pid && hashData.gid) {
			        openPhotoSwipe( hashData.pid ,  galleryElements[ hashData.gid - 1 ], true, true );
			    }
			};
			
			// execute above function
			initPhotoSwipeFromDOM('.my-gallery');
		</script>
	</body>
</html>
