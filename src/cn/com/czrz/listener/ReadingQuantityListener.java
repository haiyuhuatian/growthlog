
package cn.com.czrz.listener;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import cn.com.czrz.service.CommonService;

@SuppressWarnings("unchecked")
public class ReadingQuantityListener implements ServletContextListener
{

    @Override
    public void contextDestroyed(ServletContextEvent event)
    {
    }

    @Override
    public void contextInitialized(final ServletContextEvent event)
    {
        ScheduledThreadPoolExecutor ex = new ScheduledThreadPoolExecutor(2);

        ex.scheduleWithFixedDelay(new Runnable()
        {

            @Override
            public void run()
            {
                // 五分钟更新一次阅读量
                CommonService commonService = new CommonService();
                commonService.refreshReadingQuantity();
            }
        }, 5, 5, TimeUnit.MINUTES);

    }

}
