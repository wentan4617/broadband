package com.tm.broadband.mapper;

import java.util.List;

import com.tm.broadband.model.Organization;
import com.tm.broadband.model.Page;

/* SELECT AREA *//* // END SELECT AREA */
/* =================================================================================== */
/* INSERT AREA *//* // END INSERT AREA */
/* =================================================================================== */
/* UPDATE AREA *//* // END UPDATE AREA */
/* =================================================================================== */
/* DELETE AREA *//* // END DELETE AREA */

public interface OrganizationMapper {
	
	/* SELECT AREA */
	
	List<Organization> selectOrganizations(Organization organization);
	List<Organization> selectOrganizationsByPage(Page<Organization> page);
	int selectOrganizationsSum(Page<Organization> page);
	
	/* // END SELECT AREA */
	
	/* =================================================================================== */
	
	/* INSERT AREA */
	
	void insertOrganization(Organization organization);
	
	/* // END INSERT AREA */
	
	/* =================================================================================== */
	
	/* UPDATE AREA */
	
	void updateOrganization(Organization organization);
	
	/* // END UPDATE AREA */
	/* =================================================================================== */
	/* DELETE AREA */
	
	void deleteOrganizationByCustomerId(Integer id);
	
	/* // END DELETE AREA */
	
}
