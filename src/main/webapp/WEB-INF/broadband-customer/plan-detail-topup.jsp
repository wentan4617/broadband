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
.nav-pills>li.active>a, 
.nav-pills>li.active>a:hover, 
.nav-pills>li.active>a:focus {
	color: #fff;
	background-color: #7BC3EC;
}
.home-title-h2 {
	font-size:30px;
}

</style>
<style>
#navhead {
	margin-bottom:0;
}
</style>

<div class="bs-docs-header cyberpark-home-bg" style="padding-bottom: 0;">
	<div class="container">
		<div class="row">
			
			<div class="col-md-6">
				<h1 class="text-left" style="font-size:28px;">Top Up Unlimited ADSL Broadband</h1>
				<p class="text-left hidden-xs" style="font-size:16px;">
					<strong>Top Up Unlimited ADSL plan is the new broadband plan concept from CyberPark Limited.</strong>
				</p>
				<p class="text-left hidden-xs"  style="font-size:14px;">
				The process and conditions as bellows, it will be more cost efficiency and convenience to all the Top Up users. 
				CyberPark Top Up Plan offer you unlimited data plan by weekly charges,
				CyberPark Top Up payment method include: Top-up Voucher, Credit Cards, Account to Account, or Call 0800 2 Cyber (29237) for help.
				</p>
			</div>
			<div class="col-md-6">
				<img class="hidden-xs" src="${ctx }/public/bootstrap3/images/top-up.png" style="padding:0;margin-top: -50px;">
			</div>
		</div>
	</div>
</div>

