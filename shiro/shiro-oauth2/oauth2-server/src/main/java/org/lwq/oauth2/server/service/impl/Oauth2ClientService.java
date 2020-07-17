package org.lwq.oauth2.server.service.impl;


import org.lwq.oauth2.server.dao.Oauth2ClientMapper;
import org.lwq.oauth2.server.pojo.Oauth2Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author: wangsaichao
 * @date: 2018/5/27
 * @description:
 */
@Service("clientService")
public class Oauth2ClientService {

    @Autowired
    private Oauth2ClientMapper clientMapper;

    public Oauth2Client findByClientId(String clientId) {
        return clientMapper.findByClientId(clientId);
    }

    public Oauth2Client findByClientSecret(String clientSecret) {
        return clientMapper.findByClientSecret(clientSecret);
    }

}
