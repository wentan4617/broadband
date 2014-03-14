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
				<div class="panel-heading"><h4 class="panel-title">${panelheading }</h4></div>
				<div class="panel-body">
					<form:form modelAttribute="hardware" method="post" action="${ctx}${action }" class="form-horizontal">
						<form:hidden path="id"/>
						<div class="form-group">
							<label for="hardware_name" class="control-label col-md-4">Hardware Name</label>
							<div class="col-md-3">
								<form:input path="hardware_name" class="form-control" placeholder="Hardware Name" />
							</div>
							<p class="help-block">
								<form:errors path="hardware_name" cssErrorClass="error"/>
							</p>
						</div>
						<div class="form-group">
							<label for="hardware_type" class="control-label col-md-4">Hardware Type</label>
							<div class="col-md-3">
								<form:select path="hardware_type" class="form-control">
									<form:option value="none">None</form:option>
									<form:option value="router">Router</form:option>
								</form:select>
							</div>
							<p class="help-block">
								<form:errors path="hardware_type" cssErrorClass="error"/>
							</p>
						</div>
						<div class="form-group">
							<label for="hardware_status" class="control-label col-md-4">Hardware Status</label>
							<div class="col-md-3">
								<form:select path="hardware_status" class="form-control">
									<form:option value="active">Active</form:option>
									<form:option value="selling">Selling</form:option>
									<form:option value="disable">Disable</form:option>
								</form:select>
							</div>
							<p class="help-block">
								<form:errors path="hardware_status" cssErrorClass="error"/>
							</p>
						</div>
						<div class="form-group">
							<label for="hardware_price" class="control-label col-md-4">Hardware Price</label>
							<div class="col-md-3">
								<div class="input-group">
									<span class="input-group-addon">$</span>
									<form:input path="hardware_price" class="form-control" />
								</div>
							</div>
							<p class="help-block">
								<form:errors path="hardware_price" cssErrorClass="error"/>
							</p>
						</div>
						<div class="form-group">
							<label for="hardware_cost" class="control-label col-md-4">Hardware Cost</label>
							<div class="col-md-3">
								<div class="input-group">
									<span class="input-group-addon">$</span>
									<form:input path="hardware_cost" class="form-control" />
								</div>
							</div>
							<p class="help-block">
								<form:errors path="hardware_cost" cssErrorClass="error"/>
							</p>
						</div>
						<hr/>
						<div class="form-group">
							<div class="col-md-3 col-md-offset-4">
								<button type="submit" class="btn btn-success">Save</button>
							</div>
						</div>
						<hr/>
						<div class="form-group">
							<!-- <label for="hardware_desc" class="control-label col-md-4">Hardware Desc</label> -->
							<div class="col-md-12">
								<form:textarea path="hardware_desc" class="form-control" rows="24"/>
							</div>
							<%-- <p class="help-block">
								<form:errors path="hardware_desc" cssErrorClass="error"/>
							</p> --%>
						</div>
					</form:form>
				</div>
			</div>
		</div>
	</div>
</div>
<jsp:include page="../footer.jsp" />
<jsp:include page="../script.jsp" />
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/bootstrap-select.min.js"></script>
<script type="text/javascript">
(function($){
	
	
})(jQuery);
</script>
<jsp:include page="../footer-end.jsp" />