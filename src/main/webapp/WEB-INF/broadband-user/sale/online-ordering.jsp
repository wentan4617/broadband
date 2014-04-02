<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %> 
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>


<jsp:include page="header.jsp" />
<jsp:include page="../alert.jsp" />

<div class="container">
	<div class="page-header">
		<h1>
			1. Internet | Plans and pricing 
		</h1>
	</div>
	
	<div class="panel-group" id="accordion">
		<div class="panel panel-success">
			<div class="panel-heading">
				<h3 class="panel-title">
					<a data-toggle="collapse" data-toggle="collapse" data-parent="#accordion" href="#collapseADSL">
						ADSL Plans
					</a>
				</h3>
			</div>
			<div id="collapseADSL" class="panel-collapse collapse in">
				<c:if test="${fn:length(plans) > 0}">
					<div class="panel-body">
						<h3 class="bg-primary text-center" style="width:200px;">
							<strong>T&C</strong> 
						</h3>
						<ul class="list-unstyled text-info">
							<li><strong>Free Connection Fee (Cost $99 - 199)</strong></li>
							<li><strong>Free TP Link Router/Modem</strong></li>
							<li><strong>$1.99/GB or $9.99/20GB or $29.99GB or Stop or Slowdown</strong></li>
							<li class="text-danger">
								<strong>
									Earlier Termination Charge (ETC): completed plan pried less than 6 month $199, more than 6 month $99
								</strong>	
							</li>
						</ul>
					</div>
					<table class="table">
						<tr>
							<th>&nbsp;</th>
							<th>Plan</th>
							<th>Data</th>
							<th>PSTN</th>
							<th>Term</th>
							<th>Price</th>
						</tr>
						<c:forEach var="plan" items="${plans }">
							<c:if test="${plan.plan_type == 'ADSL' }">
								<tr>
									<td>
										<input type="radio" name="adsl_id" value="${plan.id }"/>
									</td>
									<td>${plan.plan_name }</td>
									<td>${plan.data_flow } GB</td>
									<td>${plan.pstn_count }</td>
									<td>${plan.term_period }</td>
									<td>
										<fmt:formatNumber value="${plan.plan_price }" type="number" pattern="#,#00.00" />
									</td>
								</tr>
							</c:if>
						</c:forEach>
					</table>
					<div class="panel-body">
						<div class="row">
							<div class="col-sm-2 col-sm-offset-10">
								<a href="#" class="btn btn-success btn-lg btn-block" id="adsl_btn">Order</a>
							</div>
						</div>
					</div>
				</c:if>
				<c:if test="${fn:length(plans) <= 0}">
					<div class="panel-body"></div>
				</c:if>

			</div>
		</div>
		<div class="panel panel-success">
			<div class="panel-heading">
				<h3 class="panel-title">
					<a data-toggle="collapse" data-toggle="collapse" data-parent="#accordion" href="#collapseVDSL">
						VDSL Plans
					</a>
				</h3>
			</div>
			<div id="collapseVDSL" class="panel-collapse collapse">
				<c:if test="${fn:length(plans) > 0}">
					<div class="panel-body">
						<h3 class="bg-primary text-center" style="width:200px;">
							<strong>T&C</strong> 
						</h3>
						<ul class="list-unstyled text-info">
							<li><strong>Free Connection Fee (Cost $99 - 199)</strong></li>
							<li><strong>Free TP Link Router/Modem</strong></li>
							<li><strong>$1.99/GB or $9.99/20GB or $29.99GB or Stop or Slowdown</strong></li>
							<li class="text-danger">
								<strong>
									Earlier Termination Charge (ETC): completed plan pried less than 6 month $199, more than 6 month $99
								</strong>	
							</li>
						</ul>
					</div>
					<table class="table">
						<tr>
							<th>&nbsp;</th>
							<th>Plan</th>
							<th>Data</th>
							<th>PSTN</th>
							<th>Term</th>
							<th>Price</th>
						</tr>
						<c:forEach var="plan" items="${plans }">
							<c:if test="${plan.plan_type == 'VDSL' }">
								<tr>
									<td>
										<input type="radio" name="vdsl_id" value="${plan.id }"/>
									</td>
									<td>${plan.plan_name }</td>
									<td>${plan.data_flow }  GB</td>
									<td>${plan.pstn_count }</td>
									<td>${plan.term_period }</td>
									<td>
										<fmt:formatNumber value="${plan.plan_price }" type="number" pattern="#,#00.00" />
									</td>
								</tr>
							</c:if>
						</c:forEach>
					</table>
					<div class="panel-body">
						<div class="row">
							<div class="col-sm-2 col-sm-offset-10">
								<a href="#" class="btn btn-success btn-lg btn-block" id="vdsl_btn">Order</a>
							</div>
						</div>
					</div>
				</c:if>
				<c:if test="${fn:length(plans) <= 0}">
					<div class="panel-body"></div>
				</c:if>
			</div>
		</div>
		<div class="panel panel-success">
			<div class="panel-heading">
				<h3 class="panel-title">
					<a data-toggle="collapse" data-toggle="collapse" data-parent="#accordion" href="#collapseUFB">
						UFB Plans
					</a>
				</h3>
			</div>
			<div id="collapseUFB" class="panel-collapse collapse">
				<c:if test="${fn:length(plans) > 0}">
					<div class="panel-body">
						<h3 class="bg-primary text-center" style="width:200px;">
							<strong>T&C</strong> 
						</h3>
						<ul class="list-unstyled text-info">
							<li><strong>Free Connection Fee (Cost $99 - 199)</strong></li>
							<li><strong>Free TP Link Router/Modem</strong></li>
							<li><strong>$1.99/GB or $9.99/20GB or $29.99GB or Stop or Slowdown</strong></li>
							<li class="text-danger">
								<strong>
									Earlier Termination Charge (ETC): completed plan pried less than 6 month $199, more than 6 month $99
								</strong>	
							</li>
						</ul>
					</div>
					<table class="table">
						<tr>
							<th>&nbsp;</th>
							<th>Plan</th>
							<th>Data</th>
							<th>PSTN</th>
							<th>Term</th>
							<th>Price</th>
						</tr>
						<c:forEach var="plan" items="${plans }">
							<c:if test="${plan.plan_type == 'UFB' }">
								<tr>
									<td>
										<input type="radio" name="ufb_id" value="${plan.id }"/>
									</td>
									<td>${plan.plan_name }</td>
									<td>${plan.data_flow } GB</td>
									<td>${plan.pstn_count }</td>
									<td>${plan.term_period }</td>
									<td>
										<fmt:formatNumber value="${plan.plan_price }" type="number" pattern="#,#00.00" />
									</td>
								</tr>
							</c:if>
						</c:forEach>
					</table>
					<div class="panel-body">
						<div class="row">
							<div class="col-sm-2 col-sm-offset-10">
								<a href="#" class="btn btn-success btn-lg btn-block" id="ufb_btn">Order</a>
							</div>
						</div>
					</div>
				</c:if>
				<c:if test="${fn:length(plans) <= 0}">
					<div class="panel-body"></div>
				</c:if>
			</div>
		</div>
	</div>
</div>


<jsp:include page="../footer.jsp" />
<jsp:include page="../script.jsp" />
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/icheck.min.js"></script>
<script type="text/javascript">
(function($){
	$(':radio').iCheck({
		checkboxClass : 'icheckbox_square-green',
		radioClass : 'iradio_square-green'
	});
	
	$('input[name="adsl_id"]').on('ifChecked', function(){
		$('#adsl_btn').attr('href', '${ctx}/broadband-user/sale/online/ordering/order/' + this.value);
	});
	$('input[name="vdsl_id"]').on('ifChecked', function(){
		$('#vdsl_btn').attr('href', '${ctx}/broadband-user/sale/online/ordering/order/' + this.value);
	});
	$('input[name="ufb_id"]').on('ifChecked', function(){
		$('#ufb_btn').attr('href', '${ctx}/broadband-user/sale/online/ordering/order/' + this.value);
	});
})(jQuery);
</script>
<jsp:include page="../footer-end.jsp" />