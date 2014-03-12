<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

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
						<div class="form-group">
							<label for="order_id" class="control-label col-md-3">Order
								Id</label>
							<div class="col-md-3">
								<div class="input-group">
									<p class="form-control-static">${customerOrder.id}</p>
								</div>
							</div>
							<label for="order_create_date" class="control-label col-md-3">Order
								Create Date</label>
							<div class="col-md-3">
								<div class="input-group">
									<p class="form-control-static"><fmt:formatDate  value="${customerOrder.order_create_date}" type="date" /></p>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label for="order_status" class="control-label col-md-3">Order
								Status</label>
							<div class="col-md-3">
								<div class="input-group">
									<p id="${customerOrder.id}_order_status" data-val="${customerOrder.order_status}"
										class="form-control-static"><strong>${customerOrder.order_status}</strong></p>
								</div>
							</div>
							<label for="order_type" class="control-label col-md-3">Order
								Type</label>
							<div class="col-md-3">
								<div class="input-group">
									<p class="form-control-static">${customerOrder.order_type}</p>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label for="order_broadband_type" class="control-label col-md-3">Order
								Broadband Type</label>
							<div class="col-md-3">
								<div class="input-group">
									<p class="form-control-static">${customerOrder.order_broadband_type}</p>
								</div>
							</div>
							<label for="order_total_price" class="control-label col-md-3">Order
								Total Price</label>
							<div class="col-md-3">
								<div class="input-group">
									<p id="${customerOrder.id}_order_total_price"
										data-val="${customerOrder.order_total_price}"
										class="form-control-static "><strong>${customerOrder.order_total_price}</strong></p>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label for="svlan" class="control-label col-md-3">SVLan</label>
							<div class="col-md-3">
								<div class="input-group">
									<p id="${customerOrder.id}_svlan" class="form-control-static">${customerOrder.svlan}</p>
								</div>
							</div>
							<label for="order_using_start" class="control-label col-md-3">Service
								Giving Date</label>
							<div class="col-md-3">
								<div class="input-group">
									<p id="${customerOrder.id}_order_using_start"
										class="form-control-static">${customerOrder.order_using_start_str}</p>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label for="cvlan" class="control-label col-md-3">CVLan</label>
							<div class="col-md-3">
								<div class="input-group">
									<p id="${customerOrder.id}_cvlan" class="form-control-static">${customerOrder.cvlan}</p>
								</div>
							</div>
							<label for="next_invoice_create_date" class="control-label col-md-3">Next Invoice Create Date</label>
							<div class="col-md-3">
								<div class="input-group">
									<p id="${customerOrder.id}_next_invoice_create_date" class="form-control-static">${customerOrder.next_invoice_create_date_str}</p>
								</div>
							</div>
						</div>
						<!--  if broadband type is transition then show below rows -->
						<c:if test="${customerOrder.order_broadband_type=='transition'}">
							<div class="form-group">
								<label for="transition_provider_name" class="control-label col-md-3">Transition Provider Name</label>
								<div class="col-md-3">
									<div class="input-group">
										<p id="${customerOrder.id}_transition_provider_name" class="form-control-static">${customerOrder.transition_provider_name}</p>
									</div>
								</div>
								<label for="transition_account_holder_name" class="control-label col-md-3">Transition Account Holder</label>
								<div class="col-md-3">
									<div class="input-group">
										<p id="${customerOrder.id}_transition_account_holder_name" class="form-control-static">${customerOrder.transition_account_holder_name}</p>
									</div>
								</div>
							</div>
							<div class="form-group">
								<label for="transition_account_number" class="control-label col-md-3">Transition Account Number</label>
								<div class="col-md-3">
									<div class="input-group">
										<p id="${customerOrder.id}_transition_account_number" class="form-control-static">${customerOrder.transition_account_number}</p>
									</div>
								</div>
								<label for="transition_porting_number" class="control-label col-md-3">Transition Porting Number</label>
								<div class="col-md-3">
									<div class="input-group">
										<p id="${customerOrder.id}_transition_porting_number" class="form-control-static">${customerOrder.transition_porting_number}</p>
									</div>
								</div>
							</div>
						</c:if>
					</div>
					<hr />
					<!-- order details -->
					<table class="table">
						<thead>
							<tr>
								<th>Detail Name</th>
								<th>Plan Type</th>
								<th>Plan Sort</th>
								<th>Plan Status</th>
								<th>Data Flow</th>
								<th>Detail Price</th>
								<th>Unit</th>
								<th>&nbsp;</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="customerOrderDetail" items="${customerOrder.customerOrderDetails}">
								<tr>
									<td>${customerOrderDetail.detail_name}</td>
									<td>${customerOrderDetail.detail_plan_type}</td>
									<td>${customerOrderDetail.detail_plan_sort}</td>
									<td><strong>${customerOrderDetail.detail_plan_status}</strong></td>
									<td>${customerOrderDetail.detail_data_flow}</td>
									<td><strong>${customerOrderDetail.detail_price}</strong></td>
									<td 
										<c:if test="${fn:indexOf(customerOrderDetail.detail_type,'plan-') > -1}">
											id="${customerOrder.id}_order_detail_unit"
											data-val="${customerOrderDetail.detail_unit}"
										</c:if>
									>${customerOrderDetail.detail_unit}</td>
									<td>&nbsp;</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<hr />
					<div class="form-group">
						<label class="control-label col-md-2">Service Giving Date</label>
						<div class="col-md-2">
							<input id="${customerOrder.id}_order_using_start_input"
								class="form-control" placeholder="Start Date" value="2014-03-16"/>
						</div>
						<label class="control-label col-md-1">SVLan</label>
						<div class="col-md-2">
							<input id="${customerOrder.id}_svlan_input" class="form-control"
								placeholder="SVLan" value="1"/>
						</div>
						<label class="control-label col-md-1">CVLan</label>
						<div class="col-md-2">
							<input id="${customerOrder.id}_cvlan_input" class="form-control"
								placeholder="CVLan" value="1"/>
						</div>
						<div class="col-md-2">
							<c:if test="${customerOrder.svlan!=null}">
								<a href="javascript:void(0);" id="${customerOrder.id}"
									class="btn btn-danger" onclick="return confirm('');" data-name="edit">Edit</a>
							</c:if>
							<c:if test="${customerOrder.svlan==null}">
								<a href="javascript:void(0);" id="${customerOrder.id}"
									class="btn btn-success" data-name="save">Save</a>
							</c:if>
								<a href="javascript:void(0);" id="${customerOrder.id}"
									class="btn btn-danger" data-name="edit" style="display:none;">Edit</a>
						</div>
					</div>
				</form>
			</c:forEach>
		</div>
	</div>
</div>