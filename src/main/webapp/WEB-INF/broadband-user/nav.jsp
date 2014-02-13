<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<div class="navbar navbar-default navbar-fixed-top" role="navigation">
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
								<a href="${ctx}/broadband-user/plan/view/1">View Plan</a>
							</li>
							<li class="divider"></li>
							<li>
								<a href="${ctx}/broadband-user/plan/create">Create Plan</a>
							</li>
						</ul>
					</li>
					<li class="dropdown">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown">
							CRM <b class="caret"></b>
						</a>
						<ul class="dropdown-menu">
							<li>
								<a href="#">...</a>
							</li>
							<li class="divider"></li>
							<li>
								<a href="#">...</a>
							</li>
						</ul>
					</li>
					<li class="dropdown">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown">
							Billing <b class="caret"></b>
						</a>
						<ul class="dropdown-menu">
							<li>
								<a href="#">...</a>
							</li>
							<li class="divider"></li>
							<li>
								<a href="#">...</a>
							</li>
						</ul>
					</li>
					<li class="dropdown">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown">
							Provision <b class="caret"></b>
						</a>
						<ul class="dropdown-menu">
							<li>
								<a href="#">...</a>
							</li>
							<li class="divider"></li>
							<li>
								<a href="#">...</a>
							</li>
						</ul>
					</li>
				</ul>
				<p class="navbar-text pull-right">
					<span class="glyphicon glyphicon-user" style="margin-right:10px;"></span>
					<a href="javascript:void(0);" class="navbar-link" style="margin-right:10px;">${userSession.user_name}</a>
					<a href="${ctx}/broadband-user/signout" data-toggle="tooltip" data-placement="bottom" title data-original-title="Sign out">
						<span class="glyphicon glyphicon-log-out" style="margin-right:10px;"></span>
					</a>
				</p>
			</div>
		</c:if>
	</div>
</div>