<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>  
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<jsp:include page="header.jsp" />
<jsp:include page="alert.jsp" />

<style>
.personal-info li{
	padding:5px 40px;
}
</style>
<div class="container">
	<div class="row">
		<div class="col-md-3">
			<jsp:include page="sidebar.jsp" />
		</div>
		<div class="col-md-9">
			<div class="panel panel-success">
				<div class="panel-heading">
					<h3 class="panel-title">
						Home
					</h3>
				</div>
				<div class="panel-body">
					<div class="page-header" style="margin-top: 0;">
						<h3 class="text-success">
							Personal Information 
						</h3>
					</div>
					<div class="row">
						<div class="col-md-5">
							<ul class="list-unstyled personal-info">
								<li><strong class="text-info">${customerSession.login_name }</strong></li>
								<li><strong class="text-info">${customerSession.first_name }&nbsp;${customerSession.last_name }</strong></li>
								<li><strong class="text-info"><a href="mailto:#">${customerSession.email }</a></strong></li>
								<li><strong class="text-info">${customerSession.cellphone }</strong></li>
								<li><strong class="text-info">${customerSession.address }</strong></li>
							</ul>
						</div>
						<div class="col-md-7">
							<div class="row">
								<div class="col-md-6">
									<strong class="text-info">
										Current Credit:
									</strong> 
									NZ$ 
									<strong class="text-success">
										<fmt:formatNumber value="${customerSession.balance==null?0:customerSession.balance }" type="number" pattern="#,##0.00" />
										
									</strong>
								</div>
								<div class="col-md-6">
									<div class="btn-group pull-right">
										<button type="button" style="width:120px;" class="btn btn-success  dropdown-toggle" data-toggle="dropdown">
									   		Top Up <span class="caret"></span>
									  	</button>
									  	<ul class="dropdown-menu" data-role="menu">
									    	<li><a href="${ctx }/customer/topup">Credit Card</a></li>
									    	<li><a href="#">Voucher</a></li>
									  	</ul>
									</div>
								</div>
							</div>
							<hr/>
							<div class="row">
								<div class="col-md-6">
									<strong class="text-info">
										Invoice Balance:
									</strong> 
									NZ$ 
									<strong class="text-success">
										<fmt:formatNumber value="${customerSession.customerInvoice.balance==null?0:customerSession.customerInvoice.balance }" type="number" pattern="#,##0.00" />
									</strong>
								</div>
								<div class="col-md-6">
									<a href="${ctx }/customer/billing/1" class="btn btn-success pull-right" style="width:120px;">View Invoice</a>
								</div>
							</div>
						</div>
					</div>
					
					<hr />
					<div class="page-header" style="margin-top: 0;">
						<h3 class="text-success">
							Plan Information 
						</h3>
					</div>
					<c:if test="${fn:length(customerOrders) > 0}">
						<c:forEach var="co" items="${customerOrders}">
							<div class="well">
								<ul class="list-unstyled personal-info">
									<c:if test="${fn:length(co.customerOrderDetails) > 0 }">
										<c:forEach var="cod" items="${co.customerOrderDetails }">
											<c:if test="${fn:contains(cod.detail_type, 'plan-')}">
												<li><hr style="margin:0;"/></li>
												<li><h4 class="text-info" style="margin:0;">${cod.detail_name }</h4></li>
												<li><hr style="margin:0;"/></li>
											</c:if>
											<c:if test="${fn:contains(cod.detail_type, 'hardware-')}">
												<li><hr style="margin:0;"/></li>
												<li>
													<h4 class="text-info" style="margin:0;">
														${cod.detail_name }&nbsp;&nbsp;&nbsp;
														<c:if test="${cod.is_post == 1 }">
															<span class="label label-warning">${cod.track_code }</span>
														</c:if>
														<c:if test="${cod.is_post == 0 }">
															
														</c:if>
													</h4>
												</li>
											</c:if>
										</c:forEach>
									</c:if>
								</ul>
							</div>
						</c:forEach>
					</c:if>
					<c:if test="${fn:length(customerOrders) < 0 }">
					</c:if>
				</div>
			</div>
		</div>
	</div>
</div>


<jsp:include page="footer.jsp" />
<jsp:include page="script.jsp" />
<jsp:include page="footer-end.jsp" />