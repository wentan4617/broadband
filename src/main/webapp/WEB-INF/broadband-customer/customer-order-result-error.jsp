<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<jsp:include page="header.jsp" />

<div class="container" style="padding-bottom: 200px; padding-top: 20px;">
	<c:if test="${responseBean == null || responseBean.success != '1' }">
		<div class="alert alert-warning">
			<h1>Sorry, the payment is failure.</h1>
			<h2>
				<a href="${ctx }/order/${orderPlan.plan_class }/confirm">Click Here</a> to return to the order confirm page.
			</h2>
			<p>&nbsp;</p>
			<p>
				<a href="${ctx }" class="btn btn-success">Go to Home Page</a>
			</p>
		</div>
	</c:if>
</div>


<jsp:include page="footer.jsp" />
<%-- <jsp:include page="script.jsp" /> --%>
<jsp:include page="footer-end.jsp" />