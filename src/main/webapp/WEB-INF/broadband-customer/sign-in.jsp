<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<jsp:include page="header.jsp" />
<jsp:include page="alert.jsp" />

<div class="container" style="padding-top: 20px; padding-bottom: 150px;">
	<div class="row">
		<div class="col-md-4 col-md-offset-4">
			<div class="panel panel-success">
				<div class="panel-heading">CyberPark Customer Sign in</div>
				<div class="panel-body">
					
					<form id="loginForm">
						<div class="form-group">
							<label for="login_name">Your Mobile or Email</label>
							<input type="text" id="login_name" class="form-control" data-error-field/>
						</div>
						<div class="form-group">
							<label for="password">Password
					 			<a href="${ctx }/forgotten-password" rel="nofollow"> forgot password ?</a>
							</label>
							<input type="password" id="password" class="form-control" data-error-field/>
						</div>
						<button type="submit" class="btn btn-success btn-lg btn-block ladda-button" data-style="zoom-in" id="signin-btn">Sign in</button>
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
	
	$('#signin-btn').on("click", function(e){
		
		e.preventDefault();
		var l = Ladda.create(this); l.start();
		var data = {
			login_name: $('#login_name').val()
			, password: $('#password').val()
		};
		$.post('${ctx}/sign-in', data, function(json){
			if (!$.jsonValidation(json, 'right')) {
				window.location.href = '${ctx}' + json.url;
			}
		}, 'json').always(function() { l.stop(); });
	});
	
})(jQuery);
</script>
<jsp:include page="footer-end.jsp" />