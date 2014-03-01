package com.tm.broadband.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tm.broadband.mapper.NotificationMapper;
import com.tm.broadband.mapper.UserMapper;
import com.tm.broadband.model.Notification;
import com.tm.broadband.model.Page;
import com.tm.broadband.model.User;

@Service
public class SystemService {

	private UserMapper userMapper;
	private NotificationMapper notificationMapper;

	@Autowired
	public SystemService(UserMapper userMapper,
			NotificationMapper notificationMapper) {
		this.userMapper = userMapper;
		this.notificationMapper = notificationMapper;
	}

	public SystemService() {
	}

	/*
	 * User Service begin
	 */

	@Transactional
	public User queryUserLogin(User user) {
		return this.userMapper.selectUserLogin(user);
	}

	@Transactional
	public void saveUser(User user) {
		this.userMapper.insertUser(user);
	}

	@Transactional
	public void editUser(User user) {
		this.userMapper.updateUser(user);
	}

	@Transactional
	public User queryUserById(int id) {
		return this.userMapper.selectUserById(id);
	}

	@Transactional
	public List<User> queryUsers() {
		return this.userMapper.selectUsers();
	}

	@Transactional
	public int queryExistUserByName(String login_name) {
		return this.userMapper.selectExistUserByName(login_name);
	}

	@Transactional
	public int queryExistNotSelfUserfByName(String login_name, int id) {
		return this.userMapper.selectExistNotSelfUserfByName(login_name, id);
	}

	@Transactional
	public Page<User> queryUsersByPage(Page<User> page) {
		page.setTotalRecord(this.userMapper.selectUsersSum(page));
		page.setResults(this.userMapper.selectUsersByPage(page));
		return page;
	}

	/*
	 * User Service end
	 */

	/*
	 * Notification Service begin
	 */

	@Transactional
	public void saveNotification(Notification notification) {
		this.notificationMapper.insertNotification(notification);
	}

	@Transactional
	public void editNotification(Notification notification) {
		this.notificationMapper.updateNotification(notification);
	}

	@Transactional
	public Notification queryNotificationById(int id) {
		return this.notificationMapper.selectNotificationById(id);
	}

	@Transactional
	public List<Notification> queryNotifications() {
		return this.notificationMapper.selectNotifications();
	}

	@Transactional
	public Page<Notification> queryNotificationsByUser(Page<Notification> page) {
		page.setTotalRecord(this.notificationMapper
				.selectNotificationsSum(page));
		page.setResults(this.notificationMapper.selectNotificationsByPage(page));
		return page;
	}

	@Transactional
	public Page<Notification> queryNotificationsByPage(Page<Notification> page) {
		page.setTotalRecord(this.notificationMapper
				.selectNotificationsSum(page));
		page.setResults(this.notificationMapper.selectNotificationsByPage(page));
		return page;
	}

	/*
	 * Notification Service begin
	 */

}