<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<!-- Customer Basic Details -->

<div class="panel-group" id="customerInfoAccordion">
	<div class="panel panel-default">
		<div class="panel-heading">
			<h4 class="panel-title">
				<a data-toggle="collapse" data-toggle="collapse"
					data-parent="#customerInfoAccordion" href="#collapseCustomerInfo">${panelheading }</a>
			</h4>
		</div>
		<div id="collapseCustomerInfo" class="panel-collapse collapse in">
			<div class="panel-body">
				<form:form modelAttribute="customer" method="post"
					action="${ctx}${action }" class="form-horizontal">
					<form:hidden path="id" />
					<div class="form-group">
						<label for="customer_name" class="control-label col-md-3">Customer
							Name</label>
						<div class="col-md-3">
							<form:input path="login_name" class="form-control"
								placeholder="Customer Name" />
						</div>
						<p class="help-block">
							<form:errors path="login_name" cssErrorClass="error" />
						</p>
					</div>
					<div class="form-group">
						<label for="password" class="control-label col-md-3">Password</label>
						<div class="col-md-3">
							<form:input path="password" class="form-control"
								placeholder="Password" />
						</div>
						<p class="help-block">
							<form:errors path="password" cssErrorClass="error" />
						</p>
					</div>
					<div class="form-group">
						<label for="status" class="control-label col-md-3">Status</label>
						<div class="col-md-3">
							<form:select path="status" class="form-control">
								<form:option value="verify">verify</form:option>
								<form:option value="active">active</form:option>
								<form:option value="disabled">disabled</form:option>
							</form:select>
						</div>
					</div>
					<div class="form-group">
						<label for="last_name" class="control-label col-md-3">Last
							Name</label>
						<div class="col-md-3">
							<form:input path="last_name" class="form-control"
								placeholder="Last Name" />
						</div>
						<p class="help-block">
							<form:errors path="last_name" cssErrorClass="error" />
						</p>
					</div>
					<div class="form-group">
						<label for="first_name" class="control-label col-md-3">First
							Name</label>
						<div class="col-md-3">
							<form:input path="first_name" class="form-control"
								placeholder="First Name" />
						</div>
						<p class="help-block">
							<form:errors path="first_name" cssErrorClass="error" />
						</p>
					</div>
					<div class="form-group">
						<label for="address" class="control-label col-md-3">Address</label>
						<div class="col-md-3">
							<form:input path="address" class="form-control"
								placeholder="Address" />
						</div>
						<p class="help-block">
							<form:errors path="address" cssErrorClass="error" />
						</p>
					</div>
					<div class="form-group">
						<label for="email" class="control-label col-md-3">Email</label>
						<div class="col-md-3">
							<form:input path="email" class="form-control" placeholder="Email" />
						</div>
						<p class="help-block">
							<form:errors path="email" cssErrorClass="error" />
						</p>
					</div>
					<div class="form-group">
						<label for="phone" class="control-label col-md-3">Phone</label>
						<div class="col-md-3">
							<form:input path="phone" class="form-control" placeholder="Phone" />
						</div>
						<p class="help-block">
							<form:errors path="phone" cssErrorClass="error" />
						</p>
					</div>
					<div class="form-group">
						<label for="cellphone" class="control-label col-md-3">Cellphone</label>
						<div class="col-md-3">
							<form:input path="cellphone" class="form-control"
								placeholder="Cellphone" />
						</div>
						<p class="help-block">
							<form:errors path="cellphone" cssErrorClass="error" />
						</p>
					</div>
					<div class="form-group">
						<label for="register_date" class="control-label col-md-3">Register
							Date</label>
						<div class="col-md-3">
							<form:input path="register_date_str" class="form-control"
								placeholder="Register Date" />
						</div>
						<p class="help-block">
							<form:errors path="register_date_str" cssErrorClass="error" />
						</p>
					</div>
					<div class="form-group">
						<label for="active_date" class="control-label col-md-3">Active
							Date</label>
						<div class="col-md-3">
							<form:input path="active_date_str" class="form-control"
								placeholder="Active Date" />
						</div>
						<p class="help-block">
							<form:errors path="active_date_str" cssErrorClass="error" />
						</p>
					</div>
					<div class="form-group">
						<label for="balance" class="control-label col-md-3">Balance</label>
						<div class="col-md-3">
							<div class="input-group">
								<span class="input-group-addon">$</span>
								<form:input path="balance" class="form-control" placeholder="" />
							</div>
						</div>
						<p class="help-block">
							<form:errors path="balance" cssErrorClass="error" />
						</p>
					</div>
					<div class="form-group">
						<div class="col-md-3 col-md-offset-3">
							<button type="submit" class="btn btn-success">Save</button>
						</div>
					</div>
				</form:form>
			</div>
		</div>
	</div>
</div>