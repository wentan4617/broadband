<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>  
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<jsp:include page="../../header.jsp" />

<style>
.tab-content {
	border-top-color:transparent;
}
</style>
<style>
#navhead {
	margin-bottom:0;
}
</style>

<div class="container">

	<div class="hidden-xs hidden-sm">
		<ul class="nav nav-pills nav-wizard" style="width: 750px; margin: 0 auto;">
			<li class="active"><a href="javascript:void(0);"><span class="glyphicon glyphicon-star"></span> Select One Plans</a><div class="nav-arrow"></div></li>
			<li><div class="nav-wedge"></div><a href="javascript:void(0);"><span class="glyphicon glyphicon-search"></span> Check Address</a><div class="nav-arrow"></div></li>
			<li><div class="nav-wedge"></div><a href="javascript:void(0);"><span class="glyphicon glyphicon-pencil"></span> Fill in Application</a><div class="nav-arrow"></div></li>
			<li><div class="nav-wedge"></div><a href="javascript:void(0);"><span class="glyphicon glyphicon-eye-open"></span> Review & Checkout</a></li>
		</ul>
		<hr>
	</div>
	
	<div class="row">
		<p class="text-center">
		<c:choose>
			<c:when test="${clasz=='business' }">
				<a href="${ctx }/broadband-user/sale/plans/broadband/personal" >to Personal Plans</a>
			</c:when>
			<c:when test="${clasz=='personal' }">
				<a href="${ctx }/broadband-user/sale/plans/broadband/business" >to Business Plans</a>
			</c:when>
		</c:choose>
		</p>
		<div class="col-md-6 col-md-offset-3">
			<ul class="nav nav-pills nav-justified" style="margin:10px auto 20px;">
				<li class="${adsl }"><a href="${ctx }/broadband-user/sale/plans/broadband/${clasz}">Broadband</a></li>
				<li class="${vdsl }"><a href="${ctx }/broadband-user/sale/plans/ultra-fast-vdsl/${clasz}">Ultra Fast VDSL</a></li>
				<li class="${ufb }"><a href="${ctx }/broadband-user/sale/plans/ultra-fast-fibre/${clasz}">Ultra Fast Fibre </a></li>
			</ul>
		</div>
	</div>

	<c:if test="${customerRegSale.broadband != null && !fn:contains(customerRegSale.broadband.services_available, type)}">
		<div class="alert alert-warning">
			<h2 class="text-center">Sorry, it looks like this service isn’t available at your place yet.</h2>
			<p class="text-center">Not to worry – we have a range of great alternatives. And, if you sign up with us, </p>
			<p class="text-center">upgrading to this service when it becomes available is super easy.</p>
		</div>
		<p>&nbsp;</p>
	</c:if>
	
	<div class="row">
		<div class="col-md-12">
		
			<!-- Nav tabs -->
			<ul class="nav nav-tabs nav-justified">
				<li class="active"><a href="#homeline" data-toggle="tab"><strong style="font-size:24px;"><span class="phone"></span> ${clasz == 'personal' ? 'Homeline' : 'Businessline'} Included</strong></a></li>
				<li><a href="#naked" data-toggle="tab"><strong style="font-size:24px;"><span class="without_phone"></span> Naked Broadband</strong></a></li>
			</ul>

			<c:set var="planMap" value="${planTypeMap[type_search] }"></c:set>
			<c:set var="plansClothed" value="${planMap['plansClothed'] }"></c:set>
			<c:set var="plansNaked" value="${planMap['plansNaked'] }"></c:set>
			<c:set var="color" value="success"></c:set>
			<c:choose>
				<c:when test="${type_search=='ADSL' }"><c:set var="color" value="success"></c:set></c:when>
				<c:when test="${type_search=='VDSL' }"><c:set var="color" value="danger"></c:set></c:when>
				<c:when test="${type_search=='UFB' }"><c:set var="color" value="info"></c:set></c:when>
			</c:choose>

			<!-- Tab panes -->
			<div class="tab-content panel panel-success" >
				<div class="panel-body tab-pane fade in active" id="homeline" >
				
					<div class="row">
					
					<c:choose>
					<c:when test="${fn:length(plansClothed) > 0}">
						<c:set var="count" value="${fn:length(plansClothed)}"></c:set>
						<c:forEach var="plan" items="${plansClothed }" varStatus="item">
					
						<div class="col-md-${count >= 3 ? 4 : 6 } " style="margin-top:5px;">
							<button class="btn btn-${color } btn-block" data-type data-id="${plan.id }">
								<p class="text-center" style="font-weight:bold;font-size: 50px;">
									<c:choose>
										<c:when test="${plan.data_flow < 0 }">Unlimited</c:when>
										<c:otherwise>${plan.data_flow } GB</c:otherwise>
									</c:choose>
								</p>
								<p class="text-center" style="font-weight:bold;font-size: 24px;">
									(${clasz == 'personal' ? 'Homeline' : 'Businessline'} Included)
								</p>
								<p class="text-center" style="font-weight:bold;">
									<span style="font-size:30px;">$</span>
									<span style="font-size:60px;" class="hidden-xs"> 
										<fmt:formatNumber value="${plan.plan_price} " type="number" pattern="##0" />
									</span>
									<span style="font-size:35px;" class="hidden-lg hidden-md hidden-sm"> 
										<fmt:formatNumber value="${plan.plan_price} " type="number" pattern="##0" />
									</span>
									/ Month
								</p>
							</button>
						</div>
						
						</c:forEach>
					</c:when>
					<c:otherwise>
						<div class="col-md-12">
							<div class="alert alert-warning">
								Sorry Dear, no plans has been found.
							</div>
						</div>
						
					</c:otherwise>
					</c:choose>
					
					</div>
					
				</div>
				<div class="panel-body tab-pane fade" id="naked" >
					<div class="row">
					
					<c:choose>
					<c:when test="${fn:length(plansNaked) > 0}">
						<c:set var="count" value="${fn:length(plansNaked)}"></c:set>
						<c:forEach var="plan" items="${plansNaked }">
					
						<div class="col-md-${count >= 3 ? 4 : 6 }" style="margin-top:5px;">
							<button class="btn btn-${color } btn-block" data-type data-id="${plan.id }">
								<p class="text-center" style="font-weight:bold;font-size: 50px;">
									<c:choose>
										<c:when test="${plan.data_flow < 0 }">Unlimited</c:when>
										<c:otherwise>${plan.data_flow } GB</c:otherwise>
									</c:choose>
								</p>
								<p class="text-center" style="font-weight:bold;font-size: 25px;">
									(Without ${clasz == 'personal' ? 'Homeline' : 'Businessline'})
								</p>
								<p class="text-center" style="font-weight:bold;">
									<span style="font-size:30px;">$</span>
									<span style="font-size:60px;" class="hidden-xs"> 
										<fmt:formatNumber value="${plan.plan_price} " type="number" pattern="##0" />
									</span>
									<span style="font-size:35px;" class="hidden-lg hidden-md hidden-sm"> 
										<fmt:formatNumber value="${plan.plan_price} " type="number" pattern="##0" />
									</span>
									/ Month
								</p>
							</button>
						</div>
						
						</c:forEach>
					</c:when>
					<c:otherwise>
						<div class="col-md-12">
							<div class="alert alert-warning">
								Sorry Dear, no plans has been found.
							</div>
						</div>
						
					</c:otherwise>
					</c:choose>
					
					</div>
				
				</div>
			</div>
		</div>
	</div>
	
	<p>&nbsp;</p>
</div>

<jsp:include page="../../footer.jsp" />
<jsp:include page="script.jsp" />
<script type="text/javascript">
(function($){
	
	$('button[data-type]').click(function(){
		var select_plan_id = $(this).attr('data-id');
		window.location.href = '${ctx}/broadband-user/crm/plans/address-check/${type_search}/${clasz}/' + select_plan_id;
	});

})(jQuery);
</script>
<jsp:include page="../../footer-end.jsp" />