<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<jsp:include page="header.jsp" />

<style>
.panel-default {
	border-top-color:transparent;
}
.home-title {
	font-size:36px;
}
</style>

<div class="container" style="margin-top: 20px;">
	<div class="row" style="margin-bottom: 20px;">

		<div class="col-md-12">
			<!-- Nav tabs -->
			<ul class="nav nav-tabs">
				<li class="active"><a href="#website" data-toggle="tab"><strong>CyberPark Limited</strong></a></li>
				<li><a href="#a" data-toggle="tab"><strong>BUSINESS PLAN T&C</strong></a></li>
				<li><a href="#b" data-toggle="tab"><strong>Business Line WI-FI PLANS T&C</strong></a></li>
				<li><a href="#c" data-toggle="tab"><strong>PERSONALLINE T&C</strong></a></li>
				<li><a href="#d" data-toggle="tab"><strong>UFB PLAN T&C</strong></a></li>
			</ul>

			<!-- Tab panes -->
			<div class="tab-content panel panel-default">
				<div class="panel-body tab-pane fade in active" id="website" >				
					${cyberpark.term_contracts }
				</div>
				<div class="panel-body tab-pane fade" id="a" >
					${cyberpark.tc_business_retails }
				</div>
				<div class="panel-body tab-pane fade" id="b">
					${cyberpark.tc_business_wifi }
				</div>
				<div class="panel-body tab-pane fade" id="c">
					${cyberpark.tc_personal }
				</div>
				<div class="panel-body tab-pane fade" id="d">
					${cyberpark.tc_ufb }
				</div>
			</div>
		</div>
	</div>
</div>

<jsp:include page="footer.jsp" />
<jsp:include page="script.jsp" />
<script type="text/javascript">
(function($){
	
})(jQuery);
</script>
<jsp:include page="footer-end.jsp" />