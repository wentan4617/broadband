package com.tm.broadband.util.test;

import com.tm.broadband.util.SimpleMapperCreator;

public class StevenModelTest {

	public static void main(String[] args) {

		SimpleMapperCreator smc = new SimpleMapperCreator();
		smc.setAuthor("CyberPark");
		smc.setModel("ModelTest");
		smc.setTable("tm_model_test");
		smc.initial();
		
	}

}
