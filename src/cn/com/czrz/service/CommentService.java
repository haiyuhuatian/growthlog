
package cn.com.czrz.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.com.czrz.entity.DiaryComment;

public class CommentService extends BaseService
{

    private static final long serialVersionUID = 5898311186784375768L;

    public void saveDiaryComment(DiaryComment diaryComment)
    {
        jdbc.saveEntity(diaryComment);
    }

    public void updateDiaryComment(DiaryComment diaryComment)
    {
        jdbc.updateEntity(diaryComment);
    }

    public void deleteCommentLike(Integer likeId)
    {
        jdbc.update("delete from comment_like where id = ?", likeId);
    }

    public DiaryComment getDiaryComment(Integer id)
    {
        return (DiaryComment) jdbc.loadEntity(DiaryComment.class, id);
    }

    public List getComments(Integer userId, Integer diaryId,
            String lastCommentIdSign, Integer type)
    {
        String sql = "";
        List list = new ArrayList();
        if (userId > 0)
        {
            sql = "SELECT dc.id,dc.nickname,dc.user_id,u.head_img,dc.content,"
                    + "dc.create_time,cl.id like_id  ,dc.code "
                    + "FROM diary_comment dc  "
                    + "join users u on dc.user_id = u.id "
                    + "left JOIN comment_like cl ON cl.comment_id = dc.id AND cl.user_id = ? "
                    + "WHERE dc.pid = 0 AND  dc.diary_id = ? ";
            if (!empty(lastCommentIdSign))
            {
                if (type == 0)
                {
                    sql += " and dc.id < " + lastCommentIdSign;
                }
                else
                {
                    sql += " and dc.id <= " + lastCommentIdSign;
                }

            }
            sql += " order by dc.id desc limit 20";
            list = jdbc.query(sql, new Object[]{userId, diaryId});
        }
        else
        {
            sql = "SELECT dc.id,dc.nickname,dc.user_id,u.head_img,"
                    + "dc.content,dc.create_time,dc.CODE"
                    + " FROM diary_comment dc "
                    + "join users u on dc.user_id = u.id "
                    + " WHERE dc.pid = 0 AND  dc.diary_id = ? ";
            if (!empty(lastCommentIdSign))
            {
                if (type == 0)
                {
                    sql += " and dc.id < " + lastCommentIdSign;
                }
                else
                {
                    sql += " and dc.id <= " + lastCommentIdSign;
                }

            }
            sql += " order by dc.id desc limit 20";
            list = jdbc.query(sql, new Object[]{diaryId});
        }
        if (!empty(list))
        {
            for (Object o : list)
            {
                Map map = (Map) o;
                String sql2 = "";
                List family = new ArrayList();
                if (userId > 0)
                {
                    sql2 = "SELECT dc.id, dc.nickname,co.nickname fatherName,dc.content,cl.id likeId, "
                            + "dc.create_time FROM diary_comment dc JOIN diary_comment co "
                            + "ON dc.pid = co.id left JOIN comment_like cl ON cl.comment_id = dc.id "
                            + "AND cl.user_id = ? WHERE dc.code like '"
                            + map.get("code") + "%'";
                    family = jdbc.query(sql2, new Object[]{userId});
                }
                else
                {
                    sql2 = "SELECT dc.id, dc.nickname,co.nickname fatherName,dc.content, "
                            + "dc.create_time FROM diary_comment dc JOIN diary_comment co "
                            + "ON dc.pid = co.id   WHERE dc.code like '"
                            + map.get("code") + "%'";
                    family = jdbc.query(sql2);
                }
                if (empty(map.get("like_id")))
                {
                    map.put("like_id", "");
                }
                Integer likeNum = jdbc.queryInt(
                        "select count(*) from comment_like where comment_id = ?",
                        map.get("id"));
                map.put("like_num", likeNum);
                if (!empty(family))
                {
                    for (Object b : family)
                    {
                        Map m = (Map) b;
                        Integer num = jdbc.queryInt(
                                "select count(*) from comment_like where comment_id = ?",
                                m.get("id"));
                        m.put("likeNum", num);
                    }
                }
                map.put("family_num", family.size());
            }
        }
        return list;
    }

    public List getCommentFamily(DiaryComment diaryComment,
            Integer lastCommentIdSign, Integer userId)
    {
        String sql = "SELECT dc.id, dc.nickname,dc.content,cl.id like_id,d.user_id,dc.user_id comment_user_id,u.head_img, "
                + "dc.create_time FROM diary_comment dc join users u on dc.user_id = u.id  "
                + " join diary d on dc.diary_id = d.id left JOIN comment_like cl ON cl.comment_id = dc.id "
                + "AND cl.user_id = ? WHERE dc.pid =" + diaryComment.getId();
        if (lastCommentIdSign > 0)
        {
            sql += " and dc.id < " + lastCommentIdSign;
        }
        sql += " order by dc.id desc limit 20";
        List family = jdbc.query(sql, new Object[]{userId});
        if (!empty(family))
        {
            for (Object b : family)
            {
                Map m = (Map) b;
                Integer num = jdbc.queryInt(
                        "select count(*) from comment_like where comment_id = ?",
                        m.get("id"));
                m.put("like_num", num);
                if (empty(m.get("like_id")))
                {
                    m.put("like_id", "");
                }
            }
        }
        return family;
    }

    public Integer getCommentLikeNum(Integer diaryCommentId)
    {
        return jdbc.queryInt(
                "select count(*) from comment_like where comment_id = ?",
                diaryCommentId);
    }

    public Integer getCommentLikeId(Integer diaryCommentId, Integer userId)
    {
        return jdbc.queryInt(
                "select id from comment_like where user_id = ? and comment_id = ?",
                new Object[]{userId, diaryCommentId});
    }

}
