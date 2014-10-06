<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>  
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<jsp:include page="../header.jsp" />

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

<div class="bs-docs-header cyberpark-home-bg" style="padding-bottom: 0;">
	<div class="container">
		<div class="row">
			
			<c:choose>
				<c:when test="${adsl == 'active' }">
					<div class="col-md-6">
						<h1 class="text-left">ADSL BROADBAND</h1>
						<p class="text-left hidden-xs" style="font-size:16px;">We provide the best deal on ADSL broadband plan. No speed limited and data cap on our Unlimited ADSL plans. All our plans are pay in advance. It provides you a clear and easy read bill. Simple and easy on-line application method can be filled in 5 mins. Or, give us a call, 0800 2 CYBER(29237). Our customer service can help you to apply your broadband as well.</p>
					</div>
					<div class="col-md-6">
						<img class="hidden-xs" src="${ctx }/public/bootstrap3/images/adsl.png" style="padding:0;margin-top: -50px;">
					</div>
				</c:when>
				<c:when test="${vdsl == 'active' }">
					<div class="col-md-6">
						<h1 class="text-left">High Speed VDSL</h1>
						<p class="text-left hidden-xs" style="font-size:16px;">New generation technology on VDSL plan. Faster, stable and reliable VDSL can go through your current copper line. The speed of VDSL will be 3 times faster than ADSL broadband. Download speed achieve to 15Mbps to 35Mbps, and upload speed achieve to around 10Mbps. </p>
					</div>
					<div class="col-md-6">
						<img class="hidden-xs" src="${ctx }/public/bootstrap3/images/vdsl.png" style="padding:0;margin-top: -40px;">
					</div>
				</c:when>
				<c:when test="${ufb == 'active' }">
					<div class="col-md-6">
						<h1 class="text-left">ULTRA-FAST FIBRE</h1>
						<p class="text-left hidden-xs" style="font-size:16px;">The new generation of broadband is coming to NZ. There are more and more areas are covered by UFB Fibre. Find a plan and check your address for UFB coverage. No speed limited and data cap on our Unlimited ADSL plans. All our plans are pay in advance. It provides you a clear and easy read bill. Simple and easy on-line application method can be filled in 5 mins. Or, give us a call, 0800 2 CYBER(29237). Our customer service can help you to apply your broadband as well.</p>
					</div>
					<div class="col-md-6">
						<img class="hidden-xs" src="${ctx }/public/bootstrap3/images/ufb.png" style="padding:0;margin-top: -50px;">
					</div>
				</c:when>
			</c:choose>
			
		</div>
	</div>
</div>

<div class="container">
	<div class="row">
		<div class="col-md-6 col-md-offset-3">
			<ul class="nav nav-pills nav-justified">
				<li class="" style="padding: 20px;">
					<a href="${ctx }/plans/broadband" style="${adsl=='active'?'border: 5px solid #0b7c51;':'' }">
						<img class="img-responsive" src="${ctx }/public/bootstrap3/images/adsl-s${adsl=='active'?'1':'' }.png" style="height: 120px;">
					</a>
				</li>
				<li class="" style="padding: 20px;">
					<a href="${ctx }/plans/ultra-fast-vdsl" style="${vdsl=='active'?'border: 5px solid #0b7c51;':'' }">
						<img class="img-responsive" src="${ctx }/public/bootstrap3/images/vdsl-s${vdsl=='active'?'1':'' }.png" style="height: 120px;">
					</a>
				</li>
				<li class="" style="padding: 20px;">
					<a href="${ctx }/plans/ultra-fast-fibre" style="${ufb=='active'?'border: 5px solid #0b7c51;':'' }">
						<img class="img-responsive" src="${ctx }/public/bootstrap3/images/ufb-s${ufb=='active'?'1':'' }.png" style="height: 120px;">
					</a>
				</li>
			</ul>
		</div>
	</div>
</div>

<%-- <li class="${adsl }"><a href="${ctx }/plans/broadband">ADSL Broadband</a></li>
<li class="${vdsl }"><a href="${ctx }/plans/ultra-fast-vdsl">High Speed VDSL</a></li>
<li class="${ufb }"><a href="${ctx }/plans/ultra-fast-fibre">Ultra Fast Fibre </a></li> --%>

<div class="container">

	<c:if test="${customerReg.broadband != null && !fn:contains(customerReg.broadband.services_available, type)}">
		<div class="alert alert-warning">
			<h2 class="text-center">Sorry, it looks like this service isn’t available at your place yet.</h2>
			<p class="text-center">Dont't worry, we have a range of great alternatives.</p>
			<p class="text-center">Upgrading to this service when it becomes available is super easy.</p>
		</div>
		<p>&nbsp;</p>
	</c:if>
	
	<div class="row">
		<div class="col-md-12">
		
			<!-- Nav tabs -->
			<ul class="nav nav-tabs nav-justified">
				<li class="active"><a href="#homeline" data-toggle="tab"><strong style="font-size:24px;"><span class="phone"></span> Homeline Included</strong></a></li>
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
									<c:choose>
										<c:when test="${type_search=='ADSL' }">(Homeline Included)</c:when>
										<c:when test="${type_search=='VDSL' }">(Homeline Included)</c:when>
										<c:when test="${type_search=='UFB' }">(VoIP Homeline)</c:when>
									</c:choose>
									
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
									(Without Homeline)
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

<jsp:include page="../footer.jsp" />
<jsp:include page="../script.jsp" />
<script type="text/javascript">
(function($){
	
	$('button[data-type]').click(function(){
		var select_plan_id = $(this).attr('data-id');
		window.location.href = '${ctx}/plans/define/${type_search}/' + select_plan_id;
	});

})(jQuery);
</script>
<jsp:include page="../footer-end.jsp" />