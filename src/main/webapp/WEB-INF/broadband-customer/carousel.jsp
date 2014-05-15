<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<div id="myCarousel" class="carousel slide" data-ride="carousel">
	<ol class="carousel-indicators">
		<li data-target="#myCarousel" data-slide-to="0" class="active" ></li>
		<li data-target="#myCarousel" data-slide-to="1" ></li>
	</ol>
	<div class="carousel-inner">
		<div class="item active">
			<a href="${ctx }/plans/plan-term/business" target="_blank">
				<img src="${ctx }/public/bootstrap3/images/slider_biz.png" class="img-responsive" alt="ADSL is a fast broadband plan">
			</a>
		</div>
		<div class="item">
			<a href="${ctx }/plans/plan-term/personal" target="_blank">
				<img src="${ctx }/public/bootstrap3/images/slider_fiber.png"  class="img-responsive" alt="VDSL is faster than ADSL">
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
