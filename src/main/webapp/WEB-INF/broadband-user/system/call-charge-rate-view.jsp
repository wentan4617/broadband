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
					<h4 class="panel-title">Call Charge Rate View</h4>
				</div>
				<c:if test="${fn:length(page.results) > 0 }">
					<table class="table" style="font-size:12px;">
						<thead >
							<tr>
								<th><input type="checkbox" id="checkbox_callChargeRates_top" /></th>
								<th>Customer Type</th>
								<th>Number Type</th>
								<th>Charge Way</th>
								<th>Dial Destination</th>
								<th>Cost Per Minute</th>
								<th>Cost Per Second</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="callChargeRate" items="${page.results }">
								<tr>
									<td>
										<input type="checkbox" name="checkbox_callChargeRates" value="${callChargeRate.id}"/>
									</td>
									<td>
										${callChargeRate.customer_type }
									</td>
									<td>
										${callChargeRate.number_type }
									</td>
									<td>
										${callChargeRate.charge_way }
									</td>
									<td>
										${callChargeRate.dail_destination }
									</td>
									<td>
										${callChargeRate.cost_per_minute }
									</td>
									<td>
										${callChargeRate.cost_per_second }
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
												<a href="${ctx}/broadband-user/system/call_charge_rate/view/${num}">${num}</a>
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
	
	$('#checkbox_callChargeRates_top').click(function() {
		var b = $(this).prop("checked");
		if (b) {
			$('input[name="checkbox_callChargeRates"]').prop("checked", true);
		} else {
			$('input[name="checkbox_callChargeRates"]').prop("checked", false);
		}
	});

})(jQuery);
</script>
<jsp:include page="../footer-end.jsp" />