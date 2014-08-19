<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<jsp:include page="../header.jsp" />
<jsp:include page="../alert.jsp" />

<style>
.panel-default {
	border-top-color:transparent;
}
thead th {text-align:center;}
tbody td {text-align:center;}
.input-xs{
	height:26px;
}
.form-group{
	margin-bottom:6px;
	padding-bottom:0px;
}
.btn {
	padding: 0 12px;
}
.bootstrap-select.btn-group, .bootstrap-select.btn-group[class*="span"] {
	margin-bottom: 0;
}
.align-right{
	text-align:right;
}
</style>


<div class="container">
	<div class="row">
		<div class="col-md-12">
		
			<!-- Nav tabs -->
			<ul class="nav nav-tabs">
				<li class="active"><a href="#material_view" data-toggle="tab"><strong>Material View</strong></a></li>
				<li><a href="#combo_view" data-toggle="tab"><strong>Combo View</strong></a></li>
			</ul>

			<!-- Tab panes -->
			<div class="tab-content panel panel-default">
				<div class="panel-body tab-pane fade in active" id="material_view" ></div>
				<div class="panel-body tab-pane fade" id="combo_view"></div>
			</div>
		</div>
	</div>
</div>

<!-- Material View Template -->
<script type="text/html" id="material_view_table_tmpl">
<jsp:include page="material-view-page.html" />
</script>
<!-- Combo View Template -->
<script type="text/html" id="combo_view_table_tmpl">
<jsp:include page="material-combo-view-page.html" />
</script>

<jsp:include page="../footer.jsp" />
<jsp:include page="../script.jsp" />
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/bootstrap-select.min.js"></script>
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/jTmpl.js"></script>
<script type="text/javascript">
(function($){
	
	$.getMaterialPage = function(pageNo) {
		
		$.get('${ctx}/broadband-wholesale/material/view/'+pageNo, function(page){
			page.ctx = '${ctx}';
	   		var $table = $('#material_view');
			$table.html(tmpl('material_view_table_tmpl', page));
			$table.find('tfoot a').click(function(){
				$.getMaterialPage($(this).attr('data-pageNo'));
			});
			
		}, "json");
	}
	
	$.getComboPage = function(pageNo) {
		
		$.get('${ctx}/broadband-wholesale/material/combo/view/'+pageNo, function(page){
			page.ctx = '${ctx}';
	   		var $table = $('#combo_view');
			$table.html(tmpl('combo_view_table_tmpl', page));
			$table.find('tfoot a').click(function(){
				$.getMaterialComboPage($(this).attr('data-pageNo'));
			});
			
		}, "json");
	}

	$.getMaterialPage(1);
	$.getComboPage(1);
	
	
})(jQuery);
</script>
<jsp:include page="../footer-end.jsp" />