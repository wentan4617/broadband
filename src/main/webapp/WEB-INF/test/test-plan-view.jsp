<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<jsp:include page="../broadband-user/header.jsp" />
<jsp:include page="../broadband-user/alert.jsp" />

<div class="container">
	<div class="row">
		<div class="col-md-12">
			<div class="panel panel-default">
				<div class="panel-heading">Test Plan View Page</div>
				<!-- page table for ajax -->
				<table class="table" id="plan-table"></table>
			</div>
		</div>
	</div>
</div>
<jsp:include page="../broadband-user/footer.jsp" />
<jsp:include page="../broadband-user/script.jsp" />
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/jTmpl.js"></script>
<script type="text/html" id="plan_table">
<jsp:include page="plan-view-page.html" />
</script>
<script type="text/javascript">
(function($){
	function doPage(pageNo) {
		$.get('${ctx}/test/plan/view/page/' + pageNo, function(page){
	   		var $table = $('#plan-table');
			$table.html(tmpl('plan_table', page));
			$table.find('tfoot a').click(function(){
				doPage($(this).attr('data-pageNo'));
			});
	   	});
	}
	doPage(1);
})(jQuery);
</script>
<jsp:include page="../broadband-user/footer-end.jsp" />