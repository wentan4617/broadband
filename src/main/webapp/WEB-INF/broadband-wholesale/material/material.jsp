<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<jsp:include page="../header.jsp" />
<jsp:include page="../alert.jsp" />

<style>
.labelCheck{
	cursor:pointer;
}
</style>

<div class="container">
	<div class="row">
		<div class="col-md-12">
			<div class="panel panel-info">
				<div class="panel-heading">
					<h4 class="panel-title"><strong>Create Material</strong></h4>
				</div>
				<div class="panel-body">
			
					<form class="form-horizontal">
						<h4 class="text-success">Price Setting</h4>
						<hr />
						<div class="form-group">
							<label for="name" class="control-label col-md-2">Publish For</label>
							<div class="col-md-4">
								<p class="form-control-static">
									<label class="labelCheck"><input type="radio" name="publish_for" data-type="all" checked="checked"/>All Wholesalers</label>&nbsp;
									<label class="labelCheck"><input type="radio" name="publish_for" data-type="specific"/>Specific Wholesalers</label>
								</p>
							</div>
						</div>
						<div class="form-group" id="choose_assign_wholesaler" style="display:none;">
							<label for="name" class="control-label col-md-2">Choose Wholesaler</label>
							<div class="col-md-10">
								<p class="form-control-static">
									<c:forEach var="tmsw" items="${tmsws}">
										<label class="labelCheck"><input type="checkbox" name="wholesaler_id" value="${tmsw.id}"/>&nbsp;${tmsw.company_name}</label>&nbsp;&nbsp;
									</c:forEach>
								</p>
							</div>
						</div>
						<hr />
						<h4 class="text-success">Essential Detail</h4>
						<hr />
						<div class="form-group">
							<label for="material_group" class="control-label col-md-2">Group</label>
							<div class="col-md-2">
								<select name="material_group" class="form-control input-sm">
									<option></option>
									<option disabled="disabled">--- Choose A Group --- </option>
									<option>All</option>
									<c:forEach var="tmsmg" items="${tmsmgs}">
										<c:if test="${tmsmg.group_name==m.material_group}">
											<option value="${tmsmg.id}" selected="selected">${tmsmg.group_name}</option>
										</c:if>
										<c:if test="${tmsmg.group_name!=m.material_group}">
											<option value="${tmsmg.id}">${tmsmg.group_name}</option>
										</c:if>
									</c:forEach>
								</select>
							</div>
							<label id="type_label" for="material_type" class="control-label col-md-2" ${ m.material_type==null ? 'style="display:none;"' : '' } >Type</label>
							<div data-name="material_type" class="col-md-2" ${ m.material_type==null ? 'style="display:none;"' : '' }>
								<select name="material_type" class="form-control input-sm">
									<c:forEach var="tmsmt" items="${tmsmts}">
										<c:if test="${tmsmt.type_name==m.material_type}">
											<option value="${tmsmt.id}" selected="selected">${tmsmt.type_name}</option>
										</c:if>
										<c:if test="${tmsmt.type_name!=m.material_type}">
											<option value="${tmsmt.id}">${tmsmt.type_name}</option>
										</c:if>
									</c:forEach>
								</select>
							</div>
							<label id="type_label" for="material_category" class="control-label col-md-2">Category</label>
							<div class="col-md-2">
								<select name="material_category" class="form-control input-sm">
									<c:forEach var="tmsmc" items="${tmsmcs}">
										<c:if test="${tmsmc.category_name==m.material_category}">
											<option value="${tmsmc.id}" selected="selected">${tmsmc.category_name}</option>
										</c:if>
										<c:if test="${tmsmc.category_name!=m.material_category}">
											<option value="${tmsmc.id}">${tmsmc.category_name}</option>
										</c:if>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label for="name" class="control-label col-md-2">Name</label>
							<div class="col-md-2">
								<input type="text" name="name" value="${m.name}" class="form-control input-sm"/>
							</div>
							<label for="description" class="control-label col-md-2">Description</label>
							<div class="col-md-2">
								<input type="text" name="description" value="${m.description}" class="form-control input-sm"/>
							</div>
						</div>
						<hr/>
						<h4 class="text-success">Price Setting</h4>
						<hr />
						<div class="form-group">
							<label for="wholesale_price" class="control-label col-md-2">Wholesale Price</label>
							<div class="col-md-2">
								<input type="text" name="wholesale_price" value="${m.wholesale_price}" class="form-control input-sm"/>
							</div>
							<label for="selling_price" class="control-label col-md-2">Selling Price</label>
							<div class="col-md-2">
								<input type="text" name="selling_price" value="${m.selling_price}" class="form-control input-sm"/>
							</div>
							<label for="wholesaler_earning" class="control-label col-md-2">Wholesaler Earned</label>
							<div class="col-md-2">
								<input type="text" name="wholesaler_earning" value="${m.earned_price!=null ? m.earned_price : '0.00'}" class="form-control input-sm" disabled="disabled"/>
							</div>
						</div>
						
						<!-- button -->
						<hr/>
						<div class="form-group">
							<div class="col-md-4"></div>
							<div class="col-md-3">
								<a href="javascript:void(0);" class="btn btn-primary btn-xs btn-block" id="createUpdateMaterialBtn">
									${m.material_type!=null ? 'Update Material' : 'Create Material' }
								</a>
							</div>
						</div>
					</form>
			
				</div>
			</div>
		</div>
	</div>
