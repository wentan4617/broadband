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
#statisticArea span#total_monthly_invoice_amount{
	color:#333;
}
#statisticArea div#total_monthly_invoice_amount_div{
	 border:3px solid #f2dede; border-radius:0 20px 20px 0;
}
</style>

<div class="container">
	<div class="row">
		<div class="col-md-12">
			<div class="panel panel-default">
				<div class="panel-heading"><h3 class="panel-title">${panelheading} - <strong>${customer_type}</strong></h3></div>
				<div class="panel-body">
					<div class="input-group date col-md-2">
						<input id="year_month_input"
							class="form-control" placeholder="Start Date" value="${yearMonth}"/>
							<span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
					</div>
					<hr/>
					<div id="statisticArea">
						<form class="form-horizontal">
							<div class="form-group">
								<div class="col-md-4 bg-success" style="text-align:center;font-size:16px;" id="total_monthly_invoice_amount_div">
									<!-- INVOICE AMOUNT -->
									<strong>INVOICE AMOUNT: NZ$<span id="total_monthly_invoice_amount"></span></strong>
								</div>
							</div>
							<hr/>
							<div class="form-group">
								<!-- UNPAID -->
								<div class="col-md-3">
									<a target="blank" id="unpaid_btn" href="${ctx}/broadband-user/billing/invoice/view/${customer_type}/1/unpaid/${yearMonth}">
										<span class="title">1.&nbsp;
											UNPAID: NZ$<span id="total_monthly_unpaid"></span>
										</span>
									</a>
								</div>
								<!-- PREPAYMENT -->
								<div class="col-md-3">
									<a target="blank" id="prepayment_btn" class="bg-success" href="${ctx}/broadband-user/billing/invoice/view/${customer_type}/1/prepayment/${yearMonth}">
										<span class="title">3.&nbsp;
											PREPAYMENT: NZ$<span id="total_monthly_prepayment"></span>
										</span>
									</a>
								</div>
								<!-- CREDITED -->
								<div class="col-md-3">
									<a target="blank" id="credit_btn" href="javascript:void(0);">
										<span class="title">5.&nbsp;
											CREDITED: NZ$<span id="total_monthly_credit"></span>
										</span>
									</a>
								</div>
								<!-- BAD DEBIT -->
								<div class="col-md-3">
									<a target="blank" id="bad_debit_btn" href="${ctx}/broadband-user/billing/invoice/view/${customer_type}/1/bad_debit/${yearMonth}">
										<span class="title">7.&nbsp;
											BAD DEBIT: NZ$<span id="total_monthly_bad_debit"></span>
										</span>
									</a>
								</div>
							</div>
							<div class="form-group">
								<!-- OVERDUE -->
								<div class="col-md-3">
									<a target="blank" id="overdue_btn" href="${ctx}/broadband-user/billing/invoice/view/${customer_type}/1/overdue/${yearMonth}">
										<span class="title">2.&nbsp;
											OVERDUE: NZ$<span id="total_monthly_overdue"></span>
										</span>
									</a>
								</div>
								<!-- PAID -->
								<div class="col-md-3">
									<a target="blank" id="paid_btn" href="${ctx}/broadband-user/billing/invoice/view/${customer_type}/1/paid/${yearMonth}">
										<span class="title">4.&nbsp;
											PAID: NZ$<span id="total_monthly_paid"></span>
										</span>
									</a>
								</div>
								<!-- VOIDED -->
								<div class="col-md-3">
									<a target="blank" id="void_btn" href="${ctx}/broadband-user/billing/invoice/view/${customer_type}/1/void/${yearMonth}">
										<span class="title">6.&nbsp;
											VOIDED: NZ$<span id="total_monthly_void_balance"></span>
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
	
	
	var monthlyPaidAmount = '${monthlyPaidAmount}'
	,monthlyUnpaidAmount = '${monthlyUnpaidAmount}'
	,monthlyCredit = '${monthlyCredit}'
	,monthlyVoidBalanceAmount = '${monthlyVoidBalanceAmount}'
	,monthlyInvoiceAmount = '${monthlyInvoiceAmount}'
	,monthlyOverdueAmount = '${monthlyOverdueAmount}'
	,monthlyBadDebitAmount = '${monthlyBadDebitAmount}'
	,monthlyPrepaymentAmount = '${monthlyPrepaymentAmount}';

	$('#total_monthly_paid').html('( '+new Number(monthlyPaidAmount).toFixed(2)+' )');
	$('#total_monthly_unpaid').html('( '+new Number(monthlyUnpaidAmount).toFixed(2)+' )');
	$('#total_monthly_credit').html('( '+new Number(monthlyCredit).toFixed(2)+' )');
	$('#total_monthly_void_balance').html('( '+new Number(monthlyVoidBalanceAmount).toFixed(2)+' )');
	$('#total_monthly_invoice_amount').html('( '+new Number(monthlyInvoiceAmount).toFixed(2)+' )');
	$('#total_monthly_overdue').html('( '+new Number(monthlyOverdueAmount).toFixed(2)+' )');
	$('#total_monthly_bad_debit').html('( '+new Number(monthlyBadDebitAmount).toFixed(2)+' )');
	$('#total_monthly_prepayment').html('( '+new Number(monthlyPrepaymentAmount).toFixed(2)+' )');
	
	if(monthlyUnpaidAmount>0 || monthlyPaidAmount>0 || monthlyCredit>0 || monthlyVoidBalanceAmount>0 || monthlyPrepaymentAmount>0){
		
		var jsonArr = [];

		if(monthlyUnpaidAmount>0){
			jsonArr.push('{value:'+monthlyUnpaidAmount+',color:"rgba(250,120,160,0.75)",highlight:"rgba(250,120,160,0.5)",label:"Unpaid"}');
		}
		if(monthlyOverdueAmount>0){
			jsonArr.push('{value:'+monthlyOverdueAmount+',color:"rgba(230,50,100,0.75)",highlight:"rgba(230,50,100,0.5)",label:"Overdue"}');
		}
		if(monthlyPrepaymentAmount>0){
			jsonArr.push('{value:'+monthlyPrepaymentAmount+',color:"rgba(183,216,179,0.75)",highlight:"rgba(183,216,179,0.5)",label:"Prepayment"}');
		}
		if(monthlyPaidAmount>0){
			jsonArr.push('{value:'+monthlyPaidAmount+',color:"rgba(153,186,149,0.75)",highlight:"rgba(153,186,149,0.5)",label:"Paid"}');
		}
		if(monthlyCredit>0){
			jsonArr.push('{value:'+monthlyCredit+',color:"rgba(252,147,18,0.75)",highlight:"rgba(252,147,18,0.5)",label:"Credit"}');
		}
		if(monthlyVoidBalanceAmount>0){
			jsonArr.push('{value:'+monthlyVoidBalanceAmount+',color:"rgba(137,143,156,0.75)",highlight:"rgba(137,143,156,0.5)",label:"Voided"}');
		}
		if(monthlyBadDebitAmount>0){
			jsonArr.push('{value:'+monthlyBadDebitAmount+',color:"rgba(77,83,96,0.75)",highlight:"rgba(77,83,96,0.5)",label:"Bad Debit"}');
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
