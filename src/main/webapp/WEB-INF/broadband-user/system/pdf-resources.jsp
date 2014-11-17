<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<jsp:include page="../header.jsp" />
<jsp:include page="../alert.jsp" />

<form class="form-horizontal" action="${ctx}/broadband-user/system/pdf_resources/upload" enctype="multipart/form-data" method="post">
<div class="container">
	<div class="row">
		<div class="col-md-12">
			<div class="panel-group" id="pdfResourcesAccordion">
				<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" data-toggle="collapse"
									data-parent="#pdfResourcesAccordion" href="#collapsePDFResources">${panelheading }</a>
							</h4>
						</div>
							
						<div id="collapsePDFResources" class="panel-collapse collapse in">
							<div class="panel-body">
							
								<div class="form-group">
									<label for="common_company_lg_path" class="control-label col-md-4">Common Company Logo</label>
									<div class="col-md-4" style="background-color:#f5f5f5;">
										<c:choose>
											<c:when test="${pdfr.common_company_lg_path==null || pdfr.common_company_lg_path==''}">
												<img style="border:2px solid black; width:145px; height:68px;" src="data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiIHN0YW5kYWxvbmU9InllcyI/PjxzdmcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIiB3aWR0aD0iMTQwIiBoZWlnaHQ9IjE0MCIgdmlld0JveD0iMCAwIDE0MCAxNDAiIHByZXNlcnZlQXNwZWN0UmF0aW89Im5vbmUiPjxkZWZzLz48cmVjdCB3aWR0aD0iMTQwIiBoZWlnaHQ9IjE0MCIgZmlsbD0iI0VFRUVFRSIvPjxnPjx0ZXh0IHg9IjQ1LjUiIHk9IjcwIiBzdHlsZT0iZmlsbDojQUFBQUFBO2ZvbnQtd2VpZ2h0OmJvbGQ7Zm9udC1mYW1pbHk6QXJpYWwsIEhlbHZldGljYSwgT3BlbiBTYW5zLCBzYW5zLXNlcmlmLCBtb25vc3BhY2U7Zm9udC1zaXplOjEwcHQ7ZG9taW5hbnQtYmFzZWxpbmU6Y2VudHJhbCI+MTQweDE0MDwvdGV4dD48L2c+PC9zdmc+" alt="Common Company Logo" title="Common Company Logo">
											</c:when>
											<c:otherwise>
												<img style="border:2px solid black; width:145px; height:68px;" src="${ctx}/upload/pdf/common_company_logo.png" alt="Common Company Logo" title="Common Company Logo">
											</c:otherwise>
										</c:choose>
									</div>
								</div>
								
								<div class="form-group">
									<div class="col-md-4 col-md-offset-4">
										<input type="file" class="form-control" name="common_company_lg_path" />
									</div>
								</div>
							
							
								<div class="form-group">
									<label for="invoice_company_lg_path" class="control-label col-md-4">Invoice Company Logo</label>
									<div class="col-md-4" style="background-color:#f5f5f5;">
										<c:choose>
											<c:when test="${pdfr.invoice_company_lg_path==null || pdfr.invoice_company_lg_path==''}">
												<img style="border:2px solid black; width:201px; height:80px;" src="data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiIHN0YW5kYWxvbmU9InllcyI/PjxzdmcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIiB3aWR0aD0iMTQwIiBoZWlnaHQ9IjE0MCIgdmlld0JveD0iMCAwIDE0MCAxNDAiIHByZXNlcnZlQXNwZWN0UmF0aW89Im5vbmUiPjxkZWZzLz48cmVjdCB3aWR0aD0iMTQwIiBoZWlnaHQ9IjE0MCIgZmlsbD0iI0VFRUVFRSIvPjxnPjx0ZXh0IHg9IjQ1LjUiIHk9IjcwIiBzdHlsZT0iZmlsbDojQUFBQUFBO2ZvbnQtd2VpZ2h0OmJvbGQ7Zm9udC1mYW1pbHk6QXJpYWwsIEhlbHZldGljYSwgT3BlbiBTYW5zLCBzYW5zLXNlcmlmLCBtb25vc3BhY2U7Zm9udC1zaXplOjEwcHQ7ZG9taW5hbnQtYmFzZWxpbmU6Y2VudHJhbCI+MTQweDE0MDwvdGV4dD48L2c+PC9zdmc+" alt="Invoice Company Logo" title="Invoice Company Logo">
											</c:when>
											<c:otherwise>
												<img style="border:2px solid black; width:201px; height:80px;" src="${ctx}/upload/pdf/invoice_company_logo.png" alt="Invoice Company Logo" title="Invoice Company Logo">
											</c:otherwise>
										</c:choose>
									</div>
								</div>
								
								<div class="form-group">
									<div class="col-md-4 col-md-offset-4">
										<input type="file" class="form-control" name="invoice_company_lg_path" />
									</div>
								</div>
							
								<div class="form-group">
									<label for="company_lg_customer_service_bar_path" class="control-label col-md-4">Company Logo Customer Service Bar</label>
									<div class="col-md-4" style="background-color:#f5f5f5;">
										<c:choose>
											<c:when test="${pdfr.company_lg_customer_service_bar_path==null || pdfr.company_lg_customer_service_bar_path==''}">
												<img style="border:2px solid black; width:581px; height:90px;" src="data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiIHN0YW5kYWxvbmU9InllcyI/PjxzdmcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIiB3aWR0aD0iMTQwIiBoZWlnaHQ9IjE0MCIgdmlld0JveD0iMCAwIDE0MCAxNDAiIHByZXNlcnZlQXNwZWN0UmF0aW89Im5vbmUiPjxkZWZzLz48cmVjdCB3aWR0aD0iMTQwIiBoZWlnaHQ9IjE0MCIgZmlsbD0iI0VFRUVFRSIvPjxnPjx0ZXh0IHg9IjQ1LjUiIHk9IjcwIiBzdHlsZT0iZmlsbDojQUFBQUFBO2ZvbnQtd2VpZ2h0OmJvbGQ7Zm9udC1mYW1pbHk6QXJpYWwsIEhlbHZldGljYSwgT3BlbiBTYW5zLCBzYW5zLXNlcmlmLCBtb25vc3BhY2U7Zm9udC1zaXplOjEwcHQ7ZG9taW5hbnQtYmFzZWxpbmU6Y2VudHJhbCI+MTQweDE0MDwvdGV4dD48L2c+PC9zdmc+" alt="Company Logo Customer Service Bar" title="Company Logo Customer Service Bar">
											</c:when>
											<c:otherwise>
												<img style="border:2px solid black; width:581px; height:90px;" src="${ctx}/upload/pdf/company_lg_customer_service_bar.png" alt="Company Logo Customer Service Bar" title="Company Logo Customer Service Bar">
											</c:otherwise>
										</c:choose>
									</div>
								</div>
								
								<div class="form-group">
									<div class="col-md-4 col-md-offset-4">
										<input type="file" class="form-control" name="company_lg_customer_service_bar_path" />
									</div>
								</div>
							
								<div class="form-group">
									<label for="two_dimensional_code_path" class="control-label col-md-4">Two Dimensional Code</label>
									<div class="col-md-4" style="background-color:#f5f5f5;">
										<c:choose>
											<c:when test="${pdfr.two_dimensional_code_path==null || pdfr.two_dimensional_code_path==''}">
												<img style="border:2px solid black; width:300px; height:300px;" src="data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiIHN0YW5kYWxvbmU9InllcyI/PjxzdmcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIiB3aWR0aD0iMTQwIiBoZWlnaHQ9IjE0MCIgdmlld0JveD0iMCAwIDE0MCAxNDAiIHByZXNlcnZlQXNwZWN0UmF0aW89Im5vbmUiPjxkZWZzLz48cmVjdCB3aWR0aD0iMTQwIiBoZWlnaHQ9IjE0MCIgZmlsbD0iI0VFRUVFRSIvPjxnPjx0ZXh0IHg9IjQ1LjUiIHk9IjcwIiBzdHlsZT0iZmlsbDojQUFBQUFBO2ZvbnQtd2VpZ2h0OmJvbGQ7Zm9udC1mYW1pbHk6QXJpYWwsIEhlbHZldGljYSwgT3BlbiBTYW5zLCBzYW5zLXNlcmlmLCBtb25vc3BhY2U7Zm9udC1zaXplOjEwcHQ7ZG9taW5hbnQtYmFzZWxpbmU6Y2VudHJhbCI+MTQweDE0MDwvdGV4dD48L2c+PC9zdmc+" alt="Two Dimensional Code" title="Two Dimensional Code">
											</c:when>
											<c:otherwise>
												<img style="border:2px solid black; width:300px; height:300px;" src="${ctx}/upload/pdf/two_dimensional_code.png" alt="Two Dimensional Code" title="Two Dimensional Code">
											</c:otherwise>
										</c:choose>
									</div>
								</div>
								
								<div class="form-group">
									<div class="col-md-4 col-md-offset-4">
										<input type="file" class="form-control" name="two_dimensional_code_path" />
									</div>
								</div>
								
								<hr/>
								<div class="form-group">
									<div class="col-md-2 col-md-offset-4">
										<button type="submit" class="btn btn-success btn-xs btn-block">Save</button>
									</div>
								</div>
								
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</form>

<jsp:include page="../footer.jsp" />
<jsp:include page="../script.jsp" />
<jsp:include page="../footer-end.jsp" />