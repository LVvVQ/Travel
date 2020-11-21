package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;

public class UserServiceImpl implements UserService {

    private UserDao userDao = new UserDaoImpl();

    /**
     *
     * @param user 注册用户
     * @return 注册成功返回True,失败返回False
     */
    @Override
    public boolean register(User user) {
        User u = userDao.searchByName(user.getUsername());
        if (u != null){
            return false;
        }
        userDao.save(user);
        return true;
    }
}
