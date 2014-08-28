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
				<li><a href="#customer_service_record_detail" data-toggle="tab"><strong>Customer Service Record</strong></a></li>
			</ul>

			<!-- Tab panes -->
			<div class="tab-content panel panel-default">
				<div class="panel-body tab-pane fade in active" id="customer_edit" ></div>
				<div class="panel-body tab-pane fade" id="order_detail" ></div>
				<div class="panel-body tab-pane fade" id="invoice_detail"></div>
				<div class="panel-body tab-pane fade" id="transaction_detail"></div>
				<div class="panel-body tab-pane fade" id="customer_service_record_detail"></div>
			</div>
		</div>
	</div>
</div>

<!-- Customer Info Template -->
<script type="text/html" id="customer_info_table_tmpl">
<jsp:include page="customer-info.html" />
</script>
<!-- Customer Order Template -->
<script type="text/html" id="customer_order_table_tmpl">
<jsp:include page="customer-order.html" />
</script>
<!-- Customer Invoice Detail Template -->
<script type="text/html" id="invoice_table_tmpl">
<jsp:include page="customer-invoice-view-page.html" />
</script>
<!-- Customer Transaction Detail Template -->
<script type="text/html" id="transaction_table_tmpl">
<jsp:include page="customer-transaction-view-page.html" />
</script>
<!-- Customer Service Record Detail Template -->
<script type="text/html" id="customer_service_record_table_tmpl">
<jsp:include page="customer-service-record-view-page.html" />
</script>

