<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
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
		<div class="col-md-12">
			<div class="panel panel-success">
				<div class="panel-heading">
					<h4 class="panel-title">
						<a data-toggle="collapse" data-toggle="collapse"
							data-parent="#customerOrderAccordion" href="#collapseOrderInfo">
							Online Ordering Final Step
						</a>
					</h4>
				</div>
				<div id="collapseOrderInfo" class="panel-collapse collapse in">
					<div class="panel-body">
						<form method="post" action="${ctx}/broadband-user/sale/online/ordering/order/upload" class="form-horizontal" enctype="multipart/form-data">
						  <div class="form-group">
							<div class="col-sm-12">
								<input type="hidden" name="order_id" value="${order_id}"/>
								<input type="hidden" name="customer_id" value="${customer_id}"/>
								<div class="col-sm-6">
									<a class="btn btn-success btn-lg pull-right" target="_blank" href="${ctx}/broadband-user/crm/customer/order/pdf/download/${order_id}">
										<span class="glyphicon glyphicon-save"></span> ORDER PDF
									</a>
								</div>
								<div class="col-sm-6">
									<a class="btn btn-success btn-lg" target="_blank" href="${ctx}/broadband-user/crm/customer/order/credit/pdf/download/${order_id}">
										<span class="glyphicon glyphicon-save"></span> CREDIT PDF
									</a>
								</div>
							</div>
						  </div>
							<hr/>
						  <div class="form-group">
							    <label for="order_pdf_path" class="col-sm-4 control-label">Order PDF File Path</label>
							    <div class="col-sm-4">
									<input type="file" name="order_pdf_path" class="form-control" placeholder="File Path" />
							    </div>
							    <div class="col-sm-4">
							    </div>
							  </div>
						  <div class="form-group">
							    <label for="credit_pdf_path" class="col-sm-4 control-label">Credit PDF File Path</label>
							    <div class="col-sm-4">
									<input type="file" name="credit_pdf_path" class="form-control" placeholder="Credit PDF File Path" />
							    </div>
							    <div class="col-sm-4">
							    </div>
						  </div>
						  <div class="form-group">
						    <div class="col-sm-12">
						      <button type="submit" class="btn btn-success btn-lg pull-right">Confirm</button>
						    </div>
						  </div>
						 </form>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
	
<jsp:include page="footer.jsp" />
<jsp:include page="script.jsp" />
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/icheck.min.js"></script>
<script type="text/javascript">
(function($){
	$(':radio').iCheck({
		checkboxClass : 'icheckbox_square-green',
		radioClass : 'iradio_square-green'
	});
})(jQuery);
</script>
<jsp:include page="../footer-end.jsp" />