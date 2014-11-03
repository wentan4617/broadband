(function($){
	
	var _tmpl = $('#data_query_tmpl')
		, ctx = _tmpl.attr('data-ctx')
		, g_status = 'using'
		
	function dataQueryLoading() {
		var o = {};
		$('#data-query-tmpl').html(tmpl('data_query_tmpl', o));
		
		$('.input-group.date').datepicker({
		    format: "yyyy-mm", 
		    minViewMode: 1,
		    autoclose: true
		}).datepicker('setDate', new Date()).on('changeDate', function(e){ //console.log(e.format());
			//doPage(1, e.format(), g_status);
			dataOrdersLoading(1, e.format(), g_status, 0);
		});
		
		$('#query').click(function(){
			dataOrdersLoading(1, $('#calculator_date').val(), g_status, $('#orderid').val());
		});
		
		$('a[data-name="queryBtn"]').click(function(){
			g_status = $(this).attr('data-status');
			$('a[data-group]').removeClass('active');
			var sub = $(this).attr('data-sub');
			$('a[data-group="' + sub + '"]').addClass('active');
			
			dataOrdersLoading(1, $('#calculator_date').val(), g_status, 0);
		});
		
		$('a[data-sub="in-service"]').trigger('click');
	}
	
	dataQueryLoading();
	
	function dataOrdersLoading(pageNo, date, status, orderid) {
		$.get(ctx + '/broadband-user/data/orders/view/' + pageNo + "/" + date + "/" + status + '/' + orderid, function(page){ //console.log(page);
			page.ctx = ctx;
	   		$('#data-orders-tmpl').html(tmpl('data_orders_tmpl', page));
	   		$('#data-orders-tmpl').find('tfoot a').click(function(){
	   			dataOrdersLoading($(this).attr('data-pageNo'), $('#calculator_date').val(), status, orderid);
			});
	   	});
	}
	
})(jQuery);