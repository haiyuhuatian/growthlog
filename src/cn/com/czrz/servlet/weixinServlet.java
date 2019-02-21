
package cn.com.czrz.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;

public class weixinServlet extends HttpServlet
{

    private static final long serialVersionUID = -4460065795236685688L;

    public static final String TOKEN = "czrzToken";

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response)
            throws IOException
    {
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String signature = request.getParameter("signature");
        String echostr = request.getParameter("echostr");
        String[] params = new String[]{TOKEN, timestamp, nonce};
        Arrays.sort(params);
        // 将三个参数字符串拼接成一个字符串进行sha1加密
        String clearText = params[0] + params[1] + params[2];
        String sign = DigestUtils.shaHex(clearText);
        PrintWriter out = response.getWriter();
        if (signature.equals(sign))
        {
            out.print(echostr);
        }
        out.flush();
        out.close();
    }

    @Override
    public void init() throws ServletException
    {

    }

}
