<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<jsp:include page="../header.jsp" />
<jsp:include page="../alert.jsp" />

<div class="container">
	<div class="row">
		<div class="col-md-12">
			<div class="panel panel-success">
				<div class="panel-heading"><h4 class="panel-title">${panelheading }</h4></div>
				<div class="panel-body">
					<form class="form-horizontal">
						<div class="form-group">
							<label for="hardware_name" class="control-label col-md-4">Hardware Name</label>
							<div class="col-md-8">
								<input type="text" value="${hardware.hardware_name}" id="hardware_name" class="form-control" placeholder="Hardware Name" />
							</div>
						</div>
						
						<div class="form-group">
							<label for="hardware_type" class="control-label col-md-4">Hardware Type</label>
							<div class="col-md-3">
								<select id="hardware_type" class="form-control">
									<option value="router" ${hardware.hardware_type == 'router' ? 'selected="selected"' : ''}>Router</option>
									<option value="accessory" ${hardware.hardware_type == 'accessory' ? 'selected="selected"' : ''}>Accessory</option>
									<option value="touch-pad" ${hardware.hardware_type == 'touch-pad' ? 'selected="selected"' : ''}>Touch Pad</option>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label for="hardware_class" class="control-label col-md-4"></label>
							<div class="col-md-3">
								<div class="checkbox">
									<label> 
										<input type="checkbox" id="router_adsl" ${hardware.router_adsl?'checked="checked"':'' }> Router ADSL
									</label>
								</div>
								<div class="checkbox">
									<label> 
										<input type="checkbox" id="router_vdsl" ${hardware.router_vdsl?'checked="checked"':'' }> Router VDSL
									</label>
								</div>
								<div class="checkbox">
									<label> 
										<input type="checkbox" id="router_ufb" ${hardware.router_ufb?'checked="checked"':'' }> Router UFB
									</label>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label for="hardware_class" class="control-label col-md-4"></label>
							<div class="col-md-3">
								<%-- <div class="checkbox">
									<label> 
										<input type="checkbox" id="support_pstn" ${hardware.support_pstn?'checked="checked"':'' }> Support PSTN
									</label>
								</div> --%>
								<div class="checkbox">
									<label> 
										<input type="checkbox" id="support_voip" ${hardware.support_voip?'checked="checked"':'' }> Support VoIP
									</label>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label for="hardware_status" class="control-label col-md-4">Hardware Status</label>
							<div class="col-md-3">
								<select id="hardware_status" class="form-control">
									<option value="active" ${hardware.hardware_status == 'active' ? 'selected="selected"' : ''}>Active</option>
									<option value="selling" ${hardware.hardware_status == 'selling' ? 'selected="selected"' : ''}>Selling</option>
									<option value="disable" ${hardware.hardware_status == 'disable' ? 'selected="selected"' : ''}>Disable</option>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label for="hardware_price" class="control-label col-md-4">Hardware Price</label>
							<div class="col-md-3">
								<div class="input-group">
									<span class="input-group-addon">$</span>
									<input type="text" value="${hardware.hardware_price}" id="hardware_price" class="form-control" />
								</div>
							</div>
						</div>
						<div class="form-group">
							<label for="hardware_cost" class="control-label col-md-4">Hardware Cost</label>
							<div class="col-md-3">
								<div class="input-group">
									<span class="input-group-addon">$</span>
									<input type="text" value="${hardware.hardware_cost}" id="hardware_cost" class="form-control" />
								</div>
							</div>
						</div>
						<hr/>
						<div class="form-group">
							<div class="col-md-3 col-md-offset-4">
								<button type="button" class="btn btn-success" id="save-btn">Save</button>
							</div>
						</div>
						<hr/>
						<div class="form-group">
							<!-- <label for="hardware_desc" class="control-label col-md-4">Hardware Desc</label> -->
							<div class="col-md-12">
								<textarea id="hardware_desc" class="form-control" rows="24">${hardware.hardware_desc}</textarea>
							</div>
							<%-- <p class="help-block">
								<form:errors path="hardware_desc" cssErrorClass="error"/>
							</p> --%>
						</div>
					</form>
					
				</div>
			</div>
		</div>
	</div>
</div>
<jsp:include page="../footer.jsp" />
<jsp:include page="../script.jsp" />
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/bootstrap-select.min.js"></script>
<script type="text/javascript">
(function($){
	
	$('#save-btn').on("click", function(){
		var $btn = $(this);
		$btn.button('loading');
		var hardware = {
			id: '${hardware.id}'
			, hardware_name: $('#hardware_name').val()
			, hardware_type: $('#hardware_type option:selected').val()
			, hardware_status: $('#hardware_status option:selected').val()
			, hardware_price: $('#hardware_price').val()
			, hardware_cost: $('#hardware_cost').val()
			, hardware_desc: $('#hardware_desc').val()
		}; console.log(hardware);
		if(hardware.hardware_type=='router'){
			hardware.router_adsl = $('#router_adsl').prop('checked');
			hardware.router_vdsl = $('#router_vdsl').prop('checked');
			hardware.router_ufb = $('#router_ufb').prop('checked');
			hardware.support_pstn = $('#support_pstn').prop('checked');
			hardware.support_voip = $('#support_voip').prop('checked');
		}
		$.post('${ctx}${action}', hardware, function(json){
			if (!$.jsonValidation(json, 'right')) {
				window.location.href='${ctx}' + json.url;
			}
		}, 'json').always(function () {
			$btn.button('reset');
	    }); 
	});
	
})(jQuery);
</script>
<jsp:include page="../footer-end.jsp" />