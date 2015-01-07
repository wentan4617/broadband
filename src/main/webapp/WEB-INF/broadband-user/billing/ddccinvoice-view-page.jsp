<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<jsp:include page="../header.jsp" />
<jsp:include page="../alert.jsp" />
<style>
	.progress-lower {
		background:rgba(255,50,90,0.3);
	}
	.progress-low {
		background:rgba(255,90,120,0.3);
	}
	.progress-mid {
		background:rgba(255,130,150,0.3);
	}
	.progress-high {
		background:rgba(255,170,180,0.3);
	}
	.progress-higher {
		background:rgba(255,210,210,0.3);
	}
</style>
<div class="container">
	<div class="row">
		<div class="col-md-12">
			<div class="panel panel-success">
				<div class="panel-heading">
					<h4 class="panel-title">
						DD/CC Invoice View
					</h4>
				</div>
				<jsp:include page="ddccinvoice-query.html" />
				<hr>
				<div id="ddccinvoice-view-page"></div>
			</div>
		</div>
	</div>
</div>

<script type="text/html" id="ddccinvoice_view_page_tmpl">
<jsp:include page="ddccinvoice-view-page.html" />
</script>

<jsp:include page="../footer.jsp" />
<jsp:include page="../script.jsp" />
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/jTmpl.js"></script>
<script type="text/javascript">
(function($){
	
	function doPage(pageNo, customerType, yearMonth) {
	
		$.get('${ctx}/broadband-user/billing/ddccinvoice/view/' + pageNo + '/' + customerType + '/' + yearMonth, function(page){
			page.ctx = '${ctx}';
	   		var $div = $('#ddccinvoice-view-page');
	   		$div.html(tmpl('ddccinvoice_view_page_tmpl', page));
	   		$div.find('tfoot a').click(function(){
				doPage($(this).attr('data-pageNo'), customerType, yearMonth);
			});

	   		$('a[data-val="all"] span').html('${allSum}');
	   		$('a[data-val="personal"] span').html('${personalSum}');
	   		$('a[data-val="business"] span').html('${businessSum}');
	   		
	   	}, 'json');
	}

	$('a[data-id="customer_type"]').click(function(){
		
		var customer_type = 'all';
		
		var field = $(this).attr('data-id');
		$('a[data-id="customer_type"]').removeClass('active');
		$(this).addClass('active');
		if($(this).attr('data-val')!='all'){
			customer_type = $(this).attr('data-val');
		}
		
		doPage(1, customer_type, '0');
		
	});
	
	doPage(1, 'all', '0');
	
})(jQuery);
</script>
<jsp:include page="../footer-end.jsp" />