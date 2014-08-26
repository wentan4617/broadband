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
#statisticArea span#total_annual_invoice_amount{
	color:#333;
}
#statisticArea div#total_annual_invoice_amount_div{
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
						<input id="year_input" class="form-control" value="${year}"/>
						<span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
					</div>
					<hr/>
					<div id="statisticArea">
						<form class="form-horizontal">
							<div class="form-group">
								<div class="col-md-4 bg-success" style="text-align:center;font-size:16px;" id="total_annual_invoice_amount_div">
									<!-- INVOICE AMOUNT -->
									<strong>INVOICE AMOUNT: NZ$<span id="total_annual_invoice_amount"></span></strong>
								</div>
							</div>
							<hr/>
							<div class="form-group">
								<!-- UNPAID -->
								<div class="col-md-3">
									<a target="blank" id="unpaid_btn" href="${ctx}/broadband-user/billing/invoice/view/${customer_type}/1/unpaid/${year}">
										<span class="title">1.&nbsp;
											UNPAID: NZ$<span id="total_annual_unpaid"></span>
										</span>
									</a>
								</div>
								<!-- PREPAYMENT -->
								<div class="col-md-3">
									<a target="blank" id="prepayment_btn" href="${ctx}/broadband-user/billing/invoice/view/${customer_type}/1/prepayment/${year}">
										<span class="title">3.&nbsp;
											PREPAYMENT: NZ$<span id="total_annual_prepayment"></span>
										</span>
									</a>
								</div>
								<!-- CREDITED -->
								<div class="col-md-3">
									<a target="blank" id="credit_btn" href="javascript:void(0);">
										<span class="title">5.&nbsp;
											CREDITED: NZ$<span id="total_annual_credit"></span>
										</span>
									</a>
								</div>
								<!-- BAD DEBIT -->
								<div class="col-md-3">
									<a target="blank" id="bad_debit_btn" href="${ctx}/broadband-user/billing/invoice/view/${customer_type}/1/bad_debit/${year}">
										<span class="title">7.&nbsp;
											BAD DEBIT: NZ$<span id="total_annual_bad_debit"></span>
										</span>
									</a>
								</div>
							</div>
							<div class="form-group">
								<!-- OVERDUE -->
								<div class="col-md-3">
									<a target="blank" id="overdue_btn" href="${ctx}/broadband-user/billing/invoice/view/${customer_type}/1/overdue/${year}">
										<span class="title">2.&nbsp;
											OVERDUE: NZ$<span id="total_annual_overdue"></span>
										</span>
									</a>
								</div>
								<!-- PAID -->
								<div class="col-md-3">
									<a target="blank" id="paid_btn" href="${ctx}/broadband-user/billing/invoice/view/${customer_type}/1/paid/${year}">
										<span class="title">4.&nbsp;
											PAID: NZ$<span id="total_annual_paid"></span>
										</span>
									</a>
								</div>
								<!-- VOIDED -->
								<div class="col-md-3">
									<a target="blank" id="void_btn" href="${ctx}/broadband-user/billing/invoice/view/${customer_type}/1/void/${year}">
										<span class="title">6.&nbsp;
											VOIDED: NZ$<span id="total_annual_void"></span>
										</span>
									</a>
								</div>
							</div>
						</form>
					</div>
					<hr/>
					<canvas id="canvasAnnual" height="500" width="1100"></canvas>
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
	
	var annualLabels = []
	, annualUnpaidDatum = []
	, annualPrepaymentDatum = []
	, annualPaidDatum = []
	, annualVoidDatum = []
	, annualCreditDatum = []
	, annualInvoiceAmountDatum = []
	, annualOverdueDatum = []
	, annualBadDebitDatum = [];
	
	var totalAnnualUnpaid = 0
	, totalAnnualPrepayment = 0
	, totalAnnualPaid = 0
	, totalAnnualVoid = 0
	, totalAnnualCredit = 0
	, totalAnnualInvoiceAmount = 0
	, totalAnnualOverdue = 0
	, totalAnnualBadDebit = 0;
	
	<c:forEach var="annualUnpaidInvoice" items="${annualUnpaidInvoices}">
		annualLabels.push('${annualUnpaidInvoice.billingMonthDate_str}'.substring(5, 7));
		annualUnpaidDatum.push(${annualUnpaidInvoice.billingAmount});
	</c:forEach>
	
	<c:forEach var="annualPrepaymentInvoice" items="${annualPrepaymentInvoices}">
		annualPrepaymentDatum.push(${annualPrepaymentInvoice.billingAmount});
	</c:forEach>
	
	<c:forEach var="annualPaidInvoice" items="${annualPaidInvoices}">
		annualPaidDatum.push(${annualPaidInvoice.billingAmount});
	</c:forEach>
	
	<c:forEach var="annualVoidInvoice" items="${annualVoidInvoices}">
		annualVoidDatum.push(${annualVoidInvoice.billingAmount});
	</c:forEach>
	
	<c:forEach var="annualCreditInvoice" items="${annualCreditInvoices}">
		annualCreditDatum.push(${annualCreditInvoice.billingAmount});
	</c:forEach>
	
	<c:forEach var="annualInvoiceAmountInvoice" items="${annualInvoiceAmountInvoices}">
		annualInvoiceAmountDatum.push(${annualInvoiceAmountInvoice.billingAmount});
	</c:forEach>
	
	<c:forEach var="annualOverdueInvoice" items="${annualOverdueInvoices}">
		annualOverdueDatum.push(${annualOverdueInvoice.billingAmount});
	</c:forEach>
	
	<c:forEach var="annualBadDebitInvoice" items="${annualBadDebitInvoices}">
		annualBadDebitDatum.push(${annualBadDebitInvoice.billingAmount});
	</c:forEach>
	
	if(annualInvoiceAmountDatum.length > 0){
		for(var i=0; i<annualInvoiceAmountDatum.length; i++){
			totalAnnualInvoiceAmount += annualInvoiceAmountDatum[i];
		}
		$('#total_annual_invoice_amount').html('( '+new Number(totalAnnualInvoiceAmount).toFixed(2)+' )');
	}
	
	if(annualUnpaidDatum.length > 0){
		for(var i=0; i<annualUnpaidDatum.length; i++){
			totalAnnualUnpaid += annualUnpaidDatum[i];
		}
		$('#total_annual_unpaid').html('( '+new Number(totalAnnualUnpaid).toFixed(2)+' )');
	}
	
	if(annualOverdueDatum.length > 0){
		for(var i=0; i<annualOverdueDatum.length; i++){
			totalAnnualOverdue += annualOverdueDatum[i];
		}
		$('#total_annual_overdue').html('( '+new Number(totalAnnualOverdue).toFixed(2)+' )');
	}
	
	if(annualPrepaymentDatum.length > 0){
		for(var i=0; i<annualPrepaymentDatum.length; i++){
			totalAnnualPrepayment += annualPrepaymentDatum[i];
		}
		$('#total_annual_prepayment').html('( '+new Number(totalAnnualPrepayment).toFixed(2)+' )');
	}
	
	if(annualPaidDatum.length > 0){
		for(var i=0; i<annualPaidDatum.length; i++){
			totalAnnualPaid += annualPaidDatum[i];
		}
		$('#total_annual_paid').html('( '+new Number(totalAnnualPaid).toFixed(2)+' )');
	}
	
	if(annualCreditDatum.length > 0){
		for(var i=0; i<annualCreditDatum.length; i++){
			totalAnnualCredit += annualCreditDatum[i];
		}
		$('#total_annual_credit').html('( '+new Number(totalAnnualCredit).toFixed(2)+' )');
	}
	
	if(annualVoidDatum.length > 0){
		for(var i=0; i<annualVoidDatum.length; i++){
			totalAnnualVoid += annualVoidDatum[i];
		}
		$('#total_annual_void').html('( '+new Number(totalAnnualVoid).toFixed(2)+' )');
	}
	
	if(annualBadDebitDatum.length > 0){
		for(var i=0; i<annualBadDebitDatum.length; i++){
			totalAnnualBadDebit += annualBadDebitDatum[i];
		}
		$('#total_annual_bad_debit').html('( '+new Number(totalAnnualBadDebit).toFixed(2)+' )');
	}
	
	if(totalAnnualUnpaid>0 || totalAnnualPrepayment>0 || totalAnnualPaid>0 || totalAnnualVoid>0 || totalAnnualCredit>0
	  || totalAnnualInvoiceAmount>0 || totalAnnualOverdue>0 || totalAnnualBadDebit>0){
		
		var annualBarChartData = {
			labels : annualLabels,
			datasets : [
		        {
					fillColor : "rgba(250,120,160,0.75)",
					strokeColor : "rgba(250,120,160,1)",
		            highlightFill: "rgba(250,120,160,0.5)",
		            highlightStroke: "rgba(250,120,160,0.8)",
					data : annualUnpaidDatum
				},
		        {
					fillColor : "rgba(230,50,100,0.75)",
					strokeColor : "rgba(230,50,100,1)",
		            highlightFill: "rgba(230,50,100,0.5)",
		            highlightStroke: "rgba(230,50,100,0.8)",
					data : annualOverdueDatum
				},
		        {
					fillColor : "rgba(183,216,179,0.75)",
					strokeColor : "rgba(183,216,179,1)",
		            highlightFill: "rgba(183,216,179,0.5)",
		            highlightStroke: "rgba(183,216,179,0.8)",
					data : annualPrepaymentDatum
				},
		        {
					fillColor : "rgba(153,186,149,0.75)",
					strokeColor : "rgba(153,186,149,1)",
		            highlightFill: "rgba(153,186,149,0.5)",
		            highlightStroke: "rgba(153,186,149,0.8)",
					data : annualPaidDatum
				},
		        {
					fillColor : "rgba(252,147,18,0.75)",
					strokeColor : "rgba(252,147,18,1)",
		            highlightFill: "rgba(252,147,18,0.5)",
		            highlightStroke: "rgba(252,147,18,0.8)",
					data : annualCreditDatum
				},
		        {
					fillColor : "rgba(137,143,156,0.75)",
					strokeColor : "rgba(137,143,156,1)",
		            highlightFill: "rgba(137,143,156,0.5)",
		            highlightStroke: "rgba(137,143,156,0.8)",
					data : annualVoidDatum
				},
		        {
					fillColor : "rgba(77,83,96,0.75)",
					strokeColor : "rgba(77,83,96,1)",
		            highlightFill: "rgba(77,83,96,0.5)",
		            highlightStroke: "rgba(77,83,96,0.8)",
					data : annualBadDebitDatum
				}
			]};
		
		var options = {
			barValueSpacing : 10,
			barDatasetSpacing : 0
		};
		
		new Chart(document.getElementById("canvasAnnual").getContext("2d")).Bar(annualBarChartData, options);
	} else {
		$('#alertContainer').html($('#tempAlertErrorContainer').html());
		$('#text-error').html('Could not find specific year\'s data!');
		$('#alert-error').css('display', '');
	}
	
	$('.input-group.date').datepicker({
	    format: "yyyy",
	    autoclose: true,
	    minViewMode: 2
	});
	
	$('.input-group.date').datepicker().on('changeDate', function(ev){
		var value = $('#year_input').val();
		window.location.href = value;
	});
	
})(jQuery);
</script>
<jsp:include page="../footer-end.jsp" />
