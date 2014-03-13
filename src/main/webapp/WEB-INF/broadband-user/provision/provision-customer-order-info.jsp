<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<div class="modal fade" id="provision-customer-order-info-modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width:1200px;">
		<div class="modal-content">
			<div class="modal-body">
				
				<div class="row">
					<div class="col-md-5">
						<div class="panel panel-default">
							<div class="panel-heading">
								Customer Information
								<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
							</div>
							<div class="panel-body">
								<form class="form-horizontal" role="form">
									<div class="form-group">
								    	<label class="col-sm-4 control-label">Login Name:</label>
								    	<div class="col-sm-8">
								      		<p class="form-control-static" id="c_login_name"></p>
								    	</div>
								  	</div>
								  	<div class="form-group">
								    	<label class="col-sm-4 control-label">Password:</label>
								    	<div class="col-sm-8">
								      		<p class="form-control-static" id="c_password"></p>
								    	</div>
								  	</div>
								  	<div class="form-group">
								    	<label class="col-sm-4 control-label">User Name:</label>
								    	<div class="col-sm-8">
								      		<p class="form-control-static" id="c_user_name"></p>
								    	</div>
								  	</div>
								  	<div class="form-group">
								    	<label class="col-sm-4 control-label">First Name:</label>
								    	<div class="col-sm-8">
								      		<p class="form-control-static" id="c_first_name"></p>
								    	</div>
								  	</div>
								  	<div class="form-group">
								    	<label class="col-sm-4 control-label">Last Name:</label>
								    	<div class="col-sm-8">
								      		<p class="form-control-static" id="c_last_name"></p>
								    	</div>
								  	</div>
								  	<div class="form-group">
								    	<label class="col-sm-4 control-label">Address:</label>
								    	<div class="col-sm-8">
								      		<p class="form-control-static" id="c_address"></p>
								    	</div>
								  	</div>
								  	<div class="form-group">
								    	<label class="col-sm-4 control-label">Email:</label>
								    	<div class="col-sm-8">
								      		<p class="form-control-static" id="c_email">email@example.com</p>
								    	</div>
								  	</div>
								  	<div class="form-group">
								    	<label class="col-sm-4 control-label">Phone:</label>
								    	<div class="col-sm-8">
								      		<p class="form-control-static" id="c_phone"></p>
								    	</div>
								  	</div>
								  	<div class="form-group">
								    	<label class="col-sm-4 control-label">Cell Phone:</label>
								    	<div class="col-sm-8">
								      		<p class="form-control-static" id="c_cellphone"></p>
								    	</div>
								  	</div>
								  	<div class="form-group">
								    	<label class="col-sm-4 control-label">Status:</label>
								    	<div class="col-sm-8">
								      		<p class="form-control-static" id="c_status"></p>
								    	</div>
								  	</div>
								  	<div class="form-group">
								    	<label class="col-sm-4 control-label">Register Date:</label>
								    	<div class="col-sm-8">
								      		<p class="form-control-static" id="c_register_date"></p>
								    	</div>
								  	</div>
								  	<div class="form-group">
								    	<label class="col-sm-4 control-label">Active Date:</label>
								    	<div class="col-sm-8">
								      		<p class="form-control-static" id="c_active_date"></p>
								    	</div>
								  	</div>
								</form>
								
							</div>
						</div>
					</div>
					<div class="col-md-7" id="customerOrderContainer"></div>
					<div class="col-md-12"  id="customerOrderDetailContainer"></div>
				</div>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">
