
package cn.com.czrz.action.mine;

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
import cn.com.czrz.entity.Diary;
import cn.com.czrz.entity.Follow;
import cn.com.czrz.entity.Users;
import cn.com.czrz.service.CommentService;
import cn.com.czrz.service.DiaryService;
import cn.com.czrz.service.FollowService;
import cn.com.czrz.service.UserService;

import com.google.gson.Gson;

@ParentPackage("mine")
public class FollowAction extends BaseAction
{
    private static final long serialVersionUID = -584517357400042256L;

    private FollowService followService = new FollowService();

    private DiaryService diaryService = new DiaryService();

    private UserService userService = new UserService();

    private CommentService commentService = new CommentService();

    private Follow follow;

    private Diary diary;

    private Users user;

    private String lastIdSign;

    public void follow() throws IOException
    {
        PrintWriter out = response.getWriter();
        if (!empty(diary) && !empty(diary.getId()))
        {

            diary = diaryService.get(diary.getId());
            if (diary.getUser_id().equals(getLoginUser().getId()))
            {
                out.print("self");
            }
            else
            {
                follow = new Follow();
                follow.setUser_id(diary.getUser_id());
                follow.setFollower_id(getLoginUser().getId());
                follow.setFollow_date(new Date());
                followService.save(follow);
                out.print(follow.getId());
            }
        }
        else
        {
            out.print("error");
        }
        out.close();
    }

    public void followUser() throws IOException
    {
        PrintWriter out = response.getWriter();
        if (!empty(user) && !empty(user.getId()))
        {
            follow = new Follow();
            follow.setUser_id(user.getId());
            follow.setFollower_id(getLoginUser().getId());
            follow.setFollow_date(new Date());
            followService.save(follow);
            out.print(follow.getId());
        }
        else
        {
            out.print("0");
        }
        out.close();
    }

    public void cancleFollow() throws IOException
    {
        PrintWriter out = response.getWriter();
        if (!empty(follow) && !empty(follow.getId()))
        {
            followService.deleteById(follow.getId());
            out.print(1);
        }
        else
        {
            out.print(2);
        }
        out.close();
    }

    public String list()
    {
        if (!empty(getLoginUser()))
        {
            String noncestr = "myFollow";
            String jsapi_ticket = (String) application.getAttribute("jsapi_ticket");
            long timestamp = new Date().getTime();
            String url = Constants.CZRZ_URL + "/mine/follow!list.action";
            String str = "jsapi_ticket=" + jsapi_ticket + "&noncestr="
                    + noncestr + "&timestamp=" + timestamp + "&url=" + url;
            String signature = DigestUtils.shaHex(str);
            request.setAttribute("noncestr", noncestr);
            request.setAttribute("timestamp", timestamp);
            request.setAttribute("signature", signature);
            request.setAttribute("appId", Constants.APPID);
            Map param = new HashMap();
            if (!empty(user) && !empty(user.getNickname()))
            {
                param.put("nickname", user.getNickname());
                request.setAttribute("nickname", user.getNickname());
            }
            param.put("followerId", getLoginUser().getId());
            param.put("lastIdSign", lastIdSign);
            List follows = followService.getFollows(param, "index");
            if (!empty(follows) && follows.size() > 0)
            {
                Map map = (Map) follows.get(follows.size() - 1);
                lastIdSign = map.get("fid").toString();
                request.setAttribute("lastIdSign", lastIdSign);
            }
            request.setAttribute("follows", follows);
        }
        else
        {
            request.setAttribute("isLogin", "no");
        }
        return "list";
    }

