
package cn.com.czrz.action.home;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.struts2.convention.annotation.ParentPackage;

import cn.com.czrz.action.BaseAction;
import cn.com.czrz.common.Constants;
import cn.com.czrz.entity.CommentLike;
import cn.com.czrz.entity.Diary;
import cn.com.czrz.entity.DiaryComment;
import cn.com.czrz.entity.DiaryLike;
import cn.com.czrz.entity.Follow;
import cn.com.czrz.entity.Users;
import cn.com.czrz.service.CommentService;
import cn.com.czrz.service.CommonService;
import cn.com.czrz.service.DiaryService;
import cn.com.czrz.service.FollowService;
import cn.com.czrz.service.UserService;

import com.google.gson.Gson;

@ParentPackage("home")
public class DiaryAction extends BaseAction
{
    private static final long serialVersionUID = 6761970190988243935L;

    private DiaryService diaryService = new DiaryService();

    private UserService userService = new UserService();

    private FollowService followService = new FollowService();

    private CommonService commonService = new CommonService();

    private CommentService commentService = new CommentService();

    private Diary diary;

    private Users user;

    private Integer model;

    private String comment;

    private DiaryComment diaryComment;

    private Integer likeId;

    private String firstCommentIdSign;

    private String lastCommentIdSign;

    private String fromSon;

    private String fromPath;

    public String detail()
    {
        String diaryOpenness = diaryService.getDiaryOpenness(diary.getId());
        if (!diaryOpenness.equals("3"))
        {
            return ERROR;
        }
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
        request.setAttribute("comments",
                commentService.getComments(userId, diary.getId(), null, 0));
        request.setAttribute("likeId",
                diaryService.getDiaryLikeId(diary.getId(), userId));
        Long likeCount = diary.getLike_count() + diary.getCurrent_month_likes();
        request.setAttribute("likeCount", likeCount);
        request.setAttribute("total", diaryService.getCommentNum(diary.getId()));
        String noncestr = "diaryIndexDetail";
        String jsapi_ticket = (String) application.getAttribute("jsapi_ticket");
        long timestamp = new Date().getTime();
        String url = Constants.CZRZ_URL + "/home/diary!detail.action?diary.id="
                + diary.getId();
        String str = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + noncestr
                + "&timestamp=" + timestamp + "&url=" + url;
        String signature = DigestUtils.shaHex(str);
        request.setAttribute("noncestr", noncestr);
        request.setAttribute("timestamp", timestamp);
        request.setAttribute("signature", signature);
        request.setAttribute("appId", Constants.APPID);
        request.setAttribute("serverPath", Constants.CZRZ_URL);
        request.setAttribute("model", model);
        request.setAttribute("userId", userId);
        request.setAttribute("czrzurl", Constants.CZRZ_URL);
        request.setAttribute("fromPath", fromPath);
        return "detail";
    }

    public void saveComment() throws IOException
    {
        PrintWriter out = response.getWriter();
        if (!empty(diary) && !empty(diary.getId()))
        {
            DiaryComment diaryComment = new DiaryComment();
            diaryComment.setDiary_id(diary.getId());
            diaryComment.setUser_id(getLoginUser().getId());
            diaryComment.setContent(comment);
            diaryComment.setNickname(getLoginUser().getNickname());
            diaryComment.setCreate_time(new Date());
            diaryComment.setPid(0);
            commentService.saveDiaryComment(diaryComment);
            diaryComment.setCode(diaryComment.getPid() + ","
                    + diaryComment.getId() + ",");
            commentService.updateDiaryComment(diaryComment);
            out.print(diaryComment.getId());
        }
        else
        {
            out.print(0);
        }
        out.close();
    }

    public void saveComentSon() throws IOException
    {
        PrintWriter out = response.getWriter();
        if (!empty(diaryComment) && !empty(diaryComment.getId()))
        {
            diaryComment = commentService.getDiaryComment(diaryComment.getId());
            DiaryComment dc = new DiaryComment();
            dc.setPid(diaryComment.getId());
            dc.setNickname(getLoginUser().getNickname());
            dc.setContent(comment);
            dc.setUser_id(getLoginUser().getId());
            dc.setCreate_time(new Date());
            dc.setDiary_id(diaryComment.getDiary_id());
            commentService.saveDiaryComment(dc);
            dc.setCode(diaryComment.getCode() + dc.getId() + ",");
            commentService.updateDiaryComment(dc);
            out.print(dc.getId());
        }
        else
        {
            out.print(0);
        }
        out.close();
    }

