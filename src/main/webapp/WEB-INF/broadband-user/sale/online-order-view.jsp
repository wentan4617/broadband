<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<jsp:include page="../header.jsp" />
<jsp:include page="../alert.jsp" />

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
						<c:if test="${userSession.user_role != 'sales'}">
							<select id="select_user" class="selectpicker">
							    <optgroup label="Sales List">
						    		<option value="0" data-name="selected" selected="selected">All</option>
							    	<c:forEach var="user" items="${users}">
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
					<form id="orderForm" action="" method="post">
					<input type="hidden" name="sale_id"/>
					<input type="hidden" name="signature"/>
					<table class="table" style="font-size:12px;">
						<thead >
							<tr>
								<th><input type="checkbox" id="checkbox_orders_top" /></th>
								<th>Order Id</th>
								<th>Order Status</th>
								<th>Order Type</th>
								<th>Broadband Type</th>
								<th>Total Price (Inc GST)($)</th>
								<th>Signature</th>
								<th>Sales Id</th>
								<th>Operations</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="order" items="${page.results }">
								<tr>
									<td>
										<input type="checkbox" name="checkbox_orders" value="${order.id}"/>
									</td>
									<td>
										${order.id }
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
									<td>
										<fmt:formatNumber value="${order.order_total_price }" type="number" pattern="#,#00.00" />
										
									</td>
									<td>${order.signature }</td>
									<td>${order.sale_id }</td>
									<td>
										<c:if test="${order.order_pdf_path != null}">
											<a target="_blank" href="${ctx}/broadband-user/crm/customer/order/pdf/download/${order.id}" class="glyphicon glyphicon-floppy-save btn-lg" data-toggle="tooltip" data-placement="bottom" data-original-title="Download Order PDF"></a>
										</c:if>
										<c:if test="${order.credit_pdf_path != null}">
										<a target="_blank" href="${ctx}/broadband-user/crm/customer/order/credit/pdf/download/${order.id}" class="glyphicon glyphicon-floppy-save btn-lg" data-toggle="tooltip" data-placement="bottom" data-original-title="Download Credit PDF"></a>
										</c:if>|
										<a data-name="upload-pdf" data-order-id="${order.id}" data-customer-id="${order.customer.id}" data-sale-id="${order.sale_id}" class="glyphicon glyphicon-floppy-open btn-lg" data-toggle="modal" data-placement="bottom" data-target="#uploadModal" style="cursor:pointer;"></a>
									</td>
								</tr>
							</c:forEach>
						</tbody>
						<tfoot>
						<tr>
							<td colspan="11">
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
					</form>
				</c:if>
				<c:if test="${fn:length(page.results) <= 0 }">
					<form id="orderForm" action="" method="post" style="display:none;">
						<input type="hidden" name="sale_id"/>
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

<jsp:include page="../footer.jsp" />
<jsp:include page="../script.jsp" />
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/bootstrap-select.min.js"></script>
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
		var val = this.value;
		$('input[name="sale_id"]').val(val);
		$('#orderForm').prop('action', '${ctx}/broadband-user/sale/online/ordering/view/by_sales_id');
		$('#orderForm').submit();
	});
	
	$('a[data-name="upload-pdf"]').click(function(){
		$('input[name="order_id"]').val($(this).attr('data-order-id'));
		$('input[name="customer_id"]').val($(this).attr('data-customer-id'));
		$('input[name="sale_id"]').val($(this).attr('data-sale-id'));
	});
	
})(jQuery);
</script>
<jsp:include page="../footer-end.jsp" />