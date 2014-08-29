<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>  
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<jsp:include page="header.jsp" />
<jsp:include page="alert.jsp" />

<style>
.topup-list li {
	width: 100%;
	padding: 10px 20px;
}
</style>

<div class="container">
	<div class="row">
		<div class="col-md-3">
			<jsp:include page="sidebar.jsp" />
		</div>
		<div class="col-md-9" >
			<div class="panel panel-success customer-panel-height">
				<div class="panel-heading">
					<h3 class="panel-title">
						Customer Top Up
					</h3>
				</div>
				<div class="panel-body">
					<h4 class="text-success">For Prepay Months</h4>
					<hr />
					<div class="row">
						<div class="col-md-12">
							<form class="form-horizontal" data-role="form" action="${ctx }/customer/topup/checkout" method="post">
							
							<ul class="list-unstyled topup-list">
							
								<c:set var="co" value="${customerSession.customerOrders[0].customerOrderDetails[0] }"></c:set>
								<c:if test="${co.detail_plan_type != 'UFB' }">
									
								<li>
									<label>
										<input type="radio" name="prepaymonths" value="1" checked="checked"/> 
										&nbsp;<strong>Prepay 1 month (No discount)</strong>
									</label>
								</li>
								<li>
									<label>
										<input type="radio" name="prepaymonths" value="3" />
										&nbsp;<strong>Prepay 3 months <span class="text-danger">(3% off the total price of 3 months plan)</span></strong>
									</label>
									<span style="float:right;font-weight:bold;">
										<span class="text-danger">$<script type="text/javascript">document.write(${co.detail_price * 3} - parseInt(${co.detail_price * 3 * 0.03 }))</script></span>, 
										<span class="text-success">save $<script type="text/javascript">document.write(parseInt(${co.detail_price * 3 * 0.03 }))</script></span>
									</span>
								</li>
								<li>
									<label>
										<input type="radio" name="prepaymonths" value="6" />
										&nbsp;<strong>Prepay 6 months <span class="text-danger">(7% off the total price of 6 months plan)</span></strong>
									</label>
									<span style="float:right;font-weight:bold;">
										<span class="text-danger">$<script type="text/javascript">document.write(${co.detail_price * 6} - parseInt(${co.detail_price * 6 * 0.07 }))</script></span>, 
										<span class="text-success">save $<script type="text/javascript">document.write(parseInt(${co.detail_price * 6 * 0.07 }))</script></span>
									</span>
								</li>
								
								</c:if>
								
								<li>
									<label>
										<input type="radio" name="prepaymonths" value="12" />
										&nbsp;<strong>Prepay 12 months <span class="text-danger">(15% off the total price of 12 months plan)</span></strong>
									</label>
									<span style="float:right;font-weight:bold;">
										<span class="text-danger">$<script type="text/javascript">document.write(${co.detail_price * 12} - parseInt(${co.detail_price * 12 * 0.15 }))</script></span>, 
										<span class="text-success">save $<script type="text/javascript">document.write(parseInt(${co.detail_price * 12 * 0.15 }))</script></span>
									</span>
								</li>
							</ul>
							
							<div class="form-group">
	 							<div class="col-md-2 col-md-offset-1">
									<button type="submit" class="btn btn-success btn-block">Top Up</button>
								</div>
							</div>
							</form>
						</div>
				
 						
					</div>
				</div>
			</div>
		</div>
	</div>
</div>


<jsp:include page="footer.jsp" />
<jsp:include page="script.jsp" />
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/bootstrap-select.min.js"></script>
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/icheck.min.js"></script>
<script type="text/javascript">
(function($){
	
	$('input[name="prepaymonths"]').iCheck({ checkboxClass : 'icheckbox_square-green' , radioClass : 'iradio_square-green' });
	
	$('.selectpicker').selectpicker();
})(jQuery);
</script>
<jsp:include page="footer-end.jsp" />