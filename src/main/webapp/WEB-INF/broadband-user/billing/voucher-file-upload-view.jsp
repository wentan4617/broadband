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
						<a data-toggle="collapse" data-toggle="collapse" data-parent="#accordion" href="#collapseOne"> 
							Voucher File Upload Query
						</a>
					</h4>
				</div>
				<div id="collapseOne" class="panel-collapse collapse in">
					<div class="panel-body">
						<div class="btn-group btn-group">
							<a href="${ctx}/broadband-user/billing/voucher-file-upload-record/view/1/activated" class="btn btn-default ${activatedActive }">
								Activated&nbsp;<span class="badge">${activatedSum}</span>
							</a>
							<a href="${ctx}/broadband-user/billing/voucher-file-upload-record/view/1/inactivated" class="btn btn-default ${inactivatedActive }">
								Inactivated&nbsp;<span class="badge">${inactivatedSum}</span>
							</a>
						</div>
					</div>
				</div>
			</div>
			<div class="panel panel-success">
				<div class="panel-heading">
					<h4 class="panel-title">
						Voucher File Upload View
						<div class="pull-right">
							<button class="btn btn-success btn-xs" data-name="upload_voucher_csv_file" data-toggle="tooltip" data-placement="bottom" data-original-title="Upload Voucher CSV File">
								<strong>&nbsp;UPLOAD VOUCHER CSV FILE&nbsp;<span class="glyphicon glyphicon-arrow-up"></span></strong>
							</button>
						</div>
					</h4>
				</div>
				<c:if test="${fn:length(page.results) > 0 }">
					<table class="table" style="font-size:12px;">
						<thead >
							<tr>
								<th><input type="checkbox" id="checkbox_vfus_top" /></th>
								<th>File Name</th>
								<th>Status</th>
								<th>Upload By</th>
								<th style="width:90px;">Date</th>
								<th>Insert By</th>
								<th style="width:90px;">Date</th>
								<th>Operation</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="vfu" items="${page.results }">
								<tr>
									<td>
										<input type="checkbox" name="checkbox_vfus" value="${v.id}"/>
									</td>
									<td>
										${vfu.file_name }
									</td>
									<td>
										${vfu.status}
									</td>
									<td>
										<c:forEach var="user" items="${users}">
											<c:if test="${user.id == vfu.upload_by}">
												<strong>${user.user_name}</strong>
											</c:if>
										</c:forEach>
									</td>
									<td>
										${vfu.upload_date_str }
									</td>
									<td>
										<c:forEach var="user" items="${users}">
											<c:if test="${user.id == vfu.inserted_by}">
												<strong>${user.user_name}</strong>
											</c:if>
										</c:forEach>
									</td>
									<td>
										${vfu.inserted_date_str }
									</td>
									<td style="font-size:20px;">
										<a target="_blank" href="${ctx}/broadband-user/billing/voucher-file-upload-record/csv/download/${vfu.id }" class="glyphicon glyphicon-floppy-save" style="font-size:20px;" data-toggle="tooltip" data-placement="bottom" data-original-title="Download Voucher CSV File"></a>
										&nbsp;<a href="javascript:void(0);" class="glyphicon glyphicon-play" style="font-size:20px;" data-name="insertVoucherFile" data-id="${vfu.id }" data-path="${vfu.file_path}" data-toggle="tooltip" data-placement="bottom" data-original-title="Insert Voucher CSV Into Database"></a>
										&nbsp;<a href="javascript:void(0);" class="glyphicon glyphicon-trash" style="font-size:20px;" data-name="deleteVoucherFile" data-id="${vfu.id }" data-path="${vfu.file_path}" data-toggle="tooltip" data-placement="bottom" data-original-title="Delete Voucher Record and CSV file"></a>
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
												<a href="${ctx}//broadband-user/billing/voucher-file-upload-record/view/${num}/${status}">${num}</a>
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

