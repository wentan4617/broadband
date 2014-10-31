package com.tm.broadband.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tm.broadband.mapper.CompanyDetailMapper;
import com.tm.broadband.mapper.CustomerMapper;
import com.tm.broadband.mapper.InviteRatesMapper;
import com.tm.broadband.mapper.ManualManipulationRecordMapper;
import com.tm.broadband.mapper.NotificationMapper;
import com.tm.broadband.mapper.SEOMapper;
import com.tm.broadband.mapper.UserMapper;
import com.tm.broadband.model.CompanyDetail;
import com.tm.broadband.model.InviteRates;
import com.tm.broadband.model.ManualManipulationRecord;
import com.tm.broadband.model.Notification;
import com.tm.broadband.model.Page;
import com.tm.broadband.model.SEO;
import com.tm.broadband.model.User;

/**
 * System service
 * 
 * @author Don Chen, Cook1fan
 * 
 */
@Service
public class SystemService {

	private UserMapper userMapper;
	private NotificationMapper notificationMapper;
	private CompanyDetailMapper companyDetailMapper;
	private CustomerMapper customerMapper;
	private ManualManipulationRecordMapper manualManipulationRecordMapper;
	private SEOMapper seoMapper;
	private InviteRatesMapper inviteRatesMapper;

	@Autowired
	public SystemService(UserMapper userMapper,
			NotificationMapper notificationMapper,
			CompanyDetailMapper companyDetailMapper,
			CustomerMapper customerMapper,
			ManualManipulationRecordMapper manualManipulationRecordMapper,
			SEOMapper seoMapper,
			InviteRatesMapper inviteRatesMapper) {
		this.userMapper = userMapper;
		this.notificationMapper = notificationMapper;
		this.companyDetailMapper = companyDetailMapper;
		this.customerMapper = customerMapper;
		this.manualManipulationRecordMapper = manualManipulationRecordMapper;
		this.seoMapper = seoMapper;
		this.inviteRatesMapper = inviteRatesMapper;
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
		String auth = "";
		for (int i = 0, len = user.getAuthArray().length; i < len; i++) {
			auth += user.getAuthArray()[i];
			if (i < len - 1) auth += ",";
		}
		user.setAuth(auth);
		this.userMapper.insertUser(user);
	}

	@Transactional
	public void editUser(User user) {
		String auth = "";
		for (int i = 0, len = user.getAuthArray().length; i < len; i++) {
			auth += user.getAuthArray()[i];
			if (i < len - 1) auth += ",";
		}
		user.setIs_provision(user.getIs_provision() != null ? true : false);
		user.setAuth(auth);
		this.userMapper.updateUser(user);
	}

	@Transactional
	public void editUsers(List<User> user) {
		
		for (User u : user) {
			this.userMapper.updateUser(u);
		}
		
	}

	@Transactional
	public User queryUserById(int id) {
		return this.userMapper.selectUserById(id);
	}

	@Transactional
	public List<User> queryUser() {
		return this.userMapper.selectUser(new User());
	}

	@Transactional
	public List<User> queryUser(User user) {
		return this.userMapper.selectUser(user);
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

	
	/*
	 * CompanyDetail Service Begin
	 */

	@Transactional
	public void editCompanyDetail(CompanyDetail companyDetail) {
		this.companyDetailMapper.updateCompanyDetail(companyDetail);;
	}
	
	@Transactional
	public CompanyDetail queryCompanyDetail() {
		return this.companyDetailMapper.selectCompanyDetail();
	}

	/*
	 * CompanyDetail Service End
	 */

	
	/*
	 * SEO Service Begin
	 */

	@Transactional
	public void editSEO(SEO seo) {
		this.seoMapper.updateSEO(seo);
	}
	
	@Transactional
	public SEO querySEO() {
		List<SEO> seos = this.seoMapper.selectSEOs(new SEO());
		return seos != null && seos.size() > 0 ? seos.get(0) : null;
	}

	/*
	 * SEO Service End
	 */

	/*
	 * Notification begin
	 */
	public Notification queryNotificationBySort(String sort, String type) {
		return this.notificationMapper.selectNotificationBySort(sort, type);
	}
	/*
	 * Notification end
	 */
	
	// BEGIN ManualManipulationRecord
	@Transactional
	public Page<ManualManipulationRecord> queryManualManipulationRecordsByPage(Page<ManualManipulationRecord> page) {
		page.setTotalRecord(this.manualManipulationRecordMapper.selectManualManipulationRecordsSum(page));
		page.setResults(this.manualManipulationRecordMapper.selectManualManipulationRecordsByPage(page));
		return page;
	}

	@Transactional
	public void createManualManipulationRecord(ManualManipulationRecord cir) {
		this.manualManipulationRecordMapper.insertManualManipulationRecord(cir);
	}

	@Transactional 
	public List<ManualManipulationRecord> queryManualManipulationRecord(ManualManipulationRecord cir){
		return this.manualManipulationRecordMapper.selectManualManipulationRecords(cir);
	}

	@Transactional
	public void removeManualManipulationRecord(int id) {
		this.manualManipulationRecordMapper.deleteManualManipulationRecordById(id);
	}

	@Transactional
	public void editManualManipulationRecord(ManualManipulationRecord cir) {
		this.manualManipulationRecordMapper.updateManualManipulationRecord(cir);
	}
	// END ManualManipulationRecord

	
	/*
	 * InviteRates Service Begin
	 */

	@Transactional
	public void editInviteRate(InviteRates ir) {
		this.inviteRatesMapper.updateInviteRates(ir);
	}
	
	@Transactional
	public InviteRates queryInviteRate() {
		List<InviteRates> irs = this.inviteRatesMapper.selectInviteRates(new InviteRates());
		return irs != null && irs.size() > 0 ? irs.get(0) : null;
	}

	/*
	 * InviteRates Service End
	 */
}
