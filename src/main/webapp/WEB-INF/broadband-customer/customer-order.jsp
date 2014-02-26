<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<jsp:include page="header.jsp" />


<div class="container">
	<div class="page-header">
		<h1>
			Customer Information <small>Please fill in your personal information, we will contact you</small>
		</h1>
	</div>
	
	<div class="panel panel-default">
		<div class="panel-heading">Your application form <span class="text-danger">(All fields Required)</span></div>
		<div class="panel-body">
			
			<form:form modelAttribute="customer" method="post" action="${ctx}/order" class="form-horizontal">
				<h4>Account Login Name and Password </h4>
				<hr/>
				<div class="form-group">
					<label for="login_name" class="control-label col-md-3">Your login name </label>
					<div class="col-md-3">
						<form:input path="login_name" class="form-control" placeholder="e.g.: cyberpark" />
					</div>
					<p class="help-block">
						<form:errors path="login_name" cssErrorClass="error"/>
					</p>
				</div>
				<div class="form-group">
					<label for="password" class="control-label col-md-3">Password</label>
					<div class="col-md-3">
						<form:password path="password" class="form-control" placeholder="" />
					</div>
					<p class="help-block">
						<form:errors path="password" cssErrorClass="error"/>
					</p>
				</div>
				<div class="form-group">
					<label for="ck_password" class="control-label col-md-3">Confirm your password</label>
					<div class="col-md-3">
						<form:password path="ck_password" class="form-control" placeholder="" />
					</div>
					<p class="help-block">
						<form:errors path="ck_password" cssErrorClass="error"/>
					</p>
				</div>
				<hr/>
				<h4>Personal Information</h4>
				<hr/>
				<div class="form-group">
					<label for="first_name" class="control-label col-md-3">First name</label>
					<div class="col-md-3">
						<form:input path="first_name" class="form-control" placeholder="" />
					</div>
					<p class="help-block">
						<form:errors path="first_name" cssErrorClass="error"/>
					</p>
				</div>
				<div class="form-group">
					<label for="last_name" class="control-label col-md-3">Last name</label>
					<div class="col-md-3">
						<form:input path="last_name" class="form-control" placeholder="" />
					</div>
					<p class="help-block">
						<form:errors path="last_name" cssErrorClass="error"/>
					</p>
				</div>
				<div class="form-group">
					<label for="email" class="control-label col-md-3">Your Email</label>
					<div class="col-md-3">
						<form:input path="email" class="form-control" placeholder="" />
					</div>
					<p class="help-block">
						<form:errors path="email" cssErrorClass="error"/>
					</p>
				</div>
				
				<div class="form-group">
					<label for="cellphone" class="control-label col-md-3">Your Phone</label>
					<div class="col-md-3">
						<form:input path="cellphone" class="form-control" placeholder="" />
					</div>
					<p class="help-block">
						<form:errors path="cellphone" cssErrorClass="error"/>
					</p>
				</div>
				<div class="form-group">
					<label for="address" class="control-label col-md-3">Your Address</label>
					<div class="col-md-6">
						<form:input path="address" class="form-control" placeholder="" />
					</div>
					<p class="help-block">
						<form:errors path="address" cssErrorClass="error"/>
					</p>
				</div>
				<hr/>
				<div class="form-group">
					<div class="col-md-3 col-md-offset-3">
						<a href="${ctx }/plans/${fn:toLowerCase(orderPlan.plan_group=='plan-topup'?'t':'p')}" class="btn btn-success">Back to choose plans</a>&nbsp;
						<button type="submit" class="btn btn-success">Continue</button>
					</div>
				</div>
			</form:form>
		</div>
	</div>
	
	<!-- plan show -->
	<div class="panel panel-default">
  		<div class="panel-body">
	   		<div class="media">
			  	<a class="pull-left" href="#">
			    	<img class="media-object" data-src="holder.js/350x250" alt="...">
			  	</a>
		  		<div class="media-body">
		    		<h4 class="media-heading text-info">Your current purchase of plan</h4>
		    		${orderPlan.plan_desc }
		  		</div>
			</div>
  		</div>
	</div>
</div>


<jsp:include page="footer.jsp" />
<jsp:include page="script.jsp" />
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/holder.js"></script>
<jsp:include page="footer-end.jsp" />