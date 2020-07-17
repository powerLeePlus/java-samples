package org.lwq.oauth2.server.pojo;

import java.io.Serializable;

import lombok.Data;

/**
 * shiro_perms
 * @author 
 */
@Data
public class Perms implements Serializable {
    private Integer id;

    private String name;

    private String url;

    private static final long serialVersionUID = 1L;
}