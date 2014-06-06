<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<jsp:include page="../header.jsp" />
<jsp:include page="../alert.jsp" />

<style>
#dataCustomer_table td, 
#dataCustomer_table th {
	padding: 8px;
}
</style>

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
					</div> 
				</div>
				<div id="dataCustomer-table"></div>
				
			</div>
		</div>
	</div>
</div>

<script type="text/html" id="dataCustomer_table_tmpl">
<jsp:include page="customer-view-page.html" />
</script>

<jsp:include page="../footer.jsp" />
<jsp:include page="../script.jsp" />
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/bootstrap-datepicker.js"></script>
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/bootstrap-select.min.js"></script>
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/jTmpl.js"></script>
<script type="text/javascript">
(function($){
	
	$('.input-group.date').datepicker({
	    format: "yyyy-mm", 
	    minViewMode: 1,
	    autoclose: true
	}).datepicker('setDate', new Date()).on('changeDate', function(e){ //console.log(e.format());
		doPage(1, e.format());
	});
	
	function doPage(pageNo, date) { console.log(date);
		$.get('${ctx}/broadband-user/data/customer/view/' + pageNo + "/" + date, function(page){ console.log(page);
			page.ctx = '${ctx}';
	   		var $table = $('#dataCustomer-table');
			$table.html(tmpl('dataCustomer_table_tmpl', page));
			$table.find('tfoot a').click(function(){
				doPage($(this).attr('data-pageNo'), $('#calculator_date').val());
			});
			//$('a[data-toggle="tooltip"]').tooltip();
	   	});
	}
	doPage(1, '${current_date}');
	
})(jQuery);
</script>
<jsp:include page="../footer-end.jsp" />