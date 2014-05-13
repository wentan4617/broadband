<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
.nav-pills>li.active>a, .nav-pills>li.active>a:hover, .nav-pills>li.active>a:focus {
color: #fff;
background-color: #7BC3EC;
}
</style>

<div class="container" >
	
	<ul class="panel panel-success nav nav-pills nav-justified"><!-- nav-justified -->
		<li>
			<a class="btn-lg">
				1. Choose Plans And Pricing
				<span class="glyphicon glyphicon-hand-right pull-right"></span>
			</a>
		</li>
		<li class="active">
			<a class="btn-lg">
				2. Fill Application Form
				<span class="glyphicon glyphicon-hand-right pull-right" ></span>
			</a>
		</li>
		<li>
			<a class="btn-lg">
				3. Review and Checkout 
			</a>
		</li>
	</ul>
	
	<form id="customerInfoFrom" class="form-horizontal">
	
		<div class="row">
			<div class="col-md-9">
				
				<!-- application form -->
				
				<div class="panel panel-success">
				
					<div class="panel-heading">
						<h2 class="panel-title">
							<a data-toggle="collapse" data-toggle="collapse" data-parent="#accordion" href="#application">
								Your Application Form <span class="text-danger">(All Fields Required)</span>
							</a>
						</h2>
					</div>
					
					<div id="application" class="panel-collapse collapse in">
						<div class="panel-body">
						
							<!-- customer address -->
							<h4 class="text-success">Your Address</h4>
							<hr/>
							
							<div class="form-group">
								<label for="address" class="control-label col-md-4">Your Address</label>
								<div class="col-md-8">
									
									<c:choose>
										<c:when test="${customer.address!=null&&customer.serviceAvailable}">
											<p class="form-control-static">${customer.address }</p>
											<input type="hidden" id="address" name="address" value="${customer.address }" />
										</c:when>
										<c:otherwise>
											<input type="text" id="address" name="address" value="${customer.address }" class="form-control" data-error-field data-placement="top"/>
										</c:otherwise>
									</c:choose>
								</div>
							</div>
							
							<!-- customer account -->
							<hr/>
							<h4 class="text-success">Create Your Account</h4>
							<hr/>
							
							<div class="form-group">
								<label for="cellphone" class="control-label col-md-4">Your Mobile</label>
								<div class="col-md-4">
									<input type="text" id="cellphone" name="cellphone" value="${customer.cellphone }"class="form-control" placeholder="e.g.: 0210800123" data-error-field />
								</div>
							</div>
							<div class="form-group">
								<label for="email" class="control-label col-md-4">Your Email</label>
								<div class="col-md-4">
									<input type="text" id="email" name="email" value="${customer.email }" class="form-control" placeholder="e.g.: welcome@cyberpark.co.nz" data-error-field/>
								</div>
							</div>
							
							<!-- Broadband Options -->
							<hr/>
							<h4 class="text-success">Broadband Options</h4>
							<hr/>
							
							<div class="form-group">
								<label class="control-label col-md-4">Broadband Type</label>
								<div class="col-md-4">
									<ul class="list-unstyled topup-list">
										<li>
											<input type="radio" name="order_broadband_type" 
												${customer.customerOrder.order_broadband_type=='new-connection'?'checked="checked"':'' } value="new-connection"/>
											&nbsp; <strong>New Connection Only</strong>
										</li>
										<li>
											<input type="radio" name="order_broadband_type" value="transition"
												<c:if test="${customer.customerOrder.order_broadband_type=='transition' || customer.customerOrder.order_broadband_type==null}">
													checked="checked"
												</c:if> />
											&nbsp; <strong>Transition</strong>
										</li>
									</ul>
								</div>
								<div class="col-md-4">
									<c:choose>
										<c:when test="${orderPlan.plan_group == 'plan-no-term' }">
											<div class="well">
												<p>If you choose a new connection</p>
												<p> we will charge you </p>
												<p> broadband opening costs</p>
											</div>
										</c:when>
										<c:when test="${orderPlan.plan_group == 'plan-term' }"></c:when>
									</c:choose>
								</div>
							</div>
							
							
							<!-- Transition Information -->
							<div id="transitionContainer" >
							
								<hr/>
								<h4 class="text-success">Transition Information</h4>
								<hr/>
								
								<div class="form-group">
									<label for="" class="control-label col-md-4">Your Current Provider Name</label>
									<div class="col-md-4">
										<input type="text" id="customerOrder.transition_provider_name" name="customerOrder.transition_provider_name" value="${customer.customerOrder.transition_provider_name }" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label for="" class="control-label col-md-4">Account Holder Name</label>
									<div class="col-md-4">
										<input type="text" id="customerOrder.transition_account_holder_name" name="customerOrder.transition_account_holder_name" value="${customer.customerOrder.transition_account_holder_name }" class="form-control" />
									</div>
								</div>
								<div class="form-group">
									<label for="" class="control-label col-md-4">Your Current Account Number</label>
									<div class="col-md-4">
										<input type="text" id="customerOrder.transition_account_number" name="customerOrder.transition_account_number" value="${customer.customerOrder.transition_account_number }" class="form-control" />
									</div>
								</div>
								<div class="form-group">
									<label for="" class="control-label col-md-4">Your Telephone Number</label>
									<div class="col-md-4">
										<input type="text" id="customerOrder.transition_porting_number" name="customerOrder.transition_porting_number" value="${customer.customerOrder.transition_porting_number }" class="form-control" />
									</div>
								</div>
							
							</div>
							
							
							<!-- Personal Information -->
							
							<hr/>
							<h4 class="text-success">Personal Information</h4>
							<hr/>
							
							<div class="form-group">
								<label for="title" class="control-label col-md-4">Title</label>
								<div class="col-sm-2">
									<select name="title" id="title" class="selectpicker show-tick form-control">
										<option value="Mr">Mr</option>
										<option value="Mrs">Mrs</option>
										<option value="Ms">Ms</option>
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
						</div>
					
					</div>
				</div>
				
				<!-- hardware -->
				
				<c:if test="${fn:length(hardwares) > 0}">
					<div class="panel panel-success">
					
						<div class="panel-heading">
							<h2 class="panel-title">
								<a data-toggle="collapse" data-toggle="collapse" data-parent="#accordion" href="#hardware">
									Add-ons: Hardware
								</a>
							</h2>
						</div>
						
						<div id="hardware" class="panel-collapse collapse in">
							<div class="panel-body">
								<c:forEach var="hardware" items="${hardwares }" varStatus="item">
									<div class="panel panel-success">
										<div class="panel-body bg-success">
											<h3 class="text-success" style="margin-top:0;">
												<strong>${hardware.hardware_name }</strong>
											</h3>
										  	<p>${hardware.hardware_desc }</p>
										</div>
										<ul class="list-unstyled topup-list">
											<li>
												<input type="radio" name="hardware_${hardware.id }" data-name="addons" data-id="${hardware.id }" data-price="${hardware.hardware_price }"/> &nbsp;
												<strong>None</strong>
												<span id="none_${hardware.id }" class="text-danger"></span>
											</li>
											<li>
												<input type="radio" name="hardware_${hardware.id }" value="${hardware.id }" data-name="addons" data-id="${hardware.id }" data-price="${hardware.hardware_price }" data-hname="${hardware.hardware_name }"/> &nbsp;
												<strong>${hardware.hardware_name }</strong>
												<span id="hp_${hardware.id }" class="text-danger"></span><%--  Add NZ$ [${hardware.hardware_price}] --%>
											</li>
										</ul>
									</div>
									
								</c:forEach>
							</div>
						</div>
						
					</div>
				</c:if>
					
			</div>
			<div class="col-md-3" >
				<div data-spy="affix" data-offset-top="150" class="panel panel-success">
					<div class="panel-heading">
						<h3 class="panel-title">Your current plan</h3>
					</div>
			  		<div  class="panel-body">
			  			<h4 class="text-success">${orderPlan.plan_name }</h4>
			  			<h1 class="text-danger">
			  				NZ$ 
			  				<strong>
			  					<span id="totalPrice">
			  						<c:choose>
			  							<c:when test="${orderPlan.plan_group == 'plan-topup' }">
			  								<fmt:formatNumber value="${orderPlan.topup.topup_fee}" type="number" pattern="#,##0.00" />
			  							</c:when>
			  							<c:otherwise>
			  								<fmt:formatNumber value="${orderPlan.plan_price * orderPlan.plan_prepay_months}" type="number" pattern="#,##0.00" />
			  							</c:otherwise>
			  						</c:choose>
			  						
			  					</span>
			  				</strong>
			  			</h1>
			  			<div id="serviceContainer"></div>
			  			<div id="addonContainer"></div>
			    		<hr/>
			    		
			    		<c:set var="back_url" value=""></c:set>
			    		<c:choose>
			    			<c:when test="${orderPlan.plan_group=='plan-topup' }">
			    				<c:set var="back_url" value="${ctx }/plans/plan-topup/personal"></c:set>
			    			</c:when>
			    			<c:when test="${orderPlan.plan_group == 'plan-no-term' && orderPlan.plan_class == 'personal' }">
			    				<c:set var="back_url" value="${ctx }/plans/plan-no-term/personal"></c:set>
			    			</c:when>
			    			<c:when test="${orderPlan.plan_group == 'plan-term' && orderPlan.plan_class == 'personal' }">
			    				<c:set var="back_url" value="${ctx }/plans/plan-term/personal"></c:set>
			    			</c:when>
			    			<c:when test="${orderPlan.plan_group =='plan-term' && orderPlan.plan_class == 'business' }">
			    				<c:set var="back_url" value="${ctx }/plans/plan-term/business"></c:set>
			    			</c:when>
			    		</c:choose>
						<a href="${back_url }" style="width:100px;" class="btn btn-success">Back</a>&nbsp;
						<button type="button" class="btn btn-success" style="width:100px;" id="btnConfirm">Confirm</button>
						
			  		</div>
				</div>
				
			</div>
		</div>
	</form>
