<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>  
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<jsp:include page="header.jsp" />

<style>
.nav-pills>li.active>a, .nav-pills>li.active>a:hover, .nav-pills>li.active>a:focus {
	color: #fff;
	background-color: #7BC3EC;
}
.panel-primary>.panel-footer {
	color: #fff;
	background-color: #428bca;
	border-color: #428bca;
}
.panel-info>.panel-footer {
	color: #31708f;
	background-color: #d9edf7;
	border-color: #bce8f1;
}
.panel-danger>.panel-footer {
	color: #a94442;
	background-color: #f2dede;
	border-color: #ebccd1;
}
.modal {
	z-index: 140;
}
.modal-backdrop{
	z-index: 130;
}
.home-title {
	font-size:36px;
}
</style>



<div class="container">
	<div class="page-header" style="margin-top:0;">
		<h1>
			Personal Broadband Plans <small>(PBP)</small>
		</h1>
	</div>
	<div class="alert alert-info">
		<p>
			We offer the best value and price personal broadband plans to you. 
			We can offer more values and better price telecommunication plans to you.
		</p>
	</div>
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
				3. Review and Order
			</a>
		</li>
	</ul>
	
	<c:set var="adslPlanMap" value="${planTypeMap['ADSL'] }"></c:set>
	<c:set var="vdslPlanMap" value="${planTypeMap['VDSL'] }"></c:set>
	<c:set var="ufbPlanMap" value="${planTypeMap['UFB'] }"></c:set>

	<!-- adsl -->
	<div class="page-header" style="margin-top:0;margin-bottom:5px;">
		<h3>
			<span class="label label-primary" id="adsl">
				ADSL + Home Phone Line&nbsp;
				(FAST&nbsp;
					<span class="glyphicon glyphicon-flash"></span>
					<span class="glyphicon glyphicon-flash"></span>
					<span class="glyphicon glyphicon-flash"></span>)
			</span>
		</h3>
	</div>
	<div class="row">
		<c:set var="plansPromotion" value="${adslPlanMap['plansPromotion'] }"></c:set>
		<c:set var="plans" value="${adslPlanMap['plans'] }"></c:set>
		
		
		<c:forEach var="plan" items="${plans }">
			<div class="col-md-4">
				<div class="panel panel-primary">
					<div class="panel-heading">
						<h2 class="panel-title text-center">
							<span style="font-size:30px;font-weight:bold;float:left;margin-left:70px;margin-right:-50px;margin-top:25px;">$</span>
							<span style="font-size:60px;font-weight:bold;"> 
								<fmt:formatNumber value="${plan.plan_price} " type="number" pattern="##0" />
							</span>
							/ mth
						</h2>
					</div>
				 	<div class="panel-body">
				 		<!-- desc -->${plan.plan_desc }<!-- // end desc -->
					   	<p class="text-center">
							<a class="btn btn-success btn-lg btn-block" id="adsl-purchase" data-id="${plan.id}" data-type="adsl" data-name="purchase">Order</a> 
						</p>
				  	</div>
				  	<div class="panel-footer">
						<h2 class="text-center" style="margin:0;">
							<span style="font-weight:bold;">
								<c:choose>
									<c:when test="${plan.data_flow < 0 }">Unlimited Data</c:when>
									<c:otherwise>${plan.data_flow } GB</c:otherwise>
								</c:choose>
							</span>	
						</h2>
					</div>
				</div>
			</div>
		</c:forEach>
		<c:forEach var="plan" items="${plansPromotion }">
			<div class="col-md-4">
				<div class="panel panel-primary">
					<div class="panel-heading">
						<h2 class="panel-title text-center">
							<span style="font-size:30px;font-weight:bold;float:left;margin-left:70px;margin-right:-50px;margin-top:25px;">$</span>	
							<c:if test="${plan.original_price > 0 }">
								<i style="font-size:24px;text-decoration:line-through;">
									<fmt:formatNumber value="${plan.original_price} " type="number" pattern="##0" />
								</i>
							</c:if>
							<span style="font-size:60px;font-weight:bold;"> 
								<fmt:formatNumber value="${plan.plan_price} " type="number" pattern="##0" />
							</span>
							/ mth
						</h2>
					</div>
				 	<div class="panel-body">
				 		<!-- desc -->${plan.plan_desc }<!-- // end desc -->
					   	<p class="text-center">
							<a class="btn btn-success btn-lg btn-block" id="adsl-purchase" data-id="${plan.id}" data-type="adsl" data-name="purchase">Order</a> 
						</p>
				  	</div>
				  	<div class="panel-footer">
						<h2 class="text-center" style="margin:0;">
							<span style="font-weight:bold;">
								<c:choose>
									<c:when test="${plan.data_flow < 0 }">Unlimited Data</c:when>
									<c:otherwise>${plan.data_flow } GB</c:otherwise>
								</c:choose>
							</span>	
						</h2>
					</div>
				</div>
			</div>
		</c:forEach>
	</div>

	<!-- vdsl -->
	<div class="page-header" style="margin-top:0;margin-bottom:5px;">
		<h3>
			<span class="label label-info" id="vdsl">
				VDSL + Home Phone Line&nbsp;
				(FASTER&nbsp;
					<span class="glyphicon glyphicon-flash"></span>
					<span class="glyphicon glyphicon-flash"></span>
					<span class="glyphicon glyphicon-flash"></span>
					<span class="glyphicon glyphicon-flash"></span>)
			</span>
		</h3>
	</div>
	<div class="row">
		<c:set var="plansPromotion" value="${vdslPlanMap['plansPromotion'] }"></c:set>
		<c:set var="plans" value="${vdslPlanMap['plans'] }"></c:set>
		
		
		<c:forEach var="plan" items="${plans }">
			<div class="col-md-4">
				<div class="panel panel-info">
					<div class="panel-heading">
						<h2 class="panel-title text-center">
							<span style="font-size:30px;font-weight:bold;float:left;margin-left:70px;margin-right:-50px;margin-top:25px;">$</span>
							<span style="font-size:60px;font-weight:bold;"> 
								<fmt:formatNumber value="${plan.plan_price} " type="number" pattern="##0" />
							</span>
							/ mth
						</h2>
					</div>
				 	<div class="panel-body">
				 		<!-- desc -->${plan.plan_desc }<!-- // end desc -->
					   	<p class="text-center">
							<a class="btn btn-success btn-lg btn-block" id="vdsl-purchase" data-id="${plan.id}" data-type="vdsl" data-name="purchase">Order</a> 
						</p>
				  	</div>
				  	<div class="panel-footer">
						<h2 class="text-center" style="margin:0;">
							<span style="font-weight:bold;">
								<c:choose>
									<c:when test="${plan.data_flow < 0 }">Unlimited Data</c:when>
									<c:otherwise>${plan.data_flow } GB</c:otherwise>
								</c:choose>
							</span>	
						</h2>
					</div>
				</div>
			</div>
		</c:forEach>
		<c:forEach var="plan" items="${plansPromotion }">
			<div class="col-md-4">
				<div class="panel panel-info">
					<div class="panel-heading">
						<h2 class="panel-title text-center">
							<span style="font-size:30px;font-weight:bold;float:left;margin-left:70px;margin-right:-50px;margin-top:25px;">$</span>	
							<c:if test="${plan.original_price > 0 }">
								<i style="font-size:24px;text-decoration:line-through;">
									<fmt:formatNumber value="${plan.original_price} " type="number" pattern="##0" />
								</i>
							</c:if>
							<span style="font-size:60px;font-weight:bold;"> 
								<fmt:formatNumber value="${plan.plan_price} " type="number" pattern="##0" />
							</span>
							/ mth
						</h2>
					</div>
				 	<div class="panel-body">
				 		<!-- desc -->${plan.plan_desc }<!-- // end desc -->
					   	<p class="text-center">
							<a class="btn btn-success btn-lg btn-block" id="vdsl-purchase" data-id="${plan.id}" data-type="vdsl" data-name="purchase">Order</a> 
						</p>
				  	</div>
				  	<div class="panel-footer">
						<h2 class="text-center" style="margin:0;">
							<span style="font-weight:bold;">
								<c:choose>
									<c:when test="${plan.data_flow < 0 }">Unlimited Data</c:when>
									<c:otherwise>${plan.data_flow } GB</c:otherwise>
								</c:choose>
							</span>	
						</h2>
					</div>
				</div>
			</div>
		</c:forEach>
	</div>
	
	<!-- ufb -->
	<div class="page-header" style="margin-top:0;margin-bottom:5px;">
		<h3>
			<span class="label label-danger" id="ufb">
				UFB + Home Phone Line&nbsp;
				(FASTEST&nbsp;
					<span class="glyphicon glyphicon-flash"></span>
					<span class="glyphicon glyphicon-flash"></span>
					<span class="glyphicon glyphicon-flash"></span>
					<span class="glyphicon glyphicon-flash"></span>
					<span class="glyphicon glyphicon-flash"></span>)
			</span>
		</h3>
	</div>
	<div class="row">
		<c:set var="plansPromotion" value="${ufbPlanMap['plansPromotion'] }"></c:set>
		<c:set var="plans" value="${ufbPlanMap['plans'] }"></c:set>
		
		
		<c:forEach var="plan" items="${plans }">
			<div class="col-md-4">
				<div class="panel panel-danger">
					<div class="panel-heading">
						<h2 class="panel-title text-center">
							<span style="font-size:30px;font-weight:bold;float:left;margin-left:70px;margin-right:-50px;margin-top:25px;">$</span>
							<span style="font-size:60px;font-weight:bold;"> 
								<fmt:formatNumber value="${plan.plan_price} " type="number" pattern="##0" />
							</span>
							/ mth
						</h2>
					</div>
				 	<div class="panel-body">
				 		<!-- desc -->${plan.plan_desc }<!-- // end desc -->
					   	<p class="text-center">
							<a class="btn btn-success btn-lg btn-block" id="ufb-purchase" data-id="${plan.id}" data-type="ufb" data-name="purchase">Order</a> 
						</p>
				  	</div>
				  	<div class="panel-footer">
						<h2 class="text-center" style="margin:0;">
							<span style="font-weight:bold;">
								<c:choose>
									<c:when test="${plan.data_flow < 0 }">Unlimited Data</c:when>
									<c:otherwise>${plan.data_flow } GB</c:otherwise>
								</c:choose>
							</span>	
						</h2>
					</div>
				</div>
			</div>
		</c:forEach>
		<c:forEach var="plan" items="${plansPromotion }">
			<div class="col-md-4">
				<div class="panel panel-danger">
					<div class="panel-heading">
						<h2 class="panel-title text-center">
							<span style="font-size:30px;font-weight:bold;float:left;margin-left:70px;margin-right:-50px;margin-top:25px;">$</span>	
							<c:if test="${plan.original_price > 0 }">
								<i style="font-size:24px;text-decoration:line-through;">
									<fmt:formatNumber value="${plan.original_price} " type="number" pattern="##0" />
								</i>
							</c:if>
							<span style="font-size:60px;font-weight:bold;"> 
								<fmt:formatNumber value="${plan.plan_price} " type="number" pattern="##0" />
							</span>
							/ mth
						</h2>
					</div>
				 	<div class="panel-body">
				 		<!-- desc -->${plan.plan_desc }<!-- // end desc -->
					   	<p class="text-center">
							<a class="btn btn-success btn-lg btn-block" id="ufb-purchase" data-id="${plan.id}" data-type="ufb" data-name="purchase">Order</a> 
						</p>
				  	</div>
				  	<div class="panel-footer">
						<h2 class="text-center" style="margin:0;">
							<span style="font-weight:bold;">
								<c:choose>
									<c:when test="${plan.data_flow < 0 }">Unlimited Data</c:when>
									<c:otherwise>${plan.data_flow } GB</c:otherwise>
								</c:choose>
							</span>	
						</h2>
					</div>
				</div>
			</div>
		</c:forEach>
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