
package cn.com.czrz.filter;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.com.czrz.common.Constants;
import cn.com.czrz.entity.Users;
import cn.com.czrz.service.UserService;
import cn.com.czrz.util.WeixinUtil;

public class LoginFilter implements Filter
{
    private UserService userService = new UserService();

    @Override
    public void destroy()
    {
        // TODO Auto-generated method stub

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
        Users user = (Users) httpRequest.getSession().getAttribute(
                Constants.SESSION_USER_KEY);
        String openid = (String) httpRequest.getSession()
                .getAttribute("openid");
        // 如果用户点了退出登录则不作自动登录
        String loginOut = (String) httpRequest.getSession().getAttribute(
                "loginOut");
        String requestUrl = httpRequest.getRequestURI();
        if (loginOut == null && (user == null || openid == null))
        {
            String code = request.getParameter("code");
            if (code == null || code == "")
            {
                Enumeration<String> paramNames = httpRequest.getParameterNames();
                String params = "";
                for (Enumeration e = paramNames; e.hasMoreElements();)
                {

                    String thisName = e.nextElement().toString();
                    String thisValue = request.getParameter(thisName);
                    if (params != "")
                    {
                        params = params + "&" + thisName + "=" + thisValue;
                    }
                    else
                    {
                        params = thisName + "=" + thisValue;
                    }

                }
                String redirectUrl = Constants.CZRZ_URL + requestUrl;
                if (params != "")
                {
                    redirectUrl = redirectUrl + "?" + params;
                }
                redirectUrl = URLEncoder.encode(redirectUrl, "UTF-8");
                String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="
                        + Constants.APPID
                        + "&redirect_uri="
                        + redirectUrl
                        + "&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";
                httpResponse.sendRedirect(url);
            }
            else
            {
                String infoUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="
                        + Constants.APPID
                        + "&secret="
                        + Constants.APPSECRET
                        + "&code=" + code + "&grant_type=authorization_code";
                try
                {
                    Map infoMap = WeixinUtil.https(infoUrl, null);
                    openid = infoMap.get("openid").toString();
                    if (openid != "" && openid != null)
                    {
                        user = userService.findByOpenid(openid);
                        if (user != null)
                        {
                            httpRequest.getSession().setAttribute(
                                    Constants.SESSION_USER_KEY, user);
                        }
                        httpRequest.getSession().setAttribute("openid", openid);
                    }
                    chain.doFilter(httpRequest, httpResponse);
                }
                catch (Exception e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        else
        {
            chain.doFilter(httpRequest, httpResponse);
        }
        /*
         * try { HttpsUtil.httpsRequest(url, "GET", null); } catch (Exception
         * e1) { // TODO Auto-generated catch block e1.printStackTrace(); }
         */
        /*
         * try { Users user = (Users) httpRequest.getSession().getAttribute(
         * Constants.SESSION_USER_KEY); if (user == null) { Cookie[] cs =
         * httpRequest.getCookies(); String loginName = ""; if (cs != null) {
         * for (Cookie c : cs) { if ("ticket".equals(c.getName())) { String s =
         * c.getValue(); loginName = DesUtil.decrypt(s,
         * Constants.LOGINNAME_KEY); } } } if (loginName != "") { loginName =
         * loginName.substring(5); user =
         * userService.findByLoginName(loginName); if (user != null) {
         * httpRequest.getSession().setAttribute( Constants.SESSION_USER_KEY,
         * user); } } } chain.doFilter(httpRequest, httpResponse); } catch
         * (Exception e) { // TODO Auto-generated catch block
         * e.printStackTrace(); }
         */
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException
    {
        // TODO Auto-generated method stub

    }

}
