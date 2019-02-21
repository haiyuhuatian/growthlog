
package cn.com.czrz.action.portal;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.ParentPackage;

import sun.misc.BASE64Decoder;
import cn.com.czrz.action.BaseAction;
import cn.com.czrz.common.Constants;
import cn.com.czrz.entity.CityMap;
import cn.com.czrz.entity.Integral;
import cn.com.czrz.entity.UserLoginTrack;
import cn.com.czrz.entity.Users;
import cn.com.czrz.service.IntegralService;
import cn.com.czrz.service.UserService;
import cn.com.gwypx.util.Excel;
import cn.com.gwypx.util.MapGetter;
import cn.com.gwypx.util.Utility;

import com.google.gson.Gson;

@ParentPackage("portal")
@SuppressWarnings("unchecked")
public class UserAction extends BaseAction
{

    private static final long serialVersionUID = -4482115896794128825L;

    private UserService userService = new UserService();

    private IntegralService integralService = new IntegralService();

    private Users user;

    private File file;

    private String fatherName;

    private CityMap cityMap;

    private String image;

    private String imgCode;

    private String invitationCode;

    private Integer inviter;

    private String from;

    private String ifBind;

    private String openid;

    public String toRegister()
    {

        String openId = (String) session.getAttribute("openid");
        if (!empty(openId) || openId != null || openId != "")
        {
            request.setAttribute("openid", openId);
        }
        return "register";
    }

    public void checkOpenId() throws IOException
    {
        PrintWriter out = response.getWriter();
        String openId = (String) session.getAttribute("openid");
        if (!empty(openId) && openId != null && openId != "")
        {
            Users dbuser = userService.findByOpenid(openId);
            if (!empty(dbuser))
            {
                out.print(dbuser.getLogin_name());
            }
        }
        else
        {
            out.print("1");
        }
        out.close();
    }

    public String register()
    {
        String openId = (String) session.getAttribute("openid");
        if (!empty(user))
        {
            if (!empty(userService.findByLoginName(user.getLogin_name())))
            {
                message = "该登录名已存在，换一个再试试吧";
                return ALERT;
            }
            String password_salt = UUID.randomUUID().toString();
            String pass = user.getPassword();
            String password = Utility.MD5(Constants.BEFORE_SALT + pass
                    + password_salt);
            user.setPassword(password);
            user.setPassword_salt(password_salt);
            user.setRegister_date(new Date());
            user.setTicket(Utility.MD5(Constants.LOGINNAME_KEY
                    + user.getLogin_name()));
            user.setIntegral(0);
            if (!empty(openId))
            {
                user.setOpenId(openId);
            }
            userService.save(user);
            Integral integral = new Integral();
            integral.setCreate_time(new Date());
            integral.setType(1);
            integral.setUser_id(user.getId());
            integral.setValue(200);
            integralService.save(integral);
            user.setIntegral(200);
            userService.update(user);
            // 把user放到session中
            session.setAttribute(Constants.SESSION_USER_KEY, user);
            File imagePackage = new File(
                    ServletActionContext.getServletContext().getRealPath(
                            "uploadImages")
                            + "/growthLog" + user.getId());
            imagePackage.mkdirs();
            message = "注册成功！";
        }
        session.setAttribute(Constants.SESSION_USER_KEY, user);
        this.redirectionUrl = "/mine/member!detail.action";
        return ALERT;
    }

    public String setHead()
    {
        if (!empty(getLoginUser()))
        {
            user = userService.get(getLoginUser().getId());
            request.setAttribute("headImg", user.getHead_img());
        }
        String noncestr = "portalSetHead";
        String jsapi_ticket = (String) application.getAttribute("jsapi_ticket");
        long timestamp = new Date().getTime();
        String url = Constants.CZRZ_URL + "/portal/user!setHead.action";
        String str = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + noncestr
                + "&timestamp=" + timestamp + "&url=" + url;
        String signature = DigestUtils.shaHex(str);
        request.setAttribute("noncestr", noncestr);
        request.setAttribute("timestamp", timestamp);
        request.setAttribute("signature", signature);
        request.setAttribute("appId", Constants.APPID);
        return "setHead";
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
            String fileName = "temporary.jpg";
            String filePath = ServletActionContext.getServletContext() // 得到图片保存的位置(根据root来得到图片保存的路径在tomcat下的该工程里)
                    .getRealPath("uploadImages") + "\\growthLog"
                    + getLoginUser().getId() + "\\" + fileName;
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
                user = userService.get(getLoginUser().getId());
                request.setAttribute("headImg", "temporary.jpg");
                String noncestr = "setHead";
                String jsapi_ticket = (String) application.getAttribute("jsapi_ticket");
                long timestamp = new Date().getTime();
                String url = Constants.CZRZ_URL
                        + "/portal/user!temporary.action";
                String str = "jsapi_ticket=" + jsapi_ticket + "&noncestr="
                        + noncestr + "&timestamp=" + timestamp + "&url=" + url;
                String signature = DigestUtils.shaHex(str);
                request.setAttribute("noncestr", noncestr);
                request.setAttribute("timestamp", timestamp);
                request.setAttribute("signature", signature);
                request.setAttribute("appId", Constants.APPID);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return "user_setHead";

    }

