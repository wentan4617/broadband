<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<jsp:include page="../header.jsp" />
<jsp:include page="../alert.jsp" />

<style type="text/css">
thead th {text-align:center;}
tbody td {text-align:center;}
.input-xs{
	height:26px;
}
.form-group{
	margin-bottom:6px;
	padding-bottom:0px;
}
</style>

<div class="container">
	<div class="row">
		<div class="col-md-12">
		
			<div class="panel panel-success">
				<div class="panel-heading">
					<h4 class="panel-title">
						<a data-toggle="collapse" data-toggle="collapse" data-parent="#accordion" href="#collapseOne">
							Ticket Query
						</a>
					</h4>
				</div>
				<div id="collapseOne" class="panel-collapse collapse in">
					<div class="panel-body">
						<div class="btn-group btn-group">
							<a href="${ctx}/broadband-user/crm/ticket/view/1/public" class="btn btn-default ${publicActive }">
								Public&nbsp;<span class="badge">${publicSum}</span>
							</a>
							<a href="${ctx}/broadband-user/crm/ticket/view/1/protected" class="btn btn-default ${protectedActive }">
								Protected&nbsp;<span class="badge">${protectedSum}</span>
							</a>
						</div>
					</div>
				</div>
			</div>
		
			<div class="panel panel-success">
				<div class="panel-heading">
					<h4 class="panel-title">
						Ticket View
						<div class="pull-right">
							<button class="btn btn-success btn-xs" data-name="publish_ticket" data-toggle="tooltip" data-placement="bottom" data-original-title="PUBLISH A NEW TICKET">
								<strong>&nbsp;PUBLISH NEW TICKET&nbsp;<span class="glyphicon glyphicon-arrow-up"></span></strong>
							</button>
						</div>
					</h4>
				</div>
				<c:if test="${fn:length(page.results) > 0 }">
					<table class="table" style="font-size:12px;">
						<thead >
							<tr>
								<th><input type="checkbox" id="checkbox_ts_top" /></th>
								<th>Customer Id</th>
								<th>User Id</th>
								<th>Cellphone</th>
								<th>Email</th>
								<th>First Name</th>
								<th>Last Name</th>
								<th>Publish Type</th>
								<th>Description</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="t" items="${page.results }">
								<tr class="${t.mentioned==true ? 'warning': 'info' }" >
									<td>
										<input type="checkbox" name="checkbox_ts" value="${t.id}"/>
									</td>
									<td>
										<a target="_blank" href="${ctx }/broadband-user/crm/customer/edit/${t.customer_id}">${t.customer_id}</a>
									</td>
									<td>
										${t.user_id }
									</td>
									<td>
										${t.cellphone }
									</td>
									<td>
										${t.email }
									</td>
									<td>
										${t.first_name }
									</td>
									<td>
										${t.last_name }
									</td>
									<td>
										${t.publish_type }
									</td>
									<td>
										${t.description }
									</td>
								</tr>
							</c:forEach>
						</tbody>
						<tfoot>
							<tr>
								<td colspan="11">
									<ul class="pagination">
										<c:forEach var="num" begin="1" end="${page.totalPage }" step="1">
											<li class="${page.pageNo == num ? 'active' : ''}">
												<a href="${ctx}/broadband-user/crm/ticket/view/${num}/${status}">${num}</a>
											</li>
										</c:forEach>
									</ul>
								</td>
							</tr>
						</tfoot>
					</table>
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

