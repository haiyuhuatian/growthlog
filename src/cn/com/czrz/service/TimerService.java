
package cn.com.czrz.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

public class TimerService extends BaseService
{

    private static final long serialVersionUID = -264828110507341600L;

    // 每隔10分钟统计一次当月点赞数，并更新排行榜
    public void refreshLikeCount()
    {
        String sql = "SELECT diary_id,COUNT(id) num FROM diary_like GROUP BY diary_id";
        List list = jdbc.query(sql);
        if (!empty(list) && list.size() > 0)
        {
            for (Object o : list)
            {
                Map map = (Map) o;
                jdbc.update(
                        "update diary set current_month_likes =  ? where id = ?",
                        new Object[]{map.get("num"), map.get("diary_id")});
            }
        }
        sql = "SELECT comment_id,COUNT(id) num FROM comment_like   GROUP BY comment_id";
        List list2 = jdbc.query(sql);
        if (!empty(list2) && list2.size() > 0)
        {
            for (Object o : list2)
            {
                Map m = (Map) o;
                jdbc.update(
                        "update diary_comment set current_month_likes = current_month_likes + ? where id = ?",
                        new Object[]{m.get("num"), m.get("comment_id")});
            }
        }
        Map rule = (Map) jdbc.find("select table_name,rank_column,begin_time,end_time from rank_rules where rank_status = 1");
        if (!empty(rule))
        {
            String tableName = rule.get("table_name").toString();
            String rankColumn = rule.get("rank_column").toString();
            if (tableName.equals("diary") && rankColumn.equals("like_count"))
            {
                List diaryLikeRank = jdbc.query(
                        "SELECT id,user_id,title,content,(like_count+current_month_likes) "
                                + "acount,images FROM diary WHERE like_count > 0 and openness = 3 AND create_date >= ? "
                                + "AND create_date <= ? ORDER BY acount DESC limit 15",
                        new Object[]{rule.get("begin_time"),
                                rule.get("end_time")});
                if (!empty(diaryLikeRank) && diaryLikeRank.size() > 0)
                {
                    jdbc.update("truncate table rank_list");
                }
                Gson gson = new Gson();
                for (int i = 0; i < diaryLikeRank.size(); i++)
                {
                    Map m = (Map) diaryLikeRank.get(i);
                    Map showColumn = new HashMap();
                    showColumn.put("id", m.get("id").toString());
                    showColumn.put("title", m.get("title").toString());
                    showColumn.put("content", m.get("content").toString());
                    showColumn.put("user_id", m.get("user_id").toString());
                    if (!empty(m.get("images")))
                    {
                        String images = m.get("images").toString();
                        String[] imgs = gson.fromJson(images, String[].class);
                        showColumn.put("cover", imgs[0]);
                    }
                    String columns = gson.toJson(showColumn);
                    int rankId = i + 1;
                    Long acount = Long.parseLong(m.get("acount").toString());
                    jdbc.update(
                            "INSERT INTO rank_list (id,show_columns,acount,create_time) VALUES (?,?,?,NOW())",
                            new Object[]{rankId, columns, acount});
                }
            }
            else if (tableName.equals("diary_comment")
                    && rankColumn.equals("like_count"))
            {
                List commentLikeRank = jdbc.query(
                        "SELECT id,user_id,nickname,diary_id,pid,CODE,content,(like_count+current_month_likes) "
                                + "acount FROM diary_comment WHERE like_count >0 "
                                + "AND create_time >= ? AND create_time <= ? ORDER BY acount DESC LIMIT 15",
                        new Object[]{rule.get("begin_time"),
                                rule.get("end_time")});
                if (!empty(commentLikeRank) && commentLikeRank.size() > 0)
                {
                    jdbc.update("truncate table rank_list");
                }
                for (int i = 0; i < commentLikeRank.size(); i++)
                {
                    Map m = (Map) commentLikeRank.get(i);
                    Map showColumn = new HashMap();
                    showColumn.put("id", m.get("id").toString());
                    showColumn.put("user_id", m.get("user_id").toString());
                    showColumn.put("nickname", m.get("nickname").toString());
                    showColumn.put("diary_id", m.get("diary_id").toString());
                    showColumn.put("pid", m.get("pid").toString());
                    showColumn.put("code", m.get("code").toString());
                    Gson gson = new Gson();
                    String columns = gson.toJson(showColumn);
                    int rankId = i + 1;
                    Long acount = Long.parseLong(m.get("acount").toString());
                    jdbc.update(
                            "INSERT INTO rank_list (id,show_columns,acount,create_time) VALUES (?,?,?,NOW())",
                            new Object[]{rankId, columns, acount});
                }
            }
        }
    }

