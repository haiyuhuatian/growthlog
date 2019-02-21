
package cn.com.czrz.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.czrz.entity.CommentLike;
import cn.com.czrz.entity.Diary;
import cn.com.czrz.entity.RankList;
import cn.com.czrz.entity.ReadingQuantity;

import com.google.gson.Gson;

public class DiaryService extends BaseService
{

    private static final long serialVersionUID = 1941794101570922457L;

    public void save(Diary diary)
    {
        jdbc.saveEntity(diary);
    }

    public Diary get(Integer id)
    {
        return (Diary) jdbc.loadEntity(Diary.class, id);
    }

    public void update(Diary diary)
    {
        jdbc.updateEntity(diary);
    }

    public List getMenuInfo(Integer userId)
    {
        List yearList = jdbc.query(
                "SELECT DISTINCT YEAR(diary_date) year FROM diary WHERE user_id = ? order by diary_date desc",
                userId);
        List result = new ArrayList();
        if (!empty(yearList))
        {
            for (Object o : yearList)
            {
                Map map = (Map) o;
                String year = map.get("year").toString();
                List monthList = jdbc.query(
                        "SELECT DISTINCT MONTH(diary_date) month FROM diary "
                                + "WHERE user_id = ? and YEAR(diary_date) = ? order by diary_date desc",
                        new Object[]{userId, year});
                if (!empty(monthList))
                {
                    for (Object b : monthList)
                    {
                        Map m = (Map) b;
                        m.put("number", year + m.get("month").toString());
                    }
                    Map rMap = new HashMap();
                    rMap.put("year", year);
                    rMap.put("months", monthList);
                    result.add(rMap);
                }
            }
        }
        return result;
    }

    public List getDiaryList(Integer userId, String diaryYear, String diaryMonth)
            throws ParseException
    {
        List result = new ArrayList();
        if (diaryYear == null && diaryMonth == null)
        {
            List list = jdbc.query(
                    "SELECT YEAR(diary_date) year,MONTH(diary_date) month FROM diary WHERE "
                            + "user_id = ? ORDER BY diary_date DESC LIMIT 0,1",
                    userId);
            if (!empty(list))
            {
                Map map = (Map) list.get(0);
                result = jdbc.query(
                        "SELECT d.id,d.title,d.content,d.TYPE,d.diary_date,d.weekday,d.weather,i.NAME image "
                                + "FROM diary d  "
                                + "WHERE d.user_id = ? AND YEAR(d.diary_date) = ? AND MONTH(d.diary_date) = ? order by d.diary_date desc",
                        new Object[]{userId, map.get("year"), map.get("month")});
            }
        }
        else
        {
            result = jdbc.query(
                    "SELECT d.id,d.title,d.content,d.TYPE,d.diary_date,d.weekday,d.weather  "
                            + "FROM diary d  "
                            + "WHERE d.user_id = ? AND YEAR(d.diary_date) = ? AND MONTH(d.diary_date) = ? order by d.diary_date desc",
                    new Object[]{userId, diaryYear, diaryMonth});
        }
        if (!empty(result))
        {
            for (Object o : result)
            {
                Map map = (Map) o;
                String cover = jdbc.queryString(
                        "select name from diary_images where diary_id = ? order by id asc limit 1",
                        map.get("id"));
                map.put("image", cover);
            }

        }
        return result;
    }

