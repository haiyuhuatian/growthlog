
package cn.com.czrz.entity;

import java.util.Date;

import cn.com.gwypx.jdbc.annotation.Id;
import cn.com.gwypx.jdbc.annotation.Table;

@Table(name = "award_record")
public class AwardRecord extends BaseEntity
{

    private static final long serialVersionUID = 4312369010901404402L;

    @Id
    private Integer id;

    private Integer award_id;

    private Integer user_id;

    private String realname;// 收货人

    private String mobile;// 手机号

    private Integer province;// 省

    private Integer city;// 市

    private Integer area;// 区/县

    private String detailed_address;// 详细地址

    private Integer verify;// 是否通过:0待审核,1、通过 2、不通过

    private Date create_time;

    private Date verify_time;

    private Integer status;// 状态：0、代发货 1、已发货，2、待评价、3已完成

    private String evaluation;// 评价

    private String imgs;// 照片，json存储

    private String remark;

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

    public Integer getUser_id()
    {
        return user_id;
    }

    public void setUser_id(Integer user_id)
    {
        this.user_id = user_id;
    }

    public String getRealname()
    {
        return realname;
    }

    public void setRealname(String realname)
    {
        this.realname = realname;
    }

    public String getMobile()
    {
        return mobile;
    }

    public void setMobile(String mobile)
    {
        this.mobile = mobile;
    }

    public Integer getProvince()
    {
        return province;
    }

    public void setProvince(Integer province)
    {
        this.province = province;
    }

    public Integer getCity()
    {
        return city;
    }

    public void setCity(Integer city)
    {
        this.city = city;
    }

    public Integer getArea()
    {
        return area;
    }

    public void setArea(Integer area)
    {
        this.area = area;
    }

    public String getDetailed_address()
    {
        return detailed_address;
    }

    public void setDetailed_address(String detailed_address)
    {
        this.detailed_address = detailed_address;
    }

    public Integer getVerify()
    {
        return verify;
    }

    public void setVerify(Integer verify)
    {
        this.verify = verify;
    }

    public Date getCreate_time()
    {
        return create_time;
    }

    public void setCreate_time(Date create_time)
    {
        this.create_time = create_time;
    }

    public Date getVerify_time()
    {
        return verify_time;
    }

    public void setVerify_time(Date verify_time)
    {
        this.verify_time = verify_time;
    }

    public Integer getStatus()
    {
        return status;
    }

    public void setStatus(Integer status)
    {
        this.status = status;
    }

    public String getImgs()
    {
        return imgs;
    }

    public void setImgs(String imgs)
    {
        this.imgs = imgs;
    }

    public String getRemark()
    {
        return remark;
    }

    public void setRemark(String remark)
    {
        this.remark = remark;
    }

    public String getEvaluation()
    {
        return evaluation;
    }

    public void setEvaluation(String evaluation)
    {
        this.evaluation = evaluation;
    }

}
