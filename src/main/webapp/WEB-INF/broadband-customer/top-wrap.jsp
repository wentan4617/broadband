<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<style>
#navhead {
	margin-bottom:0;
}
</style>
<div class="cyberpark-home-bg">
	<img class="img-responsive" src="${ctx }/public/bootstrap3/images/cyberpark_home_text.png" alt="open term $99 per month" title="open term $99 per month">
	<div class="container">
		<div class="row">
			<div class="col-md-2 col-md-offset-2 col-xs-3">
				<a href="${ctx }/plans/broadband">
					<img class="img-responsive" src="${ctx }/public/bootstrap3/images/adsl-icon.png" style="padding: 0 0 10px;" alt="Traditional Broadband" title="Traditional Broadband">
				</a>
				<p class="text-center hidden-xs" style="color:white;font-weight: bold; font-size: 18px;">ADSL Broadband</p>
			</div>
			<div class="col-md-2 col-xs-3">
				<a href="${ctx }/plans/ultra-fast-vdsl">
					<img class="img-responsive" src="${ctx }/public/bootstrap3/images/vdsl-icon.png" style="padding: 0 0 10px;" alt="Ultra Fast Broadband" title="Ultra Fast Broadband">
				</a>
				<p class="text-center hidden-xs" style="color:white;font-weight: bold; font-size: 18px;">High-Speed VDSL</p>
			</div>
			<div class="col-md-2 col-xs-3">
				<a href="${ctx }/plans/ultra-fast-fibre">
					<img class="img-responsive" src="${ctx }/public/bootstrap3/images/ufb-icon.png" style="padding: 0 0 10px;" alt="Super Speed Broadband" title="Super Speed Broadband">
				</a>
				<p class="text-center hidden-xs" style="color:white;font-weight: bold; font-size: 18px;">Ultra-Fast Fibre</p>
			</div>
			<div class="col-md-2 col-xs-3">
				<a href="${ctx }/plans/plan-topup/personal">
					<img class="img-responsive" src="${ctx }/public/bootstrap3/images/topup-icon.png" style="padding: 0 0 10px;" alt="Short Term Broadband" title="Short Term Broadband">
				</a>
				<p class="text-center hidden-xs" style="color:white;font-weight: bold; font-size: 18px;">Top-Up Plans</p>
			</div>
		</div>
		<div class="row" style="margin-bottom:20px;">
			
			<div class="col-md-8 col-md-offset-2 col-xs-12 col-sm-12">
				<div class="carbonad">
					<p class="lead text-center hidden-xs" style="color:white;margin:10px auto;">Let us help you figure out what's available to you</p>
					
					<p style="font-size:14px;color:white">Eg. 290 Queen St, Auckland, 1010</p>
					<div class="input-group">
						<input id="address" type="text" class="form-control input-lg" placeholder="Put your address here" /> 
						<span class="input-group-btn">
							<button class="btn btn-success btn-lg ladda-button" data-style="zoom-in" type="button" id="goCheck">
								<span class="ladda-label glyphicon glyphicon-search"></span>
							</button>
						</span>
					</div>
					<div id="checkResult" style="margin-top:15px;"></div>
					<p>&nbsp;</p>
				</div>
			</div>
		</div>
	</div>
	<div class="cyberpark-home-bottom"></div>
</div>

