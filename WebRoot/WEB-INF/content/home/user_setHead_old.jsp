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
		<link rel="stylesheet" href="${base }/phone-css/phone.css" type="text/css" />
		<link rel="stylesheet" href="${base }/phone-css/setHead.css?v=2" />
		 <script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>
		<style type="text/css">
			input[type=file] {
				width:188px;
				height:60px;
				background:#333;
				position:absolute;
				right:48%;
				opacity:0;
			}
			#uploadImg{
				margin-top: 5px;
    			background-color: rgb(221, 39, 39);
    			color: #fff;
    			padding: 8px 20px;
    			border-radius: 5px;
    			width: 40%;
    			margin-left: 3%;
			}
			#closeImg{
				margin-top: 5px;
    			background-color: rgb(221, 39, 39);
   				color: #fff;
    			padding: 8px 20px;
    			border-radius: 5px;
    			width: 40%;
			}
		</style>
		<script type="text/javascript">
	        wx.config({
	            debug: true, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
	            appId: '${appId}', // 必填，公众号的唯一标识
	            timestamp: '${timestamp}', // 必填，生成签名的时间戳
	            nonceStr: '${noncestr}', // 必填，生成签名的随机串
	            signature:'${signature}',// 必填，签名
	            jsApiList: ['checkJsApi',
	                        'chooseImage', 
	                        'previewImage', 
	                        'uploadImage'] // 必填，需要使用的JS接口列表
	        });
        </script>
	</head>
	<body>
	<jsp:include page="/home/index!head.action" ></jsp:include>
<!--加载资源-->
<div class="lazy_tip" id="lazy_tip"><span>1%</span><br>	载入中......</div>
<div class="lazy_cover"></div>
<div class="resource_lazy hide"></div>
<form action="${base }/home/user!saveHead.action" method="post" id="myForm">
	<input type="hidden" name="image" id="myHead" />
</form>
<div class="pic_edit">
	<h2 style="color:#4eaf7a;">双指旋转和双指缩放</h2>
	<div id="clipArea"></div>
	<button id="upload2">选择图片</button>
    <button id="clipBtn">上传图片</button>
	<input type="file" id="file" >
  	<div id="plan" style="display:none">
    	<canvas id="myCanvas"></canvas>
    	<button  id="uploadImg" onclick="uploadMyHead()">上传</button>
    	<button id="closeImg"  onclick="Close();">关闭</button>
    </div>  
</div>
<img src="" fileName="" id="hit" style="display:none;z-index: 9">

<div id="cover"></div>
<script src="${base }/js/jquery.js"></script>
 <script src="${base }/js/sonic.js"></script>
<script src="${base }/js/comm.js"></script>
<script src="${base }/js/hammer.js"></script>
<script src="${base }/js/iscroll-zoom.js"></script> 
<script src="${base }/js/jquery.photoClip.js?v=1"></script> 
<script>
var hammer = '';
var currentIndex = 0;
var body_width = $('body').width();
var body_height = $('body').height();

$("#clipArea").photoClip({
	width: body_width * 0.8125,
	height: body_width * 0.8125,
	file: "#file",
	view: "#hit",
	ok: "#clipBtn",
	loadStart: function () {
		//console.log("照片读取中");
		$('.lazy_tip span').text('');
		$('.lazy_cover,.lazy_tip').show();
	},
	loadComplete: function () {
		//console.log("照片读取完成");
		$('.lazy_cover,.lazy_tip').hide();
	},
	clipFinish: function (dataURL) {
		$('#hit').attr('src', dataURL);
		saveImageInfo();
	}
});
function uploadMyHead(){
	$("#myForm").submit();
}
//图片上传
function saveImageInfo() {
	var filename = $('#hit').attr('fileName');
	var img_data = $('#hit').attr('src');
	if(img_data==""){alert('null');}
	//alert(filename+'|'+img_data);
   render(img_data); 
}

/*获取文件拓展名*/
function getFileExt(str) {
	var d = /\.[^\.]+$/.exec(str);
	return d;
}

//图片上传结束
$(function () {
	/* $('#upload2').on('touchstart', function () {
		alert("haha");
		//图片上传按钮
		$('#file').click();
	}); */
})


function Close(){$('#plan').hide();}

// 渲染 Image 缩放尺寸  
function render(src){  
	 var MAX_HEIGHT = 256;  //Image 缩放尺寸 
    // 创建一个 Image 对象  
    var image = new Image();  
	
    // 绑定 load 事件处理器，加载完成后执行  
    image.onload = function(){  
		// 获取 canvas DOM 对象  
  		var canvas = document.getElementById("myCanvas"); 
        // 如果高度超标  
        if(image.height > MAX_HEIGHT) {  
            // 宽度等比例缩放 *=  
            image.width *= MAX_HEIGHT / image.height;  
            image.height = MAX_HEIGHT;  
        }  
        // 获取 canvas的 2d 环境对象,  
        // 可以理解Context是管理员，canvas是房子  
        var ctx = canvas.getContext("2d");  
        // canvas清屏  
        ctx.clearRect(0, 0, canvas.width, canvas.height); 
        canvas.width = 200;        // 重置canvas宽高  
        canvas.height = 200;  
        // 将图像绘制到canvas上  
        ctx.drawImage(image, 0, 0, image.width, image.height);  
        // !!! 注意，image 没有加入到 dom之中  
		
	 var dataurl = canvas.toDataURL();  
	 var imagedata =  encodeURIComponent(dataurl); 
	 	//$('#plan').attr('data-src',dataurl); 
	 	$("#myHead").val(dataurl);
	 	$("#myForm").submit();
	  //$('#plan').show();
    };  
    // 设置src属性，浏览器会自动加载。  
    // 记住必须先绑定render()事件，才能设置src属性，否则会出同步问题。  
    image.src = src;	
};  
</script>

</body>
</html>