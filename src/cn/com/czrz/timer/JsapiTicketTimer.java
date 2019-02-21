
package cn.com.czrz.timer;

import java.util.Map;
import java.util.TimerTask;

import javax.servlet.ServletContext;

import cn.com.czrz.common.Constants;
import cn.com.czrz.util.WeixinUtil;

public class JsapiTicketTimer extends TimerTask
{
    private ServletContext sc;

    public JsapiTicketTimer(ServletContext sc)
    {
        this.sc = sc;
    }

    @Override
    public void run()
    {
        try
        {
            String accessToken = (String) sc.getAttribute("access_token");
            Map jsapi_ticket_map = WeixinUtil.https(Constants.JSAPI_TICKET_URL
                    + "&access_token=" + accessToken, null);
            String jsapi_ticket = (String) jsapi_ticket_map.get("ticket");
            sc.setAttribute("jsapi_ticket", jsapi_ticket);
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
