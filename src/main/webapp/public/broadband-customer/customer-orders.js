(function($){
	
	var _tmpl = $('#customer_orders_tmpl')
		, ctx = _tmpl.attr('data-ctx')
		
	function loadingCustomerOrders() {
		$.get(ctx + '/customer/orders/loading', function(orders) { //console.log(orders);
			var o = {
				ctx: ctx
				, orders: orders
			};
			$('#customer-orders').html(tmpl('customer_orders_tmpl', o));
			$('a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
				var tab = $(e.target).attr('data-tab');
				var orderid = $(e.target).attr('data-order-id');
				if (tab == 'invoices') {
					doInvoicePage(orderid, 1);
				} else if (tab == 'datausage') {
					tabCustomerOrderData(orderid);
				}
			});
		});
	}
		
	loadingCustomerOrders();
	
	function doInvoicePage(orderid, pageNo) {
		$.get(ctx + '/customer/orders/invoices/loading/' + orderid + '/' + pageNo, function(page) {
			page.ctx = ctx;
			$('#invoices' + orderid).html(tmpl('customer_orders_invoices_tmpl', page));
			$('#invoices' + orderid).find('tfoot a').click(function(){
				doInvoicePage(orderid, $(this).attr('data-pageNo'));
			});
			$('a[data-invoice-credit]').click(function(){
				$('#invoiceid').val($(this).attr('data-invoiceid'));
				$('#invoice-credit').submit();
			});
		});
	}
	
	function tabCustomerOrderData(orderid) {
		var o = {
			ctx: ctx
			, orderid: orderid
			, plan_name: $('#planname' + orderid).val()
			, plan_type: $('#plantype' + orderid).val()
			, plan_data_flow: $('#plandataflow' + orderid).val()
			, svlan: $('#plansvlan' + orderid).val()
			, cvlan: $('#plancvlan' + orderid).val()
		}; //console.log(o);
		$('#datausage' + orderid).html(tmpl('customer_orders_data_tmpl', o));
		
		$('#calculator_date' + orderid).datepicker({
		    format: "yyyy-mm", 
		    minViewMode: 1,
		    autoclose: true
		}).datepicker('setDate', new Date()).on('changeDate', function(e){
			loadingCustomerOrderData(orderid, o.plan_type, o.svlan, o.cvlan, e.format(), o.plan_data_flow);
		});
		$('button[data-btn="view-btn' + orderid + '"]').click(function(){
			$('button[data-btn="view-btn' + orderid + '"]').removeClass('active');
			$(this).addClass('active');
			
			var type = $(this).attr('data-type');
			if (type == 'table') {
				$('#usage_line' + orderid).hide();
				$('#usage_table' + orderid).show();
			} else if (type == 'chart') {
				$('#usage_table' + orderid).hide();
				$('#usage_line' + orderid).show();
			}
		});
		
		var current_date = new Date();
		var year = current_date.getFullYear(); //console.log(year);
		var month = current_date.getMonth() + 1; //console.log(month);
		loadingCustomerOrderData(orderid, o.plan_type, o.svlan, o.cvlan, year + '-' + month, o.plan_data_flow);
	}
	
	function loadingCustomerOrderData(orderid, plan_type, svlan, cvlan, calculator_date, plan_data_flow) {
		
		var url = ctx + '/customer/orders/data/loading/' + plan_type + '/' + svlan + '/' + cvlan + '/' + calculator_date; //console.log(url);
		$.get(url, function(list){ //console.log(list);
			
			if (list == null || list.length == 0) return false;
			
			//dateUsages = list;
			var o = {
				orderid: orderid
				, list: list
			};
			$('#usage_table' + orderid).html(tmpl('customer_usage_view_tmpl', o));
			
			var curMonthTotal = Number($('#curMonthTotal' + orderid).val()); //console.log('curMonthTotal: ' + curMonthTotal); 
			var planUsage = Number(plan_data_flow) > 0 ? Number(plan_data_flow) : 999; //console.log('planUsage: ' + planUsage)
			var usageWidth = Number(curMonthTotal/planUsage); //console.log('usageWidth: ' + usageWidth);
			
			var widthVal = 0;
			if (usageWidth * 100 > 100) {
				widthVal = 100;
			} else {
				widthVal = usageWidth * 100;
			} //console.log('widthVal: ' + widthVal);
			
			$('#usageProgress' + orderid).attr('style', 'width:' + widthVal + '%; padding: 10px 0');
			
			if (widthVal < 50) { //console.log('progress-bar-success');
				$('#usageProgress' + orderid).addClass('progress-bar-success');
			} else if (widthVal < 80) { //console.log('progress-bar-warning');
				$('#usageProgress' + orderid).addClass('progress-bar-warning');
			} else { //console.log('progress-bar-danger');
				$('#usageProgress' + orderid).addClass('progress-bar-danger');
			}
			$('#usedPart' + orderid).text((usageWidth * 100).toFixed(3) + '%');
			
			var labelArray = [];
			var dataArray = [];
			var maxData = 0;
			var tempData;
			
			for (var i = 0; i < list.length; i++) {
				var dateUsage = list[i];
				labelArray.push(dateUsage.date.substring(8));
				if (dateUsage.usage != null) {
					var upload = dateUsage.usage.upload/1024/1024/1024;
					var download = dateUsage.usage.download/1024/1024/1024;
					var curDayTotal = upload + download;
					tempData = Number(curDayTotal);
					maxData = tempData > maxData ? tempData : maxData;
					dataArray.push(Number(curDayTotal).toFixed(3));
				} else {
					dataArray.push(0);
				}
			}
			
			var lineChartData = {
				labels: labelArray
				, datasets: [{
					fillColor: "rgba(151,187,205,0.2)",
					strokeColor: "rgba(151,187,205,1)",
					pointColor: "rgba(151,187,205,1)",
					pointStrokeColor: "#fff",
					data: dataArray
				}]
			};
				
			var lineChartOptions = {
				scaleOverride: true 
				, scaleSteps: 10
				, scaleStepWidth: (maxData/10).toFixed(3) 
				, scaleStartValue: 0 
			};
			
			$('#usage_line' + orderid).empty();
			var $canvas = $('<canvas height="300" width="810"></canvas>');
			$canvas.appendTo('#usage_line' + orderid);
			new Chart($canvas.get(0).getContext("2d")).Line(lineChartData, lineChartOptions);
	   	});
	}
	
})(jQuery);