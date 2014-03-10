<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %> 
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<jsp:include page="header.jsp" />

<style>
.topup-list li {
	padding: 10px 20px;
}
.affix {
	width: 261px;
	top:30px;
}
</style>

<div class="container">
	<div class="page-header">
		<h1>
			2. Customer Information <small>Please fill in your personal information, we will contact you</small>
		</h1>
	</div>
	
	<div class="row">
		<div class="col-md-9">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title">Your application form <span class="text-danger">(All fields Required)</span></h3>
				</div>
				<div class="panel-body">
					
					<form:form modelAttribute="customer" method="post" action="${ctx}/order" class="form-horizontal">
						<h4>Account Login Name and Password </h4>
						<hr/>
						<div class="form-group">
							<label for="login_name" class="control-label col-md-4">Your login name </label>
							<div class="col-md-4">
								<form:input path="login_name" class="form-control" placeholder="e.g.: cyberpark" />
							</div>
							<p class="help-block">
								<form:errors path="login_name" cssErrorClass="error"/>
							</p>
						</div>
						<div class="form-group">
							<label for="password" class="control-label col-md-4">Password</label>
							<div class="col-md-4">
								<form:password path="password" class="form-control" placeholder="" />
							</div>
							<p class="help-block">
								<form:errors path="password" cssErrorClass="error"/>
							</p>
						</div>
						<div class="form-group">
							<label for="ck_password" class="control-label col-md-4">Confirm your password</label>
							<div class="col-md-4">
								<form:password path="ck_password" class="form-control" placeholder="" />
							</div>
							<p class="help-block">
								<form:errors path="ck_password" cssErrorClass="error"/>
							</p>
						</div>
						
						<!-- Broadband Options -->
						<hr/>
						<h4>Broadband Options</h4>
						<hr/>
						<div class="form-group">
							<label for="" class="control-label col-md-4">Broadband Type</label>
							<div class="col-md-4">
								<ul class="list-unstyled topup-list" >
									<li>
										<form:radiobutton path="customerOrder.order_broadband_type" value="new-connection" /> &nbsp;
										<strong>New Connection Only</strong>
									</li>
									<li>
										<form:radiobutton path="customerOrder.order_broadband_type" value="transition" /> &nbsp;
										<strong>Transition</strong>
									</li>
								</ul>
							</div>
							<div class="col-md-4">
								<div class="well">
									<p>If you choose a new connection</p>
									<p> we will charge you </p>
									<p>$ 99 broadband opening costs</p>
								</div>
							</div>
						</div>
						
						
						<!-- Transition Information -->
						<div id="transitionContainer" >
							<hr/>
							<h4>Transition Information</h4>
							<hr/>
							<div class="form-group">
								<label for="" class="control-label col-md-4">Your Current Provider Name</label>
								<div class="col-md-4">
									<form:input path="customerOrder.transition_provider_name" class="form-control" placeholder="" />
								</div>
								<div class="col-md-4">
								</div>
							</div>
							<div class="form-group">
								<label for="" class="control-label col-md-4">Account Holder Name</label>
								<div class="col-md-4">
									<form:input path="customerOrder.transition_account_holder_name" class="form-control" placeholder="" />
								</div>
								<div class="col-md-4">
								</div>
							</div>
							<div class="form-group">
								<label for="" class="control-label col-md-4">Your Current Account Number</label>
								<div class="col-md-4">
									<form:input path="customerOrder.transition_account_number" class="form-control" placeholder="" />
								</div>
								<div class="col-md-4">
								</div>
							</div>
							<div class="form-group">
								<label for="" class="control-label col-md-4">Your Porting Number</label>
								<div class="col-md-4">
									<form:input path="customerOrder.transition_porting_number" class="form-control" placeholder="" />
								</div>
								<div class="col-md-4">
								</div>
							</div>
						
						</div>
						
						
						<!-- Personal Information -->
						<hr/>
						<h4>Personal Information</h4>
						<hr/>
						<div class="form-group">
							<label for="first_name" class="control-label col-md-4">First name</label>
							<div class="col-md-4">
								<form:input path="first_name" class="form-control" placeholder="" />
							</div>
							<p class="help-block">
								<form:errors path="first_name" cssErrorClass="error"/>
							</p>
						</div>
						<div class="form-group">
							<label for="last_name" class="control-label col-md-4">Last name</label>
							<div class="col-md-4">
								<form:input path="last_name" class="form-control" placeholder="" />
							</div>
							<p class="help-block">
								<form:errors path="last_name" cssErrorClass="error"/>
							</p>
						</div>
						<div class="form-group">
							<label for="email" class="control-label col-md-4">Your Email</label>
							<div class="col-md-4">
								<form:input path="email" class="form-control" placeholder="" />
							</div>
							<p class="help-block">
								<form:errors path="email" cssErrorClass="error"/>
							</p>
						</div>
						
						<div class="form-group">
							<label for="cellphone" class="control-label col-md-4">Your Phone</label>
							<div class="col-md-4">
								<form:input path="cellphone" class="form-control" placeholder="" />
							</div>
							<p class="help-block">
								<form:errors path="cellphone" cssErrorClass="error"/>
							</p>
						</div>
						<div class="form-group">
							<label for="address" class="control-label col-md-4">Your Address</label>
							<div class="col-md-5">
								<form:input path="address" class="form-control" placeholder="" />
							</div>
							<p class="help-block col-md-3" >
								<form:errors path="address" cssErrorClass="error"/>
							</p>
						</div>
						<hr/>
						<div class="form-group">
							<div class="col-md-8 col-md-offset-4">
								<a href="${ctx }/plans/${fn:toLowerCase(orderPlan.plan_group=='plan-topup'?'t':'p')}" class="btn btn-success">Back to choose plans</a>&nbsp;
								<button type="submit" class="btn btn-success">Continue</button>
							</div>
						</div>
					</form:form>
				</div>
			</div>
		</div>
		<div class="col-md-3" >
			<div data-spy="affix" data-offset-top="150" class="panel panel-success">
				<div class="panel-heading">
					<h3 class="panel-title">Your current purchase of plan</h3>
				</div>
		  		<div  class="panel-body">
		  			<h4 class="text-success">${orderPlan.plan_name }</h4>
		  			<h1 class="text-danger">
		  				NZ$ 
		  				<strong>
		  					<span id="totalPrice">
		  						<fmt:formatNumber value="${orderPlan.plan_price * orderPlan.plan_prepay_months}" type="number" pattern="#,##0.00" />
		  					</span>
		  				</strong>
		  			</h1>
		  			<div id="serviceContainer"></div>
		  			<div id="addonContainer"></div>
		  			<hr/>
		    		${orderPlan.plan_desc }
		  		</div>
			</div>
			
		</div>
	</div>
	
	
