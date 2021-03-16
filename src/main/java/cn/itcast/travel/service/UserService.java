package cn.itcast.travel.service;

import cn.itcast.travel.domain.User;

/**
 * @author  #L
 * @date    2021/03/16
 */
public interface UserService {
    /**
     *
     * @param user 注册用户
     * @return 注册成功返回true,失败返回false
     */
     boolean register(User user);

    /**
     *
     * @param code 激活码
     * @return 激活成功返回true,失败返回false
     */
    boolean active(String code);

    /**
     *
     * @param user 登录用户
     * @return 用户名密码正确返回对应用户对象,否在返回null
     */
    User login(User user);
}