    // 每月1号清除上个月的点赞数据
    public void clearLike(String nowDate)
    {
        jdbc.update("RENAME TABLE diary_like TO diary_like_old");
        jdbc.update("delete from diary_like1_old where create_time < ?",
                nowDate);
        String createStr = "CREATE TABLE `diary_like` ("
                + "`id` int(11) NOT NULL AUTO_INCREMENT,"
                + "`diary_id` int(11) NOT NULL,"
                + "`user_id` int(11) NOT NULL,"
                + "`create_time` datetime DEFAULT NULL,"
                + "PRIMARY KEY (`id`),"
                + "KEY `ref-user` (`user_id`),"
                + "KEY `FK_diary_like` (`diary_id`),"
                + "CONSTRAINT `FK_diary_like` FOREIGN KEY (`diary_id`) REFERENCES `diary` (`id`)"
                + ") ENGINE=InnoDB  DEFAULT CHARSET=utf8";
        jdbc.update(createStr);
        jdbc.update("insert into diary_like (id,diary_id,user_id,crete_time) values (select null,diary_id,user_id,create_time from diary_like_old)");
        jdbc.update("drop table diary_like_old from ");
        jdbc.update("RENAME TABLE comment_like TO comment_like_old");
        jdbc.update("delete from comment_like_old where create_time < ?",
                nowDate);
        createStr = "CREATE TABLE `comment_like` ("
                + "`id` int(11) NOT NULL AUTO_INCREMENT,"
                + "`comment_id` int(11) NOT NULL,"
                + "`user_id` int(11) NOT NULL,"
                + "`create_time` datetime NOT NULL,"
                + "PRIMARY KEY (`id`),"
                + "KEY `FK_comment_like_1` (`comment_id`),"
                + "KEY `FK_comment_like_2` (`user_id`),"
                + " CONSTRAINT `FK_comment_like_1` FOREIGN KEY (`comment_id`) REFERENCES `diary_comment` (`id`),"
                + "CONSTRAINT `FK_comment_like_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)"
                + ") ENGINE=InnoDB  DEFAULT CHARSET=utf8";
        jdbc.update(createStr);
        jdbc.update("insert into comment_like (id,comment_id,user_id,crete_time) values (select null,comment_id,user_id,create_time from comment_like_old)");
        jdbc.update("drop table diary_like_old");
    }

    // 获取状态为正常的排行规则
    public Map getCurrentRank()
    {
        return (Map) jdbc.find("SELECT id,begin_time,end_time,winner_num FROM rank_rules WHERE rank_status = 1");
    }

    // 关闭已经过期的排行
    public void closeRank(String id, String winnerNum)
    {
        jdbc.update("update rank_rules set rank_status = 2 where id = ?", id);
        List winnerList = jdbc.query(
                "select show_columns,acount from rank_list order by id asc limit ?",
                winnerNum);
        List winnerResult = new ArrayList();
        Gson gson = new Gson();
        if (!empty(winnerList) && winnerList.size() > 0)
        {
            for (Object o : winnerList)
            {
                Map map = (Map) o;
                String showColumns = map.get("show_columns").toString();
                Map columns = gson.fromJson(showColumns, Map.class);
                String modelId = columns.get("id").toString();
                Map winnerMap = new HashMap();
                winnerMap.put("id", modelId);
                winnerMap.put("acount", map.get("acount").toString());
                winnerResult.add(winnerMap);
            }
        }
        if (!empty(winnerResult) && winnerResult.size() > 0)
        {
            String winners = gson.toJson(winnerResult);
            jdbc.update("update rank_rules set winner = ? where id = ?",
                    new Object[]{winners, id});
        }
    }

    public void clearIndexDiary(Integer number)
    {
        List acountList = jdbc.query("select model,count(id) acount from index_diary group by model order by model asc");
        if (!empty(acountList))
        {
            for (Object o : acountList)
            {
                Map aMap = (Map) o;
                Integer acount = Integer.parseInt(aMap.get("acount").toString());
                if (acount > number)
                {
                    Integer deleteNum = acount - number;
                    jdbc.update(
                            "delete from index_diary where model = ? order by id asc limit ?",
                            new Object[]{aMap.get("model"), deleteNum});
                }

            }
        }
    }
}
