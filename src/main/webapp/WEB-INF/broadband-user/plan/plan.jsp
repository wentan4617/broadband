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
					<form:form modelAttribute="plan" method="post" action="${ctx}${action }" class="form-horizontal">
						<form:hidden path="id"/>
						<div class="form-group">
							<label for="plan_name" class="control-label col-md-3">Plan Name</label>
							<div class="col-md-3">
								<form:input path="plan_name" class="form-control" placeholder="Plan Name" />
							</div>
							<p class="help-block">
								<form:errors path="plan_name" cssErrorClass="error"/>
							</p>
						</div>
						<div class="form-group">
							<label for="plan_type" class="control-label col-md-3">Plan Type</label>
							<div class="col-md-3">
								<form:select path="plan_type" class="form-control">
									<form:option value="ADSL">ADSL</form:option>
									<form:option value="VDSL">VDSL</form:option>
									<form:option value="UFB">UFB</form:option>
								</form:select>
							</div>
						</div>
						<div class="form-group">
							<label for="plan_sort" class="control-label col-md-3">Plan Sort</label>
							<div class="col-md-3">
								<form:select path="plan_sort" class="form-control">
									<form:option value="NON-NAKED">NON-NAKED</form:option>
									<form:option value="NAKED">NAKED</form:option>
								</form:select>
							</div>
						</div>
						<div class="form-group">
							<label for="plan_price" class="control-label col-md-3">Monthly fee (Inc GST)</label>
							<div class="col-md-3">
								<div class="input-group">
									<span class="input-group-addon">$</span>
									<form:input path="plan_price" class="form-control" placeholder="" />
								</div>
							</div>
							<p class="help-block">
								<form:errors path="plan_price" cssErrorClass="error"/>
							</p>
						</div>
						<div class="form-group">
							<label for="data_flow" class="control-label col-md-3">Data Flow</label>
							<div class="col-md-3">
								<div class="input-group">
									<form:input path="data_flow" class="form-control" placeholder="" />
									<span class="input-group-addon">GB</span>
								</div>
							</div>
							<p class="help-block">
								<form:errors path="data_flow" cssErrorClass="error"/>
							</p>
						</div>
						<div class="form-group">
							<label for="plan_status" class="control-label col-md-3">Status</label>
							<div class="col-md-3">
								<form:select path="plan_status" class="form-control">
									<form:option value="active">Active</form:option>
									<form:option value="selling">Selling</form:option>
									<form:option value="disable">Disable</form:option>
								</form:select>
							</div>
						</div>
						<div class="form-group">
							<label for="plan_desc" class="control-label col-md-3">Description</label>
							<div class="col-md-6">
								<form:textarea path="plan_desc" class="form-control" rows="6"/>
							</div>
						</div>
						<div class="form-group">
							<label for="memo" class="control-label col-md-3">Memo</label>
							<div class="col-md-6">
								<form:textarea path="memo" class="form-control" rows="6"/>
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