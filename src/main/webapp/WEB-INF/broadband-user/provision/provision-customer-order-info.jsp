<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<style>
#provision-customer-order-info-modal .form-group {
	margin-bottom:6px;
}
</style>

<div class="modal fade" id="provision-customer-order-info-modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width:1200px;">
		<div class="modal-content">
			<div class="modal-body">
				<div class="row">
					<div class="col-md-6" id="customer_information_container"></div>
					<div class="col-md-6" id="customer_order_container"></div>
				</div>
				<div class="row">
					<div class="col-md-12"  id="customerOrderDetailContainer"></div>
				</div>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript" src="${ctx}/public/bootstrap3/js/jTmpl.js"></script>
<script type="text/html" id="customer_information_form">
<jsp:include page="customer-information-form.html" />
</script>
<script type="text/html" id="customer_order_form">
<jsp:include page="customer-order-form.html" />
</script>
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
			
			$.get('${ctx}/broadband-user/provision/customer/order/' + order_id, function(customerOrder) {
				
				$('#customer_information_container').html(tmpl('customer_information_form', customerOrder.customer));
				$('#customer_order_container').html(tmpl('customer_order_form', customerOrder));
				
				//console.log(customerOrder);
				var customer = customerOrder.customer;
				if (customer != null) {
					
					var detailHtml = "";
					detailHtml += '<div class="panel panel-success">';
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
					detailHtml += '<th>Plan Type</th>';
					detailHtml += '<th>Plan Sort</th>';
					detailHtml += '<th>Plan Status</th>';
					detailHtml += '<th>Data flow</th>';
					detailHtml += '<th>Detail Price</th>';
					detailHtml += '<th>Unit</th>';
					detailHtml += '<th>Expired</th>';
					detailHtml += '<th>&nbsp;</th>';
					detailHtml += '</tr>';
					detailHtml += '</thead>';
					detailHtml += '<tbody>';
					
					var customerOrderDetails = customerOrder.customerOrderDetails;
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
						detailHtml += '<td>' + (customerOrderDetail.detail_plan_type || ' ') + '</td>';
						detailHtml += '<td>' + (customerOrderDetail.detail_plan_sort || ' ') + '</td>';
						detailHtml += '<td><strong>' + (customerOrderDetail.detail_plan_status || ' ') + '</strong></td>';
						detailHtml += '<td>' + (customerOrderDetail.detail_data_flow || ' ') + '</td>';
						detailHtml += '<td><strong>' + (customerOrderDetail.detail_price || ' ') + '</strong></td>';
						detailHtml += '<td>' + (customerOrderDetail.detail_unit || ' ') + '</td>';
						detailHtml += '<td><strong>' + (customerOrderDetail.detail_expired_str || ' ') + '</strong></td>';
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
					
					//$('#customerOrderContainer').html(html);
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


