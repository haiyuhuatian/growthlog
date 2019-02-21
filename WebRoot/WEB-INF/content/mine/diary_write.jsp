<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/content/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>写日志</title>
		<meta name="keywords" value="成长日志" />
		<meta name="description" value="首个记录成长日志的网站" />
		<meta name="author" value="成长日志" />
		<meta name="viewport" content="width=device-width,height=device-height,inital-scale=1.0,maximum-scale=1.0,user-scalable=no;">
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="apple-mobile-web-app-status-bar-style" content="black">
		<meta name="format-detection" content="telephone=no">
		<link href="${base }/phone-css/phone.css" rel='stylesheet' type='text/css' />
		 <link rel="stylesheet" type="text/css" href="${base }/css/bootstrap-theme.min.css">
        <link rel="stylesheet" type="text/css" href="${base }/phone-css/demo.css">
        <script language="javascript" type="text/javascript" src="${base}/My97DatePicker/WdatePicker.js"></script>
        <script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>
        <script src="${base }/js/jquery.js"></script>
        <script type="text/javascript"  src="${base }/phone-js/xback.js"></script>
          <style type="text/css">
        	.myPhotos{
				width:95%;
				margin:0 auto;
				margin-top:10px;
				border:2px dashed gray;
				height:350px;
			}
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
        var savePhotoSign="";
        var packageId = "";
       function choosePhoto(){
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
       XBack.listen(function(){
	 		window.location="${base}/mine/diary!list.action";
		});
        </script>
	</head>
	<body style="background-color:#F0EFEE;">
		<div id="submitMess" class="submit-mess-box">
			<img src="${base }/images/submitMess.png" />
		</div>
		<div id="serverIds" style="display:none;"></div>
		<form action="${base }/mine/diary!save.action" method="post" enctype="multipart/form-data" id="myForm">
			<input type="hidden" name="diaryId" id="diaryId" />
			<div id="imgNames"></div>
			<div id="choosedCircles"></div>
			<div class="diary-title-box">
				<input type="text" placeholder="请在这里输入标题"  name="diary.title" id="title" />
			</div>
			<div class="weather-date-box" id="messageNode">
				日期：<input type="text"  readonly="true" onclick="WdatePicker();" id="diaryDate" class="date-input" name="diary.diary_date" />
				天气：<input type="text" class="weather-input"   name="diary.weather"/>
			</div>
			<div class="type-model-box" id="messageNode">
				类型：
				<select name="diary.type" >
					<option value="1">日常</option>
					<option value="2">游记</option>
					<option value="3">学习笔记</option>
					<option value="4">趣事</option>
				</select>
				板块：
				<select name="diary.model" >
					<option value="2">个人日志</option>
					<option value="1">宝贝日志</option>
				</select>
			</div>
			<div class="openness-box">
				<span onclick="changeOpenness(1)">
					<input type="radio" id="myself" name="diary.openness" value="1" />
					<label for="myself">仅自己可见</label>
				</span>
				<span >
					<input type="radio" id="friend" name="diary.openness" value="2"/>
					<label  onclick="changeOpenness(2)">仅圈子可见</label>
				</span>
				<input type="hidden" id="circleNum" value="${circleNum }"/>
				<span >
					<input type="radio" id="open" name="diary.openness" value="3" checked="checked"/>
					<label for="open" onclick="changeOpenness(3)">所有人可见</label>
				</span>
			</div>
			<div id="circles" class="circle-names" style="display:none">
				<c:forEach items="${circles }" var="c" varStatus="status">
					<div class="circle-box" onclick="chooseCircle(${c.id})">
						<img src="${base }/circle_cover/${c.cover}" width="100%" alt="" />
						<p >${c.name }</p>
						<img src="${base }/images/un_checked.png" id="circle${c.id }" style="float:right;" alt="" />
						<div class="clear"></div>
					</div>
				</c:forEach>
				<div class="clear"></div>
			</div>
			<div class="myPhotos"  id="myPhotos">
				<img src="${base }/images/add_circle.png" id="addPhoto" width="20%" onclick="choosePhoto()" alt="" />
				<div class="clear"></div>
			</div>
			<div id="countBar" class="photo-count-bar"></div>
	        <div class="diary-content-box">
				<div >内容</div>
				<textarea   name="diary.content" id="diaryContent"></textarea>
			</div>
	        <div class="subDiary">
		        <span onclick="checkForm()">
		        	提交
		        </span>
			</div>
        </form>
        <script src="${base }/js/extend-webuploader.js" type="text/javascript"></script>
        <script type="text/javascript" src="${base }/js/jquery.blockUI.js"></script>
        <script type="text/javascript">
			function checkForm() {
				if($("#title").val() == "") {
					alert("您还没有写标题哟");
					return false;
				}
				if($("#diaryDate").val() == "") {
					alert("您还没有选择日期哟");
					return false;
				}
				if($("input[name='diary.openness']:checked").val()==2){
					if($("input[name='circles']").length<=0){
						alert("请选择可见的圈子");
						return false;
					}
				}
				if($("#diaryContent").val() == ""){
					alert("您还没有写内容哦");
					return false;
				}
				$.blockUI({ 
					message: $('#submitMess'),
					css: {border:'#666666',width:'0px',height:'0px',top:'50%',left:'50%' } 
					});
				var photos = $("#myPhotos img");
				if(photos.length > 1){
					addServerIds(photos.length)
				}else{
					$("#myForm").submit();
				}
			}
			function addServerIds(photosNum){
	        	  	var photoSrc = $("#myPhotos img").eq(serverIdNum).attr("src");
		    		   wx.uploadImage({
		  	    		 localId: photoSrc.toString(), // 图片的localID
		  	    		 isShowProgressTips: 0, // 默认为1，显示进度提示
		  	    		 success: function (res) {
		  		    		 var mediaId  = res.serverId; // 返回图片的服务器端ID，即mediaId
		  		    		serverIdNum = serverIdNum + 1;
		  		    		 $.post(
		  		    				 "${base}/mine/diary!saveWeiPhotos.action",
		  		    				 {"serverId":mediaId,"photoNum":serverIdNum,"savePhotoSign":savePhotoSign,"packageId":packageId},
		  		    				 function(data){
		  		    					 if(data!="error"){
		  		    						var resDatas = data.split(",");
		  		    						var imgHtml = "<input type='hidden' name='diaryImages' value='"+resDatas[0]+"' />"
		  		    						$("#diaryId").before(imgHtml);
				  		    				 savePhotoSign = resDatas[1];
				  		    				packageId = resDatas[2];
				  		    				$("#diaryId").val(packageId);
				  	  		    			if(serverIdNum<photosNum-1){
				  	  		    				addServerIds(photosNum);
				  	  		    			}else {
				  	  		    				$("#myForm").submit();
				  	  		    			} 
		  		    					 }else{
		  		    						 alert("出错了，请推出后重试");
		  		    					 }
				  		    			 
		  		    		 		});
		  	    		 }
		    		 });
	       }
			function changeOpenness(openness){
        		if(openness == 2){
        			var circleNum = $("#circleNum").val();
        			if(circleNum != "0"){
        				$("#friend").attr("checked",true);
        				$("#circles").show();
        			}else{
        				alert("您还没有加入任何圈子哦");
        			}
        		}else {
        			$("#circles").hide();
        		}
        	}
			function chooseCircle(circleId){
				var circle = $("#circle"+circleId).attr("src");
				if(circle.indexOf("un_checked")!=-1){
					var circleInput = $("<input name='circles'  type='hidden' id='choosedCircle"+circleId+"' value='"+circleId+"' />")
					$("#choosedCircles").append(circleInput);
					$("#circle"+circleId).attr("src","${base}/images/circle_checked.png");
				}else {
					$("#choosedCircle"+circleId).remove();
					$("#circle"+circleId).attr("src","${base}/images/un_checked.png");
				}
			}
		</script>
	</body>
</html>
