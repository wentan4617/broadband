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
</style>

<div class="bs-docs-header cyberpark-home-bg" style="padding-bottom: 0;margin-bottom:0">
	<div class="container">
		<div class="row">
			
			<div class="col-md-6">
				<blockquote>
					<p style="font-size:24px;">Smartphone Business Communication System</p>
				  	<footer style="color:#fff">Turns smartphones into office extensions</footer>
				</blockquote>
				<h1 class="text-left" style="font-size:28px;"></h1>
				<p class="text-left hidden-xs"  style="font-size:16px;">
					<span class="glyphicon glyphicon-usd"></span> Outstanding Value - Eliminates costly infrastructure
				</p>
				<p class="text-left hidden-xs"  style="font-size:16px;">
					<span class="glyphicon glyphicon-earphone"></span> Mobility - Stay connected to your system and conduct business from anywhere
				</p>
				<p class="text-left hidden-xs"  style="font-size:16px;">
					<span class="glyphicon glyphicon-picture"></span> With user friendly web interface and can easily be DIY
				</p>
			</div>
			<div class="col-md-6">
				<img class="hidden-xs" src="${ctx }/public/bootstrap3/images/wifi.png" style="padding:0;margin-top: 0px;">
			</div>
		</div>
	</div>
</div>

<div class="container">
	<div class="row" style="margin-bottom:20px;">
		
		<div class="col-md-9 col-xs-12 col-sm-12">
		
			<!-- pxb -->	
			<section id="description">
				<div class="page-header">
					<h1>
						Description
					</h1>
				</div>
				<ol>
					<li>The IG7600 is a stand-alone Business Communications Server design for small and medium business.</li>
					<li>It connects up to 4 PSTN lines and up to 12 SIP(VoIP) Lines, serving up to 16 smartphone extensions, and or up to 49 desktop handset extensions. As an additional options, it can connect up to 8 IP cameras and 2 video door stations.</li>
					<li>It is designed to be practical and user friendly. The Gateway and associated Apps can turn user's smartphone into system extension without additional hardware. With around the world access through WifFi or 3G(4G) mobile coverage allowing it to access IG7600's phone lines and enjoy all business communication functions such as multi-line use, intercom, hold, transfer, conference, voice mail, music on hold auto attendant, plus many more.</li>
					<li>The unit can run fully in a complete wireless environment or the option of adding desktop handsets for a more complete setup.</li>
					<li>It provides a total mobility concept where user can have the complete freedom of been mobile.</li>
					<li>The system can be easily DIY in an environment where cabling is not available.</li>
					</ol>
				
			</section>	
			
			<section id="setting-up">
				<div class="page-header">
					<h1>
						Setting up the IG7600 Gateway
					</h1>
				</div>
				<ul>
					<li><strong>Simply Plug in the Telephone Lines</strong></li>
					<li><strong>Connect to Broadband Router</strong></li>
					<li><strong>Download the App</strong></li>
					<li><strong>Start making and reveiving calls on your Smartphone</strong></li>
				</ul>
			</section>	
			
			<section id="application">
				<div class="page-header">
					<h1>
						Application Scenario
					</h1>
				</div>
				<div class="row">
					<div class="col-md-12">
						<img class="img-responsive" alt="Responsive image" src="${ctx }/public/bootstrap3/images/pbx-application.png">
					</div>
				</div>
				
			</section>	
			
			<section id="ig7600">
				<div class="page-header">
					<h1>
						IG7600 Turns Smartphones Into Your Office Phones
					</h1>
				</div>
				<div class="row">
					<div class="col-md-12">
						<img class="img-responsive" alt="Responsive image" src="${ctx }/public/bootstrap3/images/pbx-incoming.png">
					</div>
				</div>
				<hr>
				<div class="row">
					<div class="col-md-12">
						<img class="img-responsive" alt="Responsive image" src="${ctx }/public/bootstrap3/images/pbx-outgoing.png">
					</div>
				</div>
			</section>	
			
			<section id="special">
				<div class="page-header">
					<h2>
						Special Value Proposition to Start-Up, Temporary, and Mobile offices
					</h2>
				</div>
				<ol>
					<li><strong class="text-primary">Minimum and affordable initial investment</strong></li>
					<li><strong class="text-primary">The unit is both small foot print and portable</strong></li>
					<li><strong class="text-primary">Stay connected to the office even on mobile</strong></li>
					<li><strong class="text-primary">Option of video monitoring</strong></li>
					<li><strong class="text-primary">The unit can also grow with your business with option to expand</strong></li>
				</ol>
				<div class="well">
					<p class="lead text-danger">
						A well all rounded Business Communication Solution design with the user in mind.
					</p>
				</div>
			</section>	
			
			<section id="smartphone">
				<div class="page-header">
					<h1>
						Smartphone Business Communication System
					</h1>
				</div>
				<div class="row">
					<div class="col-md-12">
						<img class="img-responsive" alt="Responsive image" src="${ctx }/public/bootstrap3/images/pbx-system-1.png">
					</div>
				</div>
				<div class="row">
					<div class="col-md-12">
						<img class="img-responsive" alt="Responsive image" src="${ctx }/public/bootstrap3/images/pbx-system-2.png">
					</div>
				</div>
			</section>	
				
		</div>
		<div class="col-md-3 hidden-xs hidden-sm">
			 <div class="navbar-example panel panel-default" style="margin-top:40px;" data-spy="affix" data-offset-top="300">
				<ul class="nav nav-pills nav-stacked">
					<li class="active">
						<a href="#description">Description</a>
					</li>
					<li>
						<a href="#setting-up">Setting Up</a>
					</li>
					<li>
						<a href="#application">Application</a>
					</li>
					<li>
						<a href="#ig7600">IG7600</a>
					</li>
					<li>
						<a href="#special">Special</a>
					</li>
					<li>
						<a href="#smartphone">Smartphone</a>
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