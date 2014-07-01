<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<jsp:include page="header.jsp" />

<style>

.affix {
	width: 263px;
	top: 0;
}
.nav-pills>li>a {
	border-radius: 0px;
}
.nav-pills>li:first-child>a {
	border-radius: 4px 4px 0 0;
}
.nav-pills>li:last-child>a {
	border-radius: 0 0 4px 4px;
}
.home-title {
	font-size:36px;
}
</style>

<div class="container">
	<div class="row" style="margin-bottom:20px;">
		
		<div class="col-md-9 col-xs-12 col-sm-12">
			<section id="voucher">
				<div class="page-header">
					<div class="home-title">
						Voucher Checking
					</div>
				</div>
				
				<!-- Voucher Details -->
				<div class="panel panel-success">
					<div class="panel-body">
						<div id="alertContainer"></div>
						
						<div id="tempAlertSuccessContainer" style="display:none;">
							<div id="alert-success" class="alert alert-success alert-dismissable fade in" style="display:none;">
								<button type="button" class="close" data-dismiss="alert" aria-hidden="true" >&times;</button>
								<span id="text-success"></span>
							</div>
						</div>
						<div id="tempAlertErrorContainer" style="display:none;">
							<div id="alert-error" class="alert alert-danger alert-dismissable fade in" style="display:none;">
								<button type="button" class="close" data-dismiss="alert" aria-hidden="true" >&times;</button>
								<span id="text-error"></span>
							</div>
						</div>
						
						<form class="form-horizontal" id="voucherForm">
							<div class="form-group">
								<h4 class="col-md-12">Please provide us your CyberPark voucher's details to check its availability</h4>
							</div>
							<div class="form-group">
								<div class="col-md-12">
									<img style="width:360px; height:200px; border-radius:30px 30px 30px 30px; box-shadow:5px 5px 5px rgba(0,0,0,.6);" title="Material" src="${ctx}/public/bootstrap3/images/top_up_card_30.png">
								</div>
							</div>
							<div class="form-group">
								<label for="serial_number" class="control-label col-md-2">Serial No*</label>
								<div class="col-md-6">
									<input type="text" id="serial_number" class="form-control" data-error-field />
								</div>
							</div>
							<div class="form-group">
								<label for="pin_number" class="control-label col-md-2">PIN No*</label>
								<div class="col-md-6">
									<input type="text" id="pin_number" class="form-control" data-error-field />
								</div>
							</div>
							<div class="form-group">
								<label for="code" class="control-label col-md-2">Authenticode*</label>
								<div class="col-md-6">
									<input id="code" class="form-control" placeholder="Input below box's characters" data-error-field/>
								</div>
							</div>
							<div class="form-group">
								<label for="code" class="control-label col-md-2"></label>
								<div class="col-md-3">
									<img id="codeImage" style="cursor:pointer;" alt="Verification Code" src="kaptcha.jpg" />
								</div>
								<div class="col-md-5">
									<a href="javascript:void(0);" id="codeLink">Not clear? Change other code.</a>
								</div>
							</div>
							<hr/>
							<div class="form-group">
								<div class="col-md-2"></div>
								<div class="col-md-3">
									<button type="button" data-loading-text="loading..." class="btn btn-success btn-lg btn-block" id="submit-btn">Submit Request</button>
								</div>
							</div>
						</form>
						
					</div>
				</div>
				<address>
					<p>
						<strong>Any questions please don't hesitate to contact us, CyberPark Limited.</strong> 
					</p>
					<p>
						${cyberpark.address}
					</p>
					<p>
						Give us a call on <a href="javascript:void(0);">${cyberpark.telephone}</a>
					</p>
					<p>
						Email Address <a href="mailto:#">${cyberpark.company_email }</a>
					</p>
				</address>
			</section>
		</div>
		<div class="col-md-3 hidden-xs hidden-sm">
			 <div class="navbar-example panel panel-default" style="margin-top:40px;" data-spy="affix" data-offset-top="150">
				<ul class="nav nav-pills nav-stacked">
					<li>
						<a href="#voucher">Voucher Checking</a>
					</li>
				</ul>
			</div>
		</div>
	</div>
</div>

<jsp:include page="footer.jsp" />
<jsp:include page="script.jsp" />
<script type="text/javascript">
(function($){
	$('body').scrollspy({ target: '.navbar-example' });
	
	$(document).keypress(function(e){
		if ($('#voucherForm input:focus').length > 0 && event.keyCode == 13) {
			$('#submit-btn').trigger('click');
		}
	});
	
	$('#submit-btn').on("click", function(){
		var $btn = $(this);
		$btn.button('loading');
		var data = {
			code: $('#code').val()
			,serial_number: $('#serial_number').val()
			,card_number: $('#pin_number').val()
		};
		$.post('${ctx}/voucher', data, function(json){
			$('#codeImage').attr('src', 'kaptcha.jpg?' + Math.floor(Math.random()*100));
			$.jsonValidation(json, 'right');
			$('.form-control').val('');
		}, 'json').always(function () {
			$btn.button('reset');
	    });
	});
	
	$('#codeImage,#codeLink').click(function(){
		$('#codeImage').attr('src', 'kaptcha.jpg?' + Math.floor(Math.random()*100));
	});
})(jQuery);
</script>
<jsp:include page="footer-end.jsp" />