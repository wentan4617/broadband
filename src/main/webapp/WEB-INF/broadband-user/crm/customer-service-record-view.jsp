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
					<h4 class="panel-title">Customer Service Record View</h4>
				</div>
				<c:if test="${fn:length(page.results) > 0 }">
					<table class="table" style="font-size:12px;">
						<thead>
							<tr>
								<th><input type="checkbox" id="checkbox_customer_service_record_top" /></th>
								<th>Customer Id</th>
								<th>Recorded By</th>
								<th>Description</th>
								<th>Create Date</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="csr" items="${page.results }">
								<tr>
									<td>
										<input type="checkbox" name="checkbox_customer_service_record" value="${csr.id}"/>
									</td>
									<td>
										<a href="${ctx }/broadband-user/crm/customer/edit/${csr.customer_id}">
											${csr.customer_id }
										</a>
									</td>
									<td>
										<c:forEach var="u" items="${users}">
											<c:if test="${u.id==csr.user_id }">
												${u.user_name}
											</c:if>
										</c:forEach>
									</td>
									<td>
										<p style="width:600px;overflow-x:hidden;" data-name="csr_desc_${csr.id}" data-desc="${csr.description }">
											<a href="javascript:void(0);" id="${csr.id}" data-name="csr_desc_${csr.id}">${csr.description }</a>
										</p>
									</td>
									<td>${csr.create_date_str }</td>
								</tr>
							</c:forEach>
						</tbody>
						<tfoot>
						<tr>
							<td colspan="11">
								<ul class="pagination">
									<c:forEach var="num" begin="1" end="${page.totalPage }" step="1">
										<li class="${page.pageNo == num ? 'active' : ''}">
											<a href="${ctx}/broadband-user/crm/customer-service-record/view/${num}">${num}</a>
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

	<!-- csr Description Modal -->
	<form class="form-horizontal">
		<div class="modal fade" id="csrDescModal" tabindex="-1" role="dialog" aria-labelledby="csrDescModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
						<h4 class="modal-title text-danger" id="csrDescModalLabel">
							<strong>Customer Service Record</strong>
						</h4>
					</div>
					<div class="modal-body">
						<div class="form-group">
							<p class="form-control-static col-md-12">
								<textarea class="form-control" disabled="disabled" rows="10" id="csrDescModalP" style="resize:none;"></textarea>
							</p>
						</div>
					</div>
					<div class="modal-footer">
						<a href="javascript:void(0);" class="btn btn-primary" data-dismiss="modal">Close</a>
					</div>
				</div>
				<!-- /.modal-content -->
			</div>
			<!-- /.modal-dialog -->
		</div>
	</form>
	<!-- /.modal -->
	
<jsp:include page="../footer.jsp" />
<jsp:include page="../script.jsp" />
<script type="text/javascript">
(function($){
	$('#checkbox_customer_service_record_top').click(function(){
		var b = $(this).prop("checked");
		if (b) {
			$('input[name="checkbox_customer_service_record"]').prop("checked", true);
		} else {
			$('input[name="checkbox_customer_service_record"]').prop("checked", false);
		}
	});
	
	<c:forEach var="csr" items="${page.results }">
		var id = '${csr.id}';
		$('a[data-name="csr_desc_'+id+'"]').click(function(){
			$('#csrDescModalP').html($('p[data-name="csr_desc_'+this.id+'"]').attr('data-desc'));
			$('#csrDescModal').modal('show');
		});
	</c:forEach>
	
})(jQuery);
</script>
<jsp:include page="../footer-end.jsp" />