<div class="container" style="margin-top:30px;">
	<div class="row btn-purple" style="margin-bottom:10px;">
		<div class="col-md-6" style="padding-left:0;">
			<img class="img-responsive" src="${ctx }/public/bootstrap3/images/promotion.png" style="" alt="Free Protable HDD & Free iPad" title="Free Protable HDD & Free iPad">
		</div>
		<div class="col-md-6">
			<h1 class="lead" style="padding:10px 20px 0;margin:10px 0 0;font-size: 36px;font-weight: bold;">Unlimited Data</h1>
			<h1 class="lead" style="padding:10px 20px 0;margin:0 0 0;font-size: 36px;font-weight: bold;">VoIP Homeline</h1>
			<a href="${ctx }/plans/promotions" class="btn btn-lg btn-outline-inverse pull-right" style="margin: 8px 14px;">View More</a>
		</div>
	</div>
	<div class="row btn-warning" style="margin-bottom:10px;">
		<div class="col-md-6">
			<p style="padding:20px 20px 0;"></p>
			<img class="img-responsive" src="${ctx }/public/bootstrap3/images/adsl-p.png" alt="Classic Broadband" title="Classic Broadband">
		</div>
		<div class="col-md-6">
			<h2 class="lead" style="padding:20px 20px 0;margin:0 0 10px;">ADSL BROADBAND</h2>
			<p style="padding:0 20px 0;">
				We provide the best deal on ADSL broadband plan. No speed limited and data cap on our Unlimited ADSL plans. All our plans are pay in advance. It provides you a clear and easy read bill. Simple and easy on-line application method can be filled in 5 mins. Or, give us a call, 0800 2 CYBER(29237). Our customer service can help you to apply your broadband as well.
			</p>
			<a href="${ctx }/plans/broadband" class="btn btn-lg btn-outline-inverse pull-right" style="margin:14px;">View More</a>
		</div>
	</div>
	<div class="row btn-danger" style="margin-bottom:10px;">
		<div class="col-md-6">
			<p style="padding:20px 20px 0;"></p>
			<img class="img-responsive" src="${ctx }/public/bootstrap3/images/vdsl-p.png" alt="Unlimited Ultra Fast Broadband" title="Unlimited Ultra Fast Broadband">
		</div>
		<div class="col-md-6">
			<h2 class="lead" style="padding:20px 20px 0;margin:0 0 10px;">High Speed VDSL</h2>
			<p style="padding:0 20px 0;">
				New generation technology on VDSL plan. Faster, stable and reliable VDSL can go through your current copper line. The speed of VDSL will be 3 times faster than ADSL broadband. Download speed achieve to 15Mbps to 35Mbps, and upload speed achieve to around 10Mbps.
			</p>
			<a href="${ctx }/plans/ultra-fast-vdsl" class="btn btn-lg btn-outline-inverse pull-right" style="margin:14px;">View More</a>
		</div>
		
	</div>
	<div class="row btn-info" style="margin-bottom:10px;">
		<div class="col-md-6">
			<p style="padding:20px 20px 0;"></p>
			<img class="img-responsive" src="${ctx }/public/bootstrap3/images/ufb-p.png" alt="Super Fast Unlimited Broadband" title="Super Fast Unlimited Broadband">
		</div>
		<div class="col-md-6">
			<h2 class="lead" style="padding:20px 20px 0;margin:0 0 10px;">Ultra-Fast-Fibre</h2>
			<p style="padding:0 20px 0;">
				The new generation of broadband is coming to NZ. There are more and more areas are covered by UFB Fibre. Find a plan and check your address for UFB coverage. No speed limited and data cap on our Unlimited ADSL plans. All our plans are pay in advance. Simple and easy on-line application method can be filled in 5 mins. Or, give us a call, 0800 2 CYBER(29237).
			</p>
			<a href="${ctx }/plans/ultra-fast-fibre" class="btn btn-lg btn-outline-inverse pull-right" style="margin:14px;">View More</a>
		</div>
	</div>
	<div class="row btn-success" style="margin-bottom:10px;">
		<div class="col-md-6">
			<p style="padding:20px 20px 0;"></p>
			<img class="img-responsive" src="${ctx }/public/bootstrap3/images/topup-p.png" alt="New Seasion New Fashion" title="New Seasion New Fashion">
		</div>
		<div class="col-md-6">
			<h2 class="lead" style="padding:20px 20px 0;margin:0 0 10px;">Top-Up Plans</h2>
			<p style="padding:0 20px 0;">
				The process and conditions as bellows, it will be more cost efficiency and convenience to all the Top Up users. CyberPark Top Up Plan offer you unlimited data plan by weekly charges, CyberPark Top Up payment method include: Top-up Voucher, Credit Cards, Account to Account, or Call 0800 2 Cyber (29237) for help.
			</p>
			<a href="${ctx }/plans/plan-topup/personal" class="btn btn-lg btn-outline-inverse pull-right" style="margin:14px;">View More</a>
		</div>
	</div>
	
</div>