<jsp:include page="../footer.jsp" />
<jsp:include page="../script.jsp" />
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/bootstrap-datepicker.js"></script>
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/bootstrap-select.min.js"></script>
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/jTmpl.js"></script>
<script type="text/javascript">
(function($){
	
	var orderIds = new Array();
	
	<c:forEach var="co" items="${customer.customerOrders }">
		orderIds.push('${co.id}');
	</c:forEach>
	
	$.getCustomerInfo = function() {
		var data = { 'id' : ${customer.id} };
		
		$.get('${ctx}/broadband-user/crm/customer/edit', data, function(map){
			map.customer.ctx = '${ctx}';
			map.customer.user_role = '${userSession.user_role}';
	   		var $table = $('#customer_edit');
			$table.html(tmpl('customer_info_table_tmpl', map.customer));
			
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
					, identity_type: $('#identity_type').val()
					, identity_number: $('#identity_number').val()
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
				}; // console.log("customer request:"); console.log(customer);
				
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
			
			// BEGIN Topup Account Credit
			$('a[data-name="topup_account_credit"]').click(function(){
				var pay_way = $(this).attr('data-way');
				$('a[data-name="confirm_topup_account_credit_modal_btn"]').attr('data-way', pay_way);
				if(pay_way == 'ddpay'){
					$('strong[data-name="confirm_topup_account_credit_modal_title"]').html('Use DDPay to topup account credit?');
					$('p[data-name="confirm_topup_account_credit_modal_content"]').html('This will assign account credit to (account credit + defray amount).<br/><br/>This will record your identity and manipulation time.<br/>');
					$('a[data-name="confirm_topup_account_credit_modal_btn"]').html('Confirm');
					$('div[data-name="defray_amount_input"]').css('display','');
				} else if(pay_way == 'a2a'){
					$('strong[data-name="confirm_topup_account_credit_modal_title"]').html('Use A2A to topup account credit?');
					$('p[data-name="confirm_topup_account_credit_modal_content"]').html('This will assign account credit to (account credit + defray amount).<br/><br/>This will record your identity and manipulation time.<br/>');
					$('a[data-name="confirm_topup_account_credit_modal_btn"]').html('Confirm');
					$('div[data-name="defray_amount_input"]').css('display','');
				} else if(pay_way == 'cash'){
					$('strong[data-name="confirm_topup_account_credit_modal_title"]').html('Use Cash to topup account credit?');
					$('p[data-name="confirm_topup_account_credit_modal_content"]').html('This will assign account credit to (account credit + defray amount).<br/><br/>This will record your identity and manipulation time.<br/>');
					$('a[data-name="confirm_topup_account_credit_modal_btn"]').html('Confirm');
					$('div[data-name="defray_amount_input"]').css('display','');
				}else if(pay_way == 'credit-card'){
					$('strong[data-name="confirm_topup_account_credit_modal_title"]').html('Use Credit Card to topup account credit?');
					$('p[data-name="confirm_topup_account_credit_modal_content"]').html('This will assign account credit to (account credit + defray amount).<br/><br/>This will record your identity and manipulation time.<br/>');
					$('a[data-name="confirm_topup_account_credit_modal_btn"]').html('Confirm');
					$('div[data-name="defray_amount_input"]').css('display','');
				} else if(pay_way == 'cyberpark-credit'){
					$('strong[data-name="confirm_topup_account_credit_modal_title"]').html('Give a CyberPark Credit to topup account credit?');
					$('p[data-name="confirm_topup_account_credit_modal_content"]').html('This will assign account credit to (account credit + defray amount).<br/><br/>This will record your identity and manipulation time.<br/>');
					$('a[data-name="confirm_topup_account_credit_modal_btn"]').html('Confirm');
					$('div[data-name="defray_amount_input"]').css('display','');
				} else if(pay_way == 'voucher'){
					$('strong[data-name="confirm_topup_account_credit_modal_title"]').html('Topup a voucher to this customer\'s account credit?');
					$('p[data-name="confirm_topup_account_credit_modal_content"]').html('This will assign account credit to (account credit + voucher\'s face value).<br/><br/>This will record your identity and manipulation time.<br/>');
					$('a[data-name="confirm_topup_account_credit_modal_btn"]').html('Confirm');
					$('div[data-name="voucher_pin_number_input"]').css('display','');
				}
				$('button[data-name="topup_account_credit_btn"]').button('loading');
				$('#confirmTopupAccountCreditModal').modal('show');
			});
			// Confirm PayWay
			$('a[data-name="confirm_topup_account_credit_modal_btn"]').click(function(){
				this.id = '${customer.id}';
				var pay_way = $(this).attr('data-way');
				var url = '';
				if(pay_way != 'voucher'){
					url = '${ctx}/broadband-user/crm/customer/account-credit/defrayment/pay-way';
				} else if(pay_way == 'voucher'){
					url = '${ctx}/broadband-user/crm/customer/account-credit/defrayment/voucher';
				}
				if(pay_way == 'ddpay'){
					var data = {
						customer_id : this.id
						,process_way : 'DDPay'
						,defray_amount : $('input[name="defray_amount"]').val()
					};
				} else if(pay_way == 'a2a'){
					var data = {
						customer_id : this.id
						,process_way : 'Account2Account'
						,defray_amount : $('input[name="defray_amount"]').val()
					};
				} else if(pay_way == 'credit-card'){
					var data = {
						customer_id : this.id
						,process_way : 'Credit Card'
						,defray_amount : $('input[name="defray_amount"]').val()
					};
				} else if(pay_way == 'cyberpark-credit'){
					var data = {
						customer_id : this.id
						,process_way : 'CyberPark Credit'
						,defray_amount : $('input[name="defray_amount"]').val()
					};
				} else if(pay_way == 'cash'){
					var data = {
						customer_id : this.id
						,process_way : 'Cash'
						,defray_amount : $('input[name="defray_amount"]').val()
					};
				} else if(pay_way == 'voucher'){
					var data = {
						customer_id : this.id
						,pin_number : $('input[name="pin_number"]').val()
					};
				}
				$.post(url, data, function(json){
					$.jsonValidation(json, 'right');
				}, 'json');
			});
			$('#confirmTopupAccountCreditModal').on('hidden.bs.modal', function(){
				$('button[data-name="topup_account_credit_btn"]').button('reset');
				$.getCustomerInfo();
				$.getTxPage(1);
			});
			// END Topup Account Credit
			
		}, "json");
	}


	$.getCustomerOrder = function() {
		var data = { 'id' : ${customer.id} };
		
		$.get('${ctx}/broadband-user/crm/customer/edit', data, function(map){
			map.ctx = '${ctx}';
			map.user_id = '${userSession.id}';
			map.user_role = '${userSession.user_role}';
	   		var $table = $('#order_detail');
			$table.html(tmpl('customer_order_table_tmpl', map));

			var co = map.customer.customerOrders;
			for(var i=0; i<co.length; i++){
				/*
				 *	BEGIN customer order area
				 */

				// Pay off this order
				// Get Pay off this order Dialog
				$('a[data-name="'+co[i].id+'_pay_off_order"]').click(function(){
					$btn = $(this); $btn.button('loading');
					var total_price =  new Number($('#'+this.id+'_order_total_price').attr('data-val'));
					$('input[data-name="paid_amount_'+this.id+'"]').val(total_price.toFixed(2));
					$('#payOffOrderModal_'+this.id).modal('show');
				});
				// Submit to rest controller
				$('a[data-name="payOffOrderModalBtn_'+co[i].id+'"]').click(function(){
					var paid_amount = $('input[data-name="paid_amount_'+this.id+'"]').val();
					var order_pay_way = $('select[data-name="order_pay_way_'+this.id+'"]').val();
					var order_total_price = $('#'+this.id+'_order_total_price').attr('data-val');

					var data = {
						'id':this.id
						,'customerId':'${customer.id}'
						,'paid_amount':paid_amount
						,'order_pay_way':order_pay_way
						,'order_total_price':order_total_price
					};
					$.post('${ctx}/broadband-user/crm/customer/order/pay-off/receipt', data, function(json){
						$.jsonValidation(json, 'right');
					}, "json");
				});
				// Reset button when hidden regenerate most recent invoice dialog
				$('#payOffOrderModal_'+co[i].id).on('hidden.bs.modal', function (e) {
					$.getCustomerOrder();
					$.getCustomerInfo();
					$.getTxPage(1);
					$('a[data-name="'+$(this).attr('data-id')+'_pay_off_order"]').button('reset');
				});
				
				// Regenerate most recent invoice
				// Get regenerate most recent invoice Dialog
				var orderStatusCheck = $('#'+co[i].id+'_order_status');
				if(orderStatusCheck.attr('data-val') != 'using'){
					$('a[data-name="'+co[i].id+'_regenerate_invoice"]').attr('disabled', 'disabled');
					$('a[data-name="'+co[i].id+'_manually_generate_invoice"]').attr('disabled', 'disabled');
					$('a[data-name="'+co[i].id+'_regenerate_no_term_invoice"]').attr('disabled', 'disabled');
					$('a[data-name="'+co[i].id+'_generate_no_term_invoice"]').attr('disabled', 'disabled');
				}
				if(orderStatusCheck.attr('data-val') == 'disconnected'){
					$('#'+co[i].id+'_order_disconnected_form_group').css('display','');
				}
				$('a[data-name="'+co[i].id+'_regenerate_invoice"]').click(function(){
					$btn = $(this); $btn.button('loading');
					$('a[data-name="generateOrderInvoiceModalBtn_'+this.id+'"]').prop('id', this.id);
					$('strong[data-name="generate_invoice_title_'+this.id+'"]').html('Regenerate Most Recent Invoice');
					$('p[data-name="generate_invoice_content_'+this.id+'"]').html('This action will remove all the details related to this order\'s most recent invoice. And will regenerate that invoice with it\'s PDF based on order\'s details. Do you want to regenerate this order\'s most recent invoice immediately?');
					$('a[data-name="generateOrderInvoiceModalBtn_'+this.id+'"]').html('Confirm to regenerate invoice!');
					$('a[data-name="generateOrderInvoiceModalBtn_'+this.id+'"]').attr('data-type', $btn.attr('data-type'));
					$('a[data-name="generateOrderInvoiceModalBtn_'+this.id+'"]').attr('data-order-type', 'order-term');
					$('#generateOrderInvoiceModal_'+this.id).modal('show');
				});
				$('a[data-name="'+co[i].id+'_manually_generate_invoice"]').click(function(){
					$btn = $(this); $btn.button('loading');
					$('a[data-name="generateOrderInvoiceModalBtn_'+this.id+'"]').prop('id', this.id);
					$('strong[data-name="generate_invoice_title_'+this.id+'"]').html('Generate Next Invoice');
					$('p[data-name="generate_invoice_content_'+this.id+'"]').html('This action will generate first(if there is no invoice(s)...) or next invoice for this order. Do you want to generate this order\'s first or next invoice immediately?');
					$('a[data-name="generateOrderInvoiceModalBtn_'+this.id+'"]').html('Confirm to generate invoice!');
					$('a[data-name="generateOrderInvoiceModalBtn_'+this.id+'"]').attr('data-type', $btn.attr('data-type'));
					$('a[data-name="generateOrderInvoiceModalBtn_'+this.id+'"]').attr('data-order-type', 'order-term');
					$('#generateOrderInvoiceModal_'+this.id).modal('show');
				});
				$('a[data-name="'+co[i].id+'_regenerate_no_term_invoice"]').click(function(){
					$btn = $(this); $btn.button('loading');
					$('a[data-name="generateOrderInvoiceModalBtn_'+this.id+'"]').prop('id', this.id);
					$('strong[data-name="generate_invoice_title_'+this.id+'"]').html('Regenerate Most Recent No Term Invoice');
					$('p[data-name="generate_invoice_content_'+this.id+'"]').html('This action will remove all the details related to this order\'s most recent invoice. And will regenerate that invoice with it\'s PDF based on order\'s details. Do you want to regenerate this order\'s most recent invoice immediately?');
					$('a[data-name="generateOrderInvoiceModalBtn_'+this.id+'"]').html('Confirm to regenerate invoice!');
					$('a[data-name="generateOrderInvoiceModalBtn_'+this.id+'"]').attr('data-type', $btn.attr('data-type'));
					$('a[data-name="generateOrderInvoiceModalBtn_'+this.id+'"]').attr('data-order-type', 'order-no-term');
					$('#generateOrderInvoiceModal_'+this.id).modal('show');
				});
				$('a[data-name="'+co[i].id+'_generate_no_term_invoice"]').click(function(){
					$btn = $(this); $btn.button('loading');
					$('a[data-name="generateOrderInvoiceModalBtn_'+this.id+'"]').prop('id', this.id);
					$('strong[data-name="generate_invoice_title_'+this.id+'"]').html('Generate Next No Term Invoice');
					$('p[data-name="generate_invoice_content_'+this.id+'"]').html('This action will generate first(if there is no invoice(s)...) or next invoice for this order. Do you want to generate this order\'s first or next invoice immediately?');
					$('a[data-name="generateOrderInvoiceModalBtn_'+this.id+'"]').html('Confirm to generate invoice!');
					$('a[data-name="generateOrderInvoiceModalBtn_'+this.id+'"]').attr('data-type', $btn.attr('data-type'));
					$('a[data-name="generateOrderInvoiceModalBtn_'+this.id+'"]').attr('data-order-type', 'order-no-term');
					$('#generateOrderInvoiceModal_'+this.id).modal('show');
				});
				$('a[data-name="'+co[i].id+'_regenerate_topup_invoice"]').click(function(){
					$btn = $(this); $btn.button('loading');
					$('a[data-name="generateOrderInvoiceModalBtn_'+this.id+'"]').prop('id', this.id);
					$('strong[data-name="generate_invoice_title_'+this.id+'"]').html('Regenerate Most Recent Topup Invoice');
					$('p[data-name="generate_invoice_content_'+this.id+'"]').html('This action will remove all the details related to this order\'s most recent invoice. And will regenerate that invoice with it\'s PDF based on order\'s details. Do you want to regenerate this order\'s most recent invoice immediately?');
					$('a[data-name="generateOrderInvoiceModalBtn_'+this.id+'"]').html('Confirm to regenerate invoice!');
					$('a[data-name="generateOrderInvoiceModalBtn_'+this.id+'"]').attr('data-type', $btn.attr('data-type'));
					$('a[data-name="generateOrderInvoiceModalBtn_'+this.id+'"]').attr('data-order-type', 'order-topup');
					$('#generateOrderInvoiceModal_'+this.id).modal('show');
				});
				$('a[data-name="'+co[i].id+'_generate_topup_invoice"]').click(function(){
					$btn = $(this); $btn.button('loading');
					$('a[data-name="generateOrderInvoiceModalBtn_'+this.id+'"]').prop('id', this.id);
					$('strong[data-name="generate_invoice_title_'+this.id+'"]').html('Generate Next Topup Invoice');
					$('p[data-name="generate_invoice_content_'+this.id+'"]').html('This action will generate first(if there is no invoice(s)...) or next invoice for this order. Do you want to generate this order\'s first or next invoice immediately?');
					$('a[data-name="generateOrderInvoiceModalBtn_'+this.id+'"]').html('Confirm to generate invoice!');
					$('a[data-name="generateOrderInvoiceModalBtn_'+this.id+'"]').attr('data-type', $btn.attr('data-type'));
					$('a[data-name="generateOrderInvoiceModalBtn_'+this.id+'"]').attr('data-order-type', 'order-topup');
					$('#generateOrderInvoiceModal_'+this.id).modal('show');
				});
				$('a[data-name="'+co[i].id+'_regenerate_ordering_form"]').click(function(){
					$btn = $(this); $btn.button('loading');
					$('a[data-name="generateOrderInvoiceModalBtn_'+this.id+'"]').prop('id', this.id);
					$('strong[data-name="generate_invoice_title_'+this.id+'"]').html('Regenerate Ordeing Form');
					$('p[data-name="generate_invoice_content_'+this.id+'"]').html('Regenerate this order\'s ordering form?');
					$('a[data-name="generateOrderInvoiceModalBtn_'+this.id+'"]').html('Confirm to generate ordering form!');
					$('a[data-name="generateOrderInvoiceModalBtn_'+this.id+'"]').attr('data-type', $btn.attr('data-type'));
					$('a[data-name="generateOrderInvoiceModalBtn_'+this.id+'"]').attr('data-order-type', 'ordering-form');
					$('#generateOrderInvoiceModal_'+this.id).modal('show');
				});
				// Submit to rest controller
				$('a[data-name="generateOrderInvoiceModalBtn_'+co[i].id+'"]').click(function(){
					var generateType = $(this).attr('data-type');
					var orderType = $(this).attr('data-order-type');
					var data = {
						'id':this.id
						,'generateType':generateType
					};
					
					var url = '';
					if(orderType=='order-term'){
						url = '${ctx}/broadband-user/crm/customer/order/invoice/termed/manually-generate';
					} else if(orderType=='order-no-term'){
						url = '${ctx}/broadband-user/crm/customer/order/invoice/no-term/manually-generate';
					} else if(orderType=='order-topup'){
						url = '${ctx}/broadband-user/crm/customer/order/invoice/topup/manually-generate';
					} else if(orderType=='ordering-form'){
						url = '${ctx}/broadband-user/crm/customer/order/ordering-form/pdf/regenerate';
					}
					
					$.post(url, data, function(json){
						$.jsonValidation(json, 'right');
						$.getInvoicePage(1);
						$.getCustomerInfo();
					}, "json");
				});
				// Reset button when hidden regenerate most recent invoice dialog
				$('#generateOrderInvoiceModal_'+co[i].id).on('hidden.bs.modal', function (e) {
					$('a[data-name="'+$(this).attr('data-id')+'_regenerate_invoice"]').button('reset');
					$('a[data-name="'+$(this).attr('data-id')+'_manually_generate_invoice"]').button('reset');
					$('a[data-name="'+$(this).attr('data-id')+'_regenerate_no_term_invoice"]').button('reset');
					$('a[data-name="'+$(this).attr('data-id')+'_generate_no_term_invoice"]').button('reset');
					$('a[data-name="'+$(this).attr('data-id')+'_generate_topup_invoice"]').button('reset');
					$('a[data-name="'+$(this).attr('data-id')+'_regenerate_ordering_form"]').button('reset');
				});
				

				// Early Termination Charge modal
				// Get Early Termination Charge Dialog
				if(orderStatusCheck.attr('data-val') == 'suspended'
				 ||orderStatusCheck.attr('data-val') == 'waiting-for-disconnect'
				 ||orderStatusCheck.attr('data-val') == 'disconnected'
				 ||orderStatusCheck.attr('data-val') == 'void'
				 ||orderStatusCheck.attr('data-val') == 'stop'
				 ||orderStatusCheck.attr('data-val') == 'cancel'
				 ||orderStatusCheck.attr('data-val') == 'discard'){
				} else {
					$('a[data-name="'+co[i].id+'_early_termination_charge"]').attr('disabled', 'disabled');
					$('a[data-name="'+co[i].id+'_termination_refund"]').attr('disabled', 'disabled');
				}
				$('a[data-name="'+co[i].id+'_early_termination_charge"]').click(function(){
					$btn = $(this); $btn.button('loading');
					$('a[data-name="earlyTerminationChargeModalBtn_'+this.id+'"]').prop('id', this.id);
					$('#earlyTerminationChargeModal_'+this.id).modal('show');
				});
				// Submit to rest controller
				$('a[data-name="earlyTerminationChargeModalBtn_'+co[i].id+'"]').click(function(){
					var data = {
						'id':this.id,
						'terminatedDate':$('input[data-name="early_terminated_charge_date_'+this.id+'"]').val()
					};
					
					$.post('${ctx}/broadband-user/crm/customer/order/early-termination-charge/invoice/generate', data, function(json){
						$.jsonValidation(json, 'right');
					}, "json").always(function () {
						
				    });
				});
				// Reset button when hidden Early Termination Charge dialog
				$('#earlyTerminationChargeModal_'+co[i].id).on('hidden.bs.modal', function (e) {
					$('a[data-name="'+$(this).attr('data-id')+'_early_termination_charge"]').button('reset');
				});
				
				// Termination Refund modal
				$('a[data-name="'+co[i].id+'_termination_refund"]').click(function(){
					$btn = $(this); $btn.button('loading');
					$('a[data-name="terminationRefundModalBtn_'+this.id+'"]').prop('id', this.id);
					$('input[data-name="terminated_refund_monthly_charge_'+this.id+'"]').val($('td[data-name="plan-detail_'+this.id+'"]').attr('data-price'));
					$('#terminationRefundModal_'+this.id).modal('show');
				});
				// Submit to rest controller
				$('a[data-name="terminationRefundModalBtn_'+co[i].id+'"]').click(function(){
					var data = {
						'id':this.id,
						'terminatedDate':$('input[data-name="terminated_refund_date_'+this.id+'"]').val(),
						'monthlyCharge':$('input[data-name="terminated_refund_monthly_charge_'+this.id+'"]').val(),
						'accountNo':$('input[data-name="terminated_refund_bank_account_no_'+this.id+'"]').val(),
						'accountName':$('input[data-name="terminated_refund_bank_account_name_'+this.id+'"]').val(),
						'productName':$('td[data-name="plan-detail_'+this.id+'"]').attr('data-plan-name')
					};
					
					$.post('${ctx}/broadband-user/crm/customer/order/termination-credit/invoice/generate', data, function(json){
						$.jsonValidation(json, 'right');
					}, "json").always(function () {
						
				    });
				});
				// Reset button when hidden Termination Refund dialog
				$('#terminationRefundModal_'+co[i].id).on('hidden.bs.modal', function (e) {
					$('a[data-name="'+$(this).attr('data-id')+'_termination_refund"]').button('reset');
				});
				
				$('select[data-name="'+co[i].id+'_order_status_selector"]').change(function(){
					if($(this).val()=='disconnected'){
						$('#'+this.id+'_order_disconnected_form_group').css('display', '');
					} else {
						$('#'+this.id+'_order_disconnected_form_group').css('display', 'none');
					}
				});
				
				// Update order status
				// Get order status Dialog
				$('a[data-name="'+co[i].id+'_order_status_edit"]').click(function(){
					$btn = $(this); $btn.button('loading');
					$('a[data-name="editOrderStatusModalBtn_'+this.id+'"]').prop('id', this.id);
					$('#editOrderStatusModal_'+this.id).modal('show');
				});
				// Submit to rest controller
				$('a[data-name="editOrderStatusModalBtn_'+co[i].id+'"]').click(function(){
					var orderStatus = $('select[data-name="'+this.id+'_order_status_selector"]');
					var oldOrderStatus = $('#'+this.id+'_order_status');
					var disconnected_input = $('input[data-name="'+this.id+'_order_disconnected_input_picker"]').val();
					var data = {
							'id':this.id
							,'order_status':orderStatus.val()
							,'old_order_status':oldOrderStatus.val()
							,'disconnected_date_str':disconnected_input
					};
					var order_status = $('#'+this.id+'_order_status');
					$.post('${ctx}/broadband-user/crm/customer/order/status/edit', data, function(json){
						$.jsonValidation(json, 'left');
					}, "json")
				});
				// Reset button when hidden order status dialog
				$('#editOrderStatusModal_'+co[i].id).on('hidden.bs.modal', function (e) {
					$('a[data-name="'+$(this).attr('data-id')+'_order_status_edit"]').button('reset');
					$.getCustomerOrder();
				});
				
				// Update order due date
				// Get order due date Dialog
				$('a[data-name="'+co[i].id+'_order_due_input_btn"]').click(function(){
					$btn = $(this); $btn.button('loading');
					$('a[data-name="editOrderDueDateModalBtn_'+this.id+'"]').prop('id', this.id);
					$('#editOrderDueDateModal_'+this.id).modal('show');
				});
				// Submit to rest controller
				$('a[data-name="editOrderDueDateModalBtn_'+co[i].id+'"]').click(function(){
					var orderDueDate = $('input[data-name="'+this.id+'_order_due_input_picker"]');
					var data = {
							'id':this.id
							,'order_due_str':orderDueDate.val()
					};
					var order_due_date = $('#'+this.id+'_order_due');
					$.post('${ctx}/broadband-user/crm/customer/order/due_date/edit', data, function(json){
						$.jsonValidation(json, 'left');
					}, "json");
				});
				// Reset button when hidden order due date dialog
				$('#editOrderDueDateModal_'+co[i].id).on('hidden.bs.modal', function (e) {
					$('a[data-name="'+$(this).attr('data-id')+'_order_due_input_btn"]').button('reset');
					$.getCustomerOrder();
				});
				
				$('a[data-name="generateApplicationUrl_'+co[i].id+'"]').click(function(){
					var url = $(this).attr('data-url');
					$.post(url, function(json){
						$.jsonValidation(json, 'left');
						$.getCustomerOrder();
					})
				});

				// Update order belongs to
				// Get order belongs to Dialog
				$('a[data-name="'+co[i].id+'_order_belongs_to_edit"]').click(function(){
					$btn = $(this); $btn.button('loading');
					$('a[data-name="editOrderBelongsToModalBtn_'+this.id+'"]').prop('id', this.id);
					$('#editOrderBelongsToModal_'+this.id).modal('show');
				});
				// Submit to rest controller
				$('a[data-name="editOrderBelongsToModalBtn_'+co[i].id+'"]').click(function(){
					var belongsTo = $('select[data-name="'+this.id+'_order_belongs_to_selector"]');
					var data = {
							'id':this.id
							,'sale_id':belongsTo.val()
							,'user_name':belongsTo.find("option:selected").text()
					};
					var order_belongs_to = $('#'+this.id+'_order_belongs_to');
					$.post('${ctx}/broadband-user/crm/customer/order/belongs_to/edit', data, function(json){
						$.jsonValidation(json, 'left');
					}, "json")
				});
				// Reset button when hidden belongs to dialog
				$('#editOrderBelongsToModal_'+co[i].id).on('hidden.bs.modal', function (e) {
					$('a[data-name="'+$(this).attr('data-id')+'_order_belongs_to_edit"]').button('reset');
					$.getCustomerOrder();
				});

				//
				// View order optional request
				$('a[data-name="'+co[i].id+'_order_optional_request"]').click(function(){
					$('#viewOrderOptionalRequestModal_'+this.id).modal('show');
				});
				
				// Update order optional request
				// Get order optional request Dialog
				$('a[data-name="'+co[i].id+'_optional_request_edit"]').click(function(){
					$btn = $(this); $btn.button('loading');
					$('a[data-name="editOrderOptionalRequestModalBtn_'+this.id+'"]').prop('id', this.id);
					$('#editOrderOptionalRequestModal_'+this.id).modal('show');
				});
				// Submit to rest controller
				$('a[data-name="editOrderOptionalRequestModalBtn_'+co[i].id+'"]').click(function(){
					var optionalRequest = $('textarea[data-name="'+this.id+'_order_optional_request_text"]');
					var data = {
							'id':this.id
							,'optional_request':optionalRequest.val()
					};
					var order_optional_request = $('a[data-name="'+this.id+'_order_optional_request"]');
					$.post('${ctx}/broadband-user/crm/customer/order/optional_request/edit', data, function(json){
						$.jsonValidation(json, 'left');
					}, "json")
				});
				// Reset button when hidden optional request dialog
				$('#editOrderOptionalRequestModal_'+co[i].id).on('hidden.bs.modal', function (e) {
					$('a[data-name="'+$(this).attr('data-id')+'_optional_request_edit"]').button('reset');
					$.getCustomerOrder();
				});
				
				// Get order edit is ddpay Dialog
				$('a[data-name="'+co[i].id+'_is_ddpay_edit"]').click(function(){
					$btn = $(this); $btn.button('loading');
					$('a[data-name="editIsDDPayModalBtn_'+this.id+'"]').prop('id', this.id);
					$('#editIsDDPayModal_'+this.id).modal('show');
				});
				// Submit to rest controller
				$('a[data-name="editIsDDPayModalBtn_'+co[i].id+'"]').click(function(){
					var isDDPay = $('select[data-name="'+this.id+'_is_ddpay_to_selector"]');
					var data = {
							'id':this.id
							,'is_ddpay':isDDPay.val()
					};
					$.post('${ctx}/broadband-user/crm/customer/order/is_ddpay/edit', data, function(json){
						$.jsonValidation(json, 'left');
					}, "json");
				});
				// Reset button when hidden edit is ddpay dialog
				$('#editIsDDPayModal_'+co[i].id).on('hidden.bs.modal', function (e) {
					$('a[data-name="'+$(this).attr('data-id')+'_is_ddpay_edit"]').button('reset');
					$.getCustomerOrder();
				});
				/*
				 *	END customer order area
				 */
				 
				 
				/*
				 *	BEGIN customer order PPPoE area
				 */
				// Update order PPPoE
				// Get order PPPoE Dialog
				$('a[data-name="'+co[i].id+'_pppoe_edit_btn"]').click(function(){
					$btn = $(this); $btn.button('loading');
					$('a[data-name="pppoe_edit_modal_btn_'+this.id+'"]').prop('id', this.id);
					$('#editPPPoEModal_'+this.id).modal('show');	// click Edit PPPoE then performing Ajax action
				});
				// Submit to rest controller
				$('a[data-name="pppoe_edit_modal_btn_'+co[i].id+'"]').click(function(){
					var loginname_input_val = $('input[data-name="'+this.id+'_pppoe_loginname"]').val();
					var password_input_val = $('input[data-name="'+this.id+'_pppoe_password"]').val();
					var data = {
							 'id':this.id
							,'pppoe_loginname':loginname_input_val+''
							,'pppoe_password':password_input_val+''
					};
					var order_pppoe_loginname = $('#'+this.id+'_pppoe_loginname');
					var order_pppoe_password = $('#'+this.id+'_pppoe_password');
					$.post('${ctx}/broadband-user/crm/customer/order/ppppoe/edit', data, function(json){
						if(!$.jsonValidation(json, 'left')){
							order_pppoe_loginname.html(loginname_input_val);
							order_pppoe_password.html(password_input_val);
						}
					}, "json");
				});
				// Reset button when hidden order PPPoE dialog
				$('#editPPPoEModal_'+co[i].id).on('hidden.bs.modal', function (e) {
					$('a[data-name="'+$(this).attr('data-id')+'_pppoe_edit_btn"]').button('reset');
				});
				/*
				 *	END customer order PPPoE area
				 */

				 
				/*
				 *	BEGIN customer order SV/CVLan & RFS Date area
				 */
				 // Save/Update SV/CVLan & RFS Date
				// Get order SV/CVLan & RFS Date Dialog
				$('a[data-name="'+co[i].id+'_svcvlan_save"]').click(function(){
					$btn = $(this); $btn.button('loading');
					$('a[data-name="svcvlan_rfs_date_save_'+this.id+'"]').prop('id', this.id);
					$('a[data-name="svcvlan_rfs_date_save_'+this.id+'"]').attr('data-way', $(this).attr('data-way'));
					$('#saveSVCVLanRFSDateModal_'+this.id+'').modal('show');
				});
				// Submit to rest controller
				$('a[data-name="svcvlan_rfs_date_save_'+co[i].id+'"]').click(function(){
					var cvlan_input = $('#'+this.id+'_cvlan_input').val();
					var svlan_input = $('#'+this.id+'_svlan_input').val();
					var rfs_date_input = $('input[data-name="'+this.id+'_rfs_date_input_picker"]').val();
					var data = {
							'customer_id':'${customer.id}'
							,'id':this.id
							,'cvlan':cvlan_input+''
							,'svlan':svlan_input+''
							,'rfs_date_str':rfs_date_input
							,'way':$(this).attr('data-way')
					};
					var cvlan = $('#'+this.id+'_cvlan');
					var svlan = $('#'+this.id+'_svlan');
					var rfs_date = $('#'+this.id+'_rfs_date');
					$.post('${ctx}/broadband-user/crm/customer/order/save/svcvlanrfsdate', data, function(json){
						if(!$.jsonValidation(json, 'left')){
							cvlan.html(cvlan_input);
							svlan.html(svlan_input);
							rfs_date.html(rfs_date_input);
							$.getCustomerOrder();
						}
					}, "json");
				});
				// Reset button when hidden order PPPoE dialog
				$('#saveSVCVLanRFSDateModal_'+co[i].id).on('hidden.bs.modal', function (e) {
					$('a[data-name="'+$(this).attr('data-id')+'_svcvlan_save"]').button('reset');
				});
				/*
				 *	END customer order SV/CVLan & RFS Date area
				 */

				 
				/*
				 *	BEGIN customer order Service Giving Date area
				 */
				 // Save/Update Service Giving Date
				// Get order Service Giving Date Dialog
				$('a[data-name="'+co[i].id+'_service_giving_save"]').click(function(){
					$btn = $(this); $btn.button('loading');
					var order_status = $('#'+this.id+'_order_status');
					// if status is ordering then show save modal
					if(order_status.attr('data-val')=='ordering-paid'
					|| order_status.attr('data-val')=='ordering-pending'
					|| order_status.attr('data-val')=='using'
					|| order_status.attr('data-val')=='rfs'){
						$('a[data-name="service_giving_save_'+this.id+'"]').prop('id', this.id);
						$('a[data-name="service_giving_save_'+this.id+'"]').attr('data-way', $(this).attr('data-way'));
						$('a[data-name="service_giving_save_'+this.id+'"]').attr('data-pay-status', $(this).attr('data-pay-status'));
						$('#saveServiceGivingModal_'+this.id).modal('show');	// click Save Order then performing Ajax action
					// else show denied modal
					} else {
						$('#saveServiceGivingDeniedModal_'+this.id).modal('show');
					}
				});
				// Submit to rest controller
				$('a[data-name="service_giving_save_'+co[i].id+'"]').click(function(){
					var order_using_start_input = $('input[data-name="'+this.id+'_order_using_start_input_picker"]').val();
					var order_status = $('#'+this.id+'_order_status')
					var order_type = $('#'+this.id+'_order_type').attr('data-val')
					var order_detail_unit_attr = $('#'+this.id+'_order_detail_unit').attr('data-val');
					var data = {
							'customer_id':'${customer.id}'
							,'id':this.id
							,'order_using_start_str':order_using_start_input
							,'order_detail_unit':(order_detail_unit_attr==null?1:order_detail_unit_attr)
							,'order_status':order_status.attr('data-val')
							,'order_type':order_type
							,'way':$(this).attr('data-way')
							,'pay_status':$(this).attr('data-pay-status')+''
					};
					
					$.post('${ctx}/broadband-user/crm/customer/order/service_giving_date', data, function(json){
						$.jsonValidation(json, 'left');
						
						if(json.model){
							$('#'+json.model.id+'_order_using_start').html(json.model.order_using_start_str);
							$('#'+json.model.id+'_next_invoice_create_date').html(json.model.next_invoice_create_date_str);
							$('#'+json.model.id+'_service_giving_save').css('display', '');
							$('#'+json.model.id+'_service_giving_save_btn_group').css('display', 'none');
							$.getCustomerOrder();
							$.getInvoicePage(1);
						}
						
					}, "json");
				});
				// Reset button when hidden order Service Giving dialog
				$('#saveServiceGivingModal_'+co[i].id).on('hidden.bs.modal', function (e) {
					
					$('a[data-name="'+$(this).attr('data-id')+'_service_giving_save"]').button('reset');
					
					$.getCustomerOrder();
					$.getInvoicePage(1);
					$.getCustomerInfo();
					$.getTxPage(1);
				});
				// Reset button when hidden order Service Giving dialog
				$('#saveServiceGivingDeniedModal_'+co[i].id).on('hidden.bs.modal', function (e) {
					$('a[data-name="'+$(this).attr('data-id')+'_service_giving_save"]').button('reset');
				});
				/*
				 *	END customer order Service Giving Date area
				 */

				/*
				 *	BEGIN customer order Broadband ASID area
				 */
				// Update order Broadband ASID request
				// Get order optional request Dialog
				$('a[data-name="'+co[i].id+'_brodband_asid_btn"]').click(function(){
					$btn = $(this); $btn.button('loading');
					$('a[data-name="saveBroadbandASIDModalBtn_'+this.id+'"]').prop('id', this.id);
					$('#saveBroadbandASIDModal_'+this.id).modal('show');
				});
				// Submit to rest controller
				$('a[data-name="saveBroadbandASIDModalBtn_'+co[i].id+'"]').click(function(){
					var broadbandASID = $('input[data-name="'+this.id+'_brodband_asid"]');
					var data = {
							'id':this.id
							,'broadband_asid':broadbandASID.val()
					};
					var order_broadband_asid = $('#'+this.id+'_broadband_asid');
					$.post('${ctx}/broadband-user/crm/customer/order/broadband_asid/edit', data, function(json){
						$.jsonValidation(json, 'left');
					}, "json");
				});
				// Reset button when hidden Broadband ASID dialog
				$('#saveBroadbandASIDModal_'+co[i].id).on('hidden.bs.modal', function (e) {
					$('a[data-name="'+$(this).attr('data-id')+'_brodband_asid_btn"]').button('reset');
					$.getCustomerOrder();
				});
				/*
				 *	END customer order Broadband ASID area
				 */
				 

				/*
				 *	BEGIN customer order detail(s) area
				 */
				$('a[data-name="'+co[i].id+'_update_pstn"]').click(function(){
					$btn = $(this); $btn.button('loading');
					$('a[data-name="updatePSTNModalBtn_'+this.id+'"]').attr('data-detail-id', $(this).attr('data-id'));
					$('a[data-name="updatePSTNModalBtn_'+this.id+'"]').attr('data-id', this.id);
					$('input[data-name="pstn_number_'+this.id+'"]').val($(this).attr('data-val'));
					$('#updatePSTNModal_'+this.id).modal('show');
				});
				$('a[data-name="updatePSTNModalBtn_'+co[i].id+'"]').on('click', function (e) {
					this.id = $(this).attr('data-id');
					var order_detail_id = $(this).attr('data-detail-id');
					var pstn_number = $('input[data-name="pstn_number_'+this.id+'"]').val();
					var data = {
							'order_detail_id':order_detail_id
							,'customer_id':${customer.id}
							,'pstn_number':pstn_number
					};
					$.post('${ctx}/broadband-user/crm/customer/order/pstn/edit', data, function(json){
						$.jsonValidation(json, 'left');						
					}, "json");
				});
				// Reset button when hidden order pstn dialog
				$('#updatePSTNModal_'+co[i].id).on('hidden.bs.modal', function (e) {
					$('a[data-name="'+$(this).attr('data-id')+'_update_pstn"]').button('reset');
					$.getCustomerOrder();
				});

				/*
				 * BEGIN Edit detail
				 */
				// Edit Detail modal
				$('a[data-name="'+co[i].id+'_edit_detail"]').click(function(){
					$btn = $(this); $btn.button('loading');
					$('a[data-name="editDetailModalBtn_'+this.id+'"]').prop('id', this.id);
					$('a[data-name="editDetailModalBtn_'+this.id+'"]').prop('data-id', $(this).attr('data-id'));
					$('#editDetailModal_'+this.id).prop('data-id', $(this).attr('data-id'));
					
					// Original Values
					$('input[data-name="detail_name_'+this.id+'"]').val($('td[data-name="'+$(this).attr('data-id')+'_detail_name"]').attr('data-val'));
					$('input[data-name="detail_type_'+this.id+'"]').val($('td[data-name="'+$(this).attr('data-id')+'_detail_type"]').attr('data-val'));
					$('input[data-name="detail_plan_type_'+this.id+'"]').val($('td[data-name="'+$(this).attr('data-id')+'_detail_plan_type"]').attr('data-val'));
					$('input[data-name="detail_plan_sort_'+this.id+'"]').val($('td[data-name="'+$(this).attr('data-id')+'_detail_plan_sort"]').attr('data-val'));
					$('input[data-name="detail_price_'+this.id+'"]').val($('td[data-name="'+$(this).attr('data-id')+'_detail_price"]').attr('data-val'));
					$('input[data-name="data_flow_'+this.id+'"]').val($('span[data-name="'+$(this).attr('data-id')+'_data_flow"]').attr('data-val'));
					$('#editDetailModal_'+this.id).modal('show');
				});
				// Submit to rest controller
				$('a[data-name="editDetailModalBtn_'+co[i].id+'"]').click(function(){
					var data = {
						'id':$(this).prop('data-id'),
						'order_id':this.id,
						'detail_name':$('input[data-name="detail_name_'+this.id+'"]').val(),
						'detail_type':$('input[data-name="detail_type_'+this.id+'"]').val(),
						'detail_plan_type':$('input[data-name="detail_plan_type_'+this.id+'"]').val(),
						'detail_plan_sort':$('input[data-name="detail_plan_sort_'+this.id+'"]').val(),
						'detail_price':$('input[data-name="detail_price_'+this.id+'"]').val(),
						'detail_data_flow':$('input[data-name="data_flow_'+this.id+'"]').val()
					};
					
					$.post('${ctx}/broadband-user/crm/customer/order/detail/plan/edit', data, function(json){
						$.jsonValidation(json, 'right');
					}, "json");
				});
				// Reset button when hidden Edit Detail dialog
				$('#editDetailModal_'+co[i].id).on('hidden.bs.modal', function (e) {
					$('a[data-id="'+$(this).prop('data-id')+'"]').button('reset');
					$.getCustomerOrder();
				});
				/*
				 * END Edit detail
				 */
				
				/*
				 *	BEGIN Remove detail
				 */
				$('a[data-name="'+co[i].id+'_remove_detail"]').click(function(){
					$btn = $(this); $btn.button('loading');
					$('a[data-name="removeDetailModalBtn_'+this.id+'"]').attr('data-detail-id', $(this).attr('data-id'));
					$('a[data-name="removeDetailModalBtn_'+this.id+'"]').attr('data-type', $(this).attr('data-type'));
					$('#removeDetailModal_'+this.id).modal('show');
				});
				// Submit to rest controller
				$('a[data-name="removeDetailModalBtn_'+co[i].id+'"]').on('click', function (e) {
					var order_detail_id = $(this).attr('data-detail-id');
					var data = {
							'order_detail_id':order_detail_id
							,'customer_id':${customer.id}
							,'detail_type':$(this).attr('data-type')
					};
					$.post('${ctx}/broadband-user/crm/customer/order/detail/remove', data, function(json){
						$.jsonValidation(json, 'left');
					}, "json");
				});
				// Reset button when hidden order remove detail dialog
				$('#removeDetailModal_'+co[i].id).on('hidden.bs.modal', function (e) {
					$('a[data-name="'+$(this).attr('data-id')+'_remove_detail"]').button('reset');
					$.getCustomerOrder();
				});
				/*
				 *	END Remove detail
				 */
				
				 
				/*
				 *	BEGIN Add detail
				 */
				$('a[data-name="'+co[i].id+'_add_detail"]').click(function(){
					$btn = $(this); $btn.button('loading');
					$('a[data-name="addDetailModalBtn_'+this.id+'"]').attr('data-id', this.id);
					$('#addDetailModal_'+this.id).modal('show');
				});
				$('select[data-name="'+co[i].id+'_detail_type"]').change(function(){
					$('div[data-name="'+this.id+'_detail_modal_body"]').css('display','none');
					$('div[data-type="'+this.id+'_'+$(this).val()+'_modal_body"]').css('display','');
					$('a[data-name="addDetailModalBtn_'+this.id+'"]').attr('data-type', $(this).val());
				});
				// Submit to rest controller
				$('a[data-name="addDetailModalBtn_'+co[i].id+'"]').on('click', function (e) {
					this.id = $(this).attr('data-id');
					var detail_type = $(this).attr('data-type');
					var data = {};
					
					var url = '${ctx}/broadband-user/crm/customer/order/detail/save';
					if(detail_type == 'early-termination-debit'){
						data = {
								'order_id':this.id
								,'terminatedDate':$('input[data-name="'+this.id+'_detail_early_termination_date"]').val()+''
								,'detail_type':detail_type+''
						};
						url = '${ctx}/broadband-user/crm/customer/order/detail/early-termination-debit/save';
					} else if(detail_type == 'termination-credit'){
						data = {
								'order_id':this.id
								,'terminatedDate':$('input[data-name="'+this.id+'_detail_termination_date"]').val()+''
								,'detail_type':detail_type+''
								,'monthlyCharge':$('span[data-name="'+this.id+'_detail_plan_monthly_price"]').attr('data-val')+''
						};
						url = '${ctx}/broadband-user/crm/customer/order/detail/termination-credit/save';
					} else if(detail_type == 'present-calling-minutes'){
						data = {
								'order_id':this.id
								,'customer_id':${customer.id}
								,'charge_amount':$('input[data-name="'+this.id+'_charge_amount"]').val()+''
								,'calling_minutes':$('input[data-name="'+this.id+'_calling_minutes"]').val()+''
								,'calling_country':$('select[data-name="'+this.id+'_calling_country"]').val()+''
						};
						url = '${ctx}/broadband-user/crm/customer/order/offer-calling-minutes/save';
					} else {
						var detail_name = $('input[data-name="'+this.id+'_'+detail_type+'_name"]').val();
						var detail_price = $('input[data-name="'+this.id+'_'+detail_type+'_price"]').val();
						var detail_unit = $('input[data-name="'+this.id+'_'+detail_type+'_unit"]').val();
						var detail_expired = $('input[data-name="'+this.id+'_'+detail_type+'_expired"]').val();
						data = {
								'order_id':this.id
								,'customer_id':${customer.id}
								,'detail_name':detail_name+''
								,'detail_price':detail_price+''
								,'detail_unit':detail_unit+''
								,'detail_expired':detail_expired+''
								,'detail_type':detail_type+''
						};
					}
					if(detail_type != 'none'){
						$.post(url, data, function(json){
							$.jsonValidation(json, 'left');
						}, "json");
					}
					
				});
				// Reset button when hidden order add detail dialog
				$('#addDetailModal_'+co[i].id).on('hidden.bs.modal', function (e) {
					$('a[data-name="'+$(this).attr('data-id')+'_add_detail"]').button('reset');
					$.getCustomerOrder();
				});
				/*
				 *	END Add detail
				 */

					
				/*
// 				 *	BEGIN Remove calling minutes
				 */
				$('a[data-name="'+co[i].id+'_remove_present-calling-minutes"]').click(function(){
					$btn = $(this); $btn.button('loading');
					$('a[data-name="removeCallingMinutesModalBtn_'+this.id+'"]').attr('data-detail-id', $(this).attr('data-id'));
					$('#removeCallingMinutesModal_'+this.id).modal('show');
				});
				// Submit to rest controller
				$('a[data-name="removeCallingMinutesModalBtn_'+co[i].id+'"]').on('click', function (e) {
					var order_detail_id = $(this).attr('data-detail-id');
					var data = {
							'order_detail_id':order_detail_id
							,'customer_id':${customer.id}
					};
					$.post('${ctx}/broadband-user/crm/customer/order/present-calling-minutes/remove', data, function(json){
						$.jsonValidation(json, 'left');
					}, "json");
				});
				// Reset button when hidden order remove discount dialog
				$('#removeCallingMinutesModal_'+co[i].id).on('hidden.bs.modal', function (e) {
					$('a[data-name="'+$(this).attr('data-id')+'_remove_present-calling-minutes"]').button('reset');
					$.getCustomerOrder();
				});
				/*
				 *	END Remove calling minutes
				 */
				/*
				 *	END customer order detail(s) area
				 */
				 
				 
				/*
				 *	BEGIN Datepicker area
				 */
				// Order disconnected Datepicker
				var order_disconnected_input = $('input[data-name="'+co[i].id+'_order_disconnected_input_picker"]').attr('data-val');
				$('#'+co[i].id+'_order_disconnected_datepicker').datepicker({
				    format: "yyyy-mm-dd",
				    autoclose: true,
				    todayHighlight: true
				    // if order disconnected date is null then assign new Date(), else assign order due date
				}).datepicker('setDate', order_disconnected_input || new Date());
				 
				// Order due Datepicker
				var order_due_input = $('input[data-name="'+co[i].id+'_order_due_input_picker"]').attr('data-val');
				$('#'+co[i].id+'_order_due_datepicker').datepicker({
				    format: "yyyy-mm-dd",
				    autoclose: true,
				    todayHighlight: true
				    // if order due date is null then assign new Date(), else assign order due date
				}).datepicker('setDate', order_due_input || new Date());
				
				// Order RFS date Datepicker
				var rfs_date_input = $('input[data-name="'+co[i].id+'_rfs_date_input_picker"]').attr('data-val');
				$('#'+co[i].id+'_rfs_date_picker').datepicker({
				    format: "yyyy-mm-dd",
				    autoclose: true,
				    todayHighlight: true
				    // if RFS date is null then assign new Date(), else assign RFS date
				}).datepicker('setDate', rfs_date_input || new Date());
				
				// Order using start Datepicker
				var order_using_start_input = $('input[data-name="'+co[i].id+'_order_using_start_input_picker"]').attr('data-val');
				$('#'+co[i].id+'_order_using_start_datepicker').datepicker({
				    format: "yyyy-mm-dd",
				    autoclose: true,
				    todayHighlight: true
				    
				    // if service giving date is null then assign new Date(), else assign service giving date 
				}).datepicker('setDate', order_using_start_input || new Date());
				/*
				 *	END Datepicker area
				 */
				
			}
			$('a[data-toggle="tooltip"]').tooltip();
			
		}, "json");
	}
	
	
	$.getTxPage = function(pageNo) {
		$.get('${ctx}/broadband-user/crm/transaction/view/' + pageNo +'/'+ ${customer.id}, function(json){
			json.ctx = '${ctx}';
			json.customer_id = ${customer.id};
	   		var $table = $('#transaction_detail');
			$table.html(tmpl('transaction_table_tmpl', json));
			$table.find('tfoot a').click(function(){
				$.getTxPage($(this).attr('data-pageNo'));
			});
		}, "json");
	}
	
	
	$.getCcrPage = function(pageNo) {
		$.get('${ctx}/broadband-user/crm/customer-service-record/view/' + pageNo +'/'+ ${customer.id}, function(json){
			json.ctx = '${ctx}';
			json.customer_id = ${customer.id};
	   		var $table = $('#customer_service_record_detail');
			$table.html(tmpl('customer_service_record_table_tmpl', json));
			$table.find('tfoot a').click(function(){
				$.getCcrPage($(this).attr('data-pageNo'));
			});
			

			$('a[data-name="new_service_record_btn"]').click(function(){
				$('a[data-name="new_service_record_btn"]').button('loading');
				$('#customerServiceRecordModal').modal('show');
			});
			$('a[data-name="customerServiceRecordModalBtn"]').click(function(){
				var description = $('textarea[data-name="customer_service_record_description"]').val();
				var data = {
						customer_id : ${customer.id}
						,description : description+''
				};
				$.post('${ctx}/broadband-user/crm/customer-service-record/create', data, function(json){
					$.jsonValidation(json, 'right');
				}, 'json');
			});
			$('#customerServiceRecordModal').on('hidden.bs.modal', function(){
				$('a[data-name="new_service_record_btn"]').button('reset');
				$.getCcrPage(pageNo);
			});
			
		}, "json");
	}
	
	
	$.getInvoicePage = function(pageNo) {
		$.get('${ctx}/broadband-user/crm/invoice/view/' + pageNo +'/'+ ${customer.id}, function(map){
			map.ctx = '${ctx}';
			map.customer_id = ${customer.id};
			map.orderIds = orderIds;
			map.user_role = '${userSession.user_role}';
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
					$('#regenerateInvoiceModal_'+this.id).modal('show');
				});
				// BEGIN first generate
				// Binding every firstGenerate button's click event by assign them specific invoice's id
				$('a[data-name="firstGenerate_'+invoice.id+'"]').click(function(){
					$('a[data-name="regenerateInvoiceBtn_'+this.id+'"]').attr('data-type', 'first');
					$('#regenerateInvoiceModal_'+this.id).modal('show');
				});
				// END first generate
				$('a[data-name="regenerateInvoiceBtn_'+invoice.id+'"]').click(function(){
					var url = $(this).attr('data-url');
					$.post('${ctx}/broadband-user/crm/customer/invoice/pdf/generate/'+this.id, function(json){
						$.jsonValidation(json, 'left');
					})
					if(typeof $(this).attr('data-type') != 'undefined'){
						$('a[data-name="firstGenerate_'+this.id+'"]').css('display', 'none');
						$('a[data-name="generateUrl_'+this.id+'"]').css('display', '');
						$('a[data-name="download_'+this.id+'"]').css('display', '');
						$('a[data-name="send_'+this.id+'"]').css('display', '');
					}
				});
				$('#regenerateInvoiceModal_'+invoice.id).on('hidden.bs.modal', function (e) {
					$.getInvoicePage(pageNo);
				});
				// END generate invoice
				
				// BEGIN PayWay
				$('a[data-name="pay_way_by_'+invoice.id+'"]').click(function(){
					var pay_way = $(this).attr('data-way');
					$('a[data-name="confirm_payway_modal_btn_'+this.id+'"]').attr('data-way', pay_way);
					if(pay_way == 'ddpay'){
						$('strong[data-name="confirm_payway_modal_title_'+this.id+'"]').html('Use DDPay to Pay Off this invoice?');
						$('p[data-name="confirm_payway_modal_content_'+this.id+'"]').html('This operation will assign invoice\'s balance to zero.<br/><br/>And this operation will store your identity and manipulate time into database as a record.<br/>');
						$('a[data-name="confirm_payway_modal_btn_'+this.id+'"]').html('Confirm to use DDPay to pay off this invoice');
					} else if(pay_way == 'a2a'){
						$('strong[data-name="confirm_payway_modal_title_'+this.id+'"]').html('Use A2A to Pay Off this invoice?');
						$('p[data-name="confirm_payway_modal_content_'+this.id+'"]').html('This operation will assign invoice\'s balance to zero.<br/><br/>And this operation will store your identity and manipulate time into database as a record.<br/>');
						$('a[data-name="confirm_payway_modal_btn_'+this.id+'"]').html('Confirm to use A2A to pay off this invoice');
					} else if(pay_way == 'cash'){
						$('strong[data-name="confirm_payway_modal_title_'+this.id+'"]').html('Use Cash to Pay (Off/Not Off) this invoice?');
						$('p[data-name="confirm_payway_modal_content_'+this.id+'"]').html('This operation will assign invoice\'s balance to (balance - your input amount).<br/><br/>And this operation will store your identity and manipulate time into database as a record.<br/>');
						$('a[data-name="confirm_payway_modal_btn_'+this.id+'"]').html('Confirm to use Cash to pay (off) this invoice');
						$('div[data-name="cash_defray_amount_input_'+this.id+'"]').css('display','');
					}else if(pay_way == 'credit-card'){
						$('strong[data-name="confirm_payway_modal_title_'+this.id+'"]').html('Use Credit Card to Pay Off this invoice?');
						$('p[data-name="confirm_payway_modal_content_'+this.id+'"]').html('This operation will assign invoice\'s balance to zero.<br/><br/>And this operation will store your identity and manipulate time into database as a record.<br/>');
						$('a[data-name="confirm_payway_modal_btn_'+this.id+'"]').html('Confirm to use Credit Card to pay off this invoice');
					} else if(pay_way == 'cyberpark-credit'){
						$('strong[data-name="confirm_payway_modal_title_'+this.id+'"]').html('Give a CyberPark Credit for this invoice?');
						$('p[data-name="confirm_payway_modal_content_'+this.id+'"]').html('This operation will assign invoice\'s balance to zero.<br/><br/>And this operation will store your identity and manipulate time into database as a record.<br/>');
						$('a[data-name="confirm_payway_modal_btn_'+this.id+'"]').html('Confirm to give credit');
					} else if(pay_way == 'voucher'){
						$('strong[data-name="confirm_payway_modal_title_'+this.id+'"]').html('Use Voucher to Pay (Off/Not Off) this invoice?');
						$('p[data-name="confirm_payway_modal_content_'+this.id+'"]').html('This operation will assign invoice\'s balance to (balance - vouchar\'s face value).<br/><br/>And this operation will store your identity and manipulate time into database as a record.<br/>');
						$('a[data-name="confirm_payway_modal_btn_'+this.id+'"]').html('Confirm to use Voucher to pay (off) this invoice');
						$('div[data-name="vouchar_pin_number_input_'+this.id+'"]').css('display','');
					} else if(pay_way == 'pending'){
						$('strong[data-name="confirm_payway_modal_title_'+this.id+'"]').html('Use Pending for this invoice?');
						$('p[data-name="confirm_payway_modal_content_'+this.id+'"]').html('This operation will only change the Make Payment button to Pending style.<br/>');
						$('a[data-name="confirm_payway_modal_btn_'+this.id+'"]').html('Confirm to use Pending for this invoice');
					} else if(pay_way == 'void'){
						$('strong[data-name="confirm_payway_modal_title_'+this.id+'"]').html('Use Avoid for this invoice?');
						$('p[data-name="confirm_payway_modal_content_'+this.id+'"]').html('This operation will change Invoice\'s status to void.<br/>');
						$('a[data-name="confirm_payway_modal_btn_'+this.id+'"]').html('Confirm to use Avoid for this invoice');
					} else if(pay_way == 'bad-debit'){
						$('strong[data-name="confirm_payway_modal_title_'+this.id+'"]').html('Use Bad Debit for this invoice?');
						$('p[data-name="confirm_payway_modal_content_'+this.id+'"]').html('This operation will change Invoice\'s status to bad-debit.<br/>');
						$('a[data-name="confirm_payway_modal_btn_'+this.id+'"]').html('Confirm to use Bad Debit for this invoice');
					} 
					$('button[data-name="make_payment_'+this.id+'"]').button('loading');
					$('#confirmPayWayModal_'+this.id).modal('show');
				});
				// Confirm PayWay
				$('a[data-name="confirm_payway_modal_btn_'+invoice.id+'"]').click(function(){
					var pay_way = $(this).attr('data-way');
					var url = '';
					if(pay_way == 'ddpay'){
						var data = {
								invoice_id : this.id
								,process_way : 'DDPay'
								,pay_way : pay_way
						};
						url = '${ctx}/broadband-user/crm/customer/invoice/defray/ddpay_a2a_credit-card_cyberpark-credit';
					} else if(pay_way == 'a2a'){
						var data = {
								invoice_id : this.id
								,process_way : 'Account2Account'
								,pay_way : pay_way
						};
						url = '${ctx}/broadband-user/crm/customer/invoice/defray/ddpay_a2a_credit-card_cyberpark-credit';
					} else if(pay_way == 'credit-card'){
						var data = {
								invoice_id : this.id
								,process_way : 'Credit Card'
								,pay_way : pay_way
						};
						url = '${ctx}/broadband-user/crm/customer/invoice/defray/ddpay_a2a_credit-card_cyberpark-credit';
					} else if(pay_way == 'cyberpark-credit'){
						var data = {
								invoice_id : this.id
								,process_way : 'CyberPark Credit'
								,pay_way : pay_way
						};
						url = '${ctx}/broadband-user/crm/customer/invoice/defray/ddpay_a2a_credit-card_cyberpark-credit';
					} else if(pay_way == 'cash'){
						var data = {
							invoice_id : this.id
							,eliminate_amount : $('input[name="defray_amount_'+this.id+'"]').val()
						};
						url = '${ctx}/broadband-user/crm/customer/invoice/defray/cash';
					} else if(pay_way == 'voucher'){
						var data = {
							invoice_id : this.id
							,pin_number : $('input[name="pin_number_'+this.id+'"]').val()
						};
						url = '${ctx}/broadband-user/crm/customer/invoice/defray/voucher';
					} else if(pay_way == 'pending'){
						var data = {
								invoice_id : this.id
						};
						url = '${ctx}/broadband-user/crm/customer/invoice/change-payment-status';
					} else if(pay_way == 'void'){
						var data = {
								invoice_id : this.id,
								status : pay_way
						};
						url = '${ctx}/broadband-user/crm/customer/invoice/change-status';
					} else if(pay_way == 'bad-debit'){
						var data = {
								invoice_id : this.id,
								status : pay_way
						};
						url = '${ctx}/broadband-user/crm/customer/invoice/change-status';
					}
					$.post(url, data, function(json){
						$.jsonValidation(json, 'right');
					}, 'json');
				});
				$('#confirmPayWayModal_'+invoice.id).on('hidden.bs.modal', function(){
					$('button[data-name="make_payment_'+$(this).attr('data-id')+'"]').button('reset');
					$.getInvoicePage(pageNo);
					$.getTxPage(1);
				});
				// END PayWay
			}
			
		}, 'json');
	}
	
	$.getCustomerInfo();
	$.getCustomerOrder();
	$.getInvoicePage(1);
	$.getTxPage(1);
	$.getCcrPage(1);
	
	
})(jQuery);
</script>
<jsp:include page="../footer-end.jsp" />