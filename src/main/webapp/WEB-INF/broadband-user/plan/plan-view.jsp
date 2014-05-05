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
						
						<select id="select_operations" class="selectpicker pull-right">
							<option style="font-size:16px;">Multiple Operations</option>
						    <optgroup label="Essential Operations">
						      <option value="delete" data-type="plan-delete">Delete Selected Plan</option>
						    </optgroup>
						    <optgroup label="Change Plan Group">
						    	<option value="plan-topup" data-type="plan-group">to Plan Topup</option>
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
				<c:if test="${fn:length(page.results) > 0 }">
					<form id="planForm" action="" method="post">
					<input type="hidden" name="value"/>
					<input type="hidden" name="type"/>
					<table class="table" style="font-size:12px;">
						<thead >
							<tr>
								<th><input type="checkbox" id="checkbox_plans_top" /></th>
								<th>Plan Name</th>
								<th>Group</th>
								<th>Class</th>
								<th>Type</th>
								<th>Sort</th>
								<th>Monthly fee (Inc GST)($)</th>
								<th>New Connection fee (Inc GST)($)</th>
								<th>Data Flow (GB)</th>
								<th>Status</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="plan" items="${page.results }">
								<tr class="${plan.plan_status == 'selling' ? 'success' : '' }">
									<td>
										<input type="checkbox" name="checkbox_plans" value="${plan.id}"/>
									</td>
									<td>
										<a href="${ctx }/broadband-user/plan/edit/${plan.id}">
											${plan.plan_name }
										</a>
									</td>
									<td  class="<c:choose>
										<c:when test="${plan.plan_group=='plan-topup'}">text-danger</c:when>
										<c:when test="${plan.plan_group=='plan-no-term'}">text-info</c:when>
										<c:when test="${plan.plan_group=='plan-term'}">text-warning</c:when>
									</c:choose>">
										${plan.plan_group }
									</td>
									<td>
										${plan.plan_class }
									</td>
									<td>
										${plan.plan_type }
									</td>
									<td>
										${plan.plan_sort }
									</td>
									<td>
										<fmt:formatNumber value="${plan.plan_price }" type="number" pattern="#,##0.00" />
										
									</td>
									<td>
										<fmt:formatNumber value="${plan.plan_new_connection_fee }" type="number" pattern="#,##0.00" />
										
									</td>
									<td>${plan.data_flow }</td>
									<td>${plan.plan_status}</td>
								</tr>
							</c:forEach>
						</tbody>
						<tfoot>
						<tr>
							<td colspan="11">
								<ul class="pagination">
									<c:forEach var="num" begin="1" end="${page.totalPage }" step="1">
										<li class="${page.pageNo == num ? 'active' : ''}">
											<a href="${ctx}/broadband-user/plan/view/${num}">${num}</a>
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
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/bootstrap-select.min.js"></script>
<script type="text/javascript">
(function($){
	$('.selectpicker').selectpicker();
	$('#checkbox_plans_top').click(function(){
		var b = $(this).prop("checked");
		if (b) {
			$('input[name="checkbox_plans"]').prop("checked", true);
		} else {
			$('input[name="checkbox_plans"]').prop("checked", false);
		}
	});
	
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
	
})(jQuery);
</script>
<jsp:include page="../footer-end.jsp" />