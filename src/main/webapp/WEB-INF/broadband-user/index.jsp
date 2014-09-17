<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<jsp:include page="header.jsp" />
<jsp:include page="alert.jsp" />

<style>
.panel-success {
	min-height: 251px;
}
hr {
	margin:0 0 5px 0;
}
</style>


<c:if test="${userSession != null }">
	<div class="container">
		<c:if test="${userSession.user_role != 'sales' && userSession.user_role != 'agent'}">
		    <div class="row">
		        <div class="col-md-3">
		        	<div class="panel panel-success">
				  		<div class="panel-heading">
				  			<h3 class="panel-title"><strong class="text-success">Plan</strong></h3>
				  		</div>
					  	<div class="panel-body">
					    	<p>Create a plan products, plan on-line sales</p>
		                    <ul class="list-unstyled">
		                    	<li>
		                    		<span class="glyphicon glyphicon-list" style="padding-right:10px;"></span>
		                    		<a href="${ctx }/broadband-user/plan/view">View Plan</a>
		                    	</li>
		                    	<li>
		                    		<span class="glyphicon glyphicon-plus" style="padding-right:10px;"></span>
		                    		<a href="${ctx }/broadband-user/plan/create">Create Plan</a>
		                    	</li>
		                    </ul>
		                    <hr/>
		                    <ul class="list-unstyled">
		                    	<li>
		                    		<span class="glyphicon glyphicon-list" style="padding-right:10px;"></span>
		                    		<a href="${ctx }/broadband-user/plan/hardware/view/1">View Hardware</a>
		                    	</li>
		                    	<li>
		                    		<span class="glyphicon glyphicon-plus" style="padding-right:10px;"></span>
		                    		<a href="${ctx }/broadband-user/plan/hardware/create">Create Hardware</a>
		                    	</li>
		                    </ul>
					 	</div>
					</div>
		        </div>
		        <div class="col-md-3">
		        	<div class="panel panel-success">
				  		<div class="panel-heading">
				  			<h3 class="panel-title"><strong class="text-success">CRM</strong></h3>
				  		</div>
					  	<div class="panel-body">
					  		<p>Simple customer relationship management module</p>
		                    <ul class="list-unstyled">
		                    	<li>
		                    		<span class="glyphicon glyphicon-list" style="padding-right:10px;"></span>
		                    		<a href="${ctx }/broadband-user/crm/customer/view">View Customer</a>
		                    	</li>
								<li>
		                    		<span class="glyphicon glyphicon-plus" style="padding-right:10px;"></span>
		                    		<a href="${ctx }/broadband-user/crm/customer/personal/create">Create Personal Customer</a>
								</li>
								<li>
		                    		<span class="glyphicon glyphicon-plus" style="padding-right:10px;"></span>
		                    		<a href="${ctx }/broadband-user/crm/customer/business/create">Create Business Customer</a>
								</li>
		                    </ul>
		                    <hr/>
							<ul class="list-unstyled">
								<li>
		                    		<span class="glyphicon glyphicon-list" style="padding-right:10px;"></span>
		                    		<a href="${ctx }/broadband-user/crm/customer-service-record/view/1">View Customer Service Record</a>
								</li>
							</ul>
		                    <hr/>
							<ul class="list-unstyled">
								<li>
		                    		<span class="glyphicon glyphicon-bell" style="padding-right:10px;"></span>
		                    		<a href="${ctx }/broadband-user/crm/ticket/view">
		                    			View Ticket&nbsp;&nbsp;<span class="badge" style="background:red;">${existingSum+newSum}</span>
		                    		</a>
								</li>
							</ul>
					  	</div>
					</div>
		        </div>
		        <div class="col-md-3">
		        	<div class="panel panel-success">
				  		<div class="panel-heading">
				  			<h3 class="panel-title"><strong class="text-success">Billing</strong></h3>
				  		</div>
					  	<div class="panel-body">
					  		<p>Accounting module, contains all billing related details.</p>
							<ul class="list-unstyled">
								<li>
		                    		<span class="glyphicon glyphicon-list-alt" style="padding-right:10px;"></span>
		                    		<a href="${ctx }/broadband-user/billing/invoice/view/personal/1/unpaid/all">View Invoice(Personal)</a>
		                    	</li>
								<li>
		                    		<span class="glyphicon glyphicon-list-alt" style="padding-right:10px;"></span>
		                    		<a href="${ctx }/broadband-user/billing/invoice/view/business/1/unpaid/all">View Invoice(Business)</a>
		                    	</li>
							</ul>
							<hr/>
							<ul class="list-unstyled">
								<li>
		                    		<span class="glyphicon glyphicon-usd" style="padding-right:10px;"></span>
		                    		<a href="${ctx }/broadband-user/billing/chart/transaction-statistic/0">Chart(Transaction)</a>
		                    	</li>
								<li>
		                    		<span class="glyphicon glyphicon-home" style="padding-right:10px;"></span>
		                    		<a href="${ctx }/broadband-user/billing/chart/invoice-statistic/personal/0">Chart(Monthly Personal)</a>
		                    	</li>
								<li>
		                    		<span class="glyphicon glyphicon-briefcase" style="padding-right:10px;"></span>
		                    		<a href="${ctx }/broadband-user/billing/chart/invoice-statistic/business/0">Chart(Monthly Business)</a>
		                    	</li>
								<li>
		                    		<span class="glyphicon glyphicon-home" style="padding-right:10px;"></span>
		                    		<a href="${ctx }/broadband-user/billing/chart/annual-invoice-statistic/personal/0">Chart(Annually Personal)</a>
		                    	</li>
								<li>
		                    		<span class="glyphicon glyphicon-briefcase" style="padding-right:10px;"></span>
		                    		<a href="${ctx }/broadband-user/billing/chart/annual-invoice-statistic/business/0">Chart(Annually Business)</a>
		                    	</li>
							</ul>
							<hr/>
							<ul class="list-unstyled">
								<li>
		                    		<span class="glyphicon glyphicon-list-alt" style="padding-right:10px;"></span>
		                    		<a href="${ctx }/broadband-user/billing/voucher/view/1/unused">View Voucher</a>
		                    	</li>
								<li>
		                    		<span class="glyphicon glyphicon-list-alt" style="padding-right:10px;"></span>
		                    		<a href="${ctx }/broadband-user/billing/voucher-file-upload-record/view/1/inactivated">View Voucher File Upload</a>
		                    	</li>
							</ul>
							<hr/>
							<ul class="list-unstyled">
								<li>
		                    		<span class="glyphicon glyphicon-list-alt" style="padding-right:10px;"></span>
		                    		<a href="${ctx }/broadband-user/billing/early-termination-charge/view/1/unpaid">View Early Termination Charge</a>
		                    	</li>
		                    	<li>
		                    		<span class="glyphicon glyphicon-list-alt" style="padding-right:10px;"></span>
		                    		<a href="${ctx }/broadband-user/billing/termination-refund/view/1/unpaid">View Termination Refund</a>
		                    	</li>
							</ul>
					  	</div>
					</div>
		        </div>
		        <div class="col-md-3">
		        	<div class="panel panel-success">
				  		<div class="panel-heading">
				  			<h3 class="panel-title"><strong class="text-success">Provision</strong></h3>
				  		</div>
					  	<div class="panel-body">
					  		<p>Review all customer's purchased and paid orders.</p>
		                    <ul class="list-unstyled">
		                    	<li>
		                    		<span class="glyphicon glyphicon-list" style="padding-right:10px;"></span>
		                    		<a href="${ctx }/broadband-user/provision/view/1">View Provision</a>
		                    	</li>
		                    </ul>
		                    <hr/>
		                    <ul class="list-unstyled">
		                    	<li>
		                    		<span class="glyphicon glyphicon-tasks" style="padding-right:10px;"></span>
		                    		<a href="${ctx }/broadband-user/provision/customer/view/1/paid">Provision Customer Order</a>
		                    	</li>
		                    </ul>
		                    <hr/>
		                    <ul class="list-unstyled">
		                    	<li>
									<div class="btn-group">
			                    		<span class="glyphicon glyphicon-tasks" style="padding-right:10px;"></span>
			                    		<a href="${ctx }/broadband-user/provision/contact-us/view/1/new">
			                    			View Contact Us&nbsp;&nbsp;<span class="badge" style="background:red;">${newContactUsSum}</span>
			                    		</a>
			                    	</div>
		                    	</li>
		                    </ul>
		                    <hr/>
		                    <ul class="list-unstyled">
		                    	<li>
		                    		<span class="glyphicon glyphicon-tasks" style="padding-right:10px;"></span>
		                    		<a href="${ctx }/broadband-user/provision/sale/view/1">View Sales</a>
		                    	</li>
		                    </ul>
					  	</div>
					</div>
		        </div>
		    </div>
			</c:if>
			<div class="row">
			<c:if test="${userSession.user_role != 'sales' && userSession.user_role != 'agent'}">
				<div class="col-md-3">
					<div class="panel panel-success">
				  		<div class="panel-heading">
				  			<h3 class="panel-title"><strong class="text-success">Data</strong></h3>
				  		</div>
					  	<div class="panel-body">
					  		<p>Configure, manage, query, customer data traffic.</p>
		                    <ul class="list-unstyled">
		                    	<li>
		                    		<span class="glyphicon glyphicon-cloud" style="padding-right:10px;"></span>
		                    		<a href="${ctx }/broadband-user/data/operatre">Data Operatre</a>
		                    	</li>
		                    	<li>
		                    		<span class="glyphicon glyphicon-cloud" style="padding-right:10px;"></span>
		                    		<a href="${ctx }/broadband-user/data/customer/view">Data Customer View</a>
		                    	</li>
		                    </ul>
					  	</div>
					</div>
				</div>
				<div class="col-md-3">
					<div class="panel panel-success">
				  		<div class="panel-heading">
				  			<h3 class="panel-title"><strong class="text-success">System</strong></h3>
				  		</div>
					  	<div class="panel-body">
					  		<p>User management, email, SMS templates.</p>
		                    <ul class="list-unstyled">
		                    	<li>
		                    		<span class="glyphicon glyphicon-list" style="padding-right:10px;"></span>
		                    		<a href="${ctx }/broadband-user/system/user/view/1">View User</a>
		                    	</li>
		                    	<li>
		                    		<span class="glyphicon glyphicon-plus" style="padding-right:10px;"></span>
		                    		<a href="${ctx }/broadband-user/system/user/create">Create User</a>
		                    	</li>
		                    </ul>
		                    <hr/>
		                    <ul class="list-unstyled">
		                    	<li>
		                    		<span class="glyphicon glyphicon-list" style="padding-right:10px;"></span>
		                    		<a href="${ctx }/broadband-user/system/notification/view/1">View Notification</a>
		                    	</li>
		                    	<li>
		                    		<span class="glyphicon glyphicon-plus" style="padding-right:10px;"></span>
		                    		<a href="${ctx }/broadband-user/system/notification/create">Create Notification</a>
		                    	</li>
		                    </ul>
		                    <hr/>
		                    <ul class="list-unstyled">
		                    	<li>
		                    		<span class="glyphicon glyphicon-pencil" style="padding-right:10px;"></span>
		                    		<a href="${ctx }/broadband-user/system/company-detail/edit">Edit Company Detail</a>
		                    	</li>
		                    </ul>
		                    <ul class="list-unstyled">
		                    	<li>
		                    		<span class="glyphicon glyphicon-pencil" style="padding-right:10px;"></span>
		                    		<a href="${ctx }/broadband-user/system/seo/edit">Edit SEO</a>
		                    	</li>
		                    </ul>
		                    <hr/>
		                    <ul class="list-unstyled">
		                    	<li>
		                    		<span class="glyphicon glyphicon-picture" style="padding-right:10px;"></span>
		                    		<a href="${ctx }/broadband-user/system/chart/customer-register/0">Chart(Register Customer)</a>
		                    	</li>
		                    </ul>
					  	</div>
					</div>
				</div>
			</c:if>
				<div class="col-md-3">
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
		                    		<span class="glyphicon glyphicon-list" style="padding-right:10px;"></span>
		                    		<a href='${ctx }/broadband-user/sale/online/ordering/view/1/${userSession.user_role == "sales" ? userSession.id : 0 }'>View Online Orders (PAD | PC)</a>
		                    	</li>
		                    	<%-- <li>
		                    		<span class="glyphicon glyphicon-plus" style="padding-right:10px;"></span>
		                    		<a href="${ctx }/broadband-user/sale/online/ordering/plans/personal">Ordering Online (PAD | PC)</a>
		                    	</li> --%>
		                    	<li>
		                    		<span class="glyphicon glyphicon-plus" style="padding-right:10px;"></span>
		                    		<a href="${ctx}/broadband-user/sale/plans/select-customer-business">New Ordering Online (PAD | PC)</a>
		                    	</li>
		                    </ul>
					  	</div>
					</div>
				</div>
			<c:if test="${userSession.user_role == 'agent'}">
				<div class="col-md-3">
					<div class="panel panel-success">
				  		<div class="panel-heading">
				  			<h3 class="panel-title"><strong class="text-success">Billing</strong></h3>
				  		</div>
					  	<div class="panel-body">
					  		<p>Agent Operations. View Customer Invoices, Commissions</p>
		                    <ul class="list-unstyled">
								<li>
		                    		<span class="glyphicon glyphicon-list-alt" style="padding-right:10px;"></span>
		                    		<a href="${ctx }/broadband-user/agent/billing/invoice/view/1/unpaid">View Invoice</a>
		                    	</li>
								<li>
		                    		<span class="glyphicon glyphicon-picture" style="padding-right:10px;"></span>
		                    		<a href="${ctx }/broadband-user/agent/billing/chart/commission-statistic/0">Chart(Commission)</a>
		                    	</li>
		                    </ul>
					  	</div>
					</div>
				</div>
			</c:if>
			<c:if test="${userSession.user_role != 'sales' && userSession.user_role != 'agent'}">
				<div class="col-md-3">
					<div class="panel panel-success">
				  		<div class="panel-heading">
				  			<h3 class="panel-title"><strong class="text-success">Manual Manipulation</strong></h3>
				  		</div>
					  	<div class="panel-body">
					  		<p>These are substitutes for automatic execute program.</p>
		                    <ul class="list-unstyled">
		                    	<li>
		                    		<span class="glyphicon glyphicon-list" style="padding-right:10px;"></span>
		                    		<a href='${ctx }/broadband-user/manual-manipulation/manual-manipulation-record/view/1'>Manual Termed Invoice</a>
		                    	</li>
		                    </ul>
		                    <hr/>
		                    <ul class="list-unstyled">
								<li>
		                    		<span class="glyphicon glyphicon-earphone" style="padding-right:10px;"></span>
		                    		<a href="${ctx }/broadband-user/manual-manipulation/call-billing-record/view/1/inserted/all">Customer Calling Billing</a>
		                    	</li>
								<li>
		                    		<span class="glyphicon glyphicon-registration-mark" style="padding-right:10px;"></span>
		                    		<a href="${ctx }/broadband-user/manual-manipulation/call-international-rate/view/1">Calling International Rate</a>
		                    	</li>
		                    </ul>
					  	</div>
					</div>
				</div>
			</c:if>
			</div>
	</div>
</c:if>

<jsp:include page="footer.jsp" />
<jsp:include page="script.jsp" />
<jsp:include page="footer-end.jsp" />
