<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<div class="navbar navbar-default navbar-static-top" role="navigation">
	<div class="container">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="${ctx}/broadband-user/index">Home</a>
		</div>
		<c:if test="${userSession != null }">
			<div class="collapse navbar-collapse">
				<ul class="nav navbar-nav">
					<c:if test="${userSession.user_role != 'sales' && userSession.user_role != 'agent'}">
					<li class="dropdown">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown">
							Plan <b class="caret"></b>
						</a>
						<ul class="dropdown-menu">
							<li>
								<a href="${ctx}/broadband-user/plan/view">
									<span class="glyphicon glyphicon-list" style="padding-right:10px;"></span>
									View Plan
								</a>
							</li>
							<li>
								<a href="${ctx}/broadband-user/plan/create">
									<span class="glyphicon glyphicon-plus" style="padding-right:10px;"></span>
									Create Plan
								</a>
							</li>
							<li class="divider"></li>
							<li>
								<a href="${ctx}/broadband-user/plan/hardware/view/1">
									<span class="glyphicon glyphicon-list" style="padding-right:10px;"></span>
									View Hardware
								</a>
							</li>
							<li>
								<a href="${ctx}/broadband-user/plan/hardware/create">
									<span class="glyphicon glyphicon-plus" style="padding-right:10px;"></span>
									Create Hardware
								</a>
							</li>
						</ul>
					</li>
					<li class="dropdown">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown">
							CRM <b class="caret"></b>
						</a>
						<ul class="dropdown-menu">
							<li>
								<a href="${ctx}/broadband-user/crm/customer/order/view">
									<span class="glyphicon glyphicon-list" style="padding-right:10px;"></span>
									View Customer Order
								</a>
							</li>
							<li>
								<a href="${ctx}/broadband-user/crm/plans">
									<span class="glyphicon glyphicon-plus" style="padding-right:10px;"></span>
									New Order
								</a>
							</li>
							<li class="divider"></li>
							<li>
								<a href="${ctx}/broadband-user/crm/customer-service-record/view/1">
									<span class="glyphicon glyphicon-list" style="padding-right:10px;"></span>
									View Customer Service Record
								</a>
							</li>
							<li class="divider"></li>
							<li>
								<a href="${ctx}/broadband-user/crm/ticket/view">
									<span class="glyphicon glyphicon-bell" style="padding-right:10px;"></span>
									View Ticket
								</a>
							</li>
							<!-- <li class="divider"></li> -->
						</ul>
					</li>
					<li class="dropdown">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown">
							Billing <b class="caret"></b>
						</a>
						<ul class="dropdown-menu">
							<li>
								<a href="${ctx}/broadband-user/billing/invoice/view/personal/1/unpaid/all">
									<span class="glyphicon glyphicon-list-alt" style="padding-right:10px;"></span>
									View Invoice(Personal)
								</a>
							</li>
							<li>
								<a href="${ctx}/broadband-user/billing/invoice/view/business/1/unpaid/all">
									<span class="glyphicon glyphicon-list-alt" style="padding-right:10px;"></span>
									View Invoice(Business)
								</a>
							</li>
							<li>
								<a href="${ctx}/broadband-user/billing/transaction/view/1/visa/all">
									<span class="glyphicon glyphicon-list-alt" style="padding-right:10px;"></span>
									View Transaction
								</a>
							</li>
							<li class="divider"></li>
							<li>
								<a href="${ctx}/broadband-user/billing/chart/transaction-statistic/0">
									<span class="glyphicon glyphicon-usd" style="padding-right:10px;"></span>
									Chart(Transaction)
								</a>
							</li>
							<li>
								<a href="${ctx}/broadband-user/billing/chart/invoice-statistic/personal/0">
									<span class="glyphicon glyphicon-home" style="padding-right:10px;"></span>
									Chart(Monthly Personal)
								</a>
							</li>
							<li>
								<a href="${ctx}/broadband-user/billing/chart/invoice-statistic/business/0">
									<span class="glyphicon glyphicon-briefcase" style="padding-right:10px;"></span>
									Chart(Monthly Business)
								</a>
							</li>
							<li>
								<a href="${ctx}/broadband-user/billing/chart/annual-invoice-statistic/personal/0">
									<span class="glyphicon glyphicon-home" style="padding-right:10px;"></span>
									Chart(Annually Personal)
								</a>
							</li>
							<li>
								<a href="${ctx}/broadband-user/billing/chart/annual-invoice-statistic/business/0">
									<span class="glyphicon glyphicon-briefcase" style="padding-right:10px;"></span>
									Chart(Annually Business)
								</a>
							</li>
							<li class="divider"></li>
							<li>
								<a href="${ctx }/broadband-user/billing/chart/calling-statistic/all/0">
									<span class="glyphicon glyphicon-briefcase" style="padding-right:10px;"></span>
									Chart(Calling Statistic)
								</a>
							</li>
							<li class="divider"></li>
							<li>
								<a href="${ctx}/broadband-user/billing/early-termination-charge/view/1/unpaid">
									<span class="glyphicon glyphicon-list-alt" style="padding-right:10px;"></span>
									View Early Termination Charge
								</a>
							</li>
							<li>
								<a href="${ctx}/broadband-user/billing/termination-refund/view/1/unpaid">
									<span class="glyphicon glyphicon-list-alt" style="padding-right:10px;"></span>
									View Termination Refund
								</a>
							</li>
						</ul>
					</li>
					<li class="dropdown">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown">
							Provision <b class="caret"></b>
						</a>
						<ul class="dropdown-menu">
							<li>
								<a href="${ctx}/broadband-user/provision/view/1">
									<span class="glyphicon glyphicon-list" style="padding-right:10px;"></span>
									View Provision
								</a>
							</li>
							<li class="divider"></li>
							<li>
                    			<a href="${ctx }/broadband-user/provision/orders/view">
                    				<span class="glyphicon glyphicon-tasks" style="padding-right:10px;"></span>
                    				Provision Customer Order
                    			</a>
							</li>
							<li class="divider"></li>
							<li>
                    			<a href="${ctx }/broadband-user/provision/contact-us/view/1/new">
                    				<span class="glyphicon glyphicon-tasks" style="padding-right:10px;"></span>
                    				View Contact Us
                    			</a>
							</li>
							<li class="divider"></li>
	                    	<li>
								<a href="${ctx}/broadband-user/provision/sale/view/1">
									<span class="glyphicon glyphicon-tasks" style="padding-right:10px;"></span>
									View Sales
								</a>
	                    	</li>
							<li class="divider"></li>
	                    	<li>
								<a href="${ctx }/broadband-user/provision/number-couldnot-find/chorus/unmatched">
									<span class="glyphicon glyphicon-screenshot" style="padding-right:10px;"></span>
									View Provision Exceptions
								</a>
	                    	</li>
							<!-- <li class="divider"></li>
							<li>
								<a href="#">...</a>
							</li> -->
						</ul>
					</li>
					<li class="dropdown">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown">
							Data <b class="caret"></b>
						</a>
						<ul class="dropdown-menu">
							<li>
		                   		<a href="${ctx }/broadband-user/data/operatre">
		                   			<span class="glyphicon glyphicon-cloud" style="padding-right:10px;"></span>
		                   			Data Operatre
		                   		</a>
		                   	</li>
		                   	<li>
		                   		<a href="${ctx }/broadband-user/data/orders/view">
		                   			<span class="glyphicon glyphicon-cloud" style="padding-right:10px;"></span>
		                   			Data Orders View
		                   		</a>
		                   	</li>
						</ul>
					</li>
					<li class="dropdown">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown">
							System <b class="caret"></b>
						</a>
						<ul class="dropdown-menu">
							<li>
								<a href="${ctx}/broadband-user/system/user/view/1">
									<span class="glyphicon glyphicon-list" style="padding-right:10px;"></span>
									View User
								</a>
							</li>
							<li>
								<a href="${ctx}/broadband-user/system/user/create">
									<span class="glyphicon glyphicon-plus" style="padding-right:10px;"></span>
									Create User
								</a>
							</li>
							<li class="divider"></li>
							<li>
								<a href="${ctx}/broadband-user/system/notification/view/1">
									<span class="glyphicon glyphicon-list" style="padding-right:10px;"></span>
									View Notification
								</a>
							</li>
							<li>
								<a href="${ctx}/broadband-user/system/notification/create">
									<span class="glyphicon glyphicon-plus" style="padding-right:10px;"></span>
									Create Notification
								</a>
							</li>
							<li class="divider"></li>
							<li>
								<a href="${ctx}/broadband-user/system/company-detail/edit">
									<span class="glyphicon glyphicon-pencil" style="padding-right:10px;"></span>
									Edit Company Detail
								</a>
							</li>
							<li>
								<a href="${ctx }/broadband-user/system/seo/edit">
									<span class="glyphicon glyphicon-pencil" style="padding-right:10px;"></span>
									Edit SEO
								</a>
							</li>
							<li class="divider"></li>
	                    	<li>
								<a href="${ctx}/broadband-user/system/chart/customer-register/0">
									<span class="glyphicon glyphicon-picture" style="padding-right:10px;"></span>
									Chart(Register Customer)
								</a>
	                    	</li>
							<li>
								<a href="${ctx }/broadband-user/system/ir/edit">
									<span class="glyphicon glyphicon-pencil" style="padding-right:10px;"></span>
									Edit Invite Rates
								</a>
							</li>
						</ul>
					</li>
					</c:if>
					<li class="dropdown">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown">
							${userSession.user_role == 'agent' ? 'Ordering' : 'Sales'} <b class="caret"></b>
						</a>
						<ul class="dropdown-menu">
							<li>
								<a href="${ctx}/broadband-user/sale/online/ordering/view/1/${userSession.user_role == 'sales' ? userSession.id : 0 }">
									<span class="glyphicon glyphicon-list" style="padding-right:10px;"></span>
									View Online Orders (PAD | PC)
								</a>
							</li>
							<li>
								<a href="${ctx}/broadband-user/sale/plans">
									<span class="glyphicon glyphicon-plus" style="padding-right:10px;"></span>
									New Ordering Online (PAD | PC)
								</a>
							</li>
						</ul>
					</li>
					
					<c:if test="${userSession.user_role != 'sales' && userSession.user_role != 'agent'}">
					<li class="dropdown">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown">
							Manual <b class="caret"></b>
						</a>
						<ul class="dropdown-menu">
							<li>
								<a href="${ctx}/broadband-user/manual-manipulation/manual-manipulation-record/view/1">
									<span class="glyphicon glyphicon-list" style="padding-right:10px;"></span>
									Manual Termed Invoice
								</a>
							</li>
							<li class="divider"></li>
							<li>
								<a href="${ctx}/broadband-user/manual-manipulation/call-billing-record/view/1/inserted/all">
									<span class="glyphicon glyphicon-earphone" style="padding-right:10px;"></span>
									Customer Calling Billing
								</a>
							</li>
							<li>
								<a href="${ctx }/broadband-user/manual-manipulation/pstn-calling-rate/view/1">
									<span class="glyphicon glyphicon-registration-mark" style="padding-right:10px;"></span>
									PSTN Calling Rate
								</a>
							</li>
							<li>
								<a href="${ctx }/broadband-user/manual-manipulation/vos-voip-call-rate/view/1">
									<span class="glyphicon glyphicon-registration-mark" style="padding-right:10px;"></span>
									VoIP Calling Rate
								</a>
							</li>
						</ul>
					</li>
					<li class="dropdown">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown">
							Voucher <b class="caret"></b>
						</a>
						<ul class="dropdown-menu">
							<li>
								<a href="${ctx}/broadband-user/billing/voucher/view/1/unused">
									<span class="glyphicon glyphicon-list-alt" style="padding-right:10px;"></span>
									View Voucher
								</a>
							</li>
							<li>
								<a href="${ctx}/broadband-user/billing/voucher-file-upload-record/view/1/inactivated">
									<span class="glyphicon glyphicon-list-alt" style="padding-right:10px;"></span>
									View Voucher File Upload
								</a>
							</li>
						</ul>
					</li>
					</c:if>
					
					<!-- Agent Module's Billing, read only! -->
					<c:if test="${userSession.user_role == 'agent'}">
						<li class="dropdown">
							<a href="#" class="dropdown-toggle" data-toggle="dropdown">
								Billing <b class="caret"></b>
							</a>
							<ul class="dropdown-menu">
								<li>
									<a href="${ctx}/broadband-user/agent/billing/invoice/view/1/unpaid">
										<span class="glyphicon glyphicon-list" style="padding-right:10px;"></span>
										View Invoice
									</a>
								</li>
								<li>
									<a href="${ctx}/broadband-user/agent/billing/chart/commission-statistic/0">
										<span class="glyphicon glyphicon-list" style="padding-right:10px;"></span>
										Chart(Commission)
									</a>
								</li>
							</ul>
						</li>
					</c:if>
				</ul>
				
				<p class="navbar-text pull-right">
					<span class="glyphicon glyphicon-user" style="margin-right:10px;"></span>
					<a href="javascript:void(0);" class="navbar-link" style="margin-right:10px;">${userSession.user_name}&nbsp;
						<c:if test="${userSession.user_role == 'agent' || userSession.user_role == 'sales'}">
							|&nbsp;&nbsp;My ID: ${userSession.id}
						</c:if>
					</a>
					<a href="${ctx}/broadband-user/signout" data-toggle="tooltip" data-placement="bottom" data-original-title="Sign out">
						<span class="glyphicon glyphicon-log-out" style="margin-right:10px;"></span>
					</a>
				</p>
			</div>
		</c:if>
	</div>
</div>