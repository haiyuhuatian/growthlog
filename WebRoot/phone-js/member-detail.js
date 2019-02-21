function editUser(type){
				var userId = $("#userId").val();
				if(userId == null || userId == ""){
					alert("您还没有登录呢！");
				}else{
					$("#editArea").show();
					$("#remind").html("");
					var editContent = "";
					if(type == 1) {
						$("#editContent").empty();
						editContent = $("<p style='padding-top:10px;'>请填写新的昵称</p>"
											+"<input style='margin-top:10px;' type='text' name='user.nickname' id='nickname' /> "
						                    );
						$("#editContent").append(editContent);
						$("#updateType").val("1");
						$("#nickname").focus();
					}else if(type == 2) {
						$("#editContent").empty();
						$("#remind").html("");
						editContent =  $("<div>请输入密码："
										+"<input style='margin-top:10px;' type='password' name='user.password' id='password' /></div> "
										+"请确认密码："
										+"<input style='margin-top:10px;' type='password'  id='confirmPass' /> "
	                    				);
						$("#editContent").append(editContent);
						$("#updateType").val("2");
						$("#password").focus();
					}else if(type == 3){
						$("#editContent").empty();
						$("#remind").html("");
						editContent = $("<p style='padding-top:10px;margin-bottom:10px;'>请选择性别</p>"
										+"<input type='radio' value='0' name='user.sex' id='women' checked='checked'/><label for='women'>女</label>"
										+"<input type='radio' value='1' name='user.sex' id='man'style='margin-left:10px;'/><label for='man'>男</label>");
						$("#editContent").append(editContent);
						$("#updateType").val("3");
					}else if(type == 4){
						$("#editContent").empty();
						$("#remind").html("");
						editContent = $("<p style='padding-top:10px;'>请选择日期</p>"
								+"<input style='margin-top:10px;' type='text' name='user.birthday' id='birthday'"
								+" onfocus='WdatePicker();' onclick='cleanSpan($(this))'/> "
			                    );
						$("#editContent").append(editContent);
						$("#updateType").val("4");
					}else if(type == 5){
						$("#editContent").empty();
						$("#remind").html("");
						editContent = $("<p style='padding-top:10px;'>请选择日期</p>"
								+"<input style='margin-top:10px;' type='text' name='user.baby_birthday' id='babyBirthday'"
								+" onfocus='WdatePicker();' onclick='cleanSpan($(this))'/> "
			                    );
						$("#editContent").append(editContent);
						$("#updateType").val("5");
					}else if(type == 6){
						$("#editContent").empty();
						$("#remind").html("");
						editContent = $("<p style='padding-top:10px;'>请填写邮箱地址</p>"
								+"<input style='margin-top:10px;' type='text' name='user.email' id='email' /> "
			                    );
						$("#editContent").append(editContent);
						$("#updateType").val("6");
						$("#email").fucus();
					}else if(type == 7){
						$("#editContent").empty();
						$("#remind").html("");
						editContent = $("<p style='padding-top:10px;margin-bottom:10px;'>请选择地域</p>"
								+"<select style='display:block;margin:0 auto;' id='city1' onchange='getCityName(\"2\",\"city1\")'></select>"
								+ "<select  style='display:block;margin:0 auto;margin-top:5px;' id='city2' onchange='getCityName(\"3\",\"city2\")'><option value='' >请选择</option></select>"
								+"<select style='display:block;margin:0 auto;margin-top:5px;' id='city3' name='user.area' ><option value='' >请选择</option></select> "
			                    );
						$("#editContent").append(editContent);
						$("#updateType").val("7");
						$.post("/portal/user!getCityName.action",{"cityMap.pid":0},function(data){
							if(data != "") {
								$("#city1").append("<option value='' >请选择</option>");
								$.each(data,function(i,item){
					    				$("#city1").append("<option value='"+item.id+"' >"+item.name+"</option>");
					    		});
							}
						},"json");
					}else if(type == 8){
						$("#editContent").empty();
						$("#remind").html("");
						editContent = $("<p style='padding-top:10px;'>请填写微信号</p>"
											+"<input style='margin-top:10px;' type='text'"
											+" name='user.wchat_no' id='wchatNo' />"
											+"<div style='margin-top:10px;'><p style='float:left;margin-left:33%;'><input type='radio' value='1' name='user.is_wchat_open' id='open' checked='checked'/>"
											+"<label for='open'>公开</label></p><p style='float:left;margin-left:20px;'><input type='radio' value='0  name='user.is_wchat_open' id='notOpen' />"
											+"<label for='notOpen'>不公开</label></p><div class='clear'></div></div> "
						                    );
						$("#editContent").append(editContent);
						$("#updateType").val("8");
						$("#wchatNo").focus();
					}else if(type == 9){
						$("#editContent").empty();
						$("#remind").html("");
						var introduction = $("#introduction").val();
						$("#contentPar").css("height","473px");
						editContent = $("<p style='padding-top:10px;'>请完善简介</p>"
								+"<textarea style='margin-top:10px;height:357px;width:95%;line-height:30px' "
								+" name='user.introduction' id='userIntroduction' >"
								+introduction
								+"</textarea><div style='margin-top:10px;' id='acountRemind'>最多只能输入500字哦</div>"
								+"<div class='clear'></div></div> "
			                    );
						$("#editContent").append(editContent);
						$("#updateType").val("9");
						$("#userIntroduction").focus();
					}
				}
			}
			function getCityName(level,item) {
  				var pid = $("#"+item).val();
  				$("#city"+level).empty();
  	    		$("#city"+level).append("<option value=''>--请选择--</option>");
  	    		if(level == "2") {
  	    			$("#city"+3).empty();
  		    		$("#city"+3).append("<option value=''>--请选择--</option>");
  	    		}
  				$.post("/portal/user!getCityName.action",{"cityMap.pid":pid},function(data){
  					if(data != "") {
  						$.each(data,function(i,item){
  			    				$("#city"+level).append("<option value='"+item.id+"' >"+item.name+"</option>");
  			    		});
  					}
  				},"json");
  			}
			function checkForm(){
				var updateType = $("#updateType").val();
				var toEditImg = "<img src='/images/to_edit.png' style='vertical-align: middle;' />";
				if(updateType == "") {
					alert("参数错误，请刷新再试！");
				}else {
					if(updateType == "1") {
						var nickname = $("#nickname").val();
						if(nickname != ""){
							$.post("/portal/user!checkNickName.action",{"user.nickname":nickname},function(data){
			    				if(data<0){
			    					$("#remind").text("该昵称已被占用")
			    				}else {
			    					$("#myForm").ajaxSubmit({
			    						 url: "/mine/member!update.action",
			    	   		    	     type: "Post",
			    	   		    	     success: function (data) {
			    	   		    	    	$("#myNickname").html(nickname+toEditImg);
			    	   		    	    	$("#editArea").hide();
			    	   		    	     }
			    					});
			    				}
			    			});
						}else {
							alert("参数错误，请刷新再试！");
						}
						
					}else if(updateType == "2"){
						var password = $("#password").val();
						var confirmPass = $("#confirmPass").val();
						if(password == ""||confirmPass == ""){
							alert("参数错误，请刷新再试！");
						}else {
							if(password != confirmPass){
								$("#remind").text("密码输入不一致，请重新输入");
							}else {
								$("#myForm").ajaxSubmit({
		    						 url: "/mine/member!update.action",
		    	   		    	     type: "Post",
		    	   		    	     success: function (data) {
		    	   		    	    	 if(data == "password"){
		    	   		    	    		 alert("密码修改成功，请重新登录");
		    	   		    	    		 loginOut();
		    	   		    	    	 }
		    	   		    	    	$("#editArea").hide();
		    	   		    	     }
		    					});
							}
						}
					}else if(updateType == "3"){
						$("#myForm").ajaxSubmit({
	   						 url: "/mine/member!update.action",
	   	   		    	     type: "Post",
	   	   		    	     success: function (data) {
	   	   		    	    	 $("#mySex").html(data+toEditImg);
	   	   		    	    	 $("#editArea").hide();
	   	   		    	     }
						});
					}else if(updateType == "4"){
						var birthday = $("#birthday").val();
						if(birthday == ""){
							$("#remind").text("请选择日期");
						}else {
							$("#myForm").ajaxSubmit({
		   						 url: "/mine/member!update.action",
		   	   		    	     type: "Post",
		   	   		    	     success: function (data) {
		   	   		    	    	 $("#myBirthday").html(birthday+toEditImg);
		   	   		    	    	 $("#editArea").hide();
		   	   		    	     }
							});
						}
					}else if(updateType == "5"){
						var babyBirthday = $("#babyBirthday").val();
						if(babyBirthday == ""){
							$("#remind").text("请选择日期");
						}else {
							$("#myForm").ajaxSubmit({
		   						 url: "/mine/member!update.action",
		   	   		    	     type: "Post",
		   	   		    	     success: function (data) {
		   	   		    	    	 $("#myBabyBirthday").html(babyBirthday+toEditImg);
		   	   		    	    	 $("#editArea").hide();
		   	   		    	     }
							});
						}
					}else if(updateType == "6"){
						var email = $("#email").val();
						if(email == ""){
							$("#remind").text("请填写正确的邮箱地址");
						}else {
							$("#myForm").ajaxSubmit({
		   						 url: "/mine/member!update.action",
		   	   		    	     type: "Post",
		   	   		    	     success: function (data) {
		   	   		    	    	 $("#myEmail").html(email+toEditImg);
		   	   		    	    	 $("#editArea").hide();
		   	   		    	     }
							});
						}
					}else if(updateType == "7"){
						var city1 = $("#city1").val();
						var city2 = $("#city2").val();
						var city3 = $("#city3").val();
						if(city1 == ""|| city2 == ""|| city3 == ""){
							$("#remind").text("请选择完整地域");
						}else {
							$("#myForm").ajaxSubmit({
		   						 url: "/mine/member!update.action",
		   	   		    	     type: "Post",
		   	   		    	     success: function (data) {
		   	   		    	    	 $("#myArea").html(data+toEditImg);
		   	   		    	    	 $("#editArea").hide();
		   	   		    	     }
							});
						}
					}else if(updateType == "8"){
						if($("#wchatNo").val()==""){
							$("#remind").text("请填写微信号");
							$("#wchatNo").focus();
						}else {
							$("#myForm").ajaxSubmit({
	    						 url: "/mine/member!update.action",
	    	   		    	     type: "Post",
	    	   		    	     success: function (data) {
	    	   		    	    	$("#myWchatNo").html($("#wchatNo").val()+toEditImg);
	    	   		    	    	$("#editArea").hide();
	    	   		    	     }
	    					});
						}
					}else if(updateType == "9"){
						var userIntroduction = $("#userIntroduction").val()
						if(userIntroduction != ""){
							if(userIntroduction.length > 500){
								$("#acountRemind").text("字数不能超过500哦").css("color","red");
							}else {
								$("#myForm").ajaxSubmit({
		    						 url: "/mine/member!update.action",
		    	   		    	     type: "Post",
		    	   		    	     success: function (data) {
		    	   		    	    	$("#introduction").val(userIntroduction);
		    	   		    	    	$("#editArea").hide();
		    	   		    	     }
		    					});
							}
						}else {
							$("#myForm").ajaxSubmit({
	    						 url: "/mine/member!update.action",
	    	   		    	     type: "Post",
	    	   		    	     success: function (data) {
	    	   		    	    	$("#introduction").val(userIntroduction);
	    	   		    	    	$("#editArea").hide();
	    	   		    	     }
	    					});
						}
					}
				}
			}
			function cleanSpan(item){
				item.val("");
			}
			function closeForm() {
				$("#editArea").hide();
			}
			function changeHead() {
				var userId = $("#userId").val();
				if(userId == null || userId == ""){
					alert("您还没有登录呢！");
				}else{
					$("#myForm").attr("action","/portal/user!setHead.action");
					$("#setHead").val(1);
					$("#myForm").submit();
				}
				
			}
			
	    	
	    
		
			function toLogin(){
				refreshImg();
				$("#loginArea").show();
			}
