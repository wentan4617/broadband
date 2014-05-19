<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>  

<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<jsp:include page="header.jsp" />

<style>
.nav-pills>li.active>a, .nav-pills>li.active>a:hover, .nav-pills>li.active>a:focus {
color: #fff;
background-color: #7BC3EC;
}
.modal {
	z-index: 140;
}
.modal-backdrop{
	z-index: 130;
}
#adslPanel .col-md-3 {
	padding: 0;
}
#adslPanel .col-md-3:first-child {
	padding-left: 15px;
}
#adslPanel .col-md-3:last-child {
	padding-right: 15px;
}
#adslPanel .thumbnail {
	border-right: 0;
	border-radius: 0;
}
#adslPanel .col-md-3:first-child .thumbnail{
	border-radius: 4px 0 0 4px;
}
#adslPanel .col-md-3:last-child .thumbnail{
	border-right: 1px solid #ddd;
	border-radius: 0 4px 4px 0;
}
.home-title {
	font-size:36px;
}
</style>


<div class="container" style="margin-top:20px;">

	<ul class="panel panel-success nav nav-pills nav-justified"><!-- nav-justified -->
		<li class="active">
			<a class="btn-lg">
				1. Choose Plans And Pricing
				<span class="glyphicon glyphicon-hand-right pull-right"></span>
			</a>
		</li>
		<li class="">
			<a class="btn-lg">
				2. Fill Application Form
				<span class="glyphicon glyphicon-hand-right pull-right" ></span>
			</a>
		</li>
		<li class="">
			<a class="btn-lg">
				3. Review and Checkout 
			</a>
		</li>
	</ul>
	
	<c:set var="adslPlanMap" value="${planTypeMap['ADSL'] }"></c:set>
	<c:set var="plans" value="${adslPlanMap['plans'] }"></c:set>
	
	
	<div class="panel panel-success" id="adslPanel">
		<div class="panel-body">
			<img class="pull-right" src="${ctx }/public/bootstrap3/images/icon_most-popular.png" alt="ADSL provides fast bandwidth for home use">
			<h1 style="height:54px;font-weight:bold;" class="text-success">ADSL Naked BROADBAND (No Term)</h1>
			<hr/>
			<div class="well">
				The broadband standard in NZ. Fast Internet over your copper phone line.
			</div>
			<h3><strong class="text-success">How much data do you need?</strong></h3>
			
			<div class="row">
				<c:forEach var="plan" items="${plans}" varStatus="item">
					<div class="col-md-3">
						<div class="thumbnail">
							<div class="caption">
								<div class="text-success text-center home-title" style="font-weight:bold;">
									<c:choose>
										<c:when test="${plan.data_flow < 0 }">Unlimited</c:when>
										<c:otherwise>${plan.data_flow } GB</c:otherwise>
									</c:choose>
								</div>
								<hr style="margin-top:0;"/>
								<p class="text-center text-success" style="position:relative;margin-bottom:0;">
									<span style="font-size:40px;font-weight:bold;float:left;margin-left:20px;margin-top:20px;">$</span>
									<span style="font-size:60px;font-weight:bold;"> 
										<fmt:formatNumber value="${plan.plan_price} " type="number" pattern="##0" />
									</span>
									/ mth
								</p>
								<p style="font-size:18px;">
									<span class="text-success" style="font-weight:bold;">${plan.plan_name }</span>
								</p>
								
								<!-- desc -->${plan.plan_desc }<!-- // end desc -->
								<hr/>
								<p>
									<a class="btn btn-success btn-lg btn-block"  data-name="purchase" data-id="${plan.id }" data-type="adsl">Purchase</a> 
								</p>
							</div>
						</div>
					</div>
				</c:forEach>
			</div>
		</div>
	</div>
	
	
</div>