<!-- Publish Ticket Modal -->
<form:form modelAttribute="ticket" class="form-horizontal" action="${ctx}/broadband-user/crm/ticket/create" method="post">
	<input type="hidden" name="pageNo" value="1" />
	<input type="hidden" name="publish_type" />
	<input type="hidden" name="billingType" value="all" />
	<div class="modal fade" id="publishTicketModal" tabindex="-1" role="dialog" aria-labelledby="publishTicketModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width:1200px;">
			<div class="modal-content">
				<div class="modal-body">
					<div class="row">
						<div>
							<div class="panel panel-success">
								<div class="panel-heading">
									<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
									<h3 class="panel-title">
										<strong>PUBLISH A TICKET</strong>
									</h3>
								</div>
								<div class="panel-body">
									<div class="form-group col-md-12">
										<div class="form-group col-md-2">
											<strong>PUBLISH TYPE:</strong>
										</div>
										<div class="form-group col-md-10">
											<label><form:radiobutton path="publish_type" checked="checked" value="public" data-name="publish_type_option" />Public</label>&nbsp;
											<label><form:radiobutton path="publish_type" value="protected" data-name="publish_type_option" />Protected</label>
										</div>
									</div>
									<div class="form-group" data-name="at_to" style="display:none;">
										<div class="form-group col-md-12" data-module="all-roles">
											<div class="col-md-1" data-module="at-to">
												<ul class="list-unstyled">
													<li>
														<h4><strong>@</strong></h4>
													</li>
												</ul>
											</div>
											<div class="col-md-2" data-module="accountant">
												<ul class="list-unstyled">
													<li>
														<h4>Accountant</h4>
													</li>
													<li>
														<label> 
															<input type="checkbox" data-name="checkbox_all" data-type="checkbox_accountant" /> All
														</label>
													</li>
													<c:forEach var="u" items="${users}">
														<c:if test="${u.user_role=='accountant'}">
															<li>
																<label>
																	<form:checkbox path="useridArray" value="${u.id}" data-type="checkbox_accountant" />${u.user_name}
																</label>
															</li>
														</c:if>
													</c:forEach>
												</ul>
											</div>
											<div class="col-md-2" data-module="provision">
												<ul class="list-unstyled">
													<li>
														<h4>Provision</h4>
													</li>
													<li>
														<label> 
															<input type="checkbox" data-name="checkbox_all" data-type="checkbox_provision" /> All
														</label>
													</li>
													<c:forEach var="u" items="${users}">
														<c:if test="${u.user_role=='provision-team'}">
															<li>
																<label>
																	<form:checkbox path="useridArray" value="${u.id}" data-type="checkbox_provision" />${u.user_name}
																</label>
															</li>
														</c:if>
													</c:forEach>
												</ul>
											</div>
											<div class="col-md-2" data-module="administrator">
												<ul class="list-unstyled">
													<li>
														<h4>Administrator</h4>
													</li>
													<li>
														<label> 
															<input type="checkbox" data-name="checkbox_all" data-type="checkbox_administrator" /> All
														</label>
													</li>
													<c:forEach var="u" items="${users}">
														<c:if test="${u.user_role=='administrator'}">
															<li>
																<label>
																	<form:checkbox path="useridArray" value="${u.id}" data-type="checkbox_administrator" />${u.user_name}
																</label>
															</li>
														</c:if>
													</c:forEach>
												</ul>
											</div>
											<div class="col-md-2" data-module="developer">
												<ul class="list-unstyled">
													<li>
														<h4>Developer</h4>
													</li>
													<li>
														<label> 
															<input type="checkbox" data-name="checkbox_all" data-type="checkbox_developer" /> All
														</label>
													</li>
													<c:forEach var="u" items="${users}">
														<c:if test="${u.user_role=='system-developer'}">
															<li>
																<label>
																	<form:checkbox path="useridArray" value="${u.id}" data-type="checkbox_developer" />${u.user_name}
																</label>
															</li>
														</c:if>
													</c:forEach>
												</ul>
											</div>
										</div>
									</div>
									<div class="form-group col-md-12" style="display:block;">
										<div class="form-group col-md-2">
											<strong>CUSTOMER TYPE:</strong>
										</div>
										<div class="form-group col-md-10">
											<label><form:radiobutton path="existing_customer" value="true" data-name="existing_customer_option" />Existing Customer</label>&nbsp;
											<label><form:radiobutton path="existing_customer" value="false" data-name="existing_customer_option" />New Customer</label>
										</div>
									</div>
									<div class="form-group col-md-12" data-name="existing_customer_area">
										<div class="form-group col-md-12">
											<span class="col-md-offset-2" style="color:green;">Keywords Includes: customer id, email, phone, cellphone, first name, last name </span>
										</div>
										<div class="form-group col-md-2">
											<label for="keyword" class="control-label pull-right">Keyword&nbsp;&nbsp;</label>
										</div>
										<div class="form-group col-md-2">
											<form:input id="keyword" class="form-control col-md-2 input-xs" path="keyword"/>
										</div>
										<div class="form-group col-md-2">
											&nbsp;&nbsp;<a href="javascript:void(0);" data-name="check-customer-existence">MANUAL CHECK</a>
										</div>
										<div class="form-group col-md-6">
											&nbsp;&nbsp;
											<span data-name="message-customer-check" style="font-weight:bold;"></span>
										</div>
									</div>
									<hr/>
									<div class="form-group col-md-12">
										<div class="form-group col-md-12">
											<h4 class="text-success">CUSTOMER DETAILS:</h4>
										</div>
										<div class="form-group col-md-2">
											<label for="first_name" class="control-label pull-right">First Name&nbsp;&nbsp;</label>
										</div>
										<div class="form-group col-md-2">
											<form:input id="first_name" class="form-control col-md-2 input-xs" path="first_name" />
										</div>
										<div class="form-group col-md-2">
											<label for="last_name" class="control-label pull-right">Last Name&nbsp;&nbsp;</label>
										</div>
										<div class="form-group col-md-2">
											<form:input id="last_name" class="form-control col-md-2 input-xs" path="last_name" />
										</div>
									</div>
									<div class="form-group col-md-12">
										<div class="form-group col-md-2">
											<label for="cellphone" class="control-label pull-right">Cellphone&nbsp;&nbsp;</label>
										</div>
										<div class="form-group col-md-2">
											<form:input id="cellphone" class="form-control col-md-2 input-xs" path="cellphone" />
										</div>
									</div>
									<div class="form-group col-md-12">
										<div class="form-group col-md-2">
											<label for="email" class="control-label pull-right">Email&nbsp;&nbsp;</label>
										</div>
										<div class="form-group col-md-4">
											<form:input id="email" class="form-control col-md-2 input-xs" path="email" />
										</div>
									</div>
									<br/>
								</div>
								<div class="panel-footer col-md-12">
									<button type="submit" class="btn btn-sm btn-success col-md-offset-5" style="width:200px;">Publish</button>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- /.modal -->
