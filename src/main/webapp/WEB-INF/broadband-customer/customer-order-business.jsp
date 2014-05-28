<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
		<li class="">
			<a class="btn-lg">
				1. Choose Plan
				<span class="glyphicon glyphicon-hand-right pull-right"></span>
			</a>
		</li>
		<li class="">
			<a class="btn-lg">
				2. Check Your Address
				<span class="glyphicon glyphicon-hand-right pull-right" ></span>
			</a>
		</li>
		<li class="active">
			<a class="btn-lg">
				3. Fill Application Form
				<span class="glyphicon glyphicon-hand-right pull-right" ></span>
			</a>
		</li>
		<li class="">
			<a class="btn-lg">
				4. Review and Order
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
								Business Application Form <span class="text-danger"></span>
							</a>
						</h2>
					</div>
					
					<div id="application" class="panel-collapse collapse in">
						<div class="panel-body">
						
							<!-- customer address -->
							<h4 class="text-success">Group/Organization Address</h4>
							<hr/>
							
							<div class="form-group">
								<label for="address" class="control-label col-md-4">Business Address</label>
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

							<!-- Broadband Options -->
							<hr/>
							<h4 class="text-success">Broadband Options</h4>
							<hr/>
							
							<div class="form-group">
								<div class="col-md-12">
									<ul class="list-unstyled topup-list">
										<li>
											<input type="radio" name="order_broadband_type" value="transition"
												<c:if test="${customer.customerOrder.order_broadband_type=='transition' || customer.customerOrder.order_broadband_type==null}">
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
												${customer.customerOrder.order_broadband_type=='new-connection'?'checked="checked"':'' } value="new-connection"/>
											&nbsp; <strong>Get a new broadband connection on an existing (but inactive) Phone Jack charge NZ$ <fmt:formatNumber value="${orderPlan.plan_new_connection_fee }" type="number" pattern="#,##0" /></strong>
										</li>
										<li>
											<input type="radio" name="order_broadband_type" 
												${customer.customerOrder.order_broadband_type=='jackpot'?'checked="checked"':'' } value="jackpot"/>
											&nbsp; <strong>Get a new broadband connection and an new Phone Jack installation and activation charge NZ$ <fmt:formatNumber value="${orderPlan.jackpot_fee }" type="number" pattern="#,##0" /></strong>
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
									<label  class="control-label col-md-4">Current Provider</label>
									<div class="col-md-4">
										<input type="text" id="customerOrder.transition_provider_name" name="customerOrder.transition_provider_name" value="${customer.customerOrder.transition_provider_name }" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label  class="control-label col-md-4">Account Holder</label>
									<div class="col-md-4">
										<input type="text" id="customerOrder.transition_account_holder_name" name="customerOrder.transition_account_holder_name" value="${customer.customerOrder.transition_account_holder_name }" class="form-control" />
									</div>
								</div>
								<div class="form-group">
									<label  class="control-label col-md-4">Current Account Number</label>
									<div class="col-md-4">
										<input type="text" id="customerOrder.transition_account_number" name="customerOrder.transition_account_number" value="${customer.customerOrder.transition_account_number }" class="form-control" />
									</div>
								</div>
								<div class="form-group">
									<label  class="control-label col-md-4">Telephone Number</label>
									<div class="col-md-4">
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
								<label for="organization.org_name" class="control-label col-sm-4">Group/Organization Name</label>
								<div class="col-sm-6">
									<input type="text" name="org_name" id="organization.org_name" value="${customer.organization.org_name }" class="form-control" data-error-field/>
								</div>
							</div>
							<div class="form-group">
								<label for="organization.org_trading_name" class="control-label col-sm-4">Trading Name</label>
								<div class="col-sm-6">
									<input type="text" name="org_trading_name" id="organization.org_trading_name" value="${customer.organization.org_trading_name }" class="form-control" data-error-field/>
								</div>
							</div>
							<div class="form-group">
								<label for="organization.org_register_no" class="control-label col-sm-4">Registration No.</label>
								<div class="col-sm-6">
									<input type="text" name="org_register_no" id="organization.org_register_no" value="${customer.organization.org_register_no }" class="form-control" data-error-field/>
								</div>
							</div>
							<div class="form-group">
								<label for="organization.org_incoporate_date" class="control-label col-sm-4">Date Incorporated</label>
								<div class="col-sm-4">
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
								<label for="organization.holder_name" class="control-label col-sm-4">Full name</label>
								<div class="col-sm-6">
									<input type="text" name="holder_name" id="organization.holder_name" value="${customer.organization.holder_name }" class="form-control"  data-error-field/>
								</div>
							</div>
							<div class="form-group">
								<label for="organization.holder_job_title" class="control-label col-sm-4">Job title</label>
								<div class="col-sm-6">
									<input type="text" name="holder_job_title" id="organization.holder_job_title" value="${customer.organization.holder_job_title }" class="form-control" data-error-field/>
								</div>
							</div>
							<div class="form-group">
								<label for="organization.holder_phone" class="control-label col-sm-4">Phone Number</label>
								<div class="col-sm-6">
									<input type="text" name="holder_phone" id="organization.holder_phone" value="${customer.organization.holder_phone }" class="form-control" data-error-field/>
								</div>
							</div>
							<div class="form-group">
								<label for="organization.holder_email" class="control-label col-sm-4">Email Address</label>
								<div class="col-sm-6">
									<input type="text" name="holder_email" id="organization.holder_email" value="${customer.organization.holder_email }" class="form-control" data-error-field/>
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
			
			<!-- order-modal -->
			<div class="col-md-3" >
				<div data-spy="affix" data-offset-top="150" id="order-result"></div>
			</div>
			
		</div>
	</form>
