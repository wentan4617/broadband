<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
					action="${ctx}${action }" class="form-horizontal" id="customer_info_form">
					<form:hidden path="id" />
					<div class="form-group">
						<label for="customer_name" class="control-label col-md-2">Customer Name</label>
						<div class="col-md-3">
							<form:input path="login_name" class="form-control" placeholder="Customer Name" />
						</div>
						<p class="help-block col-md-1">
							<form:errors path="login_name" cssErrorClass="error" />
						</p>
						<label for="password" class="control-label col-md-2">Password</label>
						<div class="col-md-3">
							<form:input path="password" class="form-control" placeholder="Password" />
						</div>
						<p class="help-block col-md-1">
							<form:errors path="password" cssErrorClass="error" />
						</p>
					</div>
					<div class="form-group">
						<label for="status" class="control-label col-md-2">Status</label>
						<div class="col-md-3">
							<form:select path="status" class="form-control">
								<form:option value="verify">verify</form:option>
								<form:option value="active">active</form:option>
								<form:option value="disabled">disabled</form:option>
							</form:select>
						</div>
						<label for="last_name" class="control-label col-md-3">Last Name</label>
						<div class="col-md-3">
							<form:input path="last_name" class="form-control" placeholder="Last Name" />
						</div>
						<p class="help-block col-md-1">
							<form:errors path="last_name" cssErrorClass="error" />
						</p>
					</div>
					<div class="form-group">
						<label for="first_name" class="control-label col-md-2">First Name</label>
						<div class="col-md-3">
							<form:input path="first_name" class="form-control" placeholder="First Name" />
						</div>
						<p class="help-block col-md-1">
							<form:errors path="first_name" cssErrorClass="error" />
						</p>
						<label for="email" class="control-label col-md-2">Email</label>
						<div class="col-md-3">
							<form:input path="email" class="form-control" placeholder="Email" />
						</div>
						<p class="help-block col-md-1">
							<form:errors path="email" cssErrorClass="error" />
						</p>
					</div>
					<div class="form-group">
						<label for="phone" class="control-label col-md-2">Phone</label>
						<div class="col-md-3">
							<form:input path="phone" class="form-control" placeholder="Phone" />
						</div>
						<p class="help-block col-md-1">
							<form:errors path="phone" cssErrorClass="error" />
						</p>
						<label for="register_date" class="control-label col-md-2">Register Date</label>
						<div class="col-md-3">
							<p class="form-control-static"><strong><fmt:formatDate value="${customer.register_date}" type="both"/></strong></p>
						</div>
					</div>
					<div class="form-group">
						<label for="cellphone" class="control-label col-md-2">Cellphone</label>
						<div class="col-md-3">
							<form:input path="cellphone" class="form-control" placeholder="Cellphone" />
						</div>
						<p class="help-block col-md-1">
							<form:errors path="cellphone" cssErrorClass="error" />
						</p>
						<label for="active_date" class="control-label col-md-2">Active Date</label>
						<div class="col-md-3">
							<p class="form-control-static"><strong><fmt:formatDate value="${customer.active_date}" type="both"/></strong></p>
						</div>
					</div>
					<div class="form-group">
						<label for="balance" class="control-label col-md-2">Balance</label>
						<div class="col-md-3">
							<div class="input-group">
								<span class="input-group-addon"><strong>$</strong></span>
								<strong><form:input path="balance" class="form-control" placeholder="" /></strong>
							</div>
						</div>
					</div>
					<div class="form-group">
						<label for="address" class="control-label col-md-2">Address</label>
						<div class="col-md-9">
							<form:input path="address" class="form-control" placeholder="Address" />
						</div>
						<p class="help-block col-md-1">
							<form:errors path="address" cssErrorClass="error" />
						</p>
					</div>
					<div class="form-group">
						<label class="col-md-2"></label>
						<div class="col-md-2 pull-left">
							<a class="btn btn-success" data-toggle="modal" data-target="#saveCustomerModal">Update Customer Info</a>
						</div>
					</div>
				</form:form>
			</div>
		</div>
	</div>
</div>

<!-- Save Customer Modal -->
<div class="modal fade" id="saveCustomerModal" tabindex="-1" role="dialog" aria-labelledby="saveCustomerModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
        <h4 class="modal-title" id="saveCustomerModalLabel"><strong>Edit Customer Information</strong></h4>
      </div>
      <div class="modal-body">
		<div class="form-group">
			<label class="control-label col-md-8">Update customer's info?</label>
		</div>
      </div>
      <div class="modal-footer">
        <a href="javascript:void(0);" class="btn btn-warning" data-name="customer_save" data-dismiss="modal">Update</a>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
