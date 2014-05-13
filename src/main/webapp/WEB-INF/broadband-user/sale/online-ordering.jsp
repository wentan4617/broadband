<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %> 
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<jsp:include page="header.jsp" />
<jsp:include page="../alert.jsp" />

<style>
.modal {
	z-index: 140;
}
.modal-backdrop{
	z-index: 130;
}
</style>

<div class="container">
	<div class="page-header">
		<h1>
			<c:choose>
				<c:when test="${classz=='business' }">
					1. Business Plans & Pricing  
					<a href="${ctx }/broadband-user/sale/online/ordering/plans/personal" ><small>(to Personal Plans)</small></a>
				</c:when>
				<c:when test="${classz=='personal' }">
					1. Personal Plans & Pricing  
					<a href="${ctx }/broadband-user/sale/online/ordering/plans/business" ><small>(to Business Plans)</small></a>
				</c:when>
			</c:choose>
		</h1>
	</div>
	
	<!--  -->
	<div class="panel-group" id="accordion">
	
		<c:forEach var="type" items="adsl,vdsl,ufb" varStatus="item">
		
			<div class="panel panel-success">
				<div class="panel-heading">
					<h4 class="panel-title">
						<a data-toggle="collapse" data-toggle="collapse" data-parent="#accordion" href="#collapse${fn:toUpperCase(type) }">
							${fn:toUpperCase(type)} Plans
						</a>
					</h4>
				</div>
				<div id="collapse${fn:toUpperCase(type)}" class="panel-collapse collapse ${item.first?'in':'' }">
					<div class="panel-body">
					
						<c:choose>
							<c:when test="${classz=='business' }">
								<h3 class="bg-primary text-center" style="width:200px;">
									<strong>T&C</strong> 
								</h3>
								<ul class=" text-info">
									<li><strong>Early Termination Fee(ETC): NZ$299 completed less than 12 months; NZ$199 completed more than 12 months</strong></li>
									<li>
										<strong>
											Transition from previous Internet Service Provider for fee. <br/>
											(processing date: around 3-7 working days, if base on UCLL number transferring takes around 5-10 working days)
										</strong>
									</li>
									<li><strong>New connection fee: NZ$145+GST (processing date: around 5-10 working days)</strong></li>
									<li><strong>New Installation Fee (Jack port): NZ$199+GST (processing date: around 5-15 working days)</strong></li>
									<li>
										<strong>
											Home number can keep if number under POTS condition, or extra NZ$99+GST for transferring number from UCLL system<br/>
											(eg, Vodafone, Orcon, Slingshort, woosh may use UCLL)
										</strong>
									</li>
									<li><strong>Application and Direct Debt form are required necessarily</strong></li>
								</ul>
							</c:when>
							<c:when test="${classz=='personal' }">
								<h3 class="bg-primary text-center" style="width:200px;">
									<strong>T&C</strong> 
								</h3>
								<ul class=" text-info">
									<li><strong>Early Termination Fee(ETC): NZ$199 completed less than 6 months; NZ$99 completed more than 6 months</strong></li>
									<li>
										<strong>
											Transition from previous Internet Service Provider for fee. <br/>
											(processing date: around 3-7 working days, if base on UCLL number transferring takes around 5-10 working days)
										</strong>
									</li>
									<li><strong>New connection fee: NZ$145+GST (processing date: around 5-10 working days)</strong></li>
									<li><strong>New Installation Fee (Jack port): NZ$199+GST (processing date: around 5-15 working days)</strong></li>
									<li>
										<strong>
											Home number can keep if number under POTS condition, or extra NZ$99+GST for transferring number from UCLL system<br/>
											(eg, Vodafone, Orcon, Slingshort, woosh may use UCLL)
										</strong>
									</li>
									<li><strong>Application and Direct Debt form are required necessarily</strong></li>
								</ul>
							</c:when>
						</c:choose>
						
						
						
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
							<c:if test="${plan.plan_type == fn:toUpperCase(type) }">
								<tr data-name="${type }_tr" data-value="${plan.id }" data-pstn-count="${plan.pstn_count }">
									<td>
										<input type="radio" name="${type }_id" value="${plan.id }" data-pstn-count="${plan.pstn_count }" />
									</td>
									<td>${plan.plan_name }</td>
									<td>
										<c:choose>
											<c:when test="${plan.data_flow < 0 }">Unlimited Data</c:when>
											<c:otherwise>${plan.data_flow } GB</c:otherwise>
										</c:choose>
									</td>
									<td>${plan.pstn_count }</td>
									<td>${plan.term_period }</td>
									<td>
										<fmt:formatNumber value="${plan.plan_price }" type="number" pattern="#,##0.00" />
									</td>
								</tr>
							</c:if>
						</c:forEach>
					</table>
					
					<div class="panel-body" id="${type }-addons-panel" style="display:none;">
					
						<!-- operator -->
						<div class="row" id="${type }-btnContainer" style="display:none;">
							<div class="col-sm-12">
								<div class="btn-group" style="width:200px;">
								  	<button type="button" class="btn btn-success dropdown-toggle btn-lg btn-block" data-toggle="dropdown">
								    	Add-ons <span class="caret"></span>
								 	</button>
								  	<ul class="dropdown-menu" >
								    	<li><a href="javascript:void(0);" data-type="${type }" data-sub-type="hardware" data-add-btn style="width:200px;font-size:16px;">Add Hardware</a></li>
								    	<li><a href="javascript:void(0);" data-type="${type }" data-sub-type="pstn" data-add-btn style="width:200px;font-size:16px;">Add PSTN</a></li>
								    	<li><a href="javascript:void(0);" data-type="${type }" data-sub-type="voip" data-add-btn style="width:200px;font-size:16px;">Add Voip</a></li>
								  	</ul>
								</div>
							</div>
						</div>
						
						<form class="form-horizontal">
						
						<!-- hardware  -->
						<div id="${type }-hardwareContainer" style="display:none;">
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
						<div id="${type }-pstnContainer" style="display:none;">
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
						<div id="${type }-voipContainer" style="display:none;">
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
								<button type="button" class="btn btn-success btn-lg btn-block" data-order data-type="${type }">Order</button>
							</div>
						</div>
					</div>
				</div>
			</div>
			
		</c:forEach>
	
		
	</div>
