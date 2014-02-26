<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>


<div class="panel-group" id="accordion">
	<div class="panel panel-default">
		<div class="panel-heading">
			<h4 class="panel-title">
				<a data-toggle="collapse" data-toggle="collapse"
					data-parent="#accordion" href="#collapseOne"> Customer Query </a>
			</h4>
		</div>
		<div id="collapseOne" class="panel-collapse collapse in">
			<div class="panel-body">
				<form:form  modelAttribute="customerQuery" method="get" action="${ctx }/broadband-user/crm/customer/query/1" class="form-horizontal">
				
					<div class="form-group">
						<label class="col-md-2 control-label">Customer ID:</label>
						<div class="col-md-4">
							<div class="input-group">
								<span class="input-group-addon"> 
									<form:checkbox path="id_ck" value="" data-role="query" data-id="id"/> 
								</span> 
								<form:input path="id" class="form-control" />
							</div>
						</div>
						<label class="col-md-2 control-label">Login Name:</label>
						<div class="col-md-4">
							<div class="input-group">
								<span class="input-group-addon"> 
									<form:checkbox path="login_name_ck" value="" data-role="query" data-id="login_name"/> 
								</span> 
								<form:input path="login_name" class="form-control" />
							</div>
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-2 control-label">Phone:</label>
						<div class="col-md-4">
							<div class="input-group">
								<span class="input-group-addon"> 
									<form:checkbox path="phone_ck" value="" data-role="query" data-id="phone"/> 
								</span> 
								<form:input path="phone" class="form-control" />
							</div>
						</div>
						<label class="col-md-1 control-label">SVLan:</label>
						<div class="col-md-2">
							<div class="input-group">
								<span class="input-group-addon"> 
									<form:checkbox path="svlan_ck" value="" data-role="query" data-id="svlan"/> 
								</span> 
								<form:input path="svlan" class="form-control" />
							</div>
						</div>
						<label class="col-md-1 control-label">CVLan:</label>
						<div class="col-md-2">
							<div class="input-group">
								<span class="input-group-addon">
									<form:checkbox path="cvlan_ck" value="" data-role="query" data-id="cvlan"/> 
								</span> 
								<form:input path="cvlan" class="form-control" />
							</div>
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-2 control-label">Cell phone</label>
						<div class="col-md-4">
							<div class="input-group">
								<span class="input-group-addon"> 
									<form:checkbox path="cellphone_ck" value="" data-role="query" data-id="cellphone" /> 
								</span> 
								<form:input path="cellphone" class="form-control" />
							</div>
						</div>
						<label class="col-md-2 control-label">Email</label>
						<div class="col-md-4">
							<div class="input-group">
								<span class="input-group-addon" > 
									<form:checkbox path="email_ck" value="" data-role="query" data-id="email"/> 
								</span> 
								<form:input path="email" class="form-control" />
							</div>
						</div>
					</div>		
					<div class="form-group">
						<div class="col-md-1 col-md-offset-2">
							<button type="submit" class="btn btn-success" >Query</button>
						</div>
					</div>
				</form:form>
			</div>
		</div>
	</div>
</div>

