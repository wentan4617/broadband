<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<jsp:include page="header.jsp" />
<jsp:include page="alert.jsp" />

<div class="container">
	<div class="row">
		<div class="col-md-3">
			<jsp:include page="sidebar.jsp" />
		</div>
		<div class="col-md-9">
			<div class="panel panel-success customer-panel-height">
				<div class="panel-heading">
					<h3 class="panel-title">
						Change Password
					</h3>
				</div>
				<div class="panel-body">
					<form id="changePasswordForm" class="form-horizontal">
						<div class="page-header" style="margin-top: 0;">
							<h3 class="text-success">
								Old Password
							</h3>
						</div>
						<div class="form-group">
							<label for="old_password" class="col-sm-3 control-label">Old Password</label>
							<div class="col-sm-4">
								<input type="password" class="form-control" id="old_password" data-error-field/>
							</div>
						</div>
						<hr/>
						<div class="page-header" style="margin-top: 0;">
							<h3 class="text-success">
								New Password
							</h3>
						</div>
						
						<div class="form-group">
							<label for="password" class="col-sm-3 control-label">New Password</label>
							<div class="col-sm-4">
								<input type="password" class="form-control" id="password" data-error-field/>
							</div>
						</div>
						<div class="form-group">
							<label for="ck_password" class="col-sm-3 control-label">Confirm New Password</label>
							<div class="col-sm-4">
								<input type="password" class="form-control" id="ck_password" data-error-field/>
							</div>
						</div>
						<hr/>
						<div class="form-group">
							<div class="col-sm-4 col-sm-offset-3">
								<button type="submit" class="btn btn-success btn-lg btn-block ladda-button" data-style="zoom-in" id="change-btn">Change Password</button>
							</div>
						</div>
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
	
	$('#change-btn').on("click", function(e){
		
		e.preventDefault();
		var l = Ladda.create(this); l.start();
		var customer = {
			old_password: $('#old_password').val()
			, password: $('#password').val()
			, ck_password: $('#ck_password').val()
		};
		$.post('${ctx}/customer/change-password', customer, function(json){
			if (!$.jsonValidation(json, 'right')) {
				window.location.href = '${ctx}' + json.url;
			}
		}, 'json').always(function() { l.stop(); });
	});
	
})(jQuery);
</script>
<jsp:include page="footer-end.jsp" />