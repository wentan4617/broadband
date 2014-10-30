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
							Call Billing Record Query
						</a>
					</h4>
				</div>
				<div id="collapseOne" class="panel-collapse collapse in">
					<div class="panel-body">
						<div class="btn-group btn-group">
							<a href="${ctx}/broadband-user/manual-manipulation/call-billing-record/view/1/inserted/all" class="btn btn-default ${insertedActive }">
								All Inserted&nbsp;<span class="badge">${insertedSum}</span>
							</a>
							<a href="${ctx}/broadband-user/manual-manipulation/call-billing-record/view/1/notInserted/all" class="btn btn-default ${notInsertedActive }">
								All Haven't&nbsp;<span class="badge">${notInsertedSum}</span>
							</a>
							<a href="${ctx}/broadband-user/manual-manipulation/call-billing-record/view/1/inserted/chorus" class="btn btn-default ${chorusInsertedActive }">
								Chorus Inserted&nbsp;<span class="badge">${chorusInsertedSum}</span>
							</a>
							<a href="${ctx}/broadband-user/manual-manipulation/call-billing-record/view/1/inserted/callplus" class="btn btn-default ${callplusInsertedActive }">
								Callplus Inserted&nbsp;<span class="badge">${callplusInsertedSum}</span>
							</a>
							<a href="${ctx}/broadband-user/manual-manipulation/call-billing-record/view/1/inserted/vosVoIP" class="btn btn-default ${vosVoIPInsertedActive }">
								VOS VoIP Inserted&nbsp;<span class="badge">${vosVoIPInsertedSum}</span>
							</a>
						</div>
					</div>
				</div>
			</div>
			<div class="panel panel-success">
				<div class="panel-heading">
					<h4 class="panel-title">
						Call Billing Record View
						<div class="pull-right">
							<button class="btn btn-success btn-xs" id="${callBillingRecord.id }" data-name="upload_call_billing_record_csv_file" data-toggle="tooltip" data-placement="bottom" data-original-title="Upload Call Billing Record CSV">
								<strong>&nbsp;UPLOAD CALL BILLING RECORD CSV FILE&nbsp;<span class="glyphicon glyphicon-arrow-up"></span></strong>
							</button>
						</div>
					</h4>
				</div>
				<c:if test="${fn:length(page.results) > 0 }">
					<table class="table" style="font-size:12px;">
						<thead >
							<tr>
								<th><input type="checkbox" id="checkbox_callBillingRecords_top" /></th>
								<c:if test="${insertedActive=='active'}">
									<th>Data Insert Date</th>
								</c:if>
								<th>CSV Upload Date</th>
								<th>File Name</th>
								<th>Is Inserted</th>
								<th>Upload By</th>
								<th>Billing Type</th>
								<th>Operation</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="callBillingRecord" items="${page.results }">
								<tr>
									<td>
										<input type="checkbox" name="checkbox_callBillingRecords" value="${callBillingRecord.id}"/>
									</td>
									<c:if test="${insertedActive=='active'}">
										<td>
											${callBillingRecord.insert_date_str }
										</td>
									</c:if>
									<td>
										${callBillingRecord.upload_date_str }
									</td>
									<td>
										${callBillingRecord.upload_file_name}
									</td>
									<td>
										${callBillingRecord.inserted_database }
									</td>
									<td>
										<c:forEach var="user" items="${users}">
											<c:if test="${user.id == callBillingRecord.upload_by}">
												<strong>${user.user_name}</strong>
											</c:if>
										</c:forEach>
									</td>
									<td>
										<strong>${callBillingRecord.billing_type }</strong>
									</td>
									<td style="font-size:20px;">
										<a target="_blank" href="${ctx}/broadband-user/manual-manipulation/call-billing-record/csv/download/${callBillingRecord.id }" class="glyphicon glyphicon-floppy-save" style="font-size:20px;" data-toggle="tooltip" data-placement="bottom" data-original-title="Download Call Billing Record CSV"></a>
										<c:if test="${notInsertedActive=='active' || userSession.user_role=='system-developer'}">
											&nbsp;<a href="javascript:void(0);" class="glyphicon glyphicon-play" style="font-size:20px;" data-name="insertBillingFile" data-id="${callBillingRecord.id }" data-date="${callBillingRecord.statement_date_str }" data-path="${callBillingRecord.upload_path}" data-type="${callBillingRecord.billing_type }" data-toggle="tooltip" data-placement="bottom" data-original-title="Insert Records Into Database"></a>
											&nbsp;<a href="javascript:void(0);" class="glyphicon glyphicon-trash" style="font-size:20px;" data-name="deleteBillingFile" data-id="${callBillingRecord.id }" data-path="${callBillingRecord.upload_path}" data-type="${callBillingRecord.billing_type }" data-toggle="tooltip" data-placement="bottom" data-original-title="Delete record and CSV file"></a>
											<c:if test="${callBillingRecord.billing_type=='chorus'}">
											&nbsp;<a href="javascript:void(0);" class="glyphicon glyphicon-play" style="font-size:20px;" data-name="insertBillingFile" data-id="${callBillingRecord.id }" data-date="${callBillingRecord.statement_date_str }" data-path="${callBillingRecord.upload_path}" data-type="chorus-broadband-asid" data-toggle="tooltip" data-placement="bottom" data-original-title="Insert BroadbandASID Records Into Database"></a>
											</c:if>
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
												<a href="${ctx}/broadband-user/manual-manipulation/call-billing-record/view/${num}/${status}">${num}</a>
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

