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
			<a class="navbar-brand" href="${ctx}/broadband-user/index">CyberPark Manager System</a>
		</div>
		<c:if test="${userSession != null }">
			<div class="collapse navbar-collapse">
				<ul class="nav navbar-nav">
					<li class="dropdown">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown">
							Plan <b class="caret"></b>
						</a>
						<ul class="dropdown-menu">
							<li>
								<a href="${ctx}/broadband-user/plan/view/1">
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
							
							<%-- 
							<li class="divider"></li>
							<li>
								<a href="${ctx}/broadband-user/plan/topup/view/1">
									<span class="glyphicon glyphicon-list" style="padding-right:10px;"></span>
									View Topup
								</a>
							</li>
							<li>
								<a href="${ctx}/broadband-user/plan/topup/create">
									<span class="glyphicon glyphicon-plus" style="padding-right:10px;"></span>
									Create Topup
								</a>
							</li> --%>
						</ul>
					</li>
					<li class="dropdown">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown">
							CRM <b class="caret"></b>
						</a>
						<ul class="dropdown-menu">
							<li>
								<a href="${ctx}/broadband-user/crm/customer/view/1">
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
							<!-- <li class="divider"></li> -->
						</ul>
					</li>
					<li class="dropdown">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown">
							Billing <b class="caret"></b>
						</a>
						<!-- <ul class="dropdown-menu">
							<li>
								<a href="#">...</a>
							</li>
							<li class="divider"></li>
							<li>
								<a href="#">...</a>
							</li>
						</ul> -->
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
						<!-- <ul class="dropdown-menu">
							<li>
								<a href="#">...</a>
							</li>
							<li class="divider"></li>
							<li>
								<a href="#">...</a>
							</li>
						</ul> -->
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