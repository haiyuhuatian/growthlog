
package cn.com.czrz.action;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import cn.com.czrz.common.Constants;
import cn.com.czrz.entity.Users;
import cn.com.gwypx.util.Utility;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

public class BaseAction extends ActionSupport implements Preparable
{
    private static final long serialVersionUID = 7516794290648979579L;

    public static final String ALERT = "alert";

    protected HttpServletRequest request;

    protected HttpServletResponse response;

    protected HttpSession session;

    protected ServletContext application;

    /**
     * 操作提示内容
     */
    protected String message;

    /**
     * 操作结果被记录到日志中
     */
    protected Integer logResult;

    /**
     * 操作提示后的跳转URL,为空则返回前一页
     */
    protected String redirectionUrl;

    @Override
    public void prepare() throws Exception
    {
        request = ServletActionContext.getRequest();
        response = ServletActionContext.getResponse();
        session = request.getSession();
        application = ServletActionContext.getServletContext();
    }

    /**
     * 设置 Response 对象，不允许浏览器缓存
     */
    protected void noCache()
    {
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        response.addHeader("Cache-Control", "no-cache");
        response.addHeader("Cache-Control", "no-store");
        response.addHeader("Cache-Control", "must-revalidate");
    }

    /**
     * 获得当前登录用户
     */
    protected Users getLoginUser()
    {
        return (Users) session.getAttribute(Constants.SESSION_USER_KEY);
    }

    protected boolean empty(Object o)
    {
        return Utility.empty(o);
    }

    /**
     * 获得当前Request真实的IP地址
     */
    public String getIpAddress()
    {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 转义XML/HTML字符
     */
    public String escapeXml(String s)
    {
        if (s == null)
            return null;

        StringBuilder sb = new StringBuilder();
        char[] chars = s.toCharArray();
        for (char c : chars)
        {
            if (c == '<')
                sb.append("&lt;");
            else if (c == '>')
                sb.append("&gt;");
            else if (c == '&')
                sb.append("&amp;");
            else if (c == '\'')
                sb.append("&#039;");
            else if (c == '"')
                sb.append("&#034;");
            else
                sb.append(c);
        }
        return sb.toString();
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public String getRedirectionUrl()
    {
        return redirectionUrl;
    }

    public void setRedirectionUrl(String redirectionUrl)
    {
        this.redirectionUrl = redirectionUrl;
    }
}
