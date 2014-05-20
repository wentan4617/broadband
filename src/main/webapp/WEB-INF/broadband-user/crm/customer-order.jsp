<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<style>
#order_detail .form-group {
	margin-bottom:4px;
}
</style>

<!-- orders -->
<c:forEach var="customerOrder" items="${customer.customerOrders }">
	<form class="form-horizontal">
	
		<h4 class="text-success">Order Information</h4>
		<hr/>
		
		<div class="row">
			<div class="col-md-6">
				<div class="form-group">
					<label for="order_id" class="control-label col-md-6">Order ID</label>
					<div class="col-md-6">
						<p class="form-control-static">${customerOrder.id}</p>
					</div>
				</div>
				<div class="form-group">
					<label for="order_create_date" class="control-label col-md-6">Order Create Date</label>
					<div class="col-md-6">
						<p class="form-control-static">
							<fmt:formatDate  value="${customerOrder.order_create_date}" type="date" pattern="yyyy-MM-dd"/>
						</p>
					</div>
				</div>
				<div class="form-group">
					<label for="order_status" class="control-label col-md-6">Order Status</label>
					<div class="col-md-6">
						<p id="${customerOrder.id}_order_status" data-val="${customerOrder.order_status}" class="form-control-static">
							${customerOrder.order_status}
						</p>
					</div>
				</div>
				<div class="form-group">
					<label for="order_type" class="control-label col-md-6">Order Type</label>
					<div class="col-md-6">
						<p id="${customerOrder.id}_order_type" data-val="${customerOrder.order_type}" class="form-control-static">
							${customerOrder.order_type}
						</p>
					</div>
				</div>
				<div class="form-group">
					<label for="order_broadband_type" class="control-label col-md-6">Order Broadband Type</label>
					<div class="col-md-6">
						<p class="form-control-static">${customerOrder.order_broadband_type}</p>
					</div>
				</div>
				<div class="form-group">
					<label for="order_total_price" class="control-label col-md-6">Order Total Price</label>
					<div class="col-md-6">
						<p id="${customerOrder.id}_order_total_price" data-val="${customerOrder.order_total_price}" class="form-control-static ">
							$ <fmt:formatNumber value="${customerOrder.order_total_price}" type="number" pattern="#,##0.00" />
						</p>
					</div>
				</div>
				<div class="form-group">
					<label for="order_due" class="control-label col-md-6">Order Due</label>
					<div class="col-md-6">
						<p id="${customerOrder.id}_order_due" data-val="${customerOrder.order_due_str}" class="form-control-static ">
							${customerOrder.order_due_str}
						</p>
					</div>
				</div>
				<div class="form-group">
					<label for="pstn_count" class="control-label col-md-6">PSTN Amount</label>
					<div class="col-md-6">
						<p id="${customerOrder.id}_pstn_count" data-val="${customerOrder.pstn_count}" class="form-control-static ">
							${customerOrder.pstn_count}
						</p>
					</div>
				</div>
				<div class="form-group">
					<label for="pstn_rental_amount" class="control-label col-md-6">PSTN Rental Amount</label>
					<div class="col-md-6">
						<p id="${customerOrder.id}_pstn_rental_amount" data-val="${customerOrder.pstn_rental_amount}" class="form-control-static ">
							${customerOrder.pstn_rental_amount}
						</p>
					</div>
				</div>
				<div class="form-group">
					<label for="term_period" class="control-label col-md-6">Term Period</label>
					<div class="col-md-6">
						<p id="${customerOrder.id}_term_period" data-val="${customerOrder.term_period}" class="form-control-static ">
							${customerOrder.term_period}
						</p>
					</div>
				</div>
				<div class="form-group">
					<label for="transition_provider_name" class="control-label col-md-6">Transition Provider Name</label>
					<div class="col-md-6">
						<p id="${customerOrder.id}_transition_provider_name" class="form-control-static">
							${customerOrder.transition_provider_name}
						</p>
					</div>
				</div>
				<div class="form-group">
					<label for="transition_account_holder_name" class="control-label col-md-6">Transition Account Holder</label>
					<div class="col-md-6">
						<p id="${customerOrder.id}_transition_account_holder_name" class="form-control-static">
							${customerOrder.transition_account_holder_name}
						</p>
					</div>
				</div>
				<div class="form-group">
					<label for="transition_account_number" class="control-label col-md-6">Transition Account Number</label>
					<div class="col-md-6">
						<p id="${customerOrder.id}_transition_account_number" class="form-control-static">
							${customerOrder.transition_account_number}
						</p>
					</div>
				</div>
				<div class="form-group">
					<label for="transition_porting_number" class="control-label col-md-6">Transition Telephone Number</label>
					<div class="col-md-6">
						<p id="${customerOrder.id}_transition_porting_number" class="form-control-static">
							${customerOrder.transition_porting_number}
						</p>
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-md-6">Download Application Form</label>
					<div class="col-md-6">
						<c:if test="${customerOrder.order_pdf_path!=null}">
							<a target="_blank" href="${ctx}/broadband-user/crm/customer/order/pdf/download/${customerOrder.id}" class="glyphicon glyphicon-floppy-save btn-lg"></a>
						</c:if>
						<c:if test="${customerOrder.order_pdf_path==null}">
  							<p class="form-control-static">Empty</p>
						</c:if>
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-md-6">Download Credit Card Rquest</label>
					<div class="col-md-6">
						<c:if test="${customerOrder.credit_pdf_path!=null}">
							<a target="_blank" href="${ctx}/broadband-user/crm/customer/order/credit/pdf/download/${customerOrder.id}" class="glyphicon glyphicon-floppy-save btn-lg"></a>
						</c:if>
						<c:if test="${customerOrder.credit_pdf_path==null}">
  							<p class="form-control-static">Empty</p>
						</c:if>
					</div>
				</div>
			</div>
			<div class="col-md-6">
				<div class="form-group">
					<label class="control-label col-md-6">&nbsp;</label>
					<div class="col-md-6">&nbsp;</div>
				</div>
				<div class="form-group">
					<label class="control-label col-md-6">&nbsp;</label>
					<div class="col-md-6">&nbsp;</div>
				</div>
				<div class="form-group">
					<label class="control-label col-md-6">&nbsp;</label>
					<div class="col-md-6">
						<select data-name="${customerOrder.id}_order_status_selector" data-val="${customerOrder.order_status}" class="form-control input-sm">
							<c:forEach var="status" items="paid,pending,ordering-paid,ordering-pending,using,cancel,discard">
								<option value="${status}"
									<c:if test="${customerOrder.order_status == status}">
										selected="selected"
									</c:if>
								>${status}</option>
							</c:forEach>
						</select>
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-md-6">&nbsp;</label>
					<div class="col-md-6">&nbsp;</div>
				</div>
				<div class="form-group">
					<label class="control-label col-md-6">&nbsp;</label>
					<div class="col-md-6">&nbsp;</div>
				</div>
				<div class="form-group">
					<label class="control-label col-md-6">&nbsp;</label>
					<div class="col-md-6">&nbsp;</div>
				</div>
				<div class="form-group">
					<label class="control-label col-md-6">&nbsp;</label>
					<div class="col-md-6">
						<div class="input-group date" id="${customerOrder.id}_order_due_datepicker">
							<strong><input data-val="${customerOrder.order_due_str}" data-name="${customerOrder.id}_order_due_input" class="form-control input-sm" placeholder="Order Due Date"/></strong>
							<span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
						</div>
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-md-6">&nbsp;</label>
					<div class="col-md-6">&nbsp;</div>
				</div>
				<div class="form-group">
					<label class="control-label col-md-6">&nbsp;</label>
					<div class="col-md-6">&nbsp;</div>
				</div>
				<div class="form-group">
					<label class="control-label col-md-6">&nbsp;</label>
					<div class="col-md-6">&nbsp;</div>
				</div>
				<div class="form-group">
					<label class="control-label col-md-6">&nbsp;</label>
					<div class="col-md-6">&nbsp;</div>
				</div>
				<div class="form-group">
					<label class="control-label col-md-6">&nbsp;</label>
					<div class="col-md-6">&nbsp;</div>
				</div>
				<div class="form-group">
					<label class="control-label col-md-6">&nbsp;</label>
					<div class="col-md-6">&nbsp;</div>
				</div>
				<div class="form-group">
					<label class="control-label col-md-6">&nbsp;</label>
					<div class="col-md-6">&nbsp;</div>
				</div>
				<div class="form-group">
					<label class="control-label col-md-6">&nbsp;</label>
					<div class="col-md-6">&nbsp;</div>
				</div>
				<div class="form-group">
					<label class="control-label col-md-6">&nbsp;</label>
					<div class="col-md-6">&nbsp;</div>
				</div>
			</div>
		</div>
		<hr/>
		<div class="row">
			<div class="col-md-10"></div>
			<div class="col-md-2">
				<a id="${customerOrder.id}" class="btn btn-success btn-lg btn-block" data-name="${customerOrder.id}_order_info_edit" >Update Order</a>
			</div>
		</div>
		
		
		<!-- pppoe -->
		<hr />
		<h4 class="text-success">PPPoE Information</h4>
		<hr />
		<div class="row">
			<div class="col-md-6">
				<div class="form-group">
					<label for="pppoe_loginname" class="control-label col-md-6">PPPoE Login Name</label>
					<div class="col-md-6">
						<p id="${customerOrder.id}_pppoe_loginname" class="form-control-static">${customerOrder.pppoe_loginname}</p>
					</div>
				</div>
				<div class="form-group">
					<label for="pppoe_password" class="control-label col-md-6">PPPoE Password</label>
					<div class="col-md-6">
						<p id="${customerOrder.id}_pppoe_password" class="form-control-static">${customerOrder.pppoe_password}</p>
					</div>
				</div>
			</div>
			<div class="col-md-6">
				<div class="form-group">
					<label for="pppoe_loginname" class="control-label col-md-6"><!-- PPPoE Login Name --></label>
					<div class="col-md-6">
						<input id="${customerOrder.id}_pppoe_loginname_input" class="form-control input-sm" placeholder="PPPoE Login Name"/>
					</div>
				</div>
				<div class="form-group">
					<label for="pppoe_password" class="control-label col-md-6"><!-- PPPoE Password --></label>
					<div class="col-md-6">
						<input id="${customerOrder.id}_pppoe_password_input" class="form-control input-sm" placeholder="PPPoE Password"/>
					</div>
				</div>
				
			</div>
		</div>
		<hr />
		<div class="row">
			<div class="col-md-10"></div>
			<div class="col-md-2">
				<c:if test="${customerOrder.pppoe_loginname!=null}">
					<a data-val="${customerOrder.id}" class="btn btn-danger btn-lg btn-block" data-name="${customerOrder.id}_pppoe_edit">Update PPPoE</a>
				</c:if>
				<c:if test="${customerOrder.pppoe_loginname==null}">
					<a data-val="${customerOrder.id}" class="btn btn-success btn-lg btn-block" data-name="${customerOrder.id}_pppoe_save">Save PPPoE</a>
				</c:if>
				<a data-val="${customerOrder.id}" class="btn btn-danger btn-lg btn-block" data-name="${customerOrder.id}_pppoe_edit" style="display: none;" >Update PPPoE</a>
			</div>
		</div>
		
		<!-- SV/CVLan Information -->
		<hr />
		<h4 class="text-success">SV/CVLan Information</h4>
		<hr />
		<div class="row">
			<div class="col-md-6">
				<div class="form-group">
					<label for="svlan" class="control-label col-md-6">SVLan</label>
					<div class="col-md-6">
						<p id="${customerOrder.id}_svlan" class="form-control-static">${customerOrder.svlan}</p>
					</div>
				</div>
				<div class="form-group">
					<label for="cvlan" class="control-label col-md-6">CVLan</label>
					<div class="col-md-6">
						<p id="${customerOrder.id}_cvlan" class="form-control-static">${customerOrder.cvlan}</p>
					</div>
				</div>
				<div class="form-group">
					<label for="order_using_start" class="control-label col-md-6">Service Giving Date</label>
					<div class="col-md-6">
						<p id="${customerOrder.id}_order_using_start" class="form-control-static">
							${customerOrder.order_using_start_str}
						</p>
					</div>
				</div>
				<div class="form-group">
					<label for="next_invoice_create_date" class="control-label col-md-6">Next Invoice Create Date</label>
					<div class="col-md-6">
						<p id="${customerOrder.id}_next_invoice_create_date" class="form-control-static">
							${customerOrder.next_invoice_create_date_str}	
						</p>
					</div>
				</div>
			</div>
			<div class="col-md-6">
				
				<div class="form-group">
					<label class="control-label col-md-6"><!-- SVLan --></label>
					<div class="col-md-6">
						<input id="${customerOrder.id}_svlan_input" class="form-control input-sm" placeholder="SVLan" />
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-md-6"><!-- CVLan --></label>
					<div class="col-md-6">
						<input id="${customerOrder.id}_cvlan_input" class="form-control input-sm" placeholder="CVLan" />
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-md-6"><!-- Service Giving Date --></label>
					<div class="col-md-6">
						<div class="input-group date" id="${customerOrder.id}_order_using_start_datepicker">
							<input id="${customerOrder.id}" data-val="${customerOrder.order_using_start_str}" data-name="${customerOrder.id}_order_using_start_input" class="form-control input-sm" placeholder="Order Using Start Date"/>
							<span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
						</div>
					</div>
				</div>
				<div class="form-group">
					<div class="col-md-6">&nbsp;</div>
				</div>
			</div>
		</div>
		
		<hr />
		<div class="row">
			<div class="col-md-10"></div>
			<div class="col-md-2">
				<c:if test="${customerOrder.order_using_start!=null}">
					<a data-val="${customerOrder.id}" class="btn btn-danger btn-lg btn-block" data-name="${customerOrder.id}_svcvlan_edit">Update SV/CVLan</a>
				</c:if>
				<c:if test="${customerOrder.order_using_start==null}">
					<a data-val="${customerOrder.id}" class="btn btn-success btn-lg btn-block" data-name="${customerOrder.id}_svcvlan_save">Save SV/CVLan</a>
				</c:if>
				<a data-val="${customerOrder.id}" class="btn btn-danger btn-lg btn-block" data-name="${customerOrder.id}_svcvlan_edit" style="display: none;">Update SV/CVLan</a>
			</div>
		</div>




		
		<!-- order details -->
		<hr />
		<table class="table" style="font-size:12px;">
			<thead>
				<tr>
					<th>Detail Name</th>
					<th>Detail Type</th>
					<th>Plan Type</th>
					<th>Plan Sort</th>
					<th>Plan Status</th>
					<th>Data Flow</th>
					<th>Detail Price</th>
					<th>Unit</th>
					<th>Detail Expired</th>
					<th>&nbsp;</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="customerOrderDetail" items="${customerOrder.customerOrderDetails}">
					<tr class="${customerOrderDetail.detail_type=='discount' ? 'alert alert-warning':'' }">
						<td>
							${customerOrderDetail.detail_name}
							<c:if test="${customerOrderDetail.detail_type=='pstn' || customerOrderDetail.detail_type=='voip'}">
								<br/>
								(<c:if test="${customerOrderDetail.pstn_number==''}">
									<strong class='text-warning'>Number is Empty</strong>
								</c:if>
								<c:if test="${customerOrderDetail.pstn_number!=''}">
									<strong class='text-success'>${customerOrderDetail.pstn_number}</strong>
								</c:if>)
							</c:if>
						</td>
						<td>${customerOrderDetail.detail_type}</td>
						<td>${customerOrderDetail.detail_plan_type}</td>
						<td>${customerOrderDetail.detail_plan_sort}</td>
						<td><strong>${customerOrderDetail.detail_plan_status}</strong></td>
						<td>${customerOrderDetail.detail_data_flow}</td>
						<td><strong><fmt:formatNumber value="${customerOrderDetail.detail_price}" type="number" pattern="#,##0.00" /></strong></td>
						<td 
							<c:if test="${fn:contains(customerOrderDetail.detail_type, 'plan-')}">
							id="${customerOrder.id}_order_detail_unit" data-val="${customerOrderDetail.detail_unit}"
							</c:if>
						>${customerOrderDetail.detail_unit}</td>
						<td><strong><fmt:formatDate  value="${customerOrderDetail.detail_expired}" type="both" pattern="yyyy-MM-dd" /></strong></td>
						<td>&nbsp;
							<c:if test="${customerOrderDetail.detail_type=='discount'}">
								<a class="btn btn-danger btn-xs pull-right" data-name="${customerOrder.id}_remove_discount" data-val="${customerOrderDetail.id}" data-toggle="modal" data-target="#removeDiscountModal">
								  	<span data-toggle="tooltip" data-placement="bottom" data-original-title="delete this discount" class="glyphicon glyphicon-trash"></span> 
								</a>
							</c:if>
							<c:if test="${customerOrderDetail.detail_type=='pstn'}">
								<a class="btn btn-success btn-xs pull-right" data-name="${customerOrder.id}_update_pstn" data-val="${customerOrderDetail.id}" data-toggle="modal" data-target="#updatePSTNModal" style="width:100px;">
								  	<span class="glyphicon glyphicon-edit"></span> PSTN
								</a>
							</c:if>
							<c:if test="${customerOrderDetail.detail_type=='voip'}">
								<a class="btn btn-success btn-xs pull-right" data-name="${customerOrder.id}_update_pstn" data-val="${customerOrderDetail.id}" data-toggle="modal" data-target="#updatePSTNModal" style="width:100px;">
								  	<span class="glyphicon glyphicon-edit"></span> VoIP
								</a>
							</c:if>
						</td>
					</tr>
				</c:forEach>
			</tbody>
			<tfoot>
				<tr>
					<td colspan="12">
						<!-- Button trigger modal -->
						<a class="btn btn-success btn-xs pull-right" data-name="${customerOrder.id}_add_discount" data-val="${customerOrder.id}" data-toggle="modal" data-target="#addDiscountModal" style="width:100px;">
						  <span class="glyphicon glyphicon-plus"></span> Discount
						</a>
					</td>
				</tr>
			</tfoot>
		</table>
		
	</form>
