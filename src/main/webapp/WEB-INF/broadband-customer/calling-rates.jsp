<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %> 
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<jsp:include page="header.jsp" />

<style>

.affix {
	width: 263px;
	top: 0;
}
.nav-pills>li>a {
	border-radius: 0px;
}
.nav-pills>li:first-child>a {
	border-radius: 4px 4px 0 0;
}
.nav-pills>li:last-child>a {
	border-radius: 0 0 4px 4px;
}
.panel-primary>.panel-footer {
	color: #fff;
	background-color: #428bca;
	border-color: #428bca;
}
.panel-info>.panel-footer {
	color: #31708f;
	background-color: #d9edf7;
	border-color: #bce8f1;
}
</style>

<div class="container">
	<div class="row" style="margin-bottom:20px;">
		
		<div class="col-md-9 col-xs-12 col-sm-12">
			<div class="panel panel-success">
				<div class="panel-heading">
					<h4 class="panel-title">International Calling Rates</h4>
				</div>
				<c:if test="${fn:length(letterCirs) > 0}">
					<table class="table">
						<thead>
							<tr>
								<th>Country name</th>
								<th>Cents per min rate</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="lCir" items="letterCirs">
								<tr>
									<td>${lCir}</td>
									<td>&nbsp;</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</c:if>
			</div>
			
			
		</div>
		<div class="col-md-3 hidden-xs hidden-sm">
			 <div class="navbar-example panel panel-default" style="margin-top:40px;" data-spy="affix" data-offset-top="150">
				<ul class="nav nav-pills nav-stacked">
					<li class="active"><a href="#introduction">A</a></li>
					<li class="active"><a href="#introduction">B</a></li>
					<li class="active"><a href="#introduction">C</a></li>
					<li class="active"><a href="#introduction">D</a></li>
					<li class="active"><a href="#introduction">E</a></li>
					<li class="active"><a href="#introduction">F</a></li>
					<li class="active"><a href="#introduction">G</a></li>
					<li class="active"><a href="#introduction">H</a></li>
					<li class="active"><a href="#introduction">I</a></li>
					<li class="active"><a href="#introduction">J</a></li>
					<li class="active"><a href="#introduction">K</a></li>
					<li class="active"><a href="#introduction">L</a></li>
					<li class="active"><a href="#introduction">M</a></li>
					<li class="active"><a href="#introduction">N</a></li>
					<li class="active"><a href="#introduction">O</a></li>
					<li class="active"><a href="#introduction">P</a></li>
					<li class="active"><a href="#introduction">Q</a></li>
					<li class="active"><a href="#introduction">R</a></li>
					<li class="active"><a href="#introduction">S</a></li>
					<li class="active"><a href="#introduction">T</a></li>
					<li class="active"><a href="#introduction">U</a></li>
					<li class="active"><a href="#introduction">V</a></li>
					<li class="active"><a href="#introduction">W</a></li>
					<li class="active"><a href="#introduction">X</a></li>
					<li class="active"><a href="#introduction">Y</a></li>
					<li class="active"><a href="#introduction">Z</a></li>
					<li class="active"><a href="#introduction">Other</a></li>
				</ul>
			</div>
		</div>
	</div>
</div>

<jsp:include page="footer.jsp" />
<jsp:include page="script.jsp" />
<script type="text/javascript">
(function($){
	$('body').scrollspy({ target: '.navbar-example' })
})(jQuery);
</script>
<jsp:include page="footer-end.jsp" />