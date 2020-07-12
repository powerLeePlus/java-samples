package demo2.pojo;

import java.io.Serializable;
import lombok.Data;

/**
 * shiro_role
 * @author 
 */
@Data
public class Role implements Serializable {
    private Integer id;

    private String name;

    private static final long serialVersionUID = 1L;
}