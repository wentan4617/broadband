<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<jsp:include page="header.jsp" />
<jsp:include page="alert.jsp" />

<div class="container">
	<div class="row">
		<div class="col-md-12">

			<!-- Contact Us Details -->
			<div class="panel-group" id="contactUsAccordion">
				<div class="panel panel-success">
					<div class="panel-heading">
						<h4 class="panel-title">
							<a data-toggle="collapse" data-toggle="collapse" data-parent="#contactUsAccordion" href="#collapseContactUs">Contact Us</a>
						</h4>
					</div>
					<div id="collapseContactUs" class="panel-collapse collapse in">
						<div class="panel-body">
							
							<form method="post" action="${ctx}/contact_us" class="form-horizontal" id="contactUsForm">
								<div class="form-group">
									<label for="first_name" class="control-label col-md-3">First Name*</label>
									<div class="col-md-6">
										<input id="first_name" class="form-control" placeholder="First Name" data-error-field/>
									</div>
								</div>
								<div class="form-group">
									<label for="last_name" class="control-label col-md-3">Last Name*</label>
									<div class="col-md-6">
										<input id="last_name" class="form-control" placeholder="Last Name" data-error-field/>
									</div>
								</div>
								<div class="form-group">
									<label for="email" class="control-label col-md-3">Email*</label>
									<div class="col-md-6">
										<input id="email" class="form-control" placeholder="Email" data-error-field/>
									</div>
								</div>
								<div class="form-group">
									<label for="cellphone" class="control-label col-md-3">Mobile</label>
									<div class="col-md-6">
										<input id="cellphone" class="form-control" placeholder="Cellphone" data-error-field/>
									</div>
								</div>
								<div class="form-group">
									<label for="phone" class="control-label col-md-3">Phone</label>
									<div class="col-md-6">
										<input id="phone" class="form-control" placeholder="Phone" data-error-field/>
									</div>
								</div>
								<div class="form-group">
									<label for="content" class="control-label col-md-3">Your request*</label>
									<div class="col-md-6">
										<textarea id="content" class="form-control" rows="6" data-error-field></textarea>
									</div>
								</div>
								<div class="form-group">
									<label for="code" class="control-label col-md-3"></label>
									<div class="col-md-6">
										<input id="code" class="form-control" placeholder="Verification Code" data-error-field/>
									</div>
								</div>
								<div class="form-group">
									<label for="code" class="control-label col-md-3"></label>
									<div class="col-md-2">
										<img id="codeImage" style="cursor:pointer;" alt="Verification Code" src="kaptcha.jpg" />
										
									</div>
									<div class="col-md-3">
										<a href="javascript:void(0);" id="codeLink">Not clear? Change other code.</a>
									</div>
									
								</div>
								<hr/>
								<div class="form-group">
									<div class="col-md-3"></div>
									<div class="col-md-3">
										<button type="button" data-loading-text="loading..." class="btn btn-success btn-lg btn-block" id="submit-btn">Submit Request</button>
									</div>
								</div>
							</form>
							
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<jsp:include page="footer.jsp" />
<jsp:include page="script.jsp" />
<script type="text/javascript">


(function($){
	$(document).keypress(function(e){
		if ($('#contactUsForm input:focus').length > 0 && event.keyCode == 13) {
			$('#submit-btn').trigger('click');
		}
	});
	
	$('#submit-btn').on("click", function(){
		var $btn = $(this);
		$btn.button('loading');
		var data = {
			first_name: $('#first_name').val()
			, last_name: $('#last_name').val()
			, email: $('#email').val()
			, cellphone: $('#cellphone').val()
			, phone: $('#phone').val()
			, content: $('#content').val()
			, code: $('#code').val()
		};
		$.post('${ctx}/contact-us', data, function(json){
			$('#codeImage').attr('src', 'kaptcha.jpg?' + Math.floor(Math.random()*100));
			if (json.hasErrors) {
				$.jsonValidation(json, 'right');
			} else {
				window.location.href='${ctx}' + json.url;
			}
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
