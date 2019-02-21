<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/content/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>设置头像</title>
		<meta name="keywords" value="成长日志" />
		<meta name="description" value="首个记录成长日志的网站" />
		<meta name="author" value="成长日志" />
		<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1, maximum-scale=1">
		<link href="${base }/css/bootstrap.min.css" rel="stylesheet">
  		<link href="${base }/css/cropper.min.css" rel="stylesheet">
  		<link href="${base }/css/setCover.css" rel="stylesheet">
  		<link rel="stylesheet" href="${base }/phone-css/phone.css" type="text/css" />
		<script src="${base }/js/jquery.min.js"></script>
  		<script src="${base }/js/bootstrap.min.js"></script>
  		<script src="${base }/phone-js/cropper.min.js"></script>
  		<script src="${base }/js/setHead.js"></script>
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
	                         'chooseImage', 
	                         'previewImage', 
	                         'uploadImage'] // 必填，需要使用的JS接口列表
	         });
	  		 function chooseImg(){
	  		   wx.chooseImage({
        		   count: 1, // 默认9
        		   sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有
        		   sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
        		   success: function (res) {
        		     localId = res.localIds; // 返回选定照片的本地ID列表，localId可以作为img标签的src属性显示图片
        		     wx.getLocalImgData({
        		    	 localId: localId.toString(), // 图片的localID
        		    	 success: function (res) {
        		    	 	var localData = res.localData; // localData是图片的base64数据，可以用img标签显示
        		    	 	$("#temporaryImg").val(localData);
        		    	 	$("#temporaryForm").submit();
        		    	 }
        		     });
        		   }
        		 });
	  		 }
  			function uploadImg(){
  				var can = $("#modalBody").find("canvas")[0];
  				var data = can.toDataURL();
  				$("#myImg").val(data);
  				$("#myForm").submit();
  			}
  			 XBack.listen(function(){
				  window.location = "${base}/mine/member!showInfo.action";
				});
  		</script>
	</head>
	<body>
	  	 	<div class="row" style="margin-top:50px;">
		      <div class="col-md-9 docs-buttons" style="text-align:center;">
		        <!-- <h3 class="page-header">Toolbar:</h3> -->
		        <div class="btn-group" style="text-align:center;">
		          <button class="btn btn-primary" data-method="zoom" data-option="0.1" type="button" title="放大">
		            <span class="docs-tooltip" data-toggle="tooltip" >
		              <span class="icon icon-zoom-in"></span>
		            </span>
		          </button>
		          <button class="btn btn-primary" data-method="zoom" data-option="-0.1" type="button" title="缩小">
		            <span class="docs-tooltip" data-toggle="tooltip" >
		              <span class="icon icon-zoom-out"></span>
		            </span>
		          </button>
		          <button class="btn btn-primary" data-method="rotate" data-option="-45" type="button" title="逆时针旋转45度">
		            <span class="docs-tooltip" data-toggle="tooltip" >
		              <span class="icon icon-rotate-left"></span>
		            </span>
		          </button>
		          <button class="btn btn-primary" data-method="rotate" data-option="45" type="button" title="顺时针旋转45度">
		            <span class="docs-tooltip" data-toggle="tooltip" >
		              <span class="icon icon-rotate-right"></span>
		            </span>
		          </button>
		        </div>
		        <div class="btn-group">
		          <label class="btn btn-primary btn-upload" for="inputImage" title="Upload image file">
		            <div onclick="chooseImg()">选择图片</div>
		          </label>
		        </div>
		
		        <div class="btn-group btn-group-crop">
		          <button class="btn btn-primary"  data-method="getCroppedCanvas" data-option="{ &quot;width&quot;:200, &quot;height&quot;:200 }" type="button">
		            <span class="docs-tooltip" data-toggle="tooltip" >
		              	裁切
		            </span>
		          </button>
		        </div>
		
		        <!-- Show the cropped image in modal -->
		        <form action="user!saveHead.action" method="post" id="myForm">
		        	<input type="hidden" name="image" id="myImg" />
		        	<input type="hidden" name="type" value="${type }" />
		        <div class="modal fade docs-cropped"  id="getCroppedCanvasModal" aria-hidden="true" aria-labelledby="getCroppedCanvasTitle" role="dialog" tabindex="-1" style="display:none">
		          <div class="modal-dialog">
		            <div class="modal-content">
		              <div class="modal-header">
		              <button class="btn btn-primary"  type="button" onclick="uploadImg()">上传</button> 
		                <button class="close" data-dismiss="modal" type="button" aria-hidden="true">&times;</button>
		                <h4 class="modal-title" id="getCroppedCanvasTitle"></h4>
		              </div>
		              <div class="modal-body" id="modalBody" ></div>
		              <!-- <div class="modal-footer">
		                <button class="btn btn-primary" data-dismiss="modal" type="button">Close</button>
		              </div> -->
		            </div>
		          </div>
		        </div><!-- /.modal -->
		        </form>
		      </div>
		      <div class="clear"></div>
    	</div>
		<div class="row" style="margin-bottom:100px;">
		      <div class="col-md-9">
		        <!-- <h3 class="page-header">Demo:</h3> -->
		        <div class="img-container">
		        <c:if test="${!empty(headImg) }">
		          <img src="${base }/uploadImages/growthLog${loginUser.id}/${headImg}" 
		          	onerror="this.src='${base}/images/default_head.png'" id="headImg" alt="Picture">
		         </c:if>
		          <c:if test="${empty(headImg) }">
		          <img src="${base}/images/default_head.png" id="headImg" alt="Picture">
		         </c:if>
		        </div>
		      </div>
   	 	</div>
    	<form action="user!temporary.action" method="post" id="temporaryForm">
    		<input type="hidden" id="temporaryImg" name="image"/>
    	</form>
    	<div class="docs-alert"><span class="warning message"></span></div>
	</body>
</html>