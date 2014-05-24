<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>  
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<jsp:include page="header.jsp" />

<style>
#navhead {
	margin-bottom:0;
}
.nav-pills>li.active>a, .nav-pills>li.active>a:hover, .nav-pills>li.active>a:focus {
	color: #fff;
	background-color: #7BC3EC;
}
.modal {
	z-index: 140;
}
.modal-backdrop{
	z-index: 130;
}
.home-title {
	font-size:36px;
}
.pb-bg {
	background-color: rgba(236, 236, 236, 0.54);
}
</style>

<div class="container">
	<div class="page-header" style="margin-top:0;">
		<h1>
			Business Broadband Plans
		</h1>
	</div>
	
	<%-- <img src="${ctx }/public/bootstrap3/images/business-plan.png"  class="img-responsive" alt="business plan"> --%>
	
	<div class="alert alert-info">
		<p>
			We offer the best value and price business broadband plans to you. 
			Our service range includes small business, retailers, service providers, hospitality, entertainment companies, restaurants, professional service etc.
			Give us your telecommunication requirements and your previous telecommunication bills.
			We can offer more values and better price telecommunication plans to you.
		</p>

	</div>
	<ul class="panel panel-success nav nav-pills nav-justified"><!-- nav-justified -->
		<li class="active">
			<a class="btn-lg">
				1. Choose Plan
				<span class="glyphicon glyphicon-hand-right pull-right"></span>
			</a>
		</li>
		<li class="">
			<a class="btn-lg">
				2. Check Your Address
				<span class="glyphicon glyphicon-hand-right pull-right" ></span>
			</a>
		</li>
		<li class="">
			<a class="btn-lg">
				3. Fill Application Form
				<span class="glyphicon glyphicon-hand-right pull-right" ></span>
			</a>
		</li>
		<li class="">
			<a class="btn-lg">
				4. Review and Order
			</a>
		</li>
	</ul>
	
	<c:set var="adslPlanMap" value="${planTypeMap['ADSL'] }"></c:set>
	<c:set var="vdslPlanMap" value="${planTypeMap['VDSL'] }"></c:set>
	<c:set var="ufbPlanMap" value="${planTypeMap['UFB'] }"></c:set>

	<!-- adsl -->
	<div class="page-header" style="margin-top:0;margin-bottom:5px;">
		<h3>
			<span class="label label-primary" id="adsl">
				ADSL + Business Phone Line&nbsp;
				(FAST&nbsp;
					<span class="glyphicon glyphicon-flash"></span>
					<span class="glyphicon glyphicon-flash"></span>
					<span class="glyphicon glyphicon-flash"></span>)
			</span>
		</h3>
	</div>
	<div class="row">
		<c:set var="plansPromotion" value="${adslPlanMap['plansPromotion'] }"></c:set>
		<c:set var="plans" value="${adslPlanMap['plans'] }"></c:set>
		
		<c:forEach var="plan" items="${plansPromotion }">
			<div class="col-md-4">
				<div class="panel panel-primary">
					<div class="panel-heading">
						<h2 class="panel-title text-center">
							<span style="font-size:30px;font-weight:bold;float:left;margin-left:70px;margin-right:-50px;margin-top:25px;">$</span>	
							<c:if test="${plan.original_price > 0 }">
								<i style="font-size:24px;text-decoration:line-through;">
									<fmt:formatNumber value="${plan.original_price} " type="number" pattern="##0" />
								</i>
							</c:if>
							<span style="font-size:60px;font-weight:bold;"> 
								<fmt:formatNumber value="${plan.plan_price} " type="number" pattern="##0" />
							</span>
							/ mth
						</h2>
					</div>
				 	<div class="panel-body pb-bg">
				 		<p class="text-center" style="font-weight:bold;font-size: 50px;">
							<c:choose>
								<c:when test="${plan.data_flow < 0 }">Unlimited</c:when>
								<c:otherwise>${plan.data_flow } GB</c:otherwise>
							</c:choose>
						</p>
				 		<!-- desc -->${plan.plan_desc }<!-- // end desc -->
				 		<hr style="margin:0;"/>
					   	<p class="text-center">
							<a class="btn btn-success btn-lg btn-block" id="adsl-purchase" data-id="${plan.id}" data-type="adsl" data-name="purchase">Order</a> 
						</p>
				  	</div>
				</div>
			</div>
		</c:forEach>
		<c:forEach var="plan" items="${plans }">
			<div class="col-md-4">
				<div class="panel panel-primary">
					<div class="panel-heading">
						<h2 class="panel-title text-center">
							<span style="font-size:30px;font-weight:bold;float:left;margin-left:70px;margin-right:-50px;margin-top:25px;">$</span>
							<span style="font-size:60px;font-weight:bold;"> 
								<fmt:formatNumber value="${plan.plan_price} " type="number" pattern="##0" />
							</span>
							/ mth
						</h2>
					</div>
				 	<div class="panel-body pb-bg">
				 		<p class="text-center" style="font-weight:bold;font-size: 50px;">
							<c:choose>
								<c:when test="${plan.data_flow < 0 }">Unlimited</c:when>
								<c:otherwise>${plan.data_flow } GB</c:otherwise>
							</c:choose>
						</p>
				 		<!-- desc -->${plan.plan_desc }<!-- // end desc -->
				 		<hr style="margin:0;"/>
					   	<p class="text-center">
							<a class="btn btn-success btn-lg btn-block" id="adsl-purchase" data-id="${plan.id}" data-type="adsl" data-name="purchase">Order</a> 
						</p>
				  	</div>
				</div>
			</div>
		</c:forEach>
		
	</div>


	<!-- vdsl -->
	<div class="page-header" style="margin-top:0;margin-bottom:5px;">
		<h3>
			<span class="label label-info" id="vdsl">
				VDSL + Business Phone Line&nbsp;
				(FASTER&nbsp;
					<span class="glyphicon glyphicon-flash"></span>
					<span class="glyphicon glyphicon-flash"></span>
					<span class="glyphicon glyphicon-flash"></span>
					<span class="glyphicon glyphicon-flash"></span>)
			</span>
		</h3>
	</div>
	<div class="row">
		<c:set var="plansPromotion" value="${vdslPlanMap['plansPromotion'] }"></c:set>
		<c:set var="plans" value="${vdslPlanMap['plans'] }"></c:set>
		
		<c:forEach var="plan" items="${plansPromotion }">
			<div class="col-md-4">
				<div class="panel panel-info">
					<div class="panel-heading">
						<h2 class="panel-title text-center">
							<span style="font-size:30px;font-weight:bold;float:left;margin-left:70px;margin-right:-50px;margin-top:25px;">$</span>	
							<c:if test="${plan.original_price > 0 }">
								<i style="font-size:24px;text-decoration:line-through;">
									<fmt:formatNumber value="${plan.original_price} " type="number" pattern="##0" />
								</i>
							</c:if>
							<span style="font-size:60px;font-weight:bold;"> 
								<fmt:formatNumber value="${plan.plan_price} " type="number" pattern="##0" />
							</span>
							/ mth
						</h2>
					</div>
				 	<div class="panel-body pb-bg">
				 		<p class="text-center" style="font-weight:bold;font-size: 50px;">
							<c:choose>
								<c:when test="${plan.data_flow < 0 }">Unlimited</c:when>
								<c:otherwise>${plan.data_flow } GB</c:otherwise>
							</c:choose>
						</p>
				 		<!-- desc -->${plan.plan_desc }<!-- // end desc -->
				 		<hr style="margin:0;"/>
					   	<p class="text-center">
							<a class="btn btn-success btn-lg btn-block" id="vdsl-purchase" data-id="${plan.id}" data-type="vdsl" data-name="purchase">Order</a> 
						</p>
				  	</div>
				</div>
			</div>
		</c:forEach>
		<c:forEach var="plan" items="${plans }">
			<div class="col-md-4">
				<div class="panel panel-info">
					<div class="panel-heading">
						<h2 class="panel-title text-center">
							<span style="font-size:30px;font-weight:bold;float:left;margin-left:70px;margin-right:-50px;margin-top:25px;">$</span>
							<span style="font-size:60px;font-weight:bold;"> 
								<fmt:formatNumber value="${plan.plan_price} " type="number" pattern="##0" />
							</span>
							/ mth
						</h2>
					</div>
				 	<div class="panel-body pb-bg">
				 		<p class="text-center" style="font-weight:bold;font-size: 50px;">
							<c:choose>
								<c:when test="${plan.data_flow < 0 }">Unlimited</c:when>
								<c:otherwise>${plan.data_flow } GB</c:otherwise>
							</c:choose>
						</p>
				 		<!-- desc -->${plan.plan_desc }<!-- // end desc -->
				 		<hr style="margin:0;"/>
					   	<p class="text-center">
							<a class="btn btn-success btn-lg btn-block" id="vdsl-purchase" data-id="${plan.id}" data-type="vdsl" data-name="purchase">Order</a> 
						</p>
				  	</div>
				</div>
			</div>
		</c:forEach>
		
	</div>


	<!-- ufb -->
	<div class="page-header" style="margin-top:0;margin-bottom:5px;">
		<h3>
			<span class="label label-danger" id="ufb">
				UFB + Business Phone Line&nbsp;
				(FASTEST&nbsp;
					<span class="glyphicon glyphicon-flash"></span>
					<span class="glyphicon glyphicon-flash"></span>
					<span class="glyphicon glyphicon-flash"></span>
					<span class="glyphicon glyphicon-flash"></span>
					<span class="glyphicon glyphicon-flash"></span>)
			</span>
		</h3>
	</div>
	<div class="row">
		<c:set var="plansPromotion" value="${ufbPlanMap['plansPromotion'] }"></c:set>
		<c:set var="plans" value="${ufbPlanMap['plans'] }"></c:set>
		
		<c:forEach var="plan" items="${plansPromotion }">
			<div class="col-md-4">
				<div class="panel panel-danger">
					<div class="panel-heading">
						<h2 class="panel-title text-center">
							<span style="font-size:30px;font-weight:bold;float:left;margin-left:70px;margin-right:-50px;margin-top:25px;">$</span>	
							<c:if test="${plan.original_price > 0 }">
								<i style="font-size:24px;text-decoration:line-through;">
									<fmt:formatNumber value="${plan.original_price} " type="number" pattern="##0" />
								</i>
							</c:if>
							<span style="font-size:60px;font-weight:bold;"> 
								<fmt:formatNumber value="${plan.plan_price} " type="number" pattern="##0" />
							</span>
							/ mth
						</h2>
					</div>
				 	<div class="panel-body pb-bg">
				 		<p class="text-center" style="font-weight:bold;font-size: 50px;">
							<c:choose>
								<c:when test="${plan.data_flow < 0 }">Unlimited</c:when>
								<c:otherwise>${plan.data_flow } GB</c:otherwise>
							</c:choose>
						</p>
				 		<!-- desc -->${plan.plan_desc }<!-- // end desc -->
				 		<hr style="margin:0;"/>
					   	<p class="text-center">
							<a class="btn btn-success btn-lg btn-block" id="ufb-purchase" data-id="${plan.id}" data-type="ufb" data-name="purchase">Order</a> 
						</p>
				  	</div>
				</div>
			</div>
		</c:forEach>
		<c:forEach var="plan" items="${plans }">
			<div class="col-md-4">
				<div class="panel panel-danger">
					<div class="panel-heading">
						<h2 class="panel-title text-center">
							<span style="font-size:30px;font-weight:bold;float:left;margin-left:70px;margin-right:-50px;margin-top:25px;">$</span>
							<span style="font-size:60px;font-weight:bold;"> 
								<fmt:formatNumber value="${plan.plan_price} " type="number" pattern="##0" />
							</span>
							/ mth
						</h2>
					</div>
				 	<div class="panel-body pb-bg">
				 		<p class="text-center" style="font-weight:bold;font-size: 50px;">
							<c:choose>
								<c:when test="${plan.data_flow < 0 }">Unlimited</c:when>
								<c:otherwise>${plan.data_flow } GB</c:otherwise>
							</c:choose>
						</p>
				 		<!-- desc -->${plan.plan_desc }<!-- // end desc -->
				 		<hr style="margin:0;"/>
					   	<p class="text-center">
							<a class="btn btn-success btn-lg btn-block" id="ufb-purchase" data-id="${plan.id}" data-type="ufb" data-name="purchase">Order</a> 
						</p>
				  	</div>
				</div>
			</div>
		</c:forEach>
		
	</div>
</div>


<jsp:include page="footer.jsp" />
<jsp:include page="script.jsp" />
<script type="text/javascript">
(function($){
	
	$('a[data-name="purchase"]').click(function(){
		var select_plan_id = $(this).attr('data-id');
		var select_plan_type = $(this).attr('data-type');
		window.location.href = '${ctx}/plans/term-plan/busniess/' + select_plan_type + '/address-check/' + select_plan_id;
	});
})(jQuery);
</script>
<jsp:include page="footer-end.jsp" />