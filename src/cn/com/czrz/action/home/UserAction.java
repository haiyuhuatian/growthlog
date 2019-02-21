
package cn.com.czrz.action.home;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.ParentPackage;

import sun.misc.BASE64Decoder;
import cn.com.czrz.action.BaseAction;
import cn.com.czrz.common.Constants;
import cn.com.czrz.entity.CityMap;
import cn.com.czrz.entity.UserLoginTrack;
import cn.com.czrz.entity.Users;
import cn.com.czrz.service.IntegralService;
import cn.com.czrz.service.UserService;
import cn.com.czrz.util.DesUtil;
import cn.com.gwypx.util.Excel;
import cn.com.gwypx.util.MapGetter;
import cn.com.gwypx.util.Utility;

import com.google.gson.Gson;

@ParentPackage("home")
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

    public String setHead()
    {
        if (!empty(getLoginUser()))
        {
            user = userService.get(getLoginUser().getId());
            request.setAttribute("headImg", user.getHead_img());
        }
        String noncestr = "setHead";
        String jsapi_ticket = (String) application.getAttribute("jsapi_ticket");
        long timestamp = new Date().getTime();
        String url = Constants.CZRZ_URL + "/home/user!setHead.action";
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
                String url = Constants.CZRZ_URL + "/home/user!setHead.action";
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

    private Color getRandColor(int i, int j)
    {
        // TODO Auto-generated method stub
        return null;
    }

    public String login() throws Exception
    {
        if (!empty(user) && !empty(user.getLogin_name()))
        {
            String psd = user.getPassword();
            user = userService.findByLoginName(user.getLogin_name());
            if (empty(user))
            {
                message = "登录名输入错误！";
                return ALERT;
            }
            else
            {
                String password = Utility.MD5(Constants.BEFORE_SALT + psd
                        + user.getPassword_salt());
                if (!password.equals(user.getPassword()))
                {
                    message = "密码输入错误！";
                    return ALERT;
                }
                else
                {
                    String code = (String) session.getAttribute(Constants.IMAGE_CODE);
                    if (!code.equals(imgCode))
                    {
                        message = "验证码错误！";
                        return ALERT;
                    }
                }
            }
            // cookie登陆
            String userName = DesUtil.encrypt("czrz:" + user.getLogin_name(),
                    Constants.LOGINNAME_KEY);
            Cookie cookie = new Cookie("ticket", userName);
            response.addCookie(cookie);
            user.getTrack().setIp(getIpAddr(request));
            user.getTrack().setUser_agent(request.getHeader("user-agent"));
            user.getTrack().setBrowser_type(
                    getBrowserType(request.getHeader("user-agent")));
            session.setAttribute(Constants.SESSION_USER_KEY, user);
            this.redirectionUrl = "/home/index!index.action?model=";
        }
        else
        {
            message = "登录失败";
            return ALERT;
        }
        return ALERT;
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

}
