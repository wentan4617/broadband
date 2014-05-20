<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<jsp:include page="../header.jsp" />
<jsp:include page="../alert.jsp" />

<style>
.panel-default {
	border-top-color:transparent;
}
</style>


<div class="container">
	<div class="row">
		<div class="col-md-12">
		
			<!-- Nav tabs -->
			<ul class="nav nav-tabs">
				<li class="active"><a href="#customer_edit" data-toggle="tab"><strong>Customer Edit</strong></a></li>
				<li><a href="#order_detail" data-toggle="tab"><strong>Order Detail</strong></a></li>
				<li><a href="#invoice_detail" data-toggle="tab"><strong>Invoice Detail</strong></a></li>
				<li><a href="#transaction_detail" data-toggle="tab"><strong>Transaction Detail</strong></a></li>
			</ul>

			<!-- Tab panes -->
			<div class="tab-content panel panel-default">
				<div class="panel-body tab-pane fade in active" id="customer_edit" >				
					<!-- Customer Basic Info Module -->
					<jsp:include page="customer-info.jsp" />
				</div>
				<div class="panel-body tab-pane fade" id="order_detail" >
					<!-- Customer Order Info Module -->
					<jsp:include page="customer-order.jsp" />
				</div>
				<div class="panel-body tab-pane fade" id="invoice_detail">
				</div>
				<div class="panel-body tab-pane fade" id="transaction_detail">
					<!-- Customer Transaction Info Module -->
					<jsp:include page="customer-transaction.jsp" />
				</div>
			</div>
		</div>
	</div>
</div>

