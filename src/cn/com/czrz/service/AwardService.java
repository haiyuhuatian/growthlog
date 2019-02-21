
package cn.com.czrz.service;

import java.util.List;
import java.util.Map;

import cn.com.czrz.entity.Award;

public class AwardService extends BaseService
{
    public List findAwards(Map param)
    {
        String sql = "select a.id,a.name,a.description,a.detail_message,"
                + "a.inventory,a.integral_need,ai.name cover from award a join "
                + "award_image ai on a.id = ai.award_id and ai.type = 1 where a.status = 1";
        if (!empty(param) && !empty(param.get("lastIdSign")))
        {
            if (!empty(param.get("searchType")))
            {
                sql += " and a.id <= " + param.get("lastIdSign");
            }
            else
            {
                sql += " and a.id < " + param.get("lastIdSign");
            }
        }
        sql += " order by a.id desc limit 20";
        return jdbc.query(sql);
    }

    public Award get(Integer id)
    {
        return (Award) jdbc.loadEntity(Award.class, id);
    }

    public List findImages(Integer awardId)
    {
        return jdbc.query("select name from award_image where award_id = ?",
                awardId);
    }
}
