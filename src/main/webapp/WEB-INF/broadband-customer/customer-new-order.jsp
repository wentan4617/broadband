<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>  
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<jsp:include page="header.jsp" />
<jsp:include page="alert.jsp" />

<style>
.personal-info li{
	padding:5px 0;
}
</style>
<div class="container">
	<div class="row">
		<div class="col-md-3">
			<jsp:include page="sidebar.jsp" />
		</div>
		<div class="col-md-9">
			<div class="panel panel-success">
				<div class="panel-heading">
					<h3 class="panel-title">
						Order one new service for your current account
					</h3>
				</div>
				<div class="panel-body">
					<div class="row">
						<div class="col-md-4">
							<a href="${ctx }/customer/new-order/topup" class="btn btn-success btn-lg btn-block" style="font-size: 44px; padding: 40px;">
								Top Up
							</a>
						</div>
						<div class="col-md-4">
							<a href="${ctx }/customer/new-order/personal" class="btn btn-danger btn-lg btn-block" style="font-size: 44px; padding: 40px;">
								Personal
							</a>
						</div>
						<div class="col-md-4">
							<a href="${ctx }/customer/new-order/business" class="btn btn-info btn-lg btn-block" style="font-size: 44px; padding: 40px;">
								Business
							</a>
						</div>
					</div>
					
					<div class="row" style="margin-top:10px;">
						<div class="col-md-10 col-md-offset-1 btn-info" style="padding: 20px 20px 0 0;">
							<a href="${ctx }/customer/new-order/promotion/ipadmini">
								<img class="img-responsive" src="${ctx }/public/bootstrap3/images/promotion1.png">
							</a>
						</div>
					</div>
				
					<div class="row" style="margin-top:10px;">
						<div class="col-md-10 col-md-offset-1 btn-warning" style="padding: 20px 20px 0 0;">
							<a href="${ctx }/customer/new-order/promotion/hd">
								<img class="img-responsive" src="${ctx }/public/bootstrap3/images/promotion2.png" >
							</a>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<jsp:include page="footer.jsp" />
<jsp:include page="script.jsp" />
<script>
(function($){

})(jQuery);
</script>
<jsp:include page="footer-end.jsp" />