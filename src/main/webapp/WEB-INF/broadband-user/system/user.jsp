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
						<hr/>
						<h4>User Authentication</h4>
						<div class="form-group">
							<div class="col-md-2">
								<ul class="list-unstyled">
									<li>
											<h3>Plan</h3>
									</li>
									<li>
										<label> 
											<form:checkbox path="authArray" value="plan/view" /> View Plan
										</label>
									</li>
									<li>
										<label> 
											<form:checkbox path="authArray" value="plan/create" /> Create Plan
										</label>
									</li>
									<li>
										<label> 
											<form:checkbox path="authArray" value="plan/edit" /> Edit Plan
										</label>
									</li>
								</ul>
									<hr/>
								<ul class="list-unstyled">
									<li>
										<label> 
											<form:checkbox path="authArray" value="plan/hardware/view" /> View Hardware
										</label>
									</li>
									<li>
										<label> 
											<form:checkbox path="authArray" value="plan/hardware/create" /> Create Hardware
										</label>
									</li>
									<li>
										<label> 
											<form:checkbox path="authArray" value="plan/hardware/edit" /> Edit Hardware
										</label>
									</li>
								</ul>
							</div>
							<div class="col-md-2">
								<ul class="list-unstyled">
									<li>
											<h3>CRM</h3>
									</li>
									<li>
										<label> 
											<form:checkbox path="authArray" value="crm/customer/view" /> View Customer
										</label>
									</li>
									<li>
										<label> 
											<form:checkbox path="authArray" value="crm/customer/create" /> Create Customer
										</label>
									</li>
									<li>
										<label> 
											<form:checkbox path="authArray" value="crm/customer/edit" /> Edit Customer
										</label>
									</li>
								</ul>
							</div>
							<div class="col-md-2">
								<ul class="list-unstyled">
									<li>
											<h3>Billing</h3>
									</li>
								</ul>
							</div>
							<div class="col-md-2">
								<ul class="list-unstyled">
									<li>
										<h3>Provision</h3>
									</li>
									<li>
										<label> 
											<form:checkbox path="authArray" value="provision/view" /> View Provision
										</label>
									</li>
									<li>
										<label> 
											<form:checkbox path="authArray" value="provision/customer/view" /> Provision Customer Order
										</label>
									</li>
								</ul>
							</div>
							<div class="col-md-2">
								<ul class="list-unstyled">
									<li>
											<h3>Data</h3>
									</li>
								</ul>
							</div>
							<div class="col-md-2">
								<ul class="list-unstyled">
									<li>
											<h3>System</h3>
									</li>
									<li>
										<label> 
											<form:checkbox path="authArray" value="system/user/view" /> View User
										</label>
									</li>
									<li>
										<label> 
											<form:checkbox path="authArray" value="system/user/create" /> Create User
										</label>
									</li>
									<li>
										<label> 
											<form:checkbox path="authArray" value="system/user/edit" /> Edit User
										</label>
									</li>
								</ul>
									<hr/>
								<ul class="list-unstyled">
									<li>
										<label> 
											<form:checkbox path="authArray" value="system/notification/view" /> View Notification
										</label>
									</li>
									<li>
										<label> 
											<form:checkbox path="authArray" value="system/notification/create" /> Create Notification
										</label>
									</li>
									<li>
										<label> 
											<form:checkbox path="authArray" value="system/notification/edit" /> Edit Notification
										</label>
									</li>
								</ul>
									<hr/>
								<ul class="list-unstyled">
									<li>
										<label> 
											<form:checkbox path="authArray" value="system/company-detail/edit" /> Edit Company Detail
										</label>
									</li>
								</ul>
									<hr/>
								<ul class="list-unstyled">
									<li>
										<label> 
											<form:checkbox path="authArray" value="system/chart/customer-register" /> Chart(Register Customer)
										</label>
									</li>
								</ul>
							</div>
						</div>
						<hr/>
						<div class="form-group">
							<div class="col-md-3 col-md-offset-4">
								<button type="submit" class="btn btn-success">Save</button>
							</div>
						</div>
						<hr/>
						<div class="form-group">
							<label for="memo" class="control-label col-md-4">Memo</label>
							<div class="col-md-6">
								<form:textarea path="memo" class="form-control" rows="6"/>
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