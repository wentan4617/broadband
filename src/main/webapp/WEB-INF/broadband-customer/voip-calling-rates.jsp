<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %> 
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<jsp:include page="header.jsp" />

<style>
#navhead {
	margin-bottom:0;
}
.affix {
	width: 263px;
	top: 40px;
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

.pagination>li:first-child>a, 
.pagination>li:first-child>span {
	margin-left: -1px;
	border-bottom-left-radius: 0;
	border-top-left-radius: 0;
}
.pagination>li:last-child>a, 
.pagination>li:last-child>span {
	border-bottom-right-radius: 0;
	border-top-right-radius: 0;
}
.panel-success {
	border-top-color:transparent;
}
</style>

<div class="container">

	<div class="page-header">
		<h1>VoIP Rates</h1>
	</div>

	<div class="row" style="margin-bottom:20px;">
		
		<div class="col-md-9 col-xs-12 col-sm-12">

			<!-- Nav tabs -->
			<ul class="nav nav-tabs" id="rates">
				<li class="active"><a href="#personal-voip-rates" data-toggle="tab" data-type="p"><strong>Personal VoIP Rates</strong></a></li>
				<li><a href="#business-voip-rates" data-toggle="tab" data-type="b"><strong>Business VoIP Rates</strong></a></li>
			</ul>

			<!-- Tab panes -->
			<div class="tab-content panel panel-success">
				<div class="panel-body tab-pane fade in active" id="personal-voip-rates" >	
				
					<table class="table table-bordered">
						<thead>
							<tr class="success">
								<th>NZ Local</th>
								<th>NZ National</th>
								<th>NZ Mobile</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>Free</td>
								<td>Free</td>
								<td>$ 0.10</td>
							</tr>
						</tbody>
					</table>		
					
					<div class="panel panel-success">
						<div class="panel-heading">
							<h4 class="panel-title">International</h4>
						</div>
						<c:if test="${fn:length(letterVrs) > 0}">
							<table class="table table-bordered">
								<thead>
									<tr>
										<th>Country name</th>
										<th>Area Prefix</th>
										<th>Dollar per min rate</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="lVr" items="${letterVrs }">
										<c:if test="${fn:length(lVr.vrs) > 0 }">
											<tr class="active" id="${lVr.letter}p">
												<td colspan="3">${lVr.letter}</td>
											</tr>
											<c:forEach var="vr" items="${lVr.vrs }">
												<tr>
													<td>${vr.area_name}</td>
													<td>${vr.area_prefix}</td>
													<td>$ ${vr.billed_per_min}</td>
												</tr>
											</c:forEach>
										</c:if>
									</c:forEach>
								</tbody>
							</table>
						</c:if>
					</div>
					
				</div>
				
				
				<div class="panel-body tab-pane fade" id="business-voip-rates" >
				
					<table class="table table-bordered">
						<thead>
							<tr class="success">
								<th>NZ Local</th>
								<th>NZ National</th>
								<th>NZ Mobile</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>Free</td>
								<td>$ 0.04</td>
								<td>$ 0.10</td>
							</tr>
						</tbody>
					</table>		
					
					<div class="panel panel-success">
						<div class="panel-heading">
							<h4 class="panel-title">International</h4>
						</div>
						<c:if test="${fn:length(letterVrs) > 0}">
							<table class="table table-bordered">
								<thead>
									<tr>
										<th>Country name</th>
										<th>Area Prefix</th>
										<th>Dollar per min rate</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="lVr" items="${letterVrs }">
										<c:if test="${fn:length(lVr.vrs) > 0 }">
											<tr class="active" id="${lVr.letter}b">
												<td colspan="3">${lVr.letter}</td>
											</tr>
											<c:forEach var="vr" items="${lVr.vrs }">
												<tr>
													<td>${vr.area_name}</td>
													<td>${vr.area_prefix}</td>
													<td>$ ${vr.billed_per_min}</td>
												</tr>
											</c:forEach>
										</c:if>
									</c:forEach>
								</tbody>
							</table>
						</c:if>
					</div>
				</div>
				
				
				
				
				
			</div>			
		</div>
		<div class="col-md-3 hidden-xs hidden-sm">
			 <div data-spy="affix" data-offset-top="100">
				<ul class="pagination" style="margin-top:0;">
					<c:forEach var="lVr" items="${letterVrs }" varStatus="item">
						<c:if test="${fn:length(lVr.vrs) > 0 }">
							<li>
								<a href="#${lVr.letter}p" style="${item.last ? 'width:80px;height:35px;' : 'width:40px;height:35px;'}" data-letter="${lVr.letter }">${lVr.letter }</a>
							</li>
						</c:if>
					</c:forEach>
				</ul>
			</div>
		</div>
	</div>
</div>

<jsp:include page="footer.jsp" />
<jsp:include page="script.jsp" />
<script type="text/javascript">
(function($){
	$('body').scrollspy({ target: '.navbar-example' });
	
	$('a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
		var type = $(e.target).attr('data-type');
		//console.log(type);
		$('a[data-letter]').each(function(){
			var l = $(this).attr('data-letter');
			$(this).attr('href', '#' + l + type);
		});
	});
})(jQuery);
</script>
<jsp:include page="footer-end.jsp" />