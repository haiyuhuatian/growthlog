
package cn.com.czrz.entity;

import java.util.Date;

import cn.com.gwypx.jdbc.annotation.Id;
import cn.com.gwypx.jdbc.annotation.Table;

@Table(name = "award")
public class Award extends BaseEntity
{

    private static final long serialVersionUID = -6143784782987742457L;

    @Id
    private Integer id;

    private String name;

    private String description;

    private String detail_message;

    private Integer inventory;

    private Integer integral_need;

    private Date create_time;

    private Integer status;

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getDetail_message()
    {
        return detail_message;
    }

    public void setDetail_message(String detail_message)
    {
        this.detail_message = detail_message;
    }

    public Integer getInventory()
    {
        return inventory;
    }

    public void setInventory(Integer inventory)
    {
        this.inventory = inventory;
    }

    public Integer getIntegral_need()
    {
        return integral_need;
    }

    public void setIntegral_need(Integer integral_need)
    {
        this.integral_need = integral_need;
    }

    public Date getCreate_time()
    {
        return create_time;
    }

    public void setCreate_time(Date create_time)
    {
        this.create_time = create_time;
    }

    public Integer getStatus()
    {
        return status;
    }

    public void setStatus(Integer status)
    {
        this.status = status;
    }
}
