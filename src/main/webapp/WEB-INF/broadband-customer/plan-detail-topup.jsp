<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<jsp:include page="header.jsp" />


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
							<a id="join_plan" href="${ctx }/plans/define/ADSL/${planTypeMap['ADSL']['plansNaked'][0].id}" class="btn btn-success btn-block" style="height: 140px;" >
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
							<a id="join_plan" href="${ctx }/plans/define/ADSL/${planTypeMap['ADSL']['plansClothed'][0].id}" class="btn btn-success btn-block" style="height: 140px;" >
								<span style="font-size: 50px; font-weight: bold; margin: 10px; line-height: 1; display:block;">Join</span>
								<span style="font-size: 50px; font-weight: bold; margin: 0; line-height: 1;">Plan</span>
							</a>
						</div>
					</div>
				</div>
			</div>
			
		</div>
	</div>
	
	<div class="row" >
		<div class="col-md-6">
			<p class="lead" style="margin-top: 70px;">
				New Concept for New Zealand Broadband Plan
			</p>
			<p class="lead">
				No Terms, No Contract, Use your own Routers, or you can purchase a new router from us online. 
			</p>
			<p class="lead">
				Visit Our Web-site <a href="http://www.cyberpark.co.nz">www.cyberpark.co.nz</a> and select "Top Up Plan"
			</p>
		</div>
		<div class="col-md-6">
			<img class="img-responsive" alt="" src="${ctx }/public/bootstrap3/images/topup-1.png">
		</div>
	</div>
	
	<hr>
	
	<div class="row" >
		<div class="col-md-6" >
			<p class="lead" style="margin-top: 20px;">
				Filling your Personal Details, like, name, address, contact phone number, email, etc.
			</p>
			<p class="lead">
				Transfer the existing broadband connection to Cyber Park is free of charge
			</p>
			<p class="lead">
				Get a new broadband connection on an existing (but inactive) Phone Jack point costs $169
			</p>
			<p class="lead">
				Get a new broadband connection and a new Phone Jack point and activation costs $229
			</p>
		</div>
		<div class="col-md-6">
			<img class="img-responsive" alt="" src="${ctx }/public/bootstrap3/images/topup-2.png">
		</div>
	</div>
	
	<hr>
	
	<div class="row">
		<div class="col-md-6">
			<p class="lead"  style="margin-top: 30px;">
				All our plans are pay in advance plan. Please arrange the payment after you receive your Ordering Form. At the same time, you will receive your Cyber Park account number and passwords. You can check your accounts online. 
			</p>
			<p class="lead">
				Payment methods include: Credit Cards (online), Account-to-account (online), Voucher (online), and bank deposits. 
			</p>
		</div>
		<div class="col-md-6">
			<img class="img-responsive" alt="" src="${ctx }/public/bootstrap3/images/topup-3.png">
		</div>
	</div>
	
	<hr>
	
	<div class="row" >
		<div class="col-md-6">
			<p class="lead" style="margin-top: 30px;">
				After we received your ordering deposit, you will receive Cyber Park broadband services normally in 5 working days. 
			</p>
			<p class="lead">
				New connection or New Phone Jack point installation application may takes longer, and cooperator may contact with you for booking appointments. 
			</p>
		</div>
		<div class="col-md-6">
			<img class="img-responsive" alt="" src="${ctx }/public/bootstrap3/images/topup-4.png">
		</div>
	</div>
	
	<hr>
	
	<div class="row" >
		<div class="col-md-6">
			<p class="lead" style="margin-top: 30px;">
				Log into your Cyber Park account, you can easily to find out your personal details, accounts, and several payment methods. 
			</p>
			<p class="lead">
				There are no any extra charges will be happened because our Top Up plan is unlimited data or local call only.
			</p>
		</div>
		<div class="col-md-6">
			<img class="img-responsive" alt="" src="${ctx }/public/bootstrap3/images/topup-5.png">
		</div>
	</div>
	
	<hr>
	
	<div class="row">
		<div class="col-md-6">
			<p class="lead" style="margin-top: 30px;">
				Cyber Park Top Up plan service will be automatically suspended after expire date. 
			</p>
			<p class="lead">
				Reactivating in 48 hours will be free of charges. The services will be disconnected 2 working days after the services are suspended. The first reactivating cost is NZD$45 after the services are disconnected. The seconded re-connection will be defined as new connection which costs NZ$ 169.
			</p>
		</div>
		<div class="col-md-6">
			<img class="img-responsive" alt="" src="${ctx }/public/bootstrap3/images/topup-6.png">
		</div>
	</div>
	
	<hr>
</div>


<jsp:include page="footer.jsp" />
<jsp:include page="script.jsp" />
<script type="text/javascript">
(function($) {
	
})(jQuery);
</script>
<jsp:include page="footer-end.jsp" />