</div>

<div id="map_canvas" style="width:720px;height:600px;display:none;"></div>
<script type="text/html" id="order_modeal_tmpl">
<jsp:include page="order-modal.html" />
</script>

<jsp:include page="footer.jsp" />
<jsp:include page="script.jsp" />
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/jTmpl.js"></script>
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/icheck.min.js"></script>
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/bootstrap-select.min.js"></script>
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/bootstrap-datepicker.js"></script>
<script type="text/javascript">
(function($) {
	
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
	
	var plan = {
		plan_name: '${orderPlan.plan_name}'
		, plan_group: '${orderPlan.plan_group}'
		, plan_class: '${orderPlan.plan_class}'
		, plan_prepay_months: new Number(${orderPlan.plan_prepay_months})
		, plan_new_connection_fee: new Number(${orderPlan.plan_new_connection_fee})
		, plan_price: new Number(${orderPlan.plan_price})
		, topup_fee: new Number(${orderPlan.topup.topup_fee})
		, pstn_count: new Number(${orderPlan.pstn_count})
		, term_period: new Number(${orderPlan.term_period})
		, jackpot_fee: new Number(${orderPlan.jackpot_fee})
		, transition_fee: new Number(${orderPlan.transition_fee})
	};
	
	var price = {
		plan_price: 0
		, service_price: 0
		, addons_price: 0
	};
	
	var modal = {
		plan: plan
		, price: price
		, back_url: ''
		, ctx: '${ctx}'
		, addons: []
		, order_broadband_type:'${customer.customerOrder.order_broadband_type}' != '' ? '${customer.customerOrder.order_broadband_type}': 'transition'
	};
	
	function loadOrderModal() {
		$('#order-result').html(tmpl('order_modeal_tmpl', modal));
		$('#btnConfirm').click(confirm);
	}
	
	//loadOrderModal();
	
	$('input[name="order_broadband_type"]').on('ifChecked', function(){
		modal.order_broadband_type = this.value;
		if (this.value == "transition") {
			$('#transitionContainer').show('fast');
			price.service_price = plan.transition_fee;
		} else if (this.value == "new-connection") {
			$('#transitionContainer').hide('fast');
			price.service_price = plan.plan_new_connection_fee;
		} else if (this.value == 'jackpot') {
			$('#transitionContainer').hide('fast');
			price.service_price = plan.jackpot_fee;
		}
		loadOrderModal();
	});
	
	$('input[name="order_broadband_type"]:checked').trigger('ifChecked');
	
	$('input[data-name="addons"]').on('ifChecked', function(){
		var $ipt = $(this);
		var val = $ipt.val(); //console.log(val);
		var id = $ipt.attr('data-id');
		var unit_price = $ipt.attr('data-price');
		if (val != 'on') {
			$('#hp_' + id).text('');
			$('#none_' + id).text('[Subtract NZ$ ' + unit_price + ']');
		} else {
			$('#hp_' + id).text('[Add NZ$ ' + unit_price + ']');
			$('#none_' + id).text('');
		}
		price.addons_price = 0;
		modal.addons = [];
		$('input[data-name="addons"][data-hname]:checked').each(function(){
			var $addon = $(this);
			price.addons_price += Number($addon.attr('data-price'));
			modal.addons.push({ name: $addon.attr('data-hname'), price: Number($addon.attr('data-price')) });
		});
		loadOrderModal();
	});
	
	function confirm() {
		var $btn = $(this);
		var url = '${ctx}/order/business';
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
		}
		
		$('input[data-hname]:checked').each(function(){
			var $elem = $(this);
			var hardware = {
				hardware_name: $elem.attr('data-hname')
				, hardware_price: $elem.attr('data-price')
			};
			customer.customerOrder.hardwares.push(hardware);
		}); // console.log("customer request:"); console.log(customer);
		
		$btn.button('loading');
		$.ajax({
			type: 'post'
			, contentType:'application/json;charset=UTF-8'         
	   		, url: url
		   	, data: JSON.stringify(customer)
		   	, dataType: 'json'
		   	, success: function(json){
		   		if (!$.jsonValidation(json, 'right')) { //console.log("customer response:"); console.log(json.model);
					window.location.href = '${ctx}' + json.url;
				} 
		   	}
		}).always(function () {
			$btn.button('reset');
	    });
	}
	
})(jQuery);
</script>
<script src="http://maps.google.com/maps/api/js?sensor=false&libraries=places&region=NZ" type="text/javascript"></script>
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/autoCompleteAddress.js"></script>
<jsp:include page="footer-end.jsp" />