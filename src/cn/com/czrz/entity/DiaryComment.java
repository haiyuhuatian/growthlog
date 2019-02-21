
package cn.com.czrz.entity;

import java.util.Date;

import cn.com.gwypx.jdbc.annotation.Id;
import cn.com.gwypx.jdbc.annotation.Table;

@Table(name = "diary_comment")
public class DiaryComment extends BaseEntity
{
    /**
     * 
     */
    private static final long serialVersionUID = 3119682679705787099L;

    @Id
    private Integer id;

    private Integer user_id;

    private String nickname;

    private Integer diary_id;

    private String content;

    private Integer pid;

    private String code;

    private Long like_count;

    private Long current_month_likes;

    private Date create_time;

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getUser_id()
    {
        return user_id;
    }

    public void setUser_id(Integer user_id)
    {
        this.user_id = user_id;
    }

    public Integer getDiary_id()
    {
        return diary_id;
    }

    public void setDiary_id(Integer diary_id)
    {
        this.diary_id = diary_id;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public Integer getPid()
    {
        return pid;
    }

    public void setPid(Integer pid)
    {
        this.pid = pid;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public Date getCreate_time()
    {
        return create_time;
    }

    public void setCreate_time(Date create_time)
    {
        this.create_time = create_time;
    }

    public String getNickname()
    {
        return nickname;
    }

    public void setNickname(String nickname)
    {
        this.nickname = nickname;
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
}
