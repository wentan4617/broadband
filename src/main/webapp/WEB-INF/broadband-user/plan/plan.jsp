<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<jsp:include page="../header.jsp" />
<jsp:include page="../alert.jsp" />

<div class="container">
	<div class="row">
		<div class="col-md-12">
			<div class="panel panel-success">
				<div class="panel-heading">
					<h4 class="panel-title">${panelheading }</h4>
				</div>
				<div class="panel-body">
					<form class="form-horizontal">
					
						<h4 class="text-success">Plan Information</h4>
						<hr/>
						
						<div class="form-group">
							<label for="plan_name" class="control-label col-md-4">Plan Name</label>
							<div class="col-md-3">
								<input value="${plan.plan_name}" type="text" id="plan_name" class="form-control" data-error-field/>
							</div>
						</div>
						
						<div class="form-group">
							<label for="plan_group" class="control-label col-md-4">Plan Group</label>
							<div class="col-md-3">
								<select id="plan_group" class="form-control selectpicker">
									<option value="">None</option>
									<option value="plan-no-term" ${plan.plan_group=='plan-no-term'?'selected="selected"':'' }>Plan-No-Term</option>
									<option value="plan-term" ${plan.plan_group=='plan-term'?'selected="selected"':'' }>Plan-Term</option>
									<option value="plan-topup" ${plan.plan_group=='plan-topup'?'selected="selected"':'' }>Plan-Topup</option>
								</select>
							</div>
						</div>
						
						<div class="form-group">
							<label for="plan_prepay_months" class="control-label col-md-4">Prepay Months (Qty)</label>
							<div class="col-md-3">
								<div class="input-group">
									<input type="text" value="${plan.plan_prepay_months}" id="plan_prepay_months" class="form-control" />
									<span class="input-group-addon">Months</span>
								</div>
							</div>
						</div>
						
						<div class="form-group">
							<label for="term_period" class="control-label col-md-4">Term Period</label>
							<div class="col-md-3">
								<div class="input-group">
									<input type="text" value="${plan.term_period}" id="term_period" class="form-control" />
									<span class="input-group-addon">Months</span>
								</div>
							</div>
						</div>
						
						<div class="form-group">
							<label for="plan_class" class="control-label col-md-4">Plan Class</label>
							<div class="col-md-3">
								<select id="plan_class" class="form-control selectpicker">
									<option value="">None</option>
									<option value="personal" ${plan.plan_class=='personal'?'selected="selected"':'' }>Personal</option>
									<option value="business" ${plan.plan_class=='business'?'selected="selected"':'' }>Business</option>
								</select>
							</div>
						</div>
						
						<div class="form-group">
							<label for="plan_type" class="control-label col-md-4">Plan Type</label>
							<div class="col-md-3">
								<select id="plan_type" class="form-control selectpicker">
									<option value="">None</option>
									<option value="ADSL" ${plan.plan_type=='ADSL'?'selected="selected"':'' }>ADSL</option>
									<option value="VDSL" ${plan.plan_type=='VDSL'?'selected="selected"':'' }>VDSL</option>
									<option value="UFB" ${plan.plan_type=='UFB'?'selected="selected"':'' }>UFB</option>
								</select>
							</div>
						</div>
						
						<div class="form-group">
							<label for="plan_sort" class="control-label col-md-4">Plan Sort</label>
							<div class="col-md-3">
								<select id="plan_sort" class="form-control selectpicker">
									<option value="">None</option>
									<option value="CLOTHED" ${plan.plan_sort=='CLOTHED'?'selected="selected"':'' }>CLOTHED</option>
									<option value="NAKED" ${plan.plan_sort=='NAKED'?'selected="selected"':'' }>NAKED</option>
								</select>
							</div>
						</div>
						
						<div class="form-group">
							<label for="plan_price" class="control-label col-md-4">Monthly fee (Inc. GST(P), Excl. GST(B), Current)</label>
							<div class="col-md-3">
								<div class="input-group">
									<span class="input-group-addon">$</span>
									<input type="text" value="${plan.plan_price}" id="plan_price" class="form-control" data-error-field/>
								</div>
							</div>
						</div>
						
						<div class="form-group">
							<label for="original_price" class="control-label col-md-4">Original fee (Inc. GST(P), Excl. GST(B))</label>
							<div class="col-md-3">
								<div class="input-group">
									<span class="input-group-addon">$</span>
									<input type="text" value="${plan.original_price}" id="original_price" class="form-control"/>
								</div>
							</div>
						</div>
						
						<div class="form-group">
							<label for="transition_fee" class="control-label col-md-4">Transition fee</label>
							<div class="col-md-3">
								<div class="input-group">
									<span class="input-group-addon">$</span>
									<input type="text" value="${plan.transition_fee}" id="transition_fee" class="form-control" />
								</div>
							</div>
						</div>
						
						<div class="form-group">
							<label for="plan_new_connection_fee" class="control-label col-md-4">New Connection fee</label>
							<div class="col-md-3">
								<div class="input-group">
									<span class="input-group-addon">$</span>
									<input type="text" value="${plan.plan_new_connection_fee}" id="plan_new_connection_fee" class="form-control" />
								</div>
							</div>
						</div>
						
						<div class="form-group">
							<label for="jackpot_fee" class="control-label col-md-4">Jackpoint Installation Fee</label>
							<div class="col-md-3">
								<div class="input-group">
									<span class="input-group-addon">$</span>
									<input type="text" value="${plan.jackpot_fee}" id="jackpot_fee" class="form-control" />
								</div>
							</div>
						</div>
						
						<div class="form-group">
							<label for="data_flow" class="control-label col-md-4">Data Flow</label>
							<div class="col-md-3">
								<div class="input-group">
									<input type="text" value="${plan.data_flow}" id="data_flow" class="form-control" />
									<span class="input-group-addon">GB</span>
								</div>
							</div>
						</div>
						
						<div class="form-group">
							<label for="pstn_count" class="control-label col-md-4">PSTN (Qty)</label>
							<div class="col-md-3">
								<input type="text" value="${plan.pstn_count}" id="pstn_count" class="form-control" />
							</div>
						</div>
						
						<div class="form-group">
							<label for="voip_count" class="control-label col-md-4">VOIP (Qty)</label>
							<div class="col-md-3">
								<input type="text" value="${plan.voip_count}" id="voip_count" class="form-control" />
							</div>
						</div>
						
						<%-- <div class="form-group">
							<label for="pstn_rental_amount" class="control-label col-md-4">PSTN Rental Amount</label>
							<div class="col-md-3">
								<div class="input-group">
									<span class="input-group-addon">$</span>
									<input type="text" value="${plan.pstn_rental_amount}" id="pstn_rental_amount" class="form-control" />
								</div>
							</div>
						</div> --%>
						
						<div class="form-group">
							<label for="plan_status" class="control-label col-md-4">Status</label>
							<div class="col-md-3">
								<select id="plan_status" class="form-control selectpicker">
									<option value="">None</option>
									<option value="active" ${plan.plan_status=='active'?'selected="selected"':'' }>Active</option>
									<option value="selling" ${plan.plan_status=='selling'?'selected="selected"':'' }>Selling</option>
									<option value="disable" ${plan.plan_status=='disable'?'selected="selected"':'' }>Disable</option>
								</select>
							</div>
						</div>
						
						<!-- Other Setting -->
						<hr/>
						<h4 class="text-success">Other Setting</h4>
						<hr/>
						
						<div class="form-group">
							<label class="control-label col-md-4"></label>
							<div class="col-md-3">
								<div class="checkbox" style="padding-left: 0px;">
									<label> 
										<input type="checkbox" id="promotion" ${plan.promotion?'checked="checked"':'' }/>&nbsp;<strong>Promotion</strong>
									</label>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label for="place_sort" class="control-label col-md-4">Place Sort</label>
							<div class="col-md-2">
								<input type="text" value="${plan.place_sort}" id="place_sort" class="form-control" />
							</div>
						</div>
						
						<!-- button  -->
						<hr/>
						<div class="form-group">
							<div class="col-md-3 col-md-offset-4">
								<button type="button" class="btn btn-success btn-lg btn-block" id="save-btn">Save</button>
							</div>
						</div>
						
						<!-- plan desc -->
						<hr/>
						<div class="form-group">
							<label for="plan_desc" class="control-label col-md-1">Description</label>
							<div class="col-md-11">
								<textarea id="plan_desc" class="form-control" rows="16">${plan.plan_desc}</textarea>
							</div>
						</div>
						
						<div class="form-group">
							<label for="memo" class="control-label col-md-1" >Memo</label>
							<div class="col-md-11">
								<textarea id="memo" class="form-control" rows="6">${plan.memo}</textarea>
							</div>
						</div>
						
					</form>
					
					<form:form modelAttribute="plan" method="post" action="${ctx}/broadband-user/plan/pic/edit" class="form-horizontal" enctype="multipart/form-data">
						<form:hidden path="id"/>
						
						<hr/>
						<h4 class="text-success">Plan Pictures</h4>
						<hr/>
						
						<div class="form-group">
						    <label for="img1" class="col-md-3 control-label">Picture 1:</label>
						    <div class="col-md-8">
								<input type="file" name="imgs" class="form-control" />
						    </div>
						</div>
						
						<div class="form-group">
						    <label for="img2" class="col-md-3 control-label">Picture 2:</label>
						    <div class="col-sm-8">
								<input type="file" name="imgs" class="form-control" />
						    </div>
						</div>
						
						<div class="form-group" >
						    <label for="img3" class="col-md-3 control-label">Picture 3:</label>
						    <div class="col-sm-8">
								<input type="file" name="imgs" class="form-control"/>
						    </div>
						</div>
						
						<hr/>
						<div class="form-group">
							<div class="col-md-3 col-md-offset-4">
								<button type="submit" class="btn btn-success btn-lg btn-block">Save Pictures</button>
							</div>
						</div>
						<hr/>
						
						<div class="form-group" >
							<div class="col-md-4">
							    <a href="#" class="thumbnail">
							      <img data-src="holder.js/300x200" alt="..."
								      <c:if test="${plan.img1!=null && plan.img1!=''}">
								      	src="${ctx}/public/upload/${plan.img1}"
								      </c:if>
							      >
							    </a>
							</div>
							<div class="col-md-4">
							    <a href="#" class="thumbnail">
							      <img data-src="holder.js/300x200" alt="..."
								      <c:if test="${plan.img2!=null && plan.img2!=''}">
								      	src="${ctx}/public/upload/${plan.img2}"
								      </c:if>
							      >
							    </a>
							</div>
							<div class="col-md-4">
							    <a href="#" class="thumbnail">
							      <img data-src="holder.js/300x200" alt="..."
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
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/holder.js"></script>
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/icheck.min.js"></script>
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/bootstrap-select.min.js"></script>
<script type="text/javascript">
(function($){
	
	$(':checkbox').iCheck({
		checkboxClass : 'icheckbox_square-green',
		radioClass : 'iradio_square-green'
	});
	
	$('.selectpicker').selectpicker();
	
	$('#save-btn').on("click", function(){
		var $btn = $(this);
		var plan = {
			id: '${plan.id}'
			, plan_name: $('#plan_name').val()
			, plan_group: $('#plan_group option:selected').val()
			, plan_prepay_months: $('#plan_prepay_months').val()
			, term_period: $('#term_period').val()
			, plan_class: $('#plan_class').val()
			, plan_type: $('#plan_type').val()
			, plan_sort: $('#plan_sort').val()
			, plan_price: $('#plan_price').val()
			, original_price: $('#original_price').val()
			, transition_fee: $('#transition_fee').val()
			, plan_new_connection_fee: $('#plan_new_connection_fee').val()
			, jackpot_fee: $('#jackpot_fee').val()
			, data_flow: $('#data_flow').val()
			, pstn_count: $('#pstn_count').val()
			, pstn_rental_amount: $('#pstn_rental_amount').val()
			, plan_status: $('#plan_status').val()
			, plan_desc: $('#plan_desc').val()
			, memo: $('#memo').val()
			, promotion: $('#promotion').prop("checked")
			, place_sort: $('#place_sort').val()
			, voip_count: $('#voip_count').val()
		};
		$btn.button('loading');
		$.post('${ctx}${action}', plan, function(json){
			if (!$.jsonValidation(json, 'right')) {
				window.location.href = '${ctx}' + json.url;
			}
		}, 'json').always(function () {
			$btn.button('reset');
	    });
	});
	
})(jQuery);
</script>
<jsp:include page="../footer-end.jsp" />