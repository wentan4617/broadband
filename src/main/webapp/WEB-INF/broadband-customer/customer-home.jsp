<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>  
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<jsp:include page="header.jsp" />
<jsp:include page="alert.jsp" />

<style>
.personal-info li{
	padding:5px 40px;
}
</style>
<div class="container">
	<div class="row">
		<div class="col-md-3">
			<jsp:include page="sidebar.jsp" />
		</div>
		<div class="col-md-9">
			<div class="panel panel-success">
				<div class="panel-heading">
					<h3 class="panel-title">
						Home
					</h3>
				</div>
				<div class="panel-body">
					<div class="page-header" style="margin-top: 0;">
						<h3 class="text-success">
							Personal Information 
						</h3>
					</div>
					<div class="row">
						<div class="col-md-5">
							<ul class="list-unstyled personal-info">
								<li><strong class="text-info">${customerSession.login_name }</strong></li>
								<li><strong class="text-info">${customerSession.first_name }&nbsp;${customerSession.last_name }</strong></li>
								<li><strong class="text-info"><a href="mailto:#">${customerSession.email }</a></strong></li>
								<li><strong class="text-info">${customerSession.cellphone }</strong></li>
								<li><strong class="text-info">${customerSession.address }</strong></li>
							</ul>
						</div>
						<div class="col-md-7">
							<div class="row">
								<div class="col-md-6">
									<strong class="text-info">
										Current Credit:
									</strong> 
									<span class="glyphicon glyphicon-star"></span>&nbsp;NZ$ 
									<strong class="text-success">
										<fmt:formatNumber value="${customerSession.balance==null?0:customerSession.balance }" type="number" pattern="#,##0.00" />										
									</strong>
								</div>
								<div class="col-md-4">
									<div class="btn-group">
										<button type="button" class="btn btn-success dropdown-toggle" data-toggle="dropdown">
									   		Top Up <span class="caret"></span>
									  	</button>
									  	<ul class="dropdown-menu" data-role="menu">
									    	<li><a href="${ctx }/customer/topup"><span class="glyphicon glyphicon-credit-card"></span>&nbsp;&nbsp;Credit Card</a></li>
									    	<li><a href="javascript:void(0);" data-name="pay_way_by" data-way="voucher"><span class="glyphicon glyphicon-tags"></span>&nbsp;&nbsp;Voucher</a></li>
									  	</ul>
									</div>
								</div>
							</div>
							<hr/>
							<div class="row">
								<div class="col-md-6">
									<strong class="text-info">
										Invoice Balance:
									</strong> 
									NZ$ 
									<strong class="text-success">
										<fmt:formatNumber value="${customerSession.customerInvoice.balance==null?0:customerSession.customerInvoice.balance }" type="number" pattern="#,##0.00" />
									</strong>
								</div>
								<div class="col-md-4">
									<a href="${ctx }/customer/billing/1" class="btn btn-success btn-block" >View Invoice</a>
								</div>
							</div>
							<hr/>
							<div class="row">
								<div class="col-md-6">
									<c:if test="${customerSession.customerOrders[0].ordering_form_pdf_path != null && customerSession.customerOrders[0].ordering_form_pdf_path != '' }">
										<a target="_blank" href="${ctx }/customer/home/ordering-form/pdf/download" class="btn btn-success">
											<span class="glyphicon glyphicon-floppy-save"></span> Download Ordering Form
										</a>
									</c:if>
								</div>
								<div class="col-md-4">
									<c:choose>
										<c:when test="${customerSession.customerOrders[0].receipt_pdf_path != null && customerSession.customerOrders[0].receipt_pdf_path != '' }">
											<a target="_blank" href="${ctx }/customer/home/receipt/pdf/download" class="btn btn-success">
												<span class="glyphicon glyphicon-floppy-save"></span> Download Ordering Receipt
											</a>
										</c:when>
										<c:otherwise>
											<form action="${ctx }/customer/ordering-form/checkout" method="post" id="orderingForm">
												<button type="submit" class="btn btn-success btn-block" >Online Payment</button>
											</form>
										</c:otherwise>
									</c:choose>
									
								</div>
							</div>
						</div>
					</div>
					
					<hr />
					<div class="page-header" style="margin-top: 0;">
						<h3 class="text-success">
							Plan Information 
						</h3>
					</div>
					<c:if test="${fn:length(customerOrders) > 0}">
						<c:forEach var="co" items="${customerOrders}">
							<div class="well">
								<ul class="list-unstyled personal-info">
									<c:if test="${fn:length(co.customerOrderDetails) > 0 }">
										<c:forEach var="cod" items="${co.customerOrderDetails }">
											<c:if test="${fn:contains(cod.detail_type, 'plan-')}">
												<li><hr style="margin:0;"/></li>
												<li><h4 class="text-info" style="margin:0;">${cod.detail_name }</h4></li>
												<li><hr style="margin:0;"/></li>
											</c:if>
											<c:if test="${fn:contains(cod.detail_type, 'hardware-')}">
												<li><hr style="margin:0;"/></li>
												<li>
													<h4 class="text-info" style="margin:0;">
														${cod.detail_name }&nbsp;&nbsp;&nbsp;
														<c:if test="${cod.is_post == 1 }">
															<span class="label label-warning">${cod.track_code }</span>
														</c:if>
														<c:if test="${cod.is_post == 0 }">
															
														</c:if>
													</h4>
												</li>
											</c:if>
										</c:forEach>
									</c:if>
								</ul>
							</div>
						</c:forEach>
					</c:if>
					<c:if test="${fn:length(customerOrders) < 0 }">
					</c:if>
				</div>
			</div>
		</div>
	</div>
