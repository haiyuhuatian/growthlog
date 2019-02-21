
package cn.com.czrz.entity;

import java.util.Date;

import cn.com.gwypx.jdbc.annotation.Id;
import cn.com.gwypx.jdbc.annotation.Table;
import cn.com.gwypx.jdbc.annotation.Transient;

@Table(name = "users")
public class Users extends BaseEntity
{

    private static final long serialVersionUID = 1L;

    @Id
    private Integer id;

    private String login_name;

    private String nickname;

    private String password;

    private String password_salt;

    private Date birthday;

    // 宝宝出生日期
    private Date baby_birthday;

    // 性别（0：女，1：男）
    private Integer sex;

    private String address;

    private String email;

    private Date register_date;

    // 所在的区县
    private Integer area;

    @Transient
    private UserLoginTrack track = new UserLoginTrack();

    private String ticket;

    // 积分
    private Integer integral;

    // 微信号
    private String wchat_no;

    // 微信号是否公开(0、不公开，1、公开)
    private Integer is_wchat_open;

    // 简介
    private String introduction;

    // 用户的openId
    private String openId;

    private String head_img;

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getLogin_name()
    {
        return login_name;
    }

    public void setLogin_name(String login_name)
    {
        this.login_name = login_name;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getPassword_salt()
    {
        return password_salt;
    }

    public void setPassword_salt(String password_salt)
    {
        this.password_salt = password_salt;
    }

    public Date getBirthday()
    {
        return birthday;
    }

    public void setBirthday(Date birthday)
    {
        this.birthday = birthday;
    }

    public Date getBaby_birthday()
    {
        return baby_birthday;
    }

    public void setBaby_birthday(Date baby_birthday)
    {
        this.baby_birthday = baby_birthday;
    }

    public Integer getSex()
    {
        return sex;
    }

    public void setSex(Integer sex)
    {
        this.sex = sex;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public Date getRegister_date()
    {
        return register_date;
    }

    public void setRegister_date(Date register_date)
    {
        this.register_date = register_date;
    }

    public Integer getArea()
    {
        return area;
    }

    public void setArea(Integer area)
    {
        this.area = area;
    }

    public UserLoginTrack getTrack()
    {
        return track;
    }

    public void setTrack(UserLoginTrack track)
    {
        this.track = track;
    }

    public String getTicket()
    {
        return ticket;
    }

    public void setTicket(String ticket)
    {
        this.ticket = ticket;
    }

    public String getNickname()
    {
        return nickname;
    }

    public void setNickname(String nickname)
    {
        this.nickname = nickname;
    }

    public Integer getIntegral()
    {
        return integral;
    }

    public void setIntegral(Integer integral)
    {
        this.integral = integral;
    }

    public String getWchat_no()
    {
        return wchat_no;
    }

    public void setWchat_no(String wchat_no)
    {
        this.wchat_no = wchat_no;
    }

    public Integer getIs_wchat_open()
    {
        return is_wchat_open;
    }

    public void setIs_wchat_open(Integer is_wchat_open)
    {
        this.is_wchat_open = is_wchat_open;
    }

    public String getIntroduction()
    {
        return introduction;
    }

    public void setIntroduction(String introduction)
    {
        this.introduction = introduction;
    }

    public String getHead_img()
    {
        return head_img;
    }

    public void setHead_img(String head_img)
    {
        this.head_img = head_img;
    }

    public String getOpenId()
    {
        return openId;
    }

    public void setOpenId(String openId)
    {
        this.openId = openId;
    }

}
