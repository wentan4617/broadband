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
				<div class="panel-heading">
					<h4 class="panel-title">Provision View (Customer Order)</h4>
				</div>
				<c:if test="${fn:length(page.results) > 0 }">
					<table class="table">
						<thead >
							<tr>
								<th><input type="checkbox" id="checkbox_provisions_top" /></th>
								<th>Provision Id</th>
								<th>Operator</th>
								<th>Process Time</th>
								<th>Order Sort</th>
								<th>Customer Order Id</th>
								<th>Process Way</th>
								<th>&nbsp;</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="provision" items="${page.results }">
								<tr>
									<td>
										<input type="checkbox" name="checkbox_provisions" value="${provision.id}"/>
									</td>
									<td>
										${provision.id }
									</td>
									<td>
										${provision.user.user_name }
									</td>
									<td>
										<fmt:formatDate value="${provision.process_datetime }" type="both"/>
									</td>
									<td>
										${provision.order_sort }
									</td>
									<td>
										${provision.order_id_customer }
									</td>
									<td>
										${provision.process_way }
									</td>
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
												<a href="${ctx}/broadband-user/provision/view/${num}">${num}</a>
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
	$('#checkbox_provisions_top').click(function(){
		var b = $(this).prop("checked");
		if (b) {
			$('input[name="checkbox_provisions"]').prop("checked", true);
		} else {
			$('input[name="checkbox_provisions"]').prop("checked", false);
		}
	});
})(jQuery);
</script>
<jsp:include page="../footer-end.jsp" />