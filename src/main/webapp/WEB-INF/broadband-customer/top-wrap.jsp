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
						Phone & 
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
						Brilliant value broadband, offering great speeds via ADSL technology, and available with a range of data caps. In a nutshell, it's great value, solid as broadband, backed with great service from our New Zealand-based call centre.
					</p>
					<a href="${ctx }/plans/broadband" >Check out all our Broadband Plans</a>
				</div>
			</div>
		</div>
		
		<div class="col-md-4">
			<div class="panel panel-info">
				<button class="btn btn-info btn-block" style="border-radius:0;" onclick="window.location.href='${ctx }/plans/broadband';">
					<p class="text-center" style="font-weight:bold;font-size:30px;margin-bottom:0;">
						Naked Broadband
					</p>
					<p class="text-center" style="font-weight:bold;font-size:30px;margin-bottom:0;">
						Unlimited
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
						No need for a home phone? But after awesome broadband? It's time to get naked. You get full speed, affordable internet without having to pay for a homeline you don't need.
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
						Get next generation Ultra Fast broadband at your place. Faster download speeds mean snappier browsing, awesome HD video streaming, and a better online experience. It's the future!
					</p>
					<a href="${ctx }/plans/ultra-fast-vdsl" >Check out all our Ultra-Fast-VSDL Plans</a>
				</div>
			</div>
		</div>
	</div>
</div>
