<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
									<p class="form-control-static">${customerOrder.order_create_date}</p>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label for="order_status" class="control-label col-md-3">Order
								Status</label>
							<div class="col-md-3">
								<div class="input-group">
									<p id="${customerOrder.id}_order_status"
										class="form-control-static">${customerOrder.order_status}</p>
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
										class="form-control-static">${customerOrder.order_total_price}</p>
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
										class="form-control-static">${customerOrder.order_using_start}</p>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label for="cvlan" class="control-label col-md-3">CVLan</label>
							<div class="col-md-9">
								<div class="input-group">
									<p id="${customerOrder.id}_cvlan" class="form-control-static">${customerOrder.cvlan}</p>
								</div>
							</div>
						</div>
					</div>
					<hr />
					<!-- order details -->
					<table class="table">
						<thead>
							<tr>
								<th>Plan Name</th>
								<th>Plan Type</th>
								<th>Plan Sort</th>
								<th>Plan Status</th>
								<th>Data Flow</th>
								<th>Plan Price</th>
								<th>Unit</th>
								<th>&nbsp;</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="customerOrderDetail"
								items="${customerOrder.customerOrderDetails}">
								<tr>
									<td>${customerOrderDetail.detail_plan_name}</td>
									<td>${customerOrderDetail.detail_plan_type}</td>
									<td>${customerOrderDetail.detail_plan_sort}</td>
									<td>${customerOrderDetail.detail_plan_status}</td>
									<td>${customerOrderDetail.detail_data_flow}</td>
									<td>${customerOrderDetail.detail_plan_price}</td>
									<td id="${customerOrder.id}_order_detail_unit"
										data-val="${customerOrderDetail.detail_unit}">${customerOrderDetail.detail_unit}</td>
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
								class="form-control" placeholder="Start Date" />
						</div>
						<label class="control-label col-md-1">SVLan</label>
						<div class="col-md-2">
							<input id="${customerOrder.id}_svlan_input" class="form-control"
								placeholder="SVLan" />
						</div>
						<label class="control-label col-md-1">CVLan</label>
						<div class="col-md-2">
							<input id="${customerOrder.id}_cvlan_input" class="form-control"
								placeholder="CVLan" />
						</div>
						<div class="col-md-2">
							<c:if test="${customerOrder.svlan!=null}">
								<button id="${customerOrder.id}cvlan" type="submit"
									class="btn btn-danger" >Edit</button>
							</c:if>
							<c:if test="${customerOrder.svlan==null}">
								<a href="javascript:void(0);" id="${customerOrder.id}"
									class="btn btn-success" data-name="save">Save</a>
							</c:if>
						</div>
					</div>
				</form>
			</c:forEach>
		</div>
	</div>
</div>