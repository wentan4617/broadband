package com.tm.broadband.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tm.broadband.mapper.HardwareMapper;
import com.tm.broadband.mapper.PlanMapper;
import com.tm.broadband.model.Hardware;
import com.tm.broadband.model.Page;
import com.tm.broadband.model.Plan;

/**
 * plan service
 * 
 * @author Cook1fan
 * 
 */
@Service
public class PlanService {

	private PlanMapper planMapper;
	private HardwareMapper hardwareMapper;

	@Autowired
	public PlanService(PlanMapper planMapper, HardwareMapper hardwareMapper) {
		this.planMapper = planMapper;
		this.hardwareMapper = hardwareMapper;
	}

	public PlanService() {
	}

	/*
	 * PLAN AREA
	 */
	
	@Transactional
	public Plan queryPlan(Plan plan) {
		return this.planMapper.selectPlan(plan);
	}
	
	@Transactional
	public List<Plan> queryPlans(Plan plan) {
		return this.planMapper.selectPlans(plan);
	}
	
	@Transactional
	public Page<Plan> queryPlansByPage(Page<Plan> page) {
		page.setTotalRecord(this.planMapper.selectPlansSum(page));
		page.setResults(this.planMapper.selectPlansByPage(page));
		return page;
	}
	
	@Transactional
	public int queryExistPlan(Plan plan) {
		return this.planMapper.selectExistPlan(plan);
	}
	
	@Transactional
	public void savePlan(Plan plan) {
		this.planMapper.insertPlan(plan);
	}
	
	@Transactional
	public void editPlan(Plan plan) {
		this.planMapper.updatePlan(plan);
	}
	
	@Transactional
	public void editPlans(List<Plan> plans) {
		if (plans != null) {
			for (Plan plan : plans) 
				this.planMapper.updatePlan(plan);
		}
	}

	@Transactional
	public void removePlan(Plan plan) {
		this.planMapper.deletePlan(plan);
	}
	
	@Transactional
	public void removePlans(List<Plan> plans) {
		if (plans != null) {
			for (Plan plan : plans) 
				this.planMapper.deletePlan(plan);
		}
	}
	
	/*
	 * END PLAN AREA
	 */
	
	/*
	 * Hardware Begin
	 */
	
	@Transactional
	public void editHardwarePic(Hardware hardware) {
		this.hardwareMapper.updateHardwarePicById(hardware);
	}
	
	@Transactional
	public void saveHardware(Hardware hardware) {
		this.hardwareMapper.insertHardware(hardware);
	}
	
	@Transactional
	public void removeHardwareById(int id){
		this.hardwareMapper.deleteHardwareById(id);
	}

	@Transactional
	public void editHardware(Hardware hardware) {
		this.hardwareMapper.updateHardware(hardware);
	}

	@Transactional
	public Hardware queryHardwareById(int id) {
		return this.hardwareMapper.selectHardwareById(id);
	}

	@Transactional
	public Page<Hardware> queryHardwaresByPage(Page<Hardware> page) {
		page.setTotalRecord(this.hardwareMapper.selectHardwaresSum(page));
		page.setResults(this.hardwareMapper.selectHardwaresByPage(page));
		return page;
	}

	@Transactional
	public int queryExistHardwareByName(String hardware_name) {
		return this.hardwareMapper.selectExistHardwareByName(hardware_name);
	}
	
	@Transactional
	public List<Hardware> queryHardwares(Hardware hardware) {
		return this.hardwareMapper.selectHardwares(hardware);
	}
	
	/*
	 * Hardware end
	 */

}
