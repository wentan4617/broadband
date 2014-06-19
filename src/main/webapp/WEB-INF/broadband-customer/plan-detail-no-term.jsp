<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>  

<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<jsp:include page="header.jsp" />

<style>
.nav-pills>li.active>a, 
.nav-pills>li.active>a:hover, 
.nav-pills>li.active>a:focus {
	color: #fff;
	background-color: #7BC3EC;
}

.home-title {
	font-size:36px;
}
</style>


<div class="container" style="margin-top:20px;">

	<ul class="panel panel-success nav nav-pills nav-justified hidden-xs hidden-sm"><!-- nav-justified -->
		<li class="active">
			<a class="btn-lg">
				1. Choose Plan
				<span class="glyphicon glyphicon-hand-right pull-right"></span>
			</a>
		</li>
		<li class="">
			<a class="btn-lg">
				2. Fill Application Form
				<span class="glyphicon glyphicon-hand-right pull-right" ></span>
			</a>
		</li>
		<li class="">
			<a class="btn-lg">
				3. Review and Order 
			</a>
		</li>
	</ul>
	
	<c:set var="adslPlanMap" value="${planTypeMap['ADSL'] }"></c:set>
	<c:set var="plans" value="${adslPlanMap['plans'] }"></c:set>
	
	
	<div class="panel panel-success" id="adslPanel">
		<div class="panel-body">
			<img class="pull-right" src="${ctx }/public/bootstrap3/images/icon_most-popular.png" alt="ADSL provides fast bandwidth for home use">
			<h1 style="height:54px;font-weight:bold;" class="text-success hidden-xs hidden-sm">ADSL Naked BROADBAND (No Term)</h1>
			<h4 style="height:54px;font-weight:bold;" class="text-success hidden-md hidden-lg">ADSL Naked BROADBAND (No Term)</h4>
			<hr/>
			<div class="well">
				The broadband standard in NZ. Fast Internet over your copper phone line.
			</div>
			<h3 class="hidden-xs hidden-sm"><strong class="text-success">How much data do you need?</strong></h3>
			<h4 class="hidden-lg hidden-md"><strong class="text-success">How much data do you need?</strong></h4>
			<div class="row">
				<c:forEach var="plan" items="${plans}" varStatus="item">
					<div class="col-md-3">
						<div class="thumbnail">
							<div class="caption">
								<div class="text-success text-center home-title" style="font-weight:bold;">
									<c:choose>
										<c:when test="${plan.data_flow < 0 }">Unlimited</c:when>
										<c:otherwise>${plan.data_flow } GB</c:otherwise>
									</c:choose>
								</div>
								<hr style="margin-top:0;"/>
								<p class="text-center text-success" style="position:relative;margin-bottom:0;">
									<span style="font-size:40px;font-weight:bold;">$</span>
									<span style="font-size:60px;font-weight:bold;"> 
										<fmt:formatNumber value="${plan.plan_price} " type="number" pattern="##0" />
									</span>
									/ mth
								</p>
								<p style="font-size:18px;">
									<span class="text-success" style="font-weight:bold;">${plan.plan_name }</span>
								</p>
								
								<!-- desc -->${plan.plan_desc }<!-- // end desc -->
								<hr/>
								<p>
									<a class="btn btn-success btn-lg btn-block"  data-name="purchase" data-id="${plan.id }" data-type="adsl">Purchase</a> 
								</p>
							</div>
						</div>
					</div>
				</c:forEach>
			</div>
		</div>
	</div>
	
</div>

<jsp:include page="footer.jsp" />
<jsp:include page="script.jsp" />
<script type="text/javascript">
(function($){
	
	$('a[data-name="purchase"]').click(function(){
		
		var select_plan_id = $(this).attr('data-id');
		window.location.href = '${ctx}/order/' + select_plan_id;
		
	});

})(jQuery);
</script>
<jsp:include page="footer-end.jsp" />