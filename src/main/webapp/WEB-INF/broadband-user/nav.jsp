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
			<c:if test="${userSession.user_role == 'sales'}">
				<a class="navbar-brand" href="${ctx}/broadband-user/sale/online/ordering/view/1/${userSession.id}">CyberPark Manager System</a>
			</c:if>
			<c:if test="${userSession.user_role != 'sales'}">
			<a class="navbar-brand" href="${ctx}/broadband-user/index">CyberPark Manager System</a>
			</c:if>
		</div>
		<c:if test="${userSession != null }">
			<div class="collapse navbar-collapse">
				<ul class="nav navbar-nav">
					<c:if test="${userSession.user_role != 'sales' }">
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
								<a href="${ctx}/broadband-user/crm/customer/view">
									<span class="glyphicon glyphicon-list" style="padding-right:10px;"></span>
									View Customer
								</a>
							</li>
							<li>
								<a href="${ctx}/broadband-user/crm/customer/personal/create">
									<span class="glyphicon glyphicon-plus" style="padding-right:10px;"></span>
									Create Personal Customer
								</a>
							</li>
							<li>
								<a href="${ctx}/broadband-user/crm/customer/business/create">
									<span class="glyphicon glyphicon-plus" style="padding-right:10px;"></span>
									Create Business Customer
								</a>
							</li>
							<li class="divider"></li>
							<li>
								<a href="${ctx}/broadband-user/crm/customer-service-record/view/1">
									<span class="glyphicon glyphicon-list" style="padding-right:10px;"></span>
									View Customer Service Record
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
								<a href="${ctx}/broadband-user/billing/invoice/view/1/unpaid">
									<span class="glyphicon glyphicon-list-alt" style="padding-right:10px;"></span>
									View Invoice
								</a>
							</li>
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
                    			<a href="${ctx }/broadband-user/provision/customer/view/1/paid">
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
		                   		<a href="${ctx }/broadband-user/data/customer/view">
		                   			<span class="glyphicon glyphicon-cloud" style="padding-right:10px;"></span>
		                   			Data Customer View
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
							<li class="divider"></li>
	                    	<li>
								<a href="${ctx}/broadband-user/system/chart/customer-register/0">
									<span class="glyphicon glyphicon-picture" style="padding-right:10px;"></span>
									Chart(Register Customer)
								</a>
	                    	</li>
						</ul>
					</li>
					</c:if>
					<li class="dropdown">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown">
							Sales <b class="caret"></b>
						</a>
						<ul class="dropdown-menu">
							<li>
								<a href="${ctx}/broadband-user/sale/online/ordering/view/1/${userSession.user_role == 'sales' ? userSession.id : 0 }">
									<span class="glyphicon glyphicon-list" style="padding-right:10px;"></span>
									View Online Orders (PAD | PC)
								</a>
							</li>
							<li>
								<a href="${ctx}/broadband-user/sale/online/ordering/plans/business">
									<span class="glyphicon glyphicon-plus" style="padding-right:10px;"></span>
									Ordering Online (PAD | PC)
								</a>
							</li>
						</ul>
					</li>
					<c:if test="${userSession.user_role != 'sales' }">
					<li class="dropdown">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown">
							Manual <b class="caret"></b>
						</a>
						<ul class="dropdown-menu">
							<li>
								<a href="${ctx}/broadband-user/manual-manipulation/manual-manipulation-record/view/1/generate-termed-invoice">
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
								<a href="${ctx}/broadband-user/manual-manipulation/call-international-rate/view/1">
									<span class="glyphicon glyphicon-registration-mark" style="padding-right:10px;"></span>
									Calling International Rate
								</a>
							</li>
						</ul>
					</li>
					</c:if>
				</ul>
				<p class="navbar-text pull-right">
					<span class="glyphicon glyphicon-user" style="margin-right:10px;"></span>
					<a href="javascript:void(0);" class="navbar-link" style="margin-right:10px;">${userSession.user_name}</a>
					<a href="${ctx}/broadband-user/signout" data-toggle="tooltip" data-placement="bottom" data-original-title="Sign out">
						<span class="glyphicon glyphicon-log-out" style="margin-right:10px;"></span>
					</a>
				</p>
			</div>
		</c:if>
	</div>
</div>