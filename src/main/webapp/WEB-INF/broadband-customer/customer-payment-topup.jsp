<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<jsp:include page="header.jsp" />
<jsp:include page="alert.jsp" />

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
					<div class="row">
						<div class="col-md-6">
							&nbsp;&nbsp;&nbsp;
							<strong class="text-success">Top up:</strong>
							&nbsp; &nbsp;&nbsp;
							<select name="adsl-topup-check" class="selectpicker" data-style="btn-success">
								<option value="30">Top up $ 30</option>
								<option value="50">Top up $ 50</option>
								<option value="80">Top up $ 80</option>
								<option value="100">Top up $ 100</option>
								<option value="120">Top up $ 120</option>
								<option value="150">Top up $ 150</option>
								<option value="180">Top up $ 180</option>
							</select>
						</div>
						<div class="col-md-4">
							<button type="submit" class="btn btn-success">Checkout</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>


<jsp:include page="footer.jsp" />
<jsp:include page="script.jsp" />
<script type="text/javascript"
	src="${ctx}/public/bootstrap3/js/bootstrap-select.min.js"></script>
<script type="text/javascript">
(function($){
	$('.selectpicker').selectpicker();
})(jQuery);
</script>
<jsp:include page="footer-end.jsp" />