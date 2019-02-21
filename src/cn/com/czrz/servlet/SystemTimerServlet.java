
package cn.com.czrz.servlet;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.com.czrz.service.TimerService;

public class SystemTimerServlet extends HttpServlet
{
    private static final long serialVersionUID = -3249601792080728687L;

    private TimerService timerService = new TimerService();

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        System.out.println("开始执行");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DATE);
        String begin = year + "-" + month + "-" + day + " 00:00:00";
        String end = year + "-" + month + "-" + day + " 23:59:59";
        // 关闭已经过期的排行规则
        Map currentRank = timerService.getCurrentRank();
        String rankEnd = currentRank.get("end_time").toString();
        try
        {
            Date rankEndDate = sdf.parse(rankEnd);
            /**
             * 日期格式是按当天的00:00:00算的，而生活中习惯按当年晚上23:59:59算结束，
             * 比如结束日期设为6月30日，则应该是倒6月30日晚上23:59:59分结束，而不是6月30日的00:00:00，
             * 所以这里加上23小时59分59秒
             **/
            long endTime = rankEndDate.getTime() + 86399 * 1000;
            long nowTime = new Date().getTime();
            if (endTime <= nowTime)
            {
                timerService.closeRank(currentRank.get("id").toString(),
                        currentRank.get("winner_num").toString());
            }
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        if (day == 1)
        {
            timerService.clearLike(begin);
        }
        // 清理首页显示日志表，目前是超过100条就把超过的部分删除，以后再改
        timerService.clearIndexDiary(200);
        System.out.println("执行结束");
    }
}
