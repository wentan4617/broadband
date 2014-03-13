package com.tm.broadband.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tm.broadband.mapper.HardwareMapper;
import com.tm.broadband.mapper.PlanMapper;
import com.tm.broadband.mapper.TopupMapper;
import com.tm.broadband.model.Hardware;
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
	private HardwareMapper hardwareMapper;

	@Autowired
	public PlanService(PlanMapper planMapper, TopupMapper topupMapper,
			HardwareMapper hardwareMapper) {
		this.planMapper = planMapper;
		this.topupMapper = topupMapper;
		this.hardwareMapper = hardwareMapper;
	}

	public PlanService() {
	}

	/*
	 * PLAN AREA
	 */
	
	@Transactional
	public void savePlan(Plan plan) {
		
		
		if ("plan-topup".equals(plan.getPlan_group()) && plan.getTopupArray() != null) {
			String idArray = "";
			for (int i = 0, len = plan.getTopupArray().length; i < len; i++) {
				idArray += plan.getTopupArray()[i];
				if (i < len - 1) idArray += ",";
			}
			plan.setPlan_topupid_array(idArray);
			
		}
		
		System.out.println(plan.getPlan_topupid_array());
		this.planMapper.insertPlan(plan);

		
		if ("plan-topup".equals(plan.getPlan_group()) &&  plan.getTopupArray() != null) {
			for (String topupId : plan.getTopupArray()) {
				this.planMapper.insertPlanTopup(plan.getId(),
						Integer.parseInt(topupId));
			}
		}
	}

	@Transactional
	public void removePlanById(int id) {
		this.planMapper.deletePlanById(id);
	}

	@Transactional
	public void editPlan(Plan plan) {
		if ("plan-topup".equals(plan.getPlan_group()) && plan.getTopupArray() != null) {
			String idArray = "";
			for (int i = 0, len = plan.getTopupArray().length; i < len; i++) {
				idArray += plan.getTopupArray()[i];
				if (i < len - 1) idArray += ",";
			}
			plan.setPlan_topupid_array(idArray);
			
		}

		this.planMapper.updatePlan(plan);
		
		this.planMapper.deletePlanTopupById(plan.getId());

	
		if ("plan-topup".equals(plan.getPlan_group()) &&  plan.getTopupArray() != null) {
			for (String topupId : plan.getTopupArray()) {
				this.planMapper.insertPlanTopup(plan.getId(),
						Integer.parseInt(topupId));
			}
		}
	}

	@Transactional
	public Plan queryPlanById(int id) {
		return this.planMapper.selectPlanById(id);
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
	public List<Plan> queryPlansBySome(Plan plan) {
		return this.planMapper.selectPlansBySome(plan);
	}
	
	@Transactional
	public List<Plan> queryPlansWithTopups(Plan plan) {
		return this.planMapper.selectPlansWithTopups(plan);
	}
	
	/*
	 * END PLAN AREA
	 */
	
	/*
	 * TOPUP AREA
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
		return null; //this.topupMapper.selectTopups();
	}

	@Transactional
	public Page<Topup> queryTopupsByPage(Page<Topup> page) {
		page.setTotalRecord(this.topupMapper.selectTopupsSum(page));
		page.setResults(this.topupMapper.selectTopupsByPage(page));
		return page;
	}

	/*
	 * END TOPUP AREA
	 */
	
	/*
	 * Hardware Begin
	 */
	
	@Transactional
	public void saveHardware(Hardware hardware) {
		this.hardwareMapper.insertHardware(hardware);
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
	public List<Hardware> queryHardwaresBySome(Hardware hardware) {
		return this.hardwareMapper.selectHardwaresBySome(hardware);
	}
	
	/*
	 * Hardware end
	 */

}
