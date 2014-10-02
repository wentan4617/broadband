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
					<h4 class="panel-title">Hardware View&nbsp;
					<c:if test="${true}">
						<div class="btn-group">
							<button type="button" class="btn btn-default btn-sm dropdown-toggle" data-toggle="dropdown">
								Operations <span class="caret"></span>
							</button>
							<ul class="dropdown-menu" data-role="menu">
								<li>
									<a href="javascript:void(0);" id="delete_selected_hardware">
										DELETE:
										<span class="text-danger">Delete Selected Hardware</span>
									</a>
								</li>
							</ul>
						</div>
					</c:if>
					</h4>
				</div>
				<c:if test="${fn:length(page.results) > 0 }">
					<form id="hardwareForm" action="${ctx }/broadband-user/plan/hardware/delete" method="post">
					<table class="table">
						<thead >
							<tr>
								<th><input type="checkbox" id="checkbox_hardwares_top" /></th>
								<th>Name</th>
								<th>Type</th>
								<th>Status</th>
								<th>Price</th>
								<th>Cost</th>
								<th>Router ADSL</th>
								<th>Router VDSL</th>
								<th>Router UFB</th>
								<th>Support VoIP</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="hardware" items="${page.results }">
								<tr class="${hardware.hardware_status == 'selling' ? 'success' : '' }">
									<td>
										<input type="checkbox" name="checkbox_hardwares" value="${hardware.id}"/>
									</td>
									<td>
										<a href="${ctx }/broadband-user/plan/hardware/edit/${hardware.id}">
											${hardware.hardware_name }
										</a>
									</td>
									<td>
										${hardware.hardware_type }
									</td>
									<td>
										${hardware.hardware_status }
									</td>
									<td>
										<fmt:formatNumber value="${hardware.hardware_price }" type="number" pattern="#,#00.00" />
										
									</td>
									<td>
										<fmt:formatNumber value="${hardware.hardware_cost }" type="number" pattern="#,#00.00" />
										
									</td>
									<td>
										<c:if test="${hardware.router_adsl}">
											<span class="glyphicon glyphicon-ok"></span>
										</c:if>
									</td>
									<td>
										<c:if test="${hardware.router_vdsl }">
											<span class="glyphicon glyphicon-ok"></span>
										</c:if>
									</td>
									<td>
										<c:if test="${hardware.router_ufb }">
											<span class="glyphicon glyphicon-ok"></span>
										</c:if>
									</td>
									<td>
										<c:if test="${hardware.support_voip }">
											<span class="glyphicon glyphicon-ok"></span>
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
											<a href="${ctx}/broadband-user/plan/hardware/view/${num}">${num}</a>
										</li>
									</c:forEach>
								</ul>
							</td>
						</tr>
					</tfoot>
					</table>
					</form>
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
	$('#checkbox_hardwares_top').click(function(){
		var b = $(this).prop("checked");
		if (b) {
			$('input[name="checkbox_hardwares"]').prop("checked", true);
		} else {
			$('input[name="checkbox_hardwares"]').prop("checked", false);
		}
	});

	$('#delete_selected_hardware').click(function(){
		$('#hardwareForm').submit();
	});
})(jQuery);
</script>
<jsp:include page="../footer-end.jsp" />