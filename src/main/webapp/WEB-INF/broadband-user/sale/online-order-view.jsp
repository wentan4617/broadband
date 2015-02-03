<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<jsp:include page="../header.jsp" />
<jsp:include page="../alert.jsp" />

<style>
</style>

<div class="container">
	<div class="page-header">
		<h1>
			Online Ordering List
		</h1>
	</div>
	
	<!--  -->
	<div class="row">
		<div class="col-md-12">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h4 class="panel-title">
						<a data-toggle="collapse" data-toggle="collapse" data-parent="#accordion" href="#collapseOne"> 
							Online Order Query
						</a>
					</h4>
				</div>
				<div id="collapseOne" class="panel-collapse collapse in">
					<div class="panel-body">
						<div class="btn-group btn-group">
							<a href="${ctx}/broadband-user/sale/online/ordering/view/by/${sale_id != 0 ? sale_id : 0 }/both" class="btn btn-default ${bothActive }">
								All Status&nbsp;<span class="badge">${bothSum}</span>
							</a>
							<a href="${ctx}/broadband-user/sale/online/ordering/view/by/${sale_id != 0 ? sale_id : 0 }/signed" class="btn btn-default ${signedActive }">
								Signed&nbsp;<span class="badge">${signedSum}</span>
							</a>
							<a href="${ctx}/broadband-user/sale/online/ordering/view/by/${sale_id != 0 ? sale_id : 0 }/unsigned" class="btn btn-default ${unsignedActive }">
								Unsigned&nbsp;<span class="badge">${unsignedSum}</span>
							</a>
						</div>
					</div>
				</div>
			</div>
			<div class="panel panel-default">
				<div class="panel-heading">
					<h4 class="panel-title">Online Order View&nbsp;
						<c:if test="${userSession.user_role != 'sales' && userSession.user_role != 'agent'}">
							<select id="select_user" class="selectpicker">
							    <optgroup label="Sales List">
						    		<option value="0" data-name="selected" selected="selected">All</option>
							    	<c:forEach var="user" items="${salesUsers}">
							    		<option value="${user.id}"
							    			<c:if test="${user.id==sale_id}">
							    				selected="selected"
							    			</c:if>
							    		>${user.user_name}</option>
							    	</c:forEach>
							    </optgroup>
							</select>
						</c:if>
					</h4>
				</div>
				<c:if test="${fn:length(page.results) > 0 }">
					<table class="table" style="font-size:12px;">
						<thead >
							<tr>
								<th><input type="checkbox" id="checkbox_orders_top" /></th>
								<th>Order Id</th>
								<th>Order Status</th>
								<th>Order Type</th>
								<th>Broadband Type</th>
								<th style="text-align:right;">Total Price (Inc GST)($)</th>
								<th>Signature</th>
								<th>Sales Name</th>
								<th>Operations</th>
								<c:if test="${userSession.user_role=='agent'}">
									<th>Payment</th>
								</c:if>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="order" items="${page.results }">
							<form id="orderForm" action="" method="post">
							<input type="hidden" name="sale_id"/>
							<input type="hidden" name="signature"/>
								<tr>
									<td>
										<input type="checkbox" name="checkbox_orders" value="${order.id}"/>
									</td>
									<td>
										<a href="#" data-name="show_customer_order_info" data-id="${order.id}">${order.id}</a>
									</td>
									<td>
										${order.order_status }
									</td>
									<td>
										${order.order_type }
									</td>
									<td>
										${order.order_broadband_type }
									</td>
									<td style="text-align:right;">
										<fmt:formatNumber value="${order.order_total_price }" type="number" pattern="#,#00.00" />
										
									</td>
									<td>${order.signature }</td>
									<td>
								    	<c:forEach var="user" items="${salesUsers}">
							    			<c:if test="${user.id==order.sale_id || user.id==order.user_id}">
							    				${user.user_name }
							    			</c:if>
								    	</c:forEach>
									</td>
									<td style="font-size:20px;">
										<c:if test="${order.order_pdf_path != null}">
											<a target="_blank" href="${ctx}/broadband-user/crm/customer/order/pdf/download/${order.id}" class="glyphicon glyphicon-floppy-save" data-toggle="tooltip" data-placement="bottom" data-original-title="Download Order PDF"></a>&nbsp;
										</c:if>
										<%-- <c:if test="${order.credit_pdf_path != null && userSession.user_role!='sales' && userSession.user_role!='agent'}">
											<a target="_blank" href="${ctx}/broadband-user/crm/customer/order/credit/pdf/download/${order.id}" class="glyphicon glyphicon-floppy-save" data-toggle="tooltip" data-placement="bottom" data-original-title="Download Credit PDF"></a>&nbsp;
										</c:if> --%>
										<c:if test="${order.credit_pdf_path == null}">
											<a target="_blank" href="${ctx}/broadband-user/sale/online/ordering/order/credit/${order.customer_id}/${order.id}" class="glyphicon glyphicon-credit-card" data-toggle="tooltip" data-placement="bottom" data-original-title="Fill Credit Form"></a>&nbsp;
										</c:if>|
										<a href="javascript:void(0);" data-name="upload-pdf" data-order-id="${order.id}" data-customer-id="${order.customer.id}" data-sale-id="${order.sale_id}" class="glyphicon glyphicon-floppy-open" data-toggle="tooltip" data-placement="bottom" data-original-title="Upload Signed Form(s)"></a>&nbsp;
										<a href="javascript:void(0);" data-name="upload_previous_provider_invoice_pdf" data-order-id="${order.id}" data-customer-id="${order.customer.id}" data-sale-id="${order.sale_id}" class="glyphicon glyphicon-floppy-open" data-toggle="tooltip" data-placement="bottom" data-original-title="Upload Previous Provider Invoice"></a>&nbsp;
										
										<a href="javascript:void(0);" data-order-id="${order.id}" data-name="optional_request_btn" class="glyphicon glyphicon-info-sign" data-toggle="tooltip" data-placement="bottom" data-original-title="Sales's Optional Request"></a>&nbsp;
										
										<c:if test="${order.order_status=='pending' || order.order_status=='pending-warning' }">
											<a href="javascript:void(0);" data-order-id="${order.id}" data-name="void_order_btn" class="glyphicon glyphicon-ban-circle" data-toggle="tooltip" data-placement="bottom" data-original-title="Void this order!"></a>&nbsp;
										</c:if>
										
									</td>
									<c:choose>
										<c:when test="${(order.ordering_form_pdf_path!=null && order.ordering_form_pdf_path!='')
			  									||(order.receipt_pdf_path!=null && order.receipt_pdf_path!='')}">
											<c:choose>
												<c:when test="${order.receipt_pdf_path!=null && order.receipt_pdf_path!=''}">
													<td></td>
												</c:when>
												<c:otherwise>
													<td>
														<div class="btn-group">
															<button type="button" class="btn btn-primary btn-xs" data-name="make_payment_<.= invoice.id .>">Make Payment</button>
															<button type="button" class="btn btn-primary btn-xs dropdown-toggle" data-toggle="dropdown">
																<span class="caret"></span>
															</button>
															<ul class="dropdown-menu" role="menu">
																<li><a href="${ctx}/broadband-user/crm/customer/invoice/payment/credit-card/${order.id}/agent_ordering/${order.order_status}"><span class="glyphicon glyphicon-link"></span>&nbsp;&nbsp;DPS</a></li>
															</ul>
														</div>
													</td>
												</c:otherwise>
											</c:choose>
										</c:when>
										<c:otherwise>
												<td></td>
										</c:otherwise>
									</c:choose>
								</tr>
							</form>
							
							<!-- Optional Request Modal -->
							<form action="${ctx}/broadband-user/sale/online/ordering/optional_request/edit" method="post">
							<input type="hidden" name="order_id" value="${order.id}" />
							<input type="hidden" name="sale_id" value="${sale_id}"/>
							<div class="modal fade" id="optionalRequestModel_${order.id}" tabindex="-1" role="dialog" aria-labelledby="optionalRequestModalLabel" aria-hidden="true">
								<div class="modal-dialog">
									<div class="modal-content">
										<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
											<h4 class="modal-title" id="optionalRequestModalLabel">Request Record: </h4>
										</div>
										<div class="modal-body">
											<textarea class="form-control" name="optional_request" rows="5">${order.optional_request }</textarea>
										</div>
										<div class="modal-footer">
											<button type="submit" class="btn btn-primary" >Confirm to update record</button>
										</div>
									</div>
								</div>
							</div>
							</form>
							
							<!-- Status Modal -->
							<form action="${ctx}/broadband-user/sale/online/ordering/status/edit" method="post">
							<input type="hidden" name="order_id" value="${order.id}" />
							<input type="hidden" name="old_status" value="${order.order_status }" />
							<input type="hidden" name="status" value="void" />
							<input type="hidden" name="sale_id" value="${sale_id}"/>
							<div class="modal fade" id="orderStatusModel_${order.id}" tabindex="-1" role="dialog" aria-labelledby="orderStatusModalLabel" aria-hidden="true">
								<div class="modal-dialog">
									<div class="modal-content">
										<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
											<h4 class="modal-title" id="orderStatusModalLabel">Void Order: </h4>
										</div>
										<div class="modal-body">
											<p>
												Sure to void this order? this order is in ${order.order_status } status and it is allowed to be void.
											</p>
										</div>
										<div class="modal-footer">
											<button type="submit" class="btn btn-primary" >Confirm to void order</button>
										</div>
									</div>
								</div>
							</div>
							</form>
							</c:forEach>
						</tbody>
						<tfoot>
						<tr>
							<td colspan="11" style="text-align:left;">
								<ul class="pagination">
									<c:forEach var="num" begin="1" end="${page.totalPage }" step="1">
										<li class="${page.pageNo == num ? 'active' : ''}">
											<a href="${ctx}/broadband-user/sale/online/ordering/view/${num}/${sale_id != 0 ? sale_id : 0 }">${num}</a>
										</li>
									</c:forEach>
								</ul>
							</td>
						</tr>
					</tfoot>
					</table>
				</c:if>
				<c:if test="${fn:length(page.results) <= 0 }">
					<form id="orderForm" action="" method="post" style="display:none;">
					</form>
					<div class="panel-body">
						<div class="alert alert-warning">No records have been found.</div>
					</div>
				</c:if>
			</div>
		</div>
	</div>