    public void likeComment() throws IOException
    {
        PrintWriter out = response.getWriter();
        if (!empty(diaryComment) && !empty(diaryComment.getId()))
        {
            CommentLike like = new CommentLike();
            like.setComment_id(diaryComment.getId());
            like.setUser_id(getLoginUser().getId());
            like.setCreate_time(new Date());
            diaryService.saveLike(like);
            out.print(like.getId());
        }
        else
        {
            out.print(0);
        }
        out.close();
    }

    public void cancleCommentLike() throws IOException
    {
        PrintWriter out = response.getWriter();
        if (!empty(likeId))
        {
            commentService.deleteCommentLike(likeId);
            out.print(1);
        }
        else
        {
            out.print(0);
        }
        out.close();
    }

    public void likeDiary() throws IOException
    {
        PrintWriter out = response.getWriter();
        if (!empty(diary) && !empty(diary.getId()))
        {
            DiaryLike dl = new DiaryLike();
            dl.setDiary_id(diary.getId());
            dl.setUser_id(getLoginUser().getId());
            dl.setCreate_time(new Date());
            commonService.saveDiaryLike(dl);
            out.print(dl.getId());
        }
        else
        {
            out.print(0);
        }
        out.close();
    }

    public void cancleLikeDiary() throws IOException
    {
        PrintWriter out = response.getWriter();
        if (!empty(likeId))
        {
            commonService.deleteDiaryList(likeId);
            out.print(1);
        }
        else
        {
            out.print(0);
        }
        out.close();
    }

    public String showFamilyComments()
    {
        if (!empty(diaryComment) && !empty(diaryComment.getId()))
        {
            diaryComment = commentService.getDiaryComment(diaryComment.getId());
            Integer userId = 0;
            if (!empty(getLoginUser()))
            {
                userId = getLoginUser().getId();
                user = userService.get(userId);
            }
            Integer lastId = empty(lastCommentIdSign) ? 0
                    : Integer.parseInt(lastCommentIdSign);
            List family = commentService.getCommentFamily(diaryComment, lastId,
                    userId);
            if (family.size() > 0)
            {
                Map sonMap = (Map) family.get(family.size() - 1);
                request.setAttribute("lastCommentIdSign", sonMap.get("id")
                        .toString());
            }
            request.setAttribute("family", family);
            request.setAttribute("likeNum",
                    commentService.getCommentLikeNum(diaryComment.getId()));
            request.setAttribute("likeId", commentService.getCommentLikeId(
                    diaryComment.getId(), userId));
            request.setAttribute("commentUser",
                    userService.get(diaryComment.getUser_id()));
            request.setAttribute("diaryId", diaryComment.getDiary_id());
            request.setAttribute("model", model);
        }
        return "comment_family";
    }

    public String moreComments()
    {
        if (!empty(diary) && !empty(diary.getId()))
        {
            diary = diaryService.get(diary.getId());
            Integer userId = 0;
            if (!empty(getLoginUser()))
            {
                userId = getLoginUser().getId();
                request.setAttribute("userId", userId);
                user = userService.get(userId);
            }
            Integer searchType = 0;
            if (!empty(fromSon))
            {
                searchType = 1;
            }
            List commentList = commentService.getComments(userId,
                    diary.getId(), lastCommentIdSign, searchType);
            request.setAttribute("commentList", commentList);
            if (commentList.size() > 0)
            {
                Map firstComment = (Map) commentList.get(0);
                firstCommentIdSign = firstComment.get("id").toString();
                request.setAttribute("firstCommentIdSign", firstCommentIdSign);
                Map lastComment = (Map) commentList.get(commentList.size() - 1);
                lastCommentIdSign = lastComment.get("id").toString();
                request.setAttribute("lastCommentIdSign", lastCommentIdSign);
            }
            request.setAttribute("model", model);
        }
        return "more_comments";
    }

