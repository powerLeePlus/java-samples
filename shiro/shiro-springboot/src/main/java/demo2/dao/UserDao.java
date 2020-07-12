package demo2.dao;

import demo2.pojo.Perms;
import demo2.pojo.User;
import org.apache.ibatis.jdbc.Null;

import javax.lang.model.type.NullType;
import java.util.List;

public interface UserDao extends MyBatisBaseDao<User, Integer, Null> {
//    int deleteByPrimaryKey(Integer id);
//
//    int insert(User record);
//
//    int insertSelective(User record);
//
//    User selectByPrimaryKey(Integer id);
//
//    int updateByPrimaryKeySelective(User record);
//
//    int updateByPrimaryKey(User record);

    // 根据用户名查询
    User getByUsername(String username);

    //根据用户名查询所有角色
    User findRolesByUsername(String username);
    //根据角色id查询权限集合
    List<Perms> findPermsByRoleId(Integer id);


}