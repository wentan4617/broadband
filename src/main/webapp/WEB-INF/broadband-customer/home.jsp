<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<jsp:include page="header.jsp" />

<style>
#imgContainer .thumbnail {
	margin-bottom: 0;
}
#imgContainer img {
	height:70px;
}
#imgContainer hr {
	margin: 5px 0;
}
#navhead {
	margin-bottom:0;
}
.home-title {
	font-size:36px;
	margin-top: 20px;
	margin-bottom: 10px;
}
.slider-img img{
	margin:0 auto;
}
</style>

<jsp:include page="carousel.jsp" />

<div class="homebg">
	<div class="container marketing" >
		
		<%-- <div class="row" style="margin-top:20px;">
			<div class="col-lg-9">
				<jsp:include page="carousel.jsp" />
			</div>
			<div class="col-lg-3">
				<div id="imgContainer" class="panel-body" style="height:377px;">
		  			<a href="${ctx }/plans/plan-term/personal" class="thumbnail" target="_blank" rel="nofollow" > 
		  				<img src="${ctx }/public/bootstrap3/images/personal-plan.png" alt="Personal Plan provides fast broadband for home users">
			    	</a>
		  			<a href="${ctx }/plans/plan-term/business" class="thumbnail" target="_blank" rel="nofollow" style="margin-top:10px;"> 
		  				<img src="${ctx }/public/bootstrap3/images/business-plan-icon.png" alt="Business Plan provides faster broadband for commercial users">
		  			</a>
		  			<a href="${ctx }/wifi-solution" class="thumbnail" target="_blank" rel="nofollow" style="margin-top:10px;"> 
		  				<img src="${ctx }/public/bootstrap3/images/wifi-solution.png" alt="Wifi Solution is for huge enterprise users">
		  			</a>
		  			<a href="${ctx }/e-commerce" class="thumbnail" target="_blank" rel="nofollow" style="margin-top:10px;"> 
		  				<img src="${ctx }/public/bootstrap3/images/e-commerce.png" alt="It Service provides E-Commerce Solutions for Growing Enterprise">	
		  			</a>
			    	
			  	</div>
			</div>
		</div> --%>
		
		<div class="row" style="margin-top:20px;">
			<div class="col-lg-2"></div>
			<div class="col-lg-2">
				<a href="${ctx }/plans/plan-no-term/personal" target="_blank">
					<img src="${ctx }/public/bootstrap3/images/c_noterm.jpg" alt="personal broadbands without terms and conditions" class="img-circle">
				</a>
			</div>
			<div class="col-lg-1"></div>
			<div class="col-lg-2">
				<a href="${ctx }/plans/plan-topup/personal" target="_blank">
					<img src="${ctx }/public/bootstrap3/images/c_topup.jpg" alt="new broadband concept, topup your broadband's data, realization of buy less use more" class="img-circle">
				</a>
			</div>
			<div class="col-lg-1"></div>
			<div class="col-lg-2">
				<a href="javascript:void(0);" id="plan-term-option">
					<img src="${ctx }/public/bootstrap3/images/c_term.jpg" alt="promotions for broadbands have agreement" class="img-circle">
				</a>
			</div>
			<div class="col-lg-2"></div>
		</div>
	</div>
</div>

<jsp:include page="welcome.jsp"/>


<div id="map_canvas" style="width:720px;height:600px;display:none;"></div>

 
<div id="popContainer" style="display:none;">
	<a href="${ctx }/plans/plan-term/personal" class="btn btn-success btn-block" target="_blank">
		<span class="glyphicon glyphicon-hand-up"></span> Personal Plan
	</a>
	<hr style="margin: 10px 0;"/>
	<a href="${ctx }/plans/plan-term/business" class="btn btn-success btn-block" target="_blank">
		<span class="glyphicon glyphicon-hand-up"></span> Business Plan
	</a>
</div>

<jsp:include page="footer.jsp" />
<jsp:include page="script.jsp" />
<script type="text/javascript">
(function($){
	
	var opt = {
		html: true
		, trigger: 'click'
		, placement: 'right'
		, content: $('#popContainer').html()
		, container: 'body'
	};
	
	$('#plan-term-option').popover(opt);
	
})(jQuery);
</script>
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/holder.js"></script>
<%-- <script src="https://maps.google.com/maps/api/js?sensor=false&libraries=places&region=NZ" type="text/javascript"></script>
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/autoCompleteAddress.js"></script> --%>
<jsp:include page="footer-end.jsp" />