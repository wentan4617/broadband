<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>  

<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<jsp:include page="header.jsp" />

<style>
.nav-pills>li.active>a, .nav-pills>li.active>a:hover, .nav-pills>li.active>a:focus {
color: #fff;
background-color: #5cb85c;
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
	
	
	<div class="row">
		<div class="col-md-1"></div>
		<div class="col-md-5">
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
						<c:forEach var="plan" items="${planMaps['ADSL'] }" varStatus="item">
							<a href="javascript:void(0);" data-name="a-adsl" data-id="${plan.id }" class="btn ${item.first?'btn-success active':'btn-default' }">${plan.data_flow } GB</a>
						</c:forEach>
					</div>
					<c:forEach var="plan" items="${planMaps['ADSL'] }" varStatus="item">
						<div style="display:${!item.first?'none':'block'}" data-name="p-adsl" data-id="${plan.id }" >
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
						<a class="btn btn-success" id="adsl-purchase" data-id="" data-name="purchase">Purchase</a> 
					</p>
				</div>
			</div>
		</div>
		<div class="col-md-5">
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
						<a class="btn btn-success" id="vdsl-purchase" data-id="" data-name="purchase">Purchase</a> 
					</p>
				</div>
			</div>
		</div>
		<div class="col-md-1"></div>
		<%-- <div class="col-md-4">
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


<jsp:include page="footer.jsp" />
<jsp:include page="script.jsp" />
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/holder.js"></script>
<script type="text/javascript">
(function($){
	
	$('a[data-name="a-adsl"]').click(function(){
		
		$('a[data-name="a-adsl"]').removeClass().addClass('btn btn-default');
		$(this).addClass('btn btn-success active');
		
		$('div[data-name="p-adsl"]').hide();
		var id = $(this).attr('data-id');
		$('div[data-id="' + id + '"]').show();
		
		$('#adsl-purchase').attr('href', '${ctx}/order/' + id);
	});
	
	$('a[data-name="a-vdsl"]').click(function(){
			
		$('a[data-name="a-vdsl"]').removeClass().addClass('btn btn-default');
		$(this).addClass('btn btn-success active');
		
		$('div[data-name="p-vdsl"]').hide();
		var id = $(this).attr('data-id');
		$('div[data-id="' + id + '"]').show();
		
		$('#vdsl-purchase').attr('href', '${ctx}/order/' + id);
	});
		
	$('a[data-name="a-ufb"]').click(function(){
		
		$('a[data-name="a-ufb"]').removeClass().addClass('btn btn-default');
		$(this).addClass('btn btn-success active');
		
		$('div[data-name="p-ufb"]').hide();
		var id = $(this).attr('data-id');
		$('div[data-id="' + id + '"]').show();
		
		$('#ufb-purchase').attr('href', '${ctx}/order/' + id);
	});
	
	$('#adsl-purchase').attr('href', '${ctx}/order/' + $('a[data-name="a-adsl"][class="btn btn-success active"]').attr('data-id'));
	$('#vdsl-purchase').attr('href', '${ctx}/order/' + $('a[data-name="a-vdsl"][class="btn btn-success active"]').attr('data-id'));
	$('#ufb-purchase').attr('href', '${ctx}/order/' + $('a[data-name="a-ufb"][class="btn btn-success active"]').attr('data-id'));

	
	
})(jQuery);
</script>
<jsp:include page="footer-end.jsp" />