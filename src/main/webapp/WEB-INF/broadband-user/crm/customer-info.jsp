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
						<div class="col-md-6">
							<div class="form-group">
								<label for="title" class="control-label col-md-4">Title</label>
								<div class="col-md-6">
									<form:input path="title" class="form-control" placeholder="Title" />
								</div>
							</div>
						</div>
						<c:if test="${customer.customer_type=='personal'}">
							<div class="col-md-6">
								<div class="form-group">
									<label for="customer_birth_datepicker" class="control-label col-md-4">Birth</label>
									<div class="col-md-6">
										<div class="input-group date" id="customer_birth_datepicker">
											<strong><form:input path="birth_str" data-val="${customer.birth_str}" data-name="customer_birth_input" class="form-control input-sm" placeholder="Birth"/></strong>
											<span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
										</div>
									</div>
								</div>
							</div>
							<div class="col-md-6">
								<div class="form-group">
									<label for="driver_licence" class="control-label col-md-4">Driver License</label>
									<div class="col-md-6">
										<form:input path="driver_licence" class="form-control" placeholder="Driver License" />
									</div>
								</div>
							</div>
							<div class="col-md-6">
								<div class="form-group">
									<label for="passport" class="control-label col-md-4">Passport</label>
									<div class="col-md-6">
										<form:input path="passport" class="form-control" placeholder="Passport" />
									</div>
								</div>
							</div>
							<div class="col-md-6">
								<div class="form-group">
									<label for="country" class="control-label col-md-4">Country</label>
									<div class="col-md-6">
										<form:input path="country" class="form-control" placeholder="Country" />
									</div>
								</div>
							</div>
						</c:if>
						<div class="col-md-6">
							<div class="form-group">
								<label for="company_name" class="control-label col-md-4">Company Name</label>
								<div class="col-md-6">
									<form:input path="company_name" class="form-control" placeholder="Company Name" />
								</div>
							</div>
						</div>
						<div class="col-md-6">
							<div class="form-group">
								<label for="customer_type" class="control-label col-md-4">Customer Type</label>
								<div class="col-md-6">
									<form:select path="customer_type" class="form-control">
										<c:forEach var="type" items="personal,business">
											<option value="${type}"
												<c:if test='${customer.customer_type == type}'>
													 selected='selected'
												</c:if>
											>${type}</option>
										</c:forEach>
									</form:select>
								</div>
							</div>
						</div>
						<div class="col-md-6">
							<div class="form-group">
								<label for="login_name" class="control-label col-md-4">Customer Name</label>
								<div class="col-md-6">
									<form:input path="login_name" class="form-control" placeholder="Customer Name" />
								</div>
							</div>
						</div>
						<div class="col-md-6">
							<div class="form-group">
								<label for="password" class="control-label col-md-4">Password</label>
								<div class="col-md-6">
									<form:input path="password" class="form-control" placeholder="Password" />
								</div>
								<p class="help-block col-md-2">
									<form:errors path="password" cssErrorClass="error" />
								</p>
							</div>
						</div>
						<div class="col-md-6">
							<div class="form-group">
								<label for="status" class="control-label col-md-4">Status</label>
								<div class="col-md-6">
									<form:select path="status" class="form-control">
									<c:forEach var="status" items="verify,active,disabled">
										<option value="${status }"
											<c:if test='${customer.status == status}'>
												 selected='selected'
											</c:if>
										>${status }</option>
									</c:forEach>
									</form:select>
								</div>
							</div>
						</div>
						<div class="col-md-6">
							<div class="form-group">
								<label for="last_name" class="control-label col-md-4">Last Name</label>
								<div class="col-md-6">
									<form:input path="last_name" class="form-control" placeholder="Last Name" />
								</div>
								<p class="help-block col-md-2">
									<form:errors path="last_name" cssErrorClass="error" />
								</p>
							</div>
						</div>
						<div class="col-md-6">
							<div class="form-group">
								<label for="first_name" class="control-label col-md-4">First Name</label>
								<div class="col-md-6">
									<form:input path="first_name" class="form-control" placeholder="First Name" />
								</div>
								<p class="help-block col-md-2">
									<form:errors path="first_name" cssErrorClass="error" />
								</p>
							</div>
						</div>
						<div class="col-md-6">
							<div class="form-group">
								<label for="email" class="control-label col-md-4">Email</label>
								<div class="col-md-6">
									<form:input path="email" class="form-control" placeholder="Email" />
								</div>
								<p class="help-block col-md-2">
									<form:errors path="email" cssErrorClass="error" />
								</p>
							</div>
						</div>
						<div class="col-md-6">
							<div class="form-group">
								<label for="phone" class="control-label col-md-4">Phone</label>
								<div class="col-md-6">
									<form:input path="phone" class="form-control" placeholder="Phone" />
								</div>
							</div>
						</div>
						<div class="col-md-6">
							<div class="form-group">
								<label for="register_date" class="control-label col-md-4">Register Date</label>
								<div class="col-md-6">
									<p class="form-control-static"><strong><fmt:formatDate value="${customer.register_date}" type="both"/></strong></p>
								</div>
								<p class="help-block col-md-2">
									<form:errors path="cellphone" cssErrorClass="error" />
								</p>
							</div>
						</div>
						<div class="col-md-6">
							<div class="form-group">
								<label for="active_date" class="control-label col-md-4">Active Date</label>
								<div class="col-md-6">
									<p class="form-control-static"><strong><fmt:formatDate value="${customer.active_date}" type="both"/></strong></p>
								</div>
							</div>
						</div>
						<div class="col-md-6">
							<div class="form-group">
								<label for="cellphone" class="control-label col-md-4">Cellphone</label>
								<div class="col-md-6">
									<form:input path="cellphone" class="form-control" placeholder="Cellphone" />
								</div>
								<p class="help-block col-md-2">
									<form:errors path="cellphone" cssErrorClass="error" />
								</p>
							</div>
						</div>
						<div class="col-md-6">
							<div class="form-group">
								<label for="balance" class="control-label col-md-4">Balance</label>
								<div class="col-md-6">
									<div class="input-group">
										<span class="input-group-addon"><strong>$</strong></span>
										<strong><form:input path="balance" class="form-control" placeholder="" /></strong>
									</div>
								</div>
							</div>
						</div>
						<div class="col-md-12">
							<div class="form-group">
								<label for="address" class="control-label col-md-2">Address</label>
								<div class="col-md-9">
									<form:input path="address" class="form-control" placeholder="Address" />
								</div>
								<p class="help-block col-md-1">
									<form:errors path="address" cssErrorClass="error" />
								</p>
							</div>
						<hr/>
						</div>
						<!-- Organization Detail -->
						<c:if test="${customer.customer_type=='business'}">
							<div class="page-header" style="margin:0">
								<h3 class="text-success"><strong>Organization Information</strong></h3>
							</div><br/>
							<div class="col-md-6">
								<div class="form-group">
									<label for="organization.org_name" class="control-label col-md-4">Organization Name</label>
									<div class="col-md-6">
										<form:input path="organization.org_name" class="form-control" placeholder="Organization Name" />
									</div>
								</div>
							</div>
							<div class="col-md-6">
								<div class="form-group">
									<label for="organization.org_type" class="control-label col-md-4">Organization Type</label>
									<div class="col-md-6">
										<form:input path="organization.org_type" class="form-control" placeholder="Organization Type" />
									</div>
								</div>
							</div>
							<div class="col-md-6">
								<div class="form-group">
									<label for="organization.org_trading_name" class="control-label col-md-4">Trading Name</label>
									<div class="col-md-6">
										<form:input path="organization.org_trading_name" class="form-control" placeholder="Trading Name" />
									</div>
								</div>
							</div>
							<div class="col-md-6">
								<div class="form-group">
									<label for="organization.org_register_no" class="control-label col-md-4">Register No.</label>
									<div class="col-md-6">
										<form:input path="organization.org_register_no" class="form-control" placeholder="Register No." />
									</div>
								</div>
							</div>
							<div class="col-md-6">
								<div class="form-group">
									<label for="organization.org_incoporate_date_str" class="control-label col-md-4">Incoporate Date</label>
									<div class="col-md-6">
										<div class="input-group date" id="incoporate_date_datepicker">
											<strong><form:input path="organization.org_incoporate_date_str" data-val="${customer.organization.org_incoporate_date_str}" data-name="incoporate_date_input" class="form-control input-sm" placeholder="Incoporate Date"/></strong>
											<span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
										</div>
									</div>
								</div>
							</div>
							<div class="col-md-6">
								<div class="form-group">
									<label for="organization.org_trading_months" class="control-label col-md-4">Trading Months</label>
									<div class="col-md-6">
										<form:input path="organization.org_trading_months" class="form-control" placeholder="Trading Months" />
									</div>
								</div>
							</div>
							<div class="col-md-6">
								<div class="form-group">
									<label for="organization.holder_name" class="control-label col-md-4">Holder Name</label>
									<div class="col-md-6">
										<form:input path="organization.holder_name" class="form-control" placeholder="Holder Name" />
									</div>
								</div>
							</div>
							<div class="col-md-6">
								<div class="form-group">
									<label for="organization.holder_job_title" class="control-label col-md-4">Holder Job Title</label>
									<div class="col-md-6">
										<form:input path="organization.holder_job_title" class="form-control" placeholder="Holder Job Title" />
									</div>
								</div>
							</div>
							<div class="col-md-6">
								<div class="form-group">
									<label for="organization.holder_phone" class="control-label col-md-4">Holder Phone</label>
									<div class="col-md-6">
										<form:input path="organization.holder_phone" class="form-control" placeholder="Holder Phone" />
									</div>
								</div>
							</div>
							<div class="col-md-6">
								<div class="form-group">
									<label for="organization.holder_email" class="control-label col-md-4">Holder E-mail</label>
									<div class="col-md-6">
										<form:input path="organization.holder_email" class="form-control" placeholder="Holder E-mail" />
									</div>
								</div>
							</div>
						</c:if>
						<div class="col-md-12">
							<div class="form-group">
								<label class="col-md-2"></label>
								<div class="col-md-2 pull-left">
									<a class="btn btn-success" data-toggle="modal" data-target="#saveCustomerModal">Update Customer Info</a>
								</div>
								<div class="col-md-2">
									<a class="btn btn-danger" data-toggle="modal" data-target="#deleteCustomerModal">Delete customer and related information</a>
								</div>
							</div>
						</div>
				</form:form>
			</div>
		</div>
	</div>
</div>


<!-- Delete Customer Modal -->
<form class="form-horizontal">
	<div class="modal fade" id="deleteCustomerModal" tabindex="-1" role="dialog" aria-labelledby="deleteCustomerModalLabel" aria-hidden="true">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
	        <h3 class="modal-title" id="deleteCustomerModalLabel"><strong class="text-danger">Delete Customer Permanently!</strong></h3>
	      </div>
	      <div class="modal-body">
			<div class="form-group">
				<label class="control-label col-md-11">
					<strong class="text-danger">This operation is irreversible, be careful!<br/>
					Info listed below will be disappear permanently!:<br/></strong>
					personal informations&nbsp;(1)<br/>
					related orders with details&nbsp;(2)<br/>
					related invoices with details&nbsp;(3)<br/>
					related transaction's records&nbsp;(4)<br/>
				</label>
			</div>
	      </div>
	      <div class="modal-footer">
	        <a href="${ctx}/broadband-user/crm/customer/delete" class="btn btn-warning">Delete</a>
	      </div>
	    </div><!-- /.modal-content -->
	  </div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
</form>


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
