<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<jsp:include page="header.jsp" />
<jsp:include page="alert.jsp" />

<div class="container">
	<div class="row">
		<div class="col-md-3">
			<jsp:include page="sidebar.jsp" />
		</div>
		<div class="col-md-9">
			<div class="panel panel-success customer-panel-height">
				<div class="panel-heading">
					<h4 class="panel-title">View Billing</h4>
				</div>
				<div class="table-responsive" id="invoice_table">
				</div>
			</div>
		</div>
	</div>
</div>

<form action="${ctx }/customer/invoice/checkout" method="post" id="balanceForm">
	<input type="hidden" id="invoice_id" name="invoice_id"/>
</form>

		
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
				<div class="form-group" data-name="pin_number_input" style="display:none;">
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

<script type="text/html" id="customer_billing_tmpl">
<jsp:include page="customer-billing.html" />
</script>

<jsp:include page="footer.jsp" />
<jsp:include page="script.jsp" />
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/jTmpl.js"></script>
<script type="text/javascript">
(function($){
	
	function doBilling(pageNo) {
		var url = '${ctx}/customer/billing/' + pageNo;
		$.get(url, function(json){
			json.model.ctx = '${ctx}';
			json.model.customer_type = '${customerSession.customer_type}';
			console.log(json.model);
	   		var $table = $('#invoice_table');
			$table.html(tmpl('customer_billing_tmpl', json.model));
			$table.find('tfoot a').click(function(){
				doPage($(this).attr('data-pageNo'));
			});
			

			$('a[data-balance]').click(function(){
				$('#invoice_id').val($(this).attr('data-id'));
				$('#balanceForm').submit();
			});
			// BEGIN PayWay
			$('a[data-name="pay_way_by"]').click(function(){
				var pay_way = $(this).attr('data-way');
				var invoice_id = $(this).attr('data-id');
				$('a[data-name="confirm_payway_modal_btn"]').attr('data-way', pay_way);
				$('a[data-name="confirm_payway_modal_btn"]').attr('data-id', invoice_id);
				if(pay_way == 'voucher'){
					$('strong[data-name="confirm_payway_modal_title"]').html('Use Voucher to pay this invoice?');
					$('strong[data-name="confirm_payway_modal_content"]').html('If the voucher\'s face value is greater than invoice\'s balance then the surplus will be automatically add into your account\'s credit.<br/>');
					$('a[data-name="confirm_payway_modal_btn"]').html('Confirm to use Voucher');
					$('div[data-name="pin_number_input"]').css('display', '');
				} else if(pay_way == 'account-credit'){
					$('strong[data-name="confirm_payway_modal_title"]').html('Use Your Account Credit to pay this invoice?');
					$('strong[data-name="confirm_payway_modal_content"]').html('If your account credit is greater than or equal to invoice\'s balance then the surplus will be automatically return back to your account\'s credit. If your account\'s credit is less than the invoice\'s balance then decrease the invoice balance and status will be set not_pay_off.<br/>');
					$('a[data-name="confirm_payway_modal_btn"]').html('Confirm to use Account Credit');
					$('div[data-name="pin_number_input"]').css('display', 'none');
				}
				$('#confirmPayWayModal').attr('data-id', invoice_id);
				$('#confirmPayWayModal').modal('show');
			});
			// Confirm PayWay
			$('a[data-name="confirm_payway_modal_btn"]').click(function(){
				var pay_way = $(this).attr('data-way');
				var url = '';
				if(pay_way == 'voucher'){
					var data = {
						invoice_id : $(this).attr('data-id')
						,pin_number : $('input[name="pin_number"]').val()
					};
					url = '${ctx}/customer/invoice/defray/voucher';
				} else if(pay_way == 'account-credit'){
					var data = {
						invoice_id : $(this).attr('data-id')
					};
					url = '${ctx}/customer/invoice/defray/account-credit';
				}
				
				$.post(url, data, function(json){
					$.jsonValidation(json, 'right');
					doBilling(1);
				}, 'json');
			});
			// END PayWay
		});
	}
	doBilling(1);
	
})(jQuery);
</script>
<jsp:include page="footer-end.jsp" />