</div>

<!-- Check Address Modal -->
<div class="modal fade" id="checkAddressModal" tabindex="-1" role="dialog" aria-labelledby="checkAddressModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="margin-top:55px;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="checkAddressModalLabel">Check your address whether the service can be installed</h4>
			</div>
			<div class="modal-body">
				<div class="row">
					<div class="col-md-12">
						<div class="input-group">
							<input id="address" type="text" class="form-control input-lg" placeholder="Put your address here" /> 
							<span class="input-group-btn">
								<button class="btn btn-success btn-lg ladda-button" data-style="zoom-in" type="button" id="goCheck">
									<span class="ladda-label">Go</span>
								</button>
							</span>
						</div>
					</div>
				</div>
			</div>
			<div id="checkResult"></div>
		</div> <!-- /.modal-content -->
	</div> <!-- /.modal-dialog -->
</div> <!-- /.modal -->

<div id="map_canvas" style="width:720px;height:600px;display:none;"></div>
<script type="text/html" id="result_tmpl">
<jsp:include page="resultAddressCheck.html" />
</script>

<jsp:include page="footer.jsp" />
<jsp:include page="script.jsp" />
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/icheck.min.js"></script>
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/bootstrap-select.min.js"></script>
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/jTmpl.js"></script>
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/spin.min.js"></script>
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/ladda.min.js"></script>
<script type="text/javascript">
(function($){
	
	var classz = '${classz}';
	
	$(':radio').iCheck({
		checkboxClass : 'icheckbox_square-green',
		radioClass : 'iradio_square-green'
	});
	
	$('tr[data-name*="_tr"]').click(function(){
		var type = $(this).attr('data-name').replace('_tr', '');
		$('tr[data-name="' + type + '_tr"]').removeClass('success');
		$('input[name="' + type + '_id"]').iCheck('uncheck');
		$(this).addClass('success');
		var data_value = $(this).attr('data-value');
		$('input[name="' + type + '_id"][value="' + data_value + '"]').iCheck('check');
	});
	
	$('input[name*="_id"]').on('ifChecked', function(){
		var type = this.name.replace('_id', '');
		$('#' + type + '-addons-panel').css('display', 'block');
		hideTypeContainer(type, 'hardware');
		hideTypeContainer(type, 'pstn');
		hideTypeContainer(type, 'voip');
		showbtns(type);
		$('button[data-order][data-type="' + type + '"]').attr('data-plan-id', this.value);
		$('tr[data-name="' + type + '_tr"]').removeClass('success');
		$('tr[data-value="' + this.value + '"]').addClass('success');
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
	
	function hideTypeContainer(type, type_remove) {
		var len = $('div[data-' + type + '-' + type_remove + '-enable]').length;
		if (len == 0) {
			$('#' + type + '-' + type_remove + 'Container').css('display', 'none');
		}
	}
	
	function addHardware(type) {
		$('#' + type + '-hardwareContainer').css('display', 'block');
		var index = $('div[data-' + type + '-enable]').length;
		var html = "";
		html += '<div class="form-group" id="' + type + '_hardware_' + index + '" data-' + type + '-enable data-' + type + '-hardware-enable>';
		html += '	<div class="col-sm-1"></div>';
		html += '	<div class="col-sm-5">';
		html += '		<select name="detail_name" class="selectpicker show-tick form-control" data-name="harewareSelect">';
		<c:forEach var="hd" items="${hardwares }">
		html += '<option value="${hd.hardware_name}" data-hd-price="${hd.hardware_price}" data-target="">${hd.hardware_name}</option>';
		</c:forEach>
		html += '		</select>';
		html += '		<input type="hidden" name="detail_type" value="hardware-router" />';
		html += '	</div>';
		html += '	<div class="col-sm-3">';
		html += '		<div class="input-group">';
		html += '			<span class="input-group-addon">$</span>';
		html += '			<input type="text" name="detail_price" class="form-control" value="0.00"/>';
		html += '		</div>';
		html += '	</div>';
		html += '	<div class="col-sm-2">';
		html += '		<input type="text" class="form-control" name="detail_unit" value="1"/>';
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
		$('#' + type + '-pstnContainer').css('display', 'block');
		var index = $('div[data-' + type + '-enable]').length;
		var html = "";
		html += '<div class="form-group" id="' + type + '_pstn_' + index + '" data-' + type + '-enable data-' + type + '-pstn-enable>';
		html += '	<div class="col-sm-1"></div>';
		html += '	<div class="col-sm-4">';
		if (classz == 'business') {
			html += '	<label class="control-label">Business Phone Line</label>';
			html += '	<input type="hidden" name="detail_name" value="Business Phone Line" />';
		} else if (classz == 'personal') {
			html += '	<label class="control-label">Home Phone Line</label>';
			html += '	<input type="hidden" name="detail_name" value="Home Phone Line" />';
		}
		html += '		<input type="hidden" name="detail_type" value="pstn" />';
		html += '	</div>';
		html += '	<div class="col-sm-4">';
		html += '		<div class="input-group">';
		html += '			<select name="pstn_number1" class="form-control">';
		html += '				<option value="09">09</option>';
		html += '				<option value="07">07</option>';
		html += '				<option value="06">06</option>';
		html += '				<option value="04">04</option>';
		html += '				<option value="03">03</option>';
		html += '			</select>';
		html += '			<span class="input-group-addon">-</span>';
		html += '			<input type="text"  class="form-control col-sm-8" name="pstn_number2" placeholder="e.g.:5789941"/>';
		html += '		</div>';
		html += '	</div>';
		html += '	<div class="col-sm-2">';
		html += '		<div class="input-group">';
		html += '			<span class="input-group-addon">$</span>';
		html += '			<input type="text"  class="form-control" name="detail_price" value="45.00"/>';
		html += '		</div>';
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
		html += '	<div class="col-sm-4">';
		html += '		<label class="control-label">Voip</label>';
		html += '		<input type="hidden" name="detail_name" value="Voip" />';
		html += '		<input type="hidden" name="detail_type" value="voip" />';
		html += '	</div>';
		html += '	<div class="col-sm-4">'
		html += ' 		<input type="text" class="form-control col-sm-8" name="pstn_number" placeholder="e.g.:5789941"/>';
		html += '	</div>';
		html += '	<div class="col-sm-2">';
		html += '		<div class="input-group">';
		html += '			<span class="input-group-addon">$</span>';
		html += '			<input type="text"  class="form-control" name="detail_price" value="0.00"/>';
		html += '		</div>';
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
	
	var select_plan_id = "";
	var select_plan_type = "";
	
	$('button[data-order]').click(function(){
		var $btn = $(this);
		select_plan_id = $btn.attr('data-plan-id');
		if (!select_plan_id) {
			alert('Please choose one plan at least.');
			return false;
		}
		select_plan_type = $btn.attr('data-type');//console.log(select_plan_id);
		$('#checkResult').empty();
		$('#checkAddressModal').modal('show');
	});
	
	$('#goCheck').click(function(){
		var address = $('#address').val();
		address = $.trim(address.replace(/[\/]/g,' ').replace(/[\\]/g,' ')); //console.log(address);
		if (address != '') {
			var l = Ladda.create(this);
		 	l.start();
			$.get('${ctx}/sale/address/check/' + address, function(broadband){
				//broadband.href = '${ctx}/order/' + select_plan_id;
				broadband.type = select_plan_type;
				$('#checkResult').html(tmpl('result_tmpl', broadband));
				$('a[data-toggle="tooltip"]').tooltip();
				$('#continue-selected-plan').click(function(){
					submitOrder(select_plan_type, $(this));
				});
		   	}).always(function(){ l.stop(); });
		} else {
			alert('Please enter a real address.');
		}
	});
	
	function submitOrder(type, $btn){
		var cods = [];
		$('div[data-' + type + '-hardware-enable]').each(function(){
			var $this = $(this);
			var cod = {
				detail_name: $this.find('select[name="detail_name"]').val()
				, detail_type: $this.find('input[name="detail_type"]').val()
				, detail_price: $this.find('input[name="detail_price"]').val()
				, detail_unit: $this.find('input[name="detail_unit"]').val()
			}; //customerOrder.customerOrderDetails.push(cod);
			cods.push(cod);
		});
		$('div[data-' + type + '-pstn-enable]').each(function(){
			var $this = $(this);
			var cod = {
				detail_name: $this.find('input[name="detail_name"]').val()
				, detail_type: $this.find('input[name="detail_type"]').val()
				, detail_price: $this.find('input[name="detail_price"]').val()
				, detail_unit: 1
				, pstn_number: $this.find('select[name="pstn_number1"]').val() + '' + $this.find('input[name="pstn_number2"]').val()
			}; //customerOrder.customerOrderDetails.push(cod);
			cods.push(cod);
		});
		$('div[data-' + type + '-voip-enable]').each(function(){
			var $this = $(this);
			var cod = {
				detail_name: $this.find('input[name="detail_name"]').val()
				, detail_type: $this.find('input[name="detail_type"]').val()
				, detail_price: $this.find('input[name="detail_price"]').val()
				, detail_unit: 1
				, pstn_number: $this.find('input[name="pstn_number"]').val()
			}; //customerOrder.customerOrderDetails.push(cod);
			cods.push(cod);
		});
		
		//console.log(JSON.stringify(cods));
		
		$btn.button('loading');
		
		$.ajax({
			type: 'post'
			, contentType:'application/json;charset=UTF-8'         
	   		, url: '${ctx}/broadband-user/sale/online/ordering/order/details'
		   	, data: JSON.stringify(cods)
		   	, dataType: 'json'
		   	, success: function(json){
				if (json.hasErrors) {
					$.jsonValidation(json);
				} else { //console.log(json);
					window.location.href='${ctx}' + json.url + select_plan_id;
				}
		   	}
		}).always(function () {
			$btn.button('reset');
	    });
		
		
	}

})(jQuery);
</script>
<script src="https://maps.google.com/maps/api/js?sensor=false&libraries=places&region=NZ" type="text/javascript"></script>
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/autoCompleteAddress.js"></script>
<jsp:include page="../footer-end.jsp" />