
package cn.com.czrz.action.mine;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.ParentPackage;

import sun.misc.BASE64Decoder;
import cn.com.czrz.action.BaseAction;
import cn.com.czrz.common.Constants;
import cn.com.czrz.entity.Award;
import cn.com.czrz.entity.AwardRecord;
import cn.com.czrz.entity.ShippingList;
import cn.com.czrz.entity.Users;
import cn.com.czrz.service.AwardRecordService;
import cn.com.czrz.service.AwardService;
import cn.com.czrz.service.IntegralService;
import cn.com.czrz.service.UserService;

import com.google.gson.Gson;

@ParentPackage("mine")
public class IntegralAction extends BaseAction
{

    private static final long serialVersionUID = 8840321761058589572L;

    private IntegralService integralService = new IntegralService();

    private UserService userService = new UserService();

    private AwardService awardService = new AwardService();

    private AwardRecordService awardRecordService = new AwardRecordService();

    private Users user;

    private String searchDate;

    private AwardRecord awardRecord;

    private Award award;

    private String myImgs;

    private String serverId;

    private Integer photoNum;

    private String packageId;

    public String detail() throws ParseException
    {
        if (empty(getLoginUser()))
        {
            message = "请先登录";
            return ALERT;
        }
        if (empty(searchDate))
        {
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
            searchDate = sdf.format(date);
        }
        user = userService.get(getLoginUser().getId());
        List list = integralService.getIntegralDetail(user.getId(), searchDate);
        request.setAttribute("monthTotal",
                integralService.getTotal(user.getId(), searchDate));
        request.setAttribute("integralList", list);
        request.setAttribute("totalIntegral", user.getIntegral());
        request.setAttribute("searchDate", searchDate);
        return "detail";
    }

    public String claimRecord()
    {
        if (empty(getLoginUser()))
        {
            message = "请先登录";
            return ALERT;
        }
        else
        {
            Map param = new HashMap();
            param.put("userId", getLoginUser().getId());
            request.setAttribute("list",
                    awardRecordService.findClaimRecord(param));
        }
        return "claim_record";
    }

    public void confirmGoods() throws IOException
    {
        PrintWriter out = response.getWriter();
        if (!empty(awardRecord) && !empty(awardRecord.getId()))
        {
            awardRecord = awardRecordService.get(awardRecord.getId());
            ShippingList sl = awardRecordService.findByAwardRecordId(awardRecord.getId());
            sl.setSign_status(1);
            sl.setSign_time(new Date());
            awardRecordService.updateShippingList(sl);
            awardRecord.setStatus(2);
            awardRecordService.update(awardRecord);
            out.print("1");
        }
        else
        {
            out.print("-1");
        }
        out.close();
    }

    public String evaluation()
    {
        if (!empty(awardRecord) && !empty(awardRecord.getId()))
        {
            awardRecord = awardRecordService.get(awardRecord.getId());
            request.setAttribute("awardRecord", awardRecord);
            String userAgent = request.getHeader("user-agent");
            if (userAgent.contains("iPhone"))
            {
                return "award_evaluation_iphone";
            }
            else
            {
                String noncestr = "awardEvaluation";
                String jsapi_ticket = (String) application.getAttribute("jsapi_ticket");
                long timestamp = new Date().getTime();
                String url = Constants.CZRZ_URL
                        + "/mine/integral!evaluation.action?awardRecord.id="
                        + awardRecord.getId();
                String str = "jsapi_ticket=" + jsapi_ticket + "&noncestr="
                        + noncestr + "&timestamp=" + timestamp + "&url=" + url;
                String signature = DigestUtils.shaHex(str);
                request.setAttribute("noncestr", noncestr);
                request.setAttribute("timestamp", timestamp);
                request.setAttribute("signature", signature);
                request.setAttribute("appId", Constants.APPID);
            }
        }
        return "award_evaluation";
    }

