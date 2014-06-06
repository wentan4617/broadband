<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<div id="cyberparkCarousel" class="carousel slide" data-ride="carousel">
	<ol class="carousel-indicators">
		<li data-target="#cyberparkCarousel" data-slide-to="0" class="active" ></li>
		<li data-target="#cyberparkCarousel" data-slide-to="1" ></li>
		<li data-target="#cyberparkCarousel" data-slide-to="2" ></li>
		<li data-target="#cyberparkCarousel" data-slide-to="3" ></li>
	</ol>
	<div class="carousel-inner">
		<div class="item active" >
			<a href="${ctx }/plans/plan-topup/personal" target="_blank">
				<img src="${ctx }/public/bootstrap3/images/topup-plan.jpg" class="img-responsive" alt="Broadband Top-Up" >
			</a>
		</div>
		<div class="item" >
			<a href="${ctx }/plans/plan-term/personal" target="_blank">
				<img src="${ctx }/public/bootstrap3/images/persoanl-12months-unlimited-plan.jpg"  class="img-responsive" alt="persoanl 12months unlimited plan" >
			</a>
		</div>
		<div class="item" >
			<a href="${ctx }/plans/plan-term/business/adsl/promotion" target="_blank">
				<img src="${ctx }/public/bootstrap3/images/slider_biz.jpg" class="img-responsive" alt="ADSL is a fast broadband plan">
			</a>
		</div>
		<div class="item" >
			<a href="${ctx }/plans/plan-term/personal/ufb/promotion" target="_blank">
				<img src="${ctx }/public/bootstrap3/images/slider_fiber.jpg"  class="img-responsive" alt="VDSL is faster than ADSL">
			</a>
		</div>
		
	</div>
	<a class="left carousel-control" href="#cyberparkCarousel" data-slide="prev">
		<span class="glyphicon glyphicon-chevron-left"></span>
	</a>
	<a class="right carousel-control" href="#cyberparkCarousel" data-slide="next">
		<span class="glyphicon glyphicon-chevron-right"></span>
	</a>
</div>
