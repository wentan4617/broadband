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
	<div class="panel-group" id="accordion">
		<div class="panel panel-success">
			<div class="panel-heading">
				<h4 class="panel-title">
					<a data-toggle="collapse" data-parent="#accordion" href="#customer_details"> Customer Details <!-- <span class="text-danger">(All fields Required)</span> --></a>
				</h4>
			</div>
			<div id="customer_details" class="panel-collapse collapse in">
				<div class="panel-body">
					<form id="customerInfoFrom" class="form-horizontal">
						<h4 class="text-success">Create your account</h4>
						<hr />
						<div class="form-group">
							<label for="login_name" class="control-label col-sm-4">Your login name </label>
							<div class="col-sm-6">
								<input type="text" name="login_name" id="login_name" class="form-control" placeholder="e.g.: cyberpark" data-error-field/>
							</div>
						</div>
						<div class="form-group">
							<label for="password" class="control-label col-sm-4">Password</label>
							<div class="col-sm-6">
								<input type="password" name="password" id="password" class="form-control" placeholder="" data-error-field/>
							</div>
						</div>
						<div class="form-group">
							<label for="ck_password" class="control-label col-sm-4">Confirm your password</label>
							<div class="col-sm-6">
								<input type="password" name="ck_password" id="ck_password" class="form-control" placeholder="" data-error-field/>
							</div>
						</div>

						<!-- Broadband Options -->
						<hr />
						<h4 class="text-success">Broadband Options</h4>
						<hr />
						<div class="form-group">
							<label for="" class="control-label col-sm-4">Broadband Type</label>
							<div class="col-sm-6">
								<ul class="list-unstyled topup-list">
									<li>
										<input type="radio" name="order_broadband_type" checked="checked" value="new-connection"/>
										&nbsp; <strong>New Connection Only</strong>
									</li>
									<li>
										<input type="radio" name="order_broadband_type" value="transition"/>
										&nbsp; <strong>Transition</strong>
									</li>
								</ul>
							</div>
						</div>
						
						<!-- Transition Information -->
						<div id="transitionContainer" style="display:none;">
							<hr />
							<h4 class="text-success">Transition Information</h4>
							<hr />
							<div class="form-group">
								<label for="" class="control-label col-sm-4">Current Provider Name</label>
								<div class="col-sm-6">
									<input type="text" name="customerOrder.transition_provider_name" id="customerOrder.transition_provider_name" class="form-control" placeholder="" />
									
								</div>
							</div>
							<div class="form-group">
								<label for="" class="control-label col-sm-4">Account Holder Name</label>
								<div class="col-sm-6">
									<input type="text" name="customerOrder.transition_account_holder_name" id="customerOrder.transition_account_holder_name" class="form-control" placeholder="" />
								</div>
							</div>
							<div class="form-group">
								<label for="" class="control-label col-sm-4">Current Account Number</label>
								<div class="col-sm-6">
									<input type="text" name="customerOrder.transition_account_number" id="customerOrder.transition_account_number" class="form-control" placeholder="" />
									
								</div>
							</div>
							<div class="form-group">
								<label for="" class="control-label col-sm-4">Telephone Number</label>
								<div class="col-sm-6">
									<input type="text" name="customerOrder.transition_porting_number" id="customerOrder.transition_porting_number" class="form-control" placeholder="" />
							
								</div>
							</div>

						</div>


						<!-- Customer Information -->
						<hr />
						<h4 class="text-success">Customer Information</h4>
						<hr />
						<div class="form-group">
							<label for="" class="control-label col-sm-4">Customer Type</label>
							<div class="col-sm-6">
								<ul class="list-unstyled topup-list">
									<li>
										<input type="radio" name="customer_type" checked="checked" value="business"/>
										&nbsp; <strong>Business</strong>
									</li>
									<li>
										<input type="radio" name="customer_type" value="personal"/>
										&nbsp; <strong>Personal</strong>
									</li>
								</ul>
							</div>
						</div>
						<hr />
						
						<div id="businessContainer">
							<div class="form-group">
								<label for="organization.org_type" class="control-label col-sm-4">Group/Organization Type</label>
								<div class="col-sm-6">
									<select name="org_type" id="organization.org_type" class="selectpicker show-tick form-control" data-error-field>
										<option value="NZ Incorporated Company">NZ Incorporated Company</option>
										<option value="Limited Partnership">Limited Partnership</option>
										<option value="Sole Trader">Sole Trader</option>
										<option value="Partnership">Partnership</option>
										<option value="Trust">Trust</option>
										<option value="Overseas Company">Overseas Company</option>
										<option value="Society">Society</option>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label for="organization.org_name" class="control-label col-sm-4">Group/Organization Name</label>
								<div class="col-sm-6">
									<input type="text" name="org_name" id="organization.org_name" class="form-control" placeholder="" data-error-field/>
								</div>
							</div>
							<div class="form-group">
								<label for="organization.org_trading_name" class="control-label col-sm-4">Trading Name</label>
								<div class="col-sm-6">
									<input type="text" name="org_trading_name" id="organization.org_trading_name" class="form-control" placeholder="" data-error-field/>
								</div>
							</div>
							<div class="form-group">
								<label for="organization.org_register_no" class="control-label col-sm-4">Registration No.</label>
								<div class="col-sm-6">
									<input type="text" name="org_register_no" id="organization.org_register_no" class="form-control" placeholder="" data-error-field/>
								</div>
							</div>
							<div class="form-group">
								<label for="organization.org_incoporate_date" class="control-label col-sm-4">Date Incorporated</label>
								<div class="col-sm-4">
									<input type="date" name="org_incoporate_date" id="organization.org_incoporate_date" class="form-control" placeholder="" data-error-field/>
								</div>
							</div>
							<div class="form-group">
								<label for="organization.holder_name" class="control-label col-sm-4">Full name</label>
								<div class="col-sm-6">
									<input type="text" name="holder_name" id="organization.holder_name" class="form-control" placeholder="" data-error-field/>
								</div>
							</div>
							<div class="form-group">
								<label for="organization.holder_job_title" class="control-label col-sm-4">Job title</label>
								<div class="col-sm-6">
									<input type="text" name="holder_job_title" id="organization.holder_job_title" class="form-control" placeholder="" data-error-field/>
								</div>
							</div>
							<div class="form-group">
								<label for="organization.holder_phone" class="control-label col-sm-4">Phone Number</label>
								<div class="col-sm-6">
									<input type="text" name="holder_phone" id="organization.holder_phone" class="form-control" placeholder="" data-error-field/>
								</div>
							</div>
							<div class="form-group">
								<label for="organization.holder_email" class="control-label col-sm-4">Email Address</label>
								<div class="col-sm-6">
									<input type="text" name="holder_email" id="organization.holder_email" class="form-control" placeholder="" data-error-field/>
								</div>
							</div>
						</div>
						
						<div id="personalContainer" style="display:none;">
							<div class="form-group">
								<label for="title" class="control-label col-sm-4">Title</label>
								<div class="col-sm-6">
									<select name="title" id="title" class="selectpicker show-tick form-control" data-error-field>
										<option value="Mr">Mr</option>
										<option value="Mrs">Mrs</option>
										<option value="Ms">Ms</option>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label for="first_name" class="control-label col-sm-4">First name</label>
								<div class="col-sm-6">
									<input type="text" name="first_name" id="first_name" class="form-control" placeholder="" data-error-field/>
								</div>
							</div>
							<div class="form-group">
								<label for="last_name" class="control-label col-sm-4">Last name</label>
								<div class="col-sm-6">
									<input type="text" name="last_name" id="last_name" class="form-control" placeholder="" data-error-field/>
								</div>
							</div>
							<div class="form-group">
								<label for="email" class="control-label col-sm-4">Email</label>
								<div class="col-sm-6">
									<input type="text" name="email" id="email" class="form-control" placeholder="" data-error-field/>
								</div>
							</div>
							<div class="form-group">
								<label for="cellphone" class="control-label col-sm-4">Phone</label>
								<div class="col-sm-6">
									<input type="text" name="cellphone" id="cellphone" class="form-control" placeholder="" data-error-field/>
								</div>
							</div>
							
						</div>
						
						<div class="form-group">
							<label for="address" class="control-label col-sm-4">Your Address</label>
							<div class="col-sm-8">
								<input id="address" type="text" name="address" id="address" class="form-control" placeholder="" data-error-field/>
							</div>
						</div>
						<hr>
						<div class="form-group">
							<div class="col-sm-2">
								<a href="${ctx }/broadband-user/sale/online/ordering/plans" class="btn btn-success btn-lg btn-block" >Back</a>
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
</div>
<div id="map_canvas" style="width:720px;height:600px;display:none;"></div>


