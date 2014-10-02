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
					<h4 class="panel-title">Sales View</h4>
				</div>
				<c:if test="${fn:length(page.results) > 0 }">
					<table class="table" style="font-size:12px;">
						<thead >
							<tr>
								<th><input type="checkbox" id="checkbox_users_top" /></th>
								<th>Sales Id</th>
								<th>Login Account</th>
								<th>Name</th>
								<th>Cell phone</th>
								<th>Email</th>
								<th>&nbsp;</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="user" items="${page.results }">
								<tr>
									<td>
										<input type="checkbox" name="checkbox_users" value="${user.id}"/>
									</td>
									<td>
										${user.id }
									</td>
									<td>
										${user.login_name }
									</td>
									<td>
										${user.user_name }
									</td>
									<td>
										${user.cellphone }
									</td>
									<td>
										${user.email }
									</td>
									<td style="font-size:20px;">
										<a href="javascript:void;" class="glyphicon glyphicon-envelope" data-toggle="tooltip" data-placement="bottom" data-original-title="Send email"></a>&nbsp;
										<a href="javascript:void;" class="glyphicon glyphicon-send" data-toggle="tooltip" data-placement="bottom" data-original-title="Send sms message"></a>&nbsp;
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
											<a href="${ctx}/broadband-user/provision/sale/view/${num}">${num}</a>
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

<!-- Upload PDF Modal -->
<form class="form-horizontal" action="${ctx }/broadband-user/sale/online/ordering/order/upload-single" method="post" enctype="multipart/form-data">
	<input type="hidden" name="order_id"/>
	<input type="hidden" name="customer_id"/>
	<input type="hidden" name="sale_id"/>
	<div class="modal fade" id="uploadModal" tabindex="-1" role="dialog" aria-labelledby="uploadModalLabel" aria-hidden="true">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
	        <h3 class="modal-title text-danger" id="uploadModalLabel"><strong>Upload PDF</strong></h3>
	      </div>
	      <div class="modal-body">
			<div class="form-group">
				<label class="control-label col-md-4">Order File Path</label>
				<div class="col-md-6">
					<input type="file" name="order_pdf_path" class="form-control input-sm" placeholder="Choose a file"/>
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-md-4">Credit File Path</label>
				<div class="col-md-6">
					<input type="file" name="credit_pdf_path" class="form-control input-sm" placeholder="Choose a file"/>
				</div>
			</div>
	      </div>
	      <div class="modal-footer">
	        <button type="submit" class="btn btn-success">Upload File</button>
	      </div>
	    </div><!-- /.modal-content -->
	  </div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
</form>

<!-- Modal -->
<div class="modal fade" id="optionalRequestModel" tabindex="-1" role="dialog" aria-labelledby="optionalRequestModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="optionalRequestModalLabel">Additional Request: </h4>
			</div>
			<div class="modal-body">
				<div class="form-group">
					<div class="col-md-12">
						<p class="form-control-static" data-name="optional_request_p">
						</p>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<jsp:include page="../footer.jsp" />
<jsp:include page="../script.jsp" />
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/bootstrap-select.min.js"></script>
<script type="text/javascript">
(function($){
	$('a[data-toggle="tooltip"]').tooltip();

	$('#checkbox_users_top').click(function(){
		var b = $(this).prop("checked");
		if (b) {
			$('input[name="checkbox_users"]').prop("checked", true);
		} else {
			$('input[name="checkbox_users"]').prop("checked", false);
		}
	});
	
})(jQuery);
</script>
<jsp:include page="../footer-end.jsp" />