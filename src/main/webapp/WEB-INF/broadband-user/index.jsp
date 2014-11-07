<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<jsp:include page="header.jsp" />
<jsp:include page="alert.jsp" />

<style>
.panel-success {
	min-height: 240px;
}
.first-row-panel-success {
	min-height: 300px;
}
hr {
	margin:0 0 5px 0;
}
</style>

<div class="container">

	<c:if test="${userSession.user_role != 'sales' && userSession.user_role != 'agent'}">
	
	<div class="row">
		
		<!-- left column  -->
		<div class="col-md-6">
			
			<div class="row">
				
				<div class="col-md-6">
				
					<!-- CRM Module -->
					<div class="panel panel-success first-row-panel-success">
				  		<div class="panel-heading">
				  			<h3 class="panel-title"><strong class="text-success">CRM</strong></h3>
				  		</div>
					  	<div class="panel-body">
					  		<p>Simple customer relationship management module</p>
		                    <ul class="list-unstyled">
		                    	<li>
		                    		<span class="glyphicon glyphicon-list" ></span> 
		                    		<a href="${ctx }/broadband-user/crm/customer/order/view">View Customer Order</a>
		                    	</li>
								<li>
		                    		<span class="glyphicon glyphicon-plus" ></span> 
		                    		<a href="${ctx }/broadband-user/crm/plans">New Order</a>
								</li>
		                    </ul>
		                    <hr/>
							<ul class="list-unstyled">
								<li>
		                    		<span class="glyphicon glyphicon-list" ></span> 
		                    		<a href="${ctx }/broadband-user/crm/customer-service-record/view/1">View Customer Service Record</a>
								</li>
							</ul>
		                    <hr/>
							<ul class="list-unstyled">
								<li>
		                    		<span class="glyphicon glyphicon-bell" ></span> 
		                    		<a href="${ctx }/broadband-user/crm/ticket/view">
		                    			View Ticket <span class="badge" style="background:red;">${existingSum + newSum}</span>
		                    		</a>
								</li>
							</ul>
					  	</div>
					</div>
					<!-- // CRM Module -->
					
				</div>
				
				<div class="col-md-6">
				
					<!-- Provision Module -->
					<div class="panel panel-success first-row-panel-success">
				  		<div class="panel-heading">
				  			<h3 class="panel-title"><strong class="text-success">Provision</strong></h3>
				  		</div>
					  	<div class="panel-body">
					  		<p>Review all customer's purchased and paid orders.</p>
		                    <ul class="list-unstyled">
		                    	<li>
		                    		<span class="glyphicon glyphicon-list" ></span> 
		                    		<a href="${ctx }/broadband-user/provision/view/1">View Provision</a>
		                    	</li>
		                    	<li>
		                    		<span class="glyphicon glyphicon-tasks" ></span> 
		                    		<a href="${ctx }/broadband-user/provision/orders/view">Provision Customer Order</a>
		                    	</li>
		                    	
		                    	<li>
		                    		<span class="glyphicon glyphicon-tasks" ></span> 
		                    		<a href="${ctx }/broadband-user/provision/sale/view/1">View Sales</a>
		                    	</li>
		                    </ul>
		                    <hr>
		                    <ul class="list-unstyled">
		                    	<li>
									<span class="glyphicon glyphicon-tasks" ></span> 
		                    		<a href="${ctx }/broadband-user/provision/contact-us/view/1/new">
		                    			View Contact Us <span class="badge" style="background:red;">${newContactUsSum}</span>
		                    		</a>
		                    	</li>
		                    </ul>
		                    <hr>
		                    <ul class="list-unstyled">
								<li>
		                    		<span class="glyphicon glyphicon-screenshot" ></span> 
		                    		<a href="${ctx }/broadband-user/provision/number-couldnot-find/chorus/unmatched">View Provision Exceptions</a>
		                    	</li>
								<li>
		                    		<span class="glyphicon glyphicon-screenshot" ></span> 
		                    		<a href="${ctx }/broadband-user/provision/pstn-position-view/1/chorus">View PSTN Position</a>
		                    	</li>
		                    </ul>
					  	</div>
					</div>
					<!-- // Provision Module -->
					
				</div>
				
			</div>
			
			<div class="row">
				
				<div class="col-md-6">
				
					<!-- Plan Module -->
					<div class="panel panel-success">
				  		<div class="panel-heading">
				  			<h3 class="panel-title"><strong class="text-success">Plan</strong></h3>
				  		</div>
					  	<div class="panel-body">
					    	<p>Create a plan products, plan on-line sales</p>
		                    <ul class="list-unstyled">
		                    	<li>
		                    		<span class="glyphicon glyphicon-list" ></span> 
		                    		<a href="${ctx }/broadband-user/plan/view">View Plan</a>
		                    	</li>
		                    	<li>
		                    		<span class="glyphicon glyphicon-plus" ></span> 
		                    		<a href="${ctx }/broadband-user/plan/create">Create Plan</a>
		                    	</li>
		                    </ul>
		                    <hr/>
		                    <ul class="list-unstyled">
		                    	<li>
		                    		<span class="glyphicon glyphicon-list" ></span> 
		                    		<a href="${ctx }/broadband-user/plan/hardware/view/1">View Hardware</a>
		                    	</li>
		                    	<li>
		                    		<span class="glyphicon glyphicon-plus" ></span> 
		                    		<a href="${ctx }/broadband-user/plan/hardware/create">Create Hardware</a>
		                    	</li>
		                    </ul>
					 	</div>
					</div>
					<!-- // Plan Module -->
				
				</div>
				
				<div class="col-md-6">
				
					<!-- Data Module -->
					<div class="panel panel-success">
				  		<div class="panel-heading">
				  			<h3 class="panel-title"><strong class="text-success">Data</strong></h3>
				  		</div>
					  	<div class="panel-body">
					  		<p>Configure, manage, query, customer data traffic.</p>
		                    <ul class="list-unstyled">
		                    	<li>
		                    		<span class="glyphicon glyphicon-cloud" ></span> 
		                    		<a href="${ctx }/broadband-user/data/operatre">Data Operatre</a>
		                    	</li>
		                    	<li>
		                    		<span class="glyphicon glyphicon-cloud" ></span> 
		                    		<a href="${ctx }/broadband-user/data/orders/view">Data Orders View</a>
		                    	</li>
		                    </ul>
					  	</div>
					</div>
					<!-- // Data Module -->
					
				</div>
				
			</div>
			
			<div class="row">
			
				<div class="col-md-6">
				
					<!-- Manual Module -->
					<div class="panel panel-success">
				  		<div class="panel-heading">
				  			<h3 class="panel-title"><strong class="text-success">Manual Manipulation</strong></h3>
				  		</div>
					  	<div class="panel-body">
					  		<p>These are substitutes for automatic execute program.</p>
		                    <ul class="list-unstyled">
		                    	<li>
		                    		<span class="glyphicon glyphicon-list" ></span> 
		                    		<a href='${ctx }/broadband-user/manual-manipulation/manual-manipulation-record/view/1'>Manual Termed Invoice</a>
		                    	</li>
		                    </ul>
		                    <hr/>
		                    <ul class="list-unstyled">
								<li>
		                    		<span class="glyphicon glyphicon-earphone" ></span> 
		                    		<a href="${ctx }/broadband-user/manual-manipulation/call-billing-record/view/1/inserted/all">Customer Calling Billing</a>
		                    	</li>
								<li>
		                    		<span class="glyphicon glyphicon-registration-mark" ></span> 
		                    		<a href="${ctx }/broadband-user/manual-manipulation/pstn-calling-rate/view/1">PSTN Calling Rate</a>
		                    	</li>
								<li>
		                    		<span class="glyphicon glyphicon-registration-mark" ></span> 
		                    		<a href="${ctx }/broadband-user/manual-manipulation/vos-voip-call-rate/view/1">VoIP Calling Rate</a>
		                    	</li>
		                    </ul>
					  	</div>
					</div>
					<!-- // Manual Module -->
					
				</div>
				
				<div class="col-md-6">
				
					<!-- Voucher Module -->
					<div class="panel panel-success">
				  		<div class="panel-heading">
				  			<h3 class="panel-title"><strong class="text-success">Voucher</strong></h3>
				  		</div>
					  	<div class="panel-body">
					  		<p>View and upload voucher data.</p>
		                    <ul class="list-unstyled">
		                    	<li>
		                    		<span class="glyphicon glyphicon-list-alt" ></span> 
									<a href="${ctx}/broadband-user/billing/voucher/view/1/unused">View Voucher</a>
								</li>
								<li>
									<span class="glyphicon glyphicon-list-alt" ></span> 
									<a href="${ctx}/broadband-user/billing/voucher-file-upload-record/view/1/inactivated">View Voucher File Upload</a>
								</li>
		                    </ul>
					  	</div>
					</div>
					<!-- // Voucher Module -->
				
				</div>
				
			</div>
			
		</div>
		<!-- // left column  -->
		
		<!-- right column -->
		<div class="col-md-6">
		
			<!-- Billing Module -->
			<div class="panel panel-success first-row-panel-success">
		  		<div class="panel-heading">
		  			<h3 class="panel-title"><strong class="text-success">Billing</strong></h3>
		  		</div>
			  	<div class="panel-body">
			  		<p>Accounting module, contains all billing related details.</p>
			  		<div class="row">
			  			<div class="col-md-6">
			  				<ul class="list-unstyled">
								<li>
		                    		<span class="glyphicon glyphicon-list-alt" ></span> 
		                    		<a href="${ctx }/broadband-user/billing/invoice/view/personal/1/unpaid/all">View Invoice(Personal)</a>
		                    	</li>
								<li>
		                    		<span class="glyphicon glyphicon-list-alt" ></span> 
		                    		<a href="${ctx }/broadband-user/billing/invoice/view/business/1/unpaid/all">View Invoice(Business)</a>
		                    	</li>
								<li>
		                    		<span class="glyphicon glyphicon-list-alt" ></span> 
		                    		<a href="${ctx }/broadband-user/billing/transaction/view/1/visa/all">View Transaction</a>
		                    	</li>
							</ul>
							<hr/>
							<ul class="list-unstyled">
								<li>
		                    		<span class="glyphicon glyphicon-list-alt" ></span> 
		                    		<a href="${ctx }/broadband-user/billing/early-termination-charge/view/1/unpaid">View Early Termination Charge</a>
		                    	</li>
		                    	<li>
		                    		<span class="glyphicon glyphicon-list-alt" ></span> 
		                    		<a href="${ctx }/broadband-user/billing/termination-refund/view/1/unpaid">View Termination Refund</a>
		                    	</li>
							</ul>
			  			</div>
			  			<div class="col-md-6">
			  				<ul class="list-unstyled">
								<li>
		                    		<span class="glyphicon glyphicon-usd" ></span> 
		                    		<a href="${ctx }/broadband-user/billing/chart/transaction-statistic/0">Chart(Transaction)</a>
		                    	</li>
								<li>
		                    		<span class="glyphicon glyphicon-home" ></span> 
		                    		<a href="${ctx }/broadband-user/billing/chart/invoice-statistic/personal/0">Chart(Monthly Personal)</a>
		                    	</li>
								<li>
		                    		<span class="glyphicon glyphicon-briefcase" ></span> 
		                    		<a href="${ctx }/broadband-user/billing/chart/invoice-statistic/business/0">Chart(Monthly Business)</a>
		                    	</li>
								<li>
		                    		<span class="glyphicon glyphicon-home" ></span> 
		                    		<a href="${ctx }/broadband-user/billing/chart/annual-invoice-statistic/personal/0">Chart(Annually Personal)</a>
		                    	</li>
								<li>
		                    		<span class="glyphicon glyphicon-briefcase" ></span> 
		                    		<a href="${ctx }/broadband-user/billing/chart/annual-invoice-statistic/business/0">Chart(Annually Business)</a>
		                    	</li>
							</ul>
			  				<hr/>
			  				<ul class="list-unstyled">
								<li>
		                    		<span class="glyphicon glyphicon-phone-alt" ></span> 
		                    		<a href="${ctx }/broadband-user/billing/chart/calling-statistic/all/0">Chart(Calling Statistic)</a>
		                    	</li>
							</ul>
			  			</div>
			  		</div>
			  	</div>
			</div>
			<!-- // Billing Module -->
			
			<!-- Sales Module -->
			<div class="panel panel-success">
		  		<div class="panel-heading">
		  			<h3 class="panel-title">
		  				<strong class="text-success">${userSession.user_role == 'agent' ? 'Ordering' : 'Sales'}</strong>
		  			</h3>
		  		</div>
			  	<div class="panel-body">
			  		<p>${userSession.user_role == 'agent' ? 'Agent' : 'Sale'} Online Ordering.</p>
                    <ul class="list-unstyled">
                    	<li>
                    		<span class="glyphicon glyphicon-list" ></span> 
                    		<a href='${ctx }/broadband-user/sale/online/ordering/view/1/${userSession.user_role == "sales" ? userSession.id : 0 }'>View Online Orders (PAD | PC)</a>
                    	</li>
                    	<li>
                    		<span class="glyphicon glyphicon-plus" ></span> 
                    		<a href="${ctx}/broadband-user/sale/plans">New Ordering Online (PAD | PC)</a>
                    	</li>
                    </ul>
			  	</div>
			</div>
			<!-- // Sales Module -->
			
			<!-- System Module -->
			<div class="panel panel-success">
		  		<div class="panel-heading">
		  			<h3 class="panel-title"><strong class="text-success">System</strong></h3>
		  		</div>
			  	<div class="panel-body">
			  		<p>User management, email, SMS templates.</p>
		  			<div class="row">
		  				<div class="col-md-6">
		  					<ul class="list-unstyled">
		                    	<li>
		                    		<span class="glyphicon glyphicon-list" ></span> 
		                    		<a href="${ctx }/broadband-user/system/user/view/1">View User</a>
		                    	</li>
		                    	<li>
		                    		<span class="glyphicon glyphicon-plus" ></span> 
		                    		<a href="${ctx }/broadband-user/system/user/create">Create User</a>
		                    	</li>
		                    </ul>
		                    <hr/>
		                    <ul class="list-unstyled">
		                    	<li>
		                    		<span class="glyphicon glyphicon-list" ></span> 
		                    		<a href="${ctx }/broadband-user/system/notification/view/1">View Notification</a>
		                    	</li>
		                    	<li>
		                    		<span class="glyphicon glyphicon-plus" ></span> 
		                    		<a href="${ctx }/broadband-user/system/notification/create">Create Notification</a>
		                    	</li>
		                    </ul>
		  				</div>
		  				<div class="col-md-6">
		  					<ul class="list-unstyled">
		                    	<li>
		                    		<span class="glyphicon glyphicon-pencil" ></span> 
		                    		<a href="${ctx }/broadband-user/system/company-detail/edit">Edit Company Detail</a>
		                    	</li>
		                    	<li>
		                    		<span class="glyphicon glyphicon-pencil" ></span> 
		                    		<a href="${ctx }/broadband-user/system/seo/edit">Edit SEO</a>
		                    	</li>
		                    </ul>
		                    <hr/>
		                    <ul class="list-unstyled">
		                    	<li>
		                    		<span class="glyphicon glyphicon-picture" ></span> 
		                    		<a href="${ctx }/broadband-user/system/chart/customer-register/0">Chart(Register Customer)</a>
		                    	</li>
		                    	<li>
		                    		<span class="glyphicon glyphicon-pencil" ></span> 
		                    		<a href="${ctx }/broadband-user/system/ir/edit">Edit Invite Rates</a>
		                    	</li>
		                    </ul>
		  				</div>
		  			</div>
			  	</div>
			</div>
			<!-- // System Module -->
		
		</div>
		<!-- // right column -->
		
	</div>
	
	</c:if>
	
	<c:if test="${userSession.user_role == 'agent'}">
	
	<div class="row">
		
		<div class="col-md-4">
			
			<!-- Sales Module -->
			<div class="panel panel-success">
		  		<div class="panel-heading">
		  			<h3 class="panel-title">
		  				<strong class="text-success">${userSession.user_role == 'agent' ? 'Ordering' : 'Sales'}</strong>
		  			</h3>
		  		</div>
			  	<div class="panel-body">
			  		<p>${userSession.user_role == 'agent' ? 'Agent' : 'Sale'} Online Ordering.</p>
                    <ul class="list-unstyled">
                    	<li>
                    		<span class="glyphicon glyphicon-list" ></span> 
                    		<a href='${ctx }/broadband-user/sale/online/ordering/view/1/${userSession.user_role == "sales" ? userSession.id : 0 }'>View Online Orders (PAD | PC)</a>
                    	</li>
                    	<li>
                    		<span class="glyphicon glyphicon-plus" ></span> 
                    		<a href="${ctx}/broadband-user/sale/plans">New Ordering Online (PAD | PC)</a>
                    	</li>
                    </ul>
			  	</div>
			</div>
			<!-- // Sales Module -->
			
		</div>
		<div class="col-md-4">
		
			<!-- Billing Module -->
			<div class="panel panel-success">
		  		<div class="panel-heading">
		  			<h3 class="panel-title"><strong class="text-success">Billing</strong></h3>
		  		</div>
			  	<div class="panel-body">
			  		<p>Agent Operations. View Customer Invoices, Commissions</p>
                    <ul class="list-unstyled">
						<li>
                    		<span class="glyphicon glyphicon-list-alt" ></span>
                    		<a href="${ctx }/broadband-user/agent/billing/invoice/view/1/unpaid">View Invoice</a>
                    	</li>
						<li>
                    		<span class="glyphicon glyphicon-picture" ></span>
                    		<a href="${ctx }/broadband-user/agent/billing/chart/commission-statistic/0">Chart(Commission)</a>
                    	</li>
                    </ul>
			  	</div>
			</div>
			<!-- // Billing Module -->	
				
		</div>
		
	</div>
	
	</c:if>
	
	<c:if test="${userSession.user_role == 'sales'}">
		
	<div class="row">
	
		<div class="col-md-4">
			
			<!-- Sales Module -->
			<div class="panel panel-success">
		  		<div class="panel-heading">
		  			<h3 class="panel-title">
		  				<strong class="text-success">${userSession.user_role == 'agent' ? 'Ordering' : 'Sales'}</strong>
		  			</h3>
		  		</div>
			  	<div class="panel-body">
			  		<p>${userSession.user_role == 'agent' ? 'Agent' : 'Sale'} Online Ordering.</p>
                    <ul class="list-unstyled">
                    	<li>
                    		<span class="glyphicon glyphicon-list" ></span> 
                    		<a href='${ctx }/broadband-user/sale/online/ordering/view/1/${userSession.user_role == "sales" ? userSession.id : 0 }'>View Online Orders (PAD | PC)</a>
                    	</li>
                    	<li>
                    		<span class="glyphicon glyphicon-plus" ></span> 
                    		<a href="${ctx}/broadband-user/sale/plans">New Ordering Online (PAD | PC)</a>
                    	</li>
                    </ul>
			  	</div>
			</div>
			<!-- // Sales Module -->
			
		</div>
		
	</div>	
		
	</c:if>
	
</div>


<jsp:include page="footer.jsp" />
<jsp:include page="script.jsp" />
<jsp:include page="footer-end.jsp" />
