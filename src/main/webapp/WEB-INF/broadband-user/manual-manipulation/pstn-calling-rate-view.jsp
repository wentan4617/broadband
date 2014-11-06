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
						PSTN Rate View
						<div class="pull-right">
							<button class="btn btn-success btn-xs" data-name="upload_pstn_calling_rate_csv_file_btn" data-toggle="tooltip" data-placement="bottom" data-original-title="Upload PSTN Calling Rate CSV">
								<strong>&nbsp;UPLOAD CALL INTERNATIONAL RATE CSV FILE&nbsp;<span class="glyphicon glyphicon-arrow-up"></span></strong>
							</button>
						</div>
					</h4>
				</div>
				<c:if test="${fn:length(page.results) > 0 }">
					<table class="table">
						<thead >
							<tr>
								<th>Area Prefix</th>
								<th>Rate Type</th>
								<th>Area Name</th>
								<th>Rate Cost</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="callInternationalRate" items="${page.results }">
								<tr>
									<td>
										${callInternationalRate.area_prefix }
									</td>
									<td>
										${callInternationalRate.rate_type }
									</td>
									<td>
										${callInternationalRate.area_name }
									</td>
									<td>
										${callInternationalRate.rate_cost}
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
												<a href="${ctx}/broadband-user/manual-manipulation/pstn-calling-rate/view/${num}">${num}</a>
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

<!-- Upload PSTN Calling Rate Modal -->
<form class="form-horizontal" action="${ctx}/broadband-user/manual-manipulation/pstn-calling-rate/csv/insert" method="post" enctype="multipart/form-data">
	<input type="hidden" name="pageNo" value="${page.pageNo}" />
	<div class="modal fade" id="uploadPSTNCallingRateCSVModal" tabindex="-1" role="dialog" aria-labelledby="uploadPSTNCallingRateCSVModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="uploadPSTNCallingRateCSVModalLabel">
						<strong>Upload PSTN Calling Rate CSV</strong>
					</h4>
				</div>
				<div class="modal-body">
					<div class="form-group">
						<label for="pstn_calling_rate_csv_file" class="control-label col-md-5">PSTN Calling Rate File(CSV):</label>
						<div class="col-md-5">
							<input class="form-control input-sm" type="file" name="pstn_calling_rate_csv_file" placeholder="Choose a file"/>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="submit" class="btn btn-success">Upload CSV File</button>
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
	
	$('button[data-name="upload_pstn_calling_rate_csv_file_btn"]').click(function(){
		$('#uploadPSTNCallingRateCSVModal').modal('show');
	});

})(jQuery);
</script>
<jsp:include page="../footer-end.jsp" />