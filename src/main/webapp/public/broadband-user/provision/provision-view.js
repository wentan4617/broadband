(function($){
	
	var _tmpl = $('#provision_query_tmpl')
		, ctx = _tmpl.attr('data-ctx')
		, user_role = _tmpl.attr('data-user_role')
		, order_id = null
		
	function provisionQueryLoading(status) {
		$.get(ctx + '/broadband-user/provision/query/loading', function(provision) {
			$('#provision-query-tmpl').html(tmpl('provision_query_tmpl', provision));
			
			$('#pending_to_ordering, #paid_to_ordering').click(function(){
				var that = this.id;
				var data = {
					checkbox_orders: ''
					, change_order_status: $(this).attr('data-val')
				};
				if (that == 'pending_to_ordering') {
					data.process_way = 'pending to ordering-pending';
				} else if (that == 'paid_to_ordering') {
					data.process_way = 'paid to ordering-paid';
				} 
				$('input[name="checkbox_orders"]:checked').each(function(i) {
					data.checkbox_orders += (i == 0 ? this.value : ',' + this.value);
				});
				//console.log(data);
				
				$.post(ctx + '/broadband-user/provision/orders/status', data, function(json){
					if (!$.jsonValidation(json)) {
						if (that == 'pending_to_ordering') {
							provisionQueryLoading('pending');
						} else if (that == 'paid_to_ordering') {
							provisionQueryLoading('paid');
						}
					}
				});
			});
			
			$('a[data-for-parent]').click(function(){
				
				$('#pendingToOrdering, #paidToOrdering').hide();
				
				var parent = $(this).attr('data-for-parent');
				var status = $(this).attr('data-status');
				$('a[data-parent]').removeClass('active');
				$('a[data-parent="' + parent + '"]').addClass('active');
				
				if (parent == 'new-order') {
					if (status == 'pending') {
						$('#provision-view-title').html('New Order &gt; Pending');
						$('#pendingToOrdering').show();
					} else if (status == 'paid') {
						$('#provision-view-title').html('New Order &gt; Paid');
						$('#paidToOrdering').show();
					} else if (status == 'pending-warning') {
						$('#provision-view-title').html('New Order &gt; Pending Warning');
					}
				} else if (parent == 'provision') {
					if (status == 'ordering-pending') {
						$('#provision-view-title').html('Provision &gt; Ordering Pending');
					} else if (status == 'ordering-paid') {
						$('#provision-view-title').html('Provision &gt; Ordering Paid');
					} else if (status == 'rfs') {
						$('#provision-view-title').html('Provision &gt; RFS');
					}
				} else if (parent == 'in-service') {
					$('#provision-view-title').html('In Service &gt; Using');
				} else if (parent == 'suspension') {
					if (status == 'overflow') {
						$('#provision-view-title').html('Suspension &gt; Overflow');
					} else if (status == 'suspended') {
						$('#provision-view-title').html('Suspension &gt; Suspended');
					} 
				} else if (parent == 'disconnect') {
					if (status == 'waiting-for-disconnect') {
						$('#provision-view-title').html('Disconnect &gt; Waiting For Disconnect');
					} else if (status == 'disconnected') {
						$('#provision-view-title').html('Disconnect &gt; Disconnected');
					} 
				} else if (parent == 'void') {
					$('#provision-view-title').html('Void Order &gt; Void');
				} else if (parent == 'upgrade') {
					$('#provision-view-title').html('Upgrade Order &gt; Upgrade');
				}
				
				provisionOrdersLoading(1, status);
			});
			
			if (status) {
				$('a[data-status="' + status + '"]').trigger('click');
			} else {
				$('a[data-status="paid"]').trigger('click');
			}
			
		});
	}
	
	provisionQueryLoading(status);
	
	function provisionOrdersLoading(pageNo, order_status) {
		$.get(ctx + '/broadband-user/provision/orders/loading/' + pageNo + '/' + order_status, function(page) {
			page.ctx = ctx;
			$('#provision-orders-tmpl').html(tmpl('provision_orders_tmpl', page));
			$('#provision-orders-tmpl').find('tfoot a').click(function(){
				provisionOrdersLoading($(this).attr('data-pageNo'), order_status);
			});
			$('#checkbox_orders_top').click(function() {
				var b = $(this).prop("checked");
				if (b) { $('input[name="checkbox_orders"]').prop("checked", true); } 
				else { $('input[name="checkbox_orders"]').prop("checked", false); }
			});
			
			$('a[data-name="show_customer_order_info"]').click(function(){
				order_id = $(this).attr('data-id');
				$('#provision-customer-order-info-modal').modal("show");
			});
			
			$('#provision-customer-order-info-modal').on('show.bs.modal', function (e) {		
				loadInfo();
			});
			
		});
	}
	
	function loadInfo() {
		
		$('#customer-information-tmpl').empty();
		$('#customer-order-tmpl').empty();
		$('#customer-order-detail-tmpl').empty();
		
		$.get(ctx + '/broadband-user/provision/order/' + order_id, function(order) {
			
			order.user_role = user_role;
			$('#customer-information-tmpl').html(tmpl('customer_information_tmpl', order));
			$('#customer-order-tmpl').html(tmpl('customer_order_tmpl', order));
			$('#customer-order-detail-tmpl').html(tmpl('customer_order_detail_tmpl', order));

			$('button[data-name="trackcode_btn"]').click(function(){
				var l = Ladda.create(this); l.start();
				var id = $(this).attr('data-cod-id');
				var data = {
					order_id: order.id
					, hardware_post: order.hardware_post || 0
					, detail_id: id
					, is_post: ($(this).attr('data-cod-ispost') == '1' ? 0 : 1)
					, comment: $('#comment_' + id).val()
					, trackcode: $('#trackcode_' + id).val()
				};
				var url = ctx + '/broadband-user/provision/customer/order/detail/set';
				$.post(url, data, function(cod){
					loadInfo();
				}).always(function() { l.stop(); });
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