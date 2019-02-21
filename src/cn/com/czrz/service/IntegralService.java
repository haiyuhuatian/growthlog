
package cn.com.czrz.service;

import java.util.List;

import cn.com.czrz.entity.Integral;

public class IntegralService extends BaseService
{

    private static final long serialVersionUID = -1925663577952949933L;

    public void save(Integral integral)
    {
        jdbc.saveEntity(integral);
    }

    public List getIntegralDetail(Integer userId, String date)
    {
        return jdbc.query(
                "SELECT VALUE,TYPE,create_time FROM integral WHERE user_id = ? "
                        + "and DATE_FORMAT(create_time,'%Y-%m') = ? order by id desc ",
                new Object[]{userId, date});
    }

    public Integer getTotal(Integer userId, String date)
    {
        return jdbc.queryInt(
                "SELECT SUM(VALUE) FROM integral WHERE user_id = ? "
                        + " AND DATE_FORMAT(create_time,'%Y-%m') = ? ORDER BY id DESC",
                new Object[]{userId, date});
    }
}
