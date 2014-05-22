<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>


<div id="myCarousel" class="carousel slide" data-ride="carousel">
	<ol class="carousel-indicators">
		<li data-target="#myCarousel" data-slide-to="0" class="active" ></li>
		<li data-target="#myCarousel" data-slide-to="1" ></li>
		<li data-target="#myCarousel" data-slide-to="2" ></li>
		<li data-target="#myCarousel" data-slide-to="3" ></li>
	</ol>
	<div class="carousel-inner slider-img">
		<div class="item active" style="background-color:#05211a;"><!-- style="width:900px;height:380px;" -->
			<a href="${ctx }/plans/plan-term/business/adsl/promotion" target="_blank">
				<img src="${ctx }/public/bootstrap3/images/slider_biz.png" class="img-responsive" alt="ADSL is a fast broadband plan">
			</a>
		</div>
		<div class="item" style="background-color:#030205;">
			<a href="${ctx }/plans/plan-term/personal/ufb/promotion" target="_blank"><!-- slider_fiber -->
				<img src="${ctx }/public/bootstrap3/images/slider_fiber.png"  class="img-responsive" alt="VDSL is faster than ADSL">
			</a>
		</div>
		<div class="item" style="background-color:#004739;">
			<a href="${ctx }/plans/plan-topup/personal" target="_blank">
				<img src="${ctx }/public/bootstrap3/images/topup-plan.png"  class="img-responsive" alt="Broadband Top-Up" >
			</a>
		</div>
		<div class="item" style="background-color:#002927;">
			<a href="${ctx }/plans/plan-term/personal" target="_blank">
				<img src="${ctx }/public/bootstrap3/images/persoanl-12months-unlimited-plan.png"  class="img-responsive" alt="persoanl 12months unlimited plan" >
			</a>
		</div>
	</div>
	<a class="left carousel-control" href="#myCarousel" data-slide="prev">
		<span class="glyphicon glyphicon-chevron-left"></span>
	</a>
	<a class="right carousel-control" href="#myCarousel" data-slide="next">
		<span class="glyphicon glyphicon-chevron-right"></span>
	</a>
	
</div>
