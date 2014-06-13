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
							<label for="user_role" class="control-label col-md-4">User Permission</label>
							<div class="col-md-3">
								<form:select path="user_role" class="form-control" data-name="user_permission_selector">
									<form:option value="none">Clean</form:option>
									<form:option value="sales">Sales</form:option>
									<form:option value="plan-designer">Plan Designer</form:option>
									<form:option value="crm-operator">CRM Operator</form:option>
									<form:option value="accountant">Accountant</form:option>
									<form:option value="provision-team">Provision Team</form:option>
									<form:option value="chief-operator">Chief Operator</form:option>
									<form:option value="administrator">Administrator</form:option>
									<form:option value="system-developer">System Developer</form:option>
								</form:select>
							</div>
						</div>
						<div class="form-group">
							<label for="email" class="control-label col-md-4">Email</label>
							<div class="col-md-3">
								<form:input path="email" class="form-control" placeholder="Email" />
							</div>
							<p class="help-block">
								<form:errors path="email" cssErrorClass="error"/>
							</p>
						</div>
						<div class="form-group">
							<label for="cellphone" class="control-label col-md-4">Cellphone</label>
							<div class="col-md-3">
								<form:input path="cellphone" class="form-control" placeholder="Cellphone" />
							</div>
							<p class="help-block">
								<form:errors path="cellphone" cssErrorClass="error"/>
							</p>
						</div>
						<hr/>
						<h4>User Authentication</h4>
						<div class="form-group" data-module="administrator">
							<div class="col-md-2" data-module="plan">
								<ul class="list-unstyled">
									<li>
											<h3>Plan</h3>
									</li>
									<li>
										<label> 
											<input type="checkbox" data-name="checkbox_all" data-type="checkbox_plan" /> All
										</label>
									</li>
									<li>
										<label> 
											<form:checkbox path="authArray" value="plan/view" data-type="checkbox_plan" /> View Plan
										</label>
									</li>
									<li>
										<label> 
											<form:checkbox path="authArray" value="plan/create" data-type="checkbox_plan" /> Create Plan
										</label>
									</li>
									<li>
										<label> 
											<form:checkbox path="authArray" value="plan/edit" data-type="checkbox_plan" /> Edit Plan
										</label>
									</li>
								</ul>
								<hr/>
								<ul class="list-unstyled">
									<li>
										<label> 
											<form:checkbox path="authArray" value="plan/hardware/view" data-type="checkbox_plan" /> View Hardware
										</label>
									</li>
									<li>
										<label> 
											<form:checkbox path="authArray" value="plan/hardware/create" data-type="checkbox_plan" /> Create Hardware
										</label>
									</li>
									<li>
										<label> 
											<form:checkbox path="authArray" value="plan/hardware/edit" data-type="checkbox_plan" /> Edit Hardware
										</label>
									</li>
								</ul>
							</div>
							<div class="col-md-2" data-module="crm">
								<ul class="list-unstyled">
									<li>
											<h3>CRM</h3>
									</li>
									<li>
										<label> 
											<input type="checkbox" data-name="checkbox_all" data-type="checkbox_customer" /> All
										</label>
									</li>
									<li>
										<label> 
											<form:checkbox path="authArray" value="crm/customer/view" data-type="checkbox_customer" /> View Customer
										</label>
									</li>
									<li>
										<label>
											<form:checkbox path="authArray" value="crm/customer/personal/create" data-type="checkbox_customer" /> Create Personal Customer
										</label>
									</li>
									<li>
										<label>
											<form:checkbox path="authArray" value="crm/customer/business/create" data-type="checkbox_customer" /> Create Business Customer
										</label>
									</li>
									<li>
										<label> 
											<form:checkbox path="authArray" value="crm/customer/edit" data-type="checkbox_customer" /> Edit Customer
										</label>
									</li>
								</ul>
							</div>
							<div class="col-md-2" data-module="billing">
								<ul class="list-unstyled">
									<li>
											<h3>Billing</h3>
									</li>
									<li>
										<label>
											<input type="checkbox" data-name="checkbox_all" data-type="checkbox_billing" /> All
										</label>
									</li>
									<li>
										<label>
											<form:checkbox path="authArray" value="billing/early-termination-charge/view" data-type="checkbox_billing" /> View Early Termination Charge
										</label>
									</li>
								</ul>
							</div>
							<div class="col-md-2" data-module="provision">
								<ul class="list-unstyled">
									<li>
										<h3>Provision</h3>
									</li>
									<li>
										<label> 
											<input type="checkbox" data-name="checkbox_all" data-type="checkbox_provision" /> All
										</label>
									</li>
									<li>
										<label> 
											<form:checkbox path="authArray" value="provision/view" data-type="checkbox_provision" /> View Provision
										</label>
									</li>
									<li>
										<label> 
											<form:checkbox path="authArray" value="provision/customer/view" data-type="checkbox_provision" /> Provision Customer Order
										</label>
									</li>
									<li>
										<label> 
											<form:checkbox path="authArray" value="provision/contact-us/view" data-type="checkbox_provision" /> View Contact Us
										</label>
									</li>
								</ul>
								<hr/>
								<ul class="list-unstyled">
									<li>
										<label> 
											<form:checkbox path="authArray" value="provision/sale/view" data-type="checkbox_provision" /> View Sales
										</label>
									</li>
								</ul>
							</div>
							<div class="col-md-2" data-module="data">
								<ul class="list-unstyled">
									<li>
											<h3>Data</h3>
									</li>
									<li>
										<label> 
											<input type="checkbox" data-name="checkbox_all" data-type="checkbox_data" /> All
										</label>
									</li>
									<li>
										<label> 
											<form:checkbox path="authArray" value="data/operatre" data-type="checkbox_data" /> Data Operatre
										</label>
									</li>
									<li>
										<label> 
											<form:checkbox path="authArray" value="data/customer/view" data-type="checkbox_data" /> Data Customer View
										</label>
									</li>
								</ul>
							</div>
							<div class="col-md-2" data-module="system">
								<ul class="list-unstyled">
									<li>
											<h3>System</h3>
									</li>
									<li>
										<label> 
											<input type="checkbox" data-name="checkbox_all" data-type="checkbox_system" /> All
										</label>
									</li>
									<li>
										<label> 
											<form:checkbox path="authArray" value="system/user/view" data-type="checkbox_system" /> View User
										</label>
									</li>
									<li>
										<label> 
											<form:checkbox path="authArray" value="system/user/create" data-type="checkbox_system" /> Create User
										</label>
									</li>
									<li>
										<label> 
											<form:checkbox path="authArray" value="system/user/edit" data-type="checkbox_system" /> Edit User
										</label>
									</li>
								</ul>
								<hr/>
								<ul class="list-unstyled">
									<li>
										<label> 
											<form:checkbox path="authArray" value="system/notification/view" data-type="checkbox_system" /> View Notification
										</label>
									</li>
									<li>
										<label> 
											<form:checkbox path="authArray" value="system/notification/create" data-type="checkbox_system" /> Create Notification
										</label>
									</li>
									<li>
										<label> 
											<form:checkbox path="authArray" value="system/notification/edit" data-type="checkbox_system" /> Edit Notification
										</label>
									</li>
								</ul>
								<hr/>
								<ul class="list-unstyled">
									<li>
										<label> 
											<form:checkbox path="authArray" value="system/call_charge_rate/view" data-type="checkbox_system" /> View Call Charge Rate
										</label>
									</li>
									<li>
										<label> 
											<form:checkbox path="authArray" value="system/call_charge_rate/create" data-type="checkbox_system" /> Create Call Charge Rate
										</label>
									</li>
									<li>
										<label> 
											<form:checkbox path="authArray" value="system/call_charge_rate/edit" data-type="checkbox_system" /> Edit Call Charge Rate
										</label>
									</li>
								</ul>
								<hr/>
								<ul class="list-unstyled">
									<li>
										<label> 
											<form:checkbox path="authArray" value="system/company-detail/edit" data-type="checkbox_system" /> Edit Company Detail
										</label>
									</li>
								</ul>
								<hr/>
								<ul class="list-unstyled">
									<li>
										<label> 
											<form:checkbox path="authArray" value="system/chart/customer-register" data-type="checkbox_system" /> Chart(Register Customer)
										</label>
									</li>
								</ul>
							</div>
							<hr/>
							<div class="col-md-2" data-module="sales">
								<ul class="list-unstyled">
									<li>
										<h3>Sales</h3>
									</li>
									<li>
										<label> 
											<input type="checkbox" data-name="checkbox_all" data-type="checkbox_sales" /> All
										</label>
									</li>
									<li>
										<label>
											<form:checkbox path="authArray" value="sale/online/ordering/view" data-type="checkbox_sales" /> View Online Orders (PAD | PC)
										</label>
									</li>
									<li>
										<label>
											<form:checkbox path="authArray" value="sale/online/ordering/plans" data-type="checkbox_sales" /> Ordering Online (PAD | PC)
										</label>
									</li>
								</ul>
							</div>
							<hr/>
							<div class="col-md-2" data-module="manual-manipulation">
								<ul class="list-unstyled">
									<li>
										<h3>Manual Manipulation</h3>
									</li>
									<li>
										<label> 
											<input type="checkbox" data-name="checkbox_all" data-type="checkbox_manual_manipulation" /> All
										</label>
									</li>
									<li>
										<label>
											<form:checkbox path="authArray" value="manual-manipulation/manual-manipulation-record/view" data-type="checkbox_manual_manipulation" /> View Manual Termed Invoice
										</label>
									</li>
									<li>
										<label>
											<form:checkbox path="authArray" value="manual-manipulation/call-billing-record/view" data-type="checkbox_manual_manipulation" /> View Customer Calling Billing
										</label>
									</li>
									<li>
										<label>
											<form:checkbox path="authArray" value="manual-manipulation/call-international-rate/view" data-type="checkbox_manual_manipulation" /> View Calling International Rate
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
	
	$('select[data-name="user_permission_selector"]').change(function(){
		var permission = $(this).find('option:selected').val();
		$('div[data-module="administrator"]').find('input').prop('checked', false);
		
		if("sales"==permission){	// Sales
			
			$('div[data-module="sales"]').find('input').prop('checked', true);
			
		} else if("plan-designer"==permission){	// Plan Designer
			
			$('div[data-module="plan"]').find('input').prop('checked', true);
			
		} else if("crm-operator"==permission){	// CRM Operator
			
			$('div[data-module="crm"]').find('input').prop('checked', true);
			
		} else if("accountant"==permission){	// Accountant
			
			$('div[data-module="crm"]').find('input').prop('checked', true);
			$('div[data-module="billing"]').find('input').prop('checked', true);
			$('div[data-module="provision"]').find('input').prop('checked', true);
			$('div[data-module="data"]').find('input').prop('checked', true);
			
		} else if("provision-team"==permission){	// Provision Team
			
			$('div[data-module="crm"]').find('input').prop('checked', true);
			$('div[data-module="provision"]').find('input').prop('checked', true);
			$('div[data-module="data"]').find('input').prop('checked', true);

		} else if("chief-operator"==permission){	// Chief Operator
			
			$('div[data-module="administrator"]').find('input').prop('checked', true);
			$('div[data-module="system"]').find('input').prop('checked', false);
			
		} else if("administrator"==permission){	// Administrator
			
			$('div[data-module="administrator"]').find('input').prop('checked', true);
			
 		} else if("system-developer"==permission){	// System Developer
			
			$('div[data-module="administrator"]').find('input').prop('checked', true);
			
		}
	});
})(jQuery);
</script>
<jsp:include page="../footer-end.jsp" />