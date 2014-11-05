<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
.btn {
	padding: 0 12px;
}
.bootstrap-select.btn-group, .bootstrap-select.btn-group[class*="span"] {
	margin-bottom: 0;
}
</style>

<div class="container">
	<div class="row">
		<div class="col-md-12">
		
			<div class="panel panel-success">
				<div class="panel-heading">
					<h4 class="panel-title">
						<div class="btn-group btn-group-sm">
							<button data-name="existing_customer_btn" data-bool="true" class="btn btn-default" style="pointer-events:none;">
								<strong>Total New Tickets</strong>&nbsp;<span class="badge">${existingSum+newSum}</span>
							</button>
							<button data-name="existing_customer_btn" data-bool="true" class="btn btn-default" style="pointer-events:none;">
								<strong>Existed Customer</strong>&nbsp;<span class="badge">${existingSum}</span>
							</button>
							<button data-name="existing_customer_btn" data-bool="false" class="btn btn-default" style="pointer-events:none;">
								<strong>New Customer</strong>&nbsp;<span class="badge">${newSum}</span>
							</button>
						</div>
						<div class="pull-right">
							<select id="filter_operations" class="selectpicker" multiple title="Filter Operation">
							    <optgroup label="Filtering Ticket Type">
							    	<option value="faulty" data-type="ticket-type">Faulty</option>
							      	<option value="billing" data-type="ticket-type">Billing</option>
							      	<option value="hardware-issue" data-type="ticket-type">Hardware Issue</option>
							      	<option value="application" data-type="ticket-type">Application</option>
							      	<option value="booking-appointment" data-type="ticket-type">Booking Appointment</option>
							      	<option value="announcement" data-type="ticket-type">Announcement</option>
							    </optgroup>
							    <optgroup label="Filtering Publish Type">
							    	<option value="public" data-type="publish-type">Public</option>
							      	<option value="protected" data-type="publish-type">Protected</option>
							    </optgroup>
							    <optgroup label="Filtering Customer Type">
							    	<option value="true" data-type="existing-customer-str">Existing Customer</option>
							      	<option value="false" data-type="existing-customer-str">New Customer</option>
							    </optgroup>
							    <optgroup label="Filtering Ticket Status">
							    	<option value="1" data-type="ticket-status">New Ticket</option>
							    	<option value="0" data-type="ticket-status">Old Ticket</option>
							    </optgroup>
							    <optgroup label="Filtering Who's Ticket">
							    	<option value="1" data-type="my-id">My Published Ticket</option>
							    	<option value="0" data-type="my-id">Others Published Ticket</option>
							    </optgroup>
							    <optgroup label="Filtering Has Commented">
							    	<option value="true" data-type="commented-str">Commented</option>
							    	<option value="false" data-type="commented-str">No Comments</option>
							    </optgroup>
							</select>
							<button class="btn btn-success btn-xs" data-name="publish_ticket" data-toggle="tooltip" data-placement="bottom" data-original-title="PUBLISH A NEW TICKET">
								<strong>&nbsp;PUBLISH NEW TICKET&nbsp;<span class="glyphicon glyphicon-arrow-up"></span></strong>
							</button>
						</div>
					</h4>
				</div>
				
				<!-- ticket view page table for ajax -->
				<div id="ticket-table"></div>
				
			</div>
		</div>
	</div>
</div>