<!-- Check Address Modal -->
<div class="modal fade" id="checkAddressModal" tabindex="-1" role="dialog" aria-labelledby="checkAddressModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="margin-top:55px;width:60%">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="checkAddressModalLabel">Check your address whether the service can be installed</h4>
			</div>
			<div class="modal-body">
				<div class="row">
					<div class="col-md-12">
						<div class="input-group">
							<input id="address" type="text" class="form-control input-lg" placeholder="Put your address here" /> 
							<span class="input-group-btn">
								<button class="btn btn-success btn-lg ladda-button" data-style="zoom-in" type="button" id="goCheck">
									<span class="ladda-label">Go</span>
								</button>
							</span>
						</div>
					</div>
				</div>
			</div>
			<div id="checkResult"></div>
		</div> <!-- /.modal-content -->
	</div> <!-- /.modal-dialog -->
</div> <!-- /.modal -->

<div id="map_canvas" style="width:720px;height:600px;display:none;"></div>
<script type="text/html" id="result_tmpl">
<jsp:include page="resultAddressCheck.html" />
</script>

<jsp:include page="footer.jsp" />
<jsp:include page="script.jsp" />
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/icheck.min.js"></script>
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/jTmpl.js"></script>
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/spin.min.js"></script>
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/ladda.min.js"></script>
<script type="text/javascript">
(function($){
	
	var adslPlans=[], vdslPlans=[], ufbPlans=[];
	<c:forEach var="type" items="ADSL,VDSL,UFB">
		<c:set var="plansPromotion" value="${planTypeMap[type]['plansPromotion'] }"></c:set>
		<c:set var="plans" value="${planTypeMap[type]['plans'] }"></c:set>
		<c:forEach var="plan" items="${plansPromotion }">
			var plan = {
				id: ${plan.id }
				, plan_name: '${plan.plan_name }'
				, plan_price: ${plan.plan_price }
				, data_flow: ${plan.data_flow }
			};
			if ('${type }'=='ADSL') { adslPlans.push(plan); } 
			else if ('${type }'=='VDSL') { vdslPlans.push(plan); } 
			else if ('${type }'=='UFB') { ufbPlans.push(plan); }
		</c:forEach>
		<c:forEach var="plan" items="${plans }">
			var plan = {
				id: ${plan.id }
				, plan_name: '${plan.plan_name }'
				, plan_price: ${plan.plan_price }
				, data_flow: ${plan.data_flow }
			};
			if ('${type }'=='ADSL') { adslPlans.push(plan); } 
			else if ('${type }'=='VDSL') { vdslPlans.push(plan); } 
			else if ('${type }'=='UFB') { ufbPlans.push(plan); }
		</c:forEach>
	</c:forEach>
	
	var select_plan_id = "";
	var select_plan_type = "";
	
	$('#goCheck').click(function(){
		var address = $('#address').val();
		address = $.trim(address.replace(/[\/]/g,' ').replace(/[\\]/g,' ')); //console.log(address);
		if (address != '') {
			var l = Ladda.create(this);
		 	l.start();
			$.get('${ctx}/address/check/' + address, function(broadband){
				broadband.type = select_plan_type;
				broadband.selected_id = select_plan_id;
				broadband.adslPlans = adslPlans;
				broadband.vdslPlans = vdslPlans;
				broadband.ufbPlans = ufbPlans;
				$('#checkResult').html(tmpl('result_tmpl', broadband));
				$(':radio').iCheck({
					checkboxClass : 'icheckbox_square-green',
					radioClass : 'iradio_square-green'
				});
				$('a[data-toggle="tooltip"]').tooltip();
				$('a[data-name="continue-selected-plan"]').click(function(){
					var type = $(this).attr('data-type');
					$.get('${ctx}/do/service/', function(){
						var id = $('input[name="' + type + '_id"]:checked').val();
						if (id) window.location.href = '${ctx}/order/' + id;
						else { alert('Please choose one plan at least.'); }
						
					});
				});
		   	}).always(function(){ l.stop(); });
		} else {
			alert('Please enter a real address.');
		}
	});
	
	$('a[data-name="purchase"]').click(function(){
		select_plan_id = $(this).attr('data-id');
		select_plan_type = $(this).attr('data-type');//console.log(select_plan_id);
		$('#checkResult').empty();
		$('#checkAddressModal').modal('show');
	});

})(jQuery);
</script>
<script src="https://maps.google.com/maps/api/js?sensor=false&libraries=places&region=NZ" type="text/javascript"></script>
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/autoCompleteAddress.js"></script>
<jsp:include page="footer-end.jsp" />