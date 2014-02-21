<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>  

<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<jsp:include page="header.jsp" />


<div class="container">

	<!-- naked plans -->
	<div class="page-header">
		<h1>
			Internet | Plans and pricing <small>Please choose what you need</small>
		</h1>
	</div>
	<div class="row">
		<div class="col-md-4">
			<div class="thumbnail">
				<div class="caption">
					<img class="pull-right" data-src="holder.js/120x86" alt="...">
					<h3><strong class="text-success">ADSL BROADBAND</strong></h3>
					<hr/>
					<div class="well">
						The broadband standard in NZ. 
						Fast internet over your copper 
						phone line.
					</div>
					<h4><strong class="text-success">How much data do you need?</strong></h4>
					
					<div class="btn-group btn-group-justified">
						<c:forEach var="plan" items="${planMaps['ADSL'] }" varStatus="item">
							<a href="javascript:void(0);" data-name="a-adsl" data-id="${plan.id }" class="btn ${item.first?'btn-success active':'btn-default' }">${plan.data_flow } GB</a>
						</c:forEach>
					</div>
					<c:forEach var="plan" items="${planMaps['ADSL'] }" varStatus="item">
						<p style="display:${!item.first?'none':'block'}" data-name="p-adsl" data-id="${plan.id }" class="text-center text-success" style="position:relative;margin-bottom:0;">
							<strong style="font-size:60px;float:left;margin-left:70px;margin-right:-20px;margin-top:35px;">$</strong>
							<strong style="font-size:100px;"> 
								<fmt:formatNumber value="${plan.plan_price} " type="number" pattern="##00" />
							</strong>
							/ mth
						</p>
					</c:forEach>
					<hr style="margin-top:0;"/>
					<p class="text-success"><strong>How fast is ADSL?</strong></p>
					<ul class="list-unstyled" style="height:80px;">
						<li><span class="glyphicon glyphicon glyphicon-ok-sign" style="padding:0 20px;"></span>Easy to install</li>
						<li><span class="glyphicon glyphicon glyphicon-ok-sign" style="padding:0 20px;"></span>Great for everyday use</li>	
					</ul>
					<hr/>
					<ul class="list-unstyled" style="height:40px;" >
						
					</ul>
					<hr/>
					<p class="text-center">
						<a href="${ctx }/order/4" class="btn btn-success" role="button">Purchase</a> 
					</p>
				</div>
			</div>
		</div>
		<div class="col-md-4">
			<div class="thumbnail">
				<div class="caption">
					<img class="pull-right" data-src="holder.js/120x86" alt="...">
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
						<p style="display:${!item.first?'none':'block'}" data-name="p-vdsl" data-id="${plan.id }" class="text-center text-success" style="position:relative;margin-bottom:0;">
							<strong style="font-size:60px;float:left;margin-left:70px;margin-right:-20px;margin-top:35px;">$</strong>
							<strong style="font-size:100px;"> 
								<fmt:formatNumber value="${plan.plan_price} " type="number" pattern="##00" />
							</strong>
							/ mth
						</p>
					</c:forEach>
					<hr style="margin-top:0;"/>
					<p class="text-success"><strong>Why VDSL?</strong></p>
					<ul class="list-unstyled" style="height:80px;">
						<li><span class="glyphicon glyphicon-ok-sign" style="padding:0 20px;"></span>Straightforward to install</li>
						<li><span class="glyphicon glyphicon-ok-sign" style="padding:0 20px;"></span>Better for video and online games</li>	
					</ul>
					<hr/>
					<ul class="list-unstyled">
						<li><span class="glyphicon glyphicon-cloud-download" style="padding:0 20px;"></span>Between 15-70 Mbps downstream+</li>
						<li><span class="glyphicon glyphicon-cloud-download" style="padding:0 20px;"></span>Between 5-10 Mbps upstream+</li>	
					</ul>
					<hr/>
					<p class="text-center">
						<a href="${ctx }/order/4" class="btn btn-success" role="button">Purchase</a> 
					</p>
				</div>
			</div>
		</div>
		<div class="col-md-4">
			<div class="thumbnail">
				<div class="caption">
					<img class="pull-right" data-src="holder.js/120x86" alt="..." >
					<h3 class="clearfix"><strong class="text-success">ULTRA FIBRE&copy;</strong></h3>
					<hr style="margin-top:-2px;"/>
					<div class="well">
						The broadband standard in NZ. 
						Fast internet over your copper 
						phone line.
					</div>
					<h4><strong class="text-success">How much data do you need?</strong></h4>
					<div class="btn-group btn-group-justified">
						<c:forEach var="plan" items="${planMaps['UFB'] }" varStatus="item">
							<a href="javascript:void(0);" data-name="a-ufb" data-id="${plan.id }" class="btn ${item.first?'btn-success active':'btn-default' }">${plan.data_flow } GB</a>
						</c:forEach>
					</div>
					<c:forEach var="plan" items="${planMaps['UFB'] }" varStatus="item">
						<p style="display:${!item.first?'none':'block'}" data-name="p-ufb" data-id="${plan.id }" class="text-center text-success" style="position:relative;margin-bottom:0;">
							<strong style="font-size:60px;float:left;margin-left:70px;margin-right:-20px;margin-top:35px;">$</strong>
							<strong style="font-size:100px;"> 
								<fmt:formatNumber value="${plan.plan_price} " type="number" pattern="##00" />
							</strong>
							/ mth
						</p>
					</c:forEach>
					<hr style="margin-top:0;"/>
					<p class="text-success"><strong>Why Fibre?</strong></p>
					<ul class="list-unstyled" style="height:80px;">
						<li><span class="glyphicon glyphicon glyphicon-ok-sign" style="padding:0 20px;"></span>Huge jump in speed and capacity</li>
						<li><span class="glyphicon glyphicon glyphicon-ok-sign" style="padding:0 20px;"></span>Provides the best available consistency of </li>	
						<li style="padding:0 55px;">speed to your home</li>	
					</ul>
					<hr/>
					<ul class="list-unstyled">
						<li><span class="glyphicon glyphicon-cloud-download" style="padding:0 20px;"></span>Up to 30 Mbps downstream+</li>
						<li><span class="glyphicon glyphicon-cloud-download" style="padding:0 20px;"></span>Up to 10 Mbps upstream+</li>	
					</ul>
					<hr/>
					<p class="text-center">
						<a href="${ctx }/order/4" class="btn btn-success" role="button">Purchase</a> 
					</p>
				</div>
			</div>
		</div>
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
		
		$('p[data-name="p-adsl"]').hide();
		var id = $(this).attr('data-id');
		$('p[data-id="' + id + '"]').show();
	});
	
	$('a[data-name="a-vdsl"]').click(function(){
			
			$('a[data-name="a-vdsl"]').removeClass().addClass('btn btn-default');
			$(this).addClass('btn btn-success active');
			
			$('p[data-name="p-vdsl"]').hide();
			var id = $(this).attr('data-id');
			$('p[data-id="' + id + '"]').show();
		});
		
	$('a[data-name="a-ufb"]').click(function(){
		
		$('a[data-name="a-ufb"]').removeClass().addClass('btn btn-default');
		$(this).addClass('btn btn-success active');
		
		$('p[data-name="p-ufb"]').hide();
		var id = $(this).attr('data-id');
		$('p[data-id="' + id + '"]').show();
	});
	
	
})(jQuery);
</script>
<jsp:include page="footer-end.jsp" />