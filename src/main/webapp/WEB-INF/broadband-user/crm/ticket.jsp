<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<jsp:include page="../header.jsp" />
<jsp:include page="../alert.jsp" />

<style>
.panel-default {
	border-top-color:transparent;
}

</style>


<div class="container">
	<div class="row">
		<div class="col-md-12">
		
			<!-- Nav tabs -->
			<ul class="nav nav-tabs">
				<li class="active"><a href="#ticket_edit" data-toggle="tab"><strong>Ticket Edit</strong></a></li>
				<li><a href="#ticket_comments" data-toggle="tab"><strong>Ticket Comments</strong></a></li>
			</ul>

			<!-- Tab panes -->
			<div class="tab-content panel panel-default">
				<div class="panel-body tab-pane fade in active" id="ticket_edit" ></div>
				<div class="panel-body tab-pane fade" id="ticket_comments"></div>
			</div>
		</div>
	</div>
</div>

<!-- Ticket Info Template -->
<script type="text/html" id="ticket_info_table_tmpl">
<jsp:include page="ticket-info.html" />
</script>
<!-- Ticket Comment Template -->
<script type="text/html" id="ticket_comments_table_tmpl">
<jsp:include page="ticket-comments-view-page.html" />
</script>

<jsp:include page="../footer.jsp" />
<jsp:include page="../script.jsp" />
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/bootstrap-select.min.js"></script>
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/jTmpl.js"></script>
<script type="text/javascript">
(function($){
	
	$.getTicketInfo = function() {
		
		var data = { 'id' : ${ticket.id} };
		
		$.get('${ctx}/broadband-user/crm/ticket/edit', data, function(map){
			map.ticket.ctx = '${ctx}';
			map.ticket.user_role = '${userSession.user_role}';
			map.ticket.users = [];
			<c:forEach var="u" items="${users}">
				var user = new Object();
				user.id = '${u.id}';
				user.user_name = '${u.user_name}';
				map.ticket.users.push(user);
			</c:forEach>
	   		var $table = $('#ticket_edit');
			$table.html(tmpl('ticket_info_table_tmpl', map.ticket));
			
			// If not published by current user or (not current user and is system-developer)
			if('${userSession.id}'!='${ticket.user_id}'
			||('${userSession.id}'!='${ticket.user_id}' && '${userSession.user_role}'!='system-developer')){
				$('#first_name')[0].disabled=true;
				$('#last_name')[0].disabled=true;
				$('#cellphone')[0].disabled=true;
				$('#email')[0].disabled=true;
				$('#description')[0].disabled=true;
				$('#updateTicketBtn').css('display', 'none');
			}
			
			// Update Ticket
			$('#updateTicketBtn').click(function(){
				$('#editTicketModal').modal('show');
			});
			
			$('#editTicketBtn').click(function(){
				var first_name = $('#first_name').val();
				var last_name = $('#last_name').val();
				var cellphone = $('#cellphone').val();
				var email = $('#email').val();
				var description = $('#description').val();
				
				var data = {
					'id':'${ticket.id}',
					'first_name':first_name,
					'last_name':last_name,
					'cellphone':cellphone,
					'email':email,
					'description':description
				};
				
				$.post('${ctx}/broadband-user/crm/ticket/edit', data, function(json){
					$.jsonValidation(json, 'right');					
				}, "json");
			});
			
			// Delete Ticket
			$('#deleteTicketBtn').click(function(){
				$('#deleteTicketModal').modal('show');
			});			
			
		}, "json");
	}
	
	$.getTicketComment = function(pageNo) {
		var data = { 'ticket_id' : ${ticket.id}, 'pageNo' : pageNo };
		$.get('${ctx}/broadband-user/crm/ticket/comment/view', data, function(page){
			page.ctx = '${ctx}';
			page.ticket_id = '${ticket.id}';
			page.users = [];
			<c:forEach var="u" items="${users}">
				var user = new Object();
				user.id = '${u.id}';
				user.user_name = '${u.user_name}';
				page.users.push(user);
			</c:forEach>
	   		var $table = $('#ticket_comments');
			$table.html(tmpl('ticket_comments_table_tmpl', page));
			$table.find('tfoot a').click(function(){
				$.getTicketComment($(this).attr('data-pageNo'));
			});
			
			$('a[data-name="new_comment_btn"]').click(function(){
				$('a[data-name="new_comment_btn"]').button('loading');
				$('#ticketCommentModal').modal('show');
			});
			$('a[data-name="ticketCommentModalBtn"]').click(function(){
				var content = $('textarea[data-name="comment_content"]').val();
				var data = { 'ticket_id' : ${ticket.id}, 'content' : content };
				
				$.post('${ctx}/broadband-user/crm/ticket/comment/create', data, function(json){
					$.jsonValidation(json, 'right');
				}, 'json');
			});
			$('#ticketCommentModal').on('hidden.bs.modal', function(){
				$('a[data-name="new_comment_btn"]').button('reset');
				$.getTicketComment(pageNo);
			});
			
			$('a[data-name="view_comment_btn"]').click(function(){
				$('#comment_content').html($(this).attr('data-content'));
				$('#ticketCommentViewModal').modal('show');
			});
			
		}, "json");
	}

	$.getTicketInfo();
	$.getTicketComment(1);
	
	
})(jQuery);
</script>
<jsp:include page="../footer-end.jsp" />