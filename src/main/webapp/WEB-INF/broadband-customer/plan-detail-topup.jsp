<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<jsp:include page="header.jsp" />

<style>
.topup-list li {
	padding: 10px 20px;
}
.nav-pills>li.active>a, .nav-pills>li.active>a:hover, .nav-pills>li.active>a:focus {
color: #fff;
background-color: #5cb85c;
}
</style>

<div class="container">
 
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
	<!-- plan content -->
	<div class="panel panel-default">
		<div class="panel-body">
			<div class="row">
				<div class="col-md-8 col-md-offset-2">
					<div class="row">
						<div class="col-md-2"></div>
						<div class="col-md-4">
							<a href="javascript:void(0);" class="thumbnail active" data-name="icon" data-id="adsl"> 
								<img src="${ctx }/public/bootstrap3/images/icon-adsl.png" alt="...">
							</a>
						</div>
						<div class="col-md-4">
							<a href="javascript:void(0);" class="thumbnail" data-name="icon" data-id="vdsl"> 
							 	<img src="${ctx }/public/bootstrap3/images/icon-vdsl.png" alt="...">
							</a>
						</div>
						<div class="col-md-2"></div>
					</div>
					<div class="row">
					
						<!-- adsl ============================================================ -->
						<div class="alert alert-success" id="adsl-container">
							<h2>Standard Broadband</h2>
							<p>Fast reliable broadband available throughout New Zealand.</p>
							<hr/>
							<div class="well">
								<strong>
									Free initial broadband setup includes a standard connection worth $ 
									<fmt:formatNumber value="${planMaps['ADSL'][0].plan_new_connection_fee }" type="number" pattern="##00" />
								</strong>
							</div>
							<hr/>
							<h4>Please choose top-up amount what you needed ? 
								&nbsp;&nbsp;&nbsp;
								<select name="adsl-topup-check" class="selectpicker" data-style="btn-success">
									<option value="20">Top up $ 20</option>
									<option value="30">Top up $ 30</option>
									<option value="50">Top up $ 50</option>
									<option value="80">Top up $ 80</option>
									<option value="100">Top up $ 100</option>
									<option value="120">Top up $ 120</option>
									<option value="150">Top up $ 150</option>
									<option value="180">Top up $ 180</option>
								</select>
							</h4>
							<hr/>
							<p class="text-right">
								<a data-id="${planMaps['ADSL'][0].id}" class="btn btn-success btn-lg" id="adsl-purchase">Continue</a>
							</p>
							
						</div>
						
						<!-- vdsl ============================================================ -->
						<div class="alert alert-success" id="vdsl-container" style="display:none;">
							<h2>VDSL Broadband</h2>
							<p>Enjoy broadband that's 3x faster than standard broadband.</p>
							<hr/>
							<div class="well">
								<strong>
									Free initial broadband setup includes a standard connection worth $ 
									<fmt:formatNumber value="${planMaps['VDSL'][0].plan_new_connection_fee }" type="number" pattern="##00" />
								</strong>
							</div>
							
							<hr/>
							<h4>Please choose top-up amount what you needed ? 
								&nbsp;&nbsp;&nbsp;
								<select name="vdsl-topup-check" class="selectpicker" data-style="btn-success">
									<option value="20">Top up $ 20</option>
									<option value="30">Top up $ 30</option>
									<option value="50">Top up $ 50</option>
									<option value="80">Top up $ 80</option>
									<option value="100">Top up $ 100</option>
									<option value="120">Top up $ 120</option>
									<option value="150">Top up $ 150</option>
									<option value="180">Top up $ 180</option>
								</select>
							</h4>
							
							<hr/>
							<p class="text-right">
								<a data-id="${planMaps['VDSL'][0].id}" class="btn btn-success btn-lg" id="vdsl-purchase">Continue</a>
							</p>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>


<jsp:include page="footer.jsp" />
<jsp:include page="script.jsp" />
<script type="text/javascript"
	src="${ctx}/public/bootstrap3/js/holder.js"></script>
<script type="text/javascript"
	src="${ctx}/public/bootstrap3/js/bootstrap-select.min.js"></script>
<script type="text/javascript">
(function($) {
	
	$('.selectpicker').selectpicker();

	$('a[data-name="icon"]').click(function(){
		var val = $(this).attr('data-id');
		$('a[data-name="icon"]').removeClass('active');
		$(this).addClass('active');
		//alert(val);
		if (val === 'adsl') {
			$('#adsl-container').show();
			$('#vdsl-container').hide();
			$('#ufb-container').hide();
		} else if (val === 'vdsl') {
			$('#vdsl-container').show();
			$('#adsl-container').hide();
			$('#ufb-container').hide();
		} else if (val === 'ufb') {
			$('#ufb-container').show();
			$('#adsl-container').hide();
			$('#vdsl-container').hide();
		}
	});
	
	$('select[name="adsl-topup-check"]').on('change', function(){
		var id = $('#adsl-purchase').attr('data-id');
		var topup_id = this.value;
		$('#adsl-purchase').attr('href', '${ctx}/order/' + id + '/topup/' + topup_id);
	});
	
	$('select[name="vdsl-topup-check"]').on('change', function(){
		var id = $('#vdsl-purchase').attr('data-id');
		var topup_id = this.value;
		$('#vdsl-purchase').attr('href', '${ctx}/order/' + id + '/topup/' + topup_id);
	});
	
	$('select[name="ufb-topup-check"]').on('change', function(){
		var id = $('#ufb-purchase').attr('data-id');
		var topup_id = this.value;
		$('#ufb-purchase').attr('href', '${ctx}/order/' + id + '/topup/' + topup_id);
	});
	
	$('#adsl-purchase').attr('href', '${ctx}/order/' + $('#adsl-purchase').attr('data-id') + '/topup/' + $('select[name="adsl-topup-check"]').val());
	$('#vdsl-purchase').attr('href', '${ctx}/order/' + $('#vdsl-purchase').attr('data-id') + '/topup/' + $('select[name="vdsl-topup-check"]').val());
	$('#ufb-purchase').attr('href', '${ctx}/order/' + $('#ufb-purchase').attr('data-id') + '/topup/' + $('select[name="ufb-topup-check"]').val());

})(jQuery);
</script>
<jsp:include page="footer-end.jsp" />