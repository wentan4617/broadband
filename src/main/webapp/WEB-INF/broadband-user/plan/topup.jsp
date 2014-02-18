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
					<form:form modelAttribute="topup" method="post" action="${ctx}${action }" class="form-horizontal">
						<form:hidden path="id"/>
						<div class="form-group">
							<label for="topup_name" class="control-label col-md-3">Topup Name</label>
							<div class="col-md-3">
								<form:input path="topup_name" class="form-control" placeholder="Topup Name" />
							</div>
							<p class="help-block">
								<form:errors path="topup_name" cssErrorClass="error"/>
							</p>
						</div>
						<div class="form-group">
							<label for="topup_fee" class="control-label col-md-3">fee (Inc GST)</label>
							<div class="col-md-3">
								<div class="input-group">
									<span class="input-group-addon">$</span>
									<form:input path="topup_fee" class="form-control" placeholder="" />
								</div>
							</div>
							<p class="help-block">
								<form:errors path="topup_fee" cssErrorClass="error"/>
							</p>
						</div>
						<div class="form-group">
							<label for="topup_data_flow" class="control-label col-md-3">Topup Data Flow</label>
							<div class="col-md-3">
								<div class="input-group">
									<form:input path="topup_data_flow" class="form-control" placeholder="" />
									<span class="input-group-addon">GB</span>
								</div>
							</div>
							<p class="help-block">
								<form:errors path="topup_data_flow" cssErrorClass="error"/>
							</p>
						</div>
						<div class="form-group">
							<label for="topup_status" class="control-label col-md-3">Topup Status</label>
							<div class="col-md-3">
								<form:select path="topup_status" class="form-control">
									<form:option value="active">Active</form:option>
									<form:option value="disable">Disable</form:option>
								</form:select>
							</div>
						</div>
						<div class="form-group">
							<div class="col-md-3 col-md-offset-3">
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