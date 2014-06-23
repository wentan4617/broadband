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
						<a data-toggle="collapse" data-toggle="collapse" data-parent="#customerInfoAccordion" href="#collapseCustomerInfo">
							Personal Customer Create
						</a>
					</h4>
				</div>
				<div id="collapseCustomerInfo" class="panel-collapse collapse in">
					<div class="panel-body">
					
						<form class="form-horizontal">
						
							<!-- customer address -->
							<h4 class="text-success">Customer Address</h4>
							<hr/>
							
							<div class="form-group">
								<label for="address" class="control-label col-md-4">Customer Address</label>
								<div class="col-md-8">
									<input type="text" id="address" name="address" value="${customer.address }" class="form-control" data-error-field data-placement="top"/>
								</div>
							</div>
							
							<!-- customer account -->
							<hr/>
							<h4 class="text-success">Create Customer Account</h4>
							<hr/>
							
							<div class="form-group">
								<label for="cellphone" class="control-label col-md-4">Customer Mobile</label>
								<div class="col-md-4">
									<input type="text" id="cellphone" name="cellphone" value="${customer.cellphone }"class="form-control" placeholder="e.g.: 0210800123" data-error-field />
								</div>
							</div>
							<div class="form-group">
								<label for="email" class="control-label col-md-4">Customer Email</label>
								<div class="col-md-4">
									<input type="text" id="email" name="email" value="${customer.email }" class="form-control" placeholder="e.g.: welcome@cyberpark.co.nz" data-error-field/>
								</div>
							</div>
							
							<!-- Personal Information -->
							
							<hr/>
							<h4 class="text-success">Personal Information</h4>
							<hr/>
							
							<div class="form-group">
								<label for="title" class="control-label col-md-4">Title</label>
								<div class="col-md-2">
									<select name="title" id="title" class="selectpicker show-tick form-control">
										<option value="Mr">Mr</option>
										<option value="Mrs">Mrs</option>
										<option value="Ms">Ms</option>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label for="first_name" class="control-label col-md-4">First name</label>
								<div class="col-md-4">
									<input type="text" id="first_name" name="first_name" value="${customer.first_name }" class="form-control" data-error-field />
								</div>
							</div>
							<div class="form-group">
								<label for="last_name" class="control-label col-md-4">Last name</label>
								<div class="col-md-4">
									<input type="text" id="last_name" name="last_name" value="${customer.last_name }" class="form-control" data-error-field />
								</div>
							</div>
							
							<!-- For ID Purpose Only -->
							
							<hr/>
							<h4 class="text-success">For ID Purpose Only</h4>
							<hr/>
							
							<div class="form-group">
								<label for="identity_type" class="control-label col-md-4">Identity Type</label>
								<div class="col-md-3">
									<select name="identity_type" id="identity_type" class="selectpicker show-tick form-control">
										<option value="Driver Licence" ${customer.identity_type=='Driver Licence'?'selected="selected"':''}>Driver Licence</option>
										<option value="Passport" ${customer.identity_type=='Passport'?'selected="selected"':''}>Passport</option>
										<option value="18+" ${customer.identity_type=='18+'?'selected="selected"':''}>18+</option>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label for="identity_number" class="control-label col-md-4">Identity Number</label>
								<div class="col-md-4">
									<input type="text" id="identity_number" name="identity_number" value="${customer.identity_number }" class="form-control" data-error-field />
								</div>
							</div>
							
							<!-- Other options -->
							<hr/>
							<h4 class="text-success">Other Options</h4>
							<hr/>
							
							<div class="form-group">
								<label for="status" class="control-label col-md-4">Status</label>
								<div class="col-md-4">
									<select id="status" name="status" class="selectpicker show-tick form-control">
										<option value="active">Active</option>
										<option value="disabled">Disabled</option>
									</select>
								</div>
							</div>
							<hr/>
							
							<!-- button -->
							<div class="form-group">
								<div class="col-md-4">
								</div>
								<div class="col-md-2">
									<a  href="javascript:void(0);"class="btn btn-success btn-lg btn-block" id="save">Save</a>
								</div>
								<div class="col-md-2">
									<a  href="javascript:void(0);"class="btn btn-success btn-lg btn-block" id="next">Next</a>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<div id="map_canvas" style="width:720px;height:600px;display:none;"></div>
<jsp:include page="../footer.jsp" />
<jsp:include page="../script.jsp" />
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/icheck.min.js"></script>
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/bootstrap-select.min.js"></script>
<script type="text/javascript">
(function($){
	
	$(':radio').iCheck({
		checkboxClass : 'icheckbox_square-green',
		radioClass : 'iradio_square-green'
	});
	
	$('.selectpicker').selectpicker(); 
	
	$('#save, #next').click(function(){
		var $btn = $(this);
		var url = '${ctx}/broadband-user/crm/customer/personal/create';
		var customer = {
			address: $('#address').val()
			, cellphone: $('#cellphone').val()
			, email: $('#email').val()
			, title: $('#title').val()
			, first_name: $('#first_name').val()
			, last_name: $('#last_name').val()
			, identity_type: $('#identity_type').val()
			, identity_number: $('#identity_number').val()
			, customer_type: 'personal'
			, status: $('#status').val()
			, action: $btn.attr('id')
		};
		//console.log("customer request:");
		//console.log(customer);
		$btn.button('loading');
		$.ajax({
			type: 'post'
			, contentType:'application/json;charset=UTF-8'         
	   		, url: url
		   	, data: JSON.stringify(customer)
		   	, dataType: 'json'
		   	, success: function(json){
				if (json.hasErrors) {
					$.jsonValidation(json, 'right');
				} else {
					//console.log("customer response:");
					//console.log(json.model);
					window.location.href='${ctx}' + json.url;
				}
		   	}
		}).always(function () {
			$btn.button('reset');
	    });
	});
	
})(jQuery);
</script>
<script src="https://maps.google.com/maps/api/js?sensor=false&libraries=places&region=NZ" type="text/javascript"></script>
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/autoCompleteAddress.js"></script>
<jsp:include page="../footer-end.jsp" />