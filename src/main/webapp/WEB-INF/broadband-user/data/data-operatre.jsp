<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<jsp:include page="../header.jsp" />
<jsp:include page="../alert.jsp" />

<div class="container">
	<div class="row">
		<div class="col-md-12">
			<div class="panel panel-success">
				<div class="panel-heading">
					<h4 class="panel-title">${panelheading }</h4>
				</div>
				<div class="panel-body">
					<div class="row">
						<div class="col-md-3">
							<div class="input-group date">
						  		<input type="text" id="calculator_date" name="calculator_date" class="form-control" data-error-field />
						  		<span class="input-group-addon">
						  			<i class="glyphicon glyphicon-calendar"></i>
						  		</span>
							</div>
						</div>
						<c:if test="${userSession.user_role=='system-developer'}">
						<div class="col-md-3">
							<a class="btn btn-success" id="calculator">Calculator All Vlans Data Flow Everyday</a>
						</div>
						</c:if>
					</div>
				</div>
			</div>
		</div>
		
	</div>
</div>

<jsp:include page="../footer.jsp" />
<jsp:include page="../script.jsp" />
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/bootstrap-datepicker.js"></script>
<script type="text/javascript">
(function($){
	
	$('.input-group.date').datepicker({
	    format: "yyyy-mm", 
	    minViewMode: 1
	});
	
	$('#calculator').click(function(){
		var date = $('#calculator_date').val();
		if (date == '') {
			alert('Please enter one date.');
			return;
		}
		var $btn = $(this);
		$btn.button('loading');
		$.get('${ctx}/broadband-user/data/calculator-usage/' + date, function(json){
			$.jsonValidation(json);
		}).always(function () {
			$btn.button('reset');
	    });
	});
	
})(jQuery);
</script>
<jsp:include page="../footer-end.jsp" />