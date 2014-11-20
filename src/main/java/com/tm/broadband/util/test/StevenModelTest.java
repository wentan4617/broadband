package com.tm.broadband.util.test;

import com.tm.broadband.util.SimpleMapperCreator;

public class StevenModelTest {

	public static void main(String[] args) {

		SimpleMapperCreator smc = new SimpleMapperCreator();
		smc.setAuthor("CyberPark");
		smc.setModel("CustomerOrderProvisionChecklist");
		smc.setTable("tm_customer_order_provision_checklist");
		smc.initial();
		
	}

}