</div>


<!-- Create Material Modal -->
<div class="modal fade" id="createUpdateMaterialModal" tabindex="-1" role="dialog" aria-labelledby="createUpdateMaterialModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-body">
				<div class="row">
					<div class="panel panel-info">
						<div class="panel-heading">
							<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
							<h3 class="panel-title">
								<strong>${m.material_type!=null ? 'UPDATE CURRENT MATERIAL' : 'CREATE NEW MATERIAL'}</strong>
							</h3>
						</div>
						<div class="panel-body">
							<p>
								${m.material_type!=null ? 'Update this Material?' : 'Create a new Material?'}
							</p>
						</div>
						<div class="panel-footer">
							<div class="form-group">
								<button id="create_update_material_btn" type="button" data-type="${m.material_type!=null ? 'update' : 'create'}" class="btn btn-xs btn-primary col-md-2 col-md-offset-5" data-dismiss="modal">${m.material_type!=null ? 'Update' : 'Create'}</button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<jsp:include page="../footer.jsp" />
<jsp:include page="../script.jsp" />
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/bootstrap-select.min.js"></script>
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/icheck.min.js"></script>
<script>
(function($){
	
	$(':radio,:checkbox').iCheck({
		checkboxClass : 'icheckbox_square-blue',
		radioClass : 'iradio_square-blue'
	});
	
	// BEGIN Publish Type
	$('input[name="publish_for"]').on('ifChecked',function(){
		if($(this).attr('data-type')=='all'){
			$('#choose_assign_wholesaler').css('display','none');
		} else {
			$('#choose_assign_wholesaler').css('display','');
		}
	});
	// END Publish Type
	
	// BEGIN MaterialGroup & Type Selector
	$('select[name="material_group"]').change(function(){
		$('#type_label').css('display','');
		oDivType = $('div[data-name="material_type"]');
		oDivType.css('display','');
		
		var group_id = $(this).find('option:selected').val();
		var group_name = $(this).find('option:selected').text();
		
		if(group_name=='All'){
			group_id = null;
		}
		
		var data = {
			'group_id':group_id
		};
		
		$.get('${ctx}/broadband-wholesale/material/type/show', data, function(json){
			var oSelectType = $('select[name="material_type"]');
			oSelectType.empty();
			for(var i=0; i<json.models.length; i++){
				oSelectType.append('<option value="'+json.models[i].group_id+'">'+json.models[i].type_name+'</option>');
			}
		});
	});
	// END MaterialGroup & Type Selector
	
	// BEGIN WholesalerEarningRatesSelector
	for(var i=1; i<50; i++){
		$('#wholesaler_earning_rates').append('<option value="'+i+'">'+i+'%</option>');
	}
	// END WholesalerEarningRatesSelector
	
	// BEGIN CalculateWholesalerEarning
	var oWholesalerEarning = $('input[name="wholesaler_earning"]');
	var oWholesalePrice = $('input[name="wholesale_price"]');
	var oSellingPrice = $('input[name="selling_price"]');
	
	oWholesalePrice.blur(function(){
		if($(this).val() > 0){
			$(this).val(new Number($(this).val()).toFixed(2));
		} else {
			$(this).val('');
		}
	});
	
	oSellingPrice.blur(function(){
		if($(this).val() > 0){
			$(this).val(new Number($(this).val()).toFixed(2));
		} else {
			$(this).val('');
		}
		
		var wholesalePrice = new Number(oWholesalePrice.val());
		var sellingPrice = new Number(oSellingPrice.val());
		if(sellingPrice > 0 && sellingPrice > wholesalePrice){
			oWholesalerEarning.val(new Number(sellingPrice-wholesalePrice).toFixed(2));
		} else {
			oWholesalerEarning.val('0.00');
		}
	});
	// END CalculateWholesalerEarning
	
	// BEGIN CreateMaterialSubmit
	$('#createUpdateMaterialBtn').click(function(){
		$('#createUpdateMaterialModal').modal('show');
	});
	$('#create_update_material_btn').click(function(){
		var name = $('input[name="name"]').val();
		var material_group = $('select[name="material_group"]').find('option:selected').text();
		var material_group_id = $('select[name="material_type"]').find('option:selected').val();
		var material_type = $('select[name="material_type"]').find('option:selected').text();
		var description = $('input[name="description"]').val();
		var wholesale_price = $('input[name="wholesale_price"]').val();
		var selling_price = $('input[name="selling_price"]').val();
		var wholesaler_earning = $('input[name="wholesaler_earning"]').val();
		
		var data = {
			'id':${m.id}+'',
			'name':name,
			'material_group':material_group,
			'material_type':material_type,
			'description':description,
			'wholesale_price':wholesale_price,
			'selling_price':selling_price,
			'earned_price':wholesaler_earning,
			'material_group_id':material_group_id,
			'submit_type':$(this).attr('data-type')
		};
		
		$.post('${ctx}/broadband-wholesale/material/update_create', data, function(json){
	   		$.jsonValidation(json, 'right');
		});
		
	});
	// END CreateMaterialSubmit
	
})(jQuery);
</script>
