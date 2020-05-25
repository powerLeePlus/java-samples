package lwq.java.web.ssm_swagger.service;

import java.util.List;

import lwq.java.web.ssm_swagger.entity.User;

/**
 * ${DESCRIPTION}
 *
 * @author lwq
 * @date 2019/6/28 0028
 */
public interface UserService {
    public List list();

    void save(User user);
}
