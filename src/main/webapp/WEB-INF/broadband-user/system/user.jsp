<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<jsp:include page="../header.jsp" />
<jsp:include page="../alert.jsp" />

<div class="container">
	<div class="row">
		<div class="col-md-12">
			<div class="panel panel-default">
				<div class="panel-heading">${panelheading }</div>
				<div class="panel-body">
					<form:form modelAttribute="user" method="post" action="${ctx}${action }" class="form-horizontal">
						<form:hidden path="id"/>
						<div class="form-group">
							<label for="login_name" class="control-label col-md-4">Login Name</label>
							<div class="col-md-3">
								<form:input path="login_name" class="form-control" placeholder="Login Name" />
							</div>
							<p class="help-block">
								<form:errors path="login_name" cssErrorClass="error"/>
							</p>
						</div>
						<div class="form-group">
							<label for="password" class="control-label col-md-4">Password</label>
							<div class="col-md-3">
								<form:input path="password" class="form-control" placeholder="Password" />
							</div>
							<p class="help-block">
								<form:errors path="password" cssErrorClass="error"/>
							</p>
						</div>
						<div class="form-group">
							<label for="user_name" class="control-label col-md-4">User Name</label>
							<div class="col-md-3">
								<form:input path="user_name" class="form-control" placeholder="User Name" />
							</div>
							<p class="help-block">
								<form:errors path="user_name" cssErrorClass="error"/>
							</p>
						</div>
						<div class="form-group">
							<label for="user_role" class="control-label col-md-4">User Role</label>
							<div class="col-md-3">
								<form:select path="user_role" class="form-control">
									<form:option value="administrator">Administrator</form:option>
									<form:option value="operator">Operator</form:option>
								</form:select>
							</div>
						</div>
						<div class="form-group">
							<label for="memo" class="control-label col-md-4">Memo</label>
							<div class="col-md-6">
								<form:textarea path="memo" class="form-control" rows="6"/>
							</div>
						</div>
						<div class="form-group">
							<div class="col-md-3 col-md-offset-4">
								<button type="submit" class="btn btn-success">Save</button>
							</div>
						</div>
						
					</form:form>
				</div>
			</div>
		</div>
	</div>
</div>
<jsp:include page="../footer.jsp" />
<jsp:include page="../script.jsp" />
<jsp:include page="../footer-end.jsp" />