package com.tm.broadband.mapper;


import java.util.List;

import com.tm.broadband.model.WebsiteStaticResources;
import com.tm.broadband.model.Page;

public interface WebsiteStaticResourcesMapper {

/**
 * mapping tm_website_static_resources, websiteStaticResources DAO component
 * 
 * @author CyberPark
 * 
  */

	/* SELECT AREA */

	List<WebsiteStaticResources> selectWebsiteStaticResources(WebsiteStaticResources wsr);
	List<WebsiteStaticResources> selectWebsiteStaticResourcessByPage(Page<WebsiteStaticResources> page);
	int selectWebsiteStaticResourcessSum(Page<WebsiteStaticResources> page);

	/* // END SELECT AREA */
	/* =================================================================================== */
	/* INSERT AREA */

	void insertWebsiteStaticResources(WebsiteStaticResources wsr);

	/* // END INSERT AREA */
	/* =================================================================================== */
	/* UPDATE AREA */

	void updateWebsiteStaticResources(WebsiteStaticResources wsr);

	/* // END UPDATE AREA */
	/* =================================================================================== */
	/* DELETE AREA */

	/* // END DELETE AREA */

}
