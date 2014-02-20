package com.tm.broadband.mapper;

import java.util.List;

import com.tm.broadband.model.Notification;
import com.tm.broadband.model.Page;

public interface NotificationMapper {

	void insertNotification(Notification notification);

	void updateNotification(Notification notification);

	Notification selectNotificationById(int id);

	List<Notification> selectNotifications();

	List<Notification> selectNotificationsByPage(Page<Notification> page);
	
	int selectNotificationsSum(Page<Notification> page);

}
