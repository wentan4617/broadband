<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<jsp:include page="../header.jsp" />
<jsp:include page="../alert.jsp" />

<form class="form-horizontal" action="${ctx}/broadband-user/system/website_static_resources/upload" enctype="multipart/form-data" method="post">
<div class="container">
	<div class="row">
		<div class="col-md-12">
			<div class="panel-group" id="websiteEditableDetailsAccordion">
				<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" data-toggle="collapse"
									data-parent="#planIntroductionsAccordion" href="#collapsePlanIntroductions">${panelheading }</a>
							</h4>
						</div>
							
						<div id="collapsePlanIntroductions" class="panel-collapse collapse in">
							<div class="panel-body">
							
								<div class="form-group">
									<label for="logo_path" class="control-label col-md-4">Logo Preview (width = 111px, height = 46px)</label>
									<div class="col-md-4" style="background-color:#f5f5f5;">
										<c:choose>
											<c:when test="${wsr.logo_path==null || wsr.logo_path==''}">
												<img style="border:2px solid black;" src="data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiIHN0YW5kYWxvbmU9InllcyI/PjxzdmcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIiB3aWR0aD0iMTQwIiBoZWlnaHQ9IjE0MCIgdmlld0JveD0iMCAwIDE0MCAxNDAiIHByZXNlcnZlQXNwZWN0UmF0aW89Im5vbmUiPjxkZWZzLz48cmVjdCB3aWR0aD0iMTQwIiBoZWlnaHQ9IjE0MCIgZmlsbD0iI0VFRUVFRSIvPjxnPjx0ZXh0IHg9IjQ1LjUiIHk9IjcwIiBzdHlsZT0iZmlsbDojQUFBQUFBO2ZvbnQtd2VpZ2h0OmJvbGQ7Zm9udC1mYW1pbHk6QXJpYWwsIEhlbHZldGljYSwgT3BlbiBTYW5zLCBzYW5zLXNlcmlmLCBtb25vc3BhY2U7Zm9udC1zaXplOjEwcHQ7ZG9taW5hbnQtYmFzZWxpbmU6Y2VudHJhbCI+MTQweDE0MDwvdGV4dD48L2c+PC9zdmc+" style="width:111px; height:46px;" alt="Company Logo" title="Company Logo">
											</c:when>
											<c:otherwise>
												<img style="border:2px solid black;" src="${ctx}/upload/front-end/logo.png" style="width:111px; height:46px;" alt="Company Logo" title="Company Logo">
											</c:otherwise>
										</c:choose>
									</div>
									<div class="col-md-4">
										<input type="file" class="form-control" name="logo_path" />
									</div>
								</div>
							
							
								<div class="form-group">
									<label for="facebook_lg_path" class="control-label col-md-4">Facebook Logo Preview</label>
									<div class="col-md-4" style="background-color:#f5f5f5;">
										<c:choose>
											<c:when test="${wsr.facebook_lg_path==null || wsr.facebook_lg_path==''}">
												<img style="border:2px solid black;" src="data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiIHN0YW5kYWxvbmU9InllcyI/PjxzdmcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIiB3aWR0aD0iMTQwIiBoZWlnaHQ9IjE0MCIgdmlld0JveD0iMCAwIDE0MCAxNDAiIHByZXNlcnZlQXNwZWN0UmF0aW89Im5vbmUiPjxkZWZzLz48cmVjdCB3aWR0aD0iMTQwIiBoZWlnaHQ9IjE0MCIgZmlsbD0iI0VFRUVFRSIvPjxnPjx0ZXh0IHg9IjQ1LjUiIHk9IjcwIiBzdHlsZT0iZmlsbDojQUFBQUFBO2ZvbnQtd2VpZ2h0OmJvbGQ7Zm9udC1mYW1pbHk6QXJpYWwsIEhlbHZldGljYSwgT3BlbiBTYW5zLCBzYW5zLXNlcmlmLCBtb25vc3BhY2U7Zm9udC1zaXplOjEwcHQ7ZG9taW5hbnQtYmFzZWxpbmU6Y2VudHJhbCI+MTQweDE0MDwvdGV4dD48L2c+PC9zdmc+" style="width:64px; height:64px;" alt="Facebook Logo" title="Facebook Logo">
											</c:when>
											<c:otherwise>
												<img style="border:2px solid black;" src="${ctx}/upload/front-end/facebook_lg.png" style="width:64px; height:64px;" alt="Facebook Logo" title="Facebook Logo">
											</c:otherwise>
										</c:choose>
									</div>
									<div class="col-md-4">
										<input type="file" class="form-control" name="facebook_lg_path" />
									</div>
								</div>
							
								<div class="form-group">
									<label for="facebook_lg_path" class="control-label col-md-4">GooglePlus Logo Preview</label>
									<div class="col-md-4" style="background-color:#f5f5f5;">
										<c:choose>
											<c:when test="${wsr.googleplus_lg_path==null || wsr.googleplus_lg_path==''}">
												<img style="border:2px solid black;" src="data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiIHN0YW5kYWxvbmU9InllcyI/PjxzdmcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIiB3aWR0aD0iMTQwIiBoZWlnaHQ9IjE0MCIgdmlld0JveD0iMCAwIDE0MCAxNDAiIHByZXNlcnZlQXNwZWN0UmF0aW89Im5vbmUiPjxkZWZzLz48cmVjdCB3aWR0aD0iMTQwIiBoZWlnaHQ9IjE0MCIgZmlsbD0iI0VFRUVFRSIvPjxnPjx0ZXh0IHg9IjQ1LjUiIHk9IjcwIiBzdHlsZT0iZmlsbDojQUFBQUFBO2ZvbnQtd2VpZ2h0OmJvbGQ7Zm9udC1mYW1pbHk6QXJpYWwsIEhlbHZldGljYSwgT3BlbiBTYW5zLCBzYW5zLXNlcmlmLCBtb25vc3BhY2U7Zm9udC1zaXplOjEwcHQ7ZG9taW5hbnQtYmFzZWxpbmU6Y2VudHJhbCI+MTQweDE0MDwvdGV4dD48L2c+PC9zdmc+" style="width:64px; height:64px;" alt="GooglePlus Logo" title="GooglePlus Logo">
											</c:when>
											<c:otherwise>
												<img style="border:2px solid black;" src="${ctx}/upload/front-end/googleplus_lg.png" style="width:64px; height:64px;" alt="GooglePlus Logo" title="GooglePlus Logo">
											</c:otherwise>
										</c:choose>
									</div>
									<div class="col-md-4">
										<input type="file" class="form-control" name="googleplus_lg_path" />
									</div>
								</div>
							
								<div class="form-group">
									<label for="twitter_lg_path" class="control-label col-md-4">Twitter Logo Preview</label>
									<div class="col-md-4" style="background-color:#f5f5f5;">
										<c:choose>
											<c:when test="${wsr.twitter_lg_path==null || wsr.twitter_lg_path==''}">
												<img style="border:2px solid black;" src="data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiIHN0YW5kYWxvbmU9InllcyI/PjxzdmcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIiB3aWR0aD0iMTQwIiBoZWlnaHQ9IjE0MCIgdmlld0JveD0iMCAwIDE0MCAxNDAiIHByZXNlcnZlQXNwZWN0UmF0aW89Im5vbmUiPjxkZWZzLz48cmVjdCB3aWR0aD0iMTQwIiBoZWlnaHQ9IjE0MCIgZmlsbD0iI0VFRUVFRSIvPjxnPjx0ZXh0IHg9IjQ1LjUiIHk9IjcwIiBzdHlsZT0iZmlsbDojQUFBQUFBO2ZvbnQtd2VpZ2h0OmJvbGQ7Zm9udC1mYW1pbHk6QXJpYWwsIEhlbHZldGljYSwgT3BlbiBTYW5zLCBzYW5zLXNlcmlmLCBtb25vc3BhY2U7Zm9udC1zaXplOjEwcHQ7ZG9taW5hbnQtYmFzZWxpbmU6Y2VudHJhbCI+MTQweDE0MDwvdGV4dD48L2c+PC9zdmc+" style="width:64px; height:64px;" alt="Twitter Logo" title="Twitter Logo">
											</c:when>
											<c:otherwise>
												<img style="border:2px solid black;" src="${ctx}/upload/front-end/twitter_lg.png" style="width:64px; height:64px;" alt="Twitter Logo" title="Twitter Logo">
											</c:otherwise>
										</c:choose>
									</div>
									<div class="col-md-4">
										<input type="file" class="form-control" name="twitter_lg_path" />
									</div>
								</div>
							
								<div class="form-group">
									<label for="youtube_lg_path" class="control-label col-md-4">YouTube Logo Preview</label>
									<div class="col-md-4" style="background-color:#f5f5f5;">
										<c:choose>
											<c:when test="${wsr.youtube_lg_path==null || wsr.youtube_lg_path==''}">
												<img style="border:2px solid black;" src="data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiIHN0YW5kYWxvbmU9InllcyI/PjxzdmcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIiB3aWR0aD0iMTQwIiBoZWlnaHQ9IjE0MCIgdmlld0JveD0iMCAwIDE0MCAxNDAiIHByZXNlcnZlQXNwZWN0UmF0aW89Im5vbmUiPjxkZWZzLz48cmVjdCB3aWR0aD0iMTQwIiBoZWlnaHQ9IjE0MCIgZmlsbD0iI0VFRUVFRSIvPjxnPjx0ZXh0IHg9IjQ1LjUiIHk9IjcwIiBzdHlsZT0iZmlsbDojQUFBQUFBO2ZvbnQtd2VpZ2h0OmJvbGQ7Zm9udC1mYW1pbHk6QXJpYWwsIEhlbHZldGljYSwgT3BlbiBTYW5zLCBzYW5zLXNlcmlmLCBtb25vc3BhY2U7Zm9udC1zaXplOjEwcHQ7ZG9taW5hbnQtYmFzZWxpbmU6Y2VudHJhbCI+MTQweDE0MDwvdGV4dD48L2c+PC9zdmc+" style="width:64px; height:64px;" alt="YouTube Logo" title="YouTube Logo">
											</c:when>
											<c:otherwise>
												<img style="border:2px solid black;" src="${ctx}/upload/front-end/youtube_lg.png" style="width:64px; height:64px;" alt="YouTube Logo" title="YouTube Logo">
											</c:otherwise>
										</c:choose>
									</div>
									<div class="col-md-4">
										<input type="file" class="form-control" name="youtube_lg_path" />
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