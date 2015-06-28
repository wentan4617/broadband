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
		
		<div class="col-md-8 col-md-offset-2">
		
			<section id="1">
				<div class="page-header">
					<h1>
						CyberPark Professional Hospitality IT Solution
					</h1>
				</div>
				
				<div class="media">
					<a class="pull-right" href="javascript:void(0);"> 
						<img class="media-object" src="${ctx }/public/bootstrap3/images/gds-call-centre.jpg" alt="...">
					</a>
					<div class="media-body">
						<p class="lead">
							CyberPark is a leading provider of telecommunications hardware, software and Wi-Fi services based in Auckland. We utilise the equity in a customer’s current telecommunications call spend to off set the cost of new office equipment.  
						</p>
					</div>
				</div>
			</section>
			
			<section id="2">
				<div class="row">
					<div class="col-md-12">
						<img class="img-responsive" alt="Responsive image" src="${ctx }/public/bootstrap3/images/gds.jpg">
					</div>
				</div>
				<p class="lead">
					Cyberpark is a reseller of telecommunications airtime, buying wholesale and passing on the savings to our customers in the form of call credits.
				</p>
				<div class="media">
					<div class="media-left">
						<a href="#"> <img class="media-object"
							src="${ctx }/public/bootstrap3/images/gds600.jpg" alt="...">
						</a>
					</div>
					<div class="media-body">
						<p class="lead">Cyberpark manages the billing of our customers fixed line call
						usage, services and equipment with a comprehensive user friendly
						phone bill which includes call credits with agreed and applicable
						call usage* *Applicable call usage includes landline calls only to
						– local, mobile, national and international calls.</p>
					</div>
				</div>

			</section>
			
			<section id="3">
				<div class="page-header">
					<h1>Build-in Hospitality Feature</h1>
				</div>
				<div class="row">
					<div class="col-md-6">
						<div class="thumbnail">
							<img src="${ctx }/public/bootstrap3/images/gds-img-1.jpg" alt="...">
							<div class="caption">
								<h3>Scalability</h3>
								<p>The system simply grow with your business. Expend your business can never be easier.</p>
							</div>
						</div>
					</div>
					<div class="col-md-6">
						<div class="thumbnail">
							<img src="${ctx }/public/bootstrap3/images/gds-img-2.jpg" alt="...">
							<div class="caption">
								<h3>Flexibility</h3>
								<p>Customize your system with optional cards. Build your very own system based on your needs.</p>
							
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-6">
						<div class="thumbnail">
							<img src="${ctx }/public/bootstrap3/images/gds-img-3.jpg" alt="...">
							<div class="caption">
								<h3>Front Desk Operation</h3>
								<p>Our build-in digital attendant console make your job nothing but pressing a button.</p>
							</div>
						</div>
					</div>
					<div class="col-md-6">
						<div class="thumbnail">
							<img src="${ctx }/public/bootstrap3/images/gds-img-4.jpg" alt="...">
							<div class="caption">
								<h3>Guest Service</h3>
								<p>Your customer can get the service delivered by a push of a button.</p>
							
							</div>
						</div>
					</div>
				</div>
			</section>
			
			<section class="4">
				<div class="well">
					<h4>Key Benefits:</h4>
					<ul>
						<li>Obtain 'Leading Edge' business equipment using call credits on your phone bill to minimise costs to your business.</li>
						<li>No Capital Outlay necessary as we structure payment terms to suit the customer over 24-60 month periods.</li>
						<li>​No cash deposit required. Why buy when you can rent a business asset that is fast depreciating?</li>
						<li>Renting equipment means continued cash flow to the business.</li>
						<li>﻿Full Installation, Programming and Training.</li>
						<li>Maintenance, support and Technical Help Desk.</li>
						<li>Option to return, buy or up-grade at the end of the term.</li>
					</ul>
				</div>
			</section>
			
		</div>
		
		
		<!-- sidebar -->
		<!-- <div class="col-md-3 hidden-xs hidden-sm">
			 <div class="navbar-example panel panel-default" style="margin-top:40px;" data-spy="affix" data-offset-top="150">
				<ul class="nav nav-pills nav-stacked">
					<li class="active">
						<a href="#e-commerce">E-Commerce</a>
					</li>
					<li>
						<a href="#solution">Powerful Solution</a>
					</li>
					<li>
						<a href="#dip">Deeply Integrated Proposal</a>
					</li>
					<li>
						<a href="#what">What is Next</a>
					</li>
					<li>
						<a href="#more">More</a>
					</li>
				</ul>
			</div>
		</div> -->
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