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
				<div class="panel-heading">
					<h4 class="panel-title">${panelheading }</h4>
				</div>
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
							<label for="plan_class" class="control-label col-md-4">Plan Class</label>
							<div class="col-md-3">
								<form:select path="plan_class" class="form-control">
									<form:option value="">None</form:option>
									<form:option value="personal">Personal</form:option>
									<form:option value="business">Business</form:option>
								</form:select>
							</div>
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
									<form:option value="CLOTHING">CLOTHING</form:option>
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
					
					<form:form modelAttribute="plan" method="post" action="${ctx}/broadband-user/plan/pic/edit" class="form-horizontal" enctype="multipart/form-data">
						<form:hidden path="id"/>
						<hr/>
						<div class="page-header" style="margin:0;">
							<h3 class="text-success"><strong>Plan Pictures</strong></h3>
						</div>
						<div class="form-group" style="margin:15px 0 0 0;">
						    <label for="img1" class="col-sm-4 control-label">Picture 1:</label>
						    <div class="col-sm-8">
								<input type="file" name="imgs" class="form-control" placeholder="Picture 1 Path" />
						    </div>
						</div>
						<div class="form-group" style="margin:15px 0 0 0;">
						    <label for="img2" class="col-sm-4 control-label">Picture 2:</label>
						    <div class="col-sm-8">
								<input type="file" name="imgs" class="form-control" placeholder="Picture 2 Path" />
						    </div>
						</div>
						<div class="form-group" style="margin:15px 0 0 0;">
						    <label for="img3" class="col-sm-4 control-label">Picture 3:</label>
						    <div class="col-sm-8">
								<input type="file" name="imgs" class="form-control" placeholder="Picture 3 Path" />
						    </div>
						</div>
						<hr/>
						<div class="form-group">
							<div class="col-md-3 col-md-offset-4">
								<button type="submit" class="btn btn-success">Save Pictures</button>
							</div>
						</div>
						<div class="form-group" style="margin:15px 0 0 0;">
							<div class="col-md-4">
							    <a href="#" class="thumbnail">
							      <img data-src="holder.js/300x200" alt="..."
								      <c:if test="${plan.img1==null || plan.img1==''}">
								      	src="data:image/png;base64,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"
								      </c:if>
								      <c:if test="${plan.img1!=null && plan.img1!=''}">
								      	src="${ctx}/public/upload/${plan.img1}"
								      </c:if>
							      >
							    </a>
							</div>
							<div class="col-md-4">
							    <a href="#" class="thumbnail">
							      <img data-src="holder.js/300x200" alt="..."
								      <c:if test="${plan.img2==null || plan.img2==''}">
								      	src="data:image/png;base64,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"
								      </c:if>
								      <c:if test="${plan.img2!=null && plan.img2!=''}">
								      	src="${ctx}/public/upload/${plan.img2}"
								      </c:if>
							      >
							    </a>
							</div>
							<div class="col-md-4">
							    <a href="#" class="thumbnail">
							      <img data-src="holder.js/300x200" alt="..."
								      <c:if test="${plan.img3==null || plan.img3==''}">
								      	src="data:image/png;base64,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"
								      </c:if>
								      <c:if test="${plan.img3!=null && plan.img3!=''}">
								      	src="${ctx}/public/upload/${plan.img3}"
								      </c:if>
							      >
							    </a>
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