</c:forEach>

<!-- Edit PSTN Modal -->
<form class="form-horizontal"
	action="${ctx }/broadband-user/crm/customer/order/pstn/edit"
	method="post">
	<input type="hidden" name="order_detail_id" /> <input type="hidden"
		name="customer_id" value="${customer.id}" />
	<div class="modal fade" id="updatePSTNModal" tabindex="-1"
		role="dialog" aria-labelledby="updatePSTNModalLabel"
		aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title text-danger" id="updatePSTNModalLabel">
						<strong>Update PSTN Info</strong>
					</h4>
				</div>
				<div class="modal-body">
					<div class="form-group">
						<label class="control-label col-md-4">PSTN Number</label>
						<div class="col-md-6">
							<input name="pstn_number" class="form-control input-sm"
								placeholder="PSTN Number" />
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="submit" class="btn btn-success">Update PSTN</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- /.modal -->
</form>

<!-- Edit Order Info Modal -->
<div class="modal fade" id="editOrderInfoModal" tabindex="-1"
	role="dialog" aria-labelledby="editOrderInfoModalLabel"
	aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h4 class="modal-title text-danger" id="editOrderInfoModalLabel">
					<strong>Update Order Info</strong>
				</h4>
			</div>
			<div class="modal-body">
				<div class="form-group">
					<label class="control-label col-md-8 text-warning">Update
						order's info?</label>
				</div>
			</div>
			<div class="modal-footer">
				<a href="javascript:void(0);" class="btn btn-success"
					data-name="editOrderInfoModalBtn" data-dismiss="modal">Update
					Order</a>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal -->

