<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>  
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<jsp:include page="header.jsp" />
<jsp:include page="alert.jsp" />

<style>
.personal-info li{
	padding:5px 0;
}
</style>

<div class="container">
	<div class="row">
		<div class="col-md-3">
			<jsp:include page="sidebar.jsp" />
		</div>
		<div class="col-md-9 ">
			<div class="panel panel-success customer-panel-height">
				<div class="panel-heading">
					<h3 class="panel-title">Home</h3>
				</div>
				<div class="panel-body">
					<div class="page-header" style="margin-top: 0;">
						<h3 class="text-success">
							Account Information 
						</h3>
					</div>
					<div class="row">
						<div class="col-md-12">
							<ul class="list-unstyled personal-info" style="font-size:18px;">
								<li>
									<strong class="text-info">
										${customerSession.id }
									</strong>
									<button id="asPromotion" type="button" class="btn btn-xs btn-warning" data-container="body" data-toggle="popover" data-placement="right" data-content="This is your promotion number, if your friend when applying cyberpark broadband services to fill it, you can get a discount every month a certain amount each other."><span class="glyphicon glyphicon-question-sign"></span></button>
								</li>
								<li><strong class="text-info">${customerSession.first_name } ${customerSession.last_name }</strong></li>
								<li><strong class="text-info">${customerSession.company_name }</strong></li>
								<li><strong class="text-info"><a href="mailto:#">${customerSession.email }</a></strong></li>
								<li><strong class="text-info">${customerSession.cellphone }</strong></li>
								<li><strong class="text-info">${customerSession.address }</strong></li>
							</ul>
						</div>
					</div>
					
					<hr/>
					
					<div class="row">
						<div class="col-md-4">
							<strong class="text-info">Current Credit:</strong> 
							<span class="glyphicon glyphicon-star"></span> NZ$ 
							<strong class="text-success">
								<fmt:formatNumber value="${customerSession.balance == null ? 0 : customerSession.balance }" type="number" pattern="#,##0.00" />										
							</strong>
						</div>
						<div class="col-md-3">
							<div class="btn-group btn-block">
								<button type="button" class="btn btn-success btn-xs btn-block dropdown-toggle" data-toggle="dropdown">
							   		Top Up For Account <span class="caret"></span>
							  	</button>
							  	<ul class="dropdown-menu" data-role="menu">
							    	<li><a href="${ctx }/customer/topup"><span class="glyphicon glyphicon-credit-card"></span> Credit Card</a></li>
							    	<li><a href="javascript:void(0)" data-name="pay_way_by" data-way="voucher"><span class="glyphicon glyphicon-tags"></span> Voucher</a></li>
							  	</ul>
							</div>
						</div>
					</div>
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
	
	$('#asPromotion').popover();
})(jQuery);
</script>
<jsp:include page="footer-end.jsp" />