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
									data-parent="#websiteEditableDetailsAccordion" href="#collapseWebsiteEditableDetails">${panelheading }</a>
							</h4>
						</div>
						<div id="collapseWebsiteEditableDetails" class="panel-collapse collapse in">
							<div class="panel-body" id="website_editable_details"></div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</form>

<script type="text/html" id="website_editable_details_tmpl">
<jsp:include page="website-editable-details.html" />
</script>

<jsp:include page="../footer.jsp" />
<jsp:include page="../script.jsp" />
<script>
(function($){

	$.websiteEditableDetails = function() {
		
		$.get('${ctx}' + '/broadband-user/system/website_editable_details_info', function(json) {
			
			$('#website_editable_details').html(tmpl('website_editable_details_tmpl'));
			
			var wed = json.model;
			if(wed!=null){
				$('#company_name').val(wed.company_name);
				$('#company_name_ltd').val(wed.company_name_ltd);
				$('#company_hot_line_no').val(wed.company_hot_line_no);
				$('#company_hot_line_no_alphabet').val(wed.company_hot_line_no_alphabet);
				$('#company_address').val(wed.company_address);
				$('#company_email').val(wed.company_email);
				$('#website_year').val(wed.website_year);
			}
			
			$('#save_btn').click(function(){
				
				var company_name = $('#company_name').val();
				var company_name_ltd = $('#company_name_ltd').val();
				var company_hot_line_no = $('#company_hot_line_no').val();
				var company_hot_line_no_alphabet = $('#company_hot_line_no_alphabet').val();
				var company_address = $('#company_address').val();
				var company_email = $('#company_email').val();
				var website_year = $('#website_year').val();
				
				var data = {
					'company_name':company_name,
					'company_name_ltd':company_name_ltd,
					'company_hot_line_no':company_hot_line_no,
					'company_hot_line_no_alphabet':company_hot_line_no_alphabet,
					'company_address':company_address,
					'company_email':company_email,
					'website_year':website_year
				};

				$.post('${ctx}' + '/broadband-user/system/website_editable_details_info', data, function(json) {
					$.jsonValidation(json, 'right');
				}, 'json');
				
			});
			
		});
		
	}
	
	$.websiteEditableDetails();
	
})(jQuery)
</script>
<jsp:include page="../footer-end.jsp" />