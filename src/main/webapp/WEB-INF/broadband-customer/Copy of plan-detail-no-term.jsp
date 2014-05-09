<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
.modal {
	z-index: 140;
}
.modal-backdrop{
	z-index: 130;
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
	
	<div class="row">
		<div class="col-md-12">
			<div class="thumbnail">
				<div class="caption">
					<img class="pull-right" src="${ctx }/public/bootstrap3/images/icon_most-popular.png" alt="...">
					<h3 style="height:54px;"><strong class="text-success">ADSL BROADBAND</strong></h3>
					<hr/>
					<div class="well">
						The broadband standard in NZ. 
						Fast Internet over your copper 
						phone line.
					</div>
					<h4><strong class="text-success">How much data do you need?</strong></h4>
					
					<div class="btn-group btn-group-justified">
						<c:forEach var="plan" items="${plans}" varStatus="item">
							<a href="javascript:void(0);" data-name="a-adsl" data-id="${plan.id }" class="btn ${item.first?'btn-success active':'btn-default' }">${plan.data_flow } GB</a>
						</c:forEach>
					</div>
					<c:forEach var="plan" items="${plans}" varStatus="item">
						<div style="display:${!item.first?'none':'block'}" data-name="p-adsl" data-id="${plan.id }" >
							<p class="text-center text-success" style="position:relative;margin-bottom:0;">
								<strong style="font-size:60px;float:left;margin-left:70px;margin-right:-20px;margin-top:35px;">$</strong>
								<strong style="font-size:100px;"> 
									<fmt:formatNumber value="${plan.plan_price} " type="number" pattern="##0" />
								</strong>
								/ mth
							</p>
							<hr style="margin-top:0;"/>
							<h3 class="text-success">${plan.plan_name }</h3>
							<!-- desc -->${plan.plan_desc }<!-- // end desc -->
						</div>
						
					</c:forEach>
					<hr/>
					<p class="text-center">
						<a class="btn btn-success btn-lg btn-block" id="adsl-purchase" data-name="purchase" data-type="adsl">Purchase</a> 
					</p>
				</div>
			</div>
		</div>
		
		<%--<div class="col-md-5">
			<div class="thumbnail">
				<div class="caption">
					<img class="pull-right" src="${ctx }/public/bootstrap3/images/icon_super-fast.png" alt="...">
					<h3><strong class="text-success">ULTRA VDSL&trade; BROADBAND</strong></h3>
					<hr/>
					<div class="well">
						Like ADSL Broadband, using the copper phone line only faster.
					</div>
					<h4><strong class="text-success">How much data do you need?</strong></h4>
					<div class="btn-group btn-group-justified">
						<c:forEach var="plan" items="${planMaps['VDSL'] }" varStatus="item">
							<a href="javascript:void(0);" data-name="a-vdsl" data-id="${plan.id }" class="btn ${item.first?'btn-success active':'btn-default' }">${plan.data_flow } GB</a>
						</c:forEach>
					</div>
					<c:forEach var="plan" items="${planMaps['VDSL'] }" varStatus="item">
						<div style="display:${!item.first?'none':'block'}" data-name="p-vdsl" data-id="${plan.id }" >
							<p class="text-center text-success" style="position:relative;margin-bottom:0;">
								<strong style="font-size:60px;float:left;margin-left:70px;margin-right:-20px;margin-top:35px;">$</strong>
								<strong style="font-size:100px;"> 
									<fmt:formatNumber value="${plan.plan_price} " type="number" pattern="##0" />
								</strong>
								/ mth
							</p>
							<hr style="margin-top:0;"/>
							<h3 class="text-success">${plan.plan_name }</h3>
							<!-- desc -->${plan.plan_desc }<!-- // end desc -->
						</div>
					</c:forEach>
					<hr/>
					<p class="text-center">
						<a class="btn btn-success btn-lg btn-block" id="vdsl-purchase" data-name="purchase" data-type="vdsl">Purchase</a> 
					</p>
				</div>
			</div>
		</div>
		<div class="col-md-1"></div>
		 <div class="col-md-4">
			<div class="thumbnail">
				<div class="caption">
					<img class="pull-right" src="${ctx }/public/bootstrap3/images/icon_fastest.png" alt="..." >
					<h3 class="clearfix"><strong class="text-success">ULTRA FIBRE&copy;</strong></h3>
					<hr style="margin-top:-2px;"/>
					<div class="well">
						The broadband standard in NZ. 
						Fast Internet over your copper 
						phone line.
					</div>
					<h4><strong class="text-success">How much data do you need?</strong></h4>
					<div class="btn-group btn-group-justified">
						<c:forEach var="plan" items="${planMaps['UFB'] }" varStatus="item">
							<a href="javascript:void(0);" data-name="a-ufb" data-id="${plan.id }" class="btn ${item.first?'btn-success active':'btn-default' }">${plan.data_flow } GB</a>
						</c:forEach>
					</div>
					<c:forEach var="plan" items="${planMaps['UFB'] }" varStatus="item">
						<div style="display:${!item.first?'none':'block'}" data-name="p-ufb" data-id="${plan.id }" >
							<p class="text-center text-success" style="position:relative;margin-bottom:0;">
								<strong style="font-size:60px;float:left;margin-left:70px;margin-right:-20px;margin-top:35px;">$</strong>
								<strong style="font-size:100px;"> 
									<fmt:formatNumber value="${plan.plan_price} " type="number" pattern="##00" />
								</strong>
								/ mth
							</p>
							<hr style="margin-top:0;"/>
							<h3 class="text-success">${plan.plan_name }</h3>
							<!-- desc -->${plan.plan_desc }<!-- // end desc -->
						</div>
					</c:forEach>
					
					<hr/>
					<p class="text-center">
						<a class="btn btn-success" id="ufb-purchase" data-id="" data-name="purchase">Purchase</a> 
					</p>
				</div>
			</div>
		</div> --%>
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
	
	$('a[data-name="a-adsl"]').click(function(){
		$('a[data-name="a-adsl"]').removeClass().addClass('btn btn-default');
		$(this).addClass('btn btn-success active');
		$('div[data-name="p-adsl"]').hide();
		var id = $(this).attr('data-id');
		$('div[data-id="' + id + '"]').show();
		$('#adsl-purchase').attr('data-id', id);
	});
	
	$('a[data-name="a-vdsl"]').click(function(){
		$('a[data-name="a-vdsl"]').removeClass().addClass('btn btn-default');
		$(this).addClass('btn btn-success active');
		$('div[data-name="p-vdsl"]').hide();
		var id = $(this).attr('data-id');
		$('div[data-id="' + id + '"]').show();
		$('#vdsl-purchase').attr('data-id', id);
	});
	
	$('#adsl-purchase,#vdsl-purchase').click(function(){
		select_plan_id = $(this).attr('data-id');
		select_plan_type = $(this).attr('data-type');//console.log(select_plan_id);
		$('#checkResult').empty();
		$('#checkAddressModal').modal('show');
	});
	
	$('#adsl-purchase').attr('data-id', $('a[data-name="a-adsl"][class="btn btn-success active"]').attr('data-id'));
	$('#vdsl-purchase').attr('data-id', $('a[data-name="a-vdsl"][class="btn btn-success active"]').attr('data-id'));

})(jQuery);
</script>
<script src="https://maps.google.com/maps/api/js?sensor=false&libraries=places&region=NZ" type="text/javascript"></script>
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/autoCompleteAddress.js"></script>
<jsp:include page="footer-end.jsp" />