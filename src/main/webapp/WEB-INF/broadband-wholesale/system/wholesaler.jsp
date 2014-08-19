<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<jsp:include page="../header.jsp" />
<jsp:include page="../alert.jsp" />

<div class="container">
	<div class="row">
		<div class="col-md-12">
			<div class="panel panel-info">
				<div class="panel-heading">
					<h4 class="panel-title">${panelheading }</h4>
				</div>
				<div class="panel-body">
					<form:form modelAttribute="wholesaler" method="post" action="${ctx}${action }" class="form-horizontal">
						<form:hidden path="id"/>
						<c:if test="${wholesaler.id!=null && wholesalerSession.wholesaler_id==null}">
						<div class="form-group">
							<label class="control-label col-md-4">Account Level</label>
							<div class="col-md-3">
								<p class="form-control-static">
									<span class="badge" style="background:${wholesaler.wholesaler_id==null ? '#5cb85c' : '#c8c8c8'};">${wholesaler.wholesaler_id==null ? 'Primary' : 'Secondary'}</span>
								</p>
							</div>
						</div>
						</c:if>
						<c:if test="${userSession!=null}">
						<c:if test="${primaryWholesalers!=null}">
						<div class="form-group">
							<label for="company_name" class="control-label col-md-4"></label>
							<div class="col-md-6">
								<label><input type="radio" data-name="company_type" data-type="new" checked="checked" /> New Company</label>
								<c:if test="${fn:length(primaryWholesalers) > 0}">
									<label><input type="radio" data-name="company_type" data-type="existed" /> Existed Company</label>
								</c:if>
							</div>
						</div>
						</c:if>
						<div class="form-group">
							<label for="company_name" class="control-label col-md-4">Company Name</label>
							<div class="col-md-3">
								<form:input data-type="company_input" path="company_name" class="form-control" placeholder="Company Name"/>
								<select data-type="company_select" name="company_name" class="form-control" style="display:none;" disabled="disabled" >
									<c:forEach var="pw" items="${primaryWholesalers}">
										<option value="${pw.company_name}">${pw.company_name}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						</c:if>
						<div class="form-group">
							<label for="name" class="control-label col-md-4">Name</label>
							<div class="col-md-3">
								<form:input path="name" class="form-control" placeholder="Name" />
							</div>
							<p class="help-block">
								<form:errors path="name" cssErrorClass="error"/>
							</p>
						</div>
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
							<label for="login_password" class="control-label col-md-4">Login Password</label>
							<div class="col-md-3">
								<form:input path="login_password" class="form-control" placeholder="Login Password" />
							</div>
							<p class="help-block">
								<form:errors path="login_password" cssErrorClass="error"/>
							</p>
						</div>
						<div class="form-group">
							<label for="role" class="control-label col-md-4">Wholesaler Permission</label>
							<div class="col-md-3">
								<form:select path="role" class="form-control" data-name="wholesaler_permission_selector">
									<form:option value="">--- Please assign a role ---</form:option>
									<form:option value="chief-wholesaler">Chief Wholesaler</form:option>
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
						<c:if test="${userSession!=null || (wholesalerSession.wholesaler_id==null && wholesalerSession.id!=id)}">
							<div class="form-group">
								<label class="control-label col-md-4">Wholesaler Status</label>
								<div class="col-md-6">
									<p class="form-control-static">
										<label><form:radiobutton path="status" value="active" checked="checked"/>Active</label>
										<label><form:radiobutton path="status" value="disabled"/>Disabled</label>
										<label><form:radiobutton path="status" value="pending"/>Pending</label>
									</p>
								</div>
							</div>
						</c:if>
						<hr/>
						<c:if test="${userSession!=null || (wholesalerSession.wholesaler_id==null && wholesalerSession.id!=id)}">
						<h4>Wholesaler Authentication</h4>
						<div class="form-group" data-module="administrator">
							<div class="col-md-2" data-module="material">
								<ul class="list-unstyled">
									<li>
										<h3>Material & Combo</h3>
									</li>
									<li>
										<label> 
											<input type="checkbox" data-name="checkbox_all" data-type="checkbox_plan" /> All
										</label>
									</li>
									<li>
										<label> 
											<form:checkbox path="authArray" value="material/view" data-type="checkbox_plan" /> View Material
										</label>
									</li>
								</ul>
							</div>
						</div>
						<hr/>
						</c:if>
						<div class="form-group">
							<div class="col-md-offset-5">
								<button type="submit" class="btn btn-primary">${wholesaler.id!=null ? 'Update' : 'Save'}</button>
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
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/icheck.min.js"></script>
<script type="text/javascript">
(function($){
	
	$('input[data-name="checkbox_all"]').on('ifChecked',function(){
		var type = $(this).attr("data-type");
		$('input[data-type='+type+']').iCheck("check");
	});
	$('input[data-name="checkbox_all"]').on('ifUnchecked',function(){
		var type = $(this).attr("data-type");
		$('input[data-type='+type+']').iCheck("uncheck");
	});
	
	$(':radio,:checkbox').iCheck({
		checkboxClass : 'icheckbox_square-blue',
		radioClass : 'iradio_square-blue'
	});
	
	$('select[data-name="wholesaler_permission_selector"]').change(function(){
		var permission = $(this).find('option:selected').val();
		$('div[data-module="administrator"]').find('input').iCheck("uncheck");
		
		if("chief-wholesaler"==permission){	// Chief Operator
			
			$('div[data-module="administrator"]').find('input').iCheck("check");
			$('div[data-module="system"]').find('input').iCheck("uncheck");
			
		} else if("administrator"==permission){	// Administrator
			
			$('div[data-module="administrator"]').find('input').iCheck("check");
			
 		} else if("system-developer"==permission){	// System Developer
			
			$('div[data-module="administrator"]').find('input').iCheck("check");
			
		}
	});
	
	$(':radio[data-name="company_type"]').on('ifChecked',function(){
		if($(this).attr('data-type')=='new'){
			$(':radio[data-type="existed"]').iCheck("uncheck");
			$('select[data-type="company_select"]').css('display','none');
			$('select[data-type="company_select"]').attr('disabled','disabled');
			$('input[data-type="company_input"]').removeAttr('disabled');
			$('input[data-type="company_input"]').css('display','');
		} else {
			$(':radio[data-type="new"]').iCheck("uncheck");
			$('input[data-type="company_input"]').attr('disabled','true');
			$('input[data-type="company_input"]').css('display','none');
			$('select[data-type="company_select"]').removeAttr('disabled');
			$('select[data-type="company_select"]').css('display','');
		}
	});
	
})(jQuery);
</script>
<jsp:include page="../footer-end.jsp" />