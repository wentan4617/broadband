<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<jsp:include page="header.jsp" />

<style>

.home-title {
	font-size:36px;
	margin-top: 20px;
	margin-bottom: 10px;
}
</style>

<jsp:include page="top-wrap.jsp" />

<div id="map_canvas" style="width:720px;height:600px;display:none;"></div>
<script type="text/html" id="result_tmpl" data-ctx="${ctx }">
<jsp:include page="plans/resultAddressCheck.html" />
</script>

<jsp:include page="footer.jsp" />
<jsp:include page="script.jsp" />
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/jTmpl.js"></script>
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/spin.min.js"></script>
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/ladda.min.js"></script>
<script type="text/javascript" src="${ctx}/public/broadband-customer/plans/address-check.js?ver=2014103804"></script>
<script src="https://maps.google.com/maps/api/js?sensor=false&libraries=places&region=NZ" type="text/javascript"></script>
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/autoCompleteAddress.js"></script>
<jsp:include page="footer-end.jsp" />