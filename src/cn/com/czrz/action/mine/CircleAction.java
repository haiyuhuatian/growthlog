
package cn.com.czrz.action.mine;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
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
import cn.com.czrz.entity.Circle;
import cn.com.czrz.entity.CircleUser;
import cn.com.czrz.entity.Diary;
import cn.com.czrz.entity.Follow;
import cn.com.czrz.entity.Users;
import cn.com.czrz.service.CircleService;
import cn.com.czrz.service.CommentService;
import cn.com.czrz.service.DiaryService;
import cn.com.czrz.service.FollowService;
import cn.com.czrz.service.UserService;
import cn.com.gwypx.util.RequestUtil;

import com.google.gson.Gson;

@ParentPackage("mine")
public class CircleAction extends BaseAction
{
    private static final long serialVersionUID = -8753205507359317908L;

    private CircleService circleService = new CircleService();

    private UserService userService = new UserService();

    private DiaryService diaryService = new DiaryService();

    private CommentService commentService = new CommentService();

    private FollowService followService = new FollowService();

    private Circle circle;

    private String image;

    private Users user;

    private String coverPath;

    private Integer type;

    private Integer member;

    private String method;

    private String lastIdSign;

    private String firstIdSign;

    private Diary diary;

    private String nickname;

    private String fromPath;

    public String myCircle()
    {
        if (!empty(getLoginUser()))
        {
            String noncestr = "myCircle";
            String jsapi_ticket = (String) application.getAttribute("jsapi_ticket");
            long timestamp = new Date().getTime();
            String url = Constants.CZRZ_URL + "/mine/circle!myCircle.action";
            String str = "jsapi_ticket=" + jsapi_ticket + "&noncestr="
                    + noncestr + "&timestamp=" + timestamp + "&url=" + url;
            String signature = DigestUtils.shaHex(str);
            request.setAttribute("noncestr", noncestr);
            request.setAttribute("timestamp", timestamp);
            request.setAttribute("signature", signature);
            request.setAttribute("appId", Constants.APPID);
            request.setAttribute("myCircles",
                    circleService.getMyCircles(getLoginUser().getId()));
            request.setAttribute("type", type);
        }
        else
        {
            request.setAttribute("isLogin", "no");
        }
        return "myCircle";
    }

    public String detail()
    {
        circle = circleService.get(circle.getId());
        request.setAttribute("createUser",
                userService.get(circle.getCreate_user()).getNickname());
        request.setAttribute("fromPath", fromPath);
        return "detail";
    }

    public void joinCircle() throws IOException
    {
        PrintWriter out = response.getWriter();
        try
        {
            circle = circleService.get(circle.getId());
            CircleUser cu = new CircleUser();
            cu.setCircle_id(circle.getId());
            cu.setUser_id(getLoginUser().getId());
            cu.setCreate_time(new Date());
            circleService.saveCircleUser(cu);
            out.print(circle.getId());
        }
        catch (Exception e)
        {
            e.printStackTrace();
            out.print("");
        }

        out.close();
    }

    public void exitCircle() throws IOException
    {
        PrintWriter out = response.getWriter();
        try
        {
            circle = circleService.get(circle.getId());
            // 如果有的日志设置了该圈子可见则要删除
            circleService.cancleCircleOpen(circle.getId(),
                    getLoginUser().getId());
            out.print("1");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            out.print("");
        }
        out.close();
    }

    public String buildCircle()
    {
        request.setAttribute("method", "saveCircle");
        return "build_circle";
    }

    public void checkCircleName() throws IOException
    {
        PrintWriter out = response.getWriter();
        Integer count = circleService.findByName(circle.getName());
        out.print(count);
        out.close();
    }

    public String saveCircle()
    {
        circle.setCreate_user(getLoginUser().getId());
        circle.setCreate_time(new Date());
        circleService.save(circle);
        CircleUser cu = new CircleUser();
        cu.setCircle_id(circle.getId());
        cu.setUser_id(getLoginUser().getId());
        cu.setCreate_time(new Date());
        circleService.saveCircleUser(cu);
        method = "saveCover";
        this.redirectionUrl = "circle!setCover.action?circle.id="
                + circle.getId() + "&method=saveCover";
        message = "创建圈子成功，赶快为你的圈子设置一个封面吧";
        return ALERT;
    }

