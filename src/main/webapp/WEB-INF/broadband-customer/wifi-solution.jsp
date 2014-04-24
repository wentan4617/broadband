<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<jsp:include page="header.jsp" />

<style>

.affix {
	width: 263px;
	top: 0;
}
.nav-pills>li>a {
	border-radius: 0px;
}
.nav-pills>li:first-child>a {
	border-radius: 4px 4px 0 0;
}
.nav-pills>li:last-child>a {
	border-radius: 0 0 4px 4px;
}
</style>

<div class="container">
	<div class="row" style="margin-bottom:20px;">
		
		<div class="col-md-9">
			<section id="overview">
				<div class="page-header">
					<h1>
						Company Overview
					</h1>
				</div>
				<p class="lead">
					CyberPark as Internet Service Provider（ISP） provides professional Telecommunication services to NZ customers. 
				</p>
				<p>
					We are going to give a new method of broadband services to NZ market. Our main products include: Top-up plans, No Term (Combo) plans, 12 months Term plans, Commercial plans, and Professional Wifi Coverage broadband plans and solutions based on specific environments. 
					 
				</p>
				<p>
					Broadband application, installations, and payment will be easy, fast, and convenience to all NZ consumers. Residential customers’ broadband application will be self-services and apply on line. Installation will be directly solved by Chorus and almost there is no gap connection with our broadband services.
				</p>
				<p>
					Top-up or no term plans’ users can recharge their accounts by Top-up Vouchers, Credit Cards, Direct debts, account to account, or Paypal systems. Other broadband plans’ selectors also can use the electric business payment methods. Professional Wifi Coverage broadband plans provide more convenience to your Wifi users, at the same time, our IT team can help you to produce your advertisement to be your free Wifi’s registration pages. Moreover, these IT services can also assist to build up and keep your royal customers.
				</p>
			</section>
			<section id="service">
				<div class="page-header">
					<h1>
						Telecommunication services and WiFi coverage <br/><small>start from US!</small>
					</h1>
				</div>
				<p>
					The best Telecommunication results come from our solutions. We can provide more than what you want. 
				</p>
				<p>
					Our Telecommunication services give you more methods to improve your productivities, cost saving and easy using. For our professional WiFi coverage services, we focus on café shops, bars, restaurants, food court, supermarkets, normal retailers, and office users. Powerful devises can cover almost 95% of your work place.  
				</p>
				<p>
					Telecommunication package plans achieve all your specific Telecommunication requirements base on a lowest cost. Qualified and professional services let you feel we are standing beside with you and your business.
				</p>
			</section>
			<section id="contact">
				<div class="page-header">
					<h1>
						Contact US
					</h1>
				</div>
				<address>
					<p>
						<strong>CyberPark Limited.</strong> 
					</p>
					<p>
						${cyberpark.address}
					</p>
					<p>
						Give us a call <a href="javascript:void(0);">${cyberpark.telephone}</a>
					</p>
					<p>
						send email to <a href="mailto:#">${cyberpark.company_email }</a>
					</p>
				</address>

			</section>
			<section id="location">
				<div class="page-header">
					<h1>
						Location
					</h1>
				</div>
				<div id="map_canvas" style="height:360px"></div>
			</section>
			
		</div>
		<div class="col-md-3">
			 <div class="navbar-example panel panel-default" style="margin-top:40px;" data-spy="affix" data-offset-top="150">
				<ul class="nav nav-pills nav-stacked">
					<li class="active">
						<a href="#overview">CyberPark Overview</a>
					</li>
					<li>
						<a href="#service">WIFI Services</a>
					</li>
					<li>
						<a href="#contact">Contact US</a>
					</li>
					<li>
						<a href="#location">Location</a>
					</li>
				</ul>
			</div>
		</div>
	</div>
</div>

<jsp:include page="footer.jsp" />
<jsp:include page="script.jsp" />
<script type="text/javascript">
(function($){
	$('body').scrollspy({ target: '.navbar-example' })
})(jQuery);
</script>
<jsp:include page="footer-end.jsp" />