<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<jsp:include page="../header.jsp" />
<jsp:include page="../alert.jsp" />
<div class="container">
	<div class="row">
		<div class="col-md-12">
			<div class="panel panel-success">
				<div class="panel-heading">
					<h4 class="panel-title">
						Equipment Log View
					</h4>
				</div>
				<div id="equip-log-view"></div>
			</div>
		</div>
	</div>
</div>

<script type="text/html" id="equip_log_tmpl">
<jsp:include page="equip-log-view.html" />
</script>

<jsp:include page="../footer.jsp" />
<jsp:include page="../script.jsp" />
<script>

	var ctx = '${ctx}';
	
</script>
<script type="text/javascript" src="${ctx}/public/broadband-user/inventory/equip-log.js"></script>
<jsp:include page="../footer-end.jsp" />