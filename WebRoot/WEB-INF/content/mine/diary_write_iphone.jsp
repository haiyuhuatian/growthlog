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
        <link rel="stylesheet" type="text/css" href="${base }/css/webuploader.css">
        <script language="javascript" type="text/javascript" src="${base}/My97DatePicker/WdatePicker.js"></script>
        <style type="text/css">
        	.myPhotos{
				width:95%;
				margin:0 auto;
				margin-top:10px;
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
	</head>
	<body style="background-color:#F0EFEE;">
		<div id="submitMess" class="block-box">
			<img src="${base }/images/submitMess.png" style="width:315px;height:332px;" alt="" />
		</div>
		<form action="${base }/mine/diary!save.action" method="post" enctype="multipart/form-data" id="myForm">
			<input type="hidden" name="diaryId" id="diaryId" />
			<div id="choosedCircles"></div>
			<div  class="diary-title-box">
				<input type="text" placeholder="请在这里输入标题"  name="diary.title" id="title"/>
			</div>
			<div class="weather-date-box" id="messageNode">
				日期：<input type="text"  readonly="true" onclick="WdatePicker();" 
						id="diaryDate" name="diary.diary_date" class="date-input"/>
				天气：<input type="text"  class="weather-input" name="diary.weather"/>
			</div>
			<div  class="type-model-box" id="messageNode">
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
				<span >
					<input type="radio" id="myself" name="diary.openness" value="1" />
					<label for="myself" onclick="changeOpenness(1,$(this))">仅自己可见</label>
				</span>
				<span >
					<input type="radio" id="friend" name="diary.openness" value="2"/>
					<label  onclick="changeOpenness(2,$(this))">仅圈子可见</label>
				</span>
				<input type="hidden" id="circleNum" value="${circleNum }"/>
				<span >
					<input type="radio" id="open" name="diary.openness" value="3" checked="checked"/>
					<label for="open" onclick="changeOpenness(3,$(this))">所有人可见</label>
				</span>
			</div>
			<div id="circles" class="circle-names" style="display:none">
				<c:forEach items="${circles }" var="c" varStatus="status">
					<div style="width:20%;float:left;margin-left:3%;margin-top:10px;" onclick="chooseCircle(${c.id})">
						<img src="${base }/circle_cover/${c.cover}" width="100%" alt="" />
						<p >${c.name }</p>
						<img src="${base }/images/un_checked.png" id="circle${c.id }" style="float:right;" alt="" />
						<div class="clear"></div>
					</div>
				</c:forEach>
				<div class="clear"></div>
			</div>
			<div id="myPhotos" class="myPhotos" >
				<div class="clear" id="clearDiv"></div>
			</div>
			<div class="page-container" style="margin-top:20px;">
	            <div id="uploader" class="wu-example">
	                <div class="queueList">
	                    <div id="dndArea" class="placeholder">
	                        <div id="filePicker"></div>
	                    </div>
	                </div>
	                <div class="statusBar" style="display:none">
	                    <div class="progress">
	                        <span class="text">0%</span>
	                        <span class="percentage"></span>
	                    </div>    
	                    <div class="info"></div>
	                    <div class="btns">
	                        <div id="filePicker2"></div>
	                    </div>
	                </div>
	            </div>
	        </div>
	        <div class="diary-content-box">
				<div >内容</div>
				<textarea id="diaryContent"  name="diary.content"></textarea>
			</div>
	        <div class="subDiary">
		        <span  onclick="checkForm()">
		        	提&nbsp;&nbsp;&nbsp;&nbsp;交
		        </span>
			</div>
        </form>
		<script src="${base }/js/jquery-1.10.2.js"></script>
		<script src="${base }/js/exif.js"></script>
        <script src="${base }/js/json2.js"></script>
        <script>
        window.webuploader = {
            config:{
                thumbWidth:100, //缩略图宽度，可省略，默认为110
                thumbHeight: 100, //缩略图高度，可省略，默认为110
                wrapId: 'uploader', //必填
                theAgent:'phone',
            },
            //处理客户端新文件上传时，需要调用后台处理的地址, 必填
            uploadUrl: '${base}/member/diary!upload.action',
            //处理客户端原有文件更新时的后台处理地址，必填
            updateUrl: '${base}/uploadImages',
            //当客户端原有文件删除时的后台处理地址，必填
            removeUrl: '${base}/uploadImages',
            //初始化客户端上传文件，从后台获取文件的地址, 可选，当此参数为空时，默认已上传的文件为空
            initUrl: '${base}/uploadImages',
        }
        var imgNum = 1;
        var fileCount = 0;
        </script>
         <script type="text/javascript" src="${base }/js/webuploader.js"></script>
        <script src="${base }/js/extend-webuploader-phone.js" type="text/javascript"></script>
        <script type="text/javascript" src="${base }/js/jquery.blockUI.js"></script>
         <script type="text/javascript"  src="${base }/phone-js/xback.js"></script>
        <script type="text/javascript">
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
	        	fileCount = fileCount-1;
	        	$("#fileAcount").html(fileCount );
	        }
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
				var imgDatas = new Array();
        		if(imgNum > 0) {
        			for(var i=imgNum;i>0;i--){
        				if($("#myImage_"+i).length>0) {
        						if($("#myImage_"+i)) {
        							imgDatas[i-1] = $("#myImage_"+i+" img").eq(0).attr("src");
        							//imgDatas[i-1] = photo.attr("src");
        						}
        				}
					}
        			if(imgDatas.length>0){
        				$.ajax({  
    				        type: "post",         
    				        url:"${base}/mine/diary!savePhotos.action",  
    				        data: {"myImgs" : JSON.stringify(imgDatas)},  
    				        async: true,  
    				        cache: false,
    				        success: function (data) { 
    				              if(data>0) {
    				            	  $("#diaryId").val(data);
    				            	  $("#myForm").submit();  
    				              }
    				        },  
    						error: function(data) {  
    				              alert(e.responseText); //alert错误信息   
    				       }  
    				   }); 
        			}else{
        				$("#myForm").submit();
        			}
        		} else{
        			$("#myForm").submit(); 
        		}
				  
			}
			function changeOpenness(openness,item){
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
			XBack.listen(function(){
		 		window.location="${base}/mine/diary!list.action";
			});
		</script>
	</body>
</html>
