
package cn.com.czrz.timer;

import java.util.Map;
import java.util.TimerTask;

import javax.servlet.ServletContext;

import cn.com.czrz.common.Constants;
import cn.com.czrz.util.WeixinUtil;

public class AccessTokenTimer extends TimerTask
{
    private ServletContext sc;

    public AccessTokenTimer(ServletContext sc)
    {
        this.sc = sc;
    }

    @Override
    public void run()
    {
        try
        {
            Map access_token_map = WeixinUtil.https(Constants.ACCESS_TOKEN_URL,
                    null);
            sc.setAttribute("access_token",
                    access_token_map.get("access_token").toString());
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
