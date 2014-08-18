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

<div class="container">
	<div class="row">
		<div class="col-md-5 col-md-offset-4">
			<ul class="nav nav-pills" style="margin:10px auto;">
				<li><a href="${ctx }/plans/broadband">Broadband</a></li>
				<li ><a href="${ctx }/plans/ultra-fast-vdsl">Ultra Fast VDSL</a></li>
				<li class="active"><a href="${ctx }/plans/ultra-fast-fibre">Ultra Fast Fibre </a></li>
			</ul>
		</div>
	</div>
</div>

<div class="bs-docs-header">
	<div class="container">
		<div class="row">
			<div class="col-md-6">
				<h1 class="text-left">ULTRA-FAST FIBRE</h1>
				<p class="text-left">Get the next generation of broadband at your place delivered over the country’s new $1.5bn Ultra Fast Broadband (UFB) fibre network. Fibre broadband is the fastest and best way to connect to the web. Basically, if you can get fibre at your place, we think you should. It's awesome! We offer two speeds and a variety of data caps, so you can find something that best suits you. Installation is currently free.</p>
			</div>
			<div class="col-md-6"></div>
		</div>
		
	</div>
</div>

<div class="container">
	
	<div class="row">
		<div class="col-md-12">
		
			<!-- Nav tabs -->
			<ul class="nav nav-tabs nav-justified">
				<li class="active"><a href="#homeline" data-toggle="tab"><strong><span class="glyphicon glyphicon-earphone"></span> Homeline Included</strong></a></li>
				<li><a href="#naked" data-toggle="tab"><strong><span class="glyphicon glyphicon-earphone"></span> Naked Broadband</strong></a></li>
			</ul>

			<c:set var="planMap" value="${planTypeMap['UFB'] }"></c:set>
			<c:set var="plansClothed" value="${planMap['plansClothed'] }"></c:set>
			<c:set var="plansNaked" value="${planMap['plansNaked'] }"></c:set>

			<!-- Tab panes -->
			<div class="tab-content panel panel-success" >
				<div class="panel-body tab-pane fade in active" id="homeline" >
				
					<div class="row">
					
					<c:choose>
					<c:when test="${fn:length(plansClothed) > 0}">
						<c:set var="count" value="${fn:length(plansClothed)}"></c:set>
						<c:forEach var="plan" items="${plansClothed }" varStatus="item">
					
						<div class="col-md-${count >= 3 ? 4 : 6 } " style="margin-top:5px;">
							<button class="btn btn-info btn-block" data-ufb data-id="${plan.id }">
								<p class="text-center" style="font-weight:bold;font-size: 50px;">
									<c:choose>
										<c:when test="${plan.data_flow < 0 }">Unlimited</c:when>
										<c:otherwise>${plan.data_flow } GB</c:otherwise>
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
							<button class="btn btn-info btn-block" data-ufb data-id="${plan.id }">
								<p class="text-center" style="font-weight:bold;font-size: 50px;">
									<c:choose>
										<c:when test="${plan.data_flow < 0 }">Unlimited</c:when>
										<c:otherwise>${plan.data_flow } GB</c:otherwise>
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
			</div>
		</div>
	</div>
	
	<p>&nbsp;</p>
</div>


<jsp:include page="../footer.jsp" />
<jsp:include page="../script.jsp" />
<script type="text/javascript">
(function($){
	
	$('button[data-ufb]').click(function(){
		var select_plan_id = $(this).attr('data-id');
		window.location.href = '${ctx}/plans/address-check/ufb/' + select_plan_id;
	});

})(jQuery);
</script>
<jsp:include page="../footer-end.jsp" />