</div>

<!-- Upload PDF Modal -->
<form class="form-horizontal" action="${ctx }/broadband-user/sale/online/ordering/order/upload-single" method="post" enctype="multipart/form-data">
	<input type="hidden" name="order_id"/>
	<input type="hidden" name="customer_id"/>
	<input type="hidden" name="sale_id"/>
	<div class="modal fade" id="uploadModal" tabindex="-1" role="dialog" aria-labelledby="uploadModalLabel" aria-hidden="true">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
	        <h3 class="modal-title text-danger" id="uploadModalLabel"><strong>Upload PDF</strong></h3>
	      </div>
	      <div class="modal-body">
			<div class="form-group">
				<label class="control-label col-md-4">Order File Path</label>
				<div class="col-md-6">
					<input type="file" name="order_pdf_path" class="form-control input-sm" placeholder="Choose a file"/>
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-md-4">Credit File Path</label>
				<div class="col-md-6">
					<input type="file" name="credit_pdf_path" class="form-control input-sm" placeholder="Choose a file"/>
				</div>
			</div>
	      </div>
	      <div class="modal-footer">
	        <button type="submit" class="btn btn-success">Upload File</button>
	      </div>
	    </div><!-- /.modal-content -->
	  </div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
</form>

<!-- Upload Previous Provider Invoice PDF Modal -->
<form class="form-horizontal" action="${ctx }/broadband-user/sale/online/ordering/order/upload_previous_provider_invoice_pdf" method="post" enctype="multipart/form-data">
	<input type="hidden" name="order_id"/>
	<input type="hidden" name="customer_id"/>
	<input type="hidden" name="sale_id"/>
	<div class="modal fade" id="uploadPreviousProviderInvoiceModal" tabindex="-1" role="dialog" aria-labelledby="uploadPreviousProviderInvoiceModalLabel" aria-hidden="true">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
	        <h3 class="modal-title text-danger" id="uploadPreviousProviderInvoiceModalLabel"><strong>Upload Provider Invoice PDF</strong></h3>
	      </div>
	      <div class="modal-body">
			<div class="form-group">
				<label class="control-label col-md-6">Previous Provider Invoice Path</label>
				<div class="col-md-4">
					<input type="file" name="previous_provider_invoice_path" class="form-control input-sm" placeholder="Choose a file"/>
				</div>
			</div>
	      </div>
	      <div class="modal-footer">
	        <button type="submit" class="btn btn-success">Upload File</button>
	      </div>
	    </div><!-- /.modal-content -->
	  </div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