</div>

		
<!-- confirmPayWay Modal -->
<div class="modal fade" id="confirmPayWayModal" tabindex="-1" role="dialog" aria-labelledby="confirmPayWayModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="confirmPayWayModalLabel">
					<strong data-name="confirm_payway_modal_title"></strong>
				</h4>
			</div>
			<div class="modal-body">
				<div class="form-group">
					<label for="pin_number" class="control-label col-md-6">Pin Number:</label>
					<div class="col-md-6">
						<input id="pin_number" name="pin_number" class="form-control input-sm" type="text" placeholder="Pin Number"/>
					</div>
				</div>
				<br/>
				<div class="form-group">
					<p>
						<strong data-name="confirm_payway_modal_content"></strong>
					</p>
				</div>
			</div>
			<div class="modal-footer">
				<a href="javascript:void(0);" class="btn btn-success col-md-12" data-name="confirm_payway_modal_btn" data-dismiss="modal"></a>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal -->



<jsp:include page="footer.jsp" />
<jsp:include page="script.jsp" />
<script>
(function($){
	// BEGIN PayWay
	$('a[data-name="pay_way_by"]').click(function(){
		var pay_way = $(this).attr('data-way');
		console.log(pay_way);
		$('a[data-name="confirm_payway_modal_btn"]').attr('data-way', pay_way);
		if(pay_way == 'voucher'){
			$('strong[data-name="confirm_payway_modal_title"]').html('Topup your account amount credit by voucher?');
			$('strong[data-name="confirm_payway_modal_content"]').html('Voucher\'s face value will be automatically added into your account\'s credit.<br/>');
			$('a[data-name="confirm_payway_modal_btn"]').html('Confirm to use Voucher');
		}
		$('#confirmPayWayModal').modal('show');
	});
	// Confirm PayWay
	$('a[data-name="confirm_payway_modal_btn"]').click(function(){
		var pay_way = $(this).attr('data-way');
		var url = '';
		if(pay_way == 'voucher'){
			var data = {
				customer_id : '${customerSession.id}'
				,pin_number : $('input[name="pin_number"]').val()
			};
			url = '${ctx}/customer/account/topup/voucher';
		}
		
		$.post(url, data, function(json){
			$.jsonValidation(json, 'right');
		}, 'json');
	});
	// END PayWay
})(jQuery);
</script>
<jsp:include page="footer-end.jsp" />