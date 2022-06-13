package lwq.java.web.ssm_swagger.dao;

import java.util.List;

import lwq.java.web.ssm_swagger.entity.User;

/**
 * @ Created by liwenqiang on 2017/5/2 0002.
 * @ Description:
 */
public interface UserDao {

    List<User> list();

    int insert(User user);

}
