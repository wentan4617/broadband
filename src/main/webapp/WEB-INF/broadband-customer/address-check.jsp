<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>  
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<jsp:include page="header.jsp" />

<style>
.modal {
	z-index: 140;
}
.modal-backdrop{
	z-index: 130;
}
.home-title {
	font-size:36px;
}
.pb-bg {
	background-color: rgba(236, 236, 236, 0.54);
}
</style>



<div class="container">
	
	<%-- <img src="${ctx }/public/bootstrap3/images/persoanl-12months-unlimited-plan.png"  class="img-responsive" alt="persoanl 12months unlimited plan"> --%>
	
	<ul class="panel panel-success nav nav-pills nav-justified"><!-- nav-justified -->
		<li class="">
			<a class="btn-lg">
				1. Choose Plan
				<span class="glyphicon glyphicon-hand-right pull-right"></span>
			</a>
		</li>
		<li class="active">
			<a class="btn-lg">
				2. Check Your Address
				<span class="glyphicon glyphicon-hand-right pull-right" ></span>
			</a>
		</li>
		<li class="">
			<a class="btn-lg">
				3. Fill Application Form
				<span class="glyphicon glyphicon-hand-right pull-right" ></span>
			</a>
		</li>
		<li class="">
			<a class="btn-lg">
				4. Review and Order
			</a>
		</li>
	</ul>
	
	<div class="page-header" style="margin-top:0;">
		<h2>
			Check your address whether the service can be installed
		</h2>
	</div>
	
	<div class="row">
		<div class="col-md-7">
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
	
	<div id="checkResult" style="min-height:600px;margin-top:15px;"></div>
</div>

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
	
	$(document).keypress(function(e){
		if (event.keyCode == 13) {
			$('#goCheck').trigger('click');
		}
	});
	
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
	
	var select_plan_id = '${select_plan_id}';
	var select_plan_type = '${select_plan_type}';
	
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
	
	
})(jQuery);
</script>
<script src="https://maps.google.com/maps/api/js?sensor=false&libraries=places&region=NZ" type="text/javascript"></script>
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/autoCompleteAddress.js"></script>
<jsp:include page="footer-end.jsp" />