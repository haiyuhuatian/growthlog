
package cn.com.czrz.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import cn.com.czrz.entity.ReadingQuantity;
import cn.com.czrz.service.DiaryService;

@SuppressWarnings("unused")
public class ReadingQuantityFilter implements Filter
{

    protected final static Logger logger = Logger.getLogger(ReadingQuantityFilter.class);

    private DiaryService diaryService = new DiaryService();

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
        String requestUrl = httpRequest.getRequestURI();
        if (requestUrl.contains("/member/diary!showDetail.action"))
        {
            String param = httpRequest.getParameter("diary.id");
            ReadingQuantity rq = new ReadingQuantity();
            rq.setDiary_id(Integer.parseInt(param));
            diaryService.saveReadingQuantity(rq);
        }
        chain.doFilter(httpRequest, httpResponse);
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException
    {
        // TODO Auto-generated method stub

    }

}
