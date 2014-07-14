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
					<h4 class="panel-title">User View
						<div class="pull-right">
							<select id="select_operations" name="user-provision-notice" class="selectpicker" multiple title="Set Operation">
							    <optgroup label="Provision Notice">
							      	<option value="true">Yes</option>
							      	<option value="false">No</option>
							    </optgroup>
							</select>
						</div>
					</h4>
				</div>
				<c:if test="${fn:length(page.results) > 0 }">
					<table class="table">
						<thead >
							<tr>
								<th><input type="checkbox" id="checkbox_users_top" /></th>
								<th>Login Name</th>
								<th>Password</th>
								<th>User Name</th>
								<th>User Role</th>
								<th>Memo</th>
								<th>Provision Notice</th>
								<th>&nbsp;</th>
							</tr>
						</thead>
						<tbody>
							<form id="userForm" method="post">
							<input name="is_provision" type="hidden" />
							<c:forEach var="user" items="${page.results }">
								<tr class="">
									<td>
										<input type="checkbox" name="checkbox_users" value="${user.id}"/>
									</td>
									<td>
										<a href="${ctx }/broadband-user/system/user/edit/${user.id}">
											${user.login_name }
										</a>
									</td>
									<td>
										${user.password }
									</td>
									<td>
										${user.user_name }
									</td>
									<td>
										${user.user_role }
									</td>
									<td>
										${user.memo }
									</td>
									<td>
										<span ${user.is_provision ? 'class="glyphicon glyphicon-ok"' : '' }></span>
									</td>
									<td>&nbsp;</td>
								</tr>
							</c:forEach>
							</form>
						</tbody>
						<tfoot>
						<tr>
							<td colspan="11">
								<ul class="pagination">
									<c:forEach var="num" begin="1" end="${page.totalPage }" step="1">
										<li class="${page.pageNo == num ? 'active' : ''}">
											<a href="${ctx}/broadband-user/system/user/view/${num}">${num}</a>
										</li>
									</c:forEach>
								</ul>
							</td>
						</tr>
					</tfoot>
					</table>
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
	
	$('#checkbox_users_top').click(function(){
		var b = $(this).prop("checked");
		if (b) {
			$('input[name="checkbox_users"]').prop("checked", true);
		} else {
			$('input[name="checkbox_users"]').prop("checked", false);
		}
	});
	
	$('select[name="user-provision-notice"]').change(function(){
		$('input[name="is_provision"]').val(this.value);
		$('#userForm').attr('action', '${ctx }/broadband-user/user/is-provision');
		$('#userForm').submit();
	});
})(jQuery);
</script>
<jsp:include page="../footer-end.jsp" />