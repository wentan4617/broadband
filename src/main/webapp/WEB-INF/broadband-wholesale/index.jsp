<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<jsp:include page="header.jsp" />
<jsp:include page="alert.jsp" />

<style>
.panel-info {
	min-height: 251px;
}
hr {
	margin:0 0 5px 0;
}
</style>


<div class="container">
    <div class="row">
    	<c:if test="${userSession!=null}">
	        <div class="col-md-3">
	        	<div class="panel panel-info">
			  		<div class="panel-heading">
			  			<h3 class="panel-title"><strong class="text-info">User Operations</strong></h3>
			  		</div>
				  	<div class="panel-body">
				    	<p>User operations, wholesaler's won't see</p>
	                    <ul class="list-unstyled">
	                    	<li>
	                    		<span class="glyphicon glyphicon-plus" style="padding-right:10px;"></span>
	                    		<a href="javascript:void(0);" data-name="create_material_group_type" data-type="group">Create Group</a>
	                    	</li>
	                    	<li>
	                    		<span class="glyphicon glyphicon-plus" style="padding-right:10px;"></span>
	                    		<a href="javascript:void(0);" data-name="create_material_group_type" data-type="type">Create Type</a>
	                    	</li>
	                    	<li>
	                    		<span class="glyphicon glyphicon-plus" style="padding-right:10px;"></span>
	                    		<a href="javascript:void(0);" data-name="create_material_group_type" data-type="category">Create Category</a>
	                    	</li>
	                    </ul>
	                    <hr/>
	                    <ul class="list-unstyled">
							<li>
	                    		<span class="glyphicon glyphicon-plus" style="padding-right:10px;"></span>
	                    		<a href="${ctx }/broadband-wholesale/material/create">Create Material</a>
							</li>
							<li>
	                    		<span class="glyphicon glyphicon-plus" style="padding-right:10px;"></span>
	                    		<a href="${ctx }/broadband-wholesale/material/combo/create">Create Combo</a>
							</li>
	                    	<li>
	                    		<span class="glyphicon glyphicon-list" style="padding-right:10px;"></span>
	                    		<a href="${ctx }/broadband-wholesale/material/material-combo/view">View Material & Combo</a>
	                    	</li>
	                    </ul>
				 	</div>
				</div>
	        </div>
		</c:if>
        <div class="col-md-3">
        	<div class="panel panel-info">
		  		<div class="panel-heading">
		  			<h3 class="panel-title"><strong class="text-info">Ordering Online</strong></h3>
		  		</div>
			  	<div class="panel-body">
			    	<p>Modifying material products, create material combo</p>
                    <ul class="list-unstyled">
                    </ul>
			 	</div>
			</div>
        </div>
		<div class="col-md-3">
			<div class="panel panel-info">
		  		<div class="panel-heading">
		  			<h3 class="panel-title"><strong class="text-info">System</strong></h3>
		  		</div>
			  	<div class="panel-body">
			  		<p>Wholesaler management</p>
                    <ul class="list-unstyled">
                    	<li>
                    		<span class="glyphicon glyphicon-list" style="padding-right:10px;"></span>
                    		<a href="${ctx }/broadband-wholesale/system/wholesaler/view">View Wholesaler</a>
                    	</li>
        				<c:if test="${userSession!=null || wholesalerSession.wholesaler_id==null}">
                    	<li>
                    		<span class="glyphicon glyphicon-plus" style="padding-right:10px;"></span>
                    		<a href="${ctx }/broadband-wholesale/system/wholesaler/create">Create Wholesaler</a>
                    	</li>
        				</c:if>
                    </ul>
			  	</div>
			</div>
		</div>
    </div>
</div>


<!-- Create Group || Type Modal -->
<form class="form-horizontal" id="group_type_form">
	<input type="hidden" name="group_or_type"/>
	<div class="modal fade" id="createGroupTypeModal" tabindex="-1" role="dialog" aria-labelledby="createGroupTypeModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-body">
					<div class="row">
						<div class="panel panel-info">
							<div class="panel-heading">
								<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
								<h3 class="panel-title">
									<strong>CREATE A MATERIAL <span id="createGroupTypeTitle"></span></strong>
								</h3>
							</div>
							<div class="panel-body">
								<div class="form-group" id="group_name_select_div" style="display:none;">
									<div class="col-md-5">
										<label for="group_name_select" class="control-label pull-right">Group Name&nbsp;&nbsp;</label>
									</div>
									<div class="col-md-4">
										<select id="group_name_select" class="form-control input-sm" >
										</select>
									</div>
								</div>
								<div class="form-group">
									<div class="col-md-5">
										<label for="material_group" id="material_group_or_type_label" class="control-label pull-right"></label>
									</div>
									<div class="col-md-4">
										<input type="text" id="group_or_type_name" class="form-control input-sm" />
									</div>
								</div>
							</div>
							<div class="panel-footer">
								<div class="form-group">
									<button id="create_group_type_btn" type="button" class="btn btn-xs btn-primary col-md-2 col-md-offset-5" data-dismiss="modal">Create</button>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</form>

<jsp:include page="footer.jsp" />
<jsp:include page="script.jsp" />
<jsp:include page="footer-end.jsp" />
<script>
(function($){
	
	$('a[data-name="create_material_group_type"]').click(function(){
		
		if($(this).attr('data-type')=='group'){
			$('#material_group_or_type_label').html('Group Name&nbsp;&nbsp;');
			$('input[name="group_or_type"]').val('group');
			$('#createGroupTypeTitle').html('GROUP');
			$('#group_name_select_div').css('display','none');
		} else if($(this).attr('data-type')=='type'){

			$.get('${ctx}/broadband-wholesale/material/groups/show', function(json){
				
				var groups = json.models;
				var group_name_select = $('#group_name_select');
				group_name_select.empty();
				
				for(var i=0; i<groups.length; i++){
					group_name_select.append('<option value="'+groups[i].id+'">'+groups[i].group_name+'</option>');
				}
				
			});
			
			$('#material_group_or_type_label').html('Type Name&nbsp;&nbsp;');
			$('input[name="group_or_type"]').val('type');
			$('#createGroupTypeTitle').html('TYPE');
			$('#group_name_select_div').css('display','');
		} else {
			$('#material_group_or_type_label').html('Category Name&nbsp;&nbsp;');
			$('input[name="group_or_type"]').val('category');
			$('#createGroupTypeTitle').html('CATEGORY');
			$('#group_name_select_div').css('display','none');
		}
		
		$('#createGroupTypeModal').modal('show');
		
	});
	
	$('#create_group_type_btn').click(function(){
		
		var group_or_type = $('input[name="group_or_type"]');
		var group_or_type_name = $('#group_or_type_name').val();
		
		var data = {
			'group_or_type':group_or_type.val(),
			'group_or_type_name':group_or_type_name,
			'group_id':$('#group_name_select option:selected').val()+''
		};
		
		$('#group_or_type_name').val('');
		
		$.post('${ctx}/broadband-wholesale/material/group-type/create', data, function(json){
	   		$.jsonValidation(json, 'right');
		});
	});
	
})(jQuery);
</script>