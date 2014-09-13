<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>  
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<jsp:include page="header.jsp" />

<style>
.personal-info li{
	padding:5px 40px;
}
.nav-pills>li.active>a, 
.nav-pills>li.active>a:hover, 
.nav-pills>li.active>a:focus {
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
		<li class="">
			<a class="btn-lg">
				3. Fill Application Form
				<span class="glyphicon glyphicon-hand-right pull-right" ></span>
			</a>
		</li>
		<li class="active">
			<a class="btn-lg">
				4. Review and Order
			</a>
		</li>
	</ul>
	
	<div class="panel panel-success">
		<div class="panel-heading">
			<h4 class="panel-title">
				<a data-toggle="collapse" data-toggle="collapse" data-parent="#customerOrderAccordion" href="#collapseOrderInfo">
					Please Review Application Information
				</a>
			</h4>
		</div>
		<div id="collapseOrderInfo" class="panel-collapse collapse in">
			<div class="panel-body">
			
				<div class="row">
					<div class="col-md-6 col-xs-12 col-sm-12">
						<h4 class="text-success">
							<c:if test="${customer.customer_type == 'personal' }">
								${customer.title } ${customer.first_name } ${customer.last_name }
							</c:if>
							<c:if test="${customer.customer_type == 'business' }">
								${customer.organization.org_name }
							</c:if>
						</h4>
					</div>
				</div>
				<div class="row">
					<div class="col-sm-6">
						<h4 class="text-success">
							${customer.address }
						</h4>
					</div>
					<div class="col-sm-6">
						<h4 class="text-success pull-right hidden-xs hidden-sm" >
							Order Date: 
							<strong class="text-info">
								<fmt:formatDate  value="${customer.customerOrder.order_create_date}" type="both" pattern="yyyy-MM-dd" />
							</strong>
						</h4>
						<h4 class="text-success hidden-md hidden-lg" >
							Order Date: 
							<strong class="text-info">
								<fmt:formatDate  value="${customer.customerOrder.order_create_date}" type="both" pattern="yyyy-MM-dd" />
							</strong>
						</h4>
					</div>
				</div>
				
				<c:if test="${customer.customer_type == 'business' }">
					<hr style="margin-top:0;"/>
					<h2>Business Information</h2>
					<hr style="margin-top:0;"/>
					<div class="row">
						<div class="col-sm-4"><strong>Organization Type</strong></div>
						<div class="col-sm-6"><strong class="text-info">${customer.organization.org_type }</strong></div>
					</div>
					<div class="row" style="margin-top:5px;">
						<div class="col-sm-4"><strong>Trading Name</strong></div>
						<div class="col-sm-6"><strong class="text-info">${customer.organization.org_trading_name }</strong></div>
					</div>
					<div class="row" style="margin-top:5px;">
						<div class="col-sm-4"><strong>Registration No.</strong></div>
						<div class="col-sm-6"><strong class="text-info">${customer.organization.org_register_no }</strong></div>
					</div>
					<div class="row" style="margin-top:5px;">
						<div class="col-sm-4"><strong>Date Incoporated</strong></div>
						<div class="col-sm-6">
							<strong class="text-info">
								<fmt:formatDate  value="${customer.organization.org_incoporate_date }" type="both" pattern="yyyy-MM-dd" />
							</strong>
						</div>
					</div>
					<hr /><!-- style="margin-top:0;" -->
					<h2>Account Holder Information</h2>
					<hr style="margin-top:0;"/>
					<div class="row" >
						<div class="col-sm-4"><strong>Full name</strong></div>
						<div class="col-sm-6"><strong class="text-info">${customer.organization.holder_name }</strong></div>
					</div>
					<div class="row" style="margin-top:5px;">
						<div class="col-sm-4"><strong>Job Title</strong></div>
						<div class="col-sm-6"><strong class="text-info">${customer.organization.holder_job_title }</strong></div>
					</div>
					<div class="row" style="margin-top:5px;">
						<div class="col-sm-4"><strong>Phone</strong></div>
						<div class="col-sm-6"><strong class="text-info">${customer.organization.holder_phone }</strong></div>
					</div>
					<div class="row" style="margin-top:5px;">
						<div class="col-sm-4"><strong>Email</strong></div>
						<div class="col-sm-6"><strong class="text-info">${customer.organization.holder_email }</strong></div>
					</div>
				</c:if>
				
				<c:if test="${customer.customer_type == 'personal' }">
					<hr /><!-- style="margin-top:0;" -->
					<h2>Personal Information</h2>
					<hr style="margin-top:0;"/>
					
					<div class="row" style="margin-top:5px;">
						<div class="col-sm-4"><strong>Mobile</strong></div>
						<div class="col-sm-6"><strong class="text-info">${customer.cellphone }</strong></div>
					</div>
					<div class="row" style="margin-top:5px;">
						<div class="col-sm-4"><strong>Email</strong></div>
						<div class="col-sm-6"><strong class="text-info">${customer.email }</strong></div>
					</div>
					<div class="row" style="margin-top:5px;">
						<div class="col-sm-4"><strong>Identity Type</strong></div>
						<div class="col-sm-6"><strong class="text-info">${customer.identity_type }</strong></div>
					</div>
					<div class="row" style="margin-top:5px;">
						<div class="col-sm-4"><strong>Identity Number</strong></div>
						<div class="col-sm-6"><strong class="text-info">${customer.identity_number }</strong></div>
					</div>
				</c:if>
				
				<c:if test="${customer.customerOrder.order_broadband_type == 'transition' }">
					<hr /><!-- style="margin-top:0;" -->
					<h2>Transition</h2>
					<hr style="margin-top:0;"/>
					<div class="row" >
						<div class="col-sm-4"><strong>Current Provider</strong></div>
						<div class="col-sm-6"><strong class="text-info">${customer.customerOrder.transition_provider_name }</strong></div>
					</div>
					<div class="row" style="margin-top:5px;">
						<div class="col-sm-4"><strong>Account Holder</strong></div>
						<div class="col-sm-6"><strong class="text-info">${customer.customerOrder.transition_account_holder_name }</strong></div>
					</div>
					<div class="row" style="margin-top:5px;">
						<div class="col-sm-4"><strong>Current Account Number</strong></div>
						<div class="col-sm-6"><strong class="text-info">${customer.customerOrder.transition_account_number }</strong></div>
					</div>
					<div class="row" style="margin-top:5px;">
						<div class="col-sm-4"><strong>Telephone Number</strong></div>
						<div class="col-sm-6"><strong class="text-info">${customer.customerOrder.transition_porting_number }</strong></div>
					</div>
				</c:if>
								
				<hr/>
				
				<div class="table-responsive">
				<table class="table">
					<thead>
						<tr>
							<th>Service / Product</th>
							<th>Data</th>
							<th>Term(mth)</th>
							<th>Monthly Charge</th>
							<th>Qty</th>
							<th>Subtotal</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="detail" items="${customer.customerOrder.customerOrderDetails }">
							<c:choose>
								<c:when test="${fn:contains(detail.detail_type, 'plan-') }">
									<tr>
										<td>
											${detail.detail_name }
										</td>
										<td>
											<c:choose>
												<c:when test="${detail.detail_data_flow < 0 }">Unlimited</c:when>
												<c:otherwise>${detail.detail_data_flow } GB</c:otherwise>
											</c:choose>
										</td>
										<td>${detail.detail_term_period }</td>
										<td><fmt:formatNumber value="${detail.detail_price }" type="number" pattern="#,##0.00" /></td>
										<td>${detail.detail_unit }</td>
										<td>
											<fmt:formatNumber value="${detail.detail_price * detail.detail_unit}" type="number" pattern="#,##0.00" />
											
										</td>
									</tr>
									<tr>
										<th>&nbsp;</th>
										<th>&nbsp;</th>
										<th>&nbsp;</th>
										<th>Unit Price</th>
										<th>Qty</th>
										<th>Subtotal</th>
									</tr>
								</c:when>
								<c:otherwise>
									<tr>
										<td>
											${detail.detail_name }&nbsp;
											<c:if test="${detail.detail_type == 'pstn' || detail.detail_type == 'voip'}">
												<c:if test="${detail.pstn_number != null && detail.pstn_number != '' }">
													<strong class="text-danger">(${detail.pstn_number })</strong>
												</c:if>
											</c:if>
										</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td><fmt:formatNumber value="${detail.detail_price }" type="number" pattern="#,##0.00" /></td>
										<td>${detail.detail_unit }</td>
										<td>
											<fmt:formatNumber value="${detail.detail_price * detail.detail_unit}" type="number" pattern="#,##0.00" />
											
										</td>
									</tr>
								</c:otherwise>
							</c:choose>
							
						</c:forEach>
					</tbody>
				</table>
				</div>
				
				
				<div class="row">
					<div class="col-md-4 col-md-offset-8">
					
						<table class="table">
							<tbody>
								<c:choose>
									<c:when test="${customer.customer_type == 'personal' }">
										<tr>
											<td>Total before GST</td>
											<td>
												NZ$ 
												<fmt:formatNumber value="${customer.customerOrder.order_total_price * (1 - 0.15)}" type="number" pattern="#,##0.00" />
											</td>
										</tr>
										<tr>
											<td>GST at 15% </td>
											<td>
												NZ$ 
												<fmt:formatNumber value="${customer.customerOrder.order_total_price * 0.15}" type="number" pattern="#,##0.00" />
											</td>
										</tr>
										<tr>
											<td><strong>Order Total</strong></td>
											<td>
												<strong class="text-success">
													NZ$ 
													<fmt:formatNumber value="${customer.customerOrder.order_total_price }" type="number" pattern="#,##0.00" />
												</strong>
											</td>
										</tr>
									</c:when>
									<c:when test="${customer.customer_type == 'business' }">
										<tr>
											<td>Order Price</td>
											<td>
												NZ$ 
												<fmt:formatNumber value="${customer.customerOrder.order_total_price}" type="number" pattern="#,##0.00" />
											</td>
										</tr>
										<tr>
											<td>Plus GST at 15% </td>
											<td>
												NZ$ 
												<fmt:formatNumber value="${customer.customerOrder.order_total_price * 0.15}" type="number" pattern="#,##0.00" />
											</td>
										</tr>
										<tr>
											<td><strong>Order Total</strong></td>
											<td>
												<strong class="text-success">
													NZ$ 
													<fmt:formatNumber value="${customer.customerOrder.order_total_price * 1.15 }" type="number" pattern="#,##0.00" />
												</strong>
											</td>
										</tr>
									</c:when>
								</c:choose>
								
								<tr id="voucherTR" style="display:none;">
									<td>Voucher Price</td>
									<td>
										<strong class="text-danger" id="vPrice"></strong>
									</td>
								</tr>
								<tr id="TAAV" style="display:none;">
									<td>Total After Appiled Voucher</td>
									<td>
										<strong class="text-success" id="TAAVPrice"></strong>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
				
				
				<c:if test="${orderPlan.plan_group == 'plan-no-term' 
					|| orderPlan.plan_group == 'plan-topup'
					|| (orderPlan.plan_group == 'plan-term' && customer.customer_type == 'personal')}">
				
				<hr>
				<h4 class="text-success">
					Would you like to add one Cyberpark Voucher ? Please click here
					<a href="javascript:void(0);" id="addVoucher">
						<span class="glyphicon glyphicon-plus"></span>
					</a>
				</h4>
				
				<div id="alertContainer"></div>
						
				<div id="tempAlertSuccessContainer" style="display:none;">
					<div id="alert-success" class="alert alert-success alert-dismissable fade in" style="display:none;">
						<button type="button" class="close" data-dismiss="alert" aria-hidden="true" >&times;</button>
						<span id="text-success"></span>
					</div>
				</div>
				<div id="tempAlertErrorContainer" style="display:none;">
					<div id="alert-error" class="alert alert-danger alert-dismissable fade in" style="display:none;">
						<button type="button" class="close" data-dismiss="alert" aria-hidden="true" >&times;</button>
						<span id="text-error"></span>
					</div>
				</div>
						
				<div id="voucherFormContainer">
					<c:set var="total_vprice" value="0"></c:set>
					<c:if test="${fn:length(customer.vouchers) > 0 }">
						<c:forEach var="v" items="${customer.vouchers }">
							<c:set var="total_vprice" value="${total_vprice + v.face_value }"></c:set>
							<div data-index>
								<hr>
								<form class="form-inline">
									<div class="form-group">
										<label for="serial_number">Voucher Card Number: ${v.serial_number } has been applied.</label>
									</div>
									<a href="javascript:void(0);" class="btn btn-success btn-sm" data-voucher-cancel 
										data-voucher-serial-number="${v.serial_number }"
										data-voucher-card-number="${v.card_number }"> 
										<span class="glyphicon glyphicon-remove"></span> Cancel
									</a>
								</form>
							</div>
						</c:forEach>
					</c:if>
				</div>
				
				</c:if>
				
				<hr>
				<div class="row">
					<div class="col-md-12 col-xs-12 col-sm-12">
						<label class="well checkbox-inline pull-right"><!-- -->
							<input type="checkbox" id="termckb" value="1" />
							<a class="btn btn-link btn-lg hidden-xs hidden-sm"  data-toggle="modal" data-target="#cyberParkTerm"> &lt;&lt; CyberPark Terms & Conditions &gt;&gt;</a>
							<a class="hidden-md hidden-lg" data-toggle="modal" data-target="#cyberParkTerm"> &lt;&lt; CyberPark Terms & Conditions &gt;&gt;</a>
						</label>
					</div>
				</div>
				<hr/>
				<div class="row">
					<div class="col-md-2 hidden-xs hidden-sm">
						<c:choose>
							<c:when test="${orderPlan.plan_group == 'plan-no-term' || orderPlan.plan_group == 'plan-term' }">
								<a href="${ctx }/order/${orderPlan.id}" class="btn btn-success btn-lg btn-block">Back</a>
							</c:when>
							<c:when test="${orderPlan.plan_group == 'plan-topup' }">
								<a href="${ctx }/order/${orderPlan.id}/topup/<fmt:formatNumber value="${orderPlan.topup.topup_fee}" type="number" pattern="##" />" class="btn btn-success btn-lg btn-block">Back</a>
							</c:when>
						</c:choose>
					</div>
					<div class="col-md-2 col-md-offset-8">
						<c:choose>
							<c:when test="${orderPlan.plan_group == 'plan-term' && customer.customer_type == 'business'}">
								<%-- <c:choose>
									<c:when test="${customer.customer_type == 'personal' }">
										<form class="form-horizontal" action="${ctx }/order/plan-term/personal/save" method="post" id="orderForm">
											<button type="submit" class="btn btn-success btn-lg btn-block" data-id="orderForm">Submit Order</button>
										</form>
									</c:when>
									<c:when test="${customer.customer_type == 'business' }">
										<form class="form-horizontal" action="${ctx }/order/plan-term/business/save" method="post" id="orderForm">
											<button type="submit" class="btn btn-success btn-lg btn-block" data-id="orderForm">Submit Order</button>
										</form>
									</c:when> 
								</c:choose> --%>
								<form class="form-horizontal" action="${ctx }/order/plan-term/business/save" method="post" id="orderForm">
									<button type="submit" class="btn btn-success btn-lg btn-block" data-id="orderForm">Submit Order</button>
								</form>
							</c:when>
							<c:otherwise>
								<form class="form-horizontal" action="${ctx }/order/checkout" method="post" id="checkoutForm">
									<!-- <button type="submit"  class="btn btn-success btn-lg btn-block" data-id="checkoutForm">Checkout</button> -->
									<div class="btn-group dropup btn-block">
										<button type="button" class="btn btn-success btn-lg btn-block dropdown-toggle" data-toggle="dropdown">
											Checkout <span class="caret"></span>
										</button>
										<ul class="dropdown-menu" >
											<li><a href="javascript:void(0);" id="online_payment">Online Payment</a></li>
											<li><a href="javascript:void(0);" id="bank_depoist">Bank Deposit</a></li>
										</ul>
									</div>
								</form>
							</c:otherwise>
						</c:choose>
						
					</div>
					
				</div>		
				
			</div>
		</div>
		
	</div>
	
