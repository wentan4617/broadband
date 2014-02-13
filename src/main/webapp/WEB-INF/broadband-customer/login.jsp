<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<jsp:include page="header.jsp" />
<jsp:include page="alert.jsp" />

<div class="container" style="padding-top: 20px; padding-bottom: 150px;">
	<div class="row">
		<div class="col-md-4 col-md-offset-4">
			<div class="panel panel-default">
				<div class="panel-heading">CyberPark Customer Sign in</div>
				<div class="panel-body">
					<form:form modelAttribute="customer" method="post" action="${ctx}/login">
						<div class="form-group">
							<label for="login_name">Login Name</label>
							<form:input path="login_name" class="form-control" placeholder="Login Name" />
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


<jsp:include page="footer.jsp" />
<jsp:include page="script.jsp" />
<jsp:include page="footer-end.jsp" />