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
				<div class="panel-heading">CyberPark Customer Logon</div>
				<div class="panel-body">
					
					<form id="loginForm">
						<div class="form-group">
							<label for="login_name">Your Mobile or Email</label>
							<input type="text" id="login_name" class="form-control" placeholder="" data-error-field/>
						</div>
						<div class="form-group">
							<label for="password">Password
					 			<a href="${ctx }/forgotten-password" rel="nofollow">&nbsp;forgot password ?</a>
							</label>
							<input type="password" id="password" class="form-control" placeholder="" data-error-field/>
						</div>
						<button type="button" data-loading-text="loading..." class="btn btn-success btn-lg btn-block" id="signin-btn">login</button>
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
		$.post('${ctx}/login', data, function(json){
			if (json.hasErrors) {
				$.jsonValidation(json, 'right');
			} else {
				window.location.href='${ctx}' + json.url;
			}
		}, 'json').always(function () {
			$btn.button('reset');
	    });
	});
})(jQuery);
</script>
<jsp:include page="footer-end.jsp" />