<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<jsp:include page="../header.jsp" />
<jsp:include page="../alert.jsp" />

<style>
.panel-default {
	border-top-color:transparent;
}
</style>


<div class="container">
	<div class="row">
		<div class="col-md-12">
		
			<!-- Nav tabs -->
			<ul class="nav nav-tabs">
				<li class="active"><a href="#customer_edit" data-toggle="tab"><strong>Customer Edit</strong></a></li>
				<li><a href="#order_detail" data-toggle="tab"><strong>Order Detail</strong></a></li>
				<li><a href="#invoice_detail" data-toggle="tab"><strong>Invoice Detail</strong></a></li>
				<li><a href="#transaction_detail" data-toggle="tab"><strong>Transaction Detail</strong></a></li>
				<li><a href="#customer_service_record_detail" data-toggle="tab"><strong>Customer Service Record</strong></a></li>
				<li><a href="#customer_ticket_record_detail" data-toggle="tab"><strong>Customer Ticket Record</strong></a></li>
			</ul>

			<!-- Tab panes -->
			<div class="tab-content panel panel-default">
				<div class="panel-body tab-pane fade in active" id="customer_edit" ></div>
				<div class="panel-body tab-pane fade" id="order_detail" ></div>
				<div class="panel-body tab-pane fade" id="invoice_detail"></div>
				<div class="panel-body tab-pane fade" id="transaction_detail"></div>
				<div class="panel-body tab-pane fade" id="customer_service_record_detail"></div>
				<div class="panel-body tab-pane fade" id="customer_ticket_record_detail"></div>
			</div>
		</div>
	</div>
</div>

<!-- Customer Info Template -->
<script type="text/html" id="customer_info_table_tmpl">
<jsp:include page="customer-info.html" />
</script>
<!-- Customer Order Template -->
<script type="text/html" id="customer_order_table_tmpl">
<jsp:include page="customer-order.html" />
</script>
<!-- Customer Invoice Detail Template -->
<script type="text/html" id="invoice_table_tmpl">
<jsp:include page="customer-invoice-view-page.html" />
</script>
<!-- Customer Transaction Detail Template -->
<script type="text/html" id="transaction_table_tmpl">
<jsp:include page="customer-transaction-view-page.html" />
</script>
<!-- Customer Service Record Detail Template -->
<script type="text/html" id="customer_service_record_table_tmpl">
<jsp:include page="customer-service-record-view-page.html" />
</script>
<!-- Customer Ticket Detail Template -->
<script type="text/html" id="customer_ticket_record_table_tmpl">
<jsp:include page="customer-ticket-record-view-page.html" />
</script>

<jsp:include page="../footer.jsp" />
<jsp:include page="../script.jsp" />
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/bootstrap-datepicker.js"></script>
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/bootstrap-select.min.js"></script>
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/icheck.min.js"></script>
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/jTmpl.js"></script>
<script>

	// BEGIN PROPERTY
	var orderIds = new Array();
	<c:forEach var="co" items="${customer.customerOrders }">
		orderIds.push('${co.id}');
	</c:forEach>
	
	var customerId = '${customer.id}';
	var ctx = '${ctx}';
	var user_role = '${userSession.user_role}';
	var user_id = '${userSession.id}';
	var customer_type = '${customer.customer_type}';
	
	// END PROPERTY
	
</script>
<script type="text/javascript" src="${ctx}/public/broadband-user/crm/customer.js"></script>
<jsp:include page="../footer-end.jsp" />