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
				<c:if test="${fn:length(page.results) > 0 }">
					<table class="table" style="font-size:12px;">
						<c:forEach var="co" items="${customerSession.customerOrders}">
							<thead>
								<tr>
									<th colspan="10">
										<h4 class="text-success" style="margin:2px;">Order Serial:&nbsp;<small>${co.id}</small> </h4>
									</th>
								</tr>
								<tr>
									<th style="width:90px;">Date</th>
									<th style="width:95px;">Due Date</th>
									<th>Reference</th>
									<th style="text-align:right">Payable</th>
									<th style="text-align:right">Total Credit</th>
									<th style="text-align:right">Paid</th>
									<th style="text-align:right">Balance</th>
									<th>&nbsp;</th>
									<th>&nbsp;</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="invoice" items="${page.results}">
									<c:if test="${co.id == invoice.order_id}">
										<c:forEach var="tx" items="${transactionsList}">
											<c:if test="${tx.invoice_id==invoice.id}">
												<tr class="active">
													<td>${tx.transaction_date_str}</td>
													<td>&nbsp;</td>
													<td>${tx.card_name}</td>
													<td>&nbsp;</td>
													<td>&nbsp;</td>
													<td style="text-align:right"><strong>$ <fmt:formatNumber value="${tx.amount}" type="number" pattern="#,##0.00" /></strong></td>
													<td></td>
													<td>&nbsp;</td>
													<td>&nbsp;</td>
												</tr>
											</c:if>
										</c:forEach>
										<tr class="${(invoice.status=='unpaid' || invoice.status=='not_pay_off') ? 'danger' : ''}">
											<td>${invoice.create_date_str}</td>
											<td>
												&nbsp;
												<c:if test="${invoice.status == 'unpaid' || invoice.status == 'not_pay_off'}">
													<strong style="color: red;">${invoice.due_date_str}</strong>
												</c:if>
											</td>
											<td>Invoice#&nbsp;-&nbsp;${invoice.id}</td>
											<td style="text-align:right">
												<strong>$ <fmt:formatNumber value="${invoice.amount_payable}" type="number" pattern="#,##0.00" /></strong>
											</td>
											<td style="text-align:right">
												<strong>$ -<fmt:formatNumber value="${invoice.amount_payable - invoice.final_payable_amount}" type="number" pattern="#,##0.00" /></strong>
											</td>
											<td style="text-align:right">
												<strong>$ -<fmt:formatNumber value="${invoice.amount_paid}" type="number" pattern="#,##0.00" /></strong>
											</td>
											<td style="text-align:right">
												<strong>$ <fmt:formatNumber value="${invoice.balance}" type="number" pattern="#,##0.00" /></strong>
											</td>
											<td>
												<c:if test="${(invoice.status == 'unpaid' || invoice.status == 'not_pay_off')}">
													<div class="btn-group">
														<button type="button" class="btn btn-success btn-xs dropdown-toggle" data-toggle="dropdown">
															Make Payment <span class="caret"></span>
														</button>
														<ul class="dropdown-menu" role="menu">
															<li><a href="#" data-id="${invoice.id }" data-balance="${invoice.balance}"><span class="glyphicon glyphicon-credit-card"></span>&nbsp;&nbsp;Credit Card</a></li>
															<li><a href="javascript:void(0);" data-id="${invoice.id }" data-name="pay_way_by" data-way="voucher"><span class="glyphicon glyphicon-tags"></span>&nbsp;&nbsp;Voucher</a></li>
															<li><a href="javascript:void(0);" data-id="${invoice.id }" data-name="pay_way_by" data-way="account-credit"><span class="glyphicon glyphicon-star"></span>&nbsp;&nbsp;Account Credit</a></li>
														</ul>
													</div>
												</c:if>
											</td>
											<td>
												<c:if test="${invoice.invoice_pdf_path != null}">
													<a target="_blank"
														href="${ctx}/broadband-customer/billing/invoice/pdf/download/${invoice.id}"
														data-toggle="tooltip" data-placement="bottom" data-original-title="download invoice PDF">
														<span class="glyphicon glyphicon-cloud-download"></span>
													</a>
												</c:if>
											</td>
										</tr>
									</c:if>
								</c:forEach>
							</c:forEach>
						
						</tbody>
						
						<tfoot>
							<tr>
								<td colspan="11">
									<ul class="pagination">
										<c:forEach var="num" begin="1" end="${page.totalPage }" step="1">
											<li class="${page.pageNo == num ? 'active' : ''}">
												<a href="${ctx}/customer/billing/${num}">${num}</a>
											</li>
										</c:forEach>
									</ul>
								</td>
							</tr>
						</tfoot>
					</table>
				</c:if>
				<c:if test="${fn:length(page.results) <= 0 }">
					<div class="panel-body">
						<div class="alert alert-warning">No records have been found.</div>
					</div>
				</c:if>
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

<jsp:include page="footer.jsp" />
<jsp:include page="script.jsp" />
<script type="text/javascript">
(function($){
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
			$('a[data-name="confirm_payway_modal_btn"]').html('Confirm to use Voucher');
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
		}, 'json');
	});
	// END PayWay
})(jQuery);
</script>
<jsp:include page="footer-end.jsp" />