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
												<img style="border:2px solid black; width:111px; height:46px;" src="data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiIHN0YW5kYWxvbmU9InllcyI/PjxzdmcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIiB3aWR0aD0iMTQwIiBoZWlnaHQ9IjE0MCIgdmlld0JveD0iMCAwIDE0MCAxNDAiIHByZXNlcnZlQXNwZWN0UmF0aW89Im5vbmUiPjxkZWZzLz48cmVjdCB3aWR0aD0iMTQwIiBoZWlnaHQ9IjE0MCIgZmlsbD0iI0VFRUVFRSIvPjxnPjx0ZXh0IHg9IjQ1LjUiIHk9IjcwIiBzdHlsZT0iZmlsbDojQUFBQUFBO2ZvbnQtd2VpZ2h0OmJvbGQ7Zm9udC1mYW1pbHk6QXJpYWwsIEhlbHZldGljYSwgT3BlbiBTYW5zLCBzYW5zLXNlcmlmLCBtb25vc3BhY2U7Zm9udC1zaXplOjEwcHQ7ZG9taW5hbnQtYmFzZWxpbmU6Y2VudHJhbCI+MTQweDE0MDwvdGV4dD48L2c+PC9zdmc+" alt="Company Logo" title="Company Logo">
											</c:when>
											<c:otherwise>
												<img style="border:2px solid black; width:111px; height:46px;" src="${ctx}/upload/front-end/logo.png" alt="Company Logo" title="Company Logo">
											</c:otherwise>
										</c:choose>
									</div>
									<div class="col-md-4">
										<input type="file" class="form-control" name="logo_path" />
									</div>
								</div>
							
							
								<div class="form-group">
									<label for="facebook_lg_path" class="control-label col-md-4">Facebook Logo</label>
									<div class="col-md-4" style="background-color:#f5f5f5;">
										<c:choose>
											<c:when test="${wsr.facebook_lg_path==null || wsr.facebook_lg_path==''}">
												<img style="border:2px solid black; width:64px; height:64px;" src="data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiIHN0YW5kYWxvbmU9InllcyI/PjxzdmcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIiB3aWR0aD0iMTQwIiBoZWlnaHQ9IjE0MCIgdmlld0JveD0iMCAwIDE0MCAxNDAiIHByZXNlcnZlQXNwZWN0UmF0aW89Im5vbmUiPjxkZWZzLz48cmVjdCB3aWR0aD0iMTQwIiBoZWlnaHQ9IjE0MCIgZmlsbD0iI0VFRUVFRSIvPjxnPjx0ZXh0IHg9IjQ1LjUiIHk9IjcwIiBzdHlsZT0iZmlsbDojQUFBQUFBO2ZvbnQtd2VpZ2h0OmJvbGQ7Zm9udC1mYW1pbHk6QXJpYWwsIEhlbHZldGljYSwgT3BlbiBTYW5zLCBzYW5zLXNlcmlmLCBtb25vc3BhY2U7Zm9udC1zaXplOjEwcHQ7ZG9taW5hbnQtYmFzZWxpbmU6Y2VudHJhbCI+MTQweDE0MDwvdGV4dD48L2c+PC9zdmc+" alt="Facebook Logo" title="Facebook Logo">
											</c:when>
											<c:otherwise>
												<img style="border:2px solid black; width:64px; height:64px;" src="${ctx}/upload/front-end/facebook_lg.png" alt="Facebook Logo" title="Facebook Logo">
											</c:otherwise>
										</c:choose>
									</div>
									<div class="col-md-4">
										<input type="file" class="form-control" name="facebook_lg_path" />
									</div>
								</div>
								<div class="form-group">
									<label for="facebook_url" class="control-label col-md-4">Facebook URL</label>
									<div class="col-md-6">
										<input type="text" class="form-control" name="facebook_url" value="${wsr.facebook_url}" />
									</div>
								</div>
							
								<div class="form-group">
									<label for="facebook_lg_path" class="control-label col-md-4">GooglePlus Logo</label>
									<div class="col-md-4" style="background-color:#f5f5f5;">
										<c:choose>
											<c:when test="${wsr.googleplus_lg_path==null || wsr.googleplus_lg_path==''}">
												<img style="border:2px solid black; width:64px; height:64px;" src="data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiIHN0YW5kYWxvbmU9InllcyI/PjxzdmcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIiB3aWR0aD0iMTQwIiBoZWlnaHQ9IjE0MCIgdmlld0JveD0iMCAwIDE0MCAxNDAiIHByZXNlcnZlQXNwZWN0UmF0aW89Im5vbmUiPjxkZWZzLz48cmVjdCB3aWR0aD0iMTQwIiBoZWlnaHQ9IjE0MCIgZmlsbD0iI0VFRUVFRSIvPjxnPjx0ZXh0IHg9IjQ1LjUiIHk9IjcwIiBzdHlsZT0iZmlsbDojQUFBQUFBO2ZvbnQtd2VpZ2h0OmJvbGQ7Zm9udC1mYW1pbHk6QXJpYWwsIEhlbHZldGljYSwgT3BlbiBTYW5zLCBzYW5zLXNlcmlmLCBtb25vc3BhY2U7Zm9udC1zaXplOjEwcHQ7ZG9taW5hbnQtYmFzZWxpbmU6Y2VudHJhbCI+MTQweDE0MDwvdGV4dD48L2c+PC9zdmc+" alt="GooglePlus Logo" title="GooglePlus Logo">
											</c:when>
											<c:otherwise>
												<img style="border:2px solid black; width:64px; height:64px;" src="${ctx}/upload/front-end/googleplus_lg.png" alt="GooglePlus Logo" title="GooglePlus Logo">
											</c:otherwise>
										</c:choose>
									</div>
									<div class="col-md-4">
										<input type="file" class="form-control" name="googleplus_lg_path" />
									</div>
								</div>
								<div class="form-group">
									<label for="googleplus_url" class="control-label col-md-4">GooglePlus URL</label>
									<div class="col-md-6">
										<input type="text" class="form-control" name="googleplus_url" value="${wsr.googleplus_url}" />
									</div>
								</div>
							
								<div class="form-group">
									<label for="twitter_lg_path" class="control-label col-md-4">Twitter Logo</label>
									<div class="col-md-4" style="background-color:#f5f5f5;">
										<c:choose>
											<c:when test="${wsr.twitter_lg_path==null || wsr.twitter_lg_path==''}">
												<img style="border:2px solid black; width:64px; height:64px;" src="data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiIHN0YW5kYWxvbmU9InllcyI/PjxzdmcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIiB3aWR0aD0iMTQwIiBoZWlnaHQ9IjE0MCIgdmlld0JveD0iMCAwIDE0MCAxNDAiIHByZXNlcnZlQXNwZWN0UmF0aW89Im5vbmUiPjxkZWZzLz48cmVjdCB3aWR0aD0iMTQwIiBoZWlnaHQ9IjE0MCIgZmlsbD0iI0VFRUVFRSIvPjxnPjx0ZXh0IHg9IjQ1LjUiIHk9IjcwIiBzdHlsZT0iZmlsbDojQUFBQUFBO2ZvbnQtd2VpZ2h0OmJvbGQ7Zm9udC1mYW1pbHk6QXJpYWwsIEhlbHZldGljYSwgT3BlbiBTYW5zLCBzYW5zLXNlcmlmLCBtb25vc3BhY2U7Zm9udC1zaXplOjEwcHQ7ZG9taW5hbnQtYmFzZWxpbmU6Y2VudHJhbCI+MTQweDE0MDwvdGV4dD48L2c+PC9zdmc+" alt="Twitter Logo" title="Twitter Logo">
											</c:when>
											<c:otherwise>
												<img style="border:2px solid black; width:64px; height:64px;" src="${ctx}/upload/front-end/twitter_lg.png" alt="Twitter Logo" title="Twitter Logo">
											</c:otherwise>
										</c:choose>
									</div>
									<div class="col-md-4">
										<input type="file" class="form-control" name="twitter_lg_path" />
									</div>
								</div>
								<div class="form-group">
									<label for="twitter_url" class="control-label col-md-4">Twitter URL</label>
									<div class="col-md-6">
										<input type="text" class="form-control" name="twitter_url" value="${wsr.twitter_url}" />
									</div>
								</div>
							
								<div class="form-group">
									<label for="youtube_lg_path" class="control-label col-md-4">YouTube Logo</label>
									<div class="col-md-4" style="background-color:#f5f5f5;">
										<c:choose>
											<c:when test="${wsr.youtube_lg_path==null || wsr.youtube_lg_path==''}">
												<img style="border:2px solid black; width:64px; height:64px;" src="data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiIHN0YW5kYWxvbmU9InllcyI/PjxzdmcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIiB3aWR0aD0iMTQwIiBoZWlnaHQ9IjE0MCIgdmlld0JveD0iMCAwIDE0MCAxNDAiIHByZXNlcnZlQXNwZWN0UmF0aW89Im5vbmUiPjxkZWZzLz48cmVjdCB3aWR0aD0iMTQwIiBoZWlnaHQ9IjE0MCIgZmlsbD0iI0VFRUVFRSIvPjxnPjx0ZXh0IHg9IjQ1LjUiIHk9IjcwIiBzdHlsZT0iZmlsbDojQUFBQUFBO2ZvbnQtd2VpZ2h0OmJvbGQ7Zm9udC1mYW1pbHk6QXJpYWwsIEhlbHZldGljYSwgT3BlbiBTYW5zLCBzYW5zLXNlcmlmLCBtb25vc3BhY2U7Zm9udC1zaXplOjEwcHQ7ZG9taW5hbnQtYmFzZWxpbmU6Y2VudHJhbCI+MTQweDE0MDwvdGV4dD48L2c+PC9zdmc+" alt="YouTube Logo" title="YouTube Logo">
											</c:when>
											<c:otherwise>
												<img style="border:2px solid black; width:64px; height:64px;" src="${ctx}/upload/front-end/youtube_lg.png" alt="YouTube Logo" title="YouTube Logo">
											</c:otherwise>
										</c:choose>
									</div>
									<div class="col-md-4">
										<input type="file" class="form-control" name="youtube_lg_path" />
									</div>
								</div>
								<div class="form-group">
									<label for="youtube_url" class="control-label col-md-4">YouTube URL</label>
									<div class="col-md-6">
										<input type="text" class="form-control" name="youtube_url" value="${wsr.youtube_url}" />
									</div>
								</div>
							
								<div class="form-group">
									<label for="two_dimensional_code_path" class="control-label col-md-4">Two Dimensional Code</label>
									<div class="col-md-4" style="background-color:#f5f5f5;">
										<c:choose>
											<c:when test="${wsr.two_dimensional_code_path==null || wsr.two_dimensional_code_path==''}">
												<img style="border:2px solid black; width:160px; height:160px;" src="data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiIHN0YW5kYWxvbmU9InllcyI/PjxzdmcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIiB3aWR0aD0iMTQwIiBoZWlnaHQ9IjE0MCIgdmlld0JveD0iMCAwIDE0MCAxNDAiIHByZXNlcnZlQXNwZWN0UmF0aW89Im5vbmUiPjxkZWZzLz48cmVjdCB3aWR0aD0iMTQwIiBoZWlnaHQ9IjE0MCIgZmlsbD0iI0VFRUVFRSIvPjxnPjx0ZXh0IHg9IjQ1LjUiIHk9IjcwIiBzdHlsZT0iZmlsbDojQUFBQUFBO2ZvbnQtd2VpZ2h0OmJvbGQ7Zm9udC1mYW1pbHk6QXJpYWwsIEhlbHZldGljYSwgT3BlbiBTYW5zLCBzYW5zLXNlcmlmLCBtb25vc3BhY2U7Zm9udC1zaXplOjEwcHQ7ZG9taW5hbnQtYmFzZWxpbmU6Y2VudHJhbCI+MTQweDE0MDwvdGV4dD48L2c+PC9zdmc+" alt="Two Dimensional Code" title="Two Dimensional Code">
											</c:when>
											<c:otherwise>
												<img style="border:2px solid black; width:160px; height:160px;" src="${ctx}/upload/front-end/two_dimensional_code.png" alt="Two Dimensional Code" title="Two Dimensional Code">
											</c:otherwise>
										</c:choose>
									</div>
									<div class="col-md-4">
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