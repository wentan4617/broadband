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
.unpaid{
	background:rgba(250,70,120,0.3);
}
.unpaid:hover{
	background:rgba(250,70,120,0.35);
}
.overdue{
	background:rgba(220,50,90,0.3);
}
.overdue:hover{
	background:rgba(220,50,90,0.35);
}
.prepayment{
	background:rgba(173,206,169,0.3);
}
.prepayment:hover{
	background:rgba(173,206,169,0.35);
}
.paid{
	background:rgba(153,186,149,0.3);
}
.paid:hover{
	background:rgba(153,186,149,0.35);
}
.void{
	background:rgba(137,143,156,0.3);
}
.void:hover{
	background:rgba(137,143,156,0.35);
}
.bad-debit{
	background:rgba(77,83,96,0.3);
}
.bad-debit:hover{
	background:rgba(77,83,96,0.35);
}
</style>

<div class="container">
	<div class="row">
		<div class="col-md-12">
		
			<div class="panel panel-success">
				<div class="panel-heading">
					<h4 class="panel-title">
						<a data-toggle="collapse" data-toggle="collapse" data-parent="#accordion" href="#collapseOne">
							<c:if test="${fn:length(pageCis.results) > 0 || status!='orderNoInvoice' }">
								Invoice Query - <strong>${customer_type}</strong>
							</c:if>
							<c:if test="${fn:length(pageCos.results) > 0 || status=='orderNoInvoice' }">
								Order Query
							</c:if>
						</a>
					</h4>
				</div>
				<div id="collapseOne" class="panel-collapse collapse in">
					<div class="panel-body">
						<form class="form-horizontal">
							<div class="form-group">
								<div class="col-md-1">
									<a href="${ctx}/broadband-user/billing/invoice/view/${customer_type}/1/unpaid/all" class="btn btn-default ${allActive}">
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
							<a href="${ctx}/broadband-user/billing/invoice/view/${customer_type}/1/unpaid/${year}${yearMonth}${all}" class="btn btn-default ${unpaidActive }">
								Unpaid&nbsp;<span class="badge">${unpaidSum}</span>
							</a>
							<a href="${ctx}/broadband-user/billing/invoice/view/${customer_type}/1/not_pay_off/${year}${yearMonth}${all}" class="btn btn-default ${not_pay_offActive }">
								Not Pay Off&nbsp;<span class="badge">${notPayOffSum}</span>
							</a>
							<a href="${ctx}/broadband-user/billing/invoice/view/${customer_type}/1/overdue/${year}${yearMonth}${all}" class="btn btn-default ${overdueActive }">
								Overdue&nbsp;<span class="badge">${overdueSum}</span>
							</a>
							<a href="${ctx}/broadband-user/billing/invoice/view/${customer_type}/1/pending/${year}${yearMonth}${all}" class="btn btn-default ${pendingActive }">
								Pending&nbsp;<span class="badge">${pendingSum}</span>
							</a>
							<a href="${ctx}/broadband-user/billing/invoice/view/${customer_type}/1/prepayment/${year}${yearMonth}${all}" class="btn btn-default ${prepaymentActive }">
								Prepayment&nbsp;<span class="badge">${prepaymentSum}</span>
							</a>
							<a href="${ctx}/broadband-user/billing/invoice/view/${customer_type}/1/paid/${year}${yearMonth}${all}" class="btn btn-default ${paidActive }">
								Paid&nbsp;<span class="badge">${paidSum}</span>
							</a>
							<a href="${ctx}/broadband-user/billing/invoice/view/${customer_type}/1/void/${year}${yearMonth}${all}" class="btn btn-default ${voidActive }">
								Void&nbsp;<span class="badge">${voidSum}</span>
							</a>
							<a href="${ctx}/broadband-user/billing/invoice/view/${customer_type}/1/bad_debit/${year}${yearMonth}${all}" class="btn btn-default ${bad_debitActive }">
								Bad Debit&nbsp;<span class="badge">${badDebitSum}</span>
							</a>
							<%-- <a href="${ctx}/broadband-user/billing/invoice/view/${customer_type}/1/orderNoInvoice" class="btn btn-default ${orderNoInvoiceActive }">
								Haven't Generate&nbsp;<span class="badge">${orderNoInvoiceSum}</span>
							</a> --%>
						</div>
					</div>
				</div>
			</div>
		
			<div class="panel panel-success">
				<div class="panel-heading">
					<h4 class="panel-title">
						<c:if test="${fn:length(pageCis.results) > 0 || status!='orderNoInvoice' }">
							Invoice View - <strong>${customer_type}</strong>
						</c:if>
						<c:if test="${fn:length(pageCos.results) > 0 || status=='orderNoInvoice' }">
							Order View
						</c:if>
					</h4>
				</div>
				<c:if test="${fn:length(pageCis.results) > 0 }">
					<table class="table" style="font-size:12px;">
						<thead >
							<tr>
								<th><input type="checkbox" id="checkbox_cis_top" /></th>
								<th>Customer Id</th>
								<th>Order Id</th>
								<th>Invoice Id</th>
								<th>Create Date</th>
								<th>Due Date</th>
								<th style="text-align:right;">Amount Payable</th>
								<th style="text-align:right;">Total Credit</th>
								<th style="text-align:right;">Amount Paid</th>
								<th style="text-align:right;">Balance</th>
								<c:if test="${status=='prepayment'}">
									<th style="text-align:right;">Prepaid</th>
								</c:if>
								<th>Invoice Status</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="ci" items="${pageCis.results }">
								<tr class="${(status=='unpaid' || status=='not_pay_off') ? 'unpaid' : 
										  status=='overdue' ? 'overdue' :
										  status=='prepayment' ? 'prepayment' :
										  status=='paid' ? 'paid' :
										  status=='void' ? 'void' :
										  status=='bad_debit' ? 'bad-debit' : '' }" >
									<td>
										<input type="checkbox" name="checkbox_cis" value="${ci.id}"/>
									</td>
									<td>
										<a target="_blank" href="${ctx }/broadband-user/crm/customer/edit/${ci.customer_id}">${ci.customer_id}</a>
									</td>
									<td>
										${ci.order_id }
									</td>
									<td>
										${ci.id }
									</td>
									<td>
										${ci.create_date_str }
									</td>
									<td>
										${ci.due_date_str }
									</td>
									<td style="text-align:right;">
										<fmt:formatNumber value="${ci.amount_payable }" type="number" pattern="###,##0.00"/>
									</td>
									<td style="text-align:right;">
										<fmt:formatNumber value="${ci.amount_payable - ci.final_payable_amount }" type="number" pattern="###,##0.00"/>
									</td>
									<td style="text-align:right;">
										<fmt:formatNumber value="${ci.amount_paid }" type="number" pattern="###,##0.00"/>
									</td>
									<td style="text-align:right;">
										<fmt:formatNumber value="${ci.balance }" type="number" pattern="###,##0.00"/>
									</td>
									<c:if test="${status=='prepayment'}">
										<td style="text-align:right;">
											<fmt:formatNumber value="${ci.amount_paid-ci.final_payable_amount }" type="number" pattern="###,##0.00"/>
										</td>
									</c:if>
									<td>
										<c:choose>
											<c:when test="${status=='pending' }">${ci.payment_status }</c:when>
											<c:otherwise>${ci.status }</c:otherwise>
										</c:choose>
									</td>
								</tr>
							</c:forEach>
						</tbody>
						<tfoot>
							<tr>
								<td colspan="11">
									<ul class="pagination">
										<c:forEach var="num" begin="1" end="${pageCis.totalPage }" step="1">
											<li class="${pageCis.pageNo == num ? 'active' : ''}">
												<a href="${ctx}/broadband-user/billing/invoice/view/${customer_type}/${num}/${status}/${year}${yearMonth}${all}">${num}</a>
											</li>
										</c:forEach>
									</ul>
								</td>
							</tr>
						</tfoot>
					</table>
				</c:if>
				<c:if test="${fn:length(pageCos.results) > 0 }">
					<table class="table" style="font-size:12px;">
						<thead >
							<tr>
								<th><input type="checkbox" id="checkbox_cis_top" /></th>
								<th>Customer Id</th>
								<th>Order Id</th>
								<th>Create Date</th>
								<th>Service Given Date</th>
								<th>Order Type</th>
								<th>Belongs To</th>
								<th>Order Status</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="co" items="${pageCos.results }">
								<tr>
									<td>
										<input type="checkbox" name="checkbox_cis" value="${co.id}"/>
									</td>
									<td>
										<a href="${ctx }/broadband-user/crm/customer/edit/${co.customer_id}">${co.customer_id}</a>
									</td>
									<td>
										${co.id }
									</td>
									<td>
										${co.order_create_date_str }
									</td>
									<td>
										${co.order_using_start_str }
									</td>
									<td>
										${co.order_type }
									</td>
									<td>
										${co.sale_id }
									</td>
									<td>
										${co.order_status }
									</td>
									<td>
									</td>
								</tr>
							</c:forEach>
						</tbody>
						<tfoot>
							<tr>
								<td colspan="11">
									<ul class="pagination">
										<c:forEach var="num" begin="1" end="${pageCos.totalPage }" step="1">
											<li class="${pageCos.pageNo == num ? 'active' : ''}">
												<a href="${ctx}/broadband-user/billing/invoice/view/${num}/${status}">${num}</a>
											</li>
										</c:forEach>
									</ul>
								</td>
							</tr>
						</tfoot>
					</table>
				</c:if>
				<c:if test="${fn:length(pageCis.results) <= 0 && fn:length(pageCos.results) <= 0 }">
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
	
	$('#checkbox_cis_top').click(function() {
		var b = $(this).prop("checked");
		if (b) {
			$('input[name="checkbox_cis"]').prop("checked", true);
		} else {
			$('input[name="checkbox_cis"]').prop("checked", false);
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