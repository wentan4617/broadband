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
		
			<section id="e-commerce">
				<div class="page-header">
					<h1>
						CyberPark E-commerce Solutions Is Coming
					</h1>
				</div>
				<p class="lead">
					 If there is one thing we can all agree on, it’s that era of E-commerce is coming and smart phones and tablets is becoming a mainstream shopping venue.  
				</p>
				<p class="lead">
					The most popular area include, home, work place, education areas, bar, restaurants, cafe shops, Motels and Hotels, public places, and some other luxury or entertainment places.  
				</p>
				
			</section>
			
			<section id="solution">
				<div class="page-header">
					<span class="home-title">
						We Provide Powerful Solution
					</span>
				</div>
				<div class="media">
					<a class="pull-left" href="javascript:void(0);"> 
						<img class="media-object" src="${ctx }/public/bootstrap3/images/powerful-solution.jpg" alt="...">
					</a>
					<div class="media-body">
						<p class="lead">
							 A complete end-to-end solution that seam- lessly integrates distribution, retail store and web store activities to the backend ERP system. 
						</p>
						<p class="lead">
							Every transaction from the internet captured and automatically convert to customer records. Do not need waste time on duplicate data entry of electronic leads.
						</p>
					</div>
				</div>
			</section>
			
			<section id="dip">
				<div class="page-header">
					<span class="home-title">
						Deeply Integrated Proposal
					</span>
				</div>
				
				<ul class="media-list">
					<li class="media">
						<a class="pull-left" href="javascript:void(0);"> 
							<img class="media-object" src="${ctx }/public/bootstrap3/images/sample-website.png" alt="..." width="500px;">
						</a>
						<div class="media-body">
							 <h2 class="media-heading">Sample Website</h2>
						</div>
					</li>
					<li class="media">
						<a class="pull-left" href="javascript:void(0);"> 
							<img class="media-object" src="${ctx }/public/bootstrap3/images/mobile-interface1.png" alt="..." width="250px;">
						</a>
						<a class="pull-left" href="javascript:void(0);"> 
							<img class="media-object" src="${ctx }/public/bootstrap3/images/mobile-interface1.png" alt="..." width="250px;">
						</a>
						<div class="media-body">
							 <h2 class="media-heading">Mobile Interface</h2>
						</div>
					</li>
					<li class="media">
						<a class="pull-left" href="javascript:void(0);"> 
							<img class="media-object" src="${ctx }/public/bootstrap3/images/start-shopping.png" alt="..." width="500px;">
						</a>
						<div class="media-body">
							 <h2 class="media-heading">Start Shopping<small> - Real time Stock Check</small></h2>
						</div>
					</li>
					<li class="media">
						<a class="pull-left" href="javascript:void(0);"> 
							<img class="media-object" src="${ctx }/public/bootstrap3/images/order-process.png" alt="..." width="500px;">
						</a>
						<div class="media-body">
							 <h2 class="media-heading">Order Process<small> – Choose Courier (Set up initially), Promotion Code Available</small></h2>
						</div>
					</li>
					<li class="media">
						<a class="pull-left" href="javascript:void(0);"> 
							<img class="media-object" src="${ctx }/public/bootstrap3/images/checkout.png" alt="..." width="500px;">
						</a>
						<div class="media-body">
							 <h2 class="media-heading">Order Process<small> – Check out</small></h2>
						</div>
					</li>
				</ul>
			</section>
			
			<section id="what">
				<div class="page-header">
					<span class="home-title">
						What is Next?
					</span>
				</div>
				<p class="lead">
					When an invoice is created, system instantly update inventory, customer history and accounts receivable, and link directly through to ensure the sales history is updated. Everything is automatically kept up-to-minute.
				</p >
				<p class="lead">
					Email integration enable sending invoices, notifications and confirmations electronically. Informing suppliers to replenish stock as well.
				</p>
				<ul class="media-list">
					<li class="media">
						<a class="pull-left" href="javascript:void(0);"> 
							<img class="media-object" src="${ctx }/public/bootstrap3/images/transaction.png" alt="..." width="500px;">
						</a>
						<div class="media-body">
							 <h2 class="media-heading">Transaction Enquiry</h2>
						</div>
					</li>
					<li class="media">
						<a class="pull-left" href="javascript:void(0);"> 
							<img class="media-object" src="${ctx }/public/bootstrap3/images/reporting.png" alt="..." width="500px;">
						</a>
						<div class="media-body">
							 <h2 class="media-heading">Reporting Services<small> – Order Report (On line & Offline if applicable)</small></h2>
						</div>
					</li>
				</ul>
			</section>
			
			<section id="more">
				<div class="page-header">
					<span class="home-title">
						We Offer More Than Expecting
					</span>
				</div>
				<p class="lead">
					These days a growing business must have opera-tional information to gain the elusive competi-tive edge in the competitive business arena. Our Intelligence Reporting would provide:
					
				</p>
				<ul>
					<li> 
						Recognized pattern in what’s selling best
					</li>
					<li>
						Clear visibility of which products may be the biggest revenue earner
					</li>
					<li>
						Defining items that make the most profit
					</li>
					<li>
						Updated buying trends
						
					</li>
				</ul>
				<ul class="media-list">
					<li class="media">
						<a class="pull-left" href="javascript:void(0);"> 
							<img class="media-object" src="${ctx }/public/bootstrap3/images/reporting2.png" alt="..." width="450px;">
						</a>
						<div class="media-body">
							 <h2 class="media-heading">Reporting Services<small> – Product Revenue</small></h2>
						</div>
					</li>
					<li class="media">
						<a class="pull-left" href="javascript:void(0);"> 
							<img class="media-object" src="${ctx }/public/bootstrap3/images/reporting3.png" alt="..." width="450px;">
						</a>
						<div class="media-body">
							 <h2 class="media-heading">Reporting Services<small> – Stock Level</small></h2>
						</div>
					</li>
					<li class="media">
						<a class="pull-left" href="javascript:void(0);"> 
							<img class="media-object" src="${ctx }/public/bootstrap3/images/approach.png" alt="..." >
						</a>
						<div class="media-body">
							 <h2 class="media-heading">An Evolutionary Approach</h2>
						</div>
					</li>
				</ul>
			</section>
			
			<p>
				We also can provide your specific IT requirements on your E-Commerce Solution for Growing Business.
				
			</p>
			<p>
				Give us a call 0800 2 CYBER (0800 229 237), or email to us : <a href="mailto:#">sales@cyberpark.co.nz</a>
			</p>
		</div>
		
		
		<!-- sidebar -->
		<div class="col-md-3 hidden-xs hidden-sm">
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