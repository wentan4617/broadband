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
					<a data-toggle="collapse" data-parent="#accordion" href="#customer_details"> Customer Details <span class="text-danger">(All fields Required)</span></a>
				</h4>
			</div>
			<div id="customer_details" class="panel-collapse collapse in">
				<div class="panel-body">
					<form action="" class="form-horizontal">
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
								<label for="" class="control-label col-sm-4">Port Number</label>
								<div class="col-sm-6">
									<input type="text" name="customerOrder.transition_porting_number" id="customerOrder.transition_porting_number" class="form-control" placeholder="" />
							
								</div>
							</div>

						</div>


						<!-- Personal Information -->
						<hr />
						<h4 class="text-success">Personal Information</h4>
						<hr />
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
						<div class="form-group">
							<label for="address" class="control-label col-sm-4">Your Address</label>
							<div class="col-sm-8">
								<input type="text" name="address" id="address" class="form-control" placeholder="" data-error-field/>
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


<jsp:include page="footer.jsp" />
<jsp:include page="script.jsp" />
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/icheck.min.js"></script>
<script type="text/javascript">
(function($){
	$(':radio').iCheck({
		checkboxClass : 'icheckbox_square-green',
		radioClass : 'iradio_square-green'
	});
	
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
		$btn.button('loading');
		var customer = {
			login_name: $('#login_name').val()
			, password: $('#password').val()
			, ck_password: $('#ck_password').val()
			, first_name: $('#first_name').val()
			, last_name: $('#last_name').val()
			, address: $('#address').val()
			, email: $('#email').val()
			, cellphone: $('#cellphone').val()
			, customerOrder: {
				order_broadband_type: $('input[name="order_broadband_type"]:checked').val()
			}
		};
		console.log(customer);
		
		$.ajax({
			type: 'post'
			, contentType:'application/json;charset=UTF-8'         
	   		, url: '${ctx}/broadband-user/sale/online/ordering/order'
		   	, data: JSON.stringify(customer)
		   	, dataType: 'json'
		   	, success: function(json){
				if (json.hasErrors) {
					$.jsonValidation(json);
				} else {
					alert('ok');
					//window.location.href='${ctx}' + json.url;
				}
		   	}
		}).always(function () {
			$btn.button('reset');
	    });
		/*$.post('${ctx}/broadband-user/sale/online/ordering/order', customer, function(json){
			if (json.hasErrors) {
				$.jsonValidation(json);
			} else {
				alert('ok');
				//window.location.href='${ctx}' + json.url;
			}
		}, 'json').always(function () {
			$btn.button('reset');
	    });*/
	});
	
})(jQuery);
</script>
<jsp:include page="../footer-end.jsp" />