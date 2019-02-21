
package cn.com.czrz.listener;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import cn.com.czrz.common.Constants;
import cn.com.czrz.timer.AccessTokenTimer;
import cn.com.czrz.timer.DiaryLikeCountTimer;
import cn.com.czrz.timer.JsapiTicketTimer;
import cn.com.czrz.util.WeixinUtil;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@SuppressWarnings("unchecked")
public class SystemInitListener implements ServletContextListener
{
    @Override
    public void contextDestroyed(ServletContextEvent event)
    {
    }

    @Override
    public void contextInitialized(ServletContextEvent event)
    {

        ServletContext sc = event.getServletContext();
        Timer timer = new Timer();
        Map access_token_map;
        String access_token = "";
        try
        {
            access_token_map = WeixinUtil.https(Constants.ACCESS_TOKEN_URL,
                    null);
            access_token = access_token_map.get("access_token").toString();
            sc.setAttribute("access_token", access_token);
        }
        catch (Exception e1)
        {
            e1.printStackTrace();
        }
        List menuList = new ArrayList();
        Map indexMap = new HashMap();
        indexMap.put("name", "首页");
        Map rankMenu = new HashMap();
        rankMenu.put("type", "view");
        rankMenu.put("name", "排行榜");
        rankMenu.put("url", Constants.CZRZ_URL + "/home/index!rankList.action");
        Map selfMenu = new HashMap();
        selfMenu.put("type", "view");
        selfMenu.put("name", "个人日志");
        selfMenu.put("url", Constants.CZRZ_URL
                + "/home/index!index.action?model=2");
        Map babyMenu = new HashMap();
        babyMenu.put("type", "view");
        babyMenu.put("name", "宝贝日志");
        babyMenu.put("url", Constants.CZRZ_URL
                + "/home/index!index.action?model=1");
        Map allMenu = new HashMap();
        allMenu.put("type", "view");
        allMenu.put("name", "全部日志");
        allMenu.put("url", Constants.CZRZ_URL
                + "/home/index!index.action?model=0");
        List indexSunButtonList = new ArrayList();
        indexSunButtonList.add(allMenu);
        indexSunButtonList.add(babyMenu);
        indexSunButtonList.add(selfMenu);
        indexSunButtonList.add(rankMenu);
        indexMap.put("sub_button", indexSunButtonList);
        menuList.add(indexMap);
        Map myMap = new HashMap();
        myMap.put("name", "我的");
        Map myDiaryMap = new HashMap();
        myDiaryMap.put("type", "view");
        myDiaryMap.put("name", "我的日志");
        myDiaryMap.put("url", Constants.CZRZ_URL + "/mine/diary!list.action");
        Map myCircleMap = new HashMap();
        myCircleMap.put("type", "view");
        myCircleMap.put("name", "我的圈子");
        myCircleMap.put("url", Constants.CZRZ_URL
                + "/mine/circle!myCircle.action");
        Map myFollowMap = new HashMap();
        myFollowMap.put("type", "view");
        myFollowMap.put("name", "我的关注");
        myFollowMap.put("url", Constants.CZRZ_URL + "/mine/follow!list.action");
        Map myInfoMap = new HashMap();
        myInfoMap.put("type", "view");
        myInfoMap.put("name", "我的账号");
        myInfoMap.put("url", Constants.CZRZ_URL + "/mine/member!detail.action");
        List myList = new ArrayList();
        myList.add(myDiaryMap);
        myList.add(myCircleMap);
        myList.add(myFollowMap);
        myList.add(myInfoMap);
        myMap.put("sub_button", myList);
        menuList.add(myMap);
        Map activityCenterMap = new HashMap();
        activityCenterMap.put("name", "活动中心");
        Map integralMall = new HashMap();
        integralMall.put("type", "view");
        integralMall.put("name", "积分商城");
        integralMall.put("url", Constants.CZRZ_URL
                + "/portal/integral!mall.action");
        Map activityMap = new HashMap();
        activityMap.put("type", "view");
        activityMap.put("name", "近期活动");
        activityMap.put("url", Constants.CZRZ_URL
                + "/portal/activity!list.action");
        Map resultShow = new HashMap();
        resultShow.put("type", "view");
        resultShow.put("name", "活动成果");
        resultShow.put("url", Constants.CZRZ_URL
                + "/portal/activity!resultShow.action");
        List activityCenterList = new ArrayList();
        activityCenterList.add(integralMall);
        activityCenterList.add(activityMap);
        activityCenterList.add(resultShow);
        activityCenterMap.put("sub_button", activityCenterList);
        menuList.add(activityCenterMap);
        Map menuMap = new HashMap();
        menuMap.put("button", menuList);
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        String menuStr = gson.toJson(menuMap);
        try
        {
            Map menu_map = WeixinUtil.https(Constants.MENU_URL + access_token,
                    menuStr);
            System.err.println(menu_map.get("errmsg"));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        Date now = new Date();
        long time = now.getTime() + 90 * 60 * 1000;
        timer.schedule(new JsapiTicketTimer(sc), 0, 90 * 60 * 1000);
        timer.schedule(new DiaryLikeCountTimer(sc), 0, 10 * 60 * 1000);
        timer.schedule(new AccessTokenTimer(sc), time, 90 * 60 * 1000);
    }
}
