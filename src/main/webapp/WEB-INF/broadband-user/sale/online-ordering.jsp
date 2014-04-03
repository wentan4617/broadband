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
	
	<!--  -->
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
								<tr data-name="adsl_tr" data-value="${plan.id }" data-pstn-count="${plan.pstn_count }">
									<td>
										<input type="radio" name="adsl_id" value="${plan.id }" data-pstn-count="${plan.pstn_count }" />
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
					
					<div class="panel-body" id="adsl-addons-panel" style="display:none;">
					
						<!-- operator -->
						<div class="row" id="adsl-btnContainer" style="display:none;">
							<div class="col-sm-12">
								
								<div class="btn-group" style="width:200px;">
								  	<button type="button" class="btn btn-success dropdown-toggle btn-lg btn-block" data-toggle="dropdown">
								    	Add-ons <span class="caret"></span>
								 	</button>
								  	<ul class="dropdown-menu" role="menu">
								    	<li><a href="javascript:void(0);" data-type="adsl" data-sub-type="hardware" data-add-btn style="width:200px;font-size:16px;">Add Hardware</a></li>
								    	<li><a href="javascript:void(0);" data-type="adsl" data-sub-type="pstn" data-add-btn style="width:200px;font-size:16px;">Add PSTN</a></li>
								    	<li><a href="javascript:void(0);" data-type="adsl" data-sub-type="voip" data-add-btn style="width:200px;font-size:16px;">Add Voip</a></li>
								  	</ul>
								</div>
							</div>
						</div>
						
						
						<form class="form-horizontal">
						
						<!-- hardware  -->
						<div id="adsl-hardwareContainer" style="display:none;">
							<div class="row" >
								<div class="col-sm-12">
									<hr/>
									<h4 class="text-success">
										Add-ons Hardware
									</h4>
									<hr/>
								
								</div>
							</div>
						</div>
						
						<!-- PSTN -->
						<div id="adsl-pstnContainer" style="display:none;">
							<div class="row" >
								<div class="col-sm-12">
									<hr/>
									<h4 class="text-success">
										Add-ons PSTN
									</h4>
									<hr/>
								</div>
							</div>
						</div>
						
						<!-- Voip -->
						<div id="adsl-voipContainer" style="display:none;">
							<div class="row" >
								<div class="col-sm-12">
									<hr/>
									<h4 class="text-success">
										Add-ons Voip
									</h4>
									<hr/>
								</div>
							</div>
						</div>
						
						</form>
					</div>
					
					<div class="panel-body" style="height:100px;">
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
								<tr data-name="vdsl_tr" data-value="${plan.id }" data-pstn-count="${plan.pstn_count }">
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
					
					<div class="panel-body" id="vdsl-addons-panel" style="display:none;">
					
						<!-- operator -->
						<div class="row" id="vdsl-btnContainer" style="display:none;">
							<div class="col-sm-12">
								
								<div class="btn-group" style="width:200px;">
								  	<button type="button" class="btn btn-success dropdown-toggle btn-lg btn-block" data-toggle="dropdown">
								    	Add-ons <span class="caret"></span>
								 	</button>
								  	<ul class="dropdown-menu" role="menu">
								    	<li><a href="javascript:void(0);" data-type="vdsl" data-sub-type="hardware" data-add-btn style="width:200px;font-size:16px;">Add Hardware</a></li>
								    	<li><a href="javascript:void(0);" data-type="vdsl" data-sub-type="pstn" data-add-btn style="width:200px;font-size:16px;">Add PSTN</a></li>
								    	<li><a href="javascript:void(0);" data-type="vdsl" data-sub-type="voip" data-add-btn style="width:200px;font-size:16px;">Add Voip</a></li>
								  	</ul>
								</div>
							</div>
						</div>
						
						
						<form class="form-horizontal">
						
						<!-- hardware  -->
						<div id="vdsl-hardwareContainer" style="display:none;">
							<div class="row" >
								<div class="col-sm-12">
									<hr/>
									<h4 class="text-success">
										Add-ons Hardware
									</h4>
									<hr/>
								
								</div>
							</div>
						</div>
						
						<!-- PSTN -->
						<div id="vdsl-pstnContainer" style="display:none;">
							<div class="row" >
								<div class="col-sm-12">
									<hr/>
									<h4 class="text-success">
										Add-ons PSTN
									</h4>
									<hr/>
								</div>
							</div>
						</div>
						
						<!-- Voip -->
						<div id="vdsl-voipContainer" style="display:none;">
							<div class="row" >
								<div class="col-sm-12">
									<hr/>
									<h4 class="text-success">
										Add-ons Voip
									</h4>
									<hr/>
								</div>
							</div>
						</div>
						
						</form>
					</div>
					<div class="panel-body" style="height:100px;">
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
								<tr data-name="ufb_tr" data-value="${plan.id }" data-pstn-count="${plan.pstn_count }">
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
					
					<div class="panel-body" id="ufb-addons-panel" style="display:none;">
					
						<!-- operator -->
						<div class="row" id="ufb-btnContainer" style="display:none;">
							<div class="col-sm-12">
								
								<div class="btn-group" style="width:200px;">
								  	<button type="button" class="btn btn-success dropdown-toggle btn-lg btn-block" data-toggle="dropdown">
								    	Add-ons <span class="caret"></span>
								 	</button>
								  	<ul class="dropdown-menu" role="menu">
								    	<li><a href="javascript:void(0);" data-type="ufb" data-sub-type="hardware" data-add-btn style="width:200px;font-size:16px;">Add Hardware</a></li>
								    	<li><a href="javascript:void(0);" data-type="ufb" data-sub-type="pstn" data-add-btn style="width:200px;font-size:16px;">Add PSTN</a></li>
								    	<li><a href="javascript:void(0);" data-type="ufb" data-sub-type="voip" data-add-btn style="width:200px;font-size:16px;">Add Voip</a></li>
								  	</ul>
								</div>
							</div>
						</div>
						
						
						<form class="form-horizontal">
						
						<!-- hardware  -->
						<div id="ufb-hardwareContainer" style="display:none;">
							<div class="row" >
								<div class="col-sm-12">
									<hr/>
									<h4 class="text-success">
										Add-ons Hardware
									</h4>
									<hr/>
								
								</div>
							</div>
						</div>
						
						<!-- PSTN -->
						<div id="ufb-pstnContainer" style="display:none;">
							<div class="row" >
								<div class="col-sm-12">
									<hr/>
									<h4 class="text-success">
										Add-ons PSTN
									</h4>
									<hr/>
								</div>
							</div>
						</div>
						
						<!-- Voip -->
						<div id="ufb-voipContainer" style="display:none;">
							<div class="row" >
								<div class="col-sm-12">
									<hr/>
									<h4 class="text-success">
										Add-ons Voip
									</h4>
									<hr/>
								</div>
							</div>
						</div>
						
						</form>
					</div>
					
					<div class="panel-body" style="height:100px;">
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


