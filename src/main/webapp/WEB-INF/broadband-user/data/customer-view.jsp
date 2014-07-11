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
						<div class="col-md-2">
							<div class="input-group date">
						  		<input type="text" id="calculator_date" name="calculator_date" class="form-control" data-error-field />
						  		<span class="input-group-addon">
						  			<i class="glyphicon glyphicon-calendar"></i>
						  		</span>
							</div>
						</div>
						<div class="col-md-10">
							<div class="btn-group">
							
								<!-- In Service -->
								<a href="javascript:void(0);" data-group="in-service" data-sub="in-service" data-name="queryBtn" data-status="using" class="btn btn-default active">
									In Service <span class="badge"></span>
								</a>
					
								<!-- Suspension -->
								<div class="btn-group">
									<a href="javascript:void(0);" data-group="suspension" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
										Suspension
										<span class="badge"></span>
										<span class="caret"></span>
									</a>
									<ul class="dropdown-menu">
							      		<li>
											<a href="javascript:void(0);" data-name="queryBtn" data-sub="suspension" data-status="overflow">
												Over Flow <span class="badge"></span>
											</a>
										</li>
								      	<li>
											<a href="javascript:void(0);" data-name="queryBtn" data-sub="suspension" data-status="suspended">
												Suspended <span class="badge"></span>
											</a>
										</li>
								    </ul>
								</div>
								
								<!-- Disconnect -->
								<div class="btn-group">
									<a href="javascript:void(0);" data-group="disconnect" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
										Disconnect
										<span class="badge"></span>
										<span class="caret"></span>
									</a>
									<ul class="dropdown-menu">
							      		<li>
											<a href="javascript:void(0);" data-name="queryBtn" data-sub="disconnect" data-status="waiting-for-disconnect">
												Waiting For Disconnect <span class="badge"></span>
											</a>
										</li>
								      	<li>
											<a href="javascript:void(0);" data-name="queryBtn" data-sub="disconnect" data-status="disconnected">
												Disconnected <span class="badge"></span>
											</a>
										</li>
								    </ul>
								</div>
								
								<!-- All -->
								<a href="javascript:void(0);" data-name="queryBtn" data-group="all" data-sub="all" data-status="all" class="btn btn-default">
									All <span class="badge"></span>
								</a>
							
							</div>
						</div>
					</div> 
				</div>
				
				<hr>
				
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
	
	var g_status = 'using';
	
	$('.input-group.date').datepicker({
	    format: "yyyy-mm", 
	    minViewMode: 1,
	    autoclose: true
	}).datepicker('setDate', new Date()).on('changeDate', function(e){ //console.log(e.format());
		doPage(1, e.format(), g_status);
	});
	
	$('a[data-name="queryBtn"]').click(function(){
		g_status = $(this).attr('data-status');
		doPage(1, $('#calculator_date').val(), g_status);
		$('a[data-group]').removeClass('active');
		var sub = $(this).attr('data-sub');
		$('a[data-group="' + sub + '"]').addClass('active');
	});
	
	function doPage(pageNo, date, status) { //console.log(date);
		$.get('${ctx}/broadband-user/data/customer/view/' + pageNo + "/" + date + "/" + status, function(page){ //console.log(page);
			page.ctx = '${ctx}';
	   		var $table = $('#dataCustomer-table');
			$table.html(tmpl('dataCustomer_table_tmpl', page));
			$table.find('tfoot a').click(function(){
				doPage($(this).attr('data-pageNo'), $('#calculator_date').val(), g_status);
			});
	   	});
	}
	doPage(1, '${current_date}', g_status);
	
})(jQuery);
</script>
<jsp:include page="../footer-end.jsp" />