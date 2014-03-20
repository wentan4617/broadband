package com.tm.broadband.mapper;

import java.util.List;

import com.tm.broadband.model.Notification;
import com.tm.broadband.model.Page;

public interface NotificationMapper {

	/* SELECT AREA */

	Notification selectNotificationById(int id);

	List<Notification> selectNotifications();

	List<Notification> selectNotificationsByPage(Page<Notification> page);
	int selectNotificationsSum(Page<Notification> page);
	
	Notification selectNotificationBySort(String sort, String type);

	/* // END SELECT AREA */
	
	/* =================================================================================== */

	/* INSERT AREA */

	void insertNotification(Notification notification);
	
	/* // END INSERT AREA */
	
	/* =================================================================================== */

	/* UPDATE AREA */

	void updateNotification(Notification notification);
	
	/* // END UPDATE AREA */
	/* =================================================================================== */
	/* DELETE AREA *//* // END DELETE AREA */
}
