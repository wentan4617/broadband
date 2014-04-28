<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>  
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<jsp:include page="header.jsp" />

<style>
.nav-pills>li.active>a, .nav-pills>li.active>a:hover, .nav-pills>li.active>a:focus {
	color: #fff;
	background-color: #5cb85c;
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
</style>

<div class="container">
	<div class="page-header" style="margin-top:0;">
		<h1>
			Personal Broadband Plans <small>(PBP)</small>
		</h1>
	</div>
	<div class="alert alert-info">
		<p>
			We offer the best value and price personal broadband plans to you. 
			We can offer more values and better price telecommunication plans to you.
		</p>
	</div>
	<ul class="panel panel-success nav nav-pills nav-justified"><!-- nav-justified -->
		<li class="active">
			<a class="btn-lg">
				1. Choose Plans And Pricing
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

	<!-- adsl -->
	<div class="page-header" style="margin-top:0;margin-bottom:5px;">
		<h3>
			<span class="label label-primary">ADSL + Home Phone Line</span>
		</h3>
	</div>
	<div class="row">
		<c:forEach var="plan" items="${planMaps['ADSL'] }" varStatus="item">
			<div class="col-lg-4">
				<div class="panel panel-primary">
					<div class="panel-heading">
						<h2 class="panel-title text-center">
							<strong style="font-size:30px;float:left;margin-left:70px;margin-right:-50px;margin-top:25px;">$</strong>
							<strong style="font-size:60px;"> 
								<fmt:formatNumber value="${plan.plan_price} " type="number" pattern="##00" />
							</strong>
							/ mth
						</h2>
					</div>
				 	<div class="panel-body">
				 		<!-- desc -->${plan.plan_desc }<!-- // end desc -->

					   	<p class="text-center">
							<a href="${ctx }/order/${plan.id}" class="btn btn-default btn-lg btn-block" id="adsl-purchase" data-name="purchase">Purchase</a> 
						</p>
				  	</div>
				  	<div class="panel-footer">
						<h2 class="text-center" style="margin:0;">
							<strong>${plan.data_flow } GB</strong>	
						</h2>
					</div>
				</div>
			</div>	
		</c:forEach>
	</div>


	<!-- vdsl -->
	<div class="page-header" style="margin-top:0;margin-bottom:5px;">
		<h3>
			<span class="label label-info">VDSL + Home Phone Line</span>
		</h3>
	</div>
	<div class="row">
		<c:forEach var="plan" items="${planMaps['VDSL'] }" varStatus="item">
			<div class="col-lg-4">
				<div class="panel panel-info">
					<div class="panel-heading">
						<h2 class="panel-title text-center">
							<strong style="font-size:30px;float:left;margin-left:70px;margin-right:-50px;margin-top:25px;">$</strong>
							<strong style="font-size:60px;"> 
								<fmt:formatNumber value="${plan.plan_price} " type="number" pattern="##00" />
							</strong>
							/ mth
						</h2>
					</div>
				 	<div class="panel-body">
						<!-- desc -->${plan.plan_desc }<!-- // end desc -->
					   	<p class="text-center">
							<a href="${ctx }/order/${plan.id}"  class="btn btn-default btn-lg btn-block" id="vdsl-purchase" data-name="purchase">Purchase</a> 
						</p>
				  	</div>
				  	<div class="panel-footer">
						<h2 class="text-center" style="margin:0;">
							<strong>${plan.data_flow } GB</strong>	
						</h2>
					</div>
				</div>
			</div>	
		</c:forEach>
	</div>


	<!-- ufb -->
	<div class="page-header" style="margin-top:0;margin-bottom:5px;">
		<h3>
			<span class="label label-danger">UFB + Home Phone Line</span>
		</h3>
	</div>
	<div class="row">
		<c:forEach var="plan" items="${planMaps['UFB'] }" varStatus="item">
			<div class="col-lg-4">
				<div class="panel panel-danger">
					<div class="panel-heading">
						<h2 class="panel-title text-center">
							<strong style="font-size:30px;float:left;margin-left:70px;margin-right:-50px;margin-top:25px;">$</strong>
							<strong style="font-size:60px;"> 
								<fmt:formatNumber value="${plan.plan_price} " type="number" pattern="##00" />
							</strong>
							/ mth
						</h2>
					</div>
				 	<div class="panel-body">
						<!-- desc -->${plan.plan_desc }<!-- // end desc -->
					   	<p class="text-center">
							<a href="${ctx }/order/${plan.id}"  class="btn btn-default btn-lg btn-block" id="ufb-purchase" data-name="purchase">Purchase</a> 
						</p>
				  	</div>
				  	<div class="panel-footer">
						<h2 class="text-center" style="margin:0;">
							<strong>${plan.data_flow } GB</strong>	
						</h2>
					</div>
				</div>
			</div>	
		</c:forEach>
	</div>



</div>

<jsp:include page="footer.jsp" />
<jsp:include page="script.jsp" />
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/holder.js"></script>
<jsp:include page="footer-end.jsp" />