</div>

<!-- Modal -->
<div class="modal fade" id="cyberParkTerm" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width:1000px;">
    	<div class="modal-content">
      		<div class="modal-header">
        		<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
        		<h4 class="modal-title" id="myModalLabel">CyberPark Terms & Conditions</h4>
      		</div>
      		<div class="modal-body">
      			<c:choose>
      				<c:when test="${orderPlan.plan_class=='business' }">
      					${company.tc_business_retails }
      				</c:when>
      				<%-- <c:when test="">
      					${company.tc_business_wifi }
      				</c:when> --%>
      				<c:when test="${orderPlan.plan_class=='personal' }">
      					${company.tc_personal }
      				</c:when>
      				<c:when test="${orderPlan.plan_type=='UFB' }">
      					${company.tc_ufb }
      				</c:when>
      				<c:otherwise>
      					${company.term_contracts }
      				</c:otherwise>
      			</c:choose>
        		
      		</div>
    	</div><!-- /.modal-content -->
  	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<script type="text/html" id="voucher_form_tmpl">
<jsp:include page="voucher-form.html" />
</script>

<script type="text/html" id="voucher_form_result_tmpl">
<jsp:include page="voucher-form-result.html" />
</script>

<jsp:include page="footer.jsp" />
<jsp:include page="script.jsp" />
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/jTmpl.js"></script>
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/icheck.min.js"></script>
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/spin.min.js"></script>
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/ladda.min.js"></script>
<script type="text/javascript">
(function(){
	
	var total_vprice = new Number(${total_vprice});
	var order_price = new Number(${customer.customer_type == 'personal' ? customer.customerOrder.order_total_price : customer.customerOrder.order_total_price * 1.15 });
	
	$(':checkbox').iCheck({
		checkboxClass : 'icheckbox_square-green',
		radioClass : 'iradio_square-green'
	});
	
	$('#online_payment').click(function(){
		if ($('#termckb').prop('checked')) {
			$('#checkoutForm').submit();
		} else {
			alert("You must agree to CyberPark terms, in order to continue to buy and register as a member.");
		}
	});
	
	$('#bank_depoist').click(function(){
		if ($('#termckb').prop('checked')) {
			var $form = $('#checkoutForm');
			$form.attr('action', '${ctx }/order/plan-term/personal/save');
			$form.submit();
		} else {
			alert("You must agree to CyberPark terms, in order to continue to buy and register as a member.");
		}
	});
	
	var i = 0;
	
	$('#addVoucher').click(function(){ // 
		obj = { index: i };
		$('#voucherFormContainer').append(tmpl('voucher_form_tmpl', obj));
		$('a[data-index]').off('click').click(function(){ // console.log(1);
			$(this).closest('div[data-index]').remove();
		});
		$('a[data-apply]').off('click').click(function(){ // console.log(parent);
			var parent = $(this).closest('div[data-index]');
			var index = $(this).attr('data-index');
			var voucher = {
				serial_number: parent.find("#serial_number" + index).val()
				, card_number: parent.find("#card_number" + index).val()
				, index: index
			}; // console.log(voucher);
			var l = Ladda.create(this);
		 	l.start();
		 	$.post('${ctx}/plans/plan-topup/online-pay-by-voucher', voucher, function(json){
				if (!$.jsonValidation(json, 'top')) {
					var v = json.model;
					parent.empty();
					parent.append(tmpl('voucher_form_result_tmpl', v));
					cancelVoucher(); 
					total_vprice += v.face_value; //console.log(total_vprice);
					TAAV();
				}
			}).always(function() { l.stop(); });
		});
		i++;
	});
	
	function cancelVoucher() {
		$('a[data-voucher-cancel]').off('click').click(function(){
			var parent = $(this).closest('div[data-index]');
			var voucher = {
				serial_number: $(this).attr('data-voucher-serial-number')
				, card_number: $(this).attr('data-voucher-card-number')
			}; // console.log(voucher);
			var l = Ladda.create(this);
		 	l.start();
		 	$.post('${ctx}/plans/plan-topup/cancel-voucher-apply', voucher, function(json){
		 		if (!$.jsonValidation(json)) {
		 			var v = json.model;
		 			parent.remove();
		 			total_vprice -= v.face_value; //console.log(total_vprice);
		 			TAAV();
		 		}
		 	}).always(function() { l.stop(); });
		});
	}
	
	cancelVoucher();
	
	function TAAV() {
		if (total_vprice > 0) { //console.log('show');
			var TAAVPrice = (order_price - total_vprice).toFixed(2);
			$('#vPrice').text('NZ$ -' + total_vprice.toFixed(2));
			$('#TAAVPrice').text('NZ$ ' + TAAVPrice);
			$('#TAAV').show();
			$('#voucherTR').show();
		} else {
			$('#TAAV').hide();
			$('#voucherTR').hide();
		}
	}
	
	TAAV();
	
})(jQuery);
</script>
<jsp:include page="footer-end.jsp" />