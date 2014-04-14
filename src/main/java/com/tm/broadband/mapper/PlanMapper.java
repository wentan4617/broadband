package com.tm.broadband.mapper;

import java.util.List;

import com.tm.broadband.model.Page;
import com.tm.broadband.model.Plan;

/**
 * mapping tm_plan, plan DAO component
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

public interface PlanMapper {
	
	/* SELECT AREA */
	
	Plan selectPlan(Plan plan);
	List<Plan> selectPlans(Plan plan);
	List<Plan> selectPlansByPage(Page<Plan> page);
	int selectPlansSum(Page<Plan> page);

	Plan selectPlanById(int id);
	int selectExistPlanByName(String plan_name);
	int selectExistNotSelfPlanfByName(String plan_name, int id);

	List<String> selectDistinctPlanGroup();
	
	/* // END SELECT AREA */
	
	/* =================================================================================== */
	
	/* INSERT AREA */
	
	void insertPlan(Plan plan);
	void insertPlanTopup(int planId, int topupId);
	
	/* // END INSERT AREA */
	
	/* =================================================================================== */
	
	/* UPDATE AREA */
	
	void updatePlan(Plan plan);
	void updatePlanStatusById(String status, int id);
	
	/* // END UPDATE AREA */
	
	/* =================================================================================== */
	
	/* DELETE AREA */
	
	void deletePlanById(int id);
	
	/* // END DELETE AREA */
	
}
