<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<jsp:include page="../header.jsp" />
<jsp:include page="../alert.jsp" />

<div class="container">
	<div class="row">
		<div class="col-md-12">
		
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
								<th>Service Given</th>
								<th>Termination</th>
								<th>Legal Termination</th>
								<th>Month Between</th>
								<th>Total Charge Amount</th>
								<th>Executor</th>
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
									<td>
										${earlyTerminationCharge.total_payable_amount }
									</td>
									<td>
										<c:forEach var="user" items="${users }">
											<c:if test="${user.id == earlyTerminationCharge.execute_by }">
												${user.user_name }
											</c:if>
										</c:forEach>
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
												<a href="${ctx}/broadband-user/billing/early-termination-charge/view/${num}">${num}</a>
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

<!-- Configure Call International Rate's Parameter Modal -->
<form class="form-horizontal" action="${ctx}/broadband-user/billing/call-international-rate/csv/insert" method="post" enctype="multipart/form-data">
	<input type="hidden" name="pageNo" value="${page.pageNo}" />
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
						<label for="call_international_rate_csv_file" class="control-label col-md-5">Call International Rate File(CSV):</label>
						<div class="col-md-5">
							<input class="form-control input-sm" type="file" name="call_international_rate_csv_file" placeholder="Choose a file"/>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="submit" class="btn btn-success">Commit Setting</button>
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

})(jQuery);
</script>
<jsp:include page="../footer-end.jsp" />