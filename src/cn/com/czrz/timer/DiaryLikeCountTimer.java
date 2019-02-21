
package cn.com.czrz.timer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

import javax.servlet.ServletContext;

import cn.com.czrz.entity.RankRules;
import cn.com.czrz.service.RankService;
import cn.com.czrz.service.TimerService;

import com.google.gson.Gson;

public class DiaryLikeCountTimer extends TimerTask
{
    private ServletContext sc;

    private TimerService timerService = new TimerService();

    private RankService rankService = new RankService();

    public DiaryLikeCountTimer(ServletContext sc)
    {
        this.sc = sc;
    }

    @Override
    public void run()
    {
        try
        {
            timerService.refreshLikeCount();
            RankRules rr = rankService.get();
            if (rr.getTable_name().equals("diary")
                    && rr.getRank_column().equals("like_count"))
            {
                List rankList = rankService.getRankList();
                List result = new ArrayList();
                if (rankList != null && rankList.size() > 0)
                {
                    Gson gson = new Gson();
                    for (Object o : rankList)
                    {
                        Map map = (Map) o;
                        String showColumns = map.get("show_columns").toString();
                        Map columns = gson.fromJson(showColumns, Map.class);
                        columns.put("acount", map.get("acount").toString());
                        result.add(columns);
                    }
                }
                sc.setAttribute("rank_list", result);
            }
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
