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
			<a class="navbar-brand" href="${ctx}/broadband-wholesale/index">TMS Wholesale System</a>
			<c:if test="${userSession != null }">
				<a class="navbar-brand" href="${ctx}/broadband-user/index"><span style="border:4px solid green; border-radius:10px; padding:4px;">To CyberPark</span></a>
			</c:if>
		</div>
		<c:if test="${userSession != null || wholesalerSession != null }">
			<div class="collapse navbar-collapse">
				<ul class="nav navbar-nav">
					<li class="dropdown">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown">
							Material <b class="caret"></b>
						</a>
						<ul class="dropdown-menu">
							<li>
								<a href="javascript:void(0);" id="create_group_nav">
									<span class="glyphicon glyphicon-plus" style="padding-right:10px;"></span>
									Create Group
								</a>
							</li>
							<li>
								<a href="javascript:void(0);" id="create_type_nav">
									<span class="glyphicon glyphicon-plus" style="padding-right:10px;"></span>
									Create type
								</a>
							</li>
							<li class="divider"></li>
							<li>
								<a href="${ctx}/broadband-wholesale/material/create">
									<span class="glyphicon glyphicon-plus" style="padding-right:10px;"></span>
									Create Material
								</a>
							</li>
							<li>
								<a href="${ctx}/broadband-wholesale/material-combo/view">
									<span class="glyphicon glyphicon-list" style="padding-right:10px;"></span>
									View Material
								</a>
							</li>
							<li>
								<a href="${ctx}/broadband-wholesale/material/edit">
									<span class="glyphicon glyphicon-plus" style="padding-right:10px;"></span>
									Modify Material
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
				</ul>
				<p class="navbar-text pull-right">
					<span class="glyphicon glyphicon-user" style="margin-right:10px;"></span>
					<a href="javascript:void(0);" class="navbar-link" style="margin-right:10px;">${userSession!=null ? userSession.user_name : wholesalerSession.name}</a>
					<c:choose>
						<c:when test="${userSession != null }">
							<a href="${ctx}/broadband-user/signout" data-toggle="tooltip" data-placement="bottom" data-original-title="Sign out">
								<span class="glyphicon glyphicon-log-out" style="margin-right:10px;"></span>
							</a>
						</c:when>
						<c:otherwise>
							<a href="${ctx}/broadband-wholesale/signout" data-toggle="tooltip" data-placement="bottom" data-original-title="Sign out">
								<span class="glyphicon glyphicon-log-out" style="margin-right:10px;"></span>
							</a>
						</c:otherwise>
					</c:choose>
				</p>
			</div>
		</c:if>
	</div>
</div>