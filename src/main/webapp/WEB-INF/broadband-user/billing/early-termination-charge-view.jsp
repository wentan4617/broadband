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
							Early Termination Charge Query
						</a>
					</h4>
				</div>
				<div id="collapseOne" class="panel-collapse collapse in">
					<div class="panel-body">
						<div class="btn-group btn-group">
							<a href="${ctx}/broadband-user/billing/early-termination-charge/view/1/unpaid" class="btn btn-default ${unpaidActive }">
								Haven't Paid&nbsp;<span class="badge">${unpaidSum}</span>
							</a>
							<a href="${ctx}/broadband-user/billing/early-termination-charge/view/1/paid" class="btn btn-default ${paidActive }">
								Already Paid&nbsp;<span class="badge">${paidSum}</span>
							</a>
						</div>
					</div>
				</div>
			</div>
		
			<div class="panel panel-success">
				<div class="panel-heading">
					<h4 class="panel-title">
						Early Termination Charge View
						<div class="pull-right">
							<button class="btn btn-success btn-xs" data-name="early_termination_charge_config_btn" data-toggle="tooltip" data-placement="bottom" data-original-title="Early Termination Charge Default Configuration">
								<strong>&nbsp;CONFIG EARLY TERMINATION CHARGE PARAMETER&nbsp;<span class="glyphicon glyphicon-arrow-up"></span></strong>
							</button>
						</div>
					</h4>
				</div>
				<c:if test="${fn:length(page.results) > 0 }">
					<table class="table" style="font-size:12px;">
						<thead >
							<tr>
								<th><input type="checkbox" id="checkbox_earlyTerminationCharges_top" /></th>
								<th>Create Date</th>
								<th>Order Id</th>
								<th>Customer Id</th>
								<th>Service Given</th>
								<th>Termination</th>
								<th>Legal Termination</th>
								<th>Month Between</th>
								<th style="text-align:right;">Total Charge Amount</th>
								<th>Executor</th>
								<th>Operations</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="earlyTerminationCharge" items="${page.results }">
								<tr>
									<td>
										<input type="checkbox" name="checkbox_earlyTerminationCharges" value="${earlyTerminationCharge.id}"/>
									</td>
									<td>
										${earlyTerminationCharge.create_date_str }
									</td>
									<td>
										${earlyTerminationCharge.order_id }
									</td>
									<td>
										${earlyTerminationCharge.customer_id }
									</td>
									<td>
										${earlyTerminationCharge.service_given_date_str}
									</td>
									<td>
										${earlyTerminationCharge.termination_date_str }
									</td>
									<td>
										${earlyTerminationCharge.legal_termination_date_str }
									</td>
									<td>
										${earlyTerminationCharge.months_between_begin_end }
									</td>
									<td style="text-align:right;">
										<fmt:formatNumber value="${earlyTerminationCharge.total_payable_amount }" type="number" pattern="###,###.00"/>
									</td>
									<td>
										<c:forEach var="user" items="${users }">
											<c:if test="${user.id == earlyTerminationCharge.execute_by }">
												${user.user_name }
											</c:if>
										</c:forEach>
									</td>
									<td>
										<c:if test="${status == 'unpaid'}">
											<a target="_blank" href="${ctx}/broadband-user/billing/early_termination_charge/pdf/send/${earlyTerminationCharge.id }/${earlyTerminationCharge.order_id }" data-toggle="tooltip" data-placement="bottom" data-original-title="Send Early Termination To Customer's Email" style="font-size:20px;">
												<span class="glyphicon glyphicon-send"></span>
											</a>
											&nbsp;
										</c:if>
										<a target="_blank" href="${ctx }/broadband-user/early-termination-charge/pdf/download/${earlyTerminationCharge.id }" data-toggle="tooltip" data-placement="bottom" data-original-title="Download Early Termination Charge PDF" style="font-size:20px;">
										  <span class="glyphicon glyphicon-floppy-save"></span>
										</a>
										&nbsp;
										<c:if test="${status == 'unpaid'}">
											<a target="_blank" id="${earlyTerminationCharge.id }" data-name="change_status" data-type="unpaid" href="javascript:void(0);" data-toggle="tooltip" data-placement="bottom" data-original-title="Switch This Record's Status to Paid" style="font-size:20px;">
											  <span class="glyphicon glyphicon-off"></span>
											</a>
										</c:if>
										<c:if test="${status == 'paid'}">
											<a target="_blank" id="${earlyTerminationCharge.id }" data-name="change_status" data-type="paid" href="javascript:void(0);" data-toggle="tooltip" data-placement="bottom" data-original-title="Switch This Record's Status to Unpaid" style="font-size:20px;">
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
												<a href="${ctx}/broadband-user/billing/early-termination-charge/view/${num}/${status}">${num}</a>
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

<!-- Configure Early Termination Charge's Parameter Modal -->
<form class="form-horizontal">
	<div class="modal fade" id="earlyTerminationChargeModal" tabindex="-1" role="dialog" aria-labelledby="earlyTerminationChargeModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="earlyTerminationChargeModalLabel">
						<strong>Early Termination Charge's Initial Setting</strong>
					</h4>
				</div>
				<div class="modal-body">
					<div class="form-group">
						<label for="overdue_extra_charge" class="control-label col-md-5">Overdue Extra Chagre</label>
						<div class="col-md-5">
							<input class="form-control input-sm" type="text" id="overdue_extra_charge" value="${etcp.overdue_extra_charge }" placeholder="initial overdue extra charge"/>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<a href="javascript:void(0);" class="btn btn-success" data-name="overdueExtraChargeBtn" data-dismiss="modal">Commit Setting</a>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- /.modal -->
</form>

<form action="${ctx}/broadband-user/billing/early-termination-charge/edit/status" style="display:none;" data-name="form">
	<input type="hidden" name="termination_id" />
	<input type="hidden" name="status" value="${status}" />
	<input type="hidden" name="pageNo" value="${page.pageNo}" />
	<input type="hidden" name="detailStatus" />
</form>

<!-- Switch Early Termination Charge's Status Modal -->
<form class="form-horizontal">
	<div class="modal fade" id="changeStatusModal" tabindex="-1" role="dialog" aria-labelledby="changeStatusModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="changeStatusModalLabel">
						<strong>Switch Early Termination Charge's Status to <span data-name="status"></span></strong>
					</h4>
				</div>
				<div class="modal-body">
					<p>
						This operation will switch this early termination charge's status to <strong><span data-name="status"></span></strong>
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
	
	$('#checkbox_earlyTerminationCharges_top').click(function() {
		var b = $(this).prop("checked");
		if (b) {
			$('input[name="checkbox_earlyTerminationCharges"]').prop("checked", true);
		} else {
			$('input[name="checkbox_earlyTerminationCharges"]').prop("checked", false);
		}
	});
	
	$('button[data-name="early_termination_charge_config_btn"]').click(function(){
		$('#earlyTerminationChargeModal').modal('show');
	});
	
	$('a[data-name="overdueExtraChargeBtn"]').click(function(){
		var data = {
				'overdue_extra_charge':$('#overdue_extra_charge').val(),
				'pageNo':${page.pageNo}
		};
		$.post('${ctx}/broadband-user/billing/early-termination-charge/insert', data, function(json){
			$.jsonValidation(json, 'left');
		}, 'json');
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