<!-- Upload Voucher File Modal -->
<form class="form-horizontal" action="${ctx}/broadband-user/billing/voucher-file-upload-record/csv/upload" method="post" enctype="multipart/form-data">
	<input type="hidden" name="pageNo" value="${page.pageNo}" />
	<input type="hidden" name="status" value="${status}" />
	<div class="modal fade" id="uploadVoucherFileCSVModal" tabindex="-1" role="dialog" aria-labelledby="uploadVoucherFileCSVModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="uploadVoucherFileCSVModalLabel">
						<strong>Upload Voucher File CSV</strong>
					</h4>
				</div>
				<div class="modal-body">
					<div class="form-group">
						<label for="voucher_csv_file" class="control-label col-md-5">Voucher File(CSV):</label>
						<div class="col-md-5">
							<input class="form-control input-sm" type="file" name="voucher_csv_file" placeholder="Choose a file"/>
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

<!-- Delete Voucher File Modal -->
<form class="form-horizontal" action="${ctx}/broadband-user/billing/voucher-file-upload-record/csv/delete" method="post">
	<input type="hidden" name="pageNo" value="${page.pageNo}" />
	<input type="hidden" name="status" value="${status}" />
	<input type="hidden" name="voucherFileId" />
	<input type="hidden" name="filePath" />
	<div class="modal fade" id="deleteVoucherFileCSVModal" tabindex="-1" role="dialog" aria-labelledby="deleteVoucherFileCSVModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="deleteVoucherFileCSVModalLabel">
						<strong>Delete Voucher File CSV</strong>
					</h4>
				</div>
				<div class="modal-body">
					<p>
						Sure to delete this record and its related CSV file?
					</p>
				</div>
				<div class="modal-footer">
					<button type="submit" class="btn btn-success">Confirm to delete</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- /.modal -->
</form>

<!-- Insert Voucher File Modal -->
<form class="form-horizontal" action="${ctx}/broadband-user/billing/voucher-file-upload-record/csv/insert" method="post">
	<input type="hidden" name="pageNo" value="${page.pageNo}" />
	<input type="hidden" name="status" value="${status}" />
	<input type="hidden" name="voucherFileId" />
	<input type="hidden" name="filePath" />
	<div class="modal fade" id="insertVoucherFileCSVModal" tabindex="-1" role="dialog" aria-labelledby="insertVoucherFileCSVModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="insertVoucherFileCSVModalLabel">
						<strong>Insert Voucher CSV File Into Database</strong>
					</h4>
				</div>
				<div class="modal-body">
					<p>
						Sure to insert the related CSV file's data into database?
					</p>
				</div>
				<div class="modal-footer">
					<button type="submit" class="btn btn-success">Confirm to insert</button>
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
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/bootstrap-select.min.js"></script>
<script type="text/javascript">
(function($) {
	$('button[data-toggle="tooltip"]').tooltip();
	
	$('#checkbox_vfus_top').click(function() {
		var b = $(this).prop("checked");
		if (b) {
			$('input[name="checkbox_vfus"]').prop("checked", true);
		} else {
			$('input[name="checkbox_vfus"]').prop("checked", false);
		}
	});
	
	$('button[data-name="upload_voucher_csv_file"]').click(function(){
		$('#uploadVoucherFileCSVModal').modal('show');
	});
	
	$('a[data-name="deleteVoucherFile"]').click(function(){
		$('input[name="voucherFileId"]').val($(this).attr('data-id'));
		$('input[name="filePath"]').val($(this).attr('data-path'));
		$('#deleteVoucherFileCSVModal').modal('show');
	});
	
	$('a[data-name="insertVoucherFile"]').click(function(){
		$('input[name="voucherFileId"]').val($(this).attr('data-id'));
		$('input[name="filePath"]').val($(this).attr('data-path'));
		$('#insertVoucherFileCSVModal').modal('show');
	});

})(jQuery);
</script>
<jsp:include page="../footer-end.jsp" />