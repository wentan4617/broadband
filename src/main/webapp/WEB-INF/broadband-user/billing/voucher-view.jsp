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
							Voucher Query
						</a>
					</h4>
				</div>
				<div id="collapseOne" class="panel-collapse collapse in">
					<div class="panel-body">
						<div class="btn-group btn-group">
							<a href="${ctx}/broadband-user/billing/voucher/view/1/used" class="btn btn-default ${usedActive }">
								Used&nbsp;<span class="badge">${usedSum}</span>
							</a>
							<a href="${ctx}/broadband-user/billing/voucher/view/1/unused" class="btn btn-default ${unusedActive }">
								Unused&nbsp;<span class="badge">${unusedSum}</span>
							</a>
						<div class="btn-group btn-group">
							<a href="${ctx}/broadband-user/billing/voucher/view/1/posted" class="btn btn-default ${postedActive }">
								Posted&nbsp;<span class="badge">${postedSum}</span>
							</a>
							<a href="${ctx}/broadband-user/billing/voucher/view/1/unpost" class="btn btn-default ${unpostActive }">
								Unpost&nbsp;<span class="badge">${unpostSum}</span>
							</a>
						</div>
					</div>
				</div>
			</div>
			<div class="panel panel-success">
				<div class="panel-heading">
					<h4 class="panel-title">Voucher View</h4>
				</div>
				<c:if test="${fn:length(page.results) > 0 }">
					<table class="table" style="font-size:12px;">
						<thead >
							<tr>
								<th><input type="checkbox" id="checkbox_vs_top" /></th>
								<th>Pin Number</th>
								<th>Face Value</th>
								<th>Status</th>
								<th>Customer Id</th>
								<th>Post To</th>
								<th>Comment</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="v" items="${page.results }">
								<tr>
									<td>
										<input type="checkbox" name="checkbox_vs" value="${v.serial_number}"/>
									</td>
									<td>
										${fn:substring(v.card_number,0,3)}***********
									</td>
									<td>
										${v.face_value }
									</td>
									<td>
										${v.status}
									</td>
									<td>
										${v.customer_id}
									</td>
									<td>
										${v.post_to}
									</td>
									<td>
										${v.comment}
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
												<a href="${ctx}/broadband-user/billing/voucher/view/${num}/${status}">${num}</a>
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

<jsp:include page="../footer.jsp" />
<jsp:include page="../script.jsp" />
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/bootstrap-select.min.js"></script>
<script type="text/javascript">
(function($) {
	$('button[data-toggle="tooltip"]').tooltip();
	
	$('#checkbox_vs_top').click(function() {
		var b = $(this).prop("checked");
		if (b) {
			$('input[name="checkbox_vs"]').prop("checked", true);
		} else {
			$('input[name="checkbox_vs"]').prop("checked", false);
		}
	});
	
})(jQuery);
</script>
<jsp:include page="../footer-end.jsp" />