<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<jsp:include page="../header.jsp" />
<jsp:include page="../alert.jsp" />

<style>
#statisticArea{
	font-size:14px;
}
#statisticArea a{
	text-decoration:none;
}
#statisticArea a#unpaid_btn span.title{
	background:rgba(250,120,160,0.75); border:3px solid rgba(250,120,160,1); display:block;
}
#statisticArea a#unpaid_btn:hover span.title{
	background:rgba(250,120,160,0.5); border:3px solid rgba(250,120,160,0.8);
}
#statisticArea a#overdue_btn span.title{
	background:rgba(230,50,100,0.75); border:3px solid rgba(230,50,100,1); display:block;
}
#statisticArea a#overdue_btn:hover span.title{
	background:rgba(230,50,100,0.5); border:3px solid rgba(230,50,100,0.8);
}
#statisticArea a#credit_btn span.title{
	background:rgba(252,147,18,0.75); border:3px solid rgba(252,147,18,1); display:block;
}
#statisticArea a#credit_btn:hover span.title{
	background:rgba(252,147,18,0.5); border:3px solid rgba(252,147,18,0.8);
}
#statisticArea a#prepayment_btn span.title{
	background:rgba(183,216,179,0.75); border:3px solid rgba(183,216,179,1); display:block;
}
#statisticArea a#prepayment_btn:hover span.title{
	background:rgba(183,216,179,0.5); border:3px solid rgba(183,216,179,0.8);
}
#statisticArea a#paid_btn span.title{
	background:rgba(153,186,149,0.75); border:3px solid rgba(153,186,149,1); display:block;
}
#statisticArea a#paid_btn:hover span.title{
	background:rgba(153,186,149,0.5); border:3px solid rgba(153,186,149,0.8);
}
#statisticArea a#void_btn span.title{
	background:rgba(137,143,156,0.75); border:3px solid rgba(137,143,156,1); display:block;
}
#statisticArea a#void_btn:hover span.title{
	background:rgba(137,143,156,0.5); border:3px solid rgba(137,143,156,0.8);
}
#statisticArea a#bad_debit_btn span.title{
	background:rgba(77,83,96,0.75); border:3px solid rgba(77,83,96,1); display:block;
}
#statisticArea a#bad_debit_btn:hover span.title{
	background:rgba(77,83,96,0.5); border:3px solid rgba(77,83,96,0.8);
}
#statisticArea span{
	padding:5px; border-radius:4px; color:white; font-weight:bold;
}
</style>

<div class="container">
	<div class="row">
		<div class="col-md-12">
			<div class="panel panel-default">
				<div class="panel-heading"><h3 class="panel-title">${panelheading} - <strong>${yearMonth}</strong></h3></div>
				<div class="panel-body">
					<div class="input-group date col-md-2">
						<input id="year_month_input" class="form-control" placeholder="Start Date" value="${yearMonth}"/>
						<span class="input-group-addon">
							<i class="glyphicon glyphicon-calendar"></i>
						</span>
					</div>
					<hr/>
					<div class="btn-group btn-group">
						<a href="${ctx}/broadband-user/billing/chart/calling-statistic/all/${yearMonth}" class="btn btn-default ${allActive }">
							All&nbsp;<span class="badge">${allSum}</span>
						</a>
						<a href="${ctx}/broadband-user/billing/chart/calling-statistic/chorus/${yearMonth}" class="btn btn-default ${chorusActive }">
							CHORUS&nbsp;<span class="badge">${chorusSum}</span>
						</a>
						<a href="${ctx}/broadband-user/billing/chart/calling-statistic/nca/${yearMonth}" class="btn btn-default ${ncaActive }">
							NCA&nbsp;<span class="badge">${ncaSum}</span>
						</a>
						<a href="${ctx}/broadband-user/billing/chart/calling-statistic/vos/${yearMonth}" class="btn btn-default ${vosActive }">
							VOS&nbsp;<span class="badge">${vosSum}</span>
						</a>
					</div>
					<hr/>
					<div id="statisticArea">
						<form class="form-horizontal">
							<div class="form-group">
								<!-- Selling Total -->
								<div class="col-md-3">
									<a target="blank" id="paid_btn">
										<span class="title">
											SELLING: NZ$<span id="total_monthly_selling"></span>
										</span>
									</a>
								</div>
								<!-- Buying Total -->
								<div class="col-md-3">
									<a target="blank" id="unpaid_btn">
										<span class="title">
											BUYING: NZ$<span id=total_monthly_buying></span>
										</span>
									</a>
								</div>
							</div>
						</form>
					</div>
					<hr/>
					<canvas id="canvasMonth" height="500" width="1100"></canvas>
					<hr/>
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
	
	
	var monthlyBuyingAmount = '${monthlyBuyingAmount}'
	,monthlySellingAmount = '${monthlySellingAmount}';

	$('#total_monthly_buying').html('( '+new Number(monthlyBuyingAmount).toFixed(2)+' )');
	$('#total_monthly_selling').html('( '+new Number(monthlySellingAmount).toFixed(2)+' )');
	
	if(monthlyBuyingAmount>0 || monthlySellingAmount>0){
		
		var jsonArr = [];

		if(monthlyBuyingAmount>0){
			jsonArr.push('{value:'+monthlyBuyingAmount+',color:"rgba(250,120,160,0.75)",highlight:"rgba(250,120,160,0.5)",label:"Buying Total"}');
		}
		if(monthlySellingAmount>0){
			jsonArr.push('{value:'+monthlySellingAmount+',color:"rgba(153,186,149,0.75)",highlight:"rgba(153,186,149,0.5)",label:"Selling Total"}');
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
		
		new Chart(document.getElementById("canvasMonth").getContext("2d")).Pie(data);
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
	
	$('.input-group.date').datepicker().on('changeDate', function(ev){
		var value = $('#year_month_input').val();
		window.location.href = value;
	});
	
})(jQuery);
</script>
<jsp:include page="../footer-end.jsp" />