    public String saveHead()
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
        // 生成文件名
        Date date = new Date();
        String files = date.getTime() + ".jpg";
        // 生成文件路径
        String filename = ServletActionContext.getServletContext() // 得到图片保存的位置(根据root来得到图片保存的路径在tomcat下的该工程里)
                .getRealPath("uploadImages") + "\\growthLog"
                + getLoginUser().getId() + "\\" + files;
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
            user = userService.get(getLoginUser().getId());
            File oldHeadImg = new File(ServletActionContext.getServletContext() // 得到图片保存的位置(根据root来得到图片保存的路径在tomcat下的该工程里)
                    .getRealPath("uploadImages") + "\\growthLog" + user.getId()
                    + "\\" + user.getHead_img());
            if (oldHeadImg.exists())
            {
                oldHeadImg.delete();
            }
            user.setHead_img(files);
            userService.update(user);
            // 删除临时头像
            File temporary = new File(ServletActionContext.getServletContext() // 得到图片保存的位置(根据root来得到图片保存的路径在tomcat下的该工程里)
                    .getRealPath("uploadImages") + "\\growthLog" + user.getId()
                    + "\\temporary.jpg");
            if (temporary.exists())
            {
                temporary.delete();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        message = "设置成功";
        this.redirectionUrl = "/mine/member!detail.action";
        return ALERT;
    }

    public String checkLoginName() throws IOException
    {
        PrintWriter out = response.getWriter();
        user = userService.findByLoginName(user.getLogin_name());
        if (!empty(user))
        {
            out.print(-1);
        }
        else
        {
            out.print(1);
        }
        out.close();
        return null;
    }

    public String checkNickName() throws IOException
    {
        PrintWriter out = response.getWriter();
        if (empty(getLoginUser()))
        {
            user = userService.findByNickname(user.getNickname(), null);
        }
        else
        {
            user = userService.findByNickname(user.getNickname(),
                    getLoginUser().getId());
        }

        if (!empty(user))
        {
            out.print(-1);
        }
        else
        {
            out.print(1);
        }
        out.close();
        return null;
    }

    public String toLogin()
    {
        request.setAttribute("from", from);
        String openid = (String) request.getSession().getAttribute("openid");
        request.setAttribute("openid", openid);
        return "login";
    }

    private ByteArrayInputStream inputStream;

    public void setInputStream(ByteArrayInputStream inputStream)
    {
        this.inputStream = inputStream;
    }

    public ByteArrayInputStream getInputStream()
    {
        return inputStream;
    }

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

    private Color getRandColor(int i, int j)
    {
        // TODO Auto-generated method stub
        return null;
    }

    public void ifBind() throws IOException
    {
        PrintWriter out = response.getWriter();
        if (!empty(user) && !empty(user.getLogin_name()))
        {
            user = userService.findByLoginName(user.getLogin_name());
            if (!empty(user))
            {
                String openid = (String) request.getSession().getAttribute(
                        "openid");
                Users dbUser = null;
                if (!empty(openid) && openid != "" && openid != null)
                {
                    dbUser = userService.findByOpenid(openid);
                }
                if (empty(user.getOpenId()) && empty(dbUser))
                {
                    out.print("1");// 当前登录名没有绑定微信号，并且库里没有与当前微信号绑定的账号，询问是否绑定
                }
                else if (empty(user.getOpenId()) && !empty(dbUser))
                {
                    // 当前登录名没有绑定微信号，但是库里有与当前微信号绑定的账号，询问是重新绑定
                    out.print(dbUser.getLogin_name());
                }
                else if (!empty(user.getOpenId()) && empty(dbUser))
                {
                    // 当前登录名已经绑定了微信号，就比较是否绑定的是当前微信号
                    if (user.getOpenId().equals(openid))
                    {
                        out.print("");// 如果是的话则不作操作
                    }
                    else
                    {
                        out.print("2");// 不是的话询问是否重新绑定
                    }
                }
                else
                {
                    // 当前登录名已经绑定过微信号，并且当前微信号已经有绑定的账号
                    if (dbUser.getLogin_name().equals(user.getLogin_name()))
                    {
                        // 如果登录名相同，则表示是同一个账号，不作任何操作
                        out.print("");
                    }
                    else
                    {
                        out.print(dbUser.getLogin_name());
                    }
                }
            }
        }
        else
        {
            out.print("0");
        }
        out.close();
    }

