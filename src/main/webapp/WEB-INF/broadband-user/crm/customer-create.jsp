<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<jsp:include page="../header.jsp" />
<jsp:include page="../alert.jsp" />

<div class="container">
	<div class="row">
		<div class="col-md-12">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h4 class="panel-title">
						<a data-toggle="collapse" data-toggle="collapse"
							data-parent="#customerInfoAccordion" href="#collapseCustomerInfo">Customer Create</a>
					</h4>
				</div>
				<div id="collapseCustomerInfo" class="panel-collapse collapse in">
					<div class="panel-body">
					
						<!-- customer account -->
						<h4 class="text-success">Create customer account</h4>
						<hr/>
						<form:form modelAttribute="customer" action="${ctx}/broadband-user/crm/customer/create" method="post" id="customerFrom" class="form-horizontal">
							<div class="form-group">
								<label for="customer_name" class="control-label col-md-3">Customer login name</label>
								<div class="col-md-3">
									<form:input path="login_name" class="form-control" placeholder="Login Name" />
								</div>
								<p class="help-block col-md-6">
									<form:errors path="login_name" cssErrorClass="error" />
								</p>
							</div>
							<div class="form-group">
								<label for="password" class="control-label col-md-3">Password</label>
								<div class="col-md-3">
									<form:password path="password" class="form-control" placeholder="Password" />
								</div>
								<p class="help-block col-md-6">
									<form:errors path="password" cssErrorClass="error" />
								</p>
							</div>
							<div class="form-group">
								<label for="ck_password" class="control-label col-md-3">Confirm password</label>
								<div class="col-md-3">
									<form:password path="ck_password" class="form-control" placeholder="Confirm password" />
								</div>
								<p class="help-block">
									<form:errors path="ck_password" cssErrorClass="error"/>
								</p>
							</div>
							
							
							<!-- Personal Information -->
							<hr/>
							<h4 class="text-success">Personal Information</h4>
							<hr/>
							<div class="form-group">
								<label for="first_name" class="control-label col-md-3">First Name</label>
								<div class="col-md-3">
									<form:input path="first_name" class="form-control" placeholder="First Name" />
								</div>
								<p class="help-block col-md-6">
									<form:errors path="first_name" cssErrorClass="error" />
								</p>
							</div>
							<div class="form-group">
								<label for="last_name" class="control-label col-md-3">Last Name</label>
								<div class="col-md-3">
									<form:input path="last_name" class="form-control" placeholder="Last Name" />
								</div>
								<p class="help-block col-md-6">
									<form:errors path="last_name" cssErrorClass="error" />
								</p>
							</div>
							
							<div class="form-group">
								<label for="cellphone" class="control-label col-md-3">Cellphone</label>
								<div class="col-md-3">
									<form:input path="cellphone" class="form-control" placeholder="Cellphone" />
								</div>
								<p class="help-block col-md-6">
									<form:errors path="cellphone" cssErrorClass="error" />
								</p>
							</div>
							<div class="form-group">
								<label for="phone" class="control-label col-md-3">Phone</label>
								<div class="col-md-3">
									<form:input path="phone" class="form-control" placeholder="Phone" />
								</div>
								<p class="help-block col-md-6">
									<form:errors path="phone" cssErrorClass="error" />
								</p>
							</div>
							<div class="form-group">
								<label for="email" class="control-label col-md-3">Email</label>
								<div class="col-md-3">
									<form:input path="email" class="form-control" placeholder="Email" />
								</div>
								<p class="help-block col-md-6">
									<form:errors path="email" cssErrorClass="error" />
								</p>
							</div>
							<div class="form-group">
								<label for="address" class="control-label col-md-3">Address</label>
								<div class="col-md-6">
									<form:input id="address" path="address" class="form-control" placeholder="Address" />
								</div>
								<p class="help-block">
									<form:errors path="address" cssErrorClass="error" />
								</p>
							</div>
							
							<!-- Other options -->
							<hr/>
							<h4 class="text-success">Other Options</h4>
							<hr/>
							<div class="form-group">
								<label for="status" class="control-label col-md-3">Status</label>
								<div class="col-md-3">
									<form:select path="status" class="form-control">
										<%-- <form:option value="verify">verify</form:option> --%>
										<form:option value="active">active</form:option>
										<form:option value="disabled">disabled</form:option>
									</form:select>
								</div>
							</div>
							<hr/>
							<div class="form-group">
								<div class="col-md-3 col-md-offset-3">
									<a  href="javascript:void(0);"class="btn btn-success" id="save">Save</a>
									<a  href="javascript:void(0);"class="btn btn-success" id="next">Next</a>
									<input type="hidden" name="action" id="action"/>
								</div>
							</div>
						</form:form>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<div id="map_canvas" style="width:720px;height:600px;display:none;"></div>
<jsp:include page="../footer.jsp" />
<jsp:include page="../script.jsp" />
<script type="text/javascript">
(function($){
	
	$('#save').click(function(){
		$('#action').val('save');
		$('#customerFrom').submit();
	});
	$('#next').click(function(){

		$('#action').val('next');
		$('#customerFrom').submit();
	});
	
})(jQuery);
</script>
<script src="https://maps.google.com/maps/api/js?sensor=false&libraries=places&region=NZ" type="text/javascript"></script>
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/autoCompleteAddress.js"></script>
<jsp:include page="../footer-end.jsp" />