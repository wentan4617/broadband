<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<jsp:include page="header.jsp" />
<jsp:include page="alert.jsp" />

<div class="container" style="padding-top: 20px; padding-bottom: 150px">
	<div class="row">
		<div class="col-md-6 col-md-offset-3">
		
			<div class="panel panel-success">
				<div class="panel-heading">Forgotten Password</div>
				<div class="panel-body">
					
					<p>
						Your provided email or mobile must match the one you left.
					</p>
					
					<p class="lead">
						<strong>Get random password by my:</strong>
					</p>
					
					<form class="form-horizontal">
					
						<div class="form-group">
							<div class="col-md-12">
								<ul class="list-inline">
									<li>
										<label style="cursor:pointer;">
											<input type="radio" name="type" value="email" checked="checked" /> 
											<strong>Email</strong>
										</label>
									</li>
									<li>
										<label style="cursor:pointer;">
											<input type="radio" name="type" value="cellphone" /> 
											<strong>Mobile</strong>
										</label>
									</li>
								</ul>
							</div>
						</div>
						<div class="form-group">
							<div class="col-md-12">
								<input type="text" id="login_name" class="form-control" placeholder="e.g. cyberpark@gmail.co.nz" data-error-field/>
							</div>
						</div>
						<div class="form-group">
							<label for="code" class="control-label col-md-6">Verification Code <span class="text-danger">(*)</span></label>
							<div class="col-md-6">
								<input id="code" class="form-control" placeholder="shown below" data-error-field/>
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-5 col-sm-offset-1" style="text-align:left">
								<a href="javascript:void(0);" id="codeLink">Not clear?<br/>Change Another One.</a>
							</div>
							<div class="col-sm-6">
								<img id="codeImage" style="cursor:pointer" alt="Verification Code" src="kaptcha.jpg" />
							</div>
						</div>
						
						<button type="submit" class="btn btn-success btn-block btn-lg ladda-button" data-style="zoom-in" id="submit-btn">Confirm</button>
					</form>
				</div>
			</div>
		</div>
	</div>
</div>

<jsp:include page="footer.jsp" />
<jsp:include page="script.jsp" />
<script type="text/javascript">
(function($){
	
	$(':radio').iCheck({ checkboxClass: 'icheckbox_square-green', radioClass: 'iradio_square-green' });
	
	$('input[name="type"]').on('ifChecked',function(){
		if (this.value == 'cellphone') {
			$('#login_name').attr('placeholder', 'e.g. 02112121212');
		} else {
			$('#login_name').attr('placeholder', 'e.g. cyberpark@gmail.co.nz');
		}
		$('#login_name').val('').focus();
	});
	
	$('#submit-btn').on("click", function(e){
		
		e.preventDefault();
		var l = Ladda.create(this); l.start();
		var data = {
			login_name: $('#login_name').val()
			, type: $('input[name="type"]:checked').val()
			, code: $('#code').val()
		};
		$.post('${ctx}/forgotten-password', data, function(json){
			$('#codeImage').attr('src', 'kaptcha.jpg?' + Math.floor(Math.random() * 100));
			$.jsonValidation(json, 'right');
		}, 'json').always(function() { l.stop(); });
		
	});
	
	$('#codeImage,#codeLink').click(function(){
		$('#codeImage').attr('src', 'kaptcha.jpg?' + Math.floor(Math.random() * 100));
	});
	
})(jQuery);
</script>
<jsp:include page="footer-end.jsp" />