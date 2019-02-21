
package cn.com.czrz.action.portal;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletInputStream;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.struts2.convention.annotation.ParentPackage;

import cn.com.czrz.action.BaseAction;
import cn.com.czrz.common.Constants;
import cn.com.czrz.entity.ReplyMessage;
import cn.com.czrz.entity.Users;
import cn.com.czrz.entity.WchatMessage;
import cn.com.czrz.service.ReplyMessageService;
import cn.com.czrz.service.UserService;
import cn.com.czrz.service.WchatMessageService;
import cn.com.czrz.util.InputMessage;
import cn.com.czrz.util.OutputMessage;
import cn.com.czrz.util.SerializeXmlUtil;
import cn.com.gwypx.util.Utility;

import com.thoughtworks.xstream.XStream;

@ParentPackage("portal")
public class MessageAction extends BaseAction
{

    private static final long serialVersionUID = -2343544740715431120L;

    public static final String TOKEN = "messageToken";

    private WchatMessageService wchatMessageService = new WchatMessageService();

    private ReplyMessageService replyMessageService = new ReplyMessageService();

    private UserService userService = new UserService();

    public String execute() throws IOException
    {
        System.out.println("进入chat");
        boolean isGet = request.getMethod().toLowerCase().equals("get");
        if (isGet)
        {
            String timestamp = request.getParameter("timestamp");
            String nonce = request.getParameter("nonce");
            String signature = request.getParameter("signature");
            String echostr = request.getParameter("echostr");
            String[] params = new String[]{TOKEN, timestamp, nonce};
            Arrays.sort(params);
            // 将三个参数字符串拼接成一个字符串进行sha1加密
            String clearText = params[0] + params[1] + params[2];
            String sign = DigestUtils.shaHex(clearText);
            PrintWriter out = response.getWriter();
            if (signature.equals(sign))
            {
                out.print(echostr);
            }
            out.flush();
            out.close();
        }
        else
        {
            // 进入POST聊天处理
            System.out.println("enter post");
            try
            {
                // 接收消息并返回消息
                acceptMessage();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        return null;
    }

    public String acceptMessage() throws IOException
    {// 处理接收消息
        ServletInputStream in = request.getInputStream();
        // 将POST流转换为XStream对象
        XStream xs = SerializeXmlUtil.createXstream();
        xs.processAnnotations(InputMessage.class);
        xs.processAnnotations(OutputMessage.class);
        // 将指定节点下的xml节点数据映射为对象
        xs.alias("xml", InputMessage.class);
        // 将流转换为字符串
        StringBuilder xmlMsg = new StringBuilder();
        byte[] b = new byte[4096];
        for (int n; (n = in.read(b)) != -1;)
        {
            xmlMsg.append(new String(b, 0, n, "UTF-8"));
        }
        // 将xml内容转换为InputMessage对象
        InputMessage inputMsg = (InputMessage) xs.fromXML(xmlMsg.toString());

        String servername = inputMsg.getToUserName();// 服务端
        String custermname = inputMsg.getFromUserName();// 客户端
        long createTime = inputMsg.getCreateTime();// 接收时间
        Long returnTime = Calendar.getInstance().getTimeInMillis() / 1000;// 返回时间

        // 取得消息类型
        String msgType = inputMsg.getMsgType();
        String eventType = inputMsg.getEvent();
        // 根据消息类型获取对应的消息内容
        if (msgType.equals("text"))
        {
            // 文本消息
            WchatMessage wm = new WchatMessage();
            wm.setOpen_id(inputMsg.getFromUserName());
            wm.setContent(inputMsg.getContent());
            wm.setMsg_type(inputMsg.getMsgType());
            wm.setMsg_id(inputMsg.getMsgId());
            wm.setCreate_time(new Date());
            wchatMessageService.saveWchatMessage(wm);
            String content = inputMsg.getContent();
            String replyContent = "您好，欢迎加入成长日志，如需修改密码请直接输入您的登录名，"
                    + "并以#结束，如有其他疑问可以进行留言，我们会尽快回复，谢谢您的信任。";
            if (content.equals("密码"))
            {
                replyContent = "请输入登录名以#结尾，例如：haiyuhuatian#";
            }
            if (content.endsWith("#"))
            {
                content = content.substring(0, content.length() - 1);
                Users user = userService.findByLoginName(content);
                if (user == null)
                {
                    replyContent = "登录名输入错误，请重新输入";
                }
                else
                {
                    if (empty(user.getOpenId())
                            || !inputMsg.getFromUserName().equals(
                                    user.getOpenId()))
                    {
                        replyContent = "您好，请用注册时用的微信号修改密码,如有疑问加客服微信【telinda】进行询问";
                    }
                    else
                    {
                        user.setOpenId(inputMsg.getFromUserName());
                        String newPass = Utility.MD5(Constants.BEFORE_SALT
                                + Constants.DEFAULT_PASSWORD
                                + user.getPassword_salt());
                        user.setPassword(newPass);
                        userService.update(user);
                        replyContent = "您好，您的密码已经重置为woaiczrz，请登录后在菜单栏点击【"
                                + "我的】，然后进入【我的账号】进行修改，以确保您的账号安全。";
                    }

                }
            }
            ReplyMessage rm = new ReplyMessage();
            rm.setContent(replyContent);
            rm.setCreate_time(new Date());
            rm.setMessage_id(wm.getId());
            replyMessageService.saveReplyMessage(rm);
            StringBuffer str = new StringBuffer();
            str.append("<xml>");
            str.append("<ToUserName><![CDATA[" + custermname
                    + "]]></ToUserName>");
            str.append("<FromUserName><![CDATA[" + servername
                    + "]]></FromUserName>");
            str.append("<CreateTime>" + returnTime + "</CreateTime>");
            str.append("<MsgType><![CDATA[" + msgType + "]]></MsgType>");
            str.append("<Content><![CDATA[" + replyContent + "]]></Content>");
            str.append("</xml>");
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            System.out.println(str.toString());
            String respMessage = str.toString();
            response.getWriter().write(respMessage);
        }
        if (msgType.equals("event") && eventType.equals("subscribe"))
        {
            StringBuffer str = new StringBuffer();
            str.append("<xml>");
            str.append("<ToUserName><![CDATA[" + custermname
                    + "]]></ToUserName>");
            str.append("<FromUserName><![CDATA[" + servername
                    + "]]></FromUserName>");
            str.append("<CreateTime>" + returnTime + "</CreateTime>");
            str.append("<MsgType><![CDATA[" + msgType + "]]></MsgType>");
            str.append("<Content><![CDATA[欢迎加入成长日志，开启你的时光之旅吧]]></Content>");
            str.append("</xml>");
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            String respMessage = str.toString();
            response.getWriter().write(respMessage);
        }
        // 获取并返回多图片消息
        /*
         * if (msgType.equals(MsgType.Image.toString())) {
         * System.out.println("获取多媒体信息"); System.out.println("多媒体文件id：" +
         * inputMsg.getMediaId()); System.out.println("图片链接：" +
         * inputMsg.getPicUrl()); System.out.println("消息id，64位整型：" +
         * inputMsg.getMsgId()); OutputMessage outputMsg = new OutputMessage();
         * outputMsg.setFromUserName(servername);
         * outputMsg.setToUserName(custermname);
         * outputMsg.setCreateTime(returnTime); outputMsg.setMsgType(msgType);
         * ImageMessage images = new ImageMessage();
         * images.setMediaId(inputMsg.getMediaId()); outputMsg.setImage(images);
         * System.out.println("xml转换：/n" + xs.toXML(outputMsg));
         * response.getWriter().write(xs.toXML(outputMsg)); }
         */
        return null;
    }
}
