function getCookie(c_name) {
	if (document.cookie.length > 0) {
		c_start = document.cookie.indexOf(c_name + "=")
		if (c_start != -1) {
			c_start = c_start + c_name.length + 1
			c_end = document.cookie.indexOf(";", c_start)
			if (c_end == -1)
				c_end = document.cookie.length
			return unescape(document.cookie.substring(c_start, c_end))
		}
	}
	return ""
}
function menuInit() {
	var index = null;
	index = getCookie("menuIndex");
	if(index!=null)
		menu(index);
}


function menu(menuId)
{
		document.cookie = "menuIndex=" + menuId;
		var menus = new Array();
		var sonmenus = new Array();
		var pass = true;
		for(var i=1;pass;i++)
		{
			var m = document.getElementById("menu"+i);
			var sm = document.getElementById("sonMenu"+i);
			if(m!=null)
			{
				menus[i-1] = m;
				sonmenus[i-1] = sm;
				if(m.id == menuId)
				{
					if(sm!=null){
						sm.style.display = (sm.style.display =="block"?"none":"block");
						m.className = (sm.style.display =="block"?"menus2":"menus");
					}
						
				}
				else
				{
					if(sm!=null)
						sm.style.display = "none";
				}
			}
			else
				pass = false;
		}
		
}
function over(menu)
{
	var index = getCookie("menuIndex");
	if(menu.id != index)
		{
			menu.className = (menu.className == "icon_bg1"?"icon_bg_over":"icon_bg1");
		}
}
