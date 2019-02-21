
package cn.com.czrz.action.portal;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.struts2.convention.annotation.ParentPackage;

import cn.com.czrz.action.BaseAction;
import cn.com.czrz.common.Constants;
import cn.com.czrz.entity.Award;
import cn.com.czrz.entity.AwardRecord;
import cn.com.czrz.entity.Users;
import cn.com.czrz.service.AwardRecordService;
import cn.com.czrz.service.AwardService;
import cn.com.czrz.service.UserService;

import com.google.gson.Gson;

@ParentPackage("portal")
public class IntegralAction extends BaseAction
{

    private static final long serialVersionUID = -8436340189544231496L;

    private AwardService awardService = new AwardService();

    private UserService userService = new UserService();

    private AwardRecordService awardRecordService = new AwardRecordService();

    private String lastIdSign;

    private Award award;

    private Users user;

    private AwardRecord awardRecord;

    private String fromPath;

    public String mall()
    {
        Map param = new HashMap();
        param.put("lastIdSign", lastIdSign);
        param.put("searchType", "index");
        List awardList = awardService.findAwards(param);
        if (!empty(awardList) && awardList.size() > 0)
        {
            Map awardMap = (Map) awardList.get(awardList.size() - 1);
            lastIdSign = awardMap.get("id").toString();
            request.setAttribute("lastIdSign", lastIdSign);
            request.setAttribute("awardList", awardList);
        }
        String noncestr = "integralMall";
        String jsapi_ticket = (String) application.getAttribute("jsapi_ticket");
        long timestamp = new Date().getTime();
        String url = Constants.CZRZ_URL + "/portal/integral!mall.action";
        String str = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + noncestr
                + "&timestamp=" + timestamp + "&url=" + url;
        String signature = DigestUtils.shaHex(str);
        request.setAttribute("noncestr", noncestr);
        request.setAttribute("timestamp", timestamp);
        request.setAttribute("signature", signature);
        request.setAttribute("appId", Constants.APPID);
        return "mall";
    }

    public void loadData() throws IOException
    {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        if (!empty(lastIdSign))
        {
            Map param = new HashMap();
            param.put("lastIdSign", lastIdSign);
            List awardList = awardService.findAwards(param);
            if (!empty(awardList) && awardList.size() > 0)
            {
                Gson gson = new Gson();
                String result = gson.toJson(awardList);
                out.print(result);
            }
            else
            {
                out.print("0");
            }
        }
        else
        {
            out.print("-1");
        }
        out.close();
    }

    public String awardDetail()
    {
        if (!empty(award) && !empty(award.getId()))
        {
            award = awardService.get(award.getId());
            request.setAttribute("award", award);
            request.setAttribute("images",
                    awardService.findImages(award.getId()));
            if (!empty(getLoginUser()))
            {
                user = userService.get(getLoginUser().getId());
                request.setAttribute("user", user);
                awardRecord = awardRecordService.findByUserIdAwardId(
                        user.getId(), award.getId());
                if (!empty(awardRecord))
                {
                    request.setAttribute("awardRecord", awardRecord);
                }
                if (award.getIntegral_need() > user.getIntegral())
                {
                    request.setAttribute("notEnough", "notEnough");
                }
            }
            Map param = new HashMap();
            param.put("awardId", award.getId());
            request.setAttribute("evaluations",
                    awardRecordService.getEvaluationList(param));
            String noncestr = "awardDetail";
            String jsapi_ticket = (String) application.getAttribute("jsapi_ticket");
            long timestamp = new Date().getTime();
            String url = Constants.CZRZ_URL
                    + "/portal/integral!awardDetail.action?award.id="
                    + award.getId();
            String str = "jsapi_ticket=" + jsapi_ticket + "&noncestr="
                    + noncestr + "&timestamp=" + timestamp + "&url=" + url;
            String signature = DigestUtils.shaHex(str);
            request.setAttribute("noncestr", noncestr);
            request.setAttribute("timestamp", timestamp);
            request.setAttribute("signature", signature);
            request.setAttribute("appId", Constants.APPID);
            request.setAttribute("fromPath", fromPath);
            request.setAttribute("serverPath", Constants.CZRZ_URL);
        }
        return "award_detail";
    }

    public String toClaim()
    {
        if (!empty(award) && !empty(award.getId()) && !empty(getLoginUser())
                && !empty(getLoginUser().getId()))
        {
            request.setAttribute("city1", userService.getCityMapByPid(0));
            award = awardService.get(award.getId());
            user = userService.get(getLoginUser().getId());
            request.setAttribute("award", award);
            request.setAttribute("user", user);
        }
        return "to_claim";
    }

    public String showAwardRecord()
    {
        if (!empty(getLoginUser()))
        {
            if (empty(awardRecord) || empty(awardRecord.getId()))
            {
                message = "参数错误，请刷新重试哦";
                return ALERT;
            }
            else
            {
                awardRecord = awardRecordService.get(awardRecord.getId());
                request.setAttribute("awardRecord", awardRecord);
                request.setAttribute("province",
                        userService.getMyCityName(awardRecord.getProvince()));
                request.setAttribute("city",
                        userService.getMyCityName(awardRecord.getCity()));
                request.setAttribute("area",
                        userService.getMyCityName(awardRecord.getArea()));
            }
        }
        else
        {
            message = "请先登录哦";
            return ALERT;
        }
        return "award_record";
    }

    public String subApply()
    {
        if (empty(getLoginUser()))
        {
            message = "请先登录哦";
            return ALERT;
        }
        if (!empty(award) && !empty(award.getId()))
        {
            if (!empty(awardRecord))
            {
                try
                {
                    award = awardService.get(award.getId());
                    awardRecord.setAward_id(award.getId());
                    awardRecord.setUser_id(getLoginUser().getId());
                    awardRecord.setCreate_time(new Date());
                    awardRecord.setVerify(0);
                    awardRecordService.save(awardRecord);
                    userService.subtractIntegral(getLoginUser().getId(),
                            award.getIntegral_need());
                    message = "提交成功，我们会在48小时内完成审核哦，请耐心等待";
                    this.redirectionUrl = "integral!awardDetail.action?award.id="
                            + award.getId();
                    return ALERT;
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    message = "提交失败，请稍后重试";
                    return ALERT;
                }
            }
        }
        else
        {
            message = "参数错误，请退出页面后重试";
            return ALERT;
        }
        return null;
    }

    public String rankList()
    {

        return "rank_list";
    }

    public String getLastIdSign()
    {
        return lastIdSign;
    }

    public void setLastIdSign(String lastIdSign)
    {
        this.lastIdSign = lastIdSign;
    }

    public Award getAward()
    {
        return award;
    }

    public void setAward(Award award)
    {
        this.award = award;
    }

    public Users getUser()
    {
        return user;
    }

    public void setUser(Users user)
    {
        this.user = user;
    }

    public AwardRecord getAwardRecord()
    {
        return awardRecord;
    }

    public void setAwardRecord(AwardRecord awardRecord)
    {
        this.awardRecord = awardRecord;
    }

    public String getFromPath()
    {
        return fromPath;
    }

    public void setFromPath(String fromPath)
    {
        this.fromPath = fromPath;
    }
}
