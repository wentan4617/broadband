package com.tm.broadband.mapper;

import java.util.List;

import com.tm.broadband.model.Page;
import com.tm.broadband.model.User;

/**
 * mapping tm_user, userDAO component
 * 
 * @author Cook1fan
 * 
 */
public interface UserMapper {

	User selectUserLogin(User user);

	List<User> selectUsers();

	void insertUser(User user);

	void updateUser(User user);

	List<User> selectUsersByPage(Page<User> page);

	int selectUsersSum(Page<User> user);

	User selectUserById(int id);

	int selectExistUserByName(String login_name);

	int selectExistNotSelfUserfByName(String login_name, int id);

}
