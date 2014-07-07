package com.tm.broadband.mapper;

import java.util.List;

import com.tm.broadband.model.Page;
import com.tm.broadband.model.TempDataUsage;
import com.tm.broadband.model.User;

/**
 * mapping tm_user, user DAO component
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

public interface RadacctMapper {

	/* SELECT AREA */
	
	Long selectMaxCount();
	Long selectMaxCountMinByDate(String date);
	Long selectMaxCountMaxByDate(String date);
	List<TempDataUsage> selectDataUsageRecent(Long maxcount);
	
	/* // END SELECT AREA */
	/* =================================================================================== */
	/* INSERT AREA */
	
	void insertTempDataUsage(TempDataUsage temp);
	
	/* // END INSERT AREA */
	
	/* =================================================================================== */
	
	/* UPDATE AREA */
	
	
	
	/* // END UPDATE AREA */
	
	/* =================================================================================== */
	
	/* DELETE AREA */
	
	void deleteRadacct();
	
	/* // END DELETE AREA */
}
