<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<jsp:include page="header.jsp" />
<jsp:include page="alert.jsp" />

<div class="container" style="padding-top: 20px; padding-bottom: 150px;">
	<div class="row">
		<div class="col-md-4 col-md-offset-4">
			<div class="panel panel-success">
				<div class="panel-heading">CyberPark Customer Forgotten Password</div>
				<div class="panel-body">
					
					<form class="form-horizontal">
						<div class="form-group">
							<div class="col-sm-12">
								<ul class="list-inline topup-list" style="margin: 5px 0 0 0;">
									<li>
										<span data-name="email" data-holder="e.g. cyberpark@gmail.co.nz">
											<input type="radio" name="type" value="email" checked="checked" /> &nbsp; 
											<strong>Email Address</strong>
										</span>
									</li>
									<li>
										<span data-name="cellphone" data-holder="e.g. 02112121212">
											<input type="radio" name="type" value="cellphone" /> &nbsp; 
											<strong>Mobile Number</strong>
										</span>
									</li>
								</ul>
							</div>
						</div>
					</form>
					<form>
						<div class="form-group">
							<input type="text" id="login_name" class="form-control" placeholder="e.g. cyberpark@gmail.co.nz" data-error-field/>
						</div>
						<div class="form-group">
							<input id="code" class="form-control" placeholder="verification code shown in the picture" data-error-field/>
						</div>
						<div class="form-group">
							<img id="codeImage" style="cursor:pointer;" alt="Verification Code" src="kaptcha.jpg" />
						</div>
						<div class="form-group">
							<a href="javascript:void(0);" id="codeLink">Not clear? Change other code.</a>
						</div>
						
						<button type="button" data-loading-text="loading..." class="btn btn-success btn-block btn-lg" id="submit-btn">Confirm</button>
					</form>
				</div>
			</div>
		</div>
	</div>
</div>


<jsp:include page="footer.jsp" />
<jsp:include page="script.jsp" />
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/icheck.min.js"></script>
<script type="text/javascript">
(function($){
	
	$(':radio').iCheck({
		checkboxClass : 'icheckbox_square-green',
		radioClass : 'iradio_square-green'
	});
	
	$(document).keypress(function(e){
		if ($('#loginForm input:focus').length > 0 && event.keyCode == 13) {
			$('#submit-btn').trigger('click');
		}
	});
	
	$('span[data-name="email"] ins').on('click', function(){
		$('#login_name').attr('placeholder', $('span[data-name="email"]').attr('data-holder'));
	});
	$('span[data-name="cellphone"] ins').on('click', function(){
		$('#login_name').attr('placeholder', $('span[data-name="cellphone"]').attr('data-holder'));
	});
	
	$('#submit-btn').on("click", function(){
		
		var $btn = $(this);
		$btn.button('loading');
		var data = {
			login_name: $('#login_name').val()
			, type: $('input[name="type"]:checked').val()
			, code: $('#code').val()
		};
		$.post('${ctx}/forgotten-password', data, function(json){
			$('#codeImage').attr('src', 'kaptcha.jpg?' + Math.floor(Math.random()*100));
			$.jsonValidation(json, 'right');
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