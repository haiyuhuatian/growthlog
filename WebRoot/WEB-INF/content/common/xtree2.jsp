<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/content/common/taglib.jsp"%>
<link href="${base}/xtree/xtree.css" rel="stylesheet" type="text/css" />
<style type="text/css">
.floatTree {
	position: absolute;
	border-color: black;
	border: solid 1px;
	padding: 5px;
	padding-right: 20px;
	text-align: left;
	width: 350px;
	height: 250px;
	overflow-x: hidden;
	overflow-y: auto;
	background-color: white;
}
</style>
<script type="text/javascript" src="${base}/xtree/xtree.js"></script>
<script type="text/javascript" src="${base}/xtree/xmlextras.js"></script>
<script type="text/javascript" src="${base}/xtree/xloadtree.js"></script>
<script type="text/javascript">
	// XP Look
	webFXTreeConfig.rootIcon = "${base}/xtree/images/xp/folder.png";
	webFXTreeConfig.openRootIcon = "${base}/xtree/images/xp/openfolder.png";
	webFXTreeConfig.folderIcon = "${base}/xtree/images/xp/folder.png";
	webFXTreeConfig.openFolderIcon = "${base}/xtree/images/xp/openfolder.png";
	webFXTreeConfig.fileIcon = "${base}/xtree/images/xp/file.png";
	webFXTreeConfig.lMinusIcon = "${base}/xtree/images/xp/Lminus.png";
	webFXTreeConfig.lPlusIcon = "${base}/xtree/images/xp/Lplus.png";
	webFXTreeConfig.tMinusIcon = "${base}/xtree/images/xp/Tminus.png";
	webFXTreeConfig.tPlusIcon = "${base}/xtree/images/xp/Tplus.png";
	webFXTreeConfig.iIcon = "${base}/xtree/images/xp/I.png";
	webFXTreeConfig.lIcon = "${base}/xtree/images/xp/L.png";
	webFXTreeConfig.tIcon = "${base}/xtree/images/xp/T.png";
	webFXTreeConfig.blankIcon = "${base}/xtree/images/xp/blank.png";

	// 不使用cookie
	webFXTreeConfig.usePersistence = false;

	// 弹出层开关函数
	function openOldTree(event) {
		var tree = document.getElementById("oldTree");
		tree.style.left = event.clientX + "px";
		tree.style.top = event.clientY + document.documentElement.scrollTop
				+ "px";
		tree.style.display = "";
	}

	function closeOldTree() {
		var tree = document.getElementById("oldTree");
		tree.style.display = "none";
	}
</script>