<!-- Customer Invoice Detail Template -->
<script type="text/html" id="invoice_table_tmpl">
<jsp:include page="customer-invoice-view-page.html" />
</script>
<jsp:include page="../footer.jsp" />
<jsp:include page="../script.jsp" />
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/bootstrap-datepicker.js"></script>
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/bootstrap-select.min.js"></script>
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/jTmpl.js"></script>
<script type="text/javascript">
(function($){
	
	$('.input-group.date').datepicker({
	    format: "yyyy-mm-dd",
	    autoclose: true,
	    todayHighlight: true
	});
	
	$('.selectpicker').selectpicker(); 
	
	// BEGIN CUSTOMER BIRTH DATEPICKER
	var birth_input = $('input[data-name="customer_birth_input"]').attr('data-val');
	$('#customer_birth_datepicker').datepicker({
	    format: "yyyy-mm-dd",
	    autoclose: true,
	    todayHighlight: true
	    // if customer birth is null then assign new Date(), else assign customer birth
	}).datepicker('setDate', birth_input || new Date());
	// END CUSTOMER BIRTH DATEPICKER
	
	// BEGIN ORG INCOPORATE DATE DATEPICKER
	var birth_input = $('input[data-name="incoporate_date_input"]').attr('data-val');
	$('#incoporate_date_datepicker').datepicker({
	    format: "yyyy-mm-dd",
	    autoclose: true,
	    todayHighlight: true
	    // if customer birth is null then assign new Date(), else assign customer birth
	}).datepicker('setDate', birth_input || new Date());
	// END ORG INCOPORATE DATE DATEPICKER

	$('span[data-toggle="tooltip"]').tooltip();
	
	// BEGIN customer info modal area
	$('#updateCustomer').click(function(){
		var customer_id = '${customer.id}';
		var customer_type = '${customer.customer_type}';
		var $btn = $(this);
		var url = '${ctx}/broadband-user/crm/customer/' + customer_type + '/edit';
		var customer = {
			address: $('#address').val()
			, cellphone: $('#cellphone').val()
			, email: $('#email').val()
			, password: $('#password').val()
			, title: $('#title').val()
			, first_name: $('#first_name').val()
			, last_name: $('#last_name').val()
			, organization: {
				org_name: $('#organization\\.org_name').val()
				, org_type: $('#organization\\.org_type').val()
				, org_trading_name: $('#organization\\.org_trading_name').val()
				, org_register_no: $('#organization\\.org_register_no').val()
				, org_incoporate_date: $('#organization\\.org_incoporate_date').val()
				, org_trading_months: $('#organization\\.org_trading_months').val()
				, holder_name: $('#organization\\.holder_name').val()
				, holder_job_title: $('#organization\\.holder_job_title').val()
				, holder_phone: $('#organization\\.holder_phone').val()
				, holder_email: $('#organization\\.holder_email').val()
			}
			, id: customer_id
			, customer_type: customer_type
			, balance: $('#balance').val()
			, status: $('#status').val()
		};
		//console.log("customer request:");
		//console.log(customer);
		$btn.button('loading');
		$.ajax({
			type: 'post'
			, contentType:'application/json;charset=UTF-8'         
	   		, url: url
		   	, data: JSON.stringify(customer)
		   	, dataType: 'json'
		   	, success: function(json){
		   		$.jsonValidation(json, 'right');
		   	}
		}).always(function () {
			$btn.button('reset');
	    });
	});
	// END customer info modal area	
	
	var orderIds = new Array();
	
	<c:forEach var="co" items="${customer.customerOrders }">
		orderIds.push('${co.id}');
	
		/*
		 *	BEGIN order modal area
		 */
		// BEGIN Order Info Area Modal
		$('a[data-name="${co.id}_order_info_edit"]').click(function(){
			$('a[data-name="editOrderInfoModalBtn"]').attr('id',this.id);
			$('#editOrderInfoModal').modal('show');
		});
		// END Order Info Area Modal
		// BEGIN PPPoE Area Modal
		$('a[data-name="${co.id}_pppoe_save"]').click(function(){
			$('a[data-name="pppoe_save_modal_btn"]').attr('id',$(this).attr('data-val'));
			$('#savePPPoEModal').modal('show');	// click Save PPPoE then performing Ajax action
		});
		$('a[data-name="${co.id}_pppoe_edit"]').click(function(){
			$('a[data-name="pppoe_edit_modal_btn"]').attr('id',$(this).attr('data-val'));
			$('#editPPPoEModal').modal('show');	// click Edit PPPoE then performing Ajax action
		});
		// END PPPoE Area Modal
		// BEGIN SV/CVLan Area Modal
		$('a[data-name="${co.id}_svcvlan_save"]').click(function(){
			var order_status = $('#'+$(this).attr('data-val')+'_order_status');
			// if status is ordering then show save modal
			if(order_status.attr('data-val')=='ordering-paid' || order_status.attr('data-val')=='ordering-pending'){
				$('a[data-name="svcvlan_save"]').attr('id',$(this).attr('data-val'));
				$('#saveOrderModal').modal('show');	// click Save Order then performing Ajax action
			// else show denied modal
			} else {
				$('#saveOrderDeniedModal').modal('show');
			}
		});
		$('a[data-name="${co.id}_svcvlan_edit"]').click(function(){
			$('a[data-name="svcvlan_edit"]').attr('id',$(this).attr('data-val'));
			$('#editOrderModal').modal('show');	// click Edit Order then performing Ajax action
		});
		// END SV/CVLan Area Modal
		// BEGIN Order Detail Area Modal
		$('a[data-name="${co.id}_update_pstn"]').click(function(){
			$('input[name="order_detail_id"]').val($(this).attr('data-val'));
		});
		$('a[data-name="${co.id}_add_discount"]').click(function(){
			$('input[name="order_id"]').val($(this).attr('data-val'));
		});
		$('a[data-name="${co.id}_remove_discount"]').click(function(){
			$('input[name="order_detail_id"]').val($(this).attr('data-val'));
		});
		// END Order Detail Area Modal
		/*
		 *	END order modal area
		 */
		 

		/*
		 *	BEGIN order datepicker area
		 */
		// BEGIN order due Datapicker
		var order_due_input = $('input[data-name="${co.id}_order_due_input"]').attr('data-val');
		$('#${co.id}_order_due_datepicker').datepicker({
		    format: "yyyy-mm-dd",
		    autoclose: true,
		    todayHighlight: true
		    // if order due date is null then assign new Date(), else assign order due date
		}).datepicker('setDate', order_due_input || new Date());
		// END order due Datapicker
		// BEGIN order using start Datapicker
		var order_using_start_input = $('input[data-name="${co.id}_order_using_start_input"]').attr('data-val');
		$('#${co.id}_order_using_start_datepicker').datepicker({
		    format: "yyyy-mm-dd",
		    autoclose: true,
		    todayHighlight: true
		    
		    // if service giving date is null then assign new Date(), else assign service giving date 
		}).datepicker('setDate', order_using_start_input || new Date());
		// END order using start Datapicker
		/*
		 *	END order datepicker area
		 */
		 
		
		/*
		 *	BEGIN order info area
		 */
		$('a[data-name="editOrderInfoModalBtn"]').click(function(){
			var orderStatus = $('select[data-name="'+this.id+'_order_status_selector"]');
			var orderDueDate = $('input[data-name="'+this.id+'_order_due_input"]');
			var data = {
					'order_id':this.id
					,'order_status':orderStatus.val()
					,'due_date':orderDueDate.val()
			};
			
			var order_status = $('#'+this.id+'_order_status');
			var order_due = $('#'+this.id+'_order_due');
			
			var url = "${ctx}/broadband-user/crm/customer/order/info/edit";
			$.get(url, data, function(order){
				
				// rewrite innerHTML
				order_status.html('<strong>'+order.order_status+'</strong>');
				order_status.attr('data-val',order.order_status);
				order_due.html('<strong>'+order.order_due_str+'</strong>');
				
			}, "json");
		});
		/*
		 *	END order info area
		 */


		/*
		 *	BEGIN PPPoE area
		 */		
		$('a[data-name="pppoe_save_modal_btn"]').click(function(){
			// for data
			var order_pppoe_loginname_input = $('#'+this.id+'_pppoe_loginname_input').val();
			var order_pppoe_password_input = $('#'+this.id+'_pppoe_password_input').val();

			// for callback
			var order_pppoe_loginname = $('#'+this.id+'_pppoe_loginname');
			var order_pppoe_password = $('#'+this.id+'_pppoe_password');
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
		});
		$('a[data-name="pppoe_edit_modal_btn"]').click(function(){
			// for data
			var order_pppoe_loginname_input = $('#'+this.id+'_pppoe_loginname_input').val();
			var order_pppoe_password_input = $('#'+this.id+'_pppoe_password_input').val();

			// for callback
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
		/*
		 *	END PPPoE area
		 */
			
		 
		/*
		 *	BEGIN service giving area
		 */
		$('a[data-name="svcvlan_save"]').click(function(){
			// for data
			var cvlan_input = $('#'+this.id+'_cvlan_input').val();
			var svlan_input = $('#'+this.id+'_svlan_input').val();
			var order_using_start_input = $('input[data-name="'+this.id+'_order_using_start_input"]').val();
			var order_total_price = $('#'+this.id+'_order_total_price').attr('data-val');
			var order_status = $('#'+this.id+'_order_status');
			var order_type = $('#'+this.id+'_order_type');
			var order_detail_unit_attr = $('#'+this.id+'_order_detail_unit').attr('data-val');

			// for callback
			var cvlan = $('#'+this.id+'_cvlan');
			var svlan = $('#'+this.id+'_svlan');
			var order_using_start = $('#'+this.id+'_order_using_start');
			var order_next_invoice_create_date = $('#'+this.id+'_next_invoice_create_date');
			var data = {
					'customer_id':'${customer.id}'
					,'order_id':this.id
					,'cvlan_input':cvlan_input
					,'svlan_input':svlan_input
					,'order_using_start_input':order_using_start_input
					,'order_total_price':order_total_price
					,'order_detail_unit':(order_detail_unit_attr==null?1:order_detail_unit_attr)
					,'order_status':order_status.attr('data-val')
					,'order_type':order_type.attr('data-val')
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
				order_status.html(order.order_status);
				order_using_start.html('<strong>' + order_using_start_input + '<\/strong>');
				order_next_invoice_create_date.html('<strong>' + order.next_invoice_create_date_str + '<\/strong>');
				
				// reload invoice page one
				$.getInvoicePage(1);
			}, "json");
			
		});
		$('a[data-name="svcvlan_edit"]').click(function(){
			// for data
			var cvlan_input = $('#'+this.id+'_cvlan_input').val();
			var svlan_input = $('#'+this.id+'_svlan_input').val();
			var order_using_start_input = $('input[data-name="'+this.id+'_order_using_start_input"]').val();
			var order_type = $('#'+this.id+'_order_type');
			var order_detail_unit_attr = $('#'+this.id+'_order_detail_unit').attr('data-val');

			// for callback
			var cvlan = $('#'+this.id+'_cvlan');
			var svlan = $('#'+this.id+'_svlan');
			var order_using_start = $('#'+this.id+'_order_using_start');
			var order_next_invoice_create_date = $('#'+this.id+'_next_invoice_create_date');
			var data = {
					'customer_id':'${customer.id}'
					,'order_id':this.id
					,'cvlan_input':cvlan_input
					,'svlan_input':svlan_input
					,'order_using_start_input':order_using_start_input
					,'order_detail_unit':(order_detail_unit_attr==null?1:order_detail_unit_attr)
					,'order_type':order_type.attr('data-val')
				};
			var url = "${ctx}/broadband-user/crm/customer/order/edit";
			$.get(url, data, function(order){
				cvlan.html(cvlan_input);
				svlan.html(svlan_input);
				order_using_start.html('<strong>' + order_using_start_input + '<\/strong>');
				order_next_invoice_create_date.html('<strong>' + order.next_invoice_create_date_str + '<\/strong>');
			}, "json");
		});
		/*
		 *	END service giving area
		 */
		
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
		$.get('${ctx}/broadband-user/crm/invoice/view/' + pageNo +'/'+ ${customer.id}, function(map){
			map.ctx = '${ctx}';
			map.customer_id = ${customer.id};
			map.orderIds = orderIds;
	   		var $table = $('#invoice_detail');
			$table.html(tmpl('invoice_table_tmpl', map));
			$table.find('tfoot a').click(function(){
				$.getInvoicePage($(this).attr('data-pageNo'));
			});
			
			$('a[data-toggle="tooltip"]').tooltip();
			
			// Iterating and binding all invoice's id to specific buttons
			for (var i = 0, invoiceLen = map.invoicePage.results.length; i < invoiceLen; i++) {
				
				// BEGIN generate invoice
				// Binding every generateUrl button's click event by assign them specific invoice's id
				var invoice = map.invoicePage.results[i];
				$('a[data-name="generateUrl_'+invoice.id+'"]').click(function(){
					
					var url = $(this).attr('data-url');
					$.post(url, function(){

						// If successful, then prompt notice
						alert('Generate New Invoice Successfully!');
					})
				});
				// END generate invoice
				
				// BEGIN first generate
				// Binding every firstGenerate button's click event by assign them specific invoice's id
				$('a[data-name="firstGenerate_'+invoice.id+'"]').click(function(){
					
					var url = $(this).attr('data-url');
					$.post(url, function(){
						
						// If successful, then prompt notice
						alert('Generate New Invoice Successfully!');
					})
					
					$(this).css('display', 'none');
					$('regenerate'+this.id).css('display', '');
					$('download'+this.id).css('display', '');
					$('send'+this.id).css('display', '');
				});
				// END first generate
				
				// BEGIN DDPay
				// Confirm DDPay
				$('a[data-name="confirm_ddpay_modal_btn_'+invoice.id+'"]').click(function(){
					$(this).button('loading');
					var data = {
						invoice_id : this.id
					};
					
					$.post('${ctx}/broadband-user/crm/customer/invoice/defray/ddpay', data, function(json){
						if (json.hasErrors) {
							$.jsonValidation(json, 'right');
						} else {
							window.location.href='${ctx}' + json.url;
						}
					}, 'json').always(function () {
						$(this).button('reset');
				    });
					
				});	
				// END DDPay
				
				// BEGIN Cash
				// Confirm Cash
				$('a[data-name="confirm_cash_modal_btn_'+invoice.id+'"]').click(function(){
					$(this).button('loading');
					var data = {
						invoice_id : this.id,
						eliminate_amount : $('input[name="defray_amount_'+this.id+'"]').val()
					};
					
					$.post('${ctx}/broadband-user/crm/customer/invoice/defray/cash', data, function(json){
						if (json.hasErrors) {
							$.jsonValidation(json, 'right');
						} else {
							window.location.href='${ctx}' + json.url;
						}
					}, 'json').always(function () {
						$(this).button('reset');
				    });
					
				});	
				// END Cash
				
			}
			
			
		}, 'json');
	}
	
	$.getInvoicePage(1);
	$.getTxPage(1);
	
	
})(jQuery);
</script>
<jsp:include page="../footer-end.jsp" />