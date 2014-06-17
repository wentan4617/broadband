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
					<h3>Monthly Statistic</h3>
						<div class="input-group date col-md-2">
							<input id="year_month_input"
								class="form-control" placeholder="Start Date" value="${yearMonth}"/>
								<span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
						</div>
					<hr/>
					<canvas id="canvasMonth" height="450" width="1100"></canvas>
					<hr/>
					<h3>Weekly Statistic</h3>
					<hr/>
					<canvas id="canvasWeek" height="450" width="1100"></canvas>
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
	
	var weekOptions = {
		scaleOverride : true
		//** 如果为硬编码模式 **
		//Number - 硬编码模式下每一步的跨度
		,scaleSteps : 6
		//Number - 硬编码模式下的步数
		,scaleStepWidth : 2
		//Number - 起始值
		,scaleStartValue : 0
		//Number - XY轴线宽度
		,scaleLineWidth : 3
		//Number - 网格线宽度
		,scaleGridLineWidth : 2
	}
	
	var monthOptions = {
		scaleOverride : true
		//** 如果为硬编码模式 **
		//Number - 硬编码模式下每一步的跨度
		,scaleSteps : 6
		//Number - 硬编码模式下的步数
		,scaleStepWidth : 2
		//Number - 起始值
		,scaleStartValue : 0
		//Number - XY轴线宽度
		,scaleLineWidth : 3
		//Number - 网格线宽度
		,scaleGridLineWidth : 2
	}
	
	var weekDataArray = []
	, weekLabelArray = []
	, weekMaxNum = 0;
	
	<c:forEach var="weekRegisterStatistic" items="${weekRegisterStatistics}">
		weekLabelArray.push('${weekRegisterStatistic.registerWeekDate_str}');
		weekDataArray.push(${weekRegisterStatistic.registerCount});
		var weekTempNum = ${weekRegisterStatistic.registerCount};
		weekMaxNum = weekTempNum > weekMaxNum ? weekTempNum : weekMaxNum;
	</c:forEach>
	
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
	
	<c:forEach var="monthRegisterStatistic" items="${monthRegisterStatistics}">
		monthLabelArray.push('${monthRegisterStatistic.registerMonthDate_str}'.substring(8));
		monthDataArray.push(${monthRegisterStatistic.registerCount});
		var monthTempNum = ${monthRegisterStatistic.registerCount};
		monthMaxNum = monthTempNum > monthMaxNum ? monthTempNum : monthMaxNum;
	</c:forEach>
	
	var monthLineChartData = {
		labels : monthLabelArray,
		datasets : [{
				fillColor : "rgba(151,187,205,0.5)",
				strokeColor : "rgba(151,187,205,1)",
				pointColor : "rgba(151,187,205,1)",
				pointStrokeColor : "#fff",
				data : monthDataArray}]};
	
	new Chart(document.getElementById("canvasWeek").getContext("2d")).Line(weekLineChartData, weekOptions);
	
	new Chart(document.getElementById("canvasMonth").getContext("2d")).Line(monthLineChartData, monthOptions);
	
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
