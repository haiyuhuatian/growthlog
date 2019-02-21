
package cn.com.czrz.service;

import java.io.Serializable;

import cn.com.gwypx.jdbc.JdbcTemplate;
import cn.com.gwypx.util.Utility;

public class BaseService implements Serializable
{
    private static final long serialVersionUID = 1L;

    protected JdbcTemplate jdbc = JdbcTemplate.INSTANCE;

    protected boolean empty(Object o)
    {
        return Utility.empty(o);
    }

    protected String escapeXml(String s)
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

}
