
package cn.com.czrz.service;

import java.util.List;
import java.util.Map;

import cn.com.czrz.entity.AwardRecord;
import cn.com.czrz.entity.ShippingList;
import cn.com.gwypx.jdbc.ObjectRowMapper;

import com.google.gson.Gson;

public class AwardRecordService extends BaseService
{

    private static final long serialVersionUID = 3238409374476582684L;

    public void save(AwardRecord ar)
    {
        jdbc.saveEntity(ar);
    }

    public void update(AwardRecord ar)
    {
        jdbc.updateEntity(ar);
    }

    public AwardRecord findByUserIdAwardId(Integer userId, Integer awardId)
    {
        return (AwardRecord) jdbc.find(
                "SELECT id, award_id, user_id, realname, mobile, province, city, AREA, "
                        + "detailed_address, verify, create_time, verify_time, STATUS, evaluation, "
                        + "imgs, remark FROM award_record WHERE user_id = ? AND award_id = ? ",
                new Object[]{userId, awardId}, new ObjectRowMapper(
                        AwardRecord.class));
    }

    public AwardRecord get(Integer id)
    {
        return (AwardRecord) jdbc.loadEntity(AwardRecord.class, id);
    }

    public List findClaimRecord(Map param)
    {
        if (!empty(param) && !empty(param.get("userId")))
        {
            String sql = "SELECT ar.id,ar.award_id,ar.verify,ar.status,ar.remark,a.NAME,a.integral_need,ai.NAME cover,"
                    + "s.courier_company,s.courier_number,s.sign_status FROM award_record ar "
                    + "JOIN award a ON ar.award_id = a.id JOIN award_image ai ON a.id = ai.award_id "
                    + "AND ai.TYPE=1 LEFT JOIN shipping_list s ON s.award_record_id =ar.id "
                    + "WHERE ar.user_id = ? ";
            return jdbc.query(sql, param.get("userId"));
        }
        return null;
    }

    public ShippingList findByAwardRecordId(Integer awardRecordId)
    {
        return (ShippingList) jdbc.find(
                "SELECT id,award_record_id,courier_company,courier_number,sign_status,create_time,sign_time FROM shipping_list WHERE award_record_id = ?",
                awardRecordId, new ObjectRowMapper(ShippingList.class));
    }

    public void updateShippingList(ShippingList sl)
    {
        jdbc.updateEntity(sl);
    }

    public List getEvaluationList(Map param)
    {
        String sql = "SELECT u.nickname,ar.user_id,u.head_img,ar.id, ar.evaluation, ar.imgs, ar.create_time "
                + "FROM award_record ar JOIN users u ON ar.user_id = u.id "
                + "WHERE ar.award_id = ? AND ar.STATUS = 3";
        List list = jdbc.query(sql, param.get("awardId"));
        if (!empty(list) && list.size() > 0)
        {
            for (Object o : list)
            {
                Map arEvaluation = (Map) o;
                if (!empty(arEvaluation.get("imgs")))
                {
                    Gson gson = new Gson();
                    String[] imgs = gson.fromJson(arEvaluation.get("imgs")
                            .toString(), String[].class);
                    arEvaluation.put("imgs", imgs);
                }
            }
        }
        return list;
    }
}
