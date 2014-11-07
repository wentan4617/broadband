<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<jsp:include page="../header.jsp" />
<jsp:include page="../alert.jsp" />

<style type="text/css">
thead th {text-align:center;}
tbody td {text-align:center;}
.chorus{
	background:rgba(220,50,90,0.3);
}
.chorus:hover{
	background:rgba(220,50,90,0.35);
}
.nca{
	background:rgba(153,186,149,0.3);
}
.nca:hover{
	background:rgba(153,186,149,0.35);
}
</style>

<div class="container">
	<div class="row">
		<div class="col-md-12">
		
			<div class="panel panel-success">
				<div class="panel-heading">
					<h4 class="panel-title">
						<a data-toggle="collapse" data-toggle="collapse" data-parent="#accordion" href="#collapseOne">
							PSTN Query
						</a>
					</h4>
				</div>
				<div id="collapseOne" class="panel-collapse collapse in">
					<div class="panel-body">
						<div class="btn-group btn-group">
							<a href="${ctx}/broadband-user/provision/pstn-position-view/1/chorus" class="btn btn-default ${chorusActive }">
								Chorus&nbsp;<span class="badge">${chorusSum}</span>
							</a>
							<a href="${ctx}/broadband-user/provision/pstn-position-view/1/nca" class="btn btn-default ${ncaActive }">
								NCA&nbsp;<span class="badge">${ncaSum}</span>
							</a>
						</div>
					</div>
				</div>
			</div>
		
			<div class="panel panel-success">
				<div class="panel-heading">
					<h4 class="panel-title">
						Order Detail View
					</h4>
				</div>
				<c:if test="${fn:length(page.results) > 0 }">
					<table class="table" style="font-size:12px;">
						<thead >
							<tr>
								<th>Order Id</th>
								<th>Phone Number</th>
								<th>Detail Price</th>
								<th>Detail Type</th>
								<th>Operation</th>
								<c:if test="${pstn_position=='nca'}">
									<th>Executor</th>
								</c:if>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="cod" items="${page.results }">
								<tr class="${
										  pstn_position=='chorus' ? 'chorus' :
										  pstn_position=='nca' ? 'nca' : '' }" >
									<td>
										<a href="${ctx}/broadband-user/provision/pstn-position-view/customer/view/${cod.order_id }">${cod.order_id }</a>
									</td>
									<td>
										${cod.pstn_number}
									</td>
									<td>
										${cod.detail_price}
									</td>
									<td>
										${cod.detail_type}
									</td>
									<td>
										<a id="${cod.id}" class="btn btn-warning btn-xs" data-name="switch_between_chorus_or_nca_btn">
										  	<span class="glyphicon glyphicon-plus"></span>&nbsp;${pstn_position=='chorus' ? 'Added into NCA' : 'Undone'}
										</a>
									</td>
									<c:if test="${pstn_position=='nca'}">
										<td>
											<c:forEach var="user" items="${users}">
												<c:if test="${user.id==cod.to_nca_by_who}">
													${user.user_name}
												</c:if>
											</c:forEach>
										</td>
									</c:if>
								</tr>
							</c:forEach>
						</tbody>
						<tfoot>
							<tr>
								<td colspan="11">
									<ul class="pagination">
										<c:forEach var="num" begin="1" end="${page.totalPage }" step="1">
											<li class="${page.pageNo == num ? 'active' : ''}">
												<a href="${ctx}/broadband-user/provision/pstn-position-view/${num}/${pstn_position}">${num}</a>
											</li>
										</c:forEach>
									</ul>
								</td>
							</tr>
						</tfoot>
					</table>
				</c:if>
				<c:if test="${fn:length(page.results) <= 0 && fn:length(page.results) <= 0 }">
					<div class="panel-body">
						<div class="alert alert-warning">No records have been found.</div>
					</div>
				</c:if>
			</div>
		</div>
	</div>
</div>


<!-- Switch into NCA Modal -->
<form action="${ctx}/broadband-user/provision/pstn-position-view/switch-between-chorus-nca" method="post">
	<input type="hidden" name="id" />
	<input type="hidden" name="pageNo" value="${page.pageNo}" />
	<input type="hidden" name="pstn_position" value="${pstn_position}" />
	<div class="modal fade" id="switchIntoNCAModal" tabindex="-1" role="dialog" aria-labelledby="switchIntoNCAModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="switchIntoNCAModalLabel">
						<strong>${pstn_position=='chorus' ? 'Switch This PSTN from Chorus to NCA' : 'Undone, move this back to Chorus List'}</strong>
					</h4>
				</div>
				<div class="modal-body">
					<p>
						${pstn_position=='chorus' ? 'This PSTN number has been properly switched to NCA?' : 'This PSTN number has not been properly switch into NCA?'}
					</p>
				</div>
				<div class="modal-footer">
					<button class="btn btn-success" type="submit">Confirm</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- /.modal -->
</form>

<jsp:include page="../footer.jsp" />
<jsp:include page="../script.jsp" />
<script type="text/javascript">
(function($) {
	
	$('a[data-name="switch_between_chorus_or_nca_btn"]').click(function(){
		$('input[name="id"]').val(this.id);
		$('#switchIntoNCAModal').modal('show');
	});
	
	
	
})(jQuery);
</script>
<jsp:include page="../footer-end.jsp" />