<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<jsp:include page="../header.jsp" />
<jsp:include page="../alert.jsp" />

<div class="container">
	<div class="row">
		<div class="col-md-12">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h4 class="panel-title">
						<a data-toggle="collapse" data-toggle="collapse"
							data-parent="#customerOrderAccordion" href="#collapseOrderInfo">${panelheading }</a>
					</h4>
				</div>
				<div id="collapseOrderInfo" class="panel-collapse collapse in">
					<div class="panel-body">
						<h4 class="text-success">Create customer's order</h4>
						<hr/>
						<form:form modelAttribute="customerOrder" method="post"
							action="${ctx}${action }" class="form-horizontal">
							<div class="form-group">
								<label for="plan_group" class="control-label col-md-2">Plan Group</label>
								<div class="col-md-3">
									<select id="plan_group" class="selectpicker show-tick form-control">
										<option value="plan-topup">Plan Topup</option>
										<option value="plan-no-term">Plan No Term</option>
										<option value="plan-term">Plan Term</option>
									</select>
								</div>
								<label for="order_broadband_type" class="control-label col-md-3">Broadband Option</label>
								<div class="col-md-3">
									<select id="order_broadband_type" class="selectpicker show-tick form-control">
										<option value="new-connection">New Connection</option>
										<option value="transition">Transition</option>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label for="plan_name" class="control-label col-md-2">Plan Name</label>
								<div class="col-md-3">
									<select id="plan_name" class="selectpicker show-tick form-control">
										<option value="">None</option>
									</select>
								</div>
							</div>
							<hr/>
							<div class="form-group">
								<div class="col-md-offset-4">
									<span class="col-md-offset-1">
										<a  href="${ctx}${backAction}"class="btn btn-success" id="back">Back</a>
									</span>
									<span class="col-md-offset-1">
										<a  href="javascript:void(0);"class="btn btn-success" id="save">Save</a>
									</span>
									<span class="col-md-offset-1">
										<a  href="javascript:void(0);"class="btn btn-success" id="next">Next</a>
									</span>
								</div>
							</div>
						</form:form>
					</div>
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
	$('.selectpicker').selectpicker();
	
	var saveAction = '${ctx}${action}';
	var nextAction = '${ctx}${nextAction}';
	
	$('#save').click(function(){
		$('#orderFrom').attr('action', saveAction).submit();
	});
	$('#next').click(function(){
		$('#orderFrom').attr('action', nextAction).submit();
	});
	
	$('#plan_group').change(function(){
		$.get('${ctx}/broadband-user/crm/customer/order/plan/view/'+this.value, callbackPage, "json");
		function callbackPage(map){
			$('#plan_name')
		}
	});
	
})(jQuery);
</script>
<jsp:include page="../footer-end.jsp" />