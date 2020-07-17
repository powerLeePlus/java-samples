package org.lwq.oauth2.server.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.lwq.oauth2.server.pojo.Oauth2Client;


/**
 * @author: WangSaiChao
 * @date: 2018/5/27
 * @description: 客户端dao层
 */
@Mapper
public interface Oauth2ClientMapper {

    /**
     * 根据clientId查询客户端信息
     * @param clientId
     * @return
     */
    Oauth2Client findByClientId(@Param("clientId") String clientId);

    /**
     * 根据clientSecret查询客户端信息
     * @param clientSecret
     * @return
     */
    Oauth2Client findByClientSecret(@Param("clientSecret") String clientSecret);
}
