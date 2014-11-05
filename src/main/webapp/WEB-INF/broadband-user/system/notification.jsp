<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<jsp:include page="../header.jsp" />
<jsp:include page="../alert.jsp" />

<div class="container">
	<div class="row">
		<div class="col-md-12">
			<div class="panel panel-success">
				<div class="panel-heading"><h4 class="panel-title">${panelheading}</h4></div>
				<div class="panel-body">
					<form:form modelAttribute="notification" method="post" action="${ctx}${action }" class="form-horizontal">
						<form:hidden path="id"/>
						<div class="form-group">
							<label for="title" class="control-label col-md-2">Title</label>
							<div class="col-md-8">
								<form:input path="title" class="form-control" placeholder="Title" />
							</div>
							<p class="help-block">
								<form:errors path="title" cssErrorClass="error"/>
							</p>
						</div>
						<div class="form-group">
							<label for="type" class="control-label col-md-2">Type</label>
							<div class="col-md-3">
								<form:select path="type" class="form-control">
									<form:option value="email">Email</form:option>
									<form:option value="sms">SMS</form:option>
								</form:select>
							</div>
							<label for="sort" class="control-label col-md-2">Sort</label>
							<div class="col-md-3">
								<form:select path="sort" class="form-control">
									<form:option value="register-post-pay">Register Post Pay</form:option>
									<form:option value="register-pre-pay">Register Pre Pay</form:option>
									<form:option value="invoice">Invoice</form:option>
									<form:option value="calling-invoice">Calling Invoice</form:option>
									<form:option value="invoice-notice-third-fifth-day">Invoice Notice Third Fifth</form:option>
									<form:option value="invoice-notice-eighth-ninth-day">Invoice Notice Eighth Ninth</form:option>
									<form:option value="invoice-notice-tenth-day">Invoice Notice Tenth</form:option>
									<form:option value="plan-term_invoice">Plan Term Invoice</form:option>
									<form:option value="payment">Payment</form:option>
									<form:option value="topup-notification">Topup Notification</form:option>
									<form:option value="service-giving">Service Giving</form:option>
									<form:option value="service-giving-paid">Service Giving Paid</form:option>
									<form:option value="service-installation">Service Installation</form:option>
									<form:option value="service-terminating">Service Terminating</form:option>
									<form:option value="online-ordering">Online Ordering</form:option>
									<form:option value="online-ordering-business">Online Ordering (Business)</form:option>
									<form:option value="forgotten-password">Forgotten Password</form:option>
									<form:option value="contact-us-response">Contact Us Response</form:option>
									<form:option value="early-termination-charge">Early Termination Charge</form:option>
									<form:option value="order-warning-notice">Order Warning Notice</form:option>
									<form:option value="order-void-notice">Order Void Notice</form:option>
									<form:option value="provision-notice">Provision Notice</form:option>
								</form:select>
							</div>
						</div>
						<div class="form-group">
							<div class="col-md-3 col-md-offset-4">
								<button type="submit" class="btn btn-success btn-block">Save</button>
							</div>
						</div>
						<div class="form-group">
							<div class="col-md-12">
								<form:textarea path="content" class="form-control" rows="40" />
							</div>
						</div>
					</form:form>
				</div>
			</div>
		</div>
	</div>
</div>
<jsp:include page="../footer.jsp" />
<jsp:include page="../script.jsp" />
<jsp:include page="../footer-end.jsp" />