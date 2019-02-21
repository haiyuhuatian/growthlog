
package cn.com.czrz.entity;

import java.util.Date;

import cn.com.gwypx.jdbc.annotation.Id;
import cn.com.gwypx.jdbc.annotation.Table;

@Table(name = "integral")
public class Integral extends BaseEntity
{

    private static final long serialVersionUID = 991495815272019143L;

    @Id
    private Integer id;

    private Integer user_id;

    private Integer value;

    // 获取积分的途径（1、注册 2、写日志）
    private Integer type;

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

    public Integer getValue()
    {
        return value;
    }

    public void setValue(Integer value)
    {
        this.value = value;
    }

    public Integer getType()
    {
        return type;
    }

    public void setType(Integer type)
    {
        this.type = type;
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
