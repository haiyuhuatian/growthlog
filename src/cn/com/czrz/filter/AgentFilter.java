
package cn.com.czrz.filter;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import cn.com.czrz.entity.UserLoginTrack;
import cn.com.czrz.service.UserService;

@SuppressWarnings("unused")
public class AgentFilter implements Filter
{

    protected final static Logger logger = Logger.getLogger(AgentFilter.class);

    private String endWith;

    private String[] urlPattern;

    private static String server;

    private UserService userService = new UserService();

    // \b 是单词边界(连着的两个(字母字符 与 非字母字符) 之间的逻辑上的间隔),
    // 字符串在编译时会被转码一次,所以是 "\\b"
    // \B 是单词内部逻辑间隔(连着的两个字母字符之间的逻辑上的间隔)
    static String phoneReg = "\\b(ip(hone|od)|android|opera m(ob|in)i"
            + "|windows (phone|ce)|blackberry"
            + "|s(ymbian|eries60|amsung)|p(laybook|alm|rofile/midp"
            + "|laystation portable)|nokia|fennec|htc[-_]"
            + "|mobile|up.browser|[1-4][0-9]{2}x[1-4][0-9]{2})\\b";

    static String tableReg = "\\b(ipad|tablet|(Nexus 7)|up.browser"
            + "|[1-4][0-9]{2}x[1-4][0-9]{2})\\b";

    // 移动设备正则匹配：手机端、平板
    static Pattern phonePat = Pattern.compile(phoneReg,
            Pattern.CASE_INSENSITIVE);

    static Pattern tablePat = Pattern.compile(tableReg,
            Pattern.CASE_INSENSITIVE);

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
        String requestUrl = httpRequest.getRequestURI();

        try
        {
            String userAgent = httpRequest.getHeader("USER-AGENT")
                    .toLowerCase();
            if (null == userAgent)
            {
                userAgent = "";
            }
            boolean isFromMobile = false;
            isFromMobile = check(userAgent);
            if (!isFromMobile)
            {
                requestUrl = "/portal/index!index.action";
            }
            /*
             * String[] strs = requestUrl.split("/"); String url =
             * strs[strs.length - 1]; if (userAgent.indexOf("Android") != -1 ||
             * userAgent.indexOf("iPhone") != -1) {
             * response.setCharacterEncoding("UTF-8");
             * response.setContentType("text/html;charset=UTF-8");
             * response.getWriter() .print(
             * "<div style='width:100%;height:200px;margin-top:200px;text-align:center;'>"
             * + "<a style='font-size:40px;' href='/phone/index!index.action'>"
             * + "点击访问触屏版</a></div>"); return; }
             */
            chain.doFilter(httpRequest, httpResponse);

        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("拦截器错误");
            return;
        }
    }

    public static boolean check(String userAgent)
    {
        if (null == userAgent)
        {
            userAgent = "";
        }
        // 匹配
        Matcher matcherPhone = phonePat.matcher(userAgent);
        Matcher matcherTable = tablePat.matcher(userAgent);
        if (matcherPhone.find() || matcherTable.find())
        {
            return true;
        }
        else
        {
            return false;
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
