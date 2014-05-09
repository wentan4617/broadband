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
								<form:select path="user_role" class="form-control" data-name="user_role_selector">
									<form:option value="none">define a role for this user</form:option>
									<form:option value="administrator">Administrator</form:option>
									<form:option value="operator">Operator</form:option>
									<form:option value="sales">Sales</form:option>
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
											<input type="checkbox" data-name="checkbox_all" data-type="checkbox_plan" data-admin-role="true" data-operator-role="true" /> All
										</label>
									</li>
									<li>
										<label> 
											<form:checkbox path="authArray" value="plan/view" data-type="checkbox_plan" data-admin-role="true" data-operator-role="true" /> View Plan
										</label>
									</li>
									<li>
										<label> 
											<form:checkbox path="authArray" value="plan/create" data-type="checkbox_plan" data-admin-role="true" data-operator-role="true" /> Create Plan
										</label>
									</li>
									<li>
										<label> 
											<form:checkbox path="authArray" value="plan/edit" data-type="checkbox_plan" data-admin-role="true" data-operator-role="true" /> Edit Plan
										</label>
									</li>
								</ul>
									<hr/>
								<ul class="list-unstyled">
									<li>
										<label> 
											<form:checkbox path="authArray" value="plan/hardware/view" data-type="checkbox_plan" data-admin-role="true" data-operator-role="true" /> View Hardware
										</label>
									</li>
									<li>
										<label> 
											<form:checkbox path="authArray" value="plan/hardware/create" data-type="checkbox_plan" data-admin-role="true" data-operator-role="true" /> Create Hardware
										</label>
									</li>
									<li>
										<label> 
											<form:checkbox path="authArray" value="plan/hardware/edit" data-type="checkbox_plan" data-admin-role="true" data-operator-role="true" /> Edit Hardware
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
											<input type="checkbox" data-name="checkbox_all" data-type="checkbox_customer" data-admin-role="true" data-operator-role="true" /> All
										</label>
									</li>
									<li>
										<label> 
											<form:checkbox path="authArray" value="crm/customer/view" data-type="checkbox_customer" data-admin-role="true" data-operator-role="true" /> View Customer
										</label>
									</li>
									<li>
										<label>
											<form:checkbox path="authArray" value="crm/customer/personal/create" data-type="checkbox_customer" data-admin-role="true" data-operator-role="true" /> Create Personal Customer
										</label>
									</li>
									<li>
										<label>
											<form:checkbox path="authArray" value="crm/customer/business/create" data-type="checkbox_customer" data-admin-role="true" data-operator-role="true" /> Create Business Customer
										</label>
									</li>
									<li>
										<label> 
											<form:checkbox path="authArray" value="crm/customer/edit" data-type="checkbox_customer" data-admin-role="true" data-operator-role="true" /> Edit Customer
										</label>
									</li>
								</ul>
							</div>
							<div class="col-md-2">
								<ul class="list-unstyled">
									<li>
											<h3>Billing</h3>
									</li>
									<li>
										<label> 
											<input type="checkbox" data-name="checkbox_all" data-type="checkbox_billing" data-admin-role="true" data-operator-role="true" /> All
										</label>
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
											<input type="checkbox" data-name="checkbox_all" data-type="checkbox_provision" data-admin-role="true" data-operator-role="true" /> All
										</label>
									</li>
									<li>
										<label> 
											<form:checkbox path="authArray" value="provision/view" data-type="checkbox_provision" data-admin-role="true" data-operator-role="true" /> View Provision
										</label>
									</li>
									<li>
										<label> 
											<form:checkbox path="authArray" value="provision/customer/view" data-type="checkbox_provision" data-admin-role="true" data-operator-role="true" /> Provision Customer Order
										</label>
									</li>
								</ul>
							</div>
							<div class="col-md-2">
								<ul class="list-unstyled">
									<li>
											<h3>Data</h3>
									</li>
									<li>
										<label> 
											<input type="checkbox" data-name="checkbox_all" data-type="checkbox_data" data-admin-role="true" data-operator-role="true" /> All
										</label>
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
											<input type="checkbox" data-name="checkbox_all" data-type="checkbox_system" data-admin-role="true" /> All
										</label>
									</li>
									<li>
										<label> 
											<form:checkbox path="authArray" value="system/user/view" data-type="checkbox_system" data-admin-role="true" /> View User
										</label>
									</li>
									<li>
										<label> 
											<form:checkbox path="authArray" value="system/user/create" data-type="checkbox_system" data-admin-role="true" /> Create User
										</label>
									</li>
									<li>
										<label> 
											<form:checkbox path="authArray" value="system/user/edit" data-type="checkbox_system" data-admin-role="true" /> Edit User
										</label>
									</li>
								</ul>
									<hr/>
								<ul class="list-unstyled">
									<li>
										<label> 
											<form:checkbox path="authArray" value="system/notification/view" data-type="checkbox_system" data-admin-role="true" /> View Notification
										</label>
									</li>
									<li>
										<label> 
											<form:checkbox path="authArray" value="system/notification/create" data-type="checkbox_system" data-admin-role="true" /> Create Notification
										</label>
									</li>
									<li>
										<label> 
											<form:checkbox path="authArray" value="system/notification/edit" data-type="checkbox_system" data-admin-role="true" /> Edit Notification
										</label>
									</li>
								</ul>
									<hr/>
								<ul class="list-unstyled">
									<li>
										<label> 
											<form:checkbox path="authArray" value="system/company-detail/edit" data-type="checkbox_system" data-admin-role="true" /> Edit Company Detail
										</label>
									</li>
								</ul>
									<hr/>
								<ul class="list-unstyled">
									<li>
										<label> 
											<form:checkbox path="authArray" value="system/chart/customer-register" data-type="checkbox_system" data-admin-role="true" /> Chart(Register Customer)
										</label>
									</li>
								</ul>
							</div>
						<hr/>
							<div class="col-md-2">
								<ul class="list-unstyled">
									<li>
										<h3>Sales</h3>
									</li>
									<li>
										<label> 
											<input type="checkbox" data-name="checkbox_all" data-type="checkbox_sales" data-admin-role="true" data-operator-role="true" data-sales-role="true" /> All
										</label>
									</li>
									<li>
										<label>
											<form:checkbox path="authArray" value="sale/online/ordering/view" data-type="checkbox_sales" data-admin-role="true" data-operator-role="true" data-sales-role="true" /> View Online Orders (PAD | PC)
										</label>
									</li>
									<li>
										<label>
											<form:checkbox path="authArray" value="sale/online/ordering/plans" data-type="checkbox_sales" data-admin-role="true" data-operator-role="true" data-sales-role="true" /> Ordering Online (PAD | PC)
										</label>
									</li>
								</ul>
							</div>
						</div>
						<hr/>
						<div class="form-group">
							<div class="col-md-offset-5">
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
<script type="text/javascript">
(function($){
	$('input[data-name="checkbox_all"]').click(function(){
		var b = $(this).prop("checked");
		var type = $(this).attr("data-type");
		if (b) {
			$('input[data-type='+type+']').prop("checked", true);
		} else {
			$('input[data-type='+type+']').prop("checked", false);
		}
	});
	
	$('select[data-name="user_role_selector"]').change(function(){
		var role = $(this).find('option:selected').val();
		if("administrator"==role){
			$('input[data-admin-role=true]').prop("checked", true);
		} else if("sales"==role){
			$('input[data-admin-role=true]').prop("checked", false);
			$('input[data-sales-role=true]').prop("checked", true);
		} else if("operator"==role){
			$('input[data-admin-role=true]').prop("checked", false);
			$('input[data-operator-role=true]').prop("checked", true);
		} else if("none"==role){
			$('input[data-admin-role=true]').prop("checked", false);
		}
	});
})(jQuery);
</script>
<jsp:include page="../footer-end.jsp" />