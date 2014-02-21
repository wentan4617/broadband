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
			<div class="panel panel-default">
				<div class="panel-heading">Topup View</div>
				<c:if test="${fn:length(page.results) > 0 }">
					<table class="table">
						<thead>
							<tr>
								<th><input type="checkbox" id="checkbox_topups_top" /></th>
								<th>Topup Name</th>
								<th>Topup fee (Inc GST)($)</th>
								<th>Topup Data Flow (GB)</th>
								<th>Topup Status</th>
								<th>&nbsp;</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="topup" items="${page.results }">
								<tr class="${topup.topup_status == 'active' ? 'success' : '' }">
									<td>
										<input type="checkbox" name="checkbox_topups" value="${topup.id}"/>
									</td>
									<td>
										<a href="${ctx }/broadband-user/plan/topup/edit/${topup.id}">
											${topup.topup_name }
										</a>
									</td>
									<td>
										<fmt:formatNumber value="${topup.topup_fee }" type="number" pattern="#,#00.00" />
										
									</td>
									<td>${topup.topup_data_flow }</td>
									<td>${topup.topup_status}</td>
									<td>&nbsp;</td>
								</tr>
							</c:forEach>
						</tbody>
						<tfoot>
						<tr>
							<td colspan="11">
								<ul class="pagination">
									<c:forEach var="num" begin="1" end="${page.totalPage }" step="1">
										<li class="${page.pageNo == num ? 'active' : ''}">
											<a href="${ctx}/broadband-user/plan/topup/view/${num}">${num}</a>
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
<script type="text/javascript">
(function($){
	$('#checkbox_topups_top').click(function(){
		var b = $(this).prop("checked");
		if (b) {
			$('input[name="checkbox_topups"]').prop("checked", true);
		} else {
			$('input[name="checkbox_topups"]').prop("checked", false);
		}
	});
})(jQuery);
</script>
<jsp:include page="../footer-end.jsp" />