<jsp:include page="footer.jsp" />
<jsp:include page="script.jsp" />
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/icheck.min.js"></script>
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/bootstrap-select.min.js"></script>
<script type="text/javascript">
(function($){
	$(':radio').iCheck({
		checkboxClass : 'icheckbox_square-green',
		radioClass : 'iradio_square-green'
	});
	
	$('tr[data-name="adsl_tr"]').click(function(){
		$('tr[data-name="adsl_tr"]').removeClass('success');
		$('input[name="adsl_id"]').iCheck('uncheck');
		$(this).addClass('success');
		var value = $(this).attr('data-value');
		$('input[name="adsl_id"][value="' + value + '"]').iCheck('check');
	});
	$('tr[data-name="vdsl_tr"]').click(function(){
		$('tr[data-name="vdsl_tr"]').removeClass('success');
		$('input[name="vdsl_id"]').iCheck('uncheck');
		$(this).addClass('success');
		var value = $(this).attr('data-value');
		$('input[name="vdsl_id"][value="' + value + '"]').iCheck('check');
	});
	$('tr[data-name="ufb_tr"]').click(function(){
		$('tr[data-name="ufb_tr"]').removeClass('success');
		$('input[name="ufb_id"]').iCheck('uncheck');
		$(this).addClass('success');
		var value = $(this).attr('data-value');
		$('input[name="ufb_id"][value="' + value + '"]').iCheck('check');
	});
	
	$('input[name="adsl_id"]').on('ifChecked', function(){
		$('#adsl-addons-panel').css('display', 'block');
		
		$('div[data-adsl-enable]').remove();
		hideTypeContainer('adsl', 'hardware');
		hideTypeContainer('adsl', 'pstn');
		hideTypeContainer('adsl', 'voip');
		
		showbtns('adsl');
		addHardware('adsl');
		addPSTN('adsl');
		addVoip('adsl');
		//$('#adsl_btn').attr('href', '${ctx}/broadband-user/sale/online/ordering/order/' + this.value);
	});
	$('input[name="vdsl_id"]').on('ifChecked', function(){
		$('#vdsl-addons-panel').css('display', 'block');
				
		$('div[data-vdsl-enable]').remove();
		hideTypeContainer('vdsl', 'hardware');
		hideTypeContainer('vdsl', 'pstn');
		hideTypeContainer('vdsl', 'voip');
		
		showbtns('vdsl');
		addHardware('vdsl');
		addPSTN('vdsl');
		addVoip('vdsl');
		//$('#vdsl_btn').attr('href', '${ctx}/broadband-user/sale/online/ordering/order/' + this.value);
	});
	$('input[name="ufb_id"]').on('ifChecked', function(){
		$('#ufb-addons-panel').css('display', 'block');
		
		$('div[data-ufb-enable]').remove();
		hideTypeContainer('ufb', 'hardware');
		hideTypeContainer('ufb', 'pstn');
		hideTypeContainer('ufb', 'voip');
		
		showbtns('ufb');
		addHardware('ufb');
		addPSTN('ufb');
		addVoip('ufb');
		//$('#ufb_btn').attr('href', '${ctx}/broadband-user/sale/online/ordering/order/' + this.value);
	});
	
	$('a[data-add-btn]').click(function(){
		var $this = $(this);
		var type = $this.attr('data-type');
		var type_sub = $this.attr('data-sub-type');
		if (type_sub == 'hardware') {
			addHardware(type);
		} else if (type_sub == 'pstn') {
			addPSTN(type);
		} else if (type_sub == 'voip') {
			addVoip(type);
		}
	});
	
	
	function showbtns(type){
		$('#' + type + '-btnContainer').css('display', 'block');
	}
	
	function addHardware(type) {
		$('#' + type + '-hardwareContainer').css('display', 'block');
		var index = $('div[data-' + type + '-enable]').length;
		var html = "";
		html += '<div class="form-group" id="' + type + '_hardware_' + index + '" data-' + type + '-enable data-' + type + '-hardware-enable>';
		html += '	<div class="col-sm-1"></div>';
		html += '	<div class="col-sm-5">';
		html += '		<select name="" class="selectpicker show-tick form-control" data-name="harewareSelect">';
		<c:forEach var="hd" items="${hardwares }">
		html += '<option value="${hd.hardware_name}" data-hd-price="${hd.hardware_price}" data-target="">${hd.hardware_name}</option>';
		</c:forEach>
		html += '		</select>';
		html += '	</div>';
		html += '	<div class="col-sm-3">';
		html += '		<div class="input-group">';
		html += '			<span class="input-group-addon">$</span>';
		html += '			<input type="text"  class="form-control" />';
		html += '		</div>';
		html += '	</div>';
		html += '	<div class="col-sm-2">';
		html += '		<input type="text" class="form-control" />';
		html += '	</div>';
		html += '	<div class="col-sm-1">';
		html += '		<a href="javascript:void(0);" class="btn btn-default" data-index="' + index+ '" data-type="' + type + '" data-sub-type="hardware" data-name="remove">';
		html += '			<span class="glyphicon glyphicon-remove"></span>';
		html += '		</a>';
		html += '	</div>';
		html += '</div>';
		$('#' + type + '-hardwareContainer').append(html);
		$('.selectpicker').selectpicker('refresh'); 
		
		$('#' + type + '-hardwareContainer').delegate('a[data-name="remove"]', 'click', function(){
			var $this = $(this);
			var index = $this.attr('data-index');
			var type = $this.attr('data-type');
			var type_sub = $this.attr('data-sub-type');
			$('#' + type + '_' + type_sub + '_' + index).remove();
			hideTypeContainer(type, type_sub);
		});
	}

	
	function addPSTN(type) {
		//alert(type);
		$('#' + type + '-pstnContainer').css('display', 'block');
		var index = $('div[data-' + type + '-enable]').length;
		var html = "";
		html += '<div class="form-group" id="' + type + '_pstn_' + index + '" data-' + type + '-enable data-' + type + '-pstn-enable>';
		html += '	<div class="col-sm-1"></div>';
		html += '	<div class="col-sm-5">';
		html += '		<input type="text" class="form-control" />';
		html += '	</div>';
		html += '	<div class="col-sm-3">';
		html += '		<div class="input-group">';
		html += '			<span class="input-group-addon">$</span>';
		html += '			<input type="text"  class="form-control" />';
		html += '		</div>';
		html += '	</div>';
		html += '	<div class="col-sm-2">';
		html += '		<input type="text" class="form-control" />';
		html += '	</div>';
		html += '	<div class="col-sm-1">';
		html += '		<a href="javascript:void(0);" class="btn btn-default" data-index="' + index+ '" data-type="' + type + '" data-sub-type="pstn" data-name="remove">';
		html += '			<span class="glyphicon glyphicon-remove"></span>';
		html += '		</a>';
		html += '	</div>';
		html += '</div>';
		$('#' + type + '-pstnContainer').append(html);
		$('.selectpicker').selectpicker('refresh'); 
		
		$('#' + type + '-pstnContainer').delegate('a[data-name="remove"]', 'click', function(){
			var $this = $(this);
			var index = $this.attr('data-index');
			var type = $this.attr('data-type');
			var type_sub = $this.attr('data-sub-type');
			$('#' + type + '_' + type_sub + '_' + index).remove();
			hideTypeContainer(type, type_sub);
		});
	}
	
	function addVoip(type){
		$('#' + type + '-voipContainer').css('display', 'block');
		var index = $('div[data-' + type + '-enable]').length;
		var html = "";
		html += '<div class="form-group" id="' + type + '_voip_' + index + '" data-' + type + '-enable data-' + type + '-voip-enable>';
		html += '	<div class="col-sm-1"></div>';
		html += '	<div class="col-sm-5">';
		html += '		<input type="text" class="form-control" />';
		html += '	</div>';
		html += '	<div class="col-sm-3">';
		html += '		<div class="input-group">';
		html += '			<span class="input-group-addon">$</span>';
		html += '			<input type="text"  class="form-control" />';
		html += '		</div>';
		html += '	</div>';
		html += '	<div class="col-sm-2">';
		html += '		<input type="text" class="form-control" />';
		html += '	</div>';
		html += '	<div class="col-sm-1">';
		html += '		<a href="javascript:void(0);" class="btn btn-default" data-index="' + index+ '" data-type="' + type + '" data-sub-type="voip" data-name="remove">';
		html += '			<span class="glyphicon glyphicon-remove"></span>';
		html += '		</a>';
		html += '	</div>';
		html += '</div>';
		$('#' + type + '-voipContainer').append(html);
		$('.selectpicker').selectpicker('refresh'); 
		
		$('#' + type + '-voipContainer').delegate('a[data-name="remove"]', 'click', function(){
			var $this = $(this);
			var index = $this.attr('data-index');
			var type = $this.attr('data-type');
			var type_sub = $this.attr('data-sub-type');
			$('#' + type + '_' + type_sub + '_' + index).remove();
			hideTypeContainer(type, type_sub);
		});
	}
	
	function hideTypeContainer(type, type_remove) {
		var len = $('div[data-' + type + '-' + type_remove + '-enable]').length;
		if (len == 0) {
			$('#' + type + '-' + type_remove + 'Container').css('display', 'none');
		}
	}
	
	
})(jQuery);
</script>
<jsp:include page="../footer-end.jsp" />