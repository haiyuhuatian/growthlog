
package cn.com.czrz.entity;

import java.util.Date;

import cn.com.gwypx.jdbc.annotation.Id;
import cn.com.gwypx.jdbc.annotation.Table;

@Table(name = "rank_rules")
public class RankRules extends BaseEntity
{

    private static final long serialVersionUID = -4341710999965520796L;

    @Id
    private Integer id;

    private String table_name;

    private String rank_column;

    private Date begin_time;

    private Date end_time;

    private String remark;

    private String winner;

    private Integer winner_num;

    private Integer rank_status;

    private Date create_time;

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Date getCreate_time()
    {
        return create_time;
    }

    public void setCreate_time(Date create_time)
    {
        this.create_time = create_time;
    }

    public String getTable_name()
    {
        return table_name;
    }

    public void setTable_name(String table_name)
    {
        this.table_name = table_name;
    }

    public String getRank_column()
    {
        return rank_column;
    }

    public void setRank_column(String rank_column)
    {
        this.rank_column = rank_column;
    }

    public Date getBegin_time()
    {
        return begin_time;
    }

    public void setBegin_time(Date begin_time)
    {
        this.begin_time = begin_time;
    }

    public Date getEnd_time()
    {
        return end_time;
    }

    public void setEnd_time(Date end_time)
    {
        this.end_time = end_time;
    }

    public String getRemark()
    {
        return remark;
    }

    public void setRemark(String remark)
    {
        this.remark = remark;
    }

    public String getWinner()
    {
        return winner;
    }

    public void setWinner(String winner)
    {
        this.winner = winner;
    }

    public Integer getWinner_num()
    {
        return winner_num;
    }

    public void setWinner_num(Integer winner_num)
    {
        this.winner_num = winner_num;
    }

    public Integer getRank_status()
    {
        return rank_status;
    }

    public void setRank_status(Integer rank_status)
    {
        this.rank_status = rank_status;
    }
}
