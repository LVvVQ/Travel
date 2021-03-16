package cn.itcast.travel.dao;

import cn.itcast.travel.domain.User;

/**
 * @author  #L
 * @date    2021/03/16
 */
public interface UserDao {

     User searchByName(String username);

     User searchByCode(String code);

     void updateStatus(User user);

     void save(User user);

     User checkUsernameAndPassword(String username,String password);
}
