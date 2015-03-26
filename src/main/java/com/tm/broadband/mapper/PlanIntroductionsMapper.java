package com.tm.broadband.mapper;


import java.util.List;

import com.tm.broadband.model.PlanIntroductions;
import com.tm.broadband.model.Page;

public interface PlanIntroductionsMapper {

/**
 * mapping tm_plan_introductions, planIntroductions DAO component
 * 
 * @author CyberPark
 * 
  */

	/* SELECT AREA */

	List<PlanIntroductions> selectPlanIntroductions(PlanIntroductions pi);
	List<PlanIntroductions> selectPlanIntroductionssByPage(Page<PlanIntroductions> page);
	int selectPlanIntroductionssSum(Page<PlanIntroductions> page);

	/* // END SELECT AREA */
	/* =================================================================================== */
	/* INSERT AREA */

	void insertPlanIntroductions(PlanIntroductions pi);

	/* // END INSERT AREA */
	/* =================================================================================== */
	/* UPDATE AREA */

	void updatePlanIntroductions(PlanIntroductions pi);

	/* // END UPDATE AREA */
	/* =================================================================================== */
	/* DELETE AREA */

	void deletePlanIntroductionsById(int id);

	/* // END DELETE AREA */

}