</div>

<div id="map_canvas" style="width:720px;height:600px;display:none;"></div>

<jsp:include page="footer.jsp" />
<jsp:include page="script.jsp" />
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/icheck.min.js"></script>
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/bootstrap-select.min.js"></script>
<script type="text/javascript">
(function($) {

	$(':radio').iCheck({
		checkboxClass : 'icheckbox_square-green',
		radioClass : 'iradio_square-green'
	});
	
	$('.selectpicker').selectpicker(); 
	
	var plan = {
		plan_group: '${orderPlan.plan_group}'
		, plan_class: '${orderPlan.plan_class}'
		, plan_prepay_months: new Number(${orderPlan.plan_prepay_months})
		, plan_new_connection_fee: new Number(${orderPlan.plan_new_connection_fee})
		, plan_price: new Number(${orderPlan.plan_price})
		, topup_fee: new Number(${orderPlan.topup.topup_fee})
		, pstn_count: new Number(${orderPlan.pstn_count})
		, term_period: new Number(${orderPlan.term_period})
	};
	
	var price = {
		plan_price: plan.plan_price
		, service_price: 0
		, addons_price: 0
	};
	
	function toggleTransitionContainer(order_broadband_type) {
		var serviceHtml = "";
		if (plan.plan_group == 'plan-topup') {
			
			serviceHtml += '<hr/>';
			serviceHtml += '<p class="text-success"><strong>Services:</strong></p>';
			serviceHtml += '<ul>';
			serviceHtml += '<li><strong class="text-danger">Broadband Topup Fee ($' +  plan.topup_fee.toFixed(2) + ')</strong></li>';
			if (order_broadband_type === "new-connection") {
				$('#transitionContainer').hide('fast');
				serviceHtml += '<li><strong class="text-danger">New Connection Only ($' +  plan.plan_new_connection_fee.toFixed(2) + ')</strong></li>';
				price.service_price = plan.plan_new_connection_fee;
			} else if (order_broadband_type === "transition") {
				$('#transitionContainer').show('fast');
				price.service_price = 0;
			}
			serviceHtml += '</ul>';
			price.service_price += plan.topup_fee;
			
		} else if (plan.plan_group == 'plan-no-term') {
			if (order_broadband_type === "new-connection") {
				$('#transitionContainer').hide('fast');
				serviceHtml += '<hr/>';
				serviceHtml += '<p class="text-success"><strong>Services:</strong></p>';
				serviceHtml += '<ul>';
				serviceHtml += '<li><strong class="text-danger">New Connection Only ($' +  plan.plan_new_connection_fee.toFixed(2) + ')</strong></li>';
				serviceHtml += '<li><strong class="text-danger">Prepay ' + plan.plan_prepay_months + ' months</strong></li>';
				price.service_price = plan.plan_new_connection_fee;
				price.plan_price = plan.plan_price * plan.plan_prepay_months;
			} else if (order_broadband_type === "transition") {
				$('#transitionContainer').show('fast');
				serviceHtml += '<hr/>';
				serviceHtml += '<p class="text-success"><strong>Services:</strong></p>';
				serviceHtml += '<ul>';
				serviceHtml += '<li><strong class="text-danger">Prepay ' + plan.plan_prepay_months + ' months</strong></li>';
				price.service_price = 0;
				price.plan_price = plan.plan_price * plan.plan_prepay_months;
			}
		} else if (plan.plan_group == 'plan-term') {
			
			serviceHtml += '<hr/>';
			serviceHtml += '<p class="text-success"><strong>Bundles:</strong></p>';
			serviceHtml += '<ul>';
			serviceHtml += '<li><strong class="text-danger">' + plan.term_period + ' Months Terms</li>';
			serviceHtml += '<li><strong class="text-danger">' + plan.pstn_count + ' Home Phone Line</li>';
			serviceHtml += '<li><strong class="text-danger">Free Router</li>';
			if (order_broadband_type === "new-connection") {
				$('#transitionContainer').hide('fast');
				//serviceHtml += '<li><strong class="text-danger">Free New Connection Fee</strong></li>';
				serviceHtml += '<li><strong class="text-danger">New Connection Only ($' +  plan.plan_new_connection_fee.toFixed(2) + ')</strong></li>';
				price.service_price = plan.plan_new_connection_fee;
			} else if (order_broadband_type === "transition") {
				$('#transitionContainer').show('fast');
				price.service_price = 0;
			}
		}
		
		$('#totalPrice').text((price.plan_price + price.service_price + price.addons_price).toFixed(2));
		service(serviceHtml);
	}
	
	function toggleAddonContainer(opt){
		var val = $(opt).val(); //console.log(val);
		var id = $(opt).attr('data-id');
		var unit_price = $(opt).attr('data-price');
		if (val != 'on') {
			$('#hp_' + id).text('');
			$('#none_' + id).text('[Subtract NZ$ ' + unit_price + ']');
		} else {
			$('#hp_' + id).text('[Add NZ$ ' + unit_price + ']');
			$('#none_' + id).text('');
		}
		var addonsHtml = "";
		var $addons = $('input[data-name="addons"][data-hname]:checked');
		price.addons_price = 0;
		if ($addons.length > 0) {
			addonsHtml += '<hr/>';
			addonsHtml += '<p class="text-success"><strong>Add-ons:</strong></p>';
			addonsHtml += '<ul>';
			$addons.each(function(){
				addonsHtml += '<li><strong class="text-danger">' + $(this).attr('data-hname') + '</strong></li>';
				price.addons_price += Number($(this).attr('data-price'));
			});
			addonsHtml += '</ul>';
		} 
		
		$('#totalPrice').text((price.plan_price + price.service_price + price.addons_price).toFixed(2));
		addon(addonsHtml);
	}
	
	$('input[data-name="addons"]').on('ifChecked', function(){
		toggleAddonContainer(this);
	});
	
	
	var order_broadband_type = '${customer.customerOrder.order_broadband_type}' != '' ? '${customer.customerOrder.order_broadband_type}': 'transition';
	toggleTransitionContainer(order_broadband_type);
	
	/*$('input[data-name="addons"]:checked').each(function(){
		toggleAddonContainer(this);
	});*/
	
	
	$('input[name="order_broadband_type"]').on('ifChecked', function(){
		toggleTransitionContainer(this.value);
	});
	
	function service(html) {
		$('#serviceContainer').html(html);
	}
	
	function addon(html) {
		$('#addonContainer').html(html);
	}
	

	$('#btnConfirm').click(function(){
		var $btn = $(this);
		$btn.button('loading');
		//console.log($('input[name="order_broadband_type"]:checked').val());
		
		var url = '${ctx}/order/personal';
		var customer = {
			address: $('#address').val()
			, serviceAvailable: ${customer.serviceAvailable}
			, cellphone: $('#cellphone').val()
			, email: $('#email').val()
			, title: $('#title').val()
			, first_name: $('#first_name').val()
			, last_name: $('#last_name').val()
			, customerOrder: {
				order_broadband_type: $('input[name="order_broadband_type"]:checked').val()
				, hardwares: []
			}
			, customer_type: 'personal'
		};
		
		if (customer.customerOrder.order_broadband_type == 'transition') {
			customer.customerOrder.transition_provider_name = $('#customerOrder\\.transition_provider_name').val();
			customer.customerOrder.transition_account_holder_name = $('#customerOrder\\.transition_account_holder_name').val();
			customer.customerOrder.transition_account_number = $('#customerOrder\\.transition_account_number').val();
			customer.customerOrder.transition_porting_number = $('#customerOrder\\.transition_porting_number').val();
		}
		
		
		$('input[data-hname]:checked').each(function(){
			var $elem = $(this);
			var hardware = {
				hardware_name: $elem.attr('data-hname')
				, hardware_price: $elem.attr('data-price')
			};
			customer.customerOrder.hardwares.push(hardware);
		});
		
		//console.log("customer request:");
		//console.log(customer);
		
		$.ajax({
			type: 'post'
			, contentType:'application/json;charset=UTF-8'         
	   		, url: url
		   	, data: JSON.stringify(customer)
		   	, dataType: 'json'
		   	, success: function(json){
				if (json.hasErrors) {
					$.jsonValidation(json, 'right');
				} else {
						
					//console.log("customer response:");
					//console.log(json.model);
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
<jsp:include page="footer-end.jsp" />