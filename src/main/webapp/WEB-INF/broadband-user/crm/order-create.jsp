<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<jsp:include page="../header.jsp" />
<jsp:include page="../alert.jsp" />

<div class="container">
	<div class="row">
		<div class="col-md-12">
			<div class="panel panel-success">
				<div class="panel-heading">
					<h4 class="panel-title">
						<a data-toggle="collapse" data-toggle="collapse" data-parent="#customerOrderAccordion" href="#collapseOrderInfo">
							Order Create
						</a>
					</h4>
				</div>
				<div id="collapseOrderInfo" class="panel-collapse collapse in">
					<div class="panel-body">
					
						<!-- plan  -->
						<h4 class="text-success">Create Customer Order</h4>
						<hr/>
						<form:form modelAttribute="customerOrder" id="orderForm" action="${ctx}/broadband-user/crm/customer/order/create" method="post" class="form-horizontal">
							<div class="form-group">
								<label for="plan.plan_group" class="control-label col-md-3">Plan Group</label>
								<div class="col-md-6">
									<form:select path="plan.plan_group" class="selectpicker show-tick form-control">
										<form:option value="">None</form:option>
										<form:option value="plan-topup">Plan Top-Up</form:option>
										<form:option value="plan-no-term">Plan No Term</form:option>
										<form:option value="plan-term">Plan Term</form:option>
									</form:select>
								</div>
								<p class="help-block">
									<form:errors path="plan.plan_group" cssErrorClass="error"/>
								</p>
							</div>
							
							<div class="form-group">
								<label for="plan.plan_name" class="control-label col-md-3">Plan Name</label>
								<div class="col-md-6">
									<form:select path="plan.id" class="selectpicker show-tick form-control">
										<form:option value="">None</form:option>
									</form:select>
								</div>
								<p class="help-block">
									<form:errors path="plan.id" cssErrorClass="error"/>
								</p>
							</div>
							
							<div class="form-group" id="topupContainer">
								<label for="" class="control-label col-md-3"></label>
								<div class="col-md-6">
									<form:select path="plan.topup.topup_fee" class="selectpicker show-tick form-control">
										<form:option value="20">Top up $ 20</form:option>
										<form:option value="30">Top up $ 30</form:option>
										<form:option value="40">Top up $ 40</form:option>
										<form:option value="50">Top up $ 50</form:option>
									</form:select>
								</div>
							</div>
							
							<div class="form-group">
								<label for="order_broadband_type" class="control-label col-md-3">Broadband Option</label>
								<div class="col-md-6">
									<form:select path="order_broadband_type" class="selectpicker show-tick form-control">
										<form:option value="">None</form:option>
										<form:option value="transition">Transition</form:option>
										<form:option value="new-connection">New Connection</form:option>
										<form:option value="jackpot">New Connection & Jackpot Installation</form:option>
									</form:select>
								</div>
								<p class="help-block">
									<form:errors path="order_broadband_type" cssErrorClass="error"/>
								</p>
							</div>
							
							<!-- Transition Information -->
							<div id="transitionContainer" style="display:none;">
								<hr/>
								<h4 class="text-success">Transition Information</h4>
								<hr/>
								<div class="form-group">
									<label for="" class="control-label col-md-3">Current Provider</label>
									<div class="col-md-4">
										<form:input path="transition_provider_name" class="form-control" />
									</div>
								</div>
								<div class="form-group">
									<label for="" class="control-label col-md-3">Account Holder</label>
									<div class="col-md-4">
										<form:input path="transition_account_holder_name" class="form-control" />
									</div>
								</div>
								<div class="form-group">
									<label for="" class="control-label col-md-3">Current Account Number</label>
									<div class="col-md-4">
										<form:input path="transition_account_number" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label for="" class="control-label col-md-3">Telephone Number</label>
									<div class="col-md-4">
										<form:input path="transition_porting_number" class="form-control" />
									</div>
								</div>
							
							</div>
								
							<!-- add-ons btn -->
							<hr/>
							<div class="form-group">
								<div class="col-md-2 col-md-offset-3">
									<div class="btn-group">
									  	<button type="button" class="btn btn-success btn-lg dropdown-toggle" data-toggle="dropdown">
									    	Add-ons <span class="caret"></span>
									 	</button>
									  	<ul class="dropdown-menu">
									    	<li><a href="javascript:void(0);" id="addHardware">Add Hardware</a></li>
									    	<li><a href="javascript:void(0);" id="addPSTN">Add PSTN</a></li>
									    	<li><a href="javascript:void(0);" id="addVoip">Add Voip</a></li>
									  	</ul>
									</div>
								</div>
							</div>
							
							<!-- hardware  -->
							<div id="hardwareContainer" style="display:none;">
								<hr/>
								<h4 class="text-success">
									Add-ons Hardware
								</h4>
								<hr/>
							</div>
							
							<!-- PSTN -->
							<div id="pstnContainer" style="display:none;">
								<hr/>
								<h4 class="text-success">
									Add-ons PSTN
								</h4>
								<hr/>
							</div>
							
							<!-- Voip -->
							<div id="voipContainer" style="display:none">
								<hr/>
								<h4 class="text-success">
									Add-ons Voip
								</h4>
								<hr/>
							</div>
							
							<hr/>
							<div class="form-group">
								<div class="col-md-3">
								</div>
								<div class="col-md-2">
									<a href="${ctx}/broadband-user/crm/customer/${customer.customer_type}/create/back" class="btn btn-success btn-lg btn-block" id="back">Back</a>								
								</div>
								<div class="col-md-2">
									<a href="javascript:void(0);"class="btn btn-success btn-lg btn-block" id="next">Next</a>
									<input type="hidden" name="action" id="action"/>
								</div>
							</div>
						</form:form>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
	
