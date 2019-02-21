
package cn.com.czrz.entity;

import java.util.Date;

import cn.com.gwypx.jdbc.annotation.Id;
import cn.com.gwypx.jdbc.annotation.Table;

@Table(name = "comment_like")
public class CommentLike extends BaseEntity
{

    private static final long serialVersionUID = -6487873134188556989L;

    @Id
    private Integer id;

    private Integer comment_id;

    private Integer user_id;

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

    public Date getCreate_time()
    {
        return create_time;
    }

    public void setCreate_time(Date create_time)
    {
        this.create_time = create_time;
    }

    public Integer getComment_id()
    {
        return comment_id;
    }

    public void setComment_id(Integer comment_id)
    {
        this.comment_id = comment_id;
    }

}