<!-- Save PPPoE Modal -->
<div class="modal fade" id="savePPPoEModal" tabindex="-1" role="dialog"
	aria-labelledby="savePPPoEModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="savePPPoEModalLabel">
					<strong>Save Related Order's PPPoE Information</strong>
				</h4>
			</div>
			<div class="modal-body">
				<div class="form-group">
					<label class="control-label col-md-8">Save PPPoE Info?</label>
				</div>
			</div>
			<div class="modal-footer">
				<a href="javascript:void(0);" class="btn btn-success"
					data-name="pppoe_save_modal_btn" data-dismiss="modal">Save
					PPPoE</a>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal -->

<!-- Edit PPPoE Modal -->
<div class="modal fade" id="editPPPoEModal" tabindex="-1" role="dialog"
	aria-labelledby="editPPPoEModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="editPPPoEModalLabel">
					<strong>Update Related Order's PPPoE Information</strong>
				</h4>
			</div>
			<div class="modal-body">
				<div class="form-group">
					<label class="control-label col-md-8">Update PPPoE Info?</label>
				</div>
			</div>
			<div class="modal-footer">
				<a href="javascript:void(0);" class="btn btn-success"
					data-name="pppoe_edit_modal_btn" data-dismiss="modal">Update
					PPPoE</a>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal -->

