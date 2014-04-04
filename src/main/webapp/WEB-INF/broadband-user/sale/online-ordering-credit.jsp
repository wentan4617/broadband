<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<jsp:include page="header.jsp" />
<jsp:include page="../alert.jsp" />

<style>
.personal-info li{
	padding:5px 40px;
}
</style>

<div class="container">
	<div class="row">
		<div class="col-md-12">
			<div class="panel panel-success">
				<div class="panel-heading">
					<h4 class="panel-title">
						<a data-toggle="collapse" data-toggle="collapse"
							data-parent="#customerOrderAccordion" href="#collapseOrderInfo">
							Customer Credit Card Information
						</a>
					</h4>
				</div>
				<div id="collapseOrderInfo" class="panel-collapse collapse in">
					<div class="panel-body">
						<form:form modelAttribute="customerCredit" method="post" action="${ctx}/broadband-user/sale/online/ordering/order/credit/create" class="form-horizontal">
						  <div class="form-group">
							<div class="col-sm-12">
							<input type="hidden" name="order_id" value="${order_id}"/>
							<input type="hidden" name="customer_id" value="${customer_id}"/>
								<a class="btn btn-success btn-lg pull-right" target="_blank" href="${ctx}/broadband-user/crm/customer/order/pdf/download/${order_id}">
									<span class="glyphicon glyphicon-save"></span> ORDER PDF
								</a>
							</div>
						  </div>
							<hr/>
						  <div class="form-group">
							<label for="" class="control-label col-sm-4">Card Type</label>
							<div class="col-sm-8">
								<ul class="list-inline topup-list" style="margin:5px 0 0 0;">
									<li>
										<input type="radio" name="card_type" value="VISA" checked="checked"/>
										&nbsp; <strong>VISA</strong>
									</li>
									<li>
										<input type="radio" name="card_type" value="MASTERCARD"/>
										&nbsp; <strong>MasterCard</strong>
									</li>
								</ul>
							</div>
						  </div>
						  <div class="form-group">
						    	<label class="col-sm-12 control-label"></label>
						  </div>
						  <div class="form-group">
							    <label for="card_number" class="col-sm-4 control-label">Holder Name</label>
							    <div class="col-sm-4">
									<form:input path="holder_name" class="form-control" placeholder="Holder Name" />
							    </div>
							    <div class="col-sm-4">
									<p class="help-block">
										<form:errors path="holder_name" cssErrorClass="error"/>
									</p>
							    </div>
							  </div>
						  <div class="form-group">
							    <label for="card_number" class="col-sm-4 control-label">Card Number</label>
							    <div class="col-sm-4">
									<form:input path="card_number" class="form-control" placeholder="Card Number" />
							    </div>
							    <div class="col-sm-4">
									<p class="help-block">
										<form:errors path="card_number" cssErrorClass="error"/>
									</p>
							    </div>
						  </div>
						  <div class="form-group">
							    <label for="card_number" class="col-sm-4 control-label">Security Code</label>
							    <div class="col-sm-4">
									<form:input path="security_code" class="form-control" placeholder="Security Code" />
							    </div>
							    <div class="col-sm-4">
									<p class="help-block">
										<form:errors path="security_code" cssErrorClass="error"/>
									</p>
							    </div>
							  </div>
						  <div class="form-group">
							    <label for="card_number" class="col-sm-4 control-label">Expiry Date</label>
							    <div class="col-sm-4 input-group date" id="expiry_date_datepicker">
									<form:input path="expiry_date" class="form-control input-sm" placeholder="Expiry Date" />
									<span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
							    </div>
							    <div class="col-sm-4">
									<p class="help-block">
										<form:errors path="expiry_date" cssErrorClass="error"/>
									</p>
							    </div>
						  </div>
						  <div class="form-group">
						    <div class="col-sm-12">
						      <button type="submit" class="btn btn-success btn-lg pull-right">Submit</button>
						    </div>
						  </div>
						 </form:form>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
	
<jsp:include page="footer.jsp" />
<jsp:include page="script.jsp" />
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/icheck.min.js"></script>
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/bootstrap-datepicker.js"></script>
<script type="text/javascript">
(function($){
	$(':radio').iCheck({
		checkboxClass : 'icheckbox_square-green',
		radioClass : 'iradio_square-green'
	});
	// BEGIN Expiry Date Datapicker
	$('#expiry_date_datepicker').datepicker({
	    format: "yyyy-mm",
	    autoclose: true,
	    todayHighlight: true,
	    minViewMode: 1
	}).datepicker('setDate', new Date());
	// END Expiry Date Datapicker
})(jQuery);
</script>
<jsp:include page="../footer-end.jsp" />