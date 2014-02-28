package com.tm.broadband.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tm.broadband.mapper.CustomerMapper;
import com.tm.broadband.mapper.CustomerOrderMapper;
import com.tm.broadband.mapper.ProvisionLogMapper;
import com.tm.broadband.model.Customer;
import com.tm.broadband.model.CustomerOrder;
import com.tm.broadband.model.ProvisionLog;
import com.tm.broadband.model.User;

@Service
public class ProvisionService {
	
	private CustomerMapper customerMapper;
	private CustomerOrderMapper customerOrderMapper;
	private ProvisionLogMapper provisionLogMapper;
	
	@Autowired
	public ProvisionService(CustomerMapper customerMapper, 
			CustomerOrderMapper customerOrderMapper,
			ProvisionLogMapper provisionLogMapper) {
		this.customerMapper = customerMapper;
		this.customerOrderMapper = customerOrderMapper;
		this.provisionLogMapper = provisionLogMapper;
	}

	public ProvisionService() {}
	
	@Transactional
	public void activeRegisterCustomers(String[] customer_ids, int user_id) {
		
		if (customer_ids != null) {
			
			for (String customer_id: customer_ids) {
				
				Customer customer = new Customer();
				customer.setId(Integer.parseInt(customer_id));
				customer.setStatus("active");
				
				this.customerMapper.updateCustomerStatus(customer);
				
				int order_id = this.customerOrderMapper.selectCustomerOrderIdByCId(customer.getId());
				
				this.customerOrderMapper.updateCustomerOrderStatusById(order_id, "payment");
				
				ProvisionLog log = new ProvisionLog();
				User user = new User();
				user.setId(user_id);
				log.setUser(user);
				log.setOrder_sort("customer-order");
				CustomerOrder customerOrder = new CustomerOrder();
				customerOrder.setId(order_id);
				log.setOrder_id_customer(customerOrder);
				log.setProcess_way("pending to payment");
				
				this.provisionLogMapper.insertProvisionLog(log);
			}
			
		}		
	}
	
	
	/*public int queryPaidCustomersCount() {
		return 
	}*/

}
