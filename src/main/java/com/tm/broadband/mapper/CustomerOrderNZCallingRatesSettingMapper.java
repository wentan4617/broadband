package com.tm.broadband.mapper;


import java.util.List;

import com.tm.broadband.model.CustomerOrderNZCallingRatesSetting;
import com.tm.broadband.model.Page;

public interface CustomerOrderNZCallingRatesSettingMapper {

/**
 * mapping tm_customer_order_nz_calling_rates_setting, customerOrderNZCallingRatesSetting DAO component
 * 
 * @author CyberPark
 * 
  */

	/* SELECT AREA */

	List<CustomerOrderNZCallingRatesSetting> selectCustomerOrderNZCallingRatesSetting(CustomerOrderNZCallingRatesSetting conzcrs);
	List<CustomerOrderNZCallingRatesSetting> selectCustomerOrderNZCallingRatesSettingsByPage(Page<CustomerOrderNZCallingRatesSetting> page);
	int selectCustomerOrderNZCallingRatesSettingsSum(Page<CustomerOrderNZCallingRatesSetting> page);

	/* // END SELECT AREA */
	/* =================================================================================== */
	/* INSERT AREA */

	void insertCustomerOrderNZCallingRatesSetting(CustomerOrderNZCallingRatesSetting conzcrs);

	/* // END INSERT AREA */
	/* =================================================================================== */
	/* UPDATE AREA */

	void updateCustomerOrderNZCallingRatesSetting(CustomerOrderNZCallingRatesSetting conzcrs);

	/* // END UPDATE AREA */
	/* =================================================================================== */
	/* DELETE AREA */

	void deleteCustomerOrderNZCallingRatesSettingById(int id);
	void deleteCustomerOrderNZCallingRatesSettingByOrderId(int id);

	/* // END DELETE AREA */

}