    public String edit()
    {
        circle = circleService.get(circle.getId());
        request.setAttribute("cover", circle.getCover());
        request.setAttribute("method", "update");
        return "build_circle";
    }

    public String update()
    {
        Circle oldCircle = circleService.get(circle.getId());
        oldCircle.setName(circle.getName());
        oldCircle.setDescribetion(circle.getDescribetion());
        oldCircle.setType(circle.getType());
        circleService.update(oldCircle);
        return "diary_list";
    }

    public String setCover()
    {
        String noncestr = "setCircleCover";
        String jsapi_ticket = (String) application.getAttribute("jsapi_ticket");
        long timestamp = new Date().getTime();
        String url = Constants.CZRZ_URL
                + "/mine/circle!setCover.action?circle.id=" + circle.getId()
                + "&method=" + method;
        String str = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + noncestr
                + "&timestamp=" + timestamp + "&url=" + url;
        String signature = DigestUtils.shaHex(str);
        request.setAttribute("noncestr", noncestr);
        request.setAttribute("timestamp", timestamp);
        request.setAttribute("signature", signature);
        request.setAttribute("appId", Constants.APPID);
        circle = circleService.get(circle.getId());
        request.setAttribute("cover", circle.getCover());
        request.setAttribute("method", method);
        return "set_cover";
    }

