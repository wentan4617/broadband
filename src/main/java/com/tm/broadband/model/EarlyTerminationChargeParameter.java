package com.tm.broadband.model;

import java.io.Serializable;

public class EarlyTerminationChargeParameter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*
	 * BEGIN TABLE MAPPING PROPERTIES
	 */
	
	private Double overdue_extra_charge;
	
	/*
	 * END TABLE MAPPING PROPERTIES
	 */
	

	/*
	 * BEGIN RELATED PROPERTIES
	 */
	
	/*
	 * END RELATED PROPERTIES
	 */

	public Double getOverdue_extra_charge() {
		return overdue_extra_charge;
	}

	public void setOverdue_extra_charge(Double overdue_extra_charge) {
		this.overdue_extra_charge = overdue_extra_charge;
	}

}
