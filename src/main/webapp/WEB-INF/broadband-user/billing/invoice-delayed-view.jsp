<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<jsp:include page="../header.jsp" />
<jsp:include page="../alert.jsp" />

<style type="text/css">
thead th {text-align:center;}
tbody td {text-align:center;}
</style>

<div class="container">
	<div class="row">
		<div class="col-md-12">
		
			<div class="panel panel-success">
				<div class="panel-heading">
					<h4 class="panel-title">
						Invoice Delayed View
					</h4>
				</div>
				<c:if test="${fn:length(pageCos.results) > 0 }">
					<table class="table" style="font-size:12px;">
						<thead >
							<tr>
								<th><input type="checkbox" id="checkbox_cis_top" /></th>
								<th>Customer Id</th>
								<th>Order Id</th>
								<th>Name</th>
								<th>Next Invoice Create Date</th>
								<th>Order Type</th>
								<th>Order Status</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="co" items="${pageCos.results }">
								<tr>
									<td>
										<input type="checkbox" name="checkbox_cis" value="${co.id}"/>
									</td>
									<td>
										<a target="_blank" href="${ctx }/broadband-user/crm/customer/edit/${co.customer_id}">${co.customer_id}</a>
									</td>
									<td>
										${co.id }
									</td>
									<td>
										${co.first_name }&nbsp;&nbsp;${co.last_name}
									</td>
									<td>
										${co.next_invoice_create_date_str }
									</td>
									<td>
										${co.order_type }
									</td>
									<td>
										${co.order_status }
									</td>
									<td>
									</td>
								</tr>
							</c:forEach>
						</tbody>
						<tfoot>
							<tr>
								<td colspan="11">
									<ul class="pagination">
										<c:forEach var="num" begin="1" end="${pageCos.totalPage }" step="1">
											<li class="${pageCos.pageNo == num ? 'active' : ''}">
												<a href="${ctx}/broadband-user/billing/invoice/view/invoice-delayed/${num}">${num}</a>
											</li>
										</c:forEach>
									</ul>
								</td>
							</tr>
						</tfoot>
					</table>
				</c:if>
				<c:if test="${fn:length(pageCis.results) <= 0 && fn:length(pageCos.results) <= 0 }">
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
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/bootstrap-datepicker.js"></script>
<script type="text/javascript">
(function($) {
	$('button[data-toggle="tooltip"]').tooltip();
	
	$('#checkbox_cis_top').click(function() {
		var b = $(this).prop("checked");
		if (b) {
			$('input[name="checkbox_cis"]').prop("checked", true);
		} else {
			$('input[name="checkbox_cis"]').prop("checked", false);
		}
	});
	
	$('#year_input_datepicker').datepicker({
	    format: "yyyy",
	    autoclose: true,
	    minViewMode: 2
	});
	
	$('#month_input_datepicker').datepicker({
	    format: "yyyy-mm",
	    autoclose: true,
	    minViewMode: 1
	});
	
	$('#year_input_datepicker').datepicker().on('changeDate', function(ev){
		window.location.href = $('#year_input').val();
	});
	
	$('#month_input_datepicker').datepicker().on('changeDate', function(ev){
		window.location.href = $('#month_input').val();
	});
	
})(jQuery);
</script>
<jsp:include page="../footer-end.jsp" />