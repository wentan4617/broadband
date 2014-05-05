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
					<table class="table">
						<c:forEach var="co" items="${customerSession.customerOrders}">
							<thead>
								<tr>
									<th colspan="7">
										<h4 class="text-success" style="margin:2px;">Order Serial:&nbsp;<small>${co.id}</small> </h4>
									</th>
								</tr>
								<tr>
									<th>Date</th>
									<th>Due Date</th>
									<th>Reference</th>
									<th>Amount Payable</th>
									<th>Amount Paid</th>
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
													<td>
														<strong>NZ$ <fmt:formatNumber value="${invoice.amount_paid}" type="number" pattern="#,##0.00" /></strong>
													</td>
													<td>&nbsp;</td>
													<td>&nbsp;</td>
												</tr>
											</c:if>
										</c:forEach>
										<tr class="${(invoice.status=='unpaid' || invoice.status=='not_pay_off') ? 'danger' : ''}">
											<td>${invoice.create_date_str}</td>
											<td>
												&nbsp;
												<c:if test="${invoice.status=='unpaid' || invoice.status=='not_pay_off'}">
													<strong style="color: red;">${invoice.due_date_str}</strong>
												</c:if>
											</td>
											<td>Invoice#&nbsp;-&nbsp;${invoice.id}</td>
											<td>
												<strong>NZ$ <fmt:formatNumber value="${invoice.amount_payable}" type="number" pattern="#,##0.00" /></strong>
											</td>
											<td>&nbsp;</td>
											<td>
												<div class="btn-group">
													<button type="button" class="btn btn-success btn-xs dropdown-toggle" data-toggle="dropdown">
														Make Payment <span class="caret"></span>
    													
													</button>
													<ul class="dropdown-menu" role="menu">
														<li><a href="#">Credit Card</a></li>
													</ul>
												</div>
											</td>
											<!-- download icon -->
											<td>
												<c:if test="${invoice.invoice_pdf_path!=null}">
													<a target="_blank"
														href="${ctx}/broadband-customer/billing/invoice/pdf/download/${invoice.id}"
														data-toggle="tooltip" data-placement="bottom"
														data-original-title="download invoice PDF">
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


<jsp:include page="footer.jsp" />
<jsp:include page="script.jsp" />
<jsp:include page="footer-end.jsp" />