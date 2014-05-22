<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<!-- Customer Basic Details -->

<form id="customerEditForm" class="form-horizontal">
	<h4 class="text-success">Customer Identity</h4>
	<hr/>
	<div class="form-group">
		<label class="control-label col-md-4">Customer ID</label>
		<div class="col-md-4">
			<p class="form-control-static">${customer.id }</p>
		</div>
	</div>
	<div class="form-group">
		<label class="control-label col-md-4">Customer Type</label>
		<div class="col-md-4">
			<p class="form-control-static">${customer.customer_type }</p>
		</div>
	</div>
	
	<!-- personal -->
	<c:if test="${customer.customer_type=='personal'}">
		
		<!-- customer address -->
		<hr/>
		<h4 class="text-success">Customer Address</h4>
		<hr/>
		<div class="form-group">
			<label for="address" class="control-label col-md-4">Customer Address</label>
			<div class="col-md-8">
				<input type="text" id="address" name="address" value="${customer.address }" class="form-control" data-error-field data-placement="top"/>
			</div>
		</div>
		
		<!-- customer account -->
		<hr/>
		<h4 class="text-success">Customer Account</h4>
		<hr/>
		
		<div class="form-group">
			<label for="cellphone" class="control-label col-md-4">Customer Mobile</label>
			<div class="col-md-4">
				<input type="text" id="cellphone" name="cellphone" value="${customer.cellphone }"class="form-control" placeholder="e.g.: 0210800123" data-error-field />
			</div>
		</div>
		<div class="form-group">
			<label for="email" class="control-label col-md-4">Customer Email</label>
			<div class="col-md-4">
				<input type="text" id="email" name="email" value="${customer.email }" class="form-control" placeholder="e.g.: welcome@cyberpark.co.nz" data-error-field/>
			</div>
		</div>
		<div class="form-group">
			<label for="password" class="control-label col-md-4">Customer Password</label>
			<div class="col-md-4">
				<input type="text" id="password" name="password" value="${customer.password }" class="form-control" data-error-field/>
			</div>
		</div>
		
		<!-- Personal Information -->
			
		<hr/>
		<h4 class="text-success">Personal Information</h4>
		<hr/>
		
		<div class="form-group">
			<label for="title" class="control-label col-md-4">Title</label>
			<div class="col-md-2">
				<select name="title" id="title" class="selectpicker show-tick form-control">
					<option value="Mr" ${customer.title=='Mr'?'selected="selected"':'' }>Mr</option>
					<option value="Mrs" ${customer.title=='Mrs'?'selected="selected"':'' }>Mrs</option>
					<option value="Ms" ${customer.title=='Ms'?'selected="selected"':'' }>Ms</option>
				</select>
			</div>
		</div>
		<div class="form-group">
			<label for="first_name" class="control-label col-md-4">First name</label>
			<div class="col-md-4">
				<input type="text" id="first_name" name="first_name" value="${customer.first_name }" class="form-control" data-error-field />
			</div>
		</div>
		<div class="form-group">
			<label for="last_name" class="control-label col-md-4">Last name</label>
			<div class="col-md-4">
				<input type="text" id="last_name" name="last_name" value="${customer.last_name }" class="form-control" data-error-field />
			</div>
		</div>
		
	</c:if>
	
	<!-- business -->
	<c:if test="${customer.customer_type=='business'}">
	
		<!-- customer address -->
		<hr/>
		<h4 class="text-success">Group/Organization Address</h4>
		<hr/>
		
		<div class="form-group">
			<label for="address" class="control-label col-md-4">Business Address</label>
			<div class="col-md-8">
				<input type="text" id="address" name="address" value="${customer.address }" class="form-control" data-error-field data-placement="top"/>
			</div>
		</div>
		
		<!-- customer account -->
		<hr/>
		<h4 class="text-success">Business Account</h4>
		<hr/>
		
		<div class="form-group">
			<label for="cellphone" class="control-label col-md-4">Business Mobile</label>
			<div class="col-md-4">
				<input type="text" id="cellphone" name="cellphone" value="${customer.cellphone }"class="form-control" placeholder="e.g.: 0210800123" data-error-field />
			</div>
		</div>
		<div class="form-group">
			<label for="email" class="control-label col-md-4">Business Email</label>
			<div class="col-md-4">
				<input type="text" id="email" name="email" value="${customer.email }" class="form-control" placeholder="e.g.: welcome@cyberpark.co.nz" data-error-field/>
			</div>
		</div>
		<div class="form-group">
			<label for="password" class="control-label col-md-4">Business Password</label>
			<div class="col-md-4">
				<input type="text" id="password" name="password" value="${customer.password }" class="form-control" data-error-field/>
			</div>
		</div>
		
		<!-- Group/Organization Information -->
			
		<hr/>
		<h4 class="text-success">Group/Organization Information</h4>
		<hr/>
		
		<div class="form-group">
			<label for="organization.org_type" class="control-label col-md-4">Group/Organization Type</label>
			<div class="col-md-5">
				<select name="org_type" id="organization.org_type" class="selectpicker show-tick form-control" data-error-field>
					<option value="NZ Incorporated Company" ${customer.organization.org_type=='NZ Incorporated Company'?'selected="selected"':''}>NZ Incorporated Company</option>
					<option value="Limited Partnership" ${customer.organization.org_type=='Limited Partnership'?'selected="selected"':''}>Limited Partnership</option>
					<option value="Sole Trader" ${customer.organization.org_type=='Sole Trader'?'selected="selected"':''}>Sole Trader</option>
					<option value="Partnership" ${customer.organization.org_type=='Partnership'?'selected="selected"':''}>Partnership</option>
					<option value="Trust" ${customer.organization.org_type=='Trust'?'selected="selected"':''}>Trust</option>
					<option value="Overseas Company" ${customer.organization.org_type=='Overseas Company'?'selected="selected"':''}>Overseas Company</option>
					<option value="Society" ${customer.organization.org_type=='Society'?'selected="selected"':''}>Society</option>
				</select>
			</div>
		</div>
		<div class="form-group">
			<label for="organization.org_name" class="control-label col-md-4">Group/Organization Name</label>
			<div class="col-md-5">
				<input type="text" name="org_name" id="organization.org_name" value="${customer.organization.org_name }" class="form-control" data-error-field/>
			</div>
		</div>
		<div class="form-group">
			<label for="organization.org_trading_name" class="control-label col-md-4">Trading Name</label>
			<div class="col-md-5">
				<input type="text" name="org_trading_name" id="organization.org_trading_name" value="${customer.organization.org_trading_name }" class="form-control" data-error-field/>
			</div>
		</div>
		<div class="form-group">
			<label for="organization.org_register_no" class="control-label col-md-4">Registration No.</label>
			<div class="col-md-5">
				<input type="text" name="org_register_no" id="organization.org_register_no" value="${customer.organization.org_register_no }" class="form-control" data-error-field/>
			</div>
		</div>
		<div class="form-group">
			<label for="organization.org_incoporate_date" class="control-label col-md-4">Date Incorporated</label>
			<div class="col-md-4">
				<div class="input-group date">
			  		<input type="text" id="organization.org_incoporate_date" name="org_incoporate_date" 
			  			value="<fmt:formatDate  value="${customer.organization.org_incoporate_date }" type="both" pattern="yyyy-MM-dd" />" class="form-control" class="form-control"  data-error-field />
			  		<span class="input-group-addon">
			  			<i class="glyphicon glyphicon-calendar"></i>
			  		</span>
				</div>
			</div>
		</div>
		
		<!-- Contract Details -->
			
		<hr/>
		<h4 class="text-success">Contract Details / Holder Account</h4>
		<hr/>
		
		<div class="form-group">
			<label for="organization.holder_name" class="control-label col-md-4">Full name</label>
			<div class="col-md-5">
				<input type="text" name="holder_name" id="organization.holder_name" value="${customer.organization.holder_name }" class="form-control"  data-error-field/>
			</div>
		</div>
		<div class="form-group">
			<label for="organization.holder_job_title" class="control-label col-md-4">Job title</label>
			<div class="col-md-5">
				<input type="text" name="holder_job_title" id="organization.holder_job_title" value="${customer.organization.holder_job_title }" class="form-control" data-error-field/>
			</div>
		</div>
		<div class="form-group">
			<label for="organization.holder_phone" class="control-label col-md-4">Phone Number</label>
			<div class="col-md-5">
				<input type="text" name="holder_phone" id="organization.holder_phone" value="${customer.organization.holder_phone }" class="form-control" data-error-field/>
			</div>
		</div>
		<div class="form-group">
			<label for="organization.holder_email" class="control-label col-md-4">Email Address</label>
			<div class="col-md-5">
				<input type="text" name="holder_email" id="organization.holder_email" value="${customer.organization.holder_email }" class="form-control" data-error-field/>
			</div>
		</div>
			
	</c:if>
	
	<!-- Other Information -->
	<hr/>
	<h4 class="text-success">Other Information</h4>
	<hr/>
	
	<div class="form-group">
		<label for="balance" class="control-label col-md-4">Credit</label>
		<div class="col-md-4">
			<input type="text" id="balance" name="balance" value="<fmt:formatNumber value="${customer.balance }" type="number" pattern="#,##0.00" />" class="form-control" data-error-field />
		</div>
	</div>
	<div class="form-group">
		<label class="control-label col-md-4">Register Date</label>
		<div class="col-md-4">
			<p class="form-control-static"><strong><fmt:formatDate value="${customer.register_date}" type="both"/></strong></p>
		</div>
	</div>
	
	<!-- Other options -->
	<hr/>
	<h4 class="text-success">Other Options</h4>
	<hr/>
	
	<div class="form-group">
		<label for="status" class="control-label col-md-4">Status</label>
		<div class="col-md-4">
			<select id="status" name="status" class="selectpicker show-tick form-control">
				<option value="active" ${customer.status=='active'?'selected="selected"':'' }>Active</option>
				<option value="disabled" ${customer.status=='disabled'?'selected="selected"':'' }>Disabled</option>
			</select>
		</div>
	</div>
	
	<!-- button -->
	<hr/>
	<div class="form-group">
		<div class="col-md-4"></div>
		<div class="col-md-3">
			<a href="javascript:void(0);" class="btn btn-success btn-lg btn-block" id="updateCustomer" >Update Customer</a>
		</div>
		<div class="col-md-4">
			<a class="btn btn-danger  btn-lg btn-block" data-toggle="modal" data-target="#deleteCustomerModal">Delete customer and related information</a>
		</div>
	</div>
	
</form>

<!-- Delete Customer Modal -->
<form class="form-horizontal">
	<div class="modal fade" id="deleteCustomerModal" tabindex="-1" role="dialog" aria-labelledby="deleteCustomerModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h3 class="modal-title" id="deleteCustomerModalLabel">
						<strong class="text-danger">Delete Customer ( Permanent!!! )</strong>
					</h3>
				</div>
				<div class="modal-body">
					<p class="text-right">
						<strong class="text-danger">
							This operation is irreversible, be seriously careful!<br /> 
							Info related with this customer will be disappeared!<br />
						</strong> 
					</p>
					<p class="text-right">
						<strong>
							personal informations<br /> 
							related organization's details<br />
							related orders with records<br />
							related invoices with records<br /> 
							related transaction's records<br />
						</strong>
					</p>
				</div>
				<div class="modal-footer">
					<a href="${ctx}/broadband-user/crm/customer/remove/${customer.id}" class="btn btn-warning btn-lg">Delete</a>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- /.modal -->
</form>


