<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<jsp:include page="header.jsp" />

<style>
.topup-list li {
	padding: 10px 20px;
}
.nav-pills>li.active>a, .nav-pills>li.active>a:hover, .nav-pills>li.active>a:focus {
color: #fff;
background-color: #7BC3EC;
}
</style>

<div class="container">

 	<div class="page-header" style="margin-top:0;">
		<h2>
			Top Up Unlimited Naked ADSL Broadband Plan (Top Up UNAB Plan)
		</h2>
	</div>
	<div class="alert alert-info">
		<p>Top Up UNAB plan is the new broadband plan concept from CyberPark Limited.</p>
		<p>The process and conditions as bellows, it will be more cost efficiency and convenience to all the Top Up users. </p>
		<p>CyberPark Top Up Plan offer you unlimited data plan by weekly charges,</p>
		<p>NZD$30/week Top Up payment method include: credit cards, account to account, or call 0800 2 Cyber (29237) for help.</p>
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
				3. Review and Checkout 
			</a>
		</li>
	</ul>
	
	<div class="panel panel-success">
  		<div class="panel-heading">
			<h3 class="panel-title">Naked ADSL TopUp Plan</h3>
		</div>
		<div class="panel-body">
			<div class="row">
				<div class="col-lg-3">
					<div class="panel panel-success">
						<div class="panel-body" style="padding: 0;"> 
							<p class="bg-success text-center text-success" style="position:relative;margin-bottom:0;height: 140px;">
								<strong style="font-size:40px;float:left;margin-left: 40px;margin-top: 80px;line-height: 1;">$</strong>
								<strong style="font-size: 100px;float: left;margin-left: 10px;margin-top: 30px;line-height: 1;"> 
									<fmt:formatNumber value="20" type="number" pattern="##0" />
								</strong>
							</p>
						</div>
					</div>
				</div>
				<div class="col-lg-3">
					<div class="panel panel-success">
						<div class="panel-body" style="padding: 0;">
							<p class="bg-success text-center text-success" style="position:relative;margin-bottom:0;height: 140px;">
								<strong style="font-size: 100px;float: left;margin-left: 40px;margin-top: 30px;line-height: 1;"> 
									7
								</strong>
								<strong style="font-size: 50px;float:left;margin-left: 0px;margin-top: 70px;line-height: 1;">
									Days
								</strong>
							</p>
						</div>
					</div>
				</div>
				<div class="col-lg-3">
					<div class="panel panel-success">
						<div class="panel-body" style="padding: 0;">
							<p class="bg-success text-center text-success" style="margin-bottom:0;height: 140px;padding: 32px;">
								<strong style="font-size: 45px; line-height: 1;">Unlimited</strong>
								<strong style="font-size: 45px; line-height: 1;">Data</strong>
							</p>
						</div>
					</div>
				</div>
				<div class="col-lg-3">
					<div class="panel panel-success">
						<div class="panel-body" style="padding: 0;">
							<a href="${ctx }/order/${planTypeMap['ADSL']['plans'][0].id}/topup/20"
								class="btn btn-success btn-block" style="height: 140px;">
								<p
									style="font-size: 50px; font-weight: bold; margin: 10px; line-height: 1;">Join</p>
								<p
									style="font-size: 50px; font-weight: bold; margin: 0; line-height: 1;">Plan</p>
							</a>
						</div>
					</div>
				</div>
			</div>
			
			<p class="text-danger">
				Promotion: NZD$20/week CyberPark Top Up Unlimited Naked ADSL Broadband (UNAB) plan if you apply before the end of August, 2014.
			</p>
		</div>
	</div>
	
	<div>
		<ul>
			<li>No Terms, No contract </li>
			<li>Use your own Routers</li>
			<li>New Connection fee, one off charge $ ${planTypeMap['ADSL']['plans'][0].plan_new_connection_fee}</li>
		</ul>
	</div>
	
	<div class="page-header">
		<h2>
			Introduction of Top-up broadband plan
		</h2>
	</div>
	<div>
		<p class="lead">
			Top-up broadband plan is created by CyberPark Limited in 2014.
		</p>
		<p>
			We broke your traditional understanding to the broadband contract plans and monthly charges method. Convenience services can give you more choices to your broadband plans. You can easily top-up your account and change your monthly data usages. Customers can purchase the top-up voucher from every e-pay system. We also accept credit cards, pay-pal, and account-to-account payment methods. You don’t need change any router when you transit from your current internet service providers to our Top-up broadband plan. Your transition will be automatically change broadband to our service without any gaps. 
		</p>
	</div>
	
	<div class="page-header">
		<h2>
			How can I apply Top-up broadband plan?
		</h2>
	</div>
	<div>
		<ul>
			<li>Visit our website www.cyberpark.co.nz and choose “Apply Top-up broadband”</li>
			<li>Filling your personal details, like name, address, contact phone number</li>
			<li>If you are transiting from your current Internet service providers, please accurately fill the details of your current provider and account number. </li>
			<li>Submit all your details and select the first top-up value (minimum $20 for 7 days)</li>
			<li>New connection has one-off charge $145</li>
			<li>New Jack-point installation has one-off charge $199</li>
			<li>If you don’t have ADSL router, we can offer you$50 for TP link ADSL2+ router</li>
		</ul>
	</div>
	
	<div class="page-header">
		<h2>
			What you have in your Top-up broadband plan?
		</h2>
	</div>
	<div>
		<p>Top-up broadband plan is a Naked ADSL2+ broadband plan. All the data and usage period is based on your Top-up value and your selections. </p>
	</div>
	
	<div class="page-header">
		<h2>
			How does Top-up broadband plan works?
		</h2>
	</div>
	<div>
		<ul>
			<li>
				After your application, you will receive a confirmation email and provision confirmation email/message from us. Cyber Park broadband service normally can provide to you in 3 to 5 working days. New connection or New Jack-point installation application may takes longer, and our installation cooperator may contact with you for booking appointment.
			</li>
			<li>
				We will generate your account and passwords. You can log in your account to check your data volume and usage period (expire date). 
			</li>
			<li>
				Payment functions can offer you different methods to Top-up, like credit cards, account-to-account, paypal, or top-up voucher. All these payment methods are security and convenience in NZ market.  	
			</li>
			<li>
				When you overdue your usage period, you broadband service will be automatically suspend in the following 48 hours. After that, your broadband connection will be disconnected and reaction may charges you as a New Connection which costs $99.  
			</li>
		</ul>
	</div>
	
	<div class="page-header">
		<h2>
			Conditions:
		</h2>
	</div>
	<div>
		<ul>
			<li>Naked ADSL only</li>
			<li>CyberPark top up plan is based on rechargeable process. All the top up amount will be the credits in Customers’ Cyber Park account.</li>
			<li>User your own router/modem</li>
			<li>No term</li>
			<li>“week” is defined as 7 days after the customer received UNAB service</li>
			<li>Transfer the existing broadband connection to CyberPark is free </li>
			<li>Get a new broadband connection on an existing (but inactive) jackpot charge NZD$145</li>
			<li>Get a new broadband connection and an new jackpot installation and activation charge NZD$199</li>
			<li>CyberPark Top Up UNAB plan service is automatically terminated in 48 hours after expire date</li>
			<li>After CyberPark Top Up UNAB plan service was terminated, CyberPark account user can Top Up his/her CyberPark account online or contact with our customer service to reactivating the broadband services in 2-5 days. However, we cannot guarantee the previous ADSL pots available all the time. More information, please contact with our customer service , 0800 2 Cyber (29237) </li>
			<li>Re-activating CyberPark Top Up UNAB plan charges: 1st re-activating plan account for free, 2nd re-activating costs NZD $49, 3rd re-activating costs NZD $99 and 4th re-activating costs NZD $149 in 1 year period from CyberPark account by online, message, or telephone calling 0800 2 Cyber.  </li>
			<li>Reasonable unlimited data use</li>
			<li>Respect to the copy right regulation in NZ</li>
			<li>Free of charge upgrade to any CyberPark term plans</li>
			<li>CyberPark has the rights to explain the charging system to Top Up UNAB Plans, more information you can email to <a href="">info@cyberpark.co.nz</a> or call 0800 2 Cyber (29237) </li>
		</ul>
	</div>
	<p>&nbsp;</p>
	<!-- plan content -->

	<!-- plan content -->
</div>


<jsp:include page="footer.jsp" />
<jsp:include page="script.jsp" />
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/holder.js"></script>
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/bootstrap-select.min.js"></script>
<script type="text/javascript">
(function($) {
	
	

})(jQuery);
</script>
<jsp:include page="footer-end.jsp" />