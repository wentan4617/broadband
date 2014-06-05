<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %> 
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<jsp:include page="header.jsp" />
<jsp:include page="../alert.jsp" />

<style>
.topup-list li {
	padding: 10px 20px;
}
</style>

<div class="container">
	<div class="page-header">
		<h1>
			2. Customer Information 
		</h1>
	</div>
	
	<div class="panel panel-success">
		<div class="panel-heading">
			<h2 class="panel-title">
				<a data-toggle="collapse" data-toggle="collapse"  href="#application">
					Business Application Form <span class="text-danger"></span>
				</a>
			</h2>
		</div>
		<div id="application" class="panel-collapse collapse in">
			<div class="panel-body">
				<form id="customerInfoFrom" class="form-horizontal">
				
					<!-- customer address -->
					<h4 class="text-success">Group/Organization Address</h4>
					<hr/>
					
					<div class="form-group">
						<label for="address" class="control-label col-sm-4">Business Address</label>
						<div class="col-sm-8">
							<c:choose>
								<c:when test="${orderCustomer.address!=null}">
									<p class="form-control-static">${orderCustomer.address }</p>
									<input type="hidden" id="address" name="address" value="${orderCustomer.address }" />
								</c:when>
								<c:otherwise>
									<input type="text" id="address" name="address" value="${orderCustomer.address }" class="form-control" data-error-field data-placement="top"/>
								</c:otherwise>
							</c:choose>
							
						</div>
					</div>
						
					<!-- customer account -->
					<hr/>
					<h4 class="text-success">Create Your Account <small>(use your mobile/email when login account)</small></h4>
					<hr/>
					
					<div class="form-group">
						<label for="cellphone" class="control-label col-sm-4">Business Mobile</label>
						<div class="col-sm-4">
							<input type="text" id="cellphone" name="cellphone" value="${orderCustomer.cellphone }"class="form-control" placeholder="e.g.: 0210800123" data-error-field />
						</div>
					</div>
					<div class="form-group">
						<label for="email" class="control-label col-sm-4">Business Email</label>
						<div class="col-sm-4">
							<input type="text" id="email" name="email" value="${orderCustomer.email }" class="form-control" placeholder="e.g.: welcome@cyberpark.co.nz" data-error-field/>
						</div>
					</div>

					<!-- Broadband Options -->
					<hr/>
					<h4 class="text-success">Broadband Options</h4>
					<hr/>
					
					<div class="form-group">
						<div class="col-sm-12">
							<ul class="list-unstyled topup-list">
								<li>
									<input type="radio" name="order_broadband_type" value="transition"
										<c:if test="${orderCustomer.customerOrder.order_broadband_type=='transition' || customer.customerOrder.order_broadband_type==null}">
											checked="checked"
										</c:if> />
									&nbsp; 
									<strong>
										Transfer the existing broadband connection to CyberPark 
										<c:choose>
											<c:when test="${orderPlan.transition_fee > 0 }">
												costs NZ$ <fmt:formatNumber value="${orderPlan.transition_fee }" type="number" pattern="#,##0" />
											</c:when>
											<c:otherwise>
												is free
											</c:otherwise>
										</c:choose>
									</strong>
								</li>
								<li>
									<input type="radio" name="order_broadband_type" 
										${orderCustomer.customerOrder.order_broadband_type=='new-connection'?'checked="checked"':'' } value="new-connection"/>
									&nbsp; <strong>Get a new broadband connection on an existing (but inactive) jackpot charge NZ$ <fmt:formatNumber value="${orderPlan.plan_new_connection_fee }" type="number" pattern="#,##0" /></strong>
								</li>
								<li>
									<input type="radio" name="order_broadband_type" 
										${orderCustomer.customerOrder.order_broadband_type=='jackpot'?'checked="checked"':'' } value="jackpot"/>
									&nbsp; <strong>Get a new broadband connection and an new jackpot installation and activation charge NZ$ <fmt:formatNumber value="${orderPlan.jackpot_fee }" type="number" pattern="#,##0" /></strong>
								</li>
							</ul>
						</div>
					</div>
					
					<!-- Transition Information -->
					<div id="transitionContainer" >
					
						<hr/>
						<h4 class="text-success">Transition Information</h4>
						<hr/>
						
						<div class="form-group">
							<label  class="control-label col-sm-4">Current Provider</label>
							<div class="col-sm-4">
								<input type="text" id="customerOrder.transition_provider_name" name="customerOrder.transition_provider_name" value="${customer.customerOrder.transition_provider_name }" class="form-control"/>
							</div>
						</div>
						<div class="form-group">
							<label  class="control-label col-sm-4">Account Holder</label>
							<div class="col-sm-4">
								<input type="text" id="customerOrder.transition_account_holder_name" name="customerOrder.transition_account_holder_name" value="${customer.customerOrder.transition_account_holder_name }" class="form-control" />
							</div>
						</div>
						<div class="form-group">
							<label  class="control-label col-sm-4">Current Account</label>
							<div class="col-sm-4">
								<input type="text" id="customerOrder.transition_account_number" name="customerOrder.transition_account_number" value="${customer.customerOrder.transition_account_number }" class="form-control" />
							</div>
						</div>
						<div class="form-group">
							<label  class="control-label col-sm-4">Telephone Number</label>
							<div class="col-sm-4">
								<input type="text" id="customerOrder.transition_porting_number" name="customerOrder.transition_porting_number" value="${customer.customerOrder.transition_porting_number }" class="form-control" />
							</div>
						</div>
					
					</div>


					<!-- Group/Organization Information -->
						
					<hr/>
					<h4 class="text-success">Group/Organization Information</h4>
					<hr/>
					
					<div class="form-group">
						<label for="organization.org_type" class="control-label col-sm-4">Group/Organization Type</label>
						<div class="col-sm-6">
							<select name="org_type" id="organization.org_type" class="selectpicker show-tick form-control" data-error-field>
								<option value="NZ Incorporated Company" ${orderCustomer.organization.org_type=='NZ Incorporated Company'?'selected="selected"':''}>NZ Incorporated Company</option>
								<option value="Limited Partnership" ${orderCustomer.organization.org_type=='Limited Partnership'?'selected="selected"':''}>Limited Partnership</option>
								<option value="Sole Trader" ${orderCustomer.organization.org_type=='Sole Trader'?'selected="selected"':''}>Sole Trader</option>
								<option value="Partnership" ${orderCustomer.organization.org_type=='Partnership'?'selected="selected"':''}>Partnership</option>
								<option value="Trust" ${orderCustomer.organization.org_type=='Trust'?'selected="selected"':''}>Trust</option>
								<option value="Overseas Company" ${orderCustomer.organization.org_type=='Overseas Company'?'selected="selected"':''}>Overseas Company</option>
								<option value="Society" ${orderCustomer.organization.org_type=='Society'?'selected="selected"':''}>Society</option>
							</select>
						</div>
					</div>
					<div class="form-group">
						<label for="organization.org_name" class="control-label col-sm-4">Group/Organization Name</label>
						<div class="col-sm-6">
							<input type="text" name="org_name" id="organization.org_name" value="${orderCustomer.organization.org_name }" class="form-control" data-error-field/>
						</div>
					</div>
					<div class="form-group">
						<label for="organization.org_trading_name" class="control-label col-sm-4">Trading Name</label>
						<div class="col-sm-6">
							<input type="text" name="org_trading_name" id="organization.org_trading_name" value="${orderCustomer.organization.org_trading_name }" class="form-control" data-error-field/>
						</div>
					</div>
					<div class="form-group">
						<label for="organization.org_register_no" class="control-label col-sm-4">Registration No.</label>
						<div class="col-sm-6">
							<input type="text" name="org_register_no" id="organization.org_register_no" value="${orderCustomer.organization.org_register_no }" class="form-control" data-error-field/>
						</div>
					</div>
					<div class="form-group">
						<label for="organization.org_incoporate_date" class="control-label col-sm-4">Date Incorporated</label>
						<div class="col-sm-4">
							<div class="input-group date">
						  		<input type="text" id="organization.org_incoporate_date" name="org_incoporate_date" 
						  			value="<fmt:formatDate  value="${orderCustomer.organization.org_incoporate_date }" type="both" pattern="yyyy-MM-dd" />" class="form-control"  data-error-field />
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
						<label for="organization.holder_name" class="control-label col-sm-4">Full name</label>
						<div class="col-sm-6">
							<input type="text" name="holder_name" id="organization.holder_name" value="${orderCustomer.organization.holder_name }" class="form-control"  data-error-field/>
						</div>
					</div>
					<div class="form-group">
						<label for="organization.holder_job_title" class="control-label col-sm-4">Job title</label>
						<div class="col-sm-6">
							<input type="text" name="holder_job_title" id="organization.holder_job_title" value="${orderCustomer.organization.holder_job_title }" class="form-control" data-error-field/>
						</div>
					</div>
					<div class="form-group">
						<label for="organization.holder_phone" class="control-label col-sm-4">Phone Number</label>
						<div class="col-sm-6">
							<input type="text" name="holder_phone" id="organization.holder_phone" value="${orderCustomer.organization.holder_phone }" class="form-control" data-error-field/>
						</div>
					</div>
					<div class="form-group">
						<label for="organization.holder_email" class="control-label col-sm-4">Email Address</label>
						<div class="col-sm-6">
							<input type="text" name="holder_email" id="organization.holder_email" value="${orderCustomer.organization.holder_email }" class="form-control" data-error-field/>
						</div>
					</div>
						
					<hr>
					<div class="form-group">
						<div class="col-sm-2">
							<a href="${ctx }/broadband-user/sale/online/ordering/plans/${orderPlan.plan_class}" class="btn btn-success btn-lg btn-block" >Back</a>
						</div>
						<div class="col-sm-2 col-sm-offset-8">
							<button type="button" class="btn btn-success btn-lg btn-block" id="confirm">Confirm</button>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>

