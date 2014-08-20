<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<jsp:include page="../header.jsp" />
<jsp:include page="../alert.jsp" />

<div class="container">
	<div class="row">
		<div class="col-md-12">
			<div class="panel panel-success">
				<div class="panel-heading"><h4 class="panel-title">Notification View</h4></div>
				<c:if test="${fn:length(page.results) > 0 }">
					<table class="table">
						<thead >
							<tr>
								<th><input type="checkbox" id="checkbox_notifications_top" /></th>
								<th>Title</th>
								<th>Type</th>
								<th>Sort</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="notification" items="${page.results }">
								<tr class="${notification.type == 'sms' ? 'success' : '' }">
									<td>
										<input type="checkbox" name="checkbox_notifications" value="${notification.id}"/>
									</td>
									<td>
										<a href="${ctx }/broadband-user/system/notification/edit/${notification.id}">
											${notification.title }
										</a>
									</td>
									<td>${notification.type }</td>
									<td>${notification.sort }</td>
								</tr>
							</c:forEach>
						</tbody>
						<tfoot>
							<tr>
								<td colspan="11">
									<ul class="pagination">
										<c:forEach var="num" begin="1" end="${page.totalPage }" step="1">
											<li class="${page.pageNo == num ? 'active' : ''}">
												<a href="${ctx}/broadband-user/system/notification/view/${num}">${num}</a>
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
<script type="text/javascript">
(function($){
	$('#checkbox_notifications_top').click(function(){
		var b = $(this).prop("checked");
		if (b) {
			$('input[name="checkbox_notifications"]').prop("checked", true);
		} else {
			$('input[name="checkbox_notifications"]').prop("checked", false);
		}
	});
})(jQuery);
</script>
<jsp:include page="../footer-end.jsp" />