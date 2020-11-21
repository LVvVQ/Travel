package cn.itcast.travel.dao;

import cn.itcast.travel.domain.User;

public interface UserDao {

    /**
     *
     * @param username 用户名
     * @return 用户对象
     */
    public User searchByName(String username);

    public void save(User user);
}
