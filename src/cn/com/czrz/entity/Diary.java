
package cn.com.czrz.entity;

import java.util.Date;

import cn.com.gwypx.jdbc.annotation.Id;
import cn.com.gwypx.jdbc.annotation.Table;

@Table(name = "diary")
public class Diary extends BaseEntity
{

    private static final long serialVersionUID = 8820068607175872151L;

    @Id
    private Integer id;

    private String title;

    private String weather;

    private Integer user_id;

    // 日志的创建日期
    private Date create_date;

    // 日志的日期
    private Date diary_date;

    // 1:仅自己可见 2:仅圈子可见 3:所有人可见
    private Integer openness;

    // 1： 日常 2：游记 3： 学习笔记 4：趣事
    private Integer type;

    private String content;

    private String weekday;

    // 记录保存状态 0:上传了照片，内容还未保存，1：保存完毕；
    private Integer status;

    // 阅读量
    private Integer reading_quantity;

    // 模式（1、宝贝日志，2、个人日志）
    private Integer model;

    // 在哪些圈子可见
    private String circle_ids;

    // 图片名称
    private String images;

    // 点赞数
    private Long like_count;

    // 当月点赞数
    private Long current_month_likes;

    // 评论数
    private Long comment_count;

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getWeather()
    {
        return weather;
    }

    public void setWeather(String weather)
    {
        this.weather = weather;
    }

    public Integer getUser_id()
    {
        return user_id;
    }

    public void setUser_id(Integer user_id)
    {
        this.user_id = user_id;
    }

    public Date getCreate_date()
    {
        return create_date;
    }

    public void setCreate_date(Date create_date)
    {
        this.create_date = create_date;
    }

    public Integer getOpenness()
    {
        return openness;
    }

    public void setOpenness(Integer openness)
    {
        this.openness = openness;
    }

    public Integer getType()
    {
        return type;
    }

    public void setType(Integer type)
    {
        this.type = type;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public String getWeekday()
    {
        return weekday;
    }

    public void setWeekday(String weekday)
    {
        this.weekday = weekday;
    }

    public Integer getStatus()
    {
        return status;
    }

    public void setStatus(Integer status)
    {
        this.status = status;
    }

    public Integer getReading_quantity()
    {
        return reading_quantity;
    }

    public void setReading_quantity(Integer reading_quantity)
    {
        this.reading_quantity = reading_quantity;
    }

    public Date getDiary_date()
    {
        return diary_date;
    }

    public void setDiary_date(Date diary_date)
    {
        this.diary_date = diary_date;
    }

    public Integer getModel()
    {
        return model;
    }

    public void setModel(Integer model)
    {
        this.model = model;
    }

    public String getCircle_ids()
    {
        return circle_ids;
    }

    public void setCircle_ids(String circle_ids)
    {
        this.circle_ids = circle_ids;
    }

    public String getImages()
    {
        return images;
    }

    public void setImages(String images)
    {
        this.images = images;
    }

    public Long getLike_count()
    {
        return like_count;
    }

    public void setLike_count(Long like_count)
    {
        this.like_count = like_count;
    }

    public Long getCurrent_month_likes()
    {
        return current_month_likes;
    }

    public void setCurrent_month_likes(Long current_month_likes)
    {
        this.current_month_likes = current_month_likes;
    }

    public Long getComment_count()
    {
        return comment_count;
    }

    public void setComment_count(Long comment_count)
    {
        this.comment_count = comment_count;
    }

}
