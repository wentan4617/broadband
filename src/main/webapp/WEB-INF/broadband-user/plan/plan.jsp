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
					<form:form modelAttribute="plan" method="post" action="${ctx}${action }" class="form-horizontal">
						<form:hidden path="id"/>
						<div class="form-group">
							<label for="plan_name" class="control-label col-md-4">Plan Name</label>
							<div class="col-md-3">
								<form:input path="plan_name" class="form-control" placeholder="Plan Name" />
							</div>
							<p class="help-block">
								<form:errors path="plan_name" cssErrorClass="error"/>
							</p>
						</div>
						<div class="form-group">
							<label for="plan_group" class="control-label col-md-4">Plan Group</label>
							<div class="col-md-3">
								<form:select path="plan_group" class="form-control">
									<form:option value="">None</form:option>
									<form:option value="plan-no-term">Plan No Term</form:option>
									<form:option value="plan-term">Plan Term</form:option>
									<form:option value="plan-topup">Plan Topup</form:option>
								</form:select>
							</div>
							<div class="col-md-5">
								<div class="well">
									<span class="text-danger">When choose different options, it will display other input items.</span>
								</div>
							</div>
						</div>
						
						<div id="noTermContainer" style="display: 
							<c:if test="${plan.plan_group == 'plan-no-term' || plan.plan_group == 'plan-term'}">block</c:if>">
							<hr/>
							<div class="form-group">
								<label for="plan_prepay_months" class="control-label col-md-4">Prepay Months Amount</label>
								<div class="col-md-3">
									<div class="input-group">
										<form:input path="plan_prepay_months" class="form-control" placeholder="" />
										<span class="input-group-addon">Months</span>
									</div>
								</div>
								<p class="help-block">
									<form:errors path="plan_prepay_months" cssErrorClass="error"/>
								</p>
							</div>
							<div class="form-group">
								<label for="term_period" class="control-label col-md-4">Term Period</label>
								<div class="col-md-3">
									<div class="input-group">
										<form:input path="term_period" class="form-control" placeholder="" />
										<span class="input-group-addon">Months</span>
									</div>
								</div>
								<p class="help-block">
									<form:errors path="term_period" cssErrorClass="error"/>
								</p>
							</div>
							<hr/>
						</div>
						
						
						<div class="form-group">
							<label for="plan_type" class="control-label col-md-4">Plan Type</label>
							<div class="col-md-3">
								<form:select path="plan_type" class="form-control">
									<form:option value="">None</form:option>
									<form:option value="ADSL">ADSL</form:option>
									<form:option value="VDSL">VDSL</form:option>
									<form:option value="UFB">UFB</form:option>
								</form:select>
							</div>
						</div>
						<div class="form-group">
							<label for="plan_sort" class="control-label col-md-4">Plan Sort</label>
							<div class="col-md-3">
								<form:select path="plan_sort" class="form-control">
									<form:option value="">None</form:option>
									<form:option value="NON-NAKED">NON-NAKED</form:option>
									<form:option value="NAKED">NAKED</form:option>
								</form:select>
							</div>
						</div>
						<div class="form-group">
							<label for="plan_price" class="control-label col-md-4">Monthly fee (Inc GST)</label>
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
							<label for="plan_new_connection_fee" class="control-label col-md-4">New Connection fee (Inc GST)</label>
							<div class="col-md-3">
								<div class="input-group">
									<span class="input-group-addon">$</span>
									<form:input path="plan_new_connection_fee" class="form-control" placeholder="" />
								</div>
							</div>
							<p class="help-block">
								<form:errors path="plan_new_connection_fee" cssErrorClass="error"/>
							</p>
						</div>
						<div class="form-group">
							<label for="data_flow" class="control-label col-md-4">Data Flow</label>
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
							<label for="pstn_count" class="control-label col-md-4">PSTN Amount</label>
							<div class="col-md-3">
								<form:input path="pstn_count" class="form-control" placeholder="" />
							</div>
							<p class="help-block">
								<form:errors path="pstn_count" cssErrorClass="error"/>
							</p>
						</div>
						<div class="form-group">
							<label for="pstn_rental_amount" class="control-label col-md-4">PSTN Rental Amount</label>
							<div class="col-md-3">
								<div class="input-group">
									<span class="input-group-addon">$</span>
									<form:input path="pstn_rental_amount" class="form-control" placeholder="" />
								</div>
							</div>
							<p class="help-block">
								<form:errors path="pstn_rental_amount" cssErrorClass="error"/>
							</p>
						</div>
						
						<div class="form-group">
							<label for="plan_status" class="control-label col-md-4">Status</label>
							<div class="col-md-3">
								<form:select path="plan_status" class="form-control">
									<form:option value="active">Active</form:option>
									<form:option value="selling">Selling</form:option>
									<form:option value="disable">Disable</form:option>
								</form:select>
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
							<label for="plan_desc" class="control-label col-md-4">Description</label>
							<div class="col-md-8">
								<form:textarea path="plan_desc" class="form-control" rows="12"/>
							</div>
						</div>
						<div class="form-group">
							<label for="memo" class="control-label col-md-4">Memo</label>
							<div class="col-md-8">
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
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/bootstrap-select.min.js"></script>
<script type="text/javascript">
(function($){
	$('.selectpicker').selectpicker();
	
	$('#plan_group').change(function(){
		var val = this.value;
		if (val === 'plan-topup') {
			$('#topupContainer').show('fast');
			$('#noTermContainer').hide('fast');
			//$('#termContainer').hide('fast');
		} else if (val === 'plan-no-term') {
			$('#topupContainer').hide('fast');
			$('#noTermContainer').show('fast');
			//$('#termContainer').hide('fast');
		} else if (val === 'plan-term') {
			$('#topupContainer').hide('fast');
			$('#noTermContainer').show('fast');
			//$('#termContainer').show('fast');
		} else {
			$('#topupContainer').hide('fast');
			$('#noTermContainer').hide('fast');
			//$('#termContainer').hide('fast');
		}
	});
})(jQuery);
</script>
<jsp:include page="../footer-end.jsp" />