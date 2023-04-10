package com.app.service;

import com.app.model.UserInfo;

public interface IUserService {

	public Integer createUser(UserInfo userInfo);
	//Optional<UserInfo> findByUsername(String username);
	
}