    public void login() throws Exception
    {
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        if (!empty(user) && !empty(user.getLogin_name()))
        {
            String psd = user.getPassword();
            user = userService.findByLoginName(user.getLogin_name());
            if (empty(user))
            {
                out.print("0");
            }
            else
            {
                String password = Utility.MD5(Constants.BEFORE_SALT + psd
                        + user.getPassword_salt());
                if (!password.equals(user.getPassword()))
                {
                    out.print("1");
                }
                else
                {
                    String code = (String) session.getAttribute(Constants.IMAGE_CODE);
                    if (!empty(ifBind))
                    {
                        if (ifBind.equals("1"))
                        {
                            String openid = (String) session.getAttribute("openid");
                            if (!empty(openid))
                            {
                                // 如果之前已经绑定别的账号，先解绑之前的账号，再重新绑定
                                Users opUser = userService.findByOpenid(openid);
                                if (!empty(opUser))
                                {
                                    opUser.setOpenId(null);
                                    userService.update(opUser);
                                }
                                user.setOpenId(openid);
                                userService.update(user);
                            }
                        }
                    }
                    user.getTrack().setIp(getIpAddr(request));
                    user.getTrack().setUser_agent(
                            request.getHeader("user-agent"));
                    user.getTrack().setBrowser_type(
                            getBrowserType(request.getHeader("user-agent")));
                    session.setAttribute(Constants.SESSION_USER_KEY, user);
                    session.removeAttribute("loginOut");
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Map userMap = new HashMap();
                    userMap.put("id", user.getId());
                    userMap.put("login_name", user.getLogin_name());
                    userMap.put("nickname", user.getNickname());
                    if (!empty(user.getBirthday()))
                    {
                        userMap.put("birthday", sdf.format(user.getBirthday()));
                    }
                    else
                    {
                        userMap.put("birthday", "");
                    }
                    if (!empty(user.getBaby_birthday()))
                    {
                        userMap.put("baby_birthday",
                                sdf.format(user.getBaby_birthday()));
                    }
                    else
                    {
                        userMap.put("baby_birthday", "");
                    }
                    userMap.put("head_img", user.getHead_img());
                    if (!empty(user.getWchat_no()))
                    {
                        userMap.put("wchat_no", user.getWchat_no());
                    }
                    else
                    {
                        userMap.put("wchat_no", "");
                    }
                    if (!empty(user.getIntroduction()))
                    {
                        userMap.put("introduction", user.getIntroduction());
                    }
                    else
                    {
                        userMap.put("introduction", "");
                    }
                    if (!empty(user.getIntegral()))
                    {
                        userMap.put("integral", user.getIntegral());
                    }
                    else
                    {
                        userMap.put("integral", "");
                    }
                    if (!empty(user.getSex()))
                    {
                        userMap.put("sex", user.getSex() == 0 ? "女" : "男");
                    }
                    else
                    {
                        userMap.put("sex", "");
                    }
                    if (!empty(user.getEmail()))
                    {
                        userMap.put("email", user.getEmail());
                    }
                    else
                    {
                        userMap.put("email", "");
                    }
                    if (!empty(user.getArea()))
                    {
                        userMap.put("area",
                                userService.getMyCityName(user.getArea())
                                        .replaceAll(",", "-"));
                    }
                    else
                    {
                        userMap.put("area", "");
                    }
                    Gson gson = new Gson();
                    String result = gson.toJson(userMap);
                    out.print(result);
                }

            }

        }
        else
        {
            message = "登录失败";
        }
        out.close();
    }

    public void loginOut() throws IOException
    {
        PrintWriter out = response.getWriter();
        if (!empty(getLoginUser()))
        {
            session.removeAttribute(Constants.SESSION_USER_KEY);
            session.setAttribute("loginOut", "loginOut");
            Cookie[] cookies = request.getCookies();
            if (cookies != null)
            {
                for (Cookie c : cookies)
                {
                    if ("ticket".equals(c.getName()))
                    {
                        c.setValue(null);
                        c.setMaxAge(0);
                        c.setPath("/");
                        response.addCookie(c);
                    }
                }
            }
            out.print("1");
        }
        out.close();
    }

