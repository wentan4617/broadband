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
					<h3>Monthly Transaction Statistics - Total Monthly Income:&nbsp;<strong><span id="total_monthly_income" class="text-success"></span></strong></h3>
						<div class="input-group date col-md-2">
							<input id="year_month_input"
								class="form-control" placeholder="Start Date" value="${yearMonth}"/>
								<span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
						</div>
					<hr/>
					<canvas id="canvasMonth" height="350" width="1100"></canvas>
					<hr/>
					<h3>Weekly Transaction Statistics - Total Weekly Income:&nbsp;<strong><span id="total_weekly_income" class="text-success"></span></strong></h3>
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
	
	<c:forEach var="weekPaidStatistic" items="${weekPaidStatistics}">
		weekLabelArray.push('${weekPaidStatistic.billingWeekDate_str}');
		weekDataArray.push(${weekPaidStatistic.billingAmount});
		var weekTempNum = ${weekPaidStatistic.billingAmount};
		weekMaxNum = weekTempNum > weekMaxNum ? weekTempNum : weekMaxNum;
	</c:forEach>
	
	if(weekDataArray.length > 0){
		var totalWeekIncome = 0;
		for(var i=0; i<weekDataArray.length; i++){
			totalWeekIncome += weekDataArray[i];
		}
		$('#total_weekly_income').html('( '+new Number(totalWeekIncome).toFixed(2)+' )');
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
	
	<c:forEach var="monthBillingTransaction" items="${monthBillingTransactions}">
		monthLabelArray.push('${monthBillingTransaction.billingMonthDate_str}'.substring(8));
		monthDataArray.push(${monthBillingTransaction.billingAmount});
		var monthTempNum = ${monthBillingTransaction.billingAmount};
		monthMaxNum = monthTempNum > monthMaxNum ? monthTempNum : monthMaxNum;
	</c:forEach>
	
	if(monthDataArray.length > 0){
		var totalMonthIncome = 0;
		for(var i=0; i<monthDataArray.length; i++){
			totalMonthIncome += monthDataArray[i];
		}
		$('#total_monthly_income').html('( '+new Number(totalMonthIncome).toFixed(2)+' )');
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
