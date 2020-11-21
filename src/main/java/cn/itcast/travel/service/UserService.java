package cn.itcast.travel.service;

import cn.itcast.travel.domain.User;

public interface UserService {
    /**
     *
     * @param user 注册用户
     * @return 注册成功返回true,失败返回false
     */
    public boolean register(User user);
}
