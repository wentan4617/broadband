<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<jsp:include page="header.jsp" />

<style>
#navhead {
	margin-bottom:0;
}
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
.home-title {
	font-size:36px;
}
</style>

<div class="container">
	<div class="row" style="margin-bottom:20px;">
		<div class="col-md-9 col-xs-12 col-sm-12">
		
			<section id="overview">
				<div class="page-header">
					<div class="home-title">
						Company Overview
					</div>
				</div>
				<p class="lead">
					CyberPark as Internet Service Provider (ISP) provides professional Telecommunication services to NZ customers. 
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
					<div class="home-title">
						Telecommunication services and WiFi coverage <br/><small>start from US!</small>
					</div>
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
					<div class="home-title">
						Contact US
					</div>
				</div>
				<address>
					<p>
						<strong>CyberPark Limited.</strong> 
					</p>
					<p>
						P.O.Box 41547 St Lukes, Auckland
					</p>
					<p>
						Give us a call <a href="javascript:void(0);">${cyberpark.telephone}</a>
					</p>
					<p>
						send email to <a href="mailto:#">${cyberpark.company_email }</a>
					</p>
				</address>
				
				<!-- Contact Us Details -->
				<div class="panel panel-success">
					<div class="panel-body">
						<div id="alertContainer"></div>
						
						<div id="tempAlertSuccessContainer" style="display:none;">
							<div id="alert-success" class="alert alert-success alert-dismissable fade in" style="display:none;">
								<button type="button" class="close" data-dismiss="alert" aria-hidden="true" >&times;</button>
								<span id="text-success"></span>
							</div>
						</div>
						<div id="tempAlertErrorContainer" style="display:none;">
							<div id="alert-error" class="alert alert-danger alert-dismissable fade in" style="display:none;">
								<button type="button" class="close" data-dismiss="alert" aria-hidden="true" >&times;</button>
								<span id="text-error"></span>
							</div>
						</div>
						
						<form class="form-horizontal" id="contactUsForm">
							<div class="form-group">
								<label for="first_name" class="control-label col-md-2">First name <span class="text-danger">(*)</span></label>
								<div class="col-md-6">
									<input id="first_name" class="form-control" placeholder="First name" data-error-field/>
								</div>
							</div>
							<div class="form-group">
								<label for="last_name" class="control-label col-md-2">Last name <span class="text-danger">(*)</span></label>
								<div class="col-md-6">
									<input id="last_name" class="form-control" placeholder="Last name" data-error-field/>
								</div>
							</div>
							<div class="form-group">
								<label for="email" class="control-label col-md-2">Email <span class="text-danger">(*)</span></label>
								<div class="col-md-6">
									<input id="email" class="form-control" placeholder="e.g. cyebrpark@gmail.com" data-error-field/>
								</div>
							</div>
							<div class="form-group">
								<label for="cellphone" class="control-label col-md-2">Mobile</label>
								<div class="col-md-6">
									<input id="cellphone" class="form-control" placeholder="e.g. 0211405369" data-error-field/>
								</div>
							</div>
							<div class="form-group">
								<label for="phone" class="control-label col-md-2">Phone</label>
								<div class="col-md-6">
									<input id="phone" class="form-control" placeholder="e.g. 095701596" data-error-field/>
								</div>
							</div>
							<div class="form-group">
								<label for="content" class="control-label col-md-2">Your request <span class="text-danger">(*)</span></label>
								<div class="col-md-6">
									<textarea id="content" class="form-control" rows="6" data-error-field></textarea>
								</div>
							</div>
							<div class="form-group">
								<label for="code" class="control-label col-md-2"></label>
								<div class="col-md-6">
									<input id="code" class="form-control" placeholder="Verification Code" data-error-field/>
								</div>
							</div>
							<div class="form-group">
								<label for="code" class="control-label col-md-2"></label>
								<div class="col-md-3">
									<img id="codeImage" style="cursor:pointer" alt="Verification Code" src="kaptcha.jpg" />
								</div>
								<div class="col-md-5">
									<a href="javascript:void(0);" id="codeLink">Not clear? Change other code.</a>
								</div>
							</div>
							<hr/>
							<div class="form-group">
								<div class="col-md-3 col-md-offset-2">
									<button type="submit" class="btn btn-success btn-lg btn-block ladda-button" data-style="zoom-in" id="submit-btn">Submit Request</button>
								</div>
							</div>
						</form>
						
					</div>
				</div>
			</section>
			<!-- <section id="location">
				<div class="page-header">
					<div class="home-title">
						Location
					</div>
				</div>
				<div id="map_canvas" style="height:360px"></div>
			</section> -->
			
		</div>
		<div class="col-md-3 hidden-xs hidden-sm">
			 <div class="navbar-example panel panel-default" style="margin-top:40px" data-spy="affix" data-offset-top="150">
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
					<!-- <li>
						<a href="#location">Location</a>
					</li> -->
				</ul>
			</div>
		</div>
	</div>
</div>

<jsp:include page="footer.jsp" />
<jsp:include page="script.jsp" />
<script type="text/javascript">
(function($){
	
	$('body').scrollspy({ target: '.navbar-example' });
	
	$('#submit-btn').on("click", function(e){
		e.preventDefault();
		var l = Ladda.create(this); l.start();
		var data = {
			first_name: $('#first_name').val()
			, last_name: $('#last_name').val()
			, email: $('#email').val()
			, cellphone: $('#cellphone').val()
			, phone: $('#phone').val()
			, content: $('#content').val()
			, code: $('#code').val()
		};
		$.post('${ctx}/contact-us', data, function(json){
			$('#codeImage').attr('src', 'kaptcha.jpg?' + Math.floor(Math.random() * 100));
			$.jsonValidation(json, 'right');
			$('.form-control').val('');
		}, 'json').always(function() { l.stop(); });
	});
	
	$('#codeImage,#codeLink').click(function(){
		$('#codeImage').attr('src', 'kaptcha.jpg?' + Math.floor(Math.random() * 100));
	});
})(jQuery);
</script>
<!-- <script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBVW1VF22QhBHMntGWSOt1Vqi5l88cPak8&sensor=true&region=NZ"></script>
<script type="text/javascript">
(function(){
	var addresses = [ '${cyberpark.google_map_address}' ];
	var geocoder;
	var map;
	function initialize() {
		geocoder = new google.maps.Geocoder();
		var mapOptions = {
			zoom : 15,
			mapTypeId : google.maps.MapTypeId.ROADMAP
		}
		for (var i = 0; i < addresses.length; i++) { //console.log(addresses[i]);
			geocoder.geocode({ 'address' : addresses[i] },
			function(results, status) {
				if (status == google.maps.GeocoderStatus.OK) {
					map.setCenter(results[0].geometry.location);
					var marker = new google.maps.Marker({
						map : map,
						position : results[0].geometry.location
					});
				} else { alert("Geocode was not successful for the following reason: " + status); }
			});
		}
		map = new google.maps.Map(document.getElementById("map_canvas"), mapOptions);
	}
	
	initialize();
})();
</script> -->
<jsp:include page="footer-end.jsp" />