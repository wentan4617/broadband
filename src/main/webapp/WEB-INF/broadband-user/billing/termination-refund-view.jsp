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
							Termination Refund Query
						</a>
					</h4>
				</div>
				<div id="collapseOne" class="panel-collapse collapse in">
					<div class="panel-body">
						<div class="btn-group btn-group">
							<a href="${ctx}/broadband-user/billing/termination-refund/view/1/unpaid" class="btn btn-default ${unpaidActive }">
								Haven't Refunded&nbsp;<span class="badge">${unpaidSum}</span>
							</a>
							<a href="${ctx}/broadband-user/billing/termination-refund/view/1/paid" class="btn btn-default ${paidActive }">
								Already Refunded&nbsp;<span class="badge">${paidSum}</span>
							</a>
						</div>
					</div>
				</div>
			</div>
		
			<div class="panel panel-success">
				<div class="panel-heading">
					<h4 class="panel-title">
						Termination Refund View
					</h4>
				</div>
				<c:if test="${fn:length(page.results) > 0 }">
					<table class="table" style="font-size:12px;">
						<thead >
							<tr>
								<th><input type="checkbox" id="checkbox_terminationRefunds_top" /></th>
								<th>Create Date</th>
								<th>Order Id</th>
								<th>Customer Id</th>
								<th>Max Date</th>
								<th>Termination</th>
								<th>Day(s) Between</th>
								<th style="text-align:right;">Refund Amount</th>
								<th style="text-align:right;">Plan Fee/Month</th>
								<th>Executor</th>
								<th>Operations</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="terminationRefund" items="${page.results }">
								<tr>
									<td>
										<input type="checkbox" name="checkbox_terminationRefunds" value="${terminationRefund.id}"/>
									</td>
									<td>
										${terminationRefund.create_date_str }
									</td>
									<td>
										${terminationRefund.order_id }
									</td>
									<td>
										${terminationRefund.customer_id }
									</td>
									<td>
										${terminationRefund.last_date_of_month_str }
									</td>
									<td>
										${terminationRefund.termination_date_str }
									</td>
									<td>
										${terminationRefund.days_between_end_last }
									</td>
									<td style="text-align:right;">
										<fmt:formatNumber value="${terminationRefund.refund_amount }" type="number" pattern="###,###.00"/>
									</td>
									<td style="text-align:right;">
										<fmt:formatNumber value="${terminationRefund.product_monthly_price }" type="number" pattern="###,###.00"/>
									</td>
									<td>
										<c:forEach var="user" items="${users }">
											<c:if test="${user.id == terminationRefund.execute_by }">
												${user.user_name }
											</c:if>
										</c:forEach>
									</td>
									<td>
										<c:if test="${status == 'unpaid'}">
											<%-- <a target="_blank" href="${ctx}/broadband-user/billing/termination_refund/pdf/send/${terminationRefund.id }/${terminationRefund.order_id }" data-toggle="tooltip" data-placement="bottom" data-original-title="Send Termination Refund To Customer's Email" style="font-size:20px;">
												<span class="glyphicon glyphicon-send"></span>
											</a> --%>
											&nbsp;
										</c:if>
										<a target="_blank" href="${ctx }/broadband-user/termination-refund/pdf/download/${terminationRefund.id }" data-toggle="tooltip" data-placement="bottom" data-original-title="Download Termination Refund PDF" style="font-size:20px;">
										  <span class="glyphicon glyphicon-floppy-save"></span>
										</a>&nbsp;
										<c:if test="${status == 'unpaid'}">
											<a target="_blank" id="${terminationRefund.id }" data-name="change_status" data-type="unpaid" href="javascript:void(0);" data-toggle="tooltip" data-placement="bottom" data-original-title="Switch This Record's Status to Paid" style="font-size:20px;">
											  <span class="glyphicon glyphicon-off"></span>
											</a>
										</c:if>
										<c:if test="${status == 'paid'}">
											<a target="_blank" id="${terminationRefund.id }" data-name="change_status" data-type="paid" href="javascript:void(0);" data-toggle="tooltip" data-placement="bottom" data-original-title="Switch This Record's Status to Unpaid" style="font-size:20px;">
											  <span class="glyphicon glyphicon-off"></span>
											</a>
										</c:if>
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
												<a href="${ctx}/broadband-user/billing/termination-refund/view/${num}/${status}">${num}</a>
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

<form action="${ctx}/broadband-user/billing/termination-refund/edit/status" style="display:none;" data-name="form">
	<input type="hidden" name="termination_id" />
	<input type="hidden" name="status" value="${status}" />
	<input type="hidden" name="pageNo" value="${page.pageNo}" />
	<input type="hidden" name="detailStatus" />
</form>

<!-- Switch Termination Refund's Status Modal -->
<form class="form-horizontal">
	<div class="modal fade" id="changeStatusModal" tabindex="-1" role="dialog" aria-labelledby="changeStatusModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="changeStatusModalLabel">
						<strong>Switch Termination Refund's Status to <span data-name="status"></span></strong>
					</h4>
				</div>
				<div class="modal-body">
					<p>
						This operation will switch this termination refund's status to <strong><span data-name="status"></span></strong>
					</p>
				</div>
				<div class="modal-footer">
					<a href="javascript:void(0);" class="btn btn-success" data-name="changeStatusBtn" data-dismiss="modal">Confirm to change Status</a>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- /.modal -->
</form>

<jsp:include page="../footer.jsp" />
<jsp:include page="../script.jsp" />
<script type="text/javascript">
(function($) {
	$('button[data-toggle="tooltip"]').tooltip();
	
	$('#checkbox_terminationCharges_top').click(function() {
		var b = $(this).prop("checked");
		if (b) {
			$('input[name="checkbox_terminationCharges"]').prop("checked", true);
		} else {
			$('input[name="checkbox_terminationCharges"]').prop("checked", false);
		}
	});
	
	$('a[data-name="change_status"]').click(function(){
		$('input[name="termination_id"]').val(this.id);
		var status = $(this).attr('data-type');
		if(status == 'unpaid' ){
			$('input[name="detailStatus"]').val('paid');
			$('span[data-name="status"]').html('paid');
		} else if(status == 'paid' ) {
			$('input[name="detailStatus"]').val('unpaid');
			$('span[data-name="status"]').html('unpaid');
		}
		$('#changeStatusModal').modal('show');
	});
	
	$('a[data-name="changeStatusBtn"]').click(function(){
		$('form[data-name="form"]').submit();
	});
})(jQuery);
</script>
<jsp:include page="../footer-end.jsp" />