<!-- Save Order Denied Modal -->
<div class="modal fade" id="saveOrderDeniedModal" tabindex="-1"
	role="dialog" aria-labelledby="saveOrderDeniedModalLabel"
	aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="saveOrderDeniedModalLabel">
					<strong>Save Related Order's SV/CVLan Information Denied</strong>
				</h4>
			</div>
			<div class="modal-body">
				<div class="form-group">
					<label class="control-label col-md-8">Couldn't save these
						changes!<br />Please double check this order's status!!!
					</label>
				</div>
			</div>
			<div class="modal-footer">
				<a href="javascript:void(0);" class="btn btn-primary"
					data-dismiss="modal">Close</a>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal -->

<!-- Save Order Modal -->
<div class="modal fade" id="saveOrderModal" tabindex="-1" role="dialog"
	aria-labelledby="saveOrderModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="saveOrderModalLabel">
					<strong>Save Related Order's SV/CVLan Information</strong>
				</h4>
			</div>
			<div class="modal-body">
				<div class="form-group">
					<label class="control-label col-md-8">Save SV/CVLan Info?</label>
				</div>
			</div>
			<div class="modal-footer">
				<a href="javascript:void(0);" class="btn btn-success"
					data-name="svcvlan_save" data-dismiss="modal">Save SV/CVLan</a>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal -->

