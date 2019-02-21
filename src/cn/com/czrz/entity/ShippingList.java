
package cn.com.czrz.entity;

import java.util.Date;

import cn.com.gwypx.jdbc.annotation.Id;
import cn.com.gwypx.jdbc.annotation.Table;

@Table(name = "shipping_list")
public class ShippingList extends BaseEntity
{

    private static final long serialVersionUID = 6925172701627864848L;

    @Id
    private Integer id;

    private Integer award_record_id;

    private String courier_company;// 快递公司

    private String courier_number;// 快递单号

    private Integer sign_status;// 签收状态 0、待签收，1、已签收

    private Date create_time;

    private Date sign_time;

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getAward_record_id()
    {
        return award_record_id;
    }

    public void setAward_record_id(Integer award_record_id)
    {
        this.award_record_id = award_record_id;
    }

    public String getCourier_company()
    {
        return courier_company;
    }

    public void setCourier_company(String courier_company)
    {
        this.courier_company = courier_company;
    }

    public String getCourier_number()
    {
        return courier_number;
    }

    public void setCourier_number(String courier_number)
    {
        this.courier_number = courier_number;
    }

    public Integer getSign_status()
    {
        return sign_status;
    }

    public void setSign_status(Integer sign_status)
    {
        this.sign_status = sign_status;
    }

    public Date getCreate_time()
    {
        return create_time;
    }

    public void setCreate_time(Date create_time)
    {
        this.create_time = create_time;
    }

    public Date getSign_time()
    {
        return sign_time;
    }

    public void setSign_time(Date sign_time)
    {
        this.sign_time = sign_time;
    }
}