<!-- Publish Ticket Modal -->
<form class="form-horizontal">
	<div class="modal fade" id="publishTicketModal" tabindex="-1" role="dialog" aria-labelledby="publishTicketModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width:1200px;">
			<div class="modal-content">
				<div class="modal-body">
					<div class="row">
						<div class="panel panel-success">
							<div class="panel-heading">
								<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
								<h3 class="panel-title">
									<strong>PUBLISH A TICKET</strong>
								</h3>
							</div>
							<div class="panel-body">
								<div class="form-group col-md-12">
									<h4 class="text-success">PREREQUISITES:</h4>
								</div>
								<div class="form-group col-md-12">
									<div class="form-group col-md-2">
										<label for="publish_type" class="control-label pull-right" style="padding-top:0;">Publish Type&nbsp;&nbsp;</label>
									</div>
									<div class="form-group col-md-10">
										<label class="text-danger" style="font-size:18px; cursor:pointer;"><input type="radio" id="publish_type" name="publish_type" checked="checked" value="public" data-name="publish_type_option" />&nbsp;Public&nbsp;<span class="glyphicon glyphicon-globe"></span></label>&nbsp;
										<label class="text-success" style="font-size:18px; cursor:pointer;"><input type="radio" name="publish_type" value="protected" data-name="publish_type_option" />&nbsp;Protected&nbsp;<span class="glyphicon glyphicon-user"></span></label>
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
													<h4 data-name="checkbox_all" data-type="checkbox_accountant">Accountant</h4>
												</li>
												<li>
													<label> 
														<input type="checkbox" data-name="checkbox_all" data-type="checkbox_accountant" />&nbsp;All
													</label>
												</li>
												<c:forEach var="u" items="${users}">
													<c:if test="${u.user_role=='accountant' && u.id!=userSession.id}">
														<li>
															<label>
																<input type="checkbox" name="useridArray" value="${u.id}" data-type="checkbox_accountant" />&nbsp;${u.user_name}
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
														<input type="checkbox" data-name="checkbox_all" data-type="checkbox_provision" />&nbsp;All
													</label>
												</li>
												<c:forEach var="u" items="${users}">
													<c:if test="${u.user_role=='provision-team' && u.id!=userSession.id}">
														<li>
															<label>
																<input type="checkbox" name="useridArray" value="${u.id}" data-type="checkbox_provision" />&nbsp;${u.user_name}
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
														<input type="checkbox" data-name="checkbox_all" data-type="checkbox_administrator" />&nbsp;All
													</label>
												</li>
												<c:forEach var="u" items="${users}">
													<c:if test="${u.user_role=='administrator' && u.id!=userSession.id}">
														<li>
															<label>
																<input type="checkbox" name="useridArray" value="${u.id}" data-type="checkbox_administrator" />&nbsp;${u.user_name}
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
														<input type="checkbox" data-name="checkbox_all" data-type="checkbox_developer" />&nbsp;All
													</label>
												</li>
												<c:forEach var="u" items="${users}">
													<c:if test="${u.user_role=='system-developer' && u.id!=userSession.id}">
														<li>
															<label>
																<input type="checkbox" name="useridArray" value="${u.id}" data-type="checkbox_developer" />&nbsp;${u.user_name}
															</label>
														</li>
													</c:if>
												</c:forEach>
											</ul>
										</div>
									</div>
								</div>
								<div class="col-md-4">
									<span class="form-group" data-name="message-customer-userids" style="font-weight:bold;">&nbsp;</span>
								</div>
								<div class="form-group col-md-12">
									<hr/>
								</div>
								<div class="form-group col-md-12">
									<div class="form-group col-md-2">
										<label for="customer_type" class="control-label pull-right" style="padding-top:0;">Customer Type&nbsp;&nbsp;</label>
									</div>
									<div class="form-group col-md-10">
										<label class="text-success" style="font-size:18px; cursor:pointer;"><input type="radio" id="customer_type" name="existing_customer" value="true" data-name="existing_customer_option" checked="checked" />&nbsp;Existing Customer&nbsp;<span class="glyphicon glyphicon-search"></span></label>&nbsp;
										<label class="text-warning" style="font-size:18px; cursor:pointer;"><input type="radio" name="existing_customer" value="false" data-name="existing_customer_option" />&nbsp;New Customer&nbsp;<span class="glyphicon glyphicon-pencil"></span></label>
									</div>
								</div>
								<div class="form-group col-md-12" data-name="keyword_area">
									<div class="form-group col-md-12">
										<span class="col-md-offset-2" style="color:green;">Keywords Includes: First/Last Name, Customer/Order/Invoice Id, Email, Mobile, PSTN/VoIP/ASID No., Address</span>
									</div>
									<div class="form-group col-md-2">
										<label for="keyword" class="control-label pull-right">Keyword&nbsp;&nbsp;</label>
									</div>
									<div class="form-group col-md-2">
										<input type="text" id="keyword" class="form-control col-md-2 input-xs"/>
									</div>
									<div class="form-group col-md-2">
										&nbsp;&nbsp;<a href="javascript:void(0);" data-name="check-customer-existence">MANUAL CHECK</a>
									</div>
									<div class="form-group col-md-6">
										&nbsp;&nbsp;
										<span data-name="message-customer-check" style="font-weight:bold;"></span>
									</div>
								</div>
								<div class="form-group col-md-12">
									<hr/>
								</div>
								<div class="form-group col-md-12" id="customer_detail_area" style="display:none;">
									<div class="form-group col-md-12">
										<div class="form-group col-md-12">
											<h4 class="text-success">CUSTOMER DETAILS & DESCRIPTIONS:</h4>
										</div>
									</div>
									<div class="form-group col-md-12" id="customer_id_area" style="display:none;">
										<div class="form-group col-md-2">
											<label for="customer_id" class="control-label pull-right">Customer Id&nbsp;&nbsp;</label>
										</div>
										<div class="form-group col-md-2">
											<input type="text" id="customer_id" class="form-control col-md-2 input-xs"/>
										</div>
									</div>
									<div class="form-group col-md-12">
										<div class="form-group col-md-2">
											<label for="first_name" class="control-label pull-right">First Name&nbsp;&nbsp;</label>
										</div>
										<div class="form-group col-md-2">
											<input type="text" id="first_name" class="form-control col-md-2 input-xs" />
										</div>
										<div class="form-group col-md-2">
											<label for="last_name" class="control-label pull-right">Last Name&nbsp;&nbsp;</label>
										</div>
										<div class="form-group col-md-2">
											<input type="text" id="last_name" class="form-control col-md-2 input-xs" />
										</div>
										<div class="form-group col-md-4">
											&nbsp;&nbsp;
											<span data-name="message-customer-name" style="font-weight:bold;"></span>
										</div>
									</div>
									<div class="form-group col-md-12">
										<div class="form-group col-md-2">
											<label for="cellphone" class="control-label pull-right">Cellphone&nbsp;&nbsp;</label>
										</div>
										<div class="form-group col-md-2">
											<input type="text" id="cellphone" class="form-control col-md-2 input-xs" />
										</div>
										<div class="form-group col-md-2">
											<label for="email" class="control-label pull-right">Email&nbsp;&nbsp;</label>
										</div>
										<div class="form-group col-md-3">
											<input type="text" id="email" class="form-control input-xs" />
										</div>
										<div class="form-group col-md-4">
											&nbsp;&nbsp;
											<span data-name="message-customer-contact" style="font-weight:bold;"></span>
										</div>
									</div>
									<div class="form-group col-md-12">
										<div class="form-group col-md-2">
											<label for="description" class="control-label pull-right">Ticket Type&nbsp;&nbsp;</label>
										</div>
										<div class="form-group col-md-6">
											<select id="ticket_type" class="control-label" style="padding:4px 0; width:300px;">
												<option value="faulty">Faulty</option>
												<option value="billing">Billing</option>
												<option value="hardware-issue">Hardware Issue</option>
												<option value="application">Application</option>
												<option value="booking-appointment">Booking Appointment</option>
												<option value="announcement">Announcement</option>
												<option value="voip">VoIP</option>
											</select>
										</div>
									</div>
									<div class="form-group col-md-12">
										<div class="form-group col-md-2">
											<label for="description" class="control-label pull-right">Description&nbsp;&nbsp;</label>
										</div>
										<div class="form-group col-md-6">
											<textarea id="description" class="form-control" rows="8"></textarea>
										</div>
										<div class="form-group col-md-4">
											&nbsp;&nbsp;
											<span data-name="message-customer-description" style="font-weight:bold;"></span>
										</div>
									</div>
								</div>
								<br/>
							</div>
							<div class="panel-footer col-md-12">
								<button id="publish_btn" type="button" class="btn btn-sm btn-success col-md-offset-5" style="display:none; width:200px;">Publish</button>
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
</form>

