package org.lwq.oauth2.server.pojo;

import lombok.Data;

/**
 * @author lwq
 * @date 2020/7/15 0015
 */
@Data
public class Oauth2Client {

	private String id;
	private String clientName;
	private String clientId;
	private String clientSecret;
}
