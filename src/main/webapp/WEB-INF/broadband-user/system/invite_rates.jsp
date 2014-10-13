<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<jsp:include page="../header.jsp" />
<jsp:include page="../alert.jsp" />

<form:form modelAttribute="ir" method="post" action="${ctx}${action}" class="form-horizontal">
<div class="container">
	<div class="row">
		<div class="col-md-12">
			<div class="panel-group" id="inviteRatesAccordion">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h4 class="panel-title">
							<a data-toggle="collapse" data-toggle="collapse" data-parent="#inviteRatesAccordion" href="#collapseInviteRates">${panelheading}</a>
						</h4>
					</div>
					<div id="collapseInviteRates" class="panel-collapse collapse in">
						<div class="panel-body">
							<div class="form-group">
								<label for="inviter_user_rate" class="control-label col-md-2">
									<a href="javascript:void(0);" id="inviter_user_rate" data-toggle="tooltip" data-placement="bottom" data-original-title="The rate of everytime invitee's total transaction settlement for Agent">Inviter User Rate</a>
								</label>
								<div class="col-md-4">
									<form:input path="inviter_user_rate" class="form-control"/>
								</div>
							</div>
							<div class="form-group">
								<label for="inviter_customer_rate" class="control-label col-md-2">
									<a href="javascript:void(0);" id="inviter_customer_rate" data-toggle="tooltip" data-placement="bottom" data-original-title="The rate of everytime invitee's total transaction settlement for Customer">Inviter Customer Rate</a>
								</label>
								<div class="col-md-4">
									<form:input path="inviter_customer_rate" class="form-control"/>
								</div>
							</div>
							<div class="form-group">
								<label for="invitee_rate" class="control-label col-md-2">
									<a href="javascript:void(0);" id="invitee_rate" data-toggle="tooltip" data-placement="bottom" data-original-title="The rate of everytime invitee's total transaction settlement for Customer Him/Her self">Invitee Rate</a>
								</label>
								<div class="col-md-4">
									<form:input path="invitee_rate" class="form-control"/>
								</div>
							</div>
							<hr/>
							<div class="form-group">
								<div class="col-md-2 col-md-offset-2">
									<button type="submit" class="btn btn-success btn-lg btn-block">Save</button>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
</form:form>
<jsp:include page="../footer.jsp" />
<jsp:include page="../script.jsp" />
<jsp:include page="../footer-end.jsp" />