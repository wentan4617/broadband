<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<jsp:include page="../header.jsp" />
<jsp:include page="../alert.jsp" />

<form class="form-horizontal">
<div class="container">
	<div class="row">
		<div class="col-md-12">
			<div class="panel-group" id="websiteEditableDetailsAccordion">
				<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" data-toggle="collapse"
									data-parent="#planIntroductionsAccordion" href="#collapsePlanIntroductions">${panelheading }</a>
							</h4>
						</div>
						<div id="collapsePlanIntroductions" class="panel-collapse collapse in">
							<div class="panel-body" id="plan_introductions"></div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</form>

<script type="text/html" id="plan_introductions_tmpl">
<jsp:include page="plan-introductions.html" />
</script>

<jsp:include page="../footer.jsp" />
<jsp:include page="../script.jsp" />
<script>
(function($){

	$.planIntroductions = function() {
		
		$.get('${ctx}' + '/broadband-user/system/plan_introductions_info', function(json) {
			
			$('#plan_introductions').html(tmpl('plan_introductions_tmpl'));
			
			var pi = json.model;
			if(pi!=null){
				$('#adsl_title').val(pi.adsl_title);
				$('#adsl_content').val(pi.adsl_content);
				$('#vdsl_title').val(pi.vdsl_title);
				$('#vdsl_content').val(pi.vdsl_content);
				$('#ufb_title').val(pi.ufb_title);
				$('#ufb_content').val(pi.ufb_content);
			}
			
			$('#save_btn').click(function(){
				
				var adsl_title = $('#adsl_title').val();
				var adsl_content = $('#adsl_content').val();
				var vdsl_title = $('#vdsl_title').val();
				var vdsl_content = $('#vdsl_content').val();
				var ufb_title = $('#ufb_title').val();
				var ufb_content = $('#ufb_content').val();
				
				var data = {
					'adsl_title':adsl_title,
					'adsl_content':adsl_content,
					'vdsl_title':vdsl_title,
					'vdsl_content':vdsl_content,
					'ufb_title':ufb_title,
					'ufb_content':ufb_content
				};
				

				$.post('${ctx}' + '/broadband-user/system/plan_introductions_info', data, function(json) {
					$.jsonValidation(json, 'right');
				}, 'json');
				
			});
			
		});
		
	}
	
	$.planIntroductions();
	
})(jQuery)
</script>
<jsp:include page="../footer-end.jsp" />