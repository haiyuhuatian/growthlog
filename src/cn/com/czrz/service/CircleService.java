
package cn.com.czrz.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.com.czrz.entity.Circle;
import cn.com.czrz.entity.CircleUser;
import cn.com.gwypx.jdbc.ObjectRowMapper;

import com.google.gson.Gson;

@SuppressWarnings("serial")
public class CircleService extends BaseService
{
    public Integer findByName(String name)
    {
        List list = jdbc.query("select id from circle where name = ?", name);
        return list.size();
    }

    public void save(Circle circle)
    {
        jdbc.saveEntity(circle);
    }

    public void saveCircleUser(CircleUser circleUser)
    {
        jdbc.saveEntity(circleUser);
    }

    public Circle get(Integer id)
    {
        return (Circle) jdbc.loadEntity(Circle.class, id);
    }

    public CircleUser getCircleUser(Integer userId, Integer circleId)
    {
        return (CircleUser) jdbc.find(
                "SELECT id, circle_id, user_id, create_time FROM circle_user WHERE circle_id = ? AND user_id = ?",
                new Object[]{circleId, userId}, new ObjectRowMapper(
                        CircleUser.class));
    }

    public void update(Circle circle)
    {
        jdbc.updateEntity(circle);
    }

    public List getMyCircles(Integer userId)
    {
        return jdbc.query(
                "select c.id, c.name,c.describetion,c.cover,c.type,c.create_user from circle c join "
                        + " circle_user cu on c.id = cu.circle_id and cu.user_id = ?",
                userId);
    }

    public List getAllCircles(Map param, String searchType)
    {
        String sql = "select id,name,describetion,cover from circle where id not in "
                + "(select circle_id from circle_user where user_id = ?)";
        String userId = param.get("user_id").toString();
        if (!empty(param.get("lastCircleIdSign")))
        {
            String lastCircleSign = param.get("lastCircleIdSign").toString();
            if (empty(searchType))
            {
                sql += " and id < " + lastCircleSign;
            }
            else
            {
                sql += " and id <= " + lastCircleSign;
            }
        }
        if (!empty(param.get("circle.name")))
        {
            sql += " and name like '%" + param.get("circle.name") + "%' ";
        }
        if (!empty(param.get("circle.type"))
                && !param.get("circle.type").toString().equals("0"))
        {
            sql += " and type = ? order by id desc limit 20";
            return jdbc.query(sql,
                    new Object[]{userId, param.get("circle.type")});
        }
        sql += " order by id desc limit 20";
        return jdbc.query(sql, userId);
    }

    public List getDiaryList(Integer circleId, Integer lastDiaryId,
            Integer firstDiaryId, String searchType)
    {
        String sql = "SELECT d.id,d.title,d.content,d.diary_date,d.user_id uid,u.nickname FROM diary d JOIN circle_user cu "
                + "ON d.user_id = cu.user_id AND cu.circle_id = ? left join users u on d.user_id = u.id "
                + "where (d.openness=3 or (d.openness=2 and d.circle_ids like '%,"
                + circleId + ",%')) ";
        List list = new ArrayList();
        if (lastDiaryId > 0 && empty(searchType))
        {
            sql += " and d.id < " + lastDiaryId;
        }
        if (lastDiaryId > 0 && !empty(searchType))
        {
            sql += " and d.id <= " + lastDiaryId;
        }
        if (firstDiaryId > 0)
        {
            sql += " and d.id > " + firstDiaryId;
        }
        sql += " order by d.id desc limit 20";
        list = jdbc.query(sql, new Object[]{circleId});
        if (!empty(list))
        {
            for (Object o : list)
            {
                Map map = (Map) o;
                Integer count = jdbc.queryInt(
                        "select count(*) from diary_like dl "
                                + "join diary d on dl.diary_id = d.id where dl.diary_id=?",
                        map.get("id"));
                String cover = jdbc.queryString(
                        "select name from diary_images where diary_id = ? order by id asc limit 1",
                        map.get("id"));
                map.put("image", cover);
                map.put("likeCount", count);
            }
        }
        return list;
    }

    public Integer getCircleUserNum(Integer circleId)
    {
        return jdbc.queryInt(
                "select count(*) from circle_user where circle_id = ?",
                circleId);
    }

    public List getCircleUsers(Map param, Integer circleId, Integer userId,
            Integer lastId, String searchType)
    {
        String sql = "select u.id,cu.id cuid,u.nickname,u.head_img,u.introduction,f.id follow_id from users u join circle_user cu "
                + "on u.id = cu.user_id left join follow f on u.id = f.user_id and f.follower_id = ? where 1=1 ";

        if (!empty(lastId))
        {
            if (!empty(searchType))
            {
                sql += " and cu.id < " + lastId;
            }
            else
            {
                sql += " and cu.id <= " + lastId;
            }
        }
        sql += " and cu.circle_id = ?";
        if (!empty(param) && !empty(param.get("user.nickname")))
        {
            sql += " and u.nickname like '%" + param.get("user.nickname")
                    + "%'";
        }
        sql += " order by cu.id desc limit 10";
        return jdbc.query(sql, new Object[]{userId, circleId});
    }

    public List getPersonDiaryList(Integer userId, Integer circleId,
            Integer lastId, String searchType)
    {
        List list = new ArrayList();
        String sql = "SELECT d.id,d.title,d.diary_date,d.content,d.user_id uid,d.images FROM diary d  "
                + "where 1=1 ";
        if (!empty(lastId))
        {
            if (!empty(searchType))
            {
                sql += " and d.id < " + lastId;
            }
            else
            {
                sql += " and d.id <=" + lastId;
            }
        }
        sql += " and d.user_id = ? and (d.openness=3 or (d.openness =2 and d.circle_ids like '%,"
                + circleId + ",%')) order by d.id desc limit 20";
        list = jdbc.query(sql, new Object[]{userId});
        if (list.size() > 0)
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

    public void cancleCircleOpen(Integer circleId, Integer userId)
    {
        jdbc.update("UPDATE diary SET circle_ids = REPLACE(circle_ids,',"
                + circleId
                + ",',',') WHERE user_id = ? AND circle_ids IS NOT NULL",
                userId);
        jdbc.update(
                "UPDATE diary SET circle_ids = NULL WHERE user_id = ? AND circle_ids = ','",
                userId);
        jdbc.update(
                "delete from circle_user where circle_id = ? and user_id = ? ",
                new Object[]{circleId, userId});
    }
}
