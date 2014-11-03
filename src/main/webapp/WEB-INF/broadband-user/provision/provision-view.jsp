<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<jsp:include page="../header.jsp" />
<jsp:include page="../alert.jsp" />

<div class="container">
	<div class="row">
		<div class="col-md-12">
			<div class="panel panel-success">
				<div class="panel-heading">
					<h3 class="panel-title" id="provision-view-title"></h3>
				</div>
				<div id="provision-query-tmpl"></div>
				<div id="provision-orders-tmpl"></div>
			</div>
		</div>
	</div>
</div>

<div class="modal fade" id="provision-customer-order-info-modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width:1200px">
		<div class="modal-content">
			<div class="modal-body">
				<div class="row">
					<div class="col-md-5" id="customer-information-tmpl"></div>
					<div class="col-md-7" id="customer-order-tmpl"></div>
				</div>
				<div class="row">
					<div class="col-md-12" id="customer-order-detail-tmpl"></div>
				</div>
			</div>
		</div>
	</div>
</div>

<script type="text/html" id="provision_query_tmpl" data-ctx="${ctx }" data-user_role="${userSession.user_role}">
<jsp:include page="provision-query-tmpl.html" />
</script>

<script type="text/html" id="provision_orders_tmpl">
<jsp:include page="provision-orders-tmpl.html" />
</script>

<script type="text/html" id="customer_information_tmpl">
<jsp:include page="customer-information-tmpl.html" />
</script>

<script type="text/html" id="customer_order_tmpl">
<jsp:include page="customer-order-tmpl.html" />
</script>

<script type="text/html" id="customer_order_detail_tmpl">
<jsp:include page="customer-order-detail-tmpl.html" />
</script>

<jsp:include page="../footer.jsp" />
<jsp:include page="../script.jsp" />
<script type="text/javascript" src="${ctx }/public/broadband-user/provision/provision-view.js"></script>
<jsp:include page="../footer-end.jsp" />