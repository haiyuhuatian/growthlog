
package cn.com.czrz.service;

import java.util.List;

import cn.com.czrz.entity.CityMap;
import cn.com.czrz.entity.Users;
import cn.com.gwypx.jdbc.ObjectRowMapper;

public class UserService extends BaseService
{
    public void save(Users user)
    {
        jdbc.saveEntity(user);
    }

    public void update(Users user)
    {
        jdbc.updateEntity(user);
    }

    public Users get(Integer id)
    {
        return (Users) jdbc.loadEntity(Users.class, id);
    }

    public Users findByLoginName(String loginName)
    {
        return (Users) jdbc.find("select * from users where login_name = ?",
                loginName, new ObjectRowMapper(Users.class));
    }

    public Users findByNickname(String nickname, Integer userId)
    {
        if (!empty(userId))
        {
            return (Users) jdbc.find(
                    "select * from users where nickname = ? and id <> ?",
                    new Object[]{nickname, userId}, new ObjectRowMapper(
                            Users.class));
        }
        else
        {
            return (Users) jdbc.find("select * from users where nickname = ?",
                    new Object[]{nickname}, new ObjectRowMapper(Users.class));
        }

    }

    public void saveCityMap(CityMap map)
    {
        jdbc.saveEntity(map);
    }

    public CityMap findCityMap(String name)
    {
        Integer id = jdbc.queryInt(
                "select max(id) from city_map where name = ?", name);
        return (CityMap) jdbc.loadEntity(CityMap.class, id);
    }

    public void updateCityMap(CityMap map)
    {
        jdbc.updateEntity(map);
    }

    public List getCityMapByPid(Integer pid)
    {
        return jdbc.query("select * from city_map where pid = ?", pid);
    }

    public Users loginByCookie(String ticket)
    {
        Users user = (Users) jdbc.find("select * from users where ticket=?",
                ticket, new ObjectRowMapper(Users.class));
        if (!empty(user))
        {
            return user;
        }
        return null;
    }

    public String getMyCityName(Integer id)
    {
        String code = jdbc.queryString(
                "select code from city_map where id = ?", id);
        code = code.substring(0, code.length() - 1);
        return jdbc.queryString("SELECT GROUP_CONCAT(NAME) FROM city_map WHERE id IN ("
                + code + ")");
    }

    public Users findByOpenid(String openid)
    {
        return (Users) jdbc.find(
                "select id, login_name, nickname, password, password_salt, birthday, "
                        + "baby_birthday, sex, address, email, register_date, area, ticket, "
                        + "integral, wchat_no, is_wchat_open, introduction, head_img, "
                        + "openId from users where openid = ?", openid,
                new ObjectRowMapper(Users.class));
    }

    public void addIntegral(Integer userId, Integer integral)
    {
        jdbc.update("update users set integral = integral+" + integral
                + " where id = ?", userId);
    }

    public void subtractIntegral(Integer userId, Integer integral)
    {
        jdbc.update("update users set integral = integral-" + integral
                + " where id = ?", userId);
    }
}
