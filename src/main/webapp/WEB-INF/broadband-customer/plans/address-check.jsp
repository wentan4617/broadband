<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>  
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<jsp:include page="../header.jsp" />

<div class="container">
	
	<!-- <ul class="nav nav-wizard">
		<li><a href="javascript:void(0);"><span class="glyphicon glyphicon-search"></span> Check Address</a></li>
		<li><a href="javascript:void(0);"><span class="glyphicon glyphicon-pencil"></span> Fill in Application</a></li>
		<li class="active"><a href="javascript:void(0);"><span class="glyphicon glyphicon-eye-open"></span> Review & Checkout</a></li>
	</ul> -->

	<h2 class="text-center hidden-xs hidden-sm">
		Check your address whether the service can be installed
	</h2>
	<h4 class="text-center hidden-lg hidden-md">
		Check the service can be installed
	</h4>
	
	<div class="row" style="margin-top:30px;">
		<div class="col-md-6 col-md-offset-3 col-xs-12 col-sm-12">
			<div class="input-group">
				<input id="address" value="${customerReg.address }" type="text" class="form-control input-lg" placeholder="Put your address here" /> 
				<span class="input-group-btn">
					<button class="btn btn-success btn-lg ladda-button" data-style="zoom-in" type="button" id="goCheck">
						<span class="ladda-label glyphicon glyphicon-search"></span>
					</button>
				</span>
			</div>
		</div>
	</div>
	
	<p class="text-center">
		<a href="${ctx }/plans/address/clear" class="btn btn-link">Clear Address</a>
	</p>
	
	<div id="checkResult" data-select_plan_id="${select_plan_id}" data-select_plan_type="${select_plan_type}" style="min-height:600px;margin-top:15px;"></div>
</div>

<div id="map_canvas" style="width:720px;height:600px;display:none;"></div>
<script type="text/html" id="result_tmpl" data-ctx="${ctx }">
<jsp:include page="resultAddressCheck.html" />
</script>

<jsp:include page="../footer.jsp" />
<jsp:include page="../script.jsp" />
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/icheck.min.js"></script>
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/jTmpl.js"></script>
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/spin.min.js"></script>
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/ladda.min.js"></script>
<script type="text/javascript" src="${ctx}/public/broadband-customer/plans/address-check.js"></script>
<script src="https://maps.google.com/maps/api/js?sensor=false&libraries=places&region=NZ" type="text/javascript"></script>
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/autoCompleteAddress.js"></script>
<jsp:include page="../footer-end.jsp" />