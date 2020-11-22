package cn.itcast.travel.dao;

import cn.itcast.travel.domain.User;

public interface UserDao {

     User searchByName(String username);

     User searchByCode(String code);

     void updateStatus(User user);

     void save(User user);
}
