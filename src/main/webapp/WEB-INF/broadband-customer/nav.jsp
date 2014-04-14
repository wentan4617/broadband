<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<div class="navbar navbar-darkgreen navbar-static-top" >
	<div class="container">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
				<span class="icon-bar"></span> 
				<span class="icon-bar"></span> 
				<span class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="${ctx }">
				<span class="logo"></span> 
			</a>
		</div>
		<div class="collapse navbar-collapse">
			<%-- <ul class="nav navbar-nav">
				<li class="dropdown">
					<a href="#" class="dropdown-toggle" data-toggle="dropdown" >
						<strong style="font-size:18px;"> Internet </strong><b class="caret"></b>
					</a>
					<ul class="dropdown-menu">
						<li>
							<a href="${ctx }/plans/t">Plan Top-Up</a>
						</li>
						<li>
							<a href="${ctx }/plans/p">Plan Prepay</a>
						</li>
					</ul>
				</li> 
			</ul> --%>
			
			<c:if test="${customerSession != null }">
				<p class="navbar-text pull-right" >
					
					<a href="${ctx }/customer/home" class="navbar-link" style="margin-right:10px;">
						<span class="glyphicon glyphicon-user" style="margin-right:10px;"></span>
						<c:choose>
							<c:when test="${customerSession.customer_type == 'personal' }">
								${customerSession.first_name } ${customerSession.last_name }
							</c:when>
							<c:when test="${customerSession.customer_type == 'business' }">
								${customerSession.company_name }
							</c:when>
						</c:choose>
					</a>
					<a href="${ctx}/signout" data-toggle="tooltip" data-placement="bottom" title data-original-title="Sign out">
						<span class="glyphicon glyphicon-log-out" style="margin-right:10px;"></span>
					</a>
				</p>
			</c:if>
			<c:if test="${customerSession == null }">
				<!-- <p class="navbar-text navbar-right" >
	     			<a href="${ctx }/login" class="btn btn-default navbar-btn" >Sign in</a>
	 			</p> -->
	 			<a href="${ctx }/login" class="btn btn-success navbar-btn navbar-right" >
	 				<span class="glyphicon glyphicon-log-in"></span> login
	 			</a>
			</c:if>
		</div>
		
	</div>
</div>


