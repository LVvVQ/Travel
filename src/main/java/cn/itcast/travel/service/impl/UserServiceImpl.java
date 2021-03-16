package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.util.MailUtils;
import cn.itcast.travel.util.UuidUtil;

/**
 * @author  #L
 * @date    2021/03/16
 */
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

        user.setCode(UuidUtil.getUuid());
        user.setStatus("N");
        userDao.save(user);

        //// FIXME: 2020/11/22 you should use your own project path
        String content = "<a href = 'http://localhost:8080/travel/user/active?code="+user.getCode()+"'>点击激活邮箱[黑马旅游网]</a>";
        MailUtils.sendMail(user.getEmail(),content,"激活邮件");
        return true;
    }

    @Override
    public boolean active(String code) {
        User user = userDao.searchByCode(code);

        if (user != null){
            userDao.updateStatus(user);
            return true;
        }else {
            return false;
        }
    }

    @Override
    public User login(User user) {
        return userDao.checkUsernameAndPassword(user.getUsername(),user.getPassword());
    }
}