    public String temporary()
    {
        if (!empty(image))
        {
            response.setHeader("Access-Control-Allow-Origin", "*");
            image = image.replaceAll("data:image/png;base64,", "");
            image = image.replaceAll("data:image/jpg;base64,", "");
            image = image.replaceAll("data:image/jgp;base64,", "");
            image = image.replaceAll("data:image/gif;base64,", "");
            image = image.replaceAll("data:image/jpeg;base64,", "");
            BASE64Decoder decoder = new BASE64Decoder();
            // Base64解码
            byte[] imageByte = null;
            try
            {
                imageByte = decoder.decodeBuffer(image);
                for (int i = 0; i < imageByte.length; ++i)
                {
                    if (imageByte[i] < 0)
                    {// 调整异常数据
                        imageByte[i] += 256;
                    }
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            String fileName = "temporary" + circle.getId() + ".jpg";
            String filePath = ServletActionContext.getServletContext() // 得到图片保存的位置(根据root来得到图片保存的路径在tomcat下的该工程里)
                    .getRealPath("circle_cover") + "\\" + fileName;
            try
            {
                // 生成文件
                File imageFile = new File(filePath);
                imageFile.createNewFile();
                if (!imageFile.exists())
                {
                    imageFile.createNewFile();
                }
                OutputStream imageStream = new FileOutputStream(imageFile);
                imageStream.write(imageByte);
                imageStream.flush();
                imageStream.close();
                circle = circleService.get(circle.getId());
                request.setAttribute("cover", "temporary" + circle.getId()
                        + ".jpg");
                request.setAttribute("method", method);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return "circle_set_cover";
    }

    public String saveCover()
    {
        circle = circleService.get(circle.getId());
        response.setHeader("Access-Control-Allow-Origin", "*");
        image = image.replaceAll("data:image/png;base64,", "");
        image = image.replaceAll("data:image/jpg;base64,", "");
        image = image.replaceAll("data:image/jgp;base64,", "");
        image = image.replaceAll("data:image/gif;base64,", "");
        image = image.replaceAll("data:image/jpeg;base64,", "");
        BASE64Decoder decoder = new BASE64Decoder();
        // Base64解码
        byte[] imageByte = null;
        try
        {
            imageByte = decoder.decodeBuffer(image);
            for (int i = 0; i < imageByte.length; ++i)
            {
                if (imageByte[i] < 0)
                {// 调整异常数据
                    imageByte[i] += 256;
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        File oldCover = new File(ServletActionContext.getServletContext() // 得到图片保存的位置(根据root来得到图片保存的路径在tomcat下的该工程里)
                .getRealPath("circle_cover") + "\\" + circle.getCover());
        if (oldCover.exists())
        {
            oldCover.delete();
        }
        File temporaryFile = new File(ServletActionContext.getServletContext() // 得到图片保存的位置(根据root来得到图片保存的路径在tomcat下的该工程里)
                .getRealPath("circle_cover") + "\\" + "temporary"
                + circle.getId() + ".jpg");
        if (temporaryFile.exists())
        {
            temporaryFile.delete();
        }
        String coverName = new Date().getTime() + ".jpg";
        circle.setCover(coverName);
        circleService.update(circle);
        // 生成文件路径
        String filename = ServletActionContext.getServletContext() // 得到图片保存的位置(根据root来得到图片保存的路径在tomcat下的该工程里)
                .getRealPath("circle_cover") + "\\" + coverName;

        try
        {
            // 生成文件
            File imageFile = new File(filename);
            imageFile.createNewFile();
            if (!imageFile.exists())
            {
                imageFile.createNewFile();
            }
            OutputStream imageStream = new FileOutputStream(imageFile);
            imageStream.write(imageByte);
            imageStream.flush();
            imageStream.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        if (method.equals("saveCover"))
        {
            message = "封面保存成功，赶紧让小伙伴们加入你的圈子吧";
            this.redirectionUrl = "circle!myCircle.action?type=0";
            return ALERT;
        }
        else
        {
            request.setAttribute("method", method);
            this.redirectionUrl = "circle!edit.action?circle.id="
                    + circle.getId();
            message = "封面修改成功";
            return ALERT;
        }

    }

    public String diaryList()
    {
        circle = circleService.get(circle.getId());
        Integer lastDiaryId = empty(lastIdSign) ? 0
                : Integer.parseInt(lastIdSign);
        Integer firstDiaryId = empty(firstIdSign) ? 0
                : Integer.parseInt(firstIdSign);
        List diaryList = circleService.getDiaryList(circle.getId(),
                lastDiaryId, firstDiaryId, "index");
        request.setAttribute("diaries", diaryList);
        request.setAttribute("create_user",
                userService.get(circle.getCreate_user()).getNickname());
        request.setAttribute("userSize",
                circleService.getCircleUserNum(circle.getId()));
        request.setAttribute("circle", circle);
        if (!empty(diaryList))
        {
            Map lastDiaryMap = (Map) diaryList.get(diaryList.size() - 1);
            request.setAttribute("lastIdSign", lastDiaryMap.get("id"));
            Map firstDiaryMap = (Map) diaryList.get(0);
            request.setAttribute("firstIdSign", firstDiaryMap.get("id"));
        }
        return "diary_list";
    }

    public void loadData() throws IOException
    {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        if (!empty(lastIdSign) || !empty(firstIdSign) || empty(circle)
                || empty(circle.getId()))
        {
            Integer lastDiaryId = empty(lastIdSign) ? 0
                    : Integer.parseInt(lastIdSign);
            Integer firstDiaryId = empty(firstIdSign) ? 0
                    : Integer.parseInt(firstIdSign);
            List diaryList = circleService.getDiaryList(circle.getId(),
                    lastDiaryId, firstDiaryId, "");
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

    public String diaryDetail()
    {
        if (!empty(circle) && !empty(circle.getId()) && !empty(diary)
                && !empty(diary.getId()))
        {
            diary = diaryService.get(diary.getId());
            circle = circleService.get(circle.getId());
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
            request.setAttribute("circle", circle);
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
            String noncestr = "circleDiaryDetail";
            String jsapi_ticket = (String) application.getAttribute("jsapi_ticket");
            long timestamp = new Date().getTime();
            String url = Constants.CZRZ_URL
                    + "/mine/circle!diaryDetail.action?circle.id="
                    + circle.getId() + "&diary.id=" + diary.getId();
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

    public String userList()
    {
        circle = circleService.get(circle.getId());
        String key = "circleUserList";
        if (request.getParameter("init") != null)
        {
            session.removeAttribute(key);
        }
        Map paramMap = RequestUtil.processParameters(request, key);
        request.setAttribute("create_user",
                userService.get(circle.getCreate_user()).getNickname());
        Integer lastId = null;
        if (!empty(lastIdSign))
        {
            lastId = Integer.parseInt(lastIdSign);
        }
        List userList = circleService.getCircleUsers(paramMap, circle.getId(),
                getLoginUser().getId(), lastId, null);
        if (userList.size() > 0)
        {
            Map last = (Map) userList.get(userList.size() - 1);
            lastIdSign = last.get("cuid").toString();
            request.setAttribute("lastIdSign", lastIdSign);
        }
        request.setAttribute("users", userList);
        request.setAttribute("userNum", userList.size());
        request.setAttribute("circle", circle);
        return "user_list";
    }

    public void loadUserData() throws IOException
    {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        if (!empty(circle) && !empty(circle.getId()) && !empty(lastIdSign))
        {
            circle = circleService.get(circle.getId());
            Map paramMap = new HashMap();
            if (!empty(nickname))
            {
                paramMap.put("user.nickname", nickname);
            }
            Integer lastId = Integer.parseInt(lastIdSign);
            List userList = circleService.getCircleUsers(paramMap,
                    circle.getId(), getLoginUser().getId(), lastId, "index");
            if (userList.size() == 0)
            {
                out.print("0");
            }
            else
            {
                Gson gson = new Gson();
                String result = gson.toJson(userList);
                out.print(result);
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
        user = userService.get(member);
        circle = circleService.get(circle.getId());
        request.setAttribute("nickname", user.getNickname());
        Integer lastId = null;
        if (!empty(lastIdSign))
        {
            lastId = Integer.parseInt(lastIdSign);
        }
        List diaryList = circleService.getPersonDiaryList(member,
                circle.getId(), lastId, null);
        if (diaryList.size() > 0)
        {
            Map lastDiary = (Map) diaryList.get(diaryList.size() - 1);
            lastIdSign = lastDiary.get("id").toString();
            request.setAttribute("lastIdSign", lastIdSign);

        }
        request.setAttribute("circle", circle);
        request.setAttribute("member", user.getId());
        request.setAttribute("diaries", diaryList);
        return "person_diary_list";
    }

    public void loadPersonDiaryData() throws IOException
    {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        if (!empty(circle) && !empty(circle.getId()) && !empty(member)
                && !empty(lastIdSign))
        {
            Integer lastId = Integer.parseInt(lastIdSign);
            List diaryList = circleService.getPersonDiaryList(member,
                    circle.getId(), lastId, "index");
            if (diaryList.size() > 0)
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

    public String otherCircles()
    {
        String key = "otherCircleList";
        if (request.getParameter("init") == null)
        {
            session.removeAttribute(key);
        }
        Map paramMap = RequestUtil.processParameters(request, key);
        paramMap.put("user_id", getLoginUser().getId());
        if (!empty(lastIdSign))
        {
            paramMap.put("lastCircleIdSign", lastIdSign);
        }
        paramMap.put("circle.type", type);
        List circleList = circleService.getAllCircles(paramMap, "index");

        if (circleList.size() > 0)
        {
            Map sonMap = (Map) circleList.get(circleList.size() - 1);
            request.setAttribute("lastCircleIdSign", sonMap.get("id")
                    .toString());
        }
        request.setAttribute("otherCircles", circleList);
        request.setAttribute("type", type);
        return "other_circle";
    }

    public void loadOtherCircles() throws IOException
    {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        Map param = new HashMap();
        if (!empty(lastIdSign))
        {
            param.put("lastCircleIdSign", lastIdSign);
        }
        if (!empty(circle.getName()))
        {
            param.put("circle.name", circle.getName());
        }
        if (!empty(circle.getType()))
        {
            param.put("circle.type", circle.getType());
        }
        param.put("user_id", getLoginUser().getId());
        List circleList = circleService.getAllCircles(param, null);
        if (!empty(circleList))
        {
            Gson gson = new Gson();
            String result = gson.toJson(circleList);
            out.print(result);
        }
        else
        {
            out.print("0");
        }
        out.close();
    }

    public Circle getCircle()
    {
        return circle;
    }

    public void setCircle(Circle circle)
    {
        this.circle = circle;
    }

    public String getImage()
    {
        return image;
    }

    public void setImage(String image)
    {
        this.image = image;
    }

    public Users getUser()
    {
        return user;
    }

    public void setUser(Users user)
    {
        this.user = user;
    }

    public String getCoverPath()
    {
        return coverPath;
    }

    public void setCoverPath(String coverPath)
    {
        this.coverPath = coverPath;
    }

    public Integer getType()
    {
        return type;
    }

    public void setType(Integer type)
    {
        this.type = type;
    }

    public Integer getMember()
    {
        return member;
    }

    public void setMember(Integer member)
    {
        this.member = member;
    }

    public String getMethod()
    {
        return method;
    }

    public void setMethod(String method)
    {
        this.method = method;
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

    public Diary getDiary()
    {
        return diary;
    }

    public void setDiary(Diary diary)
    {
        this.diary = diary;
    }

    public String getNickname()
    {
        return nickname;
    }

    public void setNickname(String nickname)
    {
        this.nickname = nickname;
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
