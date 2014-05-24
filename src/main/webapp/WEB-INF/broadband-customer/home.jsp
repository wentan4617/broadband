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

<jsp:include page="welcome.jsp"/>

<div id="map_canvas" style="width:720px;height:600px;display:none;"></div>

<jsp:include page="footer.jsp" />
<jsp:include page="script.jsp" />
<script type="text/javascript">
(function($){
	
})(jQuery);
</script>
<%-- <script src="https://maps.google.com/maps/api/js?sensor=false&libraries=places&region=NZ" type="text/javascript"></script>
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/autoCompleteAddress.js"></script> --%>
<jsp:include page="footer-end.jsp" />