    public void loadMoreComments() throws IOException
    {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        if (!empty(diary) && !empty(diary.getId()))
        {
            diary = diaryService.get(diary.getId());
            Integer userId = 0;
            if (!empty(getLoginUser()))
            {
                userId = getLoginUser().getId();
            }
            if (!empty(lastCommentIdSign))
            {
                List commentList = commentService.getComments(userId,
                        diary.getId(), lastCommentIdSign, 0);
                if (commentList.size() == 0)
                {
                    out.print("0");
                }
                else
                {
                    Gson gson = new Gson();
                    String result = gson.toJson(commentList);
                    out.print(result);
                }
            }
        }
        else
        {
            out.print("-1");
        }
        out.close();
    }

    public String showSonComments()
    {
        if (!empty(diaryComment) && !empty(diaryComment.getId()))
        {
            diaryComment = commentService.getDiaryComment(diaryComment.getId());
            request.setAttribute("lastCommentIdSign", diaryComment.getId());
            Integer userId = 0;
            if (!empty(getLoginUser()))
            {
                userId = getLoginUser().getId();
                request.setAttribute("userId", userId);
            }
            Integer lastId = empty(lastCommentIdSign) ? 0
                    : Integer.parseInt(lastCommentIdSign);
            List family = commentService.getCommentFamily(diaryComment, lastId,
                    userId);
            if (family.size() > 0)
            {
                Map sonMap = (Map) family.get(family.size() - 1);
                request.setAttribute("lastCommentIdSign", sonMap.get("id")
                        .toString());
            }
            request.setAttribute("family", family);
            request.setAttribute("likeNum",
                    commentService.getCommentLikeNum(diaryComment.getId()));
            request.setAttribute("likeId", commentService.getCommentLikeId(
                    diaryComment.getId(), userId));
            request.setAttribute("commentUser",
                    userService.get(diaryComment.getUser_id()));
            request.setAttribute("diaryId", diaryComment.getDiary_id());
            request.setAttribute("model", model);
        }
        return "show_son_comments";
    }

    public void loadSonComments() throws IOException
    {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        if (!empty(diaryComment) && !empty(diaryComment.getId()))
        {
            if (!empty(lastCommentIdSign))
            {
                Integer userId = 0;
                if (!empty(getLoginUser()))
                {
                    userId = getLoginUser().getId();
                }
                Integer lastId = Integer.parseInt(lastCommentIdSign);
                List sonComments = commentService.getCommentFamily(
                        diaryComment, lastId, userId);
                if (sonComments.size() == 0)
                {
                    out.print("0");
                }
                else
                {
                    Gson gson = new Gson();
                    String result = gson.toJson(sonComments);
                    out.print(result);
                }
            }
            else
            {
                out.print("0");
            }
        }
        else
        {
            out.print("0");
        }
        out.close();
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

    public Integer getModel()
    {
        return model;
    }

    public void setModel(Integer model)
    {
        this.model = model;
    }

    public String getComment()
    {
        return comment;
    }

    public void setComment(String comment)
    {
        this.comment = comment;
    }

    public DiaryComment getDiaryComment()
    {
        return diaryComment;
    }

    public void setDiaryComment(DiaryComment diaryComment)
    {
        this.diaryComment = diaryComment;
    }

    public Integer getLikeId()
    {
        return likeId;
    }

    public void setLikeId(Integer likeId)
    {
        this.likeId = likeId;
    }

    public String getFirstCommentIdSign()
    {
        return firstCommentIdSign;
    }

    public void setFirstCommentIdSign(String firstCommentIdSign)
    {
        this.firstCommentIdSign = firstCommentIdSign;
    }

    public String getLastCommentIdSign()
    {
        return lastCommentIdSign;
    }

    public void setLastCommentIdSign(String lastCommentIdSign)
    {
        this.lastCommentIdSign = lastCommentIdSign;
    }

    public String getFromSon()
    {
        return fromSon;
    }

    public void setFromSon(String fromSon)
    {
        this.fromSon = fromSon;
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