(function($){
	
	var order_id = null;
	
	$('a[data-name="show_customer_order_info"]').click(function(){
		
		order_id = $(this).attr('data-id');
		
		$('#provision-customer-order-info-modal').modal("show");
	});
	
	$('#provision-customer-order-info-modal').on('show.bs.modal', function (e) {
		
		if (order_id != null) {
			$.get('${ctx}/broadband-user/provision/customer/order/' + order_id, function(customerOrder){
				
				//console.log(customerOrder);
				var customer = customerOrder.customer;
				if (customer != null) {
					$('#c_login_name').text(customer.login_name);
					$('#c_password').text(customer.password);
					$('#c_user_name').text(customer.user_name);
					$('#c_first_name').text(customer.first_name);
					$('#c_last_name').text(customer.last_name);
					$('#c_address').text(customer.address);
					$('#c_email').text(customer.email);
					$('#c_phone').text(customer.phone||"");
					$('#c_cellphone').text(customer.cellphone);
					$('#c_status').text(customer.status);
					$('#c_register_date').text(customer.register_date_str);
					$('#c_active_date').text(customer.active_date_str);
					
					var html = "";
					
					html += '<div class="panel panel-default">';
					html += '<div class="panel-heading">';
					html += 'Order Serial: <span class="text-danger">' + customerOrder.id + '</span>';
					html += '<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>';
					html += '</div>';
					html += '<div class="panel-body">';
					html += '<form class="form-horizontal" role="form">';
					html += '<h4>Order Basic Information</h4>';
					html += '<hr/>';
					html += '<div class="form-group">';
					html += '<label class="col-sm-2 control-label">Price:</label>';
					html += '<div class="col-sm-2">';
					html += '<p class="form-control-static">$ ' + (customerOrder.order_total_price||"") + '</p>';
					html += '</div>';
					html += '<label class="col-sm-4 control-label">Create Date:</label>';
					html += '<div class="col-sm-3">';
					html += '<p class="form-control-static">' + customerOrder.order_create_date_str + '</p>';
					html += '</div>';
					html += '</div>';
					html += '<div class="form-group">';
					html += '<label class="col-sm-2 control-label">Status:</label>';
					html += '<div class="col-sm-2">';
					html += '<p class="form-control-static text-danger">' + customerOrder.order_status + '</p>';
					html += '</div>';
					html += '<label class="col-sm-4 control-label">Service Givening Date:</label>';
					html += '<div class="col-sm-3">';
					html += '<p class="form-control-static">' + customerOrder.order_using_start_str + '</p>';
					html += '</div>';
					html += '</div>';
					html += '<div class="form-group">';
					html += '<label class="col-sm-8 control-label">Next Invoice Create Date:</label>';
					html += '<div class="col-sm-3">';
					html += '<p class="form-control-static">' + customerOrder.next_invoice_create_date_str + '</p>';
					html += '</div>';
					html += '</div>';
					html += '<div class="form-group">';
					html += '<label class="col-sm-8 control-label">Order Broadband Type:</label>';
					html += '<div class="col-sm-3">';
					html += '<p class="form-control-static">' + customerOrder.order_broadband_type + '</p>';
					html += '</div>';
					html += '</div>';
					html += '<div class="form-group">';
					html += '<label class="col-sm-8 control-label">Hardware Post:</label>';
					html += '<div class="col-sm-3">';
					html += '<p class="form-control-static">' + (customerOrder.hardware_post||"") + '</p>';
					html += '</div>';
					html += '</div>';
					html += '<div class="form-group">';
					html += '<label class="col-sm-2 control-label">SVLan:</label>';
					html += '<div class="col-sm-4">';
					html += '<p class="form-control-static">' + (customerOrder.svlan||"") + '</p>';
					html += '</div>';
					html += '<label class="col-sm-1 control-label">CVLan:</label>';
					html += '<div class="col-sm-4">';
					html += '<p class="form-control-static">' + (customerOrder.cvlan||"") + '</p>';
					html += '</div>';
					html += '</div>';
					html += '<div class="form-group">';
					html += '<label class="col-sm-5 control-label">Transition Provider Name:</label>';
					html += '<div class="col-sm-6">';
					html += '<p class="form-control-static">' + customerOrder.transition_provider_name + '</p>';
					html += '</div>';
					html += '</div>';
					html += '<div class="form-group">';
					html += '<label class="col-sm-5 control-label">Transition Account Holder Name:</label>';
					html += '<div class="col-sm-6">';
					html += '<p class="form-control-static">' + customerOrder.transition_account_holder_name + '</p>';
					html += '</div>';
					html += '</div>';
					html += '<div class="form-group">';
					html += '<label class="col-sm-5 control-label">Transition Account Number:</label>';
					html += '<div class="col-sm-6">';
					html += '<p class="form-control-static">' + customerOrder.transition_account_number + '</p>';
					html += '</div>';
					html += '</div>';
					html += '<div class="form-group">';
					html += '<label class="col-sm-5 control-label">Transition Porting Number :</label>';
					html += '<div class="col-sm-6">';
					html += '<p class="form-control-static">' + customerOrder.transition_porting_number  + '</p>';
					html += '</div>';
					html += '</div>';
					html += '</form>';
					
					var detailHtml = "";
					detailHtml += '<div class="panel panel-default">';
					detailHtml += '<div class="panel-heading">';
					detailHtml += 'Order Details<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>';
					detailHtml += '</div>';
					detailHtml += '<div class="panel-body">';
					detailHtml += '<table class="table">';
					detailHtml += '<thead>';
					detailHtml += '<tr>';
					detailHtml += '<th>Detail name</th>';
					detailHtml += '<th>Detail type</th>';
					detailHtml += '<th>Data flow</th>';
					detailHtml += '<th>Status</th>';
					detailHtml += '<th>Type</th>';
					detailHtml += '<th>Sort</th>';
					detailHtml += '<th>Unit</th>';
					detailHtml += '<th>Price</th>';
					detailHtml += '<th>Track Code</th>';
					detailHtml += '<th>&nbsp;</th>';
					detailHtml += '</tr>';
					detailHtml += '</thead>';
					detailHtml += '<tbody>';
					
					var  customerOrderDetails = customerOrder.customerOrderDetails;
					for (var j = 0, jlen = customerOrderDetails.length; j < jlen; j++) {
						var customerOrderDetail = customerOrderDetails[j];
						detailHtml += '<tr>';
						detailHtml += '<td>' + customerOrderDetail.detail_name + '</td>';
						detailHtml += '<td>' + (customerOrderDetail.detail_type || ' ') + '</td>';
						detailHtml += '<td>' + (customerOrderDetail.detail_data_flow || ' ') + '</td>';
						detailHtml += '<td><strong>' + (customerOrderDetail.detail_plan_status || ' ') + '</strong></td>';
						detailHtml += '<td>' + (customerOrderDetail.detail_plan_type || ' ') + '</td>';
						detailHtml += '<td>' + (customerOrderDetail.detail_plan_sort || ' ') + '</td>';
						detailHtml += '<td>' + (customerOrderDetail.detail_unit || ' ') + '</td>';
						detailHtml += '<td><strong>' + (customerOrderDetail.detail_price || ' ') + '</strong></td>';
						detailHtml += '<td>' + (customerOrderDetail.track_code || ' ') + '</td>';
						detailHtml += '<td><a href="javascript:void(0);" data-name="show_comment"><span class="glyphicon glyphicon-comment"></span></a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0);" data-name="show_comment_trackcode"><span class="glyphicon glyphicon-italic"></span></a></td>';
						detailHtml += '</tr>';
					}

					detailHtml += '</tbody>';
					detailHtml += '</table>';
					detailHtml += '</div>';
					detailHtml += '</div>';
					
					$('#customerOrderContainer').html(html);
					$('#customerOrderDetailContainer').html(detailHtml);
				}
				
			}, 'json');
		}
		
		
	});
	//
})(jQuery);
</script>


