
package cn.com.czrz.util;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.dispatcher.multipart.JakartaMultiPartRequest;

public class MyRequestParser extends JakartaMultiPartRequest
{
    @Override
    public void parse(HttpServletRequest servletRequest, String arg1)
            throws IOException
    {

    }
}
