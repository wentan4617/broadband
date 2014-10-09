package com.tm.broadband.mapper;

import java.util.List;

import com.tm.broadband.model.Page;
import com.tm.broadband.model.User;

/**
 * mapping tm_user, user DAO component
 * 
 * @author Cook1fan
 * 
 */

/* SELECT AREA *//* // END SELECT AREA */
/* =================================================================================== */
/* INSERT AREA *//* // END INSERT AREA */
/* =================================================================================== */
/* UPDATE AREA *//* // END UPDATE AREA */
/* =================================================================================== */
/* DELETE AREA *//* // END DELETE AREA */

public interface UserMapper {

	/* SELECT AREA */
	
	User selectUserLogin(User user);
	List<User> selectUsersByPage(Page<User> page);
	int selectUsersSum(Page<User> user);
	int selectExistUser(User user);
	int selectExistUserByName(String login_name);
	int selectExistNotSelfUserfByName(String login_name, int id);
	User selectUserById(int id);
	
	List<User> selectUser(User user);
	
	List<User> selectUsersWhoseIdExistInOrder();
	
	/* // END SELECT AREA */
	/* =================================================================================== */
	/* INSERT AREA */
	
	void insertUser(User user);
	
	/* // END INSERT AREA */
	
	/* =================================================================================== */
	
	/* UPDATE AREA */
	
	void updateUser(User user);
	
	/* // END UPDATE AREA */
	
	/* =================================================================================== */
	
	/* DELETE AREA */
	
	/* // END DELETE AREA */
}
