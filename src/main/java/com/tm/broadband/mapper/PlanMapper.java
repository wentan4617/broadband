package com.tm.broadband.mapper;

import java.util.List;

import com.tm.broadband.model.Page;
import com.tm.broadband.model.Plan;

/**
 * mapping tm_plan, planDAO component
 * 
 * @author Cook1fan
 * 
 */
public interface PlanMapper {

	void insertPlan(Plan plan);

	void deletePlanById(int id);

	void updatePlan(Plan plan);

	List<Plan> selectLikePlansByName(String plan_name);

	List<Plan> selectPlans();

	Plan selectPlanById(int id);

	Plan selectPlanByName(String plan_name);

	int selectExistPlanByName(String plan_name);

	int selectExistNotSelfPlanfByName(String plan_name, int id);
	
	List<Plan> selectPlansByPage(Page<Plan> page);
	
	int selectPlansSum(Page<Plan> page);
	
	void updatePlanStatusById(String status, int id);
	
	List<Plan> selectPlansByType(String type);
	
	void insertPlanTopup(int planId, int topupId);
	
	void deletePlanTopupById(int planId);
}