    public List getDiaryListForMobile(Integer userId, String diaryYear,
            String diaryMonth)
    {
        List result = new ArrayList();
        if (diaryYear == null && diaryMonth == null)
        {
            List list = jdbc.query(
                    "SELECT YEAR(diary_date) year,MONTH(diary_date) month FROM diary WHERE "
                            + "user_id = ? ORDER BY diary_date DESC LIMIT 0,1",
                    userId);
            if (!empty(list))
            {
                Map map = (Map) list.get(0);
                result = jdbc.query(
                        "SELECT d.id,d.title,d.content,d.TYPE,day(d.diary_date) diaryDay,month(d.diary_date) diaryMonth,d.weekday,d.weather,"
                                + ",d.images FROM diary d  "
                                + "WHERE d.user_id = ? AND YEAR(d.diary_date) = ? AND MONTH(d.diary_date) = ? order by d.diary_date desc",
                        new Object[]{userId, map.get("year"), map.get("month")});
            }
        }
        else
        {
            result = jdbc.query(
                    "SELECT d.id,d.title,d.content,d.TYPE,day(d.diary_date) diaryDay,month(d.diary_date) diaryMonth,d.weekday,d.weather  "
                            + " ,d.images FROM diary d  "
                            + "WHERE d.user_id = ? AND YEAR(d.diary_date) = ? AND MONTH(d.diary_date) = ? order by d.diary_date desc",
                    new Object[]{userId, diaryYear, diaryMonth});
        }
        if (!empty(result))
        {
            for (Object o : result)
            {
                Map diary = (Map) o;
                if (!empty(diary.get("images")))
                {
                    String images = diary.get("images").toString();
                    Gson gson = new Gson();
                    String[] imgs = gson.fromJson(images, String[].class);
                    if (imgs.length > 4)
                    {
                        String[] showImgs = new String[4];
                        showImgs[0] = imgs[0];
                        showImgs[1] = imgs[1];
                        showImgs[2] = imgs[2];
                        showImgs[3] = imgs[3];
                        diary.put("images", showImgs);
                        diary.put("imgNum", 4);
                    }
                    else
                    {
                        diary.put("images", imgs);
                        diary.put("imgNum", imgs.length);
                    }
                }
                else
                {
                    diary.put("imgNum", 0);
                }
            }

        }
        return result;
    }

    public void delete(Integer id)
    {
        jdbc.update("delete from reading_quantity where diary_id = ?", id);
        jdbc.update(
                "DELETE FROM comment_like WHERE comment_id IN (SELECT id FROM diary_comment WHERE diary_id = ?)",
                id);
        jdbc.update("delete from diary_comment where diary_id = ?", id);
        jdbc.update("delete from diary_like where diary_id = ?", id);
        jdbc.update("delete from diary where id = ?", id);
    }

    /** searchType用来区分是加载数据时进入的方法还是index方法进入的，主要用于返回首页时定位 **/
    public List getIndexDiaryList(Integer type, Integer userId,
            Integer lastDiaryId, Integer firstDiaryId, String searchType,
            Integer model)
    {
        String sql = "SELECT a.id aid,d.id,d.user_id uid,d.title,d.content,d.TYPE  "
                + ",d.images  FROM  index_diary a join diary d on a.diary_id = d.id  where 1=1 ";

        if (lastDiaryId > 0 && empty(searchType))
        {
            sql += " and a.id < " + lastDiaryId;
        }
        if (lastDiaryId > 0 && !empty(searchType))
        {
            sql += " and a.id <= " + lastDiaryId;
        }
        if (firstDiaryId > 0)
        {
            sql += " and a.id > " + firstDiaryId;
        }
        sql += " and d.openness = 3";
        if (!empty(userId))
        {
            sql = "SELECT a.id aid,d.id,d.user_id uid,d.title,d.content,d.TYPE,dl.id likeId "
                    + ",d.images from index_diary a join diary d on a.diary_id = d.id "
                    + "  left join diary_like dl on dl.diary_id = d.id and dl.user_id ="
                    + userId + " where 1=1  ";
            if (lastDiaryId > 0 && empty(searchType))
            {
                sql += " and a.id < " + lastDiaryId;
            }
            if (lastDiaryId > 0 && !empty(searchType))
            {
                sql += " and a.id <= " + lastDiaryId;
            }
            if (firstDiaryId > 0)
            {
                sql += " and a.id > " + firstDiaryId;
            }
            sql += " and d.openness = 3 ";
        }
        if (!empty(type) && type != 0)
        {
            sql += " and d.type = " + type;
        }
        if (model != 0)
        {
            sql += " and a.model = " + model;
        }
        sql += " ORDER BY a.id DESC ";
        sql += " limit 20";
        List list = jdbc.query(sql);
        if (!empty(list))
        {
            for (Object o : list)
            {
                Map map = (Map) o;
                Integer count = jdbc.queryInt(
                        "select count(*) from diary_like dl "
                                + "join diary d on dl.diary_id = d.id where dl.diary_id=?",
                        map.get("id"));
                if (!empty(map.get("images")))
                {
                    String images = map.get("images").toString();
                    Gson gson = new Gson();
                    String[] diaryImages = gson.fromJson(images, String[].class);
                    String cover = diaryImages[0];
                    map.put("image", cover);
                }
                map.put("likeCount", count);
            }
        }
        return list;
    }

