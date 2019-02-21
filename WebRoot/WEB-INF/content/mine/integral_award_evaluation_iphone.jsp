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
		<link rel="stylesheet" type="text/css" href="${base }/css/bootstrap-theme.min.css">
        <link rel="stylesheet" type="text/css" href="${base }/phone-css/demo.css">
        <link rel="stylesheet" type="text/css" href="${base }/css/webuploader.css">
		<script src="${base }/js/jquery-1.10.2.js"></script>
		<script type="text/javascript"  src="${base }/phone-js/xback.js"></script>
		<script src="${base }/js/exif.js"></script>
        <script src="${base }/js/json2.js"></script>
        <script type="text/javascript" src="${base }/js/webuploader.js"></script>
         <script src="${base }/js/extend-webuploader-phone.js" type="text/javascript"></script>
          <script type="text/javascript" src="${base }/js/jquery.blockUI.js"></script>
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
		<script type="text/javascript">
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
		        		if(imgNum > 0) {
		        			for(var i=imgNum;i>0;i--){
		        				if($("#myImage_"+i).length>0) {
		        						if($("#myImage_"+i)) {
		        							imgDatas[i-1] = $("#myImage_"+i+" img").eq(0).attr("src");
		        							//imgDatas[i-1] = photo.attr("src");
		        						}
		        				}
							}
		        			 $.ajax({  
							        type: "post",         
							        url:"${base}/mine/integral!savePhotos.action",  
							        data: {"myImgs" : JSON.stringify(imgDatas),"awardRecord.id":awardRecordId},  
							        async: true,  
							        cache: false,
							        success: function (data) { 
							              if(data>0) {
							            	  $("#myForm").submit();  
							              }
							        },  
									error: function(data) {  
							              alert(e.responseText); //alert错误信息   
							       }  
							   }); 
		        		} else{
		        			$("#myForm").submit(); 
		        		}
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
		<div id="myPhotos" class="myPhotos" >
				<div class="clear" id="clearDiv"></div>
		</div>
		<div class="page-container" style="margin-top:20px;width:95%;margin-left:2.5%;background-color:#fff;padding: 10px 0;">
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
	    <div class="evaluation-sub-box" onclick="subEvaluation()">
	    	提&nbsp;&nbsp;&nbsp;&nbsp;交
	    </div>
	</body>
</html>
