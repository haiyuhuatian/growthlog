
package cn.com.czrz.entity;

import java.util.Date;

import cn.com.gwypx.jdbc.annotation.Id;
import cn.com.gwypx.jdbc.annotation.Table;

@Table(name = "diary_like")
public class DiaryLike extends BaseEntity
{

    private static final long serialVersionUID = -1010756773623728518L;

    @Id
    private Integer id;

    private Integer diary_id;

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

    public Integer getDiary_id()
    {
        return diary_id;
    }

    public void setDiary_id(Integer diary_id)
    {
        this.diary_id = diary_id;
    }

}
