<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>  
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<jsp:include page="../header.jsp" />

<style>
.tab-content {
	border-top-color:transparent;
}
</style>
<style>
#navhead {
	margin-bottom:0;
}
</style>

<div class="container">
	<div class="row">
		<div class="col-md-5 col-md-offset-1">
			<a href="${ctx }/broadband-user/sale/plans/address-check/ADSL/personal/0" class="btn btn-success btn-lg btn-block" style="font-size: 64px; padding: 60px;">
				Personal
			</a>
		</div>
		<div class="col-md-5">
			<a href="${ctx }/broadband-user/sale/plans/address-check/ADSL/business/0" class="btn btn-info btn-lg btn-block" style="font-size: 64px; padding: 60px;">
				Business
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