</form>

<!-- Void Order Modal -->
<form class="form-horizontal" action="${ctx }/broadband-user/sale/online/ordering/order/upload_previous_provider_invoice_pdf" method="post" enctype="multipart/form-data">
	<input type="hidden" name="order_id"/>
	<input type="hidden" name="customer_id"/>
	<input type="hidden" name="sale_id"/>
	<div class="modal fade" id="uploadPreviousProviderInvoiceModal" tabindex="-1" role="dialog" aria-labelledby="uploadPreviousProviderInvoiceModalLabel" aria-hidden="true">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
	        <h3 class="modal-title text-danger" id="uploadPreviousProviderInvoiceModalLabel"><strong>Upload Provider Invoice PDF</strong></h3>
	      </div>
	      <div class="modal-body">
			<div class="form-group">
				<label class="control-label col-md-6">Previous Provider Invoice Path</label>
				<div class="col-md-4">
					<input type="file" name="previous_provider_invoice_path" class="form-control input-sm" placeholder="Choose a file"/>
				</div>
			</div>
	      </div>
	      <div class="modal-footer">
	        <button type="submit" class="btn btn-success">Upload File</button>
	      </div>
	    </div><!-- /.modal-content -->
	  </div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
</form>

<div class="modal fade" id="provision-customer-order-info-modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width:1200px">
		<div class="modal-content">
			<div class="modal-body">
				<div class="row">
					<div class="col-md-5" id="customer-information-tmpl"></div>
					<div class="col-md-7" id="customer-order-tmpl"></div>
				</div>
				<div class="row">
					<div class="col-md-12" id="customer-order-detail-tmpl"></div>
				</div>
			</div>
		</div>
	</div>
