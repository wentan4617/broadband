<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<jsp:include page="header.jsp" />

<%-- <jsp:include page="carousel.jsp" /> --%>

<div style="background:#eee">
<div class="container marketing" >
	<div class="row">
		<div class="col-lg-8">
			<jsp:include page="carousel.jsp" />
		</div>
		<div class="col-lg-4">
			<div class="panel panel-default">
		  		<div class="panel-body">
			    	<a href="#" class="btn btn-primary btn-lg btn-block">View Plan Top-Up</a>
			    	<hr/>
			    	<a href="${ctx }/plans/adsl" class="btn btn-primary btn-lg btn-block">View Plan Prepay</a>
			  	</div>
			</div>
		</div>
		
	</div>
	<div class="row">
		<div class="col-lg-3">
			<img class="img-thumbnail" src="public/bootstrap3/images/wifi-solution.png" alt="wifi-solution">
			<ul class="list-unstyled text-center list-menu">
				<li>
					<h4><a href="javascript:void(0)">Bar/cafe/Restaurant</a></h4>
				</li>
				<li>
					<h4><a href="javascript:void(0)">Entertainment place</a></h4>
				</li>
				<li>
					<h4><a href="javascript:void(0)">Hospitality</a></h4>
				</li>
				<li>
					<h4><a href="javascript:void(0)">Public place</a></h4>
				</li>
			</ul>
		</div>

		<div class="col-lg-3">
			<img class="img-thumbnail" src="public/bootstrap3/images/business-plan.png"  alt="business-plan">
			<ul class="list-unstyled text-center list-menu">
				<li>
					<h4><a href="javascript:void(0)">Cost Saver</a></h4>
				</li>
				<li>
					<h4><a href="javascript:void(0)">Office Advisor</a></h4>
				</li>
				<li>
					<h4><a href="javascript:void(0)">Best-Spot WiFi Zone</a></h4>
				</li>
				<li>
					<h4><a href="javascript:void(0)">Grand Slam</a></h4>
				</li>
				<li>
					<h4><a href="javascript:void(0)">OEM Plans</a></h4>
				</li>
			</ul>
		</div>

		<div class="col-lg-3">
			<img class="img-thumbnail" src="public/bootstrap3/images/personal-plan.png"  alt="personal-plan">
			<ul class="list-unstyled text-center list-menu">
				<li>
					<h4><a href="javascript:void(0)">Chat2U</a></h4>
				</li>
				<li>
					<h4><a href="javascript:void(0)">ADSL Plans</a></h4>
				</li>
				<li>
					<h4><a href="javascript:void(0)">VDSL Plans</a></h4>
				</li>
				<li>
					<h4><a href="javascript:void(0)">Fiber Plans</a></h4>
				</li>
			</ul>
		</div>

		<div class="col-lg-3">
			<img class="img-thumbnail" src="public/bootstrap3/images/it-services.png" alt="it-services">
			<ul class="list-unstyled text-center list-menu">
				<li>
					<h4><a href="javascript:void(0)">Hosting</a></h4>
				</li>
				<li>
					<h4><a href="javascript:void(0)">B2C web design</a></h4>
				</li>
				<li>
					<h4><a href="javascript:void(0)">Web Advertising</a></h4>
				</li>
				<li>
					<h4><a href="javascript:void(0)">Inventory Mgr</a></h4>
				</li>
			</ul>
		</div>

	</div>
</div>
</div>



<jsp:include page="footer.jsp" />
<jsp:include page="script.jsp" />
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/holder.js"></script>
<jsp:include page="footer-end.jsp" />