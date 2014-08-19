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
		<h1 class="hidden-xs hidden-sm">Personal Broadband Plans</h1>
		<h2 class="hidden-lg hidden-md" style="font-size:22px;">Personal Broadband Plans</h2>
	</div>
	
	<div class="alert alert-success">
		<p style="font-weight: bold; font-size: 24px;" class="text-danger hidden-xs hidden-sm">
			Pay-in-Advance, Local Call only.
		</p>
		<p style="font-weight: bold; font-size: 24px;" class="text-danger hidden-xs hidden-sm">
			All our Broadband plans are Pay-in-Advance. For the home phone line, it will be local call only. 
		</p>
		<p style="font-weight: bold; font-size: 24px;" class="text-danger hidden-xs hidden-sm">
			All the toll call will not be allowed.
		</p>
		<p style="font-weight: bold;" class="text-danger hidden-lg hidden-md">
			Pay-in-Advance, Local Call only.
		</p>
		<p style="font-weight: bold;" class="text-danger hidden-lg hidden-md">
			All our Broadband plans are Pay-in-Advance. For the home phone line, it will be local call only. 
		</p>
		<p style="font-weight: bold;" class="text-danger hidden-lg hidden-md">
			All the toll call will not be allowed.
		</p>
		<p>
			We offer the best value and price personal broadband plans to you. 
			We can offer more values and better price telecommunication plans to you.
		</p>
	</div>
	<ul class="panel panel-success nav nav-pills nav-justified hidden-xs hidden-sm">
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
	
	<c:forEach var="type" items="ADSL,VDSL,UFB">
	
		<c:set var="planMap" value="${planTypeMap[type] }"></c:set>
		<c:set var="plansClothed" value="${planMap['plansClothed'] }"></c:set>
		<c:set var="plansNaked" value="${planMap['plansNaked'] }"></c:set>
		
		<c:if test="${fn:length(plansClothed) > 0 }">
		<div class="page-header" style="margin-top:0;margin-bottom:5px;" >
			<h3 class="hidden-xs">
				<c:choose>
					<c:when test="${type=='ADSL' }">
						<span class="label label-primary">
							ADSL + Home Phone Line&nbsp;(Local Call Only)
						</span>
					</c:when>
					<c:when test="${type=='VDSL' }">
						<span class="label label-info">
							VDSL + Home Phone Line&nbsp;(Local Call Only)
						</span>
					</c:when>
					<c:when test="${type=='UFB' }">
						<span class="label label-danger">
							UFB + Home Phone Line&nbsp;(Local Call Only)
						</span>
					</c:when>
				</c:choose>
			</h3>
			<h5 class="hidden-lg hidden-md hidden-sm">
				<c:choose>
					<c:when test="${type=='ADSL' }">
						<span class="label label-primary">
							ADSL + Home Phone Line&nbsp;(Local Call Only)
						</span>
					</c:when>
					<c:when test="${type=='VDSL' }">
						<span class="label label-info">
							VDSL + Home Phone Line&nbsp;(Local Call Only)
						</span>
					</c:when>
					<c:when test="${type=='UFB' }">
						<span class="label label-danger">
							UFB + Home Phone Line&nbsp;(Local Call Only)
						</span>
					</c:when>
				</c:choose>
			</h5>
		</div>
		</c:if>
		
		<div class="row">
		<c:forEach var="plan" items="${plansClothed }">
			<div class="col-md-4">
				<div class="panel panel-${type=='ADSL'?'primary':type=='VDSL'?'info':type=='UFB'?'danger':'default' }">
					<div class="panel-heading">
						<h2 class="panel-title text-center">
							<span style="font-size:30px;font-weight:bold;">$</span>
							<c:if test="${plan.promotion && plan.original_price > 0}">
								<i style="font-size:24px;text-decoration:line-through;">
									<fmt:formatNumber value="${plan.original_price} " type="number" pattern="##0" />
								</i>
							</c:if>
							<span style="font-size:60px;font-weight:bold;" class="hidden-xs"> 
								<fmt:formatNumber value="${plan.plan_price} " type="number" pattern="##0" />
							</span>
							<span style="font-size:35px;font-weight:bold;" class="hidden-lg hidden-md hidden-sm"> 
								<fmt:formatNumber value="${plan.plan_price} " type="number" pattern="##0" />
							</span>
							/ mth
							<a data-toggle="collapse" data-parent="#accordion" href="#collapse${plan.id }">
								<span class="glyphicon glyphicon-minus-sign pull-right" style="font-size:28px;" data-id="collapse${plan.id }"></span>
							</a>
						</h2>
					</div>
					<div id="collapse${plan.id }" class="panel-collapse collapse in">
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
			</div>
		</c:forEach>
		</div>
		
		<c:if test="${fn:length(plansNaked) > 0 }">
		<div class="page-header" style="margin-top:0;margin-bottom:5px;">
			<h3 class="hidden-xs">
				<c:choose>
					<c:when test="${type=='ADSL' }">
						<span class="label label-primary">
							Naked ADSL
						</span>
					</c:when>
					<c:when test="${type=='VDSL' }">
						<span class="label label-info">
							Naked VDSL 
						</span>
					</c:when>
					<c:when test="${type=='UFB' }">
						<span class="label label-danger">
							Naked UFB 
						</span>
					</c:when>
				</c:choose>
			</h3>
			<h5 class="hidden-lg hidden-md hidden-sm">
				<c:choose>
					<c:when test="${type=='ADSL' }">
						<span class="label label-primary">
							Naked ADSL
						</span>
					</c:when>
					<c:when test="${type=='VDSL' }">
						<span class="label label-info">
							Naked VDSL 
						</span>
					</c:when>
					<c:when test="${type=='UFB' }">
						<span class="label label-danger">
							Naked UFB 
						</span>
					</c:when>
				</c:choose>
			</h5>
		</div>
		</c:if>
		
		
		
		<div class="row">
		<c:forEach var="plan" items="${plansNaked }">
			<div class="col-md-4">
				<div class="panel panel-${type=='ADSL'?'primary':type=='VDSL'?'info':type=='UFB'?'danger':'default' }">
					<div class="panel-heading">
						<h2 class="panel-title text-center">
							<span style="font-size:30px;font-weight:bold;">$</span>
							<c:if test="${plan.promotion && plan.original_price > 0}">
								<i style="font-size:24px;text-decoration:line-through;">
									<fmt:formatNumber value="${plan.original_price} " type="number" pattern="##0" />
								</i>
							</c:if>
							<span style="font-size:60px;font-weight:bold;" class="hidden-xs"> 
								<fmt:formatNumber value="${plan.plan_price} " type="number" pattern="##0" />
							</span>
							<span style="font-size:35px;font-weight:bold;" class="hidden-lg hidden-md hidden-sm"> 
								<fmt:formatNumber value="${plan.plan_price} " type="number" pattern="##0" />
							</span>
							/ mth
							<a data-toggle="collapse" data-parent="#accordion" href="#collapse${plan.id }">
								<span class="glyphicon glyphicon-minus-sign pull-right" style="font-size:28px;" data-id="collapse${plan.id }"></span>
							</a>
						</h2>
					</div>
					<div id="collapse${plan.id }" class="panel-collapse collapse in">
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
			</div>
		</c:forEach>
		</div>
		
	</c:forEach>

</div>

<jsp:include page="footer.jsp" />
<jsp:include page="script.jsp" />
<script type="text/javascript">
(function($){
	$('a[data-name="purchase"]').click(function(){
		var select_plan_id = $(this).attr('data-id');
		var select_plan_type = $(this).attr('data-type');
		window.location.href = '${ctx}/plans/term-plan/personal/' + select_plan_type + '/address-check/' + select_plan_id;
	});
	
	$('div[id^="collapse"]').on('show.bs.collapse', function(){
		$('span[data-id="' + this.id + '"]').attr('class', 'glyphicon glyphicon-minus-sign pull-right');
	}).on('hide.bs.collapse', function(){
		$('span[data-id="' + this.id + '"]').attr('class', 'glyphicon glyphicon-plus-sign pull-right');
	});
	
})(jQuery);
</script>
<jsp:include page="footer-end.jsp" />