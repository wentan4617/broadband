<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="modal fade" id="sign-up-model" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-body">
				<div class="panel panel-default">
					<div class="panel-heading">
						CyberTech Sign in
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					</div>
					<div class="panel-body">
						<form:form modelAttribute="customer" method="post" action="${ctx}/login">
							<div class="form-group">
								<label for="login_name">User Name</label>
								<form:input path="login_name" class="form-control" placeholder="User Name" />
								<p class="help-block">
									<form:errors path="login_name" cssErrorClass="error"/>
								</p>
							</div>
							<div class="form-group">
								<label for="password">Password</label>
								<form:password path="password" class="form-control" placeholder="" />
								<p class="help-block">
									<form:errors path="password" cssErrorClass="error"/>
								</p>
							</div>
							<button type="submit" class="btn btn-success">Sign in</button>
						</form:form>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>