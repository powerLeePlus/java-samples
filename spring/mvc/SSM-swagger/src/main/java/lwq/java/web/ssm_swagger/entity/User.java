package lwq.java.web.ssm_swagger.entity;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;

/**
 * @ Created by liwenqiang on 2017/5/2 0002.
 * @ Description:
 */
public class User implements Serializable {
    @ApiModelProperty(hidden = true)
    private String id;
    private String username;
    private String password;
    @ApiModelProperty(hidden = true)
    private Date lastlogin;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getLastlogin() {
        return lastlogin;
    }

    public void setLastlogin(Date lastlogin) {
        this.lastlogin = lastlogin;
    }

    @Override
    public String toString() {
        return "{\"User\":{"
                + "\"id\":\""
                + id + '\"'
                + ",\"username\":\""
                + username + '\"'
                + ",\"password\":\""
                + password + '\"'
                + ",\"lastlogin\":\""
                + lastlogin + '\"'
                + "}}";

    }
}
