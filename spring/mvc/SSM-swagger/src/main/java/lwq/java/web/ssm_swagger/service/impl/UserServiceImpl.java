package lwq.java.web.ssm_swagger.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lwq.java.web.ssm_swagger.dao.UserDao;
import lwq.java.web.ssm_swagger.entity.User;
import lwq.java.web.ssm_swagger.service.UserService;
import lwq.java.web.ssm_swagger.util.MD5Util;
import lwq.java.web.ssm_swagger.util.UUIDUtil;

/**
 *
 * @author lwq
 * @create 2019-06-28 下午 2:57
 */
@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserDao userDao;

    @Override
    public List list() {
        return userDao.list();
    }

    @Override
    public void save(User user) {
        user.setId(UUIDUtil.getUUID());
        user.setLastlogin(new Date());
        user.setPassword(MD5Util.getMD5Str(user.getPassword()));
        userDao.insert(user);
    }
}
