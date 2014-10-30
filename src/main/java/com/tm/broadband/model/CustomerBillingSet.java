package com.tm.broadband.model;

import java.io.Serializable;
import java.util.List;

public class CustomerBillingSet implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<CustomerOrder> cos;
	private List<CustomerTransaction> cts;
	private Page<CustomerInvoice> page;
	private Integer notPayOffInvoices;

	public List<CustomerOrder> getCos() {
		return cos;
	}

	public void setCos(List<CustomerOrder> cos) {
		this.cos = cos;
	}

	public List<CustomerTransaction> getCts() {
		return cts;
	}

	public void setCts(List<CustomerTransaction> cts) {
		this.cts = cts;
	}

	public Page<CustomerInvoice> getPage() {
		return page;
	}

	public void setPage(Page<CustomerInvoice> page) {
		this.page = page;
	}

	public Integer getNotPayOffInvoices() {
		return notPayOffInvoices;
	}

	public void setNotPayOffInvoices(Integer notPayOffInvoices) {
		this.notPayOffInvoices = notPayOffInvoices;
	}

}
