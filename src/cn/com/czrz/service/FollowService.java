
package cn.com.czrz.service;

import java.util.List;
import java.util.Map;

import cn.com.czrz.entity.Follow;
import cn.com.gwypx.jdbc.ObjectRowMapper;

import com.google.gson.Gson;

public class FollowService extends BaseService
{

    private static final long serialVersionUID = 2529780345150962321L;

    public void save(Follow follow)
    {
        jdbc.saveEntity(follow);
    }

    public void deleteById(Integer id)
    {
        jdbc.update("delete from follow where id = ?", id);
    }

    public Follow findByUserId(Integer userId, Integer followerId)
    {
        return (Follow) jdbc.find("select id,user_id,follower_id,follow_date"
                + " from follow where user_id = ? and follower_id = ?",
                new Object[]{userId, followerId}, new ObjectRowMapper(
                        Follow.class));
    }

    public List getFollows(Map param, String searchType)
    {
        String sql = "select u.id uid,u.nickname,u.head_img, u.introduction,f.id fid from follow f  "
                + "join users u on u.id = f.user_id ";
        if (!empty(param.get("nickname")))
        {
            sql += " and u.nickname like '%" + param.get("nickname") + "%' ";
        }
        sql += " where f.follower_id = ?";
        if (!empty(param.get("lastIdSign")))
        {
            if (!empty(searchType))
            {
                sql += " and f.id <= " + param.get("lastIdSign");
            }
            else
            {
                sql += " and f.id < " + param.get("lastIdSign");
            }
        }
        sql += " order by f.id desc limit 20";
        return jdbc.query(sql, param.get("followerId"));
    }

    public List findPersonDiarys(Map param)
    {
        String sql = "SELECT id,user_id uid,content,title,images FROM diary WHERE 1=1  ";
        List circles = jdbc.query("SELECT c1.circle_id FROM circle_user c1 "
                + "JOIN circle_user c2 ON c1.circle_id = c2.circle_id "
                + "AND c2.user_id = ? WHERE c1.user_id = ?",
                new Object[]{param.get("userId"), param.get("loginUserId")});
        if (!empty(param) && !empty(param.get("lastIdSign")))
        {
            if (!empty(param.get("searchType")))
            {
                sql += " and id <= " + param.get("lastIdSign");
            }
            else
            {
                sql += " and id < " + param.get("lastIdSign");
            }
        }
        sql += " and user_id = ? AND (openness = 3 ";
        if (!empty(circles) && circles.size() > 0)
        {
            sql += " or (";
            for (int i = 0; i < circles.size(); i++)
            {
                Map map = (Map) circles.get(i);
                if (i == 0)
                {
                    sql += "circle_ids like '%," + map.get("circle_id") + ",%'";
                }
                else
                {
                    sql += " or circle_ids like '%," + map.get("circle_id")
                            + ",%' ";
                }
            }
            sql += ")";

        }
        sql += " ) order by id desc limit 20";
        List diaryList = jdbc.query(sql, param.get("userId"));
        if (diaryList.size() > 0)
        {
            for (Object o : diaryList)
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
        return diaryList;
    }
}
