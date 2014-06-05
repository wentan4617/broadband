<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<jsp:include page="../header.jsp" />
<jsp:include page="../alert.jsp" />

<div class="container">
	<div class="row">
		<div class="col-md-12">
			<div class="panel panel-success">
				<div class="panel-heading">
					<h4 class="panel-title">
						<a data-toggle="collapse" data-toggle="collapse" data-parent="#callBillingAccordion" href="#collapseCallBilling">
							Call Billing Create
						</a>
					</h4>
				</div>
				<div id="collapseCallBilling" class="panel-collapse collapse in">
					<div class="panel-body">
					
						<form:form class="form-horizontal" modelAttribute="callChargeRate" method="post">
							<div class="form-group">
								<div class="col-md-12"></div>
							</div>
							<div class="form-group">
								<label for="customer_type" class="control-label col-md-5">Customer type</label>
								<div class="col-md-3">
									<select name="customer_type" class="selectpicker show-tick form-control">
										<option value="personal">Personal</option>
										<option value="business">Business</option>
									</select>
								</div>
								<div class="col-md-4"></div>
							</div>
							<div class="form-group">
								<label for="number_type" class="control-label col-md-5">Number type</label>
								<div class="col-md-3">
									<select name="number_type" class="selectpicker show-tick form-control">
										<option value="pstn">PSTN</option>
										<option value="voip">VoiP</option>
									</select>
								</div>
								<div class="col-md-4"></div>
							</div>
							<div class="form-group">
								<label for="charge_way" class="control-label col-md-5">Charge Way</label>
								<div class="col-md-3">
									<select name="charge_way" class="selectpicker show-tick form-control">
										<option value="fixed">Fixed</option>
										<option value="non-fixed">Non-Fixed</option>
									</select>
								</div>
								<div class="col-md-4"></div>
							</div>
							<div class="form-group">
								<label for="dial_destination" class="control-label col-md-5">Dial Destination</label>
								<div class="col-md-3">
									<select name="dial_destination" class="selectpicker show-tick form-control">
										<option value="0900">0900</option>
										<option value="fax">Fax</option>
										<option value="mobile-calling">Mobile Calling</option>
										<option value="international-call">International Call</option>
										<option value="national-call">National Call</option>
									</select>
								</div>
								<div class="col-md-4"></div>
							</div>
							<div class="form-group">
								<label for="cost_per_minute" class="control-label col-md-5">Cost Per Minute</label>
								<div class="col-md-3">
									<div class="input-group">
										<span class="input-group-addon">$</span>
										<input type="text" id="cost_per_minute" name="cost_per_minute" value="${ccr.cost_per_minute }" class="form-control" placeholder="0.0" data-error-field />
									</div>
								</div>
								<div class="col-md-4"></div>
							</div>
							<div class="form-group">
								<label for="cost_per_second" class="control-label col-md-5">Cost Per Second</label>
								<div class="col-md-3">
									<div class="input-group">
										<span class="input-group-addon">$</span>
										<input type="text" id="cost_per_second" value="${ccr.cost_per_second }" class="form-control" placeholder="0.0" disabled="disabled" data-error-field />
									</div>
								</div>
								<div class="col-md-4"></div>
							</div>
							
							<!-- button -->
							<div class="form-group">
								<div class="col-md-12">
									<a  href="javascript:void(0);"class="btn btn-success btn-lg pull-right" id="save">Save</a>
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
	
})(jQuery);
</script>
<jsp:include page="../footer-end.jsp" />