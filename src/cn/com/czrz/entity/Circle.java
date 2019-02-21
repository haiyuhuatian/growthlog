
package cn.com.czrz.entity;

import java.util.Date;

import cn.com.gwypx.jdbc.annotation.Id;
import cn.com.gwypx.jdbc.annotation.Table;

@Table(name = "circle")
public class Circle extends BaseEntity
{

    private static final long serialVersionUID = 7387018331885386887L;

    @Id
    private Integer id;

    // 1:同城 2:同学 3:兴趣爱好 4:家长会5:好友6：家族
    private Integer type;

    private String name;

    private String describetion;

    private String cover;

    private Integer create_user;

    private Date create_time;

    private Integer number;

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getType()
    {
        return type;
    }

    public void setType(Integer type)
    {
        this.type = type;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDescribetion()
    {
        return describetion;
    }

    public void setDescribetion(String describetion)
    {
        this.describetion = describetion;
    }

    public String getCover()
    {
        return cover;
    }

    public void setCover(String cover)
    {
        this.cover = cover;
    }

    public Integer getCreate_user()
    {
        return create_user;
    }

    public void setCreate_user(Integer create_user)
    {
        this.create_user = create_user;
    }

    public Date getCreate_time()
    {
        return create_time;
    }

    public void setCreate_time(Date create_time)
    {
        this.create_time = create_time;
    }

    public Integer getNumber()
    {
        return number;
    }

    public void setNumber(Integer number)
    {
        this.number = number;
    }
}