<div class="container">
	
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
								<span style="font-size:50px; font-weight:bold; float:left;margin-left: 40px;margin-top: 70px;line-height: 1;">$</span>
								<span style="font-size: 100px; font-weight:bold; float: left;margin-left: 10px;margin-top: 30px;line-height: 1;"> 
									<fmt:formatNumber value="20" type="number" pattern="##0" />
								</span>
							</p>
						</div>
					</div>
				</div>
				<div class="col-lg-3">
					<div class="panel panel-success">
						<div class="panel-body" style="padding: 0;">
							<p class="bg-success text-center text-success" style="position:relative;margin-bottom:0;height: 140px;">
								<span style="font-size: 100px; font-weight:bold; float: left;margin-left: 40px;margin-top: 30px;line-height: 1;"> 
									7
								</span>
								<span style="font-size: 50px; font-weight:bold; float:left;margin-left: 0px;margin-top: 70px;line-height: 1;">
									Days
								</span>
							</p>
						</div>
					</div>
				</div>
				<div class="col-lg-3">
					<div class="panel panel-success">
						<div class="panel-body" style="padding: 0;">
							<p class="bg-success text-center text-success" style="margin-bottom:0;height: 140px;padding: 32px;">
								<span style="font-size: 45px; font-weight:bold; line-height: 1;">Unlimited</span>
								<span style="font-size: 45px; font-weight:bold; line-height: 1;">Data</span>
							</p>
						</div>
					</div>
				</div>
				<div class="col-lg-3">
					<div class="panel panel-success">
						<div class="panel-body" style="padding: 0;"><!--  -->
							<a id="join_plan" href="${ctx }/order/${planTypeMap['ADSL']['plansNaked'][0].id}" class="btn btn-success btn-block" style="height: 140px;" >
								<span style="font-size: 50px; font-weight: bold; margin: 10px; line-height: 1; display:block;">Join</span>
								<span style="font-size: 50px; font-weight: bold; margin: 0; line-height: 1;">Plan</span>
							</a>
						</div>
					</div>
				</div>
			</div>
			
		</div>
	</div>
	
	<div class="panel panel-success">
  		<div class="panel-heading">
			<h3 class="panel-title">ADSL + HomeLine TopUp Plan (<span class="text-danger">Local Call Only</span>)</h3>
		</div>
		<div class="panel-body">
			<div class="row">
				<div class="col-lg-9">
					<div class="row">
						<div class="col-lg-3">
							<div class="panel panel-success">
								<div class="panel-body" style="padding: 0;"> 
									<p class="bg-success text-center text-success" style="position:relative;margin-bottom:0;height: 140px;">
										<span style="font-size:50px; font-weight:bold; float:left;margin-left: 30px;margin-top: 70px;line-height: 1;">$</span>
										<span style="font-size: 90px; font-weight:bold; float: left;margin-left: 10px;margin-top: 40px;line-height: 1;"> 
											<fmt:formatNumber value="30" type="number" pattern="##0" />
										</span>
									</p>
								</div>
							</div>
						</div>
						<div class="col-lg-3">
							<div class="panel panel-success">
								<div class="panel-body" style="padding: 0;">
									<p class="bg-success text-center text-success" style="position:relative;margin-bottom:0;height: 140px;">
										<span style="font-size: 90px; font-weight:bold; float: left;margin-left: 25px;margin-top: 40px;line-height: 1;"> 
											7
										</span>
										<span style="font-size: 40px; font-weight:bold; float:left;margin-left: 0px;margin-top: 80px;line-height: 1;">
											Days
										</span>
									</p>
								</div>
							</div>
						</div>
						<div class="col-lg-3">
							<div class="panel panel-success">
								<div class="panel-body" style="padding: 0;">
									<p class="bg-success text-center text-success" style="margin-bottom:0;height: 140px;padding-top: 40px;">
										<span style="font-size: 35px; font-weight:bold; line-height: 1;">Unlimited</span>
										<span style="font-size: 35px; font-weight:bold; line-height: 1;">Data</span>
									</p>
								</div>
							</div>
						</div>
						<div class="col-lg-3">
							<div class="panel panel-success">
								<div class="panel-body" style="padding: 0;">
									<p class="bg-success text-center text-success" style="margin-bottom:0;height: 140px;padding-top: 40px;">
										<span style="font-size: 35px; font-weight:bold; line-height: 1;">Home</span>
										<span style="font-size: 35px; font-weight:bold; line-height: 1;">Line</span>
									</p>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="col-lg-3">
					<div class="panel panel-success">
						<div class="panel-body" style="padding: 0;"><!--  -->
							<a id="join_plan" href="${ctx }/order/${planTypeMap['ADSL']['plansClothed'][0].id}" class="btn btn-success btn-block" style="height: 140px;" >
								<span style="font-size: 50px; font-weight: bold; margin: 10px; line-height: 1; display:block;">Join</span>
								<span style="font-size: 50px; font-weight: bold; margin: 0; line-height: 1;">Plan</span>
							</a>
						</div>
					</div>
				</div>
			</div>
			
		</div>
	</div>
	
	<div>
		<ul>
			<li>No Terms, No contract</li>
			<li>Use your own Routers</li>
			
		</ul>
	</div>
	
	<div class="page-header">
		<span class="home-title-h2">
			Introduction of Top-up broadband plan
		</span>
	</div>
	<div>
		<p class="lead">
			Top-up broadband plan is created by CyberPark Limited in 2014.
		</p>
		<p>
			We broke your traditional understanding to the broadband contract plans and monthly charges method. Convenience services can give you more choices to your broadband plans. You can easily top-up your account and change your monthly data usages. Customers can purchase the top-up voucher from the dairy shops. We also accept credit cards, pay-pal, and account-to-account payment methods. You don’t need change any router when you transit from your current internet service providers to our Top-up broadband plan. Your transition will be automatically changing broadband to our service without any gaps. 
		</p>
	</div>
	
	<div class="page-header">
		<span class="home-title-h2">
			How can I apply Top-up broadband plan?
		</span>
	</div>
	<div>
		<ul>
			<li>Visit our web-site www.cyberpark.co.nz and choose "Apply Top-up broadband"</li>
			<li>Filling your personal details, like name, address, contact phone number</li>
			<li>If you are transiting from your current Internet service providers, please accurately fill the details of your current provider and account number.</li>
			<li>Submit all your details and select the first top-up value (start from NZ$ 30 for 7 days)</li>
			<li>Transition Provider is free of charge</li>
			<li>New connection has one-off charge NZ$ 145</li>
			<li>New Phone Jack installation has one-off charge NZ$ 199</li>
			<li>If you don't have ADSL router, we can offer you NZ$ 50 for TP link ADSL2+ router</li>
		</ul>
	</div>
	
	<div class="page-header">
		<span class="home-title-h2">
			What you have in your Top-up broadband plan?
		</span>
	</div>
	<div>
		<p>Top-up broadband plan is a Naked ADSL2+ broadband plan or Home Phone line + ADSL2+ broadband plan. Home phone line is for local call only.</p>
	</div>
	
	<div class="page-header">
		<span class="home-title-h2">
			How does Top-up broadband plan works?
		</span>
	</div>
	<div>
		<ul>
			<li>
				After your application, you will receive a confirmation email and provision confirmation email/message from us. Cyber Park broadband service normally can provide services to you in 3 to 5 working days. New connection or New Phone Jack point installation application may takes longer, and our installation cooperator may contact with you for booking appointment.
			</li>
			<li>
				We will generate your account and passwords. You can log in your account to check your data usage period (expire date). 
			</li>
			<li>
				Payment functions can offer you different methods to Top-up, like credit cards, account-to-account, paypal, or top-up voucher. All these payment methods are security and convenience in NZ market.  	
			</li>
			<li>
				When you overdue your service period, you broadband service will be automatically suspend in the following 48 hours. After that, your broadband connection will be disconnected and reaction may charges you as a New Connection which costs NZ$ 145.  
			</li>
		</ul>
	</div>
	
	<div class="page-header">
		<span class="home-title-h2">
			Conditions:
		</span>
	</div>
	<div>
		<ul>
			<li>CyberPark top up plan is based on rechargeable process. All the top up amount will be the credits in Customers’ Cyber Park account.</li>
			<li>User your own router/modem</li>
			<li>No term</li>
			<li>"week" is defined as 7 days after the customer received broadband service</li>
			<li>Transfer the existing broadband connection to CyberPark is free of charge</li>
			<li>Get a new broadband connection on an existing (but inactive) Phone Jack point charge NZ$ 145</li>
			<li>Get a new broadband connection and an new Phone Jack point installation and activation charge NZ$ 199</li>
			<li>CyberPark Top Up plan service is automatically disconnected in 48 hours after expire date, reactivating cost in 2 working days is NZD$45. Re-connection will be defined as new connection which costs NZ$ 145.</li>
			<li>After CyberPark Top Up plan service was terminated, CyberPark account user can Top Up his/her CyberPark account online or contact with our customer service to reactivating the broadband services in 2 working days. However, we cannot guarantee the previous ADSL pots available all the time. More information, please contact with our customer service , 0800 2 Cyber (29237)</li>
			<li>Reasonable unlimited data use</li>
			<li>Respect to the copy right regulation in NZ</li>
			<li>Free of charge upgrade to any CyberPark term plans</li>
			<li>CyberPark has the rights to explain the charging system to Top Up Plans, more information you can email to <a href="">info@cyberpark.co.nz</a> or call 0800 2 Cyber (29237)</li>
		</ul>
	</div>
	<p>&nbsp;</p>
</div>


<jsp:include page="footer.jsp" />
<jsp:include page="script.jsp" />
<script type="text/javascript">
(function($) {
	
})(jQuery);
</script>
<jsp:include page="footer-end.jsp" />