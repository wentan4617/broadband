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
						Top Up For Account
					</h3>
				</div>
				<div class="panel-body">
				
					<form class="form-horizontal" data-role="form" action="${ctx }/customer/topup/checkout" method="post">
  						<div class="form-group">
  							<label for="topup" class="col-md-2 control-label text-success">Top Up:</label>
						    <div class="col-md-6">
								<select name="topupAmount" class="selectpicker" data-width="100%">
									<option value="1">Top Up $ 1</option>
									<option value="30">Top Up $ 30</option>
									<option value="50">Top Up $ 50</option>
									<option value="80">Top Up $ 80</option>
									<option value="100">Top Up $ 100</option>
									<option value="120">Top Up $ 120</option>
									<option value="150">Top Up $ 150</option>
									<option value="180">Top Up $ 180</option>
								</select>
							</div>
							<div class="col-md-4">
								<button type="submit" class="btn btn-success">Checkout</button>
							</div>
						</div>
					</form>
					
					
				</div>
			</div>
		</div>
		
	</div>
</div>


<jsp:include page="footer.jsp" />
<jsp:include page="script.jsp" />
<script type="text/javascript">
(function($){
	$('.selectpicker').selectpicker();
})(jQuery);
</script>
<jsp:include page="footer-end.jsp" />