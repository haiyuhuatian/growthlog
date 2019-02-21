
package cn.com.czrz.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import cn.com.czrz.common.Constants;
import cn.com.czrz.entity.UserLoginTrack;
import cn.com.czrz.entity.Users;
import cn.com.czrz.service.UserService;

@SuppressWarnings("unused")
public class SecurityFilter implements Filter
{

    protected final static Logger logger = Logger.getLogger(SecurityFilter.class);

    private String endWith;

    private String[] urlPattern;

    private static String server;

    private UserService userService = new UserService();

    @Override
    public void destroy()
    {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException
    {
        request.setCharacterEncoding("UTF-8");
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        ServletContext application = httpRequest.getSession()
                .getServletContext();
        String loginUrl = httpRequest.getContextPath() + "/login.jsp";
        String requestUrl = httpRequest.getRequestURI();
        Users user = (Users) httpRequest.getSession().getAttribute(
                Constants.SESSION_USER_KEY);
        try
        {
            // 存在coolkies 用coolkies 登录

            Cookie[] cs = httpRequest.getCookies();
            String t = null;
            if (cs != null)
                for (Cookie c : cs)
                {
                    if ("ticket".equals(c.getName()))
                        t = c.getValue();
                }
            if (t != null)
            {
                if (user == null)
                {
                    String result = t;
                    // 登录失败跳到登录界面
                    if (result == null)
                    {
                        if (requestUrl.contains("/portal"))
                        {
                            chain.doFilter(request, response);
                            return;
                        }
                        else
                        {
                            setLogin(httpResponse, loginUrl);
                            return;
                        }
                    }
                    user = userService.loginByCookie(result);
                    // 登录失败跳到登录界面
                    if (user == null)
                    {
                        if (requestUrl.contains("/portal"))
                        {
                            chain.doFilter(request, response);
                            return;
                        }
                        else
                        {
                            setLogin(httpResponse, loginUrl);
                            return;
                        }
                    } // 将用户的登录ip 和浏览器信息放入实体中 方便做登录记录时调用
                    user.getTrack().setIp(getIpAddr(httpRequest));
                    user.getTrack().setUser_agent(
                            httpRequest.getHeader("user-agent"));
                    user.getTrack()
                            .setBrowser_type(
                                    getBrowserType(httpRequest.getHeader("user-agent")));
                    httpRequest.getSession().setAttribute(
                            Constants.SESSION_USER_KEY, user);
                }
            }

            if (user == null)
            {
                String userAgent = httpRequest.getHeader("user-agent");
                String[] strs = requestUrl.split("/");
                String url = strs[strs.length - 1];
                response.setCharacterEncoding("UTF-8");
                response.setContentType("text/html;charset=UTF-8");
                if (userAgent.indexOf("Android") != -1
                        || userAgent.indexOf("iPhone") != -1)
                {
                    loginUrl = httpRequest.getContextPath()
                            + "/phone/index!login.action";
                }
                response.getWriter().print(
                        "<script>alert('请您登录');parent.location.href='"
                                + loginUrl + "';</script>");
                return;
            }
            chain.doFilter(httpRequest, httpResponse);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("拦截器错误");
            setLogin(httpResponse, loginUrl);
            return;
        }
    }

    private boolean isFilter(String requestUrl)
    {

        for (String prefix : urlPattern)
        {
            if (requestUrl.startsWith(prefix) && requestUrl.endsWith(endWith))
                return true;
        }

        return false;
    }

    private void setLogin(HttpServletResponse httpResponse, String paramUrl)
            throws IOException
    {
        httpResponse.setHeader("Pragma", "no-cache");
        httpResponse.setDateHeader("Expires", 0);
        httpResponse.addHeader("Cache-Control", "no-cache");
        httpResponse.addHeader("Cache-Control", "no-store");
        httpResponse.addHeader("Cache-Control", "must-revalidate");
        httpResponse.setCharacterEncoding("UTF-8");
        httpResponse.setContentType("text/html;charset=UTF-8");
        httpResponse.getWriter().print(
                "<script>parent.location.href='" + paramUrl + "';</script>");
    }

    @Override
    public void init(FilterConfig config) throws ServletException
    {
        endWith = config.getInitParameter("endWith");
        String urlTmp = config.getInitParameter("urlPattern");
        server = config.getInitParameter("server");
    }

    public static String getServer()
    {
        return server;
    }

    /**
     * 根据request获得ip地址
     * 
     * @param request
     * @return
     */
    private String getIpAddr(HttpServletRequest request)
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
     * 根据user-agent中的内容返回浏览器的类型
     * 
     * @param userAgent
     * @return
     */
    private String getBrowserType(String userAgent)
    {
        userAgent = userAgent.toLowerCase();
        // 包含 opera 的就是Opera浏览器
        if (userAgent.indexOf("opera") != -1)
        {
            return UserLoginTrack.BROWSER_TYPE_OPEAR;
        }
        // Chrome 谷歌浏览器
        if (userAgent.indexOf("chrome") != -1)
        {
            return UserLoginTrack.BROWSER_TYPE_CHROME;
        }
        if (userAgent.indexOf("safari") != -1)
        {
            return UserLoginTrack.BROWSER_TYPE_SAFARI;
        }
        if (userAgent.indexOf("360") != -1)
        {
            return UserLoginTrack.BROWSER_TYPE_360;
        }
        if (userAgent.indexOf("msie 6.0") != -1)
        {
            return UserLoginTrack.BROWSER_TYPE_IE6;
        }
        if (userAgent.indexOf("msie 7.0") != -1)
        {
            return UserLoginTrack.BROWSER_TYPE_IE7;
        }
        if (userAgent.indexOf("msie 8.0") != -1)
        {
            return UserLoginTrack.BROWSER_TYPE_IE8;
        }
        if (userAgent.indexOf("msie 9.0") != -1)
        {
            return UserLoginTrack.BROWSER_TYPE_IE9;
        }
        if (userAgent.indexOf("firefox") != -1)
        {
            return UserLoginTrack.BROWSER_TYPE_FIREFOX;
        }
        return "其他";
    }

}
