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
.panel-success>.panel-footer {
	color: #3c763d;
	background-color: #dff0d8;
	border-color: #d6e9c6;
}
</style>

<div class="container">
	<div class="page-header" style="margin-top:0;">
		<h1>
			Business Broadband Plans <small>(BBP)</small>
		</h1>
	</div>
	<div class="alert alert-info">
		<p>
			We offer the best value and pirce business broadband plans to you. 
			Our service range includes small business, retailers, service providers, hospitality, entertainment companies, restaurants, professional service etc.
			Give us your telecommunication requirements and your previous telecommunication bills.
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
				3. Review and Ordering 
			</a>
		</li>
	</ul>
	
	<div class="page-header" style="margin-top:0;margin-bottom:5px;">
		<h3>
			<span class="label label-primary">ADSL + Business Landline</span>
		</h3>
	</div>
	<div class="row">
		<c:forEach var="plan" items="${planMaps['ADSL'] }" varStatus="item">
			<div class="col-lg-4">
				<div class="panel panel-success">
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
						<ul class="list-unstyled" style="margin-left:70px;">
							<li>
								<p class="text-info">
									<span class="glyphicon glyphicon-earphone"></span>&nbsp;${plan.pstn_count } business phone line
								</p>
							</li>
							<li>
								<p class="text-info">
									<span class="glyphicon glyphicon-phone-alt"></span>&nbsp;1 Voip phone line (optional)
								</p>
							</li>
							<li>
								<p class="text-info">
									<span class="glyphicon glyphicon-signal"></span>&nbsp;Free Router
								</p>
							</li>
							<li>
								<p class="text-info">
									<span class="glyphicon glyphicon-cloud"></span>&nbsp; Carryover data
								</p>
							</li>
							<li>
								<p class="text-info">
									<span class="glyphicon glyphicon-calendar"></span>&nbsp;${plan.term_period } months
								</p>
							</li>
					   	</ul>
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