</div>


<jsp:include page="footer.jsp" />
<jsp:include page="script.jsp" />
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/holder.js"></script>
<script type="text/javascript"
	src="${ctx}/public/bootstrap3/js/icheck.min.js"></script>
<script type="text/javascript">
(function($) {

	$(':radio').iCheck({
		checkboxClass : 'icheckbox_square-green',
		radioClass : 'iradio_square-green'
	});
	
	function toggleTransitionContainer(val) {
		var serviceHtml = "";
		var addonHtml = "";
		if (val === "new-connection") {
			//totalPrice
			
			$('#transitionContainer').hide('fast');
			
			serviceHtml += '<hr/>';
			serviceHtml += '<p class="text-success"><strong>Services:</strong></p>';
			serviceHtml += '<ul>';
			serviceHtml += '<li><strong class="text-danger">New Connection Only</strong></li>';
			serviceHtml += '<li><strong class="text-danger">Prepay ${orderPlan.plan_prepay_months} months</strong></li>';
			serviceHtml += '</ul>';
			
			var price = $('#totalPrice').text();
			$('#totalPrice').text((Number(price) + Number(${orderPlan.plan_new_connection_fee})).toFixed(2));
			
		} else if (val === "transition") {
			$('#transitionContainer').show('fast');
			
			serviceHtml += '<hr/>';
			serviceHtml += '<p class="text-success"><strong>Services:</strong></p>';
			serviceHtml += '<ul>';
			serviceHtml += '<li><strong class="text-danger">Prepay ${orderPlan.plan_prepay_months} months</strong></li>';
			serviceHtml += '</ul>';
			
			var price = $('#totalPrice').text();
			$('#totalPrice').text((Number(price) - Number(${orderPlan.plan_new_connection_fee})).toFixed(2));
		}
		
		service(serviceHtml);
		addon(addonHtml);
	}
	
	toggleTransitionContainer('${customer.customerOrder.order_broadband_type}');
	
	
	$('input[name="customerOrder\\.order_broadband_type"]').on('ifChecked', function(){
		
		var val = this.value;
		toggleTransitionContainer(val);
	});
	
	function service(html) {
		$('#serviceContainer').html(html);
	}
	
	function addon(html) {
		$('#addonContainer').html(html);
	}
	

})(jQuery);
</script>
<jsp:include page="footer-end.jsp" />