package cn.itcast.travel.service;

import cn.itcast.travel.domain.User;

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
}
