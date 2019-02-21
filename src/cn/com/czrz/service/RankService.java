
package cn.com.czrz.service;

import java.util.List;

import cn.com.czrz.entity.RankRules;
import cn.com.gwypx.jdbc.ObjectRowMapper;

public class RankService extends BaseService
{

    private static final long serialVersionUID = 4483044171689282602L;

    public RankRules get()
    {
        return (RankRules) jdbc.find(
                "SELECT id, table_name, rank_column, begin_time, end_time, remark, winner, winner_num, rank_status FROM rank_rules WHERE rank_status = 1",
                new ObjectRowMapper(RankRules.class));
    }

    public List getRankList()
    {
        return jdbc.query("SELECT show_columns,acount FROM rank_list ORDER BY id ASC");
    }
}
