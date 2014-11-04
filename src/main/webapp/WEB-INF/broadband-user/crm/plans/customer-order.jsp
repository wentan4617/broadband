<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<jsp:include page="../../header.jsp" />

<style>
.topup-list li {
	width: 100%;
	padding: 10px 20px;
}
.affix {
	width:293px;
	top:30px;
}
</style>

<div class="container" style="margin-top:20px;">

	<div class="hidden-xs hidden-sm">
		<ul class="nav nav-pills nav-wizard" style="width: 550px; margin: 0 auto;">
			<li><a href="javascript:void(0);"><span class="glyphicon glyphicon-search"></span> Check Address</a><div class="nav-arrow"></div></li>
			<li class="active"><div class="nav-wedge"></div><a href="javascript:void(0);"><span class="glyphicon glyphicon-pencil"></span> Fill in Application</a><div class="nav-arrow"></div></li>
			<li><div class="nav-wedge"></div><a href="javascript:void(0);"><span class="glyphicon glyphicon-eye-open"></span> Review & Checkout</a></li>
		</ul>
		<hr>
	</div>
	
	
	<form id="customerInfoFrom" class="form-horizontal">
	
		<div class="row">
			<div class="col-md-9 col-sm-12 col-xs-12">
				
				<div id="select-plan"></div>
				<div id="addons"></div>
				<div id="open-term"></div>
				<div id="prepay-month"></div>
				<div id="select-modem"></div>
				<div id="broadband-options"></div>
				<div id="application"></div>
				
			</div>
			<!-- order-modal -->
			<div class="col-md-3 hidden-xs hidden-sm" style="padding: 0;">
				<div data-spy="affix" data-offset-top="150" id="order-modal"></div>
			</div>
		</div>
		
	</form>
	
</div>

<script type="text/html" id="select_plan_tmpl" 
data-ctx="${ctx }" 
data-select_plan_group="${customerRegAdmin.select_plan_group}"
data-select_plan_id="${customerRegAdmin.select_plan_id}" 
data-select_plan_type="${customerRegAdmin.select_plan_type }"
data-select_customer_type="${customerRegAdmin.select_customer_type }"
data-sale-id="${customerRegAdmin.customerOrder.sale_id }"
data-promotion="${customerRegAdmin.customerOrder.promotion }"
data-neworder="${customerRegAdmin.newOrder }">
<jsp:include page="select-plan.html" />
</script>

<script type="text/html" id="promotion_code_tmpl">
<jsp:include page="promotion_code.html" />
</script>

<script type="text/html" id="open_term_tmpl"
data-contract="${customerRegAdmin.customerOrder.contract }">
<jsp:include page="open-term.html" />
</script>

<script type="text/html" id="prepay_month_tmpl"
data-prepay_months="${customerRegAdmin.customerOrder.prepay_months }">
<jsp:include page="prepay-month.html" />
</script>

<script type="text/html" id="select_modem_tmpl"
data-hardware_id_selected="${customerRegAdmin.customerOrder.hardware_id_selected }">
<jsp:include page="select-modem.html" />
</script>

<script type="text/html" id="broadband_options_tmpl"
data-order_broadband_type="${customerRegAdmin.customerOrder.order_broadband_type }">
<jsp:include page="broadband-options.html" />
</script>

<script type="text/html" id="application_tmpl" 
data-cellphone="${customerRegAdmin.cellphone }"
data-email="${customerRegAdmin.email }"
data-title="${customerRegAdmin.title }"
data-first_name="${customerRegAdmin.first_name }"
data-last_name="${customerRegAdmin.last_name }"
data-identity_type="${customerRegAdmin.identity_type }"
data-identity_number="${customerRegAdmin.identity_number }"
data-_transition_provider_name="${customerRegAdmin.customerOrder._transition_provider_name }"
data-transition_provider_name="${customerRegAdmin.customerOrder.transition_provider_name }"
data-transition_account_holder_name="${customerRegAdmin.customerOrder.transition_account_holder_name }"
data-transition_account_number="${customerRegAdmin.customerOrder.transition_account_number }"
data-transition_porting_number="${customerRegAdmin.customerOrder.transition_porting_number }"
data-org_type="${customerRegAdmin.customerOrder.org_type }"
data-org_name="${customerRegAdmin.customerOrder.org_name }"
data-org_trading_name="${customerRegAdmin.customerOrder.org_trading_name }"
data-org_register_no="${customerRegAdmin.customerOrder.org_register_no }"
data-org_incoporate_date="${customerRegAdmin.customerOrder.org_incoporate_date_str }"
data-holder_name="${customerRegAdmin.customerOrder.holder_name }"
data-holder_job_title="${customerRegAdmin.customerOrder.holder_job_title }"
data-holder_phone="${customerRegAdmin.customerOrder.holder_phone }"
data-holder_email="${customerRegAdmin.customerOrder.holder_email }">
<jsp:include page="application.html" />
</script>

<script type="text/html" id="addons_tmpl">
<jsp:include page="addons.html" />
</script>

<script type="text/html" id="addons_table_tmpl">
<jsp:include page="addons-table.html" />
</script>

<script type="text/html" id="order_modal_tmpl" data-customer-address="${customerRegAdmin.address }">
<jsp:include page="order-modal.html" />
</script>

<jsp:include page="../../footer.jsp" />
<jsp:include page="script.jsp" />
<script type="text/javascript" src="${ctx}/public/broadband-user/crm/plans/customer-order.js?ver=2014114942"></script>
<jsp:include page="../../footer-end.jsp" />