<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<jsp:include page="../header.jsp" />
<jsp:include page="../alert.jsp" />

<div class="container">
	<div class="row">
		<div class="col-md-12">
			<div class="panel panel-default">
				<div class="panel-heading">Register Customer View</div>
				<c:if test="${fn:length(page.results) > 0 }">
					

					<form id="provisionForm" action="${ctx }/broadband-user/provision/customer/order/status" method="post">
						<input type="hidden" id="process_way" name="process_way" />
						
						<div class="panel-body">
					    	<div class="btn-group">
						  		<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
							    	Operate <span class="caret"></span>
							  	</button>
							  	<ul class="dropdown-menu" role="menu">
							    	<li><a href="javascript:void(0);" id="activeRegisterCusomter">Active Register Customer (Customer Status: <span class="text-danger">verify to active</span>; Order Status: <span class="text-danger">pending to payment</span>)</a></li>
							  	</ul>
							</div>
					  	</div>
				  	
						<table class="table">
							<thead>
								<tr>
									<th><input type="checkbox" id="checkbox_customers_top" /></th>
									<th>Login Name</th>
									<th>Password</th>
									<th>Address</th>
									<th>Email</th>
									<th>Phone</th>
									<th>Register Date</th>
									<th>Status</th>
									<th>&nbsp;</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="customer" items="${page.results }">
									<tr>
										<td>
											<input type="checkbox" name="checkbox_customers" value="${customer.id}"/>
										</td>
										<td>
											<a href="#" data-name="show_customer_info" data-id="${customer.id}">
												${customer.login_name }
											</a>
										</td>
										<td>${customer.password }</td>
										<td>${customer.address }</td>
										<td>${customer.email }</td>
										<td>${customer.cellphone }</td>
										<td><fmt:formatDate  value="${customer.register_date }" type="both" pattern="yyyy-MM-dd HH:mm:ss" /></td>
										<td>${customer.status}</td>
										<td>&nbsp;</td>
									</tr>
								</c:forEach>
							</tbody>
							<tfoot>
							<tr>
								<td colspan="11">
									<ul class="pagination">
										<c:forEach var="num" begin="1" end="${page.totalPage }" step="1">
											<li class="${page.pageNo == num ? 'active' : ''}">
												<a href="${ctx}/broadband-user/provision/customer/view/${num}">${num}</a>
											</li>
										</c:forEach>
									</ul>
								</td>
							</tr>
						</tfoot>
						</table>
					</form>
				</c:if>
				<c:if test="${fn:length(page.results) <= 0 }">
					<div class="panel-body">
						<div class="alert alert-warning">No records have been found.</div>
					</div>
				</c:if>
			</div>
		</div>
	</div>
</div>



<jsp:include page="../footer.jsp" />
<jsp:include page="../script.jsp" />
<script type="text/javascript">
(function($){
	$('#checkbox_customers_top').click(function(){
		var b = $(this).prop("checked");
		if (b) {
			$('input[name="checkbox_customers"]').prop("checked", true);
		} else {
			$('input[name="checkbox_customers"]').prop("checked", false);
		}
	});
	
	$('#activeRegisterCusomter').click(function(){
		
		$('#process_way').val('pending to payment');
		$('#provisionForm').submit();
	}); 
})(jQuery);
</script>

<!-- provision customer order information model -->
<jsp:include page="provision-customer-order-info.jsp" />

<jsp:include page="../footer-end.jsp" />