</div>

<script type="text/html" id="customer_information_tmpl">
<jsp:include page="../provision/customer-information-tmpl.html" />
</script>

<script type="text/html" id="customer_order_tmpl">
<jsp:include page="../provision/customer-order-tmpl.html" />
</script>

<script type="text/html" id="customer_order_detail_tmpl">
<jsp:include page="../provision/customer-order-detail-tmpl.html" />
</script>

<jsp:include page="../footer.jsp" />
<jsp:include page="../script.jsp" />
<script type="text/javascript">
(function($){
	$('a[data-toggle="tooltip"]').tooltip();
	$('.selectpicker').selectpicker();
	$('#checkbox_orders_top').click(function(){
		var b = $(this).prop("checked");
		if (b) {
			$('input[name="checkbox_orders"]').prop("checked", true);
		} else {
			$('input[name="checkbox_orders"]').prop("checked", false);
		}
	});
	
	$('#select_user').change(function(){
		var $this = $(this);
		var sales_id = this.value;
		$('#orderForm').prop('action', '${ctx}/broadband-user/sale/online/ordering/view/by/'+sales_id);
		$('#orderForm').submit();
	});
	
	$('a[data-name="upload-pdf"]').click(function(){
		$('input[name="order_id"]').val($(this).attr('data-order-id'));
		$('input[name="customer_id"]').val($(this).attr('data-customer-id'));
		$('input[name="sale_id"]').val($(this).attr('data-sale-id'));
		$('#uploadModal').modal('show');
	});
	
	$('a[data-name="upload_previous_provider_invoice_pdf"]').click(function(){
		$('input[name="order_id"]').val($(this).attr('data-order-id'));
		$('input[name="customer_id"]').val($(this).attr('data-customer-id'));
		$('input[name="sale_id"]').val($(this).attr('data-sale-id'));
		$('#uploadPreviousProviderInvoiceModal').modal('show');
	});
	
	$('a[data-name="optional_request_btn"]').click(function(){
		$('#optionalRequestModel_'+$(this).attr('data-order-id')).modal('show');
	});
	
	$('a[data-name="void_order_btn"]').click(function(){
		$('#orderStatusModel_'+$(this).attr('data-order-id')).modal('show');
	});
	
	$('a[data-name="show_customer_order_info"]').click(function(){
		order_id = $(this).attr('data-id');
		$('#provision-customer-order-info-modal').modal("show");
	});
	
	$('#provision-customer-order-info-modal').on('show.bs.modal', function (e) {		
		loadInfo();
	});
	
	function loadInfo() {
		
		$('#customer-information-tmpl').empty();
		$('#customer-order-tmpl').empty();
		$('#customer-order-detail-tmpl').empty();
		
		$.get('${ctx}/broadband-user/provision/order/' + order_id, function(order) {
			
			order.user_role = '${userSession.user_role}';
			$('#customer-information-tmpl').html(tmpl('customer_information_tmpl', order));
			$('#customer-order-tmpl').html(tmpl('customer_order_tmpl', order));
			$('#customer-order-detail-tmpl').html(tmpl('customer_order_detail_tmpl', order));
			
			$('span[data-hardware]').on('click', function(){
				var $span = $(this);
				var id = $span.attr('data-id');
				var status = $span.attr('data-status');
				if (status == 'close') {
					$('#tr' + id).show(function(){
						$span.attr('data-status', 'open');
						$span.attr('class', 'glyphicon glyphicon-minus-sign');
					});
				} else if (status == 'open') {
					$('#tr' + id).hide(function(){
						$span.attr('data-status', 'close');
						$span.attr('class', 'glyphicon glyphicon-plus-sign');
					});
				}
			});
		}, 'json');
	}
	
})(jQuery);
</script>
<jsp:include page="../footer-end.jsp" />