    public void savePhotos() throws IOException
    {
        PrintWriter out = response.getWriter();
        if (!empty(myImgs) && !empty(awardRecord)
                && !empty(awardRecord.getId()))
        {
            awardRecord = awardRecordService.get(awardRecord.getId());
            File imagePackage = new File(
                    ServletActionContext.getServletContext().getRealPath(
                            "awardEvaluationImgs")
                            + "/awardRecord" + awardRecord.getId());
            imagePackage.mkdirs();
            Gson gson = new Gson();
            List<String> strs = gson.fromJson(myImgs, List.class);
            String[] imgNames = new String[strs.size()];
            for (int i = 0; i < strs.size(); i++)
            {
                String imgBase = strs.get(i);
                if (!empty(imgBase))
                {
                    imgBase = imgBase.replaceAll("data:image/png;base64,", "");
                    imgBase = imgBase.replaceAll("data:image/jpeg;base64,", "");
                    imgBase = imgBase.replaceAll("data:image/gif;base64,", "");
                    imgBase = imgBase.replaceAll("data:image/jpg;base64,", "");
                    BASE64Decoder decoder = new BASE64Decoder();
                    // Base64解码
                    byte[] imageByte = null;
                    try
                    {
                        imageByte = decoder.decodeBuffer(imgBase);
                        for (int t = 0; t < imageByte.length; ++t)
                        {
                            if (imageByte[t] < 0)
                            {// 调整异常数据
                                imageByte[t] += 256;
                            }
                        }
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                    // 生成文件名
                    String files = "evaluation" + awardRecord.getId() + "_" + i
                            + ".jpg";
                    imgNames[i] = files;
                    // 生成文件路径
                    String filename = imagePackage.getPath() + "\\" + files;
                    try
                    {
                        // 生成文件
                        File imageFile = new File(filename);
                        imageFile.createNewFile();
                        if (!imageFile.exists())
                        {
                            imageFile.createNewFile();
                        }
                        OutputStream imageStream = new FileOutputStream(
                                imageFile);
                        imageStream.write(imageByte);
                        imageStream.flush();
                        imageStream.close();
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }
            if (!empty(imgNames))
            {
                Gson gson2 = new Gson();
                String imgnames = gson2.toJson(imgNames);
                awardRecord.setImgs(imgnames);
                awardRecordService.update(awardRecord);
            }
            out.print(1);
        }
        else
        {
            out.print(-1);
        }
        out.close();
    }

    public void saveWeixinPhotos() throws IOException
    {
        PrintWriter out = response.getWriter();
        if (!empty(awardRecord) && !empty(awardRecord.getId()))
        {
            String fileName = saveImageToDisk(serverId, awardRecord.getId());
            if (fileName != "")
            {
                out.print(fileName);
            }
            else
            {
                out.print("error");
            }
        }
        else
        {
            out.print("error");
        }
        out.close();
    }

    /**
     * 保存图片至服务器
     * 
     * @param mediaId
     * @return 文件名
     */
    public String saveImageToDisk(String mediaId, Integer awardId)
    {
        String fileName = "";
        InputStream inputStream = getMedia(mediaId);
        byte[] data = new byte[1024];
        int len = 0;
        FileOutputStream fileOutputStream = null;
        try
        {
            // 服务器存图路径
            String path = ServletActionContext.getServletContext().getRealPath(
                    "awardEvaluationImgs")
                    + "/awardRecord" + awardId;
            File packageFile = new File(path);
            if (!packageFile.exists())
            {
                packageFile.mkdir();
            }
            path += "/";
            fileName = "evaluation" + awardRecord.getId() + "_" + photoNum
                    + ".jpg";
            fileOutputStream = new FileOutputStream(path + fileName);
            while ((len = inputStream.read(data)) != -1)
            {
                fileOutputStream.write(data, 0, len);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (inputStream != null)
            {
                try
                {
                    inputStream.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            if (fileOutputStream != null)
            {
                try
                {
                    fileOutputStream.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return fileName;
    }

    /**
     * 获取临时素材
     */
    private InputStream getMedia(String mediaId)
    {
        String url = "https://api.weixin.qq.com/cgi-bin/media/get";
        String access_token = (String) application.getAttribute("access_token");
        String params = "access_token=" + access_token + "&media_id=" + mediaId;
        InputStream is = null;
        try
        {
            String urlNameString = url + "?" + params;
            URL urlGet = new URL(urlNameString);
            HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();
            http.setRequestMethod("GET"); // 必须是get方式请求
            http.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            http.setDoOutput(true);
            http.setDoInput(true);
            http.connect();
            // 获取文件转化为byte流
            is = http.getInputStream();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return is;
    }

    public String saveEvaluation()
    {
        if (!empty(awardRecord) && !empty(awardRecord.getId()))
        {
            AwardRecord ar = awardRecordService.get(awardRecord.getId());
            ar.setEvaluation(awardRecord.getEvaluation());
            ar.setStatus(3);
            String[] imgs = request.getParameterValues("eImgs");
            if (!empty(imgs) && imgs != null)
            {
                Gson gson = new Gson();
                String evaImgs = gson.toJson(imgs);
                ar.setImgs(evaImgs);
            }
            awardRecordService.update(ar);
            int addIntegral = 10;
            userService.addIntegral(getLoginUser().getId(), addIntegral);
            message = "评价成功，本次获得" + addIntegral + "积分";
        }
        else
        {
            message = "参数错误，请退出后重试";
        }
        this.redirectionUrl = "integral!claimRecord.action";
        return ALERT;
    }

    public String getSearchDate()
    {
        return searchDate;
    }

    public void setSearchDate(String searchDate)
    {
        this.searchDate = searchDate;
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

    public Award getAward()
    {
        return award;
    }

    public void setAward(Award award)
    {
        this.award = award;
    }

    public String getMyImgs()
    {
        return myImgs;
    }

    public void setMyImgs(String myImgs)
    {
        this.myImgs = myImgs;
    }

    public String getServerId()
    {
        return serverId;
    }

    public void setServerId(String serverId)
    {
        this.serverId = serverId;
    }

    public Integer getPhotoNum()
    {
        return photoNum;
    }

    public void setPhotoNum(Integer photoNum)
    {
        this.photoNum = photoNum;
    }

    public String getPackageId()
    {
        return packageId;
    }

    public void setPackageId(String packageId)
    {
        this.packageId = packageId;
    }

}
