
package cn.com.czrz.entity;

import java.util.Date;

import cn.com.gwypx.jdbc.annotation.Id;
import cn.com.gwypx.jdbc.annotation.Table;

@Table(name = "tb_user_login_track")
public class UserLoginTrack extends BaseEntity
{
    private static final long serialVersionUID = 6134987004222393590L;

    @Id
    private Integer id;

    private Integer user_id;

    private Date login_time;

    private Date logout_time;

    private long duration;

    private String user_agent;

    private String browser_type;

    private String ip;

    public static final String BROWSER_TYPE_OPEAR = "opear";

    public static final String BROWSER_TYPE_CHROME = "谷歌";

    public static final String BROWSER_TYPE_360 = "360";

    public static final String BROWSER_TYPE_IE6 = "IE6.0";

    public static final String BROWSER_TYPE_IE7 = "IE7.0";

    public static final String BROWSER_TYPE_IE8 = "IE8.0";

    public static final String BROWSER_TYPE_IE9 = "IE9.0";

    public static final String BROWSER_TYPE_FIREFOX = "火狐";

    public static final String BROWSER_TYPE_SAFARI = "Safari";

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getUser_id()
    {
        return user_id;
    }

    public void setUser_id(Integer userId)
    {
        user_id = userId;
    }

    public Date getLogin_time()
    {
        return login_time;
    }

    public void setLogin_time(Date loginTime)
    {
        login_time = loginTime;
    }

    public Date getLogout_time()
    {
        return logout_time;
    }

    public void setLogout_time(Date logoutTime)
    {
        logout_time = logoutTime;
    }

    public long getDuration()
    {
        return duration;
    }

    public void setDuration(long duration)
    {
        this.duration = duration;
    }

    public String getUser_agent()
    {
        return user_agent;
    }

    public void setUser_agent(String userAgent)
    {
        user_agent = userAgent;
    }

    public String getBrowser_type()
    {
        return browser_type;
    }

    public void setBrowser_type(String browserType)
    {
        browser_type = browserType;
    }

    public String getIp()
    {
        return ip;
    }

    public void setIp(String ip)
    {
        this.ip = ip;
    }

}
