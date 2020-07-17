package org.lwq.oauth2.server.service;

import java.util.List;

import org.lwq.oauth2.server.pojo.Perms;
import org.lwq.oauth2.server.pojo.User;

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