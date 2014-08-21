<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %> 
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<jsp:include page="../header.jsp" />

<style>
.topup-list li {
	padding: 10px 20px;
}

.affix {
	width:293px;
	top:30px;
}
</style>

<div class="container" style="margin-top:20px;">
	
	<form id="customerInfoFrom" class="form-horizontal">
	
		<div class="row">
			<div class="col-md-9 col-sm-12 col-xs-12">
				<div id="select-plan"></div>
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

<script type="text/html" id="select_plan_tmpl" data-ctx="${ctx }" data-select_plan_id="${customerReg.select_plan_id}" data-select_plan_type="${customerReg.select_plan_type }">
<jsp:include page="select-plan.html" />
</script>
<script type="text/html" id="prepay_month_tmpl">
<jsp:include page="prepay-month.html" />
</script>
<script type="text/html" id="select_modem_tmpl">
<jsp:include page="select-modem.html" />
</script>
<script type="text/html" id="broadband_options_tmpl">
<jsp:include page="broadband-options.html" />
</script>
<script type="text/html" id="application_tmpl" 
data-cellphone="${customerReg.cellphone }"
data-email="${customerReg.email }"
data-title="${customerReg.title }"
data-first_name="${customerReg.first_name }"
data-last_name="${customerReg.last_name }"
data-identity_type="${customerReg.identity_type }"
data-identity_number="${customerReg.identity_number }"
data-transition_provider_name="${customerReg.customerOrder.transition_provider_name }"
data-transition_account_holder_name="${customerReg.customerOrder.transition_account_holder_name }"
data-transition_account_number="${customerReg.customerOrder.transition_account_number }"
data-transition_porting_number="${customerReg.customerOrder.transition_porting_number }">
<jsp:include page="application.html" />
</script>
<script type="text/html" id="order_modal_tmpl" data-customer-address="${customerReg.address }">
<jsp:include page="order-modal.html" />
</script>

<jsp:include page="../footer.jsp" />
<jsp:include page="../script.jsp" />
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/jTmpl.js"></script>
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/icheck.min.js"></script>
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/bootstrap-select.min.js"></script>
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/bootstrap-datepicker.js"></script>
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/spin.min.js"></script>
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/ladda.min.js"></script>
<script type="text/javascript" src="${ctx}/public/broadband-customer/plans/customer-order.js"></script>
<jsp:include page="../footer-end.jsp" />