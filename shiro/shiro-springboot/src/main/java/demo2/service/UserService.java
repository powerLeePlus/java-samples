package demo2.service;

import demo2.pojo.Perms;
import demo2.pojo.User;

import java.util.List;

public interface UserService {
    //注册用户方法
    void register(User user);

    //根据身份信息认证的方法
    User findByUsername(String username);

    //根据用户名查询所有角色
    User findRolesByUserName(String username);
    //根据角色id查询权限集合
    List<Perms> findPermsByRoleId(Integer id);
}