<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>  
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<jsp:include page="../header.jsp" />

<c:if test="${customerReg.customerOrder.sale_id == 10023}">
	<div class="cyberpark-home-bg" >
		<img class="img-responsive" src="${ctx }/public/bootstrap3/images/zhongqiu2${customerReg.language }.png" style="margin: -20px auto 10px;padding: 0;" >
	</div>
</c:if>
<c:if test="${customerReg.customerOrder.sale_id == 20023}">
	<div class="cyberpark-home-bg" >
		<img class="img-responsive" src="${ctx }/public/bootstrap3/images/3monthgift${customerReg.language }.jpg" style="margin: -20px auto 10px;padding: 0;" >
	</div>
</c:if>

<div class="container">
	
	<div class="hidden-xs hidden-sm">
		<ul class="nav nav-pills nav-wizard" style="width: 550px; margin: 0 auto;">
			<li class="active"><a href="javascript:void(0);"><span class="glyphicon glyphicon-search"></span> Check Address</a><div class="nav-arrow"></div></li>
			<li><div class="nav-wedge"></div><a href="javascript:void(0);"><span class="glyphicon glyphicon-pencil"></span> Fill in Application</a><div class="nav-arrow"></div></li>
			<li><div class="nav-wedge"></div><a href="javascript:void(0);"><span class="glyphicon glyphicon-eye-open"></span> Review & Checkout</a></li>
		</ul>
		<hr>
	</div>

	<h2 class="text-center hidden-xs hidden-sm">
		Check your address whether the service can be installed
	</h2>
	<h4 class="text-center hidden-lg hidden-md">
		Check the service can be installed
	</h4>
	
	<div class="row" style="margin-top:30px;">
		<div class="col-md-6 col-md-offset-3 col-xs-12 col-sm-12">
			<div class="input-group">
				<input id="address" value="${customerReg.address }" type="text" class="form-control input-lg" placeholder="Put your address here" /> 
				<span class="input-group-btn">
					<button class="btn btn-success btn-lg ladda-button" data-style="zoom-in" type="button" id="goCheck">
						<span class="ladda-label glyphicon glyphicon-search"></span>
					</button>
				</span>
			</div>
		</div>
	</div>
	
	<p class="text-center">
		<a href="${ctx }/plans/address/clear" class="btn btn-link">Clear Address</a>
	</p>
	
	<c:if test="${customerReg.customerOrder.sale_id == 10023}">
	
	<div class="panel panel-success">
		<div class="panel-heading">
			<h4 class="panel-title">
				<a data-toggle="collapse" data-parent="#accordion" href="#collapseOne"> Something You may need to Know </a>
			</h4>
		</div>
		<div id="collapseOne" class="panel-collapse collapse">
			<div class="panel-body">
				<h2>Join Cyber Park Annual Promotion Plan $1188/year</h2>
				<ul>
					<li>
						Cyber Park Annual Promotion Plan $1188/year is Ultra-Fast VDSL Unlimited Data + VoIP Home line. Payment is based on pay-in-advance method. The annual fee only includes the basic package which include NZ Local call free, 200mins for 40 countries, and unlimited VDSL data. It is for personal user only. 
					</li>
					<li>
						Cyber Park Annual Promotion Plan $1188/year application process:
						<ol>
							<li>
								1. After we received your ordering, you will receive the ordering confirmation message by email and text message to confirm your ordering details, payment amounts and methods. Your ordering application will hold 5 working days. 
							</li>
							<li>
								2. You can arrange the payment by following method: online payment (Visa or Master cards), log-in your Cyber Park account do account-to-account payment, or bank deposit. You will receive the payment receipt for your Cyber Park account credits until you start use our services. 
							</li>
							<li>
								3. Our provision team is going to process your ordering in the following 48 hours and you will receive the second message for confirming the date of receiving broadband service. At the same time you will receive our free VDSL+VoIP Router and IPad Mini Wifi 16G. 
							</li>
							<li>
								4. Transfer from other Internet services provider normally takes around 5 working days ( please inform your existing Internet service provider AFTER you received our email and text message for SERVICE GIVEN DATE.). New installation normally takes around 7 – 10 working days. 
							</li>
						</ol>
					</li>
					<li>
						The home line which included into this Annual plan is a VoIP (Voice over IP) line. Our free router can support any type of normal telephone to connect with the VoIP pots. If you do request for the traditional PSTN home line, it will be extra $15/month charges, please inform our customer services for this request. 
					</li>
				</ul>
				
				<h2>Cyber Park Account</h2>
				<ul>
					<li>
						Customer can log into the account to check the usages, do the payment, or arrange the extra credits top up, etc. 
					</li>
					<li>
						If there are any charges happened in your accounts, the bill will be generated monthly. And, you can arrange the payment online or bank deposit. 
					</li>
					<li>
						If you are going to leave our Cyber Park, we do not provide any refund policy for this Cyber Park Annual Promotion Plan $1188/year. 
					</li>
				</ul>
				
				<h2>Other Charges and Other Free Services</h2>
				<ul>
					<li>
						If you are going to move your place to some where has spare Phone Jack point, our service could be follow you and free of connection charges.
					</li>
					<li>
						If you require some other phone service, like Calling ID, call waiting, etc. please call our customer services 0800 2 Cyber (29237) for requiring. And, there are some extra charges will be applied. 
					</li>
					<li>
						200 mins for 40 countries international calling include: 
						<ol>
							<li>
								Mobile+Fixline: Bangladesh; Cambodia; Canada; China; Hon Kong; India; Malaysia; Singapore; Korea Rep; USA; Vietnam.
							</li>
							<li>
								Fixline: Argentina; Australia; Belgium; Brazil; Cyprus; Czech Rep; Denmark; France;  Germany; Greece; Indonesia; Ireland; Israel; Italy; Japan; Laos; Netherlands; Norway; Pakistan; Poland; Portugal; Russia; South Africa; Spain; Sweden; Swizerland; Taiwan; Thailand; United Kingdom. 
							</li>
							<li>
								Other countries rates, please check our website, <a href="http://www.cyberpark.co.nz">www.cyberpark.co.nz</a>
							</li>
							<li>
								New Zealand local call free. New Zealand National Wide calling charges 5cents/mins. New Zealand Mobile calling charges 19 cents/min. 	
							</li>
						</ol>
					</li>
				</ul>
			</div>
		</div>
	</div>

	<p class="alert alert-warning">
		If you have more questions, you can directly contact our customer service 0800 2 Cyber (29237).
	</p>
	
	</c:if>
	
	<div id="checkResult" data-select_plan_id="${select_plan_id}" data-select_plan_type="${select_plan_type}"  data-isShowContinue="no" style="min-height:300px;margin-top:15px;"></div>
</div>

<div id="map_canvas" style="width:720px;height:600px;display:none;"></div>
<script type="text/html" id="result_tmpl" data-ctx="${ctx }">
<jsp:include page="resultAddressCheck.html" />
</script>

<jsp:include page="../footer.jsp" />
<jsp:include page="../script.jsp" />
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/icheck.min.js"></script>
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/jTmpl.js"></script>
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/spin.min.js"></script>
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/ladda.min.js"></script>
<script type="text/javascript" src="${ctx}/public/broadband-customer/plans/address-check.js?ver=2014103803"></script>
<script src="https://maps.google.com/maps/api/js?sensor=false&libraries=places&region=NZ" type="text/javascript"></script>
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/autoCompleteAddress.js"></script>
<jsp:include page="../footer-end.jsp" />