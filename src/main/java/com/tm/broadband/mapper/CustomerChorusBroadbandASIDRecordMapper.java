package com.tm.broadband.mapper;


import java.util.List;

import com.tm.broadband.model.CustomerChorusBroadbandASIDRecord;
import com.tm.broadband.model.Page;

public interface CustomerChorusBroadbandASIDRecordMapper {

/**
 * mapping tm_customer_chorus_broadband_asid_record, customerChorusBroadbandASIDRecord DAO component
 * 
 * @author CyberPark
 * 
  */

	/* SELECT AREA */

	List<CustomerChorusBroadbandASIDRecord> selectCustomerChorusBroadbandASIDRecord(CustomerChorusBroadbandASIDRecord ccbasidr);
	List<CustomerChorusBroadbandASIDRecord> selectCustomerChorusBroadbandASIDRecordsByPage(Page<CustomerChorusBroadbandASIDRecord> page);
	int selectCustomerChorusBroadbandASIDRecordsSum(Page<CustomerChorusBroadbandASIDRecord> page);

	/* // END SELECT AREA */
	/* =================================================================================== */
	/* INSERT AREA */

	void insertCustomerChorusBroadbandASIDRecord(CustomerChorusBroadbandASIDRecord ccbasidr);

	/* // END INSERT AREA */
	/* =================================================================================== */
	/* UPDATE AREA */

	void updateCustomerChorusBroadbandASIDRecord(CustomerChorusBroadbandASIDRecord ccbasidr);

	/* // END UPDATE AREA */
	/* =================================================================================== */
	/* DELETE AREA */

	void deleteCustomerChorusBroadbandASIDRecord(CustomerChorusBroadbandASIDRecord ccbasidr);

	/* // END DELETE AREA */

}
