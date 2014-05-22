<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<div class="navbar navbar-darkgreen navbar-static-top" id="navhead">
	<div class="container">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
				<span class="icon-bar"></span> 
				<span class="icon-bar"></span> 
				<span class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="${ctx }/home" rel="${nofollow}">
				<span class="logo"></span> 
			</a>
		</div>
		<div class="collapse navbar-collapse">
			<ul class="nav navbar-nav">
				<li class="active"><a href="${ctx }/plans/plan-term/personal" rel="nofollow"><strong>Personal Plan</strong></a></li>
				<li><a href="${ctx }/plans/plan-term/business" rel="nofollow"><strong>Business Plan</strong></a></li>
				<li><a href="${ctx }/wifi-solution"><strong>Wifi Solution</strong></a></li>
				<li><a href="${ctx }/e-commerce"><strong>E-Commerce</strong></a></li>
				<li><a href="${ctx }/about-us"><strong>About CyberPark</strong></a></li>
				<li><a href="${ctx }/about-us#contact" rel="nofollow"><strong>Contact Us: 0800 229 237</strong></a></li> 
			</ul>
			
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
					<a href="${ctx}/signout" rel="nofollow" data-toggle="tooltip" data-placement="bottom" title data-original-title="Sign out">
						<span class="glyphicon glyphicon-log-out" style="margin-right:10px;"></span>
					</a>
				</p>
			</c:if>
			<c:if test="${customerSession == null }">
	 			<a href="${ctx }/login" class="btn btn-success navbar-btn navbar-right" >
	 				<span class="glyphicon glyphicon-log-in"></span> <strong>Login</strong>
	 			</a>
			</c:if>
		</div>
	</div>
</div>
