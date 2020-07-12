package demo2.dao;

import demo2.pojo.Perms;
import demo2.pojo.PermsExample;
import demo2.pojo.Role;
import demo2.pojo.RoleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RoleDao  extends MyBatisBaseDao<Role, Integer, RoleExample> {
}