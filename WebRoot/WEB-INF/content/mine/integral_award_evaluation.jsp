<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/content/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>填写评价</title>
		<meta name="keywords" value="成长日志" />
		<meta name="description" value="首个记录成长日志的网站" />
		<meta name="author" value="成长日志" />
		<meta name="viewport" content="width=device-width,height=device-height,inital-scale=1.0,maximum-scale=1.0,user-scalable=no;">
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="apple-mobile-web-app-status-bar-style" content="black">
		<meta name="format-detection" content="telephone=no">
		<link href="${base }/phone-css/phone.css" rel='stylesheet' type='text/css' />
		<script src="${base }/js/jquery.js"></script>
		<script type="text/javascript"  src="${base }/phone-js/xback.js"></script>
          <script type="text/javascript" src="${base }/js/jquery.blockUI.js"></script>
            <script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>
            <style type="text/css">
			.myPhotos .photo{
				width:26%;
				float:left;
				margin-left:4.5%;
				margin-top:8px;
				position:relative;
			}
			.myPhotos .photo img{
				height:80px;
				width:100%;
			}
			.myPhotos .photo .deletePhoto{
				width:100%;
				height:20px;
				background:url(../images/delete-photo-bg.png);
				position:absolute;
				top:0px;
				text-align:right;
			}
			.myPhotos .photo .deletePhoto p{
				width:15px;
				height:15px;
				background:url(../images/delete-photo.png);
			}
        </style>
		<script type="text/javascript">
			 	wx.config({
		            debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
		            appId: '${appId}', // 必填，公众号的唯一标识
		            timestamp: '${timestamp}', // 必填，生成签名的时间戳
		            nonceStr: '${noncestr}', // 必填，生成签名的随机串
		            signature:'${signature}',// 必填，签名
		            jsApiList: ['checkJsApi',
		                        'chooseImage', 
		                        'previewImage', 
		                        'uploadImage'] // 必填，需要使用的JS接口列表
		        });
		 	 	var imgNum = 0;
		        var localIds = new Array();
		        var serverIdNum = 0;
		        function choosePhoto(){
		        	   $("#intro").remove();
		    		   wx.chooseImage({
		        		   count: 9, // 默认9
		        		   sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有
		        		   sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
		        		   success: function (res) {
		        		     localIds = res.localIds; // 返回选定照片的本地ID列表，localId可以作为img标签的src属性显示图片
		        		     for(var i=0;i<localIds.length;i++){
		        		    	 if(imgNum <9){
		        		    		 imgNum = imgNum + 1;
		            		    	 var img = $("<div class='photo' id='myImage_"+imgNum+"'><img  src='"
		            		    			 +localIds[i]
		            		    	 		 +"' /><div class='deletePhoto' onclick='deletePhoto($(this))'><p></p></div></div>");
		            		    	 $("#addPhoto").before(img);
		            		    	 $("#countBar").html("已选择"+imgNum+"张，（共能选择9张）");
		        		    	 }
		        		     }
		        		   }
		        		 });
		       }
		       function deletePhoto(item){
			       	var itemId = item.parent().attr("id");
			       	var photoNum = parseInt(itemId.substring(8));
			       	var photoCount = $(".photo").length;
			       	if(photoNum < photoCount){
			       		var afterNum = photoNum+1;
			       		for(var v=afterNum;v<=photoCount;v++){
			       			var num = v-1;
			       			$("#myImage_"+v).attr("id","myImage_"+num);
			       		}
			       	}
			       	$("#myImage_"+photoNum).remove();
			       	imgNum = imgNum -1;
			       	$("#countBar").html("已选择"+imgNum+"张，（共能选择9张）");
		       }
		        function subEvaluation(){
		        	var imgDatas = new Array();
		        	var awardRecordId = $("#awardRecordId").val();
		        	var arEvaluation = $("#arEvaluation").val();
		        	if(arEvaluation==""||arEvaluation==null){
		        		alert("亲，你还没有填写评价哦");
		        	}else{
		        		$.blockUI({ 
							message: $('#submitMess'),
							css: {border:'#666666',width:'0px',height:'0px',top:'50%',left:'50%' } 
							});
		        		addServerIds()
		        	}
		        }
		    	function addServerIds(){
		        	var photos = $("#myPhotos img");
		        	  var awardRecordId = $("#awardRecordId").val();
		        	if(photos.length > 0) {
		        	  	var photoSrc = $("#myPhotos img").eq(serverIdNum).attr("src");
			    		   wx.uploadImage({
			  	    		 localId: photoSrc.toString(), // 图片的localID
			  	    		 isShowProgressTips: 0, // 默认为1，显示进度提示
			  	    		 success: function (res) {
			  		    		 var mediaId  = res.serverId; // 返回图片的服务器端ID，即mediaId
			  		    		serverIdNum = serverIdNum + 1;
			  		    		 $.post(
			  		    				 "${base}/mine/integral!saveWeixinPhotos.action",
			  		    				 {"serverId":mediaId,"photoNum":serverIdNum,"awardRecord.id":awardRecordId},
			  		    				 function(data){
			  		    					 	if(data!="error"){
			  		    					 	 var h = "<input type='hidden' name='eImgs' value='"+data+"'/>"
						  		    			 	$("#awardRecordId").after(h); 
						  	  		    			if(serverIdNum<photos.length-1){
						  	  		    				addServerIds();
						  	  		    			}else {
						  	  		    				$("#myForm").submit();
						  	  		    			}
			  		    					 	}else{
			  		    					 		alert("出错了，请退出重试");
			  		    					 	}
			  		    		 		});
			  	    		 }
			    		 });
		        	}
		       }
			 XBack.listen(function(){
				  window.location = "${base}/mine/integral!claimRecord.action";
				});
		</script>
	</head>
	<body style="background-color:#F0EFEE;">
		<div id="submitMess" class="block-box">
			<img src="${base }/images/submitMess.png" style="width:315px;height:332px;" alt="" />
		</div>
		<div class="welcome-box">
			<p>
				<img src="${base }/images/smile_face.png"  />
			</p>
			<p>
				为奖品填写评价，跟小伙伴们分享领取奖品的喜悦吧，提交评价还可获得10积分哦
			</p>
		</div>
		<form action="integral!saveEvaluation.action" method="post" id="myForm">
		<input type="hidden" value="${awardRecord.id }" id="awardRecordId" name="awardRecord.id"/>
		<div class="evaluation-box">
			<p>评价：</p>
			<p class="evaluation-area">
				<textarea   name="awardRecord.evaluation" id="arEvaluation"></textarea>
			</p>
		</div>
		</form>
		<div class="myPhotos photo-box"  id="myPhotos">
				<img src="${base }/images/add_circle.png" style="vertical-align: middle;" id="addPhoto" width="20%" onclick="choosePhoto()" alt="" />
				<span id="intro">上传实拍图</span>
				<div class="clear"></div>
		</div>
	    <div class="evaluation-sub-box" onclick="subEvaluation()">
	    	提&nbsp;&nbsp;&nbsp;&nbsp;交
	    </div>
	</body>
</html>