<jsp:include page="footer.jsp" />
<jsp:include page="script.jsp" />
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/icheck.min.js"></script>
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/bootstrap-select.min.js"></script>
<script type="text/javascript">
(function($){
	
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
	
	
	$('input[name="customer_type"]').on('ifChecked', function(){
		var val = this.value;
		if (val === "business") {
			$('#businessContainer').show('fast');
			$('#personalContainer').hide('fast');
		} else if (val === "personal") {
			$('#businessContainer').hide('fast');
			$('#personalContainer').show('fast');
		}
	});
	
	$('#confirm').click(function(){
		var $btn = $(this);
		$btn.button('loading');
		var customer_type = $('input[name="customer_type"]:checked').val();
		var order_broadband_type = $('input[name="order_broadband_type"]:checked').val();
		
		var customer = null;
		var url = '';
		
		if (customer_type == 'business') {
			url = '${ctx}/broadband-user/sale/online/ordering/order/business';
			customer = {
				login_name: $('#login_name').val()
				, password: $('#password').val()
				, ck_password: $('#ck_password').val()
				, address: $('#address').val()
				, customerOrder: {
					order_broadband_type: order_broadband_type
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
				, customer_type: customer_type
			};
		} else if (customer_type == 'personal') {
			url = '${ctx}/broadband-user/sale/online/ordering/order/personal';
			customer = {
				login_name: $('#login_name').val()
				, password: $('#password').val()
				, ck_password: $('#ck_password').val()
				, first_name: $('#first_name').val()
				, last_name: $('#last_name').val()
				, address: $('#address').val()
				, email: $('#email').val()
				, cellphone: $('#cellphone').val()
				, title: $('#title').val()
				, customerOrder: {
					order_broadband_type: order_broadband_type
				}
				, customer_type: customer_type
			};
		}
		
		if (customer.customerOrder.order_broadband_type == 'transition') {
			customer.customerOrder.transition_provider_name = $('#customerOrder\\.transition_provider_name').val();
			customer.customerOrder.transition_account_holder_name = $('#customerOrder\\.transition_account_holder_name').val();
			customer.customerOrder.transition_account_number = $('#customerOrder\\.transition_account_number').val();
			customer.customerOrder.transition_porting_number = $('#customerOrder\\.transition_porting_number').val();
		}
		
		console.log(customer);
		console.log(JSON.stringify(customer));
		
		$.ajax({
			type: 'post'
			, contentType:'application/json;charset=UTF-8'         
	   		, url: url
		   	, data: JSON.stringify(customer)
		   	, dataType: 'json'
		   	, success: function(json){
				if (json.hasErrors) {
					$.jsonValidation(json);
				} else {
					//alert('ok');
					window.location.href='${ctx}' + json.url;
				}
		   	}
		}).always(function () {
			$btn.button('reset');
	    });
	});
	
})(jQuery);
</script>
<script src="http://maps.google.com/maps/api/js?sensor=false&libraries=places&region=NZ" type="text/javascript"></script>
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/autoCompleteAddress.js"></script>
<jsp:include page="../footer-end.jsp" />