<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/content/common/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http//www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title></title>
<link href="${base}/css/admin.css" rel="stylesheet" type="text/css" />
<script>
function showDetail()
{
	var d = document.getElementById("detail");
	if (d.style.display == "none")
		d.style.display = "";
	else
		d.style.display = "none";
}
</script>
</head>
<body class="admin">
<div class="body">
    <table class="listTable">
        <caption>服务器内部错误</caption>
        <tr>
            <td class="left">
                <s:property value="exception"/>
                <br/>
                <a href="javascript:;" onclick="showDetail()">点击查看详细信息</a>，请您把详细信息发给我们进行调试。
            </td>
        </tr>
        <tr id="detail" style="display: none;">
            <td class="left"><pre><s:property value="exceptionStack"/></pre></td>
        </tr>
    </table>
</div>
</body>
</html>