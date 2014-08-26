<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<jsp:include page="header.jsp" />
<jsp:include page="../alert.jsp" />

<style>
.personal-info li{
	padding:5px 40px;
}
</style>

<div class="container">
	<div class="row">
		<div class="col-sm-12">
			<div class="panel panel-success">
				<div class="panel-heading">
					<h4 class="panel-title">
						<a data-toggle="collapse" data-toggle="collapse" data-parent="#customerOrderAccordion" href="#collapseOrderInfo">
							Order Confirm
						</a>
					</h4>
				</div>
				<div id="collapseOrderInfo" class="panel-collapse collapse in">
					<div class="panel-body">
						<div class="row">
							<div class="col-sm-6">
								<h4 class="text-success">
									<c:choose>
										<c:when test="${orderCustomer.customer_type == 'personal' }">
											${orderCustomer.title } ${orderCustomer.first_name } ${orderCustomer.last_name }
										</c:when>
										<c:when test="${orderCustomer.customer_type == 'business' }">
											${orderCustomer.organization.org_name }
										</c:when>
									</c:choose>
								</h4>
							</div>
							<div class="col-sm-6"></div>
						</div>
						<div class="row">
							<div class="col-sm-6">
								<h4 class="text-success">
									${orderCustomer.address }
								</h4>
							</div>
							<div class="col-sm-6">
								<h4 class="text-success pull-right" >
									Order Date: 
									<strong class="text-info">
										<fmt:formatDate  value="${orderCustomer.customerOrder.order_create_date}" type="both" pattern="yyyy-MM-dd" />
									</strong>
								</h4>
							</div>
						</div>
						
						<c:if test="${orderCustomer.customer_type == 'business' }">
							<hr style="margin-top:0;"/>
							<h2>Business Information</h2>
							<hr style="margin-top:0;"/>
							<div class="row">
								<div class="col-sm-4"><strong>Organization Type</strong></div>
								<div class="col-sm-6"><strong class="text-info">${orderCustomer.organization.org_type }</strong></div>
							</div>
							<div class="row" style="margin-top:5px;">
								<div class="col-sm-4"><strong>Trading Name</strong></div>
								<div class="col-sm-6"><strong class="text-info">${orderCustomer.organization.org_trading_name }</strong></div>
							</div>
							<div class="row" style="margin-top:5px;">
								<div class="col-sm-4"><strong>Registration No.</strong></div>
								<div class="col-sm-6"><strong class="text-info">${orderCustomer.organization.org_register_no }</strong></div>
							</div>
							<div class="row" style="margin-top:5px;">
								<div class="col-sm-4"><strong>Date Incoporated</strong></div>
								<div class="col-sm-6">
									<strong class="text-info">
										<fmt:formatDate  value="${orderCustomer.organization.org_incoporate_date }" type="both" pattern="yyyy-MM-dd" />
									</strong>
								</div>
							</div>
							<hr /><!-- style="margin-top:0;" -->
							<h2>Account Holder Information</h2>
							<hr style="margin-top:0;"/>
							<div class="row" >
								<div class="col-sm-4"><strong>Full name</strong></div>
								<div class="col-sm-6"><strong class="text-info">${orderCustomer.organization.holder_name }</strong></div>
							</div>
							<div class="row" style="margin-top:5px;">
								<div class="col-sm-4"><strong>Job Title</strong></div>
								<div class="col-sm-6"><strong class="text-info">${orderCustomer.organization.holder_job_title }</strong></div>
							</div>
							<div class="row" style="margin-top:5px;">
								<div class="col-sm-4"><strong>Phone</strong></div>
								<div class="col-sm-6"><strong class="text-info">${orderCustomer.organization.holder_phone }</strong></div>
							</div>
							<div class="row" style="margin-top:5px;">
								<div class="col-sm-4"><strong>Email</strong></div>
								<div class="col-sm-6"><strong class="text-info">${orderCustomer.organization.holder_email }</strong></div>
							</div>
						</c:if>
						
						<c:if test="${customer.customer_type == 'personal' }">
							<hr /><!-- style="margin-top:0;" -->
							<h2>Personal Information</h2>
							<hr style="margin-top:0;"/>
							
							<div class="row" style="margin-top:5px;">
								<div class="col-sm-4"><strong>Mobile</strong></div>
								<div class="col-sm-6"><strong class="text-info">${customer.cellphone }</strong></div>
							</div>
							<div class="row" style="margin-top:5px;">
								<div class="col-sm-4"><strong>Email</strong></div>
								<div class="col-sm-6"><strong class="text-info">${customer.email }</strong></div>
							</div>
							<div class="row" style="margin-top:5px;">
								<div class="col-sm-4"><strong>Identity Type</strong></div>
								<div class="col-sm-6"><strong class="text-info">${customer.identity_type }</strong></div>
							</div>
							<div class="row" style="margin-top:5px;">
								<div class="col-sm-4"><strong>Identity Number</strong></div>
								<div class="col-sm-6"><strong class="text-info">${customer.identity_number }</strong></div>
							</div>
						</c:if>
						
						<c:if test="${orderCustomer.customerOrder.order_broadband_type == 'transition' }">
							<hr /><!-- style="margin-top:0;" -->
							<h2>Transition</h2>
							<hr style="margin-top:0;"/>
							<div class="row" >
								<div class="col-sm-4"><strong>Current Provider</strong></div>
								<div class="col-sm-6"><strong class="text-info">${orderCustomer.customerOrder.transition_provider_name }</strong></div>
							</div>
							<div class="row" style="margin-top:5px;">
								<div class="col-sm-4"><strong>Account Holder</strong></div>
								<div class="col-sm-6"><strong class="text-info">${orderCustomer.customerOrder.transition_account_holder_name }</strong></div>
							</div>
							<div class="row" style="margin-top:5px;">
								<div class="col-sm-4"><strong>Current Account Number</strong></div>
								<div class="col-sm-6"><strong class="text-info">${orderCustomer.customerOrder.transition_account_number }</strong></div>
							</div>
							<div class="row" style="margin-top:5px;">
								<div class="col-sm-4"><strong>Telephone Number</strong></div>
								<div class="col-sm-6"><strong class="text-info">${orderCustomer.customerOrder.transition_porting_number }</strong></div>
							</div>
						</c:if>
						
						<hr/>
						<table class="table">
							<thead>
								<tr>
									<th>Service / Product</th>
									<th>Data</th>
									<th>Term(mth)</th>
									<th>Monthly Charge</th>
									<th>Qty</th>
									<th>Subtotal</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="detail" items="${orderCustomer.customerOrder.customerOrderDetails }">
									<c:choose>
										<c:when test="${fn:contains(detail.detail_type, 'plan-') }">
											<tr>
												<td>
													${detail.detail_name }
												</td>
												<td>
													<c:choose>
														<c:when test="${detail.detail_data_flow < 0 }">Unlimited Data</c:when>
														<c:otherwise>${detail.detail_data_flow } GB</c:otherwise>
													</c:choose>
													
												</td>
												<td>${detail.detail_term_period }</td>
												<td><fmt:formatNumber value="${detail.detail_price }" type="number" pattern="#,##0.00" /></td>
												<td>${detail.detail_unit }</td>
												<td>
													<fmt:formatNumber value="${detail.detail_price * detail.detail_unit}" type="number" pattern="#,##0.00" />
													
												</td>
											</tr>
											<tr>
												<th>&nbsp;</th>
												<th>&nbsp;</th>
												<th>&nbsp;</th>
												<th>Unit Price</th>
												<th>Qty</th>
												<th>Subtotal</th>
											</tr>
										</c:when>
										<c:otherwise>
											<tr>
												<td>
													${detail.detail_name }&nbsp;
													<c:if test="${detail.detail_type == 'pstn' || detail.detail_type == 'voip'}">
														<c:if test="${detail.pstn_number != null && detail.pstn_number != '' }">
															<strong class="text-danger">(${detail.pstn_number })</strong>
														</c:if>
													</c:if>
												</td>
												<td>&nbsp;</td>
												<td>&nbsp;</td>
												<td><fmt:formatNumber value="${detail.detail_price }" type="number" pattern="#,##0.00" /></td>
												<td>${detail.detail_unit }</td>
												<td>
													<fmt:formatNumber value="${detail.detail_price * detail.detail_unit}" type="number" pattern="#,##0.00" />
													
												</td>
											</tr>
										</c:otherwise>
									</c:choose>
									
								</c:forEach>
							</tbody>
						</table>
						<div class="row">
							<div class="col-sm-5 col-sm-offset-7">
							
								<table class="table">
									
									<tbody>
										<c:choose>
											<c:when test="${orderCustomer.customer_type == 'personal' }">
												<tr>
													<td>Total before GST</td>
													<td>
														NZ$ 
														<fmt:formatNumber value="${orderCustomer.customerOrder.order_total_price * (1 - 0.15)}" type="number" pattern="#,##0.00" />
													</td>
												</tr>
												<tr>
													<td>GST at 15% </td>
													<td>
														NZ$ 
														<fmt:formatNumber value="${orderCustomer.customerOrder.order_total_price * 0.15}" type="number" pattern="#,##0.00" />
													</td>
												</tr>
												<tr>
													<td><strong>Order Total</strong></td>
													<td>
														<strong class="text-success">
															NZ$ 
															<fmt:formatNumber value="${orderCustomer.customerOrder.order_total_price }" type="number" pattern="#,##0.00" />
														</strong>
													</td>
												</tr>
											</c:when>
											<c:when test="${orderCustomer.customer_type == 'business' }">
												<tr>
													<td>Order Price</td>
													<td>
														NZ$ 
														<fmt:formatNumber value="${orderCustomer.customerOrder.order_total_price}" type="number" pattern="#,##0.00" />
													</td>
												</tr>
												<tr>
													<td>Plus GST at 15% </td>
													<td>
														NZ$ 
														<fmt:formatNumber value="${orderCustomer.customerOrder.order_total_price * 0.15}" type="number" pattern="#,##0.00" />
													</td>
												</tr>
												<tr>
													<td><strong>Order Total</strong></td>
													<td>
														<strong class="text-success">
															NZ$ 
															<fmt:formatNumber value="${orderCustomer.customerOrder.order_total_price * 1.15 }" type="number" pattern="#,##0.00" />
														</strong>
													</td>
												</tr>
											</c:when>
										</c:choose>
										
									</tbody>
								</table>
							</div>
						</div>
						<hr/>
							<h2>Account Holder Information (optional)</h2>
							<div class="row">
								<form class="form-horizontal">
									<div class="form-group">
										<label class="control-label col-sm-2">Card Type</label>
										<div class="col-sm-10">
											<ul class="list-inline topup-list" style="margin: 5px 0 0 0;">
												<li>
													<label style="cursor:pointer;">
														<input type="radio" name="card_type" value="VISA" checked="checked" /> &nbsp; 
														<strong>VISA</strong>
													</label>
												</li>
												<li>
													<label style="cursor:pointer;">
														<input type="radio" name="card_type" value="MASTERCARD" /> &nbsp; 
														<strong>MasterCard</strong>
													</label>
												</li>
											</ul>
										</div>
									</div>
									<div class="form-group">
										<label class="control-label col-sm-12"></label>
									</div>
									<div class="form-group">
										<label for="holder_name" class="col-sm-2 control-label">Holder Name</label>
										<div class="col-sm-3">
											<input id="holder_name" class="form-control" placeholder="Holder Name" />
										</div>
									</div>
									<div class="form-group">
										<label for="card_number" class="col-sm-2 control-label">Card Number</label>
										<div class="col-sm-3">
											<input id="card_number" class="form-control" placeholder="Card Number" />
										</div>
									</div>
									<div class="form-group">
										<label for="security_code" class="col-sm-2 control-label">Security Code</label>
										<div class="col-sm-2">
											<input id="security_code" class="form-control" placeholder="Security Code" />
										</div>
									</div>
									<div class="form-group">
										<label for="expiry_month" class="col-sm-2 control-label">Exp MONTH</label>
										<div class="col-sm-2">
											<select style="width:68px;" class="form-control" id="expiry_month">
												<c:forEach var="month" items="${months}">
													<option value="${month}">${month}</option>
												</c:forEach>
											</select>
										</div>
										<label for="expiry_year" class="col-sm-1 control-label">YEAR</label>
										<div class="col-sm-2">
											<select style="width:68px;" class="form-control" id="expiry_year">
												<c:forEach var="year" items="${years}">
													<option value="${year}">${year}</option>
												</c:forEach>
											</select>
										</div>
									</div>
								</form>
							</div>
						
						<hr/>
						<div class="row">
							<div class="col-sm-3 col-md-2">
								<a href="${ctx}/broadband-user/sale/online/ordering/order/${orderPlan.id}" class="btn btn-success btn-lg btn-block" >Back</a>
							</div>
							<div class="col-sm-3 col-sm-offset-6 col-md-2 col-md-offset-8">
								
								<!-- <div class="btn-group dropup btn-block">
									<button type="button" class="btn btn-success btn-lg btn-block dropdown-toggle" data-toggle="dropdown">
										Checkout <span class="caret"></span>
									</button>
									<ul class="dropdown-menu" >
										<li><a href="javascript:void(0);" id="online_payment">Online Payment</a></li>
										<li><a data-toggle="modal" data-target="#save_order_model" id="bank_depoist">Bank Deposit</a></li>
									</ul>
								</div> -->
								
								<a  class="btn btn-success btn-lg btn-block"  data-toggle="modal" data-target="#save_order_model" id="confirm_order_btn"  >Bank Deposit</a>
							</div>
						</div>
						
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<!-- Modal -->
<form class="form-horizontal" action="${ctx}/broadband-user/sale/online/ordering/order/confirm/save" method="post">
	<input type="hidden" id="card_type" name="card_type" value="VISA" />
	<input type="hidden" name="holder_name" />
	<input type="hidden" name="card_number" />
	<input type="hidden" name="security_code" />
	<input type="hidden" name="expiry_month" />
	<input type="hidden" name="expiry_year" />
	<div class="modal fade" id="save_order_model" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">Save Order & Generate PDF</h4>
				</div>
				<div class="modal-body">
					<div class="form-group">
						<span class="col-md-12"><strong>Additional Request: </strong>(If don't have any other requests, please leave it empty)</span>
					</div>
					<div class="form-group">
						<div class="col-md-12">
							<textarea name="optional_request" class="form-control input-lg" placeholder="Optional Request" rows="10"></textarea>
						</div>
					</div>
					<hr/>
					<p class="text-danger">
						Click the button to save the order, generated broadband application form pdf.
					</p>
				</div>
				<div class="modal-footer">
					<button type="submit" class="btn btn-success btn-lg" id="save_order">Save Order & Generate PDF</button>
				</div>
			</div>
		</div>
	</div>
</form>

<jsp:include page="footer.jsp" />
<jsp:include page="script.jsp" />
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/bootstrap-select.min.js"></script>
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/icheck.min.js"></script>
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/bootstrap-datepicker.js"></script>
<script type="text/javascript">
(function($){
	$(':radio').iCheck({
		checkboxClass : 'icheckbox_square-green',
		radioClass : 'iradio_square-green'
	});
	
	$('input[name="card_type"]').on('ifChecked',function(){
		$('#card_type').val($(this).val());
	});
	
	$('#confirm_order_btn').click(function(){
		$('input[name="holder_name"]').val($('#holder_name').val());
		$('input[name="card_number"]').val($('#card_number').val());
		$('input[name="security_code"]').val($('#security_code').val());
		$('input[name="expiry_month"]').val($('#expiry_month').val());
		$('input[name="expiry_year"]').val($('#expiry_year').val());
	});
	
	/*$('#save_order').click(function(e){
		e.preventDefault();
	});*/
	
})(jQuery);
</script>
<jsp:include page="../footer-end.jsp" />