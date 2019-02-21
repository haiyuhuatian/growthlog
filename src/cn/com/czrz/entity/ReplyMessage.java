
package cn.com.czrz.entity;

import java.util.Date;

import cn.com.gwypx.jdbc.annotation.Id;
import cn.com.gwypx.jdbc.annotation.Table;

@Table(name = "reply_message")
public class ReplyMessage extends BaseEntity
{

    private static final long serialVersionUID = -3543236383441141148L;

    @Id
    private Integer id;

    private Integer message_id;

    private String content;

    private Date create_time;

    private String remark;

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getMessage_id()
    {
        return message_id;
    }

    public void setMessage_id(Integer message_id)
    {
        this.message_id = message_id;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public Date getCreate_time()
    {
        return create_time;
    }

    public void setCreate_time(Date create_time)
    {
        this.create_time = create_time;
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
