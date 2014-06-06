<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<jsp:include page="header.jsp" />

<style>
#navhead {
	margin-bottom:0;
}
.home-title {
	font-size:36px;
	margin-top: 20px;
	margin-bottom: 10px;
}
</style>

<jsp:include page="carousel.jsp" />

<jsp:include page="welcome.jsp"/>

<jsp:include page="footer.jsp" />
<jsp:include page="script.jsp" />
<script type="text/javascript">
(function($){
	
})(jQuery);
</script>
<jsp:include page="footer-end.jsp" />