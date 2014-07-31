<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<div class="container">
	<div class="row visible-xs visible-sm" style="margin-top:10px;">
		<div class="col-xs-4 col-sm-4" style="padding:0 8px;">
			<a href="${ctx }/plans/plan-no-term/personal" target="_blank" class="btn btn-success btn-block">
				<span class="glyphicon glyphicon-star-empty" style="display:block;font-size:24px;"></span>
				<span style="display:block;font-weight:blod;font-size:12px;margin-top:5px;">No Term Plan</span>
			</a>
		</div>
		<div class="col-xs-4 col-sm-4 " style="padding:0 8px;">
			<a href="${ctx }/plans/plan-topup/personal" target="_blank" class="btn btn-success btn-block">
				<span class="glyphicon glyphicon-gift" style="display:block;font-size:24px;"></span>
				<span style="display:block;font-weight:blod;font-size:12px;margin-top:5px;">Top Up Plan</span>
			</a>
		</div>
		<div class="col-xs-4 col-sm-4 " style="padding:0 8px;">
			<a href="${ctx }/plans/plan-term/personal" target="_blank" class="btn btn-success btn-block">
				<span class="glyphicon glyphicon-list-alt" style="display:block;font-size:24px;"></span>
				<span style="display:block;font-weight:blod;font-size:12px;margin-top:5px;">Term Plan</span>
			</a>
		</div>
	</div>
	<div class="row visible-xs visible-sm" style="margin-top:10px;">
		<%-- <div class="col-xs-4 col-sm-4 " style="padding:0 8px;">
			<a href="${ctx }/plans/plan-term/business" rel="nofollow" class="btn btn-success btn-block">
				<span class="glyphicon glyphicon-briefcase" style="display:block;font-size:24px;"></span>
				<span style="display:block;font-weight:blod;font-size:12px;margin-top:5px;">Business</span>
			</a>
		</div> --%>
		<div class="col-xs-4 col-sm-4 " style="padding:0 8px;">
			<a href="${ctx }/about-us" class="btn btn-success btn-block">
				<span class="glyphicon glyphicon-leaf" style="display:block;font-size:24px;"></span>
				<span style="display:block;font-weight:blod;font-size:12px;margin-top:5px;">About US</span>
			</a>
		</div>
		<div class="col-xs-4 col-sm-4 " style="padding:0 8px;">
			<a href="${ctx }/about-us#contact" rel="nofollow" class="btn btn-success btn-block">
				<span class="glyphicon glyphicon-phone-alt" style="display:block;font-size:24px;"></span>
				<span style="display:block;font-weight:blod;font-size:12px;margin-top:5px;">0800 229 237</span>
			</a>
		</div>
	</div>
	
	<div class="jumbotron text-center" style="background-color: transparent; margin-bottom:0;padding-bottom:0;padding-left: 0;padding-right: 0;">
		<h1 class="hidden-xs hidden-sm"><strong>Welcome to CyberPark</strong></h1>
		<h2 class="visible-xs visible-sm" style="font-size:24px;"><strong>Welcome to CyberPark</strong></h2>
		<p class="lead text-muted">A Safe Park</p>
	</div>
	
	<div class="row hidden-xs hidden-sm">
		<div class="col-lg-6" style="border-right: 1px solid #dfe5d7;border-bottom: 1px solid #dfe5d7;">
			<div class="text-center home-title">
				<span class="glyphicon glyphicon-pencil"></span> Simple
			</div>
			<p class="text-center">
				Easy application method through on-line system.<br/>
				Calling to customer services <span class="text-info">0800 229 237</span>.<br/>
				Use your own Modem(router) or we do provide one, you do not need to do any IT jobs.
			</p>
		</div>
		<div class="col-lg-6" style="border-bottom: 1px solid #dfe5d7;">
			<div class="text-center home-title">
				<span class="glyphicon glyphicon-usd"></span> Affordable
			</div>
			<p class="text-center">
				Top-up plan start from $20, 10G, for 15 days; $50, 30G, for 30days.<br/>
				If you transfer from your current Internet service provider, <br/>
				we free of charge first connection fee.
			</p>
		</div>
		<div class="col-lg-6" style="border-right: 1px solid #dfe5d7;">
			<div class="text-center home-title">
				<span class="glyphicon glyphicon-tower"></span> Facilitated
			</div>
			<p class="text-center">
				Smart application, personal account, <br/>
				and payment system give you more services' supports.<br/>
				We can accept all the models of modem to register.<br/>
				Different payment methods, or Change your plans and payment method any time.
			</p>
		</div>
		<div class="col-lg-6" >
			<div class="text-center home-title">
				<span class="glyphicon glyphicon-flash"></span> Efficient
			</div>
			<p class="text-center">
				Place order online and connection in 3-5 working days, <br/>
				and you will receive message and email for activation confirmation.<br/>
				Re-charge anytime, anywhere base on your personal account by Top-up Vouchers.<br/>
				Credit Cards, Direct debts, account to account, or paypal systems.
			</p>
		</div>
	</div>
	
	<div class="row hidden-md hidden-lg">
		<div class="col-lg-6" style="border-bottom: 1px solid #dfe5d7;">
			<div class="text-center home-title">
				<span class="glyphicon glyphicon-pencil"></span> Simple
				<a data-toggle="collapse" href="#collapseSimple">
					<span class="glyphicon glyphicon-chevron-down pull-right" style="font-size:28px;" data-id="collapseSimple"></span>
				</a>
			</div>
			<div id="collapseSimple" class="panel-collapse collapse">
				<p class="text-center">
					Easy application method through on-line system.<br/>
					Calling to customer services <span class="text-info">0800 229 237</span>.<br/>
					Use your own Modem(router) or we do provide one, you do not need to do any IT jobs.
				</p>
			</div>
		</div>
		<div class="col-lg-6" style="border-bottom: 1px solid #dfe5d7;">
			<div class="text-center home-title">
				<span class="glyphicon glyphicon-usd"></span> Affordable
				<a data-toggle="collapse" href="#collapseAffordable">
					<span class="glyphicon glyphicon-chevron-down pull-right" style="font-size:28px;" data-id="collapseAffordable"></span>
				</a>
			</div>
			<div id="collapseAffordable" class="panel-collapse collapse">
				<p class="text-center">
					Top-up plan start from $20, 10G, for 15 days; $50, 30G, for 30days.<br/>
					If you transfer from your current Internet service provider, <br/>
					we free of charge first connection fee.
				</p>
			</div>
		</div>
		<div class="col-lg-6" style="border-bottom: 1px solid #dfe5d7;">
			<div class="text-center home-title">
				<span class="glyphicon glyphicon-tower"></span> Facilitated
				<a data-toggle="collapse" href="#collapseFacilitated">
					<span class="glyphicon glyphicon-chevron-down pull-right" style="font-size:28px;" data-id="collapseFacilitated"></span>
				</a>
			</div>
			<div id="collapseFacilitated" class="panel-collapse collapse">
				<p class="text-center">
					Smart application, personal account, <br/>
					and payment system give you more services' supports.<br/>
					We can accept all the models of modem to register.<br/>
					Different payment methods, or Change your plans and payment method any time.
				</p>
			</div>
		</div>
		<div class="col-lg-6" >
			<div class="text-center home-title">
				<span class="glyphicon glyphicon-flash"></span> Efficient
				<a data-toggle="collapse" href="#collapseEfficient">
					<span class="glyphicon glyphicon-chevron-down pull-right" style="font-size:28px;" data-id="collapseEfficient"></span>
				</a>
			</div>
			<div id="collapseEfficient" class="panel-collapse collapse">
				<p class="text-center">
					Place order online and connection in 3-5 working days, <br/>
					and you will receive message and email for activation confirmation.<br/>
					Re-charge anytime, anywhere base on your personal account by Top-up Vouchers.<br/>
					Credit Cards, Direct debts, account to account, or paypal systems.
				</p>
			</div>
		</div>
	</div>
</div>
<hr class="featurette-divider">
