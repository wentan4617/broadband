<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<jsp:include page="../header.jsp" />
<jsp:include page="../alert.jsp" />

<div class="container">
	<div class="row">
		<div class="col-md-12">
			<div class="panel panel-default">
				<div class="panel-heading"><h4 class="panel-title">${panelheading}</h4></div>
				<div class="panel-body">
					<h3>Monthly Commission Statistics - Total:&nbsp;<strong><span id="total_monthly_commission" class="text-success"></span></strong></h3>
						<div class="input-group date col-md-2">
							<input id="year_month_input"
								class="form-control" placeholder="Start Date" value="${yearMonth}"/>
								<span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
						</div>
					<hr/>
					<canvas id="canvasMonth" height="350" width="1100"></canvas>
					<hr/>
					<h3>Weekly Commission Statistics - Total:&nbsp;<strong><span id="total_weekly_commission" class="text-success"></span></strong></h3>
					<hr/>
					<canvas id="canvasWeek" height="350" width="1100"></canvas>
				</div>
			</div>
		</div>
	</div>
</div>
<jsp:include page="../footer.jsp" />
<jsp:include page="../script.jsp" />
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/Chart.min.js"></script>
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/bootstrap-datepicker.js"></script>
<script>

	

(function($){
	
	var weekDataArray = []
	, weekLabelArray = []
	, weekMaxNum = 0;
	
	<c:forEach var="weekCommissionStatistic" items="${weekCommissionStatistics}">
		weekLabelArray.push('${weekCommissionStatistic.billingWeekDate_str}');
		weekDataArray.push(${weekCommissionStatistic.billingAmount});
		var weekTempNum = ${weekCommissionStatistic.billingAmount};
		weekMaxNum = weekTempNum > weekMaxNum ? weekTempNum : weekMaxNum;
	</c:forEach>
	
	if(weekDataArray.length > 0){
		var totalWeekCommission = 0;
		for(var i=0; i<weekDataArray.length; i++){
			totalWeekCommission += weekDataArray[i];
		}
		$('#total_weekly_commission').html('( '+new Number(totalWeekCommission).toFixed(2)+' )');
	}
	
	var weekLineChartData = {
		labels : weekLabelArray,
		datasets : [{
				fillColor : "rgba(151,187,205,0.5)",
				strokeColor : "rgba(151,187,205,1)",
				pointColor : "rgba(151,187,205,1)",
				pointStrokeColor : "#fff",
				data : weekDataArray}]};
	
	var monthDataArray = []
	, monthLabelArray = []
	, monthMaxNum = 0;
	
	<c:forEach var="monthBillingCommission" items="${monthBillingCommissions}">
		monthLabelArray.push('${monthBillingCommission.billingMonthDate_str}'.substring(8));
		monthDataArray.push(${monthBillingCommission.billingAmount});
		var monthTempNum = ${monthBillingCommission.billingAmount};
		monthMaxNum = monthTempNum > monthMaxNum ? monthTempNum : monthMaxNum;
	</c:forEach>
	
	if(monthDataArray.length > 0){
		var totalMonthCommission = 0;
		for(var i=0; i<monthDataArray.length; i++){
			totalMonthCommission += monthDataArray[i];
		}
		$('#total_monthly_commission').html('( '+new Number(totalMonthCommission).toFixed(2)+' )');
	}
	
	var monthLineChartData = {
		labels : monthLabelArray,
		datasets : [{
				fillColor : "rgba(151,187,205,0.5)",
				strokeColor : "rgba(151,187,205,1)",
				pointColor : "rgba(151,187,205,1)",
				pointStrokeColor : "#fff",
				data : monthDataArray}]};
	
	new Chart(document.getElementById("canvasWeek").getContext("2d")).Line(weekLineChartData);
	
	new Chart(document.getElementById("canvasMonth").getContext("2d")).Line(monthLineChartData);
	
	$('.input-group.date').datepicker({
	    format: "yyyy-mm",
	    autoclose: true,
	    minViewMode: 1
	});
	
	$('.input-group.date').datepicker()
	.on('changeDate', function(ev){
		var value = $('#year_month_input').val();
		window.location.href = value;
	});
	
})(jQuery);
</script>
<jsp:include page="../footer-end.jsp" />
