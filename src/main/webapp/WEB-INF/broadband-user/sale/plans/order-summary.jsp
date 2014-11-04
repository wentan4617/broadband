<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>  
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<jsp:include page="../header.jsp" />

<style>
.personal-info li{
	padding:5px 40px;
}
</style>

<div class="container" style="margin-top:20px;">

	<div class="hidden-xs hidden-sm">
		<ul class="nav nav-pills nav-wizard" style="width: 550px; margin: 0 auto;">
			<li><a href="javascript:void(0);"><span class="glyphicon glyphicon-search"></span> Check Address</a><div class="nav-arrow"></div></li>
			<li><div class="nav-wedge"></div><a href="javascript:void(0);"><span class="glyphicon glyphicon-pencil"></span> Fill in Application</a><div class="nav-arrow"></div></li>
			<li class="active"><div class="nav-wedge"></div><a href="javascript:void(0);"><span class="glyphicon glyphicon-eye-open"></span> Review & Checkout</a></li>
		</ul>
		<hr>
	</div>
	
	<div class="panel panel-success">
		<div class="panel-heading">
			<h4 class="panel-title">
				Please Review Application Information
			</h4>
		</div>
		
		<div class="panel-body">
		
			<div class="row">
				<div class="col-md-6 col-xs-12 col-sm-12">
					<h4 class="text-success">
						<c:choose>
							<c:when test="${customerRegSale.customerOrder.customer_type == 'personal' }">
								${customerRegSale.customerOrder.title } ${customerRegSale.customerOrder.first_name } ${customerRegSale.customerOrder.last_name }
							</c:when>
							<c:when test="${customerRegSale.customerOrder.customer_type == 'business' }">
								${customerRegSale.customerOrder.org_name }
							</c:when>
						</c:choose>
					</h4>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-6">
					<h4 class="text-success">
						${customerRegSale.address }
					</h4>
				</div>
				<div class="col-sm-6">
					<h4 class="text-success pull-right hidden-xs hidden-sm" >
						Order Date: 
						<strong class="text-info">
							<fmt:formatDate  value="${customerRegSale.customerOrder.order_create_date}" type="both" pattern="dd/MM/yyyy" />
						</strong>
					</h4>
					<h4 class="text-success hidden-md hidden-lg" >
						Order Date: 
						<strong class="text-info">
							<fmt:formatDate  value="${customerRegSale.customerOrder.order_create_date}" type="both" pattern="dd/MM/yyyy" />
						</strong>
					</h4>
				</div>
			</div>
			
			
			
			<c:if test="${customerRegSale.customerOrder.customer_type == 'business' }">
			
			<hr style="margin-top:0;"/>
			<h2>Business Information</h2>
			<hr style="margin-top:0;"/>
			
			<div class="row">
				<div class="col-sm-4"><strong>Organization Type</strong></div>
				<div class="col-sm-6"><strong class="text-info">${customerRegSale.customerOrder.org_type }</strong></div>
			</div>
			<div class="row" style="margin-top:5px;">
				<div class="col-sm-4"><strong>Trading Name</strong></div>
				<div class="col-sm-6"><strong class="text-info">${customerRegSale.customerOrder.org_trading_name }</strong></div>
			</div>
			<div class="row" style="margin-top:5px;">
				<div class="col-sm-4"><strong>Registration No.</strong></div>
				<div class="col-sm-6"><strong class="text-info">${customerRegSale.customerOrder.org_register_no }</strong></div>
			</div>
			<div class="row" style="margin-top:5px;">
				<div class="col-sm-4"><strong>Date Incoporated</strong></div>
				<div class="col-sm-6">
					<strong class="text-info">
						<fmt:formatDate  value="${customerRegSale.customerOrder.org_incoporate_date }" type="both" pattern="yyyy-MM-dd" />
					</strong>
				</div>
			</div>
			<hr /><!-- style="margin-top:0;" -->
			<h2>Account Holder Information</h2>
			<hr style="margin-top:0;"/>
			<div class="row" >
				<div class="col-sm-4"><strong>Full name</strong></div>
				<div class="col-sm-6"><strong class="text-info">${customerRegSale.customerOrder.holder_name }</strong></div>
			</div>
			<div class="row" style="margin-top:5px;">
				<div class="col-sm-4"><strong>Job Title</strong></div>
				<div class="col-sm-6"><strong class="text-info">${customerRegSale.customerOrder.holder_job_title }</strong></div>
			</div>
			<div class="row" style="margin-top:5px;">
				<div class="col-sm-4"><strong>Phone</strong></div>
				<div class="col-sm-6"><strong class="text-info">${customerRegSale.customerOrder.holder_phone }</strong></div>
			</div>
			<div class="row" style="margin-top:5px;">
				<div class="col-sm-4"><strong>Email</strong></div>
				<div class="col-sm-6"><strong class="text-info">${customerRegSale.customerOrder.holder_email }</strong></div>
			</div>
			<div class="row" style="margin-top:5px;">
				<div class="col-sm-4"><strong>Identity Type</strong></div>
				<div class="col-sm-6"><strong class="text-info">${customerRegSale.identity_type }</strong></div>
			</div>
			<div class="row" style="margin-top:5px;">
				<div class="col-sm-4"><strong>Identity Number</strong></div>
				<div class="col-sm-6"><strong class="text-info">${customerRegSale.identity_number }</strong></div>
			</div>
			<div class="row" style="margin-top:5px;">
				<div class="col-sm-4"><strong>Connection Date</strong></div>
				<div class="col-sm-6"><strong class="text-info">${customerRegSale.customerOrder.connection_date }</strong></div>
			</div>
			
			</c:if>
			
			<c:if test="${customerRegSale.customerOrder.customer_type == 'personal' }">
			
			<hr /><!-- style="margin-top:0;" -->
			<h2>Personal Information</h2>
			<hr style="margin-top:0;"/>
			
			<div class="row" style="margin-top:5px;">
				<div class="col-sm-4"><strong>Mobile</strong></div>
				<div class="col-sm-6"><strong class="text-info">${customerRegSale.cellphone }</strong></div>
			</div>
			<div class="row" style="margin-top:5px;">
				<div class="col-sm-4"><strong>Email</strong></div>
				<div class="col-sm-6"><strong class="text-info">${customerRegSale.email }</strong></div>
			</div>
			<div class="row" style="margin-top:5px;">
				<div class="col-sm-4"><strong>Identity Type</strong></div>
				<div class="col-sm-6"><strong class="text-info">${customerRegSale.identity_type }</strong></div>
			</div>
			<div class="row" style="margin-top:5px;">
				<div class="col-sm-4"><strong>Identity Number</strong></div>
				<div class="col-sm-6"><strong class="text-info">${customerRegSale.identity_number }</strong></div>
			</div>
			<div class="row" style="margin-top:5px;">
				<div class="col-sm-4"><strong>Connection Date</strong></div>
				<div class="col-sm-6"><strong class="text-info">${customerRegSale.customerOrder.connection_date }</strong></div>
			</div>
			
			</c:if>
			
			<c:if test="${customerRegSale.customerOrder.order_broadband_type == 'transition' }">
				<hr />
				<h2>Transition</h2>
				<hr style="margin-top:0;"/>
				<div class="row" >
					<div class="col-sm-4"><strong>Phoneline Number</strong></div>
					<div class="col-sm-6"><strong class="text-info">${customerRegSale.customerOrder.transition_porting_number }</strong></div>
				</div>
				<div class="row" style="margin-top:5px;">
					<div class="col-sm-4"><strong>Provider Name</strong></div>
					<div class="col-sm-6"><strong class="text-info">${customerRegSale.customerOrder.transition_provider_name }</strong></div>
				</div>
				<div class="row" style="margin-top:5px;">
					<div class="col-sm-4"><strong>Account Name</strong></div>
					<div class="col-sm-6"><strong class="text-info">${customerRegSale.customerOrder.transition_account_holder_name }</strong></div>
				</div>
				<div class="row" style="margin-top:5px;">
					<div class="col-sm-4"><strong>Account Number</strong></div>
					<div class="col-sm-6"><strong class="text-info">${customerRegSale.customerOrder.transition_account_number }</strong></div>
				</div>
			</c:if>
							
			<hr/>
			
			<div class="table-responsive">
			<table class="table">
				<thead>
					<tr>
						<th>Service / Product</th>
						<th>Data</th>
						<!-- <th>Term</th> -->
						<th>
							<c:choose>
								<c:when test="${customerRegSale.select_plan_group == 'plan-topup' }">
								Weekly Charge
								</c:when>
								<c:otherwise>
								Monthly Charge
								</c:otherwise>
							</c:choose>
						</th>
						<th>Qty</th>
						<th>Sub Total</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="detail" items="${customerRegSale.customerOrder.customerOrderDetails }">
						<c:choose>
							<c:when test="${fn:contains(detail.detail_type, 'plan-') }">
								<tr>
									<td>
										${detail.detail_name }
									</td>
									<td>
										<c:choose>
											<c:when test="${detail.detail_data_flow < 0 }">Unlimited</c:when>
											<c:otherwise>${detail.detail_data_flow } GB</c:otherwise>
										</c:choose>
									</td>
									<%-- <td>${detail.detail_term_period }</td> --%>
									<td><fmt:formatNumber value="${detail.detail_price }" type="number" pattern="#,##0.00" /></td>
									<td>${detail.detail_unit }</td>
									<td>
										<fmt:formatNumber value="${detail.detail_price * detail.detail_unit}" type="number" pattern="#,##0.00" />
									</td>
								</tr>
								<tr>
									<th>&nbsp;</th>
									<th>&nbsp;</th>
									<!-- <th>&nbsp;</th> -->
									<th>One-off Charge</th>
									<th>Qty</th>
									<th>Sub Total</th>
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
									<!-- <td>&nbsp;</td> -->
									<td><fmt:formatNumber value="${detail.detail_price }" type="number" pattern="#,##0.00" /></td>
									<td>${detail.detail_unit }</td>
									<td>
										<c:choose>
											<c:when test="${detail.detail_type == 'discount' }">
												<span class="text-success">
													-<fmt:formatNumber value="${detail.detail_price * detail.detail_unit}" type="number" pattern="#,##0.00" />
												</span>
											</c:when>
											<c:otherwise>
												<fmt:formatNumber value="${detail.detail_price * detail.detail_unit}" type="number" pattern="#,##0.00" />
											</c:otherwise>
										</c:choose>
									</td>
								</tr>
							</c:otherwise>
						</c:choose>
						
					</c:forEach>
				</tbody>
			</table>
			</div>
			
			
			<div class="row">
				<div class="col-md-4 col-md-offset-8">
				
					<table class="table">
						<tbody>
						<c:choose>
							<c:when test="${customerRegSale.customerOrder.customer_type == 'personal' }">
							
							<tr>
								<td>Total before GST</td>
								<td>
									NZ$ 
									<fmt:formatNumber value="${customerRegSale.customerOrder.order_total_price * (1 - 0.15)}" type="number" pattern="#,##0.00" />
								</td>
							</tr>
							<tr>
								<td>GST at 15% </td>
								<td>
									NZ$ 
									<fmt:formatNumber value="${customerRegSale.customerOrder.order_total_price * 0.15}" type="number" pattern="#,##0.00" />
								</td>
							</tr>
							<tr>
								<td><strong>Order Total</strong></td>
								<td>
									<strong class="text-success">
										NZ$ 
										<fmt:formatNumber value="${customerRegSale.customerOrder.order_total_price }" type="number" pattern="#,##0.00" />
									</strong>
								</td>
							</tr>
							
							<tr id="voucherTR" style="display:none;">
								<td>Voucher Price</td>
								<td>
									<strong class="text-danger" id="vPrice"></strong>
								</td>
							</tr>
							<tr id="TAAV" style="display:none;">
								<td>Total After Appiled Voucher</td>
								<td>
									<strong class="text-success" id="TAAVPrice"></strong>
								</td>
							</tr>
							
							</c:when>
							
							<c:when test="${customerRegSale.customerOrder.customer_type == 'business' }">
							
							<tr>
								<td>Order Price</td>
								<td>
									NZ$ 
									<fmt:formatNumber value="${(customerRegSale.customerOrder.order_total_price + customerRegSale.customerOrder.discount_price)/1.15}" type="number" pattern="#,##0.00" />
								</td>
							</tr>
							<tr>
								<td>Plus GST at 15% </td>
								<td>
									NZ$ 
									<fmt:formatNumber value="${(customerRegSale.customerOrder.order_total_price + customerRegSale.customerOrder.discount_price)/1.15 * 0.15}" type="number" pattern="#,##0.00" />
								</td>
							</tr>
							<tr>
								<td><strong>Order Total</strong></td>
								<td>
									<strong class="text-success">
										NZ$ 
										<fmt:formatNumber value="${customerRegSale.customerOrder.order_total_price}" type="number" pattern="#,##0.00" />
									</strong>
								</td>
							</tr>
												
							</c:when>
						</c:choose>
						</tbody>
					</table>
				</div>
			</div>
			
			
			<hr>
			<h4 class="text-success">
				Would you like to add one Cyberpark Voucher ? Please click here
				<a href="javascript:void(0);" id="addVoucher">
					<span class="glyphicon glyphicon-plus"></span>
				</a>
			</h4>
			
			<div id="alertContainer"></div>
					
			<div id="tempAlertSuccessContainer" style="display:none">
				<div id="alert-success" class="alert alert-success alert-dismissable fade in" style="display:none">
					<button type="button" class="close" data-dismiss="alert" aria-hidden="true" >&times;</button>
					<span id="text-success"></span>
				</div>
			</div>
			<div id="tempAlertErrorContainer" style="display:none;">
				<div id="alert-error" class="alert alert-danger alert-dismissable fade in" style="display:none;">
					<button type="button" class="close" data-dismiss="alert" aria-hidden="true" >&times;</button>
					<span id="text-error"></span>
				</div>
			</div>
					
			<div id="voucherFormContainer">
				<c:set var="total_vprice" value="0"></c:set>
				<c:if test="${fn:length(customerRegSale.vouchers) > 0 }">
					<c:forEach var="v" items="${customerRegSale.vouchers }">
						<c:set var="total_vprice" value="${total_vprice + v.face_value }"></c:set>
						<div data-index>
							<hr>
							<form class="form-inline">
								<div class="form-group">
									<label for="serial_number">Voucher Card Number: ${v.serial_number } has been applied.</label>
								</div>
								<a href="javascript:void(0);" class="btn btn-success btn-sm" data-voucher-cancel 
									data-voucher-serial-number="${v.serial_number }"
									data-voucher-card-number="${v.card_number }"> 
									<span class="glyphicon glyphicon-remove"></span> Cancel
								</a>
							</form>
						</div>
					</c:forEach>
				</c:if>
			</div>
			
			<hr/>
			
			<h2>Account Holder Information (optional)</h2>
			
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
		
			<hr/>
			
			<div class="row">
				<div class="col-md-2 hidden-xs hidden-sm">
					<a href="${ctx }/broadband-user/sale/plans/order" class="btn btn-success btn-lg btn-block">Back</a>
				</div>
				<div class="col-md-2 col-md-offset-8">
					<a  class="btn btn-success btn-lg btn-block"  data-toggle="modal" data-target="#save_order_model" id="confirm_order_btn"  >Bank Deposit</a>
				</div>
			</div>
			
		</div>
		
	</div>
	
</div>

<script type="text/html" id="voucher_form_tmpl">
<jsp:include page="voucher-form.html" />
</script>

<script type="text/html" id="voucher_form_result_tmpl">
<jsp:include page="voucher-form-result.html" />
</script>

<!-- Modal -->
<form class="form-horizontal" action="${ctx}/broadband-user/sale/plans/confirm/save" method="post">
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

<jsp:include page="../footer.jsp" />
<jsp:include page="../script.jsp" />
<script type="text/javascript">
var ctx = '${ctx}';
var total_vprice = new Number('${total_vprice}');
var order_price = new Number('${customerRegSale.customerOrder.order_total_price}');
</script>
<script type="text/javascript" src="${ctx}/public/broadband-user/sale/plans/order-summary.js?ver=2014114741"></script>
<jsp:include page="../../footer-end.jsp" />