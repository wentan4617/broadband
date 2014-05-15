<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<jsp:include page="header.jsp" />

<style>
.home-title {
	font-size:36px;
}
</style>

<div style="background:#EFEFEF;padding-bottom:20px;">
<div class="container">

	<!-- non-naked plans -->
	<div class="page-header">
		<h1>
			${nonNakedTitle } <small>Please choose what you need</small>
		</h1>
	</div>
	<div class="row">
		<c:if test="${fn:length(nonNakedPlans) > 0 }">
			<c:forEach var="plan" items="${nonNakedPlans }">
				<div class="col-md-3">
					<div class="thumbnail">
						<img src="data:image/png;base64," data-src="holder.js/300x200" alt="...">
						<div class="caption">
							<h3>Thumbnail label</h3>
							<p>...</p>
							<p>
								<a href="${ctx }/order" class="btn btn-success" role="button">Purchase</a> 
							</p>
						</div>
					</div>
				</div>
			</c:forEach>
		</c:if>
		<c:if test="${fn:length(nonNakedPlans) <= 0 }">
			<div class="alert alert-warning">
				Sorry, no search products.
			</div>
		</c:if>
		
		
		<div class="col-md-3">
			<div class="thumbnail">
				<img data-src="holder.js/300x200" alt="...">
				<div class="caption">
					<h3>Thumbnail label</h3>
					<p>...</p>
					<p>
						<a href="${ctx }/order" class="btn btn-success" role="button">Purchase</a> 
					</p>
				</div>
			</div>
		</div>
		<div class="col-md-3">
			<div class="thumbnail">
				<img data-src="holder.js/300x200" alt="...">
				<div class="caption">
					<h3>Thumbnail label</h3>
					<p>...</p>
					<p>
						<a href="${ctx }/order" class="btn btn-success" role="button">Purchase</a> 
					</p>
				</div>
			</div>
		</div>
	</div>
	
	<!-- naked plans -->
	<div class="home-title">
		<h1>
			${nakedTitle } <small>Please choose what you need</small>
		</h1>
	</div>
	<div class="row">
		<div class="col-md-3">
			<div class="thumbnail">
				<img src="data:image/png;base64," data-src="holder.js/300x200" alt="...">
				<div class="caption">
					<h3>Thumbnail label</h3>
					<p>...</p>
					<p>
						<a href="${ctx }/order" class="btn btn-success" role="button">Purchase</a> 
					</p>
				</div>
			</div>
		</div>
		<div class="col-md-3">
			<div class="thumbnail">
				<img data-src="holder.js/300x200" alt="...">
				<div class="caption">
					<h3>Thumbnail label</h3>
					<p>...</p>
					<p>
						<a href="${ctx }/order" class="btn btn-success" role="button">Purchase</a> 
					</p>
				</div>
			</div>
		</div>
		<div class="col-md-3">
			<div class="thumbnail">
				<img data-src="holder.js/300x200" alt="...">
				<div class="caption">
					<h3>Thumbnail label</h3>
					<p>...</p>
					<p>
						<a href="${ctx }/order" class="btn btn-success" role="button">Purchase</a> 
					</p>
				</div>
			</div>
		</div>
	</div>
</div>
</div>

<jsp:include page="footer.jsp" />
<jsp:include page="script.jsp" />
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/holder.js"></script>
<jsp:include page="footer-end.jsp" />