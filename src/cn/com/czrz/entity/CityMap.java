
package cn.com.czrz.entity;

import cn.com.gwypx.jdbc.annotation.Id;
import cn.com.gwypx.jdbc.annotation.Table;

@Table(name = "city_map")
public class CityMap extends BaseEntity
{

    private static final long serialVersionUID = -4977107549093104509L;

    @Id
    private Integer id;

    private String name;

    private Integer pid;

    private String code;

    private Integer sn;

    private String remark;

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

    public Integer getSn()
    {
        return sn;
    }

    public void setSn(Integer sn)
    {
        this.sn = sn;
    }

    public String getRemark()
    {
        return remark;
    }

    public void setRemark(String remark)
    {
        this.remark = remark;
    }

}