    /**
     * 根据request获得ip地址
     * 
     * @param request
     * @return
     */
    private String getIpAddr(HttpServletRequest request)
    {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 根据user-agent中的内容返回浏览器的类型
     * 
     * @param userAgent
     * @return
     */
    private String getBrowserType(String userAgent)
    {
        userAgent = userAgent.toLowerCase();
        // 包含 opera 的就是Opera浏览器
        if (userAgent.indexOf("opera") != -1)
        {
            return UserLoginTrack.BROWSER_TYPE_OPEAR;
        }
        // Chrome 谷歌浏览器
        if (userAgent.indexOf("chrome") != -1)
        {
            return UserLoginTrack.BROWSER_TYPE_CHROME;
        }
        if (userAgent.indexOf("safari") != -1)
        {
            return UserLoginTrack.BROWSER_TYPE_SAFARI;
        }
        if (userAgent.indexOf("360") != -1)
        {
            return UserLoginTrack.BROWSER_TYPE_360;
        }
        if (userAgent.indexOf("msie 6.0") != -1)
        {
            return UserLoginTrack.BROWSER_TYPE_IE6;
        }
        if (userAgent.indexOf("msie 7.0") != -1)
        {
            return UserLoginTrack.BROWSER_TYPE_IE7;
        }
        if (userAgent.indexOf("msie 8.0") != -1)
        {
            return UserLoginTrack.BROWSER_TYPE_IE8;
        }
        if (userAgent.indexOf("msie 9.0") != -1)
        {
            return UserLoginTrack.BROWSER_TYPE_IE9;
        }
        if (userAgent.indexOf("firefox") != -1)
        {
            return UserLoginTrack.BROWSER_TYPE_FIREFOX;
        }
        return "其他";
    }

    public String importMap() throws FileNotFoundException
    {

        if (!empty(file))
        {
            Excel excel = new Excel();
            excel.setNames(new String[]{"name"});
            List data = excel.read(new FileInputStream(file));
            CityMap father = null;
            if (!empty(fatherName))
            {
                father = userService.findCityMap(fatherName);
            }
            for (int i = 0, n = data.size(); i < n; i++)
            {
                Map m = (Map) data.get(i);
                MapGetter mg = new MapGetter(m);
                CityMap map = new CityMap();
                map.setName(mg.getString("name"));
                userService.saveCityMap(map);
                if (father != null)
                {
                    map.setPid(father.getId());
                    map.setCode(father.getCode() + map.getId() + ",");
                }
                else
                {
                    map.setPid(0);
                    map.setCode(map.getId() + ",");
                }
                userService.updateCityMap(map);
            }
            message = "导入成功";
        }
        return ALERT;
    }

    public String getCityName() throws IOException
    {
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        List cityList = userService.getCityMapByPid(cityMap.getPid());
        Gson gson = new Gson();
        String citys = gson.toJson(cityList);
        if (!empty(citys))
        {
            out.print(citys);
        }
        else
        {
            out.print("");
        }
        out.close();
        return null;
    }

    public String checkIsLogin() throws IOException
    {
        PrintWriter out = response.getWriter();
        Integer result = getLoginUser() == null ? 0 : 1;
        out.print(result);
        out.close();
        return null;
    }

    public String myInvitation()
    {
        request.setAttribute("invitationCode", invitationCode);
        return "invitation";
    }

    public Users getUser()
    {
        return user;
    }

    public void setUser(Users user)
    {
        this.user = user;
    }

    public File getFile()
    {
        return file;
    }

    public void setFile(File file)
    {
        this.file = file;
    }

    public String getFatherName()
    {
        return fatherName;
    }

    public void setFatherName(String fatherName)
    {
        this.fatherName = fatherName;
    }

    public CityMap getCityMap()
    {
        return cityMap;
    }

    public void setCityMap(CityMap cityMap)
    {
        this.cityMap = cityMap;
    }

    public String getImage()
    {
        return image;
    }

    public void setImage(String image)
    {
        this.image = image;
    }

    public String getImgCode()
    {
        return imgCode;
    }

    public void setImgCode(String imgCode)
    {
        this.imgCode = imgCode;
    }

    public String getInvitationCode()
    {
        return invitationCode;
    }

    public void setInvitationCode(String invitationCode)
    {
        this.invitationCode = invitationCode;
    }

    public Integer getInviter()
    {
        return inviter;
    }

    public void setInviter(Integer inviter)
    {
        this.inviter = inviter;
    }

    public String getFrom()
    {
        return from;
    }

    public void setFrom(String from)
    {
        this.from = from;
    }

    public String getIfBind()
    {
        return ifBind;
    }

    public void setIfBind(String ifBind)
    {
        this.ifBind = ifBind;
    }

    public String getOpenid()
    {
        return openid;
    }

    public void setOpenid(String openid)
    {
        this.openid = openid;
    }

}
