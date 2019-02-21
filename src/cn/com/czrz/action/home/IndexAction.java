
package cn.com.czrz.action.home;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.struts2.convention.annotation.ParentPackage;

import cn.com.czrz.action.BaseAction;
import cn.com.czrz.common.Constants;
import cn.com.czrz.entity.RankRules;
import cn.com.czrz.entity.Users;
import cn.com.czrz.service.DiaryService;
import cn.com.czrz.service.RankService;
import cn.com.czrz.service.UserService;

import com.google.gson.Gson;

@ParentPackage("home")
public class IndexAction extends BaseAction
{
    private static final long serialVersionUID = -1919571077023692008L;

    private DiaryService diaryService = new DiaryService();

    private UserService userService = new UserService();

    private RankService rankService = new RankService();

    private Users user;

    private Integer type;

    private Integer inviter;

    private String lastIdSign;

    private String firstIdSign;

    private Integer model;

    public String index()
    {
        String code = request.getParameter("code");
        request.setAttribute("code", code);
        List diaryList = new ArrayList();
        Integer lastDiaryId = empty(lastIdSign) ? 0
                : Integer.parseInt(lastIdSign);
        Integer firstDiaryId = empty(firstIdSign) ? 0
                : Integer.parseInt(firstIdSign);
        if (!empty(getLoginUser()))
        {
            diaryList = diaryService.getIndexDiaryList(type,
                    getLoginUser().getId(), lastDiaryId, firstDiaryId, "index",
                    model);
        }
        else
        {
            diaryList = diaryService.getIndexDiaryList(type, null, lastDiaryId,
                    firstDiaryId, "index", model);

        }
        if (!empty(getLoginUser()))
        {
            user = userService.get(getLoginUser().getId());
        }
        request.setAttribute("diaryList", diaryList);
        if (!empty(diaryList))
        {
            Map lastDiaryMap = (Map) diaryList.get(diaryList.size() - 1);
            request.setAttribute("lastIdSign", lastDiaryMap.get("aid"));
            Map firstDiaryMap = (Map) diaryList.get(0);
            request.setAttribute("firstIdSign", firstDiaryMap.get("aid"));
        }
        request.setAttribute("type", type);
        request.setAttribute("model", model);
        String noncestr = "index";
        String jsapi_ticket = (String) application.getAttribute("jsapi_ticket");
        long timestamp = new Date().getTime();
        String url = Constants.CZRZ_URL + "/home/index!index.action?model="
                + model;
        String str = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + noncestr
                + "&timestamp=" + timestamp + "&url=" + url;
        String signature = DigestUtils.shaHex(str);
        request.setAttribute("noncestr", noncestr);
        request.setAttribute("timestamp", timestamp);
        request.setAttribute("signature", signature);
        request.setAttribute("appId", Constants.APPID);
        return "index";
    }

    public void loadData() throws IOException
    {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        if (!empty(lastIdSign) || !empty(firstIdSign))
        {
            Integer lastDiaryId = empty(lastIdSign) ? 0
                    : Integer.parseInt(lastIdSign);
            Integer firstDiaryId = empty(firstIdSign) ? 0
                    : Integer.parseInt(firstIdSign);
            List diaryList = new ArrayList();
            if (!empty(getLoginUser()))
            {
                diaryList = diaryService.getIndexDiaryList(type,
                        getLoginUser().getId(), lastDiaryId, firstDiaryId,
                        null, model);
            }
            else
            {
                diaryList = diaryService.getIndexDiaryList(type, null,
                        lastDiaryId, firstDiaryId, null, model);
            }
            if (diaryList.size() == 0)
            {
                out.print("0");
            }
            else
            {
                Gson gson = new Gson();
                String result = gson.toJson(diaryList);
                out.print(result);
            }
        }
        else
        {
            out.print("-1");
        }
        out.close();
    }

    private ByteArrayInputStream inputStream;

    public String imgcode() throws Exception
    {
        // 在内存中创建图象
        int width = 55, height = 20;
        BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        // 获取图形上下文
        Graphics g = image.getGraphics();
        // 生成随机类
        Random random = new Random();
        // 设定背景色
        g.setColor(getRandColor(200, 250));
        g.fillRect(0, 0, width, height);
        // 设定字体
        g.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        // 随机产生155条干扰线，使图象中的认证码不易被其它程序探测到
        g.setColor(getRandColor(160, 200));
        for (int i = 0; i < 1; i++)
        {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            g.drawLine(x, y, x + xl, y + yl);
        }
        // 取随机产生的认证码(6位数字)
        String sRand = "";
        for (int i = 0; i < 4; i++)
        {
            String rand = String.valueOf(random.nextInt(10));
            sRand += rand;
            // 将认证码显示到图象中
            g.setColor(new Color(20 + random.nextInt(110),
                    20 + random.nextInt(110), 20 + random.nextInt(110)));
            // 调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
            g.drawString(rand, 13 * i + 6, 16);
        }
        // 将认证码存入SESSION
        session.setAttribute(Constants.IMAGE_CODE, sRand);
        // 图象生效
        g.dispose();
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        ImageOutputStream imageOut = ImageIO.createImageOutputStream(output);
        ImageIO.write(image, "JPEG", imageOut);
        imageOut.close();
        ByteArrayInputStream input = new ByteArrayInputStream(
                output.toByteArray());
        this.setInputStream(input);
        return "imgcode";
    }

    /*
     * 给定范围获得随机颜色
     */
    private Color getRandColor(int fc, int bc)
    {
        Random random = new Random();
        if (fc > 255)
            fc = 255;
        if (bc > 255)
            bc = 255;
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }

    public String rankList()
    {
        RankRules rr = rankService.get();
        List rankList = (List) application.getAttribute("rank_list");
        request.setAttribute("rankList", rankList);
        if (!empty(rr) && !empty(rankList))
        {
            if (rr.getTable_name().equals("diary"))
            {
                return "diary_rank_list";
            }
        }
        return "rank_list";
    }

    public void setInputStream(ByteArrayInputStream inputStream)
    {
        this.inputStream = inputStream;
    }

    public String head()
    {
        return "head";
    }

    public String footer()
    {
        if (!empty(getLoginUser()))
        {
            user = userService.get(getLoginUser().getId());
        }

        return "footer";
    }

    public String login()
    {
        return "login";
    }

    public Integer getType()
    {
        return type;
    }

    public void setType(Integer type)
    {
        this.type = type;
    }

    public ByteArrayInputStream getInputStream()
    {
        return inputStream;
    }

    public Integer getInviter()
    {
        return inviter;
    }

    public void setInviter(Integer inviter)
    {
        this.inviter = inviter;
    }

    public Users getUser()
    {
        return user;
    }

    public void setUser(Users user)
    {
        this.user = user;
    }

    public String getLastIdSign()
    {
        return lastIdSign;
    }

    public void setLastIdSign(String lastIdSign)
    {
        this.lastIdSign = lastIdSign;
    }

    public String getFirstIdSign()
    {
        return firstIdSign;
    }

    public void setFirstIdSign(String firstIdSign)
    {
        this.firstIdSign = firstIdSign;
    }

    public Integer getModel()
    {
        return model;
    }

    public void setModel(Integer model)
    {
        this.model = model;
    }

}
