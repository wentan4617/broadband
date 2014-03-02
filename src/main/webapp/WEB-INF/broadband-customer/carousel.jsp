<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>


<div id="myCarousel" class="carousel slide">
	<!-- Indicators -->
	<ol class="carousel-indicators">
		<li data-target="#myCarousel" data-slide-to="0" class="active" style="margin-right:4px;"></li>
		<li data-target="#myCarousel" data-slide-to="1" style="margin-right:4px;"></li>
	</ol>
	<div class="carousel-inner">
		<div class="item active">
			<a href="#">
				<img src="${ctx }/public/bootstrap3/images/slider_biz.png" class="img-responsive" alt="ADSL">
			</a>
			<div class="container">
				<div class="carousel-caption"></div>
			</div>
		</div>
		<div class="item">
			<a href="#">
				<img src="${ctx }/public/bootstrap3/images/slider_fiber.png"  class="img-responsive" alt="VDSL">
			</a>
			<div class="container">
				<div class="carousel-caption"></div>
			</div>
		</div>
	</div>
	<a class="left carousel-control" href="#myCarousel" data-slide="prev">
		<span class="glyphicon glyphicon-chevron-left"></span>
	</a>
	<a class="right carousel-control" href="#myCarousel" data-slide="next">
		<span class="glyphicon glyphicon-chevron-right"></span>
	</a>
</div>
