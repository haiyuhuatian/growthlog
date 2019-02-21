
package cn.com.czrz.service;

import java.util.List;
import java.util.Map;

import cn.com.czrz.entity.DiaryLike;

public class CommonService extends BaseService
{

    private static final long serialVersionUID = 1889498643230764327L;

    public void refreshReadingQuantity()
    {
        List list = jdbc.query("SELECT COUNT(*) num,diary_id FROM reading_quantity GROUP BY diary_id ");
        if (list.size() > 0)
        {
            for (Object o : list)
            {
                Map map = (Map) o;
                Integer num = Integer.parseInt(map.get("num").toString());
                Integer readingQuantity = jdbc.queryInt(
                        "select reading_quantity from diary where id = ?",
                        map.get("diary_id"));
                readingQuantity += num;
                jdbc.update(
                        "update diary set reading_quantity = ? where id = ?",
                        new Object[]{readingQuantity, map.get("diary_id")});
            }
            jdbc.update("delete from reading_quantity");
        }
    }

    public void saveDiaryLike(DiaryLike diaryLike)
    {
        jdbc.saveEntity(diaryLike);
    }

    public void deleteDiaryList(Integer likeId)
    {
        jdbc.update("delete from diary_like where id=?", likeId);
    }
}