</form:form>

<jsp:include page="../footer.jsp" />
<jsp:include page="../script.jsp" />
<script type="text/javascript">
(function($) {
	$('button[data-toggle="tooltip"]').tooltip();
	
	$('#checkbox_ts_top').click(function() {
		var b = $(this).prop("checked");
		if (b) {
			$('input[name="checkbox_ts"]').prop("checked", true);
		} else {
			$('input[name="checkbox_ts"]').prop("checked", false);
		}
	});
	
	$('button[data-name="publish_ticket"]').click(function(){
		$('#publishTicketModal').modal('show');
	});
	
	$('input[data-name="checkbox_all"]').click(function(){
		var b = $(this).prop("checked");
		var type = $(this).attr("data-type");
		if (b) {
			$('input[data-type='+type+']').prop("checked", true);
		} else {
			$('input[data-type='+type+']').prop("checked", false);
		}
	});

	$('input[data-name="publish_type_option"]').click(function(){
		var type = $(this).val();
		if(type=="public"){
			$('div[data-name="at_to"]').css("display", "none");
		} else {
			console.log(type);
			$('div[data-name="at_to"]').css("display", "");
		}
	});

	$('input[data-name="existing_customer_option"]').click(function(){
		var type = $(this).val();
		if(type=="false"){
			$('div[data-name="existing_customer_area"]').css("display", "none");
			/* Reset */
			$('#first_name').val('');
			$('#last_name').val('');
			$('#cellphone').val('');
			$('#email').val('');
		} else {
			$('div[data-name="existing_customer_area"]').css("display", "");
		}
	});
	
	$('#keyword').keyup(function(){
		searchCustomerByKeyword("keydown");
	});

	$('a[data-name="check-customer-existence"]').click(function(){
		searchCustomerByKeyword("click");
	});

	/* Individual Function Area */
	function searchCustomerByKeyword(way){
		
		var keyword = $('#keyword').val();
		
		var data = {
			'keyword':keyword
		};
		
		$.post('${ctx}/broadband-user/crm/ticket/check-customer-existence', data, function(json){
			console.log(json);
			if(json.errorMap.customerNull){
				
				if(way=='click'){
					$('span[data-name="message-customer-check"]').html('<span class="glyphicon glyphicon-remove"></span>&nbsp;'+json.errorMap.customerNull);
					$('span[data-name="message-customer-check"]').css('color', 'red');
				} else {
					$('span[data-name="message-customer-check"]').html('&nbsp;');
				}
				
				/* Reset */
				$('#first_name').val('');
				$('#last_name').val('');
				$('#cellphone').val('');
				$('#email').val('');
			} else if(json.successMap.customerNotNull) {
				/* Message */
				$('span[data-name="message-customer-check"]').html('<span class="glyphicon glyphicon-ok"></span>&nbsp;'+json.successMap.customerNotNull);
				$('span[data-name="message-customer-check"]').css('color', 'green');
				
				/* Retrieving Essential Details */
				$('#first_name').val(json.model.first_name);
				$('#last_name').val(json.model.last_name);
				$('#cellphone').val(json.model.cellphone);
				$('#email').val(json.model.email);
				
			}
		})
	};
	
})(jQuery);
</script>
<jsp:include page="../footer-end.jsp" />