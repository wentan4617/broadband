<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<jsp:include page="header.jsp" />

<div style="background:#eee;padding-bottom:200px;padding-top:20px;">
	<div class="container">
		<c:if test="${responseBean != null && responseBean.success == '1' }">
			<div class="alert alert-success alert-dismissable">
				<h1>Congratulations ! ! !</h1>
				<p>
					Your plan application has been sent to our system, our customer service staff will immediately get in touch with you.
				</p>
				<p>
					
					Now you can use your login name and password, log on to CyberPark Customer Home, <a href="${ctx }/login"> please click here. </a>
				</p>
				<p>
					Our customer service telephone number is <strong class="text-danger">0800 899 880</strong>
				</p>
				<p>&nbsp;</p>
				<p>
					<a href="${ctx }" class="btn btn-success">Go to Home Page</a>
				</p>
			</div>
		</c:if>
		
		
		<c:if test="${responseBean == null || responseBean.success != '1' }">
			<div class="alert alert-warning">
				<h1>Sorry, the payment is failure. </h1> 
				<h2>
					<a href="${ctx }/order/confirm">Click Here</a> to return to the payment page. 
				</h2>
				<p>&nbsp;</p>
				<p>
					<a href="${ctx }" class="btn btn-success">Go to Home Page</a>
				</p>
			</div>
		</c:if>
	</div>
</div>

<jsp:include page="footer.jsp" />
<jsp:include page="script.jsp" />
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/holder.js"></script>
<jsp:include page="footer-end.jsp" />