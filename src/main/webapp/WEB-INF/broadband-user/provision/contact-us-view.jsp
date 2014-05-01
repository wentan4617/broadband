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
				<div class="panel-heading">
					<h4 class="panel-title">
						<a data-toggle="collapse" data-toggle="collapse" data-parent="#accordion" href="#collapseOne"> 
							Contact Us Query
						</a>
					</h4>
				</div>
				<div id="collapseOne" class="panel-collapse collapse in">
					<div class="panel-body">
						<div class="btn-group btn-group">
							<a href="${ctx}/broadband-user/provision/contact-us/view/1/new" class="btn btn-default ${newActive }">
								New Request&nbsp;<span class="badge">${newSum}</span>
							</a>
							<a href="${ctx}/broadband-user/provision/contact-us/view/1/closed" class="btn btn-default ${closedActive }">
								Closed Request&nbsp;<span class="badge">${closedSum}</span>
							</a>
						</div>
					</div>
				</div>
			</div>
			
			<div class="panel panel-success">
				<div class="panel-heading">
					<h4 class="panel-title">Contact Us View</h4>
				</div>
				<c:if test="${fn:length(page.results) > 0 }">
					<table class="table" style="font-size:12px;">
						<thead >
							<tr>
								<th><input type="checkbox" id="checkbox_contactUss_top" /></th>
								<th>First Name</th>
								<th>Last Name</th>
								<th>Email</th>
								<th>Mobile</th>
								<th>Phone</th>
								<th>Status</th>
								<th>Submit Time</th>
								<th>Operations</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="contactUs" items="${page.results }">
								<input type="hidden" data-name="${contactUs.id}_content" value="${contactUs.content}"/>
								<input type="hidden" data-name="${contactUs.id}_respond_content" value="${contactUs.respond_content}"/>
								<tr>
									<td>
										<input type="checkbox" name="checkbox_contactUss" value="${contactUs.id}"/>
									</td>
									<td>
										${contactUs.first_name }
									</td>
									<td>
										${contactUs.last_name }
									</td>
									<td data-name="${contactUs.id}_email" data-value="${contactUs.email }">
										${contactUs.email }
									</td>
									<td>
										${contactUs.cellphone }
									</td>
									<td>
										${contactUs.phone }
									</td>
									<td>
										${contactUs.status }
									</td>
									<td>
										<fmt:formatDate value="${contactUs.submit_date }" type="both" pattern="yyyy-MM-dd HH:mm:ss" />
									</td>
									<td>
										<a id="${contactUs.id }" data-name="respond" data-email="${contactUs.email }" data-status="${contactUs.status }" data-toggle="modal" data-target="#respondContactUsModal" >
											<span class="glyphicon glyphicon-envelope btn-lg"></span>
										</a>
									</td>
								</tr>
							</c:forEach>
						</tbody>
						<tfoot>
							<tr>
								<td colspan="11">
									<ul class="pagination">
										<c:forEach var="num" begin="1" end="${page.totalPage }" step="1">
											<li class="${page.pageNo == num ? 'active' : ''}">
												<a href="${ctx}/broadband-user/provision/contact-us/view/${num}/${status}">${num}</a>
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

<!-- Contact Us Modal -->
<form action="${ctx}/broadband-user/provision/contact-us/respond" method="post">
	<input type="hidden" name="id" /> <input type="hidden" name="email" />
	<input type="hidden" name="content" /> <input type="hidden" name="pageNo" value="${page.pageNo}" />
	<div class="modal fade" id="respondContactUsModal" tabindex="-1" role="dialog" aria-labelledby="respondContactUsModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="respondContactUsModalLabel">
						<strong>Respond Contact Us</strong>
					</h4>
				</div>
				<div class="modal-body">
					<div class="form-group">
						<strong>Visitor/Customer's Request:</strong>
					</div>
					<div class="form-group">
						<p id="content"></p>
					</div>
					<div class="form-group">
						<strong>Our Response:</strong>
					</div>
					<div class="form-group">
						<textarea name="respond_content" class="form-control" rows="6"></textarea>
					</div>
				</div>
				<div class="modal-footer">
					<button type="submit" class="btn btn-success">Send response to this email</button>
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
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/bootstrap-select.min.js"></script>
<script type="text/javascript">
(function($) {
	
	$('#checkbox_contactUss_top').click(function() {
		var b = $(this).prop("checked");
		if (b) {
			$('input[name="checkbox_contactUss"]').prop("checked", true);
		} else {
			$('input[name="checkbox_contactUss"]').prop("checked", false);
		}
	});
	
	$('a[data-name="respond"]').click(function(){
		$('input[name="id"]').val(this.id);
		$('input[name="email"]').val($(this).attr('data-email'));
		$('input[name="content"]').html($('input[data-name="'+this.id+'_content"]').val());
		$('#content').html($('input[data-name="'+this.id+'_content"]').val());
		$('textarea[name="respond_content"]').html($('input[data-name="'+this.id+'_respond_content"]').val());
	});

})(jQuery);
</script>
<jsp:include page="../footer-end.jsp" />