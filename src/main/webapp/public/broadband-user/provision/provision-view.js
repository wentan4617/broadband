(function($){
	
	var _tmpl = $('#provision_query_tmpl')
		, ctx = _tmpl.attr('data-ctx')
		
	function provisionQueryLoading() {
		$.get(ctx + '/broadband-user/provision/query/loading', function(provision) {
			$('#provision-query-tmpl').html(tmpl('provision_query_tmpl', provision));
			
			$('a[data-for-parent]').click(function(){
				var parent = $(this).attr('data-for-parent');
				var status = $(this).attr('data-status');
				$('a[data-parent]').removeClass('active');
				$('a[data-parent="' + parent + '"]').addClass('active');
				
				provisionOrdersLoading(1, status);
				
				if (parent == 'new-order') {
					if (status == 'pending') {
						$('#provision-view-title').html('New Order &gt; Pending');
					} else if (status == 'paid') {
						$('#provision-view-title').html('New Order &gt; Paid');
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
			});
			
			$('a[data-status="paid"]').trigger('click');
		});
	}
	
	provisionQueryLoading();
	
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
		});
	}
	
	
	
	$('#pending_to_ordering').click(function(){
		$('#process_way').val('pending to ordering-pending');
		$('#change_order_status').val($(this).attr('data-val'));
		$('#provisionForm').submit();
	});

	$('#paid_to_ordering').click(function() {
		$('#process_way').val('paid to ordering-paid');
		$('#change_order_status').val($(this).attr('data-val'));
		$('#provisionForm').submit();
	});
	
})(jQuery);