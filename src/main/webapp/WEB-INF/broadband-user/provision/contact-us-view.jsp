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
								<c:if test="${closedActive=='active'}">
								<th>Responder</th>
								</c:if>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="contactUs" items="${page.results }">
								<textarea data-name="${contactUs.id}_content" style="display:none;">${contactUs.content}</textarea>
								<textarea data-name="${contactUs.id}_respond_content" style="display:none;">${contactUs.respond_content}</textarea>
								<tr>
									<td>
										<input type="checkbox" name="checkbox_contactUss" value="${contactUs.id}"/>
									</td>
									<td data-name="${contactUs.id}_first_name" data-value="${contactUs.first_name }">
										${contactUs.first_name }
									</td>
									<td data-name="${contactUs.id}_last_name" data-value="${contactUs.last_name }">
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
									<td style="font-size:20px;">
										<a href="javascript:(0);" id="${contactUs.id }" data-name="respond" data-email="${contactUs.email }" data-status="${contactUs.status }" data-toggle="tooltip" data-placement="bottom" data-original-title="Respond customer by E-Mail">
											<span class="glyphicon glyphicon-envelope"></span>
										</a>
									</td>
									<c:if test="${closedActive=='active'}">
									<td>
										<c:forEach var="u" items="${users}">
											<c:if test="${u.id==contactUs.user_id}">
												${u.user_name}
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
		<div class="modal-dialog" style="width:1200px;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="respondContactUsModalLabel">
						<strong>Respond Contact Us</strong>
					</h4>
				</div>
				<div class="modal-body">
					<div class="row">
						<div class="col-md-12">
							<div class="form-group">
								<strong>Visitor/Customer's Request:</strong>
							</div>
							<div class="form-group">
								<p id="content" style="border:1px #dedede solid; padding:10px; border-radius:10px"></p>
							</div>
						</div>
						<hr/>
					</div>
					<div class="row">
						<div class="col-md-6">
							<div class="form-group">
								Our Response:<strong>(editable, Press Ctrl+Z for undo)</strong>
							</div>
							<div class="form-group">
								<textarea id="respond_content" name="respond_content" class="form-control" rows="18"></textarea>
							</div>
						</div>
						<div class="col-md-6">
							<div class="form-group">
								Content Preview:<strong>(Immediate view layer)</strong>
							</div>
							<p class="form-group" data-name="view-mode" style="border:1px #dedede solid; display:none; height:374px;"></p>
						</div>
					</div>
					<strong>Shortcuts:</strong>
					<div class="form-group">
						<div class="btn-group">
							<button type="button" class="btn btn-default btn-xs" data-name="newline" data-toggle="tooltip" data-placement="bottom" data-original-title="Start a new line behind focus point">
							  <span class="glyphicon glyphicon-arrow-down"></span>
							</button>
							<button type="button" class="btn btn-default btn-xs" data-name="italic" data-toggle="tooltip" data-placement="bottom" data-original-title="Tilt selected words">
							  <span class="glyphicon glyphicon-italic"></span>
							</button>
							<button type="button" class="btn btn-default btn-xs" data-name="strong" data-toggle="tooltip" data-placement="bottom" data-original-title="Emphasize selected words">
							  <span class="glyphicon glyphicon-bold" style="font-weight:bold;"></span>
							</button>
							<button type="button" class="btn btn-default btn-xs" data-name="horizontal-line" data-toggle="tooltip" data-placement="bottom" data-original-title="Horizontal line">
							  <span class="glyphicon glyphicon-header"></span>
							</button>
							<button type="button" class="btn btn-default btn-xs" data-name="view-mode" data-toggle="tooltip" data-placement="bottom" data-original-title="Have a preview">
							  <span class="glyphicon glyphicon-eye-open"></span>
							</button>
						</div>
						<hr/>
						<div class="btn-group">
							<button type="button" class="btn btn-default btn-xs" data-name="template-1" data-toggle="tooltip" data-placement="bottom" data-original-title="Template 1">
							  <span class="glyphicon glyphicon-book"></span>
							</button>
							<button type="button" class="btn btn-default btn-xs" data-name="template-2" data-toggle="tooltip" data-placement="bottom" data-original-title="Template 2">
							  <span class="glyphicon glyphicon-book"></span>
							</button>
						</div>
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
	$('button[data-toggle="tooltip"]').tooltip();
	
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
		$('#content').html($('textarea[data-name="'+this.id+'_content"]').val());
		$('#respond_content').text($('textarea[data-name="'+this.id+'_respond_content"]').val());
		$('button[data-name="template-1"]').attr('id', this.id);
		$('button[data-name="template-2"]').attr('id', this.id);
		viewMode();
		$('#respondContactUsModal').modal('show');
	});
	
	$('#respond_content').keydown(function(){
		viewMode();
	});
	
	$('#respond_content').keyup(function(){
		viewMode();
	});
	
	function insertStyles(tag, type){
		var content = $('#respond_content')[0];
		if(content.selectionStart || content.selectionStart == '0'){
			var distance = content.selectionEnd - content.selectionStart;
			var tagLength = tag[0].length;
			
			//以下这句，应该是在焦点之前，和焦点之后的位置，中间插入我们传入的值 .然后把这个得到的新值，赋给文本框
			var valueHolder = content.value.substring(content.selectionStart, content.selectionEnd);
			if(type == 'single'){
				// single tag
				content.value = content.value.substring(0, content.selectionStart) + valueHolder + tag.shift() + content.value.substring(content.selectionEnd, content.value.length);
			} else if(type == 'double'){
				// double tag
				content.value = content.value.substring(0, content.selectionStart) + tag.shift() + valueHolder + tag.shift() + content.value.substring(content.selectionEnd, content.value.length);
			}
			//在输入元素textara没有定位光标的情况
			content.selectionStart += tagLength;
			content.selectionEnd = content.selectionEnd + distance;
			content.focus();
		}
		return content;
	}
	
	function viewMode(){
		var content = $('#respond_content');
		var viewDiv = $('p[data-name="view-mode"]');
		viewDiv.css('padding','10px');
		viewDiv.css('border-radius', '10px');
		viewDiv.css('display', '');
		viewDiv.html(content.val());
		content.selectionStart = null;
		content.selectionEnd = null;
	}
	
	$('button[data-name="newline"]').click(function(){
		insertStyles(new Array('<br/>'), 'single');
		viewMode();
	})
	
	$('button[data-name="italic"]').click(function(){
		insertStyles(new Array('<i>','</i>'), 'double');
		viewMode();
	})
	
	$('button[data-name="strong"]').click(function(){
		insertStyles(new Array('<strong>','</strong>'), 'double');
		viewMode();
	})
	
	$('button[data-name="horizontal-line"]').click(function(){
		insertStyles(new Array('<hr/>'), 'single');
		viewMode();
	})
	
	$('button[data-name="view-mode"]').click(function(){
		viewMode();
	})
	
	$('button[data-name="template-1"]').click(function(){
		var content = $('#respond_content');
		var first_name = $('td[data-name="'+this.id+'_first_name"]').attr('data-value');
		var last_name = $('td[data-name="'+this.id+'_last_name"]').attr('data-value');
		content.val(
			'Dear <strong>' + last_name + ',' + first_name + '</strong><br/><br/>\n\n'
			+'<blockquote>\n'
			+'\t<p>We have view your request, and we are solving it recently!</p>\n'
			+'</blockquote><br/><br/>\n\n'
			+'Kind regards,<br/><br/>\n\n'
			+'<p style="color:#8866dd;">\n'
			+'<strong>${userSession.user_name}</strong><br/>\n'
			+'<strong>${userSession.user_role}, CyberPark Limited</strong><br/>\n'
			+'</p>'
		);
		viewMode();
	})
	
	$('button[data-name="template-2"]').click(function(){
		var content = $('#respond_content');
		var first_name = $('td[data-name="'+this.id+'_first_name"]').attr('data-value');
		var last_name = $('td[data-name="'+this.id+'_last_name"]').attr('data-value');
		content.val(
			'Hi <strong>' + last_name + ',' + first_name + '</strong><br/><br/>\n\n'
			+'<blockquote>\n'
			+'<p>We have view your request, and we are solving it recently!</p>\n'
			+'</blockquote><br/><br/>\n\n'
			+'Kind regards,<br/><br/>\n\n'
			+'<p style="color:#8866dd;">\n'
			+'<strong>${userSession.user_name}</strong><br/>\n'
			+'<strong>${userSession.user_role}</strong><br/>\n'
			+'<strong>CyberPark Limited</strong><br/>\n'
			+'</p>'
		);
		viewMode();
	})

})(jQuery);
</script>
<jsp:include page="../footer-end.jsp" />