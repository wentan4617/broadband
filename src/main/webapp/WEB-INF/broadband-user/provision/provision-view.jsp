<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<jsp:include page="../header.jsp" />
<jsp:include page="../alert.jsp" />

<div class="container">
	<div class="row">
		<div class="col-md-12">

			<jsp:include page="provision-query.jsp" />

			<div class="panel panel-success">
				<div class="panel-heading">
					<h4 class="panel-title">

						${panelheading } &nbsp;
						
						<c:if test="${order_status == 'pending' }">
							<div class="btn-group">
								<button type="button" class="btn btn-default btn-xs dropdown-toggle" data-toggle="dropdown">
									Operate <span class="caret"></span>
								</button>
								<ul class="dropdown-menu" data-role="menu">
									<li>
										<a href="javascript:void(0);" id="pending_to_ordering" data-val="ordering-pending">
											Order Status: 
											<span class="text-danger">Pending to Ordering-Pending</span>
										</a>
									</li>
								</ul>
							</div>
						</c:if>
						
						<c:if test="${order_status == 'paid' }">
							<div class="btn-group">
								<button type="button" class="btn btn-default btn-xs dropdown-toggle" data-toggle="dropdown">
									Operate <span class="caret"></span>
								</button>
								<ul class="dropdown-menu" data-role="menu">
									<li>
										<a href="javascript:void(0);" id="paid_to_ordering" data-val="ordering-paid">
											Order Status: 
											<span class="text-danger">Paid to Ordering-Paid</span>
										</a>
									</li>
								</ul>
							</div>
						</c:if>
					</h4>

				</div>
				
				
				<c:if test="${fn:length(page.results) > 0 }">
					<form id="provisionForm" action="${ctx }/broadband-user/provision/customer/order/status" method="post">
						<input type="hidden" id="process_way" name="process_way" />
						<input type="hidden" id="order_status" name="order_status" value="${order_status}"/>
						<input type="hidden" id="change_order_status" name="change_order_status" />
						<table class="table">
							<thead>
								<tr>
									<th><input type="checkbox" id="checkbox_orders_top" /></th>
									<th>Order ID</th>
									<th>Customer Name</th>
									<th>Price ($)</th>
									<th>Create Date</th>
									<th>Status</th>
									<th>Type</th>
									<th>Broadband Type</th>
									<th>Post</th>
									<th>&nbsp;</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="order" items="${page.results }">
									<tr>
										<td><input type="checkbox" name="checkbox_orders" value="${order.id}" /></td>
										<td>
											<a href="#" data-name="show_customer_order_info" data-id="${order.id}">${order.id}</a>
										</td>
										<td>
											<a href="${ctx}/broadband-user/crm/customer/edit/${order.customer.id}" data-name="show_customer_info" data-id="${order.customer.id}"> 
											
												<c:choose>
													<c:when test="${order.customer.customer_type == 'personal' }">
														${order.customer.first_name } ${order.customer.last_name }
													</c:when>
													<c:when test="${order.customer.customer_type == 'business' }">
														${order.customer.organization.org_name == null ? order.customer.id : order.customer.organization.org_name }
													</c:when>
												</c:choose>
											</a>				
										</td>
										<td>
											<c:if test="${order.order_total_price != null }">
												<fmt:formatNumber value="${order.order_total_price }" type="number" pattern="#,##0.00" />
											</c:if>
										</td>
										<td><fmt:formatDate value="${order.order_create_date }" type="both" pattern="yyyy-MM-dd HH:mm:ss" /></td>
										<td>${order.order_status }</td>
										<td>${order.order_type }</td>
										<td>${order.order_broadband_type }</td>
										<td>
											<c:if test="${order.hardware_post > 0}">
												<a href="javascript:void(0);" data-toggle="tooltip" data-placement="bottom" data-original-title="hardware need to be post"><span class="glyphicon glyphicon-gift"></span></a>
											</c:if>
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
													<a href="${ctx}/broadband-user/provision/customer/view/${num}/${order_status}">${num}</a>
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
(function($) {

	$('#checkbox_orders_top').click(function() {
		var b = $(this).prop("checked");
		if (b) {
			$('input[name="checkbox_orders"]').prop("checked", true);
		} else {
			$('input[name="checkbox_orders"]').prop("checked", false);
		}
	});
	
	$('#pending_to_ordering').click(function(){
		$('#process_way').val('pending to ordering-pending');
		$('#change_order_status').val($(this).attr('data-val'));
		$('#provisionForm').submit();
	});

	$('#paid_to_ordering').click(function() {
		$('#process_way').val('paid to ordering-paid');
		$('#change_order_status').val($(this).attr('data-val'));
		$('#provisionForm').submit();
	});
	
})(jQuery);
</script>

<!-- provision customer order information model -->
<jsp:include page="provision-customer-order-info.jsp" />
<jsp:include page="../footer-end.jsp" />