<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<jsp:include page="../header.jsp" />
<jsp:include page="../alert.jsp" />

<form:form modelAttribute="seo" method="post" action="${ctx}${action }" class="form-horizontal">
<div class="container">
	<div class="row">
		<div class="col-md-12">
			<div class="panel-group" id="seoAccordion">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h4 class="panel-title">
							<a data-toggle="collapse" data-toggle="collapse"
								data-parent="#seoAccordion" href="#collapseSEO">${panelheading }</a>
						</h4>
					</div>
					<div id="collapseSEO" class="panel-collapse collapse in">
						<div class="panel-body">
							<div class="form-group">
								<label for="title" class="control-label col-md-2">
									<a href="javascript:void(0);" data-toggle="tooltip" data-placement="bottom" data-original-title="Website title">Title</a>
								</label>
								<div class="col-md-10">
									<form:textarea path="title" class="form-control" rows="8"/>
								</div>
							</div>
							<div class="form-group">
								<label for="description" class="control-label col-md-2">
									<a href="javascript:void(0);" data-toggle="tooltip" data-placement="bottom" data-original-title="Website description">Description</a>
								</label>
								<div class="col-md-10">
									<form:textarea path="description" class="form-control" rows="10"/>
								</div>
							</div>
							<div class="form-group">
								<label for="keywords" class="control-label col-md-2">
									<a href="javascript:void(0);" data-toggle="tooltip" data-placement="bottom" data-original-title="Website keywords">Keywords</a>
								</label>
								<div class="col-md-10">
									<form:textarea path="keywords" class="form-control" rows="10"/>
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