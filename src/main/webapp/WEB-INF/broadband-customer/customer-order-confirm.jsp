<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>  
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<jsp:include page="header.jsp" />

<style>
.personal-info li{
	padding:5px 40px;
}
</style>
<div class="container">
	<div class="page-header">
		<h1>
			3. Customer Information Review and Checkout 
		</h1>
	</div>
	<div class="panel panel-default">
		<div class="panel-heading">Please review your application information to checkout</div>
		<div class="panel-body">
			<div class="row">
				<div class="col-md-8">
					<div class="row">
						<div class="col-md-3">
							<h1>Order</h1>
						</div>
						<div class="col-md-8 text-right">
							<span class="logo_ct"></span>
						</div>
					</div>
					<hr style="margin-top:0;"/>
					<div class="row">
						<div class="col-md-6">
							<p class="text-success"><strong>Personal Information:</strong></p>
							<ul class="list-unstyled personal-info">
								<li><strong class="text-info">${customer.login_name }</strong></li>
								<li><strong class="text-info">${customer.first_name }&nbsp;${customer.last_name }</strong></li>
								<li><strong class="text-info"><a href="mailto:#">${customer.email }</a></strong></li>
								<li><strong class="text-info">${customer.cellphone }</strong></li>
								<li><strong class="text-info">${customer.address }</strong></li>
							</ul>
							<c:if test="${customer.customerOrder.order_broadband_type == 'transition' }">
								<p class="text-success"><strong>Provider Information:</strong></p>
								<ul class="list-unstyled personal-info">
									<li><strong class="text-info">${customer.customerOrder.transition_provider_name }</strong></li>
									<li><strong class="text-info">${customer.customerOrder.transition_account_holder_name }</strong></li>
									<li><strong class="text-info">${customer.customerOrder.transition_account_number }</strong></li>
									<li><strong class="text-info">${customer.customerOrder.transition_porting_number }</strong></li>
								</ul>
							</c:if>
						</div>
						<div class="col-md-6 ">
							<p class="text-success"><strong>&nbsp;</strong></p>
							<ul class="list-unstyled personal-info pull-right">
								<li>
									Order Date: 
									<strong class="text-info">
										<fmt:formatDate  value="${customer.customerOrder.order_create_date}" type="both" pattern="yyyy-MM-dd" />
									</strong>
								</li>
								<li>
									Total Price: 
									<strong class="text-info">
										NZ$ <fmt:formatNumber value="${customer.customerOrder.order_total_price }" type="number" pattern="#,##0.00" />
									</strong>
								</li>
							</ul>
						</div>
					</div>
				</div>
			</div>
			<hr/>
			<table class="table">
				<thead>
					<tr>
						<th>Service / Product</th>
						<th>Unit Price</th>
						<th>Discount</th>
						<th>Qty</th>
						<th>Subtotal</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="detail" items="${customer.customerOrder.customerOrderDetails }">
						<tr>
							<td>${detail.detail_name }</td>
							<td>
								<fmt:formatNumber value="${detail.detail_price }" type="number" pattern="#,##0.00" />
								
							</td>
							<td></td>
							<td>${detail.detail_unit }</td>
							<td>
								<fmt:formatNumber value="${detail.detail_price * detail.detail_unit}" type="number" pattern="#,##0.00" />
								
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<div class="row">
				<div class="col-md-4 col-md-offset-8">
				
					<table class="table">
						<tbody>
							<tr>
								<td>Total before GST</td>
								<td>
									NZ$ 
									<fmt:formatNumber value="${customer.customerOrder.order_total_price * (1 - 0.15)}" type="number" pattern="#,##0.00" />
								</td>
							</tr>
							<tr>
								<td>GST at 15% </td>
								<td>
									NZ$ 
									<fmt:formatNumber value="${customer.customerOrder.order_total_price * 0.15}" type="number" pattern="#,##0.00" />
								</td>
							</tr>
							<tr>
								<td><strong>Order Total</strong></td>
								<td>
									<strong class="text-success">
										NZ$ 
										<fmt:formatNumber value="${customer.customerOrder.order_total_price }" type="number" pattern="#,##0.00" />
									</strong>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			<hr/>
			<div class="row">
				<div class="col-md-12">
					<c:choose>
						<c:when test="${orderPlan.plan_group == 'plan-no-term' }">
							<a href="${ctx }/order/${orderPlan.id}" class="btn btn-success btn-lg pull-left">Back</a>
						</c:when>
						<c:when test="${orderPlan.plan_group == 'plan-topup' }">
							<a href="${ctx }/order/${orderPlan.id}/topup/<fmt:formatNumber value="${orderPlan.topup.topup_fee}" type="number" pattern="##" />" class="btn btn-success btn-lg pull-left">Back</a>
						</c:when>
					</c:choose>
					<form class="form-horizontal" action="${ctx }/order/submit" method="post">
						<button type="submit" class="btn btn-success btn-lg pull-right">Checkout</button>
					</form>
				</div>
			</div>
		</div>
	</div>
	
</div>


<jsp:include page="footer.jsp" />
<jsp:include page="script.jsp" />
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/holder.js"></script>
<jsp:include page="footer-end.jsp" />