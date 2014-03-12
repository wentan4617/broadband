<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %> 
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<jsp:include page="header.jsp" />

<style>
.topup-list li {
	padding: 10px 20px;
}
.affix {
	width: 261px;
	top:30px;
}
</style>

<div class="container">
	<div class="page-header">
		<h1>
			2. Customer Information <small>Please fill in your personal information, we will contact you</small>
		</h1>
	</div>
	
	<form:form modelAttribute="customer" method="post" action="${ctx}/order" class="form-horizontal">
	<div class="row">
		<div class="col-md-9">
			
			
			<!-- application form -->
			
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">
							<a data-toggle="collapse" data-toggle="collapse" data-parent="#accordion" href="#application">
								Your application form <span class="text-danger">(All fields Required)</span>
							</a>
						</h3>
					</div>
					
					<div id="application" class="panel-collapse collapse in">
						<div class="panel-body">
							
							
								<h4 class="text-success">Create your account</h4>
								<hr/>
								<div class="form-group">
									<label for="login_name" class="control-label col-md-4">Your login name </label>
									<div class="col-md-4">
										<form:input path="login_name" class="form-control" placeholder="e.g.: cyberpark" />
									</div>
									<p class="help-block">
										<form:errors path="login_name" cssErrorClass="error"/>
									</p>
								</div>
								<div class="form-group">
									<label for="password" class="control-label col-md-4">Password</label>
									<div class="col-md-4">
										<form:password path="password" class="form-control" placeholder="" />
									</div>
									<p class="help-block">
										<form:errors path="password" cssErrorClass="error"/>
									</p>
								</div>
								<div class="form-group">
									<label for="ck_password" class="control-label col-md-4">Confirm your password</label>
									<div class="col-md-4">
										<form:password path="ck_password" class="form-control" placeholder="" />
									</div>
									<p class="help-block">
										<form:errors path="ck_password" cssErrorClass="error"/>
									</p>
								</div>
								
								<!-- Broadband Options -->
								<hr/>
								<h4 class="text-success">Broadband Options</h4>
								<hr/>
								<div class="form-group">
									<label for="" class="control-label col-md-4">Broadband Type</label>
									<div class="col-md-4">
										<ul class="list-unstyled topup-list" >
											<li>
												<form:radiobutton path="customerOrder.order_broadband_type" value="new-connection" /> &nbsp;
												<strong>New Connection Only</strong>
											</li>
											<li>
												<form:radiobutton path="customerOrder.order_broadband_type" value="transition" /> &nbsp;
												<strong>Transition</strong>
											</li>
										</ul>
									</div>
									<div class="col-md-4">
										<div class="well">
											<p>If you choose a new connection</p>
											<p> we will charge you </p>
											<p>$ 99 broadband opening costs</p>
										</div>
									</div>
								</div>
								
								
								<!-- Transition Information -->
								<div id="transitionContainer" >
									<hr/>
									<h4 class="text-success">Transition Information</h4>
									<hr/>
									<div class="form-group">
										<label for="" class="control-label col-md-4">Your Current Provider Name</label>
										<div class="col-md-4">
											<form:input path="customerOrder.transition_provider_name" class="form-control" placeholder="" />
										</div>
										<div class="col-md-4">
										</div>
									</div>
									<div class="form-group">
										<label for="" class="control-label col-md-4">Account Holder Name</label>
										<div class="col-md-4">
											<form:input path="customerOrder.transition_account_holder_name" class="form-control" placeholder="" />
										</div>
										<div class="col-md-4">
										</div>
									</div>
									<div class="form-group">
										<label for="" class="control-label col-md-4">Your Current Account Number</label>
										<div class="col-md-4">
											<form:input path="customerOrder.transition_account_number" class="form-control" placeholder="" />
										</div>
										<div class="col-md-4">
										</div>
									</div>
									<div class="form-group">
										<label for="" class="control-label col-md-4">Your Porting Number</label>
										<div class="col-md-4">
											<form:input path="customerOrder.transition_porting_number" class="form-control" placeholder="" />
										</div>
										<div class="col-md-4">
										</div>
									</div>
								
								</div>
								
								
								<!-- Personal Information -->
								<hr/>
								<h4 class="text-success">Personal Information</h4>
								<hr/>
								<div class="form-group">
									<label for="first_name" class="control-label col-md-4">First name</label>
									<div class="col-md-4">
										<form:input path="first_name" class="form-control" placeholder="" />
									</div>
									<p class="help-block">
										<form:errors path="first_name" cssErrorClass="error"/>
									</p>
								</div>
								<div class="form-group">
									<label for="last_name" class="control-label col-md-4">Last name</label>
									<div class="col-md-4">
										<form:input path="last_name" class="form-control" placeholder="" />
									</div>
									<p class="help-block">
										<form:errors path="last_name" cssErrorClass="error"/>
									</p>
								</div>
								<div class="form-group">
									<label for="email" class="control-label col-md-4">Your Email</label>
									<div class="col-md-4">
										<form:input path="email" class="form-control" placeholder="" />
									</div>
									<p class="help-block">
										<form:errors path="email" cssErrorClass="error"/>
									</p>
								</div>
								
								<div class="form-group">
									<label for="cellphone" class="control-label col-md-4">Your Phone</label>
									<div class="col-md-4">
										<form:input path="cellphone" class="form-control" placeholder="" />
									</div>
									<p class="help-block">
										<form:errors path="cellphone" cssErrorClass="error"/>
									</p>
								</div>
								<div class="form-group">
									<label for="address" class="control-label col-md-4">Your Address</label>
									<div class="col-md-5">
										<form:input path="address" class="form-control" placeholder="" />
									</div>
									<p class="help-block col-md-3" >
										<form:errors path="address" cssErrorClass="error"/>
									</p>
								</div>
								
							
						</div>
					
					</div>
				</div>
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">
							<a data-toggle="collapse" data-toggle="collapse" data-parent="#accordion" href="#hardware">
								Hardware
							</a>
						</h3>
					</div>
					
					<div id="hardware" class="panel-collapse collapse in">
						<div class="panel-body">
							<c:forEach var="hardware" items="${hardwares }" varStatus="item">
								<div class="panel panel-success">
									<div class="panel-body bg-success">
										<h3 class="text-success" style="margin-top:0;"><strong>${hardware.hardware_name }</strong></h3>
									  	<p>${hardware.hardware_desc }</p>
									</div>
									<ul class="list-unstyled topup-list">
										<li>
											<form:radiobutton path="customerOrder.hardwares[${item.index }].id"  data-name="addons" data-id="${hardware.id }" data-price="${hardware.hardware_price }"/> &nbsp;
											<strong>None</strong>
											<span id="none_${hardware.id }" class="text-danger"></span>
										</li>
										<li>
											<form:radiobutton path="customerOrder.hardwares[${item.index }].id" value="${hardware.id }" data-name="addons" data-id="${hardware.id }" data-price="${hardware.hardware_price }" data-hname="${hardware.hardware_name }"/> &nbsp;
											<strong>${hardware.hardware_name }</strong>
											<span id="hp_${hardware.id }" class="text-danger">Add NZ$ [${hardware.hardware_price}]</span>
										</li>
									</ul>
								</div>
								
							</c:forEach>
						</div>
					</div>
				</div>
		
			<!-- hardware -->
		</div>
		<div class="col-md-3" >
			<div data-spy="affix" data-offset-top="150" class="panel panel-success">
				<div class="panel-heading">
					<h3 class="panel-title">Your current purchase of plan</h3>
				</div>
		  		<div  class="panel-body">
		  			<h4 class="text-success">${orderPlan.plan_name }</h4>
		  			<h1 class="text-danger">
		  				NZ$ 
		  				<strong>
		  					<span id="totalPrice">
		  						<c:choose>
		  							<c:when test="${orderPlan.plan_group == 'plan-topup' }">
		  								<fmt:formatNumber value="${orderPlan.topup.topup_fee}" type="number" pattern="#,##0.00" />
		  							</c:when>
		  							<c:otherwise>
		  								<fmt:formatNumber value="${orderPlan.plan_price * orderPlan.plan_prepay_months}" type="number" pattern="#,##0.00" />
		  							</c:otherwise>
		  						</c:choose>
		  						
		  					</span>
		  				</strong>
		  			</h1>
		  			<div id="serviceContainer"></div>
		  			<div id="addonContainer"></div>
		  			<%-- <hr/>
		    		${orderPlan.plan_desc } --%>
		    		<hr/>
					<a href="${ctx }/plans/${fn:toLowerCase(orderPlan.plan_group=='plan-topup'?'t':'p')}" class="btn btn-success">Back</a>&nbsp;
					<button type="submit" class="btn btn-success">Continue</button>
		  		</div>
			</div>
			
		</div>
	</div>
	</form:form>
