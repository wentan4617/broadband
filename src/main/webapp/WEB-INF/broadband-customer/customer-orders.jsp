<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<jsp:include page="header.jsp" />
<jsp:include page="alert.jsp" />

<div class="container">
	<div class="row">
		<div class="col-md-3">
			<jsp:include page="sidebar.jsp" />
		</div>
		<div class="col-md-9">
			<div id="customer-orders"></div>
		</div>
	</div>
</div>

<script type="text/html" id="customer_orders_tmpl" data-ctx="${ctx }">
<jsp:include page="customer-orders-tmpl.html" />
</script>

<script type="text/html" id="customer_orders_invoices_tmpl" data-ctx="${ctx }">
<jsp:include page="customer-orders-invoices-tmpl.html" />
</script>

<script type="text/html" id="customer_orders_data_tmpl">
<jsp:include page="customer-orders-data-tmpl.html" />
</script>

<script type="text/html" id="customer_usage_view_tmpl">
<jsp:include page="customer-usage-view.html" />
</script>

<jsp:include page="footer.jsp" />
<jsp:include page="script.jsp" />
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/Chart.min.js"></script>
<script type="text/javascript" src="${ctx}/public/broadband-customer/customer-orders.js"></script>
<jsp:include page="footer-end.jsp" />