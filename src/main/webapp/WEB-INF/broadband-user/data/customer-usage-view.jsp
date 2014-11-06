<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<jsp:include page="../header.jsp" />
<jsp:include page="../alert.jsp" />

<style>
#dataCustomer_table td, 
#dataCustomer_table th {
	padding: 4px;
}
</style>

<div class="container">
	<div class="row">
		<div class="col-md-12">
			<div class="panel panel-success">
				<div class="panel-heading">
					<h1 class="panel-title">Data Usage</h1>
				</div>
				<div class="panel-body">
					<div class="row">
						<div class="col-md-6">
							<strong>Broadband User:</strong> 
							<c:if test="${co.customer_type == 'personal' }">
								${co.first_name } ${co.last_name }
							</c:if>
							<c:if test="${co.customer_type == 'business' }">
								${co.org_name }
							</c:if>
						</div>
						<div class="col-md-3 col-md-offset-3">
							<div class="btn-group">
								<button type="button" class="btn btn-default active" data-btn="view-btn" data-type="table">
									<span class="glyphicon glyphicon-th"></span> Table
								</button>
							  	<button type="button" class="btn btn-default" data-btn="view-btn" data-type="line">
							  		<span class="glyphicon glyphicon-stats"></span> Line
							  	</button>
							  	<c:if test="${co.cod.detail_data_flow > 0}">
								  	<button type="button" class="btn btn-default" data-btn="view-btn" data-type="pie">
								  		<span class="glyphicon glyphicon-certificate"></span> Pie
								  	</button>
							  	</c:if>
							</div>
						</div>
					</div>
					<hr />
					<div class="row">
						<div class="col-md-4">
							<strong>Plan:</strong> ${co.cod.detail_name }
						</div>
						<div class="col-md-4">
							<strong>Data Quotation:</strong> ${co.cod.detail_data_flow > 0 ? co.cod.detail_data_flow : 'Unlimited'}
						</div>
					</div>
					<hr />
					<div class="row">
						<div class="col-md-2">
						    <strong>Current Date:</strong> 
						</div>
						<div class="col-md-2">
							<div class="input-group date">
						  		<input type="text" id="calculator_date" name="calculator_date" class="form-control" data-error-field />
						  		<span class="input-group-addon">
						  			<i class="glyphicon glyphicon-calendar"></i>
						  		</span>
							</div>
						</div>
					</div>
					<hr />
					<div class="progress" style="height: 40px;">
						<div id="usageProgress" class="progress-bar" style="padding: 10px">
							<span id="usedPart" style="font-size: 18px"></span>
					  	</div>
					  	<span id="unusedPart"></span>
					</div>
					<div id="usageView"></div>
				</div>
				<div class="panel-body">
					<div id="usage_table"></div>
					<div id="usage_line" style="display:none;"></div>
					<c:if test="${co.cod.detail_data_flow > 0}">
						<div id="chart_pie" style="height:300px;" ></div>
					</c:if>
				</div>
			</div>
			
		</div>
	</div>
</div>

<script type="text/html" id="customer_usage_view_tmpl">
<jsp:include page="customer-usage-view.html" />
</script>

<jsp:include page="../footer.jsp" />
<jsp:include page="../script.jsp" />
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/Chart.min.js"></script>
<script type="text/javascript">
(function($){
	
	$('.input-group.date').datepicker({
	    format: "yyyy-mm", 
	    minViewMode: 1,
	    autoclose: true
	}).datepicker('setDate', new Date()).on('changeDate', function(e){ //console.log(e.format());
		doUsage(e.format());
	});
	
	var svlan = '${co.svlan}';
	var cvlan = '${co.cvlan}';
	var type = '${co.cod.detail_plan_type}';
	
	function doUsage(date) {
		var url = '${ctx}/broadband-user/data/orders/usage/view/' + svlan + '/' + cvlan + '/' + type + '/' + date;
		$.get(url, function(list){ //console.log(list);
			//dateUsages = list;
			var obj = {
				list: list
				, ctx: '${ctx}'
			}
	   		var $table = $('#usage_table');
			$table.html(tmpl('customer_usage_view_tmpl', obj));
			
			var curMonthTotal = Number($('#curMonthTotal').val());
			var planUsage = Number(${co.cod.detail_data_flow > 0 ? co.cod.detail_data_flow : 999});
			var usageWidth = Number(curMonthTotal/planUsage);
			
			var widthVal = 0;
			if (usageWidth*100 > 100) {
				widthVal = 100;
			} else {
				widthVal = usageWidth*100;
			}
			
			$('#usageProgress').attr('style', 'width:' + widthVal + '%; padding: 10px 0');
			
			//console.log('widthVal: ' + widthVal);
			//console.log('usageWidth: ' + usageWidth);
			
			if (widthVal < 50) { console.log('progress-bar-success');
				$('#usageProgress').addClass('progress-bar-success');
			} else if (widthVal < 80) { console.log('progress-bar-warning');
				$('#usageProgress').addClass('progress-bar-warning');
			} else { console.log('progress-bar-danger');
				$('#usageProgress').addClass('progress-bar-danger');
			}
			$('#usedPart').text((usageWidth*100).toFixed(3) + '%');
			
			var labelArray = [];
			var dataArray = [];
			var maxData = 0;
			var tempData;
			
			for (var i = 0; i < list.length; i++) {
				var dateUsage = list[i];
				labelArray.push(dateUsage.date);
				if (dateUsage.usage != null) {
					var upload = dateUsage.usage.upload/1024/1024/1024;
					var download = dateUsage.usage.download/1024/1024/1024;
					var curDayTotal = upload + download;
					tempData = Number(curDayTotal);
					maxData = tempData > maxData ? tempData : maxData;
					dataArray.push(Number(curDayTotal).toFixed(3));
				} else {
					dataArray.push(0);
				}
			}
			
			var lineChartData = {
				labels: labelArray,
				datasets: [ {
					fillColor : "rgba(151,187,205,0.2)",
					strokeColor : "rgba(151,187,205,1)",
					pointColor : "rgba(151,187,205,1)",
					pointStrokeColor : "#fff",
					data : dataArray
				} ]
			};
				
			var lineChartOptions = {
				scaleOverride: true // 如果我们想要一个硬编码的规模与覆盖, false
				, scaleSteps: 10 // 纵轴步数, null
				, scaleStepWidth: (maxData/10).toFixed(3) // 纵轴数字间隔, null
				, scaleStartValue: 0 // 纵轴起始值, null
			};
			
			$('#usage_line').empty();
			var $canvas = $('<canvas id="canvas" height="450" width="1100"></canvas>');
			$canvas.appendTo('#usage_line');
			new Chart($canvas.get(0).getContext("2d")).Line(lineChartData, lineChartOptions);
			
			$('#chart_pie').hide();
	   	});
	}
	doUsage('${current_date}'); //${current_date}
	
	$('button[data-btn="view-btn"]').click(function(){
		$('button[data-btn="view-btn"]').removeClass('active');
		$(this).addClass('active');
		
		var type = $(this).attr('data-type');
		if (type == 'table') {
			$('#usage_table').show();
			$('#usage_line').hide();
			$('#chart_pie').hide();
		} else if (type == 'line') {
			$('#usage_table').hide();
			$('#usage_line').show();
			$('#chart_pie').hide();
		} else if (type == 'pie') {
			$('#usage_table').hide();
			$('#usage_line').hide();
			$('#chart_pie').show();
		}
	});
	
})(jQuery);
</script>
<jsp:include page="../footer-end.jsp" />