
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
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.ParentPackage;

import sun.misc.BASE64Decoder;
import cn.com.czrz.action.BaseAction;
import cn.com.czrz.common.Constants;
import cn.com.czrz.entity.Circle;
import cn.com.czrz.entity.CommentLike;
import cn.com.czrz.entity.Diary;
import cn.com.czrz.entity.DiaryComment;
import cn.com.czrz.entity.DiaryLike;
import cn.com.czrz.entity.Follow;
import cn.com.czrz.entity.Integral;
import cn.com.czrz.entity.Users;
import cn.com.czrz.service.CircleService;
import cn.com.czrz.service.CommentService;
import cn.com.czrz.service.CommonService;
import cn.com.czrz.service.DiaryService;
import cn.com.czrz.service.FollowService;
import cn.com.czrz.service.IntegralService;
import cn.com.czrz.service.UserService;

import com.drew.imaging.ImageProcessingException;
import com.google.gson.Gson;

@ParentPackage("mine")
public class DiaryAction extends BaseAction
{

    private static final long serialVersionUID = 1L;

    private DiaryService diaryService = new DiaryService();

    private CommonService commonService = new CommonService();

    private UserService userService = new UserService();

    private CircleService circleService = new CircleService();

    private IntegralService integralService = new IntegralService();

    private FollowService followService = new FollowService();

    private CommentService commentService = new CommentService();

    private Diary diary;

    private DiaryComment diaryComment;

    private String comment;

    private String diaryYear;

    private String diaryMonth;

    private String myImgs;

    private String diaryId;

    private Users user;

    private Circle circle;

    private String serverId;

    private String savePhotoSign;

    private Integer photoNum;

    private String packageId;

    private String imageSrc;

    private Integer model;

    private Integer likeId;

    private String firstCommentIdSign;

    private String lastCommentIdSign;

    private String fromSon;

