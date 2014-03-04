<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<script type="text/javascript">
(function($){

	$.getTxPage = function(pageNo) {
		
		$.get('${ctx}/broadband-user/crm/transaction/view/' + pageNo +'/'+ ${customer.id}, callbackPage, "json");
		
		function callbackPage(page){
			var html = "";
			if (page.results != null && page.results.length > 0) {
				html += '<table class="table">';
				html += '<thead>';
				html += '<tr>';
				html += '<th>Transaction Date</th>';
				html += '<th>Amount</th>';
				html += '<th>&nbsp;</th>';
				html += '</tr>';
				html += '</thead>';
				html += '<tbody>';
				for (var i = 0, len = page.results.length; i < len; i++) {
					var tx = page.results[i];
					html += '<tr>';
					html += '<td>' + tx.transaction_date_str + '</td>';
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
		
		function callbackPage(page){
			var html = "";
			if (page.results != null && page.results.length > 0) {
				html += '<table class="table">';
				html += '<thead>';
				html += '<tr>';
				html += '<th>Invoice ID</th>';
				html += '<th>Create Date</th>';
				html += '<th>Due Date</th>';
				html += '<th>Amount Payable</th>';
				html += '<th>Amount Paid</th>';
				html += '<th>Balance</th>';
				html += '<th>Status</th>';
				html += '<th>&nbsp;</th>';
				html += '</tr>';
				html += '</thead>';
				html += '<tbody>';
				for (var i = 0, len = page.results.length; i < len; i++) {
					var invoice = page.results[i];
					html += '<tr>';
					html += '<td>' + invoice.id + '</td>';
					html += '<td>' + invoice.create_date_str + '</td>';
					html += '<td>' + invoice.due_date_str + '</td>';
					html += '<td>' + invoice.amount_payable + '</td>';
					html += '<td>' + invoice.amount_paid + '</td>';
					html += '<td>' + invoice.balance + '</td>';
					html += '<td>' + invoice.status + '</td>';
					html += '<td><a target="_blank" href="${ctx}/broadband-user/crm/customer/order/download/' + invoice.id + '"><span class="glyphicon glyphicon-floppy-disk"></span></a></td>';
					html += '</tr>';
				}
				html += '</tbody>';
				html += '<tfoot>';
				html += '<tr>';
				html += '<td colspan="11">';
				html += '<ul class="pagination">';
				for (var i = 1, len = page.totalPage; i <= len; i++) {
					html += '<li class="' + (page.pageNo == i ? 'active' : '') + '">';
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
		
		var data = {
				'customer_id':'${customer.id}'
				,'order_id':this.id
				,'cvlan_input':cvlan_input
				,'svlan_input':svlan_input
				,'order_using_start_input':order_using_start_input
				,'order_total_price':order_total_price
				,'order_detail_unit':order_detail_unit_attr
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
			order_using_start.html(order_using_start_input);
			order_next_invoice_create_date.html(order.next_invoice_create_date_str);
		}, "json");
	});
	
	$.getInvoicePage(1);
	$.getTxPage(1);
	
	
	
})(jQuery);
</script>
<jsp:include page="../footer-end.jsp" />