</div>


<jsp:include page="footer.jsp" />
<jsp:include page="script.jsp" />
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/holder.js"></script>
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/icheck.min.js"></script>
<script type="text/javascript">
(function($) {

	$(':radio').iCheck({
		checkboxClass : 'icheckbox_square-green',
		radioClass : 'iradio_square-green'
	});
	
	var totalprice = Number($('#totalPrice').text());
	var newprice = 0;
	var addonsprice = 0;
	
	function toggleTransitionContainer(val, type) {
		var serviceHtml = "";
		if (val === "new-connection") {
			$('#transitionContainer').hide('fast');
			serviceHtml += '<hr/>';
			serviceHtml += '<p class="text-success"><strong>Services:</strong></p>';
			serviceHtml += '<ul>';
			serviceHtml += '<li><strong class="text-danger">New Connection Only</strong></li>';
			if (type === 'plan-topup') {
				serviceHtml += '<li><strong class="text-danger">Top up NZ$ ${orderPlan.topup.topup_fee}</strong></li>';
			} else {
				serviceHtml += '<li><strong class="text-danger">Prepay ${orderPlan.plan_prepay_months} months</strong></li>';
			}
			
			serviceHtml += '</ul>';
			newprice = Number(${orderPlan.plan_new_connection_fee});
			$('#totalPrice').text((totalprice + newprice + addonsprice).toFixed(2));
		} else if (val === "transition") {
			$('#transitionContainer').show('fast');
			serviceHtml += '<hr/>';
			serviceHtml += '<p class="text-success"><strong>Services:</strong></p>';
			serviceHtml += '<ul>';
			if (type === 'plan-topup') {
				serviceHtml += '<li><strong class="text-danger">Top up NZ$ ${orderPlan.topup.topup_fee}</strong></li>';
			} else {
				serviceHtml += '<li><strong class="text-danger">Prepay ${orderPlan.plan_prepay_months} months</strong></li>';
			}
			serviceHtml += '</ul>';
			newprice = 0;
			$('#totalPrice').text((totalprice + newprice + addonsprice).toFixed(2));
		}
		service(serviceHtml);
	}
	
	function toggleAddonContainer(opt){
		var val = $(opt).val();
		var id = $(opt).attr('data-id');
		var price = $(opt).attr('data-price');
		if (val.length > 0) {
			$('#hp_' + id).text('');
			$('#none_' + id).text('[Subtract NZ$ ' + price + ']');
		} else {
			$('#hp_' + id).text('[Add NZ$ ' + price + ']');
			$('#none_' + id).text('');
		}
		var addonsHtml = "";
		var $addons = $('input[data-name="addons"][value!=""]:checked');
		addonsprice = 0;
		if ($addons.length > 0) {
			addonsHtml += '<hr/>';
			addonsHtml += '<p class="text-success"><strong>Add-ons:</strong></p>';
			addonsHtml += '<ul>';
			$addons.each(function(){
				addonsHtml += '<li><strong class="text-danger">' + $(this).attr('data-hname') + '</strong></li>';
				addonsprice += Number($(this).attr('data-price'));
			});
			addonsHtml += '</ul>';
		} 
		$('#totalPrice').text((totalprice + newprice + addonsprice).toFixed(2));
		addon(addonsHtml);
	}
	
	$('input[data-name="addons"]').on('ifChecked', function(){
		toggleAddonContainer(this);
	});
	
	toggleTransitionContainer('${customer.customerOrder.order_broadband_type}', '${orderPlan.plan_group}');
	$('input[data-name="addons"]:checked').each(function(){
		toggleAddonContainer(this);
	});
	
	
	$('input[name="customerOrder\\.order_broadband_type"]').on('ifChecked', function(){
		
		var val = this.value;
		toggleTransitionContainer(val, '${orderPlan.plan_group}');
	});
	
	function service(html) {
		$('#serviceContainer').html(html);
	}
	
	function addon(html) {
		$('#addonContainer').html(html);
	}
	

})(jQuery);
</script>
<jsp:include page="footer-end.jsp" />