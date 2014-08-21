<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<style>
#navhead {
	margin-bottom:0;
}
</style>
<div class="cyberpark-home-bg">
	<img class="img-responsive" src="${ctx }/public/bootstrap3/images/cyberpark_home_text.png">
	<div class="container">
		<p class="lead text-center">
			<a href="${ctx }/plans/ultra-fast-vdsl" class="btn btn-lg btn-outline-inverse" style="padding:20px 26px;">SEE THE PLANS</a>
		</p>
	</div>
</div>
<div class="container" style="margin-top:30px;">
	<div class="row">
		<div class="col-md-4">
			<div class="panel panel-warning">
				<button class="btn btn-warning btn-block" style="border-radius:0;" onclick="window.location.href='${ctx }/plans/broadband';">
					<p class="text-center" style="font-weight:bold;font-size:30px;margin-bottom:0;">
						Home Phone &
					</p>
					<p class="text-center" style="font-weight:bold;font-size:30px;margin-bottom:0;">
						Unlimited Broadband
					</p>
					<p class="text-center" style="font-weight:bold;margin-bottom:0;">
						<span style="font-size:30px;">$</span>
						<span style="font-size:60px;" class="hidden-xs"> 
							89
						</span>
						<span style="font-size:35px;" class="hidden-lg hidden-md hidden-sm"> 
							89
						</span>
						/ Month
					</p>
				</button>
				<div class="panel-body" style="height:180px;">
					<p style="height:120px;">
						We provide the best deal on ADSL broadband plan. No speed limited and data cap on our Unlimited ADSL plans. Apply on-line or Call our Customer Service 0800 2 CYBER (29237) now.
					</p>
					<a href="${ctx }/plans/broadband" >Check out all our Broadband Plans</a>
				</div>
			</div>
		</div>
		
		<div class="col-md-4">
			<div class="panel panel-info">
				<button class="btn btn-info btn-block" style="border-radius:0;" onclick="window.location.href='${ctx }/plans/broadband';">
					<p class="text-center" style="font-weight:bold;font-size:30px;margin-bottom:0;">
						Unlimited Naked 
					</p>
					<p class="text-center" style="font-weight:bold;font-size:30px;margin-bottom:0;">
						ADSL Broadband
					</p>
					<p class="text-center" style="font-weight:bold;margin-bottom:0;">
						<span style="font-size:30px;">$</span>
						<span style="font-size:60px;" class="hidden-xs"> 
							79
						</span>
						<span style="font-size:35px;" class="hidden-lg hidden-md hidden-sm"> 
							79
						</span>
						/ Month
					</p>
				</button>
				<div class="panel-body" style="height:180px;">
					<p style="height:120px;">
						You need Data only? No Home Line requirement? We can provide a no cap Naked ADSL Broadband plan cost only $79. No speed limited and data cap on our Unlimited ADSL plans. Apply on-line or Call our Customer Service 0800 2 CYBER (29237) now.
					</p>
					<a href="${ctx }/plans/broadband" >Check out all our Broadband Plans</a>
				</div>
			</div>
		</div>
		
		<div class="col-md-4">
			<div class="panel panel-danger">
				<button class="btn btn-danger btn-block" style="border-radius:0;" onclick="window.location.href='${ctx }/plans/ultra-fast-vdsl';">
					<p class="text-center" style="font-weight:bold;font-size:30px;margin-bottom:0;">
						Ultra-Fast-VDSL
					</p>
					<p class="text-center" style="font-weight:bold;font-size:30px;margin-bottom:0;">
						Plans
					</p>
					<p class="text-center" style="font-weight:bold;margin-bottom:0;">
						<span style="font-size:30px;">$</span>
						<span style="font-size:60px;" class="hidden-xs"> 
							99
						</span>
						<span style="font-size:35px;" class="hidden-lg hidden-md hidden-sm"> 
							99
						</span>
						/ Month
					</p>
				</button>
				<div class="panel-body" style="height:180px;">
					<p style="height:120px;">
						New generation technology on VDSL plan. Faster, stable and reliable VDSL can go through your current copper line. The speed of VDSL will be 3 times faster than ADSL broadband. Download speed achieve to 15Mbps to 35Mbps, and upload speed achieve to around 10Mbps.
					</p>
					<a href="${ctx }/plans/ultra-fast-vdsl" >Check out all our Ultra-Fast-VSDL Plans</a>
				</div>
			</div>
		</div>
	</div>
</div>
