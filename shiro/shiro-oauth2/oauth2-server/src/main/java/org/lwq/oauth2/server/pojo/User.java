package org.lwq.oauth2.server.pojo;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * shiro_user
 * @author 
 */
@Data
public class User implements Serializable {
    private Integer id;

    private String username;

    private String password;

    private String salt;

    //定义角色集合
    private List<Role> roles;

    private static final long serialVersionUID = 1L;
}