<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<jsp:include page="../header.jsp" />
<jsp:include page="../alert.jsp" />

<div class="container">
	<div class="row">
		<div class="col-md-12">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h4 class="panel-title">
						<a data-toggle="collapse" data-toggle="collapse"
							data-parent="#customerOrderAccordion" href="#collapseOrderInfo">
							Order Create
						</a>
					</h4>
				</div>
				<div id="collapseOrderInfo" class="panel-collapse collapse in">
					<div class="panel-body">
					
						<!-- plan  -->
						<h4 class="text-success">Create customer order</h4>
						<hr/>
						<form:form modelAttribute="customerOrder" id="orderForm" action="${ctx}/broadband-user/crm/customer/order/create" method="post" class="form-horizontal">
							<div class="form-group">
								<label for="plan.plan_group" class="control-label col-md-3">Plan Group</label>
								<div class="col-md-3">
									<form:select path="plan.plan_group" class="selectpicker show-tick form-control">
										<form:option value="">None</form:option>
										<form:option value="plan-topup">Plan Topup</form:option>
										<form:option value="plan-no-term">Plan No Term</form:option>
										<form:option value="plan-term">Plan Term</form:option>
									</form:select>
								</div>
							</div>
							<div class="form-group">
								<label for="plan.plan_name" class="control-label col-md-3">Plan Name</label>
								<div class="col-md-3">
									<form:select path="plan.plan_name" class="selectpicker show-tick form-control">
										<form:option value="">None</form:option>
									</form:select>
								</div>
							</div>
							<div class="form-group">
								<label for="order_broadband_type" class="control-label col-md-3">Broadband Option</label>
								<div class="col-md-3">
									<form:select path="order_broadband_type" class="selectpicker show-tick form-control">
										<form:option value="">None</form:option>
										<form:option value="new-connection">New Connection</form:option>
										<form:option value="transition">Transition</form:option>
									</form:select>
								</div>
							</div>
							<div class="form-group">
								<div class="col-md-3 col-md-offset-3">
									<div class="btn-group">
									  	<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
									    	Add-ons <span class="caret"></span>
									 	</button>
									  	<ul class="dropdown-menu" role="menu">
									    	<li><a href="javascript:void(0);" id="addHardware">Add Hardware</a></li>
									    	<li><a href="javascript:void(0);" id="addPSTN">Add PSTN</a></li>
									  	</ul>
									</div>
								</div>
							</div>
								
							<!-- hardware  -->
							<div id="hardwareContainer" style="display:none">
								<hr/>
								<h4 class="text-success">
									Add-ons Hardware
								</h4>
								<hr/>
								<c:forEach begin="0" end="4" step="1" varStatus="item">
									<div class="form-group" id="hardware_${item.index }" style="display:none;" data-hardware-enable="0">
										<div class="col-md-3"></div>
										<div class="col-md-5">
											<form:select path="customerOrderDetails[${item.index }].detail_name" class="selectpicker show-tick form-control" items="${hardwares }" itemLabel="hardware_name" itemValue="id" />
										</div>
										<div class="col-md-1">
											<form:input path="customerOrderDetails[${item.index }].detail_unit" class="form-control" value="1"/>
										</div>
										<div class="col-md-1">
											<a href="javascript:void(0);" class="btn btn-default" data-index="${item.index }" data-name="hardware_remove">
												<span class="glyphicon glyphicon-remove"></span>
											</a>
										</div>
									</div>
								</c:forEach>
								
							</div>
							
							<!-- PSTN -->
							<div class="PSTNContainer">
								<hr/>
								<h4 class="text-success">
									Add-ons PSTN
								</h4>
							</div>
							
							<hr/>
							<div class="form-group">
								<div class="col-md-4 col-md-offset-3">
									<a href="${ctx}/broadband-user/crm/customer/create/back" class="btn btn-success" id="back">Back</a>
									<a href="javascript:void(0);"class="btn btn-success" id="save">Save</a>
									<a href="javascript:void(0);"class="btn btn-success" id="next">Next</a>
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

<c:forEach var="plan" items="${plans}">
	
	<c:if test="${plan.plan_group == 'plan-topup' }"></c:if>
	<c:if test="${plan.plan_group == 'plan-no-term' }"></c:if>
	<c:if test="${plan.plan_group == 'plan-term' }"></c:if>
</c:forEach>
	
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
		topup_html += '<option value="' + plan.id + '">' + plan.plan_name + '</option>';
	});
	$.each(no_term_plans, function(i, plan){
		no_term_html += '<option value="' + plan.id + '">' + plan.plan_name + '</option>';
	});
	$.each(term_plans, function(i, plan){
		term_html += '<option value="' + plan.id + '">' + plan.plan_name + '</option>';
	});
	
	$('.selectpicker').selectpicker();
	
	$('#plan\\.plan_group').on("change", function(){
		var val = this.value;
		var $plan = $('#plan\\.plan_name');
		$('#plan\\.plan_name option').remove();;
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
		
	});
	
	$('#save').click(function(){
		$('#action').val('save');
		$('#orderForm').submit();
	});
	$('#next').click(function(){
		$('#action').val('next');
		$('#orderForm').submit();
	});
	
	$('#addHardware').click(function(){
		$('#hardwareContainer').css('display', 'block');
		$('div[data-hardware-enable="0"]').each(function(i){
			if (i == 0) {
				$(this).css("display", "block").attr('data-hardware-enable', '1');
			}
		});
	});
	
	$('a[data-name="hardware_remove"]').click(function(){
		var index = $(this).attr('data-index');
		$('#hardware_' + index).css("display", "none").attr('data-hardware-enable', '0');
		var len = $('div[data-hardware-enable="0"]').length;
		if (len == 5) {
			$('#hardwareContainer').css('display', 'none');
		}
	});
	
	
	

})(jQuery);
</script>
<jsp:include page="../footer-end.jsp" />