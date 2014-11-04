<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<jsp:include page="../header.jsp" />

<div class="container" style="padding-bottom: 200px; padding-top: 20px;">

	<div class="alert alert-success alert-dismissable">
		<h1>Congratulations ! ! !</h1>
		<p>Your plan application has been sent to our system, our customer service staff will immediately get in touch with you.</p>
		<p class="text-danger">
			Please check your email. Your receipt or ordering form will send your email.
		</p>
		<p>
			Now you can log on to CyberPark Customer Home, <a href="${ctx }/sign-in"> please click here. </a>
		</p>
		<p>
			Our customer service telephone number is <strong class="text-danger">0800 229 237</strong>
		</p>
		<p>&nbsp;</p>
		<p>
			<a href="${ctx }/home" class="btn btn-success">Go to Home Page</a>
		</p>
	</div>
	
</div>


<jsp:include page="../footer.jsp" />
<jsp:include page="../script.jsp" />
<jsp:include page="../footer-end.jsp" />