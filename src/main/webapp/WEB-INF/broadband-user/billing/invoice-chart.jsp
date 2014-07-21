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
					<h3>Monthly Invoice Statistics - Total Monthly Statistics:&nbsp;</h3><br/>
						<strong>PAID: <span id="total_monthly_paid" class="text-success"></span></strong><br/>
						<strong>CREDIT: <span id="total_monthly_credit" class="text-success"></span></strong><br/>
						<strong>UNPAID: <span id="total_monthly_unpaid" class="text-danger"></span></strong><br/>
						<strong>VOID BALANCE: <span id="total_monthly_void_balance" class="text-danger"></span></strong>
						<hr/>
						<div class="input-group date col-md-2">
							<input id="year_month_input"
								class="form-control" placeholder="Start Date" value="${yearMonth}"/>
								<span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
						</div>
					<hr/>
					<canvas id="canvasMonth" height="800" width="1100"></canvas>
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
	
	var monthOptions = {
		    //Boolean - Show a backdrop to the scale label
		    scaleShowLabelBackdrop : true,
		    //String - The colour of the label backdrop
		    scaleBackdropColor : "rgba(255,255,255,0.75)",
		    //Number - The backdrop padding above & below the label in pixels
		    scaleBackdropPaddingY : 2,
		    //Number - The backdrop padding to the side of the label in pixels
		    scaleBackdropPaddingX : 2,
		    //Boolean - Show line for each value in the scale
		    scaleShowLine : true,
		    //Boolean - Stroke a line around each segment in the chart
		    segmentShowStroke : true,
		    //String - The colour of the stroke on each segement.
		    segmentStrokeColor : "#fff",
		    //Number - The width of the stroke value in pixels
		    segmentStrokeWidth : 1,
		    //Number - Amount of animation steps
		    animationSteps : 60,
		    //String - Animation easing effect.
		    animationEasing : "easeOutBounce",
		    //Boolean - Whether to animate the rotation of the chart
		    animateRotate : true,
		    //Boolean - Whether to animate scaling the chart from the centre
		    animateScale : false
		}
	
	var monthlyPaidAmount = '${monthlyPaidAmount}'
	,monthlyUnpaidAmount = '${monthlyUnpaidAmount}'
	,monthlyCredit = '${monthlyCredit}'
	,monthlyVoidBalanceAmount = '${monthlyVoidBalanceAmount}';
	
	if(monthlyUnpaidAmount>0 || monthlyPaidAmount>0 || monthlyCredit>0 || monthlyVoidBalanceAmount>0){

		$('#total_monthly_paid').html('( '+new Number(monthlyPaidAmount).toFixed(2)+' )');
		$('#total_monthly_unpaid').html('( '+new Number(monthlyUnpaidAmount).toFixed(2)+' )');
		$('#total_monthly_credit').html('( '+new Number(monthlyCredit).toFixed(2)+' )');
		$('#total_monthly_void_balance').html('( '+new Number(monthlyVoidBalanceAmount).toFixed(2)+' )');
		
		var jsonArr = [];
		
		if(monthlyUnpaidAmount>0){
			jsonArr.push('{value:'+monthlyUnpaidAmount+',color:"#F7464A",highlight: "#FF5A5E",label: "Unpaid"}');
		}
		if(monthlyPaidAmount>0){
			jsonArr.push('{value:'+monthlyPaidAmount+',color: "#FDB45C",highlight: "#FFC870",label: "Paid"}');
		}
		if(monthlyCredit>0){
			jsonArr.push('{value:'+monthlyCredit+',color: "#949FB1",highlight: "#A8B3C5",label: "Credit"}');
		}
		if(monthlyVoidBalanceAmount>0){
			jsonArr.push('{value:'+monthlyVoidBalanceAmount+',color: "#46BFBD",highlight: "#5AD3D1",label: "Void Balance"}');
		}
		
		var jsonArrFinal = '[';
		for(var i=0; i<jsonArr.length; i++){
			jsonArrFinal+=jsonArr[i];
			if(i < jsonArr.length-1){
				jsonArrFinal+=',';
			}
		}
		jsonArrFinal += ']';
		
		var data = eval(jsonArrFinal);
		console.log(data);
		
		new Chart(document.getElementById("canvasMonth").getContext("2d")).PolarArea(data, monthOptions);
	} else {
		$('#alertContainer').html($('#tempAlertErrorContainer').html());
		$('#text-error').html('Could not find specific month\'s data!');
		$('#alert-error').css('display', '');
	}
	
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
