<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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

	<!-- adsl -->
	<div class="page-header" style="margin-top:0;margin-bottom:5px;">
		<h3>
			<span class="label label-primary" id="adsl">ADSL + Home Phone Line</span>
		</h3>
	</div>
	<div class="row">
		<c:forEach var="plan" items="${planMaps['ADSL'] }" varStatus="item">
			<div class="col-lg-4">
				<div class="panel panel-primary">
					<div class="panel-heading">
						<h2 class="panel-title text-center">
							<strong style="font-size:30px;float:left;margin-left:70px;margin-right:-50px;margin-top:25px;">$</strong>
							<strong style="font-size:60px;"> 
								<fmt:formatNumber value="${plan.plan_price} " type="number" pattern="##0" />
							</strong>
							/ mth
						</h2>
					</div>
				 	<div class="panel-body">
				 		<!-- desc -->${plan.plan_desc }<!-- // end desc -->

					   	<p class="text-center">
							<a class="btn btn-success btn-lg btn-block" id="adsl-purchase" data-id="${plan.id}" data-type="adsl" data-name="purchase">Purchase</a> 
						</p>
						
				  	</div>
				  	<div class="panel-footer">
						<h2 class="text-center" style="margin:0;">
							<strong>${plan.data_flow } GB</strong>	
						</h2>
					</div>
				</div>
			</div>	
		</c:forEach>
	</div>


	<!-- vdsl -->
	<div class="page-header" style="margin-top:0;margin-bottom:5px;">
		<h3>
			<span class="label label-info" id="vdsl">VDSL + Home Phone Line</span>
		</h3>
	</div>
	<div class="row">
		<c:forEach var="plan" items="${planMaps['VDSL'] }" varStatus="item">
			<div class="col-lg-4">
				<div class="panel panel-info">
					<div class="panel-heading">
						<h2 class="panel-title text-center">
							<strong style="font-size:30px;float:left;margin-left:70px;margin-right:-50px;margin-top:25px;">$</strong>
							<strong style="font-size:60px;"> 
								<fmt:formatNumber value="${plan.plan_price} " type="number" pattern="##0" />
							</strong>
							/ mth
						</h2>
					</div>
				 	<div class="panel-body">
						<!-- desc -->${plan.plan_desc }<!-- // end desc -->
					   	<p class="text-center">
							<a class="btn btn-success btn-lg btn-block" id="vdsl-purchase" data-id="${plan.id}" data-type="vdsl" data-name="purchase">Purchase</a> 
						</p>
				  	</div>
				  	<div class="panel-footer">
						<h2 class="text-center" style="margin:0;">
							<strong>${plan.data_flow } GB</strong>	
						</h2>
					</div>
				</div>
			</div>	
		</c:forEach>
	</div>


	<!-- ufb -->
	<div class="page-header" style="margin-top:0;margin-bottom:5px;">
		<h3>
			<span class="label label-danger" id="ufb">UFB + Home Phone Line</span>
		</h3>
	</div>
	<div class="row">
		<c:forEach var="plan" items="${planMaps['UFB'] }" varStatus="item">
			<div class="col-lg-4">
				<div class="panel panel-danger">
					<div class="panel-heading">
						<h2 class="panel-title text-center">
							<strong style="font-size:30px;float:left;margin-left:70px;margin-right:-50px;margin-top:25px;">$</strong>
							<strong style="font-size:60px;"> 
								<fmt:formatNumber value="${plan.plan_price} " type="number" pattern="##0" />
							</strong>
							/ mth
						</h2>
					</div>
				 	<div class="panel-body">
						<!-- desc -->${plan.plan_desc }<!-- // end desc -->
					   	<p class="text-center">
							<a class="btn btn-success btn-lg btn-block" id="ufb-purchase" data-id="${plan.id}" data-type="ufb" data-name="purchase">Purchase</a> 
							<%-- ${ctx }/order/${plan.id} --%>
						</p>
				  	</div>
				  	<div class="panel-footer">
						<h2 class="text-center" style="margin:0;">
							<strong>${plan.data_flow } GB</strong>	
						</h2>
					</div>
				</div>
			</div>	
		</c:forEach>
	</div>
</div>

<!-- Check Address Modal -->
<div class="modal fade" id="checkAddressModal" tabindex="-1" role="dialog" aria-labelledby="checkAddressModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="margin-top:55px;">
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
			
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal -->

<div id="map_canvas" style="width:720px;height:600px;display:none;"></div>
<script type="text/html" id="result_tmpl">
<jsp:include page="resultAddressCheck.html" />
</script>

<jsp:include page="footer.jsp" />
<jsp:include page="script.jsp" />
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/holder.js"></script>
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/jTmpl.js"></script>
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/spin.min.js"></script>
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/ladda.min.js"></script>
<script type="text/javascript">
(function($){
	
	var select_plan_id = "";
	var select_plan_type = "";
	
	$('#goCheck').click(function(){
		var address = $('#address').val();
		address = $.trim(address.replace(/[\/]/g,' ').replace(/[\\]/g,' ')); //console.log(address);
		if (address != '') {
			var l = Ladda.create(this);
		 	l.start();
			$.get('${ctx}/address/check/' + address, function(broadband){
				broadband.href = '${ctx}/order/' + select_plan_id;
				broadband.type = select_plan_type;
				$('#checkResult').html(tmpl('result_tmpl', broadband));
				$('a[data-toggle="tooltip"]').tooltip();
				$('#continue-selected-plan').click(function(){
					$.get('${ctx}/do/service/', function(){
						window.location.href = broadband.href;
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