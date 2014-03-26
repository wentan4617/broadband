<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<jsp:include page="../header.jsp" />
<jsp:include page="../alert.jsp" />

<div class="container">
	<div class="row">
		<div class="col-md-12">
				<!-- Customer Basic Info Module -->
				<jsp:include page="customer-info.jsp" />
				
				<!-- Customer Order Info Module -->
				<jsp:include page="customer-order.jsp" />
				
				<!-- Customer Invoice Info Module -->
				<jsp:include page="customer-invoice.jsp" />
				
				<!-- Customer Transaction Info Module -->
				<jsp:include page="customer-transaction.jsp" />
		</div>
	</div>
</div>
<jsp:include page="../footer.jsp" />
<jsp:include page="../script.jsp" />
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/bootstrap-datepicker.js"></script>
<script type="text/javascript">
(function($){
	
	$('a[data-name="add_discount"]').click(function(){
		$('input[name="order_id"]').val($(this).attr('data-val'));
	});
	
	$('a[data-name="remove_discount"]').click(function(){
		$('input[name="order_detail_id"]').val($(this).attr('data-val'));
	});
	
	$('.input-group.date').datepicker({
	    format: "yyyy-mm-dd",
	    autoclose: true,
	    todayHighlight: true
	});
	
	var orderIds = new Array();
	
	<c:forEach var="co" items="${customer.customerOrders }">
		orderIds.push('${co.id}');
	</c:forEach>
	
	
	$.getTxPage = function(pageNo) {
		
		$.get('${ctx}/broadband-user/crm/transaction/view/' + pageNo +'/'+ ${customer.id}, callbackPage, "json");
		
		function callbackPage(page){
			var html = "";
			if (page.results != null && page.results.length > 0) {
				html += '<table class="table">';
				html += '<thead>';
				html += '<tr>';
				html += '<th>Reference</th>';
				html += '<th>Transaction Type</th>';
				html += '<th>Transaction Date</th>';
				html += '<th>Card Name</th>';
				html += '<th>Transaction Sort</th>';
				html += '<th>Amount</th>';
				html += '<th>&nbsp;</th>';
				html += '</tr>';
				html += '</thead>';
				html += '<tbody>';
				for (var i = 0, len = page.results.length; i < len; i++) {
					var tx = page.results[i];
					html += '<tr>';
					html += '<td>Transaction# - ' + tx.id + '</td>';
					html += '<td>' + tx.transaction_type + '</td>';
					html += '<td>' + tx.transaction_date_str + '</td>';
					html += '<td>' + tx.card_name + '</td>';
					html += '<td>' + tx.transaction_sort + '</td>';
					html += '<td>' + tx.amount + '</td>';
					html += '<td>&nbsp;</td>';
					html += '</tr>';
				}
				html += '</tbody>';
				html += '<tfoot>';
				html += '<tr>';
				html += '<td colspan="11">';
				html += '<ul class="pagination">';
				for (var i = 1, len = page.totalPage; i <= len; i++) {
					html += '<li class="' + (page.pageNo == i ? 'active' : '') + '">';
					html += '<a href="javascript:void(0);" onclick="$.getTxPage(' + i + ')">' + i + '</a>';
					html += '</li>';
				}
				html += '</ul>';
				html += '</td>';
				html += '</tr>';
				html += '</tfoot>';
				html += '</table>';
			} else {
				html += '<div class="panel-body">';
				html += '<div class="alert alert-warning">No records have been found.</div>';
				html += '</div>';
			}
			
			$('#txContainer').html(html);
		}
	}
	
	$.getInvoicePage = function(pageNo) {
		
		$.get('${ctx}/broadband-user/crm/invoice/view/' + pageNo+'/'+ ${customer.id}, callbackPage, "json");
		
		function callbackPage(map){
			var html = "";
			if (map.invoicePage.results != null && map.invoicePage.results.length > 0) {
				html += '<table class="table">';
				
				// ieterating order's related invoices according to each and every order id
				for (var o = 0, len = orderIds.length; o < len; o++){
					html += '<thead>';
					html += '<tr>';
					html += '<th colspan="8"><h1>Order Serial: ' + orderIds[o] + '</h1></th>';
					html += '</tr>';
					html += '<tr>';
					html += '<th>Reference</th>';
					html += '<th>Create Date</th>';
					html += '<th>Due Date</th>';
					html += '<th>Amount Payable</th>';
					html += '<th>Amount Paid</th>';
					html += '<th>Balance</th>';
					html += '<th>Status</th>';
					html += '<th>&nbsp;</th>';
					html += '<th>&nbsp;</th>';
					html += '</tr>';
					html += '</thead>';
					html += '<tbody>';
					for (var i = 0, invoiceLen = map.invoicePage.results.length; i < invoiceLen; i++) {
						var invoice = map.invoicePage.results[i];
						if(orderIds[o]==invoice.order_id){
							for (var t = 0, txLen = map.transactionsList.length; t < txLen; t++) {
								var tx = map.transactionsList[t];
								if(tx.invoice_id==invoice.id){
									html += '<tr class="success">';
									html += '<td>Transaction# - ' + tx.id + '</td>';
									html += '<td>' + tx.transaction_date_str + '</td>';
									html += '<td>&nbsp;</td>';
									html += '<td>&nbsp;</td>';
									var txAmount_paid = new Number(invoice.amount_paid);
									html += '<td><strong>' + txAmount_paid.toFixed(2) + '</strong></td>';
									html += '<td>&nbsp;</td>';
									html += '<td>' + invoice.status + '</td>';
									html += '<td>&nbsp;</td>';
									html += '<td>&nbsp;</td>';
									html += '</tr>';
								}
							}
							html += '<tr ';
							
							// if unpaid or not pay off then class=danger else class=active
							invoice.status=='unpaid' || invoice.status=='not_pay_off' ? html+='class="danger"' : html+='';
							html += '>';
							html += '<td>Invoice# - ' + invoice.id + '</td>';
							html += '<td>' + invoice.create_date_str + '</td>';
							invoice.status!='unpaid' && invoice.status!='not_pay_off' ? html+='<td>&nbsp;</td>' : html+='<td><strong style="color:red;">'+invoice.due_date_str+'</strong></td>';
							var invoiceAmount_payable = new Number(invoice.amount_payable);
							var invoiceAmount_paid = new Number(invoice.amount_paid);
							var invoiceBalance = new Number(invoice.balance);
							html += '<td><strong>' + invoiceAmount_payable.toFixed(2) + '</strong></td>';
							html += '<td><strong>' + invoiceAmount_paid.toFixed(2) + '</strong></td>';
							html += '<td><strong>' + invoiceBalance.toFixed(2) + '</strong></td>';
							html += '<td><strong>' + invoice.status + '</strong></td>';
							if(invoice.status=='unpaid' || invoice.status=='not_pay_off'){
								html += '<td>';
								html += '	<div class="btn-group dropup">';
								html += '		<button type="button" class="btn btn-primary btn-xs">Make Payment</button>';
								html += '		<button type="button" class="btn btn-primary btn-xs dropdown-toggle" data-toggle="dropdown">';
								html += '			<span class="caret"></span>';
								html += '			<span class="sr-only">Toggle Dropdown</span>';
								html += '		</button>';
								html += '		<ul class="dropdown-menu" role="menu">';
								html += '			<li><a href="${ctx}/broadband-user/crm/customer/invoice/payment/credit-card/'+invoice.id+'">Credit Card</a></li>';
								html += '			<li><a href="#">Paypal</a></li>';
								html += '			<li><a href="#">DD</a></li>';
								html += '			<li><a href="#">Voucher</a></li>';
								html += '			<li><a href="#">Cash</a></li>';
								html += '		</ul>';
								html += '	</div>';
								html += '</td>';
							} else {
								html += '<td>&nbsp;</td>';
							}
							var downloadUrl = '${ctx}/broadband-user/crm/customer/invoice/pdf/download/' + invoice.id;
							var sendUrl = '${ctx}/broadband-user/crm/customer/invoice/pdf/send/' + invoice.id + '/' + ${customer.id};
							var generateUrl = '${ctx}/broadband-user/crm/customer/invoice/pdf/generate/' + invoice.id;
							
							if(invoice.status=='unpaid' || invoice.status=='not_pay_off'){
								if(invoice.invoice_pdf_path != null){
									html += '<td>';
									// regenerate icon
									html += '<a target="_blank" href="javascript:void(0);" onclick="var xmlHttp=new XMLHttpRequest; xmlHttp.open(\'GET\',\'' + generateUrl + '\',\'true\'); xmlHttp.send();" data-toggle="tooltip" data-placement="bottom" data-original-title="regenerate invoice PDF"><span class="glyphicon glyphicon-refresh"></span></a>&nbsp;&nbsp;&nbsp;';
									// download icon
									html += '<a target="_blank" href="' + downloadUrl + '" data-toggle="tooltip" data-placement="bottom" data-original-title="download invoice PDF"><span class="glyphicon glyphicon-floppy-disk"></span></a>&nbsp;&nbsp;&nbsp;';
									// send icon
									html += '<a target="_blank" href="' + sendUrl + '" id="' + invoice.id + '" data-toggle="tooltip" data-placement="bottom" data-original-title="send invoice details to customer\'s email"><span class="glyphicon glyphicon-send"></span></a>';
									html += '</td>';
								}else{
									// generate first invoice PDF
									html += '<td>';
									// regenerate icon
									html += '<a target="_blank" href="javascript:void(0);" onclick="var xmlHttp=new XMLHttpRequest; xmlHttp.open(\'GET\',\'' + generateUrl + '\',\'true\'); xmlHttp.send();" id="' + invoice.id + 'regenerate" data-toggle="tooltip" data-placement="bottom" data-original-title="regenerate invoice PDF" style="display:none;"><span class="glyphicon glyphicon-refresh"></span></a>&nbsp;&nbsp;&nbsp;';
									// download icon
									html += '<a target="_blank" href="' + downloadUrl + '" id="' + invoice.id + 'download" data-toggle="tooltip" data-placement="bottom" data-original-title="download invoice PDF" style="display:none;"><span class="glyphicon glyphicon-floppy-disk"></span></a>&nbsp;&nbsp;&nbsp;';
									// send icon
									html += '<a target="_blank" href="' + sendUrl + '" id="' + invoice.id + 'send" data-toggle="tooltip" data-placement="bottom" data-original-title="send invoice details to customer" style="display:none;"><span class="glyphicon glyphicon-send"></span></a>';
									
									// showed before first time click generate invoice PDF icon
									html += '<script type="text\/javascript">														';
									html += '	function showOthers(obj){															';
									html += '		obj.style.display = "none";														';
									html += '		document.getElementById(\'' + invoice.id + 'regenerate\').style.display=\'\';	';
									html += '		document.getElementById(\'' + invoice.id + 'download\').style.display=\'\';		';
									html += '		document.getElementById(\'' + invoice.id + 'send\').style.display=\'\';			';
									html += '		var xmlHttp = new XMLHttpRequest;												';
									html += '		xmlHttp.open(\'GET\',\'' + generateUrl + '\',\'true\');							';
									html += '		xmlHttp.send();																	';
									html += '	}																					';
									html += '<\/script>																				';
									html += '<a target="_blank" href="javascript:void(0);" onclick="showOthers(this);" data-toggle="tooltip" data-placement="bottom" data-original-title="generate invoice PDF"><span class="glyphicon glyphicon-hdd"></span></a>';
									html += '</td>';
								}
							} else {
								html += '<td>';
								// download icon
								html += '<a target="_blank" href="' + downloadUrl + '" data-toggle="tooltip" data-placement="bottom" data-original-title="download invoice PDF"><span class="glyphicon glyphicon-floppy-disk"></span></a>&nbsp;&nbsp;&nbsp;';
								html += '</td>';
							}
							html += '</tr>';
						}
					}
				}
				
				
				
				html += '</tbody>';
				html += '<tfoot>';
				html += '<tr>';
				html += '<td colspan="11">';
				html += '<ul class="pagination">';
				for (var i = 1, len = map.invoicePage.totalPage; i <= len; i++) {
					html += '<li class="' + (map.invoicePage.pageNo == i ? 'active' : '') + '">';
					html += '<a href="javascript:void(0);" onclick="$.getInvoicePage(' + i + ')">' + i + '</a>';
					html += '</li>';
				}
				html += '</ul>';
				html += '</td>';
				html += '</tr>';
				html += '</tfoot>';
				html += '</table>';
			} else {
				html += '<div class="panel-body">';
				html += '<div class="alert alert-warning">No records have been found.</div>';
				html += '</div>';
			}

			$('#invoiceContainer').html(html);
			$('a[data-toggle="tooltip"]').tooltip();
			
			
			
		}
	}
	
	$('a[data-name="save"]').click(function(){
		var cvlan_input = $('#'+this.id+'_cvlan_input').val();
		var svlan_input = $('#'+this.id+'_svlan_input').val();
		var order_using_start_input = $('#'+this.id+'_order_using_start_input').val();
		var order_total_price = $('#'+this.id+'_order_total_price').attr('data-val');
		
		var cvlan = $('#'+this.id+'_cvlan');
		var svlan = $('#'+this.id+'_svlan');
		var order_using_start = $('#'+this.id+'_order_using_start');
		var order_status = $('#'+this.id+'_order_status');
		var order_next_invoice_create_date = $('#'+this.id+'_next_invoice_create_date');
		var order_detail_unit_attr = $('#'+this.id+'_order_detail_unit').attr('data-val');
		
		// if status not ordering then into statement
		if(order_status.attr('data-val')=='ordering-paid' || order_status.attr('data-val')=='ordering-pending'){
			// if yes then performing Ajax action
			if(confirm('Confirm to save these changes')){
				var data = {
						'customer_id':'${customer.id}'
						,'order_id':this.id
						,'cvlan_input':cvlan_input
						,'svlan_input':svlan_input
						,'order_using_start_input':order_using_start_input
						,'order_total_price':order_total_price
						,'order_detail_unit':(order_detail_unit_attr==null?1:order_detail_unit_attr)
						,'order_status':order_status.attr('data-val')
					};
				
				var url = "${ctx}/broadband-user/crm/customer/order/save";
				$.get(url, data, function(order){
					var oBtnSave = $('a[data-name="save"]');
					// hide Save Btn
					oBtnSave.css('display', 'none');
					var oBtnEdit = $('a[data-name="edit"]');
					// show Edit Btn
					oBtnEdit.css('display', '');
					
					// rewrite innerHTML
					cvlan.html(cvlan_input);
					svlan.html(svlan_input);
					order_using_start.html(order_using_start_input);
					order_status.html(order.order_status);
					order_next_invoice_create_date.html(order.next_invoice_create_date_str);
					
					// reload invoice page one
					$.getInvoicePage(1);
				}, "json");
			}
		} else {
			alert('couldn\'t save these changes');
		}
		
	});
	
	$('a[data-name="edit"]').click(function(){
		var cvlan_input = $('#'+this.id+'_cvlan_input').val();
		var svlan_input = $('#'+this.id+'_svlan_input').val();
		var order_using_start_input = $('#'+this.id+'_order_using_start_input').val();
		
		var cvlan = $('#'+this.id+'_cvlan');
		var svlan = $('#'+this.id+'_svlan');
		var order_using_start = $('#'+this.id+'_order_using_start');
		var order_next_invoice_create_date = $('#'+this.id+'_next_invoice_create_date');
		var order_detail_unit_attr = $('#'+this.id+'_order_detail_unit').attr('data-val');
		if(confirm('Confirm to edit these changes')){
			var data = {
					'customer_id':'${customer.id}'
					,'order_id':this.id
					,'cvlan_input':cvlan_input
					,'svlan_input':svlan_input
					,'order_using_start_input':order_using_start_input
					,'order_detail_unit':order_detail_unit_attr
				};
			
			var url = "${ctx}/broadband-user/crm/customer/order/edit";
			$.get(url, data, function(order){
				cvlan.html(cvlan_input);
				svlan.html(svlan_input);
				order_using_start.html('<strong>' + order_using_start_input + '<\/strong>');
				order_next_invoice_create_date.html(order.next_invoice_create_date_str);
			}, "json");
		}
	});
	
	$('a[data-name="pppoe_save"]').click(function(){
		var order_pppoe_loginname_input = $('#'+this.id+'_pppoe_loginname_input').val();
		var order_pppoe_password_input = $('#'+this.id+'_pppoe_password_input').val();
		
		var order_pppoe_loginname = $('#'+this.id+'_pppoe_loginname');
		var order_pppoe_password = $('#'+this.id+'_pppoe_password');
		
		// if yes then performing Ajax action
		if(confirm('Confirm to save PPPoE Login Name & Password')){
			var data = {
					 'order_id':this.id
					,'order_pppoe_loginname_input':order_pppoe_loginname_input
					,'order_pppoe_password_input':order_pppoe_password_input
				};
			
			var url = "${ctx}/broadband-user/crm/customer/order/ppppoe/save";
			$.get(url, data, function(order){
				var oBtnSave = $('a[data-name="pppoe_save"]');
				// hide Save Btn
				oBtnSave.css('display', 'none');
				var oBtnEdit = $('a[data-name="pppoe_edit"]');
				// show Edit Btn
				oBtnEdit.css('display', '');
				
				// rewrite innerHTML
				order_pppoe_loginname.html(order_pppoe_loginname_input);
				order_pppoe_password.html(order_pppoe_password_input);
				
			}, "json");
		}
	});
	
	$('a[data-name="pppoe_edit"]').click(function(){
		var order_pppoe_loginname_input = $('#'+this.id+'_pppoe_loginname_input').val();
		var order_pppoe_password_input = $('#'+this.id+'_pppoe_password_input').val();
		
		var order_pppoe_loginname = $('#'+this.id+'_pppoe_loginname');
		var order_pppoe_password = $('#'+this.id+'_pppoe_password');
		
		var data = {
				 'order_id':this.id
				,'order_pppoe_loginname_input':order_pppoe_loginname_input
				,'order_pppoe_password_input':order_pppoe_password_input
			};
		
		var url = "${ctx}/broadband-user/crm/customer/order/ppppoe/edit";
		$.get(url, data, function(order){
			
			// rewrite innerHTML
			order_pppoe_loginname.html(order_pppoe_loginname_input);
			order_pppoe_password.html(order_pppoe_password_input);
			
		}, "json");
	});

	$.getTxPage(1);
	$.getInvoicePage(1);
	
	
	
})(jQuery);
</script>
<jsp:include page="../footer-end.jsp" />