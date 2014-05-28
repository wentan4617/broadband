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
				<div class="panel-body tab-pane fade in active" id="customer_edit" ></div>
				<div class="panel-body tab-pane fade" id="order_detail" ></div>
				<div class="panel-body tab-pane fade" id="invoice_detail"></div>
				<div class="panel-body tab-pane fade" id="transaction_detail"></div>
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
		var data = {
				'id' : ${customer.id}
		}
		$.get('${ctx}/broadband-user/crm/customer/edit', data, function(map){
			map.customer.ctx = '${ctx}';
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
		}, "json");
	}


	$.getCustomerOrder = function() {
		var data = {
				'id' : ${customer.id}
		}
		$.get('${ctx}/broadband-user/crm/customer/edit', data, function(map){
			map.ctx = '${ctx}';
	   		var $table = $('#order_detail');
			$table.html(tmpl('customer_order_table_tmpl', map));

			var co = map.customer.customerOrders;
			for(var i=0; i<co.length; i++){
				/*
				 *	BEGIN customer order area
				 */
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
					var data = {
							'id':this.id
							,'order_status':orderStatus.val()
					};
					var order_status = $('#'+this.id+'_order_status');
					$.post('${ctx}/broadband-user/crm/customer/order/status/edit', data, function(json){
						// rewrite order status's content
						order_status.html(json.model.order_status);
						order_status.attr('data-val',json.model.order_status);
					}, "json")
				});
				// Reset button when hidden order status dialog
				$('#editOrderStatusModal_'+co[i].id).on('hidden.bs.modal', function (e) {
					$('a[data-name="'+$(this).attr('data-id')+'_order_status_edit"]').button('reset');
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
						// rewrite order due date's content
						order_due_date.html(json.model.order_due_str);
						order_due_date.attr('data-val',json.model.order_due_str);
					}, "json").always(function () {
						$btn.button('reset');
				    });
				});
				// Reset button when hidden order due date dialog
				$('#editOrderDueDateModal_'+co[i].id).on('hidden.bs.modal', function (e) {
					$('a[data-name="'+$(this).attr('data-id')+'_order_due_input_btn"]').button('reset');
				});
				
				$('a[data-name="generateApplicationUrl_'+co[i].id+'"]').click(function(){
					
					var url = $(this).attr('data-url');
					$.post(url, function(){

						// If successful, then prompt notice
						alert('Generate New Order Application Successfully!');
						
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
						// rewrite belongs to's content
						order_belongs_to.html(json.model);
					}, "json")
				});
				// Reset button when hidden belongs to dialog
				$('#editOrderBelongsToModal_'+co[i].id).on('hidden.bs.modal', function (e) {
					$('a[data-name="'+$(this).attr('data-id')+'_order_belongs_to_edit"]').button('reset');
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
						// rewrite optional request's content
						if(json.model.optional_request != ''){
							order_optional_request.css('display', '');
							$('p[data-name="'+json.model.id+'_order_optional_request_p"]').html(json.model.optional_request);
						}
					}, "json")
				});
				// Reset button when hidden optional request dialog
				$('#editOrderOptionalRequestModal_'+co[i].id).on('hidden.bs.modal', function (e) {
					$('a[data-name="'+$(this).attr('data-id')+'_optional_request_edit"]').button('reset');
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
					var order_pppoe_loginname_input = $('input[data-name="'+this.id+'_pppoe_loginname"]');
					var order_pppoe_password_input = $('input[data-name="'+this.id+'_pppoe_password"]');
					var data = {
							 'id':this.id
							,'pppoe_loginname':order_pppoe_loginname_input.val()+' '
							,'pppoe_password':order_pppoe_password_input.val()+' '
					};
					var order_pppoe_loginname = $('#'+this.id+'_pppoe_loginname');
					var order_pppoe_password = $('#'+this.id+'_pppoe_password');
					$.post('${ctx}/broadband-user/crm/customer/order/ppppoe/edit', data, function(json){
						if(!$.jsonValidation(json, 'left')){
							// rewrite order PPPoE's content
							order_pppoe_loginname.html(json.model.pppoe_loginname);
							order_pppoe_password.html(json.model.pppoe_password);
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
							,'cvlan':cvlan_input+' '
							,'svlan':svlan_input+' '
							,'rfs_date_str':rfs_date_input
							,'way':$(this).attr('data-way')
					};
					var cvlan = $('#'+this.id+'_cvlan');
					var svlan = $('#'+this.id+'_svlan');
					var rfs_date = $('#'+this.id+'_rfs_date');
					$.post('${ctx}/broadband-user/crm/customer/order/save/svcvlanrfsdate', data, function(json){
						if(!$.jsonValidation(json, 'left')){
							// rewrite order SV/CVLan & RFS Date's content
							cvlan.html(json.model.cvlan);
							svlan.html(json.model.svlan);
							rfs_date.html(json.model.rfs_date_str);
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
					if(order_status.attr('data-val')=='ordering-paid' || order_status.attr('data-val')=='ordering-pending' || order_status.attr('data-val')=='using'){
						$('a[data-name="service_giving_save_'+this.id+'"]').prop('id', this.id);
						$('a[data-name="service_giving_save_'+this.id+'"]').attr('data-way', $(this).attr('data-way'));
						$('#saveServiceGivingModal_'+this.id).modal('show');	// click Save Order then performing Ajax action
					// else show denied modal
					} else {
						$('#saveServiceGivingDeniedModal_'+this.id).modal('show');
					}
				});
				// Submit to rest controller
				$('a[data-name="service_giving_save_'+co[i].id+'"]').click(function(){
					var order_using_start_input = $('input[data-name="'+this.id+'_order_using_start_input_picker"]').val();
					var order_status = $('#'+this.id+'_order_status');
					var order_type = $('#'+this.id+'_order_type');
					var order_detail_unit_attr = $('#'+this.id+'_order_detail_unit').attr('data-val');
					var data = {
							'customer_id':'${customer.id}'
							,'id':this.id
							,'order_using_start_str':order_using_start_input
							,'order_detail_unit':(order_detail_unit_attr==null?1:order_detail_unit_attr)
							,'order_status':order_status.attr('data-val')
							,'order_type':order_type.attr('data-val')
							,'way':$(this).attr('data-way')
					};
					var order_using_start = $('#'+this.id+'_order_using_start');
					var order_next_invoice_create_date = $('#'+this.id+'_next_invoice_create_date');
					$.post('${ctx}/broadband-user/crm/customer/order/service_giving_date', data, function(json){
						// rewrite order Service Giving Date's content
						order_status.html(json.model.order_status);
						order_using_start.html(json.model.order_using_start_str);
						order_next_invoice_create_date.html(json.model.next_invoice_create_date_str);
						
						// reload invoice page one
						$.getInvoicePage(1);
					}, "json");
				});
				// Reset button when hidden order PPPoE dialog
				$('#saveSVCVLanRFSDateModal_'+co[i].id).on('hidden.bs.modal', function (e) {
					console.log($(this).attr('data-id'));
					$('a[data-name="'+$(this).attr('data-id')+'_service_giving_save"]').button('reset');
				});
				// Reset button when hidden order PPPoE dialog
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
						// rewrite optional Broadband ASID's content
						if(!$.jsonValidation(json, 'left')){
							order_broadband_asid.html(json.model.broadband_asid);
						}
					}, "json");
				});
				// Reset button when hidden Broadband ASID dialog
				$('#saveBroadbandASIDModal_'+co[i].id).on('hidden.bs.modal', function (e) {
					$('a[data-name="'+$(this).attr('data-id')+'_brodband_asid_btn"]').button('reset');
				});
				/*
				 *	END customer order Broadband ASID area
				 */
				 

				/*
				 *	BEGIN customer order detail(s) area
				 */
				$('a[data-name="'+co[i].id+'_update_pstn"]').click(function(){
					$btn = $(this); $btn.button('loading');
					$('#updatePSTNModal_'+this.id).attr('data-detail-id', $(this).attr('data-id'));
					$('#updatePSTNModal_'+this.id).modal('show');
				});
				$('a[data-name="updatePSTNModalBtn_'+co[i].id+'"]').on('click', function(){
					$('#updatePSTNModal_'+this.id).attr('type', 'submit');
				});
				// Reset button when hidden order pstn dialog
				$('#updatePSTNModal_'+co[i].id+'').on('hidden.bs.modal', function (e) {
					if($(this).attr('type') == 'submit'){
						this.id = $(this).attr('data-id');
						var order_detail_id = $(this).attr('data-detail-id');
						var pstn_number = $('input[data-name="pstn_number_'+this.id+'"]').val();
						var data = {
								'order_detail_id':order_detail_id
								,'customer_id':${customer.id}
								,'pstn_number':pstn_number
						};
						$.post('${ctx}/broadband-user/crm/customer/order/pstn/edit', data, function(json){
							$.getCustomerOrder();
						}, "json");
					}
					$('a[data-name="'+$(this).attr('data-id')+'_update_pstn"]').button('reset');
				});

				
				/*
				 *	BEGIN Remove discount
				 */
				$('a[data-name="'+co[i].id+'_remove_discount"]').click(function(){
					$btn = $(this); $btn.button('loading');
					$('#removeDiscountModal_'+this.id).attr('data-detail-id', $(this).attr('data-id'));
					$('#removeDiscountModal_'+this.id).modal('show');
				});
				$('a[data-name="removeDiscountModalBtn_'+co[i].id+'"]').on('click', function(){
					$('#removeDiscountModal_'+this.id).attr('type', 'submit');
				});
				// Submit to rest controller
				$('#removeDiscountModal_'+co[i].id).on('hidden.bs.modal', function (e) {
					if($(this).attr('type') == 'submit'){
						this.id = $(this).attr('data-id');
						var order_detail_id = $(this).attr('data-detail-id');
						var data = {
								'order_detail_id':order_detail_id
								,'customer_id':${customer.id}
						};
						$.post('${ctx}/broadband-user/crm/customer/order/discount/remove', data, function(json){
							$.getCustomerOrder();
						}, "json");
					}
					// Reset button when hidden order remove discount dialog
					$('a[data-name="'+$(this).attr('data-id')+'_remove_discount"]').button('reset');
				});
				/*
				 *	END Remove discount
				 */
				
				 
				/*
				 *	BEGIN Add discount
				 */
				$('a[data-name="'+co[i].id+'_add_discount"]').click(function(){
					$btn = $(this); $btn.button('loading');
					$('#addDiscountModal_'+this.id).modal('show');
				});
				$('a[data-name="addDiscountModalBtn_'+co[i].id+'"]').on('click', function(){
					$('#addDiscountModal_'+this.id).attr('type', 'submit');
				});
				// Submit to rest controller
				$('#addDiscountModal_'+co[i].id).on('hidden.bs.modal', function (e) {
					if($(this).attr('type') == 'submit'){
						this.id = $(this).attr('data-id');
						var detail_name = $('input[data-name="'+this.id+'_detail_name"]');
						var detail_price = $('input[data-name="'+this.id+'_detail_price"]');
						var detail_unit = $('input[data-name="'+this.id+'_detail_unit"]');
						var detail_expired = $('input[data-name="'+this.id+'_detail_expired"]');
						var detail_type = $('select[data-name="'+this.id+'_detail_type"]');
						var data = {
								'order_id':this.id
								,'customer_id':${customer.id}
								,'detail_name':detail_name.val()+''
								,'detail_price':detail_price.val()+''
								,'detail_unit':detail_unit.val()+''
								,'detail_expired':detail_expired.val()+''
								,'detail_type':detail_type.val()+''
						};
						$.post('${ctx}/broadband-user/crm/customer/order/discount/save', data, function(json){
							$.getCustomerOrder();
						}, "json");
					}
					// Reset button when hidden order add discount dialog
					$('a[data-name="'+$(this).attr('data-id')+'_add_discount"]').button('reset');
				});
				/*
				 *	END Add discount
				 */
				/*
				 *	END customer order detail(s) area
				 */
				 
				 
				/*
				 *	BEGIN Datepicker area
				 */
				// Order due Datepicker
				var order_due_input = $('input[data-name="'+co[i].id+'_order_due_input_picker"]').attr('data-val');
				$('#'+co[i].id+'_order_due_datepicker').datepicker({
				    format: "yyyy-mm-dd",
				    autoclose: true,
				    todayHighlight: true
				    // if order due date is null then assign new Date(), else assign order due date
				}).datepicker('setDate', order_due_input || new Date());
				
				// Order RFS date Datepicker
				var rfs_date_input = $('input[data-name="'+co[i].id+'_rfs_date_picker"]').attr('data-val');
				$('#'+co[i].id+'_rfs_date_datepicker').datepicker({
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
				$.getInvoicePage($(this).attr('data-pageNo'));
			});
		}, "json");
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
				$('a[data-name="pay_by_ddpay_'+invoice.id+'"]').click(function(){
					$('button[data-name="make_payment_'+this.id+'"]').button('loading');
					$('#confirmDDPayModal_'+this.id).modal('show');
				});
				$('a[data-name="confirm_ddpay_modal_btn_'+invoice.id+'"]').click(function(){
					var data = {
							invoice_id : this.id
					};
					
					$.post('${ctx}/broadband-user/crm/customer/invoice/defray/ddpay', data, function(json){
						if (json.hasErrors) {
							$.jsonValidation(json, 'right');
						} else {
							$.getInvoicePage(pageNo);
						}
					}, 'json');
				});
				$('#confirmDDPayModal_'+invoice.id).on('hidden.bs.modal', function(){
					$('button[data-name="make_payment_'+$(this).attr('data-id')+'"]').button('reset');
				});
				// END DDPay
				
				// BEGIN Cash
				// Confirm Cash
				$('a[data-name="pay_by_cash_'+invoice.id+'"]').click(function(){
					$('button[data-name="make_payment_'+this.id+'"]').button('loading');
					$('#confirmCashModal_'+this.id).modal('show');
				});
				$('a[data-name="confirm_cash_modal_btn_'+invoice.id+'"]').click(function(){
					var data = {
							invoice_id : this.id,
							eliminate_amount : $('input[name="defray_amount_'+this.id+'"]').val()
					};
					
					$.post('${ctx}/broadband-user/crm/customer/invoice/defray/cash', data, function(json){
						if (json.hasErrors) {
							$.jsonValidation(json, 'right');
						} else {
							$.getInvoicePage(pageNo);
						}
					}, 'json');
				});
				$('#confirmCashModal_'+invoice.id).on('hidden.bs.modal', function(){
					$('button[data-name="make_payment_'+$(this).attr('data-id')+'"]').button('reset');
				});
				// END Cash
				
			}
			
			
		}, 'json');
	}
	
	$.getCustomerInfo();
	$.getCustomerOrder();
	$.getInvoicePage(1);
	$.getTxPage(1);
	
	
})(jQuery);
</script>
<jsp:include page="../footer-end.jsp" />