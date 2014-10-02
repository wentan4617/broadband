<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<jsp:include page="../header.jsp" />
<jsp:include page="../alert.jsp" />

<style type="text/css">
thead th {text-align:center;}
tbody td {text-align:center;}
</style>

<div class="container">
	<div class="row">
		<div class="col-md-12">
		
			<div class="panel panel-success">
				<div class="panel-heading">
					<h4 class="panel-title">
						<a data-toggle="collapse" data-toggle="collapse" data-parent="#accordion" href="#collapseOne">
							Transaction Query
						</a>
					</h4>
				</div>
				<div id="collapseOne" class="panel-collapse collapse in">
					<div class="panel-body">
						<form class="form-horizontal">
							<div class="form-group">
								<div class="col-md-1">
									<a href="${ctx}/broadband-user/billing/transaction/view/1/all" class="btn btn-default ${allActive}">
										All&nbsp;<span class="badge">${allSum}</span>
									</a>
								</div>
								<div class="col-md-3">
									<div class="input-group date" id="year_input_datepicker">
										<input id="year_input" class="btn btn-default" value="${year}"/>
										<span class="input-group-addon"><i class="glyphicon glyphicon-calendar">&nbsp;&nbsp;&nbsp;&nbsp;</i>&nbsp;YEAR</span>
									</div>
								</div>
								<div class="col-md-3">
									<div class="input-group date" id="month_input_datepicker">
										<input id="month_input" class="btn btn-default" value="${yearMonth}"/>
										<span class="input-group-addon"><i class="glyphicon glyphicon-calendar">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</i>&nbsp;MONTH</span>
									</div>
								</div>
							</div>
						</form>
					<hr/>
						<div class="btn-group btn-group">
							<a href="${ctx}/broadband-user/billing/transaction/view/1/visa/${year}${yearMonth}${all}" class="btn btn-default ${visaActive }">
								Visa&nbsp;<span class="badge">${visaSum}</span>
							</a>
							<a href="${ctx}/broadband-user/billing/transaction/view/1/masterCard/${year}${yearMonth}${all}" class="btn btn-default ${masterCardActive }">
								Master Card&nbsp;<span class="badge">${masterCardSum}</span>
							</a>
							<a href="${ctx}/broadband-user/billing/transaction/view/1/cash/${year}${yearMonth}${all}" class="btn btn-default ${cashActive }">
								Cash&nbsp;<span class="badge">${cashSum}</span>
							</a>
							<a href="${ctx}/broadband-user/billing/transaction/view/1/a2a/${year}${yearMonth}${all}" class="btn btn-default ${a2aActive }">
								A2A&nbsp;<span class="badge">${a2aSum}</span>
							</a>
							<a href="${ctx}/broadband-user/billing/transaction/view/1/ddpay/${year}${yearMonth}${all}" class="btn btn-default ${ddpayActive }">
								DDPay&nbsp;<span class="badge">${ddpaySum}</span>
							</a>
							<a href="${ctx}/broadband-user/billing/transaction/view/1/creditCard/${year}${yearMonth}${all}" class="btn btn-default ${creditCardActive }">
								Credit Card&nbsp;<span class="badge">${creditCardSum}</span>
							</a>
							<a href="${ctx}/broadband-user/billing/transaction/view/1/accountCredit/${year}${yearMonth}${all}" class="btn btn-default ${accountCreditActive }">
								Account Credit&nbsp;<span class="badge">${accountCreditSum}</span>
							</a>
							<a href="${ctx}/broadband-user/billing/transaction/view/1/cyberparkCredit/${year}${yearMonth}${all}" class="btn btn-default ${cyberparkCreditActive }">
								CyberPark Credit&nbsp;<span class="badge">${cyberparkCreditSum}</span>
							</a>
							<a href="${ctx}/broadband-user/billing/transaction/view/1/voucher/${year}${yearMonth}${all}" class="btn btn-default ${voucherActive }">
								Voucher&nbsp;<span class="badge">${voucherSum}</span>
							</a>
						</div>
					</div>
				</div>
			</div>
			<div class="panel panel-success">
				<div class="panel-heading">
					<h4 class="panel-title">
						<c:if test="${fn:length(page.results) > 0}">
							Transaction View
						</c:if>
					</h4>
				</div>
				<c:if test="${fn:length(page.results) > 0 }">
					<table class="table" style="font-size:12px;">
						<thead >
							<tr>
								<th><input type="checkbox" id="checkbox_txs_top" /></th>
								<th>Customer Id</th>
								<th>Order Id</th>
								<th>Invoice Id</th>
								<th>Transaction Date</th>
								<th>Transaction Type</th>
								<th>Cardholder Name</th>
								<th>Card Name</th>
								<th>Card Number</th>
								<th style="text-align:right;">Amount</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="tx" items="${page.results }">
								<tr>
									<td>
										<input type="checkbox" name="checkbox_txs" value="${tx.id}"/>
									</td>
									<td>
										<a target="_blank" href="${ctx}/broadband-user/crm/customer/edit/${tx.customer_id}">${tx.customer_id}</a>
									</td>
									<td>
										${tx.order_id }
									</td>
									<td>
										${tx.invoice_id }
									</td>
									<td>
										${tx.transaction_date_str }
									</td>
									<td>
										${tx.transaction_type }
									</td>
									<td>
										${tx.cardholder_name }
									</td>
									<td>
										${tx.card_name }
									</td>
									<td>
										${tx.card_number }
									</td>
									<td style="text-align:right;">
										<fmt:formatNumber value="${tx.amount }" type="number" pattern="###,##0.00"/>
									</td>
								</tr>
							</c:forEach>
						</tbody>
						<tfoot>
							<tr>
								<td colspan="11">
									<ul class="pagination">
										<c:forEach var="num" begin="1" end="${page.totalPage }" step="1">
											<li class="${page.pageNo == num ? 'active' : ''}">
												<a href="${ctx}/broadband-user/billing/transaction/view/${num}/${transactionType}/${year}${yearMonth}${all}">${num}</a>
											</li>
										</c:forEach>
									</ul>
								</td>
							</tr>
						</tfoot>
					</table>
				</c:if>
				<c:if test="${fn:length(page.results) <= 0}">
					<div class="panel-body">
						<div class="alert alert-warning">No records have been found.</div>
					</div>
				</c:if>
			</div>
		</div>
	</div>
</div>

<jsp:include page="../footer.jsp" />
<jsp:include page="../script.jsp" />
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/bootstrap-datepicker.js"></script>
<script type="text/javascript">
(function($) {
	$('button[data-toggle="tooltip"]').tooltip();
	
	$('#checkbox_txs_top').click(function() {
		var b = $(this).prop("checked");
		if (b) {
			$('input[name="checkbox_txs"]').prop("checked", true);
		} else {
			$('input[name="checkbox_txs"]').prop("checked", false);
		}
	});
	
	$('#year_input_datepicker').datepicker({
	    format: "yyyy",
	    autoclose: true,
	    minViewMode: 2
	});
	
	$('#month_input_datepicker').datepicker({
	    format: "yyyy-mm",
	    autoclose: true,
	    minViewMode: 1
	});
	
	$('#year_input_datepicker').datepicker().on('changeDate', function(ev){
		window.location.href = $('#year_input').val();
	});
	
	$('#month_input_datepicker').datepicker().on('changeDate', function(ev){
		window.location.href = $('#month_input').val();
	});
	
})(jQuery);
</script>
<jsp:include page="../footer-end.jsp" />