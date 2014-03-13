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
								<h3 class="panel-title">Customer Information
									<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
								</h3>
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
								    	<label class="col-sm-4 control-label">Mobile:</label>
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
		
		$('a[data-name]').popover('destroy');
		
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
					html += '<h3 class="panel-title">';
					html += 'Order Serial: <span class="text-danger">' + customerOrder.id + '</span>';
					html += '<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>';
					html += '</h3>';
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
					html += '<label class="col-sm-8 control-label">Hardware needs to be sent:</label>';
					html += '<div class="col-sm-3">';
					html += '<p class="form-control-static" id="hardware_post_' + customerOrder.id + '">' + (customerOrder.hardware_post||0) + '</p>';
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
					detailHtml += '<h3 class="panel-title">'
					detailHtml += 'Order Details<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>';
					detailHtml += '</h3>';
					detailHtml += '</div>';
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
					detailHtml += '<th>&nbsp;</th>';
					detailHtml += '</tr>';
					detailHtml += '</thead>';
					detailHtml += '<tbody>';
					
					var  customerOrderDetails = customerOrder.customerOrderDetails;
					for (var j = 0, jlen = customerOrderDetails.length; j < jlen; j++) {
						var customerOrderDetail = customerOrderDetails[j];
						detailHtml += '<tr id="htr_' + customerOrderDetail.id + '"';
						if (customerOrderDetail.detail_type.indexOf('hardware-') > -1) {
							if (customerOrderDetail.is_post != 1) {
								detailHtml += ' class="warning" ';
							} else {
								detailHtml += ' class="success" ';
							}
						} 
						detailHtml += '>';
						detailHtml += '<td>' + customerOrderDetail.detail_name + '</td>';
						detailHtml += '<td>' + (customerOrderDetail.detail_type || ' ') + '</td>';
						detailHtml += '<td>' + (customerOrderDetail.detail_data_flow || ' ') + '</td>';
						detailHtml += '<td><strong>' + (customerOrderDetail.detail_plan_status || ' ') + '</strong></td>';
						detailHtml += '<td>' + (customerOrderDetail.detail_plan_type || ' ') + '</td>';
						detailHtml += '<td>' + (customerOrderDetail.detail_plan_sort || ' ') + '</td>';
						detailHtml += '<td>' + (customerOrderDetail.detail_unit || ' ') + '</td>';
						detailHtml += '<td><strong>' + (customerOrderDetail.detail_price || ' ') + '</strong></td>';
						detailHtml += '<td>';
						if (customerOrderDetail.detail_type.indexOf('hardware-') > -1) {
							detailHtml += '<a href="javascript:void(0);" data-name="show_comment_trackcode" data-id="' + customerOrderDetail.id + '"';
							detailHtml += 'data-is_post="' + (customerOrderDetail.is_post||0) + '"';
							detailHtml += 'data-track_code="' + (customerOrderDetail.track_code||"") + '"';
							detailHtml += 'data-comment="' + (customerOrderDetail.hardware_comment||"") + '"';
							detailHtml += '>';
							detailHtml += '<span class="glyphicon glyphicon-edit"></span>';
							detailHtml += '</a>';
						}
						detailHtml += '</td>';
						detailHtml += '</tr>';
					}

					detailHtml += '</tbody>';
					detailHtml += '</table>';
					detailHtml += '</div>';
					
					$('#customerOrderContainer').html(html);
					$('#customerOrderDetailContainer').html(detailHtml);
					
					$('a[data-name="show_comment_trackcode"]').each(function(){
						
						var $this = $(this);
						
						var detailid = $this.attr('data-id');
						var is_post = $this.attr('data-is_post');
						var track_code = $this.attr('data-track_code');
						var comment = $this.attr('data-comment');
						
						var pop = "";
						pop += '<input type="text" class="form-control" placeholder="Post Track Code" id="trackcode_' + detailid + '" value="' + track_code + '"/>';
						pop += '<hr/>';
						pop += '<textarea class="form-control" rows="3" placeholder="Comment" id="comment_' + detailid + '">' + comment + '</textarea><hr/>';
						pop += '<button class="btn btn-success" data-name="trackcode_btn" data-id="' + detailid + '" id="detail_save_' + detailid + '">Save</button>';
						
						var opt = {
							html: true
							, placement: 'left'
							, title: 'Track Code And Comment'
							, content: pop
							, container: 'div[id="provision-customer-order-info-modal"]'
						};
						
						$this.popover(opt);
						
					});
					
					$('div[id="provision-customer-order-info-modal"]').delegate('button[data-name="trackcode_btn"]', 'click', function(){
						//alert('a');
						//console.log($('#trackcode_' + detail_id).val() + $('#comment_' + detail_id).val());
						var detail_id = $(this).attr('data-id');
						var data = {
							'order_id': customerOrder.id
							, 'hardware_post': customerOrder.hardware_post||0
							, 'detail_id': detail_id
							, 'is_post': ($('a[data-id="' +  detail_id + '"]').attr('data-is_post') == '1' ? 0 : 1)
							, 'comment': $('#comment_' + detail_id).val()
							, 'trackcode': $('#trackcode_' + detail_id).val()
						};
						//console.log(data);
						var url = '${ctx}/broadband-user/provision/customer/order/detail/set';
						$.post(url, data, function(cod){
							if (cod != null) {
								$('a[data-id="' + cod.params['id'] + '"]')
									.attr("data-is_post", cod.is_post)
									.attr("data-track_code", cod.track_code)
									.attr("data-comment", cod.hardware_comment)
								/* $('#trackcode_'  + cod.params['id']).val(cod.track_code);
								$('#comment_'  + cod.params['id']).val(cod.hardware_comment); */
								$('#hardware_post_' + customerOrder.id).text(customerOrder.hardware_post = cod.customerOrder.hardware_post);
								if (cod.is_post == 1) {
									$('#htr_' + cod.params['id']).removeClass('warning').addClass('success');
								} else {
									$('#htr_' + cod.params['id']).removeClass('success').addClass('warning');
								}
								
								$('a[data-name]').popover('destroy');
								$('a[data-name="show_comment_trackcode"]').each(function(){
									
									var $this = $(this);
									
									var detailid = $this.attr('data-id');
									var is_post = $this.attr('data-is_post');
									var track_code = $this.attr('data-track_code');
									var comment = $this.attr('data-comment');
									
									var pop = "";
									pop += '<input type="text" class="form-control" placeholder="Post Track Code" id="trackcode_' + detailid + '" value="' + track_code + '"/>';
									pop += '<hr/>';
									pop += '<textarea class="form-control" rows="3" placeholder="Comment" id="comment_' + detailid + '">' + comment + '</textarea><hr/>';
									pop += '<button class="btn btn-success" data-name="trackcode_btn" data-id="' + detailid + '" id="detail_save_' + detailid + '">Save</button>';
									
									var opt = {
										html: true
										, placement: 'left'
										, title: 'Track Code And Comment'
										, content: pop
										, container: 'div[id="provision-customer-order-info-modal"]'
									};
									
									$this.popover(opt);
									
								});
							}
						});
						//$this.popover('hide');


					});
				}
				
			}, 'json');
		}
		
		
	});
	
})(jQuery);
</script>