    private Follow follow;

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
            diaryComment.setLike_count(0l);
            diaryComment.setCurrent_month_likes(0l);
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
            dc.setLike_count(0l);
            dc.setCurrent_month_likes(0l);
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
            if (!empty(circle) && !empty(circle.getId()))
            {
                circle = circleService.get(circle.getId());
                request.setAttribute("circleId", circle.getId());
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
        }
        return "comment_family";
    }

    public String moreComments()
    {
        if (!empty(diary) && !empty(diary.getId()))
        {
            diary = diaryService.get(diary.getId());
            if (!empty(circle) && !empty(circle.getId()))
            {
                circle = circleService.get(circle.getId());
                request.setAttribute("circle", circle);
            }
            if (!empty(follow) && !empty(follow.getId()))
            {
                request.setAttribute("followId", follow.getId());
            }
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
            if (!empty(circle) && !empty(circle.getId()))
            {
                circle = circleService.get(circle.getId());
                request.setAttribute("circle", circle);
            }
            if (!empty(follow) && !empty(follow.getId()))
            {
                request.setAttribute("followId", follow.getId());
            }
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
        if (!empty(diaryComment) && !empty(diaryComment.getId())
                && !empty(lastCommentIdSign))
        {
            diaryComment = commentService.getDiaryComment(diaryComment.getId());
            Integer userId = 0;
            if (!empty(getLoginUser()))
            {
                userId = getLoginUser().getId();
            }
            List family = commentService.getCommentFamily(diaryComment,
                    Integer.parseInt(lastCommentIdSign), userId);
            if (empty(family))
            {
                out.print("0");
            }
            else
            {
                Gson gson = new Gson();
                String result = gson.toJson(family);
                out.print(result);
            }
        }
        out.close();
    }

    public String list() throws ParseException, IOException
    {
        if (!empty(getLoginUser()))
        {
            if (empty(diaryYear) && empty(diaryMonth))
            {
                diaryYear = diaryService.getMaxYear(getLoginUser().getId());
                diaryMonth = diaryService.getMaxMonth(getLoginUser().getId());
                request.setAttribute("diaryList",
                        diaryService.getDiaryListForMobile(
                                getLoginUser().getId(), diaryYear, diaryMonth));
                request.setAttribute("son", diaryYear + diaryMonth);
            }
            else
            {
                request.setAttribute("diaryList",
                        diaryService.getDiaryListForMobile(
                                getLoginUser().getId(), diaryYear, diaryMonth));
                request.setAttribute("son", request.getParameter("son"));
            }
            request.setAttribute("menu",
                    diaryService.getMenuList(getLoginUser().getId()));
            request.setAttribute("package", "growthLog"
                    + getLoginUser().getId());
            request.setAttribute("diaryYear", diaryYear);
            request.setAttribute("diaryMonth", diaryMonth);
        }
        else
        {
            request.setAttribute("isLogin", "no");
        }
        return "list";
    }

    public String save() throws ParseException
    {

        if (!empty(diaryId) && !empty(diary))
        {
            Diary myDiary = diaryService.get(Integer.parseInt(diaryId));
            if (diary.getOpenness() == 2)
            {
                String[] circles = request.getParameterValues("circles");
                String circle_ids = ",";
                if (circles.length > 0)
                {
                    for (int i = 0; i < circles.length; i++)
                    {
                        circle_ids += circles[i] + ",";
                    }
                }
                myDiary.setCircle_ids(circle_ids);
            }
            String[] diaryImages = request.getParameterValues("diaryImages");
            if (!empty(diaryImages) && diaryImages.length > 0)
            {
                Gson gson = new Gson();
                String images = gson.toJson(diaryImages);
                myDiary.setImages(images);
            }
            myDiary.setContent(diary.getContent());
            myDiary.setDiary_date(diary.getDiary_date());
            myDiary.setOpenness(diary.getOpenness());
            myDiary.setTitle(diary.getTitle());
            myDiary.setStatus(0);
            myDiary.setType(diary.getType());
            myDiary.setWeather(diary.getWeather());
            myDiary.setModel(diary.getModel());
            myDiary.setReading_quantity(0);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String day = sdf.format(diary.getDiary_date());
            Calendar c = Calendar.getInstance();
            c.setTime(sdf.parse(day));
            if (c.get(Calendar.DAY_OF_WEEK) == 1)
            {
                myDiary.setWeekday("星期天");
            }
            else if (c.get(Calendar.DAY_OF_WEEK) == 2)
            {
                myDiary.setWeekday("星期一");
            }
            else if (c.get(Calendar.DAY_OF_WEEK) == 3)
            {
                myDiary.setWeekday("星期二");
            }
            else if (c.get(Calendar.DAY_OF_WEEK) == 4)
            {
                myDiary.setWeekday("星期三");
            }
            else if (c.get(Calendar.DAY_OF_WEEK) == 5)
            {
                myDiary.setWeekday("星期四");
            }
            else if (c.get(Calendar.DAY_OF_WEEK) == 6)
            {
                myDiary.setWeekday("星期五");
            }
            else
            {
                myDiary.setWeekday("星期六");
            }
            diaryService.update(myDiary);
            diaryService.insertToIndex(myDiary);
            if (diaryService.getDiaryNumToday(getLoginUser().getId()) == 1)
            {
                Integral integral = new Integral();
                integral.setCreate_time(new Date());
                integral.setType(1);
                integral.setUser_id(getLoginUser().getId());
                integral.setValue(10);
                integralService.save(integral);
                user = userService.get(getLoginUser().getId());
                user.setIntegral(user.getIntegral() + 10);
                userService.update(user);
            }
        }
        else
        {
            if (diary.getOpenness() == 2)
            {
                String[] circles = request.getParameterValues("circles");
                String circle_ids = ",";
                if (circles.length > 0)
                {
                    for (int i = 0; i < circles.length; i++)
                    {
                        circle_ids += circles[i] + ",";
                    }
                }
                diary.setCircle_ids(circle_ids);
            }
            diary.setUser_id(getLoginUser().getId());
            diary.setStatus(1);
            diary.setLike_count(0l);
            diary.setCurrent_month_likes(0l);
            diary.setComment_count(0l);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String day = sdf.format(diary.getDiary_date());
            Calendar c = Calendar.getInstance();
            c.setTime(sdf.parse(day));
            if (c.get(Calendar.DAY_OF_WEEK) == 1)
            {
                diary.setWeekday("星期天");
            }
            else if (c.get(Calendar.DAY_OF_WEEK) == 2)
            {
                diary.setWeekday("星期一");
            }
            else if (c.get(Calendar.DAY_OF_WEEK) == 3)
            {
                diary.setWeekday("星期二");
            }
            else if (c.get(Calendar.DAY_OF_WEEK) == 4)
            {
                diary.setWeekday("星期三");
            }
            else if (c.get(Calendar.DAY_OF_WEEK) == 5)
            {
                diary.setWeekday("星期四");
            }
            else if (c.get(Calendar.DAY_OF_WEEK) == 6)
            {
                diary.setWeekday("星期五");
            }
            else
            {
                diary.setWeekday("星期六");
            }
            diaryService.save(diary);
            diaryService.insertToIndex(diary);
        }
        message = "保存成功";
        this.redirectionUrl = "diary!list.action";
        return ALERT;
    }

    /*
     * public void savePhoto() throws IOException { PrintWriter out =
     * response.getWriter(); if (!empty(imageSrc)) { String packagePath =
     * ServletActionContext.getServletContext() .getRealPath("uploadImages") +
     * "/growthLog" + getLoginUser().getId() + "/new"; File packageFile = new
     * File(packagePath); SimpleDateFormat sdf = new
     * SimpleDateFormat("yyyy-MM-dd-HH"); String nowDate = sdf.format(new
     * Date()); File signFile = new File(packagePath + "/" + nowDate + ".txt");
     * if (!packageFile.exists()) { packageFile.mkdir();
     * signFile.createNewFile(); } else { if (!signFile.exists()) { String[]
     * oldFiles = packageFile.list(); if (oldFiles.length > 0) { for (int t = 0;
     * t < oldFiles.length; t++) { File oldFile = new File(packagePath + "/" +
     * oldFiles[t]); if (oldFile.exists()) { oldFile.delete(); } if (t ==
     * oldFiles.length - 1) { packageFile.delete(); packageFile = new
     * File(packagePath); packageFile.mkdir(); signFile.createNewFile(); } } }
     * else { packageFile.delete(); packageFile = new File(packagePath);
     * packageFile.mkdir(); signFile.createNewFile(); } } } imageSrc =
     * imageSrc.replaceAll("data:image/png;base64,", ""); imageSrc =
     * imageSrc.replaceAll("data:image/jpeg;base64,", ""); imageSrc =
     * imageSrc.replaceAll("data:image/gif;base64,", ""); imageSrc =
     * imageSrc.replaceAll("data:image/jpg;base64,", ""); BASE64Decoder decoder
     * = new BASE64Decoder(); // Base64解码 byte[] imageByte = null; try {
     * imageByte = decoder.decodeBuffer(imageSrc); for (int t = 0; t <
     * imageByte.length; ++t) { if (imageByte[t] < 0) {// 调整异常数据 imageByte[t] +=
     * 256; } } } catch (Exception e) { e.printStackTrace(); } // 生成文件名 String
     * imgPath = new Date().getTime() + ".png"; // 生成文件路径 String filename =
     * packagePath + "/" + imgPath; try { // 生成文件 File imageFile = new
     * File(filename); if (!imageFile.exists()) { imageFile.createNewFile(); }
     * OutputStream imageStream = new FileOutputStream(imageFile);
     * imageStream.write(imageByte); imageStream.flush(); imageStream.close(); }
     * catch (Exception e) { e.printStackTrace(); }
     * out.print("/uploadImages/growthLog" + getLoginUser().getId() + "/new/" +
     * imgPath); } else { out.print("error"); } out.close(); }
     */

    public void savePhotos() throws IOException, ImageProcessingException,
            ParseException
    {
        PrintWriter out = response.getWriter();
        if (!empty(myImgs))
        {
            Diary d = new Diary();
            d.setUser_id(getLoginUser().getId());
            d.setOpenness(3);
            d.setStatus(0);
            d.setReading_quantity(0);
            d.setCreate_date(new Date());
            d.setModel(0);
            d.setLike_count(0l);
            d.setCurrent_month_likes(0l);
            d.setComment_count(0l);
            diaryService.save(d);
            File imagePackage = new File(
                    ServletActionContext.getServletContext().getRealPath(
                            "uploadImages")
                            + "/growthLog"
                            + getLoginUser().getId()
                            + "/diary"
                            + d.getId());
            imagePackage.mkdirs();
            Gson gson = new Gson();
            List<String> strs = gson.fromJson(myImgs, List.class);
            String[] imageNames = new String[strs.size()];
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
                    String files = "diary" + d.getId() + i + ".png";
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
                    imageNames[i] = files;
                }
            }
            String diaryImages = gson.toJson(imageNames);
            d.setImages(diaryImages);
            diaryService.update(d);
            out.print(d.getId());
        }
        else
        {
            out.print(-1);
        }
        out.close();
    }

    public void saveWeiPhotos() throws IOException
    {
        PrintWriter out = response.getWriter();
        String fileName = saveImageToDisk(serverId);
        if (fileName != "")
        {
            out.print(fileName);
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
    public String saveImageToDisk(String mediaId)
    {
        String fileName = "";
        InputStream inputStream = getMedia(mediaId);
        byte[] data = new byte[1024];
        int len = 0;
        FileOutputStream fileOutputStream = null;
        try
        {
            if (empty(packageId))
            {
                Diary d = new Diary();
                d.setUser_id(getLoginUser().getId());
                d.setOpenness(3);
                d.setStatus(0);
                d.setReading_quantity(0);
                d.setCreate_date(new Date());
                d.setModel(0);
                d.setLike_count(0l);
                d.setCurrent_month_likes(0l);
                d.setComment_count(0l);
                diaryService.save(d);
                packageId = d.getId().toString();
            }
            // 服务器存图路径
            String path = ServletActionContext.getServletContext().getRealPath(
                    "uploadImages")
                    + "/growthLog"
                    + getLoginUser().getId()
                    + "/diary"
                    + packageId;
            File packageFile = new File(path);
            if (!packageFile.exists())
            {
                packageFile.mkdir();
            }
            else
            {
                // 如果没有标记则表示文件夹是上次只上传了照片没有提交日志内容时存的，给删掉，重新创建目录。
                if (empty(savePhotoSign))
                {
                    String[] oldFiles = packageFile.list();
                    if (oldFiles.length > 0)
                    {
                        for (int t = 0; t < oldFiles.length; t++)
                        {
                            File oldFile = new File(path + "/" + oldFiles[t]);
                            if (oldFile.exists())
                            {
                                oldFile.delete();
                            }
                            if (t == oldFiles.length - 1)
                            {
                                packageFile.delete();
                                packageFile = new File(path);
                                packageFile.mkdir();
                            }
                        }
                    }
                    else
                    {
                        packageFile.delete();
                        packageFile = new File(path);
                        packageFile.mkdir();
                    }
                }
            }
            savePhotoSign = "sign";
            path += "/";
            fileName = "diary" + packageId + photoNum + ".jpg";
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
        return fileName + "," + savePhotoSign + "," + packageId;
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

    public String detail()
    {
        if (!empty(diary) && !empty(diary.getId()))
        {
            String diaryUser = diaryService.getDiaryUserId(diary.getId());
            String userId = getLoginUser().getId().toString();
            if (diaryUser.equals(userId))
            {
                user = userService.get(getLoginUser().getId());
                diary = diaryService.get(diary.getId());
                String images = diary.getImages();
                if (!empty(images) && images != "" && images != null)
                {
                    Gson gson = new Gson();
                    String[] diaryImages = gson.fromJson(images, String[].class);
                    request.setAttribute("imgs", diaryImages);
                }

                request.setAttribute("comments", commentService.getComments(
                        getLoginUser().getId(), diary.getId(), null, 0));
                request.setAttribute("total",
                        diaryService.getCommentNum(diary.getId()));
                String noncestr = "diaryDetail";
                String jsapi_ticket = (String) application.getAttribute("jsapi_ticket");
                long timestamp = new Date().getTime();
                String url = Constants.CZRZ_URL
                        + "/mine/diary!detail.action?diary.id=" + diary.getId();
                String str = "jsapi_ticket=" + jsapi_ticket + "&noncestr="
                        + noncestr + "&timestamp=" + timestamp + "&url=" + url;
                String signature = DigestUtils.shaHex(str);
                request.setAttribute("noncestr", noncestr);
                request.setAttribute("timestamp", timestamp);
                request.setAttribute("signature", signature);
                request.setAttribute("appId", Constants.APPID);
                request.setAttribute("diaryMonth", diaryMonth);
                request.setAttribute("diaryYear", diaryYear);
            }
            else
            {
                return ERROR;
            }
        }
        return "detail";
    }

    public String delete()
    {
        if (!empty(diary) && !empty(diary.getId()))
        {
            String diaryUserId = diaryService.getDiaryUserId(diary.getId());
            String userId = getLoginUser().getId().toString();
            if (!diaryUserId.equals(userId))
            {
                return ERROR;
            }
            diary = diaryService.get(diary.getId());
            Gson gson = new Gson();
            String[] images = gson.fromJson(diary.getImages(), String[].class);
            if (!empty(images))
            {
                for (int i = 0; i < images.length; i++)
                {
                    String name = images[i];
                    File image = new File(
                            ServletActionContext.getServletContext() // 得到图片保存的位置(根据root来得到图片保存的路径在tomcat下的该工程里)
                                    .getRealPath("uploadImages")
                                    + "\\growthLog" + getLoginUser().getId()
                                    + "\\diary" + diary.getId() + "\\" + name);
                    if (image.exists())
                    {
                        image.delete();
                    }
                }
            }
            File myPackage = new File(ServletActionContext.getServletContext()
                    .getRealPath("uploadImages")
                    + "\\growthLog"
                    + getLoginUser().getId() + "\\diary" + diary.getId());
            if (myPackage.exists())
            {
                myPackage.delete();
            }
            Date diaryDate = diary.getDiary_date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(diaryDate);
            int diaryYear = calendar.get(Calendar.YEAR);
            int diaryMonth = calendar.get(Calendar.MONTH) + 1;
            diaryService.delete(diary.getId());
            this.redirectionUrl = "diary!list.action?&diaryYear=" + diaryYear
                    + "&diaryMonth=" + diaryMonth;
            // message = "删除成功";
        }
        else
        {
            this.redirectionUrl = "diary!list.action";
            message = "删除失败，请联系客服！";
        }
        return ALERT;
    }

    public String write()
    {
        List circles = circleService.getMyCircles(getLoginUser().getId());
        request.setAttribute("circles", circles);
        if (!empty(circles) && circles.size() > 0)
        {
            request.setAttribute("circleNum", circles.size());
        }
        else
        {
            request.setAttribute("circleNum", 0);
        }
        String userAgent = request.getHeader("user-agent");
        if (userAgent.contains("iPhone"))
        {
            return "write_iphone";
        }
        else
        {
            String noncestr = "diarywrite";
            String jsapi_ticket = (String) application.getAttribute("jsapi_ticket");
            long timestamp = new Date().getTime();
            String url = Constants.CZRZ_URL + request.getRequestURI();
            String str = "jsapi_ticket=" + jsapi_ticket + "&noncestr="
                    + noncestr + "&timestamp=" + timestamp + "&url=" + url;
            String signature = DigestUtils.shaHex(str);
            request.setAttribute("noncestr", noncestr);
            request.setAttribute("timestamp", timestamp);
            request.setAttribute("signature", signature);
            request.setAttribute("appId", Constants.APPID);
        }
        return "write";
    }

    public Diary getDiary()
    {
        return diary;
    }

    public void setDiary(Diary diary)
    {
        this.diary = diary;
    }

    public DiaryComment getDiaryComment()
    {
        return diaryComment;
    }

    public void setDiaryComment(DiaryComment diaryComment)
    {
        this.diaryComment = diaryComment;
    }

    public String getComment()
    {
        return comment;
    }

    public void setComment(String comment)
    {
        this.comment = comment;
    }

    public String getDiaryYear()
    {
        return diaryYear;
    }

    public void setDiaryYear(String diaryYear)
    {
        this.diaryYear = diaryYear;
    }

    public String getDiaryMonth()
    {
        return diaryMonth;
    }

    public void setDiaryMonth(String diaryMonth)
    {
        this.diaryMonth = diaryMonth;
    }

    public String getMyImgs()
    {
        return myImgs;
    }

    public void setMyImgs(String myImgs)
    {
        this.myImgs = myImgs;
    }

    public Users getUser()
    {
        return user;
    }

    public void setUser(Users user)
    {
        this.user = user;
    }

    public String getDiaryId()
    {
        return diaryId;
    }

    public void setDiaryId(String diaryId)
    {
        this.diaryId = diaryId;
    }

    public Circle getCircle()
    {
        return circle;
    }

    public void setCircle(Circle circle)
    {
        this.circle = circle;
    }

    public String getServerId()
    {
        return serverId;
    }

    public void setServerId(String serverId)
    {
        this.serverId = serverId;
    }

    public String getSavePhotoSign()
    {
        return savePhotoSign;
    }

    public void setSavePhotoSign(String savePhotoSign)
    {
        this.savePhotoSign = savePhotoSign;
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

    public String getImageSrc()
    {
        return imageSrc;
    }

    public void setImageSrc(String imageSrc)
    {
        this.imageSrc = imageSrc;
    }

    public Integer getModel()
    {
        return model;
    }

    public void setModel(Integer model)
    {
        this.model = model;
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

    public Follow getFollow()
    {
        return follow;
    }

    public void setFollow(Follow follow)
    {
        this.follow = follow;
    }

}
