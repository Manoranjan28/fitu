package org.commonfarm.service;

import org.commonfarm.app.model.User;

public class UserService extends ThinkingService {
	
	/**
	 * Get User by userid
	 * @param username
	 * @return
	 */
	public User getUser(String userId) {
		Object obj = getObject(new User(userId), new String[] {"userId"});
		if (obj == null) return null;
		User user = (User) obj;
		user.init();
		return (User) obj;
	}
}
