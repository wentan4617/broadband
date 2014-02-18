package com.tm.broadband.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tm.broadband.mapper.PlanMapper;
import com.tm.broadband.mapper.TopupMapper;
import com.tm.broadband.model.Topup;
import com.tm.broadband.model.Page;
import com.tm.broadband.model.Plan;
import com.tm.broadband.model.Topup;

/**
 * plan service
 * 
 * @author Cook1fan
 * 
 */
@Service
public class PlanService {

	private PlanMapper planMapper;
	private TopupMapper topupMapper;

	@Autowired
	public PlanService(PlanMapper planMapper, TopupMapper topupMapper) {
		this.planMapper = planMapper;
		this.topupMapper = topupMapper;
	}

	public PlanService() {

	}

	@Transactional
	public void savePlan(Plan plan) {
		this.planMapper.insertPlan(plan);
	}

	@Transactional
	public void removePlanById(int id) {
		this.planMapper.deletePlanById(id);
	}

	@Transactional
	public void editPlan(Plan plan) {
		this.planMapper.updatePlan(plan);
	}

	@Transactional
	public List<Plan> queryLikePlansByName(String plan_name) {
		return this.planMapper.selectLikePlansByName(plan_name);
	}

	@Transactional
	public List<Plan> queryPlans() {
		return this.planMapper.selectPlans();
	}

	@Transactional
	public Plan queryPlanById(int id) {
		return this.planMapper.selectPlanById(id);
	}

	@Transactional
	public Plan queryPlanByName(String plan_name) {
		return this.planMapper.selectPlanByName(plan_name);
	}

	@Transactional
	public int queryExistPlanByName(String plan_name) {
		return this.planMapper.selectExistPlanByName(plan_name);
	}

	@Transactional
	public int queryExistNotSelfPlanfByName(String plan_name, int id) {
		return this.planMapper.selectExistNotSelfPlanfByName(plan_name, id);
	}

	@Transactional
	public Page<Plan> queryPlansByPage(Page<Plan> page) {
		page.setTotalRecord(this.planMapper.selectPlansSum(page));
		page.setResults(this.planMapper.selectPlansByPage(page));
		return page;
	}

	@Transactional
	public void editPlansStatus(List<Plan> plans) {
		if (plans != null) {
			for (Plan plan : plans) {
				this.planMapper.updatePlanStatusById(plan.getPlan_status(),
						plan.getId());
			}
		}
	}

	@Transactional
	public List<Plan> queryPlansByType(String type) {
		return this.planMapper.selectPlansByType(type);
	}

	/*
	 * Topup Operation start
	 */

	@Transactional
	public void saveTopup(Topup topup) {
		this.topupMapper.insertTopup(topup);
	}

	@Transactional
	public void editTopup(Topup topup) {
		this.topupMapper.updateTopup(topup);
	}

	@Transactional
	public Topup queryTopupById(int id) {
		return this.topupMapper.selectTopupById(id);
	}

	@Transactional
	public List<Topup> queryTopups() {
		return this.topupMapper.selectTopups();
	}

	@Transactional
	public Page<Topup> queryTopupsByPage(Page<Topup> page) {
		page.setTotalRecord(this.topupMapper.selectTopupsSum(page));
		page.setResults(this.topupMapper.selectTopupsByPage(page));
		return page;
	}

	/*
	 * Topup Operation end
	 */

}
