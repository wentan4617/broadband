<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<style>
#provision-customer-order-info-modal .form-group {
	margin-bottom:0px;
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
					<div class="col-md-12"  id="customer_order_detail_container"></div>
				</div>
			</div>
		</div>
	</div>
</div>

<script type="text/html" id="customer_information_form">
<jsp:include page="customer-information-form.html" />
</script>
<script type="text/html" id="customer_order_form">
<jsp:include page="customer-order-form.html" />
</script>
<script type="text/html" id="customer_order_detail_form">
<jsp:include page="customer-order-detail-form.html" />
</script>

<script type="text/javascript" src="${ctx}/public/bootstrap3/js/jTmpl.js"></script>
<script type="text/javascript">
(function($){
	
	var order_id = null;
	
	$('a[data-name="show_customer_order_info"]').click(function(){
		order_id = $(this).attr('data-id');
		$('#provision-customer-order-info-modal').modal("show");
	});
	
	$('#provision-customer-order-info-modal').on('show.bs.modal', function (e) {
		
		loadInfo();
		
	});
	
	
	function loadInfo() {
		
		$('#customer_information_container').empty();
		$('#customer_order_container').empty();
		$('#customer_order_detail_container').empty();
		
		$.get('${ctx}/broadband-user/provision/customer/order/' + order_id, function(customerOrder) {
			
			$('#customer_information_container').html(tmpl('customer_information_form', customerOrder.customer));
			$('#customer_order_container').html(tmpl('customer_order_form', customerOrder));
			$('#customer_order_detail_container').html(tmpl('customer_order_detail_form', customerOrder));

			$('button[data-name="trackcode_btn"]').click(function(){
				var $btn = $(this);
				var id = $btn.attr('data-cod-id');
				var data = {
					order_id: customerOrder.id
					, hardware_post: customerOrder.hardware_post || 0
					, detail_id: id
					, is_post: ($btn.attr('data-cod-ispost') == '1' ? 0 : 1)
					, comment: $('#comment_' + id).val()
					, trackcode: $('#trackcode_' + id).val()
				};
				var url = '${ctx}/broadband-user/provision/customer/order/detail/set';
				$.post(url, data, function(cod){
					loadInfo();
				});
			});
			
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


