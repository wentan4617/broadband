<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>  
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<jsp:include page="header.jsp" />

<style>
.nav-pills>li.active>a, .nav-pills>li.active>a:hover, .nav-pills>li.active>a:focus {
	color: #fff;
	background-color: #7BC3EC;
}
.panel-primary>.panel-footer {
	color: #fff;
	background-color: #428bca;
	border-color: #428bca;
}
.panel-info>.panel-footer {
	color: #31708f;
	background-color: #d9edf7;
	border-color: #bce8f1;
}
.panel-danger>.panel-footer {
	color: #a94442;
	background-color: #f2dede;
	border-color: #ebccd1;
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
.tab-font {
	font-weight: bold;
	font-size: xx-large;
}
</style>



<div class="container">
	<div class="page-header" style="margin-top:0;">
		<h1>
			Personal Broadband Promotion Plans 
		</h1>
	</div>
	
	<%-- <img src="${ctx }/public/bootstrap3/images/persoanl-12months-unlimited-plan.png"  class="img-responsive" alt="persoanl 12months unlimited plan"> --%>

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
	
	<c:set var="adslPlans" value="${planTypeMap['ADSL']['plansPromotion'] }"></c:set>
	<c:set var="vdslPlans" value="${planTypeMap['VDSL']['plansPromotion'] }"></c:set>
	<c:set var="ufbPlans" value="${planTypeMap['UFB']['plansPromotion'] }"></c:set>
	
	<div class="panel panel-success">
		
		<div class="panel-body">
			<div class="btn-group btn-group-justified">
				<div class="btn-group">
					<button type="button" class="btn btn-danger" data-name="promotion_service" data-type="adsl"><span class="tab-font"><span class="glyphicon glyphicon-flash"></span>ADSL</span></button>
				</div>
				<div class="btn-group">
					<button type="button" class="btn btn-danger" data-name="promotion_service" data-type="vdsl"><span class="tab-font"><span class="glyphicon glyphicon-flash"></span><span class="glyphicon glyphicon-flash"></span>VDSL</span></button>
				</div>
				<div class="btn-group">
					<button type="button" class="btn btn-danger" data-name="promotion_service" data-type="ufb"><span class="tab-font"><span class="glyphicon glyphicon-flash"></span><span class="glyphicon glyphicon-flash"></span><span class="glyphicon glyphicon-flash"></span>UFB</span></button>
				</div>
			</div>
		</div>
		<div class="panel-body">
		
			<div class="adslContainer" style="display:none;">
				<c:choose>
					<c:when test="${fn:length(adslPlans) > 0 }">
					
					<c:forEach var="plan" items="${adslPlans }">
						<c:if test="${plan.original_price > 0 }">
							<div class="row">
								<div class="col-md-3">
									<div class="thumbnail alert alert-warning" style="height:330px;">
										<div class="caption">
											<h4 class="well">Original Price</h4>
											<p class="text-center text-warning" style="position:relative;">
												<span style="font-size:40px;font-weight:bold;float:left;margin-left:25px;margin-top:45px;">$</span>
												<span style="font-size:90px;font-weight:bold;text-decoration:line-through;"> 
													<fmt:formatNumber value="${plan.original_price }" type="number" pattern="##0" />
												</span>
											</p>
										</div>
									</div>
								</div>
								<div class="col-md-3">
									<div class="thumbnail alert alert-success" style="height:330px;">
										<div class="caption">
											<h4 class="well">Promotion Price</h4>
											<p class="text-center text-success" style="position:relative;">
												<span style="font-size:40px;font-weight:bold;float:left;margin-left:25px;margin-top:45px;">$</span>
												<span style="font-size:90px;font-weight:bold;"> 
													<fmt:formatNumber value="${plan.plan_price }" type="number" pattern="##0" />
												</span>
											</p>
											<hr style="margin-top:0;">
											<p>
												<a class="btn btn-success btn-lg btn-block" data-id="${plan.id}" data-type="${plan.plan_type}" data-name="order">Order</a> 
											</p>
										</div>
									</div>
								</div>
								<div class="col-md-6">
									<div class="thumbnail alert alert-info" style="height:330px;">
										<div class="caption">
											<h4 class="well text-success">${plan.plan_name }</h4>
											<!-- desc -->${fn:replace(plan.plan_desc, '70px', '5px') }<!-- // end desc -->
										</div>
									</div>
								</div>
							</div>
						</c:if>
					</c:forEach>
					</c:when>
					<c:otherwise>
						<div class="alert alert-warning">
							Sorry, There are't no promotion plans on this broadband service!
						</div>
					</c:otherwise>
				</c:choose>
				
			</div>
			
			<div class="vdslContainer" style="display:none;">
				<c:choose>
					<c:when test="${fn:length(vdslPlans) > 0 }">
					
					<c:forEach var="plan" items="${vdslPlans }">
						<c:if test="${plan.original_price > 0 }">
							<div class="row">
								<div class="col-md-3">
									<div class="thumbnail alert alert-warning" style="height:330px;">
										<div class="caption">
											<h4 class="well">Original Price</h4>
											<p class="text-center text-warning" style="position:relative;">
												<span style="font-size:40px;font-weight:bold;float:left;margin-left:25px;margin-top:45px;">$</span>
												<span style="font-size:90px;font-weight:bold;text-decoration:line-through;"> 
													<fmt:formatNumber value="${plan.original_price }" type="number" pattern="##0" />
												</span>
											</p>
										</div>
									</div>
								</div>
								<div class="col-md-3">
									<div class="thumbnail alert alert-success" style="height:330px;">
										<div class="caption">
											<h4 class="well">Promotion Price</h4>
											<p class="text-center text-success" style="position:relative;">
												<span style="font-size:40px;font-weight:bold;float:left;margin-left:25px;margin-top:45px;">$</span>
												<span style="font-size:90px;font-weight:bold;"> 
													<fmt:formatNumber value="${plan.plan_price }" type="number" pattern="##0" />
												</span>
											</p>
											<hr style="margin-top:0;">
											<p>
												<a class="btn btn-success btn-lg btn-block" data-id="${plan.id}" data-type="${plan.plan_type}" data-name="order">Order</a> 
											</p>
										</div>
									</div>
								</div>
								<div class="col-md-6">
									<div class="thumbnail alert alert-info" style="height:330px;">
										<div class="caption">
											<h4 class="well text-success">${plan.plan_name }</h4>
											<!-- desc -->${fn:replace(plan.plan_desc, '70px', '5px') }<!-- // end desc -->
										</div>
									</div>
								</div>
							</div>
						</c:if>
					</c:forEach>
					</c:when>
					<c:otherwise>
						<div class="alert alert-warning">
							Sorry, There are't no promotion plans on this broadband service!
						</div>
					</c:otherwise>
				</c:choose>
			</div>
			
			<div class="ufbContainer" style="display:none;">
			
				<c:choose>
					<c:when test="${fn:length(ufbPlans) > 0 }">
					
					<c:forEach var="plan" items="${ufbPlans }">
						<c:if test="${plan.original_price > 0 }">
							<div class="row">
								<div class="col-md-3">
									<div class="thumbnail alert alert-warning" style="height:330px;">
										<div class="caption">
											<h4 class="well">Original Price</h4>
											<p class="text-center text-warning" style="position:relative;">
												<span style="font-size:40px;font-weight:bold;float:left;margin-left:25px;margin-top:45px;">$</span>
												<span style="font-size:90px;font-weight:bold;text-decoration:line-through;"> 
													<fmt:formatNumber value="${plan.original_price }" type="number" pattern="##0" />
												</span>
											</p>
										</div>
									</div>
								</div>
								<div class="col-md-3">
									<div class="thumbnail alert alert-success" style="height:330px;">
										<div class="caption">
											<h4 class="well">Promotion Price</h4>
											<p class="text-center text-success" style="position:relative;">
												<span style="font-size:40px;font-weight:bold;float:left;margin-left:25px;margin-top:45px;">$</span>
												<span style="font-size:90px;font-weight:bold;"> 
													<fmt:formatNumber value="${plan.plan_price }" type="number" pattern="##0" />
												</span>
											</p>
											<hr style="margin-top:0;">
											<p>
												<a class="btn btn-success btn-lg btn-block" data-id="${plan.id}" data-type="${plan.plan_type}" data-name="order">Order</a> 
											</p>
										</div>
									</div>
								</div>
								<div class="col-md-6">
									<div class="thumbnail alert alert-info" style="height:330px;">
										<div class="caption">
											<h4 class="well text-success">${plan.plan_name }</h4>
											<!-- desc -->${fn:replace(plan.plan_desc, '70px', '5px') }<!-- // end desc -->
										</div>
									</div>
								</div>
							</div>
						</c:if>
					</c:forEach>
					</c:when>
					<c:otherwise>
						<div class="alert alert-warning">
							Sorry, There are't no promotion plans on this broadband service!
						</div>
					</c:otherwise>
				</c:choose>
				
			</div>
			
			
		</div>
			
	</div>
	
	
</div>




<jsp:include page="footer.jsp" />
<jsp:include page="script.jsp" />
<script type="text/javascript">
(function($){

	
	$('a[data-name="order"]').click(function(){
		
		var select_plan_id = $(this).attr('data-id');
		var select_plan_type = $(this).attr('data-type');
		window.location.href = '${ctx}/plans/term-plan/personal/' + select_plan_type + '/address-check/' + select_plan_id;
	});
	
	$('button[data-name="promotion_service"]').click(function(){
		var $btn = $(this);
		var type = $btn.attr('data-type');
		$('button[data-name="promotion_service"]').removeClass('active');
		$btn.addClass('active');
		$('div[class$="Container"]').hide();
		$('div[class="' + type + 'Container"]').show();
	});
	
	$('button[data-name="promotion_service"][data-type="${selectdType}"]').trigger('click');
	
})(jQuery);
</script>

<jsp:include page="footer-end.jsp" />