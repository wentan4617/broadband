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
			<div class="panel-group" id="termsConditionsAccordion">
				<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" data-toggle="collapse" data-parent="#termsConditionsAccordion" href="#collapseTermsConditions">${panelheading }</a>
							</h4>
						</div>
						<div id="collapseTermsConditions" class="panel-collapse collapse in">
							<div class="panel-body" id="terms_conditions"></div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</form>

<script type="text/html" id="terms_conditions_tmpl">
<jsp:include page="terms-conditions.html" />
</script>

<jsp:include page="../footer.jsp" />
<jsp:include page="../script.jsp" />
<script>
(function($){

	$.termsConditions = function() {
		
		$.get('${ctx}' + '/broadband-user/system/terms_conditions_info', function(json) {
			
			$('#terms_conditions').html(tmpl('terms_conditions_tmpl'));
			
			var tc = json.model;
			if(tc!=null){
				$('#term_contracts_title').val(tc.term_contracts_title);
				$('#terms_conditions_business_retails_title').val(tc.terms_conditions_business_retails_title);
				$('#terms_conditions_business_wifi_title').val(tc.terms_conditions_business_wifi_title);
				$('#terms_conditions_personal_title').val(tc.terms_conditions_personal_title);
				$('#terms_conditions_ufb_title').val(tc.terms_conditions_ufb_title);
				$('#term_contracts').val(tc.term_contracts);
				$('#terms_conditions_business_retails').val(tc.terms_conditions_business_retails);
				$('#terms_conditions_business_wifi').val(tc.terms_conditions_business_wifi);
				$('#terms_conditions_personal').val(tc.terms_conditions_personal);
				$('#terms_conditions_ufb').val(tc.terms_conditions_ufb);
			}
			
			$('#save_btn').click(function(){

				var term_contracts_title = $('#term_contracts_title').val();
				var terms_conditions_business_retails_title = $('#terms_conditions_business_retails_title').val();
				var terms_conditions_business_wifi_title = $('#terms_conditions_business_wifi_title').val();
				var terms_conditions_personal_title = $('#terms_conditions_personal_title').val();
				var terms_conditions_ufb_title = $('#terms_conditions_ufb_title').val();
				var term_contracts = $('#term_contracts').val();
				var terms_conditions_business_retails = $('#terms_conditions_business_retails').val();
				var terms_conditions_business_wifi = $('#terms_conditions_business_wifi').val();
				var terms_conditions_personal = $('#terms_conditions_personal').val();
				var terms_conditions_ufb = $('#terms_conditions_ufb').val();
				
				var data = {
					'term_contracts_title':term_contracts_title,
					'terms_conditions_business_retails_title':terms_conditions_business_retails_title,
					'terms_conditions_business_wifi_title':terms_conditions_business_wifi_title,
					'terms_conditions_personal_title':terms_conditions_personal_title,
					'terms_conditions_ufb_title':terms_conditions_ufb_title,
					'term_contracts':term_contracts,
					'terms_conditions_business_retails':terms_conditions_business_retails,
					'terms_conditions_business_wifi':terms_conditions_business_wifi,
					'terms_conditions_personal':terms_conditions_personal,
					'terms_conditions_ufb':terms_conditions_ufb
				};

				$.post('${ctx}' + '/broadband-user/system/terms_conditions_info', data, function(json) {
					$.jsonValidation(json, 'right');
				}, 'json');
				
			});
			
		});
		
	}
	
	$.termsConditions();
	
})(jQuery)
</script>
<jsp:include page="../footer-end.jsp" />