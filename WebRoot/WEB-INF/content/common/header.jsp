<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/content/common/taglib.jsp" %>
<link href="${base}/css/page.css" rel="stylesheet" type="text/css" />
<div id="toplinkbar">
  <ul>
    <li></li>
  </ul>

  <div class="clear"></div>
</div>
<div id="topimgbar">
		<div align="center">
	       <object id="FlashID" classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" width="972px" height="158px">
			    <param name="movie" value="${base}/flash/banner.swf" />
			    <param name="scale" value="Exactfit" />
			    <param name="quality" value="high" />
			    <param name="wmode" value="opaque" />
			    <param name="swfversion" value="9.0.45.0" />
			    <!-- 此 param 标签提示使用 Flash Player 6.0 r65 和更高版本的用户下载最新版本的 Flash Player。如果您不想让用户看到该提示，请将其删除。 -->
			    <param name="expressinstall" value="Scripts/expressInstall.swf" />
			    <!-- 下一个对象标签用于非 IE 浏览器。所以使用 IECC 将其从 IE 隐藏。 -->
			    <!--[if !IE]>-->
			    <object type="application/x-shockwave-flash" data="${base}/flash/banner.swf" width="972px" height="158px">
			      <!--<![endif]-->
			      <param name="quality" value="high" />
			      <param name="wmode" value="opaque" />
			      <param name="swfversion" value="9.0.45.0" />
			      <param name="expressinstall" value="Scripts/expressInstall.swf" />
			      <!-- 浏览器将以下替代内容显示给使用 Flash Player 6.0 和更低版本的用户。 -->
			      <div>
			        <h4>此页面上的内容需要较新版本的 Adobe Flash Player。</h4>
			        <p><a href="http://www.adobe.com/go/getflashplayer"><img src="http://www.adobe.com/images/shared/download_buttons/get_flash_player.gif" alt="获取 Adobe Flash Player" width="112" height="33" /></a></p>
			      </div>
			      <!--[if !IE]>-->
			    </object>
			    <!--<![endif]-->
				 </object>
	 </div>
</div>
<div id="pagebanner">
  <div id="rollnews">
		<ul id="newslist">
			
		</ul>
  </div>
  <script type="text/javascript" src="${base}/js/new.js"></script>
  <div id="nav">
    <ul>
      <li>
        <a src="/index.jsp">首页</a>
      </li>
      <li>
        <a src="/issueType.jsp?type=1"></a>
      </li>
      <li>
        <a src="/issueType.jsp?type=2"></a>
      </li>
      <li>
        <a src="/survey/index.jsp">教学评估</a>
      </li>
      <li>
        <a src="/issueType.jsp?type=4"></a>
      </li>
      <li>
        <a src="/issueType.jsp?type=3"></a>
      </li>
      <li>
        <a src="#" onclick="return false;">电子图书</a>
      </li>
      <li>
        <a src="#" onclick="return false;">帮助大全</a>
      </li>
    
    </ul>
  </div>
</div>
