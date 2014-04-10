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
		
			<jsp:include page="customer-query.jsp" />
		
		
			<div class="panel panel-default">
				<div class="panel-heading">
					<h4 class="panel-title">Customer View</h4>
				</div>
				<c:if test="${fn:length(page.results) > 0 }">
					<table class="table" style="font-size:12px;">
						<thead>
							<tr>
								<th><input type="checkbox" id="checkbox_customer_top" /></th>
								<th>Customer</th>
								<th>Type</th>
								<th>Address</th>
								<th>Email</th>
								<th>Mobile</th>
								<th>Status</th>
								<th>Reg On</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="customer" items="${page.results }">
								<tr>
									<td>
										<input type="checkbox" name="checkbox_customer" value="${customer.id}"/>
									</td>
									<td>
										<a href="${ctx }/broadband-user/crm/customer/edit/${customer.id}">
											<c:choose>
												<c:when test="${customer.customer_type == 'personal' }">
													${customer.first_name } ${customer.last_name }
												</c:when>
												<c:when test="${customer.customer_type == 'business' }">
													${customer.organization.org_name }
												</c:when>
											</c:choose>
										</a>
									</td>
									<td>${customer.customer_type }</td>
									<td>${customer.address }</td>
									<td>${customer.email }</td>
									<td>${customer.cellphone }</td>
									<td>${customer.status}</td>
									<td>${customer.register_date_str }</td>
								</tr>
							</c:forEach>
						</tbody>
						<tfoot>
						<tr>
							<td colspan="11">
								<ul class="pagination">
									<c:forEach var="num" begin="1" end="${page.totalPage }" step="1">
										<li class="${page.pageNo == num ? 'active' : ''}">
											<a href="${ctx}/broadband-user/crm/customer/view/${num}">${num}</a>
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
	$('#checkbox_customer_top').click(function(){
		var b = $(this).prop("checked");
		if (b) {
			$('input[name="checkbox_customer"]').prop("checked", true);
		} else {
			$('input[name="checkbox_customer"]').prop("checked", false);
		}
	});
	
	$('input[data-role="query"]').each(function(){
		var id = $(this).attr('data-id');
		var val = $(this).prop("checked");
		//console.log(id);
		if (val) {
			$('#' + id).prop("disabled", "");
		} else {
			$('#' + id).prop("disabled", "disabled");
		}
	});
	
	$('input[data-role="query"]').click(function(){
		var id = $(this).attr('data-id');
		var val = $(this).prop("checked");
		//console.log(id);
		if (val) {
			$('#' + id).prop("disabled", "");
		} else {
			
			$('#' + id).prop("disabled", "disabled");
		}
		
	});
})(jQuery);
</script>
<jsp:include page="../footer-end.jsp" />