    public void loadData() throws IOException
    {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        if (!empty(lastIdSign))
        {
            Map param = new HashMap();
            param.put("lastIdSign", lastIdSign);
            if (!empty(user) && !empty(user.getNickname()))
            {
                param.put("nickname", user.getNickname());
            }
            param.put("followerId", getLoginUser().getId());
            List list = followService.getFollows(param, null);
            if (!empty(list) && list.size() > 0)
            {
                Gson gson = new Gson();
                String result = gson.toJson(list);
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

    public String personDiaryList()
    {
        if (!empty(user) & !empty(user.getId()) && !empty(follow)
                && !empty(follow.getId()))
        {
            Map param = new HashMap();
            user = userService.get(user.getId());
            param.put("userId", user.getId());
            param.put("loginUserId", getLoginUser().getId());
            param.put("searchType", "index");
            param.put("lastIdSign", lastIdSign);
            List diaryList = followService.findPersonDiarys(param);
            if (!empty(diaryList) && diaryList.size() > 0)
            {
                Map diaryMap = (Map) diaryList.get(diaryList.size() - 1);
                lastIdSign = diaryMap.get("id").toString();
                request.setAttribute("lastIdSign", lastIdSign);
            }
            request.setAttribute("diaries", diaryList);
            request.setAttribute("followId", follow.getId());
            request.setAttribute("userId", user.getId());
            request.setAttribute("nickname", user.getNickname());
        }
        return "person_diary_list";
    }

    public void loadPersonDiaryData() throws IOException
    {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        if (!empty(user) && !empty(user.getId()) && !empty(lastIdSign))
        {
            Map param = new HashMap();
            param.put("userId", user.getId());
            param.put("loginUserId", getLoginUser().getId());
            param.put("lastIdSign", lastIdSign);
            List diaryList = followService.findPersonDiarys(param);
            if (!empty(diaryList) && diaryList.size() > 0)
            {
                Gson gson = new Gson();
                String result = gson.toJson(diaryList);
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

    public String diaryDetail()
    {
        if (!empty(follow) && !empty(follow.getId()) && !empty(diary)
                && !empty(diary.getId()))
        {
            diary = diaryService.get(diary.getId());
            String images = diary.getImages();
            if (!empty(images) && images != "" && images != null)
            {
                Gson gson = new Gson();
                String[] diaryImages = gson.fromJson(images, String[].class);
                request.setAttribute("imgs", diaryImages);
            }
            Integer userId = 0;
            if (!empty(getLoginUser()))
            {
                user = userService.get(getLoginUser().getId());
                request.setAttribute("user", user);
                Follow follow = followService.findByUserId(diary.getUser_id(),
                        getLoginUser().getId());
                if (!empty(follow))
                {
                    request.setAttribute("follow", follow);
                }
                userId = user.getId();
            }
            request.setAttribute("followId", follow.getId());
            request.setAttribute("comments",
                    commentService.getComments(userId, diary.getId(), null, 0));
            request.setAttribute("likeId",
                    diaryService.getDiaryLikeId(diary.getId(), userId));
            request.setAttribute(
                    "likeCount",
                    diaryService.getLikeCount(diary.getId()) > 0 ? diaryService.getLikeCount(diary.getId())
                            : "");
            request.setAttribute("total",
                    diaryService.getCommentNum(diary.getId()));
            String noncestr = "followDiaryDetail";
            String jsapi_ticket = (String) application.getAttribute("jsapi_ticket");
            long timestamp = new Date().getTime();
            String url = Constants.CZRZ_URL
                    + "/mine/follow!diaryDetail.action?follow.id="
                    + follow.getId() + "&diary.id=" + diary.getId();
            String str = "jsapi_ticket=" + jsapi_ticket + "&noncestr="
                    + noncestr + "&timestamp=" + timestamp + "&url=" + url;
            String signature = DigestUtils.shaHex(str);
            request.setAttribute("noncestr", noncestr);
            request.setAttribute("timestamp", timestamp);
            request.setAttribute("signature", signature);
            request.setAttribute("appId", Constants.APPID);
            request.setAttribute("serverPath", Constants.CZRZ_URL);
            request.setAttribute("userId", userId);
            request.setAttribute("czrzurl", Constants.CZRZ_URL);
        }
        else
        {
            return ERROR;
        }

        return "diary_detail";
    }

    public Follow getFollow()
    {
        return follow;
    }

    public void setFollow(Follow follow)
    {
        this.follow = follow;
    }

    public Diary getDiary()
    {
        return diary;
    }

    public void setDiary(Diary diary)
    {
        this.diary = diary;
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
}