<!-- Upload Call Billing Record Modal -->
<form class="form-horizontal" action="${ctx}/broadband-user/manual-manipulation/call-billing-record/csv/upload" method="post" enctype="multipart/form-data">
	<input type="hidden" name="pageNo" value="1" />
	<input type="hidden" name="status" value="notInserted" />
	<input type="hidden" name="billingType" value="all" />
	<div class="modal fade" id="uploadCallBillingRecordCSVModal" tabindex="-1" role="dialog" aria-labelledby="uploadCallBillingRecordCSVModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="uploadCallBillingRecordCSVModalLabel">
						<strong>Upload Call Billing Record CSV</strong>
					</h4>
				</div>
				<div class="modal-body">
					<div class="form-group">
						<label for="call_billing_record_csv_file" class="control-label col-md-5">Call Billing Record File(CSV):</label>
						<div class="col-md-5">
							<input class="form-control input-sm" type="file" name="call_billing_record_csv_file" placeholder="Choose a file"/>
						</div>
					</div>
					<div class="form-group">
						<label for="billing_type" class="control-label col-md-5">Billing Type:</label>
						<div class="col-md-5">
							<select name="billing_type" draggable="true" class="form-control col-md-6 input-sm">
								<option value="chorus">Chorus Billing</option>
								<option value="callplus">Callplus Billing</option>
								<option value="vos-voip">VOS VoIP Billing</option>
							</select>
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

<!-- Delete Call Billing Record Modal -->
<form class="form-horizontal" action="${ctx}/broadband-user/manual-manipulation/call-billing-record/csv/delete" method="post">
	<input type="hidden" name="pageNo" value="${page.pageNo}" />
	<input type="hidden" name="status" value="${status}" />
	<input type="hidden" name="billingFileId" />
	<input type="hidden" name="filePath" />
	<input type="hidden" name="billingType" value="${billing_type}" />
	<input type="hidden" name="billing_type" />
	<div class="modal fade" id="deleteCallBillingRecordCSVModal" tabindex="-1" role="dialog" aria-labelledby="deleteCallBillingRecordCSVModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="deleteCallBillingRecordCSVModalLabel">
						<strong>Delete Call Billing Record CSV</strong>
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

<!-- Insert Call Billing Record Modal -->
<form class="form-horizontal" action="${ctx}/broadband-user/manual-manipulation/call-billing-record/csv/insert" method="post">
	<input type="hidden" name="pageNo" value="${page.pageNo}" />
	<input type="hidden" name="status" value="insert" />
	<input type="hidden" name="billingFileId" />
	<input type="hidden" name="statementDate" />
	<input type="hidden" name="filePath" />
	<input type="hidden" name="billing_type" />
	<div class="modal fade" id="insertCallBillingRecordCSVModal" tabindex="-1" role="dialog" aria-labelledby="insertCallBillingRecordCSVModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="insertCallBillingRecordCSVModalLabel">
						<strong>Insert Call Billing Record CSV Into Database</strong>
					</h4>
				</div>
				<div class="modal-body">
					<p>
						Sure to insert the related CSV file's data into database? This will rewrite the same statement date datum!!!
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
	
	$('#checkbox_callBillingRecords_top').click(function() {
		var b = $(this).prop("checked");
		if (b) {
			$('input[name="checkbox_callBillingRecords"]').prop("checked", true);
		} else {
			$('input[name="checkbox_callBillingRecords"]').prop("checked", false);
		}
	});
	
	$('button[data-name="upload_call_billing_record_csv_file"]').click(function(){
		$('#uploadCallBillingRecordCSVModal').modal('show');
	});
	
	$('a[data-name="deleteBillingFile"]').click(function(){
		$('input[name="billingFileId"]').val($(this).attr('data-id'));
		$('input[name="filePath"]').val($(this).attr('data-path'));
		$('input[name="billing_type"]').val($(this).attr('data-type'));
		$('#deleteCallBillingRecordCSVModal').modal('show');
	});
	
	$('a[data-name="insertBillingFile"]').click(function(){
		$('input[name="billingFileId"]').val($(this).attr('data-id'));
		$('input[name="statementDate"]').val($(this).attr('data-date'));
		$('input[name="filePath"]').val($(this).attr('data-path'));
		$('input[name="billing_type"]').val($(this).attr('data-type'));
		$('#insertCallBillingRecordCSVModal').modal('show');
	});

})(jQuery);
</script>
<jsp:include page="../footer-end.jsp" />