    public void saveLike(CommentLike like)
    {
        jdbc.saveEntity(like);
    }

    public Integer getCommentNum(Integer diaryId)
    {
        return jdbc.queryInt(
                "select count(id) from diary_comment where diary_id = ?",
                diaryId);
    }

    public void saveReadingQuantity(ReadingQuantity rq)
    {
        jdbc.saveEntity(rq);
    }

    public List getAllIndexShows(Integer userId)
    {
        List list = jdbc.query(
                "SELECT id,title,diary_date FROM diary WHERE openness = 3 AND user_id = ? ORDER BY diary_date DESC limit 5",
                userId);
        if (!empty(list))
        {
            for (Object o : list)
            {
                Map map = (Map) o;
                String cover = jdbc.queryString(
                        "select name from diary_images where diary_id = ? order by id asc limit 1",
                        map.get("id"));
                map.put("cover", cover);
            }
        }
        return list;
    }

    public Integer getLikeCount(Integer diaryId)
    {
        return jdbc.queryInt(
                "select count(*) from diary_like where diary_id = ?", diaryId);
    }

    public Integer getDiaryLikeId(Integer diaryId, Integer userId)
    {
        return jdbc.queryInt(
                "select id from diary_like where diary_id = ? and user_id = ?",
                new Object[]{diaryId, userId});
    }

    // 查询当天写了几篇日志，每天只有一篇日志能获得积分
    public Integer getDiaryNumToday(Integer userId)
    {
        return jdbc.queryInt(
                "select count(*) from diary where user_id = ? and create_date = DATE(NOW()) ",
                userId);
    }

    public String getMaxYear(Integer userId)
    {
        return jdbc.queryString(
                "select year(diary_date) from diary where user_id = ? order by diary_date desc limit 0,1",
                userId);
    }

    public String getMaxMonth(Integer userId)
    {
        return jdbc.queryString(
                "select month(diary_date) from diary where user_id = ? order by diary_date desc limit 0,1",
                userId);
    }

    public String getDiaryUserId(Integer diaryId)
    {
        return jdbc.queryString("select user_id from diary where id = ?",
                diaryId);
    }

    public String getDiaryOpenness(Integer diaryId)
    {
        return jdbc.queryString("select openness from diary where id = ?",
                diaryId);
    }

    public List getMenuList(Integer userId)
    {
        List yearList = jdbc.query(
                "select distinct year(diary_date) dYear from diary where user_id = ? order by diary_date desc",
                userId);
        if (!empty(yearList))
        {
            for (Object o : yearList)
            {
                Map map = (Map) o;
                List monthList = new ArrayList();
                for (int i = 1; i < 13; i++)
                {
                    Map m = new HashMap();
                    m.put("month", i);
                    monthList.add(m);
                }
                map.put("monthList", monthList);
            }
        }
        return yearList;
    }

    public String getNextId(Integer userId)
    {
        int maxId = jdbc.queryInt(
                "select max(id) from diary where user_id = ?", userId);
        int nextId = maxId + 1;
        return nextId + "";
    }

    public void reshRankList()
    {
        List list = jdbc.query("SELECT diary_id,COUNT(id)  likeCount "
                + "FROM diary_like GROUP BY diary_id  ORDER BY likeCount DESC limit 20");
        if (!empty(list) && list.size() > 0)
        {
            for (Object o : list)
            {
                Map map = (Map) o;
                Integer diaryId = Integer.parseInt(map.get("diary_id")
                        .toString());
                Map diaryMap = (Map) jdbc.find(
                        "select title,content from diary where id = ?", diaryId);
                String diaryCover = jdbc.queryString(
                        "select name from diary_images "
                                + "where diary_id = ? order by id asc limit 1",
                        diaryId);
                RankList rl = new RankList();
                rl.setDiary_id(diaryId);
                rl.setDiary_content(diaryMap.get("content").toString());
                rl.setDiary_cover(diaryCover);
                rl.setLike_count(Long.parseLong(map.get("likeCount").toString()));
                jdbc.saveEntity(rl);
            }
        }
    }

    public void insertToIndex(Diary diary)
    {
        jdbc.update(
                "insert into index_diary (id,diary_id,model,create_date) values (null,?,?,date(now()))",
                new Object[]{diary.getId(), diary.getModel()});
    }
}