<!-- Edit Order Modal -->
<div class="modal fade" id="editOrderModal" tabindex="-1" role="dialog"
	aria-labelledby="editOrderModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="editOrderModalLabel">
					<strong>Update Related Order's SV/CVLan Information</strong>
				</h4>
			</div>
			<div class="modal-body">
				<div class="form-group">
					<label class="control-label col-md-8">Update SV/CVLan Info?</label>
				</div>
			</div>
			<div class="modal-footer">
				<a href="javascript:void(0);" class="btn btn-success"
					data-name="svcvlan_edit" data-dismiss="modal">Update SV/CVLan</a>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal -->

<!-- Remove Discount Modal -->
<form class="form-horizontal"
	action="${ctx }/broadband-user/crm/customer/order/discount/remove"
	method="post">
	<input type="hidden" name="order_detail_id" /> <input type="hidden"
		name="customer_id" value="${customer.id}" />
	<div class="modal fade" id="removeDiscountModal" tabindex="-1"
		role="dialog" aria-labelledby="removeDiscountModalLabel"
		aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="removeDiscountModalLabel">
						<strong>Remove Selected Discount</strong>
					</h4>
				</div>
				<div class="modal-body">
					<div class="form-group">
						<label class="control-label col-md-8">Are you sure want to
							remove this discount?</label>
					</div>
				</div>
				<div class="modal-footer">
					<button type="submit" class="btn btn-success" id="discount_save">Remove
						Discount</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- /.modal -->
