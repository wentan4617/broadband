<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<style>
<!--
	#collapseCustomerOrder .form-group {
		margin-bottom:4px;
	}
-->
</style>

<div class="panel-group" id="customerOrderAccordion">
	<div class="panel panel-default">
		<div class="panel-heading">
			<h4 class="panel-title">
				<a data-toggle="collapse" data-toggle="collapse"
					data-parent="#customerOrderAccordion" href="#collapseCustomerOrder">Order
					Detail</a>
			</h4>
		</div>
		<div id="collapseCustomerOrder" class="panel-collapse collapse in">
			<!-- orders -->
			<c:forEach var="customerOrder" items="${customer.customerOrders }">
				<form class="form-horizontal">
					<div class="panel-body">
						<div class="page-header" style="margin:0">
							<h3>Order Information</h3>
						</div>
						<div class="row">
							<div class="col-md-6">
								<div class="form-group">
									<label for="order_id" class="control-label col-md-6">Order Id</label>
									<div class="col-md-6">
										<p class="form-control-static">${customerOrder.id}</p>
									</div>
								</div>
								<div class="form-group">
									<label for="order_create_date" class="control-label col-md-6">Order Create Date</label>
									<div class="col-md-6">
										<p class="form-control-static"><fmt:formatDate  value="${customerOrder.order_create_date}" type="date" /></p>
									</div>
								</div>
								<div class="form-group">
									<label for="order_status" class="control-label col-md-6">Order Status</label>
									<div class="col-md-6">
										<p id="${customerOrder.id}_order_status" data-val="${customerOrder.order_status}" class="form-control-static"><strong>${customerOrder.order_status}</strong></p>
									</div>
								</div>
								<div class="form-group">
									<label for="order_type" class="control-label col-md-6">Order Type</label>
									<div class="col-md-6">
										<p class="form-control-static">${customerOrder.order_type}</p>
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
										<p id="${customerOrder.id}_order_total_price" data-val="${customerOrder.order_total_price}" class="form-control-static "><strong><fmt:formatNumber value="${customerOrder.order_total_price}" type="number" pattern="#,#00.00" /></strong></p>
									</div>
								</div>
								<div class="form-group">
									<label for="order_due" class="control-label col-md-6">Order Due</label>
									<div class="col-md-6">
										<p id="${customerOrder.id}_order_due" data-val="${customerOrder.order_due}" class="form-control-static "><strong>${customerOrder.order_due}</strong></p>
									</div>
								</div>
								<div class="form-group">
									<label for="pstn_count" class="control-label col-md-6">PSTN Amount</label>
									<div class="col-md-6">
										<p id="${customerOrder.id}_pstn_count" data-val="${customerOrder.pstn_count}" class="form-control-static "><strong>${customerOrder.pstn_count}</strong></p>
									</div>
								</div>
								<div class="form-group">
									<label for="pstn_rental_amount" class="control-label col-md-6">PSTN Rental Amount</label>
									<div class="col-md-6">
										<p id="${customerOrder.id}_pstn_rental_amount" data-val="${customerOrder.pstn_rental_amount}" class="form-control-static "><strong>${customerOrder.pstn_rental_amount}</strong></p>
									</div>
								</div>
								<div class="form-group">
									<label for="term_period" class="control-label col-md-6">Term Period</label>
									<div class="col-md-6">
										<p id="${customerOrder.id}_term_period" data-val="${customerOrder.term_period}" class="form-control-static "><strong>${customerOrder.term_period}</strong></p>
									</div>
								</div>
								<div class="form-group">
									<label for="transition_provider_name" class="control-label col-md-6">Transition Provider Name</label>
									<div class="col-md-6">
										<p id="${customerOrder.id}_transition_provider_name" class="form-control-static">${customerOrder.transition_provider_name}</p>
									</div>
								</div>
								<div class="form-group">
									<label for="transition_account_holder_name" class="control-label col-md-6">Transition Account Holder</label>
									<div class="col-md-6">
										<p id="${customerOrder.id}_transition_account_holder_name" class="form-control-static">${customerOrder.transition_account_holder_name}</p>
									</div>
								</div>
								<div class="form-group">
									<label for="transition_account_number" class="control-label col-md-6">Transition Account Number</label>
									<div class="col-md-6">
										<p id="${customerOrder.id}_transition_account_number" class="form-control-static">${customerOrder.transition_account_number}</p>
									</div>
								</div>
								<div class="form-group">
									<label for="transition_porting_number" class="control-label col-md-6">Transition Porting Number</label>
									<div class="col-md-6">
										<p id="${customerOrder.id}_transition_porting_number" class="form-control-static">${customerOrder.transition_porting_number}</p>
									</div>
								</div>
							
							</div>
							<div class="col-md-6"></div>
						</div>
						<hr />
						<div class="page-header" style="margin:0">
							<h3>PPPoE Information</h3>
						</div>
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
									<div class="col-md-6"></div>
								</div>
								<div class="form-group">
									<label for="pppoe_loginname" class="control-label col-md-6">PPPoE Login Name</label>
									<div class="col-md-6">
										<input id="${customerOrder.id}_pppoe_loginname_input" class="form-control input-sm" placeholder="PPPoE Login Name"/>
									</div>
								</div>
								<div class="form-group">
									<label for="pppoe_password" class="control-label col-md-6">PPPoE Password</label>
									<div class="col-md-6">
										<input id="${customerOrder.id}_pppoe_password_input" class="form-control input-sm" placeholder="PPPoE Password"/>
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-6"></label>
									<div class="col-md-6">
										<c:if test="${customerOrder.pppoe_loginname!=null}">
											<a href="javascript:void(0);" id="${customerOrder.id}"
												class="btn btn-danger pull-right" data-name="pppoe_edit">Edit</a>
										</c:if>
										<c:if test="${customerOrder.pppoe_loginname==null}">
											<a href="javascript:void(0);" id="${customerOrder.id}"
												class="btn btn-success pull-right" data-name="pppoe_save">Save</a>
										</c:if>
											<a href="javascript:void(0);" id="${customerOrder.id}"
												class="btn btn-danger pull-right" data-name="pppoe_edit" style="display:none;">Edit</a>
									</div>
								</div>
							</div>
						</div>
						<hr />
						<div class="page-header" style="margin:0">
							<h3>SV/CVLan Information</h3>
						</div>
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
											<p id="${customerOrder.id}_order_using_start" class="form-control-static">${customerOrder.order_using_start_str}</p>
									</div>
								</div>
								<div class="form-group">
									<label for="next_invoice_create_date" class="control-label col-md-6">Next Invoice Create Date</label>
									<div class="col-md-6">
										<p id="${customerOrder.id}_next_invoice_create_date" class="form-control-static">${customerOrder.next_invoice_create_date_str}</p>
									</div>
								</div>
							</div>
							<div class="col-md-6">
								<div class="form-group">
									<div class="col-md-6"></div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-6">Service Giving Date</label>
									<div class="col-md-6">
										<div class="input-group date">
											<input id="${customerOrder.id}_order_using_start_input" class="form-control input-sm" placeholder="Start Date" value="2014-03-16"/>
											<span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-6">SVLan</label>
									<div class="col-md-6">
										<input id="${customerOrder.id}_svlan_input" class="form-control input-sm"
											placeholder="SVLan" value="1"/>
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-6">CVLan</label>
									<div class="col-md-6">
										<input id="${customerOrder.id}_cvlan_input" class="form-control input-sm"
											placeholder="CVLan" value="1"/>
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-6"></label>
									<div class="col-md-6">
										<c:if test="${customerOrder.order_using_start!=null}">
											<a href="javascript:void(0);" id="${customerOrder.id}"
												class="btn btn-danger pull-right" data-name="edit">Edit</a>
										</c:if>
										<c:if test="${customerOrder.order_using_start==null}">
											<a href="javascript:void(0);" id="${customerOrder.id}"
												class="btn btn-success pull-right" data-name="save">Save</a>
										</c:if>
											<a href="javascript:void(0);" id="${customerOrder.id}"
												class="btn btn-danger pull-right" data-name="edit" style="display:none;">Edit</a>
									</div>
								</div>
							</div>
						</div>
					</div>
					<hr />
					<!-- order details -->
					<table class="table">
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
								<th>&nbsp;</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="customerOrderDetail" items="${customerOrder.customerOrderDetails}">
								<tr class="${customerOrderDetail.detail_type=='discount' ? 'alert alert-warning':'' }">
									<td>${customerOrderDetail.detail_name}</td>
									<td>${customerOrderDetail.detail_type}</td>
									<td>${customerOrderDetail.detail_plan_type}</td>
									<td>${customerOrderDetail.detail_plan_sort}</td>
									<td><strong>${customerOrderDetail.detail_plan_status}</strong></td>
									<td>${customerOrderDetail.detail_data_flow}</td>
									<td><strong><fmt:formatNumber value="${customerOrderDetail.detail_price}" type="number" pattern="#,#00.00" /></strong></td>
									<td 
										<c:if test="${fn:indexOf(customerOrderDetail.detail_type,'plan-term') > -1 || fn:indexOf(customerOrderDetail.detail_type,'plan-no-term') > -1}">
											id="${customerOrder.id}_order_detail_unit"
											data-val="${customerOrderDetail.detail_unit}"
										</c:if>
									>${customerOrderDetail.detail_unit}</td>
									<td><strong><fmt:formatDate  value="${customerOrderDetail.detail_expired}" type="both" pattern="yyyy-MM-dd" /></strong></td>
									<td>&nbsp;</td>
									<td>&nbsp;
										<c:if test="${customerOrderDetail.detail_type=='discount'}">
											<a class="btn btn-danger btn-xs" href="javascript:void(0);" data-name="remove_discount" data-val="${customerOrderDetail.id}" data-toggle="modal" data-target="#removeDiscountModal">
											  <span class="glyphicon glyphicon-trash"></span> Remove
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
									<a class="btn btn-success btn-xs pull-left" data-name="add_discount" data-val="${customerOrder.id}" data-toggle="modal" data-target="#addDiscountModal">
									  Add Discount
									</a>
								</td>
							</tr>
						</tfoot>
					</table>
				</form>
			</c:forEach>
		</div>
	</div>
