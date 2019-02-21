
package cn.com.czrz.entity;

import cn.com.gwypx.jdbc.annotation.Id;
import cn.com.gwypx.jdbc.annotation.Table;

@Table(name = "reading_quantity")
public class ReadingQuantity extends BaseEntity
{

    private static final long serialVersionUID = 5162394980642477524L;

    @Id
    private Integer id;

    private Integer diary_id;

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
}