<div id="map_canvas" style="width:720px;height:600px;display:none;"></div>

<jsp:include page="footer.jsp" />
<jsp:include page="script.jsp" />
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/icheck.min.js"></script>
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/bootstrap-select.min.js"></script>
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/bootstrap-datepicker.js"></script>
<script type="text/javascript">
(function($){
	
	$('.input-group.date').datepicker({
	    format: "yyyy-mm-dd",
	    autoclose: true,
	    todayHighlight: true
	});
	
	$(':radio').iCheck({
		checkboxClass : 'icheckbox_square-green',
		radioClass : 'iradio_square-green'
	});
	
	$('.selectpicker').selectpicker(); 
	
	$('input[name="order_broadband_type"]').on('ifChecked', function(){
		var val = this.value;
		if (val === "new-connection") {
			$('#transitionContainer').hide('fast');
		} else if (val === "transition") {
			$('#transitionContainer').show('fast');
		}
	});
	
	$('#confirm').click(function(){
		var $btn = $(this);
		var url = '${ctx}/broadband-user/sale/online/ordering/order/business';
		var customer = {
			address: $('#address').val()
			, cellphone: $('#cellphone').val()
			, email: $('#email').val()
			, title: $('#title').val()
			, first_name: $('#first_name').val()
			, last_name: $('#last_name').val()
			, customerOrder: {
				order_broadband_type: $('input[name="order_broadband_type"]:checked').val()
			}
			, organization: {
				org_name: $('#organization\\.org_name').val()
				, org_type: $('#organization\\.org_type').val()
				, org_trading_name: $('#organization\\.org_trading_name').val()
				, org_register_no: $('#organization\\.org_register_no').val()
				, org_incoporate_date: $('#organization\\.org_incoporate_date').val()
				, org_trading_months: $('#organization\\.org_trading_months').val()
				, holder_name: $('#organization\\.holder_name').val()
				, holder_job_title: $('#organization\\.holder_job_title').val()
				, holder_phone: $('#organization\\.holder_phone').val()
				, holder_email: $('#organization\\.holder_email').val()
			}
			, customer_type: 'business'
		};
		
		if (customer.customerOrder.order_broadband_type == 'transition') {
			customer.customerOrder.transition_provider_name = $('#customerOrder\\.transition_provider_name').val();
			customer.customerOrder.transition_account_holder_name = $('#customerOrder\\.transition_account_holder_name').val();
			customer.customerOrder.transition_account_number = $('#customerOrder\\.transition_account_number').val();
			customer.customerOrder.transition_porting_number = $('#customerOrder\\.transition_porting_number').val();
		} //console.log("customer request:"); console.log(customer);
		
		$btn.button('loading');
		$.ajax({
			type: 'post'
			, contentType:'application/json;charset=UTF-8'         
	   		, url: url
		   	, data: JSON.stringify(customer)
		   	, dataType: 'json'
		   	, success: function(json){
				if (!$.jsonValidation(json, 'right')) { //console.log("customer response:"); console.log(json.model);
					window.location.href='${ctx}' + json.url;
				}
		   	}
		}).always(function () {
			$btn.button('reset');
	    });
	});
	
})(jQuery);
</script>
<script src="https://maps.google.com/maps/api/js?sensor=false&libraries=places&region=NZ" type="text/javascript"></script>
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/autoCompleteAddress.js"></script>
<jsp:include page="../footer-end.jsp" />