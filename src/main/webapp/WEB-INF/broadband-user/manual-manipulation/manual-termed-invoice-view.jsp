<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<jsp:include page="../header.jsp" />
<jsp:include page="../alert.jsp" />

<style>
.datepicker{
	z-index:9999;
}
</style>

<div class="container">
	<div class="row">
		<div class="col-md-12">
		
			<div class="panel panel-success">
				<div class="panel-heading">
					<h4 class="panel-title">
						Manual Generated Termed Invoices View
						<div class="pull-right">
							<c:if test="${userSession.user_role=='system-developer'}">
							<button class="btn btn-success btn-xs" data-name="manually_generate_all_ddpay_invoices_btn" data-toggle="tooltip" data-placement="bottom" data-original-title="Manually Generate All DDPay Order's Next Invoice">
								<strong>&nbsp;GENERATE All DDPAY ORDER'S NEXT INVOICE&nbsp;<span class="glyphicon glyphicon-arrow-up"></span></strong>
							</button>&nbsp;&nbsp;
							<button class="btn btn-success btn-xs" data-name="manually_generate_all_non_ddpay_invoices_btn" data-toggle="tooltip" data-placement="bottom" data-original-title="Manually Generate All Non DDPay Order's Next Invoice">
								<strong>&nbsp;GENERATE All NON DDPAY ORDER'S NEXT INVOICE&nbsp;<span class="glyphicon glyphicon-arrow-up"></span></strong>
							</button>
							</c:if>
						</div>
					</h4>
				</div>
				<c:if test="${fn:length(page.results) > 0 }">
					<table class="table" style="font-size:12px;">
						<thead >
							<tr>
								<th><input type="checkbox" id="checkbox_manualManipulationRecords_top" /></th>
								<th>Manipulation Name</th>
								<th>Manipulation Type</th>
								<th>Manipulation Time</th>
								<th>Admin Name</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="manualManipulationRecord" items="${page.results }">
								<tr>
									<td>
										<input type="checkbox" name="checkbox_manualManipulationRecords" value="${manualManipulationRecord.id}"/>
									</td>
									<td>
										${manualManipulationRecord.manipulation_name }
									</td>
									<td>
										${manualManipulationRecord.manipulation_type }
									</td>
									<td>
										${manualManipulationRecord.manipulation_time_str }
									</td>
									<td>
										${manualManipulationRecord.admin_name}
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
												<a href="${ctx}/broadband-user/manual-manipulation/manual-manipulation-record/view/${num}/termed-invoice-view">${num}</a>
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

<!-- Manually Generate All DDPay Invoices Modal -->
<form class="form-horizontal" action="${ctx}/broadband-user/manual-manipulation/manual-manipulation-record/create/${page.pageNo}" method="post">
	<input type="hidden" name="manipulation_type" value="generate-termed-invoice" />
	<div class="modal fade" id="manuallyGenerateAllDDPayInvoicesModal" tabindex="-1" role="dialog" aria-labelledby="manuallyGenerateAllTermedInvoicesModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="manuallyGenerateAllTermedInvoicesModalLabel">
						<strong>Manually Generate All DDPay Invoices?</strong>
					</h4>
				</div>
				<div class="modal-body">
					<p>
						This operation will generate all the termed order's next invoice, which is exactly same as the automatic generated invoice's function.<br/><br/>
						<strong>PLEASE NOTE THIS IS A SERIOUS ACTION!</strong>
					</p>
				</div>
				<div class="modal-footer">
					<button type="submit" class="btn btn-success">Generate All DDPay Invoices</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- /.modal -->
</form>

<!-- Manually Generate All Non DDPay Invoices Modal -->
<form class="form-horizontal" action="${ctx}/broadband-user/manual-manipulation/manual-manipulation-record/non-ddpay/create/${page.pageNo}" method="post">
	<input type="hidden" name="manipulation_type" value="generate-non-ddpay-invoice" />
	<div class="modal fade" id="manuallyGenerateAllNonDDPayInvoicesModal" tabindex="-1" role="dialog" aria-labelledby="manuallyGenerateAllTermedInvoicesModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="manuallyGenerateAllTermedInvoicesModalLabel">
						<strong>Manually Generate All Non DDPay Invoices?</strong>
					</h4>
				</div>
				<div class="modal-body">
					<div class="form-group">
						<label for="next_invoice_create_date" class="control-label col-md-6">Order's Next Invoice Create Date</label>
						<div class="col-md-3">
							<input id="next_invoice_create_date" class="form-control input-xs" name="next_invoice_create_date" placeholder="yyyy-MM-dd"/>
						</div>
					</div>
						<p class="form-control-static">
							This operation will generate all specific date no ddpay order's next invoice, which is exactly same as the automatic generated invoice's function.<br/><br/>
							<strong>PLEASE NOTE THIS IS A SERIOUS ACTION!</strong>
						</p>
				</div>
				<div class="modal-footer">
					<button type="submit" class="btn btn-success">Generate All Non DDPay Invoices</button>
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
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/bootstrap-datepicker.js"></script>
<script type="text/javascript">
(function($) {
	$('button[data-toggle="tooltip"]').tooltip();
	
	$('#checkbox_manualManipulationRecords_top').click(function() {
		var b = $(this).prop("checked");
		if (b) {
			$('input[name="checkbox_manualManipulationRecords"]').prop("checked", true);
		} else {
			$('input[name="checkbox_manualManipulationRecords"]').prop("checked", false);
		}
	});
	
	$('button[data-name="manually_generate_all_ddpay_invoices_btn"]').click(function(){
		$('#manuallyGenerateAllDDPayInvoicesModal').modal('show');
	});
	
	$('button[data-name="manually_generate_all_non_ddpay_invoices_btn"]').click(function(){
		$('#manuallyGenerateAllNonDDPayInvoicesModal').modal('show');
	});
	
})(jQuery);
</script>
<jsp:include page="../footer-end.jsp" />