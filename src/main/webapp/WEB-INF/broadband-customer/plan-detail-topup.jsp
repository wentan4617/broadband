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
</style>

<div class="container">

	<!-- topup plans -->
	<div class="page-header">
		<h1>Choose your broadband</h1>
	</div>

	<!-- plan content -->
	<div class="panel panel-default">
		<div class="panel-body">
			<div class="row">
				<div class="col-md-8 col-md-offset-2">
					<div class="row">
						<div class="col-md-4">
							<a href="javascript:void(0);" class="thumbnail active" data-name="icon" data-id="adsl"> 
								<img src="${ctx }/public/bootstrap3/images/icon-adsl.gif" alt="...">
							</a>
						</div>
						<div class="col-md-4">
							<a href="javascript:void(0);" class="thumbnail" data-name="icon" data-id="vdsl"> 
							 	<img src="${ctx }/public/bootstrap3/images/icon-vdsl.gif" alt="...">
							</a>
						</div>
						<div class="col-md-4">
							<a href="javascript:void(0);" class="thumbnail" data-name="icon" data-id="ufb"> 
								<img src="${ctx }/public/bootstrap3/images/icon-ufb.gif" alt="...">
							</a>
						</div>
					</div>
					<div class="row">
					
						<!-- adsl ============================================================ -->
						<div class="alert alert-success" id="adsl-container">
							<h2>Standard Broadband</h2>
							<p>Fast reliable broadband available throughout New Zealand.</p>
							<hr/>
							<ul class="list-unstyled topup-list" style="margin-left:40px;">
								<li>
									<input type="radio" name="adsl-conn-check" checked="checked" /> &nbsp; 
									<strong>
										Free initial broadband setup includes a standard connection worth $ 
										<fmt:formatNumber value="${planMaps['ADSL'].plan_new_connection_fee }" type="number" pattern="##00" />
									</strong>
								</li>
							</ul>
							<hr/>
							<h4>Please choose top-up amount what you needed</h4>
							<hr/>
							<c:if test="${fn:length(planMaps['ADSL'].topups) > 0 }">
								
								<ul class="list-unstyled topup-list" style="margin-left:40px;">
									<c:forEach var="topup" items="${planMaps['ADSL'].topups }" varStatus="item">
										<li>
											<input type="radio" name="adsl-topup-check" ${item.first?'checked="checked"':''} value="${topup.id }"/> &nbsp; 
											<strong>${topup.topup_name }</strong>
										</li>
									</c:forEach>
								</ul>
								<hr/>
								<p class="text-right">
									<a data-id="${planMaps['ADSL'].id}" class="btn btn-success" id="adsl-purchase">Purchase</a>
								</p>
								
							</c:if>
							<c:if test="${fn:length(planMaps['ADSL'].topups) <= 0 }">
								<p>
									There are no top-up options.
								</p>
							</c:if>
							
						</div>
						
						<!-- vdsl ============================================================ -->
						<div class="alert alert-success" id="vdsl-container" style="display:none;">
							<h2>VDSL Broadband</h2>
							<p>Enjoy broadband that's 3x faster than standard broadband.</p>
							<hr/>
							<ul class="list-unstyled topup-list" style="margin-left:40px;">
								<li>
									<input type="radio" name="vdsl-conn-check" checked="checked" value="${topup.id }"/> &nbsp; 
									<strong>
										Free initial broadband setup includes a standard connection worth $ 
										<fmt:formatNumber value="${planMaps['VDSL'].plan_new_connection_fee }" type="number" pattern="##00" />
									</strong>
								</li>
							</ul>
							<hr/>
							<h4>Please choose top-up amount what you needed</h4>
							<hr/>
							<c:if test="${fn:length(planMaps['VDSL'].topups) > 0 }">
								<ul class="list-unstyled topup-list" style="margin-left:40px;">
									<c:forEach var="topup" items="${planMaps['VDSL'].topups }" varStatus="item">
										<li>
											<input type="radio" name="vdsl-topup-check" ${item.first?'checked="checked"':''} value="${topup.id }"/> &nbsp; 
											<strong>${topup.topup_name }</strong>
										</li>
									</c:forEach>
								</ul>
								<hr/>
								<p class="text-right">
									<a data-id="${planMaps['VDSL'].id}" class="btn btn-success" id="vdsl-purchase">Purchase</a>
								</p>
							</c:if>
							<c:if test="${fn:length(planMaps['VDSL'].topups) <= 0 }">
								<p>
									There are no top-up options.
								</p>
							</c:if>
							
						</div>
						
						
						<!-- ufb ============================================================ -->
						<div class="alert alert-success" id="ufb-container" style="display:none;">
							<h2>Ultra Fast Broadband</h2>
							<p>Ultra Fast Broadband is available in parts of New Zealand.</p>
							<hr/>
							<ul class="list-unstyled topup-list" style="margin-left:40px;">
								<li>
									<input type="radio" name="ufb-conn-check" checked="checked" /> &nbsp; 
									<strong>
										Free initial broadband setup includes a standard connection worth $ 
										<fmt:formatNumber value="${planMaps['UFB'].plan_new_connection_fee }" type="number" pattern="##00" />
									</strong>
								</li>
							</ul>
							<hr/>
							<h4>Please choose top-up amount what you needed</h4>
							<hr/>
							<c:if test="${fn:length(planMaps['UFB'].topups) > 0 }">
								<ul class="list-unstyled topup-list" style="margin-left:40px;">
									<c:forEach var="topup" items="${planMaps['UFB'].topups }" varStatus="item">
										<li>
											<input type="radio" name="ufb-topup-check" ${item.first?'checked="checked"':''} value="${topup.id }"/> &nbsp; 
											<strong>${topup.topup_name }</strong>
										</li>
									</c:forEach>
									
								</ul>
								<hr/>
								<p class="text-right">
									<a data-id="${planMaps['UFB'].id}" class="btn btn-success" id="ufb-purchase">Purchase</a>
								</p>
							</c:if>
							<c:if test="${fn:length(planMaps['UFB'].topups) <= 0 }">
								<p>
									There are no top-up options.
								</p>
							</c:if>
							
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
	src="${ctx}/public/bootstrap3/js/icheck.min.js"></script>
