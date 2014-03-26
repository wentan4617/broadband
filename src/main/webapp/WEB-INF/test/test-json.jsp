<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<jsp:include page="../broadband-user/header.jsp" />
<jsp:include page="../broadband-user/alert.jsp" />

<div class="container">
	<div class="row">
		<div class="col-md-4 col-md-offset-4">
			<div class="panel panel-default">
				<div class="panel-heading">CyberPark Broadband Manager System Sign in</div>
				<div class="panel-body">
					<form id="loginForm">
						<div class="form-group">
							<label for="login_name">User Name</label>
							<input type="text" id="login_name" class="form-control" placeholder="Account Name" data-error-field/>
						</div>
						<div class="form-group">
							<label for="password">Password</label>
							<input type="password" id="password" class="form-control" placeholder="" data-error-field/>
						</div>
						<button type="button" data-loading-text="loading..." class="btn btn-success" id="signin-btn">Sign in</button>
					</form>
				</div>
			</div>
		</div>
	</div>
</div>
<jsp:include page="../broadband-user/footer.jsp" />
<jsp:include page="../broadband-user/script.jsp" />
<script type="text/javascript">
(function($){
	$(document).keypress(function(e){
		if ($('#loginForm input:focus').length > 0 && event.keyCode == 13) {
			$('#signin-btn').trigger('click');
		}
	});
	
	$('#signin-btn').on("click", function(){
		
		var $btn = $(this);
		$btn.button('loading');
		var data = {
			login_name: $('#login_name').val()
			, password: $('#password').val()
		};
		$.post('${ctx}/test/json', data, function(json){
			if (json.hasErrors) {
				$.jsonValidation(json);
			} else {
				window.location.href='${ctx}' + json.url;
			}
		}, 'json').always(function () {
			$btn.button('reset');
	    });
	});
})(jQuery);
</script>
<jsp:include page="../broadband-user/footer-end.jsp" />