package lwq.java.web.ssm_swagger.dao;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import lwq.java.web.ssm_swagger.entity.User;

/**
 *
 * @author lwq
 * @create 2019-06-28 下午 4:18
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
@WebAppConfiguration
public class UserDaoTest {
    @Autowired
    private UserDao userDao;

    @Test
    public void testInsert(){
        /*User user = new User();
        user.setId(UUIDUtil.getUUID());
        user.setPassword(MD5Util.getMD5Str("123456"));
        user.setUsername("华晟");
        user.setEmail("test1@qq.com");

        userDao.insert(user);*/

        List<User> users = userDao.list();
        System.out.println(users);

    }
}
