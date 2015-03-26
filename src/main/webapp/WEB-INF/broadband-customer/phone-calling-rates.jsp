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

<div class="container" style="">

	<div class="page-header">
		<h1>Phone Rates</h1>
	</div>
	
	<div class="row" style="margin-bottom:20px;">
		
		<div class="col-md-9 col-xs-12 col-sm-12">
			
			<!-- Nav tabs -->
			<ul class="nav nav-tabs" id="rates">
				<li class="active"><a href="#personal-rates" data-toggle="tab" data-type="p"><strong>Personal Phone Rates</strong></a></li>
				<li><a href="#business-rates" data-toggle="tab" data-type="b"><strong>Business Phone Rates</strong></a></li>
			</ul>

			<!-- Tab panes -->
			<div class="tab-content panel panel-success">
				<div class="panel-body tab-pane fade in active" id="personal-rates" >	
				
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
								<td>$ 0.09</td>
								<td>$ 0.28</td>
							</tr>
						</tbody>
					</table>		
					
					<div class="panel panel-success">
						<div class="panel-heading">
							<h4 class="panel-title">International</h4>
						</div>
						<c:if test="${fn:length(letterCirs) > 0}">
							<table class="table table-bordered">
								<thead>
									<tr>
										<th>Country name</th>
										<th>Area Prefix</th>
										<th>Dollar per min rate</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="lCir" items="${letterCirs }">
										<c:if test="${fn:length(lCir.cirs) > 0 }">
											<tr class="active" id="${lCir.letter}p">
												<td colspan="3">${lCir.letter}</td>
											</tr>
											<c:forEach var="cir" items="${lCir.cirs }">
												<tr>
													<td>${cir.area_name}</td>
													<td>${cir.area_prefix}</td>
													<td>$ ${cir.rate_cost}</td>
												</tr>
											</c:forEach>
										</c:if>
									</c:forEach>
								</tbody>
							</table>
						</c:if>
					</div>
					
				</div>
				
				
				<div class="panel-body tab-pane fade" id="business-rates" >
				
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
								<td>$ 0.045</td>
								<td>$ 0.09</td>
								<td>$ 0.28</td>
							</tr>
						</tbody>
					</table>		
					
					<div class="panel panel-success">
						<div class="panel-heading">
							<h4 class="panel-title">International</h4>
						</div>
						<c:if test="${fn:length(letterCirs) > 0}">
							<table class="table table-bordered">
								<thead>
									<tr>
										<th>Country name</th>
										<th>Area Prefix</th>
										<th>Dollar per min rate</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="lCir" items="${letterCirs }">
										<c:if test="${fn:length(lCir.cirs) > 0 }">
											<tr class="active" id="${lCir.letter}b">
												<td colspan="3">${lCir.letter}</td>
											</tr>
											<c:forEach var="cir" items="${lCir.cirs }">
												<tr>
													<td>${cir.area_name}</td>
													<td>${cir.area_prefix}</td>
													<td>$ ${cir.rate_cost}</td>
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
					<c:forEach var="lCir" items="${letterCirs }" varStatus="item">
						<c:if test="${fn:length(lCir.cirs) > 0 }">
							<li>
								<a href="#${lCir.letter}p" style="${item.last ? 'width:80px;height:35px;' : 'width:40px;height:35px;'}" data-letter="${lCir.letter }">${lCir.letter }</a>
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