<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
	
	<ul class="panel panel-success nav nav-pills nav-justified hidden-xs hidden-sm"><!-- nav-justified -->
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
			<div class="col-md-9 col-sm-12 col-xs-12">
				
				<!-- application form -->
				<c:choose>
					<c:when test="${orderPlan.plan_group == 'plan-no-term'}">
						<div class="well well-sm text-danger">
							<p>
								All No-Term-Plans won't provide free router, 
								you can purchase a router which had been listed below. 
							</p>
							<p>
								You can get a free router from <a href="${ctx }/plans/plan-term/personal">Click Here</a>.
							</p>
						</div>
					</c:when>
					<c:when test="${orderPlan.plan_group == 'plan-topup'}">
						<div class="well well-sm text-danger">
							<p>
								All Top-Up-Plans won't provide free router, 
								you can purchase a router which had been listed below. 
							</p>
							<p>
								You can get a free router from <a href="${ctx }/plans/plan-term/personal">Click Here</a>.
							</p>
						</div>
					</c:when>
					<c:when test="${orderPlan.plan_group == 'plan-term'}">
						<div class="well well-sm text-danger">
							<p>
								All Term-Plans will provide you a free router, 
								You don't need to choose a router which had been listed below. 
							</p>
							<p>
								We already add the free router to your cart.
							</p>
						</div>
					</c:when>
				</c:choose>
				
				<div class="panel panel-success">
				
					<div class="panel-heading">
						<h6 class="panel-title">
							<a data-toggle="collapse" data-toggle="collapse" data-parent="#accordion" href="#application">
								Your Application Form <span class="text-danger hidden-xs hidden-sm">(All Fields Required)</span>
							</a>
						</h6>
					</div>
					
					<div id="application" class="panel-collapse collapse in">
						<div class="panel-body">
						
							<!-- customer address -->
							<h4 class="text-success">Your Address</h4>
							<hr/>
							
							<div class="form-group">
								<label for="address" class="control-label col-md-4">Your Address <span class="text-danger">(*)</span></label>
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
								<label for="cellphone" class="control-label col-md-4">Your Mobile <span class="text-danger">(*)</span></label>
								<div class="col-md-4">
									<input type="text" id="cellphone" name="cellphone" value="${customer.cellphone }"class="form-control" placeholder="e.g.: 0210800123" data-error-field />
								</div>
							</div>
							<div class="form-group">
								<label for="email" class="control-label col-md-4">Your Email <span class="text-danger">(*)</span></label>
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
										<%-- <li>
											<input type="radio" name="order_broadband_type" 
												${customer.customerOrder.order_broadband_type=='jackpot'?'checked="checked"':'' } value="jackpot"/>
											&nbsp; <strong>Get a new broadband connection and an new Phone Jack installation and activation charge NZ$ <fmt:formatNumber value="${orderPlan.jackpot_fee }" type="number" pattern="#,##0" /></strong>
										</li> --%>
									</ul>
								</div>
							</div>
							
							
							<!-- Transition Information -->
							<div id="transitionContainer" >
							
								<hr/>
								<h4 class="text-success">Transition Information</h4>
								<hr/>
								
								<div class="well">
									<p>
										Please ensure the information you provided is correct; Otherwise, your order may be cancelled automatically.
									</p>
									<p>
										If you need to amend the information your provided, feel free to contact us.
									</p>
								</div>
								
								<div class="form-group">
									<label class="control-label col-md-4">Current Provider <span class="text-danger">(*)</span></label>
									<div class="col-md-4">
										<input type="text" id="customerOrder.transition_provider_name" name="customerOrder.transition_provider_name" value="${customer.customerOrder.transition_provider_name }" class="form-control" data-error-field/>
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-4">Account Holder Name <span class="text-danger">(*)</span></label>
									<div class="col-md-4">
										<input type="text" id="customerOrder.transition_account_holder_name" name="customerOrder.transition_account_holder_name" value="${customer.customerOrder.transition_account_holder_name }" class="form-control" data-error-field/>
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-4">Current Account Number <span class="text-danger">(*)</span></label>
									<div class="col-md-4">
										<input type="text" id="customerOrder.transition_account_number" name="customerOrder.transition_account_number" value="${customer.customerOrder.transition_account_number }" class="form-control" data-error-field/>
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-4">Porting Home Land-Line Number</label>
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
								<div class="col-md-2">
									<select name="title" id="title" class="selectpicker show-tick form-control">
										<option value="Mr" ${customer.title=='Mr'?'selected="selected"':''}>Mr</option>
										<option value="Mrs" ${customer.title=='Mrs'?'selected="selected"':''}>Mrs</option>
										<option value="Ms" ${customer.title=='Ms'?'selected="selected"':''}>Ms</option>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label for="first_name" class="control-label col-md-4">First name <span class="text-danger">(*)</span></label>
								<div class="col-md-4">
									<input type="text" id="first_name" name="first_name" value="${customer.first_name }" class="form-control" data-error-field />
								</div>
							</div>
							<div class="form-group">
								<label for="last_name" class="control-label col-md-4">Last name <span class="text-danger">(*)</span></label>
								<div class="col-md-4">
									<input type="text" id="last_name" name="last_name" value="${customer.last_name }" class="form-control" data-error-field />
								</div>
							</div>
							
							
							<!-- For ID Purpose Only -->
							
							<hr/>
							<h4 class="text-success">For ID Purpose Only</h4>
							<hr/>
							
							<div class="form-group">
								<label for="identity_type" class="control-label col-md-4">Identity Type</label>
								<div class="col-md-3">
									<select name="identity_type" id="identity_type" class="selectpicker show-tick form-control">
										<option value="Driver Licence" ${customer.identity_type=='Driver Licence'?'selected="selected"':''}>Driver Licence</option>
										<option value="Passport" ${customer.identity_type=='Passport'?'selected="selected"':''}>Passport</option>
										<option value="18+" ${customer.identity_type=='18+'?'selected="selected"':''}>18+</option>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label for="identity_number" class="control-label col-md-4">Identity Number <span class="text-danger">(*)</span></label>
								<div class="col-md-4">
									<input type="text" id="identity_number" name="identity_number" value="${customer.identity_number }" class="form-control" data-error-field />
								</div>
							</div>
						</div>
					
					</div>
				</div>
				
				<div class="well well-sm text-danger">
					<div class="row">
						<div class="col-md-7">
							<p>
								P.S: We've chosen fastway as our logistic supplier.
							</p>
							<p>
								Please take attention to your mailbox, if you are not available to sign the parcel.
							</p>
							<p>
								Please be paticent for your installation. It takes approximately 3 ~ 7 days, thanks.
							</p>
						</div>
						<div class="col-md-5">
							<a href="http://www.fastway.co.nz" target="_black">
								<img alt="fastway" src="${ctx }/public/bootstrap3/images/logofastway.jpg" />
							</a>
						</div>
					</div>
					
					
				</div>
						
				<!-- hardware -->
				
				<c:if test="${fn:length(hardwares) > 0}">
					<div class="panel panel-success">
					
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" data-toggle="collapse" data-parent="#accordion" href="#hardware">
									Add-ons: Hardware
								</a>
							</h4>
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
				
				<div class="panel panel-success hidden-lg hidden-md">
					<div class="panel-body">
						<button type="button" class="btn btn-success btn-lg btn-block" id="m_btnConfirm">Confirm</button>
					</div>
				</div>
					
			</div>
			
			<!-- order-modal -->
			<div class="col-md-3 hidden-xs hidden-sm" >
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
<script type="text/javascript">
(function($) {

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
		$('#m_btnConfirm').click(confirm);
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
		var url = '${ctx}/order/personal';
		var customer = {
			address: $('#address').val()
			, serviceAvailable: ${customer.serviceAvailable}
			, cellphone: $('#cellphone').val()
			, email: $('#email').val()
			, title: $('#title').val()
			, first_name: $('#first_name').val()
			, last_name: $('#last_name').val()
			, identity_type: $('#identity_type').val()
			, identity_number: $('#identity_number').val()
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
		}); //console.log("customer request:"); console.log(customer);
		
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