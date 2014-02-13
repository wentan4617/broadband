package com.tm.broadband.mapper;

import com.tm.broadband.model.User;

/**
 * mapping tm_user, userDAO component
 * @author Cook1fan
 *
 */
public interface UserMapper {
	
	User selectUserLogin(User user);
}
