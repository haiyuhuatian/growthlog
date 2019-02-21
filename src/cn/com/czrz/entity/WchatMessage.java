
package cn.com.czrz.entity;

import java.util.Date;

import cn.com.gwypx.jdbc.annotation.Id;
import cn.com.gwypx.jdbc.annotation.Table;

@Table(name = "wchat_message")
public class WchatMessage extends BaseEntity
{
    @Id
    private Integer id;

    private String open_id;

    private String msg_type;

    private String content;

    private String pic_url;

    private String media_id;

    private String format;

    private String recognition;

    private String thumbMediaId;

    private Long msg_id;

    private Date create_time;

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getOpen_id()
    {
        return open_id;
    }

    public void setOpen_id(String open_id)
    {
        this.open_id = open_id;
    }

    public String getMsg_type()
    {
        return msg_type;
    }

    public void setMsg_type(String msg_type)
    {
        this.msg_type = msg_type;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public String getPic_url()
    {
        return pic_url;
    }

    public void setPic_url(String pic_url)
    {
        this.pic_url = pic_url;
    }

    public String getMedia_id()
    {
        return media_id;
    }

    public void setMedia_id(String media_id)
    {
        this.media_id = media_id;
    }

    public String getFormat()
    {
        return format;
    }

    public void setFormat(String format)
    {
        this.format = format;
    }

    public String getRecognition()
    {
        return recognition;
    }

    public void setRecognition(String recognition)
    {
        this.recognition = recognition;
    }

    public String getThumbMediaId()
    {
        return thumbMediaId;
    }

    public void setThumbMediaId(String thumbMediaId)
    {
        this.thumbMediaId = thumbMediaId;
    }

    public Long getMsg_id()
    {
        return msg_id;
    }

    public void setMsg_id(Long msg_id)
    {
        this.msg_id = msg_id;
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
