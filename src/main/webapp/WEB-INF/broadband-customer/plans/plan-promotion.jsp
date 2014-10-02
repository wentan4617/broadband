<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>  
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<jsp:include page="../header.jsp" />

<style>
#navhead {
	margin-bottom:0;
}
</style>

<div class="bs-docs-header cyberpark-home-bg" style="padding-bottom: 0;">
	<div class="container">
		<div class="row">
			<div class="col-md-12">
				<img class="img-responsive" src="${ctx }/public/bootstrap3/images/promotion-gift.png" style="padding:0 0 10px 0;">
			</div>
		</div>
	</div>
</div>

<div class="container" style="margin-top:15px;">
	<div class="row" style="margin-bottom:10px;">
		<div class="col-md-10 col-md-offset-1 btn-info" style="padding: 20px 20px 0 0;">
			<a href="${ctx }/plans/promotion/en">
				<img class="img-responsive" src="${ctx }/public/bootstrap3/images/promotion1.png">
			</a>
		</div>
	</div>

	<div class="row" style="margin-bottom:10px;">
		<div class="col-md-10 col-md-offset-1 btn-warning" style="padding: 20px 20px 0 0;">
			<a href="${ctx }/plans/promotion/hd/en">
				<img class="img-responsive" src="${ctx }/public/bootstrap3/images/promotion2.png" >
			</a>
		</div>
	</div>
</div>

<jsp:include page="../footer.jsp" />
<jsp:include page="../script.jsp" />
<script type="text/javascript">
(function($){
	
})(jQuery);
</script>

<jsp:include page="../footer-end.jsp" />