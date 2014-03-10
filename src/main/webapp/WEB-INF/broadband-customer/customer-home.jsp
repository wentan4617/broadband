<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>  
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<jsp:include page="header.jsp" />
<jsp:include page="alert.jsp" />

<div class="container">
	<div class="row">
		<div class="col-md-3">
			<jsp:include page="sidebar.jsp" />
		</div>
		<div class="col-md-9">
			<div class="panel panel-success">
				<div class="panel-heading">Home</div>
				<div class="panel-body">
					<div class="page-header" style="margin-top: 0;">
						<h3>
							Personal Information 
							<div class="btn-group">
						  		<button type="button" class="btn btn-success dropdown-toggle" data-toggle="dropdown">
							    	Operate <span class="caret"></span>
							  	</button>
							  	<ul class="dropdown-menu" data-role="menu">
							    	<li><a href="javascript:void(0);" >Change Password</a></li>
							  	</ul>
							</div>
						</h3>
					</div>
					<form class="form-horizontal" data-role="form">
						<div class="form-group">
							<label class="col-sm-2 control-label">Login Name:</label>
							<div class="col-sm-4">
								<p class="form-control-static">${customerSession.login_name }</p>
							</div>
							<label class="col-sm-2 control-label">Register Date:</label>
							<div class="col-sm-4">
								<p class="form-control-static">
									<fmt:formatDate  value="${customerSession.register_date }" type="both" pattern="yyyy-MM-dd HH:mm:ss" />
									
								</p>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">Phone:</label>
							<div class="col-sm-4">
								<p class="form-control-static">
									${customerSession.phone }
								</p>
							</div>
							<label class="col-sm-2 control-label">Mobile:</label>
							<div class="col-sm-4">
								<p class="form-control-static">
									${customerSession.cellphone }
								</p>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">Address:</label>
							<div class="col-sm-10">
								<p class="form-control-static">
									${customerSession.address }
								</p>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">Email:</label>
							<div class="col-sm-4">
								<p class="form-control-static">
									${customerSession.email }
								</p>
							</div>
						</div>
					</form>
					<hr />
					<div class="page-header" style="margin-top: 0;">
						<h3>
							Plan Information <small></small>
						</h3>
					</div>
					
					<c:if test="${fn:length(customerOrders) > 0}">
						<c:forEach var="co" items="${customerOrders}">
							<ul class="list-unstyled" style="margin-left:50px;">
								<c:if test="${fn:length(co.customerOrderDetails) > 0 }">
									<c:forEach var="cod" items="${co.customerOrderDetails }">
										<li><h2>${cod.detail_name }</h2></li>
										<li><h2>$ <fmt:formatNumber value="${cod.detail_price }" type="number" pattern="#,#00.00" /></h2></li>
										<li><h2>${cod.detail_data_flow } GB</h2></li>
									</c:forEach>
								</c:if>
								<li>Service Start Date: <h2>${customerOrder.order_using_start }</h2></li>
							</ul>
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