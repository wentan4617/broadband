<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<jsp:include page="../../header.jsp" />

<div class="container" style="padding-bottom: 200px; padding-top: 20px;">

	<c:if test="${responseBean != null && responseBean.success == '1' }">
		<div class="alert alert-success alert-dismissable">
			<h1>Congratulations ! ! !</h1>
			<p>Your plan application has been sent to our system, our customer service staff will immediately get in touch with you.</p>
			<p class="text-danger">
				Please check your email. Your receipt or ordering form will send your email.
			</p>
			<p>
				Our customer service telephone number is <strong class="text-danger">0800 229 237</strong>
			</p>
			<p>&nbsp;</p>
			<p>
				<a href="${ctx }/broadband-user/crm/customer/order/view" class="btn btn-success">Go to CRM</a>
			</p>
		</div>
	</c:if>
	
</div>


<jsp:include page="../../footer.jsp" />
<jsp:include page="script.jsp" />
<jsp:include page="../../footer-end.jsp" />