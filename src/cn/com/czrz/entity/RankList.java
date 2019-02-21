
package cn.com.czrz.entity;

import cn.com.gwypx.jdbc.annotation.Id;
import cn.com.gwypx.jdbc.annotation.Table;

//点赞榜，定时器每天凌晨更新
@Table(name = "rank_list")
public class RankList extends BaseEntity
{
    private static final long serialVersionUID = 3857177717989437558L;

    @Id
    private Integer id;

    private Integer diary_id;

    private String diary_cover;

    private String diary_title;

    private String diary_content;

    private Long like_count;

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getDiary_id()
    {
        return diary_id;
    }

    public void setDiary_id(Integer diary_id)
    {
        this.diary_id = diary_id;
    }

    public String getDiary_cover()
    {
        return diary_cover;
    }

    public void setDiary_cover(String diary_cover)
    {
        this.diary_cover = diary_cover;
    }

    public String getDiary_title()
    {
        return diary_title;
    }

    public void setDiary_title(String diary_title)
    {
        this.diary_title = diary_title;
    }

    public String getDiary_content()
    {
        return diary_content;
    }

    public void setDiary_content(String diary_content)
    {
        this.diary_content = diary_content;
    }

    public Long getLike_count()
    {
        return like_count;
    }

    public void setLike_count(Long like_count)
    {
        this.like_count = like_count;
    }
}
