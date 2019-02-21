
package cn.com.czrz.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Constants
{
    private static Properties properties = new Properties();

    public static final String SESSION_USER_KEY = "loginUser";

    public static final String BEFORE_SALT = "czrz";

    public static final String DEFAULT_PASSWORD = "woaiczrz";

    /**
     * 用户登录列表存储Key
     */
    public static String USER_LOGIN_LIST = "userLoginList";

    public static String LOGINNAME_KEY = "om$6@.z2$sW8A1eo";

    public static String IMAGE_CODE = "imageCode";

    public static String APPID = "wx39e604997e58ca91";

    public static String APPSECRET = "ca7f416790e3494af3e0f526067f0dc2";

    public static String CZRZ_URL = "http://www.growthlog.cn";

    public static String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
            + APPID + "&secret=" + APPSECRET;

    public static String JSAPI_TICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?type=jsapi";

    public static String MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=";

    public static String CODE_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="
            + APPID + "&response_type=code&scope=snsapi_userinfo&state=123";
    static
    {
        try
        {
            InputStream in = Constants.class.getClassLoader()
                    .getResourceAsStream("config.properties");
            properties.load(in);
            in.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static String getProperty(String key)
    {
        return properties.getProperty(key);
    }
}