<jsp:include page="../footer.jsp" />
<jsp:include page="../script.jsp" />
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/bootstrap-select.min.js"></script>
<script type="text/javascript">
(function($){
	
	var topup_plans = [];
	var no_term_plans = [];
	var term_plans = [];
	
	<c:forEach var="plan" items="${plans}">
		var plan = {
			'id': ${plan.id}
			, 'plan_name': '${plan.plan_name}'
			, 'pstn_count': '${plan.pstn_count}'
			, 'pstn_rental_amount': '${plan.pstn_rental_amount}'
		}
		<c:if test="${plan.plan_group == 'plan-topup' }">
			topup_plans.push(plan);
		</c:if>
		<c:if test="${plan.plan_group == 'plan-no-term' }">
			no_term_plans.push(plan);
		</c:if>
		<c:if test="${plan.plan_group == 'plan-term' }">
			term_plans.push(plan);
		</c:if>
	</c:forEach>
	
	var none_html = '<option value="">None</option>';
	var topup_html = '<option value="">None</option>';
	var no_term_html = '<option value="">None</option>';
	var term_html = '<option value="">None</option>';
	$.each(topup_plans, function(i, plan){
		var sel = (plan.id == '${customerOrder.plan.id}' ? 'selected' : '');
		topup_html += '<option value="' + plan.id + '"' + sel + ' data-pstn="">' + plan.plan_name + '</option>';
	});
	$.each(no_term_plans, function(i, plan){
		var sel = (plan.id == '${customerOrder.plan.id}' ? 'selected' : '');
		no_term_html += '<option value="' + plan.id + '"' + sel + '>' + plan.plan_name + '</option>';
	});
	$.each(term_plans, function(i, plan){
		var sel = (plan.id == '${customerOrder.plan.id}' ? 'selected' : '');
		term_html += '<option value="' + plan.id + '"' + sel + '>' + plan.plan_name + '</option>';
	});
	
	$('.selectpicker').selectpicker();
	
	function fillPlanNameSelect(val) {
		var $plan = $('#plan\\.id');
		$('#plan\\.id option').remove();;
		if (val == "plan-topup") {
			$plan.append(topup_html);
		} else if (val == "plan-no-term") {
			$plan.append(no_term_html);
		} else if (val == "plan-term") {
			$plan.append(term_html);
		} else {
			$plan.append(none_html);
		}
		$('.selectpicker').selectpicker('refresh');
	}
	
	function fillTopupSelect(val, group) {
		//alert(val);
		if (val != "" && group == 'plan-topup') {
			$('#topupContainer').show();
		} else {
			$('#topupContainer').hide();
		}
	}
	
	fillPlanNameSelect($('#plan\\.plan_group').val());
	fillTopupSelect($('#plan\\.id').val(), $('#plan\\.plan_group').val());
	
	$('#plan\\.plan_group').on("change", function(){
		fillPlanNameSelect(this.value);
		fillTopupSelect($('#plan\\.id').val(), this.value);
	});
	
	$('#plan\\.id').on("change", function(){
		fillTopupSelect(this.value, $('#plan\\.plan_group').val());
	});
	
	$('#next').click(function(){
		$('#action').val('next');
		$('#orderForm').submit();
	});
	
	$('#addHardware').click(function(){
		$('#hardwareContainer').css('display', 'block');
		var index = $('div[data-enable]').length;
		var html = '';
		html += '<div class="form-group" id="hardware_' + index+ '" data-enable data-hardware-enable="1" >';
		html += '	<div class="col-md-3"></div>';
		html += '	<div class="col-md-5">';
		html += '		<select name="customerOrderDetails[' + index+ '].detail_name" class="selectpicker show-tick form-control" data-name="harewareSelect">';
		<c:forEach var="hd" items="${hardwares }">
		html += '<option value="${hd.hardware_name}" data-hd-price="${hd.hardware_price}" data-target="' + index+ '">${hd.hardware_name}</option>';
		</c:forEach>
		html += '		</select>';
		html += '		<input type="hidden" name="customerOrderDetails[' + index+ '].detail_type" value="hardware-router" id="dtype_' + index+ '"/>';
		html += '	</div>';
		html += '	<div class="col-md-2">';
		html += '		<div class="input-group">';
		html += '			<span class="input-group-addon">$</span>';
		html += '			<input type="text" name="customerOrderDetails[' + index+ '].detail_price" class="form-control" />';
		html += '		</div>';
		html += '	</div>';
		html += '	<div class="col-md-1">';
		html += '		<input type="text" name="customerOrderDetails[' + index+ '].detail_unit" class="form-control" value="1"/>';
		html += '	</div>';
		html += '	<div class="col-md-1">';
		html += '		<a href="javascript:void(0);" class="btn btn-default" data-index="' + index+ '" data-name="hardware_remove">';
		html += '			<span class="glyphicon glyphicon-remove"></span>';
		html += '		</a>';
		html += '	</div>';
		html += '</div>';
		$('#hardwareContainer').append(html);
		$('.selectpicker').selectpicker('refresh');
		var price = $('select[name="customerOrderDetails\\[' + index + '\\]\\.detail_name"] option:selected').attr('data-hd-price');
		$('input[name="customerOrderDetails\\[' + index + '\\]\\.detail_price"]').val(price);
	});
	
	$('#hardwareContainer').delegate('a[data-name="hardware_remove"]', 'click', function(){
		var index = $(this).attr('data-index');
		$('#hardware_' + index).attr('data-hardware-enable', '0').css("display", "none");
		$('#dtype_' + index).val('');
		var len = $('div[data-hardware-enable="1"]').length;
		if (len == 0) {
			$('#hardwareContainer').css('display', 'none');
		}
	});
	
	$('#hardwareContainer').delegate('select[data-name="harewareSelect"]', 'change', function(){
		var $option = $(this).find("option:selected");
		var price = $option.attr('data-hd-price');
		var indexTarget = $option.attr('data-target');
		$('input[name="customerOrderDetails\\[' + indexTarget + '\\]\\.detail_price"]').val(price);
		//alert(price);
	});
	
	var classz = '${customer.customer_type}';
	
	$('#addPSTN').click(function(){
		$('#pstnContainer').css('display', 'block');
		var index = $('div[data-enable]').length;
		var html = '';
		html += '<div class="form-group" id="pstn_' + index+ '" data-enable data-pstn-enable="1" >';
		html += '	<label class="control-label col-md-3"></label>';
		
		html += '	<div class="col-md-4">';
		if (classz == 'business') {
			html += '	<label class="control-label">Business Phone Line</label>';
			html += '	<input type="hidden" name="customerOrderDetails[' + index+ '].detail_name" value="Business Phone Line" />';
		} else if (classz == 'personal') {
			html += '	<label class="control-label">Home Phone Line</label>';
			html += '	<input type="hidden" name="customerOrderDetails[' + index+ '].detail_name" value="Home Phone Line" />';
		}
		html += '		<input type="hidden" name="customerOrderDetails[' + index+ '].detail_type" value="pstn" id="dtype_' + index+ '"/>';
		html += '	</div>';
		html += '	<div class="col-md-2">';
		html += '		<input type="text" name="customerOrderDetails[' + index+ '].pstn_number" class="form-control" placeholder="e.g.:5789941"/>';
		html += '	</div>';
		html += '	<div class="col-md-2">';
		html += '		<div class="input-group">';
		html += '			<span class="input-group-addon">$</span>';
		html += '			<input type="text" name="customerOrderDetails[' + index+ '].detail_price" class="form-control" value="45.00"/>';
		html += '		</div>';
		html += '	</div>';
		html += '	<div class="col-md-1">';
		html += '		<a href="javascript:void(0);" class="btn btn-default" data-index="' + index+ '" data-name="pstn_remove">';
		html += '			<span class="glyphicon glyphicon-remove"></span>';
		html += '		</a>';
		html += '	</div>';
		html += '</div>';
		$('#pstnContainer').append(html);
		$('.selectpicker').selectpicker('refresh');
	});
	
	$('#pstnContainer').delegate('a[data-name="pstn_remove"]', 'click', function(){
		var index = $(this).attr('data-index');
		$('#pstn_' + index).attr('data-pstn-enable', '0').css("display", "none");
		$('#dtype_' + index).val('');
		var len = $('div[data-pstn-enable="1"]').length;
		if (len == 0) {
			$('#pstnContainer').css('display', 'none');
		}
	});
	
	$('#addVoip').click(function(){
		$('#voipContainer').css('display', 'block');
		var index = $('div[data-enable]').length;
		var html = '';
		html += '<div class="form-group" id="voip_' + index+ '" data-enable data-voip-enable="1" >';
		html += '	<label for="" class="control-label col-md-3">Voip</label>';
		html += '	<div class="col-md-4">';
		html += '		<input type="text" name="customerOrderDetails[' + index+ '].detail_name" class="form-control" value="Voip" placeholder="Voip"/>';
		html += '		<input type="hidden" name="customerOrderDetails[' + index+ '].detail_type" value="voip" id="dtype_' + index+ '"/>';
		html += '	</div>';
		html += '	<div class="col-md-2">';
		html += '		<input type="text" name="customerOrderDetails[' + index+ '].pstn_number" class="form-control" placeholder="e.g.:5789941"/>';
		html += '	</div>';
		html += '	<div class="col-md-2">';
		html += '		<div class="input-group">';
		html += '			<span class="input-group-addon">$</span>';
		html += '			<input type="text" name="customerOrderDetails[' + index+ '].detail_price" class="form-control" value="0.00"/>';
		html += '		</div>';
		html += '	</div>';
		html += '	<div class="col-md-1">';
		html += '		<a href="javascript:void(0);" class="btn btn-default" data-index="' + index+ '" data-name="voip_remove">';
		html += '			<span class="glyphicon glyphicon-remove"></span>';
		html += '		</a>';
		html += '	</div>';
		html += '</div>';
		$('#voipContainer').append(html);
		$('.selectpicker').selectpicker('refresh');
	});
	
	$('#voipContainer').delegate('a[data-name="voip_remove"]', 'click', function(){
		var index = $(this).attr('data-index');
		$('#voip_' + index).attr('data-voip-enable', '0').css("display", "none");
		$('#dtype_' + index).val('');
		var len = $('div[data-voip-enable="1"]').length;
		if (len == 0) {
			$('#voipContainer').css('display', 'none');
		}
	});
	
	$('#order_broadband_type').on('change', function(){
		showTransitionContainer(this.value);
	});
	
	function showTransitionContainer(val) {
		if (val == "new-connection") {
			$('#transitionContainer').hide('fast');
		} else if (val == "transition"){
			$('#transitionContainer').show('fast');
		} else {
			$('#transitionContainer').hide('fast');
		}
	}
	
	showTransitionContainer($('#order_broadband_type').val());
	
	var a = "${fn:length(customerOrder.customerOrderDetails)}";

})(jQuery);
</script>
<jsp:include page="../footer-end.jsp" />