<script type="text/javascript">
	(function($) {

		$(':radio').iCheck({
			checkboxClass : 'icheckbox_square-green',
			radioClass : 'iradio_square-green'
		});

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
		
		$('input[name="adsl-topup-check"]').on('ifChecked', function(){
			var id = $('#adsl-purchase').attr('data-id');
			var topup_id = this.value;
			$('#adsl-purchase').attr('href', '${ctx}/order/' + id + '/topup/' + topup_id);
		});
		
		$('input[name="vdsl-topup-check"]').on('ifChecked', function(){
			var id = $('#vdsl-purchase').attr('data-id');
			var topup_id = this.value;
			$('#vdsl-purchase').attr('href', '${ctx}/order/' + id + '/topup/' + topup_id);
		});
		
		$('input[name="ufb-topup-check"]').on('ifChecked', function(){
			var id = $('#ufb-purchase').attr('data-id');
			var topup_id = this.value;
			$('#ufb-purchase').attr('href', '${ctx}/order/' + id + '/topup/' + topup_id);
		});
		
		$('#adsl-purchase').attr('href', '${ctx}/order/' + $('#adsl-purchase').attr('data-id') + '/topup/' + $('input[name="adsl-topup-check"]:checked').val());
		$('#vdsl-purchase').attr('href', '${ctx}/order/' + $('#vdsl-purchase').attr('data-id') + '/topup/' + $('input[name="vdsl-topup-check"]:checked').val());
		$('#ufb-purchase').attr('href', '${ctx}/order/' + $('#ufb-purchase').attr('data-id') + '/topup/' + $('input[name="ufb-topup-check"]:checked').val());

	})(jQuery);
</script>
<jsp:include page="footer-end.jsp" />