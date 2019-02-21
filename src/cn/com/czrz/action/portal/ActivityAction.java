
package cn.com.czrz.action.portal;

import java.util.Date;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.struts2.convention.annotation.ParentPackage;

import cn.com.czrz.action.BaseAction;
import cn.com.czrz.common.Constants;

@ParentPackage("portal")
public class ActivityAction extends BaseAction
{

    private static final long serialVersionUID = -4186548140506928740L;

    public String list()
    {
        String noncestr = "activityList";
        String jsapi_ticket = (String) application.getAttribute("jsapi_ticket");
        long timestamp = new Date().getTime();
        String url = Constants.CZRZ_URL + "/portal/activity!list.action";
        String str = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + noncestr
                + "&timestamp=" + timestamp + "&url=" + url;
        String signature = DigestUtils.shaHex(str);
        request.setAttribute("noncestr", noncestr);
        request.setAttribute("timestamp", timestamp);
        request.setAttribute("signature", signature);
        request.setAttribute("appId", Constants.APPID);
        return "list";
    }

    public String resultShow()
    {
        return "result_show";
    }

}
