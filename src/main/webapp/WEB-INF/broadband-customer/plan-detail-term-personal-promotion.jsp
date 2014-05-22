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
.tab-font {
	font-weight: bold;
	font-size: xx-large;
}
</style>



<div class="container">
	<div class="page-header" style="margin-top:0;">
		<h1>
			Personal Broadband Promotion Plans 
		</h1>
	</div>
	
	<%-- <img src="${ctx }/public/bootstrap3/images/persoanl-12months-unlimited-plan.png"  class="img-responsive" alt="persoanl 12months unlimited plan"> --%>

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
	
	<c:set var="adslPlans" value="${planMap['ADSL'] }"></c:set>
	<c:set var="vdslPlans" value="${planMap['VDSL'] }"></c:set>
	<c:set var="ufbPlans" value="${planMap['UFB'] }"></c:set>
	
	<div class="panel panel-success">
		
		<div class="panel-body">
			<div class="btn-group btn-group-justified">
				<div class="btn-group">
					<button type="button" class="btn btn-success" data-name="promotion_service" data-type="adsl" ><span class="tab-font">ADSL</span></button>
				</div>
				<div class="btn-group">
					<button type="button" class="btn btn-success" data-name="promotion_service" data-type="vdsl"><span class="tab-font">VDSL</span></button>
				</div>
				<div class="btn-group">
					<button type="button" class="btn btn-success" data-name="promotion_service" data-type="ufb"><span class="tab-font">UFB</span></button>
				</div>
			</div>
		</div>
		<div class="panel-body">
		
			<div class="adslContainer" style="display:none;">
				<c:choose>
					<c:when test="${fn:length(adslPlans) > 0 }">
					
					<c:forEach var="plan" items="${adslPlans }">
						<c:if test="${plan.original_price > 0 }">
							<div class="row">
								<div class="col-md-3">
									<div class="thumbnail" style="height:330px;">
										<div class="caption">
											<h4 class="alert alert-warning">Original Price</h4>
											<p class="text-center text-warning" style="position:relative;">
												<span style="font-size:40px;font-weight:bold;float:left;margin-left:35px;margin-top:45px;">$</span>	
												<span style="font-size:90px;font-weight:bold;text-decoration:line-through;"> 
													<fmt:formatNumber value="${plan.original_price }" type="number" pattern="##0" />
												</span>
											</p>
										</div>
									</div>
								</div>
								<div class="col-md-3">
									<div class="thumbnail" style="height:330px;">
										<div class="caption">
											<h4 class="alert alert-danger">Promotion Price</h4>
											<p class="text-center text-success" style="position:relative;">
												<span style="font-size:40px;font-weight:bold;float:left;margin-left:35px;margin-top:45px;">$</span>	
												<span style="font-size:90px;font-weight:bold;"> 
													<fmt:formatNumber value="${plan.plan_price }" type="number" pattern="##0" />
												</span>
											</p>
											<hr style="margin-top:0;">
											<p>
												<a class="btn btn-success btn-lg btn-block" data-id="${plan.id}" data-type="${plan.plan_type}" data-name="order">Order</a> 
											</p>
										</div>
									</div>
								</div>
								<div class="col-md-6">
									<div class="thumbnail alert alert-info" style="height:330px;">
										<div class="caption ">
											<h2 class="text-success">${plan.plan_name }</h2>
											<!-- desc -->${fn:replace(plan.plan_desc, '70px', '5px') }<!-- // end desc -->
										</div>
									</div>
								</div>
							</div>
						</c:if>
					</c:forEach>
					</c:when>
					<c:otherwise>
						<div class="alert alert-warning">
							Sorry, There are't no promotion plans on this broadband service!
						</div>
					</c:otherwise>
				</c:choose>
				
			</div>
			
			<div class="vdslContainer" style="display:none;">
				<c:choose>
					<c:when test="${fn:length(vdslPlans) > 0 }">
					
					<c:forEach var="plan" items="${vdslPlans }">
						<c:if test="${plan.original_price > 0 }">
							<div class="row">
								<div class="col-md-3">
									<div class="thumbnail" style="height:330px;">
										<div class="caption">
											<h4 class="alert alert-warning">Original Price</h4>
											<p class="text-center text-warning" style="position:relative;">
												<span style="font-size:40px;font-weight:bold;float:left;margin-left:35px;margin-top:45px;">$</span>	
												<span style="font-size:90px;font-weight:bold;text-decoration:line-through;"> 
													<fmt:formatNumber value="${plan.original_price }" type="number" pattern="##0" />
												</span>
											</p>
										</div>
									</div>
								</div>
								<div class="col-md-3">
									<div class="thumbnail" style="height:330px;">
										<div class="caption">
											<h4 class="alert alert-danger">Promotion Price</h4>
											<p class="text-center text-success" style="position:relative;">
												<span style="font-size:40px;font-weight:bold;float:left;margin-left:35px;margin-top:45px;">$</span>	
												<span style="font-size:90px;font-weight:bold;"> 
													<fmt:formatNumber value="${plan.plan_price }" type="number" pattern="##0" />
												</span>
											</p>
											<hr style="margin-top:0;">
											<p>
												<a class="btn btn-success btn-lg btn-block" data-id="${plan.id}" data-type="${plan.plan_type}" data-name="order">Order</a> 
											</p>
										</div>
									</div>
								</div>
								<div class="col-md-6">
									<div class="thumbnail alert alert-info" style="height:330px;">
										<div class="caption ">
											<h2 class="text-success">${plan.plan_name }</h2>
											<!-- desc -->${fn:replace(plan.plan_desc, '70px', '5px') }<!-- // end desc -->
										</div>
									</div>
								</div>
							</div>
						</c:if>
					</c:forEach>
					</c:when>
					<c:otherwise>
						<div class="alert alert-warning">
							Sorry, There are't no promotion plans on this broadband service!
						</div>
					</c:otherwise>
				</c:choose>
			</div>
			
			<div class="ufbContainer" style="display:none;">
			
				<c:choose>
					<c:when test="${fn:length(ufbPlans) > 0 }">
					
					<c:forEach var="plan" items="${ufbPlans }">
						<c:if test="${plan.original_price > 0 }">
							<div class="row">
								<div class="col-md-3">
									<div class="thumbnail" style="height:330px;">
										<div class="caption">
											<h4 class="alert alert-warning">Original Price</h4>
											<p class="text-center text-warning" style="position:relative;">
												<span style="font-size:40px;font-weight:bold;float:left;margin-left:35px;margin-top:45px;">$</span>	
												<span style="font-size:90px;font-weight:bold;text-decoration:line-through;"> 
													<fmt:formatNumber value="${plan.original_price }" type="number" pattern="##0" />
												</span>
											</p>
										</div>
									</div>
								</div>
								<div class="col-md-3">
									<div class="thumbnail" style="height:330px;">
										<div class="caption">
											<h4 class="alert alert-danger">Promotion Price</h4>
											<p class="text-center text-success" style="position:relative;">
												<span style="font-size:40px;font-weight:bold;float:left;margin-left:35px;margin-top:45px;">$</span>	
												<span style="font-size:90px;font-weight:bold;"> 
													<fmt:formatNumber value="${plan.plan_price }" type="number" pattern="##0" />
												</span>
											</p>
											<hr style="margin-top:0;">
											<p>
												<a class="btn btn-success btn-lg btn-block" data-id="${plan.id}" data-type="${plan.plan_type}" data-name="order">Order</a> 
											</p>
										</div>
									</div>
								</div>
								<div class="col-md-6">
									<div class="thumbnail alert alert-info" style="height:330px;">
										<div class="caption ">
											<h2 class="text-success">${plan.plan_name }</h2>
											<!-- desc -->${fn:replace(plan.plan_desc, '70px', '5px') }<!-- // end desc -->
										</div>
									</div>
								</div>
							</div>
						</c:if>
					</c:forEach>
					</c:when>
					<c:otherwise>
						<div class="alert alert-warning">
							Sorry, There are't no promotion plans on this broadband service!
						</div>
					</c:otherwise>
				</c:choose>
				
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
		<c:forEach var="plan" items="${planMap[type] }">
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
	
	$('a[data-name="order"]').click(function(){
		select_plan_id = $(this).attr('data-id');
		select_plan_type = $(this).attr('data-type');//console.log(select_plan_id);
		$('#checkResult').empty();
		$('#checkAddressModal').modal('show');
	});
	
	$('button[data-name="promotion_service"]').click(function(){
		var $btn = $(this);
		var type = $btn.attr('data-type');
		$('button[data-name="promotion_service"]').removeClass('active');
		$btn.addClass('active');
		$('div[class$="Container"]').hide();
		$('div[class="' + type + 'Container"]').show();
	});
	
	$('button[data-name="promotion_service"][data-type="${selectdType}"]').trigger('click');
})(jQuery);
</script>
<script src="https://maps.google.com/maps/api/js?sensor=false&libraries=places&region=NZ" type="text/javascript"></script>
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/autoCompleteAddress.js"></script>
<jsp:include page="footer-end.jsp" />