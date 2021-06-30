package com.lwq.example.cloud.bus;

import org.springframework.cloud.bus.event.RemoteApplicationEvent;

/**
 * @author lwq
 * @date 2021/6/30 0030
 */
public class UserRemoteApplicationEvent extends RemoteApplicationEvent {

	private User user;

	public UserRemoteApplicationEvent() {
	}

	public UserRemoteApplicationEvent(Object source, String originService, String destinationService, User user) {
		super(source, originService, destinationService);
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
