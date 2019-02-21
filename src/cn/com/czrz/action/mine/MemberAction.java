
package cn.com.czrz.action.mine;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.ParentPackage;

import cn.com.czrz.action.BaseAction;
import cn.com.czrz.common.Constants;
import cn.com.czrz.entity.Circle;
import cn.com.czrz.entity.CircleUser;
import cn.com.czrz.entity.Diary;
import cn.com.czrz.entity.Users;
import cn.com.czrz.service.CircleService;
import cn.com.czrz.service.UserService;
import cn.com.czrz.util.InviteCode;
import cn.com.gwypx.util.Utility;

@ParentPackage("mine")
public class MemberAction extends BaseAction
{
    private static final long serialVersionUID = -6295774829657439766L;

    private UserService userService = new UserService();

    private CircleService circleService = new CircleService();

    private Circle circle;

    private Users user;

    private String updateType;

    private Diary diary;

    private String followId;

    public String detail()
    {
        if (!empty(getLoginUser()))
        {
            user = userService.get(getLoginUser().getId());
            request.setAttribute("user", user);
            String noncestr = "memberDetail";
            String jsapi_ticket = (String) application.getAttribute("jsapi_ticket");
            long timestamp = new Date().getTime();
            String url = Constants.CZRZ_URL + "/mine/member!detail.action";
            String str = "jsapi_ticket=" + jsapi_ticket + "&noncestr="
                    + noncestr + "&timestamp=" + timestamp + "&url=" + url;
            String signature = DigestUtils.shaHex(str);
            request.setAttribute("noncestr", noncestr);
            request.setAttribute("timestamp", timestamp);
            request.setAttribute("signature", signature);
            request.setAttribute("appId", Constants.APPID);
            String openid = (String) request.getSession()
                    .getAttribute("openid");
            request.setAttribute("openid", openid);
        }

        return "detail";
    }

    public String showInfo()
    {
        if (!empty(getLoginUser()))
        {
            user = userService.get(getLoginUser().getId());
            if (!empty(user.getArea()))
            {
                request.setAttribute(
                        "area",
                        userService.getMyCityName(user.getArea()).replaceAll(
                                ",", "-"));
            }
            request.setAttribute("user", user);
        }
        else
        {
            message = "请先登录";
            return ALERT;
        }

        return "info";
    }

    public String detailForCircle()
    {
        if (!empty(user.getId()))
        {
            user = userService.get(user.getId());
            circle = circleService.get(circle.getId());
            String noncestr = "detailForCircle";
            String jsapi_ticket = (String) application.getAttribute("jsapi_ticket");
            long timestamp = new Date().getTime();
            String url = Constants.CZRZ_URL
                    + "/mine/member!detailForCircle.action?user.id="
                    + user.getId() + "&circle.id=" + circle.getId();
            String str = "jsapi_ticket=" + jsapi_ticket + "&noncestr="
                    + noncestr + "&timestamp=" + timestamp + "&url=" + url;
            String signature = DigestUtils.shaHex(str);
            request.setAttribute("noncestr", noncestr);
            request.setAttribute("timestamp", timestamp);
            request.setAttribute("signature", signature);
            request.setAttribute("appId", Constants.APPID);
            request.setAttribute("serverPath", Constants.CZRZ_URL);
            request.setAttribute("circle", circle);
            CircleUser cu = circleService.getCircleUser(user.getId(),
                    circle.getId());
            request.setAttribute("lastIdSign", cu.getId());
            if (!empty(user.getArea()))
            {
                request.setAttribute(
                        "area",
                        userService.getMyCityName(user.getArea()).replaceAll(
                                ",", "-"));
            }
        }
        else
        {
            message = "参数错误，请稍后重试，或联系客服";
            return ALERT;
        }
        return "detail_for_circle";
    }

