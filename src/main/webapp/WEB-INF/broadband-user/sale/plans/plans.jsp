<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>  
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<jsp:include page="../header.jsp" />

<style>
#navhead {
	margin-bottom:0;
}
.tab-content {
	border-top-color:transparent;
}
</style>

<div class="container">
	<div class="row">
		<div class="col-md-4">
			<a href="${ctx }/broadband-user/sale/plans/topup" class="btn btn-success btn-lg btn-block" style="font-size: 64px; padding: 60px;">
				Top Up
			</a>
		</div>
		<div class="col-md-4">
			<a href="${ctx }/broadband-user/sale/plans/personal" class="btn btn-danger btn-lg btn-block" style="font-size: 64px; padding: 60px;">
				Personal
			</a>
		</div>
		<div class="col-md-4">
			<a href="${ctx }/broadband-user/sale/plans/business" class="btn btn-info btn-lg btn-block" style="font-size: 64px; padding: 60px;">
				Business
			</a>
		</div>
	</div>
</div>

<div class="container" style="margin-top:15px;">
	<div class="row" style="margin-bottom:10px;">
		<div class="col-md-10 col-md-offset-1 btn-info" style="padding: 20px 20px 0 0;">
			<a href="${ctx }/broadband-user/sale/plans/promotion/ipadmini">
				<img class="img-responsive" src="${ctx }/public/bootstrap3/images/promotion1.png">
			</a>
		</div>
	</div>

	<div class="row" style="margin-bottom:10px;">
		<div class="col-md-10 col-md-offset-1 btn-warning" style="padding: 20px 20px 0 0;">
			<a href="${ctx }/broadband-user/sale/plans/promotion/hd">
				<img class="img-responsive" src="${ctx }/public/bootstrap3/images/promotion2.png" >
			</a>
		</div>
	</div>
</div>

<jsp:include page="../footer.jsp" />
<jsp:include page="../script.jsp" />
<script type="text/javascript">
(function($){
	
	$('button[data-type]').click(function(){
		var select_plan_id = $(this).attr('data-id');
		window.location.href = '${ctx}/broadband-user/sale/plans/address-check/${type_search}/${clasz}/' + select_plan_id;
	});

})(jQuery);
</script>
<jsp:include page="../../footer-end.jsp" />