</form>

<!-- Add Discount Modal -->
<form class="form-horizontal"
	action="${ctx }/broadband-user/crm/customer/order/discount/save"
	method="post">
	<input type="hidden" name="order_id" /> <input type="hidden"
		name="customer_id" value="${customer.id}" />
	<div class="modal fade" id="addDiscountModal" tabindex="-1"
		role="dialog" aria-labelledby="addDiscountModalLabel"
		aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="addDiscountModalLabel">
						<strong>Add A Discount</strong>
					</h4>
				</div>
				<div class="modal-body">
					<div class="form-group">
						<label class="control-label col-md-4">Discount Name</label>
						<div class="col-md-6">
							<input name="detail_name" class="form-control input-sm"
								placeholder="Discount Name" />
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-md-4">Discount Amount</label>
						<div class="col-md-6">
							<input name="detail_price" class="form-control input-sm"
								placeholder="Discount Amount" />
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-md-4">Discount Unit</label>
						<div class="col-md-6">
							<input name="detail_unit" class="form-control input-sm"
								placeholder="Discount Unit" />
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-md-4">Discount Expired</label>
						<div class="col-md-6">
							<input name="detail_expired" class="form-control input-sm"
								placeholder="e.g.:YYYY-MM-DD" />
							<!-- 	<div class="input-group date">
						<input name="detail_expired" class="form-control input-sm" placeholder="e.g.:YYYY-MM-DD"/>
						<span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
					</div> -->
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-md-4">Discount Type</label>
						<div class="col-md-6">
							<select name="detail_type" class="form-control input-sm">
								<option value="discount">Discount</option>
							</select>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="submit" class="btn btn-success" id="discount_save">Add
						Discount</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- /.modal -->
</form>