    public String detailForFollow()
    {
        if (!empty(user) && !empty(user.getId()) && !empty(followId))
        {
            user = userService.get(user.getId());
            String noncestr = "detailForFollow";
            String jsapi_ticket = (String) application.getAttribute("jsapi_ticket");
            long timestamp = new Date().getTime();
            String url = Constants.CZRZ_URL
                    + "/mine/member!detailForFollow.action?user.id="
                    + user.getId() + "&follow.id=" + followId;
            String str = "jsapi_ticket=" + jsapi_ticket + "&noncestr="
                    + noncestr + "&timestamp=" + timestamp + "&url=" + url;
            String signature = DigestUtils.shaHex(str);
            request.setAttribute("noncestr", noncestr);
            request.setAttribute("timestamp", timestamp);
            request.setAttribute("signature", signature);
            request.setAttribute("appId", Constants.APPID);
            request.setAttribute("serverPath", Constants.CZRZ_URL);
            request.setAttribute("followId", followId);
            if (!empty(user.getArea()))
            {
                request.setAttribute(
                        "area",
                        userService.getMyCityName(user.getArea()).replaceAll(
                                ",", "-"));
            }
        }
        else
        {
            message = "参数错误，请稍后重试，或联系客服";
            return ALERT;
        }
        return "detail_for_follow";
    }

    public void update() throws IOException
    {
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        Users oldUser = userService.get(getLoginUser().getId());
        if (updateType.equals("1"))
        {
            oldUser.setNickname(user.getNickname());
        }
        else if (updateType.equals("2"))
        {
            String pass = user.getPassword();
            String password = Utility.MD5(Constants.BEFORE_SALT + pass
                    + oldUser.getPassword_salt());
            oldUser.setPassword(password);
            userService.update(oldUser);
            out.print("password");
        }
        else if (updateType.equals("3"))
        {
            oldUser.setSex(user.getSex());
            out.print(oldUser.getSex() == 0 ? "女" : "男");
        }
        else if (updateType.equals("4"))
        {
            oldUser.setBirthday(user.getBirthday());
        }
        else if (updateType.equals("5"))
        {
            oldUser.setBaby_birthday(user.getBaby_birthday());
        }
        else if (updateType.equals("6"))
        {
            oldUser.setEmail(user.getEmail());
        }
        else if (updateType.equals("7"))
        {
            oldUser.setArea(user.getArea());
            String area = userService.getMyCityName(user.getArea()).replaceAll(
                    ",", "-");
            out.print(area);
        }
        else if (updateType.equals("8"))
        {
            oldUser.setWchat_no(user.getWchat_no());
            oldUser.setIs_wchat_open(user.getIs_wchat_open());
        }
        else if (updateType.equals("9"))
        {
            oldUser.setIntroduction(user.getIntroduction());
            out.print("introduction");
        }
        userService.update(oldUser);
        out.close();
    }

    public String createInvitation() throws Exception
    {
        String path = ServletActionContext.getServletContext() // 得到图片保存的位置(根据root来得到图片保存的路径在tomcat下的该工程里)
                .getRealPath("inviteCode") + "\\"
                + getLoginUser().getLogin_name() + ".gif";
        File file = new File(path);
        if (!file.exists())
        {
            InviteCode inviteCode = new InviteCode();
            inviteCode.createCode(getLoginUser().getId(), path);
        }
        return "to_invite";
    }

    public Users getUser()
    {
        return user;
    }

    public void setUser(Users user)
    {
        this.user = user;
    }

    public String getUpdateType()
    {
        return updateType;
    }

    public void setUpdateType(String updateType)
    {
        this.updateType = updateType;
    }

    public Diary getDiary()
    {
        return diary;
    }

    public void setDiary(Diary diary)
    {
        this.diary = diary;
    }

    public Circle getCircle()
    {
        return circle;
    }

    public void setCircle(Circle circle)
    {
        this.circle = circle;
    }

    public String getFollowId()
    {
        return followId;
    }

    public void setFollowId(String followId)
    {
        this.followId = followId;
    }

}