<script type="text/html" id="ticket_table_tmpl">
<jsp:include page="ticket-view-page.html" />
</script>
<jsp:include page="../footer.jsp" />
<jsp:include page="../script.jsp" />
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/bootstrap-select.min.js"></script>
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/icheck.min.js"></script>
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/jTmpl.js"></script>
<script type="text/javascript">
(function($) {
	
	function doPage(pageNo) {
		$.get('${ctx}/broadband-user/crm/ticket/view/' + pageNo, function(page){ //console.log(page);
		
			page.ctx = '${ctx}';
			page.users = [];
			
			<c:forEach var="u" items="${users}">
				var u = new Object();
				u.id = '${u.id}';
				u.user_name = '${u.user_name}';
				page.users.push(u);
			</c:forEach>
			
	   		var $table = $('#ticket-table');
			$table.html(tmpl('ticket_table_tmpl', page));
			$table.find('tfoot a').click(function(){
				doPage($(this).attr('data-pageNo'));
			});
			
			$('#checkbox_ts_top').click(function(){
				var b = $(this).prop("checked");
				if (b) $('input[name="checkbox_ts"]').prop("checked", true);
				else $('input[name="checkbox_ts"]').prop("checked", false);
			});
			
			$('span[data-toggle="tooltip"]').tooltip();
			
	   	});
	}
	doPage(1);

	$('#filter_operations').change(function(){
		var $this = $(this);
		var ticket = {				
			publish_type: $this.find('option[data-type="publish-type"]:selected').val() || null
			, existing_customer_str: $this.find('option[data-type="existing-customer-str"]:selected').val() || null
			, ticket_type: $this.find('option[data-type="ticket-type"]:selected').val() || null
			, not_yet_viewer: $this.find('option[data-type="ticket-status"]:selected').val() || null
			, user_id: $this.find('option[data-type="my-id"]:selected').val() || null
			, commented_str: $this.find('option[data-type="commented-str"]:selected').val() || null
		};
		console.log(ticket);
		
		$.get('${ctx}/broadband-user/crm/ticket/view/filter', ticket, function(){
			doPage(1);
		});
	});
	
	$('#filter_operations').find('option').each(function(){
		if (this.value == '${ticketFilter.publish_type}' 
			|| this.value == '${ticketFilter.existing_customer_str}'
			|| this.value == '${ticketFilter.ticket_type}'
			|| this.value == '${ticketFilter.not_yet_viewer}'
			|| this.value == '${ticketFilter.user_id}'
			|| this.value == '${ticketFilter.commented_str}' ) {
			$(this).attr("selected", "selected");
			$('.selectpicker').selectpicker('refresh');
		}
	});
	
	$('.selectpicker').selectpicker();
	
	$(':radio,:checkbox').iCheck({
		checkboxClass : 'icheckbox_square-green',
		radioClass : 'iradio_square-green'
	});
	
	/*
	 *
	 *	BEGIN Publish New Ticket
	 *
	 */
	$('button[data-name="publish_ticket"]').click(function(){
		$('#publishTicketModal').modal('show');
	});
	
	$('input[data-name="checkbox_all"]').on("ifChecked", function(){
		var type = $(this).attr("data-type");
		$('span[data-name="message-customer-userids"]').html('&nbsp;');
		$('input[data-type='+type+']').iCheck("check");
	});
	
	$('input[data-name="checkbox_all"]').on("ifUnchecked", function(){
		var type = $(this).attr("data-type");
		$('input[data-type='+type+']').iCheck("uncheck");
	});

	$('input[data-name="publish_type_option"]').on("ifChecked", function(){
		var type = $(this).val();
		if(type=="public"){
			$('span[data-name="message-customer-userids"]').html('&nbsp;');
			$('div[data-name="at_to"]').css("display", "none");
		} else {
			$('div[data-name="at_to"]').css("display", "");
		}
	});

	$('input[data-name="existing_customer_option"]').on("ifChecked", function(){
		$('span[data-name="message-customer-name"]').html('&nbsp;');
		$('span[data-name="message-customer-contact"]').html('&nbsp;');
		$('span[data-name="message-customer-description"]').html('&nbsp;');
		var type = $(this).val();
		if(type=="false"){
			$('#customer_detail_area').css("display", "");
			$('#publish_btn').css("display", "");
			$('div[data-name="keyword_area"]').css("display", "none");
			
			/* Reset */
			$('#first_name').removeAttr('disabled');
			$('#last_name').removeAttr('disabled');
			$('#cellphone').removeAttr('disabled');
			$('#email').removeAttr('disabled');
			$('#customer_id_area').css('display', 'none');
			$('#first_name').val('');
			$('#last_name').val('');
			$('#cellphone').val('');
			$('#email').val('');
		} else {
			if($('#customer_id').val()==''){
				$('#customer_detail_area').css("display", "none");
				$('#publish_btn').css("display", "none");
			}
			$('div[data-name="keyword_area"]').css("display", "");
			searchCustomerByKeyword("click");
		}
	});
	
	$('#keyword').keyup(function(){
		searchCustomerByKeyword("keyup");
	});
	
	$('#keyword').blur(function(){
		searchCustomerByKeyword("blur");
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
			if(json.errorMap && json.errorMap.customerNull){
				
				if(way=='click' || way=='blur'){
					$('span[data-name="message-customer-check"]').html('<span class="glyphicon glyphicon-remove"></span>&nbsp;'+json.errorMap.customerNull);
					$('span[data-name="message-customer-check"]').css('color', 'red');
				} else {
					$('span[data-name="message-customer-check"]').html('&nbsp;');
				}
				
				/* Reset */
				$('#first_name').removeAttr('disabled');
				$('#last_name').removeAttr('disabled');
				$('#cellphone').removeAttr('disabled');
				$('#email').removeAttr('disabled');
				$('#customer_id_area').css('display', 'none');
				$('#first_name').val('');
				$('#last_name').val('');
				$('#cellphone').val('');
				$('#email').val('');
				
				if(way=='keyup'){
					$('#customer_detail_area').css("display", "none");
					$('#publish_btn').css("display", "none");
					$('#customer_id').val('');
				}
			} else if(json.successMap && json.successMap.customerNotNull) {
				/* Message */
				$('span[data-name="message-customer-check"]').html('<span class="glyphicon glyphicon-ok"></span>&nbsp;'+json.successMap.customerNotNull);
				$('span[data-name="message-customer-check"]').css('color', 'green');
				
				/* Retrieving Essential Details */
				$('#customer_id').attr('disabled', 'disabled');
				$('#first_name').attr('disabled', 'disabled');
				$('#last_name').attr('disabled', 'disabled');
				$('#cellphone').attr('disabled', 'disabled');
				$('#email').attr('disabled', 'disabled');
				$('#customer_id_area').css('display', '');
				$('#customer_id').val(json.model.customer_id);
				$('#first_name').val(json.model.first_name);
				$('#last_name').val(json.model.last_name);
				$('#cellphone').val(json.model.mobile);
				$('#email').val(json.model.email);

				if(way=='keyup'){
					$('#customer_detail_area').css("display", "");
					$('#publish_btn').css("display", "");
				}
			}
			json = null;
		})
	};
	
	$('#publish_btn').click(function(){
		var publish_type = $('input[name="publish_type"]:checked').val();
		var existing_customer = $('input[name="existing_customer"]:checked').val();
		var customer_id = $('#customer_id').val();
		var first_name = $('#first_name').val();
		var last_name = $('#last_name').val();
		var cellphone = $('#cellphone').val();
		var email = $('#email').val();
		var ticket_type = $('#ticket_type option:selected').val();
		var description = $('#description').val();
		var useridArray = [];
		var useridArrayTemp = $('input[name="useridArray"]:checked').each(function(){
			useridArray.push($(this).val());
		});
		
		var ticket = {
			'publish_type': publish_type,
			'customer_id': customer_id,
			'existing_customer': existing_customer,
			'first_name': first_name,
			'last_name': last_name,
			'cellphone': cellphone,
			'email': email,
			'ticket_type': ticket_type,
			'description': description,
			'useridArray':useridArray
		};
		
		$.ajax({
			type: 'post'
			, contentType:'application/json;charset=UTF-8'         
	   		, url: '${ctx}/broadband-user/crm/ticket/create'
		   	, data: JSON.stringify(ticket)
		   	, dataType: 'json'
		   	, success: function(json){
				if(json.url){
					$('span[data-name="message-customer-name"]').html('&nbsp;');
					$('span[data-name="message-customer-contact"]').html('&nbsp;');
					$('span[data-name="message-customer-description"]').html('&nbsp;');
					$('span[data-name="message-customer-userids"]').html('&nbsp;');
					
					window.location.href = '${ctx}' + json.url;
				} else {
					$('span[data-name="message-customer-name"]').html('&nbsp;');
					$('span[data-name="message-customer-contact"]').html('&nbsp;');
					$('span[data-name="message-customer-description"]').html('&nbsp;');
					$('span[data-name="message-customer-userids"]').html('&nbsp;');
					if(json.errorMap.emptyContactName){
						$('span[data-name="message-customer-name"]').html('<span class="glyphicon glyphicon-remove"></span>&nbsp;'+json.errorMap.emptyContactName);
						$('span[data-name="message-customer-name"]').css('color', 'red');
					}
					if(json.errorMap.emptyContactDetail){
						$('span[data-name="message-customer-contact"]').html('<span class="glyphicon glyphicon-remove"></span>&nbsp;'+json.errorMap.emptyContactDetail);
						$('span[data-name="message-customer-contact"]').css('color', 'red');
					}
					if(json.errorMap.emptyDescription){
						$('span[data-name="message-customer-description"]').html('<span class="glyphicon glyphicon-remove"></span>&nbsp;'+json.errorMap.emptyDescription);
						$('span[data-name="message-customer-description"]').css('color', 'red');
					}
					if(json.errorMap.emptyUseridArray){
						$('span[data-name="message-customer-userids"]').html('<span class="glyphicon glyphicon-remove"></span>&nbsp;'+json.errorMap.emptyUseridArray);
						$('span[data-name="message-customer-userids"]').css('color', 'red');
					}
				}
				
				json = null;
		   	}
		});
	});
	/*
	 *
	 *	END Publish New Ticket
	 *
	 */
	 
	 
	
})(jQuery);
</script>
<jsp:include page="../footer-end.jsp" />