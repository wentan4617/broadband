<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<jsp:include page="../header.jsp" />
<jsp:include page="../alert.jsp" />

<form:form modelAttribute="equipPattern" method="post" action="${ctx}/broadband-user/inventory/equip/pattern/update" class="form-horizontal">
<form:hidden path="id"/>
<div class="container">
	<div class="row">
		<div class="col-md-12">
			<div class="panel-group" id="equipPatternAccordion">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h4 class="panel-title">
							<a data-toggle="collapse" data-toggle="collapse" data-parent="#equipPatternAccordion" href="#collapseEquip">${panelheading }</a>
						</h4>
					</div>
					<div id="collapseEquipPattern" class="panel-collapse collapse in">
						<div class="panel-body">
							<div class="form-group">
								<label for="equip_name" class="control-label col-md-4">Equipment Name</label>
								<div class="col-md-6">
										<c:choose>
											<c:when test="${userSession.user_role=='system-developer' || userSession.user_role=='administrator' || userSession.user_role=='accountant'}">
									<form:input path="equip_name" class="form-control"/>
											</c:when>
											<c:otherwise>
									<p class="form-control-static">
										${equipPattern.equip_name}
									</p>
											</c:otherwise>
										</c:choose>
								</div>
							</div>
							<div class="form-group">
								<label for="equip_type" class="control-label col-md-4">Equipment Type</label>
								<div class="col-md-6">
										<c:choose>
											<c:when test="${userSession.user_role=='system-developer' || userSession.user_role=='administrator' || userSession.user_role=='accountant'}">
									<form:select path="equip_type" class="form-control">
										<form:option value="router">Router</form:option>
										<form:option value="modem">Modem</form:option>
										<form:option value="touch-pad">Touch Pad</form:option>
										<form:option value="phone">Phone</form:option>
										<form:option value="accessory">Accessory</form:option>
									</form:select>
											</c:when>
											<c:otherwise>
									<p class="form-control-static">
										${equipPattern.equip_type}
									</p>
											</c:otherwise>
										</c:choose>
								</div>
							</div>
							<div class="form-group">
								<label for="equip_purpose" class="control-label col-md-4">Equipment Purpose</label>
								<div class="col-md-6">
										<c:choose>
											<c:when test="${userSession.user_role=='system-developer' || userSession.user_role=='administrator' || userSession.user_role=='accountant'}">
									<form:select path="equip_purpose" class="form-control">
										<form:option value="sale-item">Sale Item</form:option>
										<form:option value="gift">Gift</form:option>
									</form:select>
											</c:when>
											<c:otherwise>
									<p class="form-control-static">
										${equipPattern.equip_purpose}
									</p>
											</c:otherwise>
										</c:choose>
								</div>
							</div>
							<hr/>
							<div class="form-group">
								<c:if test="${userSession.user_role=='system-developer' || userSession.user_role=='administrator' || userSession.user_role=='accountant'}">
								<div class="col-md-2">
									<button type="submit" class="btn btn-xs btn-success btn-lg btn-block col-md-2">Edit</button>
								</div>
								<div class="col-md-2">
									<a href="javascript:void(0);" class="btn btn-xs btn-success btn-lg btn-block col-md-2" data-name="remove-equip-pattern">Delete</a>
								</div>
								</c:if>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
</form:form>

<!-- Remove Equip Pattern Modal -->
<div class="modal fade" id="removeEquipmentPatternModal" tabindex="-1" role="dialog" aria-labelledby="removeEquipmentPatternModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="removeEquipmentPatternModalLabel">
					<strong>Remove Equipment Pattern Detail</strong>
				</h4>
			</div>
			<div class="modal-body">
				<p>Sure To Remove This Equipment Pattern From the System?</p>
			</div>
			<div class="modal-footer">
				<a href="${ctx}/broadband-user/inventory/equip/pattern/delete/${equipPattern.id}" class="btn btn-success">Confirm</a>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal -->

<jsp:include page="../footer.jsp" />
<jsp:include page="../script.jsp" />
<script>
(function($){
	
	$('a[data-name="remove-equip-pattern"]').click(function(){
		$('#removeEquipmentPatternModal').modal('show');
	});
	
})(jQuery);
</script>
<jsp:include page="../footer-end.jsp" />