<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<jsp:include page="../header.jsp" />
<jsp:include page="../alert.jsp" />

<style>
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
					<h4 class="panel-title">Plan View&nbsp;
						
						<select id="filter_operations" class="selectpicker pull-right" multiple >
						    <optgroup label="Filter Plan Group">
						    	<option value="plan-topup" data-type="plan-group">to Plan Top-Up</option>
						      	<option value="plan-no-term" data-type="plan-group">to Plan No Term</option>
						      	<option value="plan-term" data-type="plan-group">to Plan Term</option>
						    </optgroup>
						     <optgroup label="Filter Plan Class">
						    	<option value="personal" data-type="plan-class">to Personal</option>
						      	<option value="business" data-type="plan-class">to Business</option>
						    </optgroup>
						    <optgroup label="Filter Plan Type">
						    	<option value="ADSL" data-type="plan-type">To ADSL</option>
						      	<option value="VDSL" data-type="plan-type">To VDSL</option>
						      	<option value="UFB" data-type="plan-type">to UFB</option>
						    </optgroup>
						</select>
						
						&nbsp;
						
						<select id="select_operations" class="selectpicker pull-right" multiple>
						    <optgroup label="Essential Operations">
						      	<option value="delete" data-type="plan-delete">Delete Selected Plan</option>
						    </optgroup>
						    <optgroup label="Change Plan Group">
						    	<option value="plan-topup" data-type="plan-group">to Plan Top-Up</option>
						      	<option value="plan-no-term" data-type="plan-group">to Plan No Term</option>
						      	<option value="plan-term" data-type="plan-group">to Plan Term</option>
						    </optgroup>
						     <optgroup label="Change Plan Class">
						    	<option value="personal" data-type="plan-class">to Personal</option>
						      	<option value="business" data-type="plan-class">to Business</option>
						    </optgroup>
						    <optgroup label="Change Plan Type">
						    	<option value="ADSL" data-type="plan-type">To ADSL</option>
						      	<option value="VDSL" data-type="plan-type">To VDSL</option>
						      	<option value="UFB" data-type="plan-type">to UFB</option>
						    </optgroup>
						    <optgroup label="Change Plan Sort">
						    	<option value="NAKED" data-type="plan-sort">to NAKED</option>
						      	<option value="CLOTHING" data-type="plan-sort">to CLOTHING</option>
						    </optgroup>
						    <optgroup label="Change Plan Status">
						    	<option value="active" data-type="plan-status">to Active</option>
						      	<option value="selling" data-type="plan-status">to Selling</option>
						      	<option value="disable" data-type="plan-status">to Disable</option>
						    </optgroup>
						</select>
						
					</h4>
				</div>
				
				<!-- plan view of page table for ajax -->
				<div id="plan-table"></div>
				
			</div>
		</div>
	</div>
</div>

<script type="text/html" id="plan_table_tmpl">
<jsp:include page="plan-view-page.html" />
</script>

<jsp:include page="../footer.jsp" />
<jsp:include page="../script.jsp" />
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/bootstrap-select.min.js"></script>
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/jTmpl.js"></script>

<script type="text/javascript">
(function($){
	
	function doPage(pageNo) {
		$.get('${ctx}/broadband-user/plan/view/' + pageNo, function(page){ //console.log(page);
			page.ctx = '${ctx}';
	   		var $table = $('#plan-table');
			$table.html(tmpl('plan_table_tmpl', page));
			$table.find('tfoot a').click(function(){
				doPage($(this).attr('data-pageNo'));
			});
			
			$('#checkbox_plans_top').click(function(){
				var b = $(this).prop("checked");
				if (b) $('input[name="checkbox_plans"]').prop("checked", true);
				else $('input[name="checkbox_plans"]').prop("checked", false);
			});
	   	});
	}
	doPage(1);
	
	$('.selectpicker').selectpicker();
	
	$('#select_operations').change(function(){
		var $this = $(this);
		var val = this.value;
		var type = $this.find('option:selected').attr('data-type');
		if(type=='plan-delete'){
			$('#planForm').attr('action', '${ctx }/broadband-user/plan/delete');
			$('#planForm').submit();
		} else {
			$('input[name="value"]').val(val);
			$('input[name="type"]').val(type);
			$('#planForm').attr('action', '${ctx}/broadband-user/plan/options/edit');
			$('#planForm').submit();
		}
	});
	
	$('#filter_operations').change(function(){
		var $this = $(this);
		var plan = {				
			plan_group: $this.find('option[data-type="plan-group"]:selected').val() || null
			, plan_class: $this.find('option[data-type="plan-class"]:selected').val() || null
			, plan_type: $this.find('option[data-type="plan-type"]:selected').val() || null
		}; //console.log(plan);
		$.get('${ctx}/broadband-user/plan/view/filter', plan, function(){
			doPage(1);
		});
	});
	
	
	
	$('#filter_operations').find('option').each(function(){
		if (this.value == '${planFilter.plan_group}' 
			|| this.value == '${planFilter.plan_class}' 
			|| this.value == '${planFilter.plan_type}' ) {
			$(this).attr("selected", "selected");
			$('.selectpicker').selectpicker('refresh');
		}
		 
	});
	
	
})(jQuery);
</script>
<jsp:include page="../footer-end.jsp" />