</div>

<!-- Remove Discount Modal -->
<form class="form-horizontal" action="${ctx }/broadband-user/crm/customer/order/discount/remove" method="post">
	<input type="hidden" name="order_detail_id"/>
	<input type="hidden" name="customer_id" value="${customer.id}"/>
	<div class="modal fade" id="removeDiscountModal" tabindex="-1" role="dialog" aria-labelledby="removeDiscountModalLabel" aria-hidden="true">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
	        <h4 class="modal-title" id="removeDiscountModalLabel">Remove Discount</h4>
	      </div>
	      <div class="modal-body">
			<div class="form-group">
				<label class="control-label col-md-8">Are you sure want to remove this discount?</label>
			</div>
	      </div>
	      <div class="modal-footer">
	        <button type="submit" class="btn btn-warning" id="discount_save">YES, Remove</button>
	      </div>
	    </div><!-- /.modal-content -->
	  </div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
</form>

<!-- Add Discount Modal -->
<form class="form-horizontal" action="${ctx }/broadband-user/crm/customer/order/discount/save" method="post">
	<input type="hidden" name="order_id"/>
	<input type="hidden" name="customer_id" value="${customer.id}"/>
	<div class="modal fade" id="addDiscountModal" tabindex="-1" role="dialog" aria-labelledby="addDiscountModalLabel" aria-hidden="true">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
	        <h4 class="modal-title" id="addDiscountModalLabel">Add Discount</h4>
	      </div>
	      <div class="modal-body">
			<div class="form-group">
				<label class="control-label col-md-4">Discount Name</label>
				<div class="col-md-6">
					<input name="detail_name" class="form-control input-sm" placeholder="Discount Name"/>
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-md-4">Discount Amount</label>
				<div class="col-md-6">
					<input name="detail_price" class="form-control input-sm" placeholder="Discount Amount"/>
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-md-4">Discount Unit</label>
				<div class="col-md-6">
					<input name="detail_unit" class="form-control input-sm" placeholder="Discount Unit"/>
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-md-4">Discount Expired</label>
				<div class="col-md-6">
						<input name="detail_expired" class="form-control input-sm" placeholder="e.g.:YYYY-MM-DD"/>
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
	        <button type="submit" class="btn btn-success" id="discount_save">Save Discount</button>
	      </div>
	    </div><!-- /.modal-content -->
	  </div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
</form>