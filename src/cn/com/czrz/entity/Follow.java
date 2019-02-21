
package cn.com.czrz.entity;

import java.util.Date;

import cn.com.gwypx.jdbc.annotation.Id;
import cn.com.gwypx.jdbc.annotation.Table;

@Table(name = "follow")
public class Follow extends BaseEntity
{

    private static final long serialVersionUID = -514762252551094317L;

    @Id
    private Integer id;

    // 被关注的人的id
    private Integer user_id;

    // 关注的人的id
    private Integer follower_id;

    private Date follow_date;

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

    public Integer getFollower_id()
    {
        return follower_id;
    }

    public void setFollower_id(Integer follower_id)
    {
        this.follower_id = follower_id;
    }

    public Date getFollow_date()
    {
        return follow_date;
    }

    public void setFollow_date(Date follow_date)
    {
        this.follow_date = follow_date;
    }

}
