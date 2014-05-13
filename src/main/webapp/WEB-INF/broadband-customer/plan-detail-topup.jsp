<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
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
			Forget your monthly broadband plans, we can offer you a Top Up broadband
		</h2>
	</div>
	<div class="alert alert-info">
		<p>
			Free of Charges, Switch from your current broadband suppliers to our Top-Up broadband plans 
		</p>
		<p>
			Easy to apply online or give us call 0800 2 CYBER (29237)
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
				3. Review and Checkout 
			</a>
		</li>
	</ul>
	
	<div class="row">
		<div class="col-lg-6">
			<div class="panel panel-success">
		  		<div class="panel-heading">
					<h3 class="panel-title">Naked ADSL Only</h3>
				</div>
				<table class="table">
					<tr>
						<th>Top-up Price</th>
						<th>Data</th>
						<th>Available Days</th>
						<th>&nbsp;</th>
					</tr>
					<tr>
						<td>$20.00</td>
						<td>10 GB</td>
						<td>20 days</td>
						<td>
							<a href="${ctx }/order/${planTypeMap['ADSL']['plans'][0].id}/topup/20" class="btn btn-success btn-block">Purchase</a>
						</td>
					</tr>
					<tr>
						<td>$50.00</td>
						<td>30 GB</td>
						<td>45 days</td>
						<td>
							<a href="${ctx }/order/${planTypeMap['ADSL']['plans'][0].id}/topup/50" class="btn btn-success btn-block">Purchase</a>
						</td>
					</tr>
					<tr>
						<td>$100.00</td>
						<td>80 GB</td>
						<td>60 days</td>
						<td>
							<a href="${ctx }/order/${planTypeMap['ADSL']['plans'][0].id}/topup/100" class="btn btn-success btn-block">Purchase</a>
						</td>
					</tr>
					<tr>
						<td>$150.00</td>
						<td>150 GB</td>
						<td>90 days</td>
						<td>
							<a href="${ctx }/order/${planTypeMap['ADSL']['plans'][0].id}/topup/150" class="btn btn-success btn-block">Purchase</a>
						</td>
					</tr>
					<tr>
						<td>$200.00</td>
						<td>250 GB</td>
						<td>120 days</td>
						<td>
							<a href="${ctx }/order/${planTypeMap['ADSL']['plans'][0].id}/topup/200" class="btn btn-success btn-block">Purchase</a>
						</td>
					</tr>
				</table>
			</div>
		</div>
		
	</div>
	
	<div>
		<ul>
			<li>No Terms, No contract </li>
			<li>Use your own Routers</li>
			<li>New Connection fee, one off charge $99</li>
			<li>New Jack-point installation + new connection fee, one off charge $169</li>
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
			<li>If you are transiting from your current internet service providers, please accurately fill the details of your current provider and account number. </li>
			<li>Submit all your details and select the first top-up value (minimum $20 for 10 GB 20days)</li>
			<li>New connection has one-off charge $99</li>
			<li>New Jack-point installation has one-off charge $169</li>
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