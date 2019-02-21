
package cn.com.czrz.entity;

import java.util.Date;

import cn.com.gwypx.jdbc.annotation.Id;
import cn.com.gwypx.jdbc.annotation.Table;

@Table(name = "circle_user")
public class CircleUser extends BaseEntity
{

    private static final long serialVersionUID = 1L;

    @Id
    private Integer id;

    private Integer circle_id;

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

    public Integer getCircle_id()
    {
        return circle_id;
    }

    public void setCircle_id(Integer circle_id)
    {
        this.circle_id = circle_id;
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

}
