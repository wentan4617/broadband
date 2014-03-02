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
	
	List<Plan> selectPlansBySome(Plan plan);
	List<Plan> selectPlansWithTopups(Plan plan);
	Plan selectPlanById(int id);
	int selectExistPlanByName(String plan_name);
	int selectExistNotSelfPlanfByName(String plan_name, int id);
	List<Plan> selectPlansByPage(Page<Plan> page);
	int selectPlansSum(Page<Plan> page);
	
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
	void deletePlanTopupById(int planId);
	
	/* // END DELETE AREA */
	
}
