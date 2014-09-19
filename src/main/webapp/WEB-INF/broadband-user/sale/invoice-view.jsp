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
							<c:if test="${fn:length(pageCis.results) > 0 || status!='orderNoInvoice' }">
								Invoice Query
							</c:if>
							<c:if test="${fn:length(pageCos.results) > 0 || status=='orderNoInvoice' }">
								Order Query
							</c:if>
						</a>
					</h4>
				</div>
				<div id="collapseOne" class="panel-collapse collapse in">
					<div class="panel-body">
						<div class="btn-group btn-group">
							<a href="${ctx}/broadband-user/agent/billing/invoice/view/1/unpaid" class="btn btn-default ${unpaidActive }">
								Unpaid&nbsp;<span class="badge">${unpaidSum}</span>
							</a>
							<a href="${ctx}/broadband-user/agent/billing/invoice/view/1/not_pay_off" class="btn btn-default ${not_pay_offActive }">
								Not Pay Off&nbsp;<span class="badge">${notPayOffSum}</span>
							</a>
							<a href="${ctx}/broadband-user/agent/billing/invoice/view/1/pending" class="btn btn-default ${pendingActive }">
								Pending&nbsp;<span class="badge">${pendingSum}</span>
							</a>
							<a href="${ctx}/broadband-user/agent/billing/invoice/view/1/void" class="btn btn-default ${voidActive }">
								Void&nbsp;<span class="badge">${voidSum}</span>
							</a>
							<a href="${ctx}/broadband-user/agent/billing/invoice/view/1/paid" class="btn btn-default ${paidActive }">
								Paid&nbsp;<span class="badge">${paidSum}</span>
							</a>
							<a href="${ctx}/broadband-user/agent/billing/invoice/view/1/orderNoInvoice" class="btn btn-default ${orderNoInvoiceActive }">
								Haven't Generate&nbsp;<span class="badge">${orderNoInvoiceSum}</span>
							</a>
						</div>
					</div>
				</div>
			</div>
		
			<div class="panel panel-success">
				<div class="panel-heading">
					<h4 class="panel-title">
						<c:if test="${fn:length(pageCis.results) > 0 || status!='orderNoInvoice' }">
							Invoice View
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
								<th>Status</th>
								<c:if test="${paidActive=='active'}">
									<th style="text-align:right;">Commission</th>
								</c:if>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="ci" items="${pageCis.results }">
								<tr class="${(ci.status=='unpaid' || ci.status=='not_pay_off') ? 'danger' : 
									ci.status=='discard' ? 'info' : ''  }" >
									<td>
										<input type="checkbox" name="checkbox_cis" value="${ci.id}"/>
									</td>
									<td>
										${ci.customer_id}
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
									<td>
										<c:choose>
											<c:when test="${status=='pending' }">${ci.payment_status }</c:when>
											<c:otherwise>${ci.status }</c:otherwise>
										</c:choose>
									</td>
									<c:if test="${unpaidActive=='active' || not_pay_offActive=='active' || pendingActive=='active'}">
										<td>
											<div class="btn-group">
												<button type="button" class="btn btn-primary btn-xs" data-name="make_payment_<.= invoice.id .>">Make Payment</button>
												<button type="button" class="btn btn-primary btn-xs dropdown-toggle" data-toggle="dropdown">
													<span class="caret"></span>
												</button>
												<ul class="dropdown-menu" role="menu">
													<li><a href="${ctx}/broadband-user/crm/customer/invoice/payment/credit-card/${ci.id}/agent_invoice/${ci.status}"><span class="glyphicon glyphicon-link"></span>&nbsp;&nbsp;DPS</a></li>
												</ul>
											</div>
										</td>
									</c:if>
									<c:if test="${paidActive=='active'}">
										<td style="text-align:right; font-weight:bold; color:rgb(126,210,0);">
											<fmt:formatNumber value="${ci.commission }" type="number" pattern="###,##0.00"/>
										</td>
									</c:if>
								</tr>
							</c:forEach>
						</tbody>
						<tfoot>
							<tr>
								<td colspan="12">
									<ul class="pagination">
										<c:forEach var="num" begin="1" end="${pageCis.totalPage }" step="1">
											<li class="${pageCis.pageNo == num ? 'active' : ''}">
												<a href="${ctx}/broadband-user/billing/invoice/view/${num}/${status}">${num}</a>
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
										${co.customer_id}
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
})(jQuery);
</script>
<jsp:include page="../footer-end.jsp" />