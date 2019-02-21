
package cn.com.czrz.entity;

import java.util.Date;

import cn.com.gwypx.jdbc.annotation.Id;
import cn.com.gwypx.jdbc.annotation.Table;

@Table(name = "award_image")
public class AwardImage extends BaseEntity
{

    private static final long serialVersionUID = -1240128520606748741L;

    @Id
    private Integer id;

    private Integer award_id;

    private String name;

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

    public Integer getAward_id()
    {
        return award_id;
    }

    public void setAward_id(Integer award_id)
    {
        this.award_id = award_id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
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
