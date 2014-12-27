<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<jsp:include page="../header.jsp" />
<jsp:include page="../alert.jsp" />

<style>
.panel-default {
	border-top-color: transparent;
}
</style>

<div class="container">
	<div class="row">
		<div class="col-md-12">
			<div class="panel panel-success">
				<div class="panel-heading">
					<h4 class="panel-title">
						Equipment View
						<c:if test="${userSession.user_role=='system-developer' || userSession.user_role=='administrator' || userSession.user_role=='accountant'}">
						<a data-name="add_equip_btn" class="btn btn-success btn-xs" data-oper-type="add" style="color:white;">New Equipment</a>
						&nbsp;&nbsp;
						<a data-name="add_equip_pattern_btn" class="btn btn-success btn-xs" style="color:white;">New Pattern</a>
						</c:if>
					</h4>
				</div>
				<div class="panel-body">
				
					<!-- Nav tabs -->
					<ul class="nav nav-tabs">
						<li class="${equipActive}"><a href="#equip_view" data-toggle="tab"><strong>Equipment</strong></a></li>
						<li class="${equipPatternActive}"><a href="#equip_pattern_view" data-toggle="tab"><strong>Equipment Pattern</strong></a></li>
					</ul>
					
					<!-- Tab panes -->
					<div class="tab-content panel panel-default">
						<div class="panel-body tab-pane fade in ${equipActive}" id="equip_view" ></div>
						<div class="panel-body tab-pane fade in ${equipPatternActive}" id="equip_pattern_view" ></div>
					</div>
					
				</div>
			</div>
		</div>
	</div>
</div>

<!-- Add Equip Modal -->
<form class="form-horizontal">
<div class="modal fade" id="addEquipmentModal" tabindex="-1" role="dialog" aria-labelledby="addEquipmentModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="addEquipmentModalLabel">
					<strong>Add New Equipment Detail</strong>
				</h4>
			</div>
			<div class="modal-body">
				<div class="form-group">
					<label class="control-label col-md-4">Select A Pattern</label>
					<div class="col-md-6">
						<select data-name="equip_pattern_selector" class="form-control input-sm">
						</select>
					</div>
				</div>
			</div>
			<div class="modal-body">
				<div class="form-group">
					<label class="control-label col-md-4">Equipment Name</label>
					<div class="col-md-6">
						<input type="text" data-name="equip_name" class="form-control input-sm" placeholder="Equip Name" />
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-md-4">Equipment Type</label>
					<div class="col-md-6">
						<select data-name="equip_type" class="form-control input-sm">
							<option value="router">router</option>
							<option value="modem">modem</option>
							<option value="touch-pad">touch-pad</option>
							<option value="phone">phone</option>
							<option value="accessory">accessory</option>
						</select>
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-md-4">Equipment Purpose</label>
					<div class="col-md-6">
						<select data-name="equip_purpose" class="form-control input-sm">
							<option value="sale-item">sale-item</option>
							<option value="gift">gift</option>
						</select>
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-md-4">Equipment S/N</label>
					<div class="col-md-6">
						<input type="text" data-name="equip_sn" class="form-control input-sm" placeholder="Equip S/N" />
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<a data-name="addEquipmentModalBtn" class="btn btn-success" data-dismiss="modal">Confirm</a>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
</form>
<!-- /.modal -->

<!-- Add Equip Pattern Modal -->
<form class="form-horizontal">
<div class="modal fade" id="addEquipPatternModal" tabindex="-1" role="dialog" aria-labelledby="addEquipPatternModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="addEquipPatternModalLabel">
					<strong>Add New Equipment Pattern Detail</strong>
				</h4>
			</div>
			<div class="modal-body">
				<div class="form-group">
					<label class="control-label col-md-4">Equipment Name</label>
					<div class="col-md-6">
						<input type="text" data-name="equip_pattern_name" class="form-control input-sm" placeholder="Equip Pattern Name" />
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-md-4">Equipment Type</label>
					<div class="col-md-6">
						<select data-name="equip_pattern_type" class="form-control input-sm">
							<option value="router">router</option>
							<option value="modem">modem</option>
							<option value="touch-pad">touch-pad</option>
							<option value="phone">phone</option>
							<option value="accessory">accessory</option>
						</select>
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-md-4">Equipment Purpose</label>
					<div class="col-md-6">
						<select data-name="equip_pattern_purpose" class="form-control input-sm">
							<option value="sale-item">sale-item</option>
							<option value="gift">gift</option>
						</select>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<a data-name="addEquipPatternModalBtn" class="btn btn-success" data-dismiss="modal">Confirm</a>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
</form>
<!-- /.modal -->

<script type="text/html" id="equip_tmpl">
<jsp:include page="equip-view.html" />
</script>

<script type="text/html" id="equip_pattern_tmpl">
<jsp:include page="equip-pattern-view.html" />
</script>

<jsp:include page="../footer.jsp" />
<jsp:include page="../script.jsp" />
<script>

	var ctx = '${ctx}';
	var user_role = '${userSession.user_role}';
	
</script>
<script type="text/javascript" src="${ctx}/public/broadband-user/inventory/equip.js"></script